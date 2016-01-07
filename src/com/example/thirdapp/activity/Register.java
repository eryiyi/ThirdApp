package com.example.thirdapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.http.AsyncHttpResponseHandler;
import com.example.thirdapp.http.HttpClientUtils;
import com.example.thirdapp.http.HttpParams;
import com.example.thirdapp.serverid.CountDownButtonHelper;
import com.example.thirdapp.serverid.CountDownButtonHelper.OnFinishListener;
import com.example.thirdapp.serverid.ServerId;
import org.json.JSONObject;

public class Register extends BaseActivity implements OnClickListener{
	ImageView back;
	private static EditText phonenumber;
	private static Button getcode;
	private static int code;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.register);
		phonenumber = (EditText) findViewById(R.id.phonenumber);
		getcode = (Button) findViewById(R.id.getcode);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		getcode.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.getcode:
			if (phonenumber.getText().length() == 11) {
				if (phonenumber.getText().toString().equals("")){
					
				}else{
					CountDownButtonHelper helper = new CountDownButtonHelper(getcode, "重新发送", 60, 1);
					helper.setOnFinishListener(new OnFinishListener() {
						
						@Override
						public void finish() {
							//Toast.makeText(Register.this, "倒计时结束",Toast.LENGTH_SHORT).show();
							getcode.setText(R.string.sendnum);
							getcode.setBackgroundDrawable(getResources().getDrawable(R.drawable.layout_selectorred));
						}
					});
					getPhoneCode();
					helper.start(Register.this);
				}
			}else{
				Toast.makeText(Register.this, "请输入正确的手机号码！", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}
	
	public void getPhoneCode(){
		HttpParams params = new HttpParams();
		params.put("mobile", phonenumber.getText().toString());
		HttpClientUtils.getInstance().get(ServerId.serveradress2, ServerId.sendCode, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonObject) {
				System.out.println("jsonObject = " + jsonObject);
				code = jsonObject.optInt("code");
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		});
	}
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (code == -3) {
					Toast.makeText(Register.this, "该手机号码已经注册，请直接登陆", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(Register.this, Logon.class);
					intent.putExtra("accountnumber", phonenumber.getText().toString());
					startActivity(intent);
				}else if (code == 1) {
					Toast.makeText(Register.this, "获取验证码成功，请完善信息", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(Register.this, RegisterDetail.class);
					startActivity(intent);
				}
				break;
			default:
				break;
			}
			return false;
		}
	});
}
