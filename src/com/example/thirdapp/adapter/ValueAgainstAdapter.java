package com.example.thirdapp.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.bean.ValueAgainstBean;

import java.util.List;


public class ValueAgainstAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<ValueAgainstBean> list;
	
	public ValueAgainstAdapter(Context context, List<ValueAgainstBean> list){
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
			convertView = inflater.inflate(R.layout.itemvalueagainst, null);
			//holder.image = (ImageView) convertView.findViewById(R.id.vaimage);
			holder.vaname = (TextView) convertView.findViewById(R.id.vaname);
			holder.valimited = (TextView) convertView.findViewById(R.id.valimited);
			holder.vaintegral = (TextView) convertView.findViewById(R.id.vaintegral);
			holder.vapriceoriginal = (TextView) convertView.findViewById(R.id.vapriceoriginal);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			//BitmapUtil.getInstance().download("", "", holder.vaimage);
			holder.vaname.setText(list.get(position).vaname);
			holder.valimited.setText(list.get(position).valimited);
			holder.vaintegral.setText(list.get(position).vaintegral);
			holder.vapriceoriginal.setText(list.get(position).vapriceoriginal);
			holder.vapriceoriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
		}
		return convertView;
	}

	class Holder {
		//ImageView vaimage;
		TextView vaname;
		TextView valimited;
		TextView vaintegral;
		TextView vapriceoriginal;
	}
}
