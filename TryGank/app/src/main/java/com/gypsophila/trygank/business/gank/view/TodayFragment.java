package com.gypsophila.trygank.business.gank.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.business.gank.DailyGankAdapter;
import com.gypsophila.trygank.business.gank.model.GankIOPlusBean;
import com.gypsophila.trygank.business.gank.presenter.GankDatePresenterImpl;
import com.gypsophila.trygank.business.gank.presenter.IGankDatePresenter;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/11/1
 */
public class TodayFragment extends Fragment implements ITodayView {

    public static String TAG = "TodayFragment";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private IGankDatePresenter mGankDatePresenter;
    private GankIOPlusBean mGankIOPlusBean;
    private DailyGankAdapter mAdapter;

    public static TodayFragment newIntance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mGankDatePresenter = new GankDatePresenterImpl(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorAccent);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DailyGankAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        mGankDatePresenter.loadGank(mContext, null);
        return view;
    }

    @Override
    public void showProgress() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setEnabled(false);
                }
            });
        }
    }

    @Override
    public void addData(GankIOPlusBean bean) {
        mGankIOPlusBean = bean;
        mAdapter.setData(bean.results);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgress() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);

                }
            });
        }

    }

    @Override
    public void showLoadFailMsg() {

    }
}
