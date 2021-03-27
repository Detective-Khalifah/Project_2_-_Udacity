package com.blogspot.thengnet.project2_udacity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] checkBoxId = {R.id.cb0_a, R.id.cb0_b, R.id.cb0_c, R.id.cb0_d, R.id.cb1_a,
            R.id.cb1_b, R.id.cb1_c};
    private int trav = 0;
    ArrayList<Byte> ops;

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

        // Pick {@link CheckBox} from array and assign ID from array of #checkBoxIds
        for (int i = 0; i < checkboxes.length; i++) {
            checkboxes[i] = (CheckBox) findViewById(checkBoxId[i]);
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

        ops = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            checkboxes[i].setText(q1Options[randomOptionPicker(4)]);
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
            if (checkboxes[cb0].isChecked())
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
        for (int cb1 = 4; cb1 < checkboxes.length; cb1++) {
            if (checkboxes[cb1].isChecked())
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