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
import android.widget.TextView;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.business.gank.GankAdapter;
import com.gypsophila.trygank.business.gank.model.GankBean;
import com.gypsophila.trygank.business.gank.presenter.GankPresenterImpl;
import com.gypsophila.trygank.business.gank.presenter.IGankPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/29
 */
public class GankListFragment extends Fragment implements IGankView, SwipeRefreshLayout.OnRefreshListener {

    public static final String TYPE_FAVORITE = "favorite";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GankAdapter mAdapter;
    private IGankPresenter mGankPresenter;
    private String mType;
    private Context mContext;
    private int mPageIndex;
    private List<GankBean> mData;
    private TextView mEmptyView;

    public static GankListFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        GankListFragment fragment = new GankListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mType = getArguments().getString("type");
        mGankPresenter = new GankPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_list, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mEmptyView = (TextView) view.findViewById(R.id.empty_view);
        mAdapter = new GankAdapter(mContext);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        onRefresh();
        return view;
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void addNews(List<GankBean> gankBeanList) {
        //收藏不需要footerview
        if (!TYPE_FAVORITE.equals(mType)) {
            mAdapter.isShowFooter(true);
        } else {
            if (gankBeanList != null && gankBeanList.size() <= 0) {
                mEmptyView.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
            }
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(gankBeanList);
        mAdapter.setData(mData);
        if (gankBeanList == null || gankBeanList.size() <= 0) {
            mAdapter.isShowFooter(false);
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
    public void onRefresh() {
        mPageIndex = 1;
        if (mData != null) {
            mData.clear();
        }
        if (TYPE_FAVORITE.equals(mType)) {
            mGankPresenter.loadGankFromDataBase(getActivity());
        } else {
            mGankPresenter.loadGank(getActivity(), mType, mPageIndex, null);
        }
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (TYPE_FAVORITE.equals(mType)) {

            } else {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    mPageIndex++;
                    mGankPresenter.loadGank(getActivity(), mType, mPageIndex, null);
                }

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };

}
