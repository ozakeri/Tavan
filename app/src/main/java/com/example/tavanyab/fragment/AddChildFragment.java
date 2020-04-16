package com.example.tavanyab.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tavanyab.R;
import com.example.tavanyab.activity.StartEvaluatingActivity;
import com.example.tavanyab.db.Child;
import com.example.tavanyab.db.manager.DBManager;
import com.example.tavanyab.db.manager.Services;
import com.example.tavanyab.utiles.DateConvert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddChildFragment extends Fragment {
    private FragmentTransaction ft;
    private AppCompatTextView txt_date;
    private AppCompatEditText edt_name, edt_family;
    private CardView btn_save;
    private Spinner spinner_year, spinner_month, spinner_day;
    private List<String> yearList = new ArrayList<>();
    private List<String> dayList = new ArrayList<>();
    private String[] month = {"فروردين", "ارديبهشت", "خرداد", "تير", "مرداد", "شهريور", "مهر", "آبان", "آذر", "دي", "بهمن", "اسفند"};
    private String[] day = {"شنبه", "يکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنج شنبه", "جمعه"};
    private String yearStr, monthStr, dayStr;
    private Services services;

    public AddChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_child, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        services = new Services(new DBManager(getActivity()));
        txt_date = view.findViewById(R.id.txt_date);
        edt_name = view.findViewById(R.id.edt_name);
        edt_family = view.findViewById(R.id.edt_family);
        btn_save = view.findViewById(R.id.btn_save);
        spinner_year = view.findViewById(R.id.spinner_year);
        spinner_month = view.findViewById(R.id.spinner_month);
        spinner_day = view.findViewById(R.id.spinner_day);
        ft = getActivity().getSupportFragmentManager().beginTransaction();

        Date date = new Date();
        txt_date.setText(DateConvert.day_of_week(date.getYear(), date.getMonth(), date.getDay()) + " " + DateConvert.getDay() + " " + DateConvert.getMonth() + " " + DateConvert.getYear());

        int thisYear = Integer.parseInt(DateConvert.getYear());
        System.out.println("thisYear====" + thisYear);
        for (int i = thisYear; i >= 1370; i--) {
            yearList.add(Integer.toString(i));
        }

        for (int i = 1; i <= 30; i++) {
            if (i < 10) {
                dayList.add("0" + i);
            } else {
                dayList.add(Integer.toString(i));
            }

        }

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter<String> a1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, yearList);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(a1);
        spinner_year.setPrompt(getResources().getString(R.string.yera));

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yearStr = yearList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter<String> a2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, month);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(a2);
        spinner_month.setPrompt(getResources().getString(R.string.month));
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthStr = String.valueOf(i + 1);
                if (i < 10) {
                    monthStr = "0" + monthStr;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter<String> a3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, dayList);
        a3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(a3);
        spinner_day.setPrompt(getResources().getString(R.string.day));
        spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dayStr = String.valueOf(i + 1);
                if (i < 10) {
                    dayStr = "0" + dayStr;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    /*    edt_birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Calendar minDate = Calendar.getInstance();
                Calendar maxDate = Calendar.getInstance();
                maxDate.set(Calendar.YEAR, maxDate.get(Calendar.YEAR) + 1);
                minDate.set(Calendar.YEAR, maxDate.get(Calendar.YEAR) - 30);
                new DatePicker.Builder()
                        .id(1)
                        .minDate(minDate)
                        .maxDate(maxDate)
                        .build(new DateSetListener() {
                            @Override
                            public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {
                                if (calendar == null)
                                    return;

                                calFrom = calendar;
                                JalaliCalendarUtil jalaliCalendarUtil = new JalaliCalendarUtil(calendar);
                                System.out.println("jalaliCalendarUtil====" + jalaliCalendarUtil.getIranianWeekDayStr() + " " + jalaliCalendarUtil.getIranianDate3());
                            }
                        })
                        .show(fm, "");
            }
        });*/

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edt_name.getText().toString();
                String family = edt_family.getText().toString();
                String dateCreation = txt_date.getText().toString();
                String birthDate = yearStr + "/" + monthStr + "/" + dayStr;

                Child child = new Child();
                child.setFirst_name(name);
                child.setLast_name(family);
                child.setBirth_date(birthDate);
                child.setDate_creation(dateCreation);
                services.insertChild(child);

                Intent intent = new Intent(getActivity(), StartEvaluatingActivity.class);
                System.out.println("getId=====" + child.getId());
                intent.putExtra("childId", child.getId());
                startActivity(intent);
            }
        });
        return view;
    }

}
