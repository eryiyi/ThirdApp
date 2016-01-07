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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.StudyAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.StudyObjData;
import com.example.thirdapp.module.StudyObj;
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

public class Study extends BaseActivity implements OnRefreshListener, OnLoadListener,OnClickListener{
	List<StudyObj> list;
	MyListViewUpDown studylv;
	StudyAdapter adapter;
	ImageView back;
	private EditText auto;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.study);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		studylv = (MyListViewUpDown) findViewById(R.id.studylv);
		studylv.setonRefreshListener(this);
		studylv.setOnLoadListener(this);
		list = new ArrayList<StudyObj>();

		adapter = new StudyAdapter(this, list);
		studylv.setAdapter(adapter);
		studylv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(Study.this, StudyDetail.class);
				StudyObj studyObj = list.get(position-1);
				intent.putExtra("studyObj", studyObj);
				startActivity(intent);
			}
		});

		progressDialog = new CustomProgressDialog(Study.this , "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getData();

		this.findViewById(R.id.mang).setOnClickListener(this);
		auto = (EditText) this.findViewById(R.id.auto);
	}

	void getData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.STUDY_LIST_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									StudyObjData data = getGson().fromJson(s, StudyObjData.class);
									list.addAll(data.getData());
									adapter.notifyDataSetChanged();
									studylv.onLoadComplete();
									studylv.onRefreshComplete();
								}else {
									Toast.makeText(Study.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(Study.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(Study.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
				studylv.onLoadComplete();
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
				studylv.onRefreshComplete();
			};
		}.execute(null, null, null);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			Study.this.finish();
			break;
			case R.id.mang:
				//
				getDataKeyWord();
				break;

		default:
			break;
		}
	}

	void getDataKeyWord(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.STUDY_SEARCH_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									StudyObjData data = getGson().fromJson(s, StudyObjData.class);
									if(data.getData() != null && data.getData().size()>0){
										list.clear();
										list.addAll(data.getData());
										adapter.notifyDataSetChanged();
										studylv.onLoadComplete();
										studylv.onRefreshComplete();
									}else {
										showMsg(Study.this, "搜索完毕，暂无数据");
									}
								}else {
									Toast.makeText(Study.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(Study.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(Study.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("keyword", auto.getText().toString());
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
