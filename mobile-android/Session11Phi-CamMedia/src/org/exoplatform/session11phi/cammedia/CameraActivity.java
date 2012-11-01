package org.exoplatform.session11phi.cammedia;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class CameraActivity extends Activity {

	private SurfaceView preview;
	private SurfaceHolder holder;
	private Camera camera;
	private boolean cameraIsReady = false; // whether the camera has been initialized and is ready to preview images
	
	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {}
		@Override
		public void surfaceCreated(SurfaceHolder holder) {}
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			initPreview(width, height);
			startPreview();
		}
	};
	
	Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera cam) {
			Toast.makeText(getBaseContext(), "Press back to return to the main activity.", Toast.LENGTH_SHORT).show();
		}
	};

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        
        preview = (SurfaceView)findViewById(R.id.camera_preview);
        holder = preview.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(surfaceCallback);
    }

	@Override
	protected void onDestroy() {
		releaseCamera();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		releaseCamera();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		camera = Camera.open();
		startPreview();
	}
	
	/**
	 * Returns the biggest Camera.Size that fits in the SurfaceView.
	 * @param width The Surface width.
	 * @param height The surface height.
	 * @param parameters The Camera.Parameters object that contains the supported sizes on the device.
	 * @return The Camera.Size to use in Camera.Parameters.setPreviewSize(...).
	 */
	private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
		Camera.Size result=null;
		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
			// continue only if the current size fits in the surface size
			if (size.width<=width && size.height<=height) {
				if (result==null) result=size; // set the result for the first time
				else {
					int resultArea=result.width*result.height;
					int newArea=size.width*size.height;
					// if the new size is bigger than the previous one, use that one instead
					if (newArea>resultArea) result=size;
				}
			}
		}
		return result;
	}
	
	private void initPreview(int w, int h) {
		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (!cameraIsReady) {
			Camera.Parameters params = camera.getParameters();
			Camera.Size size = getBestPreviewSize(w, h, params);
			if (size != null) {
				params.setPreviewSize(size.width, size.height);
				camera.setParameters(params);
				cameraIsReady=true;
			}
		}
	}
	
	private void startPreview() {
		// only if the camera has been initialized (initPreview) we can use it for preview
		if (cameraIsReady)
			camera.startPreview();
	}
	
	public void capture(View v) {
		camera.takePicture(null, null, pictureCallback);
	}
	
	private void releaseCamera() {
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera=null;
		}
	}
}
