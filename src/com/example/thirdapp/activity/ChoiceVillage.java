package com.example.thirdapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.ChoiceVillageAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.bean.VillageBean;
import com.example.thirdapp.http.AsyncHttpResponseHandler;
import com.example.thirdapp.http.HttpClientUtils;
import com.example.thirdapp.http.HttpParams;
import com.example.thirdapp.serverid.ServerId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChoiceVillage extends BaseActivity {
	List<VillageBean> list;
	ChoiceVillageAdapter adapter;
	ListView cvlv;
	public static String communityname;
	public static int communityid;
	TextView confirm;
	int flag = 0;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.choicevillage);
		getVillage();
		cvlv = (ListView) findViewById(R.id.villagelv);
		confirm = (TextView) findViewById(R.id.confirm);
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (flag == 0) {
					communityname = list.get(0).community_name;
				}
				finish();
			}
		});
		list = new ArrayList<VillageBean>();
		adapter = new ChoiceVillageAdapter(this, list);
		cvlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				communityname = list.get(position).community_name;
				communityid = list.get(position).community_id;
				flag = 1;
				((ChoiceVillageAdapter) parent.getAdapter()).setSelection(position);
			}
		});
	}

	public void getVillage() {
		HttpParams params = new HttpParams();
		HttpClientUtils.getInstance().post(ServerId.serveradress2,
				ServerId.getCommunityList, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject jsonObject) {
						System.out.println("jsonObject = " + jsonObject);
						JSONArray jsonArray = jsonObject.optJSONArray("data");
						if (jsonArray != null) {
							for (int i = 0; i < jsonArray.length(); i++) {
								VillageBean bean = new VillageBean();
								JSONObject object = jsonArray.optJSONObject(i);
								bean.area = object.optString("area");
								bean.address = object.optString("address");
								bean.community_name = object
										.optString("community_name");
								bean.community_id = object
										.optInt("community_id");
								list.add(bean);
							}
						}
						Message message = new Message();
						message.what = 123;
						handler.sendMessage(message);
					}
				});
	}

	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				cvlv.setAdapter(adapter);
				// adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
			return false;
		}
	});
}
