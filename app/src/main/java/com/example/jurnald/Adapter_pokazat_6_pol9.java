package com.example.jurnald;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_pokazat_6_pol9 extends BaseAdapter {
    ArrayList<Using_adapter_pokazat_6_pol9> uadp = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    Adapter_pokazat_6_pol9(Context context, ArrayList<Using_adapter_pokazat_6_pol9> uadp) {
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
        view = lInflater.inflate(R.layout.otobrajenie_izmenenie_studenta_list, parent, false);
        if (uadp.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.oi4p_id_inviz)).setText(uadp.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.oi4p_pervoe_pole)).setText(uadp.get(position).getName_1_pole());
        ((TextView) view.findViewById(R.id.oi4p_vtoroe_pole)).setText(uadp.get(position).getName_2_pole());
        ((TextView) view.findViewById(R.id.oi4p_tretie_pole)).setText(uadp.get(position).getName_3_pole());
        ((TextView) view.findViewById(R.id.oi4p_4tvertoe_pole)).setText(uadp.get(position).getName_4_pole());
        ((TextView) view.findViewById(R.id.oi4p_4tvertoe_pole)).setText(uadp.get(position).getName_5_pole());
        ((TextView) view.findViewById(R.id.oi4p_4tvertoe_pole)).setText(uadp.get(position).getName_6_pole());
        return view;
    }

    public void setDropDownViewResource(int simple_spinner_dropdown_item) {

    }
}