package com.example.tavanyab.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.tavanyab.MainActivity;
import com.example.tavanyab.R;
import com.example.tavanyab.application.Application;
import com.example.tavanyab.db.Result;
import com.example.tavanyab.db.manager.DBManager;
import com.example.tavanyab.db.manager.Services;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartEvaluatingActivity extends AppCompatActivity {

    private NumberPicker numberPicker;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4;
    private Services services;
    private List<String> lettersList = new ArrayList<>();
    private AppCompatButton btn_true, btn_false, btn_irritability, btn_keyboard, btn_prev, btn_next;
    private AppCompatTextView txt_placeLoad;
    long id;
    private Result result = null;
    private int changeValue;
    private String value;
    private int counter = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_start_evaluating);
        services = new Services(new DBManager(this));

        btn_true = findViewById(R.id.btn_true);
        btn_false = findViewById(R.id.btn_false);
        btn_irritability = findViewById(R.id.btn_irritability);
        btn_keyboard = findViewById(R.id.btn_keyboard);
        btn_prev = findViewById(R.id.btn_prev);
        btn_next = findViewById(R.id.btn_next);
        txt_placeLoad = findViewById(R.id.txt_placeLoad);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getLong("childId");
            System.out.println("id======" + id);

            List<String> Lines = Arrays.asList(getResources().getStringArray(R.array.letter_array));
            for (String s : Lines) {
                Result result = new Result();
                result.setLetter_name(s);
                result.setChild_id((int) id);
                services.insertResult(result);
            }
        }

        List<Result> results = new ArrayList<>();
        results = services.getResultListByChildId(id);
        for (Result res : results) {
            lettersList.add(res.getLetter_name());
        }

        String[] data = new String[lettersList.size()];
        data = lettersList.toArray(data);

        numberPicker = findViewById(R.id.picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setValue(1);
        changeValue = numberPicker.getValue();

        txt_placeLoad.setText(numberPicker.getValue() + "" + counter);
        System.out.println("========changeValue========" + changeValue);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                changeValue = newVal;
                System.out.println("oldVal===" + oldVal);
                value = lettersList.get(newVal - 1);
                System.out.println("value=====" + value);
                result = services.getResultByChildIdAndLetter(id, value);
                System.out.println("Letter_name===" + result.getLetter_name());
            }
        });

        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        btn_irritability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        btn_keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if (counter > 3) {
                    changeValue = numberPicker.getValue();
                    numberPicker.setValue(changeValue + 1);
                    counter = 1;
                }

                txt_placeLoad.setText(value + "" + counter);
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                if (counter < 1) {
                    changeValue = numberPicker.getValue();
                    numberPicker.setValue(changeValue - 1);
                    counter = 3;
                }
                txt_placeLoad.setText(value + "" + counter);
            }
        });

        materialDesignFAM = findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = findViewById(R.id.material_design_floating_action_menu_item4);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Application.getInstance().addToSharedPreferences("currentActivity", "currentPage", "StartEvaluatingActivity");
                finish();
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

            }
        });
    }
}
