package com.developers.prasakshi.iitdcomplaints;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RemoveActivity extends AppCompatActivity {
    TextInputLayout userIdToRem;
    Button remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        userIdToRem = (TextInputLayout) findViewById(R.id.userremid);
        remove = (Button) findViewById(R.id.removeuserBtn);

        remove.setOnClickListener(new Button.OnClickListener() {

                                     public void onClick(View v) {
                                         try {
                                             if (v == remove) {
                                                 removePress();          //LogginPress is a new method mainly used for working of the Submit Button
                                             }
                                         } catch (Exception e) {

                                         }
                                     }
                                 }
        );

    }

    private void removePress() throws JSONException {             //Sends POST request for login and displays success or error accordingly

        RequestQueue ReqQ = Volley.newRequestQueue(this);
        String url = "";

        final String _userid  = userIdToRem.getEditText().getText().toString();

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){
                Toast.makeText(RemoveActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    if(response.getString("success").equals("true")){
                        Toast.makeText(RemoveActivity.this, "Successfully Removed", Toast.LENGTH_LONG).show();

                    }
                    else
                        Toast.makeText(RemoveActivity.this, "An Error Occured\nPlease check the details and try again.", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(RemoveActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                if(error.toString().contains("com.android.volley.NoConnectionError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(RemoveActivity.this);
                    blankname.setMessage("No Internet Connection available.\nPlease connect to network and try again.");
                    blankname.setCancelable(true);
                    AlertDialog dialogblank = blankname.create();
                    dialogblank.show();
                    dialogblank.setCanceledOnTouchOutside(true);
                }
                else if(error.toString().contains("TimeoutError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(RemoveActivity.this);
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
                params.put("userid", _userid);

                return params;
            }

        };
        ReqQ.add(jsonObj);
    }
}
