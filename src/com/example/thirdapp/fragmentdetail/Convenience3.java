package com.example.thirdapp.fragmentdetail;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.PopupTextAdapter;
import com.example.thirdapp.base.BaseFragment;
import com.example.thirdapp.bean.PopupTextlBean;

import java.util.ArrayList;
import java.util.List;

public class Convenience3 extends BaseFragment implements OnClickListener{
	View view;
	View view2;
	View line;
	ImageView image1;
	ImageView image2;
	ImageView image3;
	LinearLayout rent;
	LinearLayout livingroom;
	LinearLayout source;
	private static int ll;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view =inflater.inflate(R.layout.convenience3, null);
		line = view.findViewById(R.id.line);
		image1 = (ImageView) view.findViewById(R.id.image1);
		image2 = (ImageView) view.findViewById(R.id.image2);
		image3 = (ImageView) view.findViewById(R.id.image3);
		rent = (LinearLayout) view.findViewById(R.id.rent);
		livingroom = (LinearLayout) view.findViewById(R.id.livingroom);
		source = (LinearLayout) view.findViewById(R.id.source);
		//取屏幕的宽
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		ll = screenW/3;
		rent.setOnClickListener(this);
		livingroom.setOnClickListener(this);
		source.setOnClickListener(this);
		
		return view;
	}
	
	public void showPopupWindow(View imageView, int posX){
		final PopupWindow popupWindow = new PopupWindow(getActivity());
		popupWindow.setWindowLayoutMode(ll, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);//设置可点击
		popupWindow.setFocusable(true); //可聚焦
		popupWindow.setOutsideTouchable(true);//设置外部可点击
		ColorDrawable colorDrawable = new ColorDrawable(0xffffffff);
		popupWindow.setBackgroundDrawable(colorDrawable); //设置背景
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		view2 = inflater.inflate(R.layout.popupitem, null);
		ListView popuplv = (ListView) view2.findViewById(R.id.popuplv);
		List<PopupTextlBean> list = new ArrayList<PopupTextlBean>();
		if(posX ==  0){
			PopupTextlBean bean1 = new PopupTextlBean("不限");
			list.add(bean1);
			PopupTextlBean bean2 = new PopupTextlBean("500以下");
			list.add(bean2);
			PopupTextlBean bean3 = new PopupTextlBean("500-1000");
			list.add(bean3);
			PopupTextlBean bean4 = new PopupTextlBean("1000-1500");
			list.add(bean4);
			PopupTextlBean bean5 = new PopupTextlBean("1500-2000");
			list.add(bean5);
			PopupTextlBean bean6 = new PopupTextlBean("2000-2500");
			list.add(bean6);
			PopupTextlBean bean7 = new PopupTextlBean("2500-3000");
			list.add(bean7);
			PopupTextlBean bean8 = new PopupTextlBean("3000以上");
			list.add(bean8);
		}
		if(posX ==  ll*1){
			PopupTextlBean bean1 = new PopupTextlBean("不限");
			list.add(bean1);
			PopupTextlBean bean2 = new PopupTextlBean("主卧");
			list.add(bean2);
			PopupTextlBean bean3 = new PopupTextlBean("次卧");
			list.add(bean3);
			PopupTextlBean bean4 = new PopupTextlBean("隔断");
			list.add(bean4);
		}
		if(posX ==  ll*2){
			PopupTextlBean bean1 = new PopupTextlBean("不限");
			list.add(bean1);
			PopupTextlBean bean2 = new PopupTextlBean("个人");
			list.add(bean2);
			PopupTextlBean bean3 = new PopupTextlBean("经纪人");
			list.add(bean3);
			PopupTextlBean bean4 = new PopupTextlBean("诚信房源");
			list.add(bean4);
		}
		PopupTextAdapter adapter = new PopupTextAdapter(getActivity(), list);
		popuplv.setAdapter(adapter);
		popuplv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				System.out.println("pos = " + position);
				popupWindow.dismiss();
			}
		});
		popupWindow.setAnimationStyle(R.style.AnimationPreview);
		popupWindow.setContentView(view2);
		popupWindow.setWidth(ll);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);  
		popupWindow.showAsDropDown(imageView, posX, 0);
		popupWindow.setOnDismissListener(new poponDismissListener());
	}
	
	public void backgroundAlpha(float bgAlpha) {

		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getActivity().getWindow().setAttributes(lp);
	}

	public class poponDismissListener implements PopupWindow.OnDismissListener {

		@Override
		public void onDismiss() {
			backgroundAlpha(1f);
			image1.setImageResource(R.drawable.rent);
			image2.setImageResource(R.drawable.livingroom);
			image3.setImageResource(R.drawable.source);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rent:
			showPopupWindow(line, 0);
			image1.setImageResource(R.drawable.rentb);
			break;
		case R.id.livingroom:
			showPopupWindow(line,  ll*1);
			image2.setImageResource(R.drawable.livingroomb);
			break;
		case R.id.source:
			showPopupWindow(line,  ll*2);
			image3.setImageResource(R.drawable.sourceb);
			break;
		default:
			break;
		}
	}
}
