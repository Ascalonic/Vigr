package com.ascalonic.vigr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VigrDoctors extends AppCompatActivity {

    public static List<VigrDoctor> docslist;
    private RecyclerView recyclerView;
    private DoctorAdapter docAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigr_doctors);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_docs);

        docAdapter = new DoctorAdapter(docslist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(docAdapter);
    }
}
