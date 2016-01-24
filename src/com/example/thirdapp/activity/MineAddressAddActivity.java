package com.example.thirdapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thirdapp.R;
import com.example.thirdapp.addressdb.DBManager;
import com.example.thirdapp.addressdb.MyAdapter;
import com.example.thirdapp.addressdb.MyListItem;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.data.CitysDATA;
import com.example.thirdapp.data.CountrysDATA;
import com.example.thirdapp.data.ProvinceDATA;
import com.example.thirdapp.module.City;
import com.example.thirdapp.module.Country;
import com.example.thirdapp.module.Province;
import com.example.thirdapp.util.StringUtil;
import com.example.thirdapp.view.CustomProgressDialog;
import com.example.thirdapp.view.CustomerSpinner;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/10.
 * 收货地址
 */
public class MineAddressAddActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private EditText nickname;
    private EditText telephone;
    private EditText address;
    private Button sure;
    private EditText codeyb;
    Resources res;

//    private CustomerSpinner province;//选择教学楼
//    private CustomerSpinner city;
//    private CustomerSpinner country;
//
//    private List<Province> provinces = new ArrayList<Province>();//省
//    private ArrayList<String> provinceNames = new ArrayList<String>();//省份名称
//
//    private List<City> citys = new ArrayList<City>();//市
//    private ArrayList<String> cityNames = new ArrayList<String>();//市名称
//
//    private List<Country> countrys = new ArrayList<Country>();//区
//    private ArrayList<String> countrysNames = new ArrayList<String>();//区名称

//
//    ArrayAdapter<String> ProvinceAdapter;
//
//    ArrayAdapter<String> cityAdapter;
//
//    ArrayAdapter<String> countryAdapter;


    private String provinceName = "";
    private String cityName = "";
    private String countryName = "";

