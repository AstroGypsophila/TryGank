package com.gypsophila.trygank.business.gank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gypsophila.commonlib.utils.ImageLoaderUtils;
import com.gypsophila.trygank.R;
import com.gypsophila.trygank.entity.GankBean;
import com.gypsophila.trygank.business.gank.view.GankDetailActivity;
import com.gypsophila.trygank.business.gank.view.GankPictureActivity;
import com.gypsophila.trygank.utils.DateUtil;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/10/4
 */
public class GankAdapter extends AbstractRecyclerAdapter {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_PHOTO = 2;
    public static final int TYPE_EXTRA_IMAGE = 3;

    private boolean mIsShowFooter = true;
    private int mType;

    public GankAdapter(Context ctx, int type) {
        super(ctx);
        mContext = ctx;
        mInflater = LayoutInflater.from(mContext);
        mType = type;
    }

    public void setData(List<GankBean> gankBeanList) {
        mBeanList = gankBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.recycler_item_gank, parent, false);
            GankViewHolder holder = new GankViewHolder(view);
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View footer = mInflater.inflate(R.layout.footer_layout, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footer.setLayoutParams(params);
            return new GankAdapter.FooterViewHolder(footer);
        } else if (viewType == TYPE_PHOTO) {
            View view = mInflater.inflate(R.layout.recycler_item_photo_card, parent, false);
            PhotoViewHolder holder = new PhotoViewHolder(view);
            return holder;
        } else if (viewType == TYPE_EXTRA_IMAGE) {
            View view = mInflater.inflate(R.layout.recycler_image_item_gank, null);
            ImageGankViewHolder holder = new ImageGankViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GankViewHolder) {
            GankViewHolder gankViewHolder = (GankViewHolder) holder;
            GankBean gankBean = (GankBean) mBeanList.get(position);
            gankViewHolder.gankTitle.setText(gankBean.desc);
            gankViewHolder.gankVia.setText(mContext.getString(R.string.gank_via, gankBean.who));
            gankViewHolder.gankTime.setText(DateUtil.DateToString(gankBean.publishTime));
        } else if (holder instanceof PhotoViewHolder) {
            PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
            GankBean gankBean = (GankBean) mBeanList.get(position);
            photoViewHolder.mDescTv.setText(gankBean.desc);
            ImageLoaderUtils.loadImage(mContext, photoViewHolder.mWelfareImage, gankBean.url);
        } else if (holder instanceof ImageGankViewHolder) {
            final int currentPos = position;
            ImageGankViewHolder imageGankViewHolder = (ImageGankViewHolder) holder;
            GankBean gankBean = (GankBean) mBeanList.get(position);
            imageGankViewHolder.gankTitle.setText(gankBean.desc);
            imageGankViewHolder.gankVia.setText(mContext.getString(R.string.gank_via, gankBean.who));
            imageGankViewHolder.gankTime.setText(DateUtil.DateToString(gankBean.publishTime));
            ImageLoaderUtils.loadImage(mContext, imageGankViewHolder.imageView, gankBean.images[0]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GankBean gankBean = (GankBean) mBeanList.get(currentPos);
                    GankDetailActivity.openWebView(mContext, gankBean);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (!mIsShowFooter) {
            return mType == TYPE_ITEM ? TYPE_ITEM : TYPE_PHOTO;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            GankBean gankBean = (GankBean) mBeanList.get(position);
            if (gankBean.images != null && gankBean.images.length >= 1) {
                return TYPE_EXTRA_IMAGE;
            } else {
                return mType == TYPE_ITEM ? TYPE_ITEM : TYPE_PHOTO;
            }
        }
    }

    public void isShowFooter(boolean isShowFooter) {
        mIsShowFooter = isShowFooter;
    }

    @Override
    public int getItemCount() {
        if (mBeanList == null) return 0;
        return mBeanList.size() + (mIsShowFooter ? 1 : 0);
    }

    public class GankViewHolder extends RecyclerView.ViewHolder {
        public final TextView gankTitle;
        public final TextView gankVia;
        public final TextView gankTime;

        public GankViewHolder(View itemView) {
            super(itemView);
            gankTitle = (TextView) itemView.findViewById(R.id.title);
            gankVia = (TextView) itemView.findViewById(R.id.via);
            gankTime = (TextView) itemView.findViewById(R.id.publish_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBeanList != null && mBeanList.size() >= 1) {
                        int position = getAdapterPosition();
                        GankBean gankBean = (GankBean) mBeanList.get(position);
                        GankDetailActivity.openWebView(mContext, gankBean);
                    }

                }
            });
        }
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView mWelfareImage;
        public TextView mDescTv;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            mWelfareImage = (ImageView) itemView.findViewById(R.id.welfare_image);
            mDescTv = (TextView) itemView.findViewById(R.id.welfare_desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBeanList != null && mBeanList.size() >= 1) {
                        int position = getAdapterPosition();
                        GankBean gankBean = (GankBean) mBeanList.get(position);
                        GankPictureActivity.openPictureActivity(mContext, gankBean.url, gankBean.desc);
                    }
                }
            });
        }
    }

    public class ImageGankViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public final TextView gankTitle;
        public final TextView gankVia;
        public final TextView gankTime;

        public ImageGankViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.extra_img);
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
