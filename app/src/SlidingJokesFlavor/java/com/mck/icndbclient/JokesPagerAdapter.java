package com.mck.icndbclient;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by mike on 4/24/2015.
 */
public class JokesPagerAdapter extends PagerAdapter {

    // Keys for the save/restore bundle and determining frag postion.
    private static final String KEY_FRAGMENT_POSITION =
            "JokesPagerAdapter.KEY_FRAGMENT_POSITION";
    private static final String KEY_JOKES_ARRAY =
            "JokesPagerAdapter.KEY_JOKES_ARRAY";
    private static final String KEY_PRIME_FRAGMENT_ID =
            "JokesPagerAdapter.KEY_PRIME_FRAGMENT_ID";
    private final FragmentManager mFragmentManager;

    ArrayList<Joke> mJokes;
    private Fragment mCurrentPrimaryItem;
    private FragmentTransaction mCurTransaction;

    public JokesPagerAdapter(FragmentManager fragmentManager){
        mJokes = new ArrayList<Joke>();
        mFragmentManager = fragmentManager;
    }
    public void addJoke(Joke joke){
        mJokes.add(joke);
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mJokes.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment.getView() == view){
            return true;
        }
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        Fragment fragment = new JokeFragment();
        Bundle args = new Bundle();
        args.putParcelable(JokeFragment.KEY_JOKE, mJokes.get(position));
        args.putInt(KEY_FRAGMENT_POSITION, position);
        fragment.setArguments(args);
        //  this fragment is not being set to primary.
        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        mCurTransaction.add(container.getId(), fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        mCurTransaction.remove((Fragment)object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if(mCurTransaction != null) {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        Bundle bundle = (Bundle) state;
        bundle.setClassLoader(loader);
        int fragId = bundle.getInt(KEY_PRIME_FRAGMENT_ID, -1);
        if (fragId != -1){
            mCurrentPrimaryItem = mFragmentManager.findFragmentById(fragId);
        }
        mJokes = bundle.getParcelableArrayList(KEY_JOKES_ARRAY);
    }

    @Override
    public Parcelable saveState() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_JOKES_ARRAY, mJokes);
        if (mCurrentPrimaryItem != null) {
            int primeFragId = mCurrentPrimaryItem.getId();
            bundle.putInt(KEY_PRIME_FRAGMENT_ID, primeFragId);
        }
        return bundle;
    }

    @Override
    public int getItemPosition(Object object) {
        Fragment fragment = (Fragment) object;
        return fragment.getArguments().getInt(KEY_FRAGMENT_POSITION);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != mCurrentPrimaryItem){
            if (mCurrentPrimaryItem != null){
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null){
                fragment.setUserVisibleHint(true);
                fragment.setMenuVisibility(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }
}
