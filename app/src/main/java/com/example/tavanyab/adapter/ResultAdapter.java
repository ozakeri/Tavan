package com.example.tavanyab.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tavanyab.R;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.CustomView> {

    // private List<String> stringList = new ArrayList<>();
    // private List<Integer> dataFirst = new ArrayList<>();
    // private List<Integer> dataMiddle = new ArrayList<>();
    // private List<Integer> dataLast = new ArrayList<>();

    private String[] data;
    private int[] dataFirst;
    private int[] dataMiddle;
    private int[] dataLast;

    public ResultAdapter(String[] stringList, int[] dataFirst, int[] dataMiddle, int[] dataLast) {
        this.data = stringList;
        this.dataFirst = dataFirst;
        this.dataMiddle = dataMiddle;
        this.dataLast = dataLast;
    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item_list, parent, false);
        return new CustomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {

        System.out.println("position====" + position);
        if (holder.itemView.getLayoutParams() instanceof GridLayoutManager.LayoutParams) {
            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            System.out.println("In column " + lp.getSpanIndex() + ", size " + lp.getSpanSize());
        }

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class CustomView extends RecyclerView.ViewHolder {
        private AppCompatImageView imageView;

        public CustomView(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_status);
        }
    }

}
