package com.gypsophila.trygank.business.gank;

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
import com.gypsophila.trygank.business.gank.model.GankBean;
import com.gypsophila.trygank.business.gank.view.GankDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/4
 */
public class GankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_PHOTO = 2;

    private boolean mIsShowFooter = false;
    private LayoutInflater mInflater;
    private List<GankBean> mGankBeans;
    private Context mContext;
    private int mType;

    public GankAdapter(Context ctx, int type) {
        mContext = ctx;
        mInflater = LayoutInflater.from(mContext);
        mGankBeans = new ArrayList<>();
        mType = type;
    }

    public void setData(List<GankBean> gankBeanList) {
        mGankBeans = gankBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.recycler_item_gank, parent, false);
            GankViewHolder holder = new GankViewHolder(view);
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View footer = mInflater.inflate(R.layout.footer_layout, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footer.setLayoutParams(params);
            return new GankAdapter.FooterViewHolder(footer);
        } else if (viewType == TYPE_PHOTO) {
            View view = mInflater.inflate(R.layout.recycler_item_photo_card, parent, false);
            PhotoViewHolder holder = new PhotoViewHolder(view);
            return holder;
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
            gankViewHolder.gankTime.setText(gankBean.publishTime.substring(0, 10));
        } else if (holder instanceof PhotoViewHolder) {
            PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
            GankBean gankBean = mGankBeans.get(position);
            photoViewHolder.mDescTv.setText(gankBean.desc);
            ImageLoaderUtils.loadImage(mContext, photoViewHolder.mWelfareImage, gankBean.url);
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
            return mType == TYPE_ITEM ? TYPE_ITEM : TYPE_PHOTO;
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
                    int position = getAdapterPosition();
                    GankBean gankBean = mGankBeans.get(position);
                    GankDetailActivity.openWebView(mContext, gankBean);
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
                    int position = getAdapterPosition();
                    Toast.makeText(mContext, "u click " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

}
