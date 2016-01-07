package com.example.thirdapp.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
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
import com.example.thirdapp.adapter.ItemGoodsAdapter;
import com.example.thirdapp.adapter.SearchAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.bean.SearchBean;
import com.example.thirdapp.data.HotGoodsObjData;
import com.example.thirdapp.module.HotGoodsObj;
import com.example.thirdapp.speek.JsonParser;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.MyDialog;
import com.iflytek.cloud.speech.*;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends BaseActivity implements OnClickListener{
	GridView gv1;
	GridView gv2;
	ListView lv;
	List<SearchBean> list1;
	List<SearchBean> list2;
	List<HotGoodsObj> list3;
	SearchAdapter adapter1;
	SearchAdapter adapter2;
	ItemGoodsAdapter adapter3;
	ImageView back;
	ImageView speek;
	ImageView delete;
	EditText edit;
	TextView searchproduct;
	private Toast mToast;
	private RecognizerDialog iatDialog;
	private SpeechSynthesizer mSpeechSynthesizer;
	private static final String APP_ID = "appid=53364cbc";
	private String input;
	private String name;
	SharedPreferences share;
	TextView nosearch;
	TextView hotsearch;
	RelativeLayout lastsearch;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchinterface);
		super.onCreate(arg0);
		back = (ImageView) findViewById(R.id.back);
		speek = (ImageView) findViewById(R.id.speek);
		delete = (ImageView) findViewById(R.id.delete);
		edit = (EditText) findViewById(R.id.edit);
		gv1 = (GridView) findViewById(R.id.lastgv);
		gv2 = (GridView) findViewById(R.id.hotgv);
		lv = (ListView) findViewById(R.id.searchdetail);
		lastsearch = (RelativeLayout) findViewById(R.id.lastsearch);
		nosearch = (TextView) findViewById(R.id.nosearch);
		searchproduct = (TextView) findViewById(R.id.searchproduct);
		hotsearch = (TextView) findViewById(R.id.hotsearch);
		list1 = new ArrayList<SearchBean>();
		list2 = new ArrayList<SearchBean>();
		list3 = new ArrayList<HotGoodsObj>();
		share = getSharedPreferences("historyrecord", MODE_PRIVATE);
		name = share.getString("name", "");
		String[] hisArrays = name.split(",");
		if (!"".equals(name)) {
			for (int i = 0; i < hisArrays.length; i++) {
				SearchBean bean = new SearchBean();
				bean.searchtext = hisArrays[i];
				list1.add(bean);
			}
		}
		for (int i = 0; i < 5; i++) {
			SearchBean bean = new SearchBean("测试数据" + i);
			list2.add(bean);
		}
		adapter1 = new SearchAdapter(this, list1);
		adapter2 = new SearchAdapter(this, list2);
		adapter3 = new ItemGoodsAdapter( list3, this);
		gv1.setAdapter(adapter1);
		gv2.setAdapter(adapter2);
		iatDialog = new RecognizerDialog(this);
		mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(this);
		mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);
		// 用户登录
		SpeechUser.getUser().login(SearchActivity.this, null, null, APP_ID, listener);
		searchproduct.setOnClickListener(this);
		delete.setOnClickListener(this);
		back.setOnClickListener(this);
		speek.setOnClickListener(this);
		gv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				edit.setText(list1.get(position).searchtext);
			}
		});
		
		gv2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				edit.setText(list2.get(position).searchtext);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			SearchActivity.this.finish();
			break;
		case R.id.speek:
			showIatDialog();
			break;
		case R.id.searchproduct:
			input = edit.getText().toString().trim();
			if (edit.getText().toString().trim() == null || "".equals(edit.getText().toString().trim())) {
				Toast.makeText(SearchActivity.this, "请输入商品信息", Toast.LENGTH_SHORT).show();
			} else {
				System.out.println("share.getString.name = " + share.getString("name", ""));
				System.out.println("edittext.getText().toString().trim() = " + edit.getText().toString().trim());
				System.out.println("share.getInt = " + share.getInt("copyname", 0));
				saveHistory("name", edit);
			}
			getSearchProduct();
			break;
		case R.id.delete:
			showMyDialog();
		default:
			break;
		}
	}
	
	private void showMyDialog() {
		MyDialog dialog = new MyDialog(this);
		//dialog.setIcon(R.drawable.a008);
		dialog.setTitle("删除搜索记录");
		dialog.setMessage("   确定删除所有搜索记录吗？   ");
		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		
		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				share.edit().clear().commit();
				list1.clear();
				adapter1.notifyDataSetChanged();
			}
		});
		dialog.show();
	}
	
