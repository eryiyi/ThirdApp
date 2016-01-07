package com.example.thirdapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import com.example.thirdapp.MainActivity;
import com.example.thirdapp.R;
import com.example.thirdapp.activity.SearchActivity;
import com.example.thirdapp.base.BaseFragment;

public class MallTitleFragment extends BaseFragment implements OnClickListener{
	View view;
	View viewpopup;
	View viewbottom;
	ImageView more;
	ImageView refreshmall;
	ImageView sharemall;
	ImageView searchmall;
	ImageView mainpagemall;
	PopupWindow popupWindow;
	PopupWindow popupWindow2;
	ImageView cancel; 
	MainActivity activity;
	BottomFragment bottomFragment;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.malltitle, null);
		more = (ImageView) view.findViewById(R.id.more);
		more.setOnClickListener(this);
		activity = new MainActivity();
		bottomFragment = (BottomFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.bottombar);
		return view;
	}
	
	public void showPopupWindow() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		viewpopup = inflater.inflate(R.layout.itempopupmaill, null);
		
		refreshmall = (ImageView) viewpopup.findViewById(R.id.refreshmall);
		sharemall = (ImageView) viewpopup.findViewById(R.id.sharemall);
		searchmall = (ImageView) viewpopup.findViewById(R.id.searchmall);
		mainpagemall = (ImageView) viewpopup.findViewById(R.id.mainpagemall);
		
		refreshmall.setOnClickListener(this);
		sharemall.setOnClickListener(this);
		searchmall.setOnClickListener(this);
		mainpagemall.setOnClickListener(this);
		
		//WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		//int wid = wm.getDefaultDisplay().getWidth();
		popupWindow = new PopupWindow(getActivity());
		popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true); // 可聚焦
		popupWindow.setOutsideTouchable(true);// 设置外部可点击
		ColorDrawable colorDrawable = new ColorDrawable(0xffffffff);
		popupWindow.setBackgroundDrawable(colorDrawable); // 设置背景
		//设置popupwindow动画效果
		//popupWindow.setAnimationStyle(R.style.AnimationPreview);
		popupWindow.setContentView(viewpopup);
		popupWindow.showAsDropDown(more, 50, 0);
		//popupWindow.showAtLocation(viewpopup.findViewById(R.id.cancelinvate), Gravity.CENTER, 0, 0);
	}

	public void showPopupWindowShare(){
		popupWindow2 = new PopupWindow(getActivity());
		popupWindow2.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow2.setTouchable(true);
		popupWindow2.setFocusable(true); //可聚焦
		popupWindow2.setOutsideTouchable(true);//设置外部可点击
		ColorDrawable colorDrawable = new ColorDrawable(0xffffffff);
		popupWindow2.setBackgroundDrawable(colorDrawable); //设置背景
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		viewbottom = inflater.inflate(R.layout.popupshare, null);
		ImageView sharemsg = (ImageView) viewbottom.findViewById(R.id.sharemsg);
		sharemsg.setOnClickListener(this);
		cancel = (ImageView) viewbottom.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		//设置PopupWindow的弹出和消失效果
		popupWindow2.setAnimationStyle(R.style.popupAnimation);
		popupWindow2.setContentView(viewbottom);
		popupWindow2.showAtLocation(more, Gravity.BOTTOM, 0, 0);
		backgroundAlpha(0.4f);
		popupWindow2.setOnDismissListener(new poponDismissListener());
	}
	
	public void backgroundAlpha(float bgAlpha){
		
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
	}
	
	public class poponDismissListener implements PopupWindow.OnDismissListener{

		@Override
		public void onDismiss() {
			backgroundAlpha(1f);
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more:
			showPopupWindow();
			break;
		case R.id.refreshmall:
			
			popupWindow.dismiss();
			break;
		case R.id.sharemall:
			showPopupWindowShare();
			popupWindow.dismiss();
			break;
		case R.id.searchmall:
			Intent intent2 = new Intent(getActivity(), SearchActivity.class);
			startActivity(intent2);
			popupWindow.dismiss();
			break;	
		case R.id.mainpagemall:
			activity.setChioceItem(0);
			Fragment mainpagetilte = new MainPageTitleFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.titlefragment, mainpagetilte).commit();
			bottomFragment.setSelected(0);
			popupWindow.dismiss();
			break;
		case R.id.cancel:
			popupWindow2.dismiss();
			break;
		case R.id.sharemsg:
			showShareDialog();
			popupWindow2.dismiss();
			break;
		default:
			break;
		}
	}
	
	 private void showShareDialog(){
         AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
         builder.setTitle("选择分享类型");
         builder.setItems(new String[]{"邮件","短信","其他"}, new DialogInterface.OnClickListener() {
             
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                case 0: //邮件
                    sendMail("http://www.google.com.hk/");
                    break;
                case 1: //短信
                    sendSMS("http://www.google.com.hk/");
                    break;
                case 3: //调用系统分享
                    Intent intent=new Intent(Intent.ACTION_SEND); 
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"分享");   
                    intent.putExtra(Intent.EXTRA_TEXT, "我正在浏览这个,觉得真不错,推荐给你哦~ 地址:"+"http://www.google.com.hk/");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
                    startActivity(Intent.createChooser(intent, "share"));
                    break;
                default:
                    break;
                }
                 
            }
        });
        builder.setNegativeButton( "取消" ,  new  DialogInterface.OnClickListener() {    
           @Override    
           public   void  onClick(DialogInterface dialog,  int  which) {    
              dialog.dismiss();    
          }    
      });    
      builder.create().show();
    }
    
    
   /**
    * 发送邮件
    * @param emailBody
    */
   private void sendMail(String emailUrl){
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("plain/text");
         
        String emailBody = "我正在浏览这个,觉得真不错,推荐给你哦~ 地址:" + emailUrl;
        //邮件主题
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "测试数据");
        //邮件内容
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);  
         
        startActivityForResult(Intent.createChooser(email,  "请选择邮件发送内容" ), 1001 ); 
    }
    
    
   /**
    * 发短信
    */
   private   void  sendSMS(String webUrl){  
      String smsBody = "我正在浏览这个,觉得真不错,推荐给你哦~ 地址:" + webUrl;
      Uri smsToUri = Uri.parse( "smsto:" );  
      Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);  
      //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
      //短信内容
      sendIntent.putExtra( "sms_body", smsBody);  
      sendIntent.setType( "vnd.android-dir/mms-sms" );  
      startActivityForResult(sendIntent, 1002 );  
   }
}
