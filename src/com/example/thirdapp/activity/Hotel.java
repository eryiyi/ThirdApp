package com.example.thirdapp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.fragmentdetail.Domestic;
import com.example.thirdapp.fragmentdetail.International;

import java.util.ArrayList;
import java.util.List;

public class Hotel extends BaseActivity implements OnClickListener{
	ViewPager viewPager;
	List<Fragment> list = new ArrayList<Fragment>();
	Fragment fm1;
	Fragment fm2;
	private ImageView mIv;// 动画图片
	RadioButton domestic;
	RadioButton international;
	private static int off = 0;
	private static int ll;
	private static int dhBmpW;
	RadioGroup radiogroup;
	private static int position;
	ImageView back;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.hotel);
		initView();
		back = (ImageView) findViewById(R.id.back);
		viewPager = (ViewPager) findViewById(R.id.hotelvp);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		domestic = (RadioButton) findViewById(R.id.domestic);
		international = (RadioButton) findViewById(R.id.international);
		back.setOnClickListener(this);
		domestic.setOnClickListener(this);
		international.setOnClickListener(this);
		//radiogroup.setOnCheckedChangeListener(this);
		fm1 = new Domestic();
		fm2 = new International();
		list.add(fm1);
		list.add(fm2);
		myViewPagerAdapter adapter = new myViewPagerAdapter(getSupportFragmentManager(), list);
		viewPager.setAdapter(adapter);  
		viewPager.setOffscreenPageLimit(1);
		viewPager.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				setSelected(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				int ox = ll*arg0 + arg2/2;
				Animation am = new TranslateAnimation(off, ox,0,0);
				am.setFillAfter(true);
				am.setDuration(300);
				mIv.startAnimation(am);
				off = ox;
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	public void initView(){
		mIv = (ImageView) findViewById(R.id.bluemark);
		//取图的宽和高
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.btn_tab_highlight_middle_bg);
		int w = bm.getWidth();
		dhBmpW = bm.getWidth();  
		
		//取屏幕的宽
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		ll = screenW/2;
		//设置图片
		Matrix matrix = new Matrix();
		matrix.postScale(screenW/(2*w)+1, dhBmpW);			
		Bitmap newbm  = Bitmap.createBitmap(bm, 0, 0, w, dhBmpW, matrix, true);
		
		//设置ImageView的宽
		LayoutParams para;
        para = mIv.getLayoutParams();
        para.width = screenW/2;
        mIv.setLayoutParams(para);
		mIv.setImageBitmap(newbm);
		mIv.setColorFilter(getResources().getColor(R.color.common_blue));
		
	}
	
	class myViewPagerAdapter extends FragmentPagerAdapter {
		List<Fragment> list;

		public myViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			return list.size();
		}
	}

	/*@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.domestic:
			viewPager.setCurrentItem(0, true);
			position = 0;
			break;
		case R.id.international:
			viewPager.setCurrentItem(1, true);
			position = 1;
			break;
		default:
			break;
		}
		setSelected(position);
	}*/
	
	public void setSelected(int pos){
		switch (pos) {
		case 0:
			domestic.setTextColor(getResources().getColor(R.color.common_blue));
			international.setTextColor(getResources().getColor(R.color.black));
			break;
		case 1:
			domestic.setTextColor(getResources().getColor(R.color.black));
			international.setTextColor(getResources().getColor(R.color.common_blue));
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.domestic:
			viewPager.setCurrentItem(0, true);
			position = 0;
			break;
		case R.id.international:
			viewPager.setCurrentItem(1, true);
			position = 1;
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
		setSelected(position);
		
	}
}
