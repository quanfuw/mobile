package org.exoplatform.session11phi.cammedia;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends Activity {

	private VideoView video;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        
        video = (VideoView)findViewById(R.id.video_surface);
        
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_office);
        MediaController mc = new MediaController(this);
        
        video.setMediaController(mc);
        video.setVideoURI(uri);
    }

	@Override
	protected void onPause() {
		if (video != null) {
			video.stopPlayback();
		}
		super.onPause();
	}
    
    

}
