package com.svoemestodev.catscitycalc.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
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
import com.svoemestodev.catscitycalc.BuildConfig;
import com.svoemestodev.catscitycalc.CityCalcService;
import com.svoemestodev.catscitycalc.GlobalApplication;
import com.svoemestodev.catscitycalc.OverScreenService;
import com.svoemestodev.catscitycalc.adapters.ListTeamsAdapter;
import com.svoemestodev.catscitycalc.classes.Car;
import com.svoemestodev.catscitycalc.classes.CarList;
import com.svoemestodev.catscitycalc.classes.Forecaster;
import com.svoemestodev.catscitycalc.classes.LastModified;
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
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class GameActivity extends AppCompatActivity {

    // Game views


    ProgressBar ga_pb_progress;         // прогресс-бар
    LinearLayout ga_in_game_group;      // группа вьюшет игры
    RelativeLayout ga_rl_game;          // лайаут всей игры
    ScrollView ga_sv_game;              // скроллвью всей игры



    TextView ga_tv_status;              // текущий статус игры
    TextView ga_tv_user;                // имя пользователя, банда, роль
    TextView ga_tv_screenshot_time;     // информация о времени последнего скриншота
    Switch ga_sw_listen_new_file;       // переключатель "следить за файлами в папке"
    TextView lgi_tv_start_game_time;     // время начала игры
    TextView lgi_tv_end_game_time;       // время конца игры
    
    TextView lgi_tv_vs;                  // "против"
    
    // Time views
    TextView lgi_tv_total_time;          // время до конца игры
    TextView lgi_tv_early_win;           // очки до досрочной победы
    
    // Our team views
    ImageView lgi_iv_our_team_name;      // название нашей команды (картинка)
    TextView lgi_tv_our_increase;        // прибавка нашей команды
    TextView lgi_tv_our_points;          // очки нашей команды
    TextView lgi_tv_our_end_time;        // время до конца игры для нашей команды
    
    // Enemy team views
    ImageView lgi_iv_enemy_team_name;    // название команды противника (картинка)
    TextView lgi_tv_enemy_increase;      // прибавка команды противника
    TextView lgi_tv_enemy_points;        // очки команды противника
    TextView lgi_tv_enemy_end_time;      // время до конца игры для команды противника


    RelativeLayout[] lgb_rl_bld = new RelativeLayout[6];
    Button[] lgb_bt_bld = new Button[6];
    ImageView[] lgb_iv_bld_icon = new ImageView[6];
    ImageView[] lgb_iv_bld_name = new ImageView[6];
    TextView[] lgb_tv_bld_x2 = new TextView[6];
    TextView[] lgb_tv_bld_points = new TextView[6];
    TextView[] lgb_tv_bld_slots = new TextView[6];
    TextView[] lgb_tv_bld_slots_our = new TextView[6];
    TextView[] lgb_tv_bld_slots_empty = new TextView[6];
    TextView[] lgb_tv_bld_slots_enemy = new TextView[6];
    ImageView[] lgb_iv_bld_progress = new ImageView[6];
    ImageView[] lgb_iv_bld_can_win_with_x2 = new ImageView[6];
    ImageView[] lgb_iv_bld_can_early_win_with_x2 = new ImageView[6];

    // Рекламный блок
    AdView ga_ad_banner;                // баннер

    ImageView lgci_iv_game_car_black;     // машинка черная большая (картинка)
    ImageView lgci_iv_game_car_our;       // машинка синяя большая (картинка)
    ImageView lgci_iv_game_car_empty;     // машинка серая большая (картинка)
    ImageView lgci_iv_game_car_enemy;     // машинка красная большая (картинка)
    TextView lgci_tv_game_slots;          // слотов в игре всего
    TextView lgci_tv_game_slots_our;      // слотов в игре наших
    TextView lgci_tv_game_slots_empty;    // слотов в игре пустых
    TextView lgci_tv_game_slots_enemy;    // слотов в игре противника

    ImageButton lgcb_ib_car1;             // кнопка-картинка 1-й машинки
    ImageView lgcb_iv_car1_building_icon;
    ImageView lgcb_iv_car1_task_icon;             
    TextView lgcb_tv_car1_name;           // название 1-й машинки
    TextView lgcb_tv_car1_repair;         // время ремонта 1-й машинки
    TextView lgcb_tv_car1_health_shield;

    ImageButton lgcb_ib_car2;             // кнопка-картинка 2-й машинки
    ImageView lgcb_iv_car2_building_icon;
    ImageView lgcb_iv_car2_task_icon;
    TextView lgcb_tv_car2_name;           // название 2-й машинки
    TextView lgcb_tv_car2_repair;         // время ремонта 2-й машинки
    TextView lgcb_tv_car2_health_shield;

    ImageButton lgcb_ib_car3;             // кнопка-картинка 3-й машинки
    ImageView lgcb_iv_car3_building_icon;
    ImageView lgcb_iv_car3_task_icon;
    TextView lgcb_tv_car3_name;           // название 3-й машинки
    TextView lgcb_tv_car3_repair;         // время ремонта 3-й машинки
    TextView lgcb_tv_car3_health_shield;

    Button ga_bt_strategy;              // кнопка "Стратегичское планирование"
    TextView ga_tv_forecast;

    private static final int SIGN_IN_REQUEST_CODE = 1;

//    public transient static String pathToCATScalcFolder;        // путь к папке программы
    public static final int REQUEST_CODE_SECOND_ACTIVITY = 100; // код реквеста для вызова второй активности
    public static File fileGameScreenshot = null;               // текущий файл скриншота
    public static File fileGameScreenshotPrevious;              // предыдущий файл скриншота
    public static File fileLastInFolderScreenshot;                        // последний файл в папке
    public static File fileLastInFolderData;                        // последний файл в папке
    public static File fileLastInFolderWhatsapp;                        // последний файл в папке
    public static File fileLastInFolderTelegram;                        // последний файл в папке
    public static File fileCarScreenshot = null;                // текущий файл скриншота
    public static File fileCarScreenshotPrevious;               // предыдущий файл скриншота
    public static File fileLastScreenshot;                                // последний файл в папке
    public static File fileLastData;                                // последний файл в папке
    public static File fileLastWhatsapp;                                // последний файл в папке
    public static File fileLastTelegram;                                // последний файл в папке
    public static File lastDataFile;                                // последний файл в папке
    public static File lastWhatsappFile;                                // последний файл в папке
    public static File lastTelegramFile;                                // последний файл в папке
//    private NotificationManager notificationManager;            // нотификатор
//    private static final int NOTIFY_ID = 1;                     // айди нотификатора
//    private static final String CHANNEL_ID = "C.A.T.S. City Calculator Channel #1";   // канал нотификатора
    public static String pathToScreenshotDir = "";              // путь к папке скриншотов
    public static String pathToDataDir = "";              // путь к папке скриншотов
    public static String pathToWhatsappDir = "";              // путь к папке скриншотов
    public static String pathToTelegramDir = "";              // путь к папке скриншотов
    public static boolean isListenToNewFileInFolder;            // флаг "Следить за новыми файлами в папке"
    public static boolean isListenDataFolder;            // флаг "Следить за новыми файлами в папке"
    public static boolean isListenWhatsappFolder;            // флаг "Следить за новыми файлами в папке"
    public static boolean isListenTelegramFolder;            // флаг "Следить за новыми файлами в папке"
    public static boolean isAllFieldsCorrect;                   // флаг "Все поля заполнены правильно"
    public static int calibrateX;                              // калибровка
    public static int calibrateY;                              // калибровка
    private Timer timer;                                        // таймер
    public static boolean isDebugMode;                          // флаг "Режим отладки"
//    public static Context context;                              // контекст

    public static CityCalc mainCityCalc;                        // текущая игра
    public static CCAGame mainCCAGame;                        // текущая игра

    public static CityCalc prevCityCalc;                        // предыдущая игра
    public static CCAGame prevCCAGame;                        // предыдущая игра

    private static final String TAG = "GameActivity";           // таг для лога

    public boolean isResumed;                                   // isResumed

    public static FirebaseUser fbUser;          // юзер файрбейса
    public static FirebaseAuth fbAuth;          // аутентификация файрбейса
//    public static FirebaseStorage fbStor = FirebaseStorage.getInstance();       // стораж файрбейса

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
    public static MenuItem menu_main_team_work;               // пункт меню "Банда"
    public static MenuItem menu_main_team_manage;               // пункт меню "Банда"
    public static MenuItem menu_main_team_create;               // пункт меню "Создать банду"
    public static MenuItem menu_main_team_leave;                // пункт меню "Выйти из банды"
    public static MenuItem menu_main_team_find;                 // пункт меню "Найти банду"
    public static MenuItem menu_main_team_game_load;            // пункт меню "Team Game Load"
    public static MenuItem menu_main_team_game_share;           // пункт меню "Team Game Share"
    public static MenuItem menu_main_user_cars_load;            // пункт меню "User Cars Load"
    public static MenuItem menu_main_user_cars_share;           // пункт меню "User Cars Share"
    public static MenuItem menu_main_state_share;               // пункт меню "State Share"
    public static MenuItem menu_main_take_screenshot;               // пункт меню "State Share"

    public static String mainUserNIC = "";
    public static String mainUserUID = "";
    public static String mainTeamID = "";

    public static firstTask.TaskListenScreenshotFolder tlff;

    public static GameActivity mainGameActivity;

//    public static int OVERLAY_PERMISSION_REQ_CODE = 1;

    OverScreenService overScreenService;    // сервис
    boolean boundOverScreenService;         // флаг биндинга сервиса
    Intent intentOSS;                          // интент текущей активности

    private static final int REQUEST_OVERLAY_PERMISSION = 23658;
    private static final int REQUEST_SCREENSHOT=59706;
    public static MediaProjectionManager mediaProjectionManager;

    public static boolean isNeedUpdateCars;

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            OverScreenService.LocalBinder binder = (OverScreenService.LocalBinder) service;
            overScreenService = binder.getService();
            boundOverScreenService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            boundOverScreenService = false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;
        checkMenuVisibility();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String logMsgPref = "onCreate: ";
        Log.i(TAG, logMsgPref + "start");

        super.onCreate(savedInstanceState);

        mainGameActivity = this;

        fbAuth = FirebaseAuth.getInstance();
        fbUser = fbAuth.getCurrentUser();

//        context = getBaseContext();

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
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("FA048D0A042E981F253B2DFF6ECE1133")).build());
        AdRequest adRequest = new AdRequest.Builder().build();
        ga_ad_banner.loadAd(adRequest);

        // нотификейшн менеджер
