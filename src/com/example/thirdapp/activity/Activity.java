package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.ActivityAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.ActivityObjData;
import com.example.thirdapp.module.ActivityObj;
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

public class Activity extends BaseActivity implements OnRefreshListener, OnLoadListener,OnClickListener {
	MyListViewUpDown activitylv;
	ActivityAdapter adapter;
	List<ActivityObj> list;
	ImageView back;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.activity);
//		getActivityData();
		back = (ImageView) findViewById(R.id.back);
		activitylv = (MyListViewUpDown) findViewById(R.id.activitylv);
		list = new ArrayList<ActivityObj>();
		adapter = new ActivityAdapter(this, list);
		activitylv.setAdapter(adapter);
		activitylv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(Activity.this, ActivityDetail.class);
				ActivityObj activityObj = list.get(position - 1);
				intent.putExtra("activityObj", activityObj);
				startActivity(intent);
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Activity.this.finish();
			}
		});

		progressDialog = new CustomProgressDialog(Activity.this , "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getData();
	}

	void getData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.ACTIVE_LIST_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									ActivityObjData data = getGson().fromJson(s, ActivityObjData.class);
									list.addAll(data.getData());
									adapter.notifyDataSetChanged();
									activitylv.onLoadComplete();
									activitylv.onRefreshComplete();
								}else {
									Toast.makeText(Activity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(Activity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(Activity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
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
				activitylv.onLoadComplete();
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
				activitylv.onRefreshComplete();
			};
		}.execute(null, null, null);
	}
	
//	public void getActivityData(){
//		HttpParams params = new HttpParams();
//		params.put("community_id", 1);
//		HttpClientUtils.getInstance().get(ServerId.serveradress2, ServerId.getActivityList, params,
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(JSONObject jsonObject) {
//						System.out.println("jsonObject = " + jsonObject);
//						JSONArray jsonArray = jsonObject.optJSONArray("data");
//						if (jsonArray != null) {
//							for (int i = 0; i < jsonArray.length(); i++) {
//								JSONObject object = jsonArray.optJSONObject(i);
//								ActivityBean bean = new ActivityBean();
//								bean.actname = object.optString("title");
//								bean.actdescription = object.optString("club_name");
//								bean.acttime = object.optLong("create_time");
//								bean.pic0 = object.optString("pic0");
//								bean.pic1 = object.optString("pic1");
//								bean.pic2 = object.optString("pic2");
//								bean.pic3 = object.optString("pic3");
//								bean.info = object.optString("info");
//								bean.address = object.optString("");
//								list.add(bean);
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
//				activitylv.setAdapter(adapter);
//				break;
//			default:
//				break;
//			}
//			return false;
//		}
//	});

	@Override
	public void onClick(View v) {

	}
}
