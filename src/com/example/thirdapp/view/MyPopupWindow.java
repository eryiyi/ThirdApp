package com.example.thirdapp.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.example.thirdapp.R;

public class MyPopupWindow extends PopupWindow{
	public static LinearLayout popupitem1;
	public static LinearLayout popupitem2;
	public static LinearLayout popupitem3;
	public static LinearLayout popupitem4;
	public static View view;
	
	public static void showPopupWindow(final Context context, View menu){
		PopupWindow popupWindow = new PopupWindow();
		popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);//设置可点击
		popupWindow.setFocusable(true); //可聚焦
		popupWindow.setOutsideTouchable(true);//设置外部可点击
		ColorDrawable colorDrawable = new ColorDrawable(0xffffffff);
		popupWindow.setBackgroundDrawable(colorDrawable); //设置背景
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.popupitem, null);
		/*popupitem1 = (LinearLayout) view.findViewById(R.id.popupitem1);
		popupitem2 = (LinearLayout) view.findViewById(R.id.popupitem2);
		popupitem3 = (LinearLayout) view.findViewById(R.id.popupitem3);
		popupitem4 = (LinearLayout) view.findViewById(R.id.popupitem4);*/
		/*popupitem1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, MainActivityFirstApp.class);
				intent.putExtra("skip", 0);
				context.startActivity(intent);
			}
		});
		
		popupitem2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, MainActivityFirstApp.class);
				intent.putExtra("skip", 1);
				context.startActivity(intent);
			}
		});
		
		popupitem3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, MainActivityFirstApp.class);
				intent.putExtra("skip", 3);
				context.startActivity(intent);
			}
		});
		
		popupitem4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, MainActivityFirstApp.class);
				intent.putExtra("skip", 4);
				context.startActivity(intent);
			}
		});*/
		
		popupWindow.setAnimationStyle(R.style.AnimationPreview);
		int w = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
		popupWindow.setContentView(view);
		popupWindow.setWidth(w / 2 + 50);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);  
		popupWindow.showAsDropDown(menu, menu.getLayoutParams().width / 2 + 50, 0);
	}
}
