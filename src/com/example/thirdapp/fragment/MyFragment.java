package com.example.thirdapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.example.thirdapp.base.BaseFragment;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.AdSlideData;
import com.example.thirdapp.data.MemberObjData;
import com.example.thirdapp.db.DBHelper;
import com.example.thirdapp.db.ShoppingCart;
import com.example.thirdapp.module.MemberObj;
import com.example.thirdapp.util.StringUtil;
import com.image.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFragment extends BaseFragment implements OnClickListener{
	View view;
	ImageView tobepaid;
	ImageView tobeshipped;
	ImageView tobereceive;
	ImageView tobeevaluate;
	ImageView toberefund;
	ImageView wallet;
	ImageView coupons;
	ImageView voucher;
	ImageView integral;
	ImageView walletfu;
	TextView username;
	RelativeLayout allorder;
	RelativeLayout setting;
	LinearLayout imgtxt;
	RoundImageView mysetting;
	LinearLayout collectionfoot;
	RelativeLayout feedbackpage;

	private TextView mine_cart;//我的购物车数量

	private MemberObj memberObj;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

//
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.myfragment, null);
		initView();
		registerBoradcastReceiver();
		getCartNum();//获得购物车数量

		getMember();
		return view;
	}

	//广播接收动作
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("cart_success")) {
				getCartNum();
			}
		}
	};

	//注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("cart_success");
		//注册广播
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}

	void initView(){
		tobepaid = (ImageView) view.findViewById(R.id.tobepaid);
		imgtxt = (LinearLayout) view.findViewById(R.id.imgtxt);
		username = (TextView) view.findViewById(R.id.username);
		setting = (RelativeLayout) view.findViewById(R.id.setting);
		mysetting = (RoundImageView) view.findViewById(R.id.mysetting);
		collectionfoot = (LinearLayout) view.findViewById(R.id.collectionfoot);
		tobeshipped = (ImageView) view.findViewById(R.id.tobeshipped);
		tobereceive = (ImageView) view.findViewById(R.id.tobereceive);
		tobeevaluate = (ImageView) view.findViewById(R.id.tobeevaluate);
		toberefund = (ImageView) view.findViewById(R.id.toberefund);
		wallet = (ImageView) view.findViewById(R.id.wallet);
		coupons = (ImageView) view.findViewById(R.id.coupons);
		voucher = (ImageView) view.findViewById(R.id.voucher);
		integral = (ImageView) view.findViewById(R.id.integral);
		walletfu = (ImageView) view.findViewById(R.id.walletfu);
		allorder = (RelativeLayout) view.findViewById(R.id.allorder);
		feedbackpage = (RelativeLayout) view.findViewById(R.id.feedbackpage);
		tobepaid.setOnClickListener(this);
		tobeshipped.setOnClickListener(this);
		tobereceive.setOnClickListener(this);
		tobeevaluate.setOnClickListener(this);
		toberefund.setOnClickListener(this);
		wallet.setOnClickListener(this);
		coupons.setOnClickListener(this);
		voucher.setOnClickListener(this);
		integral.setOnClickListener(this);
		walletfu.setOnClickListener(this);
		allorder.setOnClickListener(this);
		mysetting.setOnClickListener(this);
		setting.setOnClickListener(this);
		feedbackpage.setOnClickListener(this);
		mine_cart = (TextView) view.findViewById(R.id.mine_cart);
		view.findViewById(R.id.liner_cart).setOnClickListener(this);
	}

	void getCartNum(){
		//获得购物车商品数量
		List<ShoppingCart> listCart = DBHelper.getInstance(getActivity()).getShoppingList();
		if(listCart!=null){
			mine_cart.setText(String.valueOf(listCart.size()));
		}else {
			mine_cart.setText("0");
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tobepaid:
				Intent intent = new Intent(getActivity(), ToBePaid.class);
				startActivity(intent);
				break;
			case R.id.tobeshipped:
				Intent intent2 = new Intent(getActivity(), ToBeShipped.class);
				startActivity(intent2);
				break;
			case R.id.tobereceive:
				Intent intent3 = new Intent(getActivity(), ToBeReceive.class);
				startActivity(intent3);
				break;
			case R.id.tobeevaluate:
				Intent intent4 = new Intent(getActivity(), ToBeEvaluate.class);
				startActivity(intent4);
				break;
			case R.id.toberefund:
				Intent intent5 = new Intent(getActivity(), ToBeRefund.class);
				startActivity(intent5);
			case R.id.allorder:
				Intent intent6 = new Intent(getActivity(), MineOrdersMngActivity.class);
				startActivity(intent6);

				break;
			case R.id.wallet:
				Intent intent7 = new Intent(getActivity(), Wallet.class);
				startActivity(intent7);
				break;
			case R.id.coupons:
				Intent intent8 = new Intent(getActivity(), Coupons.class);
				startActivity(intent8);
				break;
			case R.id.voucher:
				Intent intent9 = new Intent(getActivity(), VouchersAct.class);
				startActivity(intent9);
				break;
			case R.id.integral:
				Intent intent10 = new Intent(getActivity(), Integral.class);
				startActivity(intent10);
				break;
			case R.id.walletfu:
				Intent intent11 = new Intent(getActivity(), WalletFu.class);
				startActivity(intent11);
				break;
			case R.id.mysetting:
				Intent intent12 = new Intent(getActivity(), PersonMsg.class);
				intent12.putExtra("memberObj", memberObj);
				startActivity(intent12);
				break;
			case R.id.setting:
				Intent intent13 = new Intent(getActivity(), Setting.class);
				startActivity(intent13);
				break;
			case R.id.feedbackpage:
				Intent intent14 = new Intent(getActivity(), FeedBack.class);
				startActivity(intent14);
				break;
			case R.id.liner_cart:
				Intent mineCart =  new Intent(getActivity(), MineCartActivity.class);
				startActivity(mineCart);
				break;
		default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		//todo
		if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
			imgtxt.setVisibility(View.VISIBLE);
			collectionfoot.setVisibility(View.VISIBLE);
//			username.setText(getGson().fromJson(getSp().getString("nick_name", ""), String.class));
		}
		super.onResume();
	}

	void getMember(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.GET_MEMBER_INFO_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									MemberObjData data = getGson().fromJson(s, MemberObjData.class);
									memberObj = data.getData();
									initData();
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
				params.put("user_name", getGson().fromJson(getSp().getString("mobile", ""), String.class));
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

	void initData(){
		//
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + memberObj.getCover(), mysetting, ThirdApplication.txOptions, animateFirstListener);
		username.setText(memberObj.getNick_name());


	}
}
