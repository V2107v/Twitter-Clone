/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity {
  EditText usernameText;
  EditText passwordText;

  public void redirectUsers () {
    if(ParseUser.getCurrentUser() != null) {
      Intent intent = new Intent(this,UsersActivity.class);
      startActivity(intent);
    }
  }

  public void onClick (View view) {
    usernameText = findViewById(R.id.username);
    passwordText = findViewById(R.id.password);

    ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if(e == null) {
          Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
          redirectUsers();
        } else {
          ParseUser newUser = new ParseUser();
          newUser.setUsername(usernameText.getText().toString());
          newUser.setPassword(passwordText.getText().toString());
          newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
              if(e == null) {
                Toast.makeText(MainActivity.this, "Sign up Successful!", Toast.LENGTH_SHORT).show();
                redirectUsers();
              } else {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
              }
            }
          });
        }
      }
    });
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("SignUp/Login");
    redirectUsers();

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}