//        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        readPreferences(); // считываем преференцы
        ga_sw_listen_new_file.setChecked(isListenToNewFileInFolder);

        File lastScrFile = new File (getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name));
        CityCalc tmpCityCalc = new CityCalc(lastScrFile, calibrateX, calibrateY, mainUserNIC, mainUserUID, mainTeamID);
        fileGameScreenshot = lastScrFile;   // текущий скриншот = последнему файлу в папке
        fileLastScreenshot = lastScrFile;
        mainCityCalc = new CityCalc(tmpCityCalc, false);
        mainCCAGame = (CCAGame)mainCityCalc.getMapAreas().get(Area.CITY);

        prevCityCalc = mainCityCalc.getClone();
        prevCCAGame = (CCAGame)prevCityCalc.getMapAreas().get(Area.CITY);

        loadDataToViews(true);
        setDataToCarsViews();

        startTimer();   // стартуем таймер

        mediaProjectionManager = (MediaProjectionManager)getSystemService(MEDIA_PROJECTION_SERVICE);
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_SCREENSHOT);

        if(!Settings.canDrawOverlays(this)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION);
        }

    }

    public void loadDataToViews(boolean withNotify) {

        String logMsgPref = "loadDataToViews: ";

        ga_pb_progress.setVisibility(View.VISIBLE);

        String textStartGameTime;
        String textEndGameTime;
//        CCAGame ccaGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);
        CCAGame ccaGame = mainCCAGame;
        CCATeam ccaOurTeam = (CCATeam) mainCityCalc.getMapAreas().get(Area.TEAM_NAME_OUR);
        CCATeam ccaEnemyTeam = (CCATeam) mainCityCalc.getMapAreas().get(Area.TEAM_NAME_ENEMY);
        CCABuilding[] ccabld = new CCABuilding[6];
        ccabld[0] = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLT);
        ccabld[1] = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLC);
        ccabld[2] = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLB);
        ccabld[3] = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRT);
        ccabld[4] = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRC);
        ccabld[5] = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRB);
        String pattern = "dd MMM HH:mm";

        if (ccaGame != null && ccaGame.getDateStartGame() != null) {

            ccaGame.calcWin(true);


            Date dateScreenshot = ccaGame.getDateScreenshot();
            int minutesFromTakingScreenshot = (int)((Calendar.getInstance().getTime().getTime() - dateScreenshot.getTime()) / 60000);
            String screenshotTimeText = getString(R.string.screenshot_will_create) + " " + minutesFromTakingScreenshot + " " + getString(R.string.minutes_ago) + ((mainCCAGame.getUserNIC() != null && !mainCCAGame.getUserNIC().equals("")) ? " " + getString(R.string.by_user) + " " + mainCCAGame.getUserNIC() + "." : ".") +
                    " (" + (mainCCAGame.getSource() == 0 ? getString(R.string.info_from_screenshot) : (mainCCAGame.getSource() == 1 ? getString(R.string.info_from_server) : getString(R.string.info_from_data_file))) + ")";
            ga_tv_screenshot_time.setText(screenshotTimeText);
            ga_tv_screenshot_time.setTextColor(minutesFromTakingScreenshot >= 10 ? Color.RED :  Color.BLACK);

            ga_bt_strategy.setVisibility(!ccaGame.isGameOver() ? View.VISIBLE : View.INVISIBLE);
            ga_tv_forecast.setVisibility(!ccaGame.isGameOver() ? View.VISIBLE : View.INVISIBLE);

            textStartGameTime = getString(R.string.start_game_at) + ": " + Utils.convertDateToString(ccaGame.getDateStartGame(), pattern);    // дата/время начала игры

            textEndGameTime = getString(R.string.end_game_at) + ": "  + Utils.convertDateToString(ccaGame.getDateEndGame(), pattern);          // дата/время окончания игры

            if (ccaGame.isWillOurWin() || ccaGame.isWinOur()) {
                ga_tv_status.setBackground(getDrawable(R.drawable.rounded_small_corner_color_our_dark));
            } else {
                ga_tv_status.setBackground(getDrawable(R.drawable.rounded_small_corner_color_enemy_dark));
            }

//            ga_tv_status.setBackground(getDrawable((ccaGame.isWillOurWin() || ccaGame.isWinOur()) ? R.drawable.rounded_small_corner_color_our_dark : R.drawable.rounded_small_corner_color_enemy_dark));
            ga_tv_status.setText(ccaGame.getStatus());   // статус
            ga_tv_forecast.setText(ccaGame.getForecastText());   // прогноз
            lgi_tv_start_game_time.setText(textStartGameTime);   // дата/время начала игры
            lgi_tv_end_game_time.setText(textEndGameTime);       // дата/время окончания игры

            if (ccaGame.isGameOver()) {   // если игра закончена
                lgi_tv_total_time.setText("");   // время игры - пустое
            } else { // если игра не закончена
                lgi_tv_total_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToEndGame())); // время игры
            }

            lgi_tv_early_win.setText(String.valueOf(ccaGame.getEarlyWin())); // очки до досрочной победы

            if (ccaOurTeam != null) lgi_iv_our_team_name.setImageBitmap(PictureProcessor.makeTransparent(ccaOurTeam.getBmpSrc(),0xFFFFFF));  // имя нашей команды
            if (ccaEnemyTeam != null) lgi_iv_enemy_team_name.setImageBitmap(PictureProcessor.makeTransparent(ccaEnemyTeam.getBmpSrc(),0xFFFFFF));  // имя команды противника


            lgi_tv_our_increase.setText(ccaGame.getIncreaseOur() == 0 ? "" : " +" + ccaGame.getIncreaseOur() + " ");   // прирост нашей команды
            lgi_tv_our_points.setText(String.valueOf(ccaGame.getPointsOur()));  // очки нашей команды

            lgi_tv_enemy_increase.setText(ccaGame.getIncreaseEnemy() == 0 ? "" : " +" + ccaGame.getIncreaseEnemy() + " "); // прирост команды противника
            lgi_tv_enemy_points.setText(String.valueOf(ccaGame.getPointsEnemy()));    // очки команды противника

            if (ccaGame.isGameOver()) {   // если игра закончена
                lgi_tv_our_end_time.setText(""); // наше время пустое
                lgi_tv_enemy_end_time.setText(""); // время противника пустое
            } else { // если игра незакончена
                if (ccaGame.isWillOurWin()) {
                    lgi_tv_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                    lgi_tv_enemy_end_time.setText("");   // время противника пустое
                } else if (ccaGame.isWillEnemyWin()) {
                    lgi_tv_our_end_time.setText(""); // наше время пустое
                    lgi_tv_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                } else if (ccaGame.isWillNobodyWin()) {
                    lgi_tv_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                    lgi_tv_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                }

            }

            Context context = getApplicationContext();

            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);
            int color_bxx_mayX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_mayX2_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_mayX2_main), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_mayX2), 16)));
            int color_bxx_isX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_isX2_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_isX2_main), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_isX2), 16)));
