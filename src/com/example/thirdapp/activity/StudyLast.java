package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
import com.example.thirdapp.module.AnswerObj;
import com.example.thirdapp.module.StudyObj;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudyLast extends BaseActivity implements OnClickListener{
	ImageView back;
	private AnswerObj answerObj;
	private ImageView goodanswer;
	private ImageView goodanswer2;
	private ImageView stdimage;
	private TextView stdname;
	private TextView t1;
	private TextView t2;
	private TextView t3;
	private TextView cont;
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	StudyObj studyObj;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		studyObj = (StudyObj) getIntent().getExtras().get("studyObj");
		answerObj = (AnswerObj) getIntent().getExtras().get("answerObj");
		setContentView(R.layout.studylast);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);

		stdimage = (ImageView) this.findViewById(R.id.stdimage);
		goodanswer = (ImageView) this.findViewById(R.id.goodanswer);
		goodanswer2 = (ImageView) this.findViewById(R.id.goodanswer2);
		stdname = (TextView) this.findViewById(R.id.stdname);
		t1 = (TextView) this.findViewById(R.id.t1);
		t2 = (TextView) this.findViewById(R.id.t2);
		t3 = (TextView) this.findViewById(R.id.t3);
		cont = (TextView) this.findViewById(R.id.cont);

		imageLoader.displayImage(InternetURL.INTERNAL_PIC + answerObj.getCover() , stdimage , ThirdApplication.txOptions, animateFirstListener);
		stdname.setText(answerObj.getNick_name() == null ? "" : answerObj.getNick_name());
		cont.setText(answerObj.getContent() == null ? "" : answerObj.getContent());
		t1.setText(studyObj.getSupport_num()==null?"0":studyObj.getSupport_num());
		t3.setText(studyObj.getReply_num()==null?"0":studyObj.getReply_num());
		if(studyObj.getBest_reply_id().equals(answerObj.getId())){
			//是最棒答案
			goodanswer.setVisibility(View.VISIBLE);
			goodanswer2.setVisibility(View.VISIBLE);
		}else {
			goodanswer.setVisibility(View.GONE);
			goodanswer2.setVisibility(View.GONE);
		}

		progressDialog = new CustomProgressDialog(StudyLast.this, "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getData();

	}

	public void addSupport(View view){
		//
		if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
			StringRequest request = new StringRequest(
					Request.Method.POST,
					InternetURL.SUPPORT_STUDY_URL,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String s) {
							if (StringUtil.isJson(s)) {
								try {
									JSONObject jo = new JSONObject(s);
									String code =  jo.getString("code");
									if(Integer.parseInt(code) == 200){
										Toast.makeText(StudyLast.this,  "点赞成功", Toast.LENGTH_SHORT).show();
									}else {
										Toast.makeText(StudyLast.this,  jo.getString("msg"), Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							} else {
								Toast.makeText(StudyLast.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
							Toast.makeText(StudyLast.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
						}
					}
			) {
				@Override
				protected Map<String, String> getParams() throws AuthFailureError {
					Map<String, String> params = new HashMap<String, String>();
					params.put("user_name" , getGson().fromJson(getSp().getString("user_name", ""), String.class));
					params.put("question_id" , answerObj.getId());
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
		}else {
			Intent intent = new Intent(StudyLast.this, Logon.class);
			intent.putExtra("skip", 1);
			startActivity(intent);
		}
	}
	public void addFavour(View view){
		//
		if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
		}else {
			Intent intent = new Intent(StudyLast.this, Logon.class);
			intent.putExtra("skip", 1);
			startActivity(intent);
		}
		if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
			StringRequest request = new StringRequest(
					Request.Method.POST,
					InternetURL.COLLECT_STUDY_URL,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String s) {
							if (StringUtil.isJson(s)) {
								try {
									JSONObject jo = new JSONObject(s);
									String code =  jo.getString("code");
									if(Integer.parseInt(code) == 200){
										Toast.makeText(StudyLast.this,  "收藏成功", Toast.LENGTH_SHORT).show();
									}else {
										Toast.makeText(StudyLast.this,  jo.getString("msg"), Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							} else {
								Toast.makeText(StudyLast.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
							Toast.makeText(StudyLast.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
						}
					}
			) {
				@Override
				protected Map<String, String> getParams() throws AuthFailureError {
					Map<String, String> params = new HashMap<String, String>();
					params.put("user_name" , getGson().fromJson(getSp().getString("user_name", ""), String.class));
					params.put("question_id" , answerObj.getId());
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
		}else {
			Intent intent = new Intent(StudyLast.this, Logon.class);
			intent.putExtra("skip", 1);
			startActivity(intent);
		}
	}
	public void addBest(View view){
		//
		if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
		}else {
			Intent intent = new Intent(StudyLast.this, Logon.class);
			intent.putExtra("skip", 1);
			startActivity(intent);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

	void getData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.STUDY_REPLY_DETAIL_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
//									StudyObjData data = getGson().fromJson(s, StudyObjData.class);
//									list.addAll(data.getData());
//									adapter.notifyDataSetChanged();
//									studylv.onLoadComplete();
//									studylv.onRefreshComplete();
								}else {
									Toast.makeText(StudyLast.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(StudyLast.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(StudyLast.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("id" ,answerObj.getId());
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
