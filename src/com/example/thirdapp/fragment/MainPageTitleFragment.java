package com.example.thirdapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.activity.Xq;
import com.example.thirdapp.base.BaseFragment;
import com.example.thirdapp.city.CityList;
import com.example.thirdapp.module.Community;
import com.example.thirdapp.util.StringUtil;
import com.zxing.activity.CaptureActivity;

public class MainPageTitleFragment extends BaseFragment implements OnClickListener {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	View view;
	ImageView scanning;
	LinearLayout xq;
	LinearLayout location;

	private TextView mine_cityName;
	private TextView commutiy_text;
	Community community;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		registerBoradcastReceiver();
		view = inflater.inflate(R.layout.mainpagetitle, null);
		scanning = (ImageView) view.findViewById(R.id.scanning);
		location = (LinearLayout) view.findViewById(R.id.location);
		xq = (LinearLayout) view.findViewById(R.id.xq);
		scanning.setOnClickListener(this);
		location.setOnClickListener(this);
		xq.setOnClickListener(this);
		mine_cityName = (TextView) view.findViewById(R.id.mine_cityName);
		commutiy_text = (TextView) view.findViewById(R.id.commutiy_text);

		if(!StringUtil.isNullOrEmpty(ThirdApplication.cityName)){
			mine_cityName.setText(ThirdApplication.cityName);
		}else if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("city", ""), String.class))) {
			mine_cityName.setText(getGson().fromJson(getSp().getString("city", ""), String.class));
		}


		if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("community_name", ""), String.class))){
			commutiy_text.setText(getGson().fromJson(getSp().getString("community_name", ""), String.class));
		}else {
			commutiy_text.setText("选择小区");
		}

		return view;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scanning:
			Intent intent = new Intent();
			intent.setClass(getActivity(), CaptureActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			break;
		case R.id.location:
//			Intent intent2 = new Intent(getActivity(), TestSort.class);
//			startActivity(intent2);
			Intent city = new Intent(getActivity(), CityList.class);
			startActivity(city);
			break;
		case R.id.xq:
			//选择小区
			Intent selectAddressView = new Intent(getActivity(), Xq.class);
			startActivityForResult(selectAddressView, 0);
			break;
		default:
			break;
		}
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			System.out.println("resultCode = " + resultCode);
			System.out.println("RESULT_OK = " + android.app.Activity.RESULT_OK);
			if (resultCode == android.app.Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				System.out.println("result = " + bundle.getString("result"));
				// 显示扫描到的内容
				// mTextView.setText(bundle.getString("result"));
				// 显示
				// mImageView.setImageBitmap((Bitmap)
				// data.getParcelableExtra("bitmap"));
			}
			break;
		}
		if(resultCode == 100){
			community = (Community) data.getExtras().get("community");
			if(community != null){
				commutiy_text.setText(community.getCommunity_name());
			}
		}
	}


	//广播接收动作
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (action.equals("select_city_success")) {
				//
				if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("city", ""), String.class))){
					mine_cityName.setText(getGson().fromJson(getSp().getString("city", ""), String.class));
				}else {
					mine_cityName.setText("重庆");
				}
			}
		}
	};

	//注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("add_goods_success");
		myIntentFilter.addAction("select_city_success");
		//注册广播
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}
}
