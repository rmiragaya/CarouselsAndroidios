package com.rodrigomiragaya.carrouselsandroidios;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivitie extends AppCompatActivity {
    private static final String TAG = "VideoActivitie";

    private VideoView videoView;
    private ProgressBar circularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_activitie);

        videoView = findViewById(R.id.videoView);
        circularProgressBar = findViewById(R.id.progressBar);

        //Set Controles de video
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        //Set Url
        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra("VIDEOURL");
        String nombrepelicula = intent.getStringExtra("NOMBREPELICULA");

        Uri uri = Uri.parse(videoUrl);

        //Set Video
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        // TODO Auto-generated method stub
                        circularProgressBar.setVisibility(View.GONE);
                        mp.start();
                    }
                });
            }
        });



        Toast.makeText(this, "Cargando " + nombrepelicula, Toast.LENGTH_LONG).show();
    }
}
