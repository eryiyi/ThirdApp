package com.example.thirdapp.fragmentdetail;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.activity.Logon;
import com.example.thirdapp.adapter.OnClickContentItemListener;
import com.example.thirdapp.adapter.ValueAgainstAdapter;
import com.example.thirdapp.base.BaseFragment;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.bean.ValueAgainstBean;
import com.example.thirdapp.data.AdSlideData;
import com.example.thirdapp.data.ProductScoreObjData;
import com.example.thirdapp.module.ProductScoreObj;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.MyListViewUpDown;
import com.example.thirdapp.view.MyListViewUpDown.OnLoadListener;
import com.example.thirdapp.view.MyListViewUpDown.OnRefreshListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueAgainst extends BaseFragment implements  OnClickListener,OnClickContentItemListener{
	View view;
	ListView valueagainstlv;
	List<ProductScoreObj> list;
	ValueAgainstAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.valueagainst, null);
		valueagainstlv = (ListView) view.findViewById(R.id.valueagainstlv);
		list = new ArrayList<ProductScoreObj>();
		adapter = new ValueAgainstAdapter(getActivity(), list);

		valueagainstlv.setAdapter(adapter);
		adapter.setOnClickContentItemListener(this);
		getData();

		return view;
	}

	void getData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.SCORELIST_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									ProductScoreObjData data = getGson().fromJson(s, ProductScoreObjData.class);
									list.addAll(data.getData());
									adapter.notifyDataSetChanged();
								}else {
									Toast.makeText(getActivity(), jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("type", "0");
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



//	@Override
//	public void onLoad() {
//		new AsyncTask<Void, Void, Void>() {
//			@Override
//			protected Void doInBackground(Void... params) {
//				try {
//					Thread.sleep(800);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				//上拉加载的内容
//				return null;
//			}
//
//			protected void onPostExecute(Void result) {
//				adapter.notifyDataSetChanged();
//				valueagainstlv.onLoadComplete();
//			};
//		}.execute(null, null, null);
//	}
//
//	@Override
//	public void onRefresh() {
//		new AsyncTask<Void, Void, Void>() {
//			@Override
//			protected Void doInBackground(Void... params) {
//				try {
//					Thread.sleep(800);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				/*FruitBean bean = new FruitBean("下拉刷新测试");
//				list.add(bean);*/
//				return null;
//			}
//
//			protected void onPostExecute(Void result) {
//				adapter.notifyDataSetChanged();
//				valueagainstlv.onRefreshComplete();
//			};
//		}.execute(null, null, null);
//	}

	@Override
	public void onClick(View v) {
		
	}

	ProductScoreObj productScoreObj;
	@Override
	public void onClickContentItem(int position, int flag, Object object) {
		if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
			switch (flag){
				case 1:
					productScoreObj = list.get(position);
					duihuan();
					break;
			}
		}else {
			Intent intent = new Intent(getActivity(), Logon.class);
			intent.putExtra("skip", 1);
			startActivity(intent);
		}
	}

	void duihuan(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.EXCHANGE_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									Toast.makeText(getActivity(), "兑换成功", Toast.LENGTH_SHORT).show();
								}else {
									Toast.makeText(getActivity(), jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("product_id", productScoreObj.getProduct_id());
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
