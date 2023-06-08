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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dobavlenie_Jurnal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ArrayList<Using_adater_dobavlenie> spisok,spisok_1_pol9,spisokfk;
    Adapter_dobavlenie adapter_dobavlenie;

    ArrayList<Using_adapter_dobavleni9_studenta> spisok_studenta;
    Adapter_dobavleni9_studenta adapter_dobavleni9_studenta;

    ArrayList<Using_adapter_dobavleni9_predmeta> spisok_predmeta,spisol_2_pol9,spisok_2_pol9_2;
    Adapter_dobavleni9_predmeta adapter_dobavleni9_predmeta;
    List<Integer> list_id_studentiv;
    Adapter_pokazat_3_pol9 adapter_pokazat_3_pol9;
    ArrayList<Using_adapter_pokazat_3_pol9> spisok_3_pol9;

    Adapter_pokazat_4_pol9 adapter_pokazat_4_pol9;
    ArrayList<Using_adapter_pokazat_4_pol9> spisok_4_pol9;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurnal);
      Pokazat_Jurnal();
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout_jurnal);
        NavigationView navigationView = findViewById(R.id.nav_view_bokovoe_okno_jurnal);
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
    public void Pokazat_Jurnal() {






        final int position_fngs;
        //подключаемся к базе
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Беру из бд выборку которая пришла (Select *)
        Cursor query,query_Prepod,query_Predmet,query_Fk,query_S,query_GNF,query_G;

        query=db.rawQuery("SELECT J.id,J.id_rabotnika, J.id_predmeta, J.id_studentov, S.id_groupa," +
                "R.last_name, P.name_premeta,G.name_group,S.last_name " +
                "FROM jurnal J " +
                "inner Join rabotnik R on R.id=J.id_rabotnika " +
                "inner Join predmet P on P.id=J.id_predmeta "+
                "inner Join studenti S on J.id_studentov=S.id " +
                "inner Join groupa G on S.id_groupa=G.id " +
                "ORDER BY R.last_name ,P.name_premeta,G.name_group,S.last_name", null);

        //  while(query.moveToNext()) {            Log.i("Mainn"," "+ query.getInt(0));}

        ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_jurnal);
        spisok_4_pol9 = new ArrayList<>();
        //идём по записям в таблицах (типо идём по строкам таблицы)
        while (query.moveToNext()) {
            //Log.i("Mainn"," "+ query.getInt(0));


            //Получаем id Журнала
            Integer id,id_prepoda_doljn,id_predmeta_fk,id_studenta,propyski;
            String naz_id_prepoda_doljn,naz_id_predmeta_fk,naz_id_studenta,naz_FNG;

            //Для Препода
            String last_name_id_prepoda,first_name_id_prepoda,middle_name_id_prepoda;
            

            //Для Предмета
            Integer id_fk;
            String name_predmeta;
            //Для Формы контроля
            String name_id_fk;

            //Для  Студента
            Integer id_groupi;
            String last_name_id_studenta,first_name_id_studenta,middle_name_id_studenta;
            //Для ФНГ
            String naz_id_FNG = null;


            id= query.getInt(0);
            id_prepoda_doljn= query.getInt(1);
            id_predmeta_fk= query.getInt(2);
            id_studenta= query.getInt(3);
//
            /** буду по айди из журнала искать препода с должностью Прпеподаватель */

            query_Prepod=db.rawQuery("SELECT * FROM rabotnik WHERE id=?",new String[] {id_prepoda_doljn.toString()});
            query_Prepod.moveToFirst();
            last_name_id_prepoda=query_Prepod.getString(1);
            first_name_id_prepoda=query_Prepod.getString(2);
            middle_name_id_prepoda=query_Prepod.getString(3);
            /** Препод Фамилия и иницалы беру + Должность */
            try {
                naz_id_prepoda_doljn=last_name_id_prepoda + " " + first_name_id_prepoda.substring(0,1)
                        + "." + middle_name_id_prepoda.substring(0,1) + ".   " ;}
            catch(Exception e ){
                naz_id_prepoda_doljn=last_name_id_prepoda + " " + first_name_id_prepoda
                        + " " + middle_name_id_prepoda+ " " ;
            }



            /** Предмет */
            query_Predmet=db.rawQuery("SELECT * FROM predmet WHERE id=?",new String[] {id_predmeta_fk.toString()});
            query_Predmet.moveToFirst();
            name_predmeta=query_Predmet.getString(1);
            id_fk=query_Predmet.getInt(2);
            query_Fk=db.rawQuery("SELECT * FROM forma_kontrol9 WHERE id=?",new String[] {id_fk.toString()});
            query_Fk.moveToFirst();
            name_id_fk=query_Fk.getString(1);
            /** Предмет */
            naz_id_predmeta_fk=name_predmeta + " " + name_id_fk;



            /** Студент и ФНГ*/
            query_S=db.rawQuery("SELECT * FROM studenti WHERE id=?",new String[] {id_studenta.toString()});
            query_S.moveToFirst();
            last_name_id_studenta=query_S.getString(1);
            first_name_id_studenta=query_S.getString(2);
            middle_name_id_studenta=query_S.getString(3);
            id_groupi=query_S.getInt(4);
            try {
                naz_id_studenta=last_name_id_studenta + " " + first_name_id_studenta.substring(0,1)
                        + "." + middle_name_id_studenta.substring(0,1) + ". " ;}
            catch(Exception e ){
                naz_id_studenta=last_name_id_studenta + " " + first_name_id_studenta
                        + " " + middle_name_id_studenta + " " ;
            }

            /** Студент */

            query_G = db.rawQuery("SELECT * FROM groupa WHERE id=?", new String[]{String.valueOf(id_groupi)});
            query_G.moveToFirst();
            Integer id_fk_0 = query_G.getInt(0);
            query_GNF=db.rawQuery("SELECT distinct G.id,G.name_group,N.id,N.name_napravlenie,F.id,F.name_faculteta" +
                    " FROM groupa G inner Join napravlenie N on N.id=G.id_napravlenie " +
                    "inner Join facultet F on N.id_facultet=F.id ORDER BY G.id ",null);
            while (query_GNF.moveToNext()) {
                if(query_GNF.getInt(0)==id_fk_0)
                {
                    naz_id_FNG = query_GNF.getString(1) + " " + query_GNF.getString(3) + " " + query_GNF.getString(5);
                }
            }


            /** ФНГ*/
                   // x.getId_rabotnika(),x.getId_predmeta(),x.getId_studentov()));}
            spisok_4_pol9.add(new Using_adapter_pokazat_4_pol9(id,naz_id_prepoda_doljn,naz_id_predmeta_fk, naz_id_studenta,naz_id_FNG, id_prepoda_doljn,id_predmeta_fk,id_studenta));
            query_GNF.close();
            query_G.close();
            query_Fk.close();
            query_Predmet.close();
            query_Prepod.close();
            query_S.close();

        }
        //Ну и полученный список в адаптер чтобы отобразить
        adapter_pokazat_4_pol9= new Adapter_pokazat_4_pol9(Dobavlenie_Jurnal.this, spisok_4_pol9);
        lv.setAdapter(adapter_pokazat_4_pol9);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                //вызываем метод где будем вносить изменение если нам понадобиться
                Redactirovanie_jurnal(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.oi4p_id_inviz)).getText().toString()));
            }
        });


        query.close();
        db.close();






    }
    public void OnClick_perehod_k_dobav_jurnala(View view){
        //Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Jurnal.this);
        subjectDialog.setTitle("Выбирите поля");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_jurnala, null);
        final Spinner sp1 = (Spinner) vv.findViewById(R.id.j_spinner_prepod);
        final Spinner sp2 = (Spinner) vv.findViewById(R.id.j_spinner_predmet);
        final Spinner sp3 = (Spinner) vv.findViewById(R.id.j_spinner_jng);
        List<Adapter_odnogo_dobavleni9> fng_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> fng_name2 = new ArrayList<Adapter_odnogo_dobavleni9>();



        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_Fk,query_F,query_N,query_G;
        //Флаг для того чтобы понятьт заходит ли в вайл ( т.е проверка пустые ли таблицы ФНГ, если пустые то не даст добавить студента)
        Boolean flag_FNG=false,flag_Prepod=false,flag_Predmet=false;
        Integer flag_kolvo_studentov=0;


        /** Форма Препода*/
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> prepod_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM rabotnik", null);
/** Захожу в тьаблицу работник если пустая то ставлю флаг =0 где в последствии говорю что занести препода, чтобы заполнить журнал
 * тцт цикл из 2 вайлов где я ещё привязвыю вторичный ключ должность тк как препод может быть и лектором например и практиком
 * ну и чтобы сразу показывалась должность для удобства*/
        while (query.moveToNext()) {
            //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
            Integer id,id_doljnosti;
            String prlast_name,prfirst_name,prmiddle_name,name;
            id= query.getInt(0);
            prlast_name = query.getString(1);
            prfirst_name= query.getString(2);
            prmiddle_name= query.getString(3);
            name=prlast_name+" "+ prfirst_name + " " + prmiddle_name;
            prepod_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_Prepod=true;
        }
        //После заполнения списка названий я передаю его
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Jurnal.this,prepod_name);
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
        //Конец Препода




