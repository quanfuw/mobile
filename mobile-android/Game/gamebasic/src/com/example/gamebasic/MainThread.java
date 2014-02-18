package com.example.gamebasic;

import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private static final String TAG = MainThread.class.getSimpleName();

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	// flag to hold game state
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}
	

	@Override
	public void run() {
		while (running) {
			 long tickCount = 0L;
			 
			         Log.d(TAG, "Starting game loop");
			 
			         while (running) {
			 
			             tickCount++;
			 
			             // update game state
			 
			             // render state to the screen
			 
			         }
			 
			         Log.d(TAG, "Game loop executed " + tickCount + " times");

		}
	}
}
