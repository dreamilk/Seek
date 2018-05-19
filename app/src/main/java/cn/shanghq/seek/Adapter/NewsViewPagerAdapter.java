package cn.shanghq.seek.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.squareup.picasso.Transformation;

import java.util.List;

import cn.shanghq.seek.R;

/**
 * Created by 17634 on 2018/1/2.
 */

public class NewsViewPagerAdapter extends PagerAdapter {

    private List<View> views;
    private List<String> titles;
    private Context context;

    public NewsViewPagerAdapter(List<View> views, List<String> titles, Context context){
        this.views=views;
        this.titles=titles;
        this.context=context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
