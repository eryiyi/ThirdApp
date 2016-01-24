package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.MainActivity;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.util.DataCleanManager;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;
import com.zxing.decoding.Intents;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Setting extends BaseActivity implements View.OnClickListener,Runnable{
	private static final String ATTR_PACKAGE_STATS="PackageStats";
	private TextView huncun;
	private TextView version;
	private ImageView select_friend;

	public CustomProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.setting);

		this.findViewById(R.id.back).setOnClickListener(this);
		this.findViewById(R.id.mineAddress).setOnClickListener(this);
		this.findViewById(R.id.checkUpdate).setOnClickListener(this);
		this.findViewById(R.id.huancunliner).setOnClickListener(this);
		version = (TextView) this.findViewById(R.id.version);
		huncun = (TextView) this.findViewById(R.id.huncun);
		try {
			version.setText(getVersionName()==null?"V1.0":getVersionName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		select_friend = (ImageView) this.findViewById(R.id.select_friend);
		select_friend.setOnClickListener(this);

		String is_friend_allow = getGson().fromJson(getSp().getString("is_friend_allow", ""), String.class);
		if("1".equals(is_friend_allow)){
			select_friend.setImageResource(R.drawable.checkedtrue);
		}else {
			select_friend.setImageResource(R.drawable.checkedtfalse);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.back:
				finish();
				break;
			case R.id.mineAddress:
				Intent addressView = new Intent(Setting.this, MineAddressActivity.class);
				startActivity(addressView);
				break;
			case R.id.checkUpdate:
				progressDialog = new CustomProgressDialog(Setting.this , "", R.anim.frame_paopao);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressDialog.setCancelable(false);
				progressDialog.setIndeterminate(true);
				progressDialog.show();
				UmengUpdateAgent.forceUpdate(this);
				UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
					@Override
					public void onUpdateReturned(int i, UpdateResponse updateResponse) {
						progressDialog.dismiss();
						switch (i) {
							case UpdateStatus.Yes:
//                                Toast.makeText(mContext, "有新版本发现", Toast.LENGTH_SHORT).show();
								break;
							case UpdateStatus.No:
								Toast.makeText(Setting.this, "已是最新版本", Toast.LENGTH_SHORT).show();
								break;
							case UpdateStatus.Timeout:
								Toast.makeText(Setting.this, "连接超时", Toast.LENGTH_SHORT).show();
								break;
						}
					}
				});
				break;

			case R.id.huancunliner:
				//缓存
				DataCleanManager.cleanInternalCache(Setting.this);
				getpkginfo("com.example.thirdapp");
				// 启动一个线程
				progressDialog = new CustomProgressDialog(Setting.this , "", R.anim.frame_paopao);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressDialog.setCancelable(false);
				progressDialog.setIndeterminate(true);
				progressDialog.show();
				new Thread(Setting.this).start();
				break;
			case R.id.select_friend:
				//设置允许交友
				String is_friend_allow = getGson().fromJson(getSp().getString("is_friend_allow", ""), String.class);
				if("1".equals(is_friend_allow)){
					//允许交友
					save("is_friend_allow", "0");
					select_friend.setImageResource(R.drawable.checkedtfalse);
				}else {
					//不允许
					save("is_friend_allow", "1");
					select_friend.setImageResource(R.drawable.checkedtrue);
					setFriend();
				}
				break;

		}
	}

	private String getVersionName() throws Exception
	{
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
		String version = packInfo.versionName;
		return version;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					String infoString="";
					PackageStats newPs = msg.getData().getParcelable(ATTR_PACKAGE_STATS);
					if (newPs!=null) {
//                        infoString+="应用程序大小: "+formatFileSize(newPs.codeSize);
//                        infoString+="\n数据大小: "+formatFileSize(newPs.dataSize);
						infoString+= formatFileSize(newPs.cacheSize);
					}
					huncun.setText(infoString);
					break;
				default:
					break;
			}
		}
	};
	public void getpkginfo(String pkg){
		PackageManager pm = getPackageManager();
		try {
			Method getPackageSizeInfo = pm.getClass().getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
			getPackageSizeInfo.invoke(pm, pkg,new PkgSizeObserver());
		} catch (Exception e) {
		}
	}

	class PkgSizeObserver extends IPackageStatsObserver.Stub {
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) {
			Message msg = mHandler.obtainMessage(1);
			Bundle data = new Bundle();
			data.putParcelable(ATTR_PACKAGE_STATS, pStats);
			msg.setData(data);
			mHandler.sendMessage(msg);

		}
	}

	/**
	 * 获取文件大小
	 *
	 * @param length
	 * @return
	 */
	public static String formatFileSize(long length) {
		String result = null;
		int sub_string = 0;
		if (length >= 1073741824) {
			sub_string = String.valueOf((float) length / 1073741824).indexOf(
					".");
			result = ((float) length / 1073741824 + "000").substring(0,
					sub_string + 3)
					+ "GB";
		} else if (length >= 1048576) {
			sub_string = String.valueOf((float) length / 1048576).indexOf(".");
			result = ((float) length / 1048576 + "000").substring(0,
					sub_string + 3)
					+ "MB";
		} else if (length >= 1024) {
			sub_string = String.valueOf((float) length / 1024).indexOf(".");
			result = ((float) length / 1024 + "000").substring(0,
					sub_string + 3)
					+ "KB";
		} else if (length < 1024)
			result = Long.toString(length) + "B";
		return result;
	}



	void setFriend(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.SET_OPEN_FRIEND_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								showMsg(Setting.this , jo.getString("msg"));
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							showMsg(Setting.this , "设置失败");
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						showMsg(Setting.this, "设置失败");
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("user_name", getGson().fromJson(getSp().getString("mobile", ""), String.class));
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/x-www-form-urlencoded");
				return params;
			}
		};
		getRequestQueue().add(request);
	}


	@Override
	public void run() {
		try {
			// 3秒后跳转到登录界面
			Thread.sleep(3000);
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			showMsg(Setting.this, "清除缓存成功！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
