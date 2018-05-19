package cn.shanghq.seek;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.WindowManager;
import android.widget.StackView;

import java.util.ArrayList;

import cn.shanghq.seek.Adapter.ImgPageAdapter;

public class ImgViewActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ArrayList<String> imgLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置为全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_girl_full_screen);


        initView();
    }

    private void initView() {
        viewPager=findViewById(R.id.ImgViewPager);
        imgLists=getIntent().getStringArrayListExtra("ImgList");
        viewPager.setAdapter(new ImgPageAdapter(imgLists,getApplicationContext()));
    }
}
