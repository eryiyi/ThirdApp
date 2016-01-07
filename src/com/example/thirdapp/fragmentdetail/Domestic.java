package com.example.thirdapp.fragmentdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseFragment;

public class Domestic extends BaseFragment {
	View view;
	ToggleButton togglebutton1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.domestic, null);
		togglebutton1 = (ToggleButton) view.findViewById(R.id.togglebutton1);
		togglebutton1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					
				}else{
					
				}
			}
	  	});
		return view;
	}
}
