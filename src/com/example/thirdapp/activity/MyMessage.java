package com.example.thirdapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.MyMsgAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.bean.MyMsgBean;
import com.example.thirdapp.http.AsyncHttpResponseHandler;
import com.example.thirdapp.http.HttpClientUtils;
import com.example.thirdapp.http.HttpParams;
import com.example.thirdapp.serverid.ServerId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyMessage extends BaseActivity {
	ListView mymsglv;
	List<MyMsgBean> list;
	MyMsgAdapter adapter;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.mymessage);
		mymsglv = (ListView) findViewById(R.id.mymsglv);
		getMyMessage();
		list = new ArrayList<MyMsgBean>();
		adapter = new MyMsgAdapter(this, list);
	
	}
	
	public void getMyMessage(){
		HttpParams params = new HttpParams();
		params.put("uid", getGson().fromJson(getSp().getString("uid", ""), String.class));
		HttpClientUtils.getInstance().get(ServerId.serveradress2, ServerId.getMessage, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("jsonObject = " + jsonObject);
				JSONArray jsonArray = jsonObject.optJSONArray("data");
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.length(); i++) {
						MyMsgBean bean = new MyMsgBean();
						JSONObject object = jsonArray.optJSONObject(i);
						bean.content = object.optString("content");
						bean.publictime = object.optString("dateline");
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
				mymsglv.setAdapter(adapter);
				break;
			default:
				break;
			}
			return false;
		}
	});
}
