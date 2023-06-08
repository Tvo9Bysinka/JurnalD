package com.example.jurnald;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.jurnald.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//Подключение к БД

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Создание таблиц БД
        //Факультет
        db.execSQL("CREATE TABLE IF NOT EXISTS facultet " +
                    "(id integer primary key autoincrement, name_faculteta TEXT)");

        //Направлениеу
        db.execSQL("CREATE TABLE IF NOT EXISTS napravlenie " +
                "(id integer primary key autoincrement, name_napravlenie TEXT,"+
                "id_facultet integer,foreign key (id_facultet) references facultet (id))");

        //Группа
        db.execSQL("CREATE TABLE IF NOT EXISTS groupa " +
                "(id integer primary key autoincrement, name_group TEXT,"+
                "id_napravlenie integer,foreign key (id_napravlenie) references napravlenie (id))");

        //Студенты
        db.execSQL("CREATE TABLE IF NOT EXISTS studenti " +
                "(id integer primary key autoincrement,  last_name TEXT,first_name TEXT, middle_name TEXT, " +
                "id_groupa integer,foreign key (id_groupa) references groupa (id))");

        //Форма контроля
        db.execSQL("CREATE TABLE IF NOT EXISTS forma_kontrol9 " +
                "(id integer primary key autoincrement, vid_formi TEXT)");

        //Предмет
        db.execSQL("CREATE TABLE IF NOT EXISTS predmet " +
                "(id integer primary key autoincrement, name_premeta TEXT," +
                "id_forma_kontrol9 integer, foreign key (id_forma_kontrol9) references forma_kontrol9 (id))");

        //ПРеподаватель
        db.execSQL("CREATE TABLE IF NOT EXISTS rabotnik " +
                "(id integer primary key autoincrement,last_name TEXT, first_name TEXT, middle_name TEXT)");

        //Журнал
        db.execSQL("CREATE TABLE IF NOT EXISTS jurnal " +
                "(id integer primary key autoincrement, " +
                "id_rabotnika integer,id_predmeta integer, id_studentov integer," +
                "foreign key (id_rabotnika) references rabotnik (id)," +
                "foreign key (id_predmeta) references predmet (id)," +
                "foreign key (id_studentov) references studenti (id) ON DELETE CASCADE)");

        //Фиксация
        db.execSQL("CREATE TABLE IF NOT EXISTS ficsation " +
                "(id integer primary key autoincrement, " +
                "id_nomer_zapisi integer," +
                "data_propyskov DATE," +
                "propyski Boolean," +
                "opisanie TEXT," +
                "foreign key (id_nomer_zapisi) references jurnal (id) ON DELETE CASCADE)");



        //context delete db удвление бд
        /*
        Context context =  this;
        context.deleteDatabase("app.db");
        */
        //query.close();
        db.close();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);



    }

}
