package com.image.view;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.thirdapp.R;
import com.example.thirdapp.ThirdApplication;
import com.example.thirdapp.adapter.AnimateFirstDisplayListener;
import com.example.thirdapp.adapter.OnClickContentItemListener;
import com.example.thirdapp.base.InternetURL;
import com.example.thirdapp.module.HotGoodsObj;
import com.example.thirdapp.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class MyAdapter extends BaseAdapter{

	private List<HotGoodsObj> list = null;
	private Context mContext;

	public MyAdapter(Context mContext, List<HotGoodsObj> list) {
		this.mContext = mContext;
		this.list = list;

	}

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

	private OnClickContentItemListener onClickContentItemListener;

	public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
		this.onClickContentItemListener = onClickContentItemListener;
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.itemcommodity, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.namecom);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.price = (TextView) view.findViewById(R.id.pricecom);
			viewHolder.originalprice = (TextView) view.findViewById(R.id.priceoriginalcom);
			viewHolder.image = (ImageView) view.findViewById(R.id.image);
			viewHolder.carImg = (ImageView) view.findViewById(R.id.carImg);
			viewHolder.imagebig = (ImageView) view.findViewById(R.id.imagebig);
			viewHolder.promotioncom = (ImageView) view.findViewById(R.id.promotioncom);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		final HotGoodsObj hotGoodsObj = list.get(position);
//		LinearLayout layout = (LinearLayout) view.findViewById(R.id.linear);
//		if (position == 0) {
//			layout.setVisibility(View.VISIBLE);
//			viewHolder.image.setVisibility(View.VISIBLE);
//			viewHolder.image.setImageResource(list.get(position).getImage());
//			viewHolder.tvLetter.setVisibility(View.GONE);
//			viewHolder.tvLetter.setText(mContent.getLetter());
//		} else {
//			String lastCatalog = list.get(position - 1).getLetter();
//			int lastimage = list.get(position -1).getImage();
//			if (mContent.getLetter().equals(lastCatalog) && mContent.getImage() == lastimage) {
//				layout.setVisibility(View.GONE);
//				viewHolder.tvLetter.setVisibility(View.GONE);
//				viewHolder.image.setVisibility(View.GONE);
//			} else {
//				layout.setVisibility(View.VISIBLE);
//				viewHolder.tvLetter.setVisibility(View.GONE);
//				viewHolder.tvLetter.setText(mContent.getLetter());
//				viewHolder.image.setVisibility(View.VISIBLE);
//				viewHolder.image.setImageResource(list.get(position).getImage());
//			}
//		}

		if(hotGoodsObj != null){
			imageLoader.displayImage(InternetURL.INTERNAL_PIC + hotGoodsObj.getProduct_pic(), viewHolder.imagebig,
					ThirdApplication.options, animateFirstListener);

			String title = hotGoodsObj.getProduct_name()==null?"":hotGoodsObj.getProduct_name();
			if(title.length() >30){
				title = title.substring(0,29);
			}

			viewHolder.tvTitle.setText(title);
//		viewHolder.imagebig.setImageResource(list.get(position).getImageBig());
			viewHolder.price.setText(hotGoodsObj.getPrice_tuangou());
			viewHolder.originalprice.setText(hotGoodsObj.getPrice());
			viewHolder.originalprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);

			if(!StringUtil.isNullOrEmpty(hotGoodsObj.getDiscount_type())){
				viewHolder.promotioncom.setVisibility(View.VISIBLE);
				if("full_reduce".equals(hotGoodsObj.getDiscount_type())){
					viewHolder.promotioncom.setImageDrawable(mContext.getResources().getDrawable(R.drawable.man1));
				}if("reduce_20".equals(hotGoodsObj.getDiscount_type())){
					viewHolder.promotioncom.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ershi));
				}
			}else {
				viewHolder.promotioncom.setVisibility(View.GONE);
			}

			viewHolder.carImg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickContentItemListener.onClickContentItem(position, 1, "000");
				}
			});

		}

		return view;

	}
	


	final static class ViewHolder {
		TextView tvTitle;
		TextView tvLetter;
		ImageView image;
		ImageView imagebig;
		ImageView carImg;
		ImageView promotioncom;
		TextView price;
		TextView originalprice;
	}


//	public Object[] getSections() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public int getSectionForPosition(int position) {
//
//		return 0;
//	}

//	public int getPositionForSection(int section) {
//		Content mContent;
//		String l;
//		if (section == '!') {
//			return 0;
//		} else {
//			for (int i = 0; i < getCount(); i++) {
//				mContent = (Content) list.get(i);
//				l = mContent.getLetter();
//				char firstChar = l.toUpperCase().charAt(0);
//				if (firstChar == section) {
//					return i + 1;
//				}
//
//			}
//		}
//		mContent = null;
//		l = null;
//		return -1;
//	}
}