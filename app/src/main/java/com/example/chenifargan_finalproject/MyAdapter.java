package com.example.chenifargan_finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
//extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    private Context context;
    //private ArrayList<String> songs;
    private ArrayList <person> songs;
    public MyAdapter(Context context, ArrayList<person> songs) {
        this.context = context;
        this.songs = songs;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        MyViewHolder mySongViewHolder = new MyViewHolder(view);
        return mySongViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(songs.get(position).getNameOfPerson());
        holder.tv2.setText(songs.get(position).getNameOfContext());


    }

    @Override
    public int getItemCount() {
           return songs == null ? 0 : songs.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private TextView tv2;
        private ImageView im;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.my_tv);
            im= itemView.findViewById(R.id.imageView2);
            tv2 = itemView.findViewById(R.id.my_tv2);

        }

    }

}
