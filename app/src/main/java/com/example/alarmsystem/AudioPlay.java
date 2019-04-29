package com.example.alarmsystem;

        import android.content.Context;
        import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.media.SoundPool;

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
