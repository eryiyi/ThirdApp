package com.example.thirdapp.fragmentdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseFragment;

public class Vouchers extends BaseFragment implements OnClickListener{
	View view;
	TextView t1;
	TextView t2;
	TextView t3;
	TextView t4;
	TextView t5;
	TextView t6;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.vouchers, null);
		t1 = (TextView) view.findViewById(R.id.t1);
		t2 = (TextView) view.findViewById(R.id.t2);
		t3 = (TextView) view.findViewById(R.id.t3);
		t4 = (TextView) view.findViewById(R.id.t4);
		t5 = (TextView) view.findViewById(R.id.t5);
		t6 = (TextView) view.findViewById(R.id.t6);
		t1.setOnClickListener(this);
		t2.setOnClickListener(this);
		t3.setOnClickListener(this);
		t4.setOnClickListener(this);
		t5.setOnClickListener(this);
		t6.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		int position = 0;
		switch (v.getId()) {
		case R.id.t1:
			position = 0;
			break;
		case R.id.t2:
			position = 1;
			break;
		case R.id.t3:
			position = 2;
			break;
		case R.id.t4:
			position = 3;
			break;
		case R.id.t5:
			position = 4;
			break;
		case R.id.t6:
			position = 5;
			break;
		default:
			break;
		}
		setselected(position);
	}
	
	public void setselected(int position) {
		switch (position) {
		case 0:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			break;
		case 1:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			break;
		case 2:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			break;
		case 3:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			break;
		case 4:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			break;
		case 5:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			break;
		default:
			break;
		}
	}
}
