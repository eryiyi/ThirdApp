package com.example.thirdapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.WalletAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.bean.WalletBean;
import com.example.thirdapp.view.MyListView;

import java.util.ArrayList;
import java.util.List;

public class Wallet extends BaseActivity implements OnClickListener{
	MyListView lv1;
	MyListView lv2;
	List<WalletBean> list;
	WalletAdapter adapter;
	ImageView back;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.wallet);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		lv1 = (MyListView) findViewById(R.id.savingscardlv);
		lv2 = (MyListView) findViewById(R.id.creditlv);
		list = new ArrayList<WalletBean>();
		WalletBean bean = new WalletBean(R.drawable.bankofchina, "中国银行", "1234567890");
		list.add(bean);
		WalletBean bean2 = new WalletBean(R.drawable.constructionbank, "建设银行", "1234567890");
		list.add(bean2);
		WalletBean bean3 = new WalletBean(R.drawable.merchantsbank, "招商银行", "1234567890");
		list.add(bean3);
		WalletBean bean4 = new WalletBean(R.drawable.agriculturalbank, "农业银行", "1234567890");
		list.add(bean4);
		adapter = new WalletAdapter(this, list);
		lv1.setAdapter(adapter);
		lv2.setAdapter(adapter);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
}
