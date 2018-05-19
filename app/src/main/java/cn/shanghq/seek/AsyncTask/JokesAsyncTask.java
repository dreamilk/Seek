package cn.shanghq.seek.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.shanghq.seek.Adapter.ImgsRecyclerAdapter;
import cn.shanghq.seek.Adapter.JokesRecyclerAdapter;
import cn.shanghq.seek.Utils.MyHttpTool;
import cn.shanghq.seek.Utils.GsonUtils;

/**
 * Created by 17634 on 2018/1/6.
 */

public class JokesAsyncTask extends AsyncTask {
    private String url;
    private JokesRecyclerAdapter adapter;
    private ArrayList<String> lists;

    public JokesAsyncTask(String url, JokesRecyclerAdapter adapter, ArrayList<String> lists){
        this.url=url;
        this.adapter=adapter;
        this.lists=lists;;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        ArrayList<String> arrayList=(ArrayList<String>)o;
        for(int i=0;i<arrayList.size();i++){
//            lists.add(arrayList.get(i));
            if(arrayList.get(i).contains("</p><p>")){
                adapter.getArrayList().add(arrayList.get(i).replaceAll("</p><p>","" +
                        ""));
            }else {
                adapter.getArrayList().add(arrayList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String result= null;
        try {
            result = MyHttpTool.getUrlResponse(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result=result.substring(42,result.length()-2);
//        result=result.substring(43,result.length()-2);
        ArrayList<String> lists= null;
        try {
            lists = GsonUtils.parseJokesJson(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lists;
    }
}
