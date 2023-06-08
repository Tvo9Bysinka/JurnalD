package com.example.jurnald;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dobavlenie_Prepodavatel9 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ArrayList<Using_adater_dobavlenie> spisok;
    Adapter_dobavlenie adapter_dobavlenie;

    ArrayList<Using_adapter_pokazat_FIO> spisok_fio;
    Adapter_pokazat_FIO adapter_pokazat_fio;
private Toolbar toolbar;
private DrawerLayout drawer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepod);
        Pokazat_Prepodavatel9();
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout_prepod);
        NavigationView navigationView = findViewById(R.id.nav_view_bokovoe_okno_prepod);
        // Log.i("Mainn"," "+navigationView);

        navigationView.setNavigationItemSelectedListener(this);
        //Копочка для выдвижения меню слева
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


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

    public void Pokazat_Prepodavatel9(){
        final int position_fngs;
        //подключаемся к базе
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Беру из бд выборку которая пришла (Select *)
        Cursor query;
        query=db.rawQuery("SELECT * FROM rabotnik ORDER BY last_name", null);

/**Для Препода поиск чтобы я искал поиск по ийди находил этой айди и отображалось не айди а название факультета**/
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_doljnost_prepodavatel);
            spisok_fio = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                Integer id;
                String last_name ,first_name , middle_name;
                id= query.getInt(0);
                last_name= query.getString(1); first_name= query.getString(2);middle_name= query.getString(3);
                spisok_fio.add(new Using_adapter_pokazat_FIO(id,last_name,first_name,middle_name));
            }
            adapter_pokazat_fio= new Adapter_pokazat_FIO(Dobavlenie_Prepodavatel9.this, spisok_fio);
            lv.setAdapter(adapter_pokazat_fio);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie_Prepodavatel9(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.otobr_id_scritoe)).getText().toString()));

                }
            });





        query.close();
        db.close();
    }
    public void OnClick_perehod_k_dobav_prepodavatel9(View view){
        //Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Prepodavatel9.this);
        subjectDialog.setTitle("Введите ФИО преподавателя");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_fio, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_F);
        final EditText et2 = (EditText) vv.findViewById(R.id.ed_dobavlenie_I);
        final EditText et3 = (EditText) vv.findViewById(R.id.ed_dobavlenie_O);
        subjectDialog.setView(vv);
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query;
                ContentValues values = new ContentValues();
                values.put("last_name", et1.getText().toString());
                values.put("first_name", et2.getText().toString());
                values.put("middle_name", et3.getText().toString());
                long id_zapisi= db.insert("rabotnik", null, values);
                if(id_zapisi!=-1) Toast.makeText(getApplicationContext(),"Преподаватель успешно добавлен", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(),"Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
                Pokazat_Prepodavatel9();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    public void Redactirovanie_Prepodavatel9(Integer id){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Prepodavatel9.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_fio, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_F);
        final EditText et2 = (EditText) vv.findViewById(R.id.ed_dobavlenie_I);
        final EditText et3 = (EditText) vv.findViewById(R.id.ed_dobavlenie_O);
        String stroka_firstname,stroka_lastname,stroka_middlename;

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query;


        query=db.rawQuery("SELECT * FROM rabotnik WHERE id=?",new String[] {String.valueOf(id)});
        query.moveToFirst();
        stroka_lastname=query.getString(1);
        stroka_firstname=query.getString(2);
        stroka_middlename=query.getString(3);
        query.close();
        et1.setText(stroka_lastname);
        et2.setText(stroka_firstname);
        et3.setText(stroka_middlename);

        db.close();




        subjectDialog.setView(vv);
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();

                long id_zapisi ;
                if (id > 0) {
                    db.execSQL("PRAGMA foreign_keys=ON");
                    values.put("last_name", et1.getText().toString());
                    values.put("first_name", et2.getText().toString());
                    values.put("middle_name", et3.getText().toString());
                    id_zapisi = db.update("rabotnik", values, "id = ?", new String[]{String.valueOf(id)});
                    if (id_zapisi != -1)
                        Toast.makeText(getApplicationContext(), "Изменения внесены", Toast.LENGTH_LONG).show();
                    else Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                db.close();
                Pokazat_Prepodavatel9();


            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long id_zapisi=0 ;
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                if (id > 0) {
                    db.execSQL("PRAGMA foreign_keys=ON");
                    try{
                        id_zapisi=db.delete("rabotnik", "id = ?", new String[]{String.valueOf(id)});
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "У этого Работника есть запись в журнале", Toast.LENGTH_LONG).show();
                    }
                    if(id_zapisi==1) Toast.makeText(getApplicationContext(),"Произошло удаление",Toast.LENGTH_LONG).show();
                    else  Toast.makeText(getApplicationContext(),"Ошибка удаления",Toast.LENGTH_LONG).show();
                }
                else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                db.close();
                Pokazat_Prepodavatel9();
            }
        });
        subjectDialog.show();

    }




}