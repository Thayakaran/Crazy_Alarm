package com.example.crazyalarm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class AlarmViewAdapter extends RecyclerView.Adapter<AlarmViewAdapter.ViewHolder> {

    private OnItemClickListener mlistener;

    private List<Alarm> mAlarm;
    private static DatabaseHelper myDb;
    private Context context;

    public interface OnItemClickListener{
        void onItemUpdate(Alarm alarm);
        void itemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }

    @Override
    public void onBindViewHolder(AlarmViewAdapter.ViewHolder viewHolder, int position) {
        Alarm alarm = mAlarm.get(position);

        TextView mTime = viewHolder.time;
        mTime.setText(alarm.getTime());

        TextView mName = viewHolder.name;
        mName.setText(alarm.getName());

        Switch mStatus = viewHolder.status;
        if(alarm.getStatus().equals("1")){
            mStatus.setChecked(true);
        }else {
            mStatus.setChecked(false);
        }

        viewHolder.bind(alarm,mlistener,context);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView time;
        public TextView name;
        public Switch status;
        MainActivity main;


        public ViewHolder(final View itemView, final Context context) {
            super(itemView);
            main = new MainActivity();
            time = (TextView) itemView.findViewById(R.id.alarmTimeView);
            name = (TextView) itemView.findViewById(R.id.alarmNameView);
            status = (Switch) itemView.findViewById(R.id.alram_switch);
            myDb = new DatabaseHelper(context);
        }

        public void bind(final Alarm alarm , final OnItemClickListener listener , final Context context){
            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){

                        if(status.isChecked()){
                            boolean out =  myDb.updateAlarmStatus(alarm.getId(),"1");

                            Log.e("switch","checked on ");
                            Log.e("switch", String.valueOf(out));

                        }else {
                            boolean out =  myDb.updateAlarmStatus(alarm.getId(),"0");

                            Log.e("switch","checked off");
                            Log.e("switch", String.valueOf(out));

                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("cardview clicked","edddd");
                    String id  = alarm.getId();
                    String time = alarm.getTime();
                    String name = alarm.getName();
                    String tone = alarm.getTone();
                    String count = alarm.getCount();
                    Intent intent = new Intent(context,MainActivity.class);
                    intent.putExtra("methode","show");
                    intent.putExtra("id",id);
                    intent.putExtra("time",time);
                    intent.putExtra("name",name);
                    intent.putExtra("tone",tone);
                    intent.putExtra("count",count);
                    context.startActivity(intent);

                }
            });

        }

    }

    public AlarmViewAdapter(List<Alarm> alarms, OnItemClickListener listener) {
        mAlarm = alarms;
        mlistener = listener;
    }


    @Override
    public AlarmViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.single_alarm_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView,context);
        return viewHolder;
    }




    @Override
    public int getItemCount() {
        return mAlarm.size();
    }
}
