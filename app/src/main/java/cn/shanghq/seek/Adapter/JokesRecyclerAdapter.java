package cn.shanghq.seek.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.shanghq.seek.R;

/**
 * Created by 17634 on 2018/1/11.
 */

public class JokesRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<String> arrayList;

    public JokesRecyclerAdapter(Context context, ArrayList<String> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JokeViewHolder(LayoutInflater.from(context).inflate(R.layout.jokes_recyclerview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JokeViewHolder viewHolder= (JokeViewHolder) holder;
        viewHolder.textView.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    class JokeViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public JokeViewHolder(View view){
            super(view);
            textView=view.findViewById(R.id.tv_jokes_recycleView);

        }
    }
}
