package com.example.crazyalarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.crazyalarm.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    ImageButton addAlarm;
    AlertDialog.Builder mBuilder, tBuilder;
    View mView, tView;
    TimePicker mTimePicker;
    NumberPicker mNumberPicker;
    AlertDialog mDialog, tDialog;
    EditText mName;
    TextView alarmTime, mhead, alarmName, testTxt;
    Button mTone, mAdd, mCancel, tSelect, tCancel;
    RadioGroup radioGroup1;

    RecyclerView recyclerView;

    List<Alarm> alarmList = new ArrayList<>();
    AlarmViewAdapter.OnItemClickListener listener;
    private  String sid ;
    private String stime ;
    private String sname;
    private String stone ;

//    NotificationManagerCompat notificationManager;


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
//        mTimePicker.setIs24HourView(true);
        mNumberPicker = (NumberPicker) mView.findViewById(R.id.numberPicker);
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(10);
        mNumberPicker.setWrapSelectorWheel(true);
        mName = (EditText) mView.findViewById(R.id.etName);
        mTone = (Button) mView.findViewById(R.id.btnTone);
        mAdd = (Button) mView.findViewById(R.id.btnAdd);
        mCancel = (Button) mView.findViewById(R.id.btnCancel);
        mhead = (TextView) mView.findViewById(R.id.textView2);

        radioGroup1 = (RadioGroup) tView.findViewById(R.id.radGrp1);
        tSelect = (Button) tView.findViewById(R.id.btnSelect);
        tCancel = (Button) tView.findViewById(R.id.btnCancel);

        alarmTime = (TextView) findViewById(R.id.alarmTimeView);
        alarmTime = (TextView) findViewById(R.id.alarmNameView);
        recyclerView = (RecyclerView) findViewById(R.id.alarmRecycler);

//        notificationManager = NotificationManagerCompat.from(this);

        setAlarm();
//        registerReceiver(broadcastReceiver, new IntentFilter("Alarm_Intent"));
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
//                        Log.e("timePick",mTimePicker.getCurrentHour().toString());
                        int TpHour = mTimePicker.getCurrentHour(); // hourOfDay
                        int TpMinute =  mTimePicker.getCurrentMinute(); // Minute
                        String getAMPMValue = "AM";
                        if(TpHour>11){
                            getAMPMValue="PM";
                            if(TpHour!=12) {
                                TpHour = TpHour-12;
                            }
                        }
                        // Display the 12 hour format time in TextView
                        Log.e("timePicks",""+TpHour+":"+ TpMinute+ " "+ getAMPMValue);

                        boolean isInserted = myDb.insertData(mName.getText().toString(),mTimePicker.getCurrentHour().toString()+":"+mTimePicker.getCurrentMinute().toString(),mTone.getText().toString(), String.valueOf(mNumberPicker.getValue()), "1");
                        if(isInserted == true){
                            Toast.makeText(MainActivity.this, "Alarm added Successfully", Toast.LENGTH_LONG).show();

                            DecimalFormat formatter = new DecimalFormat("00");
                            String HourString = formatter.format(TpHour);
                            String MinuteString = formatter.format(TpMinute);
                            String message = "Alarm set for "+""+HourString+":"+ MinuteString+ " "+ getAMPMValue;
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

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

        /**********  deleting alarm when swiping listed alarm atems   *********/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                sid = alarmList.get(viewHolder.getAdapterPosition()).getId();
                stime = alarmList.get(viewHolder.getAdapterPosition()).getTime();
                sname = alarmList.get(viewHolder.getAdapterPosition()).getName();
                stone = alarmList.get(viewHolder.getAdapterPosition()).getTone();
                myDb.deleteData(sid);
                showAlarms();
                Toast.makeText(MainActivity.this, "alarm deleted", Toast.LENGTH_SHORT).show();

                /******       snakbar for undo deleted item     ***********/
                Snackbar.make(recyclerView, "alarm deleted.",Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb.insertData(sname,stime,stone,"1","0");
                        showAlarms();
                    }
                }).show();

            }


        }).attachToRecyclerView(recyclerView);

        Intent intent = getIntent();
        final String method = intent.getStringExtra("methode");
        final String name = intent.getStringExtra("name");
        final String id = intent.getStringExtra("id");
        final String time = intent.getStringExtra("time");
        final String tone = intent.getStringExtra("tone");
        final String count = intent.getStringExtra("count");


        if(method != null && method.equals("show")){

            String[] separated = time.split(":");

//                showDialog(id,name,time);
            mhead.setText("UPDATE ALARM");
            mAdd.setText("Update");
            mName.setText(name);
            String hr = separated[0].replaceAll("^0+","").replaceAll(" ","");
            String min = separated[1].replaceAll("^0+","").replaceAll(" ","").replaceAll("[^0-9]", "");
            Log.e("hour",hr);
            mTimePicker.setCurrentHour(Integer.valueOf(hr));
            mTimePicker.setCurrentMinute(Integer.valueOf(min));
            mTone.setText(tone);
            mNumberPicker.setValue(Integer.parseInt(count));

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
//                        Log.e("timePick",mTimePicker.getCurrentHour().toString());
                    int TpHour = mTimePicker.getCurrentHour(); // hourOfDay
                    int TpMinute =  mTimePicker.getCurrentMinute(); // Minute
                    String getAMPMValue = "AM";
                    if(TpHour>11){
                        getAMPMValue="PM";
                        if(TpHour!=12) {
                            TpHour = TpHour-12;
                        }
                    }
                    // Display the 12 hour format time in TextView
                    Log.e("timePicks",""+TpHour+":"+ TpMinute+ " "+ getAMPMValue);
                    boolean isInserted = myDb.updateAlarm(id,mName.getText().toString(),mTimePicker.getCurrentHour().toString()+":"+mTimePicker.getCurrentMinute().toString(),mTone.getText().toString(), String.valueOf(mNumberPicker.getValue()),"1");
                    if(isInserted == true){
                        Toast.makeText(MainActivity.this, "Alarm Updated Successfully", Toast.LENGTH_LONG).show();
                        DecimalFormat formatter = new DecimalFormat("00");
                        String HourString = formatter.format(TpHour);
                        String MinuteString = formatter.format(TpMinute);
                        String message = "Alarm set for "+""+HourString+":"+ MinuteString+ " "+ getAMPMValue;
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                        showAlarms();
                        setAlarm();
                        mDialog.dismiss();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Failed to update Alarm", Toast.LENGTH_LONG).show();
                    }
                }
            });

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                    Toast.makeText(MainActivity.this, "cancel clicked", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    public void setAlarm(){

        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            return;
        }

        String name;
        String time;
        String tone;
        String count;
        String[] separatedTime = new String[10];
        int x = 0;

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date date = new Date();

        while (res.moveToNext()){
            name = res.getString(1);
            time = res.getString(2);
            tone = res.getString(3);
            count = res.getString(4);

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


            Intent BroadcastIntent = new Intent(MainActivity.this,MyBroadcastReceiver.class);
            BroadcastIntent.putExtra("name", name);
            BroadcastIntent.putExtra("time", time);
            BroadcastIntent.putExtra("tone", tone);
            BroadcastIntent.putExtra("count", count);
            BroadcastIntent.putExtra("path", getPackageName());
            ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,x,BroadcastIntent,0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),pendingIntent); //setRepeating**************************

            intentArray.add(pendingIntent);
            x++;
        }

    }

