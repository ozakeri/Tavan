package com.example.tavanyab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tavanyab.R;
import com.example.tavanyab.db.Child;

import java.util.ArrayList;
import java.util.List;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.CustomView> {

    private List<Child> childList = new ArrayList<>();
    private final OnItemClickListener listener;

    public ChildListAdapter(List<Child> childList, OnItemClickListener listener) {
        this.childList = childList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_list, parent, false);
        return new CustomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {
        Child child = childList.get(position);
        if (child != null) {
            holder.txt_nameFamily.setText(child.getFirst_name() + " " + child.getLast_name());
            holder.txt_dateCreation.setText(child.getDate_creation());
            holder.bind(childList.get(position), listener);
        }
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    public class CustomView extends RecyclerView.ViewHolder {
        AppCompatTextView txt_dateCreation, txt_nameFamily;

        public CustomView(@NonNull View itemView) {
            super(itemView);
            txt_dateCreation = itemView.findViewById(R.id.txt_dateCreation);
            txt_nameFamily = itemView.findViewById(R.id.txt_nameFamily);
        }

        public void bind(final Child item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Child item);
    }
}
