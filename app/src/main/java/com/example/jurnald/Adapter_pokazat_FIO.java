package com.example.jurnald;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_pokazat_FIO extends BaseAdapter {
    ArrayList<Using_adapter_pokazat_FIO> uadp = new ArrayList<>();
    Context ctx;
    LayoutInflater lInflater;

    Adapter_pokazat_FIO(Context context, ArrayList<Using_adapter_pokazat_FIO> uadp) {
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
        view = lInflater.inflate(R.layout.otobrajenie_fio, parent, false);
        if (uadp.isEmpty()) return view;
        ((TextView) view.findViewById(R.id.otobr_id_scritoe)).setText(uadp.get(position).getId().toString());
        ((TextView) view.findViewById(R.id.otobr_F)).setText(uadp.get(position).getName_pervoe_pole());
        ((TextView) view.findViewById(R.id.otobr_I)).setText(uadp.get(position).getName_vtoroe_pole());
        ((TextView) view.findViewById(R.id.otobr_O)).setText(uadp.get(position).getName_tretie_pole());
        return view;
    }

    public void setDropDownViewResource(int simple_spinner_dropdown_item) {

    }
}
