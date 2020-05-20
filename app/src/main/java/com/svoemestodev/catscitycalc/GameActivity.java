package com.svoemestodev.catscitycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    // Game views
    Switch sw_ga_listen_new_file;
    TextView tv_ga_start_game_time;
    TextView tv_ga_end_game_time;
    TextView tv_ga_status;
    TextView tv_ga_vs;
    
    // Time views
    TextView tv_ga_total_time;
    TextView tv_ga_early_win;
    
    // Our team views
    ImageView iv_ga_our_team_name;
    TextView tv_ga_our_increase;
    TextView tv_ga_our_points;
    TextView tv_ga_our_end_time;
    
    // Enemy team views
    ImageView iv_ga_enemy_team_name;
    TextView tv_ga_enemy_increase;
    TextView tv_ga_enemy_points;
    TextView tv_ga_enemy_end_time;
    
    // BLT views
    ImageView iv_ga_blt_name;
    TextView tv_ga_blt_x2;
    TextView tv_ga_blt_points;
    TextView tv_ga_blt_slots;
    TextView tv_ga_blt_slots_our;
    TextView tv_ga_blt_slots_empty;
    TextView tv_ga_blt_slots_enemy;
    ImageView iv_ga_blt_car_black;
    ImageView iv_ga_blt_car_our;
    ImageView iv_ga_blt_car_empty;
    ImageView iv_ga_blt_car_enemy;
    ImageView iv_ga_blt_progress;

    // BLC views
    ImageView iv_ga_blc_name;
    TextView tv_ga_blc_x2;
    TextView tv_ga_blc_points;
    TextView tv_ga_blc_slots;
    TextView tv_ga_blc_slots_our;
    TextView tv_ga_blc_slots_empty;
    TextView tv_ga_blc_slots_enemy;
    ImageView iv_ga_blc_car_black;
    ImageView iv_ga_blc_car_our;
    ImageView iv_ga_blc_car_empty;
    ImageView iv_ga_blc_car_enemy;
    ImageView iv_ga_blc_progress;

    // BLB views
    ImageView iv_ga_blb_name;
    TextView tv_ga_blb_x2;
    TextView tv_ga_blb_points;
    TextView tv_ga_blb_slots;
    TextView tv_ga_blb_slots_our;
    TextView tv_ga_blb_slots_empty;
    TextView tv_ga_blb_slots_enemy;
    ImageView iv_ga_blb_car_black;
    ImageView iv_ga_blb_car_our;
    ImageView iv_ga_blb_car_empty;
    ImageView iv_ga_blb_car_enemy;
    ImageView iv_ga_blb_progress;

    // BRT views
    ImageView iv_ga_brt_name;
    TextView tv_ga_brt_x2;
    TextView tv_ga_brt_points;
    TextView tv_ga_brt_slots;
    TextView tv_ga_brt_slots_our;
    TextView tv_ga_brt_slots_empty;
    TextView tv_ga_brt_slots_enemy;
    ImageView iv_ga_brt_car_black;
    ImageView iv_ga_brt_car_our;
    ImageView iv_ga_brt_car_empty;
    ImageView iv_ga_brt_car_enemy;
    ImageView iv_ga_brt_progress;

    // BRC views
    ImageView iv_ga_brc_name;
    TextView tv_ga_brc_x2;
    TextView tv_ga_brc_points;
    TextView tv_ga_brc_slots;
    TextView tv_ga_brc_slots_our;
    TextView tv_ga_brc_slots_empty;
    TextView tv_ga_brc_slots_enemy;
    ImageView iv_ga_brc_car_black;
    ImageView iv_ga_brc_car_our;
    ImageView iv_ga_brc_car_empty;
    ImageView iv_ga_brc_car_enemy;
    ImageView iv_ga_brc_progress;

    // BRB views
    ImageView iv_ga_brb_name;
    TextView tv_ga_brb_x2;
    TextView tv_ga_brb_points;
    TextView tv_ga_brb_slots;
    TextView tv_ga_brb_slots_our;
    TextView tv_ga_brb_slots_empty;
    TextView tv_ga_brb_slots_enemy;
    ImageView iv_ga_brb_car_black;
    ImageView iv_ga_brb_car_our;
    ImageView iv_ga_brb_car_empty;
    ImageView iv_ga_brb_car_enemy;
    ImageView iv_ga_brb_progress;

    // Рекламный блок
    AdView mAdView;

    private static final int MY_PERMISSIONS_REQUESTREAD_MULTIPERMISIONS = 4;   // код пермишенс
    public static final int REQUEST_CODE_SECOND_ACTIVITY = 100; // код реквеста для вызова Настроек
    public static String pathToCATScalcFolder = "";   // путь к папке программы в корне флешки
    public static File fileScreenshot;    // текущий файл скриншота
    public static File fileScreenshotPrevious;    // предыдущий файл скриншота
    public static File fileLastInFolder;    // последний файл в папке
    private NotificationManager notificationManager;    // нотификатор
    private static final int NOTIFY_ID = 1;             // айди нотификатора
    private static final String CHANNEL_ID = "C.A.T.S. City Calculator Channel #1";   // канал нотификатора
    public static String pathToScreenshotDir = "";    // путь к папке скриншотов
    public static boolean isListenToNewFileInFolder;  // флаг "Следить за новыми файлами в папке"
    public static boolean isAllFieldsCorrect;         // флаг "Все поля заполнены правильно"
    public static int calibrateX;                              // калибровка
    public static int calibrateY;                              // калибровка
    private Timer timer;                        // таймер
    public static boolean isDebugMode;
    public Context context;
    public static CityCalc mainCityCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        initializeViews(); // Инициализация вьюшек
        context = tv_ga_start_game_time.getContext();

        // отслеживание изменения свича "Следить за файлами в папке"
        sw_ga_listen_new_file.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    // если свич переключили
                // обновляем соответствующий пермишн и переменную
                SharedPreferences sharedPreferences = GameActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_listen_last_file), isChecked);
                editor.apply();
                isListenToNewFileInFolder = isChecked;
            }
        });

        // инициализация рекламного блока
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        requestPermissions(); // запрос разрешений на чтение/запись

        createProgramDirAndInitializeScreenshot(); // создаем папку программы (если её нет) и инициализируем скриншот

        // нотификейшт менеджер
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        readPreferences(); // считываем преференцы

        sw_ga_listen_new_file.setChecked(isListenToNewFileInFolder);

        startTimer();   // стартуем таймер

    }

    public void loadDataToViews() {

        String textStartGameTime;
        String textEndGameTime;
        String textStatus;
        CCAGame ccaGame = (CCAGame) mainCityCalc.mapAreas.get(Area.CITY);
        CCATeam ccaOurTeam = (CCATeam) mainCityCalc.mapAreas.get(Area.TEAM_NAME_OUR);
        CCATeam ccaEnemyTeam = (CCATeam) mainCityCalc.mapAreas.get(Area.TEAM_NAME_ENEMY);
        CCABuilding ccaBLT = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLT);
        CCABuilding ccaBLC = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLC);
        CCABuilding ccaBLB = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLB);
        CCABuilding ccaBRT = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRT);
        CCABuilding ccaBRC = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRC);
        CCABuilding ccaBRB = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRB);

        if (ccaGame != null) {
            textStartGameTime = getString(R.string.start_game_at) + ": " + ccaGame.strStartTime;
            textEndGameTime = getString(R.string.end_game_at) + ": " + ccaGame.strEndTime;

            tv_ga_status.setText(ccaGame.status);
            tv_ga_start_game_time.setText(textStartGameTime);
            tv_ga_end_game_time.setText(textEndGameTime);
            if (ccaGame.isGameOver) {
                tv_ga_total_time.setText("");
            } else {
                tv_ga_total_time.setText(ccaGame.strTotalTime);
            }

            tv_ga_early_win.setText(String.valueOf(ccaGame.earlyWin));
            if (ccaOurTeam != null && ccaEnemyTeam != null) {

                iv_ga_our_team_name.setImageBitmap(ccaOurTeam.bmpSrc);
                tv_ga_our_increase.setText(ccaOurTeam.increase == 0 ? "" : " +" + ccaOurTeam.increase + " ");
                tv_ga_our_points.setText(String.valueOf(ccaOurTeam.points));

                iv_ga_enemy_team_name.setImageBitmap(ccaEnemyTeam.bmpSrc);
                tv_ga_enemy_increase.setText(ccaEnemyTeam.increase == 0 ? "" : " +" + ccaEnemyTeam.increase + " ");
                tv_ga_enemy_points.setText(String.valueOf(ccaEnemyTeam.points));

                if (ccaGame.isGameOver) {
                    tv_ga_our_end_time.setText("");
                    tv_ga_enemy_end_time.setText(String.valueOf(ccaEnemyTeam.strTimeToEarlyWin));
                } else {
                    tv_ga_our_end_time.setText("");
                    tv_ga_enemy_end_time.setText(String.valueOf(ccaEnemyTeam.strTimeToEarlyWin));
                }

            }

            if (ccaBLT != null) {
                CityCalcArea ccaBLTname = mainCityCalc.mapAreas.get(Area.BLT_NAME);
                CityCalcArea ccaBLTprogress = mainCityCalc.mapAreas.get(Area.BLT_PROGRESS);
                if (ccaBLTname != null) iv_ga_blt_name.setImageBitmap(ccaBLTname.bmpSrc);
                if (ccaBLTprogress != null) iv_ga_blt_progress.setImageBitmap(ccaBLTprogress.bmpSrc);
                tv_ga_blt_slots.setText(String.valueOf(ccaBLT.slots));
                tv_ga_blt_slots_our.setText(String.valueOf(ccaBLT.slots_our));
                tv_ga_blt_slots_empty.setText(String.valueOf(ccaBLT.slots_empty));
                tv_ga_blt_slots_enemy.setText(String.valueOf(ccaBLT.slots_enemy));
                if (ccaBLT.buildingIsOur) {
                    tv_ga_blt_points.setText("+" + ccaBLT.our_points);
                    tv_ga_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaBLT.buildingIsEnemy) {
                    tv_ga_blt_points.setText("+" + ccaBLT.enemy_points);
                    tv_ga_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaBLT.buildingIsEmpty) {
                    tv_ga_blt_points.setText("");
                    tv_ga_blt_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaBLT.isX2) {
                    tv_ga_blt_x2.setText("X2");
                    tv_ga_blt_x2.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints),16));
                } else {
                    tv_ga_blt_x2.setText("");
                    tv_ga_blt_x2.setBackgroundColor(0xFFFFFFFF);
                }
            }

            if (ccaBLC != null) {
                CityCalcArea ccaBLCname = mainCityCalc.mapAreas.get(Area.BLC_NAME);
                CityCalcArea ccaBLCprogress = mainCityCalc.mapAreas.get(Area.BLC_PROGRESS);
                if (ccaBLCname != null) iv_ga_blc_name.setImageBitmap(ccaBLCname.bmpSrc);
                if (ccaBLCprogress != null) iv_ga_blc_progress.setImageBitmap(ccaBLCprogress.bmpSrc);
                tv_ga_blc_slots.setText(String.valueOf(ccaBLC.slots));
                tv_ga_blc_slots_our.setText(String.valueOf(ccaBLC.slots_our));
                tv_ga_blc_slots_empty.setText(String.valueOf(ccaBLC.slots_empty));
                tv_ga_blc_slots_enemy.setText(String.valueOf(ccaBLC.slots_enemy));
                if (ccaBLC.buildingIsOur) {
                    tv_ga_blc_points.setText("+" + ccaBLC.our_points);
                    tv_ga_blc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaBLC.buildingIsEnemy) {
                    tv_ga_blc_points.setText("+" + ccaBLC.enemy_points);
                    tv_ga_blc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaBLC.buildingIsEmpty) {
                    tv_ga_blc_points.setText("");
                    tv_ga_blc_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaBLC.isX2) {
                    tv_ga_blc_x2.setText("X2");
                    tv_ga_blc_x2.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints),16));
                } else {
                    tv_ga_blc_x2.setText("");
                    tv_ga_blc_x2.setBackgroundColor(0xFFFFFFFF);
                }
            }

        }


        if (ccaBLB != null) {
            CityCalcArea ccaBLBname = mainCityCalc.mapAreas.get(Area.BLB_NAME);
            CityCalcArea ccaBLBprogress = mainCityCalc.mapAreas.get(Area.BLB_PROGRESS);
            if (ccaBLBname != null) iv_ga_blb_name.setImageBitmap(ccaBLBname.bmpSrc);
            if (ccaBLBprogress != null) iv_ga_blb_progress.setImageBitmap(ccaBLBprogress.bmpSrc);
            tv_ga_blb_slots.setText(String.valueOf(ccaBLB.slots));
            tv_ga_blb_slots_our.setText(String.valueOf(ccaBLB.slots_our));
            tv_ga_blb_slots_empty.setText(String.valueOf(ccaBLB.slots_empty));
            tv_ga_blb_slots_enemy.setText(String.valueOf(ccaBLB.slots_enemy));
            if (ccaBLB.buildingIsOur) {
                tv_ga_blb_points.setText("+" + ccaBLB.our_points);
                tv_ga_blb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
            } else if (ccaBLB.buildingIsEnemy) {
                tv_ga_blb_points.setText("+" + ccaBLB.enemy_points);
                tv_ga_blb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
            } else if (ccaBLB.buildingIsEmpty) {
                tv_ga_blb_points.setText("");
                tv_ga_blb_points.setBackgroundColor(0xFFFFFFFF);
            }
            if (ccaBLB.isX2) {
                tv_ga_blb_x2.setText("X2");
                tv_ga_blb_x2.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints),16));
            } else {
                tv_ga_blb_x2.setText("");
                tv_ga_blb_x2.setBackgroundColor(0xFFFFFFFF);
            }
        }

        if (ccaBRT != null) {
            CityCalcArea ccaBRTname = mainCityCalc.mapAreas.get(Area.BRT_NAME);
            CityCalcArea ccaBRTprogress = mainCityCalc.mapAreas.get(Area.BRT_PROGRESS);
            if (ccaBRTname != null) iv_ga_brt_name.setImageBitmap(ccaBRTname.bmpSrc);
            if (ccaBRTprogress != null) iv_ga_brt_progress.setImageBitmap(ccaBRTprogress.bmpSrc);
            tv_ga_brt_slots.setText(String.valueOf(ccaBRT.slots));
            tv_ga_brt_slots_our.setText(String.valueOf(ccaBRT.slots_our));
            tv_ga_brt_slots_empty.setText(String.valueOf(ccaBRT.slots_empty));
            tv_ga_brt_slots_enemy.setText(String.valueOf(ccaBRT.slots_enemy));
            if (ccaBRT.buildingIsOur) {
                tv_ga_brt_points.setText("+" + ccaBRT.our_points);
                tv_ga_brt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
            } else if (ccaBRT.buildingIsEnemy) {
                tv_ga_brt_points.setText("+" + ccaBRT.enemy_points);
                tv_ga_brt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
            } else if (ccaBRT.buildingIsEmpty) {
                tv_ga_brt_points.setText("");
                tv_ga_brt_points.setBackgroundColor(0xFFFFFFFF);
            }
            if (ccaBRT.isX2) {
                tv_ga_brt_x2.setText("X2");
                tv_ga_brt_x2.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints),16));
            } else {
                tv_ga_brt_x2.setText("");
                tv_ga_brt_x2.setBackgroundColor(0xFFFFFFFF);
            }
        }

        if (ccaBRC != null) {
            CityCalcArea ccaBRCname = mainCityCalc.mapAreas.get(Area.BRC_NAME);
            CityCalcArea ccaBRCprogress = mainCityCalc.mapAreas.get(Area.BRC_PROGRESS);
            if (ccaBRCname != null) iv_ga_brc_name.setImageBitmap(ccaBRCname.bmpSrc);
            if (ccaBRCprogress != null) iv_ga_brc_progress.setImageBitmap(ccaBRCprogress.bmpSrc);
            tv_ga_brc_slots.setText(String.valueOf(ccaBRC.slots));
            tv_ga_brc_slots_our.setText(String.valueOf(ccaBRC.slots_our));
            tv_ga_brc_slots_empty.setText(String.valueOf(ccaBRC.slots_empty));
            tv_ga_brc_slots_enemy.setText(String.valueOf(ccaBRC.slots_enemy));
            if (ccaBRC.buildingIsOur) {
                tv_ga_brc_points.setText("+" + ccaBRC.our_points);
                tv_ga_brc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
            } else if (ccaBRC.buildingIsEnemy) {
                tv_ga_brc_points.setText("+" + ccaBRC.enemy_points);
                tv_ga_brc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
            } else if (ccaBRC.buildingIsEmpty) {
                tv_ga_brc_points.setText("");
                tv_ga_brc_points.setBackgroundColor(0xFFFFFF);
            }
            if (ccaBRC.isX2) {
                tv_ga_brc_x2.setText("X2");
                tv_ga_brc_x2.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints),16));
            } else {
                tv_ga_brc_x2.setText("");
                tv_ga_brc_x2.setBackgroundColor(0xFFFFFF);
            }
        }


        if (ccaBRB != null) {
            CityCalcArea ccaBRBname = mainCityCalc.mapAreas.get(Area.BRB_NAME);
            CityCalcArea ccaBRBprogress = mainCityCalc.mapAreas.get(Area.BRB_PROGRESS);
            if (ccaBRBname != null) iv_ga_brb_name.setImageBitmap(ccaBRBname.bmpSrc);
            if (ccaBRBprogress != null) iv_ga_brb_progress.setImageBitmap(ccaBRBprogress.bmpSrc);
            tv_ga_brb_slots.setText(String.valueOf(ccaBRB.slots));
            tv_ga_brb_slots_our.setText(String.valueOf(ccaBRB.slots_our));
            tv_ga_brb_slots_empty.setText(String.valueOf(ccaBRB.slots_empty));
            tv_ga_brb_slots_enemy.setText(String.valueOf(ccaBRB.slots_enemy));
            if (ccaBRB.buildingIsOur) {
                tv_ga_brb_points.setText("+" + ccaBRB.our_points);
                tv_ga_brb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
            } else if (ccaBRB.buildingIsEnemy) {
                tv_ga_brb_points.setText("+" + ccaBRB.enemy_points);
                tv_ga_brb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
            } else if (ccaBRB.buildingIsEmpty) {
                tv_ga_brb_points.setText("");
                tv_ga_brb_points.setBackgroundColor(0xFFFFFF);
            }
            if (ccaBRB.isX2) {
                tv_ga_brb_x2.setText("X2");
                tv_ga_brb_x2.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints),16));
            } else {
                tv_ga_brb_x2.setText("");
                tv_ga_brb_x2.setBackgroundColor(0xFFFFFF);
            }
        }



        // нотификация
        if (ccaGame != null) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setAutoCancel(false)
                            .setSmallIcon(R.drawable.ic_catscalciconsmall)
                            .setWhen(System.currentTimeMillis())
                            .setContentIntent(pendingIntent)
                            .setContentText(ccaGame.status)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(ccaGame.status));
            createChannelIfNeeded(notificationManager);
            notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
        }

    }

    public void initializeViews() {

        // Game views
        sw_ga_listen_new_file = findViewById(R.id.sw_ga_listen_new_file);
        tv_ga_start_game_time = findViewById(R.id.tv_ga_start_game_time);
        tv_ga_end_game_time = findViewById(R.id.tv_ga_end_game_time);
        tv_ga_status = findViewById(R.id.tv_ga_status);
        tv_ga_vs = findViewById(R.id.tv_ga_vs);

        // Time views
        tv_ga_total_time = findViewById(R.id.tv_ga_total_time);
        tv_ga_early_win = findViewById(R.id.tv_ga_early_win);

        // Our team views
        iv_ga_our_team_name = findViewById(R.id.iv_ga_our_team_name);
        tv_ga_our_increase = findViewById(R.id.tv_ga_our_increase);
        tv_ga_our_points = findViewById(R.id.tv_ga_our_points);
        tv_ga_our_end_time = findViewById(R.id.tv_ga_our_end_time);

        // Enemy team views
        iv_ga_enemy_team_name = findViewById(R.id.iv_ga_enemy_team_name);
        tv_ga_enemy_increase = findViewById(R.id.tv_ga_enemy_increase);
        tv_ga_enemy_points = findViewById(R.id.tv_ga_enemy_points);
        tv_ga_enemy_end_time = findViewById(R.id.tv_ga_enemy_end_time);

        // BLT views
        iv_ga_blt_name = findViewById(R.id.iv_ga_blt_name);
        tv_ga_blt_x2 = findViewById(R.id.tv_ga_blt_x2);
        tv_ga_blt_points = findViewById(R.id.tv_ga_blt_points);
        tv_ga_blt_slots = findViewById(R.id.tv_ga_blt_slots);
        tv_ga_blt_slots_our = findViewById(R.id.tv_ga_blt_slots_our);
        tv_ga_blt_slots_empty = findViewById(R.id.tv_ga_blt_slots_empty);
        tv_ga_blt_slots_enemy = findViewById(R.id.tv_ga_blt_slots_enemy);
        iv_ga_blt_car_black = findViewById(R.id.iv_ga_blt_car_black);
        iv_ga_blt_car_our = findViewById(R.id.iv_ga_blt_car_our);
        iv_ga_blt_car_empty = findViewById(R.id.iv_ga_blt_car_empty);
        iv_ga_blt_car_enemy = findViewById(R.id.iv_ga_blt_car_enemy);
        iv_ga_blt_progress = findViewById(R.id.iv_ga_blt_progress);

        // BLC views
        iv_ga_blc_name = findViewById(R.id.iv_ga_blc_name);
        tv_ga_blc_x2 = findViewById(R.id.tv_ga_blc_x2);
        tv_ga_blc_points = findViewById(R.id.tv_ga_blc_points);
        tv_ga_blc_slots = findViewById(R.id.tv_ga_blc_slots);
        tv_ga_blc_slots_our = findViewById(R.id.tv_ga_blc_slots_our);
        tv_ga_blc_slots_empty = findViewById(R.id.tv_ga_blc_slots_empty);
        tv_ga_blc_slots_enemy = findViewById(R.id.tv_ga_blc_slots_enemy);
        iv_ga_blc_car_black = findViewById(R.id.iv_ga_blc_car_black);
        iv_ga_blc_car_our = findViewById(R.id.iv_ga_blc_car_our);
        iv_ga_blc_car_empty = findViewById(R.id.iv_ga_blc_car_empty);
        iv_ga_blc_car_enemy = findViewById(R.id.iv_ga_blc_car_enemy);
        iv_ga_blc_progress = findViewById(R.id.iv_ga_blc_progress);

        // BLB views
        iv_ga_blb_name = findViewById(R.id.iv_ga_blb_name);
        tv_ga_blb_x2 = findViewById(R.id.tv_ga_blb_x2);
        tv_ga_blb_points = findViewById(R.id.tv_ga_blb_points);
        tv_ga_blb_slots = findViewById(R.id.tv_ga_blb_slots);
        tv_ga_blb_slots_our = findViewById(R.id.tv_ga_blb_slots_our);
        tv_ga_blb_slots_empty = findViewById(R.id.tv_ga_blb_slots_empty);
        tv_ga_blb_slots_enemy = findViewById(R.id.tv_ga_blb_slots_enemy);
        iv_ga_blb_car_black = findViewById(R.id.iv_ga_blb_car_black);
        iv_ga_blb_car_our = findViewById(R.id.iv_ga_blb_car_our);
        iv_ga_blb_car_empty = findViewById(R.id.iv_ga_blb_car_empty);
        iv_ga_blb_car_enemy = findViewById(R.id.iv_ga_blb_car_enemy);
        iv_ga_blb_progress = findViewById(R.id.iv_ga_blb_progress);

        // BRT views
        iv_ga_brt_name = findViewById(R.id.iv_ga_brt_name);
        tv_ga_brt_x2 = findViewById(R.id.tv_ga_brt_x2);
        tv_ga_brt_points = findViewById(R.id.tv_ga_brt_points);
        tv_ga_brt_slots = findViewById(R.id.tv_ga_brt_slots);
        tv_ga_brt_slots_our = findViewById(R.id.tv_ga_brt_slots_our);
        tv_ga_brt_slots_empty = findViewById(R.id.tv_ga_brt_slots_empty);
        tv_ga_brt_slots_enemy = findViewById(R.id.tv_ga_brt_slots_enemy);
        iv_ga_brt_car_black = findViewById(R.id.iv_ga_brt_car_black);
        iv_ga_brt_car_our = findViewById(R.id.iv_ga_brt_car_our);
        iv_ga_brt_car_empty = findViewById(R.id.iv_ga_brt_car_empty);
        iv_ga_brt_car_enemy = findViewById(R.id.iv_ga_brt_car_enemy);
        iv_ga_brt_progress = findViewById(R.id.iv_ga_brt_progress);

        // BRC views
        iv_ga_brc_name = findViewById(R.id.iv_ga_brc_name);
        tv_ga_brc_x2 = findViewById(R.id.tv_ga_brc_x2);
        tv_ga_brc_points = findViewById(R.id.tv_ga_brc_points);
        tv_ga_brc_slots = findViewById(R.id.tv_ga_brc_slots);
        tv_ga_brc_slots_our = findViewById(R.id.tv_ga_brc_slots_our);
        tv_ga_brc_slots_empty = findViewById(R.id.tv_ga_brc_slots_empty);
        tv_ga_brc_slots_enemy = findViewById(R.id.tv_ga_brc_slots_enemy);
        iv_ga_brc_car_black = findViewById(R.id.iv_ga_brc_car_black);
        iv_ga_brc_car_our = findViewById(R.id.iv_ga_brc_car_our);
        iv_ga_brc_car_empty = findViewById(R.id.iv_ga_brc_car_empty);
        iv_ga_brc_car_enemy = findViewById(R.id.iv_ga_brc_car_enemy);
        iv_ga_brc_progress = findViewById(R.id.iv_ga_brc_progress);

        // BRB views
        iv_ga_brb_name = findViewById(R.id.iv_ga_brb_name);
        tv_ga_brb_x2 = findViewById(R.id.tv_ga_brb_x2);
        tv_ga_brb_points = findViewById(R.id.tv_ga_brb_points);
        tv_ga_brb_slots = findViewById(R.id.tv_ga_brb_slots);
        tv_ga_brb_slots_our = findViewById(R.id.tv_ga_brb_slots_our);
        tv_ga_brb_slots_empty = findViewById(R.id.tv_ga_brb_slots_empty);
        tv_ga_brb_slots_enemy = findViewById(R.id.tv_ga_brb_slots_enemy);
        iv_ga_brb_car_black = findViewById(R.id.iv_ga_brb_car_black);
        iv_ga_brb_car_our = findViewById(R.id.iv_ga_brb_car_our);
        iv_ga_brb_car_empty = findViewById(R.id.iv_ga_brb_car_empty);
        iv_ga_brb_car_enemy = findViewById(R.id.iv_ga_brb_car_enemy);
        iv_ga_brb_progress = findViewById(R.id.iv_ga_brb_progress);

        // Рекламный блок
        mAdView = findViewById(R.id.adView);
    }

    public void requestPermissions() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE)) permissionsNeeded.add(getString(R.string.read_external_storage));
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)) permissionsNeeded.add(getString(R.string.write_external_storage));

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = getString(R.string.you_need_to_grant_access_to) + " " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), MY_PERMISSIONS_REQUESTREAD_MULTIPERMISIONS);}
                        });
            }
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(GameActivity.this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }

    public void createProgramDirAndInitializeScreenshot() {
        // путь к папке программы в корне файловой системы. Если такой папки нет - создаем её
        pathToCATScalcFolder = Environment.getExternalStorageDirectory().getPath() + "/CATScalc";
        File tempDir = new File(pathToCATScalcFolder);
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        if (tempDir.exists()) { // если папка есть
            File tmp = new File(MainActivity.pathToCATScalcFolder, "last_screenshot.PNG");       // файл картинки - путь к папке программы + имя файла
            if (!tmp.exists()) {    // если файла нет
                Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.raw.stub_screenshot);  // открываем битмап из ресурса
                try {
                    OutputStream fOutScreenshot = new FileOutputStream(tmp);                       // открываем поток вывода
                    sourceBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutScreenshot); // сжимаем картинку в ПНГ с качеством 100%
                    fOutScreenshot.flush();                                                       // сохраняем данные из потока
                    fOutScreenshot.close(); // закрываем поток
                    fileScreenshot = tmp; // файл скриншота - созданный файл
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                fileScreenshot = tmp; // файл скриншота - картинка из папки программы
            }
        }
    }

    public void readPreferences() {

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);

        pathToScreenshotDir = sharedPreferences.getString(getString(R.string.pref_screenshot_folder),"");
        isListenToNewFileInFolder = sharedPreferences.getBoolean(getString(R.string.pref_listen_last_file),false);
        isDebugMode = sharedPreferences.getBoolean(getString(R.string.pref_debug_mode),false);
        calibrateX = sharedPreferences.getInt(getString(R.string.pref_calibrate_x),0);
        calibrateY = sharedPreferences.getInt(getString(R.string.pref_calibrate_y),0);

    }

    private void startTimer() {

        if (timer == null) {    // если таймер не запущен
            timer = new Timer();    // запускаем таймер
            timer.schedule(new firstTask(), 1000,1000); // запускаем такс таймера
        }

    }

    private File getLastFileInFolder(String pathToFolder) {

        File temp = null;           // временный файл
        File lastScreenshot = new File(pathToCATScalcFolder, "last_screenshot.PNG"); // последний скри
        File dir = new File(pathToFolder); // папка
        File[] files = dir.listFiles(new FilenameFilter() { // присок файлов в папке по фильтру
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png");  // фильтр по JPG и PNG
            }
        });
        List<File> listFiles = new ArrayList<>(); // лист
        if (files != null) {    // если файлы в папке есть
            for (File file : files) {   // цикл по файлам
                listFiles.add(file);    // добавляем файл в лист
            }
        }

        if  (listFiles.size() > 0) {    // если в листе есть файлы
            long maxLastModified = 0;   // максимальная дата (ноль для начала)

            for (File file : listFiles) {   // цикл по листу
                if (file.lastModified() > maxLastModified) {    // если дата создания файла из листа больше максимальной
                    maxLastModified = file.lastModified();      // максимальная дата = дате файла из листа
                    temp = file;    // временный файл равен файлу из листа
                }
            }

            if (temp != null) { // если найден самый свежий файл
                if (!temp.equals(fileLastInFolder)) {   // если найденный файл не совпадает с раенее найденным "последним файлом"
                    Bitmap sourceBitmap = BitmapFactory.decodeFile(temp.getAbsolutePath());   // получаем битмап из файла скриншота
                    int widthSource = sourceBitmap.getWidth();      // ширина исходной картинки
                    int heightSource = sourceBitmap.getHeight();   // высота исходной картинки

                    if (widthSource < heightSource) {
                        if (fileLastInFolder == null) {
                            temp = lastScreenshot;
                            if (!temp.exists()) temp = null;
                        } else {
                            temp = null;    // если ориентация картинки неправильная - найденный файл не подходит и будет равен нулл
                        }
                    } else {
                        fileLastInFolder = temp;    // последний найденный файл - текущий найденный
                    }

                }
            }
        }

        if (temp == null && lastScreenshot.exists()) return lastScreenshot;

        return temp;
    }

    /**
     * Создание канала нотификации
     */
    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * Возврат в текущую активность из какой-то другой активности
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // если произошел возврат со страницы настрок - обновляем контролы в текущей активности
        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY) {

            readPreferences();
            fileScreenshotPrevious = null;
            sw_ga_listen_new_file.setChecked(isListenToNewFileInFolder);

        }
    }

    /**
     *  Созданием меню
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);  // создаем меню
        return true;
    }

    /**
     * Выбор элементов меню
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // айди элемента меню
        switch(id){
            case R.id.menu_open_settings :  // "Настройки"
                openSettings();
                return true;
            case R.id.menu_open_screenshot :    // "Открыть скриншотк"
                selectScreenshot();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     *  Выбор скриншота
     */
    private void selectScreenshot() {

        OpenFileDialog fileDialog = new OpenFileDialog(this, pathToScreenshotDir)   // диалог выбора скриншота по переданному пути
                .setFolderIcon(ContextCompat.getDrawable(context, R.drawable.ic_folder))            // иконка папки
                .setFileIcon(ContextCompat.getDrawable(context, R.drawable.ic_file))                // иконка файла
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {
                        fileScreenshot = new File(fileName); // файл скриншота - возавращенный из диалога
                        isListenToNewFileInFolder = false; // снимаем флажок "Следить за файлами в папке"
                        sw_ga_listen_new_file.setChecked(false); // устанавливаем контрол флажка
                        fileLastInFolder = null;    // сбрасываем последний файл в папке
                        mainCityCalc = new CityCalc(fileScreenshot, calibrateX, calibrateY, context);
                        loadDataToViews();
                    }
                });
        fileDialog.show();

    }

    /**
     * Открытие Настроек
     */
    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }

    class firstTask extends TimerTask {

        @Override
        public void run() {

            GameActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if (isListenToNewFileInFolder) {    // если установлен флажок "Следить за файлами в папке"
                        File tmpFile = getLastFileInFolder(pathToScreenshotDir);    // получаем последний файл из папки
                        if (tmpFile != null) {  // если он не пустой
                            if (!tmpFile.equals(fileScreenshot)) {  // если он не равен текущем скриншоту
                                fileScreenshot = tmpFile;   // текущий скриншот = последнему файлу в папке
                                mainCityCalc = new CityCalc(fileScreenshot, calibrateX, calibrateY, context);
                                loadDataToViews();
                            }
                        }

                    } else {
                        if (fileScreenshot == null) {
                            File lastScreenshot = new File(pathToCATScalcFolder, "last_screenshot.PNG"); // последний скри
                            if (lastScreenshot.exists()) {
                                fileScreenshot = lastScreenshot;
                                mainCityCalc = new CityCalc(fileScreenshot, calibrateX, calibrateY, context);
                                loadDataToViews();
                            }
                        }

                    }

                }
            });
        }
    };
}
