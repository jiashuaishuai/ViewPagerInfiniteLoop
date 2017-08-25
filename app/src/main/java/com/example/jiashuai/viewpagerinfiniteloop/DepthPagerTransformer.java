package com.example.jiashuai.viewpagerinfiniteloop;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by JiaShuai on 2017/8/25.
 * position 有三个临界值-1，0，1
 * 0  代表当前屏幕显示的view和position，
 * 1  代表当前view的下一个view所在的position，
 * -1  代表当前view的前一个view所在的position。
 *
 *
 */

public class DepthPagerTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {

//        if (position > -1) {
//            Log.e("tag", "大于-1");
//        } else if (position > 0) {
//            Log.e("tag", "大于0");
//        } else if (position > 0) {
//            Log.e("tag", "大于0");
//        }
        float alpha = 0.0f;
        if (0.0f == position) {
            alpha = 1.0f - position;
            Log.e("tag", "Current--------   " + position);
        } else if (0.0f < position && position <= 1.0f) {
            alpha = 1.0f - position;
            Log.e("tag", "后  >>>>>>>>>>  " + position);
        } else if (-1.0f <= position && position < 0.0f) {
            alpha = position + 1.0f;
            Log.e("tag", "前  <<<<<<<<<  " + position);
        }
        page.setAlpha(alpha);
    }
}
