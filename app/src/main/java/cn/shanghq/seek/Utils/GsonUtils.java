package cn.shanghq.seek.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.shanghq.seek.DataSet.NewsData;

/**
 * Created by 17634 on 2018/1/6.
 */

public class GsonUtils {

    public static List<ArrayList<String>> parseImgsJson(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray article = data.getJSONArray("article");
        List<JSONObject> articles = new ArrayList<JSONObject>(article.length());
        List<ArrayList<String>> lists=new ArrayList<>();
        for (int i = 0; i < article.length(); i++) {
            articles.add(article.getJSONObject(i));
            JSONArray image = articles.get(i).getJSONArray("image");
            List<JSONObject> images = new ArrayList<>(image.length());
            String title = articles.get(i).get("title").toString();
            ArrayList<String> urls=new ArrayList<>();
            for (int j = 0; j < image.length(); j++) {
                images.add((JSONObject) image.get(j));
                String url = images.get(j).get("url").toString();
                urls.add(url);
            }
            lists.add(urls);
        }
        return lists;
    }

    public static ArrayList<String> parseJokesJson(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray article = data.getJSONArray("article");
        List<JSONObject> articles = new ArrayList<JSONObject>(article.length());
        ArrayList<String> lists=new ArrayList<>();
        for (int i = 0; i < article.length(); i++) {
            articles.add(article.getJSONObject(i));
            String s=articles.get(i).get("content").toString();
            lists.add(s.substring(3,s.length()-4));
        }
        return lists;
    }

    public static ArrayList<NewsData> parseNewsJson(String jsonData) throws JSONException{
        ArrayList<NewsData> arrayList=new ArrayList<>();
        JSONObject jsonObject=new JSONObject(jsonData);
        JSONObject result1=jsonObject.getJSONObject("result");
        JSONObject result2=result1.getJSONObject("result");
        JSONArray list=result2.getJSONArray("list");
        for(int i=0;i<list.length();i++){
            JSONObject object=list.getJSONObject(i);
            NewsData data=new NewsData(object.getString("title"),
                    object.getString("time"),
                    object.getString("src"),
                    object.getString("category"),
                    object.getString("pic"),
                    object.getString("content"),
                    object.getString("url")
                    );
            arrayList.add(data);
        }
        return arrayList;
    }
}
