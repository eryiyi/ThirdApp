package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.bean.ComListBean;

import java.util.List;

public class ComListAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<ComListBean> list;
	
	public ComListAdapter(Context context, List<ComListBean> list){
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
			convertView = inflater.inflate(R.layout.itemcomlist, null);
			//holder.imagehouse = (ImageView) convertView.findViewById(R.id.imagelist);
			holder.namelist = (TextView) convertView.findViewById(R.id.namelist);
			holder.salevolume = (TextView) convertView.findViewById(R.id.salevolume);
			holder.pricelist = (TextView) convertView.findViewById(R.id.pricelist);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			//BitmapUtil.getInstance().download("", "", holder.imagelist);
			holder.namelist.setText(list.get(position).namelist);
			holder.salevolume.setText(list.get(position).salevolume);
			holder.pricelist.setText(list.get(position).pricelist);
		}
		return convertView;
	}

	class Holder {
		//ImageView imagelist;
		TextView namelist;
		TextView salevolume;
		TextView pricelist;
	}
}