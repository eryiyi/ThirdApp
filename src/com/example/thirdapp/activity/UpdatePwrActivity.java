package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.util.HttpUtils;
import com.example.thirdapp.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/19.
 */
public class UpdatePwrActivity extends BaseActivity implements View.OnClickListener {
    private EditText mobile;
    private EditText card;
    private EditText password_one;
    private EditText password_two;
    private Button sendCard;

    boolean isMobileNet, isWifiNet;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_pwr);
        res = getResources();
        this.findViewById(R.id.back).setOnClickListener(this);
        this.findViewById(R.id.getcode).setOnClickListener(this);
        mobile = (EditText) this.findViewById(R.id.mobile);
        card = (EditText) this.findViewById(R.id.card);
        password_one = (EditText) this.findViewById(R.id.password_one);
        password_two = (EditText) this.findViewById(R.id.password_two);
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
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.getcode:
                //下一步
                if(StringUtil.isNullOrEmpty(mobile.getText().toString())){
                    showMsg(UpdatePwrActivity.this , "请输入手机号码");
                     return;
                }
                if(StringUtil.isNullOrEmpty(card.getText().toString())){
                    showMsg(UpdatePwrActivity.this , "请输入验证码");
                    return;
                }if(StringUtil.isNullOrEmpty(password_one.getText().toString())){
                    showMsg(UpdatePwrActivity.this , "请输入密码");
                    return;
                }if(StringUtil.isNullOrEmpty(password_two.getText().toString())){
                    showMsg(UpdatePwrActivity.this , "请再次输入密码");
                    return;
                }
                if(!password_one.getText().toString().equals(password_two.getText().toString())){
                    showMsg(UpdatePwrActivity.this , "两次输入密码不一致");
                    return;
                }
                updatepwr();
                break;
            case R.id.sendCard:
                if(StringUtil.isNullOrEmpty(mobile.getText().toString())){
                    Toast.makeText(UpdatePwrActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendCard.setClickable(false);//不可点击
                MyTimer myTimer = new MyTimer(60000,1000);
                myTimer.start();
                getCard();
                break;
        }
    }


    void getCard(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.SEND_CODE_CHECK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                showMsg(UpdatePwrActivity.this , jo.getString("msg"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            showMsg(UpdatePwrActivity.this , "获得验证码失败");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        showMsg(UpdatePwrActivity.this, "获得验证码失败");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile.getText().toString());
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

    void updatepwr(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.UPDATE_PWR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code =  jo.getString("code");
                                if(Integer.parseInt(code) == 200){
                                    save("mobile", mobile.getText().toString());
                                    save("password", password_one.getText().toString());
                                    showMsg(UpdatePwrActivity.this , jo.getString("msg"));
                                    finish();
                                }else {
                                    showMsg(UpdatePwrActivity.this , jo.getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            showMsg(UpdatePwrActivity.this , "修改密码失败");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        showMsg(UpdatePwrActivity.this, "修改密码失败");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("access_token", getGson().fromJson(getSp().getString("access_token", ""), String.class));
                params.put("user_name", mobile.getText().toString());
                params.put("mobile", mobile.getText().toString());
                params.put("code", card.getText().toString());
                params.put("password", password_one.getText().toString());
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
