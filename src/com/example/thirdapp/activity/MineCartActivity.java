package com.example.thirdapp.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.ItemCartAdapter;
import com.example.thirdapp.adapter.OnClickContentItemListener;
import com.example.thirdapp.base.BaseActivity;
import com.example.thirdapp.db.DBHelper;
import com.example.thirdapp.db.ShoppingCart;
import com.example.thirdapp.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/26.
 */
public class MineCartActivity extends BaseActivity implements View.OnClickListener,OnClickContentItemListener {

    private ListView lstv;
    private ItemCartAdapter adapter;
    private List<ShoppingCart> lists = new ArrayList<ShoppingCart>();

    private TextView heji;
    private TextView qujiesuan;
    Resources res;
    private ImageView no_goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minecartactivity);
        res = getResources();
        heji = (TextView) this.findViewById(R.id.heji);
        qujiesuan = (TextView) this.findViewById(R.id.qujiesuan);
        lstv = (ListView) this.findViewById(R.id.lstv);
        qujiesuan.setOnClickListener(this);
        no_goods = (ImageView) this.findViewById(R.id.no_goods);
        no_goods.setVisibility(View.GONE);

        getData();
    }

//    public void mine_click(View view){
//        Intent intent = new Intent(MineCartActivity.this, MineActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.qujiesuan:
                //结算
                if ("1".equals(getGson().fromJson(getSp().getString("isLogin", ""), String.class))) {
                    if(lists != null && lists.size() > 0){
                        ArrayList<ShoppingCart> arrayList = new ArrayList<ShoppingCart>();
                        for(int i=0;i<lists.size();i++){
                            if(lists.get(i).getIs_select().equals("0")){
                                arrayList.add(lists.get(i));
                            }
                        }
                        if(arrayList != null && arrayList.size() > 0){
                            Intent orderMakeView = new Intent(MineCartActivity.this, OrderMakeActivity.class);
                            orderMakeView.putExtra("listsgoods",arrayList);
                            startActivity(orderMakeView);
                            finish();
                        }else{
                            Toast.makeText(MineCartActivity.this, R.string.cart_error_one, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MineCartActivity.this, R.string.cart_error_one, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Intent intent = new Intent(MineCartActivity.this, Logon.class);
                    intent.putExtra("skip", 1);
                    startActivity(intent);
                }
                break;
        }
    }

    public void back(View view){
        finish();
    }

    void getData(){
        //取出数据,查询所有的购物车
        lists.clear();
        lists = DBHelper.getInstance(this).getShoppingList();
        //购物车
        adapter = new ItemCartAdapter(lists, this);
        lstv.setAdapter(adapter);
        if(lists.size() == 0){
            qujiesuan.setText(res.getString(R.string.no_data));
            no_goods.setVisibility(View.VISIBLE);
            lstv.setVisibility(View.GONE);
        }else {
            qujiesuan.setText(res.getString(R.string.qujiesuan));
            no_goods.setVisibility(View.GONE);
            lstv.setVisibility(View.VISIBLE);
        }
        adapter.setOnClickContentItemListener(this);
        toCalculate();
    }


    @Override
    public void onClickContentItem(int position, int flag, Object object) {
        switch (flag){
            case 1:
                //左侧选择框按钮
                if("0".equals(lists.get(position).getIs_select())){
                    lists.get(position).setIs_select("1");
                }else {
                    lists.get(position).setIs_select("0");
                }
                adapter.notifyDataSetChanged();
                toCalculate();
                break;
            case 2:
                //加号
                lists.get(position).setGoods_count(String.valueOf((Integer.parseInt(lists.get(position).getGoods_count()) + 1)));
                adapter.notifyDataSetChanged();
                toCalculate();
                break;
            case 3:
                //减号
                int selectNum = Integer.parseInt(lists.get(position).getGoods_count());
                if(selectNum == 0){
                    return;
                }else {
                    lists.get(position).setGoods_count(String.valueOf((Integer.parseInt(lists.get(position).getGoods_count()) - 1)));
                    adapter.notifyDataSetChanged();
                }
                toCalculate();
                break;
        }
    }

    //计算金额总的
    void toCalculate(){
        if (lists != null){
            Double doublePrices = 0.0;
            for(int i=0; i<lists.size() ;i++){
                ShoppingCart shoppingCart = lists.get(i);
                if(shoppingCart.getIs_select() .equals("0")){
                    //默认是选中的
                    String scount = shoppingCart.getGoods_count();
                    if(StringUtil.isNullOrEmpty(scount)){
                        scount = "0";
                    }
                    String sell_price = shoppingCart.getSell_price();
                    if(StringUtil.isNullOrEmpty(sell_price)){
                        sell_price = "0";
                    }
                    doublePrices = doublePrices + Double.parseDouble(sell_price) * Double.parseDouble(scount);
                }
            }
            heji.setText(getResources().getString(R.string.countPrices) + doublePrices.toString());
        }
    }


}
