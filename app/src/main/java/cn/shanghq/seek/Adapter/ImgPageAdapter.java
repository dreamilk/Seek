package cn.shanghq.seek.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 17634 on 2018/1/6.
 */

public class ImgPageAdapter extends PagerAdapter {
    private ArrayList<String> list;
    private Context context;
    private ArrayList<View> views;

    public ImgPageAdapter(ArrayList<String> list,Context context){
        this.list=list;
        this.context=context;
        views=new ArrayList<>();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ImageView imageView=new ImageView(context);
        Picasso.with(context).load(list.get(position)).into(imageView);

        //设置保存png
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Picasso.with(context).load(list.get(position)).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        if(saveBitmap(bitmap)){
                            Snackbar.make(imageView,"保存成功",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
                return false;
            }
        });

        views.add(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    public boolean saveBitmap(Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory().getPath(),"cn.shanghq.seek");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String imageName = new Date(System.currentTimeMillis()) + ".png";
        File file = new File(appDir, imageName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

}
