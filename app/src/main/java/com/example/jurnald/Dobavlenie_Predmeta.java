package com.example.jurnald;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dobavlenie_Predmeta extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Using_adater_dobavlenie> spisok;
    Adapter_dobavlenie adapter_dobavlenie;
    ArrayList<Using_adapter_dobavleni9_predmeta> spisok_predmeta;
    Adapter_dobavleni9_predmeta adapter_dobavleni9_predmeta;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predmeta);
        Pokazat_Predmet();

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout_predmet);
        NavigationView navigationView = findViewById(R.id.nav_view_bokovoe_okno_predmet);
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


    /** Предмет **/
    public void OnClick_Pokazat_Predmet(View view) {
        Pokazat_Predmet();

    }
    public void Pokazat_Predmet(){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_D;
        query=db.rawQuery("SELECT * FROM predmet ORDER BY name_premeta", null);
        ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_fk_predmet);
        spisok_predmeta = new ArrayList<>();
        while (query.moveToNext()) {
            Integer id,id_fk;
            String name_predmeta,naz_id_fk;
            id= query.getInt(0);
            name_predmeta= query.getString(1);
            id_fk= query.getInt(2);
            /**Нахожу строчку по выбранному айди( перевести в стринг надо) **/
            query_D=db.rawQuery("SELECT * FROM forma_kontrol9 WHERE id=?",new String[] {id_fk.toString()});
            query_D.moveToFirst();
            naz_id_fk=query_D.getString(1);
            spisok_predmeta.add(new Using_adapter_dobavleni9_predmeta(id,name_predmeta,
                    naz_id_fk,id_fk));
            /** Закрытие табл*/
            query_D.close();
        }
        adapter_dobavleni9_predmeta= new Adapter_dobavleni9_predmeta(Dobavlenie_Predmeta.this, spisok_predmeta);
        lv.setAdapter(adapter_dobavleni9_predmeta);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Redactirovanie_Prepmeta(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.prlist_id)).getText().toString()));

            }
        });
        query.close();
        db.close();

    }
    public void OnClick_perehod_k_dobav_predmeta(View view){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Predmeta.this);
        subjectDialog.setTitle("Введите название дисциплины");
        subjectDialog.setCancelable(false);
        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_predmeta, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_predmeta_name);
        final Spinner sp1 = (Spinner) vv.findViewById(R.id.spinner_Fk);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query;
        int flag_Fk=0;
        List<Adapter_odnogo_dobavleni9> fk_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        query=db.rawQuery("SELECT * FROM forma_kontrol9", null);
        while (query.moveToNext()) {
            Integer id= query.getInt(0);
            String name= query.getString(1);
            fk_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_Fk=1;
        }
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Predmeta.this,fk_name);
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
                values.put("name_premeta", et1.getText().toString());
                Adapter_odnogo_dobavleni9 aod;
                if (finalFlag_Fk ==1){
                    aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                    values.put("id_forma_kontrol9", aod.getId());
                    db.execSQL("PRAGMA foreign_keys=ON");
                    long id_zapisi = db.insert("predmet", null, values);
                    //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                    if (id_zapisi != -1)
                        Toast.makeText(getApplicationContext(), "Работник успешно добавлен", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                else  Toast.makeText(getApplicationContext(), "Занесите информацию в пустые поля", Toast.LENGTH_LONG).show();

                db.close();
                Pokazat_Predmet();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    public void Redactirovanie_Prepmeta(Integer id) {

        /**Вызывает окно для внесения изменения информации**/
        //Log.i("Mainn"," "+id);
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Predmeta.this);
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


        query = db.rawQuery("SELECT * FROM predmet WHERE id=?", new String[]{String.valueOf(id)});
        query.moveToFirst();
        name_predmeta = query.getString(1);
        Integer id_fk_predmeta = query.getInt(2);
        query.close();
        et1.setText(name_predmeta);


        query = db.rawQuery("SELECT * FROM forma_kontrol9 WHERE id=?", new String[]{String.valueOf(id_fk_predmeta)});
        query.moveToFirst();
        Integer id_fk_0 = query.getInt(0);
        String name_fk = query.getString(1);
        fk_name.add(new Adapter_odnogo_dobavleni9(id_fk_0, name_fk));
        query.close();

        query = db.rawQuery("SELECT * FROM forma_kontrol9", null);
        while (query.moveToNext()) {
            Integer id_fk = query.getInt(0);
            name_fk = query.getString(1);
            if(id_fk_0 != id_fk)
            fk_name.add(new Adapter_odnogo_dobavleni9(id_fk, name_fk));
        }
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Predmeta.this, fk_name);
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
                            values.put("name_premeta", et1.getText().toString());
                            values.put("id_forma_kontrol9", aod.getId());
//Включаем добавление вторичных ключей
                            db.execSQL("PRAGMA foreign_keys=ON");
                            long id_zapisi = db.update("predmet", values, "id = ?", new String[]{String.valueOf(id)});
                            //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                            if (id_zapisi != -1)
                                Toast.makeText(getApplicationContext(), "Внесены изменения", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                        }
                        db.close();
                        Pokazat_Predmet();


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
                                id_zapisi = db.delete("predmet", "id = ?", new String[]{String.valueOf(id)});
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "У этого Предмета есть запись в журнале", Toast.LENGTH_LONG).show();
                            }
                            if (id_zapisi == 1)
                                Toast.makeText(getApplicationContext(), "Произошло удаление", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Ошибка удаления", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                        }

                        db.close();
                        Pokazat_Predmet();
                    }
                })
                .setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        subjectDialog.show();

    }

    /** Форма контроля **/
    public void OnClick_Pokazat_Fk(View view){
        Pokazat_Fk();
    }
    public void Pokazat_Fk(){
        final int position_fngs;
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query;
        query=db.rawQuery("SELECT * FROM forma_kontrol9 ORDER BY vid_formi", null);
        ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_fk_predmet);
        spisok = new ArrayList<>();
        while (query.moveToNext()) {
            Integer id = query.getInt(0);
            String name = query.getString(1);
            spisok.add(new Using_adater_dobavlenie(id, name));

        }
        adapter_dobavlenie = new Adapter_dobavlenie(Dobavlenie_Predmeta.this, spisok);
        lv.setAdapter(adapter_dobavlenie);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Redactirovanie_Fk(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.list_id)).getText().toString()));
            }
        });
        query.close();
        db.close();

    }
    public void OnClick_perehod_k_dobav_fk(View view) {
//Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Predmeta.this);
        subjectDialog.setTitle("Введите Вид занятия");
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
                values.put("vid_formi", et1.getText().toString());
                //Ну и откуда берём таблицу запихиваем в переменную
                long id_zapisi= db.insert("forma_kontrol9", null, values);
                //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                if(id_zapisi!=-1) Toast.makeText(getApplicationContext(),"Успешно добавлено", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(),"Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
                Pokazat_Fk();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    public void Redactirovanie_Fk(Integer id){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Predmeta.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_odno_pole_edit, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_odno_pole);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        String stroka;

        Cursor query;


        query=db.rawQuery("SELECT * FROM forma_kontrol9 WHERE id=?",new String[] {String.valueOf(id)});
        query.moveToFirst();
        stroka=query.getString(1);
        query.close();


        et1.setText(stroka);


        subjectDialog.setView(vv);
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    /** Внесение Изменения */
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        ContentValues values = new ContentValues();

                        long id_zapisi ;
                        if (id > 0) {
                            values.put("vid_formi", et1.getText().toString());
                            id_zapisi = db.update("forma_kontrol9", values, "id = ?", new String[]{String.valueOf(id)});

                            if (id_zapisi != -1)
                                Toast.makeText(getApplicationContext(), "Изменения внесены", Toast.LENGTH_LONG).show();
                            else Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        }
                        else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }

                        db.close();
                        Pokazat_Fk();
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
                                id_zapisi= db.delete("forma_kontrol9", "id = ?", new String[]{String.valueOf(id)});
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(), "У этого вида занятия  есть Предмет", Toast.LENGTH_LONG).show();
                            }
                            if(id_zapisi==1) Toast.makeText(getApplicationContext(),"Произошло удаление",Toast.LENGTH_LONG).show();
                            else  Toast.makeText(getApplicationContext(),"Ошибка удаления",Toast.LENGTH_LONG).show();

                        }
                        else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }

                        db.close();
                        Pokazat_Fk();
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
