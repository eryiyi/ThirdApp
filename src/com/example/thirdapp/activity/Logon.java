package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.MainActivity;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.UserObjData;
import com.example.thirdapp.module.UserObj;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Logon extends BaseActivity implements OnClickListener{
	ImageView back;
	TextView logon;
	TextView forgetpsd;
	EditText logonusername;
	EditText logonpsd;
	JSONObject object;
	ImageView register;
	private int skip;
	public static int flag = 0;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		registerBoradcastReceiver();
		setContentView(R.layout.logon);
		skip = getIntent().getIntExtra("skip", 0);
		register = (ImageView) findViewById(R.id.register);
		logonusername = (EditText) findViewById(R.id.logonusername);
		logonpsd = (EditText) findViewById(R.id.logonpsd);
		back = (ImageView) findViewById(R.id.back);
		logon = (TextView) findViewById(R.id.logon);
		forgetpsd = (TextView) findViewById(R.id.forgetpsd);
		back.setOnClickListener(this);
		logon.setOnClickListener(this);
		register.setOnClickListener(this);
		forgetpsd.setOnClickListener(this);

		if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("mobile", ""), String.class))){
			logonusername.setText(getGson().fromJson(getSp().getString("mobile", ""), String.class));
		}if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("password", ""), String.class))){
			logonpsd.setText(getGson().fromJson(getSp().getString("password", ""), String.class));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			flag = 1;
			finish();
			break;
		case R.id.logon:
			if(StringUtil.isNullOrEmpty(logonusername.getText().toString().trim())){
				showMsg(Logon.this, "请输入账号");
				return;
			}
			if(StringUtil.isNullOrEmpty(logonpsd.getText().toString().trim())){
				showMsg(Logon.this, "请输入密码");
				return;
			}
			progressDialog = new CustomProgressDialog(Logon.this , "", R.anim.frame_paopao);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			progressDialog.show();
			loginData();
			break;
		case R.id.register:
			Intent intent2 = new Intent(Logon.this, RegisterDetail.class);
			startActivity(intent2);
			break;
		case R.id.forgetpsd:
			//修改密码
			Intent updatePwr = new Intent(Logon.this,UpdatePwrActivity.class);
			startActivity(updatePwr);
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			flag = 1;
			System.out.println("flag = " + flag);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private UserObj user;

	void loginData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.LOGIN_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200) {
									UserObjData data = getGson().fromJson(s, UserObjData.class);
									if (data.getCode() == 200) {
										user = data.getData();
										save("access_token", user.getAccess_token());
										save("uid", user.getUid());
										save("community_id", user.getCommunity_id());
										save("user_name", user.getUser_name());
										save("mobile", user.getMobile());
										save("password", logonpsd.getText().toString().trim());
										save("lng", user.getLng());
										save("lat", user.getLat());
										save("reg_time", user.getReg_time());
										save("type", user.getType());
										save("cover", user.getCover());
										save("address", user.getAddress());
										save("nick_name", user.getNick_name());
										save("name", user.getName());
										save("is_admin", user.getIs_admin());
										save("salt", user.getSalt());
										save("is_superadmin", user.getIs_superadmin());
										save("mobile_code", user.getMobile_code());
										save("support_num", user.getSupport_num());
										save("comment_num", user.getComment_num());
										save("collect_num", user.getCollect_num());
										save("view_num", user.getView_num());
										save("sex", user.getSex());
										save("birthday", user.getBirthday());
										save("xingzuo", user.getXingzuo());
										save("birthday_place", user.getBirthday_place());
										save("email", user.getEmail());
										save("geyan", user.getGeyan());
										save("ask_num", user.getAsk_num());
										save("answer_num", user.getAnswer_num());
										save("ask_answer_support_num", user.getAsk_answer_support_num());
										save("the_best", user.getThe_best());
										save("isLogin", "1");//0未登录  1登陆

										sendLocation();//发送定位数据

										if (skip == 1) {
											Intent intent = new Intent(Logon.this, MainActivity.class);
											intent.putExtra("personcenter", 4);
											intent.putExtra("bottom", 4);
											startActivity(intent);
										} else {
											Intent intent = new Intent(Logon.this, MainActivity.class);
											startActivity(intent);
										}
										finish();
									} else {
										Toast.makeText(Logon.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
									}

								}
								else {
									Toast.makeText(Logon.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(Logon.this, R.string.login_error_one, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(Logon.this, R.string.login_error_one, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("user_name", logonusername.getText().toString().trim());
				params.put("password", logonpsd.getText().toString().trim());
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


	void sendLocation(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.SAVE_LNG_LAT_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){

								}else {
									Toast.makeText(Logon.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(Logon.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
						}

					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

						Toast.makeText(Logon.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("user_name", getGson().fromJson(getSp().getString("user_name", ""), String.class));
				params.put("lng", String.valueOf(ThirdApplication.lon));
				params.put("lat", String.valueOf(ThirdApplication.lat));
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
			if (action.equals("reg_success")) {
				if (skip == 1) {
					Intent intent1 = new Intent(Logon.this,  MainActivity.class);
					intent1.putExtra("personcenter", 4);
					intent1.putExtra("bottom", 4);
					startActivity(intent1);
				}else {
					Intent intent2 = new Intent(Logon.this,  MainActivity.class);
					startActivity(intent2);
				}
				finish();
			}
		}
	};

	//注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("reg_success");
		//注册广播
		this.registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(mBroadcastReceiver);
	}

}
