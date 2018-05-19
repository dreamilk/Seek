package cn.shanghq.seek.AsyncTask;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.shanghq.seek.Adapter.ImgsRecyclerAdapter;
import cn.shanghq.seek.Utils.MyHttpTool;
import cn.shanghq.seek.Utils.GsonUtils;

/**
 * Created by 17634 on 2018/1/6.
 */

public class ImgsAsyncTask extends AsyncTask {
    private String url;
    private ImgsRecyclerAdapter adapter;
    private List<ArrayList<String>> arrayLists;

    public ImgsAsyncTask(String url, ImgsRecyclerAdapter adapter, List<ArrayList<String>> arrayLists){
        this.url=url;
        this.adapter=adapter;
        this.arrayLists=arrayLists;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        List<ArrayList<String>> array= (List<ArrayList<String>>) o;
        for(int i=0;i<array.size();i++){
            adapter.getImageViewList().add(array.get(i).get(0));
            ArrayList<String> l=new ArrayList<>();
            for(int j=0;j<array.get(i).size();j++){
                l.add(array.get(i).get(j));
            }
            arrayLists.add(l);
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
        result=result.substring(43,result.length()-2);
        List<ArrayList<String>> lists= null;
        try {
            lists = GsonUtils.parseImgsJson(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lists;
    }
}
