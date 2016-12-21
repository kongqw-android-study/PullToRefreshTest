package com.example.kongqingwei.pulltorefreshtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kongqingwei.pulltorefreshtest.R;

/**
 * Created by kongqingwei on 2016/12/21.
 */

public class ListViewAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;

    public ListViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_listiview, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText("Item : " + position);
        return convertView;
    }

    private final class ViewHolder {
        TextView textView;
    }
}
