package com.gypsophila.trygank.business.gank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gypsophila.resmodule.R;

import java.util.List;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/11/17
 */
public class ColorsListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> mColorList;

    public ColorsListAdapter(Context ctx, List<Integer> list) {
        mContext = ctx;
        mInflater = LayoutInflater.from(mContext);
        mColorList = list;
    }
    @Override
    public int getCount() {
        return mColorList == null ? 0 : mColorList.size();
    }

    @Override
    public Integer getItem(int position) {
        return mColorList == null ? null : mColorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.theme_color_item, null);
            holder = new ViewHolder();
            holder.colorImage = (ImageView) convertView.findViewById(R.id.image_color);
            holder.doneImage = (ImageView) convertView.findViewById(R.id.image_done);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.colorImage.setImageResource(getItem(position));
        return convertView;
    }

    class ViewHolder {
        ImageView colorImage;
        ImageView doneImage;
    }
}
