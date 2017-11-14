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
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;


public class MakeAppointment extends AppCompatActivity {

    public static int selectedDay, selectedMonth, selectedYear;

    DatePicker dp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        setTitle("Appointment Details - Date");

        dp = (DatePicker) findViewById(R.id.appoDatePicker);
    }


    private void showAppoInpFailure(String msg) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Invalid Date");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Do Nothing
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public void selectDate(View v) {
        selectedDay = dp.getDayOfMonth();
        selectedMonth = dp.getMonth();
        selectedYear = dp.getYear();

        Calendar c = Calendar.getInstance();
        Date curdate = c.getTime();


        if (curdate.getDate() > selectedDay || curdate.getMonth() > selectedMonth || curdate.getYear() > selectedYear) {
            showAppoInpFailure("Please select a coming day");
        } else {
            Intent i = new Intent(getBaseContext(), PatientDetails.class);
            startActivity(i);
        }
    }
}