//            int color_progress_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_our_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_our), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_our_main), 16)));
//            int color_progress_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_enemy_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_enemy), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_enemy_main), 16)));
//            int color_progress_empty = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_empty_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_empty), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_empty_main), 16)));
            int color_progress_our = getColor(R.color.colorOurLight);
            int color_progress_enemy = getColor(R.color.colorEnemyLight);
            int color_progress_empty = getColor(R.color.colorEmptyDark);


            int progressBitmapWidth = 300;
            int progressBitmapHeight = 20;
            
            int slots = 0, slots_our = 0, slots_empty = 0, slots_enemy = 0;

            int[] bldIconsBlue = new int[6];
            int[] bldIconsRed = new int[6];
            int[] bldIconsGray = new int[6];

            bldIconsBlue[0] = R.drawable.ic_bld1_blue; bldIconsRed[0] = R.drawable.ic_bld1_red; bldIconsGray[0] = R.drawable.ic_bld1_gray;
            bldIconsBlue[1] = R.drawable.ic_bld2_blue; bldIconsRed[1] = R.drawable.ic_bld2_red; bldIconsGray[1] = R.drawable.ic_bld2_gray;
            bldIconsBlue[2] = R.drawable.ic_bld3_blue; bldIconsRed[2] = R.drawable.ic_bld3_red; bldIconsGray[2] = R.drawable.ic_bld3_gray;
            bldIconsBlue[3] = R.drawable.ic_bld4_blue; bldIconsRed[3] = R.drawable.ic_bld4_red; bldIconsGray[3] = R.drawable.ic_bld4_gray;
            bldIconsBlue[4] = R.drawable.ic_bld5_blue; bldIconsRed[4] = R.drawable.ic_bld5_red; bldIconsGray[4] = R.drawable.ic_bld5_gray;
            bldIconsBlue[5] = R.drawable.ic_bld6_blue; bldIconsRed[5] = R.drawable.ic_bld6_red; bldIconsGray[5] = R.drawable.ic_bld6_gray;

            for (int buildingIndex = 0; buildingIndex < 6; buildingIndex++) {
                lgb_iv_bld_icon[buildingIndex].setVisibility(ccaGame.getBuildings()[buildingIndex].isPresent() ? View.VISIBLE : View.INVISIBLE);
                lgb_iv_bld_name[buildingIndex].setVisibility(ccaGame.getBuildings()[buildingIndex].isPresent() ? View.VISIBLE : View.INVISIBLE);
                lgb_tv_bld_x2[buildingIndex].setVisibility(ccaGame.getBuildings()[buildingIndex].isPresent() ? View.VISIBLE : View.INVISIBLE);
                lgb_tv_bld_points[buildingIndex].setVisibility(ccaGame.getBuildings()[buildingIndex].isPresent() ? View.VISIBLE : View.INVISIBLE);
                lgb_tv_bld_slots[buildingIndex].setVisibility(ccaGame.getBuildings()[buildingIndex].isPresent() ? View.VISIBLE : View.INVISIBLE);
                lgb_tv_bld_slots_our[buildingIndex].setVisibility(ccaGame.getBuildings()[buildingIndex].isPresent() ? View.VISIBLE : View.INVISIBLE);
                lgb_tv_bld_slots_empty[buildingIndex].setVisibility(ccaGame.getBuildings()[buildingIndex].isPresent() ? View.VISIBLE : View.INVISIBLE);
                lgb_tv_bld_slots_enemy[buildingIndex].setVisibility(ccaGame.getBuildings()[buildingIndex].isPresent() ? View.VISIBLE : View.INVISIBLE);
                lgb_iv_bld_progress[buildingIndex].setVisibility(ccaGame.getBuildings()[buildingIndex].isPresent() ? View.VISIBLE : View.INVISIBLE);

                if (ccaGame.getBuildings()[buildingIndex].isPresent()) {

                    lgb_rl_bld[buildingIndex].setBackground(getDrawable(ccaGame.getBuildings()[buildingIndex].isUseInForecast() ? R.drawable.rounded_small_corner_light_gray : R.drawable.rounded_small_corner_gray));

                    lgb_iv_bld_can_win_with_x2[buildingIndex].setImageDrawable(getDrawable(ccaGame.getBuildings()[buildingIndex].isNeedToWin() ? R.drawable.ic_can_win_with_x2_true : R.drawable.ic_can_win_with_x2_false));
                    lgb_iv_bld_can_early_win_with_x2[buildingIndex].setImageDrawable(getDrawable(ccaGame.getBuildings()[buildingIndex].isNeedToEarlyWin() ? R.drawable.ic_can_early_win_with_x2_true : R.drawable.ic_can_early_win_with_x2_false));

                    if (ccabld[buildingIndex] != null) lgb_iv_bld_name[buildingIndex].setImageBitmap(PictureProcessor.makeTransparent(ccabld[buildingIndex].getBmpSrc(),0xFFFFFF));

                    lgb_iv_bld_progress[buildingIndex].setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                            new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                            new int[]{ccaGame.getBuildings()[buildingIndex].getSlots_our(), ccaGame.getBuildings()[buildingIndex].getSlots_empty(), ccaGame.getBuildings()[buildingIndex].getSlots_enemy()}));

                    lgb_tv_bld_slots[buildingIndex].setText(String.valueOf(ccaGame.getBuildings()[buildingIndex].getSlots()));
                    lgb_tv_bld_slots_our[buildingIndex].setText(String.valueOf(ccaGame.getSlots_our_toView(buildingIndex)));
                    lgb_tv_bld_slots_empty[buildingIndex].setText(String.valueOf(ccaGame.getBuildings()[buildingIndex].getSlots_empty()));
                    lgb_tv_bld_slots_enemy[buildingIndex].setText(String.valueOf(ccaGame.getSlots_enemy_toView(buildingIndex)));

                    slots += ccaGame.getBuildings()[buildingIndex].getSlots();
                    slots_our += ccaGame.getBuildings()[buildingIndex].getSlots_our();
                    slots_empty += ccaGame.getBuildings()[buildingIndex].getSlots_empty();
                    slots_enemy += ccaGame.getBuildings()[buildingIndex].getSlots_enemy();

                    if (ccaGame.getBuildings()[buildingIndex].isBuildingIsOur()) {
                        lgb_tv_bld_points[buildingIndex].setText("+" + ccaGame.getBuildings()[buildingIndex].getOur_points());
                        lgb_tv_bld_points[buildingIndex].setBackground(getDrawable(R.drawable.rounded_small_corner_color_our_dark));
                        lgb_iv_bld_icon[buildingIndex].setImageDrawable(getDrawable(bldIconsBlue[buildingIndex]));
                    } else if (ccaGame.getBuildings()[buildingIndex].isBuildingIsEnemy()) {
                        lgb_tv_bld_points[buildingIndex].setText("+" + ccaGame.getBuildings()[buildingIndex].getEnemy_points());
                        lgb_tv_bld_points[buildingIndex].setBackground(getDrawable(R.drawable.rounded_small_corner_color_enemy_dark));
                        lgb_iv_bld_icon[buildingIndex].setImageDrawable(getDrawable(bldIconsRed[buildingIndex]));
                    } else if (ccaGame.getBuildings()[buildingIndex].isBuildingIsEmpty()) {
                        lgb_tv_bld_points[buildingIndex].setText("");
                        lgb_tv_bld_points[buildingIndex].setBackgroundColor(0x00000000);
                        lgb_iv_bld_icon[buildingIndex].setImageDrawable(getDrawable(bldIconsGray[buildingIndex]));
                    }
                    if (ccaGame.getBuildings()[buildingIndex].isX2()) {
                        lgb_tv_bld_x2[buildingIndex].setText("X2");
                        lgb_tv_bld_x2[buildingIndex].setBackground(getDrawable(R.drawable.rounded_small_corner_color_isx2));
                    } else {
                        if (ccaGame.getBuildings()[buildingIndex].isMayX2()) {
                            lgb_tv_bld_x2[buildingIndex].setText("X2");
                            lgb_tv_bld_x2[buildingIndex].setBackground(getDrawable(R.drawable.rounded_small_corner_color_empty_light));
                        } else {
                            lgb_tv_bld_x2[buildingIndex].setText("");
                            lgb_tv_bld_x2[buildingIndex].setBackgroundColor(0x00000000);
                        }
                    }
                }
            }
            

            lgci_tv_game_slots.setText(String.valueOf(slots));
            lgci_tv_game_slots_our.setText(String.valueOf(slots_our));
            lgci_tv_game_slots_empty.setText(String.valueOf(slots_empty));
            lgci_tv_game_slots_enemy.setText(String.valueOf(slots_enemy));

        }



        // нотификация
        if (withNotify) {

            Log.i(TAG, logMsgPref + "withNotify");
            if (ccaGame != null) {
                Log.i(TAG, logMsgPref + "создание уведомления: " + ccaGame.getStatus());

                GlobalApplication.mService.createNotification(ccaGame.getStatus(), 0, mainCityCalc.getBmpScreenshot());

            }
        }

        ga_pb_progress.setVisibility(View.INVISIBLE);

    }

    public void initializeViews() {

        String logMsgPref = "initializeViews: ";
        Log.i(TAG, logMsgPref + "start");

        // Game views
        ga_tv_status = findViewById(R.id.ga_tv_status);
        ga_sw_listen_new_file = findViewById(R.id.ga_sw_listen_new_file);
        ga_tv_screenshot_time = findViewById(R.id.ga_tv_screenshot_time);
        lgi_tv_start_game_time = findViewById(R.id.lgi_tv_start_game_time);
        lgi_tv_end_game_time = findViewById(R.id.lgi_tv_end_game_time);
        
        lgi_tv_vs = findViewById(R.id.lgi_tv_vs);

        // Time views
        lgi_tv_total_time = findViewById(R.id.lgi_tv_total_time);
        lgi_tv_early_win = findViewById(R.id.lgi_tv_early_win);

        // Our team views
        lgi_iv_our_team_name = findViewById(R.id.lgi_iv_our_team_name);
        lgi_tv_our_increase = findViewById(R.id.lgi_tv_our_increase);
        lgi_tv_our_points = findViewById(R.id.lgi_tv_our_points);
        lgi_tv_our_end_time = findViewById(R.id.lgi_tv_our_end_time);

        // Enemy team views
        lgi_iv_enemy_team_name = findViewById(R.id.lgi_iv_enemy_team_name);
        lgi_tv_enemy_increase = findViewById(R.id.lgi_tv_enemy_increase);
        lgi_tv_enemy_points = findViewById(R.id.lgi_tv_enemy_points);
        lgi_tv_enemy_end_time = findViewById(R.id.lgi_tv_enemy_end_time);

        // bld1 views
        lgb_iv_bld_icon[0] = findViewById(R.id.lgb_iv_bld1_icon);
        lgb_iv_bld_name[0] = findViewById(R.id.lgb_iv_bld1_name);
        lgb_tv_bld_x2[0] = findViewById(R.id.lgb_tv_bld1_x2);
        lgb_tv_bld_points[0] = findViewById(R.id.lgb_tv_bld1_points);
        lgb_tv_bld_slots[0] = findViewById(R.id.lgb_tv_bld1_slots);
        lgb_tv_bld_slots_our[0] = findViewById(R.id.lgb_tv_bld1_slots_our);
        lgb_tv_bld_slots_empty[0] = findViewById(R.id.lgb_tv_bld1_slots_empty);
        lgb_tv_bld_slots_enemy[0] = findViewById(R.id.lgb_tv_bld1_slots_enemy);
        lgb_iv_bld_progress[0] = findViewById(R.id.lgb_iv_bld1_progress);
        lgb_iv_bld_can_win_with_x2[0] = findViewById(R.id.lgb_iv_bld1_can_win_with_x2);
        lgb_iv_bld_can_early_win_with_x2[0] = findViewById(R.id.lgb_iv_bld1_can_early_win_with_x2);

        
        // bld2 views
        lgb_iv_bld_icon[1] = findViewById(R.id.lgb_iv_bld2_icon);
        lgb_iv_bld_name[1] = findViewById(R.id.lgb_iv_bld2_name);
        lgb_tv_bld_x2[1] = findViewById(R.id.lgb_tv_bld2_x2);
        lgb_tv_bld_points[1] = findViewById(R.id.lgb_tv_bld2_points);
        lgb_tv_bld_slots[1] = findViewById(R.id.lgb_tv_bld2_slots);
        lgb_tv_bld_slots_our[1] = findViewById(R.id.lgb_tv_bld2_slots_our);
        lgb_tv_bld_slots_empty[1] = findViewById(R.id.lgb_tv_bld2_slots_empty);
        lgb_tv_bld_slots_enemy[1] = findViewById(R.id.lgb_tv_bld2_slots_enemy);
        lgb_iv_bld_progress[1] = findViewById(R.id.lgb_iv_bld2_progress);
        lgb_iv_bld_can_win_with_x2[1] = findViewById(R.id.lgb_iv_bld2_can_win_with_x2);
        lgb_iv_bld_can_early_win_with_x2[1] = findViewById(R.id.lgb_iv_bld2_can_early_win_with_x2);

        // bld3 views
        lgb_iv_bld_icon[2] = findViewById(R.id.lgb_iv_bld3_icon);
        lgb_iv_bld_name[2] = findViewById(R.id.lgb_iv_bld3_name);
        lgb_tv_bld_x2[2] = findViewById(R.id.lgb_tv_bld3_x2);
        lgb_tv_bld_points[2] = findViewById(R.id.lgb_tv_bld3_points);
        lgb_tv_bld_slots[2] = findViewById(R.id.lgb_tv_bld3_slots);
        lgb_tv_bld_slots_our[2] = findViewById(R.id.lgb_tv_bld3_slots_our);
        lgb_tv_bld_slots_empty[2] = findViewById(R.id.lgb_tv_bld3_slots_empty);
        lgb_tv_bld_slots_enemy[2] = findViewById(R.id.lgb_tv_bld3_slots_enemy);
        lgb_iv_bld_progress[2] = findViewById(R.id.lgb_iv_bld3_progress);
        lgb_iv_bld_can_win_with_x2[2] = findViewById(R.id.lgb_iv_bld3_can_win_with_x2);
        lgb_iv_bld_can_early_win_with_x2[2] = findViewById(R.id.lgb_iv_bld3_can_early_win_with_x2);

        // bld4 views
        lgb_iv_bld_icon[3] = findViewById(R.id.lgb_iv_bld4_icon);
        lgb_iv_bld_name[3] = findViewById(R.id.lgb_iv_bld4_name);
        lgb_tv_bld_x2[3] = findViewById(R.id.lgb_tv_bld4_x2);
        lgb_tv_bld_points[3] = findViewById(R.id.lgb_tv_bld4_points);
        lgb_tv_bld_slots[3] = findViewById(R.id.lgb_tv_bld4_slots);
        lgb_tv_bld_slots_our[3] = findViewById(R.id.lgb_tv_bld4_slots_our);
        lgb_tv_bld_slots_empty[3] = findViewById(R.id.lgb_tv_bld4_slots_empty);
        lgb_tv_bld_slots_enemy[3] = findViewById(R.id.lgb_tv_bld4_slots_enemy);
        lgb_iv_bld_progress[3] = findViewById(R.id.lgb_iv_bld4_progress);
        lgb_iv_bld_can_win_with_x2[3] = findViewById(R.id.lgb_iv_bld4_can_win_with_x2);
        lgb_iv_bld_can_early_win_with_x2[3] = findViewById(R.id.lgb_iv_bld4_can_early_win_with_x2);

        // bld5 views
        lgb_iv_bld_icon[4] = findViewById(R.id.lgb_iv_bld5_icon);
        lgb_iv_bld_name[4] = findViewById(R.id.lgb_iv_bld5_name);
        lgb_tv_bld_x2[4] = findViewById(R.id.lgb_tv_bld5_x2);
        lgb_tv_bld_points[4] = findViewById(R.id.lgb_tv_bld5_points);
        lgb_tv_bld_slots[4] = findViewById(R.id.lgb_tv_bld5_slots);
        lgb_tv_bld_slots_our[4] = findViewById(R.id.lgb_tv_bld5_slots_our);
        lgb_tv_bld_slots_empty[4] = findViewById(R.id.lgb_tv_bld5_slots_empty);
        lgb_tv_bld_slots_enemy[4] = findViewById(R.id.lgb_tv_bld5_slots_enemy);
        lgb_iv_bld_progress[4] = findViewById(R.id.lgb_iv_bld5_progress);
        lgb_iv_bld_can_win_with_x2[4] = findViewById(R.id.lgb_iv_bld5_can_win_with_x2);
        lgb_iv_bld_can_early_win_with_x2[4] = findViewById(R.id.lgb_iv_bld5_can_early_win_with_x2);

        // bld6 views
        lgb_iv_bld_icon[5] = findViewById(R.id.lgb_iv_bld6_icon);
        lgb_iv_bld_name[5] = findViewById(R.id.lgb_iv_bld6_name);
        lgb_tv_bld_x2[5] = findViewById(R.id.lgb_tv_bld6_x2);
        lgb_tv_bld_points[5] = findViewById(R.id.lgb_tv_bld6_points);
        lgb_tv_bld_slots[5] = findViewById(R.id.lgb_tv_bld6_slots);
        lgb_tv_bld_slots_our[5] = findViewById(R.id.lgb_tv_bld6_slots_our);
        lgb_tv_bld_slots_empty[5] = findViewById(R.id.lgb_tv_bld6_slots_empty);
        lgb_tv_bld_slots_enemy[5] = findViewById(R.id.lgb_tv_bld6_slots_enemy);
        lgb_iv_bld_progress[5] = findViewById(R.id.lgb_iv_bld6_progress);
        lgb_iv_bld_can_win_with_x2[5] = findViewById(R.id.lgb_iv_bld6_can_win_with_x2);
        lgb_iv_bld_can_early_win_with_x2[5] = findViewById(R.id.lgb_iv_bld6_can_early_win_with_x2);

        // Рекламный блок
        ga_ad_banner = findViewById(R.id.ga_ad_banner);

        lgci_iv_game_car_black = findViewById(R.id.lgci_iv_game_car_black);
        lgci_iv_game_car_our = findViewById(R.id.lgci_iv_game_car_our);
        lgci_iv_game_car_empty = findViewById(R.id.lgci_iv_game_car_empty);
        lgci_iv_game_car_enemy = findViewById(R.id.lgci_iv_game_car_enemy);
        lgci_tv_game_slots = findViewById(R.id.lgci_tv_game_slots);
        lgci_tv_game_slots_our = findViewById(R.id.lgci_tv_game_slots_our);
        lgci_tv_game_slots_empty = findViewById(R.id.lgci_tv_game_slots_empty);
        lgci_tv_game_slots_enemy = findViewById(R.id.lgci_tv_game_slots_enemy);

        ga_bt_strategy = findViewById(R.id.ga_bt_strategy);

        lgcb_ib_car1 = findViewById(R.id.lgcb_ib_car1);
        lgcb_tv_car1_name = findViewById(R.id.lgcb_tv_car1_name);
        lgcb_tv_car1_repair = findViewById(R.id.lgcb_tv_car1_repair);
        lgcb_iv_car1_building_icon = findViewById(R.id.lgcb_iv_car1_building_icon);
        lgcb_iv_car1_task_icon = findViewById(R.id.lgcb_iv_car1_task_icon);
        lgcb_tv_car1_health_shield = findViewById(R.id.lgcb_tv_car1_health_shield);

        lgcb_ib_car2 = findViewById(R.id.lgcb_ib_car2);
        lgcb_tv_car2_name = findViewById(R.id.lgcb_tv_car2_name);
        lgcb_tv_car2_repair = findViewById(R.id.lgcb_tv_car2_repair);
        lgcb_iv_car2_building_icon = findViewById(R.id.lgcb_iv_car2_building_icon);
        lgcb_iv_car2_task_icon = findViewById(R.id.lgcb_iv_car2_task_icon);
        lgcb_tv_car2_health_shield = findViewById(R.id.lgcb_tv_car2_health_shield);

        lgcb_ib_car3 = findViewById(R.id.lgcb_ib_car3);
        lgcb_tv_car3_name = findViewById(R.id.lgcb_tv_car3_name);
        lgcb_tv_car3_repair = findViewById(R.id.lgcb_tv_car3_repair);
        lgcb_iv_car3_building_icon = findViewById(R.id.lgcb_iv_car3_building_icon);
        lgcb_iv_car3_task_icon = findViewById(R.id.lgcb_iv_car3_task_icon);
        lgcb_tv_car3_health_shield = findViewById(R.id.lgcb_tv_car3_health_shield);

        ga_tv_user = findViewById(R.id.ga_tv_user);
        ga_tv_forecast = findViewById(R.id.ga_tv_forecast);
        ga_sv_game = findViewById(R.id.ga_sv_game);
        ga_rl_game = findViewById(R.id.ga_rl_game);
        ga_in_game_group = findViewById(R.id.ga_in_game_group);

        lgb_rl_bld[0] = findViewById(R.id.lgb_rl_bld1);
        lgb_rl_bld[1] = findViewById(R.id.lgb_rl_bld2);
        lgb_rl_bld[2] = findViewById(R.id.lgb_rl_bld3);
        lgb_rl_bld[3] = findViewById(R.id.lgb_rl_bld4);
        lgb_rl_bld[4] = findViewById(R.id.lgb_rl_bld5);
        lgb_rl_bld[5] = findViewById(R.id.lgb_rl_bld6);

        lgb_bt_bld[0] = findViewById(R.id.lgb_bt_bld1);
        lgb_bt_bld[1] = findViewById(R.id.lgb_bt_bld2);
        lgb_bt_bld[2] = findViewById(R.id.lgb_bt_bld3);
        lgb_bt_bld[3] = findViewById(R.id.lgb_bt_bld4);
        lgb_bt_bld[4] = findViewById(R.id.lgb_bt_bld5);
        lgb_bt_bld[5] = findViewById(R.id.lgb_bt_bld6);

        ga_pb_progress = findViewById(R.id.ga_pb_progress);

        for (int buildingIndex = 0; buildingIndex < 6; buildingIndex++) {
            int finalBuildingIndex = buildingIndex;
            lgb_bt_bld[buildingIndex].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doOnClickBuilding(finalBuildingIndex + 1);
                }
            });

            lgb_bt_bld[buildingIndex].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    doOnLongClickBuilding(finalBuildingIndex + 1);
                    return true;
                }
            });
        }

    }

    public void setDataToCarsViews() {

        Car car1 = Car.loadCar(1);
        Car car2 = Car.loadCar(2);
        Car car3 = Car.loadCar(3);

        lgcb_tv_car1_name.setText(car1.getName());
        lgcb_tv_car1_health_shield.setText(car1.getHealth() + "/" + car1.getShield());

        lgcb_tv_car2_name.setText(car2.getName());
        lgcb_tv_car2_health_shield.setText(car2.getHealth() + "/" + car2.getShield());

        lgcb_tv_car3_name.setText(car3.getName());
        lgcb_tv_car3_health_shield.setText(car3.getHealth() + "/" + car3.getShield());

        try {
            if (((CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY)).isGameOver()) {

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
            if (car1CarBitmap != null) lgcb_ib_car1.setImageBitmap(car1CarBitmap);
            lgcb_ib_car1.setImageBitmap(car1.getCarPicture());
            String car1textRepair = car1.isRepairing() ? "" + car1.getTimeStringToEndRepairing() : "";
            lgcb_tv_car1_repair.setVisibility(car1textRepair.equals("") ? View.INVISIBLE : View.VISIBLE);
            lgcb_tv_car1_repair.setText(car1textRepair);
            lgcb_iv_car1_building_icon.setVisibility(car1.getBuilding() >= 0 ? View.VISIBLE : View.INVISIBLE);
            lgcb_iv_car1_task_icon.setVisibility(car1.getBuildingTask() > 0 ? View.VISIBLE : View.INVISIBLE);
            switch (car1.getBuilding()) {
                case 0:
                    lgcb_iv_car1_building_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                    break;
                case 1:
                    lgcb_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld1_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld1_blue : R.drawable.ic_bld1_red));
                    break;
                case 2:
                    lgcb_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld2_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld2_blue : R.drawable.ic_bld2_red));
                    break;
                case 3:
                    lgcb_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld3_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld3_blue : R.drawable.ic_bld3_red));
                    break;
                case 4:
                    lgcb_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld4_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld4_blue : R.drawable.ic_bld4_red));
                    break;
                case 5:
                    lgcb_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld5_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld5_blue : R.drawable.ic_bld5_red));
                    break;
                case 6:
                    lgcb_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld6_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld6_blue : R.drawable.ic_bld6_red));
                    break;
                default:
            }
            switch (car1.getBuildingTask()) {
                case 0:
                    lgcb_iv_car1_task_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                    break;
                case 1:
                    lgcb_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld1_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld1_blue : R.drawable.ic_bld1_red));
                    break;
                case 2:
                    lgcb_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld2_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld2_blue : R.drawable.ic_bld2_red));
                    break;
                case 3:
                    lgcb_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld3_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld3_blue : R.drawable.ic_bld3_red));
                    break;
                case 4:
                    lgcb_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld4_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld4_blue : R.drawable.ic_bld4_red));
                    break;
                case 5:
                    lgcb_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld5_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld5_blue : R.drawable.ic_bld5_red));
                    break;
                case 6:
                    lgcb_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_bld6_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_bld6_blue : R.drawable.ic_bld6_red));
                    break;
                default:
            }
            

            Bitmap car2CarBitmap = car2.isFree() ? car2.getCarPicture() : car2.isRepairing() ? car2.getCarPictureRepairing() : car2.isDefencing() ? car2.getCarPictureDefencing() : car2.getCarPicture() ;
            if (car2CarBitmap != null) lgcb_ib_car2.setImageBitmap(car2CarBitmap);
            lgcb_ib_car2.setImageBitmap(car2.getCarPicture());
            String car2textRepair = car2.isRepairing() ? "" + car2.getTimeStringToEndRepairing() : "";
            lgcb_tv_car2_repair.setVisibility(car2textRepair.equals("") ? View.INVISIBLE : View.VISIBLE);
            lgcb_tv_car2_repair.setText(car2textRepair);
            lgcb_iv_car2_building_icon.setVisibility(car2.getBuilding() >= 0 ? View.VISIBLE : View.INVISIBLE);
            lgcb_iv_car2_task_icon.setVisibility(car2.getBuildingTask() > 0 ? View.VISIBLE : View.INVISIBLE);
            switch (car2.getBuilding()) {
                case 0:
                    lgcb_iv_car2_building_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                    break;
                case 1:
                    lgcb_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld1_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld1_blue : R.drawable.ic_bld1_red));
                    break;
                case 2:
                    lgcb_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld2_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld2_blue : R.drawable.ic_bld2_red));
                    break;
                case 3:
                    lgcb_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld3_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld3_blue : R.drawable.ic_bld3_red));
                    break;
                case 4:
                    lgcb_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld4_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld4_blue : R.drawable.ic_bld4_red));
                    break;
                case 5:
                    lgcb_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld5_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld5_blue : R.drawable.ic_bld5_red));
                    break;
                case 6:
                    lgcb_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld6_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld6_blue : R.drawable.ic_bld6_red));
                    break;
                default:
            }
            switch (car2.getBuildingTask()) {
                case 0:
                    lgcb_iv_car2_task_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                    break;
                case 1:
                    lgcb_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld1_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld1_blue : R.drawable.ic_bld1_red));
                    break;
                case 2:
                    lgcb_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld2_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld2_blue : R.drawable.ic_bld2_red));
                    break;
                case 3:
                    lgcb_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld3_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld3_blue : R.drawable.ic_bld3_red));
                    break;
                case 4:
                    lgcb_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld4_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld4_blue : R.drawable.ic_bld4_red));
                    break;
                case 5:
                    lgcb_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld5_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld5_blue : R.drawable.ic_bld5_red));
                    break;
                case 6:
                    lgcb_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_bld6_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_bld6_blue : R.drawable.ic_bld6_red));
                    break;
                default:
            }

            Bitmap car3CarBitmap = car3.isFree() ? car3.getCarPicture() : car3.isRepairing() ? car3.getCarPictureRepairing() : car3.isDefencing() ? car3.getCarPictureDefencing() : car3.getCarPicture() ;
            if (car3CarBitmap != null) lgcb_ib_car3.setImageBitmap(car3CarBitmap);
            lgcb_ib_car3.setImageBitmap(car3.getCarPicture());
            String car3textRepair = car3.isRepairing() ? "" + car3.getTimeStringToEndRepairing() : "";
            lgcb_tv_car3_repair.setVisibility(car3textRepair.equals("") ? View.INVISIBLE : View.VISIBLE);
            lgcb_tv_car3_repair.setText(car3textRepair);
            lgcb_iv_car3_building_icon.setVisibility(car3.getBuilding() >= 0 ? View.VISIBLE : View.INVISIBLE);
            lgcb_iv_car3_task_icon.setVisibility(car3.getBuildingTask() > 0 ? View.VISIBLE : View.INVISIBLE);
            switch (car3.getBuilding()) {
                case 0:
                    lgcb_iv_car3_building_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                    break;
                case 1:
                    lgcb_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld1_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld1_blue : R.drawable.ic_bld1_red));
                    break;
                case 2:
                    lgcb_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld2_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld2_blue : R.drawable.ic_bld2_red));
                    break;
                case 3:
                    lgcb_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld3_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld3_blue : R.drawable.ic_bld3_red));
                    break;
                case 4:
                    lgcb_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld4_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld4_blue : R.drawable.ic_bld4_red));
                    break;
                case 5:
                    lgcb_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld5_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld5_blue : R.drawable.ic_bld5_red));
                    break;
                case 6:
                    lgcb_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld6_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld6_blue : R.drawable.ic_bld6_red));
                    break;
                default:
            }
            switch (car3.getBuildingTask()) {
                case 0:
                    lgcb_iv_car3_task_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                    break;
                case 1:
                    lgcb_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld1_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld1_blue : R.drawable.ic_bld1_red));
                    break;
                case 2:
                    lgcb_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld2_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld2_blue : R.drawable.ic_bld2_red));
                    break;
                case 3:
                    lgcb_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld3_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld3_blue : R.drawable.ic_bld3_red));
                    break;
                case 4:
                    lgcb_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld4_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld4_blue : R.drawable.ic_bld4_red));
                    break;
                case 5:
                    lgcb_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld5_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld5_blue : R.drawable.ic_bld5_red));
                    break;
                case 6:
                    lgcb_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_bld6_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_bld6_blue : R.drawable.ic_bld6_red));
                    break;
                default:
            }

        } catch (Exception ignored) {
        }

    }

    public void readPreferences() {
        String logMsgPref = "readPreferences: ";
        Log.i(TAG, logMsgPref + "start");

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);

        pathToScreenshotDir = sharedPreferences.getString(getString(R.string.pref_screenshot_folder),sharedPreferences.getString(getString(R.string.pref_def_screenshot_folder),""));
        pathToDataDir = sharedPreferences.getString(getString(R.string.pref_data_folder),sharedPreferences.getString(getString(R.string.pref_def_data_folder),""));
        pathToWhatsappDir = sharedPreferences.getString(getString(R.string.pref_whatsapp_folder),sharedPreferences.getString(getString(R.string.pref_def_whatsapp_folder),""));
        pathToTelegramDir = sharedPreferences.getString(getString(R.string.pref_telegram_folder),sharedPreferences.getString(getString(R.string.pref_def_telegram_folder),""));
        isListenToNewFileInFolder = sharedPreferences.getBoolean(getString(R.string.pref_listen_last_file),sharedPreferences.getBoolean(getString(R.string.pref_def_listen_last_file),false));
        isListenDataFolder = sharedPreferences.getBoolean(getString(R.string.pref_listen_data_folder),sharedPreferences.getBoolean(getString(R.string.pref_def_listen_data_folder),false));
        isListenWhatsappFolder = sharedPreferences.getBoolean(getString(R.string.pref_listen_whatsapp_folder),sharedPreferences.getBoolean(getString(R.string.pref_def_listen_whatsapp_folder),false));
        isListenTelegramFolder = sharedPreferences.getBoolean(getString(R.string.pref_listen_telegram_folder),sharedPreferences.getBoolean(getString(R.string.pref_def_listen_telegram_folder),false));
        isDebugMode = sharedPreferences.getBoolean(getString(R.string.pref_debug_mode),sharedPreferences.getBoolean(getString(R.string.pref_def_debug_mode),false));
        calibrateX = sharedPreferences.getInt(getString(R.string.pref_calibrate_x),sharedPreferences.getInt(getString(R.string.pref_def_calibrate_x),0));
        calibrateY = sharedPreferences.getInt(getString(R.string.pref_calibrate_y),sharedPreferences.getInt(getString(R.string.pref_def_calibrate_y),0));

    }



    private File getLastFileInScreenshotFolder(String pathToFolder) {

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
                if (LastModified.getLastModified(file).getTime() > maxLastModified) {    // если дата создания файла из листа больше максимальной
                    maxLastModified = LastModified.getLastModified(file).getTime();      // максимальная дата = дате файла из листа
                    temp = file;    // временный файл равен файлу из листа
                }
            }

            if (temp != null) { // если найден самый свежий файл
                if (temp.exists()) {
                    if (mainCCAGame != null) {
                        if (temp.lastModified() < mainCCAGame.getDateScreenshot().getTime()) {
                            temp = null;
                        }
                    }
                }
            }

            if (temp != null) { // если найден самый свежий файл
                fileLastScreenshot = temp;
                if (!temp.equals(fileLastInFolderScreenshot)) {   // если найденный файл не совпадает с раенее найденным "последним файлом"
                    CityCalc tmpCityCalc = new CityCalc(temp, calibrateX, calibrateY, mainUserNIC, mainUserUID, mainTeamID);
                    if (!tmpCityCalc.getCityCalcType().equals(CityCalcType.ERROR)) {
                        fileLastInFolderScreenshot = temp;    // последний найденный файл - текущий найденный
                    } else {
                        if (fileLastInFolderScreenshot == null) {
                            temp = lastScreenshot;
                            if (!temp.exists()) temp = null;
                        } else {
                            temp = null;
                        }
                    }

                }
            }
        }

