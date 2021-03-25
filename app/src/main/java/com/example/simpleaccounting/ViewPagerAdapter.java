package com.example.simpleaccounting;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String> lstTitle = new ArrayList<>();

    //Constructor
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstTitle.size();
    }

    //Override methods

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    //Add Fragment
    public void AddFragment(Fragment fragment,String title){
        lstFragment.add(fragment);
        lstTitle.add(title);
    }
}
