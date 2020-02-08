package com.example.fbadspractice;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<String> str;
    Activity activity;

    public Adapter(ArrayList<String> str, Activity activity){
        this.str=str;
        this.activity=activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 1){
            Toast.makeText(activity, "Ad wil show here", Toast.LENGTH_LONG).show();
            return new ViewHolder(view);
        }else {
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(str.get(position));

    }

    @Override
    public int getItemCount() {
        return str.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.textView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 5 == 0)
            return 1;
        return 2;
    }
}