//        if (temp == null && lastScreenshot.exists()) {
//            temp = lastScreenshot;
//        }

        return temp;

    }

    private File getLastFileInDataFolder(String pathToFolder) {

        String logMsgPref = "getLastFileInDataFolder: ";

        File temp = null;           // временный файл
        File dir = new File(pathToFolder); // папка
        File[] files = dir.listFiles(new FilenameFilter() { // присок файлов в папке по фильтру
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".citycalcteamgame") || name.toLowerCase().endsWith(".citycalccars");  // фильтр по citycalcteamgame и citycalccars
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
                if (LastModified.getLastModified(file).getTime() > maxLastModified) {    // если дата создания файла из листа больше максимальной
                    maxLastModified = LastModified.getLastModified(file).getTime();      // максимальная дата = дате файла из листа
                    temp = file;    // временный файл равен файлу из листа
                }
            }

            if (temp != null) { // если найден самый свежий файл
                fileLastData = temp;
                if (!temp.equals(fileLastInFolderData)) {   // если найденный файл не совпадает с раенее найденным "последним файлом"
                    fileLastInFolderData = temp;
                }
            }
        }

        return temp;

    }

    private File getLastFileInWhatsappFolder(String pathToFolder) {

        String logMsgPref = "getLastFileInDataFolder: ";

        File temp = null;           // временный файл
        File dir = new File(pathToFolder); // папка
        File[] files = dir.listFiles(new FilenameFilter() { // присок файлов в папке по фильтру
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".citycalcteamgame") || name.toLowerCase().endsWith(".citycalccars");  // фильтр по citycalcteamgame и citycalccars
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
                if (LastModified.getLastModified(file).getTime() > maxLastModified) {    // если дата создания файла из листа больше максимальной
                    maxLastModified = LastModified.getLastModified(file).getTime();      // максимальная дата = дате файла из листа
                    temp = file;    // временный файл равен файлу из листа
                }
            }

            if (temp != null) { // если найден самый свежий файл
                fileLastWhatsapp = temp;
                if (!temp.equals(fileLastInFolderWhatsapp)) {   // если найденный файл не совпадает с раенее найденным "последним файлом"
                    fileLastInFolderWhatsapp = temp;
                }
            }
        }

        return temp;

    }


    private File getLastFileInTelegramFolder(String pathToFolder) {

        String logMsgPref = "getLastFileInDataFolder: ";

        File temp = null;           // временный файл
        File dir = new File(pathToFolder); // папка
        File[] files = dir.listFiles(new FilenameFilter() { // присок файлов в папке по фильтру
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".citycalcteamgame") || name.toLowerCase().endsWith(".citycalccars");  // фильтр по citycalcteamgame и citycalccars
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
                if (LastModified.getLastModified(file).getTime() > maxLastModified) {    // если дата создания файла из листа больше максимальной
                    maxLastModified = LastModified.getLastModified(file).getTime();      // максимальная дата = дате файла из листа
                    temp = file;    // временный файл равен файлу из листа
                }
            }

            if (temp != null) { // если найден самый свежий файл
                fileLastTelegram = temp;
                if (!temp.equals(fileLastInFolderTelegram)) {   // если найденный файл не совпадает с раенее найденным "последним файлом"
                    fileLastInFolderTelegram = temp;
                }
            }
        }

        return temp;

    }
    
    /**
     * Создание канала нотификации
     */
