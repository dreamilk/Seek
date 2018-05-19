package cn.shanghq.seek;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;

import cn.shanghq.seek.Adapter.ImgPageAdapter;

public class NewsActivity extends AppCompatActivity {

    private WebView webView;
    private String title;
    private String content;
    private String time;
    private String url;
    private String pic;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initData();
        initView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,url);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享到"));
            }
        });
    }

    private void initData() {
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        time=getIntent().getStringExtra("time");
        pic=getIntent().getStringExtra("pic");
        url=getIntent().getStringExtra("url");
    }

    private void initView() {
        imageView=findViewById(R.id.news_img);
        webView=findViewById(R.id.news_webview);
        setTitle(title);
        webView.loadDataWithBaseURL(null,content.toString(),"text/html","utf-8",null);

//        StatusBarUtil.setTranslucentForCoordinatorLayout(this,50);
        if(!pic.equals("")) {
            Picasso.with(getApplicationContext()).load(pic).into(imageView);
        }else {

        }
    }
}
