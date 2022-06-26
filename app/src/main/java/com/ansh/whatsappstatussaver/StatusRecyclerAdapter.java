package com.ansh.whatsappstatussaver;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StatusRecyclerAdapter extends RecyclerView.Adapter<StatusRecyclerAdapter.StatusViewHolder> {

    public StatusRecyclerAdapter(Context context, ArrayList<WhatsappStatusModel> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    ArrayList<WhatsappStatusModel> list = new ArrayList<>();


    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.status_item_layout, parent, false);

        StatusViewHolder viewHolder = new StatusViewHolder(v);
        Log.d("size", String.valueOf(list.size()));

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {

        WhatsappStatusModel model = list.get(position);
        if (model.getName().endsWith(".jpg")) {
            Glide.with(context).load(model.getUri()).into(holder.imageView);

        }

    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.statusImage);
        }
    }
}
