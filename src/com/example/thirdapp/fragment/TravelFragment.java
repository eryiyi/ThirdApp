package com.example.thirdapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.activity.Logon;
import com.example.thirdapp.activity.TravelDetail;
import com.example.thirdapp.adapter.OnClickContentItemListener;
import com.example.thirdapp.adapter.TravelAdapter;
import com.example.thirdapp.base.BaseFragment;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.TravelObjData;
import com.example.thirdapp.module.TravelObj;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelFragment extends BaseFragment implements OnClickContentItemListener {
	View view;
	List<TravelObj> list = new ArrayList<TravelObj>();
	TravelAdapter adapter;
	ListView listView;

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.travelfragment, null);

		listView = (ListView) view.findViewById(R.id.travellv);
		adapter = new TravelAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(getActivity(), TravelDetail.class);
				intent.putExtra("travel", list.get(position));
				startActivity(intent);
			}
		});
		adapter.setOnClickContentItemListener(this);
		progressDialog = new CustomProgressDialog(getActivity() , "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getTravelData();
		return view;
	}
	
	public void getTravelData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.TRIP_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									TravelObjData data = getGson().fromJson(s, TravelObjData.class);
									list.addAll(data.getData());
									adapter.notifyDataSetChanged();
								}else {
									Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
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

	TravelObj travelObjTmp ;
	int tmpPosition;
	@Override
	public void onClickContentItem(int position, int flag, Object object) {
		if (!"1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
			Intent intent = new Intent(getActivity(), Logon.class);
			intent.putExtra("skip", 1);
			startActivity(intent);
		}else {
			travelObjTmp = list.get(position);
			tmpPosition = position;
			switch (flag){
				case 1:
					//赞
					addZan();
					break;
				case 2:
					//收藏
					addFavour();
					break;
			}
		}
	}

	void addZan(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.SUPPORT_TRIP_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code1 =  jo.getString("code");
								if(Integer.parseInt(code1) == 200){
									Toast.makeText(getActivity(), "点赞成功", Toast.LENGTH_SHORT).show();
									list.get(tmpPosition).setSupport_num(String.valueOf(Integer.parseInt((list.get(tmpPosition).getSupport_num()==null?"0":list.get(tmpPosition).getSupport_num()))+1));
									adapter.notifyDataSetChanged();
								}else {
									Toast.makeText(getActivity(), jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							}catch (Exception e){
								e.printStackTrace();
							}

						} else {
							Toast.makeText(getActivity(), "点赞失败", Toast.LENGTH_SHORT).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(getActivity(), "点赞失败", Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("trip_id", travelObjTmp.getId());
				params.put("user_name", getGson().fromJson(getSp().getString("user_name", ""), String.class));
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


	void addFavour(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.COLLECT_TRIP_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code1 =  jo.getString("code");
								if(Integer.parseInt(code1) == 200){
									Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
									list.get(tmpPosition).setCollect_num(String.valueOf(Integer.parseInt((list.get(tmpPosition).getCollect_num() == null ? "0" : list.get(tmpPosition).getCollect_num())) + 1));
									adapter.notifyDataSetChanged();
								}else {
									Toast.makeText(getActivity(), jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							}catch (Exception e){
								e.printStackTrace();
							}

						} else {
							Toast.makeText(getActivity(), "收藏失败", Toast.LENGTH_SHORT).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(getActivity(), "收藏失败", Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("trip_id", travelObjTmp.getId());
				params.put("user_name", getGson().fromJson(getSp().getString("user_name", ""), String.class));
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

