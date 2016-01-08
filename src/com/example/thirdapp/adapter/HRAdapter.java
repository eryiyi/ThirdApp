package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.bean.HRBean;
import com.example.thirdapp.module.TypeBigObj;

import java.util.List;


public class HRAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<TypeBigObj> list;
	int selectedId = 0;
	
	public HRAdapter(Context context, List<TypeBigObj> list){
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

	public void setSelection(int selectedId) {
		this.selectedId = selectedId;
		notifyDataSetChanged();
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
			convertView = inflater.inflate(R.layout.itemhr, null);
			holder.hrtext = (TextView) convertView.findViewById(R.id.hrtext);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			holder.hrtext.setText(list.get(position).getType_name());
		}
		
		if(position == selectedId) {		
			convertView.setBackgroundResource(R.color.common_grey_background);
		} else {
			convertView.setBackgroundResource(R.color.common_white);
		}
		
		return convertView;
	}

	class Holder {
		TextView hrtext;
	}
}
