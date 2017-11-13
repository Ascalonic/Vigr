package com.ascalonic.vigr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VigrDoctors extends AppCompatActivity {

    public static List<VigrDoctor> docslist;
    private RecyclerView recyclerView;
    private DoctorAdapter docAdapter;

    public static VigrDoctor selectedDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigr_doctors);

        setTitle("Vigr - Search Results");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_docs);

        docAdapter = new DoctorAdapter(docslist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(docAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                selectedDoc = docslist.get(position);

                Intent i=new Intent(getBaseContext(),MakeAppointment.class);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
