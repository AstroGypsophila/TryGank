package com.gypsophila.trygank.business.gank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.business.gank.model.SearchBean;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/23
 */
public class GankSearchAdapter extends AbstractRecyclerAdapter {

    public GankSearchAdapter(Context ctx) {
        super(ctx);
        mContext = ctx;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<SearchBean> searchBeanList) {
        mBeanList.clear();
        mBeanList.addAll(searchBeanList);
    }

    public void addData(List<SearchBean> searchBeanList) {
        mBeanList.addAll(searchBeanList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_item_gank, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TypeAbstractViewHolder) holder).bindHolder((SearchBean) mBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    class SearchViewHolder extends TypeAbstractViewHolder {
        public final TextView gankTitle;
        public final TextView gankVia;
        public final TextView gankTime;

        public SearchViewHolder(View itemView) {
            super(itemView);
            gankTitle = (TextView) itemView.findViewById(R.id.title);
            gankVia = (TextView) itemView.findViewById(R.id.via);
            gankTime = (TextView) itemView.findViewById(R.id.publish_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Toast.makeText(mContext, "u click " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void bindHolder(SearchBean searchBean) {
            gankTitle.setText(searchBean.desc);
            gankTime.setText(searchBean.publishTime);
            gankVia.setText(searchBean.who);
        }
    }

}
