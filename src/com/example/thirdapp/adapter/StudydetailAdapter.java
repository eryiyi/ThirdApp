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
import com.example.thirdapp.module.AnswerObj;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;


public class StudydetailAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<AnswerObj> list;
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	public StudydetailAdapter(Context context, List<AnswerObj> list){
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
			convertView = inflater.inflate(R.layout.itemstudy, null);
			holder.stdimage = (ImageView) convertView.findViewById(R.id.stdimage);
			holder.stdname = (TextView) convertView.findViewById(R.id.stdname);
			holder.stdcontent = (TextView) convertView.findViewById(R.id.stdcontent);
			convertView.findViewById(R.id.stdtime).setVisibility(View.GONE);
			convertView.findViewById(R.id.stdchecknum).setVisibility(View.GONE);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			AnswerObj cell = list.get(position);
			imageLoader.displayImage(InternetURL.INTERNAL_PIC + cell.getCover(), holder.stdimage, ThirdApplication.options, animateFirstListener);
			holder.stdname.setText(cell.getNick_name()==null?"":cell.getNick_name());
			holder.stdcontent.setText(cell.getContent()==null?"":cell.getContent());
		}
		return convertView;
	}

	class Holder {
		ImageView stdimage;
		TextView stdname;
		TextView stdcontent;
	}
}
