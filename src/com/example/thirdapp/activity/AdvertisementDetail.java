package com.example.thirdapp.activity;

import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;

public class AdvertisementDetail extends BaseActivity {
	WebView webView;
	private int getint;
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.webview);
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		getint = getIntent().getIntExtra("whichpage", 10);
		switch (getint) {
		case 0:
			webView.loadUrl("http://www.baidu.com");
			break;
		case 1:
			webView.loadUrl("http://www.hao123.com");
			break;
		case 2:
			webView.loadUrl("http://www.sina.com.cn/");
			break;
		case 3:
			webView.loadUrl("http://www.autohome.com.cn/");
			break;
		default:
			break;
		}
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				//返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});
		//webView.addJavascriptInterface(new MyWebAppInterface(this), "demo");
		//webView.loadUrl("http://www.12365auto.com/news/2014-07-10/20140710115457.shtml");
	}
	
	/*class MyWebViewClient extends WebViewClient{
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			//通过返回的webview加载webview默认脚本在脚本中动�?�注入js
			view.loadUrl(
							"javascript:(function(){"  
		                    + "  var objs = document.getElementsByTagName(\"img\"); "  
		                    + "  for(var i=0;i<objs.length;i++){"  
		                    + "     objs[i].onclick=function(){"  
		                    + "          window.demo.jsInvokeJava(this.src); "  
		                    + "     }"
		                    + "  }"  
		                    + "})()");

		}
	}*/
}
