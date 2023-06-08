package com.example.jurnald;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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

public class Smotret_Propyski extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Using_adater_dobavlenie> spisok,spisok_1_pol9;
    Adapter_dobavlenie adapter_dobavlenie;
    ArrayList<Using_adapter_dvapol9_dobavleni9> spisok_pokaz_propyskov;
    Adapter_dvapol9_dobavleni9 adapter_dvapol9_dobavleni9;


    ArrayList<Using_adapter_pokazat_daty_propyskov> spisok_pokaz_daty_prop,spisok_pokaz_daty_prop1;
    Adapter_pokazat_daty_propyskov adapter_pokazat_daty_propyskov,adapter_pokazat_daty_propyskov1 ;
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


        setContentView(R.layout.activity_smotret_propyski);


        toolbar = (Toolbar) findViewById(R.id.my_toolbar_smotret_propyski);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout_sp);
        NavigationView navigationView = findViewById(R.id.nav_view_bokovoe_okno_sp);
        // Log.i("Mainn"," "+navigationView);

        navigationView.setNavigationItemSelectedListener(this);
        //Копочка для выдвижения меню слева
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        SpinnerPropyskov();
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

    public void SpinnerPropyskov(){
        List<Adapter_odnogo_dobavleni9> prepod_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> predmet_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> fng_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> fng_name2 = new ArrayList<Adapter_odnogo_dobavleni9>();
        List<Adapter_odnogo_dobavleni9> spinner_pokaz = new ArrayList<Adapter_odnogo_dobavleni9>();
        Set<Integer> fng_name1 = new HashSet<Integer>();
        final Spinner sp1 = (Spinner) findViewById(R.id.spinner_propyskov);
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

        Using_adapter_tri_dobavleni9 uatd = new Using_adapter_tri_dobavleni9(Smotret_Propyski.this,jurnal_id_name);
        sp1.setAdapter(uatd);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                AdapterView<?> parent_onItemSelected=parent;
                View view_onItemSelected=view;
                int pos_onItemSelected=pos;
                long id_onItemSelected=id;
                Pokaz_vipad_spisok_jurnala();

                //Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
    }
    public void Pokaz_vipad_spisok_jurnala(){

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_F;
        final Spinner sp1 = (Spinner) findViewById(R.id.spinner_propyskov);
        Set<Integer> fng_name1 = new HashSet<Integer>();
        Adapter_tri_dobavleni9 a3d;
        a3d = (Adapter_tri_dobavleni9) sp1.getSelectedItem();
        String propyski;
        Integer kolvo=0,nebilo=0;
        spisok=new ArrayList<>();
        spisok_pokaz_propyskov = new ArrayList<>();
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
            nebilo=0;
            query_F=db.rawQuery("SELECT * FROM ficsation WHERE id_nomer_zapisi=?",new String[] {String.valueOf(query.getInt(0))});
            while (query_F.moveToNext())
            {
                flag_date=true;
            }
            query_F.close();
            query_F=db.rawQuery("SELECT * FROM ficsation WHERE id_nomer_zapisi=?",new String[] {String.valueOf(query.getInt(0))});
            if(flag_date) {
                while (query_F.moveToNext())
                {

                if (query_F.getString(3).equals("0"))
                    nebilo++;

                }

                query_F.close();
            }
            kolvo++;
            spisok_pokaz_propyskov.add(new Using_adapter_dvapol9_dobavleni9(query.getInt(0), query.getString(5) + " " + query.getString(6), nebilo));


        }
        query.close();
        db.close();;

    TextView tv = (TextView) findViewById(R.id.osm_kolvo) ;
    tv.setText("Количетсво студентов: " + kolvo);
    adapter_dvapol9_dobavleni9= new Adapter_dvapol9_dobavleni9(Smotret_Propyski.this, spisok_pokaz_propyskov);

    ListView lv = (ListView) findViewById(R.id.otobrajenie_LV_propyski);
    lv.setAdapter(adapter_dvapol9_dobavleni9);
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
            Cursor query,query_S;
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            query=db.rawQuery("SELECT distinct J.id,S.id," +
                            "S.last_name, S.first_name" +
                            " FROM jurnal J " +
                            "inner Join studenti S on J.id_studentov=S.id " +
                            "WHERE J.id=?"
                    ,new String[] {String.valueOf(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.propysklist_id)).getText().toString()))});
            query.moveToFirst();
            String name=query.getString(2) + " " +query.getString(3);
            Integer id_jurnala_zapisi=query.getInt(0);
            query.close();
            db.close();
         //   Log.i("Mainn",id_jurnala_zapisi +" " + name);

            goNewView(id_jurnala_zapisi,name);

        }
    });
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Pokaz_vipad_spisok_jurnala();
        // Загрузите изменения, учитывая то, что Активность
        // уже стала "видимой" в рамках данного процесса.
    }

    public void goNewView(Integer id_zapisi_jurnal, String name){
                // Говорим между какими Activity будет происходить связь
                Intent intent = new Intent(this, Smotret_Spisok_Posesheni9.class);

                // указываем первым параметром ключ, а второе значение
                // по ключу мы будем получать значение с Intent

                intent.putExtra("name", name);
                intent.putExtra("id_zapisi_jurnal", id_zapisi_jurnal.toString());

                // показываем новое Activity
                startActivity(intent);

    }


}