package cn.shanghq.seek.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.shanghq.seek.DataSet.NewsData;
import cn.shanghq.seek.Interface.OnItemClickListener;
import cn.shanghq.seek.R;

/**
 * Created by 17634 on 2018/2/5.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<NewsData> dataArrayList;
    private OnItemClickListener listener;


    public NewsRecyclerAdapter(Context context,ArrayList<NewsData> arrayList,OnItemClickListener listener){
        this.context=context;
        this.dataArrayList=arrayList;
        this.listener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.news_recyclerview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final NewsViewHolder viewHolder= (NewsViewHolder) holder;
        if(listener!=null){
            viewHolder.news_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=holder.getLayoutPosition();
                    listener.onItemClick(viewHolder.news_pic,pos);
                }
            });

            viewHolder.news_layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    listener.onItemLongClick(viewHolder.news_layout, pos);
                    return false;
                }
            });
        }
//        viewHolder.news_category.setText(dataArrayList.get(position).getCategory());
        viewHolder.news_src.setText(dataArrayList.get(position).getSrc());
        viewHolder.news_time.setText(dataArrayList.get(position).getTime());
        viewHolder.news_title.setText(dataArrayList.get(position).getTitle());
        if(!dataArrayList.get(position).getPic().equals("")) {
            Picasso.with(context).load(dataArrayList.get(position).getPic()).
                    resize(300,200).
                    centerCrop().
                    into(viewHolder.news_pic);
        }else {
            viewHolder.news_pic.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView news_title;
        TextView news_time;
        TextView news_src;
        LinearLayout news_layout;
        ImageView news_pic;

        public NewsViewHolder(View itemView) {
            super(itemView);
            news_layout=itemView.findViewById(R.id.news_layout);
            news_time=itemView.findViewById(R.id.news_time);
            news_src=itemView.findViewById(R.id.news_src);
            news_pic=itemView.findViewById(R.id.news_pic);
            news_title=itemView.findViewById(R.id.news_title);
        }
    }

    public ArrayList<NewsData> getDataArrayList() {
        return dataArrayList;
    }
}
