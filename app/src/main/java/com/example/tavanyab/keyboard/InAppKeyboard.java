package com.example.tavanyab.keyboard;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.tavanyab.R;

public class InAppKeyboard extends LinearLayout implements View.OnClickListener {

    // constructors
    public InAppKeyboard(Context context) {
        this(context, null, 0);
    }

    public InAppKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InAppKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    SparseArray<String> keyValues = new SparseArray<>();

    // Our communication link to the EditText
    InputConnection inputConnection;

    private void init(Context context, AttributeSet attrs) {

        // initialize buttons
        LayoutInflater.from(context).inflate(R.layout.in_app_keyboard, this, true);
        // in_app_keyboard keys (buttons)
        Button mButton1 = (Button) findViewById(R.id.button_1);
        Button mButton2 = (Button) findViewById(R.id.button_2);
        Button mButton3 = (Button) findViewById(R.id.button_3);
        Button mButton4 = (Button) findViewById(R.id.button_4);
        Button mButton5 = (Button) findViewById(R.id.button_5);
        Button mButton6 = (Button) findViewById(R.id.button_6);
        Button mButton7 = (Button) findViewById(R.id.button_7);
        Button mButton8 = (Button) findViewById(R.id.button_8);
        Button mButton9 = (Button) findViewById(R.id.button_9);
        Button mButton0 = (Button) findViewById(R.id.button_0);
        Button mButton11 = (Button) findViewById(R.id.button_11);
        Button mButton12 = (Button) findViewById(R.id.button_12);
        Button mButton13 = (Button) findViewById(R.id.button_13);
        Button mButton14 = (Button) findViewById(R.id.button_14);
        Button mButton15 = (Button) findViewById(R.id.button_15);
        Button mButton16 = (Button) findViewById(R.id.button_16);
        Button mButton17 = (Button) findViewById(R.id.button_17);
        Button mButton18 = (Button) findViewById(R.id.button_18);
        Button mButton19 = (Button) findViewById(R.id.button_19);
        Button mButton20 = (Button) findViewById(R.id.button_20);
        Button mButton21 = (Button) findViewById(R.id.button_21);
        Button mButton22 = (Button) findViewById(R.id.button_22);
        Button mButton23 = (Button) findViewById(R.id.button_23);

        Button mButtonDelete = (Button) findViewById(R.id.button_delete);
        Button mButtonEnter = (Button) findViewById(R.id.button_enter);

        // set button click listeners
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButton0.setOnClickListener(this);
        mButton11.setOnClickListener(this);
        mButton12.setOnClickListener(this);
        mButton13.setOnClickListener(this);
        mButton14.setOnClickListener(this);
        mButton15.setOnClickListener(this);
        mButton16.setOnClickListener(this);
        mButton17.setOnClickListener(this);
        mButton18.setOnClickListener(this);
        mButton19.setOnClickListener(this);
        mButton20.setOnClickListener(this);
        mButton21.setOnClickListener(this);
        mButton22.setOnClickListener(this);
        mButton23.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);
        mButtonEnter.setOnClickListener(this);

        // map buttons IDs to input strings
        keyValues.put(R.id.button_1, "p");
        keyValues.put(R.id.button_2, "b");
        keyValues.put(R.id.button_3, "m");
        keyValues.put(R.id.button_4, "f");
        keyValues.put(R.id.button_5, "v");
        keyValues.put(R.id.button_6, "t");
        keyValues.put(R.id.button_7, "d");
        keyValues.put(R.id.button_8, "n");
        keyValues.put(R.id.button_9, "r");
        keyValues.put(R.id.button_0, "l");
        keyValues.put(R.id.button_11, "s");
        keyValues.put(R.id.button_12, "z");
        keyValues.put(R.id.button_13, "j");
        keyValues.put(R.id.button_14, "y");
        keyValues.put(R.id.button_15, "k");
        keyValues.put(R.id.button_16, "g");
        keyValues.put(R.id.button_17, "q");
        keyValues.put(R.id.button_18, "x");
        keyValues.put(R.id.button_19, "h");
        keyValues.put(R.id.button_20, "a");
        keyValues.put(R.id.button_21, "ch");
        keyValues.put(R.id.button_22, "sh");
        keyValues.put(R.id.button_23, "zh");
        keyValues.put(R.id.button_enter, "\n");
    }

    @Override
    public void onClick(View v) {

        // do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return;

        // Delete text or input key value
        // All communication goes through the InputConnection
        if (v.getId() == R.id.button_delete) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                // no selection, so delete previous character
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                // delete the selection
                inputConnection.commitText("", 1);
            }
        } else {
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, 1);
        }
    }

    // The activity (or some parent or controller) must give us
    // a reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection ic) {
        this.inputConnection = ic;
    }
}
