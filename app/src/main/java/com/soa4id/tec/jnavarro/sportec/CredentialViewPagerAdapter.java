package com.soa4id.tec.jnavarro.sportec;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CredentialViewPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
    private final ArrayList<String> mFragmentTitles = new ArrayList<String>();

    /**
     * Creates the CredentialViewPagerAdapter
     * @param fm
     */
    public CredentialViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Returns the reference to the i-eth fragment.
     * @param i
     * @return
     */
    @Override
    public Fragment getItem(int i) {

        return this.mFragmentList.get(i);
    }

    /**
     * gets the count of fragments
     * @return
     */
    @Override
    public int getCount() {
        return this.mFragmentTitles.size();
    }


    /**
     * Gets the title of the i-eth fragment
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.mFragmentTitles.get(position);
    }

    /**
     * Adds a fragment to the list of fragments.
     * @param pFragmet
     * @param pFragmentTitle
     */
    public void addFragment (Fragment pFragmet, String pFragmentTitle){
        this.mFragmentTitles.add(pFragmentTitle);
        this.mFragmentList.add(pFragmet);

    }
}
