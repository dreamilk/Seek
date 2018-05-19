package cn.shanghq.seek.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.shanghq.seek.Adapter.NewsRecyclerAdapter;
import cn.shanghq.seek.Adapter.NewsViewPagerAdapter;
import cn.shanghq.seek.AsyncTask.NewsAsyncTask;
import cn.shanghq.seek.DataSet.NewsData;
import cn.shanghq.seek.ImgViewActivity;
import cn.shanghq.seek.Interface.OnItemClickListener;
import cn.shanghq.seek.Listener.EndLessOnScrollListener;
import cn.shanghq.seek.NewsActivity;
import cn.shanghq.seek.R;

/**
 * Created by 17634 on 2018/1/2.
 */

public class NewsFragment extends Fragment {

    List<View> viewList = new ArrayList<>();
    List<RecyclerView> recyclerViewList = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    ArrayList<ArrayList<NewsData>> lists = new ArrayList<>();
    private View view;
    private TabLayout mTabs;
    private ViewPager viewPager;
    private NewsViewPagerAdapter adapter;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        mTabs = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.news_ViewPager);

        setHasOptionsMenu(true);//设置菜单

        titles.add("头条");
        titles.add("新闻");
        titles.add("财经");
        titles.add("体育");
        titles.add("娱乐");
        titles.add("军事");
        titles.add("教育");
        titles.add("科技");
        titles.add("NBA");
        titles.add("股票");
        titles.add("星座");
        titles.add("女性");
        titles.add("健康");
        titles.add("育儿");

        for(int i=0;i<titles.size();i++) {
            ArrayList<NewsData> dataArrayList = new ArrayList<>();
            lists.add(dataArrayList);

            viewList.add(getLayoutInflater().inflate(R.layout.news_viewpager,null));
            RecyclerView recyclerView=viewList.get(i).findViewById(R.id.news_recyclerview);
            recyclerViewList.add(recyclerView);
            lists.add(new ArrayList<NewsData>());
        }

        adapter = new NewsViewPagerAdapter(viewList, titles, getContext());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initNews(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabs.setupWithViewPager(viewPager);
        mTabs.setTabsFromPagerAdapter(adapter);

        //初始化
        viewPager.setCurrentItem(0);
        initNews(0);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        titles.clear();
        viewList.clear();
        recyclerViewList.clear();
        lists.clear();
    }

    private void setNews(int item,NewsRecyclerAdapter adapter){
        new NewsAsyncTask(titles.get(item),10,lists.get(item).size(),lists.get(item),adapter).execute();
    }

    private void initNews(final int pos) {
        RecyclerView recyclerView = recyclerViewList.get(pos);
        if (recyclerView.getAdapter() != null) {

        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            final NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(getContext(), lists.get(pos), new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), NewsActivity.class);
                    intent.putExtra("title",lists.get(pos).get(position).getTitle());
                    intent.putExtra("src",lists.get(pos).get(position).getSrc());
                    intent.putExtra("content",lists.get(pos).get(position).getContent());
                    intent.putExtra("category",lists.get(pos).get(position).getCategory());
                    intent.putExtra("time",lists.get(pos).get(position).getTime());
                    intent.putExtra("pic",lists.get(pos).get(position).getPic());
                    intent.putExtra("url",lists.get(pos).get(position).getUrl());
//                    String string="pic";
//                    ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(getActivity(),view,string);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            recyclerView.addOnScrollListener(new EndLessOnScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int currentPage) {
                    setNews(pos,adapter);
                }

                @Override
                public void onScroll() {

                }
            });

            if(lists.get(pos).size()<1){
                setNews(pos,adapter);
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.nav_news_search){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_news,menu);

        //设置menu的按钮
        MenuItem item=menu.findItem(R.id.nav_news_search);
        searchView= (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(),"click",Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }

}
