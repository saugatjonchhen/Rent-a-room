package com.example.saugatjonchhen.rentaroom.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.saugatjonchhen.rentaroom.Home.HomeFragment;
import com.example.saugatjonchhen.rentaroom.fragments.FavouriteFragment;
import com.example.saugatjonchhen.rentaroom.fragments.ChatFragment;
import com.example.saugatjonchhen.rentaroom.Login.LoginFragment;

/**
 * Created by Saugat Jonchhen on 11/22/2017.
 */

public class TabPageAdapter extends FragmentPagerAdapter {

    int tabCount;

    public TabPageAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeFragment tab1 = new HomeFragment();
                return tab1;
            case 1:
                FavouriteFragment tab2 = new FavouriteFragment();
                return tab2;
            case 2:
                ChatFragment tab3 = new ChatFragment();
                return tab3;
            case 3:
                LoginFragment tab4 = new LoginFragment();
                return tab4;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
