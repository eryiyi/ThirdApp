package com.example.thirdapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.CategoryAdapter;
import com.example.thirdapp.adapter.HRAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.bean.CategoryBean;
import com.example.thirdapp.bean.HRBean;
import com.example.thirdapp.data.AdSlideData;
import com.example.thirdapp.data.HotGoodsObjData;
import com.example.thirdapp.data.TypeBigObjData;
import com.example.thirdapp.http.AsyncHttpResponseHandler;
import com.example.thirdapp.http.HttpClientUtils;
import com.example.thirdapp.http.HttpParams;
import com.example.thirdapp.module.TypeBigObj;
import com.example.thirdapp.module.TypeObj;
import com.example.thirdapp.serverid.ServerId;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.ContentListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComClass extends BaseActivity {
	List<TypeBigObj> lvlist;
	List<TypeObj> gvlsit;
	ListView listView;
	GridView gridView;
	HRAdapter lvadapter;
	CategoryAdapter gvadapter;
	ImageView back;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.comclass);
		back = (ImageView) findViewById(R.id.back);
		listView = (ListView) findViewById(R.id.hrlv);
		gridView = (GridView) findViewById(R.id.hrgv);
		lvlist = new ArrayList<TypeBigObj>();
		gvlsit = new ArrayList<TypeObj>();

		
		/*for (int i = 0; i < 12; i++) {
			CategoryBean bean = new CategoryBean(R.drawable.demo2, "测试名称");
			gvlsit.add(bean);
		}*/
		
		lvadapter = new HRAdapter(this, lvlist);
		gvadapter = new CategoryAdapter(this, gvlsit);
		listView.setAdapter(lvadapter);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ComClass.this.finish();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
//				((HRAdapter)parent.getAdapter()).setSelection(position);
				TypeBigObj typeBigObj = lvlist.get(position);
				if(typeBigObj != null){
					gvlsit.clear();
					if(typeBigObj.getType_data() != null){
						gvlsit.addAll(typeBigObj.getType_data());
					}
					gridView.setAdapter(gvadapter);
					gvadapter.notifyDataSetChanged();
				}
			}
		});
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				TypeObj typeObj = gvlsit.get(position);
				if (typeObj != null){
					//
					Intent typeView = new Intent(ComClass.this, TypeGoodsActivity.class);
					typeView.putExtra("typeId", typeObj.getType_id());
					startActivity(typeView);
				}
			}
		});
		loadData();
	}

	void loadData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.PRODUCT_TYPE_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									TypeBigObjData data = getGson().fromJson(s, TypeBigObjData.class);
									lvlist.clear();
									lvlist.addAll(data.getData());
									lvadapter.notifyDataSetChanged();
									if(lvlist != null && lvlist.size() > 0){
										if(lvlist.get(0).getType_data() != null){
											gvlsit.addAll(lvlist.get(0).getType_data());
											gridView.setAdapter(gvadapter);
											gvadapter.notifyDataSetChanged();
										}
									}
								}else {
									Toast.makeText(ComClass.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(ComClass.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(ComClass.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
//				params.put("community_id", getGson().fromJson(getSp().getString("community_id", ""), String.class));
				params.put("community_id", "1");
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


	
//	public void getListView(){
//		HttpParams params = new HttpParams();
//		HttpClientUtils.getInstance().get(ServerId.serveradress2, ServerId.getType, params, new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(JSONObject jsonObject) {
//						System.out.println("jsonObject = " + jsonObject);
//						JSONArray jsonArray = jsonObject.optJSONArray("data");
//						if (jsonArray != null) {
//							for (int i = 0; i < jsonArray.length(); i++) {
//								JSONObject object = jsonArray.optJSONObject(i);
//								CategoryBean bean = new CategoryBean();
//								bean.categoryimg = object.optString("type_icon");
//								bean.categoryname = object.optString("type_name");
//								bean.type_id = object.optInt("type_id");
//								gvlsit.add(bean);
//							}
//						}
//						Message message = new Message();
//						message.what = 123;
//						handler.sendMessage(message);
//					}
//		});
//	}
	
//	Handler handler = new Handler(new Handler.Callback() {
//
//		@Override
//		public boolean handleMessage(Message msg) {
//			switch (msg.what) {
//			case 123:
//				gridView.setAdapter(gvadapter);
//				break;
//			default:
//				break;
//			}
//			return false;
//		}
//	});


//	void loadData(final int currentid){
//		StringRequest request = new StringRequest(
//				Request.Method.POST,
//				InternetURL.PRODUCT_URL,
//				new Response.Listener<String>() {
//					@Override
//					public void onResponse(String s) {
//						if (StringUtil.isJson(s)) {
//							detail_lstv.onRefreshComplete();
//							detail_lstv.onLoadComplete();
//							try {
//								JSONObject jo = new JSONObject(s);
//								String code =  jo.getString("code");
//								if(Integer.parseInt(code) == 200){
//									HotGoodsObjData data = getGson().fromJson(s, HotGoodsObjData.class);
//									if (ContentListView.REFRESH == currentid) {
//										list2.clear();
//										list2.addAll(data.getData());
//										detail_lstv.setResultSize(data.getData().size());
//										adapter.notifyDataSetChanged();
//									}
//									if (ContentListView.LOAD == currentid) {
//										list2.addAll(data.getData());
//										detail_lstv.setResultSize(data.getData().size());
//										adapter.notifyDataSetChanged();
//									}
//								}else {
//									Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
//								}
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//						} else {
//							Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
//						}
//						if (progressDialog != null) {
//							progressDialog.dismiss();
//						}
//					}
//				},
//				new Response.ErrorListener() {
//					@Override
//					public void onErrorResponse(VolleyError volleyError) {
//						if (progressDialog != null) {
//							progressDialog.dismiss();
//						}
//						detail_lstv.onRefreshComplete();
//						detail_lstv.onLoadComplete();
//						Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
//					}
//				}
//		) {
//			@Override
//			protected Map<String, String> getParams() throws AuthFailureError {
//				Map<String, String> params = new HashMap<String, String>();
////				params.put("community_id", getGson().fromJson(getSp().getString("community_id", ""), String.class));
//				params.put("community_id", "1");
//				return params;
//			}
//
//			@Override
//			public Map<String, String> getHeaders() throws AuthFailureError {
//				Map<String, String> params = new HashMap<String, String>();
//				params.put("Content-Type", "application/x-www-form-urlencoded");
//				return params;
//			}
//		};
//		getRequestQueue().add(request);
//	}





}
