package com.example.jurnald;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Smotret_Spisok_Posesheni9 extends AppCompatActivity {
    ArrayList<Using_adapter_3_string_dobavleni9> spisok_posesheni9;
    Adapter_3_string_dobavleni9 adapter_3_string_dobavleni9;
private  Toolbar toolbar;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokaz_array_list_posesheni9);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar_smotret_propyski);
        setSupportActionBar(toolbar);


        String name = getIntent().getStringExtra("name");
        String id_z = getIntent().getStringExtra("id_zapisi_jurnal");
        setTitle("Посещение: " + name);
        Spisok_Posesheni9(id_z);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Spisok_Posesheni9(String id_zapisi_jurnal){

        Cursor query;
        String propyski;
        spisok_posesheni9= new ArrayList<>();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        query=db.rawQuery("SELECT * FROM ficsation WHERE id_nomer_zapisi=?",new String[] {id_zapisi_jurnal});
        while (query.moveToNext())
        {
            if(query.getString(3).equals("1"))
                propyski = "Присутствовал";
            else
            {
                propyski="Отсутствовал";
            }
            spisok_posesheni9.add(new Using_adapter_3_string_dobavleni9(query.getInt(0),query.getString(2),query.getString(4),propyski));


        }
        query.close();
        db.close();

        ListView lv = (ListView) findViewById(R.id.array_list_posesheni9);
        adapter_3_string_dobavleni9 = new Adapter_3_string_dobavleni9(Smotret_Spisok_Posesheni9.this, spisok_posesheni9);
        lv.setAdapter(adapter_3_string_dobavleni9);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                for(Using_adapter_3_string_dobavleni9 x: spisok_posesheni9)
                {
                    if(x.getId().equals(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.op_id_posesheni9)).getText().toString())))
                    {

                        Redactirovanie_Posesheni9(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.op_id_posesheni9)).getText().toString()),x.getPole_1(),x.getPole_2(),x.getPole_3(),id_zapisi_jurnal);

                    }
                }


            }
        });




    }

    public void Redactirovanie_Posesheni9(Integer id,String data,String opis,String propysk,String id_zapisi_jurnal){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Smotret_Spisok_Posesheni9.this);
        subjectDialog.setTitle("Дата: " + data);
        subjectDialog.setCancelable(false);

        View vv = (ConstraintLayout) getLayoutInflater().inflate(R.layout.izmen_opisanie_data, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_opisanie);
        Button btn1 =(Button) vv.findViewById(R.id.btn_true_false);
        et1.setText(opis);
        if(propysk.equals("Присутствовал"))
        {
            btn1.setText("Присутствовал");
            btn1.setBackgroundColor(Color.parseColor("#60D814"));
        }
        else
        {
            btn1.setText("Отсутствовал");
            btn1.setBackgroundColor(Color.parseColor("#FFC7135B"));
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn1.getText().toString().equals("Присутствовал"))
                {
                    btn1.setText("Отсутствовал");
                    btn1.setBackgroundColor(Color.parseColor("#FFC7135B"));
                }
                else
                {
                    btn1.setText("Присутствовал");
                    btn1.setBackgroundColor(Color.parseColor("#60D814"));
                }


            }
        });
        subjectDialog.setView(vv);
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean pris;
                        ContentValues values = new ContentValues();
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        long id_zapisi ;
                        if(btn1.getText().toString().equals("Присутствовал"))
                            pris=true;

                        else
                            pris=false;
                        if(id>0) {
                            db.execSQL("PRAGMA foreign_keys=ON");
                            values.put("id_nomer_zapisi", id_zapisi_jurnal);
                            values.put("data_propyskov", data);
                            values.put("propyski", pris);
                            values.put("opisanie", et1.getText().toString());
                            id_zapisi = db.update("ficsation", values, "id = ?", new String[]{String.valueOf(id)});
                            if (id_zapisi != -1)
                                Toast.makeText(getApplicationContext(), "Изменения внесены", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        }
                        else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                        Spisok_Posesheni9(id_zapisi_jurnal);
                        db.close();
                    }})
                .setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        long id_zapisi = 0;
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        if (id > 0) {
                            db.execSQL("PRAGMA foreign_keys=ON");

                            try{
                                id_zapisi= db.delete("ficsation", "id = ?", new String[]{String.valueOf(id)});
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_LONG).show();
                            }

                            if(id_zapisi==1) Toast.makeText(getApplicationContext(),"Произошло удаление",Toast.LENGTH_LONG).show();
                            else  Toast.makeText(getApplicationContext(),"Ошибка удаления",Toast.LENGTH_LONG).show();

                        }
                        else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                        db.close();
                        Spisok_Posesheni9(id_zapisi_jurnal);


                    }



                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        subjectDialog.show();

    }





}
