package com.example.tavanyab.fragment;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tavanyab.MainActivity;
import com.example.tavanyab.R;
import com.example.tavanyab.adapter.ChildListAdapter;
import com.example.tavanyab.application.Application;
import com.example.tavanyab.db.Child;
import com.example.tavanyab.db.manager.DBManager;
import com.example.tavanyab.db.manager.Services;
import com.example.tavanyab.model.EventBusModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssessmentFragment extends Fragment {
    private CardView cardView_new;
    private FragmentTransaction ft;
    private RecyclerView recyclerView;
    private List<Child> childList = new ArrayList<>();
    private Services services;
    //private Keyboard mKeyboard;
    //private KeyboardView mKeyboardView;


    public AssessmentFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assessment, container, false);
        services = new Services(new DBManager(getActivity()));
        recyclerView = view.findViewById(R.id.recyclerView);
        ft = getActivity().getSupportFragmentManager().beginTransaction();
        AppCompatImageView img_filter = ((MainActivity) getActivity()).findViewById(R.id.img_filter);
        img_filter.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        childList = services.getChildList();
        System.out.println("childList====" + childList.size());
        if (childList != null){
            recyclerView.setAdapter(new ChildListAdapter(childList));
        }

        //InAppKeyboard keyboard = (InAppKeyboard) view.findViewById(R.id.keyboard);
        //EditText editText = (EditText) view.findViewById(R.id.edittext);

        //editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        //editText.setTextIsSelectable(true);

        //InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
        //keyboard.setInputConnection(ic);

        cardView_new = view.findViewById(R.id.cardView_new);
        cardView_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application.getInstance().gotoFragment(new AddChildFragment(), getActivity(), "AddChildFragment");
            }
        });
        return view;
    }
}
