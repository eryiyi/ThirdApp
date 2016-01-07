package com.example.thirdapp.fragmentdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseFragment;

public class International extends BaseFragment {
	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.international, null);
		
		return view;
	}
}
