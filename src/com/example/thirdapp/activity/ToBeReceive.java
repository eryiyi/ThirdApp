package com.example.thirdapp.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.ToBeReceiveAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.bean.ToBeBean;
import com.example.thirdapp.view.MyListViewUpDown;
import com.example.thirdapp.view.MyListViewUpDown.OnLoadListener;
import com.example.thirdapp.view.MyListViewUpDown.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ToBeReceive extends BaseActivity implements OnRefreshListener, OnLoadListener{
	MyListViewUpDown lv;
	List<ToBeBean> list;
	ToBeReceiveAdapter adapter;
	TextView titlechange;
	ImageView back;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.tobepaid);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		titlechange = (TextView) findViewById(R.id.titlechange);
		titlechange.setText(R.string.tobereceive);
		lv = (MyListViewUpDown) findViewById(R.id.tobepaidlv);
		lv.setonRefreshListener(this);
		lv.setOnLoadListener(this);
		list = new ArrayList<ToBeBean>();
		for (int i = 0; i < 5; i++) {
			ToBeBean bean = new ToBeBean("网上玩具店", "", "遥控飞机，非常耐摔，非常值得拥有，值得观赏", "颜色分类:黑白色", "￥ 398.00", "×1");
			list.add(bean);
		}
		adapter = new ToBeReceiveAdapter(this, list);
		lv.setAdapter(adapter);
	}
	
	@Override
	public void onLoad() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//上拉加载的内容
				return null;
			}

			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();
				lv.onLoadComplete();
			};
		}.execute(null, null, null);
	}

	@Override
	public void onRefresh() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/*FruitBean bean = new FruitBean("下拉刷新测试");  
				list.add(bean);*/
				return null;
			}

			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();
				lv.onRefreshComplete();
			};
		}.execute(null, null, null);
	}
}
