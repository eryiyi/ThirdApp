package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.MainActivity;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.adapter.ItemXiaoquAdapter;
import com.example.thirdapp.adapter.PopupTextAdapter2;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.bean.PopupTextlBean;
import com.example.thirdapp.data.CommunityData;
import com.example.thirdapp.module.Community;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCommunityTwoActivity extends BaseActivity implements OnClickListener{
	ListView villagelv;
	RelativeLayout choicestreet;
	RelativeLayout choicevillages;
	View view;
	TextView street;
	TextView village;


	private ListView lstv;
	private ItemXiaoquAdapter adapter;
	List<Community> lists = new ArrayList<Community>();
	List<Community> listsAll = new ArrayList<Community>();

	private TextView address;
	private ImageView no_collection;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.xq);
		choicestreet = (RelativeLayout) findViewById(R.id.choicestreet);
		choicevillages = (RelativeLayout) findViewById(R.id.choicevillages);
		street = (TextView) findViewById(R.id.street);
		village = (TextView) findViewById(R.id.village);
		choicestreet.setOnClickListener(this);
		choicevillages.setOnClickListener(this);
		this.findViewById(R.id.back).setOnClickListener(this);

		address = (TextView) this.findViewById(R.id.address);
		no_collection = (ImageView) this.findViewById(R.id.no_collection);
		address.setText(ThirdApplication.desc==null?"请重新打开软件，我们需要重新定位当前位置":ThirdApplication.desc);
		lstv = (ListView) this.findViewById(R.id.lstv);
		adapter = new ItemXiaoquAdapter(lists, SelectCommunityTwoActivity.this);
		lstv.setAdapter(adapter);
		lstv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Community community = lists.get(i);
				save("community_id", community.getCommunity_id());
				save("community_name" ,community.getCommunity_name());
//				//发通知
//				Intent intent = new Intent("select_community");
//				sendBroadcast(intent);
//				finish();

				Intent intent = new Intent(SelectCommunityTwoActivity.this, MainActivity.class);
				startActivity(intent);
				finish();

			}
		});
		if(!StringUtil.isNullOrEmpty(String.valueOf(ThirdApplication.lat))){
			progressDialog = new CustomProgressDialog(SelectCommunityTwoActivity.this , "", R.anim.frame_paopao);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			progressDialog.show();
			getData();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.choicestreet:
			showPopupWindow(choicestreet, street);
			break;
		case R.id.choicevillages:
			showPopupWindow(choicevillages, village);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
	
	public void showPopupWindow(RelativeLayout imageView, final TextView textview){
		final PopupWindow popupWindow = new PopupWindow();
		popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);//设置可点击
		popupWindow.setFocusable(true); //可聚焦
		popupWindow.setOutsideTouchable(true);//设置外部可点击
		ColorDrawable colorDrawable = new ColorDrawable(0xffffffff);
		popupWindow.setBackgroundDrawable(colorDrawable); //设置背景
		LayoutInflater inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.popupvillage, null);
		villagelv = (ListView) view.findViewById(R.id.villagenamelv);
		final List<PopupTextlBean> list = new ArrayList<PopupTextlBean>();
		switch (textview.getId()) {
		case R.id.street:
			for (int i = 1; i < 6; i++) {
				PopupTextlBean bean = new PopupTextlBean("街道名" + i);
				list.add(bean);
			}
			break;
		case R.id.village:
			for (int i = 1; i < 6; i++) {
				PopupTextlBean bean = new PopupTextlBean("小区名" + i);
				list.add(bean);
			}
			break;
		default:
			break;
		}
		PopupTextAdapter2 adapter = new PopupTextAdapter2(this, list);
		villagelv.setAdapter(adapter);
		villagelv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				System.out.println("position = " + position);
				String name = list.get(position).popupname;
				textview.setText(name);
				popupWindow.dismiss();
			}
		});
		//popupWindow.setAnimationStyle(R.style.AnimationPreview);
		//int w = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		popupWindow.setContentView(view);
		popupWindow.showAsDropDown(imageView, 0, 0);
		popupWindow.setOnDismissListener(new poponDismissListener());
	}

	public void backgroundAlpha(float bgAlpha) {

		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getWindow().setAttributes(lp);
	}

	public class poponDismissListener implements PopupWindow.OnDismissListener {

		@Override
		public void onDismiss() {
			backgroundAlpha(1f);
		}
	}

	public void getData() {
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.COMMUNITY_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									CommunityData data = getGson().fromJson(s, CommunityData.class);
										listsAll.addAll(data.getData());
										if(listsAll != null && listsAll.size()>0){
											initLst();
										}
								}else {
									Toast.makeText(SelectCommunityTwoActivity.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(SelectCommunityTwoActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(SelectCommunityTwoActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
//				params.put("lat",  String.valueOf(ThirdApplication.lat));
				params.put("lat",  "31");
//				params.put("lng",  String.valueOf(ThirdApplication.lon));
				params.put("lng",  "121");
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

	public void initLst(){
		if(listsAll != null){
			for(Community community:listsAll){
				if(Double.valueOf(community.getDistance()) < 1.1){
					lists.add(community);
				}
			}
		}
		if(lists != null && lists.size()>0){
			no_collection.setVisibility(View.GONE);
			lstv.setVisibility(View.VISIBLE);

			adapter.notifyDataSetChanged();
		}else {
			no_collection.setVisibility(View.VISIBLE);
			lstv.setVisibility(View.GONE);
		}
	}

}
