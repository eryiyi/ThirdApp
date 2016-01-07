package com.example.thirdapp.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.example.thirdapp.activity.*;
import com.example.thirdapp.adapter.AnimateFirstDisplayListener;
import com.example.thirdapp.adapter.OnClickContentItemListener;
import com.example.thirdapp.adapter.ViewPagerAdapter;
import com.example.thirdapp.base.BaseFragment;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.AdSlideData;
import com.example.thirdapp.data.HotGoodsObjData;
import com.example.thirdapp.module.AdSlide;
import com.example.thirdapp.module.HotGoodsObj;
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

public class MainPageFragment extends BaseFragment implements OnClickListener,OnClickContentItemListener {
	LinearLayout makefriends;
	LinearLayout rechange;
	LinearLayout forum;
	LinearLayout hotel;
	LinearLayout convenience;
	LinearLayout activity;
	LinearLayout study;
	LinearLayout integralarea;
	private View view;

	//导航
	private ViewPager viewpager;
	private ViewPagerAdapter adapter;
	private LinearLayout viewGroup;
	private ImageView dot, dots[];
	private Runnable runnable;
	private int autoChangeTime = 5000;
	private List<AdSlide> lists = new ArrayList<AdSlide>();
	private List<HotGoodsObj> listsHot = new ArrayList<HotGoodsObj>();

	private ImageView hot_one;
	private ImageView hot_two;
	private ImageView hot_three;

	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private TextView img_one_title;
	private TextView img_one_price;
	private TextView img_one_pricetuan;

	private TextView img_two_title;
	private TextView img_two_price;
	private TextView img_two_pricetuan;

