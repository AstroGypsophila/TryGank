package com.gypsophila.trygank.news.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gypsophila.commonlib.activity.BaseActivity;
import com.gypsophila.trygank.R;
import com.gypsophila.trygank.news.NewsAdapter;
import com.gypsophila.trygank.news.model.NewsBean;
import com.gypsophila.trygank.news.presenter.INewsPresenter;
import com.gypsophila.trygank.news.presenter.NewsPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/24
 */
public class NewsListFragment extends Fragment implements INewsView, SwipeRefreshLayout.OnRefreshListener {


    private int mType;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private List<NewsBean> mData;

    private INewsPresenter mNewsPresenter;
    private int mPageIndex = 0;
    private NewsAdapter mAdapter;

    public static NewsListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt("type");
        mNewsPresenter = new NewsPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mAdapter = new NewsAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        onRefresh();
        return view;
    }

    @Override
    public void onRefresh() {
        mPageIndex = 0;
        if (mData != null) {
            mData.clear();
        }
        mNewsPresenter.loadNews((BaseActivity) getActivity(), mType, mPageIndex, null, true);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void addNews(List<NewsBean> newsBeanList) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(newsBeanList);
        mAdapter.setData(mData);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {

    }
}
