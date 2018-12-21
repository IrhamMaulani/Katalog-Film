package com.example.user.catalogfilm.Notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.user.catalogfilm.Activity.MainActivity;
import com.example.user.catalogfilm.Model.FilmItems;
import com.example.user.catalogfilm.Model.GetFilmItems;
import com.example.user.catalogfilm.Network.ApiClient;
import com.example.user.catalogfilm.Network.ApiInterface;
import com.example.user.catalogfilm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmReleaseTodayReceiver extends BroadcastReceiver {
    final int ID_RELEASE_TODAY_REMINDER = 101;
    public static final String TYPE_RELEASE_TODAY_REMINDER = "TodayRemainder";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    private final static String GROUP_KEY_NOTIF = "group_key_notifs";

    private ApiInterface mApiInterface;
    private ArrayList<FilmItems> filmList = new ArrayList<>();

    private static int idNotif = 0;
    private static int maxNotif = 3;

    public AlarmReleaseTodayReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        showAlarmNotification(context, type, message, idNotif);
    }



    private void showAlarmNotification(Context context, String title, String message ,int notfiId) {
        String CHANNEL_ID = "channel_01";
        String CHANNEL_NAME = "AlarmManager channel";

        Log.v("MNTAP" , message);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, ID_RELEASE_TODAY_REMINDER, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder;

        if (idNotif < maxNotif) {
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(message)
                    .setContentText(message + " Has Been Released Today")
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setGroup(GROUP_KEY_NOTIF)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound);;
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine(message + " Has Been Released Today")
                    .addLine(message + " Has Been Released Today")
                    .setBigContentTitle(idNotif + " new emails")
                    .setSummaryText("mail@dicoding");
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(idNotif + " movies")
                    .setContentText(message + " has Been released")
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setGroup(GROUP_KEY_NOTIF)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            mBuilder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(idNotif, notification);
        }
    }

    public void setAlarmReleaseFilmReminder(final Context context, final String type){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String todaysDate = dateFormat.format(date);

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GetFilmItems> call = mApiInterface.getUpcoming("upcoming");
        call.enqueue(new Callback<GetFilmItems>() {
            @Override
            public void onResponse(Call<GetFilmItems> call, Response<GetFilmItems>
                    response) {

                if (response.isSuccessful()) {
                    filmList = (ArrayList<FilmItems>) response.body().getListDataFilm();

                    for (FilmItems film: filmList) {
                        if(film.getTanggalRilis().equals(todaysDate)){
                            Log.v("TAG", "ISI DARI FILM DENGAN TANGGAL: " + film.getJudul());

                            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(context, AlarmReceiver.class);
                            intent.putExtra(EXTRA_MESSAGE, film.getJudul() +" Has Been Release Today" );
                            intent.putExtra(EXTRA_TYPE, type);

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY, 8);
                            calendar.set(Calendar.MINUTE, 0);
                            calendar.set(Calendar.SECOND, 0);

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_TODAY_REMINDER, intent, 0);
                            if (alarmManager != null) {
                                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                            }
                        }

                        idNotif++;

                    }

                }
            }

            @Override
            public void onFailure(Call<GetFilmItems> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }

        });

    }

    public void cancelAlarm(Context context ) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_TODAY_REMINDER, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show();
    }
}


