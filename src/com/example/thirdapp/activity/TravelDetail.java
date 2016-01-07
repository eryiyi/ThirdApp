package com.example.thirdapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.module.TravelObj;
import com.example.thirdapp.speek.JsonParser;
import com.example.thirdapp.util.StringUtil;
import com.iflytek.cloud.speech.*;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TravelDetail extends BaseActivity implements OnClickListener{
	private Toast mToast;
	ImageView back;
	ImageView speek;
	EditText edit;
	private RecognizerDialog iatDialog;
	private SpeechSynthesizer mSpeechSynthesizer;
	private static final String APP_ID = "appid=53364cbc";

	private TravelObj travel;
	private TextView name;
	private TextView collection;
	private TextView datetime;
	private TextView browse;
	private TextView content;
	private ImageView img;
	private ImageView collectionImg;//收藏按钮
	private ImageView commentImg;//评论按钮
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();


	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		travel = (TravelObj) getIntent().getExtras().get("travel");
		setContentView(R.layout.traveldetail);
		back = (ImageView) findViewById(R.id.back);
		edit = (EditText) findViewById(R.id.edit);
		speek = (ImageView) findViewById(R.id.speek);
		iatDialog = new RecognizerDialog(this);
		mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(this);
		// 用户登录
//		SpeechUser.getUser().login(TravelDetail.this, null, null, APP_ID, listener);
		speek.setOnClickListener(this);
		back.setOnClickListener(this);

		name = (TextView)this.findViewById(R.id.name);
		datetime = (TextView)this.findViewById(R.id.datetime);
		collection = (TextView)this.findViewById(R.id.collection);
		browse = (TextView)this.findViewById(R.id.browse);
		content = (TextView)this.findViewById(R.id.content);
		img = (ImageView)this.findViewById(R.id.img);
		collectionImg = (ImageView)this.findViewById(R.id.collectionImg);
		commentImg = (ImageView)this.findViewById(R.id.commentImg);

		name.setText(travel.getTitle()==null?"":travel.getTitle());
		browse.setText("浏览:"+(travel.getView_num()==null?"":travel.getView_num()));
		datetime.setText((travel.getRegister_date()==null?"":travel.getRegister_date()));
		collection.setText(travel.getCollect_num()==null?"0":travel.getCollect_num());
		content.setText(travel.getContent()==null?"":travel.getContent());
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + travel.getImage(), img, ThirdApplication.options, animateFirstListener);

		this.findViewById(R.id.navigation).setOnClickListener(this);
		this.findViewById(R.id.phone).setOnClickListener(this);
		this.findViewById(R.id.heart).setOnClickListener(this);
		collectionImg.setOnClickListener(this);
		commentImg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.speek:
			showIatDialog();
			break;
		case R.id.navigation:
			//导航
			break;
		case R.id.phone:
			if(!StringUtil.isNullOrEmpty(travel.getTel())){
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + travel.getTel()));
				startActivity(intent);
			}else {
				showMsg(TravelDetail.this, "电话格式不正确");
			}
			break;
		case R.id.collectionImg:
			//收藏
			addFavour();
			break;
		case R.id.commentImg:
			//评论
			if(StringUtil.isNullOrEmpty(edit.getText().toString())){
				showMsg(TravelDetail.this, "请输入评论内容");
				return;
			}
			addComment();
			break;
		default:
			break;
		}
	}

	void addComment(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.COMMENT_TRIP_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code1 =  jo.getString("code");
								if(Integer.parseInt(code1) == 200){
									Toast.makeText(TravelDetail.this, "评论成功", Toast.LENGTH_SHORT).show();
									edit.setText("");
								}else {
									Toast.makeText(TravelDetail.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							}catch (Exception e){
								e.printStackTrace();
							}

						} else {
							Toast.makeText(TravelDetail.this, "收藏失败", Toast.LENGTH_SHORT).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(TravelDetail.this, "收藏失败", Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("trip_id", travel.getId());
				params.put("content", edit.getText().toString());
				params.put("user_name", getGson().fromJson(getSp().getString("user_name", ""), String.class));
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

	void addFavour(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.COLLECT_TRIP_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code1 =  jo.getString("code");
								if(Integer.parseInt(code1) == 200){
									Toast.makeText(TravelDetail.this, "收藏成功", Toast.LENGTH_SHORT).show();
									collection.setText(String.valueOf((Integer.parseInt(travel.getCollect_num() == null ? "0" : travel.getCollect_num())+1)));
								}else {
									Toast.makeText(TravelDetail.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
								}
							}catch (Exception e){
								e.printStackTrace();
							}

						} else {
							Toast.makeText(TravelDetail.this, "收藏失败", Toast.LENGTH_SHORT).show();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(TravelDetail.this, "收藏失败", Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("trip_id", travel.getId());
				params.put("user_name", getGson().fromJson(getSp().getString("user_name", ""), String.class));
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


	protected void showIatDialog() {
		// TODO Auto-generated method stub
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
		edit.setText("");
		// 显示听写对话框
		iatDialog.setListener(recognizerDialogListener);
		iatDialog.show();
		showTip("请开始说话...");
	}
	
	RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {

		@Override
		public void onResult(RecognizerResult arg0, boolean arg1) {
			// TODO Auto-generated method stub
			String text = JsonParser.parseIatResult(arg0.getResultString());
			edit.append(text);
			edit.setSelection(edit.length());
		}

		@Override
		public void onError(SpeechError arg0) {
			// TODO Auto-generated method stub

		}
	};
	
	protected void synthetizeInSilence() {
		// TODO Auto-generated method stub
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
				Toast.makeText(TravelDetail.this, "登录失败", Toast.LENGTH_SHORT)
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
				// TODO Auto-generated method stub

			}

			@Override
			public void onSpeakProgress(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSpeakPaused() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSpeakBegin() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted(SpeechError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
				// TODO Auto-generated method stub

			}
		};
}
