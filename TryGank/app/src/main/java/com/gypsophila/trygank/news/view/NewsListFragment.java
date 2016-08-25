package com.gypsophila.trygank.news.view;

import android.os.Bundle;
import android.os.Handler;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/24
 */
public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private int mType;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private List<String> mData = new ArrayList<>();

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        initData();
        MyAdapter myAdapter = new MyAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(myAdapter);
        return view;
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mData.add(i + "");
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholder> {

        @Override
        public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewholder viewholder = new MyViewholder(LayoutInflater.from(getActivity()).inflate(R.layout.recycler_item, parent, false));
            return viewholder;
        }

        @Override
        public void onBindViewHolder(MyViewholder holder, int position) {
            holder.tv.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewholder extends RecyclerView.ViewHolder {

            public MyViewholder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv);
            }

            TextView tv;
        }
    }



}
