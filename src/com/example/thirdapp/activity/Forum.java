package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.ItemTalkAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.TalkObjData;
import com.example.thirdapp.module.TalkObj;
import com.example.thirdapp.module.TalkTypeObj;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Forum extends BaseActivity implements OnClickListener{
	ImageView back;
	private TalkTypeObj talkTypeObj;
	private ListView lstv;
	private ItemTalkAdapter adaper;
	List<TalkObj> lists = new ArrayList<TalkObj>();
	private TextView add;
	private EditText auto;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.forum);
		registerBoradcastReceiver();
		talkTypeObj = (TalkTypeObj) getIntent().getExtras().get("talkTypeObj");
		initView();
		progressDialog = new CustomProgressDialog(Forum.this , "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getData();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.back);
		add = (TextView) findViewById(R.id.add);
		add.setOnClickListener(this);
		back.setOnClickListener(this);
		lstv = (ListView) this.findViewById(R.id.lstv);
		adaper = new ItemTalkAdapter(lists, Forum.this);
		lstv.setAdapter(adaper);
		lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TalkObj talkObj = lists.get(position);
				Intent intent = new Intent(Forum.this, TalkDetailActivity.class);
				intent.putExtra("talkObjId", talkObj.getId());
				startActivity(intent);
			}
		});
		auto = (EditText) this.findViewById(R.id.auto);
		auto.addTextChangedListener(mTextWatcher);
	}


	TextWatcher mTextWatcher = new TextWatcher() {
		private CharSequence temp;
		private int editStart ;
		private int editEnd ;
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			temp = s;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
									  int after) {
			// TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if(!StringUtil.isNullOrEmpty(auto.getText().toString())){
				//不等于空的时候
				getDataTwo();
			}else {
				//等于空
				getData();
			}
		}
	};


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.back:
				finish();
				break;
			case R.id.add:
				//
				Intent intent  = new Intent (Forum.this, AddTalkActivity.class);
				intent.putExtra("cat_id", talkTypeObj.getId());
				startActivity(intent);
				break;
		}
	}

	void getData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.TALK_LISTS_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									lists.clear();
									TalkObjData data = getGson().fromJson(s, TalkObjData.class);
									lists.addAll(data.getData());
									adaper.notifyDataSetChanged();
								}else if(Integer.parseInt(code) == -1){
									showMsg(Forum.this, "暂无数据");
								}
								else {
									Toast.makeText(Forum.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(Forum.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
						}
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						Toast.makeText(Forum.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("cat_id", talkTypeObj.getId());
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/x-www-form-urlencoded");
				return params;
			}
		};
		getRequestQueue().add(request);
	}

	//广播接收动作
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("address_talk")) {
				Resources res = getBaseContext().getResources();
				String message = res.getString(R.string.please_wait).toString();
				progressDialog = new CustomProgressDialog(Forum.this , "", R.anim.frame_paopao);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressDialog.setCancelable(false);
				progressDialog.setIndeterminate(true);
				progressDialog.show();
				getData();
			}

		}
	};

	//注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("address_talk");
		//注册广播
		this.registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(mBroadcastReceiver);
	}


	void getDataTwo(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.TALK_SEARCH_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									lists.clear();
									TalkObjData data = getGson().fromJson(s, TalkObjData.class);
									lists.addAll(data.getData());
									adaper.notifyDataSetChanged();
								}else if(Integer.parseInt(code) == -1){
									showMsg(Forum.this, "暂无数据");
								}
								else {
									Toast.makeText(Forum.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(Forum.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
						}
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						Toast.makeText(Forum.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("keyword", talkTypeObj.getId());
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/x-www-form-urlencoded");
				return params;
			}
		};
		getRequestQueue().add(request);
	}
}
