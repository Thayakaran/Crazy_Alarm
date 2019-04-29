package com.example.alarmsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AlarmViewAdapter extends RecyclerView.Adapter<AlarmViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView time;
        public TextView name;


        public ViewHolder(final View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.alarmTimeView);
            name = (TextView) itemView.findViewById(R.id.alarmNameView);
        }

    }
    private List<Alarm> mAlarm;

    public AlarmViewAdapter(List<Alarm> alarms) {
        mAlarm = alarms;
    }


    @Override
    public AlarmViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.single_alarm_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(AlarmViewAdapter.ViewHolder viewHolder, int position) {
        Alarm alarm = mAlarm.get(position);

        TextView mTime = viewHolder.time;
        mTime.setText(alarm.getTime());

        TextView mName = viewHolder.name;
        mName.setText(alarm.getName());
    }

    @Override
    public int getItemCount() {
        return mAlarm.size();
    }
}
