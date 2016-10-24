package com.gypsophila.irecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhy
 * NOTE: viewholder原作用就是减少耗时的findViewById操作，此处通用的viewholder，具体子控件view保存起来
 * SparseArray是android里为<Interger,Object> 这样的Hashmap而专门写的类,目的是提高效率，其核心是折半查找函数（binarySearch）
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private View mConvertView;
    private SparseArray<View> mViews;

    private ViewHolder(Context ctx, View itemView) {
        super(itemView);
        mContext = ctx;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder createViewHolder(Context ctx, View itemView) {
        ViewHolder viewHolder = new ViewHolder(ctx, itemView);
        return viewHolder;
    }

    public static ViewHolder createViewHolder(Context ctx, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(ctx).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(ctx, itemView);
        return viewHolder;
    }

    public <T extends View> T getViews(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

}
