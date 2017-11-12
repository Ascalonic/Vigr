package com.ascalonic.vigr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class VigrConsole extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigr_console);

        setTitle("What would you like to do?");
    }

    public void makeAppointment(View v)
    {
        Intent i=new Intent(this, VigrSearch.class);
        startActivity(i);
    }
}
