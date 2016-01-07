package com.example.thirdapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;
import com.example.thirdapp.R;
import com.example.thirdapp.adapter.HouseInfoAdapter;

import java.util.Date;

public class MyListViewUpDown extends ListView implements OnScrollListener {

	public static final int REFRESH = 0;
	public static final int LOAD = 1;
	private final static int RELEASE_To_REFRESH = 0;// 下拉过程的状态值
	private final static int PULL_To_REFRESH = 1; // 从下拉返回到不刷新的状态值
	private final static int REFRESHING = 2;// 正在刷新的状态值
	private final static int DONE = 3;
	private final static int LOADING = 4;
	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;
	private LayoutInflater inflater;
	// ListView头部下拉刷新的布局
	private LinearLayout headerView;
	private LinearLayout footerView;
	private TextView lvHeaderTipsTv;
	private TextView lvHeaderLastUpdatedTv;
	private ImageView lvHeaderArrowIv;
	private ProgressBar lvHeaderProgressBar;
	private TextView noData;
	private TextView loadFull;
	private TextView more;
	private ProgressBar loading;
	// 定义头部下拉刷新的布局的高度
	private int headerContentHeight;
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	private int startY;
	private int state;
	private boolean isBack;
	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean isRecored;
	private OnRefreshListener refreshListener;
	private OnLoadListener onLoadListener;
	private boolean isRefreshable;
	private boolean isLoading;// 判断是否正在加载
	private boolean loadEnable = true;// 开启或者关闭加载更多功能
	private boolean isLoadFull;
	private int pageSize = 10;
	private int firstVisibleItem;
	private int scrollState;
	Context context;
	

	public MyListViewUpDown(Context context) {
		super(context);
		init(context);
	}

