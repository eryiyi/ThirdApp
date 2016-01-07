package com.example.thirdapp.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.module.HotGoodsObj;

import java.util.List;


public class CommodityAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<HotGoodsObj> list;
	
	public CommodityAdapter(Context context, List<HotGoodsObj> list){
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
			convertView = inflater.inflate(R.layout.itemcommodity, null);
			holder.imagecom = (ImageView) convertView.findViewById(R.id.imagebig);
			holder.namecom = (TextView) convertView.findViewById(R.id.namecom);
			holder.pricecom = (TextView) convertView.findViewById(R.id.pricecom);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.letter = (TextView) convertView.findViewById(R.id.catalog);
			holder.priceoriginalcom = (TextView) convertView.findViewById(R.id.priceoriginalcom);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		final HotGoodsObj mcom = list.get(position);
//		LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.linear);
//		if (position == 0) {
//			layout.setVisibility(View.VISIBLE);
//			holder.image.setVisibility(View.VISIBLE);
//			holder.image.setImageResource(list.get(position).image);
//			holder.letter.setVisibility(View.GONE);
//			holder.letter.setText(mcom.letter);
//		}else{
//			String lastCatalog = list.get(position - 1).letter;
//			int lastimage = list.get(position -1).image;
//			if (mcom.letter.equals(lastCatalog) && mcom.image == lastimage) {
//				layout.setVisibility(View.GONE);
//				holder.letter.setVisibility(View.GONE);
//				holder.image.setVisibility(View.GONE);
//			} else {
//				layout.setVisibility(View.VISIBLE);
//				holder.letter.setVisibility(View.GONE);
//				holder.letter.setText(mcom.letter);
//				holder.image.setVisibility(View.VISIBLE);
//				holder.image.setImageResource(list.get(position).image);
//			}
//		}
		
//			BitmapUtil.getInstance().download("http://xiaoqu.wphl.net/", list.get(position).imagebig, holder.imagecom);
//			holder.namecom.setText(list.get(position).name);
//			holder.pricecom.setText(list.get(position).price);
//			holder.priceoriginalcom.setText(list.get(position).originalprice);
			
			holder.priceoriginalcom.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
		return convertView;
	}

	class Holder {
		ImageView imagecom;
		TextView namecom;
		TextView pricecom;
		TextView priceoriginalcom;
		ImageView image;
		TextView letter;
	}
}
