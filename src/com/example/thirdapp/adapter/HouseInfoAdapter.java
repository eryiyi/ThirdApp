package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.module.HouseInfoObj;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class HouseInfoAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<HouseInfoObj> list;

	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public HouseInfoAdapter(Context context, List<HouseInfoObj> list){
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
			convertView = inflater.inflate(R.layout.itemhouse, null);
			holder.imagehouse = (ImageView) convertView.findViewById(R.id.imagehouse);
			holder.housename = (TextView) convertView.findViewById(R.id.namehouse);
			holder.housedescription = (TextView) convertView.findViewById(R.id.descriptionhouse);
			holder.houseprice = (TextView) convertView.findViewById(R.id.pricehouse);
			holder.accuracy = (TextView) convertView.findViewById(R.id.accuracy);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			HouseInfoObj cell = list.get(position);
			//BitmapUtil.getInstance().download("", "", holder.imagehouse);
			if(cell != null){
				imageLoader.displayImage(InternetURL.INTERNAL_PIC + list.get(position).getPic1(), holder.imagehouse, ThirdApplication.options, animateFirstListener);
				holder.housename.setText(cell.getTitle()==null?"":cell.getTitle());
				holder.housedescription.setText(cell.getGeneral()==null?"":cell.getGeneral());
				holder.houseprice.setText("￥"+(cell.getPrice()==null?"":cell.getPrice()));
				holder.accuracy.setText(cell.getDecoration()==null?"":cell.getDecoration());
			}

		}
		return convertView;
	}

	class Holder {
		ImageView imagehouse;
		TextView housename;
		TextView housedescription;
		TextView houseprice;
		TextView accuracy;
	}
}
