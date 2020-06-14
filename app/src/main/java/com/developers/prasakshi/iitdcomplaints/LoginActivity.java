package com.developers.prasakshi.iitdcomplaints;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.TextInputLayout;
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

public class LoginActivity extends AppCompatActivity {

    TextInputLayout Username, Password;
    String _Username, _Password;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = (TextInputLayout) findViewById(R.id.Username);
        Password = (TextInputLayout) findViewById(R.id.Password);
        Login = (Button) findViewById(R.id.signin);

        Login.setOnClickListener(new Button.OnClickListener() {

                                     public void onClick(View v) {
                                         try {
                                             if (v == Login) {
                                                 LoginPress();          //LogginPress is a new method mainly used for working of the Submit Button
                                             }
                                         } catch (Exception e) {

                                         }
                                     }
                                 }
        );
    }
    private void LoginPress() throws JSONException {             //Sends POST request for login and displays success or error accordingly
        _Username = Username.getEditText().getText().toString();
        _Password = Password.getEditText().getText().toString();

        RequestQueue ReqQ = Volley.newRequestQueue(this);
        String url = "";

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){
                Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    if(response.getString("success").equals("true")){
                        Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                        if(!response.getString("usertype").equals("admin")) {
                            Intent i = new Intent(getApplicationContext(), UserMainActivity.class);
                            i.putExtra("user_type", response.getString("usertype"));
                            i.putExtra("user_hostel", response.getString("userhostel"));
                            i.putExtra("user_name", _Username);
                            startActivity(i);
                            finish();
                        }
                        else{
                            startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                            finish();
                        }
                    }
                    else
                        Toast.makeText(LoginActivity.this, "Login Failed\nPlease check the details and try again.", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                if(error.toString().contains("com.android.volley.NoConnectionError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(LoginActivity.this);
                    blankname.setMessage("No Internet Connection available.\nPlease connect to network and try again.");
                    blankname.setCancelable(true);
                    AlertDialog dialogblank = blankname.create();
                    dialogblank.show();
                    dialogblank.setCanceledOnTouchOutside(true);
                }
                else if(error.toString().contains("TimeoutError")){
                    AlertDialog.Builder blankname = new AlertDialog.Builder(LoginActivity.this);
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
                params.put("username", _Username);
                params.put("password", _Password);

                return params;
            }

        };
        ReqQ.add(jsonObj);
    }

}
