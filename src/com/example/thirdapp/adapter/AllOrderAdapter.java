package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.module.Orders;

import java.util.List;


public class AllOrderAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<Orders> list;
	ImageView checkx;
	TextView changetext;
	public AllOrderAdapter(Context context, List<Orders> list){
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
			convertView = inflater.inflate(R.layout.itemtobex3, null);
			//holder.tobeimage = (ImageView) convertView.findViewById(R.id.tobeimage);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.dstname = (TextView) convertView.findViewById(R.id.dstname);
			holder.dstcolor = (TextView) convertView.findViewById(R.id.dstcolor);
			holder.price = (TextView) convertView.findViewById(R.id.price);
			holder.number = (TextView) convertView.findViewById(R.id.number);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			//BitmapUtil.getInstance().download("", "", holder.tobeimage);
			holder.name.setText(list.get(position).getProduct_name());
			holder.dstname.setText(list.get(position).getShop_name());
			holder.dstcolor.setText(list.get(position).getUser_name());
			holder.price.setText(list.get(position).getPrice());
			holder.number.setText(list.get(position).getTotal());
		}
		convertView.findViewById(R.id.refund).setVisibility(View.VISIBLE);
		convertView.findViewById(R.id.edit).setVisibility(View.GONE);
		changetext = (TextView) convertView.findViewById(R.id.changetext);
		changetext.setText("交易成功");
		changetext.setTextColor(context.getResources().getColor(R.color.red));
		checkx = (ImageView) convertView.findViewById(R.id.checkx);
		checkx.setImageResource(R.drawable.deletorder);
		return convertView;
	}

	class Holder {
		//ImageView tobeimage;
		TextView name;
		TextView dstname;
		TextView dstcolor;
		TextView price;
		TextView number;
	}
}
