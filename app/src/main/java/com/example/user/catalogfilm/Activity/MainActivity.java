package com.example.user.catalogfilm.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.user.catalogfilm.Adapter.CategoryAdapter;
import com.example.user.catalogfilm.Notification.NotificationSettingActivity;
import com.example.user.catalogfilm.R;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);


        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());


        viewPager.setAdapter(adapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);


        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_local:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;

            case R.id.action_setting:
                Intent settingIntent = new Intent(MainActivity.this, NotificationSettingActivity.class);
                startActivity(settingIntent);
                break;



        }
        return super.onOptionsItemSelected(item);
    }
}
