package com.example.thirdapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.AnimateFirstDisplayListener;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.OrderMineData;
import com.example.thirdapp.data.OrderMineDataSingle;
import com.example.thirdapp.module.OrderMine;
import com.example.thirdapp.util.RelativeDateFormat;
import com.example.thirdapp.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/15.
 * 订单详情
 */
public class DetailOrderMngActivity extends BaseActivity implements View.OnClickListener {
    private OrderMine orderVo;//传递过来的值
    private ImageView back;
    private TextView order_status;

    //收货地址
    private TextView order_name;
    private TextView order_tel;
    private TextView order_location;
    //买家信息
    private ImageView item_head;
    private TextView item_nickname;

    //订单信息
    private TextView item_content;
    private TextView item_prices;
    private TextView item_money;

    //功能按钮
    private Button button_one;
    private Button button_two;

    private RelativeLayout relative_one;
    private String id;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
    private TextView order_dateline;//订单编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_order_activity);
        id = getIntent().getExtras().getString("orderVoId");
        initView();
        getdata();
    }

    private void initView() {
        back = (ImageView) this.findViewById(R.id.back);
        order_status = (TextView) this.findViewById(R.id.order_status);
        order_name = (TextView) this.findViewById(R.id.order_name);
        order_tel = (TextView) this.findViewById(R.id.order_tel);
        order_location = (TextView) this.findViewById(R.id.order_location);
        item_head = (ImageView) this.findViewById(R.id.item_head);
        item_nickname = (TextView) this.findViewById(R.id.item_nickname);
        item_content = (TextView) this.findViewById(R.id.item_content);
        item_prices = (TextView) this.findViewById(R.id.item_prices);
        item_money = (TextView) this.findViewById(R.id.item_money);
        button_one = (Button) this.findViewById(R.id.button_one);
        button_two = (Button) this.findViewById(R.id.button_two);
        relative_one = (RelativeLayout) this.findViewById(R.id.relative_one);
        order_dateline = (TextView) this.findViewById(R.id.order_dateline);

        button_one.setOnClickListener(this);
        button_two.setOnClickListener(this);
        item_head.setOnClickListener(this);
        item_nickname.setOnClickListener(this);
        back.setOnClickListener(this);
        relative_one.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.relative_one:
//                getGoodsByUUID();
                break;
            case R.id.item_head:{

            }
                break;
            case R.id.item_nickname:
            {
               

            }
                break;
            case R.id.button_one:
            {

            }
                break;
            case R.id.button_two:
            {

            }
                break;
        }
    }

    void initData(){
        StringBuilder datetime = new StringBuilder();
        datetime.append("订单编号:" + orderVo.getOrder_no());
        switch (Integer.parseInt(orderVo.getStatus())){
            //1生成订单,2支付订单,3取消订单,4作废订单,5完成订单'
            case 1:
                order_status.setText("生成订单");
                if(!StringUtil.isNullOrEmpty(orderVo.getCreate_time())){

                    if(!"任意".equals(orderVo.getAccept_time())){
                        datetime.append("\n" + "创建时间:" + RelativeDateFormat.format(Long.parseLong((orderVo.getAccept_time() == null ? "" : orderVo.getAccept_time()))));
                    } else {
                        datetime.append("\n" + "创建时间:"+orderVo.getCreate_time());
                    }

                }
                break;
            case 2:
                order_status.setText("支付订单");
                if(!"任意".equals(orderVo.getAccept_time())){
                    datetime.append("\n" + "创建时间:" + RelativeDateFormat.format(Long.parseLong((orderVo.getAccept_time() == null ? "" : orderVo.getAccept_time()))));
                } else {
                    datetime.append("\n" + "创建时间:"+orderVo.getCreate_time());
                }
                break;
            case 3:
                order_status.setText("订单已取消");
                break;
            case 4:
                order_status.setText("订单已作废");
                break;
            case 5:
                order_status.setText("订单已完成");
                if(!"任意".equals(orderVo.getAccept_time())){
                    datetime.append("\n" + "创建时间:" + RelativeDateFormat.format(Long.parseLong((orderVo.getCreate_time() == null ? "" : orderVo.getCreate_time())  + "000")));
                } else {
                    datetime.append("\n" + "创建时间:"+orderVo.getCreate_time());
                }
                if(!"任意".equals(orderVo.getPay_time())){
                    datetime.append("\n" + "付款时间:" + RelativeDateFormat.format(Long.parseLong((orderVo.getPay_time() == null ? "" : orderVo.getPay_time())  + "000")));
                } else {
                    datetime.append("\n" + "付款时间:"+orderVo.getPay_time());
                }
                if(!"任意".equals(orderVo.getSend_time())){
                    datetime.append("\n" + "发货时间:" + RelativeDateFormat.format(Long.parseLong((orderVo.getSend_time() == null ? "" : orderVo.getSend_time())  + "000")));
                } else {
                    datetime.append("\n" + "发货时间:"+orderVo.getSend_time());
                }
                if(!"任意".equals(orderVo.getAccept_time())){
                    datetime.append("\n" + "收货时间:" + RelativeDateFormat.format(Long.parseLong((orderVo.getAccept_time() == null ? "" : orderVo.getAccept_time())  + "000")));
                } else {
                    datetime.append("\n" + "收货时间:"+orderVo.getAccept_time());
                }
                if(!"任意".equals(orderVo.getCompletion_time())){
                    datetime.append("\n" + "完成时间:" + RelativeDateFormat.format(Long.parseLong((orderVo.getCompletion_time() == null ? "" : orderVo.getCompletion_time())  + "000")));
                } else {
                    datetime.append("\n" + "完成时间:"+orderVo.getCompletion_time());
                }
                break;
        }
//        imageLoader.displayImage(orderVo.getEmpCover(), item_head, UniversityApplication.txOptions, animateFirstListener);
//        imageLoader.displayImage(InternetURL.INTERNAL + orderVo.getImg(), item_pic, ThirdApplication.txOptions, animateFirstListener);
        item_nickname.setText(orderVo.getOrder_no());
        item_content.setText(orderVo.getAddress() + "-收货人：" +orderVo.getAccept_name() +"-电话："+orderVo.getMobile());
        item_prices.setText(getResources().getString(R.string.money) + orderVo.getPayable_amount());
//        item_count.setText(String.format(getResources().getString(R.string.item_count_adapter), orderVo.getGoods_nums()));
        item_money.setText(String.format(getResources().getString(R.string.item_money_adapter), Double.valueOf(orderVo.getReal_amount())));
        order_dateline.setText(datetime);

    }


    private void getdata() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.ORDER_DETAIL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            OrderMineDataSingle data = getGson().fromJson(s, OrderMineDataSingle.class);
                            if (data.getCode() == 200) {
                                orderVo = data.getData();
                                initData();
                            } else {
                                Toast.makeText(DetailOrderMngActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DetailOrderMngActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(DetailOrderMngActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", getGson().fromJson(getSp().getString("mobile", ""), String.class));
                params.put("order_id", id);
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
