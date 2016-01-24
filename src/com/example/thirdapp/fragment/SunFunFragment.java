package com.example.thirdapp.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.activity.*;
import com.example.thirdapp.adapter.AnimateFirstDisplayListener;
import com.example.thirdapp.adapter.ItemShaifanerAdapter;
import com.example.thirdapp.adapter.OnClickContentItemListener;
import com.example.thirdapp.adapter.ViewPagerAdapter;
import com.example.thirdapp.base.BaseFragment;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.bean.ShaifanerObj;
import com.example.thirdapp.data.AdSlideData;
import com.example.thirdapp.data.ShaifanerObjData;
import com.example.thirdapp.module.AdSlide;
import com.example.thirdapp.util.*;
import com.example.thirdapp.view.ContentListView;
import com.example.thirdapp.view.CustomProgressDialog;
import com.example.thirdapp.view.SelectPhoPopWindow;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SunFunFragment extends BaseFragment implements OnClickContentItemListener, ContentListView.OnRefreshListener,
		ContentListView.OnLoadListener,View.OnClickListener {
	View view;

	//导航
	private ViewPager viewpager;
	private ViewPagerAdapter adapter;
	private LinearLayout viewGroup;
	private ImageView dot, dots[];
	private Runnable runnable;
	private int autoChangeTime = 5000;
	private List<AdSlide> lists = new ArrayList<AdSlide>();

	private ContentListView detail_lstv;
	private LinearLayout commentLayout;
	private ItemShaifanerAdapter adapter2;
	private List<ShaifanerObj> commentContents = new ArrayList<ShaifanerObj>();
	private List<ShaifanerObj> commentContentsTop = new ArrayList<ShaifanerObj>();


	private ImageView head_one;
	private ImageView head_two;
	private ImageView head_three;
	private TextView name_one;
	private TextView name_two;
	private TextView name_three;
	private TextView text1;
	private TextView text2;
	private TextView text3;

	private ArrayList<String> dataList = new ArrayList<String>();
	private ArrayList<String> tDataList = new ArrayList<String>();
	private List<String> uploadPaths = new ArrayList<String>();

	private static int REQUEST_CODE = 1;

	private final static int SELECT_LOCAL_PHOTO = 110;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.sunfanfragment, null);
		registerBoradcastReceiver();

		commentLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.sunfanfragment_header, null);
		detail_lstv = (ContentListView) view.findViewById(R.id.detail_lstv);
		detail_lstv.addHeaderView(commentLayout);//添加头部
//        detail_lstv.addFooterView(commentLayoutfoot);

		adapter2 = new ItemShaifanerAdapter( commentContents, getActivity());
//		adapter.setOnClickContentItemListener(this);
		detail_lstv.setAdapter(adapter2);
		detail_lstv.setOnRefreshListener(this);
		detail_lstv.setOnLoadListener(this);
