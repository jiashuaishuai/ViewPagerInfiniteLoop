# ViewPagerInfiniteLoop
ViewPager的无限循环滚动，滑动流畅不卡顿


## 先来学习一下ViewPager实现原理
ViewPager是一个页面容器，只是每一个页面都是整一屏幕，<br>
1. 流程：ViewPager控件每一次翻动都会去Adpater中去取一个View，然后缓存一个改混动方向的View。PagerAdapter的作用是让开发者自己实现取数据，将其填充自定义的单页VIew布局中。然后实现PagerAdapter的一些生命周期方法，返回View给ViewPager显示。简单的说Adapter就是一个数据容器，开发者自定义ViewPager的获取逻辑。<br>

2. ViewPager总是持有三个View实例（默认三个开发者可以修改）setOffscreenPageLimit(预加载个数),除了第一次，每次调用PagerAdapter的instantiateItem方法获取View实例的时候，传入的position都是当前页的下一页坐标，是为缓存一页，然后销毁掉上一页的上一页。<br>
过程：初始化0，1项；翻至1项，生成2项；翻至2项；销毁0项生成3项；<br>
如图：<br>

![](https://github.com/jiashuaishuai/ViewPagerInfiniteLoop/blob/master/8E51D341-7388-43FB-A259-76667C5EDB79.png)


## ViewPager无线滚动原理实现原理：

![](https://github.com/jiashuaishuai/ViewPagerInfiniteLoop/blob/master/7D4A80B1-2DE9-4E48-B6E0-CDB2DD415C14.png)



    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE||state == ViewPager.SCROLL_STATE_DRAGGING) {//滚动结束和滚动开始
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
   
## ViewPager.PageTransformer ViewPager切换动画实现解析
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
   
   
### 向右滑动数据变化情况：<br>
   
 后  >>>>>>>>>>  0.0018115942<br>
 前  <<<<<<<<<  -0.9981884<br>
 后  >>>>>>>>>>  0.008152174<br>
 前  <<<<<<<<<  -0.9918478<br>
 后  >>>>>>>>>>  0.014492754<br>
 前  <<<<<<<<<  -0.98550725<br>
 后  >>>>>>>>>>  0.019927537<br>
 前  <<<<<<<<<  -0.98007244<br>
 后  >>>>>>>>>>  0.02807971<br>
..........<br>
 前  <<<<<<<<<  -0.0027173914<br>
 后  >>>>>>>>>>  0.9981884<br>
 前  <<<<<<<<<  -0.0018115942<br>
 后  >>>>>>>>>>  0.9990942<br>
 前  <<<<<<<<<  -9.057971E-4<br>
 后  >>>>>>>>>>  1.0<br>
 Current--------   0.0<br>
 前  <<<<<<<<<  -1.0<br>
 Current--------   0.0<br>
 后  >>>>>>>>>>  1.0<br>
       
### 向左滑动数据变化情况：<br>

 前  <<<<<<<<<  -0.010869565<br>
 后  >>>>>>>>>>  0.98913044<br>
 前  <<<<<<<<<  -0.026268115<br>
 后  >>>>>>>>>>  0.9737319<br>
 前  <<<<<<<<<  -0.04076087<br>
 后  >>>>>>>>>>  0.9592391<br>
 前  <<<<<<<<<  -0.054347824<br>
 后  >>>>>>>>>>  0.9456522<br>
................<br>
 前  <<<<<<<<<  -0.9972826<br>
 后  >>>>>>>>>>  0.0027173914<br>
 前  <<<<<<<<<  -0.9981884<br>
 后  >>>>>>>>>>  0.0018115942<br>
 前  <<<<<<<<<  -0.9990942<br>
 后  >>>>>>>>>>  9.057971E-4<br>
 前  <<<<<<<<<  -1.0<br>
 Current--------   0.0<br>
 后  >>>>>>>>>>  1.0<br>
 Current--------   0.0<br>
 前  <<<<<<<<<  -1.0<br>
