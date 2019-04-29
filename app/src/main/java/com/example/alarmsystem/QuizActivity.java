package com.example.alarmsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    Button stop;
    RadioGroup radioGroupQstn;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        stop = (Button) findViewById(R.id.btnStop);
        radioGroupQstn = (RadioGroup) findViewById(R.id.radioGroupQstn);
        result = (TextView) findViewById(R.id.tvResult);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get selected radio button from radioGroup
                int selectedId = radioGroupQstn.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);

                if(radioButton.getText().equals("n")){
                    //stop alarm
                    if(AudioPlay.isplayingAudio){
                        AudioPlay.stopAudio();
                    }
                    result.setText("Correct Answer. Alarm Stopped !!!");
                }
                else {
                    result.setText("Incorrect Answer");
                }
            }
        });
    }
}
