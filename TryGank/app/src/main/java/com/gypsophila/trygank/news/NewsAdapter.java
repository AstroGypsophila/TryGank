package com.gypsophila.trygank.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.mTextView.setText(mData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class NewsListItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public NewsListItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