	public MyListViewUpDown(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		setCacheColorHint(context.getResources().getColor(R.color.transparent));
		inflater = LayoutInflater.from(context);
		headerView = (LinearLayout) inflater.inflate(R.layout.lv_header, null);
		lvHeaderTipsTv = (TextView) headerView.findViewById(R.id.lvHeaderTipsTv);
		lvHeaderLastUpdatedTv = (TextView) headerView.findViewById(R.id.lvHeaderLastUpdatedTv);
		lvHeaderArrowIv = (ImageView) headerView.findViewById(R.id.lvHeaderArrowIv);
		// 设置下拉刷新图标的最小高度和宽度
		lvHeaderArrowIv.setMinimumWidth(70);
		lvHeaderArrowIv.setMinimumHeight(50);
		lvHeaderProgressBar = (ProgressBar) headerView.findViewById(R.id.lvHeaderProgressBar);
		measureView(headerView);
		headerContentHeight = headerView.getMeasuredHeight();
		// 设置内边距，正好距离顶部为一个负的整个布局的高度，正好把头部隐藏
		headerView.setPadding(0, -1 * headerContentHeight, 0, 0);
		// 重绘一下
		headerView.invalidate();
		// 将下拉刷新的布局加入ListView的顶部
		addHeaderView(headerView, null, false);
		setOnScrollListener(this);
		// 设置旋转动画事件
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);
		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);
		// 一开始的状态就是下拉刷新完的状态，所以为DONE
		state = DONE;
		// 是否正在刷新
		isRefreshable = false;
		
		footerView = (LinearLayout) inflater.inflate(R.layout.lv_footer, null);
		loadFull = (TextView) footerView.findViewById(R.id.loadFull);
		noData = (TextView) footerView.findViewById(R.id.noData);
		more = (TextView) footerView.findViewById(R.id.more);
		loading = (ProgressBar) footerView.findViewById(R.id.loading);
		this.addFooterView(footerView);
		this.setOnScrollListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (isRefreshable) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (!isRecored) {
					isRecored = true;
					startY = (int) ev.getY();// 手指按下时记录当前位置
				}
				break;
			case MotionEvent.ACTION_UP:
				if (state != REFRESHING && state != LOADING) {
					if (state == PULL_To_REFRESH) {
						state = DONE;
						changeHeaderViewByState();
					}
					if (state == RELEASE_To_REFRESH) {
						state = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
					}
				}
				isRecored = false;
				isBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) ev.getY();
				if (!isRecored) {
					isRecored = true;
					startY = tempY;
				}
				if (state != REFRESHING && isRecored && state != LOADING) {
					// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
					// 可以松手去刷新了
					if (state == RELEASE_To_REFRESH) {
						setSelection(0);
						// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
						if (((tempY - startY) / RATIO < headerContentHeight)// 由松开刷新状态转变到下拉刷新状态
								&& (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
						// 一下子推到顶了
						else if (tempY - startY <= 0) {// 由松开刷新状态转变到done状态
							state = DONE;
							changeHeaderViewByState();
						}
					}
					// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
					if (state == PULL_To_REFRESH) {
						setSelection(0);
						// 下拉到可以进入RELEASE_TO_REFRESH的状态
						if ((tempY - startY) / RATIO >= headerContentHeight) {// 由done或者下拉刷新状态转变到松开刷新
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();
						}
						// 上推到顶了
						else if (tempY - startY <= 0) {// 由DOne或者下拉刷新状态转变到done状态
							state = DONE;
							changeHeaderViewByState();
						}
					}
					// done状态下
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}
					// 更新headView的size
					if (state == PULL_To_REFRESH) {
						headerView.setPadding(0, -1 * headerContentHeight
								+ (tempY - startY) / RATIO, 0, 0);

					}
					// 更新headView的paddingTop
					if (state == RELEASE_To_REFRESH) {
						headerView.setPadding(0, (tempY - startY) / RATIO
								- headerContentHeight, 0, 0);
					}

				}
				break;

			    default:
				break;
			}
		}
		return super.onTouchEvent(ev);
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			lvHeaderArrowIv.setVisibility(View.VISIBLE);
			lvHeaderProgressBar.setVisibility(View.GONE);
			lvHeaderTipsTv.setVisibility(View.VISIBLE);
			lvHeaderLastUpdatedTv.setVisibility(View.VISIBLE);
			lvHeaderArrowIv.clearAnimation();// 清除动画
			lvHeaderArrowIv.startAnimation(animation);// 开始动画效果
			lvHeaderTipsTv.setText("松开刷新");
			break;
		case PULL_To_REFRESH:
			lvHeaderProgressBar.setVisibility(View.GONE);
			lvHeaderTipsTv.setVisibility(View.VISIBLE);
			lvHeaderLastUpdatedTv.setVisibility(View.VISIBLE);
			lvHeaderArrowIv.clearAnimation();
			lvHeaderArrowIv.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
				lvHeaderArrowIv.clearAnimation();
				lvHeaderArrowIv.startAnimation(reverseAnimation);
				lvHeaderTipsTv.setText("下拉刷新");
			} else {
				lvHeaderTipsTv.setText("下拉刷新");
			}
			break;

		case REFRESHING:
			headerView.setPadding(0, 0, 0, 0);
			lvHeaderProgressBar.setVisibility(View.VISIBLE);
			lvHeaderArrowIv.clearAnimation();
			lvHeaderArrowIv.setVisibility(View.GONE);
			lvHeaderTipsTv.setText("正在刷新...");
			lvHeaderLastUpdatedTv.setVisibility(View.VISIBLE);
			break;
		case DONE:
			headerView.setPadding(0, -1 * headerContentHeight, 0, 0);
			lvHeaderProgressBar.setVisibility(View.GONE);
			lvHeaderArrowIv.clearAnimation();
			lvHeaderArrowIv.setImageResource(R.drawable.ic_pulltorefresh_arrow);
			lvHeaderTipsTv.setText("下拉刷新");
			lvHeaderLastUpdatedTv.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * 这个方法是根据结果的大小来决定footer显示的。
	 * <p>
	 * 这里假定每次请求的条数为10。如果请求到了10条。则认为还有数据。如过结果不足10条，则认为数据已经全部加载，这时footer显示已经全部加载
	 * </p>
	 * 
	 * @param resultSize
	 */
	public void setResultSize(int resultSize) {
		if (resultSize == 0) {
			isLoadFull = true;
			loadFull.setVisibility(View.GONE);
			loading.setVisibility(View.GONE);
			more.setVisibility(View.GONE);
			noData.setVisibility(View.VISIBLE);
		} else if (resultSize > 0 && resultSize < pageSize) {
			isLoadFull = true;
			loadFull.setVisibility(View.VISIBLE);
			loading.setVisibility(View.GONE);
			more.setVisibility(View.GONE);
			noData.setVisibility(View.GONE);
		} else if (resultSize == pageSize) {
			isLoadFull = false;
			loadFull.setVisibility(View.GONE);
			loading.setVisibility(View.VISIBLE);
			more.setVisibility(View.VISIBLE);
			noData.setVisibility(View.GONE);
		}

	}
	
	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	private void measureView(View child) {
		ViewGroup.LayoutParams params = child.getLayoutParams();
		if (params == null) {
			params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0,
				params.width);
		int lpHeight = params.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
	
	// 下拉刷新监听
	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}
	
	// 加载更多监听
	public void setOnLoadListener(OnLoadListener onLoadListener) {
			this.loadEnable = true;
			this.onLoadListener = onLoadListener;
	}
	
	public boolean isLoadEnable() {
		return loadEnable;
	}
	
	// 这里的开启或者关闭加载更多，并不支持动态调整
	public void setLoadEnable(boolean loadEnable) {
			this.loadEnable = loadEnable;
			this.removeFooterView(footerView);
	}
	
	/*
	 * 定义下拉刷新接口
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}

	/*
	 * 定义加载更多接口
	 */
	public interface OnLoadListener {
		public void onLoad();
	}
	
	// 用于下拉刷新结束后的回调
	public void onRefreshComplete() {
		state = DONE;
		lvHeaderLastUpdatedTv.setText("最近更新:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}
	
	// 用于加载更多结束后的回调
	public void onLoadComplete() {
			isLoading = false;
			footerView.setVisibility(View.GONE);
	}
	
	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	public void onLoad() {
		if (onLoadListener != null) {
			footerView.setVisibility(View.VISIBLE);
			onLoadListener.onLoad();
		}
	}
	
	public void setAdapter(HouseInfoAdapter adapter) {
		lvHeaderLastUpdatedTv.setText("最近更新:" + new Date().toLocaleString());
		super.setAdapter(adapter);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState = scrollState;
		ifNeedLoad(view, scrollState);
	}

	// 根据listview滑动的状态判断是否需要加载更多
	private void ifNeedLoad(AbsListView view, int scrollState) {
			if (!loadEnable) {
				return;
			}
			try{
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && !isLoading && view.getLastVisiblePosition() == view
								.getPositionForView(footerView) && !isLoadFull) {
					onLoad();
					isLoading = true;
				}
			}catch(Exception e){
		    
			}
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem == 0) {
			isRefreshable = true;
		} else {
			isRefreshable = false;
		}
		this.firstVisibleItem = firstVisibleItem;		
	}
}

