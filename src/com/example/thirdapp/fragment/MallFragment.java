package com.example.thirdapp.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.activity.*;
import com.example.thirdapp.adapter.ItemGoodsAdapter;
import com.example.thirdapp.adapter.OnClickContentItemListener;
import com.example.thirdapp.adapter.ViewPagerAdapter;
import com.example.thirdapp.base.BaseFragment;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.AdSlideData;
import com.example.thirdapp.data.HotGoodsObjData;
import com.example.thirdapp.db.DBHelper;
import com.example.thirdapp.db.ShoppingCart;
import com.example.thirdapp.handler.ImageHandlerMall;
import com.example.thirdapp.module.AdSlide;
import com.example.thirdapp.module.HotGoodsObj;
import com.example.thirdapp.util.DateUtil;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.ContentListView;
import com.example.thirdapp.view.CustomProgressDialog;
import com.example.thirdapp.view.MyListViewUpDown;
import com.example.thirdapp.view.MyListViewUpDown.OnLoadListener;
import com.example.thirdapp.view.MyListViewUpDown.OnRefreshListener;
import com.image.view.Content;
import com.image.view.MyAdapter;
import com.image.view.PinyinComparator;
//import com.image.view.SideBar;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.*;

public class MallFragment extends BaseFragment implements ContentListView.OnRefreshListener,
		ContentListView.OnLoadListener, OnClickListener,OnRefreshListener, OnLoadListener,OnClickContentItemListener {
	View view;

	List<HotGoodsObj> list2;
	ItemGoodsAdapter adapter;
	private ImageView shoppingcart;
	private ImageView mallclass;
	TextView nouser;
	public ImageHandlerMall handlermall = new ImageHandlerMall(new WeakReference<MallFragment>(this));
	private WindowManager mWindowManager;
	private TextView mDialogText;

	private TextView number ;//数量

	//导航
	private ViewPager viewpager;
	private ViewPagerAdapter adapterAd;
	private LinearLayout viewGroup;
	private ImageView dot, dots[];
	private Runnable runnable;
	private int autoChangeTime = 5000;
	private List<AdSlide> lists = new ArrayList<AdSlide>();
	private List<HotGoodsObj> listsHot = new ArrayList<HotGoodsObj>();

	//商品
	MyAdapter adapter2;

	private ContentListView detail_lstv;
	private FrameLayout commentLayout;
	private int pageIndex = 1;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.mallfragment, null);
		detail_lstv = (ContentListView) view.findViewById(R.id.malllv);

		commentLayout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.mallfragment_header, null);


		nouser = (TextView) commentLayout.findViewById(R.id.nouser);
		mDialogText = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.list_position, null);
		mDialogText.setVisibility(View.INVISIBLE);
		mWindowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		mWindowManager.addView(mDialogText, lp);

		shoppingcart = (ImageView) commentLayout.findViewById(R.id.shoppingcart);
		shoppingcart.setOnClickListener(this);
		mallclass = (ImageView) commentLayout.findViewById(R.id.mallclass);
		mallclass.setOnClickListener(this);

		detail_lstv.addHeaderView(commentLayout);//添加头部
		detail_lstv.setOnRefreshListener(this);
		detail_lstv.setOnLoadListener(this);
		list2 = new ArrayList<HotGoodsObj>();
		adapter = new ItemGoodsAdapter(listsHot ,getActivity());

		adapter2 = new MyAdapter(getActivity(), list2);
		adapter2.setOnClickContentItemListener(this);
		// 为listView设置适配
		detail_lstv.setAdapter(adapter2);

		number = (TextView) view.findViewById(R.id.number);

		detail_lstv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//				Intent intent = new Intent(getActivity(), CommodityList.class);
