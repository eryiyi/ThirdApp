package com.example.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.module.HotGoodsObj;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 */
public class ItemGoodsAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<HotGoodsObj> findEmps;
    private Context mContext;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    private OnClickContentItemListener onClickContentItemListener;

    public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
        this.onClickContentItemListener = onClickContentItemListener;
    }

    public ItemGoodsAdapter(List<HotGoodsObj> findEmps, Context mContext) {
        this.findEmps = findEmps;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return findEmps.size();
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.itemcommodity, null);
            holder.imagecom = (ImageView) convertView.findViewById(R.id.imagebig);
            holder.namecom = (TextView) convertView.findViewById(R.id.namecom);
            holder.promotioncom = (TextView) convertView.findViewById(R.id.promotioncom);
            holder.pricecom = (TextView) convertView.findViewById(R.id.pricecom);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.carImg = (ImageView) convertView.findViewById(R.id.carImg);
            holder.letter = (TextView) convertView.findViewById(R.id.catalog);
            holder.priceoriginalcom = (TextView) convertView.findViewById(R.id.priceoriginalcom);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HotGoodsObj cell = findEmps.get(position);
        imageLoader.displayImage(InternetURL.INTERNAL_PIC + cell.getProduct_pic(), holder.imagecom, ThirdApplication.options, animateFirstListener);
        holder.namecom.setText(cell.getProduct_name());
        holder.promotioncom.setText(cell.getProduct_name());
        holder.pricecom.setText("￥"+cell.getPrice_tuangou());
        holder.priceoriginalcom.setText("￥"+cell.getPrice());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickContentItemListener.onClickContentItem(position, 1, null);
            }
        });
        holder.carImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickContentItemListener.onClickContentItem(position, 2, null);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView imagecom;
        TextView promotioncom;
        TextView namecom;
        TextView pricecom;
        TextView priceoriginalcom;
        ImageView image;
        ImageView carImg;
        TextView letter;
    }

}