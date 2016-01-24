package com.example.thirdapp.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import com.example.thirdapp.R;
import com.example.thirdapp.activity.Logon;
import com.example.thirdapp.base.BaseFragment;

public class SunFunTitleFragment extends BaseFragment implements OnClickListener{
	View view;
	ImageView more;
	View viewpopup;
	ImageView refreshmall;
	PopupWindow popupWindow;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.sunfantitle, null);
		more = (ImageView) view.findViewById(R.id.more);
		more.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more:
			//
			Intent intent = new Intent("add_box");
			getActivity().sendBroadcast(intent);
			break;
		case R.id.refreshmall:
			popupWindow.dismiss();
			break;
		default:
			break;
		}
	}
	
	public void showPopupWindow() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		viewpopup = inflater.inflate(R.layout.sunonlyonepopupwindow, null);
		refreshmall = (ImageView) viewpopup.findViewById(R.id.refreshmall);
		refreshmall.setOnClickListener(this);
		
		//WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		//int wid = wm.getDefaultDisplay().getWidth();
		popupWindow = new PopupWindow(getActivity());
		popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true); // 可聚焦
		popupWindow.setOutsideTouchable(true);// 设置外部可点击
		ColorDrawable colorDrawable = new ColorDrawable(0xffffffff);
		popupWindow.setBackgroundDrawable(colorDrawable); // 设置背景
		//设置popupwindow动画效果
		//popupWindow.setAnimationStyle(R.style.AnimationPreview);
		popupWindow.setContentView(viewpopup);
		popupWindow.showAsDropDown(more, 50, 0);
		//popupWindow.showAtLocation(viewpopup.findViewById(R.id.cancelinvate), Gravity.CENTER, 0, 0);
	}
}
