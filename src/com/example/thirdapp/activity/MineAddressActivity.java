package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.ItemMineAddressAdapter;
import com.example.thirdapp.adapter.OnClickContentItemListener;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.ShoppingAddressDATA;
import com.example.thirdapp.module.ShoppingAddress;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import com.example.thirdapp.view.DeletePopWindow;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/25.
 * 我的收货地址列表
 */
public class MineAddressActivity extends BaseActivity implements View.OnClickListener ,OnClickContentItemListener{
    private ImageView back;
    private ImageView no_goods;
    private ListView lstv;
    private ItemMineAddressAdapter adapter;
    private List<ShoppingAddress> lists  = new ArrayList<ShoppingAddress>();
    private TextView add;

    private DeletePopWindow deleteWindow;

    ShoppingAddress shoppingAddressTmp;
    int tmpid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBoradcastReceiver();
        setContentView(R.layout.mine_address_activity);
        initView();
        Resources res = getBaseContext().getResources();
        String message = res.getString(R.string.please_wait).toString();
        progressDialog = new CustomProgressDialog(MineAddressActivity.this , "", R.anim.frame_paopao);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        getData();
    }

    // 选择是否删除
    private void showSelectImageDialog() {
        deleteWindow = new DeletePopWindow(MineAddressActivity.this, itemsOnClick);
        //显示窗口
        deleteWindow.showAtLocation(MineAddressActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            deleteWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_sure:
                    delete();
                    break;
                default:
                    break;
            }
        }
    };


    private void initView() {
        back = (ImageView) this.findViewById(R.id.back);
        no_goods = (ImageView) this.findViewById(R.id.no_goods);
        no_goods.setVisibility(View.GONE);
        lstv = (ListView) this.findViewById(R.id.lstv);
        back.setOnClickListener(this);
        adapter = new ItemMineAddressAdapter(lists, MineAddressActivity.this);
        lstv.setAdapter(adapter);
        adapter.setOnClickContentItemListener(this);
        add = (TextView) this.findViewById(R.id.add);
        add.setOnClickListener(this);
        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingAddress goodsAddress = lists.get(i);
                Intent updateAddressView = new Intent(MineAddressActivity.this, MineAddressUpdateActivity.class);
                updateAddressView.putExtra("goodsAddress", goodsAddress);
                startActivity(updateAddressView);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.add:
                //添加收货地址
                Intent  intentAddressAdd = new Intent(MineAddressActivity.this, MineAddressAddActivity.class);
                startActivity(intentAddressAdd);
                break;
        }
    }
    void getData(){
        //获得收货地址列表
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.ADDRESS_LISTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code1 =  jo.getString("code");
                                if(Integer.parseInt(code1) == 200){
                                    ShoppingAddressDATA data = getGson().fromJson(s, ShoppingAddressDATA.class);
                                        lists.clear();
                                        lists.addAll(data.getData());
                                        adapter.notifyDataSetChanged();
                                        if(lists.size() == 0){
                                            no_goods.setVisibility(View.VISIBLE);
                                            lstv.setVisibility(View.GONE);
                                        }else {
                                            no_goods.setVisibility(View.GONE);
                                            lstv.setVisibility(View.VISIBLE);
                                        }
                                }else {
                                    Toast.makeText(MineAddressActivity.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        } else {
                            Toast.makeText(MineAddressActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MineAddressActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("access_token", getGson().fromJson(getSp().getString("access_token", ""), String.class));
                params.put("sMobile", getGson().fromJson(getSp().getString("mobile", ""), String.class));
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
            if (action.equals("address_success")) {
                progressDialog = new CustomProgressDialog(MineAddressActivity.this , "", R.anim.frame_paopao);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                getData();
            }if (action.equals("update_address_success")) {
                progressDialog = new CustomProgressDialog(MineAddressActivity.this , "", R.anim.frame_paopao);
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
        myIntentFilter.addAction("address_success");//设置默认收货地址成功
        myIntentFilter.addAction("update_address_success");//更新收货地址成功
        //注册广播
        this.registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onClickContentItem(int position, int flag, Object object) {
        switch (flag){
            case 1:
                tmpid = position;
                shoppingAddressTmp = lists.get(position);
                showSelectImageDialog();
                break;
        }
    }

    //删除方法
    private void delete() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.ADDRESS_DEL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code1 = jo.getString("code");
                                if (Integer.parseInt(code1) == 200) {
                                    Toast.makeText(MineAddressActivity.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
                                    lists.remove(tmpid);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MineAddressActivity.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(MineAddressActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MineAddressActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("recordId", getGson().fromJson(getSp().getString("access_token", ""), String.class));
                params.put("sMobile", getGson().fromJson(getSp().getString("mobile", ""), String.class));
                params.put("addressId", shoppingAddressTmp.getUid());
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
