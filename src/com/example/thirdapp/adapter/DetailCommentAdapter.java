package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.module.TravelCommentObj;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * author: liuzwei
 * Date: 2014/8/9
 * Time: 11:07
 * 类的功能、说明写在此处.
 */
public class DetailCommentAdapter extends BaseAdapter {
    private ViewHolder holder;
    private Context context;
    private List<TravelCommentObj> list;
    private LayoutInflater inflater;
    private OnClickContentItemListener onClickContentItemListener;
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
        this.onClickContentItemListener = onClickContentItemListener;
    }

    public DetailCommentAdapter(Context context, List<TravelCommentObj> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.detail_comment, null);

            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TravelCommentObj commentContent = list.get(position);
        if (commentContent != null) {
            holder.nickname.setText(commentContent.getUser_name()+":");
            holder.content.setText(commentContent.getContent());

        }

        return convertView;
    }

    class ViewHolder {
        TextView content;//
        TextView nickname;//
    }
}
