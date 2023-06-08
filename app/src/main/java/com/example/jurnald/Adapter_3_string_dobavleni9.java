package com.example.jurnald;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_3_string_dobavleni9 extends BaseAdapter {
    ArrayList<Using_adapter_3_string_dobavleni9> fngs = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    Adapter_3_string_dobavleni9(Context context, ArrayList<Using_adapter_3_string_dobavleni9> fngs ) {
        this.ctx = context;
        //this.subjects = subjects;
        this.fngs.addAll(fngs);
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fngs.size();
    }

    @Override
    public Object getItem(int position) {
        return fngs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = lInflater.inflate(R.layout.otobrajenie_posesheni9, parent, false);
        if (fngs.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.op_id_posesheni9)).setText(fngs.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.op_data_posesheni9)).setText(fngs.get(position).getPole_1());
        ((TextView) view.findViewById(R.id.op_poseshenie)).setText(fngs.get(position).getPole_3());
        ((TextView) view.findViewById(R.id.op_opisanie)).setText(fngs.get(position).getPole_2());
        if(fngs.get(position).getPole_3().equals("Присутствовал"))
            ((TextView) view.findViewById(R.id.op_poseshenie)).setBackgroundColor(Color.parseColor("#60D814"));
        else if(fngs.get(position).getPole_3().equals("Отсутствовал"))
            ((TextView) view.findViewById(R.id.op_poseshenie)).setBackgroundColor(Color.parseColor("#FFC7135B"));
        //((TextView) view.findViewById(R.id.odp_ficsation)).setTextColor(Color.RED);
        return view;
    }
}