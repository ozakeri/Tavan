package com.example.tavanyab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tavanyab.R;
import com.example.tavanyab.model.Child;

import java.util.ArrayList;
import java.util.List;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.CustomView> {

    private List<Child> childList = new ArrayList<>();

    public ChildListAdapter(List<Child> childList) {
        this.childList = childList;
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
            holder.txt_nameFamily.setText(child.getFirstName() + " " + child.getLastName());
            holder.txt_dateCreation.setText(child.getDateCreation());
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
    }
}
