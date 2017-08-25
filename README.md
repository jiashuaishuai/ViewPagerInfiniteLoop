# ViewPagerInfiniteLoop
ViewPager的无限循环滚动，滑动流畅不卡顿

实现原理：

![](https://github.com/jiashuaishuai/ViewPagerInfiniteLoop/blob/master/7D4A80B1-2DE9-4E48-B6E0-CDB2DD415C14.png)

拓展知识：


        public void onPageScrollStateChanged(int state) {  
            switch (state) {  
            case ViewPager.SCROLL_STATE_DRAGGING://1 dragging（拖动），理解为：只要触发拖动/滑动事件时
                misScrolled = false;  
                Log.e("onPageScrollStateChanged", "state:"+state+"---------->misScrolled:"+misScrolled+"---------->现在的页码索引:"+mViewPager.getCurrentItem());  
                break;  
            case ViewPager.SCROLL_STATE_SETTLING://2 settling(安放、定居、解决)，理解为：通过拖动/滑动，安放到了目标页
                misScrolled = true;  
                Log.e("onPageScrollStateChanged", "state:"+state+"---------->misScrolled:"+misScrolled+"---------->现在的页码索引:"+mViewPager.getCurrentItem());  
                break;  
            case ViewPager.SCROLL_STATE_IDLE://0 idle(空闲，挂空挡)， 理解为：只要拖动/滑动结束，无论是否安放到了目标页
                if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !misScrolled) {//如果当前页是最后一页，并且滑动，则触发finish()  
                    /*此处可写一些逻辑，如finish() 或 startActivity() 
                    finish();*/  
                    Log.e("在末页向左滑", "state:"+state+"---------->misScrolled:"+misScrolled+"---------->现在的页码索引:"+mViewPager.getCurrentItem());  
                    Toast.makeText(mContext, "已经是最后一页", 2).show();  
                }  
                if (mViewPager.getCurrentItem() == 0 && !misScrolled) {//如果当前页是第一页，并且滑动，则触发finish()  
                    /*此处可写一些逻辑，如finish() 或 startActivity() 
                    finish();*/  
                    Log.e("在首页向右滑", "state:"+state+"---------->misScrolled:"+misScrolled+"---------->现在的页码索引:"+mViewPager.getCurrentItem());  
                    Toast.makeText(mContext, "已经是第一页", 2).show();  
                }  
                misScrolled = true;  
                Log.e("onPageScrollStateChanged", "state:"+state+"---------->misScrolled:"+misScrolled+"---------->现在的页码索引:"+mViewPager.getCurrentItem());  
                break;  
            }  
        }  
    }
    
   #ViewPager.PageTransformer ViewPager切换动画实现解析
   实现方法：
   
   my_viewpager.setPageTransformer(true,new DepthPagerTransformer());
   
   PageTransformer接口中实现public void transformPage(View page, float position)方法，<br>
   page： 顾名思义这里不多做解释；<br>
   position：该值有三个零界点-1，0，1<br>
         * 0  代表当前屏幕显示的view的坐标<br>
         * 1  代表当前view的下一个view所在的坐标<br>
         * -1 代表当前view的前一个view所在的坐标<br>
         
   ![](https://github.com/jiashuaishuai/ViewPagerInfiniteLoop/blob/master/A403EF71-AA89-4F39-8554-701D4AFCDDEB.png)
   
   当ViewPager左右滑动时变化情况：
   
   ![](https://github.com/jiashuaishuai/ViewPagerInfiniteLoop/blob/master/48CB865D-3212-4064-8604-74BB65CBFB3E.png)
   
   
   ##向右滑动数据变化情况：<br>
08-25 14:14:28.143 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.0018115942
08-25 14:14:28.143 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.9981884
08-25 14:14:28.160 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.008152174
08-25 14:14:28.160 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.9918478
08-25 14:14:28.176 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.014492754
08-25 14:14:28.176 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.98550725
08-25 14:14:28.193 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.019927537
08-25 14:14:28.193 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.98007244
08-25 14:14:28.215 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.02807971
08-25 14:14:28.215 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.9719203
08-25 14:14:28.232 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.033514492
08-25 14:14:28.232 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.9664855
08-25 14:14:28.249 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.039855074
08-25 14:14:28.249 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.96014494
08-25 14:14:28.265 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.046195652
..........
08-25 14:14:29.334 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.009057971
08-25 14:14:29.350 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.99365944
08-25 14:14:29.351 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.0063405796
08-25 14:14:29.367 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.995471
08-25 14:14:29.367 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.0045289854
08-25 14:14:29.384 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.9963768
08-25 14:14:29.384 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.0036231885
08-25 14:14:29.400 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.9972826
08-25 14:14:29.400 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.0027173914
08-25 14:14:29.417 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.9981884
08-25 14:14:29.417 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.0018115942
08-25 14:14:29.434 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.9990942
08-25 14:14:29.434 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -9.057971E-4
08-25 14:14:29.484 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  1.0
08-25 14:14:29.484 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: Current--------   0.0
08-25 14:14:29.668 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -1.0
08-25 14:14:29.668 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: Current--------   0.0
08-25 14:14:29.668 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  1.0
       
##向左滑动数据变化情况：<br>

08-25 14:20:35.861 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.010869565
08-25 14:20:35.861 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.98913044
08-25 14:20:35.877 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.026268115
08-25 14:20:35.877 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.9737319
08-25 14:20:35.894 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.04076087
08-25 14:20:35.894 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.9592391
08-25 14:20:35.911 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.054347824
08-25 14:20:35.911 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.9456522
08-25 14:20:35.932 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.07155797
08-25 14:20:35.933 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.928442
................
08-25 14:20:36.534 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.007246377
08-25 14:20:36.550 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.9945652
08-25 14:20:36.551 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.0054347827
08-25 14:20:36.567 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.9963768
08-25 14:20:36.567 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.0036231885
08-25 14:20:36.584 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.9972826
08-25 14:20:36.584 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.0027173914
08-25 14:20:36.600 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.9981884
08-25 14:20:36.600 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  0.0018115942
08-25 14:20:36.617 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -0.9990942
08-25 14:20:36.617 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  9.057971E-4
08-25 14:20:36.650 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -1.0
08-25 14:20:36.651 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: Current--------   0.0
08-25 14:20:36.835 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 后  >>>>>>>>>>  1.0
08-25 14:20:36.835 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: Current--------   0.0
08-25 14:20:36.835 1157-1157/com.example.jiashuai.viewpagerinfiniteloop E/tag: 前  <<<<<<<<<  -1.0
