package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.AllOrderAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.OrdersDATA;
import com.example.thirdapp.module.Orders;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import com.example.thirdapp.view.MyListViewUpDown;
import com.example.thirdapp.view.MyListViewUpDown.OnLoadListener;
import com.example.thirdapp.view.MyListViewUpDown.OnRefreshListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllOrder extends BaseActivity implements OnRefreshListener, OnLoadListener{
	MyListViewUpDown allorderlv;
	List<Orders> list = new ArrayList<Orders>();
	AllOrderAdapter adapter;
	ImageView back;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.allorders);
		back = (ImageView) findViewById(R.id.back);
		allorderlv = (MyListViewUpDown) findViewById(R.id.allorderlv);
		allorderlv.setonRefreshListener(this);
		allorderlv.setOnLoadListener(this);
		adapter = new AllOrderAdapter(this, list);
		allorderlv.setAdapter(adapter);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AllOrder.this.finish();
			}
		});
		progressDialog = new CustomProgressDialog(AllOrder.this , "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getData();
	}
	
	@Override
	public void onLoad() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//上拉加载的内容
				return null;
			}

			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();
				allorderlv.onLoadComplete();
			};
		}.execute(null, null, null);
	}

	@Override
	public void onRefresh() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/*FruitBean bean = new FruitBean("下拉刷新测试");  
				list.add(bean);*/
				return null;
			}

			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();
				allorderlv.onRefreshComplete();
			};
		}.execute(null, null, null);
	}
	void getData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.ORDER_LISTS_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									OrdersDATA data = getGson().fromJson(s, OrdersDATA.class);
									list.addAll(data.getData());
									adapter.notifyDataSetChanged();
								}else {
									Toast.makeText(AllOrder.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(AllOrder.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(AllOrder.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