//    private String provinceCode = "";
//    private String cityCode = "";
//    private String countryCode = "";

    private CheckBox checkbox;


    private DBManager dbm;
    private SQLiteDatabase db;
    private Spinner spinner1 = null;
    private Spinner spinner2=null;
    private Spinner spinner3=null;
    private String province=null;
    private String city=null;
    private String district=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_address_add_activity);
        res = getResources();
        initView();

    }

    private void initView() {
        checkbox = (CheckBox) this.findViewById(R.id.checkbox);
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        nickname = (EditText) this.findViewById(R.id.nickname);
        codeyb = (EditText) this.findViewById(R.id.codeyb);
        telephone = (EditText) this.findViewById(R.id.telephone);
        address = (EditText) this.findViewById(R.id.address);
        sure = (Button) this.findViewById(R.id.sure);
        sure.setOnClickListener(this);

        spinner1=(Spinner)findViewById(R.id.spinner1);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner3=(Spinner)findViewById(R.id.spinner3);
//        spinner1.setPrompt(getResources().getString(R.string.province));
//        spinner2.setPrompt(getResources().getString(R.string.city));
//        spinner3.setPrompt(getResources().getString(R.string.dist));

        initSpinner1();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.sure:
                //确定按钮
                if(StringUtil.isNullOrEmpty(nickname.getText().toString())){
                    Toast.makeText(MineAddressAddActivity.this, R.string.address_error_one, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(StringUtil.isNullOrEmpty(telephone.getText().toString())){
                    Toast.makeText(MineAddressAddActivity.this, R.string.address_error_two, Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(StringUtil.isNullOrEmpty(mobile.getText().toString())){
//                    Toast.makeText(MineAddressAddActivity.this, R.string.address_error_threee, Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(StringUtil.isNullOrEmpty(address.getText().toString())){
                    Toast.makeText(MineAddressAddActivity.this, R.string.address_error_three, Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog = new CustomProgressDialog(MineAddressAddActivity.this , "", R.anim.frame_paopao);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                setAddress();
                break;

        }
    }

    //设置收货地址
    public void setAddress(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.ADDRESS_SET_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code1 =  jo.getString("code");
                                if(Integer.parseInt(code1) == 200){
                                    Intent intent = new Intent("address_success");
                                    MineAddressAddActivity.this.sendBroadcast(intent);
                                    finish();
                                }else {
                                    Toast.makeText(MineAddressAddActivity.this, jo.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(MineAddressAddActivity.this, R.string.caozuoshibai, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MineAddressAddActivity.this, R.string.caozuoshibai, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("access_token", getGson().fromJson(getSp().getString("access_token", ""), String.class));
                params.put("sMobile", getGson().fromJson(getSp().getString("mobile", ""), String.class));
                params.put("sAddress", province+city+district);
                params.put("sStreet", address.getText().toString());
                params.put("sZip", codeyb.getText().toString());
                params.put("sAcceptName", nickname.getText().toString());
                params.put("sTelephone", telephone.getText().toString());

                if(checkbox.isChecked()){
                    params.put("nSelected", "1");
                }else {
                    params.put("nSelected", "0");
                }
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


    public void initSpinner1(){
        dbm = new DBManager(getApplication());
        dbm.openDatabase();
        db = dbm.getDatabase();
        List<MyListItem> list = new ArrayList<MyListItem>();

        try {
            String sql = "select * from province";
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while (!cursor.isLast()){
                String code=cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[]=cursor.getBlob(2);
                String name=new String(bytes,"gbk");
                MyListItem myListItem=new MyListItem();
                myListItem.setName(name);
                myListItem.setPcode(code);
                list.add(myListItem);
                cursor.moveToNext();
            }
            String code=cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[]=cursor.getBlob(2);
            String name=new String(bytes,"gbk");
            MyListItem myListItem=new MyListItem();
            myListItem.setName(name);
            myListItem.setPcode(code);
            list.add(myListItem);

        } catch (Exception e) {
        }
        dbm.closeDatabase();
        db.close();

        MyAdapter myAdapter = new MyAdapter(this,list);
        spinner1.setAdapter(myAdapter);
        spinner1.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
    }
    public void initSpinner2(String pcode){
        dbm = new DBManager(this);
        dbm.openDatabase();
        db = dbm.getDatabase();
        List<MyListItem> list = new ArrayList<MyListItem>();

        try {
            String sql = "select * from city where pcode='"+pcode+"'";
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while (!cursor.isLast()){
                String code=cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[]=cursor.getBlob(2);
                String name=new String(bytes,"gbk");
                MyListItem myListItem=new MyListItem();
                myListItem.setName(name);
                myListItem.setPcode(code);
                list.add(myListItem);
                cursor.moveToNext();
            }
            String code=cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[]=cursor.getBlob(2);
            String name=new String(bytes,"gbk");
            MyListItem myListItem=new MyListItem();
            myListItem.setName(name);
            myListItem.setPcode(code);
            list.add(myListItem);

        } catch (Exception e) {
        }
        dbm.closeDatabase();
        db.close();

        MyAdapter myAdapter = new MyAdapter(this,list);
        spinner2.setAdapter(myAdapter);
        spinner2.setOnItemSelectedListener(new SpinnerOnSelectedListener2());
    }
    public void initSpinner3(String pcode){
        dbm = new DBManager(this);
        dbm.openDatabase();
        db = dbm.getDatabase();
        List<MyListItem> list = new ArrayList<MyListItem>();

        try {
            String sql = "select * from district where pcode='"+pcode+"'";
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToFirst();
            while (!cursor.isLast()){
                String code=cursor.getString(cursor.getColumnIndex("code"));
                byte bytes[]=cursor.getBlob(2);
                String name=new String(bytes,"gbk");
                MyListItem myListItem=new MyListItem();
                myListItem.setName(name);
                myListItem.setPcode(code);
                list.add(myListItem);
                cursor.moveToNext();
            }
            String code=cursor.getString(cursor.getColumnIndex("code"));
            byte bytes[]=cursor.getBlob(2);
            String name=new String(bytes,"gbk");
            MyListItem myListItem=new MyListItem();
            myListItem.setName(name);
            myListItem.setPcode(code);
            list.add(myListItem);

        } catch (Exception e) {
        }
        dbm.closeDatabase();
        db.close();

        MyAdapter myAdapter = new MyAdapter(this,list);
        spinner3.setAdapter(myAdapter);
        spinner3.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
    }

    class SpinnerOnSelectedListener1 implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                                   long id) {
            province=((MyListItem) adapterView.getItemAtPosition(position)).getName();
            String pcode =((MyListItem) adapterView.getItemAtPosition(position)).getPcode();

            initSpinner2(pcode);
            initSpinner3(pcode);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            // TODO Auto-generated method stub
        }
    }
    class SpinnerOnSelectedListener2 implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                                   long id) {
            city=((MyListItem) adapterView.getItemAtPosition(position)).getName();
            String pcode =((MyListItem) adapterView.getItemAtPosition(position)).getPcode();

            initSpinner3(pcode);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            // TODO Auto-generated method stub
        }
    }

    class SpinnerOnSelectedListener3 implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                                   long id) {
            district=((MyListItem) adapterView.getItemAtPosition(position)).getName();
//            Toast.makeText(City_cnActivity.this, province+" "+city+" "+district, Toast.LENGTH_LONG).show();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            // TODO Auto-generated method stub
        }
    }

}
