package com.example.thirdapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thirdapp.MainActivity;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.adapter.AnimateFirstDisplayListener;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.module.MemberObj;
import com.example.thirdapp.sharedprefercens.SharedPrefsUtil;
import com.example.thirdapp.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class PersonMsg extends BaseActivity implements OnClickListener{
	TextView confirm;
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

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		memberObj = (MemberObj) getIntent().getExtras().get("memberObj");
		setContentView(R.layout.personmsg);
		confirm = (TextView) findViewById(R.id.confirm);
		propertyphone = (RelativeLayout) findViewById(R.id.propertyphone);
		confirm.setOnClickListener(this);
		propertyphone.setOnClickListener(this);

		icon = (ImageView) this.findViewById(R.id.icon);
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


		zhanghao.setText(memberObj.getMobile()==null?"":memberObj.getMobile());
		xiaoqu.setText(getGson().fromJson(getSp().getString("community_id", ""), String.class));
		nicheng.setText(memberObj.getNick_name()==null?"":memberObj.getNick_name());

		String sexstr = "";
		if("0".equals(memberObj.getSex())){
			sexstr = "男";
		}
		if("1".equals(memberObj.getSex())){
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

		nicheng.setOnClickListener(this);
		age.setOnClickListener(this);
		sex.setOnClickListener(this);
		weight.setOnClickListener(this);
		geyan.setOnClickListener(this);
		home.setOnClickListener(this);
		email.setOnClickListener(this);
		qq.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.confirm:
				save("isLogin","0");
				SharedPrefsUtil.putValue(PersonMsg.this, "UserUid", 0);
				Toast.makeText(PersonMsg.this, "已退出当前用户", Toast.LENGTH_SHORT).show();
				Intent intent2 = new Intent(PersonMsg.this, MainActivity.class);
				startActivity(intent2);
				break;
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
				edit.putExtra("type", "nicheng");
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
				break;
			case R.id.age:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("age", "age");
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.sex:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("sex", "sex");
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.weight:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("weight", "weight");
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.geyan:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("geyan", "geyan");
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.home:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("home", "home");
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.email:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("email", "email");
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			case R.id.qq:
			{
				//
				Intent edit = new Intent(PersonMsg.this, EditMemberActivity.class);
				edit.putExtra("qq", "qq");
				edit.putExtra("memberObj", memberObj);
				startActivity(edit);
			}
			break;
			default:
				break;
		}
	}
	
	
	
}
