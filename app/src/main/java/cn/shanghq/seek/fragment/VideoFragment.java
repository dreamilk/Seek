package cn.shanghq.seek.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import cn.shanghq.seek.Adapter.VideoRecyclerAdapter;
import cn.shanghq.seek.AsyncTask.VideoAsyncTask;
import cn.shanghq.seek.Listener.EndLessOnScrollListener;
import cn.shanghq.seek.R;

/**
 * Created by 17634 on 2018/1/2.
 */

public class VideoFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private VideoRecyclerAdapter adapter;
    private ArrayList<String[]> arrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int index;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList=new ArrayList<String[]>();
        index=1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_video,container,false);
        initViews();

        return view;
    }

    private void initViews() {
        recyclerView=view.findViewById(R.id.video_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout=view.findViewById(R.id.video_swiperefreshlayout);
        adapter=new VideoRecyclerAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.addOnScrollListener(new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                setVideo();
            }

            @Override
            public void onScroll() {

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                index=1;
                setVideo();
            }
        });

        if(arrayList.size()<1){
            setVideo();
        }


    }

    private void setVideo(){
        new VideoAsyncTask(arrayList,adapter,swipeRefreshLayout,index).execute();
        index=index+1;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
