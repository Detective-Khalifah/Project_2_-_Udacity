package com.blogspot.thengnet.project2_udacity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] checkBoxId = {R.id.cb0_a, R.id.cb0_b, R.id.cb0_c, R.id.cb0_d, R.id.cb1_a,
            R.id.cb1_b, R.id.cb1_c};
    private int[] radioButtonId = {R.id.rb0_a, R.id.rb0_b, R.id.rb0_c, R.id.rb0_d, R.id.rb1_a,
            R.id.rb1_b, R.id.rb1_c};
    ArrayList<Byte> ops;

    private CheckBox cb0A, cb0B, cb0C, cb0D, // CheckBoxes for #Question 1
    cb1A, cb1B, cb1C; // CheckBoxes for #Question 4
    private CheckBox[] checkBoxOptions = {cb0A, cb0B, cb0C, cb0D, cb1A, cb1B, cb1C};
    private RadioButton rb0A, rb0B, rb0C, rb0D, // RadioButtons for #Question 2
    rb1A, rb1B, rb1C; // RadioButtons for #Question 5
    private RadioButton[] radioButtonOptions = {rb0A, rb0B, rb0C, rb0D, rb1A, rb1B, rb1C};
    private RadioGroup radGrp0, radGrp1; // RadioGroup references for #Question2 & #Question5

    private Button btnSubmit;
    private EditText editCurrentLang;
    private TextView tvWarn1, tvWarn2, tvWarn3, tvWarn4, tvWarn5; // Warning {@link TextView}s

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubmit = (Button) findViewById(R.id.btn_submit);

        editCurrentLang = (EditText) findViewById(R.id.edit_lang);

        // Pick {@link CheckBox}es & {@link RadioButton}s from arrays #checkBoxOptions,
        // #radioButtonOptions and assign ID from array of #checkBoxIds & #radioButtonIds
        for (int i = 0; i < checkBoxOptions.length; i++) {
            checkBoxOptions[i] = (CheckBox) findViewById(checkBoxId[i]);
            radioButtonOptions[i] = (RadioButton) findViewById(radioButtonId[i]);
        }

        radGrp0 = (RadioGroup) findViewById(R.id.rad_grp_lang);
        radGrp1 = (RadioGroup) findViewById(R.id.rad_grp_ide);

        tvWarn1 = (TextView) findViewById(R.id.q1_warning);
        tvWarn2 = (TextView) findViewById(R.id.q2_warning);
        tvWarn3 = (TextView) findViewById(R.id.q3_warning);
        tvWarn4 = (TextView) findViewById(R.id.q4_warning);
        tvWarn5 = (TextView) findViewById(R.id.q5_warning);

        setupOptions();
    }

    private void setupOptions() {
        String[] q1Options = new String[]{getString(R.string.q1_op0), getString(R.string.q1_op1),
                getString(R.string.q1_op2), getString(R.string.q1_op3)};
        String[] q2Options = {getString(R.string.q2_op0), getString(R.string.q2_op1),
                getString(R.string.q2_op2), getString(R.string.q2_op3)};

        ops = new ArrayList<>();

        for (int a = 0; a < 4; a++) {
            byte pick = randomOptionPicker(4);
            checkBoxOptions[a].setText(q1Options[pick]);
            radioButtonOptions[a].setText(q2Options[pick]);
        }
    }

    private byte randomOptionPicker(int size) {
        Random ra = new Random();
        byte shuffle = (byte) ra.nextInt(size);

        for (int decker = 0; decker < size; decker++) {
            if (!ops.contains(shuffle)) {
                ops.add(decker, shuffle);
                break;
            } else {
                return randomOptionPicker(size);
            }
        }
        return shuffle;
    }

    private boolean allQuestionsAnswered () {
        boolean questionUnanswered = false;

        // TODO: Make any of the checks return false - as method return value - immediately a Q is
        //  found unanswered.

        // Check if any {@link CheckBox} in Question 1 has been checked; break out of the check, and
        // check other Questions' options, otherwise make #tvWarn1 visible and set
        // #questionUnanswered to true.
        for (int cb0 = 0; cb0 < 4; cb0++) {
            if (checkBoxOptions[cb0].isChecked())
                break;
            else {
                tvWarn1.setVisibility(View.VISIBLE);
                questionUnanswered = true;
            }
        }

        // Check if any {@link RadioButton} in Question 2's {@link RadioGroup} has been checked;
        // check other Questions' options if that is the case, otherwise set #questionUnanswered to
        // true & return it's opposite value - false - immediately, and make #tvWarn2 visible.
        if (radGrp0.getCheckedRadioButtonId() == -1) {
            tvWarn2.setVisibility(View.VISIBLE);
            questionUnanswered = true;
        }

        // Check if the {@link EditText} in Question 3 is empty; make #tvWarn1 visible & set
        // #questionAnswered to true, if that is the case, otherwise check other Questions' options.
        if (editCurrentLang.getText().toString().equals("")) {
            editCurrentLang.setHint(getText(R.string.edit_empty));
            editCurrentLang.setHintTextColor(Color.RED);
            tvWarn3.setVisibility(View.VISIBLE);
            questionUnanswered = true;
        }

        // Check if any {@link CheckBox} in Question 4 has been checked; break out of the check, and
        // check other Questions' options, otherwise make #tvWarn4 visible and set
        // #questionUnanswered to true.
        for (int cb1 = 4; cb1 < checkBoxOptions.length; cb1++) {
            if (checkBoxOptions[cb1].isChecked())
                break;
            else {
                questionUnanswered = true;
                tvWarn4.setVisibility(View.VISIBLE);
            }
        }

        // Check if any {@link RadioButton} in Question 5's {@link RadioGroup} has been checked;
        // check other Questions' options if that is the case, otherwise set #questionUnanswered to
        // true & return it's opposite value - false - immediately, and make #tvWarn5 visible.
        if (radGrp1.getCheckedRadioButtonId() == -1) {
            tvWarn5.setVisibility(View.VISIBLE);
            questionUnanswered = true;
        }

        return !questionUnanswered;
    }
}