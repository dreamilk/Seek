package cn.shanghq.seek.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import cn.shanghq.seek.AsyncTask.VideoAsyncTask;
import cn.shanghq.seek.R;

/**
 * Created by 17634 on 2018/2/13.
 */

public class VideoRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<String[]> arrayList;
    private int deviceWidth;
    private Transformation transformation;


    public VideoRecyclerAdapter(Context context,ArrayList<String[]> arrayList){
        this.context=context;
        this.arrayList=arrayList;
        deviceWidth = ((Activity) this.context).getWindowManager().getDefaultDisplay().getWidth();

        transformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {


                int targetWidth = deviceWidth-48;
                if (source.getWidth() == 0) {
                    return source;
                }

                //如果图片小于设置的宽度，则返回原图
                if (source.getWidth() == targetWidth) {
                    return source;
                } else {
                    //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                    double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                    int targetHeight = (int) (targetWidth * aspectRatio);
                    if (targetHeight != 0 && targetWidth != 0) {
                        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                        if (result != source) {
                            // Same bitmap is returned if sizes are the same
                            source.recycle();
                        }
                        return result;
                    } else {
                        return source;
                    }
                }
            }

            @Override
            public String key() {
                return "";
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.video_recyclerview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoViewHolder videoViewHolder= (VideoViewHolder) holder;
        videoViewHolder.videoTitle.setText(arrayList.get(position)[0]);
        //加载图片
        if(!arrayList.get(position)[1].equals("")){
            Picasso.with(context).load("http://"+arrayList.get(position)[1])
                    .placeholder(R.drawable.loading).transform(transformation)
                    .into(videoViewHolder.videoImg);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        TextView videoTitle;
        ImageView videoImg;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoTitle=itemView.findViewById(R.id.video_item_title);
            videoImg=itemView.findViewById(R.id.video_item_img);
        }
    }
}
