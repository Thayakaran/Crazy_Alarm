package com.example.alarmsystem;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewAlarms extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.alarmRecycler);

        Cursor data = myDB.getAllData();
        List<Alarm> alarmList = new ArrayList<>();
        int i = 0;
        if(data.getCount() != 0){
            while(data.moveToNext()){
                Alarm alarms = new Alarm(data.getString(2),data.getString(1));
                alarmList.add(i,alarms);
                i++;
            }
            Log.d("list", alarmList.toString());
            AlarmViewAdapter adapter = new AlarmViewAdapter(alarmList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        }else{
            Toast.makeText(ViewAlarms.this, "There is no alarms to show!",Toast.LENGTH_LONG).show();
        }
    }
}