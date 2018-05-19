package cn.shanghq.seek.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.shanghq.seek.Adapter.ImgsRecyclerAdapter;
import cn.shanghq.seek.AsyncTask.ImgsAsyncTask;
import cn.shanghq.seek.ImgViewActivity;
import cn.shanghq.seek.Interface.AddMoreInterface;
import cn.shanghq.seek.Interface.OnItemClickListener;
import cn.shanghq.seek.Listener.AddMoreScrollListener;
import cn.shanghq.seek.R;

/**
 * Created by 17634 on 2018/1/2.
 */

public class ImgsFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ImgsRecyclerAdapter adapter;
    private List<String> lists = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManager;
    private List<ArrayList<String>> arrayLists;

    private int index;
    private int count;

    private String imgUrlHead = "http://zzd.sm.cn/iflow/api/v1/article/category/meinv?uc_param_str=dnnivebichfrmintcpgieiwidsudpf&zzd_from=webapp&app=webapp&is_h5=1&client_os=webapp&sn=15458313099352789428&";
    private String hotterUrl = "&method=hotter&callback=jQuery1102034903776928995334_1515210402661&_=1515210402662";
    private String newwerUrl = "&method=newer&callback=jQuery1102034903776928995334_1515210402661&_=1515210402663";
    private String UrlType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_grils, container, false);
        initView();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = 0;
        count = 10;
        arrayLists = new ArrayList<>();
        UrlType = newwerUrl;
    }

    private void setImg() {
        new ImgsAsyncTask(imgUrlHead + "index=" + index + "&count=" + count + UrlType, adapter, arrayLists).execute();
        adapter.notifyDataSetChanged();
        index = index + count;
    }

    private void initView() {

        swipeRefreshLayout = view.findViewById(R.id.girls_swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.girls_recycleView);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ImgsRecyclerAdapter(getContext(), lists, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ImgViewActivity.class);
                intent.putStringArrayListExtra("ImgList", arrayLists.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(), "Long click" + position, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.background_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_orange_dark));

        swipeRefreshLayout.setDistanceToTriggerSync(500);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.addOnScrollListener(new AddMoreScrollListener(new AddMoreInterface() {
            @Override
            public void onLoadMore() {
                setImg();
                Snackbar.make(view, "加载更多", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScroll() {

            }
        }));
        //如果数据为空则加载数据
        if (lists.size() == 0) {
            setImg();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_imgs, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Snackbar.make(getView(),"监听到了",Toast.LENGTH_SHORT).show();
        if(item.getItemId()==R.id.nav_img_change){
            if(UrlType==hotterUrl){
                UrlType=newwerUrl;
                index=0;
                adapter.getImageViewList().clear();
                arrayLists.clear();
                adapter.notifyItemRangeRemoved(0,adapter.getItemCount());
                setImg();
            }else {
                UrlType=hotterUrl;
                index=0;
                adapter.getImageViewList().clear();
                arrayLists.clear();
                adapter.notifyItemRangeRemoved(0,adapter.getItemCount());
                setImg();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
