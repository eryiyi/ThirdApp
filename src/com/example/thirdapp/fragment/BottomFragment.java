package com.example.thirdapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseFragment;

public class BottomFragment extends BaseFragment implements OnClickListener{
	public static interface OnBottomClickListener {
		void onBottomClick(View v, int position);
	}
	public static final int MAINPAGE = 0;
	public static final int TRAVEL = 1;
	public static final int SUNFAN = 2;
	public static final int MALL = 3;
	public static final int MY = 4;
	LinearLayout mainpage;
	LinearLayout travel;
	LinearLayout sunfan;
	LinearLayout mall;
	LinearLayout my;
	ImageView mainpage_image;
	ImageView travel_image;
	ImageView sunfan_image;
	ImageView mall_image;
	ImageView my_image;
	TextView mainpage_text;
	TextView travel_text;
	TextView sunfan_text;
	TextView mall_text;
	TextView my_text;
	private View view;
	private OnBottomClickListener bottomClickListener;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.bottombartest, null);
		mainpage = (LinearLayout) view.findViewById(R.id.linear1);
		travel = (LinearLayout) view.findViewById(R.id.linear2);
		sunfan = (LinearLayout) view.findViewById(R.id.linear3);
		mall = (LinearLayout) view.findViewById(R.id.linear4);
		my = (LinearLayout) view.findViewById(R.id.linear5);
		
		mainpage_image = (ImageView) view.findViewById(R.id.image1);
		travel_image = (ImageView) view.findViewById(R.id.image2);
		sunfan_image = (ImageView) view.findViewById(R.id.image3);
		mall_image = (ImageView) view.findViewById(R.id.image4);
		my_image = (ImageView) view.findViewById(R.id.image5);
		
		mainpage_text = (TextView) view.findViewById(R.id.text1);
		travel_text = (TextView) view.findViewById(R.id.text2);
		sunfan_text = (TextView) view.findViewById(R.id.text3);
		mall_text = (TextView) view.findViewById(R.id.text4);
		my_text = (TextView) view.findViewById(R.id.text5);
		
		mainpage.setOnClickListener(this);
		travel.setOnClickListener(this);
		sunfan.setOnClickListener(this);
		mall.setOnClickListener(this);
		my.setOnClickListener(this);
		return view;
		
	}
	
	public void setBottomClickListener(OnBottomClickListener bottomClickListener) {
		this.bottomClickListener = bottomClickListener;
	}
	
	public void setSelected(int position){
		switch (position) {
		case MAINPAGE:
			mainpage_image.setImageResource(R.drawable.mpred);
			travel_image.setImageResource(R.drawable.trlgrey);
			sunfan_image.setImageResource(R.drawable.sfgrey);
			mall_image.setImageResource(R.drawable.mallgrey);
			my_image.setImageResource(R.drawable.mygrey);
			
			mainpage_text.setTextColor(getResources().getColor(R.color.red));
			travel_text.setTextColor(getResources().getColor(R.color.common_greythird));
			sunfan_text.setTextColor(getResources().getColor(R.color.common_greythird));
			mall_text.setTextColor(getResources().getColor(R.color.common_greythird));
			my_text.setTextColor(getResources().getColor(R.color.common_greythird));
			break;
		case TRAVEL:
			mainpage_image.setImageResource(R.drawable.mpgrey);
			travel_image.setImageResource(R.drawable.trlred);
			sunfan_image.setImageResource(R.drawable.sfgrey);
			mall_image.setImageResource(R.drawable.mallgrey);
			my_image.setImageResource(R.drawable.mygrey);
			
			mainpage_text.setTextColor(getResources().getColor(R.color.common_greythird));
			travel_text.setTextColor(getResources().getColor(R.color.red));
			sunfan_text.setTextColor(getResources().getColor(R.color.common_greythird));
			mall_text.setTextColor(getResources().getColor(R.color.common_greythird));
			my_text.setTextColor(getResources().getColor(R.color.common_greythird));
			break;
		case SUNFAN:
			mainpage_image.setImageResource(R.drawable.mpgrey);
			travel_image.setImageResource(R.drawable.trlgrey);
			sunfan_image.setImageResource(R.drawable.sfred);
			mall_image.setImageResource(R.drawable.mallgrey);
			my_image.setImageResource(R.drawable.mygrey);
			
			mainpage_text.setTextColor(getResources().getColor(R.color.common_greythird));
			travel_text.setTextColor(getResources().getColor(R.color.common_greythird));
			sunfan_text.setTextColor(getResources().getColor(R.color.red));
			mall_text.setTextColor(getResources().getColor(R.color.common_greythird));
			my_text.setTextColor(getResources().getColor(R.color.common_greythird));
			break;
		case MALL:
			mainpage_image.setImageResource(R.drawable.mpgrey);
			travel_image.setImageResource(R.drawable.trlgrey);
			sunfan_image.setImageResource(R.drawable.sfgrey);
			mall_image.setImageResource(R.drawable.mallred);
			my_image.setImageResource(R.drawable.mygrey);
			
			mainpage_text.setTextColor(getResources().getColor(R.color.common_greythird));
			travel_text.setTextColor(getResources().getColor(R.color.common_greythird));
			sunfan_text.setTextColor(getResources().getColor(R.color.common_greythird));
			mall_text.setTextColor(getResources().getColor(R.color.red));
			my_text.setTextColor(getResources().getColor(R.color.common_greythird));
			break;
		case MY:
			mainpage_image.setImageResource(R.drawable.mpgrey);
			travel_image.setImageResource(R.drawable.trlgrey);
			sunfan_image.setImageResource(R.drawable.sfgrey);
			mall_image.setImageResource(R.drawable.mallgrey);
			my_image.setImageResource(R.drawable.myred);
			
			mainpage_text.setTextColor(getResources().getColor(R.color.common_greythird));
			travel_text.setTextColor(getResources().getColor(R.color.common_greythird));
			sunfan_text.setTextColor(getResources().getColor(R.color.common_greythird));
			mall_text.setTextColor(getResources().getColor(R.color.common_greythird));
			my_text.setTextColor(getResources().getColor(R.color.red));
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		int position = 0;
		switch (v.getId()) {
		case R.id.linear1:
			position = MAINPAGE;
			break;
		case R.id.linear2:
			position = TRAVEL;
			break;
		case R.id.linear3:
			position = SUNFAN;
			break;
		case R.id.linear4:
			position = MALL;
			break;
		case R.id.linear5:
			position = MY;
			break;
		default:
			break;
		}
		setSelected(position);
		if (bottomClickListener != null) {
			bottomClickListener.onBottomClick(v, position);
		}
	}
}
