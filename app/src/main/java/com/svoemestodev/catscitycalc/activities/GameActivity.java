package com.svoemestodev.catscitycalc.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.svoemestodev.catscitycalc.BuildConfig;
import com.svoemestodev.catscitycalc.adapters.ListBuildingAdapter;
import com.svoemestodev.catscitycalc.adapters.ListTeamsAdapter;
import com.svoemestodev.catscitycalc.classes.Building;
import com.svoemestodev.catscitycalc.classes.Car;
import com.svoemestodev.catscitycalc.database.Database;
import com.svoemestodev.catscitycalc.database.DbCar;
import com.svoemestodev.catscitycalc.database.DbTeam;
import com.svoemestodev.catscitycalc.database.DbTeamGame;
import com.svoemestodev.catscitycalc.database.DbUser;
import com.svoemestodev.catscitycalc.database.UserRole;
import com.svoemestodev.catscitycalc.utils.OpenFileDialog;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;
import com.svoemestodev.catscitycalc.utils.Utils;
import com.svoemestodev.catscitycalc.citycalcclasses.Area;
import com.svoemestodev.catscitycalc.citycalcclasses.CCABuilding;
import com.svoemestodev.catscitycalc.citycalcclasses.CCACar;
import com.svoemestodev.catscitycalc.citycalcclasses.CCAGame;
import com.svoemestodev.catscitycalc.citycalcclasses.CCATeam;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalc;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalcType;
import com.svoemestodev.catscitycalc.database.DbTeamUser;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    // Game views

    TextView ga_tv_user;                // имя пользователя, банда, роль
    TextView ga_tv_screenshot_time;     // информация о времени последнего скриншота
    Switch ga_sw_listen_new_file;       // переключатель "следить за файлами в папке"
    TextView ga_tv_start_game_time;     // время начала игры
    TextView ga_tv_end_game_time;       // время конца игры
    TextView ga_tv_status;              // текущий статус игры
    TextView ga_tv_vs;                  // "против"
    
    // Time views
    TextView ga_tv_total_time;          // время до конца игры
    TextView ga_tv_early_win;           // очки до досрочной победы
    
    // Our team views
    ImageView ga_iv_our_team_name;      // название нашей команды (картинка)
    TextView ga_tv_our_increase;        // прибавка нашей команды
    TextView ga_tv_our_points;          // очки нашей команды
    TextView ga_tv_our_end_time;        // время до конца игры для нашей команды
    
    // Enemy team views
    ImageView ga_iv_enemy_team_name;    // название команды противника (картинка)
    TextView ga_tv_enemy_increase;      // прибавка команды противника
    TextView ga_tv_enemy_points;        // очки команды противника
    TextView ga_tv_enemy_end_time;      // время до конца игры для команды противника
    
    // BLT views
    ImageView ga_iv_blt_name;           // blt - название здания (картинка)
    TextView ga_tv_blt_x2;              // blt - х2
    TextView ga_tv_blt_points;          // blt - очки
    TextView ga_tv_blt_slots;           // blt - слоты 
    TextView ga_tv_blt_slots_our;       // blt - наши слоты
    TextView ga_tv_blt_slots_empty;     // blt - слоты противника
    TextView ga_tv_blt_slots_enemy;     // blt - пустые слоты
    ImageView ga_iv_blt_car_black;      // blt - картинка машинки (черная)
    ImageView ga_iv_blt_car_our;        // blt - картинка машинки (синяя)
    ImageView ga_iv_blt_car_empty;      // blt - картинка машинки (серая)
    ImageView ga_iv_blt_car_enemy;      // blt - картинка машинки (красная)
    ImageView ga_iv_blt_progress;       // blt - прогресс

    // BLC views
    ImageView ga_iv_blc_name;           // blc - название здания (картинка)
    TextView ga_tv_blc_x2;              // blc - х2
    TextView ga_tv_blc_points;          // blc - очки
    TextView ga_tv_blc_slots;           // blc - слоты 
    TextView ga_tv_blc_slots_our;       // blc - наши слоты
    TextView ga_tv_blc_slots_empty;     // blc - слоты противника
    TextView ga_tv_blc_slots_enemy;     // blc - пустые слоты
    ImageView ga_iv_blc_car_black;      // blc - картинка машинки (черная)
    ImageView ga_iv_blc_car_our;        // blc - картинка машинки (синяя)
    ImageView ga_iv_blc_car_empty;      // blc - картинка машинки (серая)
    ImageView ga_iv_blc_car_enemy;      // blc - картинка машинки (красная)
    ImageView ga_iv_blc_progress;       // blc - прогресс

    // BLB views
    ImageView ga_iv_blb_name;           // blb - название здания (картинка)
    TextView ga_tv_blb_x2;              // blb - х2
    TextView ga_tv_blb_points;          // blb - очки
    TextView ga_tv_blb_slots;           // blb - слоты 
    TextView ga_tv_blb_slots_our;       // blb - наши слоты
    TextView ga_tv_blb_slots_empty;     // blb - слоты противника
    TextView ga_tv_blb_slots_enemy;     // blb - пустые слоты
    ImageView ga_iv_blb_car_black;      // blb - картинка машинки (черная)
    ImageView ga_iv_blb_car_our;        // blb - картинка машинки (синяя)
    ImageView ga_iv_blb_car_empty;      // blb - картинка машинки (серая)
    ImageView ga_iv_blb_car_enemy;      // blb - картинка машинки (красная)
    ImageView ga_iv_blb_progress;       // blb - прогресс

    // BRT views
    ImageView ga_iv_brt_name;           // brt - название здания (картинка)
    TextView ga_tv_brt_x2;              // brt - х2
    TextView ga_tv_brt_points;          // brt - очки
    TextView ga_tv_brt_slots;           // brt - слоты 
    TextView ga_tv_brt_slots_our;       // brt - наши слоты
    TextView ga_tv_brt_slots_empty;     // brt - слоты противника
    TextView ga_tv_brt_slots_enemy;     // brt - пустые слоты
    ImageView ga_iv_brt_car_black;      // brt - картинка машинки (черная)
    ImageView ga_iv_brt_car_our;        // brt - картинка машинки (синяя)
    ImageView ga_iv_brt_car_empty;      // brt - картинка машинки (серая)
    ImageView ga_iv_brt_car_enemy;      // brt - картинка машинки (красная)
    ImageView ga_iv_brt_progress;       // brt - прогресс

    // BRC views
    ImageView ga_iv_brc_name;           // brc - название здания (картинка)
    TextView ga_tv_brc_x2;              // brc - х2
    TextView ga_tv_brc_points;          // brc - очки
    TextView ga_tv_brc_slots;           // brc - слоты 
    TextView ga_tv_brc_slots_our;       // brc - наши слоты
    TextView ga_tv_brc_slots_empty;     // brc - слоты противника
    TextView ga_tv_brc_slots_enemy;     // brc - пустые слоты
    ImageView ga_iv_brc_car_black;      // brc - картинка машинки (черная)
    ImageView ga_iv_brc_car_our;        // brc - картинка машинки (синяя)
    ImageView ga_iv_brc_car_empty;      // brc - картинка машинки (серая)
    ImageView ga_iv_brc_car_enemy;      // brc - картинка машинки (красная)
    ImageView ga_iv_brc_progress;       // brc - прогресс

    // BRB views
    ImageView ga_iv_brb_name;           // brb - название здания (картинка)
    TextView ga_tv_brb_x2;              // brb - х2
    TextView ga_tv_brb_points;          // brb - очки
    TextView ga_tv_brb_slots;           // brb - слоты 
    TextView ga_tv_brb_slots_our;       // brb - наши слоты
    TextView ga_tv_brb_slots_empty;     // brb - слоты противника
    TextView ga_tv_brb_slots_enemy;     // brb - пустые слоты
    ImageView ga_iv_brb_car_black;      // brb - картинка машинки (черная)
    ImageView ga_iv_brb_car_our;        // brb - картинка машинки (синяя)
    ImageView ga_iv_brb_car_empty;      // brb - картинка машинки (серая)
    ImageView ga_iv_brb_car_enemy;      // brb - картинка машинки (красная)
    ImageView ga_iv_brb_progress;       // blt - прогресс

    // Рекламный блок
    AdView ga_ad_banner;                // баннер

    ImageView ga_iv_game_car_black;     // машинка черная большая (картинка)
    ImageView ga_iv_game_car_our;       // машинка синяя большая (картинка)
    ImageView ga_iv_game_car_empty;     // машинка серая большая (картинка)
    ImageView ga_iv_game_car_enemy;     // машинка красная большая (картинка)
    TextView ga_tv_game_slots;          // слотов в игре всего
    TextView ga_tv_game_slots_our;      // слотов в игре наших
    TextView ga_tv_game_slots_empty;    // слотов в игре пустых
    TextView ga_tv_game_slots_enemy;    // слотов в игре противника

    ImageButton ga_ib_car1;             // кнопка-картинка 1-й машинки
    TextView ga_tv_car1_number;         // номер 1-й машинки
    TextView ga_tv_car1_name;           // название 1-й машинки
    ImageView ga_iv_car1_health;        // картинка "здоровье" для 1-й машинки
    TextView ga_tv_car1_health;         // здоровье 1-й машинки
    ImageView ga_iv_car1_shield;        // картинка "щит" 1-й машинки
    TextView ga_tv_car1_shield;         // щит 1-й машинки
    TextView ga_tv_car1_repair;         // время ремонта 1-й машинки
    ImageView ga_iv_car1_building;      // картинка названия здания 1-й машинки
    ImageView ga_iv_car1_task;          // картинка названия здания задания 1-й машинки

    ImageButton ga_ib_car2;             // кнопка-картинка 2-й машинки
    TextView ga_tv_car2_number;         // номер 2-й машинки
    TextView ga_tv_car2_name;           // название 2-й машинки
    ImageView ga_iv_car2_health;        // картинка "здоровье" для 2-й машинки
    TextView ga_tv_car2_health;         // здоровье 2-й машинки
    ImageView ga_iv_car2_shield;        // картинка "щит" 2-й машинки
    TextView ga_tv_car2_shield;         // щит 2-й машинки
    TextView ga_tv_car2_repair;         // время ремонта 2-й машинки
    ImageView ga_iv_car2_building;      // картинка названия здания 2-й машинки
    ImageView ga_iv_car2_task;          // картинка названия здания задания 2-й машинки

    ImageButton ga_ib_car3;             // кнопка-картинка 3-й машинки
    TextView ga_tv_car3_number;         // номер 3-й машинки
    TextView ga_tv_car3_name;           // название 3-й машинки
    ImageView ga_iv_car3_health;        // картинка "здоровье" для 3-й машинки
    TextView ga_tv_car3_health;         // здоровье 3-й машинки
    ImageView ga_iv_car3_shield;        // картинка "щит" 3-й машинки
    TextView ga_tv_car3_shield;         // щит 3-й машинки
    TextView ga_tv_car3_repair;         // время ремонта 3-й машинки
    ImageView ga_iv_car3_building;      // картинка названия здания 3-й машинки
    ImageView ga_iv_car3_task;          // картинка названия здания задания 3-й машинки
    
    Button ga_bt_strategy;              // кнопка "Стратегичское планирование"
    
    private static final int SIGN_IN_REQUEST_CODE = 1;

    public transient static String pathToCATScalcFolder;        // путь к папке программы
    public static final int REQUEST_CODE_SECOND_ACTIVITY = 100; // код реквеста для вызова второй активности
    public static File fileGameScreenshot = null;               // текущий файл скриншота
    public static File fileGameScreenshotPrevious;              // предыдущий файл скриншота
    public static File fileLastInFolder;                        // последний файл в папке
    public static File fileCarScreenshot = null;                // текущий файл скриншота
    public static File fileCarScreenshotPrevious;               // предыдущий файл скриншота
    public static File fileLast;                                // последний файл в папке
    private NotificationManager notificationManager;            // нотификатор
    private static final int NOTIFY_ID = 1;                     // айди нотификатора
    private static final String CHANNEL_ID = "C.A.T.S. City Calculator Channel #1";   // канал нотификатора
    public static String pathToScreenshotDir = "";              // путь к папке скриншотов
    public static boolean isListenToNewFileInFolder;            // флаг "Следить за новыми файлами в папке"
    public static boolean isAllFieldsCorrect;                   // флаг "Все поля заполнены правильно"
    public static int calibrateX;                              // калибровка
    public static int calibrateY;                              // калибровка
    private Timer timer;                                        // таймер
    public static boolean isDebugMode;                          // флаг "Режим отладки"
    public static Context context;                              // контекст
    public static CityCalc mainCityCalc;                        // текущая игра

    private static final String TAG = "GameActivity";           // таг для лога

    public boolean isResumed;                                   // isResumed

    public static FirebaseUser fbUser;          // юзер файрбейса
    public static FirebaseAuth fbAuth;          // аутентификация файрбейса
    public static FirebaseStorage fbStor = FirebaseStorage.getInstance();       // стораж файрбейса
    public static FirebaseFirestore fbDb = FirebaseFirestore.getInstance(); // база данных файрбейса
    public static boolean userHaveTeam;         // флаг "у юзера есть команда"
    public static UserRole userRole;            // роль юзера в команде
    public static String userTeamID;            // ID команды
    public static DbUser mainDbUser;            // юзер БД
    public static DbTeam mainDbTeam;            // банда БД
    public static DbTeamUser mainDbTeamUser;    // юзер банды БД

    public static Menu mainMenu;                                // меню
    public static MenuItem menu_main_open_settings;             // пункт меню "Настройки"
    public static MenuItem menu_main_open_screenshot;           // пункт меню "Открыть скриншот"
    public static MenuItem menu_main_user_account;              // пункт меню "Кабинет пользователя"
    public static MenuItem menu_main_share;                     // пункт меню "Шара"
    public static MenuItem menu_main_login;                     // пункт меню "Логин"
    public static MenuItem menu_main_logout;                    // пункт меню "Логаут"
    public static MenuItem menu_main_email_verification_send;   // пункт меню "Заслать письмо с подтверждением почты"
    public static MenuItem menu_main_email_verification_check;  // пункт меню "Зарефрешить юзера"
    public static MenuItem menu_main_team_manage;               // пункт меню "Банда"
    public static MenuItem menu_main_team_create;               // пункт меню "Создать банду"
    public static MenuItem menu_main_team_leave;                // пункт меню "Выйти из банды"
    public static MenuItem menu_main_team_find;                 // пункт меню "Найти банду"

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

        fbAuth = FirebaseAuth.getInstance();
        fbUser = fbAuth.getCurrentUser();

        context = getBaseContext();

        setContentView(R.layout.activity_game);

        initializeViews(); // Инициализация вьюшек

        ga_sw_listen_new_file.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        ga_ad_banner.loadAd(adRequest);

        // нотификейшт менеджер
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        readPreferences(); // считываем преференцы
        ga_sw_listen_new_file.setChecked(isListenToNewFileInFolder);
        startTimer();   // стартуем таймер

    }

    public void loadDataToViews(boolean withNotify) {

        String logMsgPref = "loadDataToViews: ";

        String textStartGameTime;
        String textEndGameTime;
        CCAGame ccaGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);
        CCATeam ccaOurTeam = (CCATeam) mainCityCalc.getMapAreas().get(Area.TEAM_NAME_OUR);
        CCATeam ccaEnemyTeam = (CCATeam) mainCityCalc.getMapAreas().get(Area.TEAM_NAME_ENEMY);
        CCABuilding ccaBLT = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLT);
        CCABuilding ccaBLC = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLC);
        CCABuilding ccaBLB = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLB);
        CCABuilding ccaBRT = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRT);
        CCABuilding ccaBRC = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRC);
        CCABuilding ccaBRB = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRB);
        String pattern = "dd MMM HH:mm";

        if (ccaGame != null && ccaGame.getCcagDateStartGame() != null) {

            ccaGame.calcWin();

            Date dateScreenshot = ccaGame.getCcagDateScreenshot();
            int minutesFromTakingScreenshot = (int)((Calendar.getInstance().getTime().getTime() - dateScreenshot.getTime()) / 60000);
            String screenshotTimeText = getString(R.string.screenshot_will_create) + " " + minutesFromTakingScreenshot + " " + getString(R.string.minutes_ago) + (ccaGame.getUserNIC() != null ? " " + getString(R.string.by_user) + " " + ccaGame.getUserNIC() + "." : ".");
            ga_tv_screenshot_time.setText(screenshotTimeText);
            ga_tv_screenshot_time.setTextColor(minutesFromTakingScreenshot >= 10 ? Color.RED :  Color.BLACK);

            ga_bt_strategy.setVisibility(!ccaGame.isCcagIsGameOver() ? View.VISIBLE : View.INVISIBLE);

            textStartGameTime = getString(R.string.start_game_at) + ": " + Utils.convertDateToString(ccaGame.getCcagDateStartGame(), pattern);    // дата/время начала игры

            textEndGameTime = getString(R.string.end_game_at) + ": "  + Utils.convertDateToString(ccaGame.getCcagDateEndGame(), pattern);          // дата/время окончания игры

            ga_tv_status.setText(ccaGame.getCcagStatus());   // статус
            ga_tv_start_game_time.setText(textStartGameTime);   // дата/время начала игры
            ga_tv_end_game_time.setText(textEndGameTime);       // дата/время окончания игры

            if (ccaGame.isCcagIsGameOver()) {   // если игра закончена
                ga_tv_total_time.setText("");   // время игры - пустое
            } else { // если игра не закончена
                ga_tv_total_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToEndGame())); // время игры
            }

            ga_tv_early_win.setText(String.valueOf(ccaGame.getCcagEarlyWin())); // очки до досрочной победы

            if (ccaOurTeam != null) ga_iv_our_team_name.setImageBitmap(ccaOurTeam.getBmpSrc());  // имя нашей команды
            if (ccaEnemyTeam != null) ga_iv_enemy_team_name.setImageBitmap(ccaEnemyTeam.getBmpSrc());  // имя команды противника


            ga_tv_our_increase.setText(ccaGame.getCcagIncreaseOur() == 0 ? "" : " +" + ccaGame.getCcagIncreaseOur() + " ");   // прирост нашей команды
            ga_tv_our_points.setText(String.valueOf(ccaGame.getPointsOur()));  // очки нашей команды

            ga_tv_enemy_increase.setText(ccaGame.getCcagIncreaseEnemy() == 0 ? "" : " +" + ccaGame.getCcagIncreaseEnemy() + " "); // прирост команды противника
            ga_tv_enemy_points.setText(String.valueOf(ccaGame.getPointsEnemy()));    // очки команды противника

            if (ccaGame.isCcagIsGameOver()) {   // если игра закончена
                ga_tv_our_end_time.setText(""); // наше время пустое
                ga_tv_enemy_end_time.setText(""); // время противника пустое
            } else { // если игра незакончена
                if (ccaGame.isCcagWillOurWin()) {
                    ga_tv_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                    ga_tv_enemy_end_time.setText("");   // время противника пустое
                } else if (ccaGame.isCcagWillEnemyWin()) {
                    ga_tv_our_end_time.setText(""); // наше время пустое
                    ga_tv_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                } else if (ccaGame.isCcagWillNobodyWin()) {
                    ga_tv_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                    ga_tv_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                }

            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);
            int color_bxx_mayX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_mayX2_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_mayX2_main), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_mayX2), 16)));
            int color_bxx_isX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_isX2_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_isX2_main), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_isX2), 16)));

            int slots = 0, slots_our = 0, slots_empty = 0, slots_enemy = 0;

            ga_iv_blt_name.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blt_x2.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blt_points.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blt_slots.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blt_slots_our.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blt_slots_empty.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blt_slots_enemy.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blt_car_black.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blt_car_our.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blt_car_empty.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blt_car_enemy.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blt_progress.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_blt_progress_our.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_blt_progress_empty.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_blt_progress_enemy.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);

            int color_progress_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_our_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_our), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_our_main), 16)));
            int color_progress_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_enemy_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_enemy), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_enemy_main), 16)));
            int color_progress_empty = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_empty_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_empty), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_empty_main), 16)));
            int progressBitmapWidth = 300;
            int progressBitmapHeight = 20;
            
            if (ccaGame.isPresent_blt()) {

                if (ccaBLT != null) ga_iv_blt_name.setImageBitmap(ccaBLT.getBmpSrc());

                ga_iv_blt_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight, 
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy}, 
                        new int[]{ccaGame.getSlots_blt_our(), ccaGame.getSlots_blt_empty(), ccaGame.getSlots_blt_enemy()}));
