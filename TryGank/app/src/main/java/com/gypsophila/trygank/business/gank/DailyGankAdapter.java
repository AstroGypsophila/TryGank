package com.gypsophila.trygank.business.gank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gypsophila.commonlib.utils.ImageLoaderUtils;
import com.gypsophila.trygank.R;
import com.gypsophila.trygank.business.gank.model.GankBean;
import com.gypsophila.trygank.business.gank.model.GankIOPlusBean;
import com.gypsophila.trygank.business.gank.view.GankDetailActivity;
import com.gypsophila.trygank.business.gank.view.GankPictureActivity;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/11/8
 */
public class DailyGankAdapter extends AbstractRecyclerAdapter<GankBean> {

    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_ITEM = 1;

    public DailyGankAdapter(Context ctx) {
        super(ctx);
    }

    public void setData(GankIOPlusBean.Result results) {
        if (results.welfareList != null) mBeanList.addAll(results.welfareList);
        if (results.androidList != null) mBeanList.addAll(results.androidList);
        if (results.iOSList != null) mBeanList.addAll(results.iOSList);
        if (results.extraList != null) mBeanList.addAll(results.extraList);
        if (results.recommendList != null) mBeanList.addAll(results.recommendList);
        if (results.videoList != null) mBeanList.addAll(results.videoList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TypeAbstractViewHolder) holder).bindHolder(mBeanList.get(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMAGE) {
            View view = mInflater.inflate(R.layout.image_daily_gank, parent, false);
            return new ImageViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.item_daily_gank, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        GankBean bean = mBeanList.get(position);
        if (bean.type.equals(mContext.getString(R.string.gank_welfare))) {
            return TYPE_IMAGE;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    private void showCategory(View category) {
        if (!isVisibleOf(category)) category.setVisibility(View.VISIBLE);
    }

    private void hideCategory(View category) {
        if (isVisibleOf(category)) category.setVisibility(View.GONE);
    }

    private boolean isVisibleOf(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    class ItemViewHolder extends TypeAbstractViewHolder<GankBean> {

        public TextView mTitleText;
        public TextView mCategoryText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCategoryText = (TextView) itemView.findViewById(R.id.tv_category);
            mTitleText = (TextView) itemView.findViewById(R.id.tv_title);
            mTitleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    GankBean bean = mBeanList.get(position);
                    GankDetailActivity.openWebView(mContext, bean);
                }
            });
        }

        @Override
        public void bindHolder(GankBean bean) {
            int position = getAdapterPosition();
            if (position == 0) {
                showCategory(mCategoryText);
            } else {
                boolean isSectionFirst = mBeanList.get(position).type.equals(mBeanList.get(position - 1).type) ? false : true;
                if (isSectionFirst) {
                    showCategory(mCategoryText);
                } else {
                    hideCategory(mCategoryText);
                }
            }
            mCategoryText.setText(bean.type);
            mTitleText.setText(bean.desc);
        }

    }

    class ImageViewHolder extends TypeAbstractViewHolder<GankBean> {

        public ImageView welfareImageView;
        public TextView mCategoryText;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mCategoryText = (TextView) itemView.findViewById(R.id.tv_category);
            welfareImageView = (ImageView) itemView.findViewById(R.id.imageView_welfare);
            welfareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    GankBean bean = mBeanList.get(position);
                    GankPictureActivity.openPictureActivity(mContext, bean.url, bean.desc);
                }
            });
        }

        @Override
        public void bindHolder(GankBean bean) {
            int position = getAdapterPosition();
            if (position == 0) {
                showCategory(mCategoryText);
            } else {
                hideCategory(mCategoryText);
            }
            mCategoryText.setText(bean.type);
            ImageLoaderUtils.loadImage(mContext, welfareImageView, bean.url);
        }
    }

}
