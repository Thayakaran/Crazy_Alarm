package com.example.alarmsystem;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;


import static com.example.alarmsystem.App.CHANNEL_1_ID;

public class MyBroadcastReceiver extends BroadcastReceiver {

    NotificationManagerCompat notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        context.sendBroadcast(new Intent("Alarm_Notification_Intent"));

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        String name = intent.getExtras().getString("name");
        String tone = intent.getExtras().getString("tone");
        int ringtoneId = R.raw.tone_chennai_funny;;

        Log.d("check-intent-name", name);
        Log.d("check-intent-tone", tone);

        if(tone.equals("Select Alarm Tone")){
            ringtoneId = R.raw.tone_chennai_funny;
        }
        else if(tone.equals("Tone Chennai Funny")){
            ringtoneId = R.raw.tone_chennai_funny;
        }
        else if(tone.equals("Tone Energetic")){
            ringtoneId = R.raw.tone_energetic;
        }
        else if(tone.equals("Tone Iphone")){
            ringtoneId = R.raw.tone_iphone;
        }
        else if(tone.equals("Tone Kabali")){
            ringtoneId = R.raw.tone_kabali;
        }
        else if(tone.equals("Tone Kadhale Kadhale")){
            ringtoneId = R.raw.tone_kadhale_kadhale;
        }
        else if(tone.equals("Tone Morning 96")){
            ringtoneId = R.raw.tone_morning_96;
        }
        else if(tone.equals("Tone Neethanae")){
            ringtoneId = R.raw.tone_neethanae;
        }
        else if(tone.equals("Tone Pachainiramae")){
            ringtoneId = R.raw.tone_pachainiramae;
        }

        if(AudioPlay.isplayingAudio){
            AudioPlay.stopAudio();
        }
        AudioPlay.playAudio(context,ringtoneId);

        if(AudioPlay.isplayingAudio){
            Log.d("checkMedia", "playing");
        }



    }
/////////////********* failed / but called from main ********/////////////////
    public void displayNotification(Context context){

        PendingIntent actionIntent = PendingIntent.getBroadcast(context,0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Simple Notification")
                .setContentText("This is simple notification-------")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setColor(Color.RED)
                .setContentIntent(actionIntent)
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();


        notificationManager.notify(1,notification);
    }

}
