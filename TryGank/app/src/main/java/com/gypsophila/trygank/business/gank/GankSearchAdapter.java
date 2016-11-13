package com.gypsophila.trygank.business.gank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.business.gank.model.GankBean;
import com.gypsophila.trygank.business.gank.model.SearchBean;
import com.gypsophila.trygank.business.gank.view.GankDetailActivity;
import com.gypsophila.trygank.utils.DateUtil;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/23
 */
public class GankSearchAdapter extends AbstractRecyclerAdapter<SearchBean> {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;

    public GankSearchAdapter(Context ctx) {
        super(ctx);
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
        if (viewType == TYPE_FOOTER) {
            View view = mInflater.inflate(R.layout.footer_layout, null);
            return new FooterViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.recycler_item_gank, parent, false);
            return new SearchViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TypeAbstractViewHolder) holder).bindHolder(mBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mBeanList == null) return 0;
        return mBeanList.size() + (isShowFooter ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return isShowFooter ? TYPE_FOOTER : TYPE_ITEM;
    }



    class SearchViewHolder extends TypeAbstractViewHolder<SearchBean> {
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
                    GankBean bean = new GankBean();
                    SearchBean searchBean = mBeanList.get(position);
                    bean.id = searchBean.id;
                    bean.url = searchBean.url;
                    bean.publishTime = searchBean.publishTime;
                    bean.desc = searchBean.desc;
                    bean.who = searchBean.who;
                    bean.type = searchBean.type;
                    GankDetailActivity.openWebView(mContext, bean);
                }
            });
        }

        @Override
        public void bindHolder(SearchBean searchBean) {
            gankTitle.setText(searchBean.desc);
            gankTime.setText(DateUtil.DateToString(searchBean.publishTime));
            gankVia.setText(searchBean.who);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

}
