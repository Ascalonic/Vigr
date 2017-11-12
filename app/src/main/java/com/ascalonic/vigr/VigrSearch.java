package com.ascalonic.vigr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VigrSearch extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ProgressDialog progressDialog;

    Spinner spinPlace,spinSpecs;
    public static String selectedPlace,selectedSpec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigr_search);

        setTitle("Search for Doctor");
    }

    private void showFetchProgress()
    {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Getting Ready...");
        progressDialog.show();
    }

    private void showSearchProgress()
    {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Searching for doctors...");
        progressDialog.show();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        showFetchProgress();

        SearchServerInterface SSI=new SearchServerInterface(getBaseContext(), new AsyncResponse() {
            @Override
            public void processFinish(String output) {

                Log.d("CONN_TEST","out:" + output);

                progressDialog.dismiss();

                // Spinner element
                spinSpecs = (Spinner) findViewById(R.id.spinSpecs);

                // Spinner click listener
                spinSpecs.setOnItemSelectedListener(VigrSearch.this);

                // Spinner Drop down elements
                List<String> specs = new ArrayList<String>();

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, specs);


                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinSpecs.setAdapter(dataAdapter);

                try
                {
                    JSONArray json = new JSONArray(output);

                    for(int i=0;i<json.length();i++){

                        JSONObject e = json.getJSONObject(i);

                        String spec_name = e.getString("spec_name");
                        specs.add(spec_name);

                    }
                }
                catch(JSONException jex)
                {
                    //nothin' for now
                }

                selectedSpec = specs.get(0);

                dataAdapter.notifyDataSetChanged();

                showFetchProgress();

                SearchServerInterface SSI2=new SearchServerInterface(getBaseContext(), new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {

                        Log.d("CONN_TEST","out:" + output);

                        progressDialog.dismiss();

                        // Spinner element
                        spinPlace = (Spinner) findViewById(R.id.spinPlaces);

                        // Spinner click listener
                        spinPlace.setOnItemSelectedListener(VigrSearch.this);

                        // Spinner Drop down elements
                        List<String> places = new ArrayList<String>();

                        // Creating adapter for spinner
                        ArrayAdapter<String> placesAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, places);


                        // Drop down layout style - list view with radio button
                        placesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        spinPlace.setAdapter(placesAdapter);

                        try
                        {
                            JSONArray json = new JSONArray(output);

                            for(int i=0;i<json.length();i++){

                                JSONObject e = json.getJSONObject(i);

                                String place_name = e.getString("dist_name");
                                places.add(place_name);

                            }
                        }
                        catch(JSONException jex)
                        {
                            //nothin' for now
                        }

                        selectedPlace = places.get(0);

                        placesAdapter.notifyDataSetChanged();

                    }

                },1);

                SSI2.execute();

            }

        },0);

        SSI.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        if(view==spinPlace)
        {
            selectedPlace=item;
        }
        else if(view==spinSpecs)
        {
            selectedSpec=item;
        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void searchDoctor(View v)
    {
        showSearchProgress();

        SearchServerInterface SSI=new SearchServerInterface(getBaseContext(), new AsyncResponse() {
            @Override
            public void processFinish(String output) {

                Log.d("CONN_TEST","out:" + output);

                VigrDoctors.docslist = new ArrayList<>();

                progressDialog.dismiss();

                try
                {
                    JSONArray json = new JSONArray(output);

                    for(int i=0;i<json.length();i++){

                        JSONObject e = json.getJSONObject(i);

                        int doc_id = e.getInt("doc_id");
                        String doc_name = e.getString("doc_name");
                        String hosp_name = e.getString("hosp_name");
                        String place = e.getString("dist_name");
                        String spec_name = selectedSpec;

                        VigrDoctor vd=new VigrDoctor(doc_id,doc_name,hosp_name,spec_name,place);
                        VigrDoctors.docslist.add(vd);
                    }
                }
                catch(JSONException jex)
                {
                    //nothin' for now
                }

                Intent i=new Intent(getBaseContext(), VigrDoctors.class);
                startActivity(i);

            }

        },2);

        SSI.execute(selectedPlace,selectedSpec);
    }

}