//    public static void createChannelIfNeeded(NotificationManager manager) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
//            manager.createNotificationChannel(notificationChannel);
//        }
//    }

    /**
     * Возврат в текущую активность из какой-то другой активности
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String logMsgPref = "onActivityResult: ";

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_SCREENSHOT) {
            if (resultCode==RESULT_OK) {
                intentOSS = new Intent(this, OverScreenService.class)
                        .putExtra(OverScreenService.EXTRA_RESULT_CODE, resultCode)
                        .putExtra(OverScreenService.EXTRA_RESULT_INTENT, data);
                bindService(intentOSS, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        }

        if(!Settings.canDrawOverlays(this)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION);
        }

        // если произошел возврат со страницы настрок - обновляем контролы в текущей активности
        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY) {
            readPreferences();
            fileGameScreenshotPrevious = null;
            ga_sw_listen_new_file.setChecked(isListenToNewFileInFolder);
            if (fileGameScreenshot != null) {

                CityCalc tmpCityCalc = new CityCalc(fileGameScreenshot, calibrateX, calibrateY, mainUserNIC, mainUserUID, mainTeamID);
                if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                    prevCityCalc = mainCityCalc.getClone();
                    prevCCAGame = (CCAGame)prevCityCalc.getMapAreas().get(Area.CITY);
                    mainCityCalc = new CityCalc(tmpCityCalc, false);
                    mainCCAGame = (CCAGame)mainCityCalc.getMapAreas().get(Area.CITY);
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


        try {
            // пунты меню "Настройка" и "Открыть скриншот" видны в любом случае
            menu_main_open_settings.setVisible(true);
            menu_main_open_screenshot.setVisible(true);

            menu_main_team_game_load.setVisible(isDebugMode);
            menu_main_user_cars_load.setVisible(isDebugMode);

            ga_pb_progress.setVisibility(View.VISIBLE);


            String userText = "";
            if (fbUser == null) { // юзер не залогинился

                // виден пункт меню "Логин", остальные скрываем
                menu_main_login.setVisible(true);
                menu_main_logout.setVisible(false);
                menu_main_user_account.setVisible(false);
                menu_main_email_verification_send.setVisible(false);
                menu_main_email_verification_check.setVisible(false);
                menu_main_team_work.setVisible(false);
                menu_main_team_manage.setVisible(false);
                menu_main_team_create.setVisible(false);
                menu_main_team_leave.setVisible(false);
                menu_main_team_find.setVisible(false);
                menu_main_team_game_share.setVisible(false);
                menu_main_user_cars_share.setVisible(false);
                menu_main_state_share.setVisible(false);

                // обновляем инфо юзера
                userText = "Login, please.";
                ga_tv_user.setText(userText);

                ga_pb_progress.setVisibility(View.INVISIBLE);

            } else { // юзер залогинился

                // обновляем инфо юзера
                userText = fbUser.getDisplayName();
                ga_tv_user.setText(userText);

                mainUserUID = fbUser.getUid();

                // пункт меню "Логин" скрываем, "Логаут" - показываем
                menu_main_login.setVisible(false);
                menu_main_logout.setVisible(true);

                // проверяем, подтвержден ли емейл
                boolean isVerified = fbUser.isEmailVerified();

                if (!isVerified) { // почта не подтверждена

                    // обновляем инфо юзера
                    userText = userText + " " + getString(R.string.not_verified);
                    ga_tv_user.setText(userText);

                    // показываем пунты меню "Выслать письмо..." и "Обновить...", остальные скрываем
                    menu_main_user_account.setVisible(false);
                    menu_main_email_verification_send.setVisible(true);
                    menu_main_email_verification_check.setVisible(true);
                    menu_main_team_work.setVisible(false);
                    menu_main_team_manage.setVisible(false);
                    menu_main_team_create.setVisible(false);
                    menu_main_team_leave.setVisible(false);
                    menu_main_team_find.setVisible(false);
                    menu_main_team_game_share.setVisible(false);
                    menu_main_user_cars_share.setVisible(false);
                    menu_main_state_share.setVisible(false);

                    ga_pb_progress.setVisibility(View.INVISIBLE);

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

                                    mainTeamID = mainDbUser.getTeamID();

                                    if (!userHaveTeam) { // юзер не состоит в команде

                                        // обновляем инфо юзера
                                        String userText = fbUser.getDisplayName()  + " " + getString(R.string.no_team);
                                        ga_tv_user.setText(userText);

                                        // выводим пункт "Создать банду", скрываем пункты "Банда", "Покинуть банду" и "Найти банду"
                                        menu_main_team_work.setVisible(false);
                                        menu_main_team_manage.setVisible(false);
                                        menu_main_team_create.setVisible(true);
                                        menu_main_team_leave.setVisible(false);
                                        menu_main_team_find.setVisible(true);
                                        menu_main_team_game_share.setVisible(false);
                                        menu_main_user_cars_share.setVisible(false);
                                        menu_main_state_share.setVisible(false);

                                        ga_pb_progress.setVisibility(View.INVISIBLE);

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

                                                                mainUserNIC = mainDbTeamUser.getUserNIC();

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
                                                            menu_main_team_work.setVisible(true);
                                                            menu_main_team_manage.setVisible(userRole.equals(UserRole.LEADER));
                                                            menu_main_team_create.setVisible(false);
                                                            menu_main_team_leave.setVisible(true);
                                                            menu_main_team_find.setVisible(false);
                                                            menu_main_team_game_share.setVisible(true);
                                                            menu_main_user_cars_share.setVisible(true);
                                                            menu_main_state_share.setVisible(true);

                                                            ga_pb_progress.setVisibility(View.INVISIBLE);

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
                                                                            if (mainCCAGame != null) {
                                                                                DbTeamGame dbTeamGame = new DbTeamGame(documentSnapshot);                                   // считываем тимГейм из базы
                                                                                if (dbTeamGame.getDateScreenshot().getTime() > mainCCAGame.getDateScreenshot().getTime()) { // если в базе более свежий скриншот, чем в локальной игре

                                                                                    prevCCAGame = mainCCAGame.getClone(prevCityCalc);
                                                                                    mainCCAGame.updateFromDb(dbTeamGame);                                                       // обновляем локальную игру инфой из базы
                                                                                    Forecaster forecaster = new Forecaster(prevCCAGame, mainCCAGame);
                                                                                    // выводим тост и обновляем контролы в активити
                                                                                    Toast.makeText(GameActivity.this, getString(R.string.info_game_from_server), Toast.LENGTH_LONG).show();
                                                                                    loadDataToViews(true);

                                                                                }
                                                                            }
                                                                        }

                                                                    } else {
                                                                        // запись в базе отсутствует
                                                                        Log.d(TAG, "Current game data: null");
                                                                    }
                                                                }
                                                            });

                                                            for (int carSlot = 1; carSlot <= 3; carSlot++) {

                                                                // "слушаем" запись о carSlot-й машине
                                                                final DocumentReference docRefCar1 = fbDb.collection("users").document(fbUser.getUid()).collection("userCars").document("car"+carSlot);
                                                                int finalCarSlot = carSlot;
                                                                docRefCar1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                                        if (e != null) {
                                                                            // произошла ошибка "слушателя"
                                                                            Log.w(TAG, "Listen car" + finalCarSlot + " failed.", e);
                                                                            return;
                                                                        }
                                                                        if (documentSnapshot != null && documentSnapshot.exists()) {
                                                                            // "слушатель" отработал и вернул документ
                                                                            Log.d(TAG, "Current car" + finalCarSlot + " data: " + documentSnapshot.getData());

                                                                            if (mainCityCalc != null) {                                         // если текущая игра есть
                                                                                Car car = Car.loadCar(finalCarSlot);                                      // берем из списка finalCarSlot-ю машину
                                                                                DbCar dbCar = new DbCar(documentSnapshot);                      // считываем finalCarSlot-ю машину из базы
                                                                                if (car.getBuildingTask() != dbCar.getCarBuildingTask()) {  // если в базе изменилась задача для машины

                                                                                    car.setBuildingTask(dbCar.getCarBuildingTask());        // устанавливаем новую задачу для локальной машины
                                                                                    // получаем правильную картинку здания для нового задания
                                                                                    CCAGame ccaGame = (CCAGame)mainCityCalc.getMapAreas().get(Area.CITY);
                                                                                    Bitmap taskBitmap = null;
                                                                                    if (car.getBuildingTask() == 1 && ccaGame.getBuildings()[car.getBuildingTask()-1].isPresent()) {
                                                                                        taskBitmap = mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                                                                                    } else if (car.getBuildingTask() == 2 && ccaGame.getBuildings()[car.getBuildingTask()-1].isPresent()) {
                                                                                        taskBitmap = mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                                                                                    } else if (car.getBuildingTask() == 3 && ccaGame.getBuildings()[car.getBuildingTask()-1].isPresent()) {
                                                                                        taskBitmap = mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                                                                                    } else if (car.getBuildingTask() == 4 && ccaGame.getBuildings()[car.getBuildingTask()-1].isPresent()) {
                                                                                        taskBitmap = mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                                                                                    } else if (car.getBuildingTask() == 5 && ccaGame.getBuildings()[car.getBuildingTask()-1].isPresent()) {
                                                                                        taskBitmap = mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                                                                                    } else if (car.getBuildingTask() == 6 && ccaGame.getBuildings()[car.getBuildingTask()-1].isPresent()) {
                                                                                        taskBitmap = mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                                                                                    }
                                                                                    car.setTaskPicture(taskBitmap); // обновляем картинку здания задания для локальной машины
                                                                                    car.save();                     // сохраняем локальную машину
                                                                                    setDataToCarsViews();           // обновляем машины в активити

                                                                                    if (car.getBuildingTask() > 0) {
                                                                                        Intent intent = new Intent(GlobalApplication.getAppContext(), CityCalcService.class);
                                                                                        intent.setAction("New task " + car.getName());
                                                                                        intent.putExtra("message", "Машинка " + car.getName() + " получила новое задание: занять здание №" + car.getBuildingTask());
                                                                                        intent.putExtra("car_number", car.getSlot());

                                                                                        PendingIntent pendingIntent = PendingIntent.getService(GlobalApplication.getAppContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                                                                                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                                                                        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pendingIntent);
                                                                                    }

                                                                                }
                                                                            }

                                                                        } else {
                                                                            Log.d(TAG, "Current car" + finalCarSlot + " data: null");
                                                                        }
                                                                    }
                                                                });


                                                            }



                                                        } else {
                                                            // запрос обработался неудачно
                                                            Log.e(TAG, "Error getting documents: ", task.getException());
                                                            // выводим пункт "Создать банду", скрываем пункты "Банда", "Покинуть банду" и "Найти банду"
                                                            menu_main_team_work.setVisible(false);
                                                            menu_main_team_manage.setVisible(false);
                                                            menu_main_team_create.setVisible(true);
                                                            menu_main_team_leave.setVisible(false);
                                                            menu_main_team_find.setVisible(true);
                                                            menu_main_team_game_share.setVisible(false);
                                                            menu_main_user_cars_share.setVisible(false);
                                                            menu_main_state_share.setVisible(false);

                                                            ga_pb_progress.setVisibility(View.INVISIBLE);

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
                                            menu_main_team_work.setVisible(false);
                                            menu_main_team_manage.setVisible(false);
                                            menu_main_team_create.setVisible(true);
                                            menu_main_team_leave.setVisible(false);
                                            menu_main_team_find.setVisible(true);
                                            menu_main_team_game_share.setVisible(false);
                                            menu_main_user_cars_share.setVisible(false);
                                            menu_main_state_share.setVisible(false);

                                        }
                                    });

                                    ga_pb_progress.setVisibility(View.INVISIBLE);

                                }

                            }
                        }
                    });


                } // if (!isVerified)
            } //if (fbUser == null)
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        menu_main_team_work = mainMenu.findItem(R.id.menu_main_team_work);
        menu_main_team_manage = mainMenu.findItem(R.id.menu_main_team_manage);
        menu_main_team_create = mainMenu.findItem(R.id.menu_main_team_create);
        menu_main_team_leave = mainMenu.findItem(R.id.menu_main_team_leave);
        menu_main_team_find = mainMenu.findItem(R.id.menu_main_team_find);
        menu_main_team_game_load = mainMenu.findItem(R.id.menu_main_team_game_load);
        menu_main_team_game_share = mainMenu.findItem(R.id.menu_main_team_game_share);
        menu_main_user_cars_load = mainMenu.findItem(R.id.menu_main_user_cars_load);
        menu_main_user_cars_share = mainMenu.findItem(R.id.menu_main_user_cars_share);
        menu_main_state_share = mainMenu.findItem(R.id.menu_main_state_share);
        menu_main_take_screenshot = mainMenu.findItem(R.id.menu_main_take_screenshot);

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
            case R.id.menu_main_team_work :
                doMenuTeamWork();
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
            case R.id.menu_main_team_game_load :
                doMenuTeamGameLoad();
                return true;
            case R.id.menu_main_team_game_share :
                doMenuTeamGameShare();
                return true;
            case R.id.menu_main_user_cars_load :
                doMenuUserCarsLoad();
                return true;
            case R.id.menu_main_user_cars_share :
                doMenuUserCarsShare();
                return true;
            case R.id.menu_main_state_share :
                doMenuStateShare();
                return true;
            case R.id.menu_main_take_screenshot :
                doMenuTakeScreenshot();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void doMenuTakeScreenshot() {
        if (overScreenService.overlayLayout.getVisibility() == View.VISIBLE) {
            overScreenService.hideOverlayLayout();
        } else {
            overScreenService.showOverlayLayout();
        }
    }

    private void doMenuTeamGameLoad() {

        String logMsgPref = "doMenuTeamGameLoad: ";

        OpenFileDialog fileDialog = new OpenFileDialog(this, pathToScreenshotDir)   // диалог выбора скриншота по переданному пути
                .setFolderIcon(ContextCompat.getDrawable(this, R.drawable.ic_folder))            // иконка папки
                .setFileIcon(ContextCompat.getDrawable(this, R.drawable.ic_file))                // иконка файла
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {

                        if (mainDbTeamUser != null) {

                            DbTeamGame loadedDbTeamGame = DbTeamGame.load(fileName, mainDbTeamUser.getTeamID());
                            if (loadedDbTeamGame != null) {
                                if (mainCityCalc != null) { // если текущая игра есть
                                    if (mainCCAGame != null) {
//                                    if (loadedDbTeamGame.getDateScreenshot().getTime() > mainCCAGame.getDateScreenshot().getTime()) { // если в базе более свежий скриншот, чем в локальной игре

                                        if (loadedDbTeamGame.getBytesScreenshot() != null) {

                                            try {
                                                String fileNameScreenshot = GlobalApplication.pathToCATScalcFolder + "/teamGameScreenshot";
                                                OutputStream fOut = null;
                                                File file = new File(fileNameScreenshot);
                                                Bitmap bitmap = BitmapFactory.decodeByteArray(loadedDbTeamGame.getBytesScreenshot(), 0, loadedDbTeamGame.getBytesScreenshot().length);
                                                fOut = new FileOutputStream(file);
                                                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                                                fOut.flush();
                                                fOut.close();
                                                File teamGameScreenshot = new File(fileNameScreenshot);
                                                // устанавливаем у скачанного файла правильный ластмодифай
                                                LastModified.setLastModified(teamGameScreenshot, loadedDbTeamGame.getDateScreenshot());
                                                fileLastScreenshot = teamGameScreenshot;
                                                CityCalc tmpCityCalc = new CityCalc(teamGameScreenshot, loadedDbTeamGame.getCalibrateX(), loadedDbTeamGame.getCalibrateY(), loadedDbTeamGame.getUserNIC(), mainUserUID, mainTeamID);
                                                if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                                                    fileGameScreenshot = teamGameScreenshot;   // текущий скриншот = последнему файлу в папке
                                                    Toast.makeText(GameActivity.this, getString(R.string.screen_from_server), Toast.LENGTH_LONG).show();

                                                    prevCityCalc = mainCityCalc.getClone();
                                                    prevCCAGame = (CCAGame)prevCityCalc.getMapAreas().get(Area.CITY);

                                                    mainCityCalc = new CityCalc(tmpCityCalc, false);
                                                    mainCCAGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);

                                                    Forecaster forecaster = new Forecaster(prevCCAGame, mainCCAGame);

                                                    Toast.makeText(GameActivity.this, getString(R.string.info_game_from_file), Toast.LENGTH_LONG).show();
                                                    loadDataToViews(true);
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

//                                        mainCCAGame.updateFromDb(loadedDbTeamGame);                                                       // обновляем локальную игру инфой из базы
//                                        // выводим тост и обновляем контролы в активити
//                                        Toast.makeText(GameActivity.this, getString(R.string.info_game_from_file), Toast.LENGTH_LONG).show();
//                                        loadDataToViews(true);
//                                    }
                                    }
                                }

                            }


                        }


                    }
                });
        fileDialog.show();

    }

    private void doMenuUserCarsLoad() {

        String logMsgPref = "doMenuTeamGameLoad: ";

        OpenFileDialog fileDialog = new OpenFileDialog(this, pathToScreenshotDir)   // диалог выбора скриншота по переданному пути
                .setFolderIcon(ContextCompat.getDrawable(this, R.drawable.ic_folder))            // иконка папки
                .setFileIcon(ContextCompat.getDrawable(this, R.drawable.ic_file))                // иконка файла
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {

                        if (mainDbTeamUser != null) {

                            List<Car> listCars = CarList.loadListFromFile(fileName);
                            if (listCars != null) {
                                if (listCars.size() == 3) {
                                    String userUID = listCars.get(0).getUserUID();
                                    Car car1 = listCars.get(0);
                                    car1.save(userUID);
                                    Car car2 = listCars.get(1);
                                    car2.save(userUID);
                                    Car car3 = listCars.get(2);
                                    car3.save(userUID);
//                                    Car.saveList(listCars, userUID);
                                }
                            }
                        }

                    }
                });
        fileDialog.show();

    }


    private void doMenuTeamGameShare() {

        if (mainCityCalc != null) { // если текущая игра есть
            if (mainCCAGame != null) {
                DbTeamGame dbTeamGame = new DbTeamGame(mainCCAGame);
                dbTeamGame.setUserNIC(mainUserNIC);
                dbTeamGame.setUserUID(mainUserUID);
                dbTeamGame.setTeamID(mainTeamID);
                String fileName = dbTeamGame.save(GlobalApplication.pathToCATScalcFolder);
                if (fileName != null) {

                    Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                    File sharedFile = new File(fileName);

                    if(sharedFile.exists()) {
                        intentShareFile.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri fileURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, sharedFile);

                        intentShareFile.setType("*/*");
                        intentShareFile.putExtra(Intent.EXTRA_STREAM, fileURI);

                        String text = ga_tv_status.getText().toString();

                        intentShareFile.putExtra(Intent.EXTRA_SUBJECT, text);
                        intentShareFile.putExtra(Intent.EXTRA_TEXT, text);

                        startActivity(Intent.createChooser(intentShareFile, text));
                    }

                }
            }
        }

    }


    private void doMenuUserCarsShare() {

        if (mainCityCalc != null) { // если текущая игра есть
            if (mainCCAGame != null) {

//                String fileNameCars = Car.pathToFile;
                String fileName = GlobalApplication.pathToCATScalcFolder + "/cars_" + UUID.randomUUID().toString() + ".citycalccars";
                List<Car> list = new ArrayList<>();
                Car car1 = Car.loadCar(1);
                car1.setUserUID(fbUser.getUid());
                list.add(car1);

                Car car2 = Car.loadCar(2);
                car2.setUserUID(fbUser.getUid());
                list.add(car2);

                Car car3 = Car.loadCar(3);
                car3.setUserUID(fbUser.getUid());
                list.add(car3);

                CarList.saveList(list, fbUser.getUid(), fileName);

//                String fileName = pathToCATScalcFolder + "/cars.citycalccars";
//                Utils.copyFile(fileNameCars, fileName);

                Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                File sharedFile = new File(fileName);

                if(sharedFile.exists()) {
                    intentShareFile.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri fileURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, sharedFile);

                    intentShareFile.setType("*/*");
                    intentShareFile.putExtra(Intent.EXTRA_STREAM, fileURI);

                    String text = ga_tv_status.getText().toString();

                    intentShareFile.putExtra(Intent.EXTRA_SUBJECT, text);
                    intentShareFile.putExtra(Intent.EXTRA_TEXT, text);

                    startActivity(Intent.createChooser(intentShareFile, text));
                }

            }
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
        ManageTeamActivity.dbTeam = mainDbTeam;
        Intent intent = new Intent(this, ManageTeamActivity.class);
        startActivityForResult(intent, 0);
    }

    private void doMenuTeamWork() {
        WorkTeamActivity.dbTeam = mainDbTeam;
        Intent intent = new Intent(this, WorkTeamActivity.class);
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

    private void doMenuStateShare() {

//        ga_rl_game.setDrawingCacheEnabled(true);
//        ga_rl_game.buildDrawingCache();
//        Bitmap bm = ga_rl_game.getDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(ga_in_game_group.getMeasuredWidth(),
                ga_in_game_group.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        //Create a canvas with the specified bitmap to draw into
        Canvas c = new Canvas(bitmap);

//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setStyle(Paint.Style.FILL);
//        c.drawPaint(paint);

        //Render this view (and all of its children) to the given Canvas
        ga_in_game_group.draw(c);


        try (FileOutputStream out = new FileOutputStream(getApplicationContext().getFilesDir().getAbsolutePath() + "/state.jpg")) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File sharedFile = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/state.jpg");

        if(sharedFile.exists()) {

            Intent intentShareFile = new Intent(Intent.ACTION_SEND);
            intentShareFile.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri fileURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, sharedFile);

            intentShareFile.setType("image/png");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, fileURI);

            String text = ga_tv_status.getText().toString();

            intentShareFile.putExtra(Intent.EXTRA_TEXT, text);

            startActivity(Intent.createChooser(intentShareFile, text));
        }

