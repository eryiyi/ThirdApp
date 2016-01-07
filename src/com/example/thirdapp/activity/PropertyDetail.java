package com.example.thirdapp.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.adapter.AnimateFirstDisplayListener;
import com.example.thirdapp.adapter.ItemHouseProperAdapter;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.module.HouseInfoObj;
import com.example.thirdapp.module.HousePropertyObj;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class PropertyDetail extends BaseActivity implements OnClickListener{
	private static ArrayList<ImageView> imageviews;
	ImageView back;
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	ImageView imageView4;
	private RadioButton radioButton1;  
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private RadioButton radioButton4;
	ViewPager viewpager;
	private HouseInfoObj houseInfoObj;

	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private TextView propertyname;
	private TextView propertymode;
	private TextView propertyprice;
	private TextView propertytime;
	private TextView propertybrowsenumber;
	private TextView livingroom;
	private TextView area;
	private TextView decorate;
	private TextView general;
	private TextView floor;
	private TextView limit;
	private TextView bedroom;
	private TextView village;
	private TextView address;
	private TextView desc;
	private TextView phone;
	private GridView propertygv;
	private List<HousePropertyObj> listProperty = new ArrayList<HousePropertyObj>();
	private ItemHouseProperAdapter adapterPro;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.propertydetails);
		houseInfoObj = (HouseInfoObj) getIntent().getExtras().get("houseInfoObj");
		initView();
	}

	void initView(){
		viewpager = (ViewPager) findViewById(R.id.imageviewpager);
		radioButton1 = (RadioButton) findViewById(R.id.radio1);
		radioButton2 = (RadioButton) findViewById(R.id.radio2);
		radioButton3 = (RadioButton) findViewById(R.id.radio3);
		radioButton4 = (RadioButton) findViewById(R.id.radio4);
		imageviews = new ArrayList<ImageView>();
		LayoutInflater inflater =getLayoutInflater();
		imageView1 = (ImageView) inflater.inflate(R.layout.imageitem, null);
		imageView2 = (ImageView) inflater.inflate(R.layout.imageitem, null);
		imageView3 = (ImageView) inflater.inflate(R.layout.imageitem, null);
		imageView4 = (ImageView) inflater.inflate(R.layout.imageitem, null);
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + houseInfoObj.getPic1(), imageView1, ThirdApplication.options, animateFirstListener);
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + houseInfoObj.getPic2(), imageView2, ThirdApplication.options, animateFirstListener);
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + houseInfoObj.getPic3(), imageView3, ThirdApplication.options, animateFirstListener);
		imageLoader.displayImage(InternetURL.INTERNAL_PIC + houseInfoObj.getPic4(), imageView4, ThirdApplication.options, animateFirstListener);

		imageView1.setImageResource(R.drawable.demo7);
		imageView2.setImageResource(R.drawable.demo5);
		imageView3.setImageResource(R.drawable.demo4);
		imageView4.setImageResource(R.drawable.demo3);
		imageviews.add(imageView1);
		imageviews.add(imageView2);
		imageviews.add(imageView3);
		imageviews.add(imageView4);
		back = (ImageView) findViewById(R.id.back);
		radioButton1.setOnClickListener(this);
		radioButton2.setOnClickListener(this);
		radioButton3.setOnClickListener(this);
		radioButton4.setOnClickListener(this);
		back.setOnClickListener(this);
		viewpager.setAdapter(mPagerAdapter);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0 % imageviews.size()) {
					case 0:
						radioButton1.setChecked(true);
						break;
					case 1:
						radioButton2.setChecked(true);
						break;
					case 2:
						radioButton3.setChecked(true);
						break;
					case 3:
						radioButton4.setChecked(true);
						break;
					default:
						break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		propertyname = (TextView) this.findViewById(R.id.propertyname);
		propertyprice = (TextView) this.findViewById(R.id.propertyprice);
		propertymode = (TextView) this.findViewById(R.id.propertymode);
		propertytime = (TextView) this.findViewById(R.id.propertytime);
		propertybrowsenumber = (TextView) this.findViewById(R.id.propertybrowsenumber);
		livingroom = (TextView) this.findViewById(R.id.livingroom);
		area = (TextView) this.findViewById(R.id.area);
		decorate = (TextView) this.findViewById(R.id.decorate);
		general = (TextView) this.findViewById(R.id.general);
		floor = (TextView) this.findViewById(R.id.floor);
		limit = (TextView) this.findViewById(R.id.limit);
		bedroom = (TextView) this.findViewById(R.id.bedroom);
		village = (TextView) this.findViewById(R.id.village);
		address = (TextView) this.findViewById(R.id.address);
		desc = (TextView) this.findViewById(R.id.desc);
		phone = (TextView) this.findViewById(R.id.phone);
		propertygv = (GridView) this.findViewById(R.id.propertygv);

		village.setText(houseInfoObj.getCommunity_name()==null?"":houseInfoObj.getCommunity_name());
		desc.setText(houseInfoObj.getDetail()== null?"":houseInfoObj.getDetail());
		bedroom.setText(houseInfoObj.getBedroom() ==null?"":houseInfoObj.getBedroom());
		address.setText((houseInfoObj.getCity() ==null?"":houseInfoObj.getCity()) +"-"+(houseInfoObj.getAddress() ==null?"":houseInfoObj.getAddress()));
		limit.setText(houseInfoObj.getLimits() ==null?"":houseInfoObj.getLimits());
		floor.setText(houseInfoObj.getFloor() ==null?"":houseInfoObj.getFloor());
		general.setText(houseInfoObj.getGeneral() ==null?"":houseInfoObj.getGeneral());
		decorate.setText(houseInfoObj.getDecoration() ==null?"":houseInfoObj.getDecoration());
		propertyname.setText(houseInfoObj.getTitle() ==null?"":houseInfoObj.getTitle());
		area.setText(houseInfoObj.getArea() ==null?"":houseInfoObj.getArea());
		livingroom.setText(houseInfoObj.getLiving_room() ==null?"":houseInfoObj.getLiving_room());
		propertyprice.setText(houseInfoObj.getRent_price() ==null?"":houseInfoObj.getPrice());
		propertymode.setText(houseInfoObj.getLimits() ==null?"":houseInfoObj.getLimits());
		phone.setText(houseInfoObj.getUser_name() ==null?"":houseInfoObj.getUser_name());
		propertybrowsenumber.setText("已有"+(houseInfoObj.getView_num()==null?"0":houseInfoObj.getView_num())+"人浏览");
		propertytime.setText("发布时间："+(houseInfoObj.getUpdate_date()==null?"":houseInfoObj.getUpdate_date()));

		if("1".equals(houseInfoObj.getIsKuanDai()==null?"0":houseInfoObj.getIsKuanDai())){
			listProperty.add(new HousePropertyObj(R.drawable.house_one, "宽带"));
		}
		if("1".equals(houseInfoObj.getIsShaFa()==null?"0":houseInfoObj.getIsShaFa())){
			listProperty.add(new HousePropertyObj(R.drawable.house_two, "沙发"));
		}
		if("1".equals(houseInfoObj.getIsXiYiJi()==null?"0":houseInfoObj.getIsXiYiJi())){
			listProperty.add(new HousePropertyObj(R.drawable.house_thre, "洗衣机"));
		}
		if("1".equals(houseInfoObj.getIsDianShi()==null?"0":houseInfoObj.getIsDianShi())){
			listProperty.add(new HousePropertyObj(R.drawable.house_four, "电视"));
		}
		if("1".equals(houseInfoObj.getIsBingXiang()==null?"0":houseInfoObj.getIsBingXiang())){
			listProperty.add(new HousePropertyObj(R.drawable.house_five, "冰箱"));
		}
		if("1".equals(houseInfoObj.getIsKongTiao()==null?"0":houseInfoObj.getIsKongTiao())){
			listProperty.add(new HousePropertyObj(R.drawable.house_six, "空调"));
		}
		if("1".equals(houseInfoObj.getIsNuanQi()==null?"0":houseInfoObj.getIsNuanQi())){
			listProperty.add(new HousePropertyObj(R.drawable.house_seven, "暖气"));
		}
		if("1".equals(houseInfoObj.getIsBed()==null?"0":houseInfoObj.getIsBed())){
			listProperty.add(new HousePropertyObj(R.drawable.house_eight, "床"));
		}
		if("1".equals(houseInfoObj.getIsReShui()==null?"0":houseInfoObj.getIsReShui())) {
			listProperty.add(new HousePropertyObj(R.drawable.house_nine, "热水器"));
		}
		if("1".equals(houseInfoObj.getIsYiGui()==null?"0":houseInfoObj.getIsYiGui())){
			listProperty.add(new HousePropertyObj(R.drawable.house_ten, "衣柜"));
		}
		adapterPro = new ItemHouseProperAdapter(listProperty, PropertyDetail.this);
		propertygv.setSelector(new ColorDrawable(Color.TRANSPARENT));
		propertygv.setAdapter(adapterPro);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.radio1:
			viewpager.setCurrentItem(0);
			break;
		case R.id.radio2:
			viewpager.setCurrentItem(1);
			break;
		case R.id.radio3:
			viewpager.setCurrentItem(2);
			break;
		case R.id.radio4:
			viewpager.setCurrentItem(3);
			break;
		case R.id.back:
			PropertyDetail.this.finish();
			break;
		default:
			break;
		}
	}
	
	PagerAdapter mPagerAdapter = new PagerAdapter(){

        @Override
        //获取当前窗体界面数
        public int getCount() {
            return imageviews.size();
        }

        @Override
        //断是否由对象生成界面
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }
        //是从ViewGroup中移出当前View
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            ((ViewPager) arg0).removeView(imageviews.get(arg1));  
        }  
        
        //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
        public Object instantiateItem(View arg0, int arg1){
            ((ViewPager)arg0).addView(imageviews.get(arg1));
            return imageviews.get(arg1);                
        }
    };
}
