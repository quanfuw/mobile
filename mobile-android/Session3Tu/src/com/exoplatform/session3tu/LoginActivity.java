/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
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
package com.exoplatform.session3tu;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This represents the main screeen for the application with a login form 
 * and a Connect button
 * 
 * Created by The eXo Platform SAS
 * <br/>Author : Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com</a>
 * <br/>Nov 12, 2012  
 */
public class LoginActivity extends Activity
{
  
  @Override
  public void onCreate(Bundle bundle)
  {
    super.onCreate(bundle);    
    setContentView(R.layout.loginactivity);
  }
  
  /**
   * handle onClick event for the "buttonConnecteXo"
   * 
   * @param view 
   */
  public void onClickConnectToExo(View view)
  {
    Intent loginIntent = new Intent();
    loginIntent.setAction("com.exoplatform.intent.connectToExo");
    
    EditText usernameText = (EditText) findViewById(R.id.editUsername);
    EditText passwordText = (EditText) findViewById(R.id.editPassword);
    TextView loginMessage = (TextView) findViewById(R.id.loginMessage);
    
    if (!isPasswordCorrect(usernameText.getText().toString(), passwordText.getText().toString()))
    {
      loginMessage.setText("username or password incorrect");
      loginMessage.setVisibility(View.VISIBLE);
      return;
    }
    
    loginMessage.setVisibility(View.INVISIBLE);
    loginIntent.putExtra("username",  usernameText.getText());
    // start HomeActivity - sending username in the Intent
    startActivity(loginIntent);
  }
  
  /**
   * check whether the pair username, password is correct
   * 
   * @param username
   * @param password
   * @return true or false
   */
  private boolean isPasswordCorrect(String username, String password) 
  {
    Map<String, String> passwordMap = new HashMap<String, String>();
    passwordMap.put("root", "gtn");
    passwordMap.put("john", "gtn");
    
    if (!passwordMap.containsKey(username)) return false;
    if (!passwordMap.get(username).equals(password)) return false;
    return true;
  }
}
