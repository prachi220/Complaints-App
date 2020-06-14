package com.developers.prasakshi.iitdcomplaints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMainActivity extends AppCompatActivity {
    Button manageSec, complaintsSec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        manageSec = (Button) findViewById(R.id.manageBtn);
        complaintsSec = (Button) findViewById(R.id.compSecBtn);

        manageSec.setOnClickListener(new Button.OnClickListener() {

                                        public void onClick(View v) {
                                            try {
                                                if (v == manageSec) {
                                                    startActivity(new Intent(getApplicationContext(), ManageActivity.class));
                                                }
                                            } catch (Exception e) {

                                            }
                                        }
                                    }
        );

        complaintsSec.setOnClickListener(new Button.OnClickListener() {

                                        public void onClick(View v) {
                                            try {
                                                if (v == complaintsSec) {
                                                    startActivity(new Intent(getApplicationContext(), ComplaintActivity.class));
                                                }
                                            } catch (Exception e) {

                                            }
                                        }
                                    }
        );

    }
}
