package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.adapter.AnimateFirstDisplayListener;
import com.example.thirdapp.adapter.StudydetailAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.StudyObjSingleData;
import com.example.thirdapp.module.AnswerObj;
import com.example.thirdapp.module.StudyObj;
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

public class StudyDetail extends BaseActivity implements OnClickListener{
	ListView lv;
	List<AnswerObj> list;
	StudydetailAdapter adapter;  
	ImageView back;
	StudyObj studyObj;

	private ImageView stdimg;
	private TextView stdname;
	private TextView stdcontent;
	private TextView study_time;
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private TextView sendbtn;
	private EditText replyContent;


	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.studydetail);
		studyObj = (StudyObj) getIntent().getExtras().get("studyObj");
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.lv);
		list = new ArrayList<AnswerObj>();
		list.addAll(studyObj.getAnswer_data());
		adapter = new StudydetailAdapter(this, list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(StudyDetail.this, StudyLast.class);
				AnswerObj answerObj = list.get(position);
				intent.putExtra("answerObj", answerObj);
				startActivity(intent);
			}

		});
		stdimg = (ImageView) this.findViewById(R.id.stdimg);
		stdname = (TextView) this.findViewById(R.id.stdname);
		stdcontent = (TextView) this.findViewById(R.id.stdcontent);
		study_time = (TextView) this.findViewById(R.id.study_time);
		sendbtn = (TextView) this.findViewById(R.id.sendbtn);
		replyContent = (EditText) this.findViewById(R.id.replyContent);
		sendbtn.setOnClickListener(this);
		progressDialog = new CustomProgressDialog(StudyDetail.this , "", R.anim.frame_paopao);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		getData();
	}

	void getData(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.STUDY_DETAIL_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									StudyObjSingleData data = getGson().fromJson(s, StudyObjSingleData.class);
									studyObj = data.getData();
									initData();
								}else {
									Toast.makeText(StudyDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(StudyDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(StudyDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("id" ,studyObj.getId());
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
		case R.id.back:
			StudyDetail.this.finish();
			break;
			case R.id.sendbtn:
				if(StringUtil.isNullOrEmpty(replyContent.getText().toString())){
					showMsg(StudyDetail.this,"请输入回答内容");
					return;
				}
				addReply();
				break;

		default:
			break;
		}
	}

	void addReply(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.REPLY_STUDY_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									showMsg(StudyDetail.this,"回复成功，感谢回复");
									replyContent.setText("");
								}else {
									Toast.makeText(StudyDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(StudyDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(StudyDetail.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("question_id" ,studyObj.getId());
				params.put("content" , replyContent.getText().toString());
				params.put("user_name" , getGson().fromJson(getSp().getString("user_name", ""), String.class));
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
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + studyObj.getCover(),stdimg , ThirdApplication.txOptions, animateFirstListener);
		stdname.setText(studyObj.getNick_name()==null?"":studyObj.getNick_name());
		stdcontent.setText(studyObj.getContent()==null?"":studyObj.getContent());
		study_time.setText("回复数量:"+(studyObj.getReply_num()==null?"0":studyObj.getReply_num()));
	}
}
