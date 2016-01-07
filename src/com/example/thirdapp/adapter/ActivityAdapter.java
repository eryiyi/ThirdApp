package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.module.ActivityObj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ActivityAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<ActivityObj> list;
	private static SimpleDateFormat sf = null;
	
	public ActivityAdapter(Context context, List<ActivityObj> list){
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
			convertView = inflater.inflate(R.layout.itemactivity, null);
			//holder.actimage = (ImageView) convertView.findViewById(R.id.actimage);
			holder.actname = (TextView) convertView.findViewById(R.id.actname);
			holder.actdescription = (TextView) convertView.findViewById(R.id.actdescription);
			holder.acttime = (TextView) convertView.findViewById(R.id.acttime);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if (position < list.size()) {
			ActivityObj cell = list.get(position);
			holder.actname.setText(cell.getTitle()==null?"":cell.getTitle());
			holder.actdescription.setText(cell.getActive_name()==null?"":cell.getActive_name());
			holder.acttime.setText(cell.getActive_time()==null?"":cell.getActive_time());
		}
		return convertView;
	}

	class Holder {
		//ImageView actimage;
		TextView actname;
		TextView actdescription;
		TextView acttime;
	}
	
	public static String getDate(long time) {
		Date d = new Date(time * 1000L);
		sf = new SimpleDateFormat("MM月dd日");
		return sf.format(d);
	}
}
