package com.blogspot.thengnet.project2_udacity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int[] checkBoxId = {R.id.cb0_a, R.id.cb0_b, R.id.cb0_c, R.id.cb0_d, R.id.cb1_a,
            R.id.cb1_b, R.id.cb1_c};
    private int trav = 0;

    private Button btnSubmit;
    private CheckBox cb0A, cb0B, cb0C, cb0D, // CheckBoxes for #Question 1
        cb1A, cb1B, cb1C; // CheckBoxes for #Question 4
    private CheckBox[] checkboxes = {cb0A, cb0B, cb0C, cb0D, cb1A, cb1B, cb1C};
    private EditText editCurrentLang;
    private RadioGroup radGrp0, radGrp1; // RadioGroup references for #Question2 & #Question5
    private TextView tvWarn1, tvWarn2, tvWarn3, tvWarn4, tvWarn5; // Warning {@link TextView}s

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubmit = (Button) findViewById(R.id.btn_submit);

        editCurrentLang = (EditText) findViewById(R.id.edit_lang);

        // Pick CheckBox from array and assign ID from list of CheckBox Ids
        for (CheckBox checkBox : checkboxes) {
            checkBox = (CheckBox) findViewById(checkBoxId[trav]);
            trav++;
        }

        radGrp0 = (RadioGroup) findViewById(R.id.rad_grp_lang);
        radGrp1 = (RadioGroup) findViewById(R.id.rad_grp_ide);

        tvWarn1 = (TextView) findViewById(R.id.q1_warning);
        tvWarn2 = (TextView) findViewById(R.id.q2_warning);
        tvWarn3 = (TextView) findViewById(R.id.q3_warning);
        tvWarn4 = (TextView) findViewById(R.id.q4_warning);
        tvWarn5 = (TextView) findViewById(R.id.q5_warning);
    }

    private boolean allQuestionsAnswered() {
        if (radGrp0.getCheckedRadioButtonId() == -1) {

            return false;
        }

        if (radGrp1.getCheckedRadioButtonId() == -1) {

            return false;
        }

        if (editCurrentLang.getText().toString().equals("")) {
            editCurrentLang.setHint(getText(R.string.edit_empty));
            editCurrentLang.setHintTextColor(Color.RED);
            return false;
        }

        for (int cb0 = 0; cb0 < 4; cb0++) {
            if (!checkboxes[cb0].isChecked())
                return false;
        }

        for (int cb1 = 4; cb1 < checkboxes.length; cb1++) {
            if (!checkboxes[cb1].isChecked())
                return false;
        }

        return true;
    }
}