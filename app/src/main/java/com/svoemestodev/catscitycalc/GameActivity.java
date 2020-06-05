package com.svoemestodev.catscitycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
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
    AdView ad_ga_banner;

    ImageView iv_ga_game_car_black;
    ImageView iv_ga_game_car_our;
    ImageView iv_ga_game_car_empty;
    ImageView iv_ga_game_car_enemy;
    TextView tv_ga_game_slots;
    TextView tv_ga_game_slots_our;
    TextView tv_ga_game_slots_empty;
    TextView tv_ga_game_slots_enemy;

    ImageButton ib_ga_car1;
    TextView tv_ga_car1_name;
    ImageView iv_ga_car1_health;
    TextView tv_ga_car1_health;
    ImageView iv_ga_car1_shield;
    TextView tv_ga_car1_shield;
    TextView tv_ga_car1_repair;
    ImageView iv_ga_car1_house;

    ImageButton ib_ga_car2;
    TextView tv_ga_car2_name;
    ImageView iv_ga_car2_health;
    TextView tv_ga_car2_health;
    ImageView iv_ga_car2_shield;
    TextView tv_ga_car2_shield;
    TextView tv_ga_car2_repair;
    ImageView iv_ga_car2_house;

    ImageButton ib_ga_car3;
    TextView tv_ga_car3_name;
    ImageView iv_ga_car3_health;
    TextView tv_ga_car3_health;
    ImageView iv_ga_car3_shield;
    TextView tv_ga_car3_shield;
    TextView tv_ga_car3_repair;
    ImageView iv_ga_car3_house;
    
    Button bt_ga_strategy;

    private static final int MY_PERMISSIONS_REQUESTREAD_MULTIPERMISIONS = 4;   // код пермишенс
    public static final int REQUEST_CODE_SECOND_ACTIVITY = 100; // код реквеста для вызова Настроек
    public static String pathToCATScalcFolder = "";   // путь к папке программы в корне флешки
    public static String pathToTessFolder = "";   // путь к папке программы в корне флешки
    public static File fileGameScreenshot = null;    // текущий файл скриншота
    public static File fileGameScreenshotPrevious;    // предыдущий файл скриншота
    public static File fileLastInFolder;    // последний файл в папке
    public static File fileCarScreenshot = null;    // текущий файл скриншота
    public static File fileCarScreenshotPrevious;    // предыдущий файл скриншота
    public static File fileLast;    // последний файл в папке
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
    public static Context context;
    public static CityCalc mainCityCalc;

    private static final String TAG = "GameActivity";

    public boolean isResumed;


    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String logMsgPref = "onCreate: ";
        Log.i(TAG, logMsgPref + "start");

        super.onCreate(savedInstanceState);


        // путь к папке программы в корне файловой системы. Если такой папки нет - создаем её
        pathToCATScalcFolder = Environment.getExternalStorageDirectory().getPath() + "/CATScalc";
        Log.i(TAG, logMsgPref + "pathToCATScalcFolder = " + pathToCATScalcFolder);

        pathToTessFolder = pathToCATScalcFolder + "/tessdata";
        Log.i(TAG, logMsgPref + "pathToTessFolder = " + pathToTessFolder);

        context = getBaseContext();

        setContentView(R.layout.activity_game);

        Log.i(TAG, "onCreate initializeViews()");
        initializeViews(); // Инициализация вьюшек

        // отслеживание изменения свича "Следить за файлами в папке"
        sw_ga_listen_new_file.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    // если свич переключили
                String logMsgPref = "sw_ga_listen_new_file onCheckedChanged: ";
                Log.i(TAG, logMsgPref + "sharedPreferences...");
                // обновляем соответствующий пермишн и переменную
                SharedPreferences sharedPreferences = GameActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_listen_last_file), isChecked);
                Log.i(TAG, logMsgPref + "preference " + (getString(R.string.pref_listen_last_file) + " = " + isChecked));
                editor.apply();
                isListenToNewFileInFolder = isChecked;
            }
        });

        Log.i(TAG, "onCreate MobileAds.initialize");
        // инициализация рекламного блока
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        Log.i(TAG, logMsgPref + "adRequest = new AdRequest.Builder().build()");
        AdRequest adRequest = new AdRequest.Builder().build();

        Log.i(TAG, logMsgPref + "ad_ga_banner.loadAd(adRequest)");
        ad_ga_banner.loadAd(adRequest);


