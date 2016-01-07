package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.bean.WalletBean;

import java.util.List;

public class WalletAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<WalletBean> list;
	
	public WalletAdapter(Context context, List<WalletBean> list){
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
			convertView = inflater.inflate(R.layout.itemwallet, null);
			//holder.image = (ImageView) convertView.findViewById(R.id.imagecom);
			holder.bankimage = (ImageView) convertView.findViewById(R.id.bankimage);
			holder.bankclass = (TextView) convertView.findViewById(R.id.bankclass);
			holder.banknumber = (TextView) convertView.findViewById(R.id.banknumber);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			//BitmapUtil.getInstance().download("", "", holder.image);
			holder.bankimage.setImageResource(list.get(position).bankimage);
			holder.bankclass.setText(list.get(position).bankclass);
			holder.banknumber.setText(list.get(position).banknumber);
		}
		return convertView;
	}

	class Holder {
		//ImageView imagecom;
		ImageView bankimage;
		TextView bankclass;
		TextView banknumber;
	}
}
