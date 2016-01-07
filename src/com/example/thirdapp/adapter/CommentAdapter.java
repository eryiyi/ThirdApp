package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.bean.CommentBean;

import java.util.List;

public class CommentAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<CommentBean> list;
	
	public CommentAdapter(Context context, List<CommentBean> list){
		inflater = LayoutInflater.from(context);
		this.list = list;
		this.context = context;
	}
	
	
	@Override
	public int getCount() {
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		if (list != null && arg0 < list.size()) {
			return list.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.itemcomment, null);
			holder.floor = (ImageView) convertView.findViewById(R.id.floor);
			holder.username = (TextView) convertView.findViewById(R.id.username);
			holder.commentcontent = (TextView) convertView.findViewById(R.id.commentcontent);
			holder.commentdate = (TextView) convertView.findViewById(R.id.commentdate);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			holder.floor.setImageResource(list.get(position).floor);
			holder.username.setText(list.get(position).username);
			holder.commentcontent.setText(list.get(position).commentcontent);
			holder.commentdate.setText(list.get(position).commentdate);
		}
		return convertView;
	}

	class Holder {
		ImageView floor;
		TextView username;
		TextView commentcontent;
		TextView commentdate;
	}
}
