package com.example.thirdapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.bean.ShaifanerObj;
import com.example.thirdapp.db.ShoppingCart;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Administrator on 2015/5/27.
 * 购物车
 */
public class ItemShaifanerAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<ShaifanerObj> lists;
    private Context mContect;
    Resources res;
    private OnClickContentItemListener onClickContentItemListener;

    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
        this.onClickContentItemListener = onClickContentItemListener;
    }

    public ItemShaifanerAdapter(List<ShaifanerObj> lists, Context mContect){
        this.lists = lists;
        this.mContect = mContect;
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        res = mContect.getResources();
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContect).inflate(R.layout.item_shaifaner,null);
            holder.head = (ImageView) convertView.findViewById(R.id.head);
            holder.pic = (ImageView) convertView.findViewById(R.id.pic);
            holder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final ShaifanerObj typeBean = lists.get(position);
        if(typeBean != null){
            holder.title.setText(typeBean.getNick_name());
            imageLoader.displayImage(InternetURL.INTERNAL_PIC + typeBean.getCover(), holder.head, ThirdApplication.txOptions, animateFirstListener);
            String[] arr = typeBean.getImage_str().split(",");
            if(arr!= null && arr.length >0){
                imageLoader.displayImage(InternetURL.INTERNAL_PIC + arr[0], holder.pic, ThirdApplication.options, animateFirstListener);
            }
        }
        return convertView;
    }
    class ViewHolder {
        ImageView head;
        ImageView pic;
        TextView title;
    }
}
