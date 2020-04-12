package com.example.tavanyab.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tavanyab.R;
import com.example.tavanyab.adapter.ResultAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultEvaluatingFragment extends Fragment {

    private String[] data = {"A", "B", "C", "D", "E", "F", "J", "K", "L", "M", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int[] dataFirst = {1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1};
    private int[] dataMiddle = {1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1};
    private int[] dataLast = {0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0};
    private RecyclerView recycler_view;

    public ResultEvaluatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_evaluating, container, false);
        recycler_view = view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 4);
        recycler_view.setLayoutManager(layoutManager);

        recycler_view.setAdapter(new ResultAdapter(data,dataFirst,dataMiddle,dataLast));

        return view;
    }
}
