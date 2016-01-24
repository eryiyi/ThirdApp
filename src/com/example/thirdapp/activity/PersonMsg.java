package com.example.thirdapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.thirdapp.MainActivity;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.adapter.AnimateFirstDisplayListener;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.module.MemberObj;
import com.example.thirdapp.sharedprefercens.SharedPrefsUtil;
import com.example.thirdapp.upload.CommonUtil;
import com.example.thirdapp.util.FileUtils;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CompressPhotoUtil;
import com.example.thirdapp.view.SelectPhoWindow;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class PersonMsg extends BaseActivity implements OnClickListener{

	RelativeLayout propertyphone;
	private MemberObj memberObj;

	private ImageView icon;

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

	private TextView zhanghao;
	private TextView xiaoqu;
	private TextView nicheng;
	private TextView sex;
	private TextView age;
	private TextView weight;
	private TextView geyan;
	private TextView home;
	private TextView email;
	private TextView qq;


	private SelectPhoWindow deleteWindow;

	private String pics = "";
	private static final File PHOTO_CACHE_DIR = new File(Environment.getExternalStorageDirectory() + "/liangxun/PhotoCache");
	Bitmap photo;
	private String picStr = "";


	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		registerBoradcastReceiver();
		memberObj = (MemberObj) getIntent().getExtras().get("memberObj");
		setContentView(R.layout.personmsg);

		propertyphone = (RelativeLayout) findViewById(R.id.propertyphone);

		propertyphone.setOnClickListener(this);

		icon = (ImageView) this.findViewById(R.id.icon);
		icon.setOnClickListener(this);
		this.findViewById(R.id.mine_address).setOnClickListener(this);
		this.findViewById(R.id.back).setOnClickListener(this);

		imageLoader.displayImage(InternetURL.INTERNAL_PIC + memberObj.getCover(), icon, ThirdApplication.txOptions, animateFirstListener);

		zhanghao = (TextView) this.findViewById(R.id.zhanghao);
		xiaoqu = (TextView) this.findViewById(R.id.xiaoqu);
		nicheng = (TextView) this.findViewById(R.id.nicheng);
		sex = (TextView) this.findViewById(R.id.sex);
		age = (TextView) this.findViewById(R.id.age);
		weight = (TextView) this.findViewById(R.id.weight);
		geyan = (TextView) this.findViewById(R.id.geyan);
		home = (TextView) this.findViewById(R.id.home);
		email = (TextView) this.findViewById(R.id.email);
		qq = (TextView) this.findViewById(R.id.qq);


		initData();

		nicheng.setOnClickListener(this);
		age.setOnClickListener(this);
		sex.setOnClickListener(this);
		weight.setOnClickListener(this);
		geyan.setOnClickListener(this);
		home.setOnClickListener(this);
		email.setOnClickListener(this);
		qq.setOnClickListener(this);

	}

	void initData(){
		zhanghao.setText(memberObj.getMobile()==null?"":memberObj.getMobile());
		xiaoqu.setText(getGson().fromJson(getSp().getString("community_id", ""), String.class));
		nicheng.setText(memberObj.getNick_name()==null?"":memberObj.getNick_name());

		String sexstr = "";
		if("1".equals(memberObj.getSex())){
			sexstr = "男";
		}
		if("2".equals(memberObj.getSex())){
			sexstr = "女";
		}
		if("-1".equals(memberObj.getSex())){
			sexstr = "保密";
		}

		sex.setText(sexstr);

		weight.setText(memberObj.getWeight()==null?"暂未填写":memberObj.getWeight());

		String str_geyan = memberObj.getGeyan()== null?"":memberObj.getGeyan();
		if(StringUtil.isNullOrEmpty(str_geyan)){
			geyan.setText("暂无格言");
		}else {
			geyan.setText(str_geyan);
		}
		String str_home = memberObj.getBirthday_place()==null?"暂无地址":memberObj.getBirthday_place();
		if(StringUtil.isNullOrEmpty(str_home)){
			home.setText("暂无地址");
		}else {
			home.setText(str_home);
		}
		email.setText(memberObj.getEmail()==null?"":memberObj.getEmail());
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.propertyphone:
//				Intent intent = new Intent(PersonMsg.this, VillageNumber.class);
//				startActivity(intent);
				break;
			case R.id.mine_address:
				//
				Intent addressView = new Intent(PersonMsg.this, MineAddressActivity.class);
				startActivity(addressView);
				break;
			case R.id.back:
				finish();
				break;

			case R.id.nicheng:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
				break;
			case R.id.age:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.sex:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.weight:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.geyan:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.home:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("type", "home");
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.email:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.qq:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.icon:
				// //头像
				ShowPickDialog();
				break;
			default:
				break;
		}
	}
	// 选择相册，相机
	private void ShowPickDialog() {
		deleteWindow = new SelectPhoWindow(PersonMsg.this, itemsOnClickPic);
		//显示窗口
		deleteWindow.showAtLocation(PersonMsg.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

	}

	//为弹出窗口实现监听类
	private View.OnClickListener itemsOnClickPic = new View.OnClickListener() {
		public void onClick(View v) {
			deleteWindow.dismiss();
			switch (v.getId()) {
				case R.id.camera: {
					Intent camera = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					//下面这句指定调用相机拍照后的照片存储的路径
					camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri
							.fromFile(new File(Environment
									.getExternalStorageDirectory(),
									"ppCover.jpg")));
					startActivityForResult(camera, 2);
				}
				break;
				case R.id.mapstorage: {
					Intent mapstorage = new Intent(Intent.ACTION_PICK, null);
					mapstorage.setDataAndType(
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
							"image/*");
					startActivityForResult(mapstorage, 1);
				}
				break;
				default:
					break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			// 如果是直接从相册获取
			case 1:
				if (data != null) {
					startPhotoZoom(data.getData());
				}
				break;
			// 如果是调用相机拍照时
			case 2:
				File temp = new File(Environment.getExternalStorageDirectory()
						+ "/ppCover.jpg");
				startPhotoZoom(Uri.fromFile(temp));
				break;
			// 取得裁剪后的图片
			case 3:
				if (data != null) {
					setPicToView(data);
				}
				break;
			default:
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 *
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * 保存裁剪之后的图片数据
	 *
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			if (photo != null) {
				pics = CompressPhotoUtil.saveBitmap2file(photo, System.currentTimeMillis() + ".jpg", PHOTO_CACHE_DIR);
				if(!StringUtil.isNullOrEmpty(pics)){
//
					icon.setImageBitmap(photo);
					sendCover();
				}
			}
		}
	}


	public void sendCover(){
		Bitmap bm = FileUtils.getSmallBitmap(pics);
		String cameraImagePath = FileUtils.saveBitToSD(bm, System.currentTimeMillis() + ".jpg");
		File f = new File(cameraImagePath);
		Map<String, File> files = new HashMap<String, File>();
		files.put("cover", f);
		Map<String, String> params = new HashMap<String, String>();
		params.put("user_name", getGson().fromJson(getSp().getString("mobile", ""), String.class));
		CommonUtil.addPutUploadFileRequest(
				this,
				InternetURL.UPDATE_MEMBER_URL,
				files,
				params,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code1 = jo.getString("code");
								if (Integer.parseInt(code1) == 200) {
									Intent intent = new Intent("updateSuccessCover");
									sendBroadcast(intent);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

					}
				},
				null);
	}



	//广播接收动作
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (action.equals("updateSuccess")) {
				//
				updateData();
			}
		}
	};

	//注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("updateSuccess");
		//注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}



	void updateData(){
		nicheng.setText(getGson().fromJson(getSp().getString("nick_name", ""), String.class));

		String sexstr = "";
		if("1".equals(getGson().fromJson(getSp().getString("sex", ""), String.class))){
			sexstr = "男";
		}
		if("2".equals(getGson().fromJson(getSp().getString("sex", ""), String.class))){
			sexstr = "女";
		}
		if("-1".equals(getGson().fromJson(getSp().getString("sex", ""), String.class))){
			sexstr = "保密";
		}

		sex.setText(sexstr);

		weight.setText(getGson().fromJson(getSp().getString("weight", ""), String.class));

		String str_geyan = getGson().fromJson(getSp().getString("geyan", ""), String.class);
		if(StringUtil.isNullOrEmpty(str_geyan)){
			geyan.setText("暂无格言");
		}else {
			geyan.setText(str_geyan);
		}
		String str_home = getGson().fromJson(getSp().getString("birthday_place", ""), String.class);
		if(StringUtil.isNullOrEmpty(str_home)){
			home.setText("暂无地址");
		}else {
			home.setText(str_home);
		}
		email.setText(getGson().fromJson(getSp().getString("email", ""), String.class));
	}

}
