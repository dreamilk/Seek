package cn.shanghq.seek.AsyncTask;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import cn.shanghq.seek.Adapter.NewsRecyclerAdapter;
import cn.shanghq.seek.DataSet.NewsData;
import cn.shanghq.seek.Utils.GsonUtils;
import cn.shanghq.seek.Utils.MyHttpTool;

/**
 * Created by 17634 on 2018/2/5.
 */

public class NewsAsyncTask extends AsyncTask {
    private String url;
    private ArrayList<NewsData> arrayList;
    private NewsRecyclerAdapter adapter;


    public NewsAsyncTask(String channel, int num, int start, ArrayList<NewsData> list, NewsRecyclerAdapter adapter){
        this.arrayList=list;
        this.adapter=adapter;
        url="https://way.jd.com/jisuapi/get?channel="+channel+"&num="+num+"&start="+start+"&appkey=c2795d80ebc63fc17ad35fa59f650dc4";
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        String result= null;
        try {
            result = MyHttpTool.getUrlResponse(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<NewsData> list=null;
        try {
            list= GsonUtils.parseNewsJson(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        ArrayList<NewsData> list= (ArrayList<NewsData>) o;
        for(int i=0;i<list.size();i++){;
            adapter.getDataArrayList().add(list.get(i));
        }
        adapter.notifyDataSetChanged();
    }
}
