package com.example.tavanyab.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tavanyab.R;
import com.example.tavanyab.activity.PracticeActivity;
import com.example.tavanyab.db.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.CustomView> {

    private List<Result> resultList = new ArrayList<>();
    private Context context;
    private final OnItemClickListener listener;

    public ResultAdapter(Context context, List<Result> resultList, OnItemClickListener listener) {
        this.context = context;
        this.resultList = resultList;
        this.listener = listener;

    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item_list, parent, false);
        return new CustomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {

        final Result result = resultList.get(position);
        if (result != null) {

            holder.txt_letter.setText(result.getLetter_name());

            if (result.getFirst_res() != null && result.getFirst_res()) {
                holder.img_one.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.true_icon));
            } else {
                holder.img_one.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.false_icon));
            }

            if (result.getMiddle_res() != null && result.getMiddle_res()) {
                holder.img_two.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.true_icon));
            } else {
                holder.img_two.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.false_icon));
            }

            if (result.getLast_res() != null && result.getLast_res()) {
                holder.img_three.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.true_icon));
            } else {
                holder.img_three.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.false_icon));
            }

            if (result.getIrritability() != null && result.getIrritability()) {
                holder.img_irrible.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.true_icon));
            } else {
                holder.img_irrible.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.false_icon));
            }

            if (result.getNote() != null) {
                holder.txt_note.setText(result.getNote());
            }


            holder.bind(resultList.get(position), listener);

            holder.img_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PracticeActivity.class);
                    intent.putExtra("result_id", result.getId());
                    intent.putExtra("child_id", result.getChild_id());
                    intent.putExtra("letter_name", result.getLetter_name());
                    intent.putExtra("place", 1);
                    context.startActivity(intent);
                }
            });

            holder.img_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PracticeActivity.class);
                    intent.putExtra("result_id", result.getId());
                    intent.putExtra("child_id", result.getChild_id());
                    intent.putExtra("letter_name", result.getLetter_name());
                    intent.putExtra("place", 2);
                    context.startActivity(intent);
                }
            });

            holder.img_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PracticeActivity.class);
                    intent.putExtra("result_id", result.getId());
                    intent.putExtra("child_id", result.getChild_id());
                    intent.putExtra("letter_name", result.getLetter_name());
                    intent.putExtra("place", 3);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class CustomView extends RecyclerView.ViewHolder {
        private AppCompatImageView img_one, img_two, img_three, img_irrible;
        private AppCompatTextView txt_letter, txt_note;

        public CustomView(@NonNull View itemView) {
            super(itemView);
            img_one = itemView.findViewById(R.id.img_one);
            img_two = itemView.findViewById(R.id.img_two);
            img_three = itemView.findViewById(R.id.img_three);
            img_irrible = itemView.findViewById(R.id.img_irrible);
            txt_letter = itemView.findViewById(R.id.txt_letter);
            txt_note = itemView.findViewById(R.id.txt_note);
        }

        public void bind(final Result item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Result item);
    }

}
