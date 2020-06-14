package com.developers.prasakshi.iitdcomplaints;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;

public class UserMainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    String username, usertype, userhostel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        Intent i = getIntent();
        // getting attached intent data
        username = i.getStringExtra("user_name");
        usertype = i.getStringExtra("user_type");
        userhostel = i.getStringExtra("user_hostel");

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_user_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    @SuppressLint("ValidFragment")
    public class ComplaintsFrag extends Fragment {                  //assignment fragment
        final ArrayList<String> compList = new ArrayList<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            RequestQueue ReqQ = Volley.newRequestQueue(getApplicationContext());


            String url = "";

            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>(){

                @Override
                public void onResponse(JSONObject response){
                    Toast.makeText(UserMainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(UserMainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.frag_complaintList, container,
                    false);


            final Button forDepartmentalList = (Button)rootView.findViewById(R.id.specialDepButton);

            if(usertype.equals("normal")){
               forDepartmentalList.setClickable(false);
            }
            else{
                forDepartmentalList.setClickable(true);
            }
            forDepartmentalList.setOnClickListener(new Button.OnClickListener() {

                                                       public void onClick(View v) {
                                                           try {
                                                               if (v == forDepartmentalList) {
                                                                   Intent i = new Intent(getApplicationContext(), Categorised.class);
                                                                   i.putExtra("user_type", usertype);
                                                                   i.putExtra("user_hostel", userhostel);
                                                                   i.putExtra("user_name", username);
                                                                   startActivity(i);
                                                                   finish();
                                                               }
                                                           } catch (Exception e) {

                                                           }
                                                       }
                                                   }
            );

            ListView list = (ListView)rootView.findViewById(R.id.comp_list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item2, R.id.txtitem2, assnList) ;

            list.setAdapter(adapter) ;
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //String course_name = ((TextView) view).getText().toString();
                    //DummyContent course = (DummyContent) parent.getItemAtPosition(position);
                    //final Context context = getActivity().getApplicationContext();
                    //final int duration = Toast.LENGTH_SHORT;
                    //CharSequence text = "clicked!!";
                    //Toast toast = Toast.makeText(context, text, duration);
                    //toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    //toast.show();
                    View v = ((LinearLayout) view).getChildAt(0);
                    String complaintid = ((TextView) v).getText().toString().substring(0, 1);
                    //System.out.println(complaintid);
                    Intent intent = new Intent(getActivity(), ComplaintActivity.class);
                    intent.putExtra("compid", complaintid);//check here
                    startActivity(intent);
                }
            });
            return rootView;
        }
    }


    @SuppressLint("ValidFragment")
    public class SubmissionFrag extends Fragment {             //threads fragment class


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.frag_submission, container,
                    false);

            TextInputLayout title = (TextInputLayout)rootView.findViewById(R.id.compTitle);
            TextInputLayout threaddetail = (TextInputLayout)rootView.findViewById(R.id.compDetail);
            final TextInputLayout department = (TextInputLayout)rootView.findViewById(R.id.compDep);
            final Spinner level = (Spinner)rootView.findViewById(R.id.levelsel);

            // Spinner click listener
            level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    Object item = arg0.getItemAtPosition(arg2);
                    if (item!=null) {
                        Toast.makeText(UserMainActivity.this, item.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(UserMainActivity.this, "Selected",
                            Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });

            // Spinner Drop down elements
            List<String> categories = new ArrayList<String>();
            categories.add("Individual");
            categories.add("Hostel/Office");
            categories.add("Institute");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(UserMainActivity.this, android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            level.setAdapter(dataAdapter);

            final String _title = title.getEditText().getText().toString();
            final String _thread = threaddetail.getEditText().getText().toString();
            Button submit = (Button)rootView.findViewById(R.id.submitButton);

            RequestQueue ReqQ = Volley.newRequestQueue(getApplicationContext());


            String url = "";
            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>(){

                @Override
                public void onResponse(JSONObject response){
                    Toast.makeText(UserMainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(UserMainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                //Creating a hashmap of the input values
                @Override

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("title", _title);
                    params.put("detail", _thread);
                    params.put("department", department.getEditText().getText().toString());
                    params.put("level", level.getSelectedItem().toString());

                    return params;
                }

            };
            ReqQ.add(jsonObj);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue ReqQ = Volley.newRequestQueue(getApplicationContext());
                    String url = "";

                    JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String toastStr = null;
                            try {
                                toastStr = response.getString("success");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (toastStr.equals(true)) {
                                Toast.makeText(UserMainActivity.this, "Complaint Successfully Posted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserMainActivity.this, "Post Unsuccessful", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(UserMainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                    ReqQ.add(jsonObj);
                }
            });





            return rootView;
        }


    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    //page 1
                    return new ComplaintsFrag();

                case 1:
                    //page 2
                    return new SubmissionFrag();
                default:
                    //this page does not exists
                    //return null;
                    return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Complaints List";
                case 1:
                    return "Submit complaint";
            }
            return null;
        }
    }
}