//                ga_tv_blt_progress_our.setText(Utils.getProgressString(ccaGame.getSlots_blt_our()));
//                ga_tv_blt_progress_empty.setText(Utils.getProgressString(ccaGame.getSlots_blt_empty()));
//                ga_tv_blt_progress_enemy.setText(Utils.getProgressString(ccaGame.getSlots_blt_enemy()));

                ga_tv_blt_slots.setText(String.valueOf(ccaGame.getSlots_blt()));
                ga_tv_blt_slots_our.setText(String.valueOf(ccaGame.getSlots_blt_our()));
                ga_tv_blt_slots_empty.setText(String.valueOf(ccaGame.getSlots_blt_empty()));
                ga_tv_blt_slots_enemy.setText(String.valueOf(ccaGame.getSlots_blt_enemy()));

                slots += ccaGame.getSlots_blt();
                slots_our += ccaGame.getSlots_blt_our();
                slots_empty += ccaGame.getSlots_blt_empty();
                slots_enemy += ccaGame.getSlots_blt_enemy();

                if (ccaGame.isBuildingIsOur_blt()) {
                    ga_tv_blt_points.setText("+" + ccaGame.getOur_points_blt());
                    ga_tv_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_blt()) {
                    ga_tv_blt_points.setText("+" + ccaGame.getEnemy_points_blt());
                    ga_tv_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_blt()) {
                    ga_tv_blt_points.setText("");
                    ga_tv_blt_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_blt()) {
                    ga_tv_blt_x2.setText("X2");
                    ga_tv_blt_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_blt()) {
                        ga_tv_blt_x2.setText("X2");
                        ga_tv_blt_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        ga_tv_blt_x2.setText("");
                        ga_tv_blt_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }

            ga_iv_blc_name.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blc_x2.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blc_points.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blc_slots.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blc_slots_our.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blc_slots_empty.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blc_slots_enemy.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blc_car_black.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blc_car_our.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blc_car_empty.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blc_car_enemy.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blc_progress.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_blc_progress_our.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_blc_progress_empty.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_blc_progress_enemy.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);

            if (ccaGame.isPresent_blc()) {

                if (ccaBLC != null) ga_iv_blc_name.setImageBitmap(ccaBLC.getBmpSrc());
                ga_iv_blc_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                        new int[]{ccaGame.getSlots_blc_our(), ccaGame.getSlots_blc_empty(), ccaGame.getSlots_blc_enemy()}));
