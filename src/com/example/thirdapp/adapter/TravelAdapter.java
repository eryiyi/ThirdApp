package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.module.TravelObj;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;


public class TravelAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<TravelObj> list;
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private OnClickContentItemListener onClickContentItemListener;

	public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
		this.onClickContentItemListener = onClickContentItemListener;
	}



	public TravelAdapter(Context context, List<TravelObj> list){
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.itemtravel, null);
			holder.image = (ImageView) convertView.findViewById(R.id.traimage);
			holder.name = (TextView) convertView.findViewById(R.id.traname);
			holder.good = (TextView) convertView.findViewById(R.id.good);
			holder.collection = (TextView) convertView.findViewById(R.id.collection);
			holder.comment = (TextView) convertView.findViewById(R.id.comment);
			holder.frame_one = (FrameLayout) convertView.findViewById(R.id.frame_one);
			holder.frame_two = (FrameLayout) convertView.findViewById(R.id.frame_two);
			holder.frame_three = (FrameLayout) convertView.findViewById(R.id.frame_three);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			TravelObj cell = list.get(position);
			imageLoader.displayImage(InternetURL.INTERNAL_PIC + cell.getImage(), holder.image, ThirdApplication.options, animateFirstListener);
			holder.name.setText(cell.getTitle()==null?"":cell.getTitle());
			holder.good.setText(cell.getSupport_num()==null?"0":cell.getSupport_num());
			holder.collection.setText(cell.getCollect_num()==null?"0":cell.getCollect_num());
			holder.comment.setText(cell.getComment_num()==null?"0":cell.getComment_num());

			holder.frame_one.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickContentItemListener.onClickContentItem(position, 1, null);
				}
			});
			holder.frame_two.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickContentItemListener.onClickContentItem(position, 2, null);
				}
			});
		}



		return convertView;
	}

	class Holder {
		ImageView image;
		TextView name;
		TextView good;
		TextView collection;
		TextView comment;
		FrameLayout frame_one;
		FrameLayout frame_two;
		FrameLayout frame_three;
	}
}