//    public void displayNotification(String quizCount){
//
//        Intent quizActivityIntent = new Intent(this, QuizActivity.class);
//        quizActivityIntent.putExtra("count", quizCount);
//        PendingIntent quizIntent = PendingIntent.getActivity(this,0, quizActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.clock_icon);
//        // Assign big picture notification
//        NotificationCompat.BigPictureStyle bpStyle = new NotificationCompat.BigPictureStyle();
//        bpStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.quiz_icon)).build();
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
//                .setSmallIcon(R.drawable.ic_alarm)
//                .setContentTitle("Alarm ON")
//                .setContentText("SOLVE QUIZ TO STOP ALARM ")
//                .setLargeIcon(largeIcon)
//                .setStyle(bpStyle)
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setFullScreenIntent(quizIntent, true)
//                .setCategory(Notification.CATEGORY_ALARM)
//                .setColor(Color.GREEN)
//                .setOngoing(true)
//                .setAutoCancel(false)
////                .addAction(R.drawable.ic_alarm, "Solve Quiz", quizIntent)
//                .build();
//
//        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(01,notification);
//    }

    public void showAlarms(){

        String[] separatedDbTime = new String[10];

        Cursor data = myDb.getAllData();
        alarmList.clear();
        int i = 0;
        if(data.getCount() != 0){
            while(data.moveToNext()){

                separatedDbTime = data.getString(2).split(":");

                int dbHour = Integer.parseInt(separatedDbTime[0]);
                int dbMinute =  Integer.parseInt(separatedDbTime[1]);

                String getAMPMValue = "AM";
                if(dbHour>11){
                    getAMPMValue="PM";
                    if(dbHour!=12) {
                        dbHour = dbHour-12;
                    }
                }
                DecimalFormat formatter = new DecimalFormat("00");
                String dbHourString = formatter.format(dbHour);
                String dbMinuteString = formatter.format(dbMinute);
                String time = dbHourString+" : "+dbMinuteString+" "+getAMPMValue;

                Alarm alarms = new Alarm(time,data.getString(1),data.getString(0),data.getString(3),data.getString(4),data.getString(5));
                alarmList.add(i,alarms);
                i++;


            }
            Log.d("list", alarmList.toString());
            AlarmViewAdapter adapter = new AlarmViewAdapter(alarmList,listener);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        }else{
            Toast.makeText(this, "There is no alarms to show!",Toast.LENGTH_LONG).show();
        }
    }

//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            String quizCount = intent.getExtras().getString("count");
//            Log.e("zountzzzz", quizCount);
//            displayNotification(quizCount);
//        }
//    };
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(broadcastReceiver);
//    }


}