//                ga_tv_blc_progress_our.setText(Utils.getProgressString(ccaGame.getSlots_blc_our()));
//                ga_tv_blc_progress_empty.setText(Utils.getProgressString(ccaGame.getSlots_blc_empty()));
//                ga_tv_blc_progress_enemy.setText(Utils.getProgressString(ccaGame.getSlots_blc_enemy()));

                ga_tv_blc_slots.setText(String.valueOf(ccaGame.getSlots_blc()));
                ga_tv_blc_slots_our.setText(String.valueOf(ccaGame.getSlots_blc_our()));
                ga_tv_blc_slots_empty.setText(String.valueOf(ccaGame.getSlots_blc_empty()));
                ga_tv_blc_slots_enemy.setText(String.valueOf(ccaGame.getSlots_blc_enemy()));

                slots += ccaGame.getSlots_blc();
                slots_our += ccaGame.getSlots_blc_our();
                slots_empty += ccaGame.getSlots_blc_empty();
                slots_enemy += ccaGame.getSlots_blc_enemy();

                if (ccaGame.isBuildingIsOur_blc()) {
                    ga_tv_blc_points.setText("+" + ccaGame.getOur_points_blc());
                    ga_tv_blc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_blc()) {
                    ga_tv_blc_points.setText("+" + ccaGame.getEnemy_points_blc());
                    ga_tv_blc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_blc()) {
                    ga_tv_blc_points.setText("");
                    ga_tv_blc_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_blc()) {
                    ga_tv_blc_x2.setText("X2");
                    ga_tv_blc_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_blc()) {
                        ga_tv_blc_x2.setText("X2");
                        ga_tv_blc_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        ga_tv_blc_x2.setText("");
                        ga_tv_blc_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }


            ga_iv_blb_name.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blb_x2.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blb_points.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blb_slots.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blb_slots_our.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blb_slots_empty.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_blb_slots_enemy.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blb_car_black.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blb_car_our.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blb_car_empty.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blb_car_enemy.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_blb_progress.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_blb_progress_our.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_blb_progress_empty.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_blb_progress_enemy.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);

            if (ccaGame.isPresent_blb()) {

                if (ccaBLB != null) ga_iv_blb_name.setImageBitmap(ccaBLB.getBmpSrc());

                ga_iv_blb_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                        new int[]{ccaGame.getSlots_blb_our(), ccaGame.getSlots_blb_empty(), ccaGame.getSlots_blb_enemy()}));
//                ga_tv_blb_progress_our.setText(Utils.getProgressString(ccaGame.getSlots_blb_our()));
//                ga_tv_blb_progress_empty.setText(Utils.getProgressString(ccaGame.getSlots_blb_empty()));
//                ga_tv_blb_progress_enemy.setText(Utils.getProgressString(ccaGame.getSlots_blb_enemy()));

                ga_tv_blb_slots.setText(String.valueOf(ccaGame.getSlots_blb()));
                ga_tv_blb_slots_our.setText(String.valueOf(ccaGame.getSlots_blb_our()));
                ga_tv_blb_slots_empty.setText(String.valueOf(ccaGame.getSlots_blb_empty()));
                ga_tv_blb_slots_enemy.setText(String.valueOf(ccaGame.getSlots_blb_enemy()));

                slots += ccaGame.getSlots_blb();
                slots_our += ccaGame.getSlots_blb_our();
                slots_empty += ccaGame.getSlots_blb_empty();
                slots_enemy += ccaGame.getSlots_blb_enemy();

                if (ccaGame.isBuildingIsOur_blb()) {
                    ga_tv_blb_points.setText("+" + ccaGame.getOur_points_blb());
                    ga_tv_blb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_blb()) {
                    ga_tv_blb_points.setText("+" + ccaGame.getEnemy_points_blb());
                    ga_tv_blb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_blb()) {
                    ga_tv_blb_points.setText("");
                    ga_tv_blb_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_blb()) {
                    ga_tv_blb_x2.setText("X2");
                    ga_tv_blb_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_blb()) {
                        ga_tv_blb_x2.setText("X2");
                        ga_tv_blb_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        ga_tv_blb_x2.setText("");
                        ga_tv_blb_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }


            ga_iv_brt_name.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brt_x2.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brt_points.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brt_slots.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brt_slots_our.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brt_slots_empty.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brt_slots_enemy.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brt_car_black.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brt_car_our.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brt_car_empty.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brt_car_enemy.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brt_progress.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_brt_progress_our.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_brt_progress_empty.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_brt_progress_enemy.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);

            if (ccaGame.isPresent_brt()) {

                if (ccaBRT != null) ga_iv_brt_name.setImageBitmap(ccaBRT.getBmpSrc());

                ga_iv_brt_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                        new int[]{ccaGame.getSlots_brt_our(), ccaGame.getSlots_brt_empty(), ccaGame.getSlots_brt_enemy()}));
//                ga_tv_brt_progress_our.setText(Utils.getProgressString(ccaGame.getSlots_brt_our()));
//                ga_tv_brt_progress_empty.setText(Utils.getProgressString(ccaGame.getSlots_brt_empty()));
//                ga_tv_brt_progress_enemy.setText(Utils.getProgressString(ccaGame.getSlots_brt_enemy()));

                ga_tv_brt_slots.setText(String.valueOf(ccaGame.getSlots_brt()));
                ga_tv_brt_slots_our.setText(String.valueOf(ccaGame.getSlots_brt_our()));
                ga_tv_brt_slots_empty.setText(String.valueOf(ccaGame.getSlots_brt_empty()));
                ga_tv_brt_slots_enemy.setText(String.valueOf(ccaGame.getSlots_brt_enemy()));

                slots += ccaGame.getSlots_brt();
                slots_our += ccaGame.getSlots_brt_our();
                slots_empty += ccaGame.getSlots_brt_empty();
                slots_enemy += ccaGame.getSlots_brt_enemy();

                if (ccaGame.isBuildingIsOur_brt()) {
                    ga_tv_brt_points.setText("+" + ccaGame.getOur_points_brt());
                    ga_tv_brt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_brt()) {
                    ga_tv_brt_points.setText("+" + ccaGame.getEnemy_points_brt());
                    ga_tv_brt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_brt()) {
                    ga_tv_brt_points.setText("");
                    ga_tv_brt_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_brt()) {
                    ga_tv_brt_x2.setText("X2");
                    ga_tv_brt_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_brt()) {
                        ga_tv_brt_x2.setText("X2");
                        ga_tv_brt_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        ga_tv_brt_x2.setText("");
                        ga_tv_brt_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }

            ga_iv_brc_name.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brc_x2.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brc_points.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brc_slots.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brc_slots_our.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brc_slots_empty.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brc_slots_enemy.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brc_car_black.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brc_car_our.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brc_car_empty.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brc_car_enemy.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brc_progress.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_brc_progress_our.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_brc_progress_empty.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_brc_progress_enemy.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);

            if (ccaGame.isPresent_brc()) {

                if (ccaBRC != null) ga_iv_brc_name.setImageBitmap(ccaBRC.getBmpSrc());

                ga_iv_brc_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                        new int[]{ccaGame.getSlots_brc_our(), ccaGame.getSlots_brc_empty(), ccaGame.getSlots_brc_enemy()}));
//                ga_tv_brc_progress_our.setText(Utils.getProgressString(ccaGame.getSlots_brc_our()));
//                ga_tv_brc_progress_empty.setText(Utils.getProgressString(ccaGame.getSlots_brc_empty()));
//                ga_tv_brc_progress_enemy.setText(Utils.getProgressString(ccaGame.getSlots_brc_enemy()));

                ga_tv_brc_slots.setText(String.valueOf(ccaGame.getSlots_brc()));
                ga_tv_brc_slots_our.setText(String.valueOf(ccaGame.getSlots_brc_our()));
                ga_tv_brc_slots_empty.setText(String.valueOf(ccaGame.getSlots_brc_empty()));
                ga_tv_brc_slots_enemy.setText(String.valueOf(ccaGame.getSlots_brc_enemy()));

                slots += ccaGame.getSlots_brc();
                slots_our += ccaGame.getSlots_brc_our();
                slots_empty += ccaGame.getSlots_brc_empty();
                slots_enemy += ccaGame.getSlots_brc_enemy();

                if (ccaGame.isBuildingIsOur_brc()) {
                    ga_tv_brc_points.setText("+" + ccaGame.getOur_points_brc());
                    ga_tv_brc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_brc()) {
                    ga_tv_brc_points.setText("+" + ccaGame.getEnemy_points_brc());
                    ga_tv_brc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_brc()) {
                    ga_tv_brc_points.setText("");
                    ga_tv_brc_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_brc()) {
                    ga_tv_brc_x2.setText("X2");
                    ga_tv_brc_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_brc()) {
                        ga_tv_brc_x2.setText("X2");
                        ga_tv_brc_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        ga_tv_brc_x2.setText("");
                        ga_tv_brc_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }


            ga_iv_brb_name.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brb_x2.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brb_points.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brb_slots.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brb_slots_our.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brb_slots_empty.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_brb_slots_enemy.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brb_car_black.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brb_car_our.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brb_car_empty.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brb_car_enemy.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            ga_iv_brb_progress.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_brb_progress_our.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_brb_progress_empty.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
//            ga_tv_brb_progress_enemy.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);

            if (ccaGame.isPresent_brb()) {

                if (ccaBRB != null) ga_iv_brb_name.setImageBitmap(ccaBRB.getBmpSrc());

                ga_iv_brb_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                        new int[]{ccaGame.getSlots_brb_our(), ccaGame.getSlots_brb_empty(), ccaGame.getSlots_brb_enemy()}));
