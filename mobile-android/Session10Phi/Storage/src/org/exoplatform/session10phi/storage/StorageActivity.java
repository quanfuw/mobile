package org.exoplatform.session10phi.storage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StorageActivity extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener {

	private boolean canRead = false;
	private boolean canWrite = false;
	private File currentFile = null;
	private AlertDialog newFolderDialog = null;
	private AlertDialog downloadFileDialog = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        checkReadWritePermissions();
        if (canRead) {
        	cleanList();
    		readFolder(Environment.getExternalStorageDirectory());
    	}
    }

	/**
     * Empty the files/folders list, to make place for a new list.
     */
    private void cleanList() {
    	LinearLayout l = (LinearLayout)findViewById(R.id.files_layout);
		l.removeAllViews();
    }
    /**
     * Refresh the view of the current folder.
     */
    public void refresh() {
    	readFolder(currentFile);
    }
    /**
     * List the files and folders of the given folder.
     * @param f The folder to read.
     */
    private void readFolder(File f) {
    	if (f != null && f.isDirectory()) {
    		cleanList();
    		currentFile = f;
    		File[] files = f.listFiles();
    		TextView n = (TextView)findViewById(R.id.text_current_folder);
    		TextView p = (TextView)findViewById(R.id.text_current_path);
    		p.setText(f.getParent()+"/");
    		n.setText(f.getName()+"/");
    		for (File file : files) {
				drawFile(file);
			}
    	}
    }
    /**
     * Draw the given file on the view.
     * @param f The file to draw.
     */
    private void drawFile(File f) {
    	LinearLayout v = (LinearLayout)findViewById(R.id.files_layout);
    	TextView file = null;
    	if (f.isDirectory()) {
    		file = new Button(this);
    		file.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
    													LinearLayout.LayoutParams.WRAP_CONTENT));
    		file.setOnClickListener(this);
    		
    	} else if (f.isFile()) {
    		file = new TextView(this);
    		file.setTextSize(16);
    	}
    	file.setText(f.getName());
    	v.addView(file);
    }
    /**
     * Sets variables canRead and canWrite.
     */
    private void checkReadWritePermissions() {
    	String state = Environment.getExternalStorageState();

    	if (Environment.MEDIA_MOUNTED.equals(state)) {
    	    // We can read and write the media
    	    canRead = canWrite = true;
    	} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
    	    // We can only read the media
    	    canRead = true;
    	    canWrite = false;
    	} else {
    	    // Something else is wrong. It may be one of many other states, but all we need
    	    //  to know is we can neither read nor write
    	    canRead = canWrite = false;
    	}
    }

    /**
     * Called when the user clicks on a folder button.<br/>
     * Open this folder and list its files and folders.
     */
	@Override
	public void onClick(View v) {
		if (v instanceof Button) {
			File f = new File(currentFile, ((Button) v).getText().toString());
			readFolder(f);
		}
	}

	/**
	 * Called when the user presses the back button.<br/>
	 * If the user is at the root folder of the storage, keep the normal behavior.<br/>
	 * If the user is reading a folder of the storage, return to the parent folder.
	 */
	@Override
	public void onBackPressed() {
		if (currentFile.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath()))
		{
			super.onBackPressed();
		} else {
			File f = currentFile.getParentFile();
			readFolder(f);
		}
	}
	/**
	 * Show the new folder dialog.
	 */
	public void newFolder(View v) {
		if (newFolderDialog == null)
			newFolderDialog = createFolderNameDialog();
		
		newFolderDialog.show();
	}
	/**
	 * Create a new folder.
	 * @param name The name of the folder.
	 */
	private void newFolder(String name) {
		if (!name.equals("")) {
			File output = new File(currentFile, name);
			if (!output.exists() && canWrite) {
				output.mkdir();
				readFolder(currentFile);
			}
		}
	}
	/**
	 * Create the dialog in which the user enters the name of the folder to create.
	 * @return The AlertDialog.
	 */
	private AlertDialog createFolderNameDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    LayoutInflater inflater = getLayoutInflater();
	    builder.setView(inflater.inflate(R.layout.dialog_new_folder, null))
	    	.setPositiveButton("OK", this)
	    	.setNegativeButton("Cancel", this);
		return builder.create();
	}

	/**
	 * This class is set as the OnClick listener of the dialogs.<br/>
	 * This method is called when the user presses a button on a dialog.
	 */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == Dialog.BUTTON_NEGATIVE) {
			dialog.cancel();
		} else if (which == Dialog.BUTTON_POSITIVE) {
			EditText name = (EditText)newFolderDialog.findViewById(R.id.field_new_folder);
			if (name != null) {
				newFolder(name.getText().toString());
			}
		} else {
			// The user selected an item in the downloads list
			if (canWrite) {
				DownloadTask task = new DownloadTask();
				task.execute(which);
			}
		}
	}

	/**
	 * Show the dialog to download a file.
	 */
	public void download(View v) {
		if (downloadFileDialog == null)
			downloadFileDialog = createDownloadDialog();
		
		downloadFileDialog.show();
	}
	/**
	 * Create the dialog in which the user selects a file to download.
	 * @return the AlertDialog.
	 */
	private AlertDialog createDownloadDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("Pick a file")
	    	.setItems(R.array.list_downloads, this)
	    	.setNegativeButton("Cancel", this);
		return builder.create();
	}
	/**
	 * Build a notification to inform the user that a file has been downloaded.<br/>
	 * It shows a preview of the image, and opens an activity to display the image full screen.
	 * @param downloadedFile The file that has been downloaded.
	 * @return The Notification.
	 */
	private Notification buildDownloadNotification(File downloadedFile) {
		// Create a Bitmap from the image File
		Bitmap b= BitmapFactory.decodeFile(downloadedFile.getAbsolutePath());
		// Set parameters of the new Notification
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
					.setSmallIcon(R.drawable.ic_launcher) // icon of the app
					.setLargeIcon(b)					  // large icon from the image (android 4+ only)
					.setContentTitle("Download successfull")
					.setContentText("File "+downloadedFile.getName()+" downloaded successfully.")
					.setAutoCancel(true);				  // will be cancelled when the user presses it
		// Set style for the expanded view (android 4+ only)
		NotificationCompat.BigPictureStyle picStyle =
				new NotificationCompat.BigPictureStyle();
		picStyle.setBigContentTitle(downloadedFile.getName());
		picStyle.bigPicture(b);							  // picture from the image
		mBuilder.setStyle(picStyle);
		// Create the intent to launch when the notification is pressed
		Intent i = new Intent(this, ViewPicActivity.class);
		i.putExtra("FILE_NAME", downloadedFile.getAbsolutePath());
		// Create a stack to return to the current activity when the user presses the back button
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(ViewPicActivity.class);
		stackBuilder.addNextIntent(i);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		// Build and return the Notification
		return mBuilder.build();
	}
	/**
	 * The async task that downloads the file.<br/>
	 * Takes parameters:<br/>
	 * - position of the URL in the array (Integer)<br/>
	 * - Void<br/>
	 * Returns:<br/>
	 * - The newly created file, or null if the operation fails.
	 * @author paristote
	 *
	 */
	private class DownloadTask extends AsyncTask<Integer, Void, File> {
		
		private final String[] URLs = {
			"http://doodleaday.files.wordpress.com/2010/07/doodle-525-cloud.jpg",
			"http://www.touilleur-express.fr/wp-content/benjamin_mestrallet_touilleur_express_ipad.jpg",
			"http://img-ipad.lisisoft.com/img/1/4/1441-1-exo-platform-3.5.jpg",
			"http://www.bikeexif.com/wp-content/uploads/2010/04/moto-guzzi.jpg"
		};

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected File doInBackground(Integer... params) {
			// Download the file at the given URL
			String strUrl = URLs[params[0]];
			String[] array = getResources().getStringArray(R.array.list_downloads);
			File bitmap = new File(currentFile, array[params[0]]+".jpg");
			InputStream is;
		    OutputStream os;
		    try {
		    	Log.d("EXO_TAG", "*** Downloading from task");
		        URL url = new URL(strUrl);
		        is = new BufferedInputStream(url.openStream());
		        os = new FileOutputStream(bitmap.getAbsolutePath());
		          byte bdata[] = new byte[1024];
		          int count;
		          while ((count = is.read(bdata)) != -1) {
		              os.write(bdata, 0, count);
		          }
			      // Successfully finished
			      os.flush();
		  		  os.close();
		  		  is.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		      return null;
		    }
		    return bitmap;
		}
		
		@Override
		protected void onPostExecute(File result) {
			// Notify the user of the downloaded image
			NotificationManager mNotificationManager =
				    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(1234, buildDownloadNotification(result));
			refresh();
		}
    	
    }
    
}
