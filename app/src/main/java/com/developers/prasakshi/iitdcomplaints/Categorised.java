package com.developers.prasakshi.iitdcomplaints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Categorised extends AppCompatActivity {


    final ArrayList<String> compList = new ArrayList<>();
    String usertype, userhostel, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorised);

        Intent i = getIntent();
        usertype = i.getStringExtra("user_type");
        userhostel = i.getStringExtra("user_hostel");
        username = i.getStringExtra("user_name");

        RequestQueue ReqQ = Volley.newRequestQueue(getApplicationContext());


        String url = "";

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){
                Toast.makeText(Categorised.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray cArray = response.getJSONArray("complaints");
                    //temp.setText(Integer.toString(cArray.length()));
                    //temp.setText(cArray.getJSONObject(0).getString("name"));

                    for(int i=0; i<cArray.length(); i++){
                        String crs =cArray.getJSONObject(i).getString("id").toString() +".\t" +cArray.getJSONObject(i).getString("title");
                        compList.add(crs);


                    }
                    //temp.setText(courseList.get(0));


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(Categorised.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            //Creating a hashmap of the input values
            @Override

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usertype", usertype);
                params.put("userhostel", userhostel);

                return params;
            }

        };
        ReqQ.add(jsonObj);

    }

    }
}
