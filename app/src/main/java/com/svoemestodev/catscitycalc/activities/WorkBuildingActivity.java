package com.svoemestodev.catscitycalc.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.svoemestodev.catscitycalc.GlobalApplication;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.adapters.ListBuildingAdapter;
import com.svoemestodev.catscitycalc.adapters.ListCarsAdapter;
import com.svoemestodev.catscitycalc.citycalcclasses.Area;
import com.svoemestodev.catscitycalc.citycalcclasses.CCABuilding;
import com.svoemestodev.catscitycalc.citycalcclasses.CCAGame;
import com.svoemestodev.catscitycalc.classes.Building;
import com.svoemestodev.catscitycalc.classes.Car;
import com.svoemestodev.catscitycalc.classes.CarUtils;
import com.svoemestodev.catscitycalc.database.DbCar;
import com.svoemestodev.catscitycalc.database.DbTeamUser;
import com.svoemestodev.catscitycalc.database.UserRole;
import com.svoemestodev.catscitycalc.utils.ObservableString;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkBuildingActivity extends AppCompatActivity {

    public static int mainSlot;

    AdView wb_ad_banner;
    ImageView wb_iv_bxx_icon;
    ImageView wb_iv_bxx_name;
    TextView wb_tv_bxx_x2;
    TextView wb_tv_bxx_points;
    TextView wb_tv_bxx_slots;
    TextView wb_tv_bxx_slots_our;
    TextView wb_tv_bxx_slots_empty;
    TextView wb_tv_bxx_slots_enemy;
    ImageView wb_iv_bxx_progress;

    TextView wb_tv_cars_in_building;
    ListView wb_lv_cars_in_building;
    TextView wb_tv_cars_task_building;
    ListView wb_lv_cars_task_building;



    private static List<DbTeamUser> listDbTeamUsers = new ArrayList<>();
    private static List<Car> returnedListCars = new ArrayList<>();
    private static List<Car> returnedListCarsInBuilding = new ArrayList<>();
    private static List<Car> returnedListCarsTaskBuilding = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_building);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        TaskGetUsersCars tguc = new TaskGetUsersCars();
        tguc.execute();

        initializeViews();

        displayRecords();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        wb_ad_banner.loadAd(adRequest);
        
    }

    private void displayRecords() {

        Context context = getApplicationContext();

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);
        int color_bxx_mayX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_mayX2_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_mayX2_main), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_mayX2), 16)));
        int color_bxx_isX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_isX2_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_isX2_main), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_isX2), 16)));

        int slots = 0, slots_our = 0, slots_empty = 0, slots_enemy = 0;

        CCAGame ccaGame = GameActivity.mainCCAGame;
        boolean isPresent_bxx;
        int ccaGame_getSlots_bxx_our = 0;
        int ccaGame_getSlots_bxx_empty = 0;
        int ccaGame_getSlots_bxx_enemy = 0;
        int ccaGame_getSlots_bxx = 0;
        String ccaGame_getSlots_bxx_our_toView = "";
        String ccaGame_getSlots_bxx_enemy_toView = "";
        boolean ccaGame_isBuildingIsOur_bxx = false;
        boolean ccaGame_isBuildingIsEnemy_bxx = false;
        boolean ccaGame_isBuildingIsEmpty_bxx = false;
        boolean ccaGame_isX2_bxx = false;
        boolean ccaGame_isMayX2_bxx = false;
        int ccaGame_getOur_points_bxx = 0;
        int ccaGame_getEnemy_points_bxx = 0;
        int res_bxx_blue = 0;
        int res_bxx_red = 0;
        int res_bxx_gray = 0;
        
        CCABuilding ccaBXX = null;
        switch (mainSlot) {
            case 1:
                ccaBXX = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BLT);
                ccaGame_getSlots_bxx_our = ccaGame.getSlots_blt_our();
                ccaGame_getSlots_bxx_empty = ccaGame.getSlots_blt_empty();
                ccaGame_getSlots_bxx_enemy = ccaGame.getSlots_blt_enemy();
                ccaGame_getSlots_bxx = ccaGame.getSlots_blt();
                ccaGame_getSlots_bxx_our_toView = ccaGame.getSlots_blt_our_toView();
                ccaGame_getSlots_bxx_enemy_toView = ccaGame.getSlots_blt_enemy_toView();
                ccaGame_isBuildingIsOur_bxx = ccaGame.isBuildingIsOur_blt();
                ccaGame_isBuildingIsEnemy_bxx = ccaGame.isBuildingIsEnemy_blt();
                ccaGame_isBuildingIsEmpty_bxx = ccaGame.isBuildingIsEmpty_blt();
                ccaGame_isX2_bxx = ccaGame.isX2_blt();
                ccaGame_isMayX2_bxx = ccaGame.isMayX2_blt();
                ccaGame_getOur_points_bxx = ccaGame.getOur_points_blt();
                ccaGame_getEnemy_points_bxx = ccaGame.getEnemy_points_blt();
                res_bxx_blue = R.drawable.ic_blt_blue;
                res_bxx_red = R.drawable.ic_blt_red;
                res_bxx_gray = R.drawable.ic_blt_gray;
                break;
            case 2:
                ccaBXX = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BLC);
                ccaGame_getSlots_bxx_our = ccaGame.getSlots_blc_our();
                ccaGame_getSlots_bxx_empty = ccaGame.getSlots_blc_empty();
                ccaGame_getSlots_bxx_enemy = ccaGame.getSlots_blc_enemy();
                ccaGame_getSlots_bxx = ccaGame.getSlots_blc();
                ccaGame_getSlots_bxx_our_toView = ccaGame.getSlots_blc_our_toView();
                ccaGame_getSlots_bxx_enemy_toView = ccaGame.getSlots_blc_enemy_toView();
                ccaGame_isBuildingIsOur_bxx = ccaGame.isBuildingIsOur_blc();
                ccaGame_isBuildingIsEnemy_bxx = ccaGame.isBuildingIsEnemy_blc();
                ccaGame_isBuildingIsEmpty_bxx = ccaGame.isBuildingIsEmpty_blc();
                ccaGame_isX2_bxx = ccaGame.isX2_blc();
                ccaGame_isMayX2_bxx = ccaGame.isMayX2_blc();
                ccaGame_getOur_points_bxx = ccaGame.getOur_points_blc();
                ccaGame_getEnemy_points_bxx = ccaGame.getEnemy_points_blc();
                res_bxx_blue = R.drawable.ic_blc_blue;
                res_bxx_red = R.drawable.ic_blc_red;
                res_bxx_gray = R.drawable.ic_blc_gray;
                break;
            case 3:
                ccaBXX = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BLB);
                ccaGame_getSlots_bxx_our = ccaGame.getSlots_blb_our();
                ccaGame_getSlots_bxx_empty = ccaGame.getSlots_blb_empty();
                ccaGame_getSlots_bxx_enemy = ccaGame.getSlots_blb_enemy();
                ccaGame_getSlots_bxx = ccaGame.getSlots_blb();
                ccaGame_getSlots_bxx_our_toView = ccaGame.getSlots_blb_our_toView();
                ccaGame_getSlots_bxx_enemy_toView = ccaGame.getSlots_blb_enemy_toView();
                ccaGame_isBuildingIsOur_bxx = ccaGame.isBuildingIsOur_blb();
                ccaGame_isBuildingIsEnemy_bxx = ccaGame.isBuildingIsEnemy_blb();
                ccaGame_isBuildingIsEmpty_bxx = ccaGame.isBuildingIsEmpty_blb();
                ccaGame_isX2_bxx = ccaGame.isX2_blb();
                ccaGame_isMayX2_bxx = ccaGame.isMayX2_blb();
                ccaGame_getOur_points_bxx = ccaGame.getOur_points_blb();
                ccaGame_getEnemy_points_bxx = ccaGame.getEnemy_points_blb();
                res_bxx_blue = R.drawable.ic_blb_blue;
                res_bxx_red = R.drawable.ic_blb_red;
                res_bxx_gray = R.drawable.ic_blb_gray;
                break;
            case 4:
                ccaBXX = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BRT);
                ccaGame_getSlots_bxx_our = ccaGame.getSlots_brt_our();
                ccaGame_getSlots_bxx_empty = ccaGame.getSlots_brt_empty();
                ccaGame_getSlots_bxx_enemy = ccaGame.getSlots_brt_enemy();
                ccaGame_getSlots_bxx = ccaGame.getSlots_brt();
                ccaGame_getSlots_bxx_our_toView = ccaGame.getSlots_brt_our_toView();
                ccaGame_getSlots_bxx_enemy_toView = ccaGame.getSlots_brt_enemy_toView();
                ccaGame_isBuildingIsOur_bxx = ccaGame.isBuildingIsOur_brt();
                ccaGame_isBuildingIsEnemy_bxx = ccaGame.isBuildingIsEnemy_brt();
                ccaGame_isBuildingIsEmpty_bxx = ccaGame.isBuildingIsEmpty_brt();
                ccaGame_isX2_bxx = ccaGame.isX2_brt();
                ccaGame_isMayX2_bxx = ccaGame.isMayX2_brt();
                ccaGame_getOur_points_bxx = ccaGame.getOur_points_brt();
                ccaGame_getEnemy_points_bxx = ccaGame.getEnemy_points_brt();
                res_bxx_blue = R.drawable.ic_brt_blue;
                res_bxx_red = R.drawable.ic_brt_red;
                res_bxx_gray = R.drawable.ic_brt_gray;
                break;
            case 5:
                ccaBXX = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BRC);
                ccaGame_getSlots_bxx_our = ccaGame.getSlots_brc_our();
                ccaGame_getSlots_bxx_empty = ccaGame.getSlots_brc_empty();
                ccaGame_getSlots_bxx_enemy = ccaGame.getSlots_brc_enemy();
                ccaGame_getSlots_bxx = ccaGame.getSlots_brc();
                ccaGame_getSlots_bxx_our_toView = ccaGame.getSlots_brc_our_toView();
                ccaGame_getSlots_bxx_enemy_toView = ccaGame.getSlots_brc_enemy_toView();
                ccaGame_isBuildingIsOur_bxx = ccaGame.isBuildingIsOur_brc();
                ccaGame_isBuildingIsEnemy_bxx = ccaGame.isBuildingIsEnemy_brc();
                ccaGame_isBuildingIsEmpty_bxx = ccaGame.isBuildingIsEmpty_brc();
                ccaGame_isX2_bxx = ccaGame.isX2_brc();
                ccaGame_isMayX2_bxx = ccaGame.isMayX2_brc();
                ccaGame_getOur_points_bxx = ccaGame.getOur_points_brc();
                ccaGame_getEnemy_points_bxx = ccaGame.getEnemy_points_brc();
                res_bxx_blue = R.drawable.ic_brc_blue;
                res_bxx_red = R.drawable.ic_brc_red;
                res_bxx_gray = R.drawable.ic_brc_gray;
                break;
            case 6:
                ccaBXX = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BRB);
                ccaGame_getSlots_bxx_our = ccaGame.getSlots_brb_our();
                ccaGame_getSlots_bxx_empty = ccaGame.getSlots_brb_empty();
                ccaGame_getSlots_bxx_enemy = ccaGame.getSlots_brb_enemy();
                ccaGame_getSlots_bxx = ccaGame.getSlots_brb();
                ccaGame_getSlots_bxx_our_toView = ccaGame.getSlots_brb_our_toView();
                ccaGame_getSlots_bxx_enemy_toView = ccaGame.getSlots_brb_enemy_toView();
                ccaGame_isBuildingIsOur_bxx = ccaGame.isBuildingIsOur_brb();
                ccaGame_isBuildingIsEnemy_bxx = ccaGame.isBuildingIsEnemy_brb();
                ccaGame_isBuildingIsEmpty_bxx = ccaGame.isBuildingIsEmpty_brb();
                ccaGame_isX2_bxx = ccaGame.isX2_brb();
                ccaGame_isMayX2_bxx = ccaGame.isMayX2_brb();
                ccaGame_getOur_points_bxx = ccaGame.getOur_points_brb();
                ccaGame_getEnemy_points_bxx = ccaGame.getEnemy_points_brb();
                res_bxx_blue = R.drawable.ic_brb_blue;
                res_bxx_red = R.drawable.ic_brb_red;
                res_bxx_gray = R.drawable.ic_brb_gray;
                break;
            default:
        }

        int color_progress_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_our_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_our), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_our_main), 16)));
        int color_progress_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_enemy_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_enemy), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_enemy_main), 16)));
        int color_progress_empty = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_empty_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_empty), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_empty_main), 16)));
        int progressBitmapWidth = 300;
        int progressBitmapHeight = 20;
        
        if (ccaBXX != null) wb_iv_bxx_name.setImageBitmap(ccaBXX.getBmpSrc());

        wb_iv_bxx_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                new int[]{ccaGame_getSlots_bxx_our, ccaGame_getSlots_bxx_empty, ccaGame_getSlots_bxx_enemy}));

        wb_tv_bxx_slots.setText(String.valueOf(ccaGame_getSlots_bxx));
        wb_tv_bxx_slots_our.setText(String.valueOf(ccaGame_getSlots_bxx_our_toView));
        wb_tv_bxx_slots_empty.setText(String.valueOf(ccaGame_getSlots_bxx_empty));
        wb_tv_bxx_slots_enemy.setText(String.valueOf(ccaGame_getSlots_bxx_enemy_toView));

        if (ccaGame_isBuildingIsOur_bxx) {
            wb_tv_bxx_points.setText("+" + ccaGame_getOur_points_bxx);
            wb_tv_bxx_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
            wb_iv_bxx_icon.setImageDrawable(getDrawable(res_bxx_blue));
        } else if (ccaGame_isBuildingIsEnemy_bxx) {
            wb_tv_bxx_points.setText("+" + ccaGame_getEnemy_points_bxx);
            wb_tv_bxx_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
            wb_iv_bxx_icon.setImageDrawable(getDrawable(res_bxx_red));
        } else if (ccaGame_isBuildingIsEmpty_bxx) {
            wb_tv_bxx_points.setText("");
            wb_tv_bxx_points.setBackgroundColor(0xFFFFFFFF);
            wb_iv_bxx_icon.setImageDrawable(getDrawable(res_bxx_gray));
        }
        if (ccaGame_isX2_bxx) {
            wb_tv_bxx_x2.setText("X2");
            wb_tv_bxx_x2.setBackgroundColor(color_bxx_isX2);
        } else {
            if (ccaGame_isMayX2_bxx) {
                wb_tv_bxx_x2.setText("X2");
                wb_tv_bxx_x2.setBackgroundColor(color_bxx_mayX2);
            } else {
                wb_tv_bxx_x2.setText("");
                wb_tv_bxx_x2.setBackgroundColor(0xFFFFFFFF);
            }
        }

        TaskGetCarsInBuilding taskGetCarsInBuilding = new TaskGetCarsInBuilding();
        taskGetCarsInBuilding.execute();

        TaskGetCarsTaskBuilding taskGetCarsTaskBuilding = new TaskGetCarsTaskBuilding();
        taskGetCarsTaskBuilding.execute();

    }
    
    private void initializeViews() {
        wb_ad_banner = findViewById(R.id.wb_ad_banner);
        wb_iv_bxx_icon = findViewById(R.id.wb_iv_bxx_icon);
        wb_iv_bxx_name = findViewById(R.id.wb_iv_bxx_name);
        wb_tv_bxx_x2 = findViewById(R.id.wb_tv_bxx_x2);
        wb_tv_bxx_points = findViewById(R.id.wb_tv_bxx_points);
        wb_tv_bxx_slots = findViewById(R.id.wb_tv_bxx_slots);
        wb_tv_bxx_slots_our = findViewById(R.id.wb_tv_bxx_slots_our);
        wb_tv_bxx_slots_empty = findViewById(R.id.wb_tv_bxx_slots_empty);
        wb_tv_bxx_slots_enemy = findViewById(R.id.wb_tv_bxx_slots_enemy);
        wb_iv_bxx_progress = findViewById(R.id.wb_iv_bxx_progress);
        wb_tv_cars_in_building = findViewById(R.id.wb_tv_cars_in_building);
        wb_lv_cars_in_building = findViewById(R.id.wb_lv_cars_in_building);
        wb_tv_cars_task_building = findViewById(R.id.wb_tv_cars_task_building);
        wb_lv_cars_task_building = findViewById(R.id.wb_lv_cars_task_building);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // индекс нажатой кнопки
        if (id == android.R.id.home) { //если в шапке нажата кнопка "Назад"
            onBackPressed();    // вызываем метод "Назад"
            return true;        // возвращаем Истину
        }
        return super.onOptionsItemSelected(item);   // возвращаем супер-метод
    }

    public void getCars(View view) {

        List<Car> resultedList = new ArrayList<>();
        List<Car> listCarsToAdapter = returnedListCars;

        AlertDialog.Builder builder = new AlertDialog.Builder(WorkBuildingActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Cars");

        final ListCarsAdapter arrayAdapter = new ListCarsAdapter(WorkBuildingActivity.this, listCarsToAdapter, true);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (int i = 0; i < listCarsToAdapter.size(); i++) {
                    Car item = arrayAdapter.getItem(i);
                    if (item.isChecked()) {
                        resultedList.add(item);
                    }
                }

                Map<String, Object> map = new HashMap<>();
                map.put("carBuildingTask", mainSlot);

                if (resultedList.size() >0) {
                    for (Car car: resultedList) {
                        DocumentReference docRefCar = GameActivity.fbDb.collection("users").document(car.getUserUID()).collection("userCars").document("car"+car.getSlot());
                        docRefCar.update(map);
                        Log.i("WBA", car.toString());
                    }
                }

                TaskGetUsersCars tguc = new TaskGetUsersCars();
                tguc.execute();

                displayRecords();

            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();


    }

    class TaskGetUsersCars extends AsyncTask<Void, Void, List<Car>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            returnedListCars = new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<Car> cars) {
            super.onPostExecute(cars);

        }

        @Override
        protected List<Car> doInBackground(Void... voids) {


            CollectionReference crTeamUsers = GameActivity.fbDb.collection("teams").document(GameActivity.mainDbTeam.getTeamID()).collection("teamUsers");
            crTeamUsers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            listDbTeamUsers = task.getResult().toObjects(DbTeamUser.class);

                            for (DbTeamUser dbTeamUser: listDbTeamUsers) {

                                final DbTeamUser dbTU = dbTeamUser;

                                for (int slot = 1; slot <= 3; slot++) {
                                    DocumentReference docRefCar = GameActivity.fbDb.collection("users").document(dbTU.getUserID()).collection("userCars").document("car"+slot);
                                    int finalSlot = slot;
                                    docRefCar.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                            if (e != null) {
                                                return;
                                            }
                                            if (documentSnapshot != null && documentSnapshot.exists()) {

                                                if (GameActivity.mainCityCalc != null) {
                                                    Car car;
                                                    if (dbTU.getUserID().equals(GameActivity.fbUser.getUid())) {
                                                        car = Car.loadCar(finalSlot);
                                                    } else {
                                                        car = Car.loadCar(finalSlot, dbTU.getUserID());
                                                    }

                                                    DbCar dbCar = new DbCar(documentSnapshot);

                                                    car.setSlot(dbCar.getCarSlot());
                                                    car.setName(dbCar.getCarName());
                                                    car.setHealth(dbCar.getCarHealth());
                                                    car.setShield(dbCar.getCarShield());
                                                    car.setBuilding(dbCar.getCarBuilding());
                                                    car.setBuildingTask(dbCar.getCarBuildingTask());
                                                    car.setRepair(dbCar.getCarRepair());
                                                    car.setTeamID(dbCar.getTeamID());
                                                    car.setUserUID(dbCar.getUserUID());
                                                    car.setUserNIC(dbTU.getUserNIC());

                                                    if (car.getBuilding() == -1) {
                                                        Log.i("ADD CAR TO LIST", car.toString());
                                                        returnedListCars.add(car);
                                                    }

                                                }

                                            }
                                        }
                                    });
                                }

                            }


                        }
                    }
                }

            });

            return returnedListCars;

        }
    }


    class TaskGetCarsInBuilding extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            returnedListCarsInBuilding = new ArrayList<>();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            CollectionReference crTeamUsers = GameActivity.fbDb.collection("teams").document(GameActivity.mainDbTeam.getTeamID()).collection("teamUsers");
            crTeamUsers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            listDbTeamUsers = task.getResult().toObjects(DbTeamUser.class);

                            for (DbTeamUser dbTeamUser: listDbTeamUsers) {

                                final DbTeamUser dbTU = dbTeamUser;

                                for (int slot = 1; slot <= 3; slot++) {

                                    DocumentReference docRefCar = GameActivity.fbDb.collection("users").document(dbTU.getUserID()).collection("userCars").document("car"+slot);
                                    int finalSlot = slot;
                                    docRefCar.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                            if (e != null) {
                                                return;
                                            }
                                            if (documentSnapshot != null && documentSnapshot.exists()) {

                                                if (GameActivity.mainCityCalc != null) {
                                                    Car car;
                                                    if (dbTU.getUserID().equals(GameActivity.fbUser.getUid())) {
                                                        car = Car.loadCar(finalSlot);
                                                    } else {
                                                        car = Car.loadCar(finalSlot, dbTU.getUserID());
                                                    }

                                                    DbCar dbCar = new DbCar(documentSnapshot);

                                                    car.setSlot(dbCar.getCarSlot());
                                                    car.setName(dbCar.getCarName());
                                                    car.setHealth(dbCar.getCarHealth());
                                                    car.setShield(dbCar.getCarShield());
                                                    car.setBuilding(dbCar.getCarBuilding());
                                                    car.setBuildingTask(dbCar.getCarBuildingTask());
                                                    car.setRepair(dbCar.getCarRepair());
                                                    car.setTeamID(dbCar.getTeamID());
                                                    car.setUserUID(dbCar.getUserUID());
                                                    car.setUserNIC(dbTU.getUserNIC());

                                                    if (car.getBuilding() == mainSlot) {
                                                        if (!CarUtils.carIsPresentInList(returnedListCarsInBuilding, car)) {
                                                            returnedListCarsInBuilding.add(car);
                                                            ListCarsAdapter arrayAdapter = new ListCarsAdapter(WorkBuildingActivity.this, returnedListCarsInBuilding, false);
                                                            wb_lv_cars_in_building.setAdapter(arrayAdapter);
                                                        }
                                                        if (CarUtils.carIsPresentInList(returnedListCarsTaskBuilding, car)) {
                                                            returnedListCarsTaskBuilding = CarUtils.removeCarFromList(returnedListCarsTaskBuilding, car);
                                                            ListCarsAdapter arrayAdapter = new ListCarsAdapter(WorkBuildingActivity.this, returnedListCarsTaskBuilding, false);
                                                            wb_lv_cars_task_building.setAdapter(arrayAdapter);
                                                        }
                                                    }

                                                }

                                            }
                                        }
                                    });

                                }

                            }


                        }
                    }
                }

            });

            return null;

        }
    }




    class TaskGetCarsTaskBuilding extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            returnedListCarsTaskBuilding = new ArrayList<>();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            CollectionReference crTeamUsers = GameActivity.fbDb.collection("teams").document(GameActivity.mainDbTeam.getTeamID()).collection("teamUsers");
            crTeamUsers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            listDbTeamUsers = task.getResult().toObjects(DbTeamUser.class);

                            for (DbTeamUser dbTeamUser: listDbTeamUsers) {

                                final DbTeamUser dbTU = dbTeamUser;

                                for (int slot = 1; slot <= 3; slot++) {
                                    DocumentReference docRefCar = GameActivity.fbDb.collection("users").document(dbTU.getUserID()).collection("userCars").document("car"+slot);
                                    int finalSlot = slot;
                                    docRefCar.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                            if (e != null) {
                                                return;
                                            }
                                            if (documentSnapshot != null && documentSnapshot.exists()) {

                                                if (GameActivity.mainCityCalc != null) {
                                                    Car car;
                                                    if (dbTU.getUserID().equals(GameActivity.fbUser.getUid())) {
                                                        car = Car.loadCar(finalSlot);
                                                    } else {
                                                        car = Car.loadCar(finalSlot, dbTU.getUserID());
                                                    }

                                                    DbCar dbCar = new DbCar(documentSnapshot);

                                                    car.setSlot(dbCar.getCarSlot());
                                                    car.setName(dbCar.getCarName());
                                                    car.setHealth(dbCar.getCarHealth());
                                                    car.setShield(dbCar.getCarShield());
                                                    car.setBuilding(dbCar.getCarBuilding());
                                                    car.setBuildingTask(dbCar.getCarBuildingTask());
                                                    car.setRepair(dbCar.getCarRepair());
                                                    car.setTeamID(dbCar.getTeamID());
                                                    car.setUserUID(dbCar.getUserUID());
                                                    car.setUserNIC(dbTU.getUserNIC());

                                                    if (car.getBuildingTask() == mainSlot && car.getBuildingTask() != car.getBuilding()) {
                                                        if (!CarUtils.carIsPresentInList(returnedListCarsTaskBuilding, car)) {
                                                            returnedListCarsTaskBuilding.add(car);
                                                            ListCarsAdapter arrayAdapter = new ListCarsAdapter(WorkBuildingActivity.this, returnedListCarsTaskBuilding, false);
                                                            wb_lv_cars_task_building.setAdapter(arrayAdapter);
                                                        }

                                                    }

                                                }

                                            }
                                        }
                                    });

                                }


                            }


                        }
                    }
                }

            });

            return null;

        }
    }

}