package com.example.thirdapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.fragment.ConBottomFragment;
import com.example.thirdapp.fragment.ConBottomFragment.OnConBottomClickListener;
import com.example.thirdapp.fragmentdetail.Convenience1;
import com.example.thirdapp.fragmentdetail.Convenience2;
import com.example.thirdapp.fragmentdetail.Convenience3;
import com.example.thirdapp.module.BianminTypeObj;

public class Convenience extends BaseActivity implements OnConBottomClickListener{
	ConBottomFragment bottomFragment;
	private static FragmentManager fManager;
	private FragmentTransaction transaction;
	private static Convenience1 fmc1;
	private static Convenience2 fmc2;
	private static Convenience3 fmc3;
	ImageView back;
	public static BianminTypeObj bianminTypeObj;
	public static String typeid;
	private TextView title;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.convenience);
		bianminTypeObj = (BianminTypeObj) getIntent().getExtras().get("bianminTypeObj");
		typeid  = bianminTypeObj.getId();
		fmc1 = null;
		fmc2 = null;
		fmc3 = null;
		back = (ImageView) findViewById(R.id.back);
		fManager = getSupportFragmentManager();
		setChioceItem(0);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Convenience.this.finish();
			}
		});
		bottomFragment = (ConBottomFragment) getSupportFragmentManager().findFragmentById(R.id.conbottombar);
		bottomFragment.setConBottomClickListener(this);
		bottomFragment.setSelected(0);
		title = (TextView) this.findViewById(R.id.title);
		title.setText(bianminTypeObj.getCat_name()==null?"":bianminTypeObj.getCat_name());
	}
	
	@Override
	public void onConBottomClick(View v, int position) {
		switch (position) {
		case ConBottomFragment.conbottom1:
			setChioceItem(0);
			break;
		case ConBottomFragment.conbottom2:
			setChioceItem(1);
			break;
		case ConBottomFragment.conbottom3:
			setChioceItem(2);
			break;
		default:
			break;
		}
	}
	
	public void setChioceItem(int index) {
		transaction = fManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 0:
			if (fmc1 == null) {
				fmc1 = new Convenience1();
				transaction.add(R.id.concontent, fmc1);
			} else {
				transaction.show(fmc1);
			}
			break;
		case 1:
			if (fmc2 == null) {
				fmc2 = new Convenience2();
				transaction.add(R.id.concontent, fmc2);
			} else {
				transaction.show(fmc2);
			}
			break;
		case 2:
			if (fmc3 == null) {
				fmc3 = new Convenience3();
				transaction.add(R.id.concontent, fmc3);
			} else {
				transaction.show(fmc3);
			}
			break;
		}
		transaction.commit();
	}
	
	private void hideFragments(FragmentTransaction transaction) {
		if (fmc1 != null) {
			transaction.hide(fmc1);
		}
		if (fmc2 != null) {
			transaction.hide(fmc2);
		}
		if (fmc3 != null) {
			transaction.hide(fmc3);
		}
	}
	
	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		if (fmc1 == null && fragment instanceof Convenience1) {  
			fmc1 = (Convenience1)fragment;  
	    }else if (fmc2 == null && fragment instanceof Convenience2) {  
	    	fmc2 = (Convenience2)fragment;  
	    }else if (fmc3 == null && fragment instanceof Convenience3) {  
	    	fmc3 = (Convenience3)fragment;  
	    }   
	}
	

}
