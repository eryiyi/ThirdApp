package com.example.thirdapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.example.thirdapp.activity.Logon;
import com.example.thirdapp.activity.SelectCommunityActivity;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.fragment.*;
import com.example.thirdapp.fragment.BottomFragment.OnBottomClickListener;
import com.example.thirdapp.util.StringUtil;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends BaseActivity implements OnBottomClickListener,AMapLocationListener {

	private LocationManagerProxy mLocationManagerProxy;

	public static BottomFragment bottomFragment;
	private long mExitTime;
	private static FragmentManager fManager;
	private static MainPageFragment fg1;
	private static TravelFragment fg2;
	private static SunFunFragment fg3;
	private static MallFragment fg4;
	private static MyFragment fg5;
	//private static 
	private static int getskip;
	private static int getbottom;
	private FragmentTransaction transaction;
	public static int location;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		UmengUpdateAgent.update(this);
		registerBoradcastReceiver();
		Boolean flag = StringUtil.isOPen(MainActivity.this);
		if(!flag){
			Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivityForResult(intent, 0);
		}
		if(StringUtil.isNullOrEmpty(ThirdApplication.desc)){
			init();
		}
		if(StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("community_id", ""), String.class))){
			//没有小区
			Intent selectAddressView = new Intent(MainActivity.this, SelectCommunityActivity.class);
			startActivity(selectAddressView);
		}
		setContentView(R.layout.activity_main);
		fg1 = null;
		fg2 = null;
		fg3 = null;
		fg4 = null;
		fg5 = null;
		Fragment mainpagetilte = new MainPageTitleFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.titlefragment, mainpagetilte).commit();
		fManager = getSupportFragmentManager();
		getskip = getIntent().getIntExtra("personcenter", 0);
		getbottom = getIntent().getIntExtra("bottom", 0);
		setChioceItem(getskip);
		if (getbottom == 4) {
			Fragment mymsgtitle = new MyMsgTitleFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.titlefragment, mymsgtitle).commit();
		}
		bottomFragment = (BottomFragment) getSupportFragmentManager().findFragmentById(R.id.bottombar);
		bottomFragment.setBottomClickListener(this);

		bottomFragment.setSelected(getbottom);

	}

	@Override
	public void onBottomClick(View v, int position) {
		switch (position) {
		case BottomFragment.MAINPAGE:
			setTitle(0);
			location = 0;
			setChioceItem(0);
			break;
		case BottomFragment.TRAVEL:
			setTitle(1);
			location = 1;
			setChioceItem(1);
			break;
		case BottomFragment.SUNFAN:
			setTitle(2);
			location = 2;
			setChioceItem(2);
			break;
		case BottomFragment.MALL:
			setTitle(3);
			location = 3;
			setChioceItem(3);
			break;
		case BottomFragment.MY:
			if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
				setChioceItem(4);
			}else {
				Intent intent = new Intent(MainActivity.this, Logon.class);
				intent.putExtra("skip", 1);
				startActivity(intent);
			}
			Fragment mymsgtitle = new MyMsgTitleFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.titlefragment, mymsgtitle).commit();
			break;
		default:
			break;
		}
	}

	public void setTitle(int pos){
		switch (pos) {
		case 0:
			Fragment mainpagetilte = new MainPageTitleFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.titlefragment, mainpagetilte).commit();
			break;
		case 1:
			Fragment traveltitle = new TravelTitleFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.titlefragment, traveltitle).commit();
			break;
		case 2:
			Fragment sunfuntitle = new SunFunTitleFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.titlefragment, sunfuntitle).commit();
			break;
		case 3:
			Fragment malltitle = new MallTitleFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.titlefragment, malltitle).commit();
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
			if (fg1 == null) {
				fg1 = new MainPageFragment();
				transaction.add(R.id.thirdappcontent, fg1);
			} else {
				transaction.show(fg1);
			}
			break;
		case 1:
			if (fg2 == null) {
				fg2 = new TravelFragment();
				transaction.add(R.id.thirdappcontent, fg2);
			} else {
				transaction.show(fg2);
			}
			break;
		case 2:
			if (fg3 == null) {
				fg3 = new SunFunFragment();
				transaction.add(R.id.thirdappcontent, fg3);
			} else {
				transaction.show(fg3);
			}
			break;
		case 3:
			if (fg4 == null) {
				fg4 = new MallFragment();
				transaction.add(R.id.thirdappcontent, fg4);
			} else {
				transaction.show(fg4);
			}
			break;
		case 4:
			if (fg5 == null) {
				fg5 = new MyFragment();
				transaction.add(R.id.thirdappcontent, fg5);
			} else {
				transaction.show(fg5);
			}
			break;
		}
		transaction.commit();
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (fg1 != null) {
			transaction.hide(fg1);
		}
		if (fg2 != null) {
			transaction.hide(fg2);
		}
		if (fg3 != null) {
			transaction.hide(fg3);
		}
		if (fg4 != null) {
			transaction.hide(fg4);
		}
		if (fg5 != null) {
			transaction.hide(fg5);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				@SuppressWarnings("unused")
				Object mHelperUtils;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		if (fg1 == null && fragment instanceof MainPageFragment) {   
			fg1 = (MainPageFragment)fragment;  
	    }else if (fg2 == null && fragment instanceof TravelFragment) {  
	    	fg2 = (TravelFragment)fragment;  
	    }else if (fg3 == null && fragment instanceof SunFunFragment) {  
	    	fg3 = (SunFunFragment)fragment;  
	    }else if (fg4 == null && fragment instanceof MallFragment) {  
	    	fg4 = (MallFragment)fragment;  
	    }else if (fg5 == null && fragment instanceof MyFragment) {  
	    	fg5 = (MyFragment)fragment;  
	    }      
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	public void onResume() {
		System.out.println("Logon.flag = " + Logon.flag);
		if (Logon.flag == 1) {
			setChioceItem(location);
			setTitle(location);
			bottomFragment.setSelected(location);
		}
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	


	/**
	 * 初始化定位
	 */
	private void init() {
		// 初始化定位，只采用网络定位
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.setGpsEnable(false);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次,
		// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 60 * 10000, 15, this);

	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {

		if (amapLocation != null
				&& amapLocation.getAMapException().getErrorCode() == 0) {
			ThirdApplication.lat = amapLocation.getLatitude();
			ThirdApplication.lon = amapLocation.getLongitude();
			ThirdApplication.cityName =amapLocation.getCity();
			ThirdApplication.desc =amapLocation.getAddress();

		} else {
			Log.e("AmapErr", "Location ERR:" + amapLocation.getAMapException().getErrorCode());
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		// 移除定位请求
		if(mLocationManagerProxy != null){
			mLocationManagerProxy.removeUpdates(this);
			// 销毁定位
			mLocationManagerProxy.destroy();
		}
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String s, int i, Bundle bundle) {

	}

	@Override
	public void onProviderEnabled(String s) {

	}

	@Override
	public void onProviderDisabled(String s) {

	}


	//广播接收动作
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("select_community")) {
			}

		}
	};

	//注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("select_community");
		//注册广播
		this.registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(mBroadcastReceiver);
	}

}
