package cn.shanghq.seek.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import cn.shanghq.seek.Adapter.JokesRecyclerAdapter;
import cn.shanghq.seek.AsyncTask.JokesAsyncTask;
import cn.shanghq.seek.Interface.AddMoreInterface;
import cn.shanghq.seek.Listener.AddMoreScrollListener;
import cn.shanghq.seek.Listener.EndLessOnScrollListener;
import cn.shanghq.seek.MainActivity;
import cn.shanghq.seek.R;

/**
 * Created by 17634 on 2018/1/2.
 */

public class JokeFragment extends Fragment {

    private View view;
    private JokesRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<String> arrayList=new ArrayList<>();
    private int index;
    private int count;
    private String urlType;
    private String urlHeader="http://zzd.sm.cn/iflow/api/v1/article/category/youmoduanzi?uc_param_str=dnnivebichfrmintcpgieiwidsudpf&zzd_from=webapp&app=webapp&is_h5=1&client_os=webapp&sn=15458313099352789428&";
    private String urlNewer="&method=newer&callback=jQuery1102015342114723570455_1515662193354&_=1515662193356";
    private String urlHotter="&method=hotter&callback=jQuery110205554003473810629_1515723991516&_=1515723991518";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_joke,container,false);
        initView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList=new ArrayList<>();
        index=0;
        count=10;
        urlType=urlHotter;
    }


    private void initView() {
        setHasOptionsMenu(true);//设置菜单
        //设置布局
        recyclerView=view.findViewById(R.id.jokes_recycleView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout=view.findViewById(R.id.jokes_swipeRefreshLayout);

        adapter=new JokesRecyclerAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        if(arrayList.size()==0){
            setJokes();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.addOnScrollListener(new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                setJokes();
            }

            @Override
            public void onScroll() {

            }
        });

    }

    private void setJokes() {
        String url=urlHeader+"index="+index+"&count="+count+urlType;
        new JokesAsyncTask(url,adapter,arrayList).execute();
        index=index+count;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_jokes, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
