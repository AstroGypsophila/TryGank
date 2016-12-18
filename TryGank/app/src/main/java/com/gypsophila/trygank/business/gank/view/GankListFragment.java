package com.gypsophila.trygank.business.gank.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.business.AppConstants;
import com.gypsophila.trygank.business.gank.FilterType;
import com.gypsophila.trygank.business.gank.GankAdapter;
import com.gypsophila.trygank.business.gank.presenter.GankPresenterImpl;
import com.gypsophila.trygank.business.gank.presenter.IGankPresenter;
import com.gypsophila.trygank.entity.GankBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/9/29
 */
public class GankListFragment extends Fragment implements IGankView,
        SwipeRefreshLayout.OnRefreshListener {

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
    private int itemType;
    private boolean isLoading = false;
    //页面统计
    private boolean isCreated = false;

    private NestedScrollView mEmptyLayout;

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
        isCreated = true;
        mGankPresenter = new GankPresenterImpl(this);
        if (TYPE_FAVORITE.equals(mType)) {
            setHasOptionsMenu(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_list, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mEmptyLayout = (NestedScrollView) view.findViewById(R.id.empty_layout);
        if (mType.equals(mContext.getString(R.string.gank_welfare))) {
            itemType = GankAdapter.TYPE_PHOTO;
            mLayoutManager = new GridLayoutManager(mContext, 2);
            ((GridLayoutManager) mLayoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = mRecyclerView.getAdapter().getItemViewType(position);
                    if (type == GankAdapter.TYPE_FOOTER) {
                        //处理footerview只占一项位置
                        return ((GridLayoutManager) mLayoutManager).getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        } else {
            itemType = GankAdapter.TYPE_ITEM;
            mLayoutManager = new LinearLayoutManager(getActivity());
        }
        mAdapter = new GankAdapter(mContext, itemType);
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
        if (TYPE_FAVORITE.equals(mType)) {
            mAdapter.isShowFooter(false);
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }
        if (mPageIndex == 1 && mData != null) {
            mData.clear();
            if (gankBeanList != null && gankBeanList.size() <= 0) {
                showNoData(true);
                return;
            } else {
                showNoData(false);
            }
        }
        mData.addAll(gankBeanList);
        mAdapter.setData(mData);
        if (gankBeanList == null || gankBeanList.size() <= 0) {
            mAdapter.isShowFooter(false);
        }
        isLoading = false;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgress() {
        isLoading = false;
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        showMessage(msg);
    }

    private boolean isFirstInflate = true;
    private PopupMenu mPopup;

    @Override
    public void showFilteringPopUpMenu() {
        if (isFirstInflate || mPopup == null) {
            mPopup = new PopupMenu(getContext(), getActivity().findViewById(R.id.option_filter));
            mPopup.getMenuInflater().inflate(R.menu.filter_menu, mPopup.getMenu());
            isFirstInflate = false;
            mPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    for (int i = 0; i < mPopup.getMenu().size(); i++) {
                        MenuItem menuItem = mPopup.getMenu().getItem(i);
                        menuItem.setChecked(false);
                    }
                    item.setChecked(true);
                    switch (item.getItemId()) {
                        case R.id.all:
                            mGankPresenter.setFilter(FilterType.ALL);
                            mGankPresenter.loadGankFromDataBase(mContext);
                            break;
                        case R.id.android:
                            mGankPresenter.setFilter(FilterType.ANDROID);
                            mGankPresenter.loadGankFromDataBase(mContext);
                            break;
                        case R.id.ios:
                            mGankPresenter.setFilter(FilterType.IOS);
                            mGankPresenter.loadGankFromDataBase(mContext);
                            break;
                        case R.id.front_end:
                            mGankPresenter.setFilter(FilterType.FRONT_END);
                            mGankPresenter.loadGankFromDataBase(mContext);
                            break;
                        case R.id.app:
                            mGankPresenter.setFilter(FilterType.APP);
                            mGankPresenter.loadGankFromDataBase(mContext);
                            break;
                        case R.id.extra:
                            mGankPresenter.setFilter(FilterType.EXTRA);
                            mGankPresenter.loadGankFromDataBase(mContext);
                            break;
                        case R.id.recommend:
                            mGankPresenter.setFilter(FilterType.RECOMMEND);
                            mGankPresenter.loadGankFromDataBase(mContext);
                            break;

                    }
                    return true;
                }
            });
        }

        mPopup.show();
    }

    @Override
    public void showNoData(boolean shown) {
        if (shown) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
        } else {
            mEmptyLayout.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        AppConstants.PAGE_SIZE = 10;
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
                        && lastVisibleItem + 1 == mAdapter.getItemCount()
                        && !isLoading) {
                    isLoading = true;
                    mAdapter.isShowFooter(true);
                    mPageIndex++;
                    AppConstants.PAGE_SIZE = 20;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (TYPE_FAVORITE.equals(mType)) {
            inflater.inflate(R.menu.fragment_option_menu, menu);
            MenuItem item = menu.findItem(R.id.action_notification);
            item.setVisible(false);
        }

    }

    //http://www.cnblogs.com/mengdd/p/5590634.html
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_filter:
                showFilteringPopUpMenu();
                return true;
        }
        return false;
    }

    public boolean getLoadingStatus() {
        return isLoading;
    }

    public void setLoadingStatus(boolean isLoading) {
        this.isLoading = isLoading;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        if (!isCreated) {
//            //第一次调用setUserVisibleHint在create前，会导致错误统计
//            return;
//        }
//        if (isVisibleToUser) {
//            Logger.t("cj_data").w("invoke mtype " + mType);
//            UmengEvent.onFragmentStart(mType);
//        }
//    }
}
