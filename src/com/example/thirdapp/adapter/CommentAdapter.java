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
import com.example.thirdapp.module.CommentObj;
import com.example.thirdapp.util.RelativeDateFormat;

import java.util.List;

public class CommentAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<CommentObj> list;
	
	public CommentAdapter(Context context, List<CommentObj> list){
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
			holder.floor_t = (TextView) convertView.findViewById(R.id.floor_t);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		CommentObj cell = list.get(position);
		if (cell != null) {
//			holder.floor.setImageResource(list.get(position).floor);
			holder.username.setText(cell.getUser_name());
			holder.floor_t.setText(String.valueOf(position+1)+"#");
			holder.commentcontent.setText(cell.getContent());
			holder.commentdate.setText(RelativeDateFormat.format(Long.parseLong((cell.getRegister_date() == null ? "" : cell.getRegister_date()) + "000")));
		}
		return convertView;
	}

	class Holder {
		ImageView floor;
		TextView floor_t;
		TextView username;
		TextView commentcontent;
		TextView commentdate;
	}
}
