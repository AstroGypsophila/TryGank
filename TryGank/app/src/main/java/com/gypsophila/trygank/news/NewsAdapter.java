package com.gypsophila.trygank.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gypsophila.commonlib.utils.ImageLoaderUtils;
import com.gypsophila.trygank.R;
import com.gypsophila.trygank.news.model.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/27
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private boolean mIsHideFooterView = false;
    private LayoutInflater mInflater;
    private Context mContext;
    private List<NewsBean> mData;
    private OnRecyclerViewItemClickListener mOnItemClicklistener;

    public NewsAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mData = new ArrayList<>();
    }

    public void setData(List<NewsBean> data) {
        mData = data;
    }

    public void isHideFooterView(boolean isHideFooterView) {
        mIsHideFooterView = isHideFooterView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View item = mInflater.inflate(R.layout.recycler_item, parent, false);
            final NewsListItemViewHolder holder = new NewsListItemViewHolder(item);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onItemClick(v, holder.getLayoutPosition());
                }
            });
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View footer = mInflater.inflate(R.layout.footer_layout, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footer.setLayoutParams(params);
            return new FooterViewHolder(footer);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsListItemViewHolder) {
            NewsListItemViewHolder itemHolder = (NewsListItemViewHolder) holder;
            NewsBean bean = mData.get(position);
            ImageLoaderUtils.loadImage(mContext, itemHolder.mNewsImage, bean.getImgsrc());
            itemHolder.mNewsTitle.setText(bean.getTitle());
            itemHolder.mNewsDigest.setText(bean.getDigest());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsHideFooterView) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size() + (mIsHideFooterView ? 0 : 1);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void OnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClicklistener = listener;
    }

    class NewsListItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mNewsImage;
        TextView mNewsTitle;
        TextView mNewsDigest;

        public NewsListItemViewHolder(View itemView) {
            super(itemView);
            mNewsImage = (ImageView) itemView.findViewById(R.id.news_image);
            mNewsTitle = (TextView) itemView.findViewById(R.id.news_title);
            mNewsDigest = (TextView) itemView.findViewById(R.id.news_digest);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

}
