package com.example.jiashuai.viewpagerinfiniteloop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager my_viewpager;
    private LinearLayout my_pager_lly;
    private int[] imgList = {R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon5};
    public Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        my_viewpager = (ViewPager) findViewById(R.id.my_viewpager);
        my_pager_lly = (LinearLayout) findViewById(R.id.my_pager_lly);
        my_viewpager.setAdapter(new ViewPagerAdapter());
//        my_viewpager.setPageTransformer(true,new DepthPagerTransformer());//滑动动画，这里改的透明度
        my_viewpager.setCurrentItem(1);
        my_viewpager.setOffscreenPageLimit(3);
        my_viewpager.setPageMargin(30);
        my_viewpager.addOnPageChangeListener(this);
        /**
         * 滑动传递
         */
        my_pager_lly.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return my_viewpager.dispatchTouchEvent(event);
            }
        });

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_DRAGGING) {//滚动结束和滚动开始
            int position = my_viewpager.getCurrentItem();
            if (position < 1) {//当前pager小于1则偷梁换柱跳转到倒数第二个位置，imgList.length
                position = imgList.length;
                my_viewpager.setCurrentItem(position, false);
            } else if (position > imgList.length) {//当前pager大于数据数组长度时则，跳转到第二个位置， 1
                position = 1;
                my_viewpager.setCurrentItem(position, false);
            }
        }
    }


    class ViewPagerAdapter extends PagerAdapter {
        SparseArray<ImageView> imageViews;

        public ViewPagerAdapter() {
            imageViews = new SparseArray<>(imgList.length + 2);
        }

        @Override
        public int getCount() {
            return imgList.length + 2;
        }

        /**
         * 判断出去的view是否等于进来的view 如果为true直接复用
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        /**
         * 销毁预加载以外的view对象, 会把需要销毁的对象的索引位置传进来就是position
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
        }

        /**
         * 创建一个view
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (imageViews.get(position) == null) {
                ImageView img = new ImageView(mContext);
                img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                int c = position - 1;//当前viewIndex-1
                if (position == 0)//第一个view对应的是数据列表最后一个src
                {
                    c = imgList.length - 1;
                }
                if (position - 1 == imgList.length)//最后一个view里装数据列表第一个src
                {
                    c = 0;
                }
                img.setBackgroundResource(imgList[c]);
                imageViews.put(position, img);
            }
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

    }
}
