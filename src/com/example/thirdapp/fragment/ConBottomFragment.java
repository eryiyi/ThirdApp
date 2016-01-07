package com.example.thirdapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseFragment;

public class ConBottomFragment extends BaseFragment implements OnClickListener{
	
	public static interface OnConBottomClickListener {
		void onConBottomClick(View v, int position);
	}
	
	public static final int conbottom1 = 0;
	public static final int conbottom2 = 1;
	public static final int conbottom3 = 2;
	ImageView conimage1;
	ImageView conimage2;
	ImageView conimage3;
	private View view;
	private OnConBottomClickListener bottomClickListener;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.conbottombar, null);
		conimage1 = (ImageView) view.findViewById(R.id.conbottom1);
		conimage2 = (ImageView) view.findViewById(R.id.conbottom2);
		conimage3 = (ImageView) view.findViewById(R.id.conbottom3);
		
		conimage1.setOnClickListener(this);
		conimage2.setOnClickListener(this);
		conimage3.setOnClickListener(this);
		return view;
	}
	
	public void setConBottomClickListener(OnConBottomClickListener bottomClickListener) {
		this.bottomClickListener = bottomClickListener;
	}
	
	public void setSelected(int position){
		switch (position) {
		case conbottom1:
			conimage1.setImageResource(R.drawable.con1checked);
			conimage2.setImageResource(R.drawable.con2unchecked);
			conimage3.setImageResource(R.drawable.con3unchecked);
			break;
		case conbottom2:
			conimage1.setImageResource(R.drawable.con1unchecked);
			conimage2.setImageResource(R.drawable.con2checked);
			conimage3.setImageResource(R.drawable.con3unchecked);
			break;
		case conbottom3:
			conimage1.setImageResource(R.drawable.con1unchecked);
			conimage2.setImageResource(R.drawable.con2unchecked);
			conimage3.setImageResource(R.drawable.con3checked);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		int position = 0;
		switch (v.getId()) {
		case R.id.conbottom1:
			position = conbottom1;
			break;
		case R.id.conbottom2:
			position = conbottom2;
			break;
		case R.id.conbottom3:
			position = conbottom3;
			break;
		default:
			break;
		}
		setSelected(position);
		if (bottomClickListener != null) {
			bottomClickListener.onConBottomClick(v, position);
		}
	}
}
