package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.module.ShoppingAddress;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/12.
 */
public class MineAddressUpdateActivity extends BaseActivity implements View.OnClickListener {
    private ShoppingAddress goodsAddress;
    private EditText update_name;
    private EditText add_tel;
    private TextView add_address_one;
    private EditText add_address_two;
    private EditText add_youbian;
    private Button button_add_address;
    private ImageView back;
    private CheckBox checkbox;
    private String is_default = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_address_update_activity);
        goodsAddress = (ShoppingAddress) getIntent().getExtras().get("goodsAddress");
        is_default = goodsAddress.getnSelected();
        initView();
    }

    private void initView() {
        back = (ImageView) this.findViewById(R.id.back);
        update_name = (EditText) this.findViewById(R.id.update_name);
        add_tel = (EditText) this.findViewById(R.id.add_tel);
        add_address_one = (TextView) this.findViewById(R.id.add_address_one);
        add_address_two = (EditText) this.findViewById(R.id.add_address_two);
        add_youbian = (EditText) this.findViewById(R.id.add_youbian);
        button_add_address = (Button) this.findViewById(R.id.button_add_address);
        back.setOnClickListener(this);
        button_add_address.setOnClickListener(this);
        checkbox = (CheckBox) this.findViewById(R.id.checkbox);

        update_name.setText(goodsAddress.getsAcceptName());
        add_tel.setText(goodsAddress.getsTelephone());
        add_youbian.setText(goodsAddress.getsZip());
        add_address_one.setText(goodsAddress.getsAddress());
        add_address_two.setText(goodsAddress.getsStreet());
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    is_default = "1";
                } else {
                    is_default = "0";
                }
            }
        });
        if (is_default.equals("0")){
            //未选中
            checkbox.setChecked(false);
        }else{
            //选中
            checkbox.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.button_add_address:
                //修改地址
                if (StringUtil.isNullOrEmpty(update_name.getText().toString())) {
                    Toast.makeText(MineAddressUpdateActivity.this, R.string.add_address_error_one, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isNullOrEmpty(add_tel.getText().toString())) {
                    Toast.makeText(MineAddressUpdateActivity.this, R.string.add_address_error_two, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (StringUtil.isNullOrEmpty(add_youbian.getText().toString())) {
                    Toast.makeText(MineAddressUpdateActivity.this, R.string.add_address_error_four, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (StringUtil.isNullOrEmpty(add_address_two.getText().toString())) {
                    Toast.makeText(MineAddressUpdateActivity.this, R.string.add_address_error_three, Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog = new CustomProgressDialog(MineAddressUpdateActivity.this , "", R.anim.frame_paopao);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                saveAddress();
                break;
        }
    }
    //保存收货地址
    public void saveAddress(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.ADDRESS_SET_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code1 =  jo.getString("code");
                                if(Integer.parseInt(code1) == 200){
                                        //成功
                                        Intent intent = new Intent("update_address_success");
                                        sendBroadcast(intent);
                                        finish();
                                } else {
                                    Toast.makeText(MineAddressUpdateActivity.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(MineAddressUpdateActivity.this, R.string.caozuoshibai, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MineAddressUpdateActivity.this, R.string.caozuoshibai, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("access_token", getGson().fromJson(getSp().getString("access_token", ""), String.class));
                params.put("sMobile", getGson().fromJson(getSp().getString("mobile", ""), String.class));
                params.put("sAddress", add_address_one.getText().toString());
                params.put("sStreet", add_address_two.getText().toString());
                params.put("sZip", add_youbian.getText().toString());
                params.put("sAcceptName", update_name.getText().toString());
                params.put("sTelephone", add_tel.getText().toString());
                params.put("addressId", goodsAddress.getUid());

                if(checkbox.isChecked()){
                    params.put("nSelected", "1");
                }else {
                    params.put("nSelected", "0");
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



}