	private TextView img_three_title;
	private TextView img_three_price;
	private TextView img_three_pricetuan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.mainpagefragment, null);
		registerBoradcastReceiver();
		initView();
		progressDialog = new CustomProgressDialog(getActivity() , "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getSlide();

		if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("community_id", ""), String.class))){
			getdata();
		}
		return view;
	}

	void initView(){
		makefriends = (LinearLayout) view.findViewById(R.id.makefriends);
		rechange = (LinearLayout) view.findViewById(R.id.rechange);
		forum = (LinearLayout) view.findViewById(R.id.forum);
		hotel = (LinearLayout) view.findViewById(R.id.hotel);
		convenience = (LinearLayout) view.findViewById(R.id.convenience);
		activity = (LinearLayout) view.findViewById(R.id.activity);
		study = (LinearLayout) view.findViewById(R.id.study);
		integralarea = (LinearLayout) view.findViewById(R.id.integralarea);

		makefriends.setOnClickListener(this);
		rechange.setOnClickListener(this);
		forum.setOnClickListener(this);
		hotel.setOnClickListener(this);
		convenience.setOnClickListener(this);
		activity.setOnClickListener(this);
		study.setOnClickListener(this);
		integralarea.setOnClickListener(this);

		hot_one = (ImageView) view.findViewById(R.id.hot_one);
		hot_two = (ImageView) view.findViewById(R.id.hot_two);
		hot_three = (ImageView) view.findViewById(R.id.hot_three);
		hot_one.setOnClickListener(this);
		hot_two.setOnClickListener(this);
		hot_three.setOnClickListener(this);

		img_one_title = (TextView) view.findViewById(R.id.img_one_title);
		img_two_title = (TextView) view.findViewById(R.id.img_two_title);
		img_three_title = (TextView) view.findViewById(R.id.img_three_title);
		img_one_price = (TextView) view.findViewById(R.id.img_one_price);
		img_two_price = (TextView) view.findViewById(R.id.img_two_price);
		img_three_price = (TextView) view.findViewById(R.id.img_three_price);
		img_one_pricetuan = (TextView) view.findViewById(R.id.img_one_pricetuan);
		img_two_pricetuan = (TextView) view.findViewById(R.id.img_two_pricetuan);
		img_three_pricetuan = (TextView) view.findViewById(R.id.img_three_pricetuan);

		img_one_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
		img_two_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
		img_three_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);


	}

	private void initViewPager() {

		adapter = new ViewPagerAdapter(getActivity());
		adapter.change(lists);
		adapter.setOnClickContentItemListener(this);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
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
		viewGroup = (LinearLayout) view.findViewById(R.id.viewGroup);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				20, 20);
		layoutParams.setMargins(4, 3, 4, 3);

		dots = new ImageView[adapter.getCount()];
		for (int i = 0; i < adapter.getCount(); i++) {

			dot = new ImageView(getActivity());
			dot.setLayoutParams(layoutParams);
			dots[i] = dot;
			dots[i].setTag(i);
			dots[i].setOnClickListener(onClick);

			if (i == 0) {
				dots[i].setBackgroundResource(R.drawable.radiobutton1);
			} else {
				dots[i].setBackgroundResource(R.drawable.radiobutton2);
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
//        if (!StringUtil.isNullOrEmpty(lists.get(position).getNewsTitle())){
//            titleSlide = lists.get(position).getNewsTitle();
//            if(titleSlide.length() > 13){
//                titleSlide = titleSlide.substring(0,12);
//                article_title.setText(titleSlide);//当前新闻标题显示
//            }else{
//                article_title.setText(titleSlide);//当前新闻标题显示
//            }
//        }

	}

	/**
	 * 选中当前引导小点
	 */
	private void setCurDot(int position) {
		for (int i = 0; i < dots.length; i++) {
			if (position == i) {
				dots[i].setBackgroundResource(R.drawable.radiobutton1);
			} else {
				dots[i].setBackgroundResource(R.drawable.radiobutton2);
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


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.makefriends:
				//交友
				Intent intent = new Intent(getActivity(), MakeFriends.class);
				startActivity(intent);
				break;
			case R.id.rechange:
				if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
					Intent intent2 = new Intent(getActivity(), Rechange.class);
					startActivity(intent2);
				}else {
					Intent login = new Intent(getActivity(), Logon.class);
					login.putExtra("skip", 1);
					startActivity(login);
				}
				break;
			case R.id.forum:
				//论坛
				Intent intent3 = new Intent(getActivity(), TalkTypeActivity.class);
				startActivity(intent3);
				break;
			case R.id.hotel:
				Intent intent4 = new Intent(getActivity(), Hotel.class);
				startActivity(intent4);
				break;
			case R.id.convenience:
				//便民
				Intent intent5 = new Intent(getActivity(), BianminTypeActivity.class);
				startActivity(intent5);
				break;
			case R.id.activity:
				//活动列表
				Intent intent6 = new Intent(getActivity(), Activity.class);
				startActivity(intent6);
				break;
			case R.id.study:
				//学习
				Intent intent7 = new Intent(getActivity(), Study.class);
				startActivity(intent7);
				break;
			case R.id.integralarea:
				Intent intent8 = new Intent(getActivity(), Integral.class);
				startActivity(intent8);
				break;
			case R.id.hot_one:
			{
				if(listsHot != null && listsHot.size() > 0){
						HotGoodsObj hotGoodsObj = listsHot.get(0);
						if( hotGoodsObj != null){
							Intent detailgoods = new Intent(getActivity(), ComDetail.class);
							detailgoods.putExtra("product_id", hotGoodsObj.getProduct_id());
							startActivity(detailgoods);
						}
					}
			}
				break;
			case R.id.hot_two:
			{
				if(listsHot != null && listsHot.size() > 1){
					HotGoodsObj hotGoodsObj = listsHot.get(1);
					if( hotGoodsObj != null){
						Intent detailgoods = new Intent(getActivity(), ComDetail.class);
						detailgoods.putExtra("product_id", hotGoodsObj.getProduct_id());
						startActivity(detailgoods);
					}
				}
			}
				break;
			case R.id.hot_three:
			{
				if(listsHot != null && listsHot.size() > 2){
					HotGoodsObj hotGoodsObj = listsHot.get(2);
					if( hotGoodsObj != null){
						Intent detailgoods = new Intent(getActivity(), ComDetail.class);
						detailgoods.putExtra("product_id", hotGoodsObj.getProduct_id());
						startActivity(detailgoods);
					}
				}
			}
				break;
		default:
			break;
		}
	}

	void getSlide(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.ADV_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									AdSlideData data = getGson().fromJson(s, AdSlideData.class);
										lists.addAll(data.getData());
										initViewPager();
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


	void getdata(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.PRODUCT_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									HotGoodsObjData data = getGson().fromJson(s, HotGoodsObjData.class);
									listsHot.addAll(data.getData());
									if(listsHot != null && listsHot.size() > 0){
										for(int i=0;i<listsHot.size();i++){
											HotGoodsObj hotGoodsObj = listsHot.get(i);
											if(i == 0 && hotGoodsObj != null){
												img_one_title.setText(hotGoodsObj.getProduct_name()==null?"":hotGoodsObj.getProduct_name());
												img_one_pricetuan.setText("现价:￥"+(hotGoodsObj.getPrice_tuangou()==null?"":hotGoodsObj.getPrice_tuangou()));
												img_one_price.setText("原价:￥"+(hotGoodsObj.getPrice()==null?"":hotGoodsObj.getPrice()));
												imageLoader.displayImage(InternetURL.INTERNAL_PIC + hotGoodsObj.getProduct_pic(), hot_one, ThirdApplication.options, animateFirstListener);
											}
											if(i == 1 && hotGoodsObj != null){
												img_two_title.setText(hotGoodsObj.getProduct_name()==null?"":hotGoodsObj.getProduct_name());
												img_two_pricetuan.setText("现价:￥"+(hotGoodsObj.getPrice_tuangou()==null?"":hotGoodsObj.getPrice_tuangou()));
												img_two_price.setText("原价:￥"+(hotGoodsObj.getPrice()==null?"":hotGoodsObj.getPrice()));
												imageLoader.displayImage(InternetURL.INTERNAL_PIC + hotGoodsObj.getProduct_pic(), hot_two, ThirdApplication.options, animateFirstListener);
											}
											if(i == 2 && hotGoodsObj != null){
												img_three_title.setText(hotGoodsObj.getProduct_name()==null?"":hotGoodsObj.getProduct_name());
												img_three_pricetuan.setText("现价:￥"+(hotGoodsObj.getPrice_tuangou()==null?"":hotGoodsObj.getPrice_tuangou()));
												img_three_price.setText("原价:￥"+(hotGoodsObj.getPrice()==null?"":hotGoodsObj.getPrice()));
												imageLoader.displayImage(InternetURL.INTERNAL_PIC + hotGoodsObj.getProduct_pic(), hot_three, ThirdApplication.options, animateFirstListener);
											}
										}
									}
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
//				params.put("community_id", getGson().fromJson(getSp().getString("community_id", ""), String.class));
				params.put("community_id", "1");
				params.put("action", "hot");
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



	//广播接收动作
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("select_community")) {
				progressDialog = new CustomProgressDialog(getActivity() , "", R.anim.frame_paopao);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressDialog.setCancelable(false);
				progressDialog.setIndeterminate(true);
				progressDialog.show();
				getdata();
			}
		}
	};

	//注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("select_community");
		//注册广播
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}

}
