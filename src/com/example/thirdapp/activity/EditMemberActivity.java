package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.module.MemberObj;
import com.example.thirdapp.util.DateTimePickDialogUtil;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import com.example.thirdapp.view.SelectPhoWindow;
import com.example.thirdapp.view.SexPopWindow;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/27.
 */
public class EditMemberActivity extends BaseActivity implements View.OnClickListener {
    private MemberObj memberObj;

    private EditText nickname;
    private EditText age;
    private EditText home;
    private EditText email;
    private EditText geyan;
    private EditText weight;
    private EditText height;
    private TextView mine_sex;
    private TextView mine_birth;
    private String sex = "-1";

    private SexPopWindow sexPopWindow;
    private SelectPhoWindow deleteWindow;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        memberObj = (MemberObj) getIntent().getExtras().get("memberObj");


        this.findViewById(R.id.confirm).setOnClickListener(this);
        this.findViewById(R.id.mine_sex).setOnClickListener(this);
        nickname = (EditText) this.findViewById(R.id.nickname);
        age = (EditText) this.findViewById(R.id.age);
        home = (EditText) this.findViewById(R.id.home);
        email = (EditText) this.findViewById(R.id.email);
        geyan = (EditText) this.findViewById(R.id.geyan);
        weight = (EditText) this.findViewById(R.id.weight);
        height = (EditText) this.findViewById(R.id.height);
        mine_sex = (TextView) this.findViewById(R.id.mine_sex);
        mine_birth = (TextView) this.findViewById(R.id.mine_birth);


        nickname.setText(getGson().fromJson(getSp().getString("nick_name", ""), String.class));
        String timeD = getGson().fromJson(getSp().getString("birthday", ""), String.class);
        if(!StringUtil.isNullOrEmpty(timeD)){
            timeD = StringUtil.getDateTimeByMillisecond(timeD);
        }
        mine_birth.setText(timeD);
        mine_birth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = null;
                try {
                    dateTimePicKDialog = new DateTimePickDialogUtil(
                            EditMemberActivity.this,  StringUtil.getFrontBackStrDate(String.valueOf(mine_birth.getText().toString()), "yyyy-MM-dd",0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dateTimePicKDialog.dateTimePicKDialog(mine_birth);

            }
        });

        String sexstr = "";
        if("1".equals(getGson().fromJson(getSp().getString("sex", ""), String.class))){
            sexstr = "男";
        }
        if("2".equals(getGson().fromJson(getSp().getString("sex", ""), String.class))){
            sexstr = "女";
        }
        if("-1".equals(getGson().fromJson(getSp().getString("sex", ""), String.class))){
            sexstr = "保密";
        }
        sex = getGson().fromJson(getSp().getString("sex", ""), String.class);

        mine_sex.setText(sexstr);
        age.setText(getGson().fromJson(getSp().getString("age", ""), String.class));
        weight.setText(getGson().fromJson(getSp().getString("weight", ""), String.class));

        String str_geyan = getGson().fromJson(getSp().getString("geyan", ""), String.class);
        if(StringUtil.isNullOrEmpty(str_geyan)){
            geyan.setText("暂无格言");
        }else {
            geyan.setText(str_geyan);
        }
        String str_home = getGson().fromJson(getSp().getString("birthday_pplace", ""), String.class);
        if(StringUtil.isNullOrEmpty(str_home)){
            home.setText("暂无地址");
        }else {
            home.setText(str_home);
        }
        email.setText(getGson().fromJson(getSp().getString("email", ""), String.class));
        height.setText(getGson().fromJson(getSp().getString("height", ""), String.class));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                progressDialog = new CustomProgressDialog(EditMemberActivity.this , "", R.anim.frame_paopao);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                save();
                break;
            case R.id.mine_sex:
                //先隐藏键盘
                ShowSexDialog();
                break;
        }
    }

    public void back(View view){
        finish();
    }


    // 性别选择
    private void ShowSexDialog() {
        sexPopWindow = new SexPopWindow(EditMemberActivity.this, itemsOnClick);
        //显示窗口
        sexPopWindow.showAtLocation(EditMemberActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            sexPopWindow.dismiss();
            switch (v.getId()) {
                case R.id.sex_man: {
                    sex = "1";
                    mine_sex.setText("男");
                }
                break;
                case R.id.sex_woman: {
                    sex = "2";
                    mine_sex.setText("女");
                }
                break;
                default:
                    break;
            }
        }
    };


    void save (){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.UPDATE_MEMBER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code =  jo.getString("code");
                                if(Integer.parseInt(code) == 200){
                                    Toast.makeText(EditMemberActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                                    save("birthday", mine_birth.getText().toString());
                                    save("nick_name", nickname.getText().toString());
                                    save("age", age.getText().toString());
                                    save("birthday", mine_birth.getText().toString());
                                    save("birthday_place", home.getText().toString());
                                    save("email", email.getText().toString());
                                    save("geyan", geyan.getText().toString());
                                    save("height", height.getText().toString());
                                    save("weight", weight.getText().toString());
                                    if(!"1".equals(sex) && !"2".equals(sex) && !"-1".equals(sex)){
                                        save("sex", "-1");
                                    }else {
                                        save("sex", sex);
                                    }
                                    Intent intent = new Intent("updateSuccess");
                                    sendBroadcast(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(EditMemberActivity.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(EditMemberActivity.this, "获得数据失败", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", getGson().fromJson(getSp().getString("mobile", ""), String.class));
                params.put("nick_name" ,nickname.getText().toString() );
                params.put("age" , age.getText().toString());
                try {
                    params.put("birthday",  StringUtil.strToHaomiao(mine_birth.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                params.put("birthday_place" , home.getText().toString() );
                params.put("email" ,email.getText().toString() );
                params.put("geyan" ,geyan.getText().toString() );
                params.put("height" ,height.getText().toString() );
                params.put("weight" ,weight.getText().toString() );
                if(!"1".equals(sex) && !"2".equals(sex) && !"-1".equals(sex)){
                    params.put("sex" , "-1");
                }else {
                    params.put("sex", sex);
                }
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