//	public void getSearchProduct(){
//		HttpParams params = new HttpParams();
//		params.put("community_id", 1);
//		params.put("content", edit.getText().toString());
//		HttpClientUtils.getInstance().get(ServerId.serveradress2, ServerId.getLikeProducts, params, new AsyncHttpResponseHandler(){
//			@Override
//			public void onSuccess(JSONObject jsonObject) {
//				System.out.println("jsonObject = " + jsonObject);
//				JSONArray jsonArray = jsonObject.optJSONArray("data");
//				if (jsonArray != null) {
//					for (int i = 0; i < jsonArray.length(); i++) {
//						CommodityBean bean = new CommodityBean();
//						JSONObject object = jsonArray.optJSONObject(i);
//						bean.imagebig = object.optString("product_pic");
//						bean.name = object.optString("product_name");
//						bean.originalprice = object.optString("price");
//						bean.price = object.optString("price_tuangou");
//						list3.add(bean);
//					}
//				}
//				Message message = new Message();
//				message.what = 123;
//				handler.sendMessage(message);
//			}
//		});
//	}

	void getSearchProduct(){
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
									list3 = data.getData();
									adapter3.notifyDataSetChanged();
								}else {
									Toast.makeText(SearchActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(SearchActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(SearchActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
	
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 123:
				if (list3.size() == 0) {
					lastsearch.setVisibility(View.GONE);
					gv1.setVisibility(View.GONE);
					gv2.setVisibility(View.GONE);
					hotsearch.setVisibility(View.GONE);
					nosearch.setVisibility(View.VISIBLE);
				}else {
					lastsearch.setVisibility(View.GONE);
					gv1.setVisibility(View.GONE);
					gv2.setVisibility(View.GONE);
					hotsearch.setVisibility(View.GONE);
					lv.setVisibility(View.VISIBLE);
					lv.setAdapter(adapter3);
				}
				break;
			default:
				break;
			}
			return false;
		}
	});
	
	public void saveHistory(String field, EditText edit){
		input = edit.getText().toString().trim();
		name = share.getString(field, "");
		if (!name.contains(input + ",")) {
			StringBuilder builder = new StringBuilder(name);
			builder.insert(0, input + ",");
			share.edit().putString("name", builder.toString()).commit();
		}
	}
	
	protected void showIatDialog() {
		if (null == iatDialog) {
			// 初始化听写Dialog
			iatDialog = new RecognizerDialog(this);
		}

		// 清空Grammar_ID，防止识别后进行听写时Grammar_ID的干扰
		iatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
		// 设置听写Dialog的引擎
		iatDialog.setParameter(SpeechConstant.DOMAIN, "iat");
		iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		// 清空结果显示框.
		edit.setText(null);
		// 显示听写对话框
		iatDialog.setListener(recognizerDialogListener);
		iatDialog.show();
		showTip("请开始说话...");
	}
	
	RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {

		@Override
		public void onResult(RecognizerResult arg0, boolean arg1) {
			String text = JsonParser.parseIatResult(arg0.getResultString());
			edit.append(text);
			edit.setSelection(edit.length());
		}

		@Override
		public void onError(SpeechError arg0) {

		}
	};
	
	protected void synthetizeInSilence() {
		if (null == mSpeechSynthesizer) {
			// 创建合成对象.
			mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(this);
		}
		mSpeechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
		mSpeechSynthesizer.setParameter(SpeechConstant.SPEED, "50");
		mSpeechSynthesizer.setParameter(SpeechConstant.VOLUME, "80");
		mSpeechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
		Editable editable = edit.getText();
		String source = null;
		if (null != editable) {
			source = editable.toString();
		}
		// 进行语音合成.
		mSpeechSynthesizer.startSpeaking(source, mTtsListener);
		showTip("正在缓冲");
	}
	

	/**
	 * 用户登录回调监听器.
	 */
	private SpeechListener listener = new SpeechListener() {

		@Override
		public void onData(byte[] arg0) {
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error != null) {
				Toast.makeText(SearchActivity.this, "登录失败", Toast.LENGTH_SHORT)
						.show();

			}
		}

		@Override
		public void onEvent(int arg0, Bundle arg1) {
		}
	};
	
	// 提示框
		private void showTip(String str) {
			if (!TextUtils.isEmpty(str)) {
				mToast.setText(str);
				mToast.show();
			}
		}
		
		private SynthesizerListener mTtsListener = new SynthesizerListener() {

			@Override
			public void onSpeakResumed() {

			}

			@Override
			public void onSpeakProgress(int arg0, int arg1, int arg2) {

			}

			@Override
			public void onSpeakPaused() {

			}

			@Override
			public void onSpeakBegin() {

			}

			@Override
			public void onCompleted(SpeechError arg0) {

			}

			@Override
			public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {

			}
		};
}
