package com.example.thirdapp.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener,Runnable,AMapLocationListener {
    private UserObj user;
    private LocationManagerProxy mLocationManagerProxy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        save("isLogin", "0");
        Boolean flag = StringUtil.isOPen(WelcomeActivity.this);
        if(!flag){
            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
        }
        init();
        // 启动一个线程
        new Thread(WelcomeActivity.this).start();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void run() {
        try {
            // 3秒后跳转到登录界面
            Thread.sleep(3000);
            if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("password", ""), String.class)) && !StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("mobile", ""), String.class))){
                loginData();
            }else {
                if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("community_id", ""), String.class))){
                   //如果选择了小区
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(WelcomeActivity.this, SelectCommunityTwoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
                                if(Integer.parseInt(code) == 1){
                                    UserObjData data = getGson().fromJson(s, UserObjData.class);
                                    if (data.getCode() == 1) {
                                        user = data.getData();
                                        save("access_token", user.getAccess_token());
                                        save("uid", user.getUid());
                                        save("community_id", user.getCommunity_id());
                                        save("user_name", user.getUser_name());
                                        save("mobile", user.getMobile());
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
                                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("community_id", ""), String.class))){
                                            //如果选择了小区
                                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Intent intent = new Intent(WelcomeActivity.this, SelectCommunityActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }else{
                                    if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("community_id", ""), String.class))){
                                        //如果选择了小区
                                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Intent intent = new Intent(WelcomeActivity.this, SelectCommunityActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("community_id", ""), String.class))){
                                //如果选择了小区
                                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Intent intent = new Intent(WelcomeActivity.this, SelectCommunityActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("community_id", ""), String.class))){
                            //如果选择了小区
                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(WelcomeActivity.this, SelectCommunityActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", getGson().fromJson(getSp().getString("mobile", ""), String.class));
                params.put("password", getGson().fromJson(getSp().getString("password", ""), String.class));
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



    /**
     * 初始化定位
     */
    private void init() {
        // 初始化定位，只采用网络定位
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.setGpsEnable(false);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用destroy()方法
        // 其中如果间隔时间为-1，则定位只定一次,
        // 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 60 * 10000, 15, this);

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        if (amapLocation != null
                && amapLocation.getAMapException().getErrorCode() == 0) {
            ThirdApplication.lat = amapLocation.getLatitude();
            ThirdApplication.lon = amapLocation.getLongitude();
            ThirdApplication.cityName =amapLocation.getCity();
            ThirdApplication.desc =amapLocation.getAddress();
        } else {
            Log.e("AmapErr", "Location ERR:" + amapLocation.getAMapException().getErrorCode());
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        // 移除定位请求
        if(mLocationManagerProxy != null){
            mLocationManagerProxy.removeUpdates(this);
            // 销毁定位
            mLocationManagerProxy.destroy();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
