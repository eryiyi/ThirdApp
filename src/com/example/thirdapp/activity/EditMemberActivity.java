package com.example.thirdapp.activity;

import android.os.Bundle;
import android.view.View;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.module.MemberObj;

/**
 * Created by Administrator on 2015/12/27.
 */
public class EditMemberActivity extends BaseActivity implements View.OnClickListener {
    private MemberObj memberObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        memberObj = (MemberObj) getIntent().getExtras().get("memberObj");
    }

    @Override
    public void onClick(View v) {

    }

    public void back(View view){
        finish();
    }
}
