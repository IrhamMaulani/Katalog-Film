package com.example.user.catalogfilm.Notification;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.catalogfilm.Model.FilmItems;
import com.example.user.catalogfilm.Model.GetFilmItems;
import com.example.user.catalogfilm.Network.ApiInterface;
import com.example.user.catalogfilm.R;
import com.example.user.catalogfilm.SharedPreferences.UserPreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSettingActivity extends AppCompatActivity{
    @BindView(R.id.switch_daily_reminder)
    Switch switchDailyReminder;

    @BindView(R.id.switch_today_reminder)
    Switch switchTodayReminder;

    Context context;

    private UserPreference mUserPreference;

    private AlarmReceiver alarmReceiver;

    private AlarmReleaseTodayReceiver alarmReleaseTodayReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);
        ButterKnife.bind(this);

        context = this;

        mUserPreference = new UserPreference(this);


        alarmReceiver = new AlarmReceiver();

        alarmReleaseTodayReceiver = new AlarmReleaseTodayReceiver();


        switchDailyReminder.setChecked(mUserPreference.getDailyReminder());

        switchTodayReminder.setChecked(mUserPreference.getDailyTodayRelease());


        switchDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    alarmReceiver.setAlarmDailyReminder(context, AlarmReceiver.TYPE_DAILY_REMAINDER);

                    mUserPreference.setDailyReminder(true);

                    Toast.makeText(getBaseContext(), "Daily Reminder Telah Hidupkan",
                            Toast.LENGTH_SHORT).show();

                }else {
                    alarmReceiver.cancelAlarm(context);
                    mUserPreference.setDailyReminder(false);

                }
            }
        });

        switchTodayReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    alarmReleaseTodayReceiver.setAlarmReleaseFilmReminder(context, AlarmReleaseTodayReceiver.TYPE_RELEASE_TODAY_REMINDER);


                    mUserPreference.setDailyTodayRelease(true);

                    Toast.makeText(getBaseContext(), "Daily Reminder Telah Hidupkan",
                            Toast.LENGTH_SHORT).show();
                }else {

                     alarmReleaseTodayReceiver.cancelAlarm(context);
                    mUserPreference.setDailyTodayRelease(false);

                }
            }
        });

    }





}
