package com.example.thirdapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.TalkObjData;
import com.example.thirdapp.data.TalkObjSingleData;
import com.example.thirdapp.module.TalkObj;
import com.example.thirdapp.util.RelativeDateFormat;
import com.example.thirdapp.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/23.
 */
public class TalkDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private TextView see;
    private TextView comment;
    private TextView content;
    private TextView louzhu;
    private TextView dateline;
    private TalkObj talkObj;
    private String talkObjId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talk_detail_activity);
        talkObjId = getIntent().getExtras().getString("talkObjId");
        initView();
        getData();
    }

    void getData(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.TALK_DETAIL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code =  jo.getString("code");
                                if(Integer.parseInt(code) == 200){
                                    TalkObjSingleData data = getGson().fromJson(s, TalkObjSingleData.class);
                                    talkObj = data.getData();
                                    initData();
                                }else if(Integer.parseInt(code) == -1){
                                    showMsg(TalkDetailActivity.this, "暂无数据");
                                }
                                else {
                                    Toast.makeText(TalkDetailActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(TalkDetailActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(TalkDetailActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("talk_id", talkObjId);
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


    private void initData() {
        //
        title.setText(talkObj.getTitle());
        see.setText(talkObj.getHit());
        content.setText(talkObj.getContent());
        louzhu.setText(talkObj.getUser_name());
        dateline.setText(RelativeDateFormat.format(Long.parseLong((talkObj.getRegister_date() == null ? "" : talkObj.getRegister_date()) + "000")));
    }

    private void initView() {
        title = (TextView) this.findViewById(R.id.title);
        see = (TextView) this.findViewById(R.id.see);
        comment = (TextView) this.findViewById(R.id.comment);
        louzhu = (TextView) this.findViewById(R.id.louzhu);
        content = (TextView) this.findViewById(R.id.content);
        dateline = (TextView) this.findViewById(R.id.dateline);
        this.findViewById(R.id.addComment).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addComment:
                //添加评论
                Intent addComment = new Intent(TalkDetailActivity.this, AddLtCommentActivity.class);
                addComment.putExtra("talk_id", talkObjId);
                startActivity(addComment);
                break;
        }
    }
    public void back(View view){
        finish();
    }
}

