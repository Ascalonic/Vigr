package com.ascalonic.vigr;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PatientDetails extends AppCompatActivity{

    EditText txtPatientName, txtPatientDOB;
    Spinner spinGender;

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
                //Add event---------------------------------------------------------------------
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, "Calender Test");
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Trivandrum");
                intent.putExtra(CalendarContract.Events.DESCRIPTION, "Appointment Test");

                // Setting dates
                GregorianCalendar calDate = new GregorianCalendar(MakeAppointment.selectedYear,MakeAppointment.selectedMonth,MakeAppointment.selectedDay);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        calDate.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        calDate.getTimeInMillis());

                // make it a full day event
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

                // Making it private and shown as busy
                intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
                intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

                startActivity(intent);

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
}

