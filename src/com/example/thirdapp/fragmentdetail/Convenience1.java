package com.example.thirdapp.fragmentdetail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.activity.Convenience;
import com.example.thirdapp.activity.PropertyDetail;
import com.example.thirdapp.adapter.HouseInfoAdapter;
import com.example.thirdapp.adapter.PopupTextAdapter;
import com.example.thirdapp.base.BaseFragment;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.bean.PopupTextlBean;
import com.example.thirdapp.data.HouseInfoObjData;
import com.example.thirdapp.module.HouseInfoObj;
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

public class Convenience1 extends BaseFragment implements OnClickListener,OnRefreshListener, OnLoadListener{
	View view;
	View view2;
	View line;
	ImageView image1;
	ImageView image2;
	ImageView image3;
	ImageView image4;
	MyListViewUpDown listViewUpDown;
	List<HouseInfoObj> list;
	HouseInfoAdapter adapter;
	LinearLayout allcq;
	LinearLayout rent;
	LinearLayout livingroom;
	LinearLayout source;
	private static int ll;

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view =inflater.inflate(R.layout.convenience1, null);
		listViewUpDown = (MyListViewUpDown) view.findViewById(R.id.con1lv);
		listViewUpDown.setonRefreshListener(this);
		listViewUpDown.setOnLoadListener(this);
		listViewUpDown.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(getActivity(), PropertyDetail.class);
				HouseInfoObj houseInfoObj = list.get(position-1);
				intent.putExtra("houseInfoObj", houseInfoObj);
				startActivity(intent);
			}
		});
		image1 = (ImageView) view.findViewById(R.id.image1);
		image2 = (ImageView) view.findViewById(R.id.image2);
		image3 = (ImageView) view.findViewById(R.id.image3);
		image4 = (ImageView) view.findViewById(R.id.image4);
		line = view.findViewById(R.id.line);
		allcq = (LinearLayout) view.findViewById(R.id.allcq);
		rent = (LinearLayout) view.findViewById(R.id.rent);
		livingroom = (LinearLayout) view.findViewById(R.id.livingroom);
		source = (LinearLayout) view.findViewById(R.id.source);
		//取屏幕的宽
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		ll = screenW/4;
		allcq.setOnClickListener(this);
		rent.setOnClickListener(this);
		livingroom.setOnClickListener(this);
		source.setOnClickListener(this);
		
		list = new ArrayList<HouseInfoObj>();
		adapter = new HouseInfoAdapter(getActivity(), list);
		listViewUpDown.setAdapter(adapter);

		progressDialog = new CustomProgressDialog(getActivity() , "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getData();
		return view;
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
				listViewUpDown.onLoadComplete();
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
				listViewUpDown.onRefreshComplete();
			};
		}.execute(null, null, null);
	}
	
	public void showPopupWindow(View imageView, int posX){
		final PopupWindow popupWindow = new PopupWindow(getActivity());
		popupWindow.setWindowLayoutMode(ll, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);//设置可点击
		popupWindow.setFocusable(true); //可聚焦
		popupWindow.setOutsideTouchable(true);//设置外部可点击
		ColorDrawable colorDrawable = new ColorDrawable(0xffffffff);
		popupWindow.setBackgroundDrawable(colorDrawable); //设置背景
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		view2 = inflater.inflate(R.layout.popupitem, null);
		ListView popuplv = (ListView) view2.findViewById(R.id.popuplv);
		List<PopupTextlBean> list = new ArrayList<PopupTextlBean>();
		if(posX == ll*1){
			PopupTextlBean bean1 = new PopupTextlBean("不限");
			list.add(bean1);
			PopupTextlBean bean2 = new PopupTextlBean("500以下");
			list.add(bean2);
			PopupTextlBean bean3 = new PopupTextlBean("500-1000");
			list.add(bean3);
			PopupTextlBean bean4 = new PopupTextlBean("1000-1500");
			list.add(bean4);
			PopupTextlBean bean5 = new PopupTextlBean("1500-2000");
			list.add(bean5);
			PopupTextlBean bean6 = new PopupTextlBean("2000-2500");
			list.add(bean6);
			PopupTextlBean bean7 = new PopupTextlBean("2500-3000");
			list.add(bean7);
			PopupTextlBean bean8 = new PopupTextlBean("3000以上");
			list.add(bean8);
		}
		if(posX == ll*2){
			PopupTextlBean bean1 = new PopupTextlBean("不限");
			list.add(bean1);
			PopupTextlBean bean2 = new PopupTextlBean("主卧");
			list.add(bean2);
			PopupTextlBean bean3 = new PopupTextlBean("次卧");
			list.add(bean3);
			PopupTextlBean bean4 = new PopupTextlBean("隔断");
			list.add(bean4);
		}
		if(posX ==  ll*3){
			PopupTextlBean bean1 = new PopupTextlBean("不限");
			list.add(bean1);
			PopupTextlBean bean2 = new PopupTextlBean("个人");
			list.add(bean2);
			PopupTextlBean bean3 = new PopupTextlBean("经纪人");
			list.add(bean3);
			PopupTextlBean bean4 = new PopupTextlBean("诚信房源");
			list.add(bean4);
		}
		PopupTextAdapter adapter = new PopupTextAdapter(getActivity(), list);
		popuplv.setAdapter(adapter);
		popuplv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				popupWindow.dismiss();
			}
		});
		popupWindow.setAnimationStyle(R.style.AnimationPreview);
		//int w = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		popupWindow.setContentView(view2);
		popupWindow.setWidth(ll);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);  
		popupWindow.showAsDropDown(imageView, posX, 0);
		popupWindow.setOnDismissListener(new poponDismissListener());
	}

	public void backgroundAlpha(float bgAlpha) {

		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getActivity().getWindow().setAttributes(lp);
	}

	public class poponDismissListener implements PopupWindow.OnDismissListener {

		@Override
		public void onDismiss() {
			backgroundAlpha(1f);
			image1.setImageResource(R.drawable.allcq);
			image2.setImageResource(R.drawable.rent);
			image3.setImageResource(R.drawable.livingroom);
			image4.setImageResource(R.drawable.source);
		}

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.allcq:
			showPopupWindow(line, 0);
			image1.setImageResource(R.drawable.allcqb);
			break;
		case R.id.rent:
			showPopupWindow(line,  ll*1);
			image2.setImageResource(R.drawable.rentb);
			break;
		case R.id.livingroom:
			showPopupWindow(line,  ll*2);
			image3.setImageResource(R.drawable.livingroomb);
			break;
		case R.id.source:
			showPopupWindow(line,  ll*3);
			image4.setImageResource(R.drawable.sourceb);
			break;
		default:
			break;
		}
	}

	void getData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.BIANMIN_LISTS_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									HouseInfoObjData data = getGson().fromJson(s, HouseInfoObjData.class);
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
						listViewUpDown.onRefreshComplete();
						listViewUpDown.onLoadComplete();
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
				params.put("room_cat", Convenience.typeid);
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
