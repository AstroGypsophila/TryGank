package com.gypsophila.trygank.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gypsophila.commonlib.utils.ImageLoaderUtils;
import com.gypsophila.trygank.R;
import com.gypsophila.trygank.news.model.NewsBean;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/8/27
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsListItemViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<NewsBean> mData;

    public NewsAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mData = new ArrayList<>();
    }

    public void setData(List<NewsBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public NewsListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsListItemViewHolder(mInflater.inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsListItemViewHolder holder, int position) {
        NewsBean bean = mData.get(position);
        ImageLoaderUtils.loadImage(mContext, holder.mNewsImage, bean.getImgsrc());
        holder.mNewsTitle.setText(bean.getTitle());
        holder.mNewsDigest.setText(bean.getDigest());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
