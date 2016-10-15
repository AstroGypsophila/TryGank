package com.gypsophila.trygank.business.gank.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gypsophila.trygank.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/28
 */
public class GankFragment extends Fragment {

    public static String TAG = "GankFragment";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static GankFragment newInstance() {
        GankFragment fragment = new GankFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_pager, null);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        setUpViewPager(mViewPager);
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.gank_today));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.gank_android));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.gank_ios));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.gank_welfare));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.gank_front_end));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.gank_video));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.gank_app));
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {
        MyFragmentAdapter mAdapter = new MyFragmentAdapter(getChildFragmentManager());
//        mAdapter.addFragment(GankListFragment.newInstance(), getString(R.string.gank_today));
        mAdapter.addFragment(GankListFragment.newInstance(getString(R.string.gank_android)), getString(R.string.gank_android));
        mAdapter.addFragment(GankListFragment.newInstance(getString(R.string.gank_ios)), getString(R.string.gank_ios));
        mAdapter.addFragment(GankListFragment.newInstance(getString(R.string.gank_welfare)), getString(R.string.gank_welfare));
        mAdapter.addFragment(GankListFragment.newInstance(getString(R.string.gank_front_end)), getString(R.string.gank_front_end));
//        mAdapter.addFragment(GankListFragment.newInstance(getString(R.string.gank_video)), getString(R.string.gank_video));
//        mAdapter.addFragment(GankListFragment.newInstance(), getString(R.string.gank_app));
        viewPager.setOffscreenPageLimit(mAdapter.getCount() - 1);
        viewPager.setAdapter(mAdapter);
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new ArrayList<>();
        private List<String> mTitles = new ArrayList<>();

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

}
