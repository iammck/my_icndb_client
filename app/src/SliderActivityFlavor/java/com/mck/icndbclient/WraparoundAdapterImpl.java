package com.mck.icndbclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

/**
 * Created by mike on 4/20/2015.
 */
public class WraparoundAdapterImpl extends WraparoundFragmentStatePagerAdapter {
    private int size = 2;

    public WraparoundAdapterImpl(FragmentManager supportFragmentManager, ViewPager mPager) {
        super(supportFragmentManager, mPager);
    }

    @Override
    protected Fragment getFragmentForPosition(int position) {
        Fragment result = new SliderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SliderFragment.KEY_POSITION, position);
        result.setArguments(bundle);
        return result;
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void onPageScroll(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
