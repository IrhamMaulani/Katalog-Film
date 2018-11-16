package com.example.user.catalogfilm.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.catalogfilm.Fragment.NowPlayingFragment;
import com.example.user.catalogfilm.Fragment.SearchFilmFragment;
import com.example.user.catalogfilm.Fragment.UpcomingFragment;
import com.example.user.catalogfilm.R;

public class CategoryAdapter  extends FragmentPagerAdapter {
    private Context mContext;

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NowPlayingFragment();
        } else if (position == 1) {
            return new UpcomingFragment();
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
            return mContext.getString(R.string.now_playing);
        } else if (position == 1) {
            return mContext.getString(R.string.upcoming);
        }  else {
            return mContext.getString(R.string.search_film);
        }
    }
}
