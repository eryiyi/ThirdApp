package com.example.thirdapp.base;

import android.os.Bundle;
import com.example.thirdapp.view.CustomProgressDialog;

/**
 * Created by liuzwei on 2015/1/17.
 */
public class BaseFragment extends MyBaseFragment {
    public CustomProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
