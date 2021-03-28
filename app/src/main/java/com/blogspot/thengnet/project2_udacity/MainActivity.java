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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] checkBoxId = {R.id.cb0_a, R.id.cb0_b, R.id.cb0_c, R.id.cb0_d, R.id.cb1_a,
            R.id.cb1_b, R.id.cb1_c};
    private int[] radioButtonId = {R.id.rb0_a, R.id.rb0_b, R.id.rb0_c, R.id.rb0_d, R.id.rb1_a,
            R.id.rb1_b, R.id.rb1_c};
    private int score;
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (allQuestionsAnswered()) {
                    calculateScore();
                    Toast.makeText(MainActivity.this, "Test completed! Your score: " +
                            score, Toast.LENGTH_SHORT).show();
                    score = 0; // reset score after this quiz session.
                }
            }
        });
    }

    /**
     * Sets up the options components - {@link CheckBox}es & {@link RadioButton}s -
     * at random positions.
     */
    private void setupOptions() {
        String[] q1Options = new String[]{getString(R.string.q1_op0), getString(R.string.q1_op1),
                getString(R.string.q1_op2), getString(R.string.q1_op3)};
        String[] q2Options = {getString(R.string.q2_op0), getString(R.string.q2_op1),
                getString(R.string.q2_op2), getString(R.string.q2_op3)};

        String[] q4Options = {getString(R.string.q4_op0), getString(R.string.q4_op1),
                getString(R.string.q4_op2)};
        String[] q5Options = {getString(R.string.q5_op0), getString(R.string.q5_op1),
                getString(R.string.q5_op2)};

        ops = new ArrayList<>();
        for (int a = 0; a < 4; a++) {
            byte pick = randomOptionPicker(4);
            checkBoxOptions[a].setText(q1Options[pick]);
            radioButtonOptions[a].setText(q2Options[pick]);
        }

        ops = new ArrayList<>();
        for (int b = 4; b < 7; b++) {
            byte pick = randomOptionPicker(3);
            checkBoxOptions[b].setText(q4Options[pick]);
            radioButtonOptions[b].setText(q5Options[pick]);
        }
    }

    /**
     * Generates a pseudo-random byte value in range 0-(@param size-1), adds and returns it if it
     * does not already exist in the #ops {@link ArrayList}.
     * @param size -- maximum value (exclusive) sought.
     * @return generated pseudo-random byte value.
     */
    private byte randomOptionPicker(int size) {
        Random ra = new Random();
        byte shuffle = (byte) ra.nextInt(size);

        if (!ops.contains(shuffle)) {
            ops.add(shuffle);
        } else {
            return randomOptionPicker(size);
        }
        return shuffle;
    }

    private void calculateScore () {
        // Check & decide score of Question 1
        byte q1TotalOptionsTicked = 0;
        CheckBox q1SingleOptionUnselected = null;
        for (int i = 0; i < 4; i++) {
            if (checkBoxOptions[i].isChecked())
                q1TotalOptionsTicked++;
            else
                q1SingleOptionUnselected = checkBoxOptions[i];
        }
        if (q1TotalOptionsTicked == 3 && q1SingleOptionUnselected != null)
            if (q1SingleOptionUnselected.getText().toString().equalsIgnoreCase(getString(R.string.q1_op1)))
                score++;

        // Check & decide score of Question 2
        int q2AnswerId = radGrp0.getCheckedRadioButtonId();
        for (int i = 0; i < 4; i++) {
            if (radioButtonId[i] == q2AnswerId) {
                String markup = radioButtonOptions[i].getText().toString();
                if (markup.equalsIgnoreCase(getString(R.string.q2_op0)))
                    score++;
                break;
            }
        }

        // Check and decide score of #editCurrentLang -- Question 3
        String programmingLanguage = editCurrentLang.getText().toString();
        if (programmingLanguage.equalsIgnoreCase("Java") ||
                programmingLanguage.equalsIgnoreCase("Java."))
            score ++;

        // Check & decide score of Question 4
        byte q4TotalOptionsUnticked = 0;
        CheckBox q4SingleOptionUnselected = null;
        for (int i = 4; i < 7; i++) {
            if (!checkBoxOptions[i].isChecked()) {
                q4TotalOptionsUnticked++;
                q4SingleOptionUnselected = checkBoxOptions[i];
            }
        }
        if (q4TotalOptionsUnticked == 1 && q4SingleOptionUnselected != null) {
            if (q4SingleOptionUnselected.getText().equals(getString(R.string.q4_op0)))
                score++;
        }

        // Check and decide score of Question 5
        int q5AnswerId = radGrp1.getCheckedRadioButtonId();
        for (int i = 4; i < 7; i++) {
            if (radioButtonId[i] == q5AnswerId) {
                String ide = radioButtonOptions[i].getText().toString();
                if (ide.equalsIgnoreCase(getString(R.string.q5_op0)))
                    score++;
                break;
            }
        }
    }

    /**
     * This method checks if all questions have been answered.
     * For the {@link CheckBox}es, they're checked one after the other, and if at least one is ticked,
     * in the case of Q1, check gets to next question -- rather, set of option components;
     * If the {@link RadioGroup} for question 2 & 5 has one of its buttons clicked, check gets to
     * the next set of components; check for question 3 is whether anything has been typed into the
     * {@link EditText}; for question 4, logic is inverse of question 1.
     * A {@link} TextView that display the warning - "Question has not been answered!" - is made
     * visible for any question which does not 'pass' the check above.
     * @return #true if # of questions answered == 5 -- true if all Questions have been answered,
     * false otherwise.
     */
    private boolean allQuestionsAnswered () {
        byte questionsAnswered = 0;

        // TODO: Make any of the checks return false - as method return value - immediately a Q is
        //  found unanswered, without compromising warning {@link TextView}s

        // Check if any {@link CheckBox} in Question 1 has been checked; break out of the check, and
        // check other Questions' options, otherwise make #tvWarn1 visible.
        for (int cb0 = 0; cb0 < 4; cb0++) {
            if (checkBoxOptions[cb0].isChecked()) {
                questionsAnswered++;
                tvWarn1.setVisibility(View.GONE);
                break;
            } else {
                tvWarn1.setVisibility(View.VISIBLE);
            }
        }

        // Check if any {@link RadioButton} in Question 2's {@link RadioGroup} has been checked;
        // add to #questionsAnswered and proceed to check other Questions' options if that is the
        // case, otherwise and make #tvWarn2 visible.
        if (radGrp0.getCheckedRadioButtonId() == -1) {
            tvWarn2.setVisibility(View.VISIBLE);
        } else {
            questionsAnswered++;
            tvWarn2.setVisibility(View.GONE);
        }

        // Check if the {@link EditText} in Question 3 is empty; make #tvWarn1 visible & add to
        // #questionsAnswered, if that is the case, otherwise proceed to Questions 4.
        if (editCurrentLang.getText().toString().equals("")) {
            editCurrentLang.setHint(getText(R.string.edit_empty));
            editCurrentLang.setHintTextColor(Color.RED);
            tvWarn3.setVisibility(View.VISIBLE);
        } else {
            questionsAnswered++;
            tvWarn3.setVisibility(View.GONE);
        }

        // Check if any {@link CheckBox} in Question 4 has been checked; add to #quesionsAnswered &
        // break out of the check, proceeding to Question 5, otherwise make #tvWarn4 visible.
        for (int cb1 = 4; cb1 < checkBoxOptions.length; cb1++) {
            if (checkBoxOptions[cb1].isChecked()) {
                questionsAnswered++;
                tvWarn4.setVisibility(View.GONE);
                break;
            }
            else {
                tvWarn4.setVisibility(View.VISIBLE);
            }
        }

        // Check if any {@link RadioButton} in Question 5's {@link RadioGroup} has been checked;
        // add to #questionsAnswered if that is the case, otherwise make #tvWarn5 visible.
        if (radGrp1.getCheckedRadioButtonId() == -1) {
            tvWarn5.setVisibility(View.VISIBLE);
        } else {
            questionsAnswered++;
            tvWarn5.setVisibility(View.GONE);
        }

        return questionsAnswered == 5;
    }
}