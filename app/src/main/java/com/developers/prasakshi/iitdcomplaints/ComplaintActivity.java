package com.developers.prasakshi.iitdcomplaints;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ComplaintActivity extends AppCompatActivity {

    String compid;
    TextView compTitle, compDetail, usercomp, status;
    Button upvote, downvote, resolved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        Intent i = getIntent();
        compid = i.getStringExtra("compid");

        usercomp = (TextView) findViewById(R.id.usernametxt);
        compTitle = (TextView) findViewById(R.id.titletxt);
        compDetail = (TextView) findViewById(R.id.detailtxt);
        status = (TextView) findViewById(R.id.statustxt);
        upvote = (Button) findViewById(R.id.upbtn);
        downvote = (Button) findViewById(R.id.dnbtn);
        resolved = (Button) findViewById(R.id.resolvebtn);

        resolved.setClickable(true);

        RequestQueue ReqQ = Volley.newRequestQueue(this);
        String url = "";

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){
                Toast.makeText(ComplaintActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    compTitle.setText(response.getString("title"));
                    compDetail.setText(response.getString("detail"));
                    status.setText(response.getString("status"));
                    usercomp.setText(response.getString("user"));

                    if(response.getString("level").equals("hostel")){
                        if(!response.getString("usertype").equals("warden")){
                            resolved.setClickable(false);
                        }

                    }
                    if(response.getString("level").equals("institute")){
                        if(!response.getString("usertype").equals("dean")){
                            resolved.setClickable(false);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(ComplaintActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                if(error.toString().contains("com.android.volley.NoConnectionError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(ComplaintActivity.this);
                    blankname.setMessage("No Internet Connection available.\nPlease connect to network and try again.");
                    blankname.setCancelable(true);
                    AlertDialog dialogblank = blankname.create();
                    dialogblank.show();
                    dialogblank.setCanceledOnTouchOutside(true);
                }
                else if(error.toString().contains("TimeoutError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(ComplaintActivity.this);
                    blankname.setMessage("Network Timeout. Please check your network connection and try again");
                    blankname.setCancelable(true);
                    AlertDialog dialogblank = blankname.create();
                    dialogblank.show();
                    dialogblank.setCanceledOnTouchOutside(true);
                }
            }
        }){
            //Creating a hashmap of the input values
            @Override

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("complaintid", compid);

                return params;
            }

        };
        ReqQ.add(jsonObj);




        resolved.setOnClickListener(new Button.OnClickListener() {

                                        public void onClick(View v) {
                                            try {
                                                if (v == resolved) {
                                                    resolvedPress();          //LogginPress is a new method mainly used for working of the Submit Button
                                                }
                                            } catch (Exception e) {

                                            }
                                        }
                                    }
        );

        upvote.setOnClickListener(new Button.OnClickListener() {

                                     public void onClick(View v) {
                                         try {
                                             if (v == upvote) {
                                                 upvotePress();          //LogginPress is a new method mainly used for working of the Submit Button
                                             }
                                         } catch (Exception e) {

                                         }
                                     }
                                 }
        );

        downvote.setOnClickListener(new Button.OnClickListener() {

                                      public void onClick(View v) {
                                          try {
                                              if (v == downvote) {
                                                  downvotePress();          //LogginPress is a new method mainly used for working of the Submit Button
                                              }
                                          } catch (Exception e) {

                                          }
                                      }
                                  }
        );
    }

    private void resolvedPress() throws JSONException{
        resolved.setClickable(false);

        RequestQueue ReqQ = Volley.newRequestQueue(this);
        String url = "";

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){
                Toast.makeText(ComplaintActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    if(response.getString("success").equals("true")){
                        Toast.makeText(ComplaintActivity.this, "Successfully resolved", Toast.LENGTH_LONG).show();

                    }
                    else
                        Toast.makeText(ComplaintActivity.this, "Connection Failed\nPlease try again.", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(ComplaintActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                if(error.toString().contains("com.android.volley.NoConnectionError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(ComplaintActivity.this);
                    blankname.setMessage("No Internet Connection available.\nPlease connect to network and try again.");
                    blankname.setCancelable(true);
                    AlertDialog dialogblank = blankname.create();
                    dialogblank.show();
                    dialogblank.setCanceledOnTouchOutside(true);
                }
                else if(error.toString().contains("TimeoutError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(ComplaintActivity.this);
                    blankname.setMessage("Network Timeout. Please check your network connection and try again");
                    blankname.setCancelable(true);
                    AlertDialog dialogblank = blankname.create();
                    dialogblank.show();
                    dialogblank.setCanceledOnTouchOutside(true);
                }
            }
        }){
            //Creating a hashmap of the input values
            @Override

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("complaintid", compid);

                return params;
            }

        };
        ReqQ.add(jsonObj);

    }

    private void upvotePress() throws JSONException{
        upvote.setClickable(false);

        RequestQueue ReqQ = Volley.newRequestQueue(this);
        String url = "";

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){
                Toast.makeText(ComplaintActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    if(response.getString("success").equals("true")){
                        Toast.makeText(ComplaintActivity.this, "Successfully upvoted", Toast.LENGTH_LONG).show();

                    }
                    else
                        Toast.makeText(ComplaintActivity.this, "Some Error occured.\nPlease try again.", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(ComplaintActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                if(error.toString().contains("com.android.volley.NoConnectionError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(ComplaintActivity.this);
                    blankname.setMessage("No Internet Connection available.\nPlease connect to network and try again.");
                    blankname.setCancelable(true);
                    AlertDialog dialogblank = blankname.create();
                    dialogblank.show();
                    dialogblank.setCanceledOnTouchOutside(true);
                }
                else if(error.toString().contains("TimeoutError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(ComplaintActivity.this);
                    blankname.setMessage("Network Timeout. Please check your network connection and try again");
                    blankname.setCancelable(true);
                    AlertDialog dialogblank = blankname.create();
                    dialogblank.show();
                    dialogblank.setCanceledOnTouchOutside(true);
                }
            }
        }){
            //Creating a hashmap of the input values
            @Override

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("complaintid", compid);

                return params;
            }

        };
        ReqQ.add(jsonObj);

    }

    private void downvotePress() throws JSONException{
        resolved.setClickable(false);

        RequestQueue ReqQ = Volley.newRequestQueue(this);
        String url = "";

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){
                Toast.makeText(ComplaintActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    if(response.getString("success").equals("true")){
                        Toast.makeText(ComplaintActivity.this, "Successfully downvoted", Toast.LENGTH_LONG).show();

                    }
                    else
                        Toast.makeText(ComplaintActivity.this, "Downvote Failed\nPlease try again.", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(ComplaintActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                if(error.toString().contains("com.android.volley.NoConnectionError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(ComplaintActivity.this);
                    blankname.setMessage("No Internet Connection available.\nPlease connect to network and try again.");
                    blankname.setCancelable(true);
                    AlertDialog dialogblank = blankname.create();
                    dialogblank.show();
                    dialogblank.setCanceledOnTouchOutside(true);
                }
                else if(error.toString().contains("TimeoutError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(ComplaintActivity.this);
                    blankname.setMessage("Network Timeout. Please check your network connection and try again");
                    blankname.setCancelable(true);
                    AlertDialog dialogblank = blankname.create();
                    dialogblank.show();
                    dialogblank.setCanceledOnTouchOutside(true);
                }
            }
        }){
            //Creating a hashmap of the input values
            @Override

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("complaintid", compid);

                return params;
            }

        };
        ReqQ.add(jsonObj);

    }

}

