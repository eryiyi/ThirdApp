package com.example.thirdapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.AdSlideData;
import com.example.thirdapp.data.PayData;
import com.example.thirdapp.payutil.PayResult;
import com.example.thirdapp.payutil.SignUtils;
import com.example.thirdapp.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class Rechange extends BaseActivity implements OnClickListener{
	//http://shop.apptech.space/api/alipay/notify
	TextView t1;
	TextView t2;
	TextView t3;
	TextView t4;
	TextView t5;
	TextView t6;
	ImageView back;
	private EditText editph;
	private TextView btn;
	private String tmpCount;


	//---------------------------------支付开始----------------------------------------
	// 商户PID
	public static final String PARTNER = "2088021874965563";
	// 商户收款账号
	public static final String SELLER = "2040609968@qq.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALys+oYaxqv4FYju" +
			"8C1poM6qmHLjWPnXzqEJT0NxyFXgdaK/Qe9DcpcASod9mIAdlLIxJEyYNlWeonAJ" +
			"VYW8pQ+pTVGwI9n0iaT71ldWQzcMN3Dvi/+zpgw3HxxO7HJtEIlR84pvILv1yceC" +
			"ZCqqQ4O/4SemsG00oTiTyD3SM2ZvAgMBAAECgYBLToeX6ywNC7Icu7Hljll+45yB" +
			"jri+0CJLKFoYw1uA21xYnxoEE9my54zX04uA502oafDhGYfmWLDhIvidrpP6oalu" +
			"URb/gbV5Bdcm98gGGVgm6lpK+G5N/eawXDjP0ZjxXb114Y/Hn/oVFVM9OqcujFSV" +
			"+Wg4JgJ4Mmtdr35gYQJBAPbhx030xPcep8/dL5QQMc7ddoOrfxXewKcpDmZJi2ey" +
			"381X+DhuphQ5gSVBbbunRiDCEcuXFY+R7xrgnP+viWcCQQDDpN8DfqRRl+cUhc0z" +
			"/TbnSPJkMT/IQoFeFOE7wMBcDIBoQePEDsr56mtc/trIUh/L6evP9bkjLzWJs/kb" +
			"/i25AkEAtoOf1k/4NUEiipdYjzuRtv8emKT2ZPKytmGx1YjVWKpyrdo1FXMnsJf6" +
			"k9JVD3/QZnNSuJJPTD506AfZyWS6TQJANdeF2Hxd1GatnaRFGO2y0mvs6U30c7R5" +
			"zd6JLdyaE7sNC6Q2fppjmeu9qFYq975CKegykYTacqhnX4I8KEwHYQJAby60iHMA" +
			"YfSUpu//f5LMMRFK2sVif9aqlYbepJcAzJ6zbiSG5E+0xg/MjEj/Blg9rNsqDG4R" +
			"ECGJG2nPR72O8g==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "";
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					PayResult payResult = new PayResult((String) msg.obj);

					// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
					String resultInfo = payResult.getResult();

					String resultStatus = payResult.getResultStatus();

					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						Toast.makeText(Rechange.this, "充值成功",
								Toast.LENGTH_SHORT).show();
//						finish();
					} else {
						// 判断resultStatus 为非“9000”则代表可能支付失败
						// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(Rechange.this, "支付结果确认中",
									Toast.LENGTH_SHORT).show();

						} else {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							Toast.makeText(Rechange.this, "支付失败",
									Toast.LENGTH_SHORT).show();

						}
					}
					break;
				}
				case SDK_CHECK_FLAG: {
					Toast.makeText(Rechange.this, "检查结果为：" + msg.obj,
							Toast.LENGTH_SHORT).show();
					break;
				}
				default:
					break;
			}
		};
	};
	//------------------------------------------------------------------------------------

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.recharge);

		editph = (EditText) this.findViewById(R.id.editph);
		btn = (TextView) this.findViewById(R.id.btn);
		btn.setOnClickListener(this);

		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		t1 = (TextView) findViewById(R.id.t1);
		t2 = (TextView) findViewById(R.id.t2);
		t3 = (TextView) findViewById(R.id.t3);
		t4 = (TextView) findViewById(R.id.t4);
		t5 = (TextView) findViewById(R.id.t5);
		t6 = (TextView) findViewById(R.id.t6);
		t1.setOnClickListener(this);
		t2.setOnClickListener(this);
		t3.setOnClickListener(this);
		t4.setOnClickListener(this);
		t5.setOnClickListener(this);
		t6.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		int position = 0;
		switch (v.getId()) {
		case R.id.t1:
			position = 0;
			setselected(position);
			tmpCount = "10";
			break;
		case R.id.t2:
			position = 1;
			setselected(position);
			tmpCount = "30";
			break;
		case R.id.t3:
			position = 2;
			setselected(position);
			tmpCount = "100";
			break;
		case R.id.t4:
			position = 3;
			setselected(position);
			tmpCount = "200";
			break;
		case R.id.t5:
			position = 4;
			setselected(position);
			tmpCount = "500";
			break;
		case R.id.t6:
			position = 5;
			setselected(position);
			tmpCount = "1000";
			break;
		case R.id.back:
			Rechange.this.finish();
			break;
		case R.id.btn:
			//充值按钮点击事件
			if(StringUtil.isNullOrEmpty(tmpCount)){
				showMsg(Rechange.this, "请选择充值金额");
				return;
			}
			sendChongzhi();
			break;
		default:
			break;
		}

	}


	void sendChongzhi(){
		StringRequest request = new StringRequest(
				Request.Method.POST,
				InternetURL.CHONGZHI_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code =  jo.getString("code");
								if(Integer.parseInt(code) == 200){
									PayData data = getGson().fromJson(s, PayData.class);
									String payNo = data.getData().getPay_sn();
									pay(payNo);
								}else {
									showMsg(Rechange.this, jo.getString("msg"));
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							showMsg(Rechange.this,"充值失败");
						}
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						showMsg(Rechange.this,"充值失败");
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("user_name", getGson().fromJson(getSp().getString("mobile", ""), String.class));
				params.put("money", tmpCount);
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
	
	public void setselected(int position) {
		switch (position) {
		case 0:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			break;
		case 1:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			break;
		case 2:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			break;
		case 3:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			break;
		case 4:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			break;
		case 5:
			t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangegrey));
			t6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rechangered));
			break;
		default:
			break;
		}
	}


	//---------------------------------------------------------支付宝------------------------------------------

	/**
	 * call alipay sdk pay. 调用SDK支付
	 *
	 */
	public void pay(String sn) {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									finish();
								}
							}).show();
			return;
		}
		// 订单
		String orderInfo = getOrderInfo(sn, sn, tmpCount, sn);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(Rechange.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 *
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(Rechange.this);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 *
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 *
	 */
	public String getOrderInfo(String subject, String body, String price, String sn) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + sn + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + 1 + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
//        orderInfo += "&notify_url=" + "\"http://115.29.200.169/alipay_notify.php\"";
//        orderInfo += "&notify_url=" + "\"http://baidu.com\"";

		orderInfo += "&notify_url=" + "\""
				+ "http://shop.apptech.space/api/alipay/notify" + "\"";
//
//        // 服务接口名称， 固定值
//        orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}


	/**
	 * sign the order info. 对订单信息进行签名
	 *
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 *
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	//-------zfb----支付完成之后的操作
}
