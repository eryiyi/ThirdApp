package com.example.thirdapp.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.adapter.AnimateFirstDisplayListener;
import com.example.thirdapp.adapter.OnClickContentItemListener;
import com.example.thirdapp.adapter.ViewPagerAdapter;
import com.example.thirdapp.adapter.ViewPagerAdapterTwo;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.module.ActivityObj;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityDetail extends BaseActivity implements OnClickListener,OnClickContentItemListener {
	ImageView back;
//	ImageView mine_img;
	TextView actname;
	TextView actclass;
	TextView actrule;
	TextView registrationtime;
	TextView competitiontime;
	TextView competitionaddress;
	TextView registrationphone;
	private ActivityObj activityBean;

	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	//导航
	private ViewPager viewpager;
	private ViewPagerAdapterTwo adapter;
	private LinearLayout viewGroup;
	private ImageView dot, dots[];
	private Runnable runnable;
	private int autoChangeTime = 5000;
	private List<String> lists = new ArrayList<String>();


	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		activityBean = (ActivityObj) getIntent().getExtras().get("activityObj");
		setContentView(R.layout.activitydetail);
		actname = (TextView) findViewById(R.id.actname);
		actclass = (TextView) findViewById(R.id.actclass);
		actrule = (TextView) findViewById(R.id.actrule);
//		mine_img = (ImageView) findViewById(R.id.mine_img);
		registrationtime = (TextView) findViewById(R.id.registrationtime);
		competitiontime = (TextView) findViewById(R.id.competitiontime);
		competitionaddress = (TextView) findViewById(R.id.competitionaddress);
		registrationphone = (TextView) findViewById(R.id.registrationphone);
		actname.setText(activityBean.getTitle());
		actclass.setText(activityBean.getActive_name());
		actrule.setText(activityBean.getActive_rule());
		registrationtime.setText(activityBean.getJoin_time());
		competitiontime.setText(activityBean.getActive_time());
		competitionaddress.setText(activityBean.getActive_place());
		registrationphone.setText(activityBean.getTel());
//		imageLoader.displayImage(InternetURL.INTERNAL_PIC + activityBean.getImage(), mine_img, ThirdApplication.options, animateFirstListener);

		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ActivityDetail.this.finish();
			}
		});

		progressDialog = new CustomProgressDialog(ActivityDetail.this, "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getData();

		if(activityBean != null){
			lists.add(activityBean.getImage()==null?"":activityBean.getImage());
			lists.add(activityBean.getImage2()==null?"":activityBean.getImage2());
			lists.add(activityBean.getImage3()==null?"":activityBean.getImage3());
			initViewPager();
		}


	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
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
//									ActivityObjData data = getGson().fromJson(s, ActivityObjData.class);
//									list.addAll(data.getData());
//									adapter.notifyDataSetChanged();
								}else {
									Toast.makeText(ActivityDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(ActivityDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(ActivityDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("id",activityBean.getId());
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


	private void initViewPager() {
		adapter = new ViewPagerAdapterTwo(this);
		adapter.change(lists);
		adapter.setOnClickContentItemListener(this);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		viewpager.setAdapter(adapter);
		viewpager.setOnPageChangeListener(myOnPageChangeListener);
		initDot();
		runnable = new Runnable() {
			@Override
			public void run() {
				int next = viewpager.getCurrentItem() + 1;
				if (next >= adapter.getCount()) {
					next = 0;
				}
				viewHandler.sendEmptyMessage(next);
			}
		};
		viewHandler.postDelayed(runnable, autoChangeTime);
	}

	// 初始化dot视图
	private void initDot() {
		viewGroup = (LinearLayout) findViewById(R.id.viewGroup);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				20, 20);
		layoutParams.setMargins(4, 3, 4, 3);

		dots = new ImageView[adapter.getCount()];
		for (int i = 0; i < adapter.getCount(); i++) {

			dot = new ImageView(this);
			dot.setLayoutParams(layoutParams);
			dots[i] = dot;
			dots[i].setTag(i);
			dots[i].setOnClickListener(onClick);

			if (i == 0) {
				dots[i].setBackgroundResource(R.drawable.dotc);
			} else {
				dots[i].setBackgroundResource(R.drawable.dotn);
			}

			viewGroup.addView(dots[i]);
		}
	}

	ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			setCurDot(arg0);
			viewHandler.removeCallbacks(runnable);
			viewHandler.postDelayed(runnable, autoChangeTime);
		}

	};
	// 实现dot点击响应功能,通过点击事件更换页面
	View.OnClickListener onClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			setCurView(position);
		}

	};

	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position > adapter.getCount()) {
			return;
		}
		viewpager.setCurrentItem(position);
	}

	/**
	 * 选中当前引导小点
	 */
	private void setCurDot(int position) {
		for (int i = 0; i < dots.length; i++) {
			if (position == i) {
				dots[i].setBackgroundResource(R.drawable.dotc);
			} else {
				dots[i].setBackgroundResource(R.drawable.dotn);
			}
		}
	}

	/**
	 * 每隔固定时间切换广告栏图片
	 */
	@SuppressLint("HandlerLeak")
	private final Handler viewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setCurView(msg.what);
		}
	};

	@Override
	public void onClickContentItem(int position, int flag, Object object) {

	}
}
