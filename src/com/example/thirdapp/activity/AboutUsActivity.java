package com.example.thirdapp.activity;

import android.os.Bundle;
import android.view.View;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;

/**
 * Created by Administrator on 2016/1/24.
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abouus);

        this.findViewById(R.id.back).setOnClickListener(this);
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
