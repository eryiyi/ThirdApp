package com.example.thirdapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.MemberObjData;
import com.example.thirdapp.http.AsyncHttpResponseHandler;
import com.example.thirdapp.http.HttpClientUtils;
import com.example.thirdapp.http.HttpParams;
import com.example.thirdapp.serverid.ServerId;
import com.example.thirdapp.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedBack extends BaseActivity implements OnClickListener {
	ImageView back;
	EditText productadvice;
	TextView confirm;
	View view;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.feedback);
		back = (ImageView) findViewById(R.id.back);
		productadvice = (EditText) findViewById(R.id.productadvice);
		confirm = (TextView) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.confirm:
			if (!"1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
				Toast.makeText(FeedBack.this, "请登录后再提交", Toast.LENGTH_SHORT).show();
			}else {
				if (StringUtil.isNullOrEmpty(productadvice.getText().toString())) {
					Toast.makeText(FeedBack.this, "输入内容不符要求", Toast.LENGTH_SHORT).show();
					return;
				}
				addMessage();
			}
			break;
		}
	}

	void addMessage(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.FEED_BACK_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 1){
									showMsg(FeedBack.this, jo.getString("msg"));
									finish();
								}else {
									showMsg(FeedBack.this, jo.getString("msg"));
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							showMsg(FeedBack.this, "提交失败");
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
						showMsg(FeedBack.this, "提交失败");
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("user_name", getGson().fromJson(getSp().getString("mobile", ""), String.class));
				params.put("content", productadvice.getText().toString());
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
	

	public void backgroundAlpha(float bgAlpha) {

		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getWindow().setAttributes(lp);
	}

	public class poponDismissListener implements PopupWindow.OnDismissListener {

		@Override
		public void onDismiss() {
			backgroundAlpha(1f);
		}

	}

}