//        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
//        intentShareFile.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intentShareFile.setType("text/plain");
//
//        String text = mainCCAGame.getStatus() + "\n\n" + mainCCAGame.getForecastText();
//        intentShareFile.putExtra(Intent.EXTRA_TEXT, text);
//
//        startActivity(Intent.createChooser(intentShareFile, text));

    }

    private void doMenuOpenSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }

    private void doMenuOpenUserAccount() {
        Intent intent = new Intent(this, EditUserActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, 0);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }

    private void doMenuOpenScreenshot() {

        String logMsgPref = "selectScreenshot: ";
        Log.i(TAG, logMsgPref + "start");

        OpenFileDialog fileDialog = new OpenFileDialog(this, pathToScreenshotDir)   // диалог выбора скриншота по переданному пути
                .setFolderIcon(ContextCompat.getDrawable(this, R.drawable.ic_folder))            // иконка папки
                .setFileIcon(ContextCompat.getDrawable(this, R.drawable.ic_file))                // иконка файла
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {
                        String logMsgPref = "OnSelectedFile: ";
                        File newFile = new File(fileName);
                        fileLastScreenshot = newFile;
                        CityCalc tmpCityCalc = new CityCalc(newFile, calibrateX, calibrateY, mainUserNIC, mainUserUID, mainTeamID);
                        if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                            fileGameScreenshot = newFile; // файл скриншота - возавращенный из диалога
                            if (!fileGameScreenshot.getAbsolutePath().equals(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name))) Utils.copyFile(fileGameScreenshot.getAbsolutePath(), getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name));
                            isListenToNewFileInFolder = false; // снимаем флажок "Следить за файлами в папке"
                            ga_sw_listen_new_file.setChecked(false); // устанавливаем контрол флажка
                            fileLastInFolderScreenshot = null;    // сбрасываем последний файл в папке

                            prevCityCalc = mainCityCalc.getClone();
                            prevCCAGame = (CCAGame)prevCityCalc.getMapAreas().get(Area.CITY);

                            mainCityCalc = new CityCalc(tmpCityCalc, false);
                            mainCCAGame = (CCAGame)mainCityCalc.getMapAreas().get(Area.CITY);
                            loadDataToViews(true);
                        } else if (tmpCityCalc.getCityCalcType().equals(CityCalcType.CAR)) {
                            CityCalc carCityCalc = new CityCalc(tmpCityCalc, false);
                            ((CCACar)carCityCalc.getMapAreas().get(Area.CAR_IN_CITY_INFO)).parseCar();
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
        EditCarActivity.slot = 1;
        Intent intent = new Intent(this, EditCarActivity.class);
        startActivityForResult(intent, 0);
    }

    public void openCar2(View view) {
        EditCarActivity.slot = 2;
        Intent intent = new Intent(this, EditCarActivity.class);
        startActivityForResult(intent, 0);
    }

    public void openCar3(View view) {
        EditCarActivity.slot = 3;
        Intent intent = new Intent(this, EditCarActivity.class);
        startActivityForResult(intent, 0);
    }

    private void startTimer() {

        String logMsgPref = "readPreferences: ";
        Log.i(TAG, logMsgPref + "start");

        if (timer == null) {    // если таймер не запущен
            timer = new Timer();    // запускаем таймер
            timer.schedule(new firstTask(), 3000,3000); // запускаем такс таймера
            timer.schedule(new secondTask(), 10000,60000); // запускаем такс таймера
        }

    }

    public void doOnClickBuilding(int slot) {
        if (mainDbTeamUser != null) {
            if (!mainDbTeamUser.getUserRole().equals("meat")) {
                WorkBuildingActivity.mainSlot = slot;
                Intent intent = new Intent(this, WorkBuildingActivity.class);
                startActivityForResult(intent, 0);
            }
        }
    }

    public void doOnLongClickBuilding(int slot) {
        mainCCAGame.getBuildings()[slot-1].setUseInForecast(!mainCCAGame.getBuildings()[slot-1].isUseInForecast());
        mainCCAGame.doForecast();
        loadDataToViews(false);
    }

    public void doRealtimeScreenshot() {
        TakeRealtimeScreenshot takeRealtimeScreenshot = new TakeRealtimeScreenshot();
        takeRealtimeScreenshot.execute();
    }

    class TakeRealtimeScreenshot extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPostExecute(Integer aInteger) {
            super.onPostExecute(aInteger);

            Forecaster forecaster = new Forecaster(prevCCAGame, mainCCAGame);

            switch (aInteger) {
                case 0:
                    break;
                case 1:
                    Toast.makeText(GameActivity.this, getString(R.string.realtime_screenshot_is_game), Toast.LENGTH_LONG).show();
                    loadDataToViews(true);
                    break;
                case 2:
                    Toast.makeText(GameActivity.this, getString(R.string.realtime_screenshot_is_car), Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(GameActivity.this, getString(R.string.realtime_screenshot_is_error), Toast.LENGTH_LONG).show();
                    break;
                default:
            }

            if (isNeedUpdateCars) {
                isNeedUpdateCars = false;
                setDataToCarsViews();
            }
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            File tmpFileScreenshot = new File(getExternalFilesDir(null), "RealtimeScreenshot.png");
            CityCalc tmpCityCalc = new CityCalc(tmpFileScreenshot, calibrateX, calibrateY, mainUserNIC, mainUserUID, mainTeamID);
            if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                fileGameScreenshot = tmpFileScreenshot;   // текущий скриншот = последнему файлу в папке

                Utils.copyFile(fileGameScreenshot.getAbsolutePath(), getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name));

                prevCityCalc = mainCityCalc.getClone();
                prevCCAGame = (CCAGame) prevCityCalc.getMapAreas().get(Area.CITY);

                mainCityCalc = new CityCalc(tmpCityCalc, true);
                mainCCAGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);

                if (isResumed) isResumed = false;
                return 1;

            } else if (tmpCityCalc.getCityCalcType().equals(CityCalcType.CAR)) {
                if (fileCarScreenshot == null || !fileCarScreenshot.equals(tmpFileScreenshot)) {
                    CityCalc carCityCalc = new CityCalc(tmpCityCalc, true);
                    ((CCACar) carCityCalc.getMapAreas().get(Area.CAR_IN_CITY_INFO)).parseCar();
                    fileCarScreenshot = tmpFileScreenshot;
                    if (isResumed) isResumed = false;
                    return 2;
                }
            } else if (tmpCityCalc.getCityCalcType().equals(CityCalcType.ERROR)) {
                if (isResumed) isResumed = false;
                return 3;
            }

            if (isResumed) isResumed = false;

            return 0;

        }
    }


    class firstTask extends TimerTask {

        @Override
        public void run() {

            if (isListenToNewFileInFolder) {
                if (tlff == null) {
                    tlff = new TaskListenScreenshotFolder();
                    tlff.execute();
                } else {
                    if (!tlff.getStatus().equals(AsyncTask.Status.RUNNING)) {
                        tlff = new TaskListenScreenshotFolder();
                        tlff.execute();
                    }
                }

            }

        }

        class TaskListenScreenshotFolder extends AsyncTask<Void, Void, Integer> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Integer doInBackground(Void... voids) {

                File tmpFileScreenshot = getLastFileInScreenshotFolder(pathToScreenshotDir);    // получаем последний файл из папки
                if (tmpFileScreenshot != null) {  // если он не пустой
                    if ((!tmpFileScreenshot.equals(fileGameScreenshot) && !tmpFileScreenshot.equals(fileCarScreenshot)) || isResumed) {  // если он не равен текущем скриншоту

                        boolean needProceedFile = false;
                        // надо проверить, не является ли текущая игра взятая из скрина на сервере
                        if (mainCityCalc != null) { // если игра есть
                            // если в игре - скрин с сервера
                            if (mainCityCalc.getFileScreenshot().getAbsolutePath().equals(GlobalApplication.pathToCATScalcFolder + "/teamGameScreenshot")) {
                                // если последний скрин из папки - более поздний, чем в игре
                                if (LastModified.getLastModified(tmpFileScreenshot).getTime() > LastModified.getLastModified(mainCityCalc.getFileScreenshot()).getTime()) {
                                    needProceedFile = true;
                                }
                            } else {
                                needProceedFile = true;
                            }
                        } else {
                            needProceedFile = true;
                        }

                        if (needProceedFile) {


                            CityCalc tmpCityCalc = new CityCalc(tmpFileScreenshot, calibrateX, calibrateY, mainUserNIC, mainUserUID, mainTeamID);
                            if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                                fileGameScreenshot = tmpFileScreenshot;   // текущий скриншот = последнему файлу в папке

                                boolean isRealtimeScreenshot = false;
                                if (!fileGameScreenshot.getAbsolutePath().equals(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name))) {
                                    isRealtimeScreenshot = true;
                                    Utils.copyFile(fileGameScreenshot.getAbsolutePath(), getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name));
                                }

                                prevCityCalc = mainCityCalc.getClone();
                                prevCCAGame = (CCAGame)prevCityCalc.getMapAreas().get(Area.CITY);

                                mainCityCalc = new CityCalc(tmpCityCalc, isRealtimeScreenshot);
                                mainCCAGame = (CCAGame)mainCityCalc.getMapAreas().get(Area.CITY);

                                if (isResumed) isResumed = false;
                                return 1;

                            } else if (tmpCityCalc.getCityCalcType().equals(CityCalcType.CAR)) {
                                if (fileCarScreenshot == null || !fileCarScreenshot.equals(tmpFileScreenshot)) {
                                    CityCalc carCityCalc = new CityCalc(tmpCityCalc, true);
                                    ((CCACar)carCityCalc.getMapAreas().get(Area.CAR_IN_CITY_INFO)).parseCar();
                                    fileCarScreenshot = tmpFileScreenshot;
                                }
                            }
                            if (isResumed) isResumed = false;


                        }

                    }
                }



                if (isListenDataFolder && mainDbTeamUser != null) {
                    File tmpFileData = getLastFileInDataFolder(pathToDataDir);    // получаем последний файл из папки
                    if (tmpFileData != null) {  // если он не пустой

                        if (lastDataFile == null || !lastDataFile.equals(tmpFileData)) {
                            lastDataFile = tmpFileData;

                            if (tmpFileData.getAbsolutePath().endsWith(".citycalcteamgame")) {

                                if (mainDbTeamUser != null) {

                                    DbTeamGame loadedDbTeamGame = DbTeamGame.load(tmpFileData, mainDbTeamUser.getTeamID());

                                    // тут файл можно уже удалить
//                                    try {
//                                        tmpFileData.delete();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }

                                    if (loadedDbTeamGame != null) {
                                        if (mainCityCalc != null) { // если текущая игра есть
                                            if (mainCCAGame != null) {
                                                if (loadedDbTeamGame.getDateScreenshot().getTime() > mainCCAGame.getDateScreenshot().getTime()) { // если в базе более свежий скриншот, чем в локальной игре

                                                    if (loadedDbTeamGame.getBytesScreenshot() != null) {

                                                        try {
                                                            String fileNameScreenshot = GlobalApplication.pathToCATScalcFolder + "/teamGameScreenshot";
                                                            OutputStream fOut = null;
                                                            File file = new File(fileNameScreenshot);
                                                            Bitmap bitmap = BitmapFactory.decodeByteArray(loadedDbTeamGame.getBytesScreenshot(), 0, loadedDbTeamGame.getBytesScreenshot().length);
                                                            fOut = new FileOutputStream(file);
                                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                                                            fOut.flush();
                                                            fOut.close();
                                                            File teamGameScreenshot = new File(fileNameScreenshot);
                                                            // устанавливаем у скачанного файла правильный ластмодифай
                                                            LastModified.setLastModified(teamGameScreenshot, loadedDbTeamGame.getDateScreenshot());
                                                            fileLastScreenshot = teamGameScreenshot;
                                                            CityCalc tmpCityCalc = new CityCalc(teamGameScreenshot, loadedDbTeamGame.getCalibrateX(), loadedDbTeamGame.getCalibrateY(), loadedDbTeamGame.getUserNIC(), mainUserUID, mainTeamID);
                                                            if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                                                                fileGameScreenshot = teamGameScreenshot;   // текущий скриншот = последнему файлу в папке

                                                                prevCityCalc = mainCityCalc.getClone();
                                                                prevCCAGame = (CCAGame) prevCityCalc.getMapAreas().get(Area.CITY);

                                                                mainCityCalc = new CityCalc(tmpCityCalc, false);
                                                                mainCCAGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);
                                                                mainCCAGame.setSource(1);
                                                                Utils.copyFile(teamGameScreenshot.getAbsolutePath(), getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name));
                                                                LastModified.setLastModified(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name), loadedDbTeamGame.getDateScreenshot());

                                                                //                                                            Toast.makeText(GameActivity.this, getString(R.string.info_game_from_file), Toast.LENGTH_LONG).show();
                                                                //                                                            loadDataToViews(true);
                                                                return 2;
                                                            }
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }

                                            }
                                        }

                                    }


                                }


                            } else if (tmpFileData.getAbsolutePath().endsWith(".citycalccars")) {

                                if (mainDbTeamUser != null) {
                                    String fileName = tmpFileData.getAbsolutePath();
                                    List<Car> listCars = CarList.loadListFromFile(fileName);
                                    if (listCars != null) {
                                        if (listCars.size() == 3) {
                                            String userUID = listCars.get(0).getUserUID();
                                            Car car1 = listCars.get(0);
                                            car1.save(userUID);
                                            Car car2 = listCars.get(1);
                                            car2.save(userUID);
                                            Car car3 = listCars.get(2);
                                            car3.save(userUID);

//                                            Car.saveList(listCars, userUID);

                                            // тут файл можно уже удалить
//                                            try {
//                                                tmpFileData.delete();
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }

                                        }
                                    }
                                }

                            }


                        }


                    }

                }


                if (isListenWhatsappFolder && mainDbTeamUser != null) {
                    File tmpFileWhatsapp = getLastFileInWhatsappFolder(pathToWhatsappDir);    // получаем последний файл из папки
                    if (tmpFileWhatsapp != null) {  // если он не пустой

                        if (lastWhatsappFile == null || !lastWhatsappFile.equals(tmpFileWhatsapp)) {
                            lastWhatsappFile = tmpFileWhatsapp;

                            if (tmpFileWhatsapp.getAbsolutePath().endsWith(".citycalcteamgame")) {

                                if (mainDbTeamUser != null) {

                                    DbTeamGame loadedDbTeamGame = DbTeamGame.load(tmpFileWhatsapp, mainDbTeamUser.getTeamID());

                                    // тут файл можно уже удалить
                                    try {
                                        tmpFileWhatsapp.delete();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if (loadedDbTeamGame != null) {
                                        if (mainCityCalc != null) { // если текущая игра есть
                                            if (mainCCAGame != null) {
                                                if (loadedDbTeamGame.getDateScreenshot().getTime() > mainCCAGame.getDateScreenshot().getTime()) { // если в базе более свежий скриншот, чем в локальной игре

                                                    if (loadedDbTeamGame.getBytesScreenshot() != null) {

                                                        try {
                                                            String fileNameScreenshot = GlobalApplication.pathToCATScalcFolder + "/teamGameScreenshot";
                                                            OutputStream fOut = null;
                                                            File file = new File(fileNameScreenshot);
                                                            Bitmap bitmap = BitmapFactory.decodeByteArray(loadedDbTeamGame.getBytesScreenshot(), 0, loadedDbTeamGame.getBytesScreenshot().length);
                                                            fOut = new FileOutputStream(file);
                                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                                                            fOut.flush();
                                                            fOut.close();
                                                            File teamGameScreenshot = new File(fileNameScreenshot);
                                                            // устанавливаем у скачанного файла правильный ластмодифай
                                                            LastModified.setLastModified(teamGameScreenshot, loadedDbTeamGame.getDateScreenshot());
                                                            fileLastScreenshot = teamGameScreenshot;
                                                            //                                                            Utils.copyFile(teamGameScreenshot.getAbsolutePath(), getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name));
                                                            //                                                            LastModified.setLastModified(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name), loadedDbTeamGame.getDateScreenshot());
                                                            CityCalc tmpCityCalc = new CityCalc(teamGameScreenshot, loadedDbTeamGame.getCalibrateX(), loadedDbTeamGame.getCalibrateY(), loadedDbTeamGame.getUserNIC(), mainUserUID, mainTeamID);
                                                            if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                                                                fileGameScreenshot = teamGameScreenshot;   // текущий скриншот = последнему файлу в папке

                                                                prevCityCalc = mainCityCalc.getClone();
                                                                prevCCAGame = (CCAGame) prevCityCalc.getMapAreas().get(Area.CITY);

                                                                mainCityCalc = new CityCalc(tmpCityCalc, false);
                                                                mainCCAGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);
                                                                mainCCAGame.setSource(1);
                                                                Utils.copyFile(teamGameScreenshot.getAbsolutePath(), getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name));
                                                                LastModified.setLastModified(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name), loadedDbTeamGame.getDateScreenshot());

                                                                //                                                            Toast.makeText(GameActivity.this, getString(R.string.info_game_from_file), Toast.LENGTH_LONG).show();
                                                                //                                                            loadDataToViews(true);
                                                                return 3;
                                                            }
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }

                                            }
                                        }

                                    }


                                }


                            } else if (tmpFileWhatsapp.getAbsolutePath().endsWith(".citycalccars")) {

                                if (mainDbTeamUser != null) {
                                    String fileName = tmpFileWhatsapp.getAbsolutePath();
                                    List<Car> listCars = CarList.loadListFromFile(fileName);
                                    if (listCars != null) {
                                        if (listCars.size() == 3) {
                                            String userUID = listCars.get(0).getUserUID();
                                            Car car1 = listCars.get(0);
                                            car1.save(userUID);
                                            Car car2 = listCars.get(1);
                                            car2.save(userUID);
                                            Car car3 = listCars.get(2);
                                            car3.save(userUID);
//                                            Car.saveList(listCars, userUID);

                                            // тут файл можно уже удалить
                                            try {
                                                tmpFileWhatsapp.delete();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                }

                            }

                        }

                    }
                }


                if (isListenTelegramFolder && mainDbTeamUser != null) {
                    File tmpFileTelegram = getLastFileInTelegramFolder(pathToTelegramDir);    // получаем последний файл из папки
                    if (tmpFileTelegram != null) {  // если он не пустой

                        if (lastTelegramFile == null || !lastTelegramFile.equals(tmpFileTelegram)) {
                            lastTelegramFile = tmpFileTelegram;

                            if (tmpFileTelegram.getAbsolutePath().endsWith(".citycalcteamgame")) {

                                if (mainDbTeamUser != null) {

                                    DbTeamGame loadedDbTeamGame = DbTeamGame.load(tmpFileTelegram, mainDbTeamUser.getTeamID());

                                    if (loadedDbTeamGame != null) {
                                        if (mainCityCalc != null) { // если текущая игра есть
                                            if (mainCCAGame != null) {
                                                if (loadedDbTeamGame.getDateScreenshot().getTime() > mainCCAGame.getDateScreenshot().getTime()) { // если в базе более свежий скриншот, чем в локальной игре

                                                    if (loadedDbTeamGame.getBytesScreenshot() != null) {

                                                        try {
                                                            String fileNameScreenshot = GlobalApplication.pathToCATScalcFolder + "/teamGameScreenshot";
                                                            OutputStream fOut = null;
                                                            File file = new File(fileNameScreenshot);
                                                            Bitmap bitmap = BitmapFactory.decodeByteArray(loadedDbTeamGame.getBytesScreenshot(), 0, loadedDbTeamGame.getBytesScreenshot().length);
                                                            fOut = new FileOutputStream(file);
                                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                                                            fOut.flush();
                                                            fOut.close();
                                                            File teamGameScreenshot = new File(fileNameScreenshot);
                                                            // устанавливаем у скачанного файла правильный ластмодифай
                                                            LastModified.setLastModified(teamGameScreenshot, loadedDbTeamGame.getDateScreenshot());
                                                            fileLastScreenshot = teamGameScreenshot;
                                                            CityCalc tmpCityCalc = new CityCalc(teamGameScreenshot, loadedDbTeamGame.getCalibrateX(), loadedDbTeamGame.getCalibrateY(), loadedDbTeamGame.getUserNIC(), mainUserUID, mainTeamID);
                                                            if (tmpCityCalc.getCityCalcType().equals(CityCalcType.GAME)) {
                                                                fileGameScreenshot = teamGameScreenshot;   // текущий скриншот = последнему файлу в папке

                                                                prevCityCalc = mainCityCalc.getClone();
                                                                prevCCAGame = (CCAGame) prevCityCalc.getMapAreas().get(Area.CITY);

                                                                mainCityCalc = new CityCalc(tmpCityCalc, false);
                                                                mainCCAGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);
                                                                mainCCAGame.setSource(1);
                                                                Utils.copyFile(teamGameScreenshot.getAbsolutePath(), getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name));
                                                                LastModified.setLastModified(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + getString(R.string.last_screenshot_file_name), loadedDbTeamGame.getDateScreenshot());

                                                                //                                                            Toast.makeText(GameActivity.this, getString(R.string.info_game_from_file), Toast.LENGTH_LONG).show();
                                                                //                                                            loadDataToViews(true);
                                                                return 4;
                                                            }
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }

                                            }
                                        }

                                    }


                                }


                            } else if (tmpFileTelegram.getAbsolutePath().endsWith(".citycalccars")) {

                                if (mainDbTeamUser != null) {
                                    String fileName = tmpFileTelegram.getAbsolutePath();
                                    List<Car> listCars = CarList.loadListFromFile(fileName);
                                    if (listCars != null) {
                                        if (listCars.size() == 3) {
                                            String userUID = listCars.get(0).getUserUID();
                                            Car car1 = listCars.get(0);
                                            car1.save(userUID);
                                            Car car2 = listCars.get(1);
                                            car2.save(userUID);
                                            Car car3 = listCars.get(2);
                                            car3.save(userUID);
//                                            Car.saveList(listCars, userUID);
                                        }
                                    }
                                }

                            }

                        }

                    }
                }



                return 0;
            }

            @Override
            protected void onPostExecute(Integer aInteger) {
                super.onPostExecute(aInteger);

                Forecaster forecaster = new Forecaster(prevCCAGame, mainCCAGame);


                switch (aInteger) {
                    case 0:
                        break;
                    case 1:
                        Toast.makeText(GameActivity.this, getString(R.string.info_game_from_file), Toast.LENGTH_LONG).show();
                        loadDataToViews(true);
                        break;
                    case 2:
                        Toast.makeText(GameActivity.this, getString(R.string.info_game_from_data_folder), Toast.LENGTH_LONG).show();
                        loadDataToViews(true);
                        break;
                    case 3:
                        Toast.makeText(GameActivity.this, getString(R.string.info_game_from_whatsapp_folder), Toast.LENGTH_LONG).show();
                        loadDataToViews(true);
                        break;
                    case 4:
                        Toast.makeText(GameActivity.this, getString(R.string.info_game_from_telegram_folder), Toast.LENGTH_LONG).show();
                        loadDataToViews(true);
                        break;
                    default:
                }

                if (isNeedUpdateCars) {
                    isNeedUpdateCars = false;
                    setDataToCarsViews();
                }

            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

        }


    }



    class secondTask extends TimerTask {

        @Override
        public void run() {

            String logMsgPref = "secondTask: ";

            GameActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    String logMsgPref = "secondTask: ";

                    if (mainCCAGame != null) {

                        if (tlff == null) {
                            mainCCAGame.calcWin(true);
                            loadDataToViews(false);
                            setDataToCarsViews();
                        } else {
                            if (!tlff.getStatus().equals(AsyncTask.Status.RUNNING)) {
                                mainCCAGame.calcWin(true);
                                loadDataToViews(false);
                                setDataToCarsViews();
                            }
                        }

                    }

                }
            });
        }
    };


}
