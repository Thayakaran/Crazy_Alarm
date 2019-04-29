package com.example.alarmsystem;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.alarmsystem.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    ImageButton addAlarm;
    AlertDialog.Builder mBuilder, tBuilder;
    View mView, tView;
    TimePicker mTimePicker;
    AlertDialog mDialog, tDialog;
    EditText mName;
    TextView alarmTime, alarmName;
    Button mTone, mAdd, mCancel, tSelect, tCancel;
    RadioGroup radioGroup1;

    RecyclerView recyclerView;

    NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);


        addAlarm = (ImageButton) findViewById(R.id.btnAddAlarm);

        mBuilder = new AlertDialog.Builder(MainActivity.this);
        mView = getLayoutInflater().inflate(R.layout.add_alarm, null);
        mBuilder.setView(mView);
        mDialog = mBuilder.create();

        tBuilder = new AlertDialog.Builder(MainActivity.this);
        tView = getLayoutInflater().inflate(R.layout.select_tone, null);
        tBuilder.setView(tView);
        tDialog = tBuilder.create();

        mTimePicker = (TimePicker) mView.findViewById(R.id.timePicker);
        mTimePicker.setIs24HourView(true);
        mName = (EditText) mView.findViewById(R.id.etName);
        mTone = (Button) mView.findViewById(R.id.btnTone);
        mAdd = (Button) mView.findViewById(R.id.btnAdd);
        mCancel = (Button) mView.findViewById(R.id.btnCancel);

        radioGroup1 = (RadioGroup) tView.findViewById(R.id.radGrp1);
        tSelect = (Button) tView.findViewById(R.id.btnSelect);
        tCancel = (Button) tView.findViewById(R.id.btnCancel);

        alarmTime = (TextView) findViewById(R.id.alarmTimeView);
        alarmTime = (TextView) findViewById(R.id.alarmNameView);
        recyclerView = (RecyclerView) findViewById(R.id.alarmRecycler);

        notificationManager = NotificationManagerCompat.from(this);

        setAlarm();
        registerReceiver(broadcastReceiver, new IntentFilter("Alarm_Notification_Intent"));
        showAlarms();

        ////////////////////////////////////////////////////////////////////

        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.show();

                mTone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tDialog.show();

                        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                                switch (checkedId) {
                                    case R.id.btnRad1:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_chennai_funny);
                                        return;
                                    case R.id.btnRad2:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_energetic);
                                        return;
                                    case R.id.btnRad3:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_iphone);
                                        return;
                                    case R.id.btnRad4:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_kabali);
                                        return;
                                    case R.id.btnRad5:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_kadhale_kadhale);
                                        return;
                                    case R.id.btnRad6:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_morning_96);
                                        return;
                                    case R.id.btnRad7:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_neethanae);
                                        return;
                                    case R.id.btnRad8:
                                        if(AudioPlay.isplayingAudio){
                                            AudioPlay.stopAudio();
                                        }
                                        AudioPlay.playAudio(MainActivity.this, R.raw.tone_pachainiramae);
                                        return;
                                    default:
                                        return;
                                }
                            }
                        });

                        tSelect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // get selected radio button from radioGroup
                                int selectedId = radioGroup1.getCheckedRadioButtonId();

                                // find the radiobutton by returned id
                                RadioButton radioButton = (RadioButton) tView.findViewById(selectedId);

                                Button tone = (Button) mView.findViewById(R.id.btnTone);
                                tone.setText(radioButton.getText());
                                if(AudioPlay.isplayingAudio){
                                    AudioPlay.stopAudio();
                                }
                                tDialog.dismiss();
                            }
                        });

                        tCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(AudioPlay.isplayingAudio){
                                    AudioPlay.stopAudio();
                                }
                                tDialog.dismiss();
                            }
                        });
                    }
                });

                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertData(mName.getText().toString(),mTimePicker.getCurrentHour().toString()+":"+mTimePicker.getCurrentMinute().toString(),mTone.getText().toString());
                        if(isInserted == true){
                            Toast.makeText(MainActivity.this, "Alarm added Successfully", Toast.LENGTH_LONG).show();
                            showAlarms();
                            setAlarm();
                            mDialog.dismiss();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Failed to add Alarm", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });


            }
        });

    }

    public void setAlarm(){

        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            return;
        }

        String name;
        String time;
        String tone;
        String[] separatedTime = new String[10];
        int x = 0;

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date date = new Date();

        while (res.moveToNext()){
            name = res.getString(1);
            time = res.getString(2);
            tone = res.getString(3);

            separatedTime = time.split(":");
            Log.d("check 1 data", separatedTime[0]+":"+separatedTime[1]);

            Calendar cal_alarm = Calendar.getInstance();
            Calendar cal_now = Calendar.getInstance();

            cal_alarm.setTime(date);
            cal_now.setTime(date);

            cal_alarm.set(Calendar.HOUR_OF_DAY,Integer.parseInt(separatedTime[0]));
            cal_alarm.set(Calendar.MINUTE, Integer.parseInt(separatedTime[1]));
            cal_alarm.set(Calendar.SECOND,0);

            if(cal_alarm.before(cal_now)){
                cal_alarm.add(Calendar.DATE,1);
            }

            Intent intent = new Intent(MainActivity.this,MyBroadcastReceiver.class);
            intent.putExtra("name", name);
            intent.putExtra("time", time);
            intent.putExtra("tone", tone);
            intent.putExtra("path", getPackageName());
            ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,x,intent,0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),pendingIntent); //setRepeating**************************

            intentArray.add(pendingIntent);
            x++;
        }

    }

    public void displayNotification(){

        Intent quizActivityIntent = new Intent(this, QuizActivity.class);
        PendingIntent quizIntent = PendingIntent.getActivity(this,0, quizActivityIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Alarm Notification")
                .setContentText("Your Alarm is ON")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(Notification.CATEGORY_ALARM)
                .setColor(Color.RED)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_alarm, "Solve Quiz", quizIntent)
                .build();


        notificationManager.notify(1,notification);
    }

    public void showAlarms(){

        Date d = new Date();
        String[] separatedCurTime = new String[10];
        String[] separatedDbTime = new String[10];

        CharSequence curTime  = DateFormat.format("HH:mm", d.getTime());
        Log.d("timeNow",curTime.toString());

        Cursor data = myDb.getAllData();
        List<Alarm> alarmList = new ArrayList<>();
        alarmList.clear();
        int i = 0;
        if(data.getCount() != 0){
            while(data.moveToNext()){

                separatedCurTime = curTime.toString().split(":");
                separatedDbTime = data.getString(2).split(":");

                if(Integer.parseInt(separatedDbTime[0]) >= Integer.parseInt(separatedCurTime[0]) && Integer.parseInt(separatedDbTime[1]) >= Integer.parseInt(separatedCurTime[1]) ){
                    Alarm alarms = new Alarm(data.getString(2),data.getString(1));
                    alarmList.add(i,alarms);
                    i++;
                }

            }
            Log.d("list", alarmList.toString());
            AlarmViewAdapter adapter = new AlarmViewAdapter(alarmList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        }else{
            Toast.makeText(this, "There is no alarms to show!",Toast.LENGTH_LONG).show();
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // internet lost alert dialog method call from here...
            displayNotification();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }


}
