package com.mz.gallerybanner;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：Created by MZ on 2018/4/4.
 * 邮箱：maozhou3@163.com
 * QQ：770627270
 */

public class GalleryBannerView<T> extends ViewPager {

    private SimpleOnPageChangeListener simpleOnPageChangeListener;
    private Timer mTimer;

    public GalleryBannerView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public GalleryBannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public void setOnPageChangeListener(SimpleOnPageChangeListener simpleOnPageChangeListener) {
        this.simpleOnPageChangeListener = simpleOnPageChangeListener;
    }

    private void init(@Nullable AttributeSet attrs) {
        setPageMargin(0);
        setPageTransformer(true, new ScaleInAlphaTransformer());
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (simpleOnPageChangeListener != null) {
                    simpleOnPageChangeListener.onPageScrolled(position % ((MyPagerAdapter) getAdapter()).getRealCount(),
                            positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (simpleOnPageChangeListener != null) {
                    simpleOnPageChangeListener.onPageSelected(position % ((MyPagerAdapter) getAdapter()).getRealCount());
                }
            }
        });

        ViewPagerScroller pagerScroller = new ViewPagerScroller(this.getContext());
        pagerScroller.setScrollDuration(1500);//设置时间，时间越长，速度越慢
        pagerScroller.initViewPagerScroll(this);
    }

    public void update(@Nullable List<T> dataList, @NonNull @LayoutRes int layoutId, @NonNull AdapterCallback<T> adapterCallback, @Nullable OnItemClickEvent onItemClickEvent) {
        MyPagerAdapter<T> mAdapter = new MyPagerAdapter<T>(getContext());
        mAdapter.setLayoutId(layoutId);
        mAdapter.setAdapterCallback(adapterCallback);
        mAdapter.setDataList(dataList);
        mAdapter.setOnItemClickEvent(onItemClickEvent);
        setAdapter(mAdapter);
        setOffscreenPageLimit(dataList.size() - 1);
    }

    /**
     * 调用定时任务，开始滚动
     */
    public void startScroll() {
        stopScroll();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = getCurrentItem();
                        if (currentItem == ((MyPagerAdapter) getAdapter()).getRealCount() - 1) {
                            currentItem = 0;
                        } else {
                            currentItem++;
                        }
                        setCurrentItem(currentItem, true);
                    }
                });
            }
        }, 4000, 4000);
    }

    //停止滚动
    public void stopScroll() {
        if (mTimer != null) {
            //removeCallbacks方法是删除指定的Runnable对象，使线程对象停止运行
            mTimer.cancel();
            mTimer = null;
        }
    }

    class MyPagerAdapter<T> extends PagerAdapter {
        private Context context;
        private List<T> dataList = new ArrayList<>();
        private int layoutId;
        private AdapterCallback<T> adapterCallback;
        private OnItemClickEvent onItemClickEvent;

        public MyPagerAdapter(Context context) {
            this.context = context;
        }

        public void setDataList(List<T> dataList) {
            if (dataList == null) {
                dataList = new ArrayList<>();
            }
            this.dataList = dataList;
            notifyDataSetChanged();
        }

        public void setLayoutId(@LayoutRes int layoutId) {
            this.layoutId = layoutId;
        }

        public void setAdapterCallback(AdapterCallback<T> adapterCallback) {
            this.adapterCallback = adapterCallback;
        }

        public void setOnItemClickEvent(OnItemClickEvent onItemClickEvent) {
            this.onItemClickEvent = onItemClickEvent;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(layoutId, null);
            final int realPosition = getRealPosition(position);

            if (adapterCallback != null) {
                adapterCallback.convert(view, dataList.get(realPosition), realPosition);
            }
            if (onItemClickEvent != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickEvent.onItemClick(v, dataList.get(realPosition), realPosition);
                    }
                });
            }

            container.addView(view);
            return view;
        }


        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            if (dataList.size() == 0) {
                return 0;
            }
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
            ViewPager viewPager = (ViewPager) container;
            int position = viewPager.getCurrentItem();
            if (position == 0) {
                position = getFirstItemPosition();
            } else if (position == getCount() - 1) {
                position = getLastItemPosition();
            }
            viewPager.setCurrentItem(position, false);

        }

        private int getRealCount() {
            return dataList.size();
        }

        private int getRealPosition(int position) {
            return position % getRealCount();
        }

        private int getFirstItemPosition() {
            if (getRealCount() == 0) {
                return 0;
            }
            return Integer.MAX_VALUE / getRealCount() / 2 * getRealCount();
        }

        private int getLastItemPosition() {
            return Integer.MAX_VALUE / getRealCount() / 2 * getRealCount() - 1;
        }
    }

    public interface AdapterCallback<T> {
        void convert(View view, T t, int position);
    }

    public interface OnItemClickEvent<T> {
        void onItemClick(View view, T t, int position);
    }
}
