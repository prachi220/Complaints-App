package com.developers.prasakshi.iitdcomplaints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageActivity extends AppCompatActivity {
    Button addrem, changeauth, remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        addrem = (Button) findViewById(R.id.addrembtn);
        remove = (Button) findViewById(R.id.removebtn);
        changeauth = (Button) findViewById(R.id.changeauthbtn);

        addrem.setOnClickListener(new Button.OnClickListener() {

                                         public void onClick(View v) {
                                             try {
                                                 if (v == addrem) {
                                                     startActivity(new Intent(getApplicationContext(), AddremActivity.class));
                                                 }
                                             } catch (Exception e) {

                                             }
                                         }
                                     }
        );

        remove.setOnClickListener(new Button.OnClickListener() {

                                      public void onClick(View v) {
                                          try {
                                              if (v == remove) {
                                                  startActivity(new Intent(getApplicationContext(), RemoveActivity.class));
                                              }
                                          } catch (Exception e) {

                                          }
                                      }
                                  }
        );

        changeauth.setOnClickListener(new Button.OnClickListener() {

                                          public void onClick(View v) {
                                              try {
                                                  if (v == changeauth) {
                                                      startActivity(new Intent(getApplicationContext(), ChangeAuthActivity.class));
                                                  }
                                              } catch (Exception e) {

                                              }
                                          }
                                      }
        );

    }
}
