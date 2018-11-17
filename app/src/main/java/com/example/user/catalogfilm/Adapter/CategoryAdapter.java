package com.example.user.catalogfilm.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.catalogfilm.Fragment.SearchFilmFragment;
import com.example.user.catalogfilm.Fragment.UpcomingFragment;
import com.example.user.catalogfilm.R;

public class CategoryAdapter  extends FragmentPagerAdapter {
    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new UpcomingFragment("now_playing");
        } else if (position == 1) {
            return new UpcomingFragment("upcoming");
        } else  {
            return new SearchFilmFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getResources().getString(R.string.now_playing);
        } else if (position == 1) {
            return mContext.getResources().getString(R.string.upcoming);
        }  else {
            return mContext.getResources().getString(R.string.search_film);
        }
    }
}
