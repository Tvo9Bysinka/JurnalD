package com.example.jurnald;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dobavlenie extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ArrayList<Using_adater_dobavlenie> spisok;
    Adapter_dobavlenie adapter_dobavlenie;

    ArrayList<Using_adapter_dobavleni9_studenta> spisok_studenta;
    Adapter_dobavleni9_studenta adapter_dobavleni9_studenta;

    ArrayList<Using_adapter_dobavleni9_predmeta> spisok_predmeta;
    Adapter_dobavleni9_predmeta adapter_dobavleni9_predmeta;

    Adapter_pokazat_3_pol9 adapter_pokazat_3_pol9;
    ArrayList<Using_adapter_pokazat_3_pol9> spisok_3_pol9;

    Adapter_pokazat_4_pol9 adapter_pokazat_4_pol9;
    ArrayList<Using_adapter_pokazat_4_pol9> spisok_4_pol9;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fngs);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


         drawer = findViewById(R.id.drawer_layout_fngs);
         NavigationView navigationView = findViewById(R.id.nav_view_bokovoe_okno);
        // Log.i("Mainn"," "+navigationView);

        navigationView.setNavigationItemSelectedListener(this);
         //Копочка для выдвижения меню слева
         ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
         this,drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
         drawer.addDrawerListener(toggle);
         toggle.syncState();
         Pokazat_Studenti();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dobav, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.menu_otmechat:
                intent = new Intent(this,Smotret_Jurnal.class);
                finish();
                startActivity(intent);
                break;
            case R.id.menu_fngs:
                intent = new Intent(this, Dobavlenie.class);
                finish();
                startActivity(intent);
                break;

                case R.id.menu_predmet:
                intent = new Intent(this,com.example.jurnald.Dobavlenie_Predmeta.class);
                finish();
                startActivity(intent);
                break;
           case R.id.menu_prepod:
                intent = new Intent(this,Dobavlenie_Prepodavatel9.class);
                finish();
                startActivity(intent);
                break;

            case R.id.menu_jurnal:
                intent = new Intent(this,Dobavlenie_Jurnal.class);
                finish();
                startActivity(intent);
                break;


            case R.id.menu_main:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //При нажатии выбираем меню слева и взависимости что выбрали выполняется
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Intent intent;
        switch (id){
            case R.id.nav_dobavlenie:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this,Dobavlenie.class);
                finish();
                startActivity(intent);
                Toast.makeText(this, "Добавление", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_dobavleniePrepodavatel9:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this,Dobavlenie_Prepodavatel9.class);
                finish();
                startActivity(intent);
                Toast.makeText(this, "Преподаватель", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_dobavlenieDisciplina:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this,Dobavlenie_Predmeta.class);
                finish();
                startActivity(intent);
                Toast.makeText(this, "Предмет", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_jurnal:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this,Dobavlenie_Jurnal.class);
                finish();
                startActivity(intent);
                Toast.makeText(this, "Журнал", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_dobavlenie_dom:
                drawer.closeDrawer(GravityCompat.START);
                finish();
                Toast.makeText(this, "Главное меню", Toast.LENGTH_SHORT).show();
                break;
        }
        //После выбора элемента меню закрывает меню
//drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /** Факультет **/
    public void OnClick_Pokazat_Facultet(View view) {
        Pokazat_Facultet();
    }
    public void Pokazat_Facultet(){
        final int position_fngs;
        //подключаемся к базе
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Беру из бд выборку которая пришла (Select *)
        Cursor query,query_F,query_N,query_G;
        query=db.rawQuery("SELECT * FROM facultet ORDER BY name_faculteta", null);
        ListView lv = (ListView) findViewById(R.id.dobavlenie_LV);
        spisok = new ArrayList<>();
        //идём по записям в таблицах (типо идём по строкам таблицы)
        while (query.moveToNext()) {
            //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
            Integer id = query.getInt(0);
            String name = query.getString(1);
            spisok.add(new Using_adater_dobavlenie(id, name));

        }
        //Ну и полученный список в адаптер чтобы отобразить
        adapter_dobavlenie = new Adapter_dobavlenie(Dobavlenie.this, spisok);
        lv.setAdapter(adapter_dobavlenie);
        //Чтобы для всех записей можно было выбрать редактирование
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                //вызываем метод где будем вносить изменение если нам понадобиться
                Redactirovanie_Facultet(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.list_id)).getText().toString()));
            }
        });
        query.close();
        db.close();

    }
    public void OnClick_perehod_k_dobav_facultet(View view) {//Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Введите название факультета");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_odno_pole_edit, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_odno_pole);

        subjectDialog.setView(vv);
//Он клик на позитив батон
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Грубо говоря открываем бд ( подключаемся)
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                //Значение куда сохраняем значение
                ContentValues values = new ContentValues();
                //В значение сохраняем имя столбца с названием которые ввели в edit
                values.put("name_faculteta", et1.getText().toString());
                //Ну и откуда берём таблицу запихиваем в переменную
                long id_zapisi= db.insert("facultet", null, values);
                //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                if(id_zapisi!=-1) Toast.makeText(getApplicationContext(),"Факультет успешно добавлен", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(),"Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
                Pokazat_Facultet();
            }

        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    public void Redactirovanie_Facultet(Integer id){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        String stroka;
        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_odno_pole_edit, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_odno_pole);
        Cursor query;
        query=db.rawQuery("SELECT * FROM facultet WHERE id=?",new String[] {String.valueOf(id)});
        query.moveToFirst();
        stroka=query.getString(1);
        query.close();
        et1.setText(stroka);
        db.close();

        subjectDialog.setView(vv);
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    /** Внесение Изменения */
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        ContentValues values = new ContentValues();

                        long id_zapisi ;
                        if (id > 0) {
                                    values.put("name_faculteta", et1.getText().toString());
                                    id_zapisi = db.update("facultet", values, "id = ?", new String[]{String.valueOf(id)});

                            if (id_zapisi != -1)
                                Toast.makeText(getApplicationContext(), "Изменения внесены", Toast.LENGTH_LONG).show();
                            else Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        }
                        else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                        db.close();
                                Pokazat_Facultet();

                    }
                })
                /** Удаление*/
                .setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id_zapisi = 0;
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        if (id > 0) {
                            db.execSQL("PRAGMA foreign_keys=ON");

                                    try{
                                        id_zapisi= db.delete("facultet", "id = ?", new String[]{String.valueOf(id)});
                                    }catch (Exception e){
                                        Toast.makeText(getApplicationContext(), "У этого факультета есть Студенты", Toast.LENGTH_LONG).show();
                                    }

                            if(id_zapisi==1) Toast.makeText(getApplicationContext(),"Произошло удаление",Toast.LENGTH_LONG).show();
                            else  Toast.makeText(getApplicationContext(),"Ошибка удаления",Toast.LENGTH_LONG).show();

                        }
                        else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                        db.close();
                        Pokazat_Facultet();

                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        subjectDialog.show();

    }



    /** Направление **/

    public void OnClick_Pokazat_Napravlenie(View view) {
        Pokazat_Napravlenie();

    }
    public void Pokazat_Napravlenie(){

        final int position_fngs;
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_N;
        query=db.rawQuery("SELECT * FROM napravlenie ORDER BY name_napravlenie", null);
        ListView lv = (ListView) findViewById(R.id.dobavlenie_LV);
        spisok_predmeta = new ArrayList<>();
        Integer id, id_nap;
        String name_fak,naz_id_nap;
        while (query.moveToNext()) {

            id = query.getInt(0);
            name_fak = query.getString(1);
            id_nap = query.getInt(2);

            query_N=db.rawQuery("SELECT * FROM facultet  WHERE id=?",new String[] {id_nap.toString()});
            query_N.moveToFirst();
            naz_id_nap=query_N.getString(1);
            spisok_predmeta.add(new Using_adapter_dobavleni9_predmeta(id,name_fak,
                    naz_id_nap,id_nap));
            /** Закрытие табл*/
            query_N.close();

        }
        //Ну и полученный список в адаптер чтобы отобразить
        adapter_dobavleni9_predmeta = new Adapter_dobavleni9_predmeta(Dobavlenie.this, spisok_predmeta);
        lv.setAdapter(adapter_dobavleni9_predmeta);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                //вызываем метод где будем вносить изменение если нам понадобиться
                Redactirovanie_Napravlenie(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.prlist_id)).getText().toString()));
            }
        });

        query.close();
        db.close();


    }
    public void OnClick_perehod_k_dobav_napravlenie(View view){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Введите данные");
        subjectDialog.setCancelable(false);
        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_predmeta, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_predmeta_name);
        final Spinner sp1 = (Spinner) vv.findViewById(R.id.spinner_Fk);
        TextView tv1 = (TextView) vv.findViewById(R.id.tv_verh);
        TextView tv2 = (TextView) vv.findViewById(R.id.tv_niz_spinner);
        tv1.setText("Введите название Направления");
        tv2.setText("Выберите Факультет");
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query;
        int flag_Fk=0;
        List<Adapter_odnogo_dobavleni9> fk_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        query=db.rawQuery("SELECT * FROM facultet", null);
        while (query.moveToNext()) {
            Integer id= query.getInt(0);
            String name= query.getString(1);
            fk_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_Fk=1;
        }
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this,fk_name);
        sp1.setAdapter(uaod);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

            }

            public void onNothingSelected(AdapterView parent) {
            }
        });
        db.close();
        subjectDialog.setView(vv);
        int finalFlag_Fk = flag_Fk;
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query;
                ContentValues values = new ContentValues();
                values.put("name_napravlenie", et1.getText().toString());
                Adapter_odnogo_dobavleni9 aod;
                if (finalFlag_Fk ==1){
                    aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                    values.put("id_facultet", aod.getId());
                    db.execSQL("PRAGMA foreign_keys=ON");
                    long id_zapisi = db.insert("napravlenie", null, values);
                    //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                    if (id_zapisi != -1)
                        Toast.makeText(getApplicationContext(), "Направление успешно добавленно", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                else  Toast.makeText(getApplicationContext(), "Занесите информацию в пустые поля", Toast.LENGTH_LONG).show();

                db.close();
                Pokazat_Napravlenie();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    public void  Redactirovanie_Napravlenie(Integer id){

        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.izmenenie_odno_edit_spiner, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_pole_izmeneni9);
        Spinner sp1 = (Spinner) vv.findViewById(R.id.spinner_izmeneni9);
        EditText et2 = (EditText) vv.findViewById(R.id.id_inviz_pol9);
        List<Adapter_odnogo_dobavleni9> fk_name = new ArrayList<Adapter_odnogo_dobavleni9>();

        Adapter_odnogo_dobavleni9 aod;
        aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        String name_predmeta;

        Cursor query, query_fk;


        query = db.rawQuery("SELECT * FROM napravlenie WHERE id=?", new String[]{String.valueOf(id)});
        query.moveToFirst();
        name_predmeta = query.getString(1);
        Integer id_fk_predmeta = query.getInt(2);
        query.close();
        et1.setText(name_predmeta);


        query = db.rawQuery("SELECT * FROM facultet WHERE id=?", new String[]{String.valueOf(id_fk_predmeta)});
        query.moveToFirst();
        Integer id_fk_0 = query.getInt(0);
        String name_fk = query.getString(1);
        fk_name.add(new Adapter_odnogo_dobavleni9(id_fk_0, name_fk));
        query.close();

        query = db.rawQuery("SELECT * FROM facultet", null);
        while (query.moveToNext()) {
            Integer id_fk = query.getInt(0);
            name_fk = query.getString(1);
            if(id_fk_0 != id_fk)
                fk_name.add(new Adapter_odnogo_dobavleni9(id_fk, name_fk));
        }
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this, fk_name);
        sp1.setAdapter(uaod);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец.
        db.close();

        subjectDialog.setView(vv);
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {

                    @Override
                    /** Внесение Изменения */
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        Cursor query;
                        ContentValues values = new ContentValues();

                        Adapter_odnogo_dobavleni9 aod;
                        aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                        if (id > 0) {
                            values.put("name_napravlenie", et1.getText().toString());
                            values.put("id_facultet", aod.getId());
//Включаем добавление вторичных ключей
                            db.execSQL("PRAGMA foreign_keys=ON");
                            long id_zapisi = db.update("napravlenie", values, "id = ?", new String[]{String.valueOf(id)});
                            //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                            if (id_zapisi != -1)
                                Toast.makeText(getApplicationContext(), "Внесены изменения", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                        }
                        db.close();
                        Pokazat_Napravlenie();



                    }
                })
                .setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id_zapisi = 0;
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        if (id > 0) {
                            db.execSQL("PRAGMA foreign_keys=ON");
                            try {
                                id_zapisi = db.delete("napravlenie", "id = ?", new String[]{String.valueOf(id)});
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "У этого Направления есть запись в журнале", Toast.LENGTH_LONG).show();
                            }
                            if (id_zapisi == 1)
                                Toast.makeText(getApplicationContext(), "Произошло удаление", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Ошибка удаления", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                        }

                        db.close();
                        Pokazat_Napravlenie();
                    }
                })
                .setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        subjectDialog.show();

    }



    /** Группа **/
    public void OnClick_Pokazat_Group(View view) {
        Pokazat_Group();

    }
    public void Pokazat_Group(){
        final int position_fngs;
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_N;
        query=db.rawQuery("SELECT * FROM groupa ORDER BY name_group", null);
        spisok_predmeta = new ArrayList<>();
        Integer id, id_nap;
        String name_fak,naz_id_nap;
        while (query.moveToNext()) {

            id = query.getInt(0);
            name_fak = query.getString(1);
            id_nap = query.getInt(2);

            query_N=db.rawQuery("SELECT distinct N.id,N.name_napravlenie,F.name_faculteta,F.id  " +
                    "FROM napravlenie N inner Join facultet F on N.id_facultet=F.id ORDER BY N.name_napravlenie ", null);
            while(query_N.moveToNext())
            {
                if(id_nap == query_N.getInt(0))
                    spisok_predmeta.add(new Using_adapter_dobavleni9_predmeta(id,name_fak, query_N.getString(1) + " " + query_N.getString(2) ,query_N.getInt(3)));


            }
            query_N.close();

        }

        ListView lv = (ListView) findViewById(R.id.dobavlenie_LV);
        adapter_dobavleni9_predmeta = new Adapter_dobavleni9_predmeta(Dobavlenie.this, spisok_predmeta);
        lv.setAdapter(adapter_dobavleni9_predmeta);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                //вызываем метод где будем вносить изменение если нам понадобиться
                Redactirovanie_Group(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.prlist_id)).getText().toString()));
            }
        });


        query.close();
        db.close();

    }
    public void OnClick_perehod_k_dobav_group(View view){



        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Заполните данные");
        subjectDialog.setCancelable(false);
        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_predmeta, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_predmeta_name);
        final Spinner sp1 = (Spinner) vv.findViewById(R.id.spinner_Fk);
        TextView tv1 = (TextView) vv.findViewById(R.id.tv_verh);
        TextView tv2 = (TextView) vv.findViewById(R.id.tv_niz_spinner);
        tv1.setText("Введите название Группы");
        tv2.setText("Выберите Направление");
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query;
        int flag_Fk=0;
        List<Adapter_odnogo_dobavleni9> fk_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        query=db.rawQuery("SELECT distinct N.id,N.name_napravlenie,F.name_faculteta,F.id  " +
                "FROM napravlenie N inner Join facultet F on N.id_facultet=F.id ORDER BY N.id ", null);
        while (query.moveToNext()) {
            Integer id= query.getInt(0);
            String name= query.getString(1);
            fk_name.add(new Adapter_odnogo_dobavleni9(id,name +" "+query.getString(2)));
            flag_Fk=1;
        }
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this,fk_name);
        sp1.setAdapter(uaod);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

            }

            public void onNothingSelected(AdapterView parent) {
            }
        });
        db.close();
        subjectDialog.setView(vv);
        int finalFlag_Fk = flag_Fk;
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query;
                ContentValues values = new ContentValues();
                values.put("name_group", et1.getText().toString());
                Adapter_odnogo_dobavleni9 aod;
                if (finalFlag_Fk ==1){
                    aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                    values.put("id_napravlenie", aod.getId());
                    db.execSQL("PRAGMA foreign_keys=ON");
                    long id_zapisi = db.insert("groupa", null, values);
                    //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                    if (id_zapisi != -1)
                        Toast.makeText(getApplicationContext(), "Группа успешно добавленна", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                else  Toast.makeText(getApplicationContext(), "Занесите информацию в пустые поля", Toast.LENGTH_LONG).show();

                db.close();
                Pokazat_Group();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();


    }
    public void Redactirovanie_Group(Integer id){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.izmenenie_odno_edit_spiner, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_pole_izmeneni9);
        Spinner sp1 = (Spinner) vv.findViewById(R.id.spinner_izmeneni9);
        EditText et2 = (EditText) vv.findViewById(R.id.id_inviz_pol9);
        List<Adapter_odnogo_dobavleni9> fk_name = new ArrayList<Adapter_odnogo_dobavleni9>();

        Adapter_odnogo_dobavleni9 aod;
        aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        String name_predmeta;

        Cursor query, query_NF;


        query = db.rawQuery("SELECT * FROM groupa WHERE id=?", new String[]{String.valueOf(id)});
        query.moveToFirst();
        name_predmeta = query.getString(1);
        Integer id_fk_predmeta = query.getInt(2);
        query.close();
        et1.setText(name_predmeta);


        query = db.rawQuery("SELECT * FROM napravlenie WHERE id=?", new String[]{String.valueOf(id_fk_predmeta)});
        query.moveToFirst();
        Integer id_fN;
        Integer id_fk_0 = query.getInt(0);
        String name_fk = query.getString(1);
        query_NF=db.rawQuery("SELECT distinct N.id,N.name_napravlenie,F.name_faculteta,F.id  " +
                "FROM napravlenie N inner Join facultet F on N.id_facultet=F.id ORDER BY N.id ", null);
        while (query_NF.moveToNext()) {
            if(query_NF.getInt(0)==id_fk_0)
            {fk_name.add(new Adapter_odnogo_dobavleni9(id_fk_0, name_fk + " " + query_NF.getString(2)));}
        }
        query_NF.close();
        query.close();




        query = db.rawQuery("SELECT * FROM napravlenie", null);
        while (query.moveToNext())
        {

            query_NF=db.rawQuery("SELECT distinct N.id,N.name_napravlenie,F.name_faculteta,F.id  " +
                    "FROM napravlenie N inner Join facultet F on N.id_facultet=F.id ORDER BY N.id ", null);
            Integer id_fk = query.getInt(0);
            name_fk = query.getString(1);
            if(id_fk_0 != id_fk)
            {
                while (query_NF.moveToNext())
                {
                    if (query_NF.getInt(0) == id_fk)
                    {
                        fk_name.add(new Adapter_odnogo_dobavleni9(id_fk, name_fk + " " + query_NF.getString(2)));
                    }
                }

            }
            query_NF.close();
        }

        query.close();
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this, fk_name);
        sp1.setAdapter(uaod);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец.
        db.close();

        subjectDialog.setView(vv);
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {

                    @Override
                    /** Внесение Изменения */
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        Cursor query;
                        ContentValues values = new ContentValues();

                        Adapter_odnogo_dobavleni9 aod;
                        aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                        if (id > 0) {
                            values.put("name_group", et1.getText().toString());
                            values.put("id_napravlenie", aod.getId());
//Включаем добавление вторичных ключей
                            db.execSQL("PRAGMA foreign_keys=ON");
                            long id_zapisi = db.update("groupa", values, "id = ?", new String[]{String.valueOf(id)});
                            //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                            if (id_zapisi != -1)
                                Toast.makeText(getApplicationContext(), "Внесены изменения", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                        }
                        db.close();
                        Pokazat_Group();



                    }
                })
                .setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id_zapisi = 0;
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        if (id > 0) {
                            db.execSQL("PRAGMA foreign_keys=ON");
                            try {
                                id_zapisi = db.delete("groupa", "id = ?", new String[]{String.valueOf(id)});
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "У этой Группы есть запись в журнале", Toast.LENGTH_LONG).show();
                            }
                            if (id_zapisi == 1)
                                Toast.makeText(getApplicationContext(), "Произошло удаление", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Ошибка удаления", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                        }

                        db.close();
                        Pokazat_Group();
                    }
                })
                .setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        subjectDialog.show();

    }





    /** Студент **/
    public void OnClick_Pokazat_Studenti(View view) {
        Pokazat_Studenti();

    }
    public void Pokazat_Studenti(){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_N,query_G;
        query=db.rawQuery("SELECT S.id,S.last_name,S.first_name, S.middle_name,S.id_groupa,G.id,G.name_group " +
                "FROM studenti S inner join groupa G on G.id=S.id_groupa ORDER BY G.name_group,S.last_name", null);
        spisok_studenta = new ArrayList<>();
        while (query.moveToNext()) {
            Integer id, id_groupa;
            String last_name ,first_name , middle_name;
            id= query.getInt(0);
            last_name= query.getString(1); first_name= query.getString(2);middle_name= query.getString(3);
            id_groupa=query.getInt(4);

            query_G=db.rawQuery("SELECT distinct G.id,G.name_group,N.name_napravlenie,N.id  " +
                    "FROM groupa G inner Join napravlenie N on N.id=G.id_napravlenie ORDER BY name_group ", null);
            while (query_G.moveToNext())
            {
                if(query_G.getInt(0)==id_groupa)
                {
                    query_N=db.rawQuery("SELECT distinct N.id,N.name_napravlenie,F.name_faculteta,F.id  " +
                            "FROM napravlenie N inner Join facultet F on N.id_facultet=F.id ORDER BY N.id ", null);
                    while(query_N.moveToNext())
                    {
                        if(query_G.getInt(3) == query_N.getInt(0))
                        {
                            spisok_studenta.add(new Using_adapter_dobavleni9_studenta(id,
                                    last_name,  first_name , middle_name,
                                    query_N.getString(2),query_N.getInt(3) ,
                                    query_N.getString(1),query_N.getInt(0),
                                    query_G.getString(1),query_G.getInt(0)));
                        }


                    }
                    query_N.close();
                }
            }
            query_G.close();


        }
        ListView lv = (ListView) findViewById(R.id.dobavlenie_LV);
        adapter_dobavleni9_studenta = new Adapter_dobavleni9_studenta(Dobavlenie.this, spisok_studenta);
        lv.setAdapter(adapter_dobavleni9_studenta);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                //вызываем метод где будем вносить изменение если нам понадобиться
                Redactirovanie_Studenti(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.stlist_id)).getText().toString()));

            }
        });

        query.close();
        db.close();

    }
    public void OnClick_perehod_k_dobav_studenti(View view){


        //Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Введите ФИО студента");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_prepodavatel9, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_prepod_lastname);
        final EditText et2 = (EditText) vv.findViewById(R.id.ed_dobavlenie_prepod_name);
        final EditText et3 = (EditText) vv.findViewById(R.id.ed_dobavlenie_prepod_middlename);
        final Spinner sp1 = (Spinner) vv.findViewById(R.id.spinner_D);
        TextView tv1 = (TextView) vv.findViewById(R.id.textView99);
        tv1.setText("Выберите ФНГ");


        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_G,query_N;
        int flag_Fk=0;
        List<Adapter_odnogo_dobavleni9> fk_name = new ArrayList<Adapter_odnogo_dobavleni9>();

        query=db.rawQuery("SELECT distinct G.id,G.name_group,N.id,N.name_napravlenie,F.id,F.name_faculteta FROM groupa G inner Join napravlenie N on N.id=G.id_napravlenie inner Join facultet F on N.id_facultet=F.id ORDER BY G.id ",null);
while(query.moveToNext())
{ fk_name.add(new Adapter_odnogo_dobavleni9(query.getInt(0),
        query.getString(1) + " " + query.getString(3) + " " + query.getString(5)));
    flag_Fk = 1;

}


        query.close();
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this,fk_name);
        sp1.setAdapter(uaod);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

            }

            public void onNothingSelected(AdapterView parent) {
            }
        });
        db.close();
        subjectDialog.setView(vv);
        int finalFlag_Fk = flag_Fk;
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query;
                ContentValues values = new ContentValues();
                values.put("last_name", et1.getText().toString());
                values.put("first_name", et2.getText().toString());
                values.put("middle_name", et3.getText().toString());
                Adapter_odnogo_dobavleni9 aod;
                if (finalFlag_Fk ==1){
                    aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                    values.put("id_groupa", aod.getId());
                    db.execSQL("PRAGMA foreign_keys=ON");
                    long id_zapisi = db.insert("studenti", null, values);
                    //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                    if (id_zapisi != -1)
                        Toast.makeText(getApplicationContext(), "Студент успешно добавлен", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                else  Toast.makeText(getApplicationContext(), "Занесите информацию в пустые поля", Toast.LENGTH_LONG).show();

                db.close();
                Pokazat_Studenti();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();


    }
    public void Redactirovanie_Studenti(Integer id){


        /**Вызывает окно для внесения изменения информации**/

        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.izmenenie_3edit_spiner, null);
        final EditText et0 = (EditText) vv.findViewById(R.id.i3es_id_inviz_pol9);
        final EditText et1 = (EditText) vv.findViewById(R.id.i3es_edit_F);
        final EditText et2 = (EditText) vv.findViewById(R.id.i3es_edit_I);
        final EditText et3 = (EditText) vv.findViewById(R.id.i3es_edit_O);
        Spinner sp1 = (Spinner) vv.findViewById(R.id.i3es_spinner);
        List<Adapter_odnogo_dobavleni9> fk_name = new ArrayList<Adapter_odnogo_dobavleni9>();

        Adapter_odnogo_dobavleni9 aod;
        aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        String last_name, first_name, middle_name;

        Cursor query, query_NF;


        query = db.rawQuery("SELECT * FROM studenti WHERE id=?", new String[]{String.valueOf(id)});
        query.moveToFirst();
        last_name= query.getString(1);
        first_name= query.getString(2);
        middle_name= query.getString(3);
        Integer id_groupa = query.getInt(4);
        query.close();
        et1.setText(last_name);
        et2.setText(first_name);
        et3.setText(middle_name);


        query = db.rawQuery("SELECT * FROM groupa WHERE id=?", new String[]{String.valueOf(id_groupa)});
        query.moveToFirst();
        Integer id_fN;
        Integer id_fk_0 = query.getInt(0);
        String name_fk = query.getString(1);
        query_NF=db.rawQuery("SELECT distinct G.id,G.name_group,N.id,N.name_napravlenie,F.id,F.name_faculteta" +
                " FROM groupa G inner Join napravlenie N on N.id=G.id_napravlenie " +
                "inner Join facultet F on N.id_facultet=F.id ORDER BY G.id ",null);
        while (query_NF.moveToNext()) {
            if(query_NF.getInt(0)==id_fk_0)
            {
                fk_name.add(new Adapter_odnogo_dobavleni9(id_fk_0, query_NF.getString(1) + " " + query_NF.getString(3) + " " + query_NF.getString(5)));
            }
        }
        query_NF.close();
        query.close();




        query = db.rawQuery("SELECT * FROM groupa", null);
        while (query.moveToNext())
        {
            if(id_fk_0 != query.getInt(0))
            {
            query_NF=db.rawQuery("SELECT distinct G.id,G.name_group,N.id,N.name_napravlenie,F.id,F.name_faculteta" +
                    " FROM groupa G inner Join napravlenie N on N.id=G.id_napravlenie " +
                    "inner Join facultet F on N.id_facultet=F.id ORDER BY G.id ",null);
            while (query_NF.moveToNext())
            {
                if(query_NF.getInt(0) == query.getInt(0))
                {
                    fk_name.add(new Adapter_odnogo_dobavleni9(query.getInt(0), query_NF.getString(1) + " " + query_NF.getString(3) + " " + query_NF.getString(5)));
                }
            }
            query_NF.close();
            }
        }

        query.close();
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this, fk_name);
        sp1.setAdapter(uaod);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец.
        db.close();

        subjectDialog.setView(vv);
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {

                    @Override
                    /** Внесение Изменения */
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        Cursor query;
                        ContentValues values = new ContentValues();

                        Adapter_odnogo_dobavleni9 aod;
                        aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                        if (id > 0) {
                            values.put("last_name", et1.getText().toString());
                            values.put("first_name", et2.getText().toString());
                            values.put("middle_name", et3.getText().toString());
                            values.put("id_groupa", aod.getId());
//Включаем добавление вторичных ключей
                            db.execSQL("PRAGMA foreign_keys=ON");
                            long id_zapisi = db.update("studenti", values, "id = ?", new String[]{String.valueOf(id)});
                            //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                            if (id_zapisi != -1)
                                Toast.makeText(getApplicationContext(), "Внесены изменения", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                        }
                        db.close();
                        Pokazat_Studenti();



                    }
                })
                .setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id_zapisi = 0;
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        if (id > 0) {
                            db.execSQL("PRAGMA foreign_keys=ON");
                            try {
                                id_zapisi = db.delete("studenti", "id = ?", new String[]{String.valueOf(id)});
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "У этого Студента есть запись в журнале", Toast.LENGTH_LONG).show();
                            }
                            if (id_zapisi == 1)
                                Toast.makeText(getApplicationContext(), "Произошло удаление", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Ошибка удаления", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                        }

                        db.close();
                        Pokazat_Studenti();

                    }
                })
                .setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        subjectDialog.show();


    }


}


