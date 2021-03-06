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
import com.example.thirdapp.bean.CategoryBean;
import com.example.thirdapp.module.TypeObj;
import com.example.thirdapp.networkbitmap.BitmapUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class CategoryAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<TypeObj> list;

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

	public CategoryAdapter(Context context, List<TypeObj> list){
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
			convertView = inflater.inflate(R.layout.itemcategory, null);
			holder.categoryimg = (ImageView) convertView.findViewById(R.id.categoryimg);
			holder.categoryname = (TextView) convertView.findViewById(R.id.categoryname);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
//			BitmapUtil.getInstance().download("http://xiaoqu.wphl.net/", list.get(position).getImage(), holder.categoryimg);
			TypeObj cell= list.get(position);
			if(cell != null){
				imageLoader.displayImage(InternetURL.INTERNAL_PIC + cell.getImage(), holder.categoryimg, ThirdApplication.txOptions, animateFirstListener);
				holder.categoryname.setText(cell.getType_name());
			}
		}
		return convertView;
	}

	class Holder {
		ImageView categoryimg;
		TextView categoryname;
	}
}
