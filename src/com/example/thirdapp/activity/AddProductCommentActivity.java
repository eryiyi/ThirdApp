package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
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
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/5.
 * 产品评论
 */
public class AddProductCommentActivity extends BaseActivity implements View.OnClickListener{
    private ImageView back;
    private EditText replyContent;
    private TextView sendbtn;
    private ProgressDialog progressDialog;
    private String product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment);
        product_id = getIntent().getExtras().getString("product_id");
        back = (ImageView) this.findViewById(R.id.back);
        replyContent = (EditText) this.findViewById(R.id.replyContent);
        sendbtn = (TextView) this.findViewById(R.id.sendbtn);
        back.setOnClickListener(this);
        sendbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.sendbtn:
                if(StringUtil.isNullOrEmpty(replyContent.getText().toString())){
                    showMsg(AddProductCommentActivity.this, "请输入评论内容");
                    return;
                }
                progressDialog = new ProgressDialog(AddProductCommentActivity.this);
                progressDialog.setMessage("请稍后");
                progressDialog.show();
                addReply();
                break;
        }
    }


    void addReply(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.COMMENT_PRODUCT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code =  jo.getString("code");
                                if(Integer.parseInt(code) == 200){
                                    showMsg(AddProductCommentActivity.this, "评论成功，感谢参与");
                                    finish();
                                }else {
                                    Toast.makeText(AddProductCommentActivity.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(AddProductCommentActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddProductCommentActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("product_id" , product_id);
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


}
