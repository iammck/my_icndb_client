package com.mck.icndbclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

/**
 * extends {@link android.support.v4.app.FragmentStatePagerAdapter}  as
 * abstract class adding wrapping to the apdapter. There is some glitching
 * after switching from page 0 to 1 and back. Perhaps a PageTransformer would help.
 */
public abstract class WraparoundFragmentStatePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

    private final ViewPager mViewPager;
   private int count = -1;


    public WraparoundFragmentStatePagerAdapter(FragmentManager fm, ViewPager viewPager) {
        super(fm);
        count = getSize() + 2;
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);
    }

    protected abstract Fragment getFragmentForPosition(int position);
    public abstract int getSize();
    public abstract void onPageScroll(int position, float positionOffset, int positionOffsetPixels);

    @Override
    public Fragment getItem(int position) {
        // we want to duplicate the first two fragments on return for the last two positions.
        if (position >= getSize()){
            position -= getSize();
        }
        return getFragmentForPosition(position);
    }

    @Override
    public final int getCount() {
        // We have the size plus two for the wrapping.
        return count;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // there is some ui jumping happening. perhaps a PageTransformer to overlay change.
        // http://developer.android.com/training/animation/screen-slide.html
        // if in the first position go to the second to last position, which will eval to this spot.
        if (position == 0){
            if (positionOffsetPixels == 0) {
                mViewPager.setCurrentItem(getSize(), false);
            }
            return;
        // if in the last position set the current position to the second item in index 1.
        } else if (position == getSize() + 1){
            if (positionOffsetPixels == 0) {
                mViewPager.setCurrentItem(1, false);
            }
            return;
        } else {
            onPageScroll(position, positionOffset, positionOffsetPixels);
        }
    }
}