package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.module.TalkObj;
import com.example.thirdapp.util.RelativeDateFormat;

import java.util.List;

/**
 */
public class ItemTalkAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<TalkObj> findEmps;
    private Context mContext;

    public ItemTalkAdapter(List<TalkObj> findEmps, Context mContext) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.itemtalk, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.dateline = (TextView) convertView.findViewById(R.id.dateline);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TalkObj cell = findEmps.get(position);
        if(cell != null){
            holder.title.setText(cell.getTitle()==null?"":cell.getTitle());
            holder.dateline.setText(RelativeDateFormat.format(Long.parseLong((cell.getRegister_date() == null ? "" : cell.getRegister_date()) + "000")));
        }

        return convertView;
    }

    class ViewHolder {
        TextView dateline;
        TextView title;
    }

}