package com.ascalonic.vigr;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PatientDetails extends AppCompatActivity{

    EditText txtPatientName, txtPatientDOB;
    Spinner spinGender;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        txtPatientName=(EditText)findViewById(R.id.editPatientName);
        txtPatientDOB=(EditText)findViewById(R.id.editPatientDOB);
        spinGender=(Spinner)findViewById(R.id.spinGender);

        setTitle("Patient Details");

        // Spinner Drop down elements
        ArrayList gender = new ArrayList<String>();

        // Creating adapter for spinner
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, gender);


        // Drop down layout style - list view with radio button
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinGender.setAdapter(genderAdapter);

        gender.add("Male"); gender.add("Female");

        genderAdapter.notifyDataSetChanged();

    }

    private void showDataEntryFailure(String msg)
    {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Invalid Entry");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                //Do Nothing
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public void confirmPatient(View v)
    {
        String name,dob,gender;

        name=txtPatientName.getText().toString();
        dob=txtPatientDOB.getText().toString();

        gender=spinGender.getSelectedItem().toString();

        if(name.equals("")||dob.equals(""))
        {
            showDataEntryFailure("Please fill all the fields. They are required");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

        try
        {
            Date d = dateFormat.parse(dob);
            Calendar c = Calendar.getInstance();
            Date curdate=c.getTime();

            if(curdate.after(d))
            {
                //Add events
                createAppointmentWithServer(name, dob, gender);
            }
            else
            {
                showDataEntryFailure("Please enter a valid date of birth");
                return;
            }

        }
        catch(ParseException pex)
        {
            showDataEntryFailure("Please enter a valid date");
            return;
        }
    }

    private void showAppoCreationStatus(boolean status)
    {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        String msg="";

        // Setting Dialog Title
        if(status) {
            alertDialog.setTitle("Appointment Created");
            msg = "Appointment with " + VigrDoctors.selectedDoc.getDocName() + " successfully created. We will add it to your calendar";
        }
        else {
            alertDialog.setTitle("Error Occurred");
            msg="There was an error creating Appointment. Please try again";
        }

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                //Do Nothing
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void createAppointmentWithServer(String patname, String patdob, String gender)
    {
        showAppoCreationProgress();

        AccessManager am=new AccessManager(PatientDetails.this,getString(R.string.preference_file));
        String phone = am.getPhone();
        String acctok = am.getAccessToken();

        SearchServerInterface SSI=new SearchServerInterface(getBaseContext(), new AsyncResponse() {
            @Override
            public void processFinish(String output) {

                Log.d("CONN_TEST","out:" + output);
                progressDialog.dismiss();

                try {

                    JSONObject res = (new JSONObject(output));
                    Boolean success = res.getBoolean("success");

                    showAppoCreationStatus(success);
                }
                catch(JSONException jex) {}


            }

        },3);

        Log.d("CONN_TEST",patdob);

        SSI.execute(phone, acctok, patname + " " + gender, patdob, MakeAppointment.selectedYear + "-" +
                MakeAppointment.selectedMonth + "-" + MakeAppointment.selectedDay, String.valueOf(VigrDoctors.selectedDoc.getDocID()));
    }


    private void showAppoCreationProgress()
    {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Creating Appointment...");
        progressDialog.show();
    }
}

