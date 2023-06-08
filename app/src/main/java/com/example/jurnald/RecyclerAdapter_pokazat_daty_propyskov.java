package com.example.jurnald;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import java.util.List;

public class RecyclerAdapter_pokazat_daty_propyskov  extends RecyclerView.Adapter<RecyclerAdapter_pokazat_daty_propyskov.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Using_adapter_pokazat_daty_propyskov> states;

    RecyclerAdapter_pokazat_daty_propyskov(Context context, List<Using_adapter_pokazat_daty_propyskov> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerAdapter_pokazat_daty_propyskov.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.otobrajenie_daty_propyskov, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Using_adapter_pokazat_daty_propyskov state = states.get(position);
        holder.opd_view_id.setText(state.getId().toString());
        holder.opd_view_id_zapisi_jurnala.setText(state.getId_zapisi_jurnala().toString());
        holder.opd_view_fio_stud.setText(state.getFio_stud());
        holder.opd_view_opis.setText(state.getOpis());
        holder.opd_view_ficsation.setText(state.getFicsation());
        holder.opd_view_data.setText(state.getData());
        if(state.getFicsation().equals("Присутствовал"))
            holder.opd_view_ficsation.setBackgroundColor(Color.parseColor("#60D814"));
        else if(state.getFicsation().equals("Отсутствовал"))
            holder.opd_view_ficsation.setBackgroundColor(Color.parseColor("#FFC7135B"));
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView opd_view_id,opd_view_id_zapisi_jurnala,opd_view_fio_stud,opd_view_opis,opd_view_ficsation,opd_view_data;
        ViewHolder(View view){
            super(view);
            opd_view_id=view.findViewById(R.id.odp_id);
            opd_view_id_zapisi_jurnala= view.findViewById(R.id.odp_id_zapisi_jurnala);
            opd_view_fio_stud=view.findViewById(R.id.odp_fio_stud);
            opd_view_opis=view.findViewById(R.id.odp_opis);
            opd_view_ficsation=view.findViewById(R.id.odp_ficsation);
            opd_view_data=view.findViewById(R.id.odp_data);
        }
    }
}