package com.ascalonic.vigr;

/**
 * Created by HP on 12-11-2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    private List<VigrDoctor> docslist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView doc_name, spec_name, hosp_name, place_name;

        public MyViewHolder(View view) {
            super(view);
            doc_name = (TextView) view.findViewById(R.id.textDocName);
            spec_name = (TextView) view.findViewById(R.id.textSpecName);
            hosp_name = (TextView) view.findViewById(R.id.textHospName);
            place_name = (TextView) view.findViewById(R.id.textPlaceName);
        }
    }


    public DoctorAdapter(List<VigrDoctor> docslist) {
        this.docslist = docslist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doc_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VigrDoctor doctor = docslist.get(position);
        holder.doc_name.setText(doctor.getDocName());
        holder.spec_name.setText(doctor.getSpecName());
        holder.hosp_name.setText(doctor.getHospName());
        holder.place_name.setText(doctor.getPlace());
    }

    @Override
    public int getItemCount() {
        return docslist.size();
    }
}