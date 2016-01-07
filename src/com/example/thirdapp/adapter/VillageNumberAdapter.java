package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.bean.VillageBean;

import java.util.List;

public class VillageNumberAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<VillageBean> list;
	int selectedId = 0;
	
	public VillageNumberAdapter(Context context, List<VillageBean> list){
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
			convertView = inflater.inflate(R.layout.itemnumber, null);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.community_name = (TextView) convertView.findViewById(R.id.communityname);
			holder.number = (TextView) convertView.findViewById(R.id.number);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			holder.address.setText(list.get(position).address);
			holder.community_name.setText(list.get(position).community_name);
			holder.number.setText("电话:" + list.get(position).number);
		}
		
		return convertView;
	}
	
	class Holder {
		TextView address;
		TextView community_name;
		TextView number;
	}
}
