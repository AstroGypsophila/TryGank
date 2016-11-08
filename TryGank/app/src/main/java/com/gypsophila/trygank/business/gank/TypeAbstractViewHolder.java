package com.gypsophila.trygank.business.gank;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gypsophila.trygank.business.gank.model.SearchBean;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/23
 */
public abstract class TypeAbstractViewHolder<T> extends RecyclerView.ViewHolder {


    public TypeAbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindHolder(T bean);

}
