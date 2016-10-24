package com.gypsophila.trygank.business.gank.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.business.gank.GankSearchAdapter;
import com.gypsophila.trygank.business.gank.model.SearchBean;
import com.gypsophila.trygank.business.gank.presenter.IGankPresenter;
import com.gypsophila.trygank.business.gank.presenter.SearchPresenterImpl;
import com.gypsophila.trygank.systemevent.FinishInputEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/23
 */
public class SearchListFragment extends Fragment implements ISearchView, SwipeRefreshLayout.OnRefreshListener{

    public static String TAG = "SearchListFragment";

    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mEmptyView;

    private List<SearchBean> mData;
    private LinearLayoutManager mLayoutManager;
    private GankSearchAdapter mAdapter;
    private int mPageIndex;
    private IGankPresenter mSearchPresenter;

    private String mKeyWord;


    public static SearchListFragment newInstance() {
        SearchListFragment searchListFragment = new SearchListFragment();
        return searchListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mSearchPresenter = new SearchPresenterImpl(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_list, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mEmptyView = (TextView) view.findViewById(R.id.empty_view);
        mLayoutManager = new GridLayoutManager(mContext, 1);
        mAdapter = new GankSearchAdapter(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        if (mData != null) {
            mData.clear();
        }
        mSearchPresenter.loadGank(mContext, mKeyWord, mPageIndex, null);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void addNews(List<SearchBean> searchBeanList) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(searchBeanList);
        if (mPageIndex == 1) {
            mAdapter.setData(mData);
        } else {
            mAdapter.addData(mData);
        }
        if (searchBeanList == null || searchBeanList.size() <= 0) {
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FinishInputEvent event) {
        mKeyWord = event.mKeyWord;
        onRefresh();
    }
}
