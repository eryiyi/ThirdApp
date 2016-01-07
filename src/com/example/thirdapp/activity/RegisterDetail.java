package com.example.thirdapp.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.UserObjData;
import com.example.thirdapp.module.Community;
import com.example.thirdapp.module.UserObj;
import com.example.thirdapp.util.HttpUtils;
import com.example.thirdapp.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterDetail extends BaseActivity implements OnClickListener{
	public static TextView choicexq;
	private static EditText inputusername;
	private static EditText password;
	private static EditText confirmpsd;
	private static EditText phonenumber;
	private static EditText verificationcode;
	TextView confirm;
	ImageView back;
	private int registercode;
	 Community community;
	UserObj user;
	private Button sendCard;

	boolean isMobileNet, isWifiNet;
	Resources res;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		res = getResources();
		setContentView(R.layout.registerdetail);
		choicexq = (TextView) findViewById(R.id.choicexq);
		password = (EditText) findViewById(R.id.password);
		confirmpsd = (EditText) findViewById(R.id.confirmpsd);
		phonenumber = (EditText) findViewById(R.id.phonenumber);
		verificationcode = (EditText) findViewById(R.id.verificationcode);
		inputusername = (EditText) findViewById(R.id.inputusername);
		confirm = (TextView) findViewById(R.id.confirm);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		confirm.setOnClickListener(this);
		choicexq.setOnClickListener(this);
		sendCard = (Button) this.findViewById(R.id.sendCard);
		sendCard.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		//点击获得验证码
		try {
			isMobileNet = HttpUtils.isMobileDataEnable(getApplicationContext());
			isWifiNet = HttpUtils.isWifiDataEnable(getApplicationContext());
			if (!isMobileNet && !isWifiNet) {
				Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		switch (v.getId()) {
			case R.id.sendCard:
				if(StringUtil.isNullOrEmpty(phonenumber.getText().toString())){
					Toast.makeText(RegisterDetail.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
					return;
				}
				sendCard.setClickable(false);//不可点击
				MyTimer myTimer = new MyTimer(60000,1000);
				myTimer.start();
				getCard();
				break;
			case R.id.choicexq:
				Intent selectAddressView = new Intent(RegisterDetail.this, XqTwo.class);
				startActivityForResult(selectAddressView, 0);
				break;
			case R.id.back:
				finish();
				break;
			case R.id.confirm:
				if(community == null){
					showMsg(RegisterDetail.this, "请选择小区");
					return;
				}
				if (phonenumber.getText().toString().length() != 11) {
					Toast.makeText(RegisterDetail.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
					return;
				}else if (inputusername.getText().toString().length() < 3) {
					Toast.makeText(RegisterDetail.this, "请输入正确的用户名", Toast.LENGTH_SHORT).show();
					return;
				}else if (verificationcode.getText().length() == 0) {
					Toast.makeText(RegisterDetail.this, "请输入验证码", Toast.LENGTH_SHORT).show();
					return;
				}else if (password.getText().length() == 0) {
					Toast.makeText(RegisterDetail.this, "密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				else if (!password.getText().toString().equals(confirmpsd.getText().toString())) {
					Toast.makeText(RegisterDetail.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
					return;
				}else {
					verCode();
				}
				break;
			default:
				break;
		}
	}

	void getCard(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.SEND_CODE_URL ,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									Toast.makeText(RegisterDetail.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}else {
									Toast.makeText(RegisterDetail.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(RegisterDetail.this, R.string.reg_error_eight, Toast.LENGTH_SHORT).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(RegisterDetail.this, R.string.reg_error_eight, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("mobile", phonenumber.getText().toString());
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

	void verCode(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.VERITY_CODE_URL ,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									confirmRegister();
								}else {
									Toast.makeText(RegisterDetail.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(RegisterDetail.this, R.string.reg_error_one, Toast.LENGTH_SHORT).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(RegisterDetail.this, R.string.reg_error_one, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("mobile", phonenumber.getText().toString());
				params.put("code", verificationcode.getText().toString());
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


	void confirmRegister(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.REG_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) ==200){
									UserObjData data = getGson().fromJson(s, UserObjData.class);
									if (data.getCode() == 200) {
										user = data.getData();
										save("access_token", user.getAccess_token());
										save("uid", user.getUid());
										save("community_id", user.getCommunity_id());
										save("user_name", user.getUser_name());
										save("mobile", user.getMobile());
										save("password", user.getPassword());
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
										Intent intent = new Intent("reg_success");
										sendBroadcast(intent);
										finish();
									}else {
										Toast.makeText(RegisterDetail.this, data.getMsg(), Toast.LENGTH_SHORT).show();
									}
								}
								else {
									Toast.makeText(RegisterDetail.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(RegisterDetail.this, R.string.login_error_one, Toast.LENGTH_SHORT).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(RegisterDetail.this, R.string.login_error_one, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("nick_name", inputusername.getText().toString());
				params.put("password", password.getText().toString());
				params.put("user_name", phonenumber.getText().toString());
				params.put("mobile", phonenumber.getText().toString());
				params.put("code", verificationcode.getText().toString());
				params.put("community_id", community.getCommunity_id());
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

	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);

		 if(resultCode == 100){
			 community = (Community) data.getExtras().get("community");
			 if(community != null){
				 choicexq.setText(community.getCommunity_name()==null?"":community.getCommunity_name());
			 }
		 }
	 }
	
//	public void confirmRegister(){
//		HttpParams params = new HttpParams();
//		params.put("community_id", ChoiceVillage.communityid);
//		params.put("user_name", inputusername.getText().toString());
//		params.put("mobile", phonenumber.getText().toString());
//		params.put("mobile_code", verificationcode.getText().toString());
//		params.put("password", password.getText().toString());
//		HttpClientUtils.getInstance().post(ServerId.serveradress2, ServerId.register, params, new AsyncHttpResponseHandler(){
//			@Override
//			public void onSuccess(JSONObject jsonObject) {
//				System.out.println("jsonObject = " + jsonObject);
//				registercode = jsonObject.optInt("code");
//				ServerId.user.uid = jsonObject.optInt("uid");
//				Message message = new Message();
//				message.what = 123;
//				handler.sendMessage(message);
//			}
//		});
//	}
	
//	Handler handler = new Handler(new Handler.Callback() {
//
//		@Override
//		public boolean handleMessage(Message msg) {
//			switch (msg.what) {
//			case 123:
//				if (registercode == 1) {
//					Toast.makeText(RegisterDetail.this, "注册成功,请登录", Toast.LENGTH_SHORT).show();
//					Intent intent = new Intent(RegisterDetail.this, Logon.class);
//					startActivity(intent);
//				}else {
//					Toast.makeText(RegisterDetail.this, "注册失败", Toast.LENGTH_SHORT).show();
//				}
//				break;
//			default:
//				break;
//			}
//			return false;
//		}
//	});
	
	@Override
	public void onResume() {
		super.onResume();
	}



	class MyTimer extends CountDownTimer {

		public MyTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			sendCard.setText(res.getString(R.string.daojishi_three));
			sendCard.setClickable(true);//可点击
		}

		@Override
		public void onTick(long millisUntilFinished) {
			sendCard.setText(res.getString(R.string.daojishi_one) + millisUntilFinished / 1000 + res.getString(R.string.daojishi_two));
		}
	}
}
