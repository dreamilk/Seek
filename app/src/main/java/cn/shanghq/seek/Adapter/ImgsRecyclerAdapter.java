package cn.shanghq.seek.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import cn.shanghq.seek.Interface.OnItemClickListener;
import cn.shanghq.seek.R;

/**
 * Created by 17634 on 2018/1/2.
 */

public class ImgsRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> imageViewList;
    private int deviceWidth;
    private Transformation transformation;
    private OnItemClickListener listener;

    public ImgsRecyclerAdapter(Context context, List<String> imageViews, OnItemClickListener listener) {
        this.context = context;
        this.imageViewList = imageViews;
        deviceWidth = ((Activity) this.context).getWindowManager().getDefaultDisplay().getWidth();
        this.listener = listener;


        transformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {


                int targetWidth = deviceWidth / 2;
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

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.girls_recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        if (listener != null) {
            ((MyViewHolder) holder).imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    listener.onItemClick(((MyViewHolder) holder).imgView, pos);
                }
            });

            ((MyViewHolder) holder).imgView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    listener.onItemLongClick(((MyViewHolder) holder).imgView, pos);
                    return false;
                }
            });
        }
        Picasso.with(context).load(imageViewList.get(position))
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .transform(transformation)
                .into(myViewHolder.imgView);
    }

    @Override
    public int getItemCount() {
        return imageViewList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_girls_recycleView);
        }
    }

    public List<String> getImageViewList() {
        return imageViewList;
    }
}
