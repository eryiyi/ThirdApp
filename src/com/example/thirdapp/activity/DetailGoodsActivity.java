package com.example.thirdapp.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;

/**
 * Created by Administrator on 2016/1/26.
 */
public class DetailGoodsActivity extends BaseActivity implements View.OnClickListener {
    private TextView content;
    private String contentstr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailgoods);
        contentstr = getIntent().getExtras().getString("contentstr");
        this.findViewById(R.id.back).setOnClickListener(this);

        content = (TextView) this.findViewById(R.id.content);
        content.setText(Html.fromHtml(contentstr));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
