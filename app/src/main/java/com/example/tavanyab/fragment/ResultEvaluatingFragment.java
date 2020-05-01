package com.example.tavanyab.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tavanyab.R;
import com.example.tavanyab.adapter.ResultAdapter;
import com.example.tavanyab.db.Result;
import com.example.tavanyab.db.manager.DBManager;
import com.example.tavanyab.db.manager.Services;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultEvaluatingFragment extends Fragment {

    private RecyclerView recycler_view;
    private List<Result> resultList = new ArrayList<>();
    private Services services;

    public ResultEvaluatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_evaluating, container, false);
        recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        services = new Services(new DBManager(getActivity()));

        if (getArguments() != null) {
            long child_id = getArguments().getLong("child_id");
            resultList = services.getResultListByChildId(child_id);
            recycler_view.setAdapter(new ResultAdapter(getActivity(), resultList, new ResultAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Result item) {

                }
            }));
        }

        return view;
    }
}
