package cn.shanghq.seek.Utils;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 17634 on 2018/1/6.
 */

public class MyHttpTool {

    public static String getUrlResponse(String url) throws IOException {
        Request request = new Request.Builder().get().url(url).build();
        Response response = new OkHttpClient().newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }
        return null;
    }

}
