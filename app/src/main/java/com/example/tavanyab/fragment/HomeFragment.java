package com.example.tavanyab.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tavanyab.MainActivity;
import com.example.tavanyab.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private CardView cardView_assessment, cardView_quickAccess;
    private AppCompatTextView txt_about, txt_contact;
    private FragmentTransaction ft;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ft = getActivity().getSupportFragmentManager().beginTransaction();
        cardView_assessment = view.findViewById(R.id.cardView_assessment);
        cardView_quickAccess = view.findViewById(R.id.cardView_quickAccess);
        txt_about = view.findViewById(R.id.txt_about);
        txt_contact = view.findViewById(R.id.txt_contact);

        AppCompatImageView img_filter = ((MainActivity)getActivity()).findViewById(R.id.img_filter);
        img_filter.setVisibility(View.GONE);

        cardView_assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.replace(R.id.container, new AssessmentFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        cardView_quickAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.replace(R.id.container, new QuickAccessFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        txt_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.replace(R.id.container, new AboutFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        txt_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.replace(R.id.container, new ContactUsFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }
}