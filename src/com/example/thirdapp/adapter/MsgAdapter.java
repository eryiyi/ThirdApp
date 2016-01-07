package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.bean.MsgBean;

import java.util.List;

public class MsgAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<MsgBean> list;
	
	public MsgAdapter(Context context, List<MsgBean> list){
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
			convertView = inflater.inflate(R.layout.itemmsg, null);
			//holder.image = (ImageView) convertView.findViewById(R.id.imagecom);
			holder.msgimage = (ImageView) convertView.findViewById(R.id.msgimage);
			holder.msgtext = (TextView) convertView.findViewById(R.id.msgtext);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			//BitmapUtil.getInstance().download("", "", holder.image);
			holder.msgimage.setImageResource(list.get(position).msgimage);
			holder.msgtext.setText(list.get(position).msgtext);
		}
		return convertView;
	}

	class Holder {
		//ImageView imagecom;
		ImageView msgimage;
		TextView msgtext;
	}
}
