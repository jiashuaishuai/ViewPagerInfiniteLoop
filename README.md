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
   
   PageTransformer接口中实现public void transformPage(View page, float position)方法，
   page： 顾名思义这里不多做解释；
   position：该值有三个零界点-1，0，1
         * 0  代表当前屏幕显示的view的坐标
         * 1  代表当前view的下一个view所在的坐标
         * -1 代表当前view的前一个view所在的坐标
         
   ![](https://github.com/jiashuaishuai/ViewPagerInfiniteLoop/blob/master/A403EF71-AA89-4F39-8554-701D4AFCDDEB.png)
   
   当ViewPager左右滑动时变化情况：
   
   ![](https://github.com/jiashuaishuai/ViewPagerInfiniteLoop/blob/master/48CB865D-3212-4064-8604-74BB65CBFB3E.png)
       
