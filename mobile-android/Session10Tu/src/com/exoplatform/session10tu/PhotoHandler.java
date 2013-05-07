/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.exoplatform.session10tu;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by The eXo Platform SAS
 * Author : Anh-Tu Nguyen
 *          tuna@exoplatform.com
 * May 7, 2013  
 */
public class PhotoHandler implements PictureCallback {

  private final Context context;

  private static final String TAG = "PhotoHandler";
  
  public PhotoHandler(Context context) {
    this.context = context;
  }

  @Override
  public void onPictureTaken(byte[] data, Camera camera) {

    String date = (new SimpleDateFormat("yyyymmddhhmmss")).format(new Date());
    String photoFile = "Picture_" + date + ".jpg";

    // use app directory in favor of image directory, the latter raises Can't create directory error
    File pictureFile = new File(context.getFilesDir(), photoFile);

    try {
      FileOutputStream fos = new FileOutputStream(pictureFile);
      fos.write(data);
      fos.close();
      Toast.makeText(context, "New Image saved:" + photoFile,
          Toast.LENGTH_LONG).show();
    } catch (Exception error) {
      Log.i(TAG, "File" + photoFile + "not saved: "
          + error.getMessage());
      Toast.makeText(context, "Image could not be saved.",
          Toast.LENGTH_LONG).show();
    }
  }

}