//                ga_tv_brb_progress_our.setText(Utils.getProgressString(ccaGame.getSlots_brb_our()));
//                ga_tv_brb_progress_empty.setText(Utils.getProgressString(ccaGame.getSlots_brb_empty()));
//                ga_tv_brb_progress_enemy.setText(Utils.getProgressString(ccaGame.getSlots_brb_enemy()));

                ga_tv_brb_slots.setText(String.valueOf(ccaGame.getSlots_brb()));
                ga_tv_brb_slots_our.setText(String.valueOf(ccaGame.getSlots_brb_our()));
                ga_tv_brb_slots_empty.setText(String.valueOf(ccaGame.getSlots_brb_empty()));
                ga_tv_brb_slots_enemy.setText(String.valueOf(ccaGame.getSlots_brb_enemy()));

                slots += ccaGame.getSlots_brb();
                slots_our += ccaGame.getSlots_brb_our();
                slots_empty += ccaGame.getSlots_brb_empty();
                slots_enemy += ccaGame.getSlots_brb_enemy();

                if (ccaGame.isBuildingIsOur_brb()) {
                    ga_tv_brb_points.setText("+" + ccaGame.getOur_points_brb());
                    ga_tv_brb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_brb()) {
                    ga_tv_brb_points.setText("+" + ccaGame.getEnemy_points_brb());
                    ga_tv_brb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_brb()) {
                    ga_tv_brb_points.setText("");
                    ga_tv_brb_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_brb()) {
                    ga_tv_brb_x2.setText("X2");
                    ga_tv_brb_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_brb()) {
                        ga_tv_brb_x2.setText("X2");
                        ga_tv_brb_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        ga_tv_brb_x2.setText("");
                        ga_tv_brb_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }

            
            ga_tv_game_slots.setText(String.valueOf(slots));
            ga_tv_game_slots_our.setText(String.valueOf(slots_our));
            ga_tv_game_slots_empty.setText(String.valueOf(slots_empty));
            ga_tv_game_slots_enemy.setText(String.valueOf(slots_enemy));

        }



        // нотификация
        if (withNotify) {
            Log.i(TAG, logMsgPref + "withNotify");
            if (ccaGame != null) {
                Log.i(TAG, logMsgPref + "создание уведомления: " + ccaGame.getCcagStatus());
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
                                .setContentText(ccaGame.getCcagStatus())
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(ccaGame.getCcagStatus()));
                createChannelIfNeeded(notificationManager);
                notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
            }
        }

    }

    public void initializeViews() {

        String logMsgPref = "initializeViews: ";
        Log.i(TAG, logMsgPref + "start");

        // Game views
        ga_sw_listen_new_file = findViewById(R.id.ga_sw_listen_new_file);
        ga_tv_screenshot_time = findViewById(R.id.ga_tv_screenshot_time);
        ga_tv_start_game_time = findViewById(R.id.ga_tv_start_game_time);
        ga_tv_end_game_time = findViewById(R.id.ga_tv_end_game_time);
        ga_tv_status = findViewById(R.id.ga_tv_status);
        ga_tv_vs = findViewById(R.id.ga_tv_vs);

        // Time views
        ga_tv_total_time = findViewById(R.id.ga_tv_total_time);
        ga_tv_early_win = findViewById(R.id.ga_tv_early_win);

        // Our team views
        ga_iv_our_team_name = findViewById(R.id.ga_iv_our_team_name);
        ga_tv_our_increase = findViewById(R.id.ga_tv_our_increase);
        ga_tv_our_points = findViewById(R.id.ga_tv_our_points);
        ga_tv_our_end_time = findViewById(R.id.ga_tv_our_end_time);

        // Enemy team views
        ga_iv_enemy_team_name = findViewById(R.id.ga_iv_enemy_team_name);
        ga_tv_enemy_increase = findViewById(R.id.ga_tv_enemy_increase);
        ga_tv_enemy_points = findViewById(R.id.ga_tv_enemy_points);
        ga_tv_enemy_end_time = findViewById(R.id.ga_tv_enemy_end_time);

        // BLT views
        ga_iv_blt_name = findViewById(R.id.ga_iv_blt_name);
        ga_tv_blt_x2 = findViewById(R.id.ga_tv_blt_x2);
        ga_tv_blt_points = findViewById(R.id.ga_tv_blt_points);
        ga_tv_blt_slots = findViewById(R.id.ga_tv_blt_slots);
        ga_tv_blt_slots_our = findViewById(R.id.ga_tv_blt_slots_our);
        ga_tv_blt_slots_empty = findViewById(R.id.ga_tv_blt_slots_empty);
        ga_tv_blt_slots_enemy = findViewById(R.id.ga_tv_blt_slots_enemy);
        ga_iv_blt_car_black = findViewById(R.id.ga_iv_blt_car_black);
        ga_iv_blt_car_our = findViewById(R.id.ga_iv_blt_car_our);
        ga_iv_blt_car_empty = findViewById(R.id.ga_iv_blt_car_empty);
        ga_iv_blt_car_enemy = findViewById(R.id.ga_iv_blt_car_enemy);
        ga_iv_blt_progress = findViewById(R.id.ga_iv_blt_progress);
//        ga_tv_blt_progress_our = findViewById(R.id.ga_tv_blt_progress_our);
//        ga_tv_blt_progress_empty = findViewById(R.id.ga_tv_blt_progress_empty);
//        ga_tv_blt_progress_enemy = findViewById(R.id.ga_tv_blt_progress_enemy);

        // BLC views
        ga_iv_blc_name = findViewById(R.id.ga_iv_blc_name);
        ga_tv_blc_x2 = findViewById(R.id.ga_tv_blc_x2);
        ga_tv_blc_points = findViewById(R.id.ga_tv_blc_points);
        ga_tv_blc_slots = findViewById(R.id.ga_tv_blc_slots);
        ga_tv_blc_slots_our = findViewById(R.id.ga_tv_blc_slots_our);
        ga_tv_blc_slots_empty = findViewById(R.id.ga_tv_blc_slots_empty);
        ga_tv_blc_slots_enemy = findViewById(R.id.ga_tv_blc_slots_enemy);
        ga_iv_blc_car_black = findViewById(R.id.ga_iv_blc_car_black);
        ga_iv_blc_car_our = findViewById(R.id.ga_iv_blc_car_our);
        ga_iv_blc_car_empty = findViewById(R.id.ga_iv_blc_car_empty);
        ga_iv_blc_car_enemy = findViewById(R.id.ga_iv_blc_car_enemy);
        ga_iv_blc_progress = findViewById(R.id.ga_iv_blc_progress);
//        ga_tv_blc_progress_our = findViewById(R.id.ga_tv_blc_progress_our);
//        ga_tv_blc_progress_empty = findViewById(R.id.ga_tv_blc_progress_empty);
//        ga_tv_blc_progress_enemy = findViewById(R.id.ga_tv_blc_progress_enemy);

        // BLB views
        ga_iv_blb_name = findViewById(R.id.ga_iv_blb_name);
        ga_tv_blb_x2 = findViewById(R.id.ga_tv_blb_x2);
        ga_tv_blb_points = findViewById(R.id.ga_tv_blb_points);
        ga_tv_blb_slots = findViewById(R.id.ga_tv_blb_slots);
        ga_tv_blb_slots_our = findViewById(R.id.ga_tv_blb_slots_our);
        ga_tv_blb_slots_empty = findViewById(R.id.ga_tv_blb_slots_empty);
        ga_tv_blb_slots_enemy = findViewById(R.id.ga_tv_blb_slots_enemy);
        ga_iv_blb_car_black = findViewById(R.id.ga_iv_blb_car_black);
        ga_iv_blb_car_our = findViewById(R.id.ga_iv_blb_car_our);
        ga_iv_blb_car_empty = findViewById(R.id.ga_iv_blb_car_empty);
        ga_iv_blb_car_enemy = findViewById(R.id.ga_iv_blb_car_enemy);
        ga_iv_blb_progress = findViewById(R.id.ga_iv_blb_progress);
//        ga_tv_blb_progress_our = findViewById(R.id.ga_tv_blb_progress_our);
//        ga_tv_blb_progress_empty = findViewById(R.id.ga_tv_blb_progress_empty);
//        ga_tv_blb_progress_enemy = findViewById(R.id.ga_tv_blb_progress_enemy);

        // BRT views
        ga_iv_brt_name = findViewById(R.id.ga_iv_brt_name);
        ga_tv_brt_x2 = findViewById(R.id.ga_tv_brt_x2);
        ga_tv_brt_points = findViewById(R.id.ga_tv_brt_points);
        ga_tv_brt_slots = findViewById(R.id.ga_tv_brt_slots);
        ga_tv_brt_slots_our = findViewById(R.id.ga_tv_brt_slots_our);
        ga_tv_brt_slots_empty = findViewById(R.id.ga_tv_brt_slots_empty);
        ga_tv_brt_slots_enemy = findViewById(R.id.ga_tv_brt_slots_enemy);
        ga_iv_brt_car_black = findViewById(R.id.ga_iv_brt_car_black);
        ga_iv_brt_car_our = findViewById(R.id.ga_iv_brt_car_our);
        ga_iv_brt_car_empty = findViewById(R.id.ga_iv_brt_car_empty);
        ga_iv_brt_car_enemy = findViewById(R.id.ga_iv_brt_car_enemy);
        ga_iv_brt_progress = findViewById(R.id.ga_iv_brt_progress);
//        ga_tv_brt_progress_our = findViewById(R.id.ga_tv_brt_progress_our);
//        ga_tv_brt_progress_empty = findViewById(R.id.ga_tv_brt_progress_empty);
//        ga_tv_brt_progress_enemy = findViewById(R.id.ga_tv_brt_progress_enemy);

        // BRC views
        ga_iv_brc_name = findViewById(R.id.ga_iv_brc_name);
        ga_tv_brc_x2 = findViewById(R.id.ga_tv_brc_x2);
        ga_tv_brc_points = findViewById(R.id.ga_tv_brc_points);
        ga_tv_brc_slots = findViewById(R.id.ga_tv_brc_slots);
        ga_tv_brc_slots_our = findViewById(R.id.ga_tv_brc_slots_our);
        ga_tv_brc_slots_empty = findViewById(R.id.ga_tv_brc_slots_empty);
        ga_tv_brc_slots_enemy = findViewById(R.id.ga_tv_brc_slots_enemy);
        ga_iv_brc_car_black = findViewById(R.id.ga_iv_brc_car_black);
        ga_iv_brc_car_our = findViewById(R.id.ga_iv_brc_car_our);
        ga_iv_brc_car_empty = findViewById(R.id.ga_iv_brc_car_empty);
        ga_iv_brc_car_enemy = findViewById(R.id.ga_iv_brc_car_enemy);
        ga_iv_brc_progress = findViewById(R.id.ga_iv_brc_progress);
//        ga_tv_brc_progress_our = findViewById(R.id.ga_tv_brc_progress_our);
//        ga_tv_brc_progress_empty = findViewById(R.id.ga_tv_brc_progress_empty);
//        ga_tv_brc_progress_enemy = findViewById(R.id.ga_tv_brc_progress_enemy);

        // BRB views
        ga_iv_brb_name = findViewById(R.id.ga_iv_brb_name);
        ga_tv_brb_x2 = findViewById(R.id.ga_tv_brb_x2);
        ga_tv_brb_points = findViewById(R.id.ga_tv_brb_points);
        ga_tv_brb_slots = findViewById(R.id.ga_tv_brb_slots);
        ga_tv_brb_slots_our = findViewById(R.id.ga_tv_brb_slots_our);
        ga_tv_brb_slots_empty = findViewById(R.id.ga_tv_brb_slots_empty);
        ga_tv_brb_slots_enemy = findViewById(R.id.ga_tv_brb_slots_enemy);
        ga_iv_brb_car_black = findViewById(R.id.ga_iv_brb_car_black);
        ga_iv_brb_car_our = findViewById(R.id.ga_iv_brb_car_our);
        ga_iv_brb_car_empty = findViewById(R.id.ga_iv_brb_car_empty);
        ga_iv_brb_car_enemy = findViewById(R.id.ga_iv_brb_car_enemy);
        ga_iv_brb_progress = findViewById(R.id.ga_iv_brb_progress);
//        ga_tv_brb_progress_our = findViewById(R.id.ga_tv_brb_progress_our);
//        ga_tv_brb_progress_empty = findViewById(R.id.ga_tv_brb_progress_empty);
//        ga_tv_brb_progress_enemy = findViewById(R.id.ga_tv_brb_progress_enemy);

        // Рекламный блок
        ga_ad_banner = findViewById(R.id.ga_ad_banner);

        ga_iv_game_car_black = findViewById(R.id.ga_iv_game_car_black);
        ga_iv_game_car_our = findViewById(R.id.ga_iv_game_car_our);
        ga_iv_game_car_empty = findViewById(R.id.ga_iv_game_car_empty);
        ga_iv_game_car_enemy = findViewById(R.id.ga_iv_game_car_enemy);
        ga_tv_game_slots = findViewById(R.id.ga_tv_game_slots);
        ga_tv_game_slots_our = findViewById(R.id.ga_tv_game_slots_our);
        ga_tv_game_slots_empty = findViewById(R.id.ga_tv_game_slots_empty);
        ga_tv_game_slots_enemy = findViewById(R.id.ga_tv_game_slots_enemy);

        ga_bt_strategy = findViewById(R.id.ga_bt_strategy);

        ga_ib_car1 = findViewById(R.id.ga_ib_car1);
        ga_tv_car1_name = findViewById(R.id.ga_tv_car1_name);
        ga_tv_car1_number = findViewById(R.id.ga_tv_car1_number);
        ga_iv_car1_health = findViewById(R.id.ga_iv_car1_health);
        ga_tv_car1_health = findViewById(R.id.ga_tv_car1_health);
        ga_iv_car1_shield = findViewById(R.id.ga_iv_car1_shield);
        ga_tv_car1_shield = findViewById(R.id.ga_tv_car1_shield);
        ga_tv_car1_repair = findViewById(R.id.ga_tv_car1_repair);
        ga_iv_car1_building = findViewById(R.id.ga_iv_car1_building);
        ga_iv_car1_task = findViewById(R.id.ga_iv_car1_task);

        ga_ib_car2 = findViewById(R.id.ga_ib_car2);
        ga_tv_car2_name = findViewById(R.id.ga_tv_car2_name);
        ga_tv_car2_number = findViewById(R.id.ga_tv_car2_number);
        ga_iv_car2_health = findViewById(R.id.ga_iv_car2_health);
        ga_tv_car2_health = findViewById(R.id.ga_tv_car2_health);
        ga_iv_car2_shield = findViewById(R.id.ga_iv_car2_shield);
        ga_tv_car2_shield = findViewById(R.id.ga_tv_car2_shield);
        ga_tv_car2_repair = findViewById(R.id.ga_tv_car2_repair);
        ga_iv_car2_building = findViewById(R.id.ga_iv_car2_building);
        ga_iv_car2_task = findViewById(R.id.ga_iv_car2_task);

        ga_ib_car3 = findViewById(R.id.ga_ib_car3);
        ga_tv_car3_name = findViewById(R.id.ga_tv_car3_name);
        ga_tv_car3_number = findViewById(R.id.ga_tv_car3_number);
        ga_iv_car3_health = findViewById(R.id.ga_iv_car3_health);
        ga_tv_car3_health = findViewById(R.id.ga_tv_car3_health);
        ga_iv_car3_shield = findViewById(R.id.ga_iv_car3_shield);
        ga_tv_car3_shield = findViewById(R.id.ga_tv_car3_shield);
        ga_tv_car3_repair = findViewById(R.id.ga_tv_car3_repair);
        ga_iv_car3_building = findViewById(R.id.ga_iv_car3_building);
        ga_iv_car3_task = findViewById(R.id.ga_iv_car3_task);

        ga_tv_user = findViewById(R.id.ga_tv_user);

    }

    private void setDataToCarsViews() {
        List<Car> listCars = Car.loadList();

        Car car1 = listCars.get(0);
        Car car2 = listCars.get(1);
        Car car3 = listCars.get(2);

        ga_tv_car1_name.setText(car1.getName());
        ga_tv_car1_health.setText(String.valueOf(car1.getHealth()));
        ga_tv_car1_shield.setText(String.valueOf(car1.getShield()));
        
        ga_tv_car2_name.setText(car2.getName());
        ga_tv_car2_health.setText(String.valueOf(car2.getHealth()));
        ga_tv_car2_shield.setText(String.valueOf(car2.getShield()));

        ga_tv_car3_name.setText(car3.getName());
        ga_tv_car3_health.setText(String.valueOf(car3.getHealth()));
        ga_tv_car3_shield.setText(String.valueOf(car3.getShield()));

        try {
            if (((CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY)).isCcagIsGameOver()) {

                if (!car1.isFree()) {
                    car1.setStateFree();
                    car1.save();
                }

                if (!car2.isFree()) {
                    car2.setStateFree();
                    car2.save();
                }

                if (!car3.isFree()) {
                    car3.setStateFree();
                    car3.save();
                }
            }

            Bitmap car1CarBitmap = car1.isFree() ? car1.getCarPicture() : car1.isRepairing() ? car1.getCarPictureRepairing() : car1.isDefencing() ? car1.getCarPictureDefencing() : car1.getCarPicture() ;
            Bitmap car1BuildingBitmap = car1.getBuildingPicture();
            Bitmap car1TaskBitmap = car1.getTaskPicture();
            if (car1CarBitmap != null) ga_ib_car1.setImageBitmap(car1CarBitmap);
            ga_ib_car1.setImageBitmap(car1.getCarPicture());
            if (car1.isFree()) ga_tv_car1_number.setTextColor(Color.BLUE);
            if (car1.isRepairing() && !car1.isDefencing()) ga_tv_car1_number.setTextColor(Color.RED);
            if (!car1.isRepairing() && car1.isDefencing()) ga_tv_car1_number.setTextColor(Color.GREEN);
            if (car1.isRepairing() && car1.isDefencing()) ga_tv_car1_number.setTextColor(Color.YELLOW);
            String car1textRepair = car1.isRepairing() ? "\uD83D\uDD27" + " " + car1.getTimeStringToEndRepairing() : "";
            ga_tv_car1_repair.setText(car1textRepair);
            if (car1.isDefencing()) {
                ga_iv_car1_building.setVisibility(car1BuildingBitmap != null ? View.VISIBLE : View.INVISIBLE);
                if (car1BuildingBitmap != null) ga_iv_car1_building.setImageBitmap(car1BuildingBitmap);
            } else {
                ga_iv_car1_building.setVisibility(View.INVISIBLE);
            }
            if (car1.getBuildingTask() > 0) {
                ga_iv_car1_task.setVisibility(car1TaskBitmap != null ? View.VISIBLE : View.INVISIBLE);
                if (car1TaskBitmap != null) ga_iv_car1_task.setImageBitmap(car1TaskBitmap);
            }


            Bitmap car2CarBitmap = car2.isFree() ? car2.getCarPicture() : car2.isRepairing() ? car2.getCarPictureRepairing() : car2.isDefencing() ? car2.getCarPictureDefencing() : car2.getCarPicture() ;
            Bitmap car2BuildingBitmap = car2.getBuildingPicture();
            Bitmap car2TaskBitmap = car2.getTaskPicture();
            if (car2CarBitmap != null) ga_ib_car2.setImageBitmap(car2CarBitmap);
            ga_ib_car2.setImageBitmap(car2.getCarPicture());
            if (car2.isFree()) ga_tv_car2_number.setTextColor(Color.BLUE);
            if (car2.isRepairing() && !car2.isDefencing()) ga_tv_car2_number.setTextColor(Color.RED);
            if (!car2.isRepairing() && car2.isDefencing()) ga_tv_car2_number.setTextColor(Color.GREEN);
            if (car2.isRepairing() && car2.isDefencing()) ga_tv_car2_number.setTextColor(Color.YELLOW);
            String car2textRepair = car2.isRepairing() ? "\uD83D\uDD27" + " " + car2.getTimeStringToEndRepairing() : "";
            ga_tv_car2_repair.setText(car2textRepair);
            if (car2.isDefencing()) {
                ga_iv_car2_building.setVisibility(car2BuildingBitmap != null ? View.VISIBLE : View.INVISIBLE);
                if (car2BuildingBitmap != null) ga_iv_car2_building.setImageBitmap(car2BuildingBitmap);
            } else {
                ga_iv_car2_building.setVisibility(View.INVISIBLE);
            }
            if (car2.getBuildingTask() > 0) {
                ga_iv_car2_task.setVisibility(car2TaskBitmap != null ? View.VISIBLE : View.INVISIBLE);
                if (car2TaskBitmap != null) ga_iv_car2_task.setImageBitmap(car2TaskBitmap);
            }

            Bitmap car3CarBitmap = car3.isFree() ? car3.getCarPicture() : car3.isRepairing() ? car3.getCarPictureRepairing() : car3.isDefencing() ? car3.getCarPictureDefencing() : car3.getCarPicture() ;
            Bitmap car3BuildingBitmap = car3.getBuildingPicture();
            Bitmap car3TaskBitmap = car3.getTaskPicture();
            if (car3CarBitmap != null) ga_ib_car3.setImageBitmap(car3CarBitmap);
            ga_ib_car3.setImageBitmap(car3.getCarPicture());
            if (car3.isFree()) ga_tv_car3_number.setTextColor(Color.BLUE);
            if (car3.isRepairing() && !car3.isDefencing()) ga_tv_car3_number.setTextColor(Color.RED);
            if (!car3.isRepairing() && car3.isDefencing()) ga_tv_car3_number.setTextColor(Color.GREEN);
            if (car3.isRepairing() && car3.isDefencing()) ga_tv_car3_number.setTextColor(Color.YELLOW);
            String car3textRepair = car3.isRepairing() ? "\uD83D\uDD27" + " " + car3.getTimeStringToEndRepairing() : "";
            ga_tv_car3_repair.setText(car3textRepair);
            if (car3.isDefencing()) {
                ga_iv_car3_building.setVisibility(car3BuildingBitmap != null ? View.VISIBLE : View.INVISIBLE);
                if (car3BuildingBitmap != null) ga_iv_car3_building.setImageBitmap(car3BuildingBitmap);
            } else {
                ga_iv_car3_building.setVisibility(View.INVISIBLE);
            }
            if (car3.getBuildingTask() > 0) {
                ga_iv_car3_task.setVisibility(car3TaskBitmap != null ? View.VISIBLE : View.INVISIBLE);
                if (car3TaskBitmap != null) ga_iv_car3_task.setImageBitmap(car3TaskBitmap);
            }

        } catch (Exception ignored) {
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

        File temp = null;           // временный файл
        File lastScreenshot = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name)); // последний скри
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
                fileLast = temp;
                if (!temp.equals(fileLastInFolder)) {   // если найденный файл не совпадает с раенее найденным "последним файлом"
                    CityCalc tmpCityCalc = new CityCalc(temp, calibrateX, calibrateY, context);
                    if (!tmpCityCalc.getCityCalcType().equals(CityCalcType.ERROR)) {
                        fileLastInFolder = temp;    // последний найденный файл - текущий найденный
                    } else {
                        if (fileLastInFolder == null) {
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
            temp = lastScreenshot;
        }

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
        String logMsgPref = "onActivityResult: ";

        super.onActivityResult(requestCode, resultCode, data);
        // если произошел возврат со страницы настрок - обновляем контролы в текущей активности
        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY) {
            readPreferences();
            fileGameScreenshotPrevious = null;
            ga_sw_listen_new_file.setChecked(isListenToNewFileInFolder);
            if (fileGameScreenshot != null) {
                CityCalc tmpCityCalc = new CityCalc(fileGameScreenshot, calibrateX, calibrateY, context);
                if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                    mainCityCalc = new CityCalc(tmpCityCalc, false);
                    loadDataToViews(false);
                }
            }

        }

        // если был возврат из логина
        if(requestCode == SIGN_IN_REQUEST_CODE) {
            // и логин был успешный
            if (resultCode == RESULT_OK) {
                // зарефрешим юзера, дождемся рефреша и обновим меню
                fbUser = fbAuth.getCurrentUser();
                fbUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            checkMenuVisibility();
                        }
                    }
                });
            } else {
                Toast.makeText(GameActivity.this, R.string.could_not_sing_in, Toast.LENGTH_LONG).show();
                fbUser = fbAuth.getCurrentUser();
                checkMenuVisibility();
            }
        }

    }


    private void checkMenuVisibility() {

        // пунты меню "Настройка" и "Открыть скриншот" видны в любом случае
        menu_main_open_settings.setVisible(true);
        menu_main_open_screenshot.setVisible(true);
        String userText = "";
        if (fbUser == null) { // юзер не залогинился

            // виден пункт меню "Логин", остальные скрываем
            menu_main_login.setVisible(true);
            menu_main_logout.setVisible(false);
            menu_main_user_account.setVisible(false);
            menu_main_email_verification_send.setVisible(false);
            menu_main_email_verification_check.setVisible(false);
            menu_main_team_manage.setVisible(false);
            menu_main_team_create.setVisible(false);
            menu_main_team_leave.setVisible(false);
            menu_main_team_find.setVisible(false);

            // обновляем инфо юзера
            userText = "Login, please.";
            ga_tv_user.setText(userText);

        } else { // юзер залогинился

            // обновляем инфо юзера
            userText = fbUser.getDisplayName();
            ga_tv_user.setText(userText);

            // пункт меню "Логин" скрываем, "Логаут" - показываем
            menu_main_login.setVisible(false);
            menu_main_logout.setVisible(true);
            
            // проверяем, подтвержден ли емейл
            boolean isVerified = fbUser.isEmailVerified();

            if (!isVerified) { // почта не подтверждена

                // обновляем инфо юзера
                userText = userText + " (not verified)";
                ga_tv_user.setText(userText);

                // показываем пунты меню "Выслать письмо..." и "Обновить...", остальные скрываем
                menu_main_user_account.setVisible(false);
                menu_main_email_verification_send.setVisible(true);
                menu_main_email_verification_check.setVisible(true);
                menu_main_team_manage.setVisible(false);
                menu_main_team_create.setVisible(false);
                menu_main_team_leave.setVisible(false);
                menu_main_team_find.setVisible(false);

            } else { // почта подтверждена

                // показываем пункт меню "Кабинет пользователя", скрываем "Выслать письмо..." и "Обновить..."
                menu_main_user_account.setVisible(true);
                menu_main_email_verification_send.setVisible(false);
                menu_main_email_verification_check.setVisible(false);

                // ищем запись о текущем юзере
                DocumentReference docRefUser = fbDb.collection("users").document(fbUser.getUid());
                docRefUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot docSnapUser = task.getResult();
                            if (docSnapUser.exists()) { // запись о юзере найдена

                                // нашли текущего юзера в базе, считываем его
                                mainDbUser = new DbUser(docSnapUser);

                                // устанавливем флаг "у юзера есть команда"
                                userHaveTeam = (mainDbUser.getTeamID() != null);

                                if (!userHaveTeam) { // юзер не состоит в команде

                                    // обновляем инфо юзера
                                    String userText = fbUser.getDisplayName() + " (no team)";
                                    ga_tv_user.setText(userText);

                                    // выводим пункт "Создать банду", скрываем пункты "Банда", "Покинуть банду" и "Найти банду"
                                    menu_main_team_manage.setVisible(false);
                                    menu_main_team_create.setVisible(true);
                                    menu_main_team_leave.setVisible(false);
                                    menu_main_team_find.setVisible(true);

                                } else { // юзер состоит в команде

                                    // ищем запись о команде
                                    final DocumentReference docRefTeam = fbDb.collection("teams").document(mainDbUser.getTeamID());
                                    docRefTeam.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            // нашли команду, считываем ее
                                            mainDbTeam = new DbTeam(documentSnapshot);

                                            // обновляем инфо юзера
                                            String userText = fbUser.getDisplayName() + " (" + mainDbTeam.getTeamName()  + ")";
                                            ga_tv_user.setText(userText);

                                            // запрос на наличие записи о юзере в таблице teamUsers
                                            Query query = docRefTeam.collection("teamUsers").whereEqualTo("userID", mainDbUser.getUserID());
                                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    // запрос обработался
                                                    if (task.isSuccessful()) {
                                                        // запрос обработался удачно
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            // проходимся по записям запроса, но запись должна быть одна

                                                            // считываем тимЮзера и заполняем его поля, включая роль
                                                            mainDbTeamUser = document.toObject(DbTeamUser.class);
                                                            mainDbTeamUser.setTeamUserID(document.getId());
                                                            if (mainDbTeamUser.getUserRole().equals("leader")) {
                                                                userRole = UserRole.LEADER;
                                                            } else if (mainDbTeamUser.getUserRole().equals("captain")){
                                                                userRole = UserRole.CAPTAIN;
                                                            } else {
                                                                userRole = UserRole.MEAT;
                                                            }
                                                            // обновляем инфо юзера
                                                            String userText = fbUser.getDisplayName() + " (" + mainDbTeam.getTeamName()  + ") [" + userRole + "]";
                                                            ga_tv_user.setText(userText);
                                                            break;
                                                        }
                                                        // выбодим пунткы меню "Банда" и "Покинуть банду", скрываем "Создать банду" и "Найти банду"
                                                        menu_main_team_manage.setVisible(true);
                                                        menu_main_team_create.setVisible(false);
                                                        menu_main_team_leave.setVisible(true);
                                                        menu_main_team_find.setVisible(false);

                                                        // "слушаем" запись о текущей игре
                                                        final DocumentReference docRefTeamGame = fbDb.collection("teams").document(mainDbTeam.getTeamID()).collection("teamGames").document("teamGame");
                                                        docRefTeamGame.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                                if (e != null) {
                                                                    // произошла ошибка "слушателся"
                                                                    Log.w(TAG, "Listen game failed.", e);
                                                                    return;
                                                                }
                                                                if (documentSnapshot != null && documentSnapshot.exists()) {
                                                                    // "слушатель" отработал и вернул документ
                                                                    Log.d(TAG, "Current game data: " + documentSnapshot.getData());

                                                                    if (mainCityCalc != null) { // если текущая игра есть
                                                                        CCAGame ccaGame = (CCAGame)mainCityCalc.getMapAreas().get(Area.CITY);
                                                                        if (ccaGame != null) {
                                                                            DbTeamGame dbTeamGame = new DbTeamGame(documentSnapshot);                                   // считываем тимГейм из базы
                                                                            if (dbTeamGame.getDateScreenshot().getTime() > ccaGame.getCcagDateScreenshot().getTime()) { // если в базе более свежий скриншот, чем в локальной игре
                                                                                // надо взять с сервера скрин того юзера, кто обновил игру
                                                                                File teamGameScreenshot = new File(pathToCATScalcFolder + "/teamGameScreenshot");
                                                                                String storRefGamePathOnServer = "users/" + dbTeamGame.getUserUID() + "/last_screenshot";
                                                                                StorageReference storRefGame = GameActivity.fbStor.getReference().child(storRefGamePathOnServer);
                                                                                storRefGame.getFile(teamGameScreenshot).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                                    @Override
                                                                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                                                        // скрин скачался с сервера удачно
                                                                                        CityCalc tmpCityCalc = new CityCalc(teamGameScreenshot, dbTeamGame.getCalibrateX(), dbTeamGame.getCalibrateY(), context);
                                                                                        if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                                                                                            fileGameScreenshot = teamGameScreenshot;   // текущий скриншот = последнему файлу в папке
                                                                                            Toast.makeText(GameActivity.this, getString(R.string.screen_from_server), Toast.LENGTH_LONG).show();
                                                                                            mainCityCalc = new CityCalc(tmpCityCalc, false);
                                                                                            loadDataToViews(true);
                                                                                        }
                                                                                    }
                                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                        // не удалось скачать скрин с сервера
                                                                                        ccaGame.updateFromDb(dbTeamGame);                                                       // обновляем локальную игру инфой из базы
                                                                                        // выводим тост и обновляем контролы в активити
                                                                                        Toast.makeText(GameActivity.this, getString(R.string.screen_from_server), Toast.LENGTH_LONG).show();
                                                                                        loadDataToViews(true);
                                                                                    }
                                                                                });

                                                                            }
                                                                        }
                                                                    }

                                                                } else {
                                                                    // запсь в базе отсутствует
                                                                    Log.d(TAG, "Current game data: null");
                                                                }
                                                            }
                                                        });

                                                        // "слушаем" запись о 1-й машине
                                                        final DocumentReference docRefCar1 = fbDb.collection("users").document(fbUser.getUid()).collection("userCars").document("car1");
                                                        docRefCar1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                                if (e != null) {
                                                                    // произошла ошибка "слушателя"
                                                                    Log.w(TAG, "Listen car1 failed.", e);
                                                                    return;
                                                                }
                                                                if (documentSnapshot != null && documentSnapshot.exists()) {
                                                                    // "слушатель" отработал и вернул документ
                                                                    Log.d(TAG, "Current car1 data: " + documentSnapshot.getData());

                                                                    if (mainCityCalc != null) {                                         // если текущая игра есть
                                                                        List<Car> listCars = Car.loadList();                            // получаем локальный список машин
                                                                        Car car = listCars.get(0);                                      // берем из списка 1-ю машину
                                                                        DbCar dbCar = new DbCar(documentSnapshot);                      // считываем 1-ю машину из базы
                                                                        if (car.getUuid().toString().equals(dbCar.getCarUID())) {       // если UIDы локальной машины и машины из базы совпадают
                                                                            if (car.getBuildingTask() != dbCar.getCarBuildingTask()) {  // если в базе изменилась задача для машины

                                                                                car.setBuildingTask(dbCar.getCarBuildingTask());        // устанавливаем новую задачу для локальной машины
                                                                                // получаем правильную картинку здания для нового задания
                                                                                CCAGame ccaGame = (CCAGame)mainCityCalc.getMapAreas().get(Area.CITY);
                                                                                Bitmap taskBitmap = null;
                                                                                if (dbCar.getCarBuildingTask() == 1 && ccaGame.isPresent_blt()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 2 && ccaGame.isPresent_blc()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 3 && ccaGame.isPresent_blb()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 4 && ccaGame.isPresent_brt()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 5 && ccaGame.isPresent_brc()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 6 && ccaGame.isPresent_brb()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                                                                                }
                                                                                car.setTaskPicture(taskBitmap); // обновляем картинку здания задания для локальной машины
                                                                                car.save();                     // сохраняем локальную машину
                                                                                setDataToCarsViews();           // обновляем машины в активити
                                                                            }
                                                                        }
                                                                    }

                                                                } else {
                                                                    Log.d(TAG, "Current car1 data: null");
                                                                }
                                                            }
                                                        });

                                                        // "слушаем" запись о 2-й машине
                                                        final DocumentReference docRefCar2 = fbDb.collection("users").document(fbUser.getUid()).collection("userCars").document("car2");
                                                        docRefCar2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                                if (e != null) {
                                                                    // произошла ошибка "слушателя"
                                                                    Log.w(TAG, "Listen car2 failed.", e);
                                                                    return;
                                                                }
                                                                if (documentSnapshot != null && documentSnapshot.exists()) {
                                                                    // "слушатель" отработал и вернул документ
                                                                    Log.d(TAG, "Current car2 data: " + documentSnapshot.getData());

                                                                    if (mainCityCalc != null) {                                         // если текущая игра есть
                                                                        List<Car> listCars = Car.loadList();                            // получаем локальный список машин
                                                                        Car car = listCars.get(1);                                      // берем из списка 2-ю машину
                                                                        DbCar dbCar = new DbCar(documentSnapshot);                      // считываем 2-ю машину из базы
                                                                        if (car.getUuid().toString().equals(dbCar.getCarUID())) {       // если UIDы локальной машины и машины из базы совпадают
                                                                            if (car.getBuildingTask() != dbCar.getCarBuildingTask()) {  // если в базе изменилась задача для машины

                                                                                car.setBuildingTask(dbCar.getCarBuildingTask());        // устанавливаем новую задачу для локальной машины
                                                                                // получаем правильную картинку здания для нового задания
                                                                                CCAGame ccaGame = (CCAGame)mainCityCalc.getMapAreas().get(Area.CITY);
                                                                                Bitmap taskBitmap = null;
                                                                                if (dbCar.getCarBuildingTask() == 1 && ccaGame.isPresent_blt()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 2 && ccaGame.isPresent_blc()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 3 && ccaGame.isPresent_blb()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 4 && ccaGame.isPresent_brt()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 5 && ccaGame.isPresent_brc()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 6 && ccaGame.isPresent_brb()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                                                                                }
                                                                                car.setTaskPicture(taskBitmap); // обновляем картинку здания задания для локальной машины
                                                                                car.save();                     // сохраняем локальную машину
                                                                                setDataToCarsViews();           // обновляем машины в активити
                                                                            }
                                                                        }
                                                                    }

                                                                } else {
                                                                    Log.d(TAG, "Current car2 data: null");
                                                                }
                                                            }
                                                        });

                                                        // "слушаем" запись о 3-й машине
                                                        final DocumentReference docRefCar3 = fbDb.collection("users").document(fbUser.getUid()).collection("userCars").document("car3");
                                                        docRefCar3.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                                if (e != null) {
                                                                    // произошла ошибка "слушателя"
                                                                    Log.w(TAG, "Listen car3 failed.", e);
                                                                    return;
                                                                }
                                                                if (documentSnapshot != null && documentSnapshot.exists()) {
                                                                    // "слушатель" отработал и вернул документ
                                                                    Log.d(TAG, "Current car3 data: " + documentSnapshot.getData());

                                                                    if (mainCityCalc != null) {                                         // если текущая игра есть
                                                                        List<Car> listCars = Car.loadList();                            // получаем локальный список машин
                                                                        Car car = listCars.get(2);                                      // берем из списка 3-ю машину
                                                                        DbCar dbCar = new DbCar(documentSnapshot);                      // считываем 3-ю машину из базы
                                                                        if (car.getUuid().toString().equals(dbCar.getCarUID())) {       // если UIDы локальной машины и машины из базы совпадают
                                                                            if (car.getBuildingTask() != dbCar.getCarBuildingTask()) {  // если в базе изменилась задача для машины

                                                                                car.setBuildingTask(dbCar.getCarBuildingTask());        // устанавливаем новую задачу для локальной машины
                                                                                // получаем правильную картинку здания для нового задания
                                                                                CCAGame ccaGame = (CCAGame)mainCityCalc.getMapAreas().get(Area.CITY);
                                                                                Bitmap taskBitmap = null;
                                                                                if (dbCar.getCarBuildingTask() == 1 && ccaGame.isPresent_blt()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 2 && ccaGame.isPresent_blc()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 3 && ccaGame.isPresent_blb()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 4 && ccaGame.isPresent_brt()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 5 && ccaGame.isPresent_brc()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                                                                                } else if (dbCar.getCarBuildingTask() == 6 && ccaGame.isPresent_brb()) {
                                                                                    taskBitmap = mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                                                                                }
                                                                                car.setTaskPicture(taskBitmap); // обновляем картинку здания задания для локальной машины
                                                                                car.save();                     // сохраняем локальную машину
                                                                                setDataToCarsViews();           // обновляем машины в активити
                                                                            }
                                                                        }
                                                                    }

                                                                } else {
                                                                    Log.d(TAG, "Current car3 data: null");
                                                                }
                                                            }
                                                        });

                                                    } else {
                                                        // запрос обработался неудачно
                                                        Log.e(TAG, "Error getting documents: ", task.getException());
                                                        // выводим пункт "Создать банду", скрываем пункты "Банда", "Покинуть банду" и "Найти банду"
                                                        menu_main_team_manage.setVisible(false);
                                                        menu_main_team_create.setVisible(true);
                                                        menu_main_team_leave.setVisible(false);
                                                        menu_main_team_find.setVisible(true);
                                                    } // кесли запрос обработался удачно
                                                } // onComplete
                                            }); // query.get().addOnCompleteListener
                                        } // onSuccess
                                    }); // docRefTeam.get().addOnSuccessListener
                                } // if (!userHaveTeam)


                            } else { // запись о юзере не найдена - ее надо создать

                                mainDbUser = new DbUser();
                                mainDbUser.setUserID(fbUser.getUid());
                                mainDbUser.setUserUID(fbUser.getUid());
                                mainDbUser.setUserName(fbUser.getDisplayName());
                                mainDbUser.setUserNIC(fbUser.getDisplayName());
                                mainDbUser.setUserEmail(fbUser.getEmail());
                                mainDbUser.setTeamID(null);
                                mainDbUser.setLeaderUID(null);
                                docRefUser.set(mainDbUser.getMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        userHaveTeam = false;
                                        // обновляем инфо юзера
                                        String userText = fbUser.getDisplayName() + " " + getString(R.string.no_team);
                                        ga_tv_user.setText(userText);

                                        // выводим пункт "Создать банду", скрываем пункты "Банда", "Покинуть банду" и "Найти банду"
                                        menu_main_team_manage.setVisible(false);
                                        menu_main_team_create.setVisible(true);
                                        menu_main_team_leave.setVisible(false);
                                        menu_main_team_find.setVisible(true);
                                    }
                                });

                            }

                        }
                    }
                });


            } // if (!isVerified)
        } //if (fbUser == null)

    }

    /**
     *  Созданием меню
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);  // создаем меню
        mainMenu = menu;

        menu_main_open_settings = mainMenu.findItem(R.id.menu_main_open_settings);
        menu_main_open_screenshot = mainMenu.findItem(R.id.menu_main_open_screenshot);
        menu_main_user_account = mainMenu.findItem(R.id.menu_main_user_account);
        menu_main_share = mainMenu.findItem(R.id.menu_main_share);
        menu_main_login = mainMenu.findItem(R.id.menu_main_login);
        menu_main_logout = mainMenu.findItem(R.id.menu_main_logout);
        menu_main_email_verification_send = mainMenu.findItem(R.id.menu_main_email_verification_send);
        menu_main_email_verification_check = mainMenu.findItem(R.id.menu_main_email_verification_check);
        menu_main_team_manage = mainMenu.findItem(R.id.menu_main_team_manage);
        menu_main_team_create = mainMenu.findItem(R.id.menu_main_team_create);
        menu_main_team_leave = mainMenu.findItem(R.id.menu_main_team_leave);
        menu_main_team_find = mainMenu.findItem(R.id.menu_main_team_find);

        checkMenuVisibility();

        return true;
    }

    /**
     * Выбор элементов меню
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // айди элемента меню
        switch(id){
            case R.id.menu_main_open_settings:
                doMenuOpenSettings();
                return true;
            case R.id.menu_main_open_screenshot :
                doMenuOpenScreenshot();
                return true;
            case R.id.menu_main_user_account :
                doMenuOpenUserAccount();
                return true;
            case R.id.menu_main_share :
                doMenuShareFile();
                return true;
            case R.id.menu_main_login :
                doMenuLogin();
                return true;
            case R.id.menu_main_logout :
                doMenuLogout();
                return true;
            case R.id.menu_main_email_verification_send :
                doMenuEmailVerificationSend();
                return true;
            case R.id.menu_main_email_verification_check :
                doMenuEmailVerificationCheck();
                return true;
            case R.id.menu_main_team_manage :
                doMenuManageTeam();
                return true;
            case R.id.menu_main_team_create :
                doMenuCreateTeam();
                return true;
            case R.id.menu_main_team_leave :
                doMenuLeaveTeam();
                return true;
            case R.id.menu_main_team_find :
                doMenuFindTeam();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void doMenuFindTeam() {

        Query query = GameActivity.fbDb.collection("teams").whereEqualTo("teamIsPublic", true).whereEqualTo("teamIsOpened", true);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DbTeam> listTeams = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listTeams.add(document.toObject(DbTeam.class));
                    }
                    DbTeam.listTeams = listTeams;

                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle(R.string.team);

                    final ListTeamsAdapter arrayAdapter = new ListTeamsAdapter(GameActivity.this);
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DbTeam dbTeam = arrayAdapter.getItem(which);

                            AlertDialog.Builder builderConfirmation = new AlertDialog.Builder(GameActivity.this);
                            builderConfirmation.setCancelable(true);
                            builderConfirmation.setTitle(R.string.joined_team);
                            builderConfirmation.setMessage(getString(R.string.sure_joined_team) + " \"" + dbTeam.getTeamName() + "\"?");
                            builderConfirmation.setPositiveButton(R.string.yes_sure, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog2, int which) {

                                    DocumentReference drUser = fbDb.collection("users").document(fbUser.getUid());

                                    Map<String, Object> mapUpdateItem = new HashMap<>();
                                    mapUpdateItem.put("teamID", dbTeam.getTeamID());
                                    drUser.update(mapUpdateItem);
                                    mainDbUser.setTeamID(dbTeam.getTeamID());

                                    CollectionReference collectionReference = GameActivity.fbDb.collection("teams").document(dbTeam.getTeamID()).collection("teamUsers");
                                    Map<String, Object> mapNewItem = new HashMap<>();
                                    mapNewItem.put("teamID", dbTeam.getTeamID());
                                    mapNewItem.put("userID", fbUser.getUid());
                                    mapNewItem.put("userRole", "meat");
                                    mapNewItem.put("userNIC", mainDbUser.getUserNIC());
                                    mapNewItem.put("timestamp", FieldValue.serverTimestamp());

                                    // Add a new document with a generated ID
                                    collectionReference.add(mapNewItem).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                            String newDocumentID = documentReference.getId();
                                            CollectionReference collectionReference = GameActivity.fbDb.collection("teams").document(dbTeam.getTeamID()).collection("teamUsers");
                                            Map<String, Object> mapUpdateItem = new HashMap<>();
                                            mapUpdateItem.put("teamUserID", newDocumentID);
                                            mapUpdateItem.put("timestamp", FieldValue.serverTimestamp());
                                            collectionReference.document(newDocumentID).update(mapUpdateItem);

                                            checkMenuVisibility();

                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e(TAG, "Error adding document", e);
                                                }
                                            });

                                }
                            });
                            builderConfirmation.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog dialog2 = builderConfirmation.create();
                            dialog2.show();



                        }
                    });
                    builder.show();


                }
            }
        });


    }

    private void doMenuLeaveTeam() {

        if (userHaveTeam) {
            if (userRole.equals(UserRole.LEADER)) {
                // если ты в банде лидер - надо проверить, есть ли еще в банде еще хоть кто-то. Если нет - удалить банду. Если да - проверить, есть ли среди них хоть один лидер. Если нет - выходить из банды нельзя.

                CollectionReference crTeamUsers = fbDb.collection("teams").document(mainDbTeam.getTeamID()).collection("teamUsers");
                crTeamUsers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                int countUsersInTeam = 0;
                                int countLeadersInTeam = 0;
                                List<DbTeamUser> listTeamUsers = task.getResult().toObjects(DbTeamUser.class);
                                if (listTeamUsers.size() > 0) {
                                    for (DbTeamUser teamUser: listTeamUsers) {
                                        countUsersInTeam++;
                                        if (teamUser.getUserRole().equals("leader")) {
                                            countLeadersInTeam++;
                                        }
                                    }
                                }

                                if (countUsersInTeam >= 1) {

                                    AlertDialog.Builder builderConfirmation = new AlertDialog.Builder(GameActivity.this);
                                    builderConfirmation.setCancelable(true);
                                    builderConfirmation.setTitle(R.string.leaving_team);
                                    builderConfirmation.setMessage(R.string.a_you_sure);
                                    int finalCountUsersInTeam = countUsersInTeam;
                                    builderConfirmation.setPositiveButton(R.string.yes_sure, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DocumentReference drUser = GameActivity.fbDb.collection("users").document(mainDbUser.getUserID());
                                            drUser.update("teamID", null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    DocumentReference drTeamUser = GameActivity.fbDb.collection("teams").document(mainDbTeam.getTeamID()).collection("teamUsers").document(mainDbTeamUser.getTeamUserID());
                                                    drTeamUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (finalCountUsersInTeam == 1) {
                                                                DocumentReference drTeam = GameActivity.fbDb.collection("teams").document(mainDbTeam.getTeamID());
                                                                drTeam.delete();
                                                            }
                                                            mainDbUser.setTeamID(null);
                                                            checkMenuVisibility();
                                                        }
                                                    });
                                                }
                                            });


                                        }
                                    });
                                    builderConfirmation.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    AlertDialog dialog = builderConfirmation.create();
                                    dialog.show();

                                } else {
                                    Toast.makeText(GameActivity.this, getString(R.string.team_have_another_users), Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    }

                });


            } else {
                // если ты в банде не лидер - смело можно покидать банду

                AlertDialog.Builder builderConfirmation = new AlertDialog.Builder(this);
                builderConfirmation.setCancelable(true);
                builderConfirmation.setTitle(R.string.leaving_team);
                builderConfirmation.setMessage(R.string.a_you_sure);
                builderConfirmation.setPositiveButton(R.string.yes_sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DocumentReference drUser = GameActivity.fbDb.collection("users").document(mainDbUser.getUserID());
                        drUser.update("teamID", null).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DocumentReference drTeamUser = GameActivity.fbDb.collection("teams").document(mainDbTeam.getTeamID()).collection("teamUsers").document(mainDbTeamUser.getTeamUserID());
                                drTeamUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        mainDbUser.setTeamID(null);
                                        checkMenuVisibility();
                                    }
                                });
                            }
                        });
                    }
                });
                builderConfirmation.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builderConfirmation.create();
                dialog.show();

            }
        }

    }

    private void doMenuCreateTeam() {

        DocumentReference docRefDbPref = fbDb.collection("database").document("preferences");
        docRefDbPref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    final long maxCountTeams = (long) task.getResult().get("maxCountTeams");
                    Query query = fbDb.collection("teams");
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().size() >= maxCountTeams) {

                                    Toast.makeText(GameActivity.this, R.string.max_count_teams_expired, Toast.LENGTH_LONG);

                                } else {

                                    AlertDialog.Builder builderConfirmation = new AlertDialog.Builder(GameActivity.this);
                                    builderConfirmation.setCancelable(true);
                                    builderConfirmation.setTitle(R.string.creating_new_team);
                                    builderConfirmation.setMessage(R.string.a_you_sure);
                                    builderConfirmation.setPositiveButton(R.string.yes_sure, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            final AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                                            builder.setTitle(R.string.team);
                                            String defaultValue = getString(R.string.new_team);
                                            final EditText input = new EditText(GameActivity.this);
                                            input.setInputType(InputType.TYPE_CLASS_TEXT);
                                            input.setText(defaultValue);
                                            builder.setView(input);

                                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String newValue = input.getText().toString();

                                                    CollectionReference collectionReference = fbDb.collection("teams");

                                                    Map<String, Object> mapNewItem = new HashMap<>();
                                                    mapNewItem.put("teamID", null);
                                                    mapNewItem.put("teamIsPublic", false);
                                                    mapNewItem.put("teamIsOpened", false);
                                                    mapNewItem.put("teamName", newValue);
                                                    mapNewItem.put("timestamp", FieldValue.serverTimestamp());

                                                    // Add a new document with a generated ID
                                                    collectionReference.add(mapNewItem).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                            final String teamID = documentReference.getId();
                                                            CollectionReference collectionReference = fbDb.collection("teams");
                                                            Map<String, Object> mapUpdateItem = new HashMap<>();
                                                            mapUpdateItem.put("teamID", teamID);
                                                            mapUpdateItem.put("timestamp", FieldValue.serverTimestamp());
                                                            collectionReference.document(teamID).update(mapUpdateItem);

                                                            CollectionReference users = fbDb.collection("users");
                                                            Map<String, Object> updateUser = new HashMap<>();
                                                            updateUser.put("teamID", teamID);
                                                            users.document(mainDbUser.getUserID()).update(updateUser);
                                                            mainDbUser.setTeamID(teamID);

                                                            collectionReference = fbDb.collection("teams").document(teamID).collection("teamUsers");
                                                            Map<String, Object> mapNewItem = new HashMap<>();
                                                            mapNewItem.put("teamID", teamID);
                                                            mapNewItem.put("userID", mainDbUser.getUserID());
                                                            mapNewItem.put("userRole", "leader");
                                                            mapNewItem.put("userNIC", mainDbUser.getUserNIC());
                                                            mapNewItem.put("timestamp", FieldValue.serverTimestamp());

                                                            // Add a new document with a generated ID
                                                            collectionReference.add(mapNewItem).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference) {
                                                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                                    String newDocumentID = documentReference.getId();
                                                                    CollectionReference collectionReference = fbDb.collection("teams").document(teamID).collection("teamUsers");
                                                                    Map<String, Object> mapUpdateItem = new HashMap<>();
                                                                    mapUpdateItem.put("teamUserID", newDocumentID);
                                                                    mapUpdateItem.put("timestamp", FieldValue.serverTimestamp());
                                                                    collectionReference.document(newDocumentID).update(mapUpdateItem);

                                                                    checkMenuVisibility();

                                                                }
                                                            })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Log.e(TAG, "Error adding document", e);
                                                                        }
                                                                    });

                                                        }
                                                    })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.e(TAG, "Error adding document", e);
                                                                }
                                                            });

                                                    checkMenuVisibility();
                                                }
                                            });
                                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });

                                            builder.show();


                                        }
                                    });
                                    builderConfirmation.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    AlertDialog dialog = builderConfirmation.create();
                                    dialog.show();


                                }

                            }
                        }
                    });

                }
            }
        });




    }

    private void doMenuManageTeam() {
        TeamActivity.dbTeam = mainDbTeam;
        Intent intent = new Intent(this, TeamActivity.class);
        startActivityForResult(intent, 0);
    }

    private void doMenuEmailVerificationCheck() {

        AuthUI.getInstance().signOut(GameActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(GameActivity.this, R.string.your_signed_out, Toast.LENGTH_LONG).show();
                fbUser = null;
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
            }
        });

    }

    private void doMenuEmailVerificationSend() {

        AlertDialog.Builder builderConfirmation = new AlertDialog.Builder(this);
        builderConfirmation.setCancelable(true);
        builderConfirmation.setTitle(R.string.send_email_verification);
        builderConfirmation.setMessage(R.string.a_you_sure);
        builderConfirmation.setPositiveButton(R.string.yes_sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AuthUI.getInstance().signOut(GameActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        fbUser.sendEmailVerification().addOnCompleteListener(GameActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(GameActivity.this, getString(R.string.email_sent_to) + " " + fbUser.getEmail(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(GameActivity.this, getString(R.string.failed_sent_email), Toast.LENGTH_SHORT).show();
                                }
                                checkMenuVisibility();
                            }
                        });
                    }
                });
            }
        });
        builderConfirmation.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builderConfirmation.create();
        dialog.show();


    }

    private void doMenuLogout() {

        AlertDialog.Builder builderConfirmation = new AlertDialog.Builder(this);
        builderConfirmation.setCancelable(true);
        builderConfirmation.setTitle(R.string.logout);
        builderConfirmation.setMessage(R.string.a_you_sure);
        builderConfirmation.setPositiveButton(R.string.yes_sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AuthUI.getInstance().signOut(GameActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(GameActivity.this, R.string.your_signed_out, Toast.LENGTH_LONG).show();
                        fbUser = null;
                        checkMenuVisibility();
                    }
                });
            }
        });
        builderConfirmation.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builderConfirmation.create();
        dialog.show();


    }

    private void doMenuLogin() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
    }

    private void doMenuShareFile() {

        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File sharedFile = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name));

//        if (mainCityCalc != null) {
//            if (mainCityCalc.getFileScreenshot() != null) {
//                sharedFile = mainCityCalc.getFileScreenshot();
//            }
//        }

        if(sharedFile.exists()) {
            intentShareFile.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri fileURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, sharedFile);
            
            intentShareFile.setType("*/*");
