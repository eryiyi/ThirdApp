package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.bean.PopupTextlBean;

import java.util.List;


public class PopupTextAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<PopupTextlBean> list;
	
	public PopupTextAdapter(Context context, List<PopupTextlBean> list){
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
			convertView = inflater.inflate(R.layout.itempopuplv, null);
			holder.popupname = (TextView) convertView.findViewById(R.id.popuptext);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			holder.popupname.setText(list.get(position).popupname);
		}
		return convertView;
	}

	class Holder {
		TextView popupname;
	}
}
