package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.bean.MyMsgBean;

import java.util.List;

public class MyMsgAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<MyMsgBean> list;
	
	public MyMsgAdapter(Context context, List<MyMsgBean> list){
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
			convertView = inflater.inflate(R.layout.itemmymsg, null);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.publictime = (TextView) convertView.findViewById(R.id.publictime);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			holder.content.setText(list.get(position).content);
			holder.publictime.setText("提交时间:" + list.get(position).publictime);
		}
		return convertView;
	}

	class Holder {
		TextView content;
		TextView publictime;
	}
}
