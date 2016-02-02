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
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.bean.ValueAgainstBean;
import com.example.thirdapp.module.ProductScoreObj;
import com.example.thirdapp.module.TravelObj;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;


public class ValueAgainstAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<ProductScoreObj> list;
	
	public ValueAgainstAdapter(Context context, List<ProductScoreObj> list){
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

	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private OnClickContentItemListener onClickContentItemListener;
	public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
		this.onClickContentItemListener = onClickContentItemListener;
	}



	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.itemvalueagainst, null);
			holder.vaimage = (ImageView) convertView.findViewById(R.id.vaimage);
			holder.duihuan = (ImageView) convertView.findViewById(R.id.duihuan);
			holder.vaname = (TextView) convertView.findViewById(R.id.vaname);
			holder.valimited = (TextView) convertView.findViewById(R.id.valimited);
			holder.vaintegral = (TextView) convertView.findViewById(R.id.vaintegral);
			holder.vapriceoriginal = (TextView) convertView.findViewById(R.id.vapriceoriginal);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			ProductScoreObj cell = list.get(position);
			imageLoader.displayImage(cell.getProduct_pic(), holder.vaimage, ThirdApplication.options, animateFirstListener);
			holder.vaname.setText(list.get(position).getProduct_name());
			holder.vaintegral.setText(list.get(position).getScore());
			holder.vapriceoriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
			holder.duihuan.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickContentItemListener.onClickContentItem(position, 1, null);
				}
			});

		}
		return convertView;
	}

	class Holder {
		ImageView vaimage;
		ImageView duihuan;
		TextView vaname;
		TextView valimited;
		TextView vaintegral;
		TextView vapriceoriginal;
	}
}
