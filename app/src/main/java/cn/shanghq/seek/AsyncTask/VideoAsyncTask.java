package cn.shanghq.seek.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import cn.shanghq.seek.Adapter.VideoRecyclerAdapter;

/**
 * Created by 17634 on 2018/2/13.
 */

public class VideoAsyncTask extends AsyncTask {

    public final String url="https://www.qiushibaike.com/imgrank/page/";
    private ArrayList<String[]> arrayList;
    private VideoRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int index;

    public VideoAsyncTask(ArrayList<String[]> arrayList,VideoRecyclerAdapter adapter,SwipeRefreshLayout swipeRefreshLayout,int index){
        this.arrayList=arrayList;
        this.adapter=adapter;
        this.swipeRefreshLayout=swipeRefreshLayout;
        this.index=index;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        ArrayList<String[]> arrayList=new ArrayList<>();
        Document doc=null;

        try {
            Document document=Jsoup.connect(url+index+"/").get();
            Element element=document.select("#content-left").first();
            Elements elements=element.children();

            for(int i=0;i<elements.size()-1;i++) {
                Element title=elements.get(i).select("div.content>span").first();

                Element img=elements.get(i).select("div.thumb>a>img").first();
                String src=img.attr("src");

                String[] strings={title.html(),src.substring(2,src.length())};
                arrayList.add(strings);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        ArrayList<String[]> list= (ArrayList<String[]>) o;
        for(int i=0;i<list.size();i++){
            arrayList.add(list.get(i));
        }
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }
}
