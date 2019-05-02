package com.example.crazyalarm;

        import android.content.Context;
        import android.media.MediaPlayer;

public class AudioPlay {

    public static MediaPlayer mediaPlayer;

    public static boolean isplayingAudio=false;


    public static void playAudio(Context c, int id){

        mediaPlayer = MediaPlayer.create(c,id);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        isplayingAudio=true;
    }
    public static void stopAudio(){
        mediaPlayer.stop();
        isplayingAudio=false;
    }
}
