package com.example.tavanyab.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tavanyab.R;
import com.example.tavanyab.db.Assessment;
import com.example.tavanyab.db.manager.DBManager;
import com.example.tavanyab.db.manager.Services;

import java.util.ArrayList;
import java.util.List;

public class PracticeActivity extends AppCompatActivity {

    private RecyclerView recycler_view;
    private AppCompatButton btn_prev, btn_next, btn_success, btn_needPractice;
    private AppCompatImageView img_cake, img_mouse, img_balloon, imageView, img_hand;
    private AppCompatTextView txt_placeLoad;
    private NumberPicker picker;
    private List<Assessment> assessmentList = new ArrayList<>();
    private Services services;
    private Long result_id;
    private int child_id;
    private int place;
    private String letter_name;
    private AppCompatSeekBar seekBar;
    private int progress = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        services = new Services(new DBManager(this));

        recycler_view = findViewById(R.id.recycler_view);
        seekBar = findViewById(R.id.seekBar);
        btn_prev = findViewById(R.id.btn_prev);
        btn_next = findViewById(R.id.btn_next);
        btn_success = findViewById(R.id.btn_success);
        btn_needPractice = findViewById(R.id.btn_needPractice);
        img_cake = findViewById(R.id.img_cake);
        img_mouse = findViewById(R.id.img_mouse);
        img_balloon = findViewById(R.id.img_balloon);
        imageView = findViewById(R.id.imageView);
        img_hand = findViewById(R.id.img_hand);
        txt_placeLoad = findViewById(R.id.txt_placeLoad);
        picker = findViewById(R.id.picker);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            result_id = bundle.getLong("result_id");
            child_id = bundle.getInt("child_id");
            letter_name = bundle.getString("letter_name");
            place = bundle.getInt("place");

            System.out.println("result_id====" + result_id);
            System.out.println("result_id====" + child_id);
            System.out.println("result_id====" + letter_name);
            System.out.println("result_id====" + place);

            seekBar.setProgress(progress);
            img_hand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (progress != 0) {
                        progress -= 10;
                        seekBar.setProgress(progress);
                    }
                }
            });
        }


        // assessmentList = services.getAssessmentListById()
    }
}
