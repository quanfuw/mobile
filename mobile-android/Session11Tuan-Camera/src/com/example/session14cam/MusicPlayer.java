package com.example.session14cam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MusicPlayer extends ListActivity {



  private static final String MEDIA_PATH = new String("file:///android_asset/");
  private List<String> songs = new ArrayList<String>();
  private MediaPlayer mp = new MediaPlayer();
  private int currentPosition = 0;
  ImageButton  mPlay, mPause ,mstop;
  Button pre, fwd;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_music_player);
    updateSongList();

/*

    mPlay=(ImageButton) findViewById(R.id.play);
    mPlay.setImageResource(R.drawable.play);
    mPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(MusicPlayer.this, "***  *Playing...........", Toast.LENGTH_LONG).show();
        //mPlay.setImageResource(R.drawable.play);
        // managerOfSound(1);
        if (mp != null) {
          if( ! mp.isPlaying()) {
            mp.start() ;
          } 
        }
      }
    });


    mPause = (ImageButton) findViewById(R.id.pause);
    mPause.setImageResource(R.drawable.pause);

    pre=(Button)findViewById(R.id.previous);
    fwd=(Button)findViewById(R.id.forward);


    mPause.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(mPause.getTag()=="pause")
        {
          if (mp != null) {
            Toast.makeText(MusicPlayer.this, "***Press again to get RESUME",5000).show();
            mPause.setImageResource(R.drawable.pause);
            mPause.setTag("Resume");
            mp.pause();

          }
        }
        else
        {
          mp.start();


          // mPause.setImageResource(R.drawable.pause);
          mPause.setTag("pause");

        }



      }

    });

    pre.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        if(mp!=null && mp.isPlaying()){
          mp.seekTo(mp.getCurrentPosition()-50000);
          String duration;
          Toast.makeText(MusicPlayer.this, (mp.getDuration()-mp.getCurrentPosition())/6000+" Remaining", 200).show();
        }

      }

    });
    fwd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        if(mp!=null && mp.isPlaying()){
          
          mp.seekTo(mp.getCurrentPosition()+50000);
        }

      }

    });

*/
    mstop = (ImageButton) findViewById(R.id.stop);
    mstop.setImageResource(R.drawable.stop);
    mstop.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        if (mp != null) {
          // mPlay.setImageResource(R.drawable.play);
          Toast.makeText(MusicPlayer.this, "***Stopped...........", Toast.LENGTH_LONG).show();
          mp.stop();
         // mp.release();

        }

      }

    });

  }

  public void updateSongList() {
    try {
      AssetManager as = this.getAssets() ;
      for(String s: as.list("")){
        if(s.contains(".mp3"))
          songs.add(s);
      }

    } catch (Exception e) {
      Log.v(getString(R.string.app_name), e.getMessage());
    }
    ArrayAdapter<String> songList = new ArrayAdapter<String>(this,
        R.layout.song_item, songs);
    setListAdapter(songList);
  }
  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    currentPosition = position;
    playSong(songs.get(position));
  }

  private void playSong(String filename) {
    try {
      FileInputStream mp3Stream = this.getAssets().openFd(filename).createInputStream();
      mp.reset();
      mp.setDataSource(mp3Stream.getFD());
      mp.prepare();
      mp.start();

      // Setup listener so next song starts automatically
      mp.setOnCompletionListener(new OnCompletionListener() {

        public void onCompletion(MediaPlayer arg0) {
          nextSong();
        }

      });

    } catch (IOException e) {
      Log.v(getString(R.string.app_name), e.getMessage());
    }
  }

  private void nextSong() {
    if (++currentPosition >= songs.size()) {
      // Last song, just reset currentPosition
      currentPosition = 0;
    } else {
      // Play next song
      playSong(songs.get(currentPosition));
    }
  }




  class Mp3Filter implements FilenameFilter {
    public boolean accept(File dir, String name) {
      return (name.endsWith(".mp3"));
    }
  }

}
