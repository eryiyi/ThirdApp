package com.example.thirdapp.fragmentdetail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.ValueAgainstAdapter;
import com.example.thirdapp.base.BaseFragment;
import com.example.thirdapp.bean.ValueAgainstBean;
import com.example.thirdapp.view.MyListViewUpDown;
import com.example.thirdapp.view.MyListViewUpDown.OnLoadListener;
import com.example.thirdapp.view.MyListViewUpDown.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ValueAgainst extends BaseFragment implements OnRefreshListener, OnLoadListener, OnClickListener{
	View view;
	MyListViewUpDown valueagainstlv;
	List<ValueAgainstBean> list;
	ValueAgainstAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.valueagainst, null);
		valueagainstlv = (MyListViewUpDown) view.findViewById(R.id.valueagainstlv);
		valueagainstlv.setonRefreshListener(this);
		valueagainstlv.setOnLoadListener(this);
		list = new ArrayList<ValueAgainstBean>();
		for (int i = 1; i < 7; i++) {
			ValueAgainstBean bean = new ValueAgainstBean("", "高档材质的商品，绝对好用", "限量" + i * 10, i + 5 + "积分", "￥68");
			list.add(bean);
		}
		adapter = new ValueAgainstAdapter(getActivity(), list);
		valueagainstlv.setAdapter(adapter);
		return view;
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
				valueagainstlv.onLoadComplete();
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
				valueagainstlv.onRefreshComplete();
			};
		}.execute(null, null, null);
	}

	@Override
	public void onClick(View v) {
		
	}
}
