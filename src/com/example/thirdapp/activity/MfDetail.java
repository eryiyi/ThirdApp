package com.example.thirdapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import android.view.Window;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.adapter.AnimateFirstDisplayListener;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.bean.MfBean;
import com.example.thirdapp.module.FriendObj;
import com.example.thirdapp.module.Photo_data_obj;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.MyGridview;
import com.image.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class MfDetail extends BaseActivity implements OnClickListener{
	private MyGridview albumgv;
	private BottomAdapter adapter;
	private HorizontalScrollView scrollView;
	List<Photo_data_obj> list = new ArrayList<Photo_data_obj>();
	ImageView back;
	// 测试数据 让GridView移动到最右端
	DisplayMetrics dm;
	private int NUM = 3; // 每行显示个数
	private int LIEWIDTH;//每列宽度
	private int LIE = 12;//列数
	private static final int select_test = 20;
	// 每个GridView Item的宽度
	private static final int WIDTH = 50;
	private FriendObj friendObj;

	private ImageView mine_img;
	private TextView mine_support;
	private TextView mine_see;
	private TextView mine_favour;
	private TextView mine_comment;
	private TextView accountnumber;
	private TextView nickname;
	private TextView personal;
	private TextView maxim;
	private TextView hometown;
	private TextView phone;
	private TextView mail;
	private TextView village;
	private TextView mine_name;
	private ImageView mine_img_b;

	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		friendObj = (FriendObj) getIntent().getExtras().get("friendObj");
		setContentView(R.layout.mfdetail);
		initView();
//		MfBean bean = new MfBean(R.drawable.mf1);
//		list.add(bean);
//		MfBean bean2 = new MfBean(R.drawable.mf2);
//		list.add(bean2);
//		MfBean bean3 = new MfBean(R.drawable.mf3);
//		list.add(bean3);
//		MfBean bean4 = new MfBean(R.drawable.mf4);
//		list.add(bean4);
//		MfBean bean5 = new MfBean(R.drawable.mf5);
//		list.add(bean5);
//		MfBean bean6 = new MfBean(R.drawable.mf6);
//		list.add(bean6);

	}

	void initView(){
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		scrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
		albumgv = (MyGridview) findViewById(R.id.albumgv);
		mine_img_b = (ImageView) this.findViewById(R.id.mine_img_b);
		mine_img = (ImageView) this.findViewById(R.id.mine_img);
		mine_support = (TextView) this.findViewById(R.id.mine_support);
		mine_see = (TextView) this.findViewById(R.id.mine_see);
		mine_favour = (TextView) this.findViewById(R.id.mine_favour);
		mine_comment = (TextView) this.findViewById(R.id.mine_comment);
		accountnumber = (TextView) this.findViewById(R.id.accountnumber);
		nickname = (TextView) this.findViewById(R.id.nickname);
		personal = (TextView) this.findViewById(R.id.personal);
		maxim = (TextView) this.findViewById(R.id.maxim);
		hometown = (TextView) this.findViewById(R.id.hometown);
		phone = (TextView) this.findViewById(R.id.phone);
		mail = (TextView) this.findViewById(R.id.mail);
		village = (TextView) this.findViewById(R.id.village);
		mine_name = (TextView) this.findViewById(R.id.mine_name);
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + (friendObj.getCover()==null?"":friendObj.getCover()), mine_img, ThirdApplication.txOptions, animateFirstListener);
//		imageLoader.displayImage(InternetURL.INTERNAL_PIC + (friendObj.getImage()==null?"":friendObj.getImage()), mine_img_b, ThirdApplication.options, animateFirstListener);
		mine_support.setText(friendObj.getSupport_num() == null ? "0" : friendObj.getSupport_num());
		mine_see.setText(friendObj.getView_num() == null ? "0" : friendObj.getView_num());
		mine_favour.setText(friendObj.getCollect_num() == null ? "0" : friendObj.getCollect_num());
		mine_comment.setText(friendObj.getComment_num() == null ? "0" : friendObj.getComment_num());
		accountnumber.setText(friendObj.getMobile() == null ? "" : friendObj.getMobile());
		nickname.setText(friendObj.getNick_name() == null ? "" : friendObj.getNick_name());
		String sex = "";
		if("0".equals(friendObj.getSex())){
			sex = "男";
		}
		if("1".equals(friendObj.getSex())){
			sex = "女";
		}
		if("-1".equals(friendObj.getSex())){
			sex = "保密";
		}
		personal.setText(sex +" " + friendObj.getBirthday() +" " + friendObj.getXingzuo());
		String str_geyan = friendObj.getGeyan()== null?"":friendObj.getGeyan();
		if(StringUtil.isNullOrEmpty(str_geyan)){
			maxim.setText("暂无格言");
		}else {
			maxim.setText(str_geyan);
		}
		String str_home = friendObj.getBirthday_place()==null?"暂无地址":friendObj.getBirthday_place();
		if(StringUtil.isNullOrEmpty(str_home)){
			hometown.setText("暂无地址");
		}else {
			hometown.setText(str_home);
		}

		phone.setText(friendObj.getMobile()==null?"暂无手机号":friendObj.getMobile());

		String email_str = friendObj.getEmail()==null?"暂无邮箱":friendObj.getEmail();
		if(StringUtil.isNullOrEmpty(email_str)){
			mail.setText("暂无邮箱");
		}else {mail.setText(email_str);

		}

		village.setText(friendObj.getAddress()==null?"暂无地址":friendObj.getAddress());
		mine_name.setText(friendObj.getNick_name()==null?"":friendObj.getNick_name());
		if(friendObj.getPhoto_data() != null){
			for(Photo_data_obj photo_data_obj:friendObj.getPhoto_data()){
				if(photo_data_obj != null){
					list.add(photo_data_obj);
				}

			}
			adapter = new BottomAdapter(this, list);
			albumgv.setAdapter(adapter);
			getScreenDen();
			LIEWIDTH = dm.widthPixels / NUM;
			setValue();
			scrollView.getViewTreeObserver().addOnGlobalFocusChangeListener(
					new OnGlobalFocusChangeListener() {

						@Override
						public void onGlobalFocusChanged(View oldFocus,
														 View newFocus) {
							// 也可以使用ScrollTo()方法 但smoothScrollTo()方法更安全

							//scrollView.smoothScrollTo(0, 0);
							// 进入Activity后gridView将自动滑动 移动距离自己控制
						}
					});
		}
	}
	
	private void setValue() {
		albumgv.setAdapter(adapter);
		LayoutParams params = new LayoutParams((adapter.getCount() * LIEWIDTH) + dm.widthPixels / 8, LayoutParams.WRAP_CONTENT);
		albumgv.setLayoutParams(params);
		albumgv.setColumnWidth(dm.widthPixels / NUM + 1);
		albumgv.setStretchMode(GridView.NO_STRETCH);
		// 这里的设置很重要 gridView只能水平滑动
		int count = adapter.getCount();
		albumgv.setNumColumns(count);
	}

	private void getScreenDen() {
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
	}
	
	class BottomAdapter extends BaseAdapter {
		Context context;
		LayoutInflater inflater;
		List<Photo_data_obj> list;

		public BottomAdapter(Context context, List<Photo_data_obj> list) {
			inflater = LayoutInflater.from(context);
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			if (list != null) {
				return list.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			if (list != null && arg0 < list.size()) {
				return list.get(arg0);
			}
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				convertView = inflater.inflate(R.layout.mfimageitem, null);
				holder.image = (RoundImageView) convertView.findViewById(R.id.image);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			if (position < list.size()) {
				Photo_data_obj mfBean= list.get(position);
				if(mfBean != null){
//					holder.image.setImageResource(mfBean.image);
					imageLoader.displayImage(InternetURL.INTERNAL_PIC +
							(mfBean.getImage()==null?"":mfBean.getImage()), holder.image, ThirdApplication.options, animateFirstListener);
				}

			}
			return convertView;
		}
		
		class Holder {
			RoundImageView image;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			MfDetail.this.finish();
			break;
		default:
			break;
		}
	}
}
