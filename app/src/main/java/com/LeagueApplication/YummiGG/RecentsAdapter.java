package com.LeagueApplication.YummiGG;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.ViewHolder> {
    List<String> recents;
    OnClickListener clickListener;
    Context context;

    public interface OnClickListener {
        void onItemClicked(int position);
    }

    public RecentsAdapter(List<String> recents, OnClickListener clickListener) {
        this.recents = recents;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(recents.get(position));
    }

    @Override
    public int getItemCount() {
        return recents.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecentItem;
        Context context;

        public ViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            tvRecentItem = view.findViewById(android.R.id.text1);
        }
        public void bind(String in) {
            tvRecentItem.setText(in);
            tvRecentItem.setOnClickListener(v -> clickListener.onItemClicked(getAdapterPosition()));
        }
    }


}