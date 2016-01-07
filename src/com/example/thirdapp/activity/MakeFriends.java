package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.adapter.AnimateFirstDisplayListener;
import com.example.thirdapp.adapter.MfAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.FriendObjData;
import com.example.thirdapp.module.FriendObj;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import com.example.thirdapp.view.MyGridview;
import com.example.thirdapp.view.PullToRefreshView;
import com.example.thirdapp.view.PullToRefreshView.OnFooterRefreshListener;
import com.example.thirdapp.view.PullToRefreshView.OnHeaderRefreshListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

//交友
public class MakeFriends extends BaseActivity implements OnHeaderRefreshListener,OnFooterRefreshListener,OnClickListener{
	MyGridview mfgv;
	private PullToRefreshView mfpullrefresh;
	List<FriendObj> list;
	MfAdapter adapter;
	ImageView back;
	private EditText auto;
	private LinearLayout liner_top;
	List<FriendObj> listThree = new ArrayList<FriendObj>();

	//前三个
	private ImageView image1;
	private ImageView image2;
	private ImageView image3;
	private TextView name;
	private TextView name2;
	private TextView name3;
	private TextView age;
	private TextView height;
	private TextView weight;

	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.makefrriends);
		initView();
		back = (ImageView) findViewById(R.id.back);
		liner_top = (LinearLayout) this.findViewById(R.id.liner_top);//顶部区域
		mfgv = (MyGridview) findViewById(R.id.mfgv);
		mfpullrefresh = (PullToRefreshView) findViewById(R.id.mfpullrefresh);
		list = new ArrayList<FriendObj>();
		adapter = new MfAdapter(this, list);
		back.setOnClickListener(this);
		mfgv.setAdapter(adapter);
		mfpullrefresh.setOnHeaderRefreshListener(this);
		mfpullrefresh.setOnFooterRefreshListener(this);
		mfgv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(MakeFriends.this, MfDetail.class);
				FriendObj friendObj = list.get(position);
				intent.putExtra("friendObj",friendObj );
				startActivity(intent);
			}
		});

		progressDialog = new CustomProgressDialog(MakeFriends.this , "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getData();

		getThreeData();

		this.findViewById(R.id.mang).setOnClickListener(this);
		auto = (EditText) this.findViewById(R.id.auto);
	}

	private void initView() {
		image1 = (ImageView) this.findViewById(R.id.image1);
		image2 = (ImageView) this.findViewById(R.id.image2);
		image3 = (ImageView) this.findViewById(R.id.image3);

		name = (TextView) this.findViewById(R.id.name);
		name2 = (TextView) this.findViewById(R.id.name2);
		name3 = (TextView) this.findViewById(R.id.name3);
		age = (TextView) this.findViewById(R.id.age);
		height = (TextView) this.findViewById(R.id.height);
		weight = (TextView) this.findViewById(R.id.weight);

		image1.setOnClickListener(this);
		image2.setOnClickListener(this);
		image3.setOnClickListener(this);
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mfpullrefresh.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mfpullrefresh.onFooterRefreshComplete();
				liner_top.setVisibility(View.VISIBLE);
			}
		}, 1000);
	}
	
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mfpullrefresh.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mfpullrefresh.onHeaderRefreshComplete();
				mfpullrefresh.setLastUpdated("最近更新:" + new Date().toLocaleString());
				liner_top.setVisibility(View.VISIBLE);
			}
		}, 1000);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
			case R.id.mang:
				//搜索
				getSearch();
				break;
			case R.id.image1:
			{
				//第一个
				if(listThree != null && listThree.size() == 3){
					Intent intent = new Intent(MakeFriends.this, MfDetail.class);
					FriendObj friendObj = listThree.get(0);
					intent.putExtra("friendObj",friendObj );
					startActivity(intent);
				}
			}
				break;
			case R.id.image2:
			{
				//第二个
				if(listThree != null && listThree.size() == 3){
					Intent intent = new Intent(MakeFriends.this, MfDetail.class);
					FriendObj friendObj = listThree.get(1);
					intent.putExtra("friendObj",friendObj );
					startActivity(intent);
				}
			}
				break;
			case R.id.image3:
			{
				//第三个
				if(listThree != null && listThree.size() == 3){
					Intent intent = new Intent(MakeFriends.this, MfDetail.class);
					FriendObj friendObj = listThree.get(2);
					intent.putExtra("friendObj",friendObj );
					startActivity(intent);
				}
			}
				break;
		default:
			break;
		}
	}

	//获得数据
	void getData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.FRIENDS_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									FriendObjData data = getGson().fromJson(s, FriendObjData.class);
									list.addAll(data.getData());
									adapter.notifyDataSetChanged();
								}else {
									Toast.makeText(MakeFriends.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(MakeFriends.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(MakeFriends.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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


	//搜索
	void getSearch(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.FRIENDS_SEARCH_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									FriendObjData data = getGson().fromJson(s, FriendObjData.class);
									list.clear();
									liner_top.setVisibility(View.GONE);
									list.addAll(data.getData());
									adapter.notifyDataSetChanged();
								}else {
									Toast.makeText(MakeFriends.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(MakeFriends.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(MakeFriends.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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


	//取前三个
	void getThreeData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.FRIEND_POPULAR_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									FriendObjData data = getGson().fromJson(s, FriendObjData.class);
									listThree.clear();
									listThree.addAll(data.getData());
									initThree();
								}else {
									Toast.makeText(MakeFriends.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(MakeFriends.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(MakeFriends.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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

	void initThree(){
		//实例化前三个
		if(listThree != null && listThree.size() >= 3){
			FriendObj friendObj1 = listThree.get(0);
			FriendObj friendObj2 = listThree.get(1);
			FriendObj friendObj3 = listThree.get(2);
			imageLoader.displayImage(InternetURL.INTERNAL_PIC + friendObj1.getCover(), image1, ThirdApplication.txOptions, animateFirstListener);
			imageLoader.displayImage(InternetURL.INTERNAL_PIC + friendObj2.getCover(), image2, ThirdApplication.txOptions, animateFirstListener);
			imageLoader.displayImage(InternetURL.INTERNAL_PIC + friendObj3.getCover(), image3, ThirdApplication.txOptions, animateFirstListener);
			name.setText(friendObj1.getNick_name() == null ? "" : friendObj1.getNick_name());
			name2.setText(friendObj2.getNick_name() == null ? "" : friendObj2.getNick_name());
			name3.setText(friendObj3.getNick_name()==null?"":friendObj3.getNick_name());
			age.setText(friendObj1.getAge()==null?"保密":(friendObj1.getAge()+"岁"));
			height.setText(friendObj1.getHeight()==null?"保密":(friendObj1.getHeight()+"cm"));
			weight.setText(friendObj1.getWeight()==null?"保密":friendObj1.getWeight());
		}
	}

}