//        detail_lstv.setLoadEnable(true);
		detail_lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ShaifanerObj commentContent = (ShaifanerObj) commentContents.get(position-2);
				if (commentContent != null) {
					final String[] picUrls = commentContent.getImage_str().split(",");//图片链接切割
					Intent intent = new Intent(getActivity(), GalleryUrlActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
					intent.putExtra(Constants.IMAGE_URLS, picUrls);
					intent.putExtra(Constants.IMAGE_POSITION, 0);
					startActivity(intent);
				}
			}
		});
		progressDialog = new CustomProgressDialog(getActivity() , "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();

		getSlide();
		loadData(ContentListView.REFRESH);
		loadDataTop();

		head_one = (ImageView) commentLayout.findViewById(R.id.head_one);
		head_two = (ImageView) commentLayout.findViewById(R.id.head_two);
		head_three = (ImageView) commentLayout.findViewById(R.id.head_three);
		name_one = (TextView) commentLayout.findViewById(R.id.name_one);
		name_two = (TextView) commentLayout.findViewById(R.id.name_two);
		name_three = (TextView) commentLayout.findViewById(R.id.name_three);
		text1 = (TextView) commentLayout.findViewById(R.id.text1);
		text2 = (TextView) commentLayout.findViewById(R.id.text2);
		text3 = (TextView) commentLayout.findViewById(R.id.text3);

		commentLayout.findViewById(R.id.liner_one).setOnClickListener(this);
		commentLayout.findViewById(R.id.liner_two).setOnClickListener(this);
		commentLayout.findViewById(R.id.liner_three).setOnClickListener(this);

		return view;
	}

	private int pageIndex = 1;
	/**
	 * 加载数据监听实现
	 */
	@Override
	public void onLoad() {
		pageIndex++;
		loadData(ContentListView.LOAD);
	}

	/**
	 * 刷新数据监听实现
	 */
	@Override
	public void onRefresh() {
		pageIndex = 1;
		loadData(ContentListView.REFRESH);
	}

	private void loadData(final int currentid) {
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.SHOW_LISTS_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						detail_lstv.onRefreshComplete();
						detail_lstv.onLoadComplete();
						if (StringUtil.isJson(s)) {
							ShaifanerObjData data = getGson().fromJson(s, ShaifanerObjData.class);
							if (data.getCode() == 200) {
								if (ContentListView.REFRESH == currentid) {
									commentContents.clear();
									commentContents.addAll(data.getData());
									detail_lstv.setResultSize(data.getData().size());
									adapter2.notifyDataSetChanged();
								}
								if (ContentListView.LOAD == currentid) {
									commentContents.addAll(data.getData());
									detail_lstv.setResultSize(data.getData().size());
									adapter2.notifyDataSetChanged();
								}
							} else {
								Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						detail_lstv.onRefreshComplete();
						detail_lstv.onLoadComplete();
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

	private void initViewPager() {
		adapter = new ViewPagerAdapter(getActivity());
		adapter.change(lists);
		adapter.setOnClickContentItemListener(this);
		viewpager = (ViewPager) commentLayout.findViewById(R.id.viewpager);
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
		viewGroup = (LinearLayout) commentLayout.findViewById(R.id.viewGroup);

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
									lists = data.getData();
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
				params.put("type", "1");
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

	private void loadDataTop() {
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.SHOW_LISTS_TOP_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {

						if (StringUtil.isJson(s)) {
							ShaifanerObjData data = getGson().fromJson(s, ShaifanerObjData.class);
							if (data.getCode() == 200) {
								commentContentsTop.addAll(data.getData());
								//
								initTop();
							} else {
								Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
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


	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

	void initTop(){

		if(commentContentsTop != null){
			ShaifanerObj shaifanerObj1 = commentContentsTop.get(0);
			ShaifanerObj shaifanerObj2 = commentContentsTop.get(1);
			ShaifanerObj shaifanerObj3 = commentContentsTop.get(2);
			if(shaifanerObj1 != null){
				imageLoader.displayImage(InternetURL.INTERNAL_PIC + shaifanerObj1.getCover(), head_one, ThirdApplication.txOptions, animateFirstListener);
				name_one.setText(shaifanerObj1.getNick_name());
				text1.setText("晒单\n"+shaifanerObj1.getCount());
			}
			if(shaifanerObj2 != null){
				imageLoader.displayImage(InternetURL.INTERNAL_PIC + shaifanerObj1.getCover(), head_two, ThirdApplication.txOptions, animateFirstListener);
				name_two.setText(shaifanerObj1.getNick_name());
				text2.setText("晒单\n"+shaifanerObj1.getCount());
			}
			if(shaifanerObj3 != null){
				imageLoader.displayImage(InternetURL.INTERNAL_PIC + shaifanerObj1.getCover(), head_three, ThirdApplication.txOptions, animateFirstListener);
				name_three.setText(shaifanerObj1.getNick_name());
				text3.setText("晒单\n"+shaifanerObj1.getCount());
			}
		}



	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.liner_one:
			{
				//
				ShaifanerObj shaifanerObj1 = commentContentsTop.get(0);
				if(shaifanerObj1 != null){
					final String[] picUrls = shaifanerObj1.getImage_str().split(",");//图片链接切割
					Intent intent = new Intent(getActivity(), GalleryUrlActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
					intent.putExtra(Constants.IMAGE_URLS, picUrls);
					intent.putExtra(Constants.IMAGE_POSITION, 0);
					startActivity(intent);
				}
			}
				break;
			case R.id.liner_two:
			{
				ShaifanerObj shaifanerObj2 = commentContentsTop.get(1);
				if(shaifanerObj2 != null){
					final String[] picUrls = shaifanerObj2.getImage_str().split(",");//图片链接切割
					Intent intent = new Intent(getActivity(), GalleryUrlActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
					intent.putExtra(Constants.IMAGE_URLS, picUrls);
					intent.putExtra(Constants.IMAGE_POSITION, 0);
					startActivity(intent);
				}

			}
				break;
			case R.id.liner_three:
			{
				ShaifanerObj shaifanerObj3 = commentContentsTop.get(2);
				if(shaifanerObj3 != null){
					final String[] picUrls = shaifanerObj3.getImage_str().split(",");//图片链接切割
					Intent intent = new Intent(getActivity(), GalleryUrlActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
					intent.putExtra(Constants.IMAGE_URLS, picUrls);
					intent.putExtra(Constants.IMAGE_POSITION, 0);
					startActivity(intent);
				}
			}
				break;

		}
	}

	private SelectPhoPopWindow deleteWindow;

	// 选择相册，相机
	public void  ShowPickDialog() {
		deleteWindow = new SelectPhoPopWindow(getActivity(), itemsOnClick1);
		//显示窗口
		deleteWindow.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

	}
	//为弹出窗口实现监听类
	private View.OnClickListener itemsOnClick1 = new View.OnClickListener() {

		public void onClick(View v) {
			deleteWindow.dismiss();
			switch (v.getId()) {
				case R.id.picture: {
					Intent wenzi = new Intent(getActivity(), PublishPicActivity.class);
					wenzi.putExtra(Constants.SELECT_PHOTOORPIIC, "1");
					startActivity(wenzi);
				}
				break;
				case R.id.mapstorage: {
					Intent wenzi = new Intent(getActivity(), PublishPicActivity.class);
					wenzi.putExtra(Constants.SELECT_PHOTOORPIIC, "2");
					startActivity(wenzi);
				}
				break;

				default:
					break;
			}
		}
	};

	//广播接收动作
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("publishshaifaner")) {
				pageIndex = 1;
				loadData(ContentListView.REFRESH);
			}
			if (action.equals("add_box")) {
				//
				if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
					ShowPickDialog();
				}else {
					Intent login = new Intent(getActivity(), Logon.class);
					login.putExtra("skip", 1);
					startActivity(login);
				}
			}
		}
	};

	//注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("add_box");
		myIntentFilter.addAction("publishshaifaner");
		//注册广播
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}


}
