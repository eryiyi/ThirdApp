package com.example.thirdapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseFragment;

public class MyMsgTitleFragment extends BaseFragment {
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.mymsgtitle, null);
		return view;
	}
}
