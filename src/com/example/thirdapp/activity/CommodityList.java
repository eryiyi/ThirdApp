package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.ItemGoodsAdapter;
import com.example.thirdapp.adapter.OnClickContentItemListener;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.HotGoodsObjData;
import com.example.thirdapp.db.DBHelper;
import com.example.thirdapp.db.ShoppingCart;
import com.example.thirdapp.module.HotGoodsObj;
import com.example.thirdapp.util.DateUtil;
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

public class CommodityList extends BaseActivity implements OnClickListener, OnRefreshListener, OnLoadListener,OnClickContentItemListener{
	List<HotGoodsObj> list;
	MyListViewUpDown listview;
	ItemGoodsAdapter adapter;
	ImageView comprehensive;
	ImageView salevolume;
	ImageView price;
	int flag = 0;
	ImageView back;
	ImageView screening;
	LinearLayout catalog;
	private int flaggone = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.commoditylist);
		back = (ImageView) findViewById(R.id.back);
		comprehensive = (ImageView) findViewById(R.id.comprehensive);
		catalog = (LinearLayout) findViewById(R.id.catalog);
		salevolume = (ImageView) findViewById(R.id.salevolume);
		price = (ImageView) findViewById(R.id.price);
		screening = (ImageView) findViewById(R.id.screening);
		back.setOnClickListener(this);
		comprehensive.setOnClickListener(this);
		salevolume.setOnClickListener(this);
		price.setOnClickListener(this);
		screening.setOnClickListener(this);
		listview = (MyListViewUpDown) findViewById(R.id.comlistlv);
		list = new ArrayList<HotGoodsObj>();

		adapter = new ItemGoodsAdapter( list, this);
		listview.setonRefreshListener(this);
		adapter.setOnClickContentItemListener(this);
		listview.setOnLoadListener(this);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(CommodityList.this, ComDetail.class);
				HotGoodsObj hotGoodsObj = list.get(position-1);
				intent.putExtra("product_id", hotGoodsObj.getProduct_id());
				startActivity(intent);
			}
		});
		progressDialog = new CustomProgressDialog(CommodityList.this, "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("community_id", ""), String.class))){
			getdata();
		}
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
									list.addAll(data.getData());
									adapter.notifyDataSetChanged();
								}else {
									Toast.makeText(CommodityList.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(CommodityList.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(CommodityList.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comprehensive:
			
			break;
		case R.id.salevolume:
			if (flag == 0) {
				salevolume.setImageResource(R.drawable.salevolume);
				flag = 1;
			}else {
				salevolume.setImageResource(R.drawable.salevolume2);
				flag = 0;
			}
			break;
		case R.id.price:
			if (flag == 0) {
				price.setImageResource(R.drawable.price);
				flag = 1;
			}else {
				price.setImageResource(R.drawable.price2);
				flag = 0;
			}
			break;
		case R.id.back:
			CommodityList.this.finish();
			break;
		case R.id.screening:
			if (flaggone == 0) {
				catalog.setVisibility(View.VISIBLE);
				flaggone = 1;
			}else {
				catalog.setVisibility(View.GONE);
				flaggone = 0;
			}
			break;
		default:
			break;
		}
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
				listview.onLoadComplete();
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
				listview.onRefreshComplete();
			};
		}.execute(null, null, null);
	}

	HotGoodsObj hotGoodsObj;
	@Override
	public void onClickContentItem(int position, int flag, Object object) {
		hotGoodsObj = list.get(position);
		switch (flag){
			case 2:
				//
				if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
					saveCart();
				}else {
					Intent intent = new Intent(CommodityList.this, Logon.class);
					intent.putExtra("skip", 1);
					startActivity(intent);
				}
				break;
		}
	}

	//保存购物车
	void saveCart(){
		//先判断是否已经加入购物车
		if(DBHelper.getInstance(CommodityList.this).isSaved(hotGoodsObj.getProduct_id())){
			//如果你存在
			Toast.makeText(CommodityList.this, R.string.add_cart_is, Toast.LENGTH_SHORT).show();
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
			DBHelper.getInstance(CommodityList.this).addShoppingToTable(shoppingCart);
			Toast.makeText(CommodityList.this, R.string.add_cart_success, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent("cart_success");
			CommodityList.this.sendBroadcast(intent);
//			getCartNum();
		}
	}
}
