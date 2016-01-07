package com.example.thirdapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.fragmentdetail.ValueAgainst;
import com.example.thirdapp.fragmentdetail.Vouchers;

public class Integral extends BaseActivity implements OnClickListener{
	private static FragmentManager fManager;
	private FragmentTransaction transaction;
	private static ValueAgainst fm1;
	private static Vouchers fm2;
	LinearLayout valueagainst;
	LinearLayout vouchers;
	TextView text1;
	TextView text2;
	ImageView redmark;
	ImageView yellowmark;
	ImageView back;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.integral);
		back = (ImageView) findViewById(R.id.back);
		redmark = (ImageView) findViewById(R.id.redmark);
		yellowmark = (ImageView) findViewById(R.id.yellowmark);
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		valueagainst = (LinearLayout) findViewById(R.id.valueagainst);
		vouchers = (LinearLayout) findViewById(R.id.vouchers);
		back.setOnClickListener(this);
		valueagainst.setOnClickListener(this);
		vouchers.setOnClickListener(this);
		fManager = getSupportFragmentManager();
		setChioceItem(0);
	}
	
	
	public void setChioceItem(int index) {
		transaction = fManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 0:
			if (fm1 == null) {
				fm1 = new ValueAgainst();
				transaction.add(R.id.integralcontent, fm1);
			} else {
				transaction.show(fm1);
			}
			break;
		case 1:
			if (fm2 == null) {
				fm2 = new Vouchers();
				transaction.add(R.id.integralcontent, fm2);
			} else {
				transaction.show(fm2);
			}
			break;
		}
		transaction.commit();
	}
	
	private void hideFragments(FragmentTransaction transaction) {
		if (fm1 != null) {
			transaction.hide(fm1);
		}
		if (fm2 != null) {
			transaction.hide(fm2);
		}
	}
	
	@Override
	public void onPause() {
		fm1 = null;
		fm2 = null;
		super.onPause();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.valueagainst:
			text1.setTextColor(getResources().getColor(R.color.red));
			text2.setTextColor(getResources().getColor(R.color.common_grey_word));
			redmark.setImageResource(R.drawable.redmark);
			yellowmark.setImageResource(R.drawable.transparent);
			setChioceItem(0);
		
			break;
		case R.id.vouchers:
			text1.setTextColor(getResources().getColor(R.color.common_grey_word));
			text2.setTextColor(getResources().getColor(R.color.yellownew));
			redmark.setImageResource(R.drawable.transparent);
			yellowmark.setImageResource(R.drawable.yellowmark);
			setChioceItem(1);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
}
