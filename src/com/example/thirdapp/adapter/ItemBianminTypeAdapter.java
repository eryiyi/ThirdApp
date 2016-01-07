package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.module.BianminTypeObj;

import java.util.List;

/**
 */
public class ItemBianminTypeAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<BianminTypeObj> findEmps;
    private Context mContext;

    public ItemBianminTypeAdapter(List<BianminTypeObj> findEmps, Context mContext) {
        this.findEmps = findEmps;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return findEmps.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.itembianmin, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BianminTypeObj cell = findEmps.get(position);
        holder.title.setText(cell.getCat_name());
        return convertView;
    }

    class ViewHolder {
        TextView title;
    }

}