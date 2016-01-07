package com.example.thirdapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.VillageNumberAdapter;
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

public class VillageNumber extends BaseActivity {
	List<VillageBean> list;
	VillageNumberAdapter adapter;
	ListView numberlv;
	ImageView back;
	TextView nonumber;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.villagenumber);
		back = (ImageView) findViewById(R.id.back);
		numberlv = (ListView) findViewById(R.id.numberlv);
		nonumber = (TextView) findViewById(R.id.nonumber);
		getPhoneNumber();
		list = new ArrayList<VillageBean>();
		adapter = new VillageNumberAdapter(this, list);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	
	public void getPhoneNumber(){
		HttpParams params = new HttpParams();
		params.put("community_id", getGson().fromJson(getSp().getString("community_id", ""), String.class));
		HttpClientUtils.getInstance().get(ServerId.serveradress2, ServerId.getCommunityPhone, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("jsonObject = " + jsonObject);
				JSONArray jsonArray = jsonObject.optJSONArray("data");
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.length(); i++) {
						VillageBean bean = new VillageBean();
						JSONObject object = jsonArray.optJSONObject(i);
						bean.address = object.optString("address");
						bean.community_name = object.optString("community_name");
						bean.number = object.optString("number");
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
				if (list.size() == 0) {
					nonumber.setVisibility(View.VISIBLE);
				}else {
					numberlv.setAdapter(adapter);
				}
				// adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
			return false;
		}
	});
}
