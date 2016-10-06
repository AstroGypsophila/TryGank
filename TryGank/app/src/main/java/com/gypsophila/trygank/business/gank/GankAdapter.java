package com.gypsophila.trygank.business.gank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.business.gank.model.GankBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/4
 */
public class GankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private boolean mIsShowFooter = true;
    private LayoutInflater mInflater;
    private List<GankBean> mGankBeans;
    private Context mContext;

    public GankAdapter(Context ctx) {
        mContext = ctx;
        mInflater = LayoutInflater.from(mContext);
        mGankBeans = new ArrayList<>();
    }

    public void setData(List<GankBean> gankBeanList) {
        mGankBeans = gankBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_gank, parent, false);
            GankViewHolder holder = new GankViewHolder(view);
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View footer = mInflater.inflate(R.layout.footer_layout, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footer.setLayoutParams(params);
            return new GankAdapter.FooterViewHolder(footer);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GankViewHolder) {
            GankViewHolder gankViewHolder = (GankViewHolder) holder;
            GankBean gankBean = mGankBeans.get(position);
            gankViewHolder.gankTitle.setText(gankBean.desc);
            gankViewHolder.gankVia.setText(mContext.getString(R.string.gank_via, gankBean.who));
            gankViewHolder.gankTime.setText(gankBean.publishTime.substring(0,10));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (!mIsShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void isShowFooter(boolean isShowFooter) {
        mIsShowFooter = isShowFooter;
    }

    @Override
    public int getItemCount() {
        if (mGankBeans == null) return 0;
        return mGankBeans.size() + (mIsShowFooter ? 1 : 0);
    }

    public GankBean getItem(int position) {
        return mGankBeans == null ? null : mGankBeans.get(position);
    }

    public static class GankViewHolder extends RecyclerView.ViewHolder {
        public final TextView gankTitle;
        public final TextView gankVia;
        public final TextView gankTime;

        public GankViewHolder(View itemView) {
            super(itemView);
            gankTitle = (TextView) itemView.findViewById(R.id.title);
            gankVia = (TextView) itemView.findViewById(R.id.via);
            gankTime = (TextView) itemView.findViewById(R.id.publish_time);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

}