//				startActivity(intent);

				Intent intent = new Intent(getActivity(), ComDetail.class);
				HotGoodsObj hotGoodsObj = list2.get(position-2);
				intent.putExtra("product_id", hotGoodsObj.getProduct_id());
				startActivity(intent);
			}
		});

		progressDialog = new CustomProgressDialog(getActivity() , "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getSlide();
		if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("community_id", ""), String.class))){
			loadData(ContentListView.REFRESH);
		}

		getCartNum();
		return view;
	}


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
				params.put("type", "2");
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



	void loadData(final int currentid){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.PRODUCT_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							detail_lstv.onRefreshComplete();
							detail_lstv.onLoadComplete();
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									HotGoodsObjData data = getGson().fromJson(s, HotGoodsObjData.class);
									if (ContentListView.REFRESH == currentid) {
										list2.clear();
										list2.addAll(data.getData());
										detail_lstv.setResultSize(data.getData().size());
										adapter.notifyDataSetChanged();
									}
									if (ContentListView.LOAD == currentid) {
										list2.addAll(data.getData());
										detail_lstv.setResultSize(data.getData().size());
										adapter.notifyDataSetChanged();
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
						detail_lstv.onRefreshComplete();
						detail_lstv.onLoadComplete();
						Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
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


	private void initViewPager() {
		adapterAd = new ViewPagerAdapter(getActivity());
		adapterAd.change(lists);
		adapterAd.setOnClickContentItemListener(this);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		viewpager.setAdapter(adapterAd);
		viewpager.setOnPageChangeListener(myOnPageChangeListener);
		initDot();
		runnable = new Runnable() {
			@Override
			public void run() {
				int next = viewpager.getCurrentItem() + 1;
				if (next >= adapterAd.getCount()) {
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

		dots = new ImageView[adapterAd.getCount()];
		for (int i = 0; i < adapterAd.getCount(); i++) {

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
		if (position < 0 || position > adapterAd.getCount()) {
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
		String str = (String) object;
		if("000".equals(str)){
			switch (flag){
				case 1:
					//加入购物车
					if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
						HotGoodsObj goodsObj = list2.get(position);
						saveCart(goodsObj);
					}else {
						Intent intent = new Intent(getActivity(), Logon.class);
						intent.putExtra("skip", 1);
						startActivity(intent);
					}
					break;
			}
		}
	}

	//保存购物车
	void saveCart(HotGoodsObj hotGoodsObj){
		//先判断是否已经加入购物车
		if(DBHelper.getInstance(getActivity()).isSaved(hotGoodsObj.getProduct_id())){
			//如果你存在
			Toast.makeText(getActivity(), R.string.add_cart_is, Toast.LENGTH_SHORT).show();
		}else {
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setCartid(StringUtil.getUUID());
			shoppingCart.setGoods_id(hotGoodsObj.getProduct_id()==null?"":hotGoodsObj.getProduct_id());
			shoppingCart.setEmp_id(getGson().fromJson(getSp().getString("uid", ""), String.class));
			shoppingCart.setGoods_name(hotGoodsObj.getProduct_name()==null?"":hotGoodsObj.getProduct_name());
			shoppingCart.setGoods_cover(hotGoodsObj.getProduct_pic()==null?"":hotGoodsObj.getProduct_pic());
			shoppingCart.setSell_price(hotGoodsObj.getPrice_tuangou()==null?"":hotGoodsObj.getPrice());
			shoppingCart.setGoods_count("1");
			shoppingCart.setDateline(DateUtil.getCurrentDateTime());
			shoppingCart.setIs_select("0");
			DBHelper.getInstance(getActivity()).addShoppingToTable(shoppingCart);
			Toast.makeText(getActivity(), R.string.add_cart_success, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent("cart_success");
			getActivity().sendBroadcast(intent);
			getCartNum();
		}
	}

	void getCartNum(){
		//获得购物车商品数量
		number.setVisibility(View.VISIBLE);
		List<ShoppingCart> listCart = DBHelper.getInstance(getActivity()).getShoppingList();
		if(listCart!=null){
			number.setText(String.valueOf(listCart.size()));
		}else {
			number.setText("0");
		}
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mallclass:
			//产品分类
			Intent intent = new Intent(getActivity(), ComClass.class);
			startActivity(intent);
			break;
		case R.id.shoppingcart:
			Intent intent2 = new Intent(getActivity(), MineCartActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}

}