/** Форма Предмета смысл такой же как и у пррепода*/
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> predmet_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM predmet", null);

        while (query.moveToNext()) {
            query_Fk=db.rawQuery("SELECT * FROM forma_kontrol9", null);
            //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
            Integer id,id_predmeta;
            String name_predmeta,nazvanie_fk=" ",name;
            id= query.getInt(0);
            name_predmeta= query.getString(1);
            id_predmeta =query.getInt(2);
            while (query_Fk.moveToNext()){
                Integer id_fk_while;
                String name_id_fk_while;
                id_fk_while=query_Fk.getInt(0);
                name_id_fk_while=query_Fk.getString(1);
                if(id_fk_while==id_predmeta)
                    nazvanie_fk=name_id_fk_while;
            }

            name=name_predmeta+" "+nazvanie_fk;
            predmet_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_Predmet=true;
            query_Fk.close();
        }
        //После заполнения списка названий я передаю его
        uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Jurnal.this,predmet_name);
        sp2.setAdapter(uaod);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец Предмета
        /**Для ФНГ**/
        List<Adapter_odnogo_dobavleni9> fk_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        query=db.rawQuery("SELECT distinct G.id,G.name_group,N.id,N.name_napravlenie,F.id,F.name_faculteta FROM groupa G inner Join napravlenie N on N.id=G.id_napravlenie inner Join facultet F on N.id_facultet=F.id ORDER BY G.id ",null);
        while(query.moveToNext())
        { fk_name.add(new Adapter_odnogo_dobavleni9(query.getInt(0),
                query.getString(1) + " " + query.getString(3) + " " + query.getString(5)));
            flag_FNG=true;

        }


        query.close();
        uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Jurnal.this,fk_name);
        sp3.setAdapter(uaod);
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

            }

            public void onNothingSelected(AdapterView parent) {
            }
        });
        db.close();
        //Конец ФНГ
        subjectDialog.setView(vv);
        final Boolean[] flag_Student = {false};
        Boolean finalFlag_Predmet = flag_Predmet;
        Boolean finalFlag_Prepod = flag_Prepod;
        Boolean finalFlag_FNG = flag_FNG;

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Грубо говоря открываем бд ( подключаемся)
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query,query_S,query_Predmet,query_Prepod,query_J,query_PPS;

                /**Есть ли студенты по этим ФНГ
                 * Тут уже по выбранным Фнг мы будем смотреть есть ли такие студенты по вторичным ключам, если таких нет то просто
                 * говорим что тааких нет ( пуста)
                 * Если такие есть то мы берём значения из того что выбрали в спинера ФНГ
                 * в вайле смотрю  если есть такие студенты то создаю лист куда и добавляю айди всех этих студентов */
                ContentValues valuesPPFNG = new ContentValues();
                Integer Id_fng,m=0,kolvo_zapisi=0;
                Adapter_odnogo_dobavleni9 aod,aodS,aodPD,aodPFk;
                if (finalFlag_FNG){
                    //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
                    List<Adapter_odnogo_dobavleni9> student_id = new ArrayList<Adapter_odnogo_dobavleni9>();
                    //   Беру из бд выборку которая пришла (Select *)
                    query=db.rawQuery("SELECT * FROM studenti", null);
                    Adapter_odnogo_dobavleni9 aodFNG;
                    list_id_studentiv= new ArrayList<Integer>();
                    aodFNG = (Adapter_odnogo_dobavleni9) sp3.getSelectedItem();
                    Id_fng=aodFNG.getId();
                    while (query.moveToNext()) {
                        Integer id,id_groupa;
                        id= query.getInt(0);
                        id_groupa=query.getInt(4);


                        if(id_groupa.intValue()==Id_fng.intValue()){
                            list_id_studentiv.add(id);
                            flag_Student[0] =true;;
                        }
                    }
                    query.close();
                }
                //Конец Cтудента
                //Значение куда сохраняем значение
                String pismo=" ";
                ContentValues values_values = new ContentValues();
                /**
                 * Ну и вот если мы дошли и есть преподы предмет и студенты по выбранным ФНГ
                 * то заношу в таблицу журнал столько раз сколько студентов ппо ввыбранным ФНГ
                 * НО сначала проверя пустой ли журнал если пустой то могу добавить любого стдуента и прч пареметры
                 * если есть записи то смотрю в журнале все записи по таким ключам если совпадают вторичные ключи то занесение одинаковой записи невозможно
                 * есл не совпадает то заношу
                 * */
                //Проверка пустые ли таблицы ФНГ, если пустые то не даст добавить Журнал)
                if (flag_Student[0] && finalFlag_Predmet && finalFlag_Prepod) {

                    Boolean flg_while=true;
                    Integer flg_zah;
                    long id_zapisi;
                    Integer spiner_id_prepoda,spiner_id_predmeta;
                    query_J=db.rawQuery("SELECT * FROM jurnal", null);
                    while(query_J.moveToNext())
                    {flg_while=false;}
                    query_J.close();
                    for (int k=0;k<list_id_studentiv.size();k++ ) {


                        aodPD = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                        spiner_id_prepoda=aodPD.getId();
                        aodPFk = (Adapter_odnogo_dobavleni9) sp2.getSelectedItem();
                        spiner_id_predmeta=aodPFk.getId();

                        if(flg_while){


                            kolvo_zapisi++;
                            aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                            values_values.put("id_rabotnika", aod.getId());
                            aod = (Adapter_odnogo_dobavleni9) sp2.getSelectedItem();
                            values_values.put("id_predmeta", aod.getId());
                            // values_values.put("id_studentov", list_id_studentiv.get(k));
                            values_values.put("id_studentov", list_id_studentiv.get(k));
                    //Включаем добавление вторичных ключей
                            db.execSQL("PRAGMA foreign_keys=ON");
                            id_zapisi = db.insert("jurnal", null, values_values);
                            if (id_zapisi != -1)
                                Toast.makeText(getApplicationContext(), "В журнал добавлены записи", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                            flg_while=false;}

                        else{
                            query_PPS=db.rawQuery("SELECT * FROM jurnal WHERE id_rabotnika =? and id_predmeta =? and id_studentov=?",
                                    new String[] {spiner_id_prepoda.toString(),spiner_id_predmeta.toString(),list_id_studentiv.get(k).toString()});
                            try {
                                query_PPS.moveToFirst();
                                flg_zah=query_PPS.getInt(0);
                            }
                            catch (Exception e){

                                kolvo_zapisi++;
                                aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                                values_values.put("id_rabotnika", aod.getId());
                                aod = (Adapter_odnogo_dobavleni9) sp2.getSelectedItem();
                                values_values.put("id_predmeta", aod.getId());
                                // values_values.put("id_studentov", list_id_studentiv.get(k));
                                values_values.put("id_studentov", list_id_studentiv.get(k));
//Включаем добавление вторичных ключей
                                db.execSQL("PRAGMA foreign_keys=ON");
                                id_zapisi = db.insert("jurnal", null, values_values);
                                if (id_zapisi != -1)
                                    Toast.makeText(getApplicationContext(), "В журнал добавлены записи", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();

                            }
                            //////////////////////////////

                            //   query_Predmet.close(); query_Prepod.close(); query_S.close();

                            query_PPS.close();    }


                    }


                } else if (!finalFlag_Predmet )
                    Toast.makeText(getApplicationContext(), "Занесите информацию в Добавление предмета", Toast.LENGTH_LONG).show();
                else if(!finalFlag_Prepod )   Toast.makeText(getApplicationContext(), "Занесите информацию в Добавление Преподавателя", Toast.LENGTH_LONG).show();
                else if (!flag_Student[0] )
                    Toast.makeText(getApplicationContext(), "Нету Студентов по ФНГ", Toast.LENGTH_LONG).show();
                //  }


                if (kolvo_zapisi.intValue()!=0) pismo=" Добавлено: " + kolvo_zapisi.toString() + "  записи";
                else if(flag_Student[0]) pismo=" Такие записи в Журнале уже существуют";
                Toast.makeText(getApplicationContext(),pismo,Toast.LENGTH_SHORT).show();
                db.close();
              Pokazat_Jurnal();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    /** Конец.Добавления Журнала*/


    public void Redactirovanie_jurnal(Integer id){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Jurnal.this);
        subjectDialog.setTitle("Произвести Удаление");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.yes, null);
        subjectDialog.setView(vv);
        subjectDialog.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id_zapisi = 0;
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        if (id > 0) {
                            db.execSQL("PRAGMA foreign_keys=ON");
                            try {
                                id_zapisi = db.delete("jurnal", "id = ?", new String[]{String.valueOf(id)});
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Есть запись в фиксации", Toast.LENGTH_LONG).show();
                            }
                            if (id_zapisi == 1)
                                Toast.makeText(getApplicationContext(), "Произошло удаление", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Ошибка удаления", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show();
                        }

                        db.close();
                        Pokazat_Jurnal();

                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        subjectDialog.show();

    }
}
