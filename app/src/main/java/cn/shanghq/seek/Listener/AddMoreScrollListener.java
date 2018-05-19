package cn.shanghq.seek.Listener;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import cn.shanghq.seek.Interface.AddMoreInterface;

/**
 * Created by 17634 on 2018/1/5.
 */

public  class AddMoreScrollListener extends RecyclerView.OnScrollListener {


    //用来标记是否正在向最后一个滑动，既是否向下滑动
    boolean isSlidingToLast = false;
    private AddMoreInterface addMoreInterface;

    public AddMoreScrollListener(AddMoreInterface addMoreInterface){
        this.addMoreInterface=addMoreInterface;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        // 当不滚动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的ItemPosition
            int[] lastVisiblePositions = manager.findLastVisibleItemPositions(new int[manager.getSpanCount()]);
            int lastVisiblePos = getMaxElem(lastVisiblePositions);
            int totalItemCount = manager.getItemCount();

            // 判断是否滚动到底部
            if (lastVisiblePos == (totalItemCount -1) && isSlidingToLast) {
                //加载更多功能的代码
                addMoreInterface.onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
        if(dy > 0){
            //大于0表示，正在向下滚动
            isSlidingToLast = true;
        }else{
            //小于等于0 表示停止或向上滚动
            isSlidingToLast = false;
        }

        addMoreInterface.onScroll();

    }
    
    
    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i]>maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }
    
}
