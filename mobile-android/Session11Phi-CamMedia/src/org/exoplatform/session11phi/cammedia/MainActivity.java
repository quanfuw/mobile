package org.exoplatform.session11phi.cammedia;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

public class MainActivity extends Activity {
	
	private static final int REQUEST_CODE = 1234;
	private MediaPlayer player = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override
	protected void onPause() {
		releasePlayer();
		super.onPause();
	}

	public void start(View v) {
    	Intent i = null;
    	switch (v.getId()) {
		case R.id.button_photo:
			i = new Intent(this, CameraActivity.class);
			startActivity(i);
			break;
		case R.id.button_photo_intent:
		    i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		    startActivityForResult(i, REQUEST_CODE);
			break;
		case R.id.button_music:
			playPauseMusic();
			break;
		case R.id.cancel_music:
			stopMusic();
			break;
		default:
			break;
		}
    	i=null;
    }
    
    private void playPauseMusic() {
    	if (player==null)
    		player = MediaPlayer.create(this, R.raw.tryo_greenwashing);
    	if (player.isPlaying()) {
    		player.pause();
    	} else {
    		player.start();
    	}
    }
    private void stopMusic() {
    	if (player != null) {
    		player.stop();
    		releasePlayer();
    	}
    }
    private void releasePlayer() {
    	if (player != null) {
			player.release();
			player=null;
		}
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			try {
				// Transform the InputStream into a byte array
				InputStream stream = getContentResolver().openInputStream(data.getData());
				byte bdata[] = new byte[1024];
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		        int count;
		        while ((count = stream.read(bdata)) != -1) {
		        	buffer.write(bdata, 0, count);
		        }
		        buffer.flush();
		        byte bytes[] = buffer.toByteArray();
		        buffer.close();
		        stream.close();
		        // pass the byte array to the intent
		        Intent i = new Intent(this, ViewPicActivity.class);
		        i.putExtra(ViewPicActivity.IMAGE, bytes);
		        startActivity(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    
    
}