//        Log.i(TAG, logMsgPref + "вызываем createProgramDirAndInitializeScreenshot()");
//        createProgramDirAndInitializeScreenshot(); // создаем папку программы (если её нет) и инициализируем скриншот

        // нотификейшт менеджер
        Log.i(TAG, logMsgPref + "Инициализируем notificationManager");
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Log.i(TAG, logMsgPref + "вызываем readPreferences()");
        readPreferences(); // считываем преференцы

        sw_ga_listen_new_file.setChecked(isListenToNewFileInFolder);

        Log.i(TAG, logMsgPref + "вызываем startTimer()");
        startTimer();   // стартуем таймер

    }

    public void loadDataToViews(boolean withNotify) {

        String logMsgPref = "loadDataToViews: ";
        Log.i(TAG, logMsgPref + "start");

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
        String pattern = "dd MMM HH:mm";

        if (ccaGame != null && ccaGame.ccagDateStartGame != null) {

            Log.i(TAG, logMsgPref + "вызываем ccaGame.calcWin()");
            ccaGame.calcWin();

//            bt_ga_strategy.setEnabled(!ccaGame.ccagIsGameOver);
            bt_ga_strategy.setVisibility(!ccaGame.ccagIsGameOver ? View.VISIBLE : View.INVISIBLE);

            textStartGameTime = getString(R.string.start_game_at) + ": " + Utils.convertDateToString(ccaGame.ccagDateStartGame, pattern);    // дата/время начала игры
            Log.i(TAG, logMsgPref + "textStartGameTime = " + textStartGameTime);

            textEndGameTime = getString(R.string.end_game_at) + ": "  + Utils.convertDateToString(ccaGame.ccagDateEndGame, pattern);          // дата/время окончания игры
            Log.i(TAG, logMsgPref + "textEndGameTime = " + textEndGameTime);

            tv_ga_status.setText(ccaGame.ccagStatus);   // статус
            Log.i(TAG, logMsgPref + "ccagStatus = " + ccaGame.ccagStatus);

            tv_ga_start_game_time.setText(textStartGameTime);   // дата/время начала игры
            tv_ga_end_game_time.setText(textEndGameTime);       // дата/время окончания игры

            Log.i(TAG, logMsgPref + "ccagIsGameOver = " + ccaGame.ccagIsGameOver);
            if (ccaGame.ccagIsGameOver) {   // если игра закончена
                tv_ga_total_time.setText("");   // время игры - пустое
            } else { // если игра не закончена
                tv_ga_total_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToEndGame())); // время игры
                Log.i(TAG, logMsgPref + "tv_ga_total_time = " + Utils.convertMinutesToHHMM(ccaGame.getMinutesToEndGame()));
            }

            Log.i(TAG, logMsgPref + "ccagEarlyWin = " + ccaGame.ccagEarlyWin);
            tv_ga_early_win.setText(String.valueOf(ccaGame.ccagEarlyWin)); // очки до досрочной победы

            if (ccaOurTeam != null && ccaEnemyTeam != null) {   // если команды не пустые

                iv_ga_our_team_name.setImageBitmap(ccaOurTeam.bmpSrc);  // имя нашей команды
                tv_ga_our_increase.setText(ccaOurTeam.ccatIncrease == 0 ? "" : " +" + ccaOurTeam.ccatIncrease + " ");   // прирост нашей команды
                Log.i(TAG, logMsgPref + "tv_ga_our_increase = " + (ccaOurTeam.ccatIncrease == 0 ? "" : " +" + ccaOurTeam.ccatIncrease + " "));
                tv_ga_our_points.setText(String.valueOf(ccaOurTeam.getPoints()));  // очки нашей команды
                Log.i(TAG, logMsgPref + "tv_ga_our_points = " + String.valueOf(ccaOurTeam.getPoints()));

                iv_ga_enemy_team_name.setImageBitmap(ccaEnemyTeam.bmpSrc);  // имя команды противника
                tv_ga_enemy_increase.setText(ccaEnemyTeam.ccatIncrease == 0 ? "" : " +" + ccaEnemyTeam.ccatIncrease + " "); // прирост команды противника
                Log.i(TAG, logMsgPref + "tv_ga_enemy_increase = " + (ccaEnemyTeam.ccatIncrease == 0 ? "" : " +" + ccaEnemyTeam.ccatIncrease + " "));
                tv_ga_enemy_points.setText(String.valueOf(ccaEnemyTeam.getPoints()));    // очки команды противника
                Log.i(TAG, logMsgPref + "tv_ga_enemy_points = " + String.valueOf(ccaEnemyTeam.getPoints()));

                if (ccaGame.ccagIsGameOver) {   // если игра закончена
                    tv_ga_our_end_time.setText(""); // наше время пустое
                    tv_ga_enemy_end_time.setText(""); // время противника пустое
                } else { // если игра незакончена
                    if (ccaGame.ccagWillOurWin) {
                        Log.i(TAG, logMsgPref + "ccagWillOurWin = " + ccaGame.ccagWillOurWin);

                        tv_ga_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                        Log.i(TAG, logMsgPref + "tv_ga_our_end_time = " + Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));

                        tv_ga_enemy_end_time.setText("");   // время противника пустое
                    } else if (ccaGame.ccagWillEnemyWin) {
                        Log.i(TAG, logMsgPref + "ccagWillEnemyWin = " + ccaGame.ccagWillEnemyWin);

                        tv_ga_our_end_time.setText(""); // наше время пустое
                        tv_ga_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                        Log.i(TAG, logMsgPref + "tv_ga_enemy_end_time = " + Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));

                    } else if (ccaGame.ccagWillNobodyWin) {
                        Log.i(TAG, logMsgPref + "ccagWillNobodyWin = " + ccaGame.ccagWillNobodyWin);

                        tv_ga_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                        Log.i(TAG, logMsgPref + "tv_ga_our_end_time = " + Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));

                        tv_ga_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                        Log.i(TAG, logMsgPref + "tv_ga_enemy_end_time = " + Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));
                    }

                }

            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);
            int color_building_mayX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_mayX2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_mayX2), (int)Long.parseLong(context.getString(R.string.def_rgb_building_mayX2), 16)));
            int color_building_isX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_isX2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_isX2), (int)Long.parseLong(context.getString(R.string.def_rgb_building_isX2), 16)));

            int slots = 0, slots_our = 0, slots_empty = 0, slots_enemy = 0;

            if (ccaBLT != null) {

                Log.i(TAG, logMsgPref + "ccaBLT != null");

                iv_ga_blt_name.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blt_x2.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blt_points.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blt_slots.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blt_slots_our.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blt_slots_empty.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blt_slots_enemy.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blt_car_black.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blt_car_our.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blt_car_empty.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blt_car_enemy.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blt_progress.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);



                if (ccaBLT.isPresent) {
                    Log.i(TAG, logMsgPref + "ccaBLT is Present");

                    CityCalcArea ccaBLTname = mainCityCalc.mapAreas.get(Area.BLT);
                    CityCalcArea ccaBLTprogress = mainCityCalc.mapAreas.get(Area.BLT_PROGRESS);
                    if (ccaBLTname != null) iv_ga_blt_name.setImageBitmap(ccaBLTname.bmpSrc);
                    Log.i(TAG, logMsgPref + "ccaBLTname = " + ccaBLTname.finText);

                    if (ccaBLTprogress != null) iv_ga_blt_progress.setImageBitmap(ccaBLTprogress.bmpSrc);
                    tv_ga_blt_slots.setText(String.valueOf(ccaBLT.slots));
                    tv_ga_blt_slots_our.setText(String.valueOf(ccaBLT.slots_our));
                    tv_ga_blt_slots_empty.setText(String.valueOf(ccaBLT.slots_empty));
                    tv_ga_blt_slots_enemy.setText(String.valueOf(ccaBLT.slots_enemy));
                    Log.i(TAG, logMsgPref + "ccaBLT: slots = " + ccaBLT.slots + ", slots_our = " + ccaBLT.slots_our + ", slots_empty = " + ccaBLT.slots_empty + ", slots_enemy = " + ccaBLT.slots_enemy);

                    slots += ccaBLT.slots;
                    slots_our += ccaBLT.slots_our;
                    slots_empty += ccaBLT.slots_empty;
                    slots_enemy += ccaBLT.slots_enemy;

                    if (ccaBLT.buildingIsOur) {
                        Log.i(TAG, logMsgPref + "ccaBLT buildingIsOur");
                        Log.i(TAG, logMsgPref + "ccaBLT our_points = +" + ccaBLT.our_points);
                        tv_ga_blt_points.setText("+" + ccaBLT.our_points);
                        tv_ga_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
//                        iv_ga_blt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBLT.buildingIsEnemy) {
                        Log.i(TAG, logMsgPref + "ccaBLT buildingIsEnemy");
                        Log.i(TAG, logMsgPref + "ccaBLT enemy_points = +" + ccaBRB.enemy_points);
                        tv_ga_blt_points.setText("+" + ccaBLT.enemy_points);
                        tv_ga_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
//                        iv_ga_blt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBLT.buildingIsEmpty) {
                        Log.i(TAG, logMsgPref + "ccaBLT buildingIsEmpty");
                        tv_ga_blt_points.setText("");
                        tv_ga_blt_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_ga_blt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBLT.isX2) {
                        Log.i(TAG, logMsgPref + "ccaBLT isX2");
                        tv_ga_blt_x2.setText("X2");
                        tv_ga_blt_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBLT.mayX2) {
                            Log.i(TAG, logMsgPref + "ccaBLT mayX2");
                            tv_ga_blt_x2.setText("X2");
                            tv_ga_blt_x2.setBackgroundColor(color_building_mayX2);
                        } else {
                            tv_ga_blt_x2.setText("");
                            tv_ga_blt_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }

            if (ccaBLC != null) {
                Log.i(TAG, logMsgPref + "ccaBLC != null");

                iv_ga_blc_name.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blc_x2.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blc_points.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blc_slots.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blc_slots_our.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blc_slots_empty.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blc_slots_enemy.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blc_car_black.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blc_car_our.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blc_car_empty.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blc_car_enemy.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blc_progress.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);

                if (ccaBLC.isPresent) {
                    Log.i(TAG, logMsgPref + "ccaBLC is Present");

                    CityCalcArea ccaBLCname = mainCityCalc.mapAreas.get(Area.BLC);
                    CityCalcArea ccaBLCprogress = mainCityCalc.mapAreas.get(Area.BLC_PROGRESS);
                    if (ccaBLCname != null) iv_ga_blc_name.setImageBitmap(ccaBLCname.bmpSrc);
                    Log.i(TAG, logMsgPref + "ccaBLCname = " + ccaBLCname.finText);

                    if (ccaBLCprogress != null) iv_ga_blc_progress.setImageBitmap(ccaBLCprogress.bmpSrc);
                    tv_ga_blc_slots.setText(String.valueOf(ccaBLC.slots));
                    tv_ga_blc_slots_our.setText(String.valueOf(ccaBLC.slots_our));
                    tv_ga_blc_slots_empty.setText(String.valueOf(ccaBLC.slots_empty));
                    tv_ga_blc_slots_enemy.setText(String.valueOf(ccaBLC.slots_enemy));
                    Log.i(TAG, logMsgPref + "ccaBLC: slots = " + ccaBLC.slots + ", slots_our = " + ccaBLC.slots_our + ", slots_empty = " + ccaBLC.slots_empty + ", slots_enemy = " + ccaBLC.slots_enemy);

                    slots += ccaBLC.slots;
                    slots_our += ccaBLC.slots_our;
                    slots_empty += ccaBLC.slots_empty;
                    slots_enemy += ccaBLC.slots_enemy;

                    if (ccaBLC.buildingIsOur) {
                        Log.i(TAG, logMsgPref + "ccaBLC buildingIsOur");
                        Log.i(TAG, logMsgPref + "ccaBLC our_points = +" + ccaBLC.our_points);
                        tv_ga_blc_points.setText("+" + ccaBLC.our_points);
                        tv_ga_blc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_ga_blc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBLC.buildingIsEnemy) {
                        Log.i(TAG, logMsgPref + "ccaBLC buildingIsEnemy");
                        Log.i(TAG, logMsgPref + "ccaBLC enemy_points = +" + ccaBLC.enemy_points);
                        tv_ga_blc_points.setText("+" + ccaBLC.enemy_points);
                        tv_ga_blc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_ga_blc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBLC.buildingIsEmpty) {
                        Log.i(TAG, logMsgPref + "ccaBLC buildingIsEmpty");
                        tv_ga_blc_points.setText("");
                        tv_ga_blc_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_ga_blc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBLC.isX2) {
                        Log.i(TAG, logMsgPref + "ccaBLC isX2");
                        tv_ga_blc_x2.setText("X2");
                        tv_ga_blc_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBLC.mayX2) {
                            Log.i(TAG, logMsgPref + "ccaBLC mayX2");
                            tv_ga_blc_x2.setText("X2");
                            tv_ga_blc_x2.setBackgroundColor(color_building_mayX2);
                        } else {
                            tv_ga_blc_x2.setText("");
                            tv_ga_blc_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }


            if (ccaBLB != null) {
                Log.i(TAG, logMsgPref + "ccaBLB != null");
                iv_ga_blb_name.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blb_x2.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blb_points.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blb_slots.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blb_slots_our.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blb_slots_empty.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_blb_slots_enemy.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blb_car_black.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blb_car_our.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blb_car_empty.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blb_car_enemy.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_blb_progress.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);

                if (ccaBLB.isPresent) {
                    Log.i(TAG, logMsgPref + "ccaBLB is Present");

                    CityCalcArea ccaBLBname = mainCityCalc.mapAreas.get(Area.BLB);
                    CityCalcArea ccaBLBprogress = mainCityCalc.mapAreas.get(Area.BLB_PROGRESS);
                    if (ccaBLBname != null) iv_ga_blb_name.setImageBitmap(ccaBLBname.bmpSrc);
                    Log.i(TAG, logMsgPref + "ccaBLBname = " + ccaBLBname.finText);

                    if (ccaBLBprogress != null) iv_ga_blb_progress.setImageBitmap(ccaBLBprogress.bmpSrc);
                    tv_ga_blb_slots.setText(String.valueOf(ccaBLB.slots));
                    tv_ga_blb_slots_our.setText(String.valueOf(ccaBLB.slots_our));
                    tv_ga_blb_slots_empty.setText(String.valueOf(ccaBLB.slots_empty));
                    tv_ga_blb_slots_enemy.setText(String.valueOf(ccaBLB.slots_enemy));
                    Log.i(TAG, logMsgPref + "ccaBLB: slots = " + ccaBLB.slots + ", slots_our = " + ccaBLB.slots_our + ", slots_empty = " + ccaBLB.slots_empty + ", slots_enemy = " + ccaBLB.slots_enemy);

                    slots += ccaBLB.slots;
                    slots_our += ccaBLB.slots_our;
                    slots_empty += ccaBLB.slots_empty;
                    slots_enemy += ccaBLB.slots_enemy;

                    if (ccaBLB.buildingIsOur) {
                        Log.i(TAG, logMsgPref + "ccaBLB buildingIsOur");
                        Log.i(TAG, logMsgPref + "ccaBLB our_points = +" + ccaBLB.our_points);
                        tv_ga_blb_points.setText("+" + ccaBLB.our_points);
                        tv_ga_blb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_ga_blb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBLB.buildingIsEnemy) {
                        Log.i(TAG, logMsgPref + "ccaBLB buildingIsEnemy");
                        Log.i(TAG, logMsgPref + "ccaBLB enemy_points = +" + ccaBLB.enemy_points);
                        tv_ga_blb_points.setText("+" + ccaBLB.enemy_points);
                        tv_ga_blb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_ga_blb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBLB.buildingIsEmpty) {
                        Log.i(TAG, logMsgPref + "ccaBLB buildingIsEmpty");
                        tv_ga_blb_points.setText("");
                        tv_ga_blb_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_ga_blb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBLB.isX2) {
                        Log.i(TAG, logMsgPref + "ccaBLB isX2");
                        tv_ga_blb_x2.setText("X2");
                        tv_ga_blb_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBLB.mayX2) {
                            Log.i(TAG, logMsgPref + "ccaBLB mayX2");
                            tv_ga_blb_x2.setText("X2");
                            tv_ga_blb_x2.setBackgroundColor(color_building_mayX2);
                        } else {
                            tv_ga_blb_x2.setText("");
                            tv_ga_blb_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }

            if (ccaBRT != null) {
                Log.i(TAG, logMsgPref + "ccaBRT != null");
                iv_ga_brt_name.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brt_x2.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brt_points.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brt_slots.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brt_slots_our.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brt_slots_empty.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brt_slots_enemy.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brt_car_black.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brt_car_our.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brt_car_empty.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brt_car_enemy.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brt_progress.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);

                if (ccaBRT.isPresent) {
                    Log.i(TAG, logMsgPref + "ccaBRT is Present");

                    CityCalcArea ccaBRTname = mainCityCalc.mapAreas.get(Area.BRT);
                    CityCalcArea ccaBRTprogress = mainCityCalc.mapAreas.get(Area.BRT_PROGRESS);
                    if (ccaBRTname != null) iv_ga_brt_name.setImageBitmap(ccaBRTname.bmpSrc);
                    Log.i(TAG, logMsgPref + "ccaBRTname = " + ccaBRTname.finText);

                    if (ccaBRTprogress != null) iv_ga_brt_progress.setImageBitmap(ccaBRTprogress.bmpSrc);
                    tv_ga_brt_slots.setText(String.valueOf(ccaBRT.slots));
                    tv_ga_brt_slots_our.setText(String.valueOf(ccaBRT.slots_our));
                    tv_ga_brt_slots_empty.setText(String.valueOf(ccaBRT.slots_empty));
                    tv_ga_brt_slots_enemy.setText(String.valueOf(ccaBRT.slots_enemy));
                    Log.i(TAG, logMsgPref + "ccaBRT: slots = " + ccaBRT.slots + ", slots_our = " + ccaBRT.slots_our + ", slots_empty = " + ccaBRT.slots_empty + ", slots_enemy = " + ccaBRT.slots_enemy);

                    slots += ccaBRT.slots;
                    slots_our += ccaBRT.slots_our;
                    slots_empty += ccaBRT.slots_empty;
                    slots_enemy += ccaBRT.slots_enemy;

                    if (ccaBRT.buildingIsOur) {
                        Log.i(TAG, logMsgPref + "ccaBRT buildingIsOur");
                        Log.i(TAG, logMsgPref + "ccaBRT our_points = +" + ccaBRT.our_points);
                        tv_ga_brt_points.setText("+" + ccaBRT.our_points);
                        tv_ga_brt_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_ga_brt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBRT.buildingIsEnemy) {
                        Log.i(TAG, logMsgPref + "ccaBRT buildingIsEnemy");
                        Log.i(TAG, logMsgPref + "ccaBRT enemy_points = +" + ccaBRT.enemy_points);
                        tv_ga_brt_points.setText("+" + ccaBRT.enemy_points);
                        tv_ga_brt_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_ga_brt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBRT.buildingIsEmpty) {
                        Log.i(TAG, logMsgPref + "ccaBRT buildingIsEmpty");
                        tv_ga_brt_points.setText("");
                        tv_ga_brt_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_ga_brt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBRT.isX2) {
                        tv_ga_brt_x2.setText("X2");
                        Log.i(TAG, logMsgPref + "ccaBRT isX2");
                        tv_ga_brt_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBRT.mayX2) {
                            tv_ga_brt_x2.setText("X2");
                            Log.i(TAG, logMsgPref + "ccaBRT mayX2");
                            tv_ga_brt_x2.setBackgroundColor(color_building_mayX2);
                        } else {
                            tv_ga_brt_x2.setText("");
                            tv_ga_brt_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }

            if (ccaBRC != null) {
                Log.i(TAG, logMsgPref + "ccaBRC != null");
                iv_ga_brc_name.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brc_x2.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brc_points.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brc_slots.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brc_slots_our.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brc_slots_empty.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brc_slots_enemy.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brc_car_black.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brc_car_our.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brc_car_empty.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brc_car_enemy.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brc_progress.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);

                if (ccaBRC.isPresent) {
                    Log.i(TAG, logMsgPref + "ccaBRC is Present");

                    CityCalcArea ccaBRCname = mainCityCalc.mapAreas.get(Area.BRC);
                    CityCalcArea ccaBRCprogress = mainCityCalc.mapAreas.get(Area.BRC_PROGRESS);
                    if (ccaBRCname != null) iv_ga_brc_name.setImageBitmap(ccaBRCname.bmpSrc);
                    Log.i(TAG, logMsgPref + "ccaBRCname = " + ccaBRCname.finText);

                    if (ccaBRCprogress != null) iv_ga_brc_progress.setImageBitmap(ccaBRCprogress.bmpSrc);
                    tv_ga_brc_slots.setText(String.valueOf(ccaBRC.slots));
                    tv_ga_brc_slots_our.setText(String.valueOf(ccaBRC.slots_our));
                    tv_ga_brc_slots_empty.setText(String.valueOf(ccaBRC.slots_empty));
                    tv_ga_brc_slots_enemy.setText(String.valueOf(ccaBRC.slots_enemy));
                    Log.i(TAG, logMsgPref + "ccaBRC: slots = " + ccaBRC.slots + ", slots_our = " + ccaBRC.slots_our + ", slots_empty = " + ccaBRC.slots_empty + ", slots_enemy = " + ccaBRC.slots_enemy);

                    slots += ccaBRC.slots;
                    slots_our += ccaBRC.slots_our;
                    slots_empty += ccaBRC.slots_empty;
                    slots_enemy += ccaBRC.slots_enemy;

                    if (ccaBRC.buildingIsOur) {
                        Log.i(TAG, logMsgPref + "ccaBRC buildingIsOur");
                        Log.i(TAG, logMsgPref + "ccaBRC our_points = +" + ccaBRC.our_points);
                        tv_ga_brc_points.setText("+" + ccaBRC.our_points);
                        tv_ga_brc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_ga_brc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBRC.buildingIsEnemy) {
                        Log.i(TAG, logMsgPref + "ccaBRC buildingIsEnemy");
                        Log.i(TAG, logMsgPref + "ccaBRC enemy_points = +" + ccaBRC.enemy_points);
                        tv_ga_brc_points.setText("+" + ccaBRC.enemy_points);
                        tv_ga_brc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_ga_brc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBRC.buildingIsEmpty) {
                        Log.i(TAG, logMsgPref + "ccaBRC buildingIsEmpty");
                        tv_ga_brc_points.setText("");
                        tv_ga_brc_points.setBackgroundColor(0xFFFFFF);
//                        iv_ga_brc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBRC.isX2) {
                        Log.i(TAG, logMsgPref + "ccaBRC isX2");
                        tv_ga_brc_x2.setText("X2");
                        tv_ga_brc_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBRC.mayX2) {
                            Log.i(TAG, logMsgPref + "ccaBRC mayX2");
                            tv_ga_brc_x2.setText("X2");
                            tv_ga_brc_x2.setBackgroundColor(color_building_mayX2);
                        } else {
                            tv_ga_brc_x2.setText("");
                            tv_ga_brc_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }


            if (ccaBRB != null) {
                Log.i(TAG, logMsgPref + "ccaBRB != null");

                iv_ga_brb_name.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brb_x2.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brb_points.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brb_slots.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brb_slots_our.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brb_slots_empty.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_ga_brb_slots_enemy.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brb_car_black.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brb_car_our.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brb_car_empty.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brb_car_enemy.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_ga_brb_progress.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);

                if (ccaBRB.isPresent) {
                    Log.i(TAG, logMsgPref + "ccaBRB is Present");

                    CityCalcArea ccaBRBname = mainCityCalc.mapAreas.get(Area.BRB);
                    CityCalcArea ccaBRBprogress = mainCityCalc.mapAreas.get(Area.BRB_PROGRESS);
                    if (ccaBRBname != null) iv_ga_brb_name.setImageBitmap(ccaBRBname.bmpSrc);
                    Log.i(TAG, logMsgPref + "ccaBRBname = " + ccaBRBname.finText);


                    if (ccaBRBprogress != null) iv_ga_brb_progress.setImageBitmap(ccaBRBprogress.bmpSrc);
                    tv_ga_brb_slots.setText(String.valueOf(ccaBRB.slots));
                    tv_ga_brb_slots_our.setText(String.valueOf(ccaBRB.slots_our));
                    tv_ga_brb_slots_empty.setText(String.valueOf(ccaBRB.slots_empty));
                    tv_ga_brb_slots_enemy.setText(String.valueOf(ccaBRB.slots_enemy));
                    Log.i(TAG, logMsgPref + "ccaBRB: slots = " + ccaBRB.slots + ", slots_our = " + ccaBRB.slots_our + ", slots_empty = " + ccaBRB.slots_empty + ", slots_enemy = " + ccaBRB.slots_enemy);

                    slots += ccaBRB.slots;
                    slots_our += ccaBRB.slots_our;
                    slots_empty += ccaBRB.slots_empty;
                    slots_enemy += ccaBRB.slots_enemy;

                    if (ccaBRB.buildingIsOur) {
                        Log.i(TAG, logMsgPref + "ccaBRB buildingIsOur");
                        Log.i(TAG, logMsgPref + "ccaBRB our_points = +" + ccaBRB.our_points);
                        tv_ga_brb_points.setText("+" + ccaBRB.our_points);
                        tv_ga_brb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_ga_brb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBRB.buildingIsEnemy) {
                        Log.i(TAG, logMsgPref + "ccaBRB buildingIsEnemy");
                        Log.i(TAG, logMsgPref + "ccaBRB enemy_points = +" + ccaBRB.enemy_points);
                        tv_ga_brb_points.setText("+" + ccaBRB.enemy_points);
                        tv_ga_brb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_ga_brb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBRB.buildingIsEmpty) {
                        Log.i(TAG, logMsgPref + "ccaBRB buildingIsEmpty");
                        tv_ga_brb_points.setText("");
                        tv_ga_brb_points.setBackgroundColor(0xFFFFFF);
//                        iv_ga_brb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBRB.isX2) {
                        Log.i(TAG, logMsgPref + "ccaBRB isX2");
                        tv_ga_brb_x2.setText("X2");
                        tv_ga_brb_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBRC.mayX2) {
                            Log.i(TAG, logMsgPref + "ccaBRB mayX2");
                            tv_ga_brb_x2.setText("X2");
                            tv_ga_brb_x2.setBackgroundColor(color_building_mayX2);
                        } else {
                            tv_ga_brb_x2.setText("");
                            tv_ga_brb_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }

            tv_ga_game_slots.setText(String.valueOf(slots));
            tv_ga_game_slots_our.setText(String.valueOf(slots_our));
            tv_ga_game_slots_empty.setText(String.valueOf(slots_empty));
            tv_ga_game_slots_enemy.setText(String.valueOf(slots_enemy));

        }



        // нотификация
        if (withNotify) {
            Log.i(TAG, logMsgPref + "withNotify");
            if (ccaGame != null) {
                Log.i(TAG, logMsgPref + "создание уведомления: " + ccaGame.ccagStatus);
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
                                .setContentText(ccaGame.ccagStatus)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(ccaGame.ccagStatus));
                createChannelIfNeeded(notificationManager);
                notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
            }
        }

    }

    public void initializeViews() {

        String logMsgPref = "initializeViews: ";
        Log.i(TAG, logMsgPref + "start");

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
        ad_ga_banner = findViewById(R.id.ad_ga_banner);

        iv_ga_game_car_black = findViewById(R.id.iv_ga_game_car_black);
        iv_ga_game_car_our = findViewById(R.id.iv_ga_game_car_our);
        iv_ga_game_car_empty = findViewById(R.id.iv_ga_game_car_empty);
        iv_ga_game_car_enemy = findViewById(R.id.iv_ga_game_car_enemy);
        tv_ga_game_slots = findViewById(R.id.tv_ga_game_slots);
        tv_ga_game_slots_our = findViewById(R.id.tv_ga_game_slots_our);
        tv_ga_game_slots_empty = findViewById(R.id.tv_ga_game_slots_empty);
        tv_ga_game_slots_enemy = findViewById(R.id.tv_ga_game_slots_enemy);

        bt_ga_strategy = findViewById(R.id.bt_ga_strategy);

        ib_ga_car1 = findViewById(R.id.ib_ga_car1);
        tv_ga_car1_name = findViewById(R.id.tv_ga_car1_name);
        iv_ga_car1_health = findViewById(R.id.iv_ga_car1_health);
        tv_ga_car1_health = findViewById(R.id.tv_ga_car1_health);
        iv_ga_car1_shield = findViewById(R.id.iv_ga_car1_shield);
        tv_ga_car1_shield = findViewById(R.id.tv_ga_car1_shield);
        tv_ga_car1_repair = findViewById(R.id.tv_ga_car1_repair);
        iv_ga_car1_house = findViewById(R.id.iv_ga_car1_house);

        ib_ga_car2 = findViewById(R.id.ib_ga_car2);
        tv_ga_car2_name = findViewById(R.id.tv_ga_car2_name);
        iv_ga_car2_health = findViewById(R.id.iv_ga_car2_health);
        tv_ga_car2_health = findViewById(R.id.tv_ga_car2_health);
        iv_ga_car2_shield = findViewById(R.id.iv_ga_car2_shield);
        tv_ga_car2_shield = findViewById(R.id.tv_ga_car2_shield);
        tv_ga_car2_repair = findViewById(R.id.tv_ga_car2_repair);
        iv_ga_car2_house = findViewById(R.id.iv_ga_car2_house);

        ib_ga_car3 = findViewById(R.id.ib_ga_car3);
        tv_ga_car3_name = findViewById(R.id.tv_ga_car3_name);
        iv_ga_car3_health = findViewById(R.id.iv_ga_car3_health);
        tv_ga_car3_health = findViewById(R.id.tv_ga_car3_health);
        iv_ga_car3_shield = findViewById(R.id.iv_ga_car3_shield);
        tv_ga_car3_shield = findViewById(R.id.tv_ga_car3_shield);
        tv_ga_car3_repair = findViewById(R.id.tv_ga_car3_repair);
        iv_ga_car3_house = findViewById(R.id.iv_ga_car3_house);
        
    }

    private void setDataToCarsViews() {
        List<Car> listCars = Car.loadList();

        Car car1 = listCars.get(0);
        Car car2 = listCars.get(1);
        Car car3 = listCars.get(2);
        
        
        CarState car1state = car1.getState();
        if (car1state.equals(CarState.EMPTY)) {
            ib_ga_car1.setImageResource(R.drawable.ic_car1_gray);
        } else if (car1state.equals(CarState.FREE)) {
            ib_ga_car1.setImageResource(R.drawable.ic_car1_blue);
        } else if (car1state.equals(CarState.DEFENCING)) {
            ib_ga_car1.setImageResource(R.drawable.ic_car1_green);
        } else if (car1state.equals(CarState.REPAIRING)) {
            ib_ga_car1.setImageResource(R.drawable.ic_car1_red);
        }
        tv_ga_car1_name.setText(car1.getName());
        tv_ga_car1_health.setText(String.valueOf(car1.getHealth()));
        tv_ga_car1_shield.setText(String.valueOf(car1.getShield()));
        String car1textRepair = car1state.equals(CarState.REPAIRING) ? "\uD83D\uDD27" + " " + car1.getTimeStringToEndRepairing() : "";
        tv_ga_car1_repair.setText(car1textRepair);
        if (car1state.equals(CarState.DEFENCING)) {
            Bitmap car1houseBitmap = null;
            try {
                if (car1.building == 1) car1houseBitmap = mainCityCalc.mapAreas.get(Area.BLT).bmpSrc;
                if (car1.building == 2) car1houseBitmap = mainCityCalc.mapAreas.get(Area.BLC).bmpSrc;
                if (car1.building == 3) car1houseBitmap = mainCityCalc.mapAreas.get(Area.BLB).bmpSrc;
                if (car1.building == 4) car1houseBitmap = mainCityCalc.mapAreas.get(Area.BRT).bmpSrc;
                if (car1.building == 5) car1houseBitmap = mainCityCalc.mapAreas.get(Area.BRC).bmpSrc;
                if (car1.building == 6) car1houseBitmap = mainCityCalc.mapAreas.get(Area.BRB).bmpSrc;
                if (car1houseBitmap != null) {
                    iv_ga_car1_house.setImageBitmap(car1houseBitmap);
                    iv_ga_car1_house.setVisibility(View.VISIBLE);
                }
            } catch (Exception ignored) {
            }
        } else {
            iv_ga_car1_house.setVisibility(View.INVISIBLE);
        }


        CarState car2state = car2.getState();
        if (car2state.equals(CarState.EMPTY)) {
            ib_ga_car2.setImageResource(R.drawable.ic_car2_gray);
        } else if (car2state.equals(CarState.FREE)) {
            ib_ga_car2.setImageResource(R.drawable.ic_car2_blue);
        } else if (car2state.equals(CarState.DEFENCING)) {
            ib_ga_car2.setImageResource(R.drawable.ic_car2_green);
        } else if (car2state.equals(CarState.REPAIRING)) {
            ib_ga_car2.setImageResource(R.drawable.ic_car2_red);
        }
        tv_ga_car2_name.setText(car2.getName());
        tv_ga_car2_health.setText(String.valueOf(car2.getHealth()));
        tv_ga_car2_shield.setText(String.valueOf(car2.getShield()));
        String car2textRepair = car2state.equals(CarState.REPAIRING) ? "\uD83D\uDD27" + " " + car2.getTimeStringToEndRepairing() : "";
        tv_ga_car2_repair.setText(car2textRepair);
        if (car2state.equals(CarState.DEFENCING)) {
            Bitmap car2houseBitmap = null;
            try {
                if (car2.building == 1) car2houseBitmap = mainCityCalc.mapAreas.get(Area.BLT).bmpSrc;
                if (car2.building == 2) car2houseBitmap = mainCityCalc.mapAreas.get(Area.BLC).bmpSrc;
                if (car2.building == 3) car2houseBitmap = mainCityCalc.mapAreas.get(Area.BLB).bmpSrc;
                if (car2.building == 4) car2houseBitmap = mainCityCalc.mapAreas.get(Area.BRT).bmpSrc;
                if (car2.building == 5) car2houseBitmap = mainCityCalc.mapAreas.get(Area.BRC).bmpSrc;
                if (car2.building == 6) car2houseBitmap = mainCityCalc.mapAreas.get(Area.BRB).bmpSrc;
                if (car2houseBitmap != null) {
                    iv_ga_car2_house.setImageBitmap(car2houseBitmap);
                    iv_ga_car2_house.setVisibility(View.VISIBLE);
                }
            } catch (Exception ignored) {
            }
        } else {
            iv_ga_car2_house.setVisibility(View.INVISIBLE);
        }


        CarState car3state = car3.getState();
        if (car3state.equals(CarState.EMPTY)) {
            ib_ga_car3.setImageResource(R.drawable.ic_car3_gray);
        } else if (car3state.equals(CarState.FREE)) {
            ib_ga_car3.setImageResource(R.drawable.ic_car3_blue);
        } else if (car3state.equals(CarState.DEFENCING)) {
            ib_ga_car3.setImageResource(R.drawable.ic_car3_green);
        } else if (car3state.equals(CarState.REPAIRING)) {
            ib_ga_car3.setImageResource(R.drawable.ic_car3_red);
        }
        tv_ga_car3_name.setText(car3.getName());
        tv_ga_car3_health.setText(String.valueOf(car3.getHealth()));
        tv_ga_car3_shield.setText(String.valueOf(car3.getShield()));
        String car3textRepair = car3state.equals(CarState.REPAIRING) ? "\uD83D\uDD27" + " " + car3.getTimeStringToEndRepairing() : "";
        tv_ga_car3_repair.setText(car3textRepair);
        if (car3state.equals(CarState.DEFENCING)) {
            Bitmap car3houseBitmap = null;
            try {
                if (car3.building == 1) car3houseBitmap = mainCityCalc.mapAreas.get(Area.BLT).bmpSrc;
                if (car3.building == 2) car3houseBitmap = mainCityCalc.mapAreas.get(Area.BLC).bmpSrc;
                if (car3.building == 3) car3houseBitmap = mainCityCalc.mapAreas.get(Area.BLB).bmpSrc;
                if (car3.building == 4) car3houseBitmap = mainCityCalc.mapAreas.get(Area.BRT).bmpSrc;
                if (car3.building == 5) car3houseBitmap = mainCityCalc.mapAreas.get(Area.BRC).bmpSrc;
                if (car3.building == 6) car3houseBitmap = mainCityCalc.mapAreas.get(Area.BRB).bmpSrc;
                if (car3houseBitmap != null) {
                    iv_ga_car3_house.setImageBitmap(car3houseBitmap);
                    iv_ga_car3_house.setVisibility(View.VISIBLE);
                }
            } catch (Exception ignored) {
            }
        } else {
            iv_ga_car3_house.setVisibility(View.INVISIBLE);
        }
        
        
    }


    public void readPreferences() {
        String logMsgPref = "readPreferences: ";
        Log.i(TAG, logMsgPref + "start");

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);

        pathToScreenshotDir = sharedPreferences.getString(getString(R.string.pref_screenshot_folder),sharedPreferences.getString(getString(R.string.pref_def_screenshot_folder),""));
        isListenToNewFileInFolder = sharedPreferences.getBoolean(getString(R.string.pref_listen_last_file),sharedPreferences.getBoolean(getString(R.string.pref_def_listen_last_file),false));
        isDebugMode = sharedPreferences.getBoolean(getString(R.string.pref_debug_mode),sharedPreferences.getBoolean(getString(R.string.pref_def_debug_mode),false));
        calibrateX = sharedPreferences.getInt(getString(R.string.pref_calibrate_x),sharedPreferences.getInt(getString(R.string.pref_def_calibrate_x),0));
        calibrateY = sharedPreferences.getInt(getString(R.string.pref_calibrate_y),sharedPreferences.getInt(getString(R.string.pref_def_calibrate_y),0));

        Log.i(TAG, logMsgPref + "pathToScreenshotDir = " + pathToScreenshotDir);
        Log.i(TAG, logMsgPref + "isListenToNewFileInFolder = " + isListenToNewFileInFolder);
        Log.i(TAG, logMsgPref + "isDebugMode = " + isDebugMode);
        Log.i(TAG, logMsgPref + "calibrateX = " + calibrateX);
        Log.i(TAG, logMsgPref + "calibrateY = " + calibrateY);

    }

    private void startTimer() {

        String logMsgPref = "readPreferences: ";
        Log.i(TAG, logMsgPref + "start");

        if (timer == null) {    // если таймер не запущен
            timer = new Timer();    // запускаем таймер
            Log.i(TAG, logMsgPref + "firstTask");
            timer.schedule(new firstTask(), 0,3000); // запускаем такс таймера
            Log.i(TAG, logMsgPref + "secondTask");
            timer.schedule(new secondTask(), 60000,60000); // запускаем такс таймера
        }

    }

    private File getLastFileInFolder(String pathToFolder) {

        String logMsgPref = "getLastFileInFolder: ";
        Log.i(TAG, logMsgPref + "start");

        File temp = null;           // временный файл
        File lastScreenshot = new File(pathToCATScalcFolder, "last_screenshot.PNG"); // последний скри
        Log.i(TAG, logMsgPref + "lastScreenshot = " + lastScreenshot.getAbsolutePath());
        Log.i(TAG, logMsgPref + "pathToFolder = " + pathToFolder);

        File dir = new File(pathToFolder); // папка
        File[] files = dir.listFiles(new FilenameFilter() { // присок файлов в папке по фильтру
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png");  // фильтр по JPG и PNG
            }
        });
        List<File> listFiles = new ArrayList<>(); // лист
        if (files != null) {    // если файлы в папке есть
            Log.i(TAG, logMsgPref + "Файлы в папке есть");
            for (File file : files) {   // цикл по файлам
                listFiles.add(file);    // добавляем файл в лист
            }
        } else {
            Log.i(TAG, logMsgPref + "Файлов в папке нет");
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
                Log.i(TAG, logMsgPref + "Самый свежий файл: " + temp.getAbsolutePath() + ", дата: " + maxLastModified);
                fileLast = temp;
                if (!temp.equals(fileLastInFolder)) {   // если найденный файл не совпадает с раенее найденным "последним файлом"
                    Log.i(TAG, logMsgPref + "найденный файл не совпадает с ранее найденным последним файлом");

                    CityCalc tmpCityCalc = new CityCalc(temp, calibrateX, calibrateY, context);
                    Log.i(TAG, logMsgPref + "CityCalcType = " + tmpCityCalc.cityCalcType);
                    if (!tmpCityCalc.cityCalcType.equals(CityCalcType.ERROR)) {
                        fileLastInFolder = temp;    // последний найденный файл - текущий найденный
                    } else {
                        if (fileLastInFolder == null) {
                            Log.i(TAG, logMsgPref + "вернем последний скриншот если он есть");
                            temp = lastScreenshot;
                            if (!temp.exists()) temp = null;
                        } else {
                            temp = null;
                        }
                    }

                }
            }
        }

        if (temp == null && lastScreenshot.exists()) {
            Log.i(TAG, logMsgPref + "возвращаем " + lastScreenshot.getAbsolutePath());
            temp = lastScreenshot;
        }

        return temp;

    }

    /**
     * Создание канала нотификации
     */
    public static void createChannelIfNeeded(NotificationManager manager) {
        String logMsgPref = "createChannelIfNeeded: ";
        Log.i(TAG, logMsgPref + "start");
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
        String logMsgPref = "onActivityResult: ";
        Log.i(TAG, logMsgPref + "start");

        super.onActivityResult(requestCode, resultCode, data);
        // если произошел возврат со страницы настрок - обновляем контролы в текущей активности
        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY) {
            Log.i(TAG, logMsgPref + "был возврат из предыдущей активности");
            Log.i(TAG, logMsgPref + "вызов readPreferences()");
            readPreferences();
            fileGameScreenshotPrevious = null;
            sw_ga_listen_new_file.setChecked(isListenToNewFileInFolder);
            if (fileGameScreenshot != null) {
                Log.i(TAG, logMsgPref + "fileScreenshot = " + fileGameScreenshot.getAbsolutePath());
                Log.i(TAG, logMsgPref + "инициализация mainCityCalc");
                CityCalc tmpCityCalc = new CityCalc(fileGameScreenshot, calibrateX, calibrateY, context);
                if (tmpCityCalc.cityCalcType.equals(CityCalcType.GAME)) {
                    mainCityCalc = new CityCalc(tmpCityCalc);
                    Log.i(TAG, logMsgPref + "вызов loadDataToViews без нотификации");
                    loadDataToViews(false);
                }
            }



        }

    }

    /**
     *  Созданием меню
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String logMsgPref = "onCreateOptionsMenu: ";
        Log.i(TAG, logMsgPref + "start");
        getMenuInflater().inflate(R.menu.main_menu, menu);  // создаем меню
        return true;
    }

    /**
     * Выбор элементов меню
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String logMsgPref = "onOptionsItemSelected: ";
        Log.i(TAG, logMsgPref + "start");
        int id = item.getItemId();  // айди элемента меню
        switch(id){
            case R.id.menu_open_settings :  // "Настройки"
                Log.i(TAG, logMsgPref + "выбран пункт Настройки");
                Log.i(TAG, logMsgPref + "вызываем openSettings()");
                openSettings();
                return true;
            case R.id.menu_open_screenshot :    // "Открыть скриншот"
                Log.i(TAG, logMsgPref + "выбран пункт Открыть скриншот");
                Log.i(TAG, logMsgPref + "вызываем selectScreenshot()");
                selectScreenshot();
                return true;
//            case R.id.menu_open_language :  // "Язык"
//                Log.i(TAG, logMsgPref + "выбран пункт Язык");
//                Log.i(TAG, logMsgPref + "вызываем openLanguage()");
//                openLanguage();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     *  Выбор скриншота
     */
    private void selectScreenshot() {

        String logMsgPref = "selectScreenshot: ";
        Log.i(TAG, logMsgPref + "start");

        OpenFileDialog fileDialog = new OpenFileDialog(this, pathToScreenshotDir)   // диалог выбора скриншота по переданному пути
                .setFolderIcon(ContextCompat.getDrawable(context, R.drawable.ic_folder))            // иконка папки
                .setFileIcon(ContextCompat.getDrawable(context, R.drawable.ic_file))                // иконка файла
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {
                        String logMsgPref = "OnSelectedFile: ";
                        File newFile = new File(fileName);
                        fileLast = newFile;
                        CityCalc tmpCityCalc = new CityCalc(newFile, calibrateX, calibrateY, context);
                        if (tmpCityCalc.cityCalcType.equals(CityCalcType.GAME)) {
                            fileGameScreenshot = newFile; // файл скриншота - возавращенный из диалога
                            if (!fileGameScreenshot.getAbsolutePath().equals(pathToCATScalcFolder + "/last_screenshot.PNG")) Utils.copyFile(fileGameScreenshot.getAbsolutePath(), pathToCATScalcFolder + "/last_screenshot.PNG");
                            isListenToNewFileInFolder = false; // снимаем флажок "Следить за файлами в папке"
                            sw_ga_listen_new_file.setChecked(false); // устанавливаем контрол флажка
                            fileLastInFolder = null;    // сбрасываем последний файл в папке

                            Log.i(TAG, logMsgPref + "fileScreenshot = " + fileGameScreenshot.getAbsolutePath());
                            Log.i(TAG, logMsgPref + "инициализинуем mainCityCalc");
                            mainCityCalc = new CityCalc(tmpCityCalc);
                            Log.i(TAG, logMsgPref + "вызываем loadDataToViews()");
                            loadDataToViews(true);
                        } else if (tmpCityCalc.cityCalcType.equals(CityCalcType.CAR)) {
                            fileCarScreenshot = newFile;
                        }
                    }
                });
        fileDialog.show();

    }

    /**
     * Открытие Настроек
     */
    private void openSettings() {
        String logMsgPref = "openSettings: ";
        Log.i(TAG, logMsgPref + "start");
        Intent intent = new Intent(this, SettingsActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }

    private void openLanguage() {
        String logMsgPref = "openLanguage: ";
        Log.i(TAG, logMsgPref + "start");
        Intent intent = new Intent(this, LanguageActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }

    public void openStrategyActivity(View view) {
        String logMsgPref = "openStrategyActivity: ";
        Log.i(TAG, logMsgPref + "start");
        Intent intent = new Intent(this, StrategyActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, 0);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }

    public void openCar1(View view) {
        CarActivity.slot = 1;
        Intent intent = new Intent(this, CarActivity.class);
        startActivityForResult(intent, 0);
    }

    public void openCar2(View view) {
        CarActivity.slot = 2;
        Intent intent = new Intent(this, CarActivity.class);
        startActivityForResult(intent, 0);
    }

    public void openCar3(View view) {
        CarActivity.slot = 3;
        Intent intent = new Intent(this, CarActivity.class);
        startActivityForResult(intent, 0);
    }


    class firstTask extends TimerTask {

        @Override
        public void run() {

            String logMsgPref = "firstTask: ";
//            Log.i(TAG, logMsgPref + "run()");

            GameActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    String logMsgPref = "firstTask: ";
//                    Log.i(TAG, logMsgPref + "runOnUiThread()");

                    setDataToCarsViews();

                    if (isListenToNewFileInFolder) {    // если установлен флажок "Следить за файлами в папке"
//                        Log.i(TAG, logMsgPref + "Следить за файлами в папке");
//                        Log.i(TAG, logMsgPref + "Запрашиваем последний файл в папке");
                        File tmpFile = getLastFileInFolder(pathToScreenshotDir);    // получаем последний файл из папки
                        if (tmpFile != null) {  // если он не пустой
//                            Log.i(TAG, logMsgPref + "Последний файл не пустой");
                            if ((!tmpFile.equals(fileGameScreenshot)  && !tmpFile.equals(fileCarScreenshot)) || isResumed) {  // если он не равен текущем скриншоту
                                Log.i(TAG, logMsgPref + "Последний файл не равен текущем скриншотам");
                                Log.i(TAG, logMsgPref + "tmpFile = " + tmpFile.getAbsolutePath());

                                Log.i(TAG, logMsgPref + "инициализинуем mainCityCalc");
                                CityCalc tmpCityCalc = new CityCalc(tmpFile, calibrateX, calibrateY, context);
                                if (tmpCityCalc.cityCalcType.equals(CityCalcType.GAME)) {
                                    fileGameScreenshot = tmpFile;   // текущий скриншот = последнему файлу в папке

                                    if (!fileGameScreenshot.getAbsolutePath().equals(pathToCATScalcFolder + "/last_screenshot.PNG")) {
                                        Log.i(TAG, logMsgPref + "fileScreenshot != last_screenshot.PNG");
                                        Log.i(TAG, logMsgPref + "Вызываем копирование файла fileScreenshot в last_screenshot.PNG");
                                        Utils.copyFile(fileGameScreenshot.getAbsolutePath(), pathToCATScalcFolder + "/last_screenshot.PNG");
                                    }

                                    mainCityCalc = new CityCalc(tmpCityCalc);
                                    Log.i(TAG, logMsgPref + "вызываем loadDataToViews()");
                                    loadDataToViews(true);
                                } else if (tmpCityCalc.cityCalcType.equals(CityCalcType.CAR)) {
                                    fileCarScreenshot = tmpFile;
                                }
                                if (isResumed) isResumed = false;
                            }
                        }

                    } else {
//                        Log.i(TAG, logMsgPref + "Не следить за файлами в папке");
                        if (fileGameScreenshot == null) {
                            File lastScreenshot = new File(pathToCATScalcFolder, "last_screenshot.PNG"); // последний скри
                            fileLast = lastScreenshot;
                            if (lastScreenshot.exists()) {
                                fileGameScreenshot = lastScreenshot;
                                Log.i(TAG, logMsgPref + "fileScreenshot = " + fileGameScreenshot.getAbsolutePath());
                                Log.i(TAG, logMsgPref + "инициализинуем mainCityCalc");
                                CityCalc tmpCityCalc = new CityCalc(fileGameScreenshot, calibrateX, calibrateY, context);
                                if (tmpCityCalc.cityCalcType.equals(CityCalcType.GAME)) {
                                    mainCityCalc = new CityCalc(tmpCityCalc);
                                    Log.i(TAG, logMsgPref + "вызываем loadDataToViews()");
                                    loadDataToViews(true);
                                }
                            }
                        }

                    }

                }
            });
        }
    };


    class secondTask extends TimerTask {

        @Override
        public void run() {

            String logMsgPref = "secondTask: ";
//            Log.i(TAG, logMsgPref + "run()");

            GameActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    String logMsgPref = "secondTask: ";
//                    Log.i(TAG, logMsgPref + "runOnUiThread()");

                    if (mainCityCalc != null) {
//                        Log.i(TAG, logMsgPref + "mainCityCalc не null");
                        CCAGame ccaGame = (CCAGame) mainCityCalc.mapAreas.get(Area.CITY);
                        if (ccaGame != null) {
                            Log.i(TAG, logMsgPref + "ccaGame не null");
                            Log.i(TAG, logMsgPref + "вызываем ccaGame.calcWin()");
                            ccaGame.calcWin();
                            Log.i(TAG, logMsgPref + "вызываем loadDataToViews() без нотификации");
                            loadDataToViews(false);
                        }
                    }

                }
            });
        }
    };


}
