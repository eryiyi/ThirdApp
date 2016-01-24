package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.example.thirdapp.adapter.CommentAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.bean.CommentBean;
import com.example.thirdapp.data.HotGoodsObjData;
import com.example.thirdapp.data.HotGoodsObjSingleData;
import com.example.thirdapp.db.DBHelper;
import com.example.thirdapp.db.ShoppingCart;
import com.example.thirdapp.module.CommentObj;
import com.example.thirdapp.module.HotGoodsObj;
import com.example.thirdapp.util.DateUtil;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import com.example.thirdapp.view.MyListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComDetail extends BaseActivity implements OnClickListener{
	private static ArrayList<ImageView> imageviews;
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	ImageView imageView4;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private RadioButton radioButton4;
	ViewPager viewpager;
	MyListView commentlv;
	List<CommentObj> list;
	CommentAdapter adapter;
	ImageView back;
	private String product_id;
	private HotGoodsObj hotGoodsObj;
	LayoutInflater inflater;
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private TextView comname;//名称
	private TextView comsale;//月销
	private TextView comprice;//jiage
	private TextView comprcieoriginal;//jiage

	//底部
	private ImageView add_cart;//加入
	private TextView number ;//数量
	private ImageView mine_favour_img;


	private ImageView comment;//评论按钮

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		product_id = getIntent().getExtras().getString("product_id");
		setContentView(R.layout.comdetail);
		initView();
		//评论
		list = new ArrayList<CommentObj>();
		adapter = new CommentAdapter(this, list);
		commentlv.setAdapter(adapter);
		getDataById();
		getCartNum();
	}
	void initView(){
		back = (ImageView) findViewById(R.id.back);
		viewpager = (ViewPager) findViewById(R.id.imageviewpager);
		commentlv = (MyListView) findViewById(R.id.commentlv);
		radioButton1 = (RadioButton) findViewById(R.id.radio1);
		radioButton2 = (RadioButton) findViewById(R.id.radio2);
		radioButton3 = (RadioButton) findViewById(R.id.radio3);
		radioButton4 = (RadioButton) findViewById(R.id.radio4);
		back.setOnClickListener(this);
		radioButton1.setOnClickListener(this);
		radioButton2.setOnClickListener(this);
		radioButton3.setOnClickListener(this);
		radioButton4.setOnClickListener(this);
		imageviews = new ArrayList<ImageView>();
		inflater =getLayoutInflater();

		comname = (TextView) this.findViewById(R.id.comname);
		comsale = (TextView) this.findViewById(R.id.comsale);
		comprice = (TextView) this.findViewById(R.id.comprice);
		comprcieoriginal = (TextView) this.findViewById(R.id.comprcieoriginal);

		add_cart = (ImageView) this.findViewById(R.id.add_cart);
		number = (TextView) this.findViewById(R.id.number);
		add_cart.setOnClickListener(this);

		this.findViewById(R.id.foot_dianpu).setOnClickListener(this);
		this.findViewById(R.id.comment).setOnClickListener(this);
		this.findViewById(R.id.share).setOnClickListener(this);
		this.findViewById(R.id.foot_pay).setOnClickListener(this);
		mine_favour_img = (ImageView) this.findViewById(R.id.mine_favour_img);
		comment = (ImageView) this.findViewById(R.id.comment);
		mine_favour_img.setOnClickListener(this);
		comment.setOnClickListener(this);
	}

	void getDataById(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.PRODUCT_DETAIL_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									HotGoodsObjSingleData data = getGson().fromJson(s, HotGoodsObjSingleData.class);
									hotGoodsObj = data.getData();
									list.clear();
									list.addAll(hotGoodsObj.getComment_data());
									adapter.notifyDataSetChanged();
									initData();
								}else {
									Toast.makeText(ComDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(ComDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
						}

					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

						Toast.makeText(ComDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("product_id", product_id);
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
		imageView1 = (ImageView) inflater.inflate(R.layout.imageitem, null);
		imageView2 = (ImageView) inflater.inflate(R.layout.imageitem, null);
		imageView3 = (ImageView) inflater.inflate(R.layout.imageitem, null);
		imageView4 = (ImageView) inflater.inflate(R.layout.imageitem, null);

		imageView1.setImageResource(R.drawable.demo7);
		imageView2.setImageResource(R.drawable.demo5);
		imageView3.setImageResource(R.drawable.demo4);
		imageView4.setImageResource(R.drawable.demo3);
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + hotGoodsObj.getProduct_pic(), imageView1, ThirdApplication.options, animateFirstListener);
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + hotGoodsObj.getProduct_pic1(), imageView2, ThirdApplication.options, animateFirstListener);
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + hotGoodsObj.getProduct_pic2(), imageView3, ThirdApplication.options, animateFirstListener);
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + hotGoodsObj.getProduct_pic3(), imageView4, ThirdApplication.options, animateFirstListener);

		imageviews.add(imageView1);
		imageviews.add(imageView2);
		imageviews.add(imageView3);
		imageviews.add(imageView4);

		viewpager.setAdapter(mPagerAdapter);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0 % imageviews.size()) {
					case 0:
						radioButton1.setChecked(true);
						break;
					case 1:
						radioButton2.setChecked(true);
						break;
					case 2:
						radioButton3.setChecked(true);
						break;
					case 3:
						radioButton4.setChecked(true);
						break;
					default:
						break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		comname.setText(hotGoodsObj.getProduct_name()==null?"":hotGoodsObj.getProduct_name());
		comsale .setText("月销"+hotGoodsObj.getSale_num()+"笔");
		comprice.setText("现价:￥"+(hotGoodsObj.getPrice_tuangou()==null?"":hotGoodsObj.getPrice_tuangou()));
		comprcieoriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
		comprcieoriginal.setText("原价:￥"+(hotGoodsObj.getPrice()==null?"":hotGoodsObj.getPrice()));

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.radio1:
				viewpager.setCurrentItem(0);
				break;
			case R.id.radio2:
				viewpager.setCurrentItem(1);
				break;
			case R.id.radio3:
				viewpager.setCurrentItem(2);
				break;
			case R.id.radio4:
				viewpager.setCurrentItem(3);
				break;
			case R.id.back:
				ComDetail.this.finish();
				break;
			case R.id.add_cart:
				if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
					saveCart();
				}else {
					Intent intent = new Intent(ComDetail.this, Logon.class);
					intent.putExtra("skip", 1);
					startActivity(intent);
				}
				break;
			case R.id.mine_favour_img:
				//喜欢
				if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
					//
					progressDialog = new CustomProgressDialog(ComDetail.this, "", R.anim.frame_paopao);
					progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					progressDialog.setCancelable(false);
					progressDialog.setIndeterminate(true);
					progressDialog.show();
					mine_favour_img.setClickable(false);
					saveFavour();
				}else {
					Intent intent = new Intent(ComDetail.this, Logon.class);
					intent.putExtra("skip", 1);
					startActivity(intent);
				}
				break;
			case R.id.foot_dianpu:
				//店铺
				break;
			case R.id.comment:
				//点击了评论按钮
				if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
					Intent reply = new Intent(ComDetail.this, AddProductCommentActivity.class);
					reply.putExtra("product_id",product_id);
					startActivity(reply);
				}else {
					Intent intent = new Intent(ComDetail.this, Logon.class);
					intent.putExtra("skip", 1);
					startActivity(intent);
				}
				break;
			case R.id.share:
				//分享
				break;
			case R.id.foot_pay:
				//支付
				if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
					Intent orderMakeView = new Intent(ComDetail.this, OrderMakeActivity.class);
					ArrayList<ShoppingCart> arrayList = new ArrayList<ShoppingCart>();

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

					arrayList.add(shoppingCart);
					if(arrayList !=null && arrayList.size() > 0){
						orderMakeView.putExtra("listsgoods",arrayList);
						startActivity(orderMakeView);
					}else{
						Toast.makeText(ComDetail.this,R.string.cart_error_one,Toast.LENGTH_SHORT).show();
					}
				}else {
					Intent intent = new Intent(ComDetail.this, Logon.class);
					intent.putExtra("skip", 1);
					startActivity(intent);
				}
				break;
		default:
			break;
		}
		
	}



	//保存购物车
	void saveCart(){
		//先判断是否已经加入购物车
		if(DBHelper.getInstance(ComDetail.this).isSaved(hotGoodsObj.getProduct_id())){
			//如果你存在
			Toast.makeText(ComDetail.this, R.string.add_cart_is, Toast.LENGTH_SHORT).show();
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
			DBHelper.getInstance(ComDetail.this).addShoppingToTable(shoppingCart);
			Toast.makeText(ComDetail.this, R.string.add_cart_success, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent("cart_success");
			ComDetail.this.sendBroadcast(intent);
			getCartNum();
		}
	}

	void getCartNum(){
		//获得购物车商品数量
		number.setVisibility(View.VISIBLE);
		List<ShoppingCart> listCart = DBHelper.getInstance(ComDetail.this).getShoppingList();
		if(listCart!=null){
			number.setText(String.valueOf(listCart.size()));
		}else {
			number.setText("0");
		}
	}


	PagerAdapter mPagerAdapter = new PagerAdapter(){

        @Override
        //获取当前窗体界面数
        public int getCount() {
            return imageviews.size();
        }

        @Override
        //断是否由对象生成界面
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }
        //是从ViewGroup中移出当前View
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            ((ViewPager) arg0).removeView(imageviews.get(arg1));  
        }  
        
        //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
        public Object instantiateItem(View arg0, int arg1){
            ((ViewPager)arg0).addView(imageviews.get(arg1));
            return imageviews.get(arg1);                
        }
    };


	void saveFavour(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.PRODUCT_COLLECT_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									Toast.makeText(ComDetail.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}else {
									Toast.makeText(ComDetail.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(ComDetail.this, "操作失败，请稍后重试", Toast.LENGTH_SHORT).show();
						}
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						mine_favour_img.setClickable(true);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						mine_favour_img.setClickable(true);
						Toast.makeText(ComDetail.this, "操作失败，请稍后重试", Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("user_name", getGson().fromJson(getSp().getString("mobile", ""), String.class));
				params.put("product_id", product_id);
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
