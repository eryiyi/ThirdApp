package com.example.thirdapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.ItemMineOrderSjAdapter;
import com.example.thirdapp.adapter.OnClickContentItemListener;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.OrderMineData;
import com.example.thirdapp.module.OrderMine;
import com.example.thirdapp.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/15.
 * 我的订单--商家
 */
public class MineOrdersMngActivity extends BaseActivity implements View.OnClickListener,OnClickContentItemListener {
    private String order_no;

    private ListView classtype_lstv;//列表
    private int pageIndex = 1;
    private static boolean IS_REFRESH = true;
    private String emp_id = "";//当前登陆者UUID

    private ItemMineOrderSjAdapter adapter;
    private List<OrderMine> orderVos = new ArrayList<OrderMine>();
    private TextView text_one;
    private TextView text_two;
    private TextView text_three;
    private TextView text_four;
    private String status="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_order_activity);
        initView();
        initData();
    }

    private void initView() {
        classtype_lstv = (ListView) this.findViewById(R.id.lstv);
        adapter = new ItemMineOrderSjAdapter(orderVos,MineOrdersMngActivity.this);
        adapter.setOnClickContentItemListener(this);

        classtype_lstv.setAdapter(adapter);
        classtype_lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderMine orderVo = orderVos.get(position);
                Intent detailView = new Intent(MineOrdersMngActivity.this, DetailOrderMngActivity.class);
                detailView.putExtra("orderVoId", orderVo.getId());
                startActivity(detailView);
            }
        });

        text_one = (TextView) this.findViewById(R.id.text_one);
        text_two = (TextView) this.findViewById(R.id.text_two);
        text_three = (TextView) this.findViewById(R.id.text_three);
        text_four = (TextView) this.findViewById(R.id.text_four);
        text_one.setOnClickListener(this);
        text_two.setOnClickListener(this);
        text_three.setOnClickListener(this);
        text_four.setOnClickListener(this);
    }

    public void back(View view){
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text_one:
                text_one.setTextColor(getResources().getColor(R.color.button_color_orange_p));
                text_two.setTextColor(getResources().getColor(R.color.black_text_color));
                text_three.setTextColor(getResources().getColor(R.color.black_text_color));
                text_four.setTextColor(getResources().getColor(R.color.black_text_color));
                status = "";
                initData();
                break;
            case R.id.text_two:
                text_one.setTextColor(getResources().getColor(R.color.black_text_color));
                text_two.setTextColor(getResources().getColor(R.color.button_color_orange_p));
                text_three.setTextColor(getResources().getColor(R.color.black_text_color));
                text_four.setTextColor(getResources().getColor(R.color.black_text_color));
                status = "1";
                initData();
                break;
            case R.id.text_three:
                text_one.setTextColor(getResources().getColor(R.color.black_text_color));
                text_two.setTextColor(getResources().getColor(R.color.black_text_color));
                text_three.setTextColor(getResources().getColor(R.color.button_color_orange_p));
                text_four.setTextColor(getResources().getColor(R.color.black_text_color));
                status = "2";
                initData();
                break;
            case R.id.text_four:
                text_one.setTextColor(getResources().getColor(R.color.black_text_color));
                text_two.setTextColor(getResources().getColor(R.color.black_text_color));
                text_three.setTextColor(getResources().getColor(R.color.black_text_color));
                text_four.setTextColor(getResources().getColor(R.color.button_color_orange_p));
                status = "5";
                initData();
                break;
        }
    }


    OrderMine orderVoTmp;
    int tmpPosition;
    @Override
    public void onClickContentItem(int position, int flag, Object object) {
        orderVoTmp = orderVos.get(position);
        //1已发货  2 已收货 3取消  4 发送货物
        tmpPosition = position;
        switch (flag){
            case 1:
                break;
            case 2:
                break;
            case 3:

                break;
            case 4:
                break;
            case 5:
                break;
        }
    }
    //取订单
    private void initData() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.ORDER_LISTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            OrderMineData data = getGson().fromJson(s, OrderMineData.class);
                            if (data.getCode() == 200) {
                                if (IS_REFRESH) {
                                    orderVos.clear();
                                }
                                orderVos.addAll(data.getData());
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(MineOrdersMngActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MineOrdersMngActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MineOrdersMngActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", getGson().fromJson(getSp().getString("mobile", ""), String.class));
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
