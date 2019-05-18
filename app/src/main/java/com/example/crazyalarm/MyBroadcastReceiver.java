package com.example.crazyalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;


import static com.example.crazyalarm.App.CHANNEL_1_ID;

public class MyBroadcastReceiver extends BroadcastReceiver {

//    NotificationManagerCompat notificationManager;

    @Override
    public void onReceive(Context context, Intent BroadcastIntent) {

        String count = BroadcastIntent.getExtras().getString("count");
//        context.sendBroadcast(new Intent("Alarm_Intent").putExtra("count",count));

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        displayNotification(context,count);


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        String name = BroadcastIntent.getExtras().getString("name");
        String tone = BroadcastIntent.getExtras().getString("tone");

        int ringtoneId = R.raw.tone_chennai_funny;;

        Log.d("BroadcastIntent-name", name);
        Log.d("BroadcastIntent-tone", tone);

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

    //huyftytyfjh
    public void displayNotification( Context context, String quizCount){

        Intent quizActivityIntent = new Intent(context, QuizActivity.class);
        quizActivityIntent.putExtra("count", quizCount);
        PendingIntent quizIntent = PendingIntent.getActivity(context,0, quizActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.clock_icon);
        // Assign big picture notification
        NotificationCompat.BigPictureStyle bpStyle = new NotificationCompat.BigPictureStyle();
        bpStyle.bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.quiz_icon)).build();

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Alarm ON")
                .setContentText("SOLVE QUIZ TO STOP ALARM ")
                .setLargeIcon(largeIcon)
                .setStyle(bpStyle)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setFullScreenIntent(quizIntent, true)
                .setCategory(Notification.CATEGORY_ALARM)
                .setColor(Color.GREEN)
                .setOngoing(true)
                .setAutoCancel(false)
//                .addAction(R.drawable.ic_alarm, "Solve Quiz", quizIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(01,notification);
    }


}
