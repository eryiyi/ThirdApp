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

public class ChoiceVillageAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<VillageBean> list;
	int selectedId = 0;
	
	public ChoiceVillageAdapter(Context context, List<VillageBean> list){
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
	
	public void setSelection(int selectedId) {
		this.selectedId = selectedId;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.itemvillage, null);
			holder.area = (TextView) convertView.findViewById(R.id.areabig);
			holder.address = (TextView) convertView.findViewById(R.id.addressdetail);
			holder.community_name = (TextView) convertView.findViewById(R.id.village);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			holder.area.setText(list.get(position).area);
			holder.address.setText(list.get(position).address);
			holder.community_name.setText(list.get(position).community_name);
		}
		
		if(position == selectedId) {		
			convertView.setBackgroundResource(R.color.common_grey_background);
		} else {
			convertView.setBackgroundResource(R.color.common_white);
		}
		
		return convertView;
	}
	
	class Holder {
		TextView area;
		TextView address;
		TextView community_name;
	}
}
