package com.example.jurnald;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_pokazat_daty_propyskov extends BaseAdapter {
    ArrayList<Using_adapter_pokazat_daty_propyskov> uadp = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    Adapter_pokazat_daty_propyskov(Context context, ArrayList<Using_adapter_pokazat_daty_propyskov> uadp) {
        this.ctx = context;
        //this.subjects = subjects;
        this.uadp.addAll(uadp);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return uadp.size();
    }

    @Override
    public Object getItem(int position) {
        return uadp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.otobrajenie_daty_propyskov, parent, false);
        if (uadp.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.odp_id)).setText(uadp.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.odp_id_zapisi_jurnala)).setText(uadp.get(position).getId_zapisi_jurnala().toString());
        ((TextView) view.findViewById(R.id.odp_fio_stud)).setText(uadp.get(position).getFio_stud());
        ((TextView) view.findViewById(R.id.odp_opis)).setText(uadp.get(position).getOpis());
        ((TextView) view.findViewById(R.id.odp_ficsation)).setText(uadp.get(position).getFicsation());
        ((TextView) view.findViewById(R.id.odp_data)).setText(uadp.get(position).getData());

       if(uadp.get(position).getFicsation().equals("Присутствовал")){
           ((TextView) view.findViewById(R.id.odp_ficsation)).setBackgroundColor(Color.parseColor("#60D814"));
       }
       else if(uadp.get(position).getFicsation().equals("Отсутствовал")){
           ((TextView) view.findViewById(R.id.odp_ficsation)).setBackgroundColor(Color.parseColor("#FFC7135B"));
       }
       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(uadp.get(position).getFicsation().equals("Присутствовал"))
               {
                   //Log.i("Mainn"," "+uadp.get(position).getFicsation());
                   uadp.get(position).setFicsation("Отсутствовал");
                   ((TextView) view.findViewById(R.id.odp_ficsation)).setBackgroundColor(Color.parseColor("#FFC7135B"));
                   ((TextView) view.findViewById(R.id.odp_ficsation)).setText("Отсутствовал");
                  // Log.i("Mainn"," "+uadp.get(position).getFicsation());

               }
               else
               {
                   uadp.get(position).setFicsation("Присутствовал");
                   ((TextView) view.findViewById(R.id.odp_ficsation)).setBackgroundColor(Color.parseColor("#60D814"));
                   ((TextView) view.findViewById(R.id.odp_ficsation)).setText("Присутствовал");

               }
           }
       });
           //((TextView) view.findViewById(R.id.odp_ficsation)).setTextColor(Color.RED);

        return view;


    }

    public void setDropDownViewResource(int simple_spinner_dropdown_item) {

    }
}