//            intentShareFile.setType("image/png");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, fileURI);

            String text = ga_tv_status.getText().toString();

            intentShareFile.putExtra(Intent.EXTRA_SUBJECT, text);
            intentShareFile.putExtra(Intent.EXTRA_TEXT, text);

            startActivity(Intent.createChooser(intentShareFile, text));
        }

    }

    private void doMenuOpenSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }

    private void doMenuOpenUserAccount() {
        Intent intent = new Intent(this, UserActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, 0);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }

    private void doMenuOpenScreenshot() {

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
                        if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                            fileGameScreenshot = newFile; // файл скриншота - возавращенный из диалога
                            if (!fileGameScreenshot.getAbsolutePath().equals(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name))) Utils.copyFile(fileGameScreenshot.getAbsolutePath(), getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name));
                            isListenToNewFileInFolder = false; // снимаем флажок "Следить за файлами в папке"
                            ga_sw_listen_new_file.setChecked(false); // устанавливаем контрол флажка
                            fileLastInFolder = null;    // сбрасываем последний файл в папке

                            Log.i(TAG, logMsgPref + "fileScreenshot = " + fileGameScreenshot.getAbsolutePath());
                            Log.i(TAG, logMsgPref + "инициализинуем mainCityCalc");
                            mainCityCalc = new CityCalc(tmpCityCalc, false);
                            Log.i(TAG, logMsgPref + "вызываем loadDataToViews()");
                            loadDataToViews(true);
                        } else if (tmpCityCalc.getCityCalcType().equals(CityCalcType.CAR)) {
                            CityCalc carCityCalc = new CityCalc(tmpCityCalc, false);
                            ((CCACar)carCityCalc.getMapAreas().get(Area.CAR_INFO)).parseCar();
                            fileCarScreenshot = newFile;

                        }
                    }
                });
        fileDialog.show();

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

            GameActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    String logMsgPref = "firstTask: ";

                    setDataToCarsViews();

                    if (isListenToNewFileInFolder) {    // если установлен флажок "Следить за файлами в папке"
                        File tmpFile = getLastFileInFolder(pathToScreenshotDir);    // получаем последний файл из папки
                        if (tmpFile != null) {  // если он не пустой
                            if ((!tmpFile.equals(fileGameScreenshot) && !tmpFile.equals(fileCarScreenshot)) || isResumed) {  // если он не равен текущем скриншоту

                                boolean needProceedFile = false;
                                // надо проверить, не является ли текущая игра взятая из скрина на свервере
                                if (mainCityCalc != null) { // если игра есть
                                    // если в игре - скрин с сервера
                                    if (mainCityCalc.getFileScreenshot().getAbsolutePath().equals(pathToCATScalcFolder + "/teamGameScreenshot")) {
                                        // если последний скрин из папки - более поздний, чем в игре
                                        if (tmpFile.lastModified() > mainCityCalc.getFileScreenshot().lastModified()) {
                                            needProceedFile = true;
                                        }
                                    } else {
                                        needProceedFile = true;
                                    }
                                } else {
                                    needProceedFile = true;
                                }

                                if (needProceedFile) {

                                    CityCalc tmpCityCalc = new CityCalc(tmpFile, calibrateX, calibrateY, context);
                                    if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                                        fileGameScreenshot = tmpFile;   // текущий скриншот = последнему файлу в папке

                                        boolean isRealtimeScreenshot = false;
                                        if (!fileGameScreenshot.getAbsolutePath().equals(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name))) {
                                            isRealtimeScreenshot = true;
                                            Utils.copyFile(fileGameScreenshot.getAbsolutePath(), getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name));
                                        }

                                        mainCityCalc = new CityCalc(tmpCityCalc, isRealtimeScreenshot);
                                        loadDataToViews(true);
                                    } else if (tmpCityCalc.getCityCalcType().equals(CityCalcType.CAR)) {
                                        CityCalc carCityCalc = new CityCalc(tmpCityCalc, true);
                                        ((CCACar)carCityCalc.getMapAreas().get(Area.CAR_INFO)).parseCar();
                                        fileCarScreenshot = tmpFile;
                                    }
                                    if (isResumed) isResumed = false;

                                }

                            }
                        }

                    } else {
                        if (fileGameScreenshot == null) {
                            File lastScreenshot = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name)); // последний скри
                            fileLast = lastScreenshot;
                            if (lastScreenshot.exists()) {
                                fileGameScreenshot = lastScreenshot;
                                CityCalc tmpCityCalc = new CityCalc(fileGameScreenshot, calibrateX, calibrateY, context);
                                if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                                    mainCityCalc = new CityCalc(tmpCityCalc, false);
                                    loadDataToViews(true);
                                } else if (tmpCityCalc.getCityCalcType().equals(CityCalcType.CAR)) {
                                    CityCalc carCityCalc = new CityCalc(tmpCityCalc, false);
                                    ((CCACar)carCityCalc.getMapAreas().get(Area.CAR_INFO)).parseCar();
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

            GameActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    String logMsgPref = "secondTask: ";

                    if (mainCityCalc != null) {
                        CCAGame ccaGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);
                        if (ccaGame != null) {
                            ccaGame.calcWin();
                            loadDataToViews(false);
                        }
                    }

                }
            });
        }
    };


}
