package com.example.jurnald;

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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Smotret_Jurnal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ArrayList<Using_adater_dobavlenie> spisok,spisok_1_pol9;
    Adapter_dobavlenie adapter_dobavlenie;

    ArrayList<Using_adapter_dobavleni9_studenta> spisok_studenta;
    Adapter_dobavleni9_studenta adapter_dobavleni9_studenta;

    ArrayList<Using_adapter_pokazat_daty_propyskov> spisok_pokaz_daty_prop,spisok_pokaz_daty_prop1;
    Adapter_pokazat_daty_propyskov adapter_pokazat_daty_propyskov,adapter_pokazat_daty_propyskov1 ;
    RecyclerAdapter_pokazat_daty_propyskov recycleradapter_pokazat_daty_propyskov;

    ArrayList<Using_adapter_dobavleni9_predmeta> spisok_predmeta,spisol_2_pol9,spisok_2_pol9_2;
    Adapter_dobavleni9_predmeta adapter_dobavleni9_predmeta;

    Adapter_pokazat_3_pol9 adapter_pokazat_3_pol9;
    ArrayList<Using_adapter_pokazat_3_pol9> spisok_3_pol9,spisok_3_pol9_3;

    Adapter_pokazat_4_pol9 adapter_pokazat_4_pol9;
    ArrayList<Using_adapter_pokazat_4_pol9> spisok_4_pol9;

    ArrayList<Using_adapter_dobavleni9_5id> spisok_5id;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_smotret_ficsation);
        SpinnerJurnala();


        // Текущее время
        Date currentDate = new Date();
// Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        EditText et1 = (EditText) findViewById(R.id.editTextDate);
        et1.setText(dateText);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar_smotret_propyski);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout_ficsation);
        NavigationView navigationView = findViewById(R.id.nav_view_bokovoe_okno_ficsation);

        navigationView.setNavigationItemSelectedListener(this);
        //Копочка для выдвижения меню слева
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    //При нажатии выбираем меню слева и взависимости что выбрали выполняется
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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Intent intent;
        switch (id){
            case R.id.nav_ficsation:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this,Smotret_Jurnal.class);
                finish();
                startActivity(intent);
                Toast.makeText(this, "Фиксация", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_smotret_propyski:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this,Smotret_Propyski.class);
                finish();
                startActivity(intent);
                Toast.makeText(this, "Посещение", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_poseshenie_dom:
                drawer.closeDrawer(GravityCompat.START);
                finish();
                Toast.makeText(this, "Главное меню", Toast.LENGTH_SHORT).show();
                break;

        }
        //После выбора элемента меню закрывает меню
//drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void SpinnerJurnala(){
        List<Adapter_odnogo_dobavleni9> prepod_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> predmet_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> fng_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> fng_name2 = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> spinner_pokaz = new ArrayList<Adapter_odnogo_dobavleni9>();
        Set<Integer> fng_name1 = new HashSet<Integer>();
        final Spinner sp1 = (Spinner) findViewById(R.id.spinner_vibor_vsego);
        List<Adapter_tri_dobavleni9> jurnal_id_name = new ArrayList<Adapter_tri_dobavleni9>();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_Prepod,query_Predmet,query_D,query_Fk,query_S,query_F,query_N,query_G,query_SJ,query_1,query_2,query_3;
        query=db.rawQuery("SELECT distinct R.id,P.id,F.id,N.id,G.id," +
                "R.last_name, R.first_name,R.middle_name," +
                "P.name_premeta,FK.vid_formi," +
                "G.name_group,N.name_napravlenie,F.name_faculteta" +
                " FROM jurnal J " +
                "inner Join rabotnik R on R.id=J.id_rabotnika " +
                "inner Join predmet P on P.id=J.id_predmeta "+
                "inner Join forma_kontrol9 FK on FK.id=P.id_forma_kontrol9 "+
                "inner Join studenti S on J.id_studentov=S.id " +
                "inner Join groupa G on S.id_groupa=G.id " +
                "inner Join napravlenie N on N.id=G.id_napravlenie " +
                "inner Join facultet F on F.id=N.id_facultet",null);
        while (query.moveToNext()) {

          //  query=db.rawQuery("SELECT distinct J.id,J.id_rabotnika,J.id_predmeta,S.id_groupa FROM jurnal WHERE id_rabotnika=? and id_predmeta=? and id_groupa=?"
                 //   ,new String[] {Id_f.toString(),Id_n.toString(),Id_g.toString()});
            jurnal_id_name.add(new Adapter_tri_dobavleni9(query.getInt(0),query.getInt(1),query.getInt(2),query.getInt(3),query.getInt(4),
                    query.getString(5)+ " " +query.getString(6).substring(0,1) + ". " + query.getString(7).substring(0,1) + ".  "+ " "+
                            query.getString(8) + " (" +query.getString(9) + ")                                                                  "+
                            query.getString(10) + " " + query.getString(11) + " " +query.getString(12)));



        }

        query.close();
        db.close();

        Using_adapter_tri_dobavleni9 uatd = new Using_adapter_tri_dobavleni9(Smotret_Jurnal.this,jurnal_id_name);
        sp1.setAdapter(uatd);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                AdapterView<?> parent_onItemSelected=parent;
                View view_onItemSelected=view;
                int pos_onItemSelected=pos;
                long id_onItemSelected=id;
                OnClickFicsationStudentov();

                //Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
    }
    public void OnClickFicsationStudentov()
    {
        EditText et1 = (EditText) findViewById(R.id.editTextDate);
        Pokaz_vipad_spisok_jurnala(et1.getText().toString());
    }
    public void OnClickRefresh(View view)
    {
        OnClickFicsationStudentov();
    }
    public void Pokaz_vipad_spisok_jurnala(String selectedDate){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_F;

        List<Adapter_odnogo_dobavleni9> prepod_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> predmet_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> fng_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> fng_name2 = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> spinner_pokaz = new ArrayList<Adapter_odnogo_dobavleni9>();
        Set<Integer> fng_name1 = new HashSet<Integer>();
        final Spinner sp1 = (Spinner) findViewById(R.id.spinner_vibor_vsego);
        List<Adapter_tri_dobavleni9> jurnal_id_name = new ArrayList<Adapter_tri_dobavleni9>();

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        Adapter_tri_dobavleni9 a3d;
        a3d = (Adapter_tri_dobavleni9) sp1.getSelectedItem();
        String propyski;
        Integer kolvo=0,nebilo=0;
        spisok=new ArrayList<>();
        spisok_pokaz_daty_prop = new ArrayList<>();
        spisok_pokaz_daty_prop1 = new ArrayList<>();
        query=db.rawQuery("SELECT distinct J.id,J.id_rabotnika,J.id_predmeta,G.id,S.id," +
                        "S.last_name, S.first_name,S.middle_name" +
                        " FROM jurnal J " +
                        "inner Join studenti S on J.id_studentov=S.id " +
                        "inner Join groupa G on S.id_groupa=G.id " +
                        "WHERE J.id_rabotnika =? and J.id_predmeta =? and G.id=?"
                ,new String[] {String.valueOf(a3d.getId()),String.valueOf(a3d.getId_1()),String.valueOf(a3d.getId_4())});
        while(query.moveToNext())
        {
            Boolean flag_date=false;
            propyski="Присутствовал";
            query_F=db.rawQuery("SELECT * FROM ficsation WHERE data_propyskov=? and id_nomer_zapisi=?",new String[] {selectedDate,String.valueOf(query.getInt(0))});
            while (query_F.moveToNext())
            {
                flag_date=true;
            }
            query_F.moveToFirst();
            if(flag_date)
            {
                //Log.i("Mainn",query_F.getString(3));

                if(query_F.getString(3).equals("1"))
                    propyski = "Присутствовал";
                else
                {
                    propyski="Отсутствовал";
                    nebilo++;
                }
                spisok_pokaz_daty_prop.add(new Using_adapter_pokazat_daty_propyskov(query_F.getInt(0),query_F.getInt(1),
                        query.getString(5) + " "+query.getString(6),query_F.getString(4), propyski,query_F.getString(2)));
                kolvo++;


            }
            else {
                kolvo++;
                spisok_pokaz_daty_prop1.add(new Using_adapter_pokazat_daty_propyskov(0,query.getInt(0),query.getString(5) + " "+query.getString(6),"-",propyski,selectedDate));
            }
    query_F.close();
        }
        query.close();

        TextView tv = (TextView) findViewById(R.id.osj_kolvo);
        TextView tv1 = (TextView) findViewById(R.id.osj_nebilo);
        TextView tv2 = (TextView) findViewById(R.id.osj_kolvo_nebilo);
        tv.setText("Количество студентов: " + kolvo +" ");
        tv1.setText("Отсутствовало: ");
        tv2.setText(nebilo.toString());
        adapter_pokazat_daty_propyskov = new Adapter_pokazat_daty_propyskov(Smotret_Jurnal.this, spisok_pokaz_daty_prop);
        ListView lv = (ListView) findViewById(R.id.otobrajenie_proS_LV_jurnal);
        lv.setAdapter(adapter_pokazat_daty_propyskov);
        adapter_pokazat_daty_propyskov1 = new Adapter_pokazat_daty_propyskov(Smotret_Jurnal.this, spisok_pokaz_daty_prop1);
        ListView lv1 = (ListView) findViewById(R.id.otobrajenie_propyskov_LV_1);
        lv1.setAdapter(adapter_pokazat_daty_propyskov1);
        findViewById(R.id.osj_otpravit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Using_adapter_pokazat_daty_propyskov row: adapter_pokazat_daty_propyskov.uadp)
                {
                    Redactirovanie_Propyska(row.getId(),row.getId_zapisi_jurnala(), row.getData(),row.getFicsation(),row.getOpis());
                }
                for(Using_adapter_pokazat_daty_propyskov row: adapter_pokazat_daty_propyskov1.uadp)
                {
                    Redactirovanie_Propyska(row.getId(),row.getId_zapisi_jurnala(),row.getData(),row.getFicsation(),row.getOpis());
                }
                OnClickFicsationStudentov();
            }
        });
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        db.close();
        }


    public void Redactirovanie_Propyska(Integer id,Integer id_zapisi_jurnala,String data,String ficsation,String opisanie)
    {
        long id_zapisi;
        Cursor query;
        Boolean propyski;
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        ContentValues values_values = new ContentValues();
        if(ficsation.equals("Присутствовал"))
            propyski=true;
        else
            propyski=false;
        if(id==0)
        {
            values_values.put("id_nomer_zapisi",id_zapisi_jurnala);
            values_values.put("data_propyskov", data);
            values_values.put("propyski", propyski);
            values_values.put("opisanie",opisanie);
            db.execSQL("PRAGMA foreign_keys=ON");
            id_zapisi = db.insert("ficsation", null, values_values);
            if (id_zapisi != -1)
                Toast.makeText(getApplicationContext(), "Добавлены записи", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
        }
        else
        {
            values_values.put("id_nomer_zapisi",id_zapisi_jurnala);
            values_values.put("data_propyskov", data);
            values_values.put("propyski", propyski);
            values_values.put("opisanie",opisanie);
            db.execSQL("PRAGMA foreign_keys=ON");
            id_zapisi = db.update("ficsation", values_values, "id = ?", new String[]{String.valueOf(id)});
            if (id_zapisi != -1)
                Toast.makeText(getApplicationContext(), "Изменения внесены", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Произошла оиииииииииииииибка", Toast.LENGTH_LONG).show();
        }

        db.close();

    }

    public void OnClick_perehod_k_dobav_data_calendar(View view) {
//Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Smotret_Jurnal.this);
        subjectDialog.setTitle("Выберите дату");
        subjectDialog.setCancelable(false);
// Текущее время
        Date currentDate = new Date();
// Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.calendar, null);
        EditText et1 = (EditText) vv.findViewById(R.id.editTextDate_dialog);
        et1.setText(dateText);

        CalendarView calendarView = vv.findViewById(R.id.calendarView2);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                String selectedDate;
                if(mMonth+1>9)
                    if(mDay>9)
                        selectedDate = mYear+ "-" +(mMonth + 1)+ "-"+ mDay;
                    else
                        selectedDate = mYear+ "-" +(mMonth + 1)+ "-"+ "0" + mDay;
                else
                if(mDay>9)
                    selectedDate = mYear+ "-" +"0"+(mMonth + 1)+ "-"+ mDay;
                else
                    selectedDate = mYear+ "-" +"0"+(mMonth + 1)+"-"+ "0" + mDay;

                et1.setText(selectedDate);
            }

        });



        subjectDialog.setView(vv);
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et2 = (EditText) findViewById(R.id.editTextDate);
                et2.setText(et1.getText());
                OnClickFicsationStudentov();

            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }





}