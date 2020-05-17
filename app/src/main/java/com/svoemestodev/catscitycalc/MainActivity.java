package com.svoemestodev.catscitycalc;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * MainActivity
 */

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_SECOND_ACTIVITY = 100; // код реквеста для вызова Настроек
    private static final Date DATE_EXPIRED = new GregorianCalendar(2020, 5, 1).getTime();   // дата окончания жизни программы. После нее она не будет запускаться
    private static final int MY_PERMISSIONS_REQUESTREAD_READ_EXTERNAL_STORAGE = 1;   // код пермишенса (хз зачем)
    private static final int MY_PERMISSIONS_REQUESTREAD_WRITE_EXTERNAL_STORAGE = 2;   // код пермишенса (хз зачем)
    private static final int MY_PERMISSIONS_REQUESTREAD_INTERNET = 3;   // код пермишенса (хз зачем)
    private static final int MY_PERMISSIONS_REQUESTREAD_MULTIPERMISIONS = 4;   // код пермишенса (хз зачем)

    private ImageView ivCity; // кропнутая картинка

    private TextView etInTimeLeft;  // время до конца игры (по скриншоту)
    private TextView etInPlusUs; // прирост очков у нас (по скриншоту)
    private TextView etInPlusThey; // прирост очков у них (по скриншоту)
    private TextView etInTotalUs; // всего очнов у нас (по скриншоту)
    private TextView etInTotalThey; // всего очков у них (по скриншоту)
    private TextView etInInstantVic; // очков до досрочной победы (по скриншоту)

    public Switch swListenNewFiles; // переключатель "Отслеживать новые файлы"
    private TextView tvResult; // результат игры

    public static  File fileScreenshot;    // текущий файл скриншота
    public static  File fileScreenshotPrevious;    // предыдущий файл скриншота
    public static  File fileLastInFolder;    // последний файл в папке

    private Timer timer;                        // таймер
    public static String pathToScreenshotDir = "";    // путь к папке скриншотов
    public static String pathToCATScalcFolder = "";   // путь к папке программы в корне флешки
    public static boolean isListenToNewFileInFolder;  // флаг "Следить за новыми файлами в папке"
    public static boolean isAllFieldsCorrect;         // флаг "Все поля заполнены правильно"
    public static int calibrateX;                              // калибровка
    public static int calibrateY;                              // калибровка

    public static Map<Integer, String> mapStrings = new HashMap<>();
    public static Map<Integer, Integer> mapInts = new HashMap<>();
    public static Map<Integer, Float> mapFloats = new HashMap<>();


    // коэффициэнты кропа картинки
//    public static final float DEFAULT_PREF_CUT_CITY_X1 = -0.565f;
//    public static final float DEFAULT_PREF_CUT_CITY_X2 = +0.565f;
//    public static final float DEFAULT_PREF_CUT_CITY_Y1 = -0.800f;
//    public static final float DEFAULT_PREF_CUT_CITY_Y2 = -0.450f;
//
//    public static final float DEFAULT_PREF_CUT_TOTAL_US_X1 = -0.565f;
//    public static final float DEFAULT_PREF_CUT_TOTAL_US_X2 = -0.180f;
//    public static final float DEFAULT_PREF_CUT_TOTAL_US_Y1 = -0.610f;
//    public static final float DEFAULT_PREF_CUT_TOTAL_US_Y2 = -0.470f;
//
//    public static final float DEFAULT_PREF_CUT_TOTAL_THEY_X1 = +0.180f;
//    public static final float DEFAULT_PREF_CUT_TOTAL_THEY_X2 = +0.565f;
//    public static final float DEFAULT_PREF_CUT_TOTAL_THEY_Y1 = -0.610f;
//    public static final float DEFAULT_PREF_CUT_TOTAL_THEY_Y2 = -0.470f;
//
//    public static final float DEFAULT_PREF_CUT_TOTAL_TIME_X1 = -0.130f;
//    public static final float DEFAULT_PREF_CUT_TOTAL_TIME_X2 = +0.060f;
//    public static final float DEFAULT_PREF_CUT_TOTAL_TIME_Y1 = -0.780f;
//    public static final float DEFAULT_PREF_CUT_TOTAL_TIME_Y2 = -0.660f;
//
//    public static final float DEFAULT_PREF_CUT_EARLY_WIN_X1 = -0.170f;
//    public static final float DEFAULT_PREF_CUT_EARLY_WIN_X2 = +0.170f;
//    public static final float DEFAULT_PREF_CUT_EARLY_WIN_Y1 = -0.570f;
//    public static final float DEFAULT_PREF_CUT_EARLY_WIN_Y2 = -0.450f;

    public static final int DEFAULT_PREF_RGB_TOTAL_US = 0x437AD7;
    public static final int DEFAULT_PREF_RGB_PLUS_US = 0xD3DDF6;
    public static final int DEFAULT_PREF_RGB_TOTAL_THEY = 0xF64F49;
    public static final int DEFAULT_PREF_RGB_PLUS_THEY = 0xFDD5D3;
    public static final int DEFAULT_PREF_RGB_TOTAL_TIME = 0xFFFFFF;
    public static final int DEFAULT_PREF_RGB_EARLY_WIN = 0xFFFFFF;

    public static final int DEFAULT_PREF_RGB_TOTAL_US_THRESHOLD_PLUS = 4;
    public static final int DEFAULT_PREF_RGB_TOTAL_US_THRESHOLD_MINUS = 1;

    public static final int DEFAULT_PREF_RGB_PLUS_US_THRESHOLD_PLUS = 4;
    public static final int DEFAULT_PREF_RGB_PLUS_US_THRESHOLD_MINUS = 13;

    public static final int DEFAULT_PREF_RGB_TOTAL_THEY_THRESHOLD_PLUS = 24;
    public static final int DEFAULT_PREF_RGB_TOTAL_THEY_THRESHOLD_MINUS = 8;

    public static final int DEFAULT_PREF_RGB_PLUS_THEY_THRESHOLD_PLUS = 5;
    public static final int DEFAULT_PREF_RGB_PLUS_THEY_THRESHOLD_MINUS = 5;

    public static final int DEFAULT_PREF_RGB_TOTAL_TIME_THRESHOLD_PLUS = 1;
    public static final int DEFAULT_PREF_RGB_TOTAL_TIME_THRESHOLD_MINUS = 20;

    public static final int DEFAULT_PREF_RGB_EARLY_WIN_THRESHOLD_PLUS = 1;
    public static final int DEFAULT_PREF_RGB_EARLY_WIN_THRESHOLD_MINUS = 20;

    public static int rgb_points_our, rgb_points_our_thp, rgb_points_our_thm;
    public static int rgb_increase_our, rgb_increase_our_thp, rgb_increase_our_thm;
    public static int rgb_points_enemy, rgb_points_enemy_thp, rgb_points_enemy_thm;
    public static int rgb_increase_enemy, rgb_increase_enemy_thp, rgb_increase_enemy_thm;
    public static int rgb_total_time, rgb_total_time_thp, rgb_total_time_thm;
    public static int rgb_early_win, rgb_early_win_thp, rgb_early_win_thm;

    public static float cut_city_x1, cut_city_x2, cut_city_y1, cut_city_y2;
    public static float cut_points_and_increase_our_x1, cut_points_and_increase_our_x2, cut_points_and_increase_our_y1, cut_points_and_increase_our_y2;
    public static float cut_points_and_increase_enemy_x1, cut_points_and_increase_enemy_x2, cut_points_and_increase_enemy_y1, cut_points_and_increase_enemy_y2;
    public static float cut_total_time_x1, cut_total_time_x2, cut_total_time_y1, cut_total_time_y2;
    public static float cut_early_win_x1, cut_early_win_x2, cut_early_win_y1, cut_early_win_y2;
    public static float cut_team_name_our_x1, cut_team_name_our_x2, cut_team_name_our_y1, cut_team_name_our_y2;
    public static float cut_team_name_enemy_x1, cut_team_name_enemy_x2, cut_team_name_enemy_y1, cut_team_name_enemy_y2;
    public static float cut_building_lt_x1, cut_building_lt_x2, cut_building_lt_y1, cut_building_lt_y2;
    public static float cut_building_lt_name_x1, cut_building_lt_name_x2, cut_building_lt_name_y1, cut_building_lt_name_y2;
    public static float cut_building_lt_increase_x1, cut_building_lt_increase_x2, cut_building_lt_increase_y1, cut_building_lt_increase_y2;
    public static float cut_building_lt_slots_x1, cut_building_lt_slots_x2, cut_building_lt_slots_y1, cut_building_lt_slots_y2;
    public static float cut_building_lt_progress_x1, cut_building_lt_progress_x2, cut_building_lt_progress_y1, cut_building_lt_progress_y2;
    public static float cut_building_lc_x1, cut_building_lc_x2, cut_building_lc_y1, cut_building_lc_y2;
    public static float cut_building_lc_name_x1, cut_building_lc_name_x2, cut_building_lc_name_y1, cut_building_lc_name_y2;
    public static float cut_building_lc_increase_x1, cut_building_lc_increase_x2, cut_building_lc_increase_y1, cut_building_lc_increase_y2;
    public static float cut_building_lc_slots_x1, cut_building_lc_slots_x2, cut_building_lc_slots_y1, cut_building_lc_slots_y2;
    public static float cut_building_lc_progress_x1, cut_building_lc_progress_x2, cut_building_lc_progress_y1, cut_building_lc_progress_y2;
    public static float cut_building_lb_x1, cut_building_lb_x2, cut_building_lb_y1, cut_building_lb_y2;
    public static float cut_building_lb_name_x1, cut_building_lb_name_x2, cut_building_lb_name_y1, cut_building_lb_name_y2;
    public static float cut_building_lb_increase_x1, cut_building_lb_increase_x2, cut_building_lb_increase_y1, cut_building_lb_increase_y2;
    public static float cut_building_lb_slots_x1, cut_building_lb_slots_x2, cut_building_lb_slots_y1, cut_building_lb_slots_y2;
    public static float cut_building_lb_progress_x1, cut_building_lb_progress_x2, cut_building_lb_progress_y1, cut_building_lb_progress_y2;
    public static float cut_building_rt_x1, cut_building_rt_x2, cut_building_rt_y1, cut_building_rt_y2;
    public static float cut_building_rt_name_x1, cut_building_rt_name_x2, cut_building_rt_name_y1, cut_building_rt_name_y2;
    public static float cut_building_rt_increase_x1, cut_building_rt_increase_x2, cut_building_rt_increase_y1, cut_building_rt_increase_y2;
    public static float cut_building_rt_slots_x1, cut_building_rt_slots_x2, cut_building_rt_slots_y1, cut_building_rt_slots_y2;
    public static float cut_building_rt_progress_x1, cut_building_rt_progress_x2, cut_building_rt_progress_y1, cut_building_rt_progress_y2;
    public static float cut_building_rc_x1, cut_building_rc_x2, cut_building_rc_y1, cut_building_rc_y2;
    public static float cut_building_rc_name_x1, cut_building_rc_name_x2, cut_building_rc_name_y1, cut_building_rc_name_y2;
    public static float cut_building_rc_increase_x1, cut_building_rc_increase_x2, cut_building_rc_increase_y1, cut_building_rc_increase_y2;
    public static float cut_building_rc_slots_x1, cut_building_rc_slots_x2, cut_building_rc_slots_y1, cut_building_rc_slots_y2;
    public static float cut_building_rc_progress_x1, cut_building_rc_progress_x2, cut_building_rc_progress_y1, cut_building_rc_progress_y2;
    public static float cut_building_rb_x1, cut_building_rb_x2, cut_building_rb_y1, cut_building_rb_y2;
    public static float cut_building_rb_name_x1, cut_building_rb_name_x2, cut_building_rb_name_y1, cut_building_rb_name_y2;
    public static float cut_building_rb_increase_x1, cut_building_rb_increase_x2, cut_building_rb_increase_y1, cut_building_rb_increase_y2;
    public static float cut_building_rb_slots_x1, cut_building_rb_slots_x2, cut_building_rb_slots_y1, cut_building_rb_slots_y2;
    public static float cut_building_rb_progress_x1, cut_building_rb_progress_x2, cut_building_rb_progress_y1, cut_building_rb_progress_y2;

    public static float def_city_x1, def_city_x2, def_city_y1, def_city_y2;
    public static float def_points_our_x1, def_points_our_x2, def_points_our_y1, def_points_our_y2;
    public static float def_points_enemy_x1, def_points_enemy_x2, def_points_enemy_y1, def_points_enemy_y2;
    public static float def_total_time_x1, def_total_time_x2, def_total_time_y1, def_total_time_y2;
    public static float def_early_win_x1, def_early_win_x2, def_early_win_y1, def_early_win_y2;
    public static float def_team_name_our_x1, def_team_name_our_x2, def_team_name_our_y1, def_team_name_our_y2;
    public static float def_team_name_enemy_x1, def_team_name_enemy_x2, def_team_name_enemy_y1, def_team_name_enemy_y2;
    public static float def_building_lt_x1, def_building_lt_x2, def_building_lt_y1, def_building_lt_y2;
    public static float def_building_lt_name_x1, def_building_lt_name_x2, def_building_lt_name_y1, def_building_lt_name_y2;
    public static float def_building_lt_increase_x1, def_building_lt_increase_x2, def_building_lt_increase_y1, def_building_lt_increase_y2;
    public static float def_building_lt_slots_x1, def_building_lt_slots_x2, def_building_lt_slots_y1, def_building_lt_slots_y2;
    public static float def_building_lt_progress_x1, def_building_lt_progress_x2, def_building_lt_progress_y1, def_building_lt_progress_y2;
    public static float def_building_lc_x1, def_building_lc_x2, def_building_lc_y1, def_building_lc_y2;
    public static float def_building_lc_name_x1, def_building_lc_name_x2, def_building_lc_name_y1, def_building_lc_name_y2;
    public static float def_building_lc_increase_x1, def_building_lc_increase_x2, def_building_lc_increase_y1, def_building_lc_increase_y2;
    public static float def_building_lc_slots_x1, def_building_lc_slots_x2, def_building_lc_slots_y1, def_building_lc_slots_y2;
    public static float def_building_lc_progress_x1, def_building_lc_progress_x2, def_building_lc_progress_y1, def_building_lc_progress_y2;
    public static float def_building_lb_x1, def_building_lb_x2, def_building_lb_y1, def_building_lb_y2;
    public static float def_building_lb_name_x1, def_building_lb_name_x2, def_building_lb_name_y1, def_building_lb_name_y2;
    public static float def_building_lb_increase_x1, def_building_lb_increase_x2, def_building_lb_increase_y1, def_building_lb_increase_y2;
    public static float def_building_lb_slots_x1, def_building_lb_slots_x2, def_building_lb_slots_y1, def_building_lb_slots_y2;
    public static float def_building_lb_progress_x1, def_building_lb_progress_x2, def_building_lb_progress_y1, def_building_lb_progress_y2;
    public static float def_building_rt_x1, def_building_rt_x2, def_building_rt_y1, def_building_rt_y2;
    public static float def_building_rt_name_x1, def_building_rt_name_x2, def_building_rt_name_y1, def_building_rt_name_y2;
    public static float def_building_rt_increase_x1, def_building_rt_increase_x2, def_building_rt_increase_y1, def_building_rt_increase_y2;
    public static float def_building_rt_slots_x1, def_building_rt_slots_x2, def_building_rt_slots_y1, def_building_rt_slots_y2;
    public static float def_building_rt_progress_x1, def_building_rt_progress_x2, def_building_rt_progress_y1, def_building_rt_progress_y2;
    public static float def_building_rc_x1, def_building_rc_x2, def_building_rc_y1, def_building_rc_y2;
    public static float def_building_rc_name_x1, def_building_rc_name_x2, def_building_rc_name_y1, def_building_rc_name_y2;
    public static float def_building_rc_increase_x1, def_building_rc_increase_x2, def_building_rc_increase_y1, def_building_rc_increase_y2;
    public static float def_building_rc_slots_x1, def_building_rc_slots_x2, def_building_rc_slots_y1, def_building_rc_slots_y2;
    public static float def_building_rc_progress_x1, def_building_rc_progress_x2, def_building_rc_progress_y1, def_building_rc_progress_y2;
    public static float def_building_rb_x1, def_building_rb_x2, def_building_rb_y1, def_building_rb_y2;
    public static float def_building_rb_name_x1, def_building_rb_name_x2, def_building_rb_name_y1, def_building_rb_name_y2;
    public static float def_building_rb_increase_x1, def_building_rb_increase_x2, def_building_rb_increase_y1, def_building_rb_increase_y2;
    public static float def_building_rb_slots_x1, def_building_rb_slots_x2, def_building_rb_slots_y1, def_building_rb_slots_y2;
    public static float def_building_rb_progress_x1, def_building_rb_progress_x2, def_building_rb_progress_y1, def_building_rb_progress_y2;
    
    private Date timeStartGame;         // дата начала игры
    private Date timeEndGame;           // дата конца игры

    private int initMinutesToEndGame;   // инитное значение кол-ва минут до конца игры
    private int initPlusUs;             // инитное значение ПлюсНам
    private int initInstantVic;         // инитное значение очков до досрочки
    private int initTotalUs;            // инитное значение НашиОчки
    private int initTotalThey;          // инитное значение ИхОчки
    private int initPlusThey;           // инитное значение ПлюсИм

    private NotificationManager notificationManager;    // нотификатор
    private static final int NOTIFY_ID = 1;             // айди нотификатора
    private static final String CHANNEL_ID = "chan1";   // канал нотификатора

    private static boolean allPermissionsIsGranted;
    private static boolean permReadIsGranted;
    private static boolean permWriteIsGranted;
    private static boolean permInternetIsGranted;

    // DEBUG MODE - контролы
    public static boolean isDebugMode;
    private LinearLayout dbgLayout;
    private TextView dbgTvFileName;
    private TextView dbgTvFileDate;
    private ImageView dbgIvTime;
    private TextView dbgTvTime;
    private ImageView dbgIvInstanceVic;
    private TextView dbgTvInstanceVic;
    private ImageView dbgIvTotalUs;
    private TextView dbgTvTotalUs;
    private ImageView dbgIvPlusUs;
    private TextView dbgTvPlusUs;
    private ImageView dbgIvTotalThey;
    private TextView dbgTvTotalThey;
    private ImageView dbgIvPlusThey;
    private TextView dbgTvPlusThey;


    /**
     *  Распознование картинки
     *  На входе - битмап
     *  На выходе - строка
     */

    public void initMaps() {
        TypedValue typedValue = new TypedValue();
        int index;
        index = R.dimen.def_cut_city_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_city_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_city_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_total_time_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_total_time_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_total_time_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_total_time_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_early_win_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_early_win_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_early_win_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_early_win_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_points_and_increase_our_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_points_and_increase_our_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_points_and_increase_our_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_points_and_increase_our_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_points_and_increase_enemy_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_points_and_increase_enemy_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_points_and_increase_enemy_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_points_and_increase_enemy_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_team_name_our_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_team_name_our_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_team_name_our_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_team_name_our_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_team_name_enemy_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_team_name_enemy_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_team_name_enemy_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_team_name_enemy_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_increase_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_increase_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_increase_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_increase_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_name_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_name_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_name_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_name_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_slots_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_slots_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_slots_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_slots_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_progress_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_progress_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_progress_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lt_progress_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_increase_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_increase_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_increase_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_increase_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_name_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_name_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_name_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_name_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_slots_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_slots_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_slots_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_slots_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_progress_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_progress_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_progress_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lc_progress_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_increase_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_increase_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_increase_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_increase_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_name_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_name_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_name_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_name_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_slots_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_slots_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_slots_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_slots_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_progress_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_progress_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_progress_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_lb_progress_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_increase_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_increase_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_increase_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_increase_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_name_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_name_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_name_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_name_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_slots_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_slots_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_slots_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_slots_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_progress_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_progress_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_progress_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rt_progress_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_increase_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_increase_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_increase_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_increase_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_name_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_name_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_name_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_name_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_slots_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_slots_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_slots_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_slots_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_progress_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_progress_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_progress_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rc_progress_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_increase_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_increase_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_increase_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_increase_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_name_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_name_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_name_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_name_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_slots_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_slots_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_slots_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_slots_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_progress_x1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_progress_x2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_progress_y1; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());
        index = R.dimen.def_cut_building_rb_progress_y2; getResources().getValue(index, typedValue, true); mapFloats.put(index,typedValue.getFloat());

        index = R.integer.def_rgb_total_time_main; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_total_time_back1; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_total_time_back2; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_total_time_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_total_time_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_early_win_main; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_early_win_back; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_early_win_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_early_win_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_points_our_main; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_points_our_back1; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_points_our_back2; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_points_our_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_points_our_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_increase_our_main; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_increase_our_back; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_increase_our_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_increase_our_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_points_enemy_main; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_points_enemy_back1; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_points_enemy_back2; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_points_enemy_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_points_enemy_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_increase_enemy_main; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_increase_enemy_back; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_increase_enemy_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_increase_enemy_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_slot_main; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_slot_back; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_slot_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_slot_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_points_our_main; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_points_our_back; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_points_our_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_points_our_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_points_enemy_main; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_points_enemy_back; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_points_enemy_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_points_enemy_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_progress_our; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_progress_enemy; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_progress_empty; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_progress_our_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_progress_our_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_progress_enemy_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_progress_enemy_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_progress_empty_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_progress_empty_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_doublepoints; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_doublepoints_thm; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
        index = R.integer.def_rgb_building_doublepoints_thp; getResources().getValue(index, typedValue, true); mapInts.put(index,(int)typedValue.getFloat());
    }


    public void readPreferences() {

        TypedValue typedValue = new TypedValue();
        int index;
        float value;
        
        index = R.dimen.def_cut_city_x1; getResources().getValue(index, typedValue, true); value = typedValue.getFloat();
        
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);

        pathToScreenshotDir = sharedPreferences.getString(getString(R.string.pref_screenshot_folder),"");
        isListenToNewFileInFolder = sharedPreferences.getBoolean(getString(R.string.pref_listen_last_file),false);
        isDebugMode = sharedPreferences.getBoolean(getString(R.string.pref_debug_mode),false);
        calibrateX = sharedPreferences.getInt(getString(R.string.pref_calibrate_x),0);
        calibrateY = sharedPreferences.getInt(getString(R.string.pref_calibrate_y),0);

        rgb_points_our = sharedPreferences.getInt(getString(R.string.pref_rgb_total_us),DEFAULT_PREF_RGB_TOTAL_US);
        rgb_points_our_thp = sharedPreferences.getInt(getString(R.string.pref_rgb_total_us_threshold_plus),DEFAULT_PREF_RGB_TOTAL_US_THRESHOLD_PLUS);
        rgb_points_our_thm = sharedPreferences.getInt(getString(R.string.pref_rgb_total_us_threshold_minus),DEFAULT_PREF_RGB_TOTAL_US_THRESHOLD_MINUS);

        rgb_points_enemy = sharedPreferences.getInt(getString(R.string.pref_rgb_total_they),DEFAULT_PREF_RGB_TOTAL_THEY);
        rgb_points_enemy_thp = sharedPreferences.getInt(getString(R.string.pref_rgb_total_they_threshold_plus),DEFAULT_PREF_RGB_TOTAL_THEY_THRESHOLD_PLUS);
        rgb_points_enemy_thm = sharedPreferences.getInt(getString(R.string.pref_rgb_total_they_threshold_minus),DEFAULT_PREF_RGB_TOTAL_THEY_THRESHOLD_MINUS);

        rgb_increase_our = sharedPreferences.getInt(getString(R.string.pref_rgb_plus_us),DEFAULT_PREF_RGB_PLUS_US);
        rgb_increase_our_thp = sharedPreferences.getInt(getString(R.string.pref_rgb_plus_us_threshold_plus),DEFAULT_PREF_RGB_PLUS_US_THRESHOLD_PLUS);
        rgb_increase_our_thm = sharedPreferences.getInt(getString(R.string.pref_rgb_plus_us_threshold_minus),DEFAULT_PREF_RGB_PLUS_US_THRESHOLD_MINUS);

        rgb_increase_enemy = sharedPreferences.getInt(getString(R.string.pref_rgb_plus_they),DEFAULT_PREF_RGB_PLUS_THEY);
        rgb_increase_enemy_thp = sharedPreferences.getInt(getString(R.string.pref_rgb_plus_they_threshold_plus),DEFAULT_PREF_RGB_PLUS_THEY_THRESHOLD_PLUS);
        rgb_increase_enemy_thm = sharedPreferences.getInt(getString(R.string.pref_rgb_plus_they_threshold_minus),DEFAULT_PREF_RGB_PLUS_THEY_THRESHOLD_MINUS);

        rgb_total_time = sharedPreferences.getInt(getString(R.string.pref_rgb_total_time),DEFAULT_PREF_RGB_TOTAL_TIME);
        rgb_total_time_thp = sharedPreferences.getInt(getString(R.string.pref_rgb_total_time_threshold_plus),DEFAULT_PREF_RGB_TOTAL_TIME_THRESHOLD_PLUS);
        rgb_total_time_thm = sharedPreferences.getInt(getString(R.string.pref_rgb_total_time_threshold_minus),DEFAULT_PREF_RGB_TOTAL_TIME_THRESHOLD_MINUS);

        rgb_early_win = sharedPreferences.getInt(getString(R.string.pref_rgb_early_win), DEFAULT_PREF_RGB_EARLY_WIN);
        rgb_early_win_thp = sharedPreferences.getInt(getString(R.string.pref_rgb_early_win_threshold_plus),DEFAULT_PREF_RGB_EARLY_WIN_THRESHOLD_PLUS);
        rgb_early_win_thm = sharedPreferences.getInt(getString(R.string.pref_rgb_early_win_threshold_minus),DEFAULT_PREF_RGB_EARLY_WIN_THRESHOLD_MINUS);

        getResources().getValue(R.dimen.def_cut_city_x1, typedValue, true); cut_city_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_city_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_city_x2, typedValue, true); cut_city_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_city_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_city_y1, typedValue, true); cut_city_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_city_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_city_y2, typedValue, true); cut_city_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_city_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_points_and_increase_our_x1, typedValue, true); cut_points_and_increase_our_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_points_and_increase_our_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_points_and_increase_our_x2, typedValue, true); cut_points_and_increase_our_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_points_and_increase_our_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_points_and_increase_our_y1, typedValue, true); cut_points_and_increase_our_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_points_and_increase_our_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_points_and_increase_our_y2, typedValue, true); cut_points_and_increase_our_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_points_and_increase_our_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_points_and_increase_enemy_x1, typedValue, true); cut_points_and_increase_enemy_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_points_and_increase_enemy_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_points_and_increase_enemy_x2, typedValue, true); cut_points_and_increase_enemy_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_points_and_increase_enemy_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_points_and_increase_enemy_y1, typedValue, true); cut_points_and_increase_enemy_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_points_and_increase_enemy_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_points_and_increase_enemy_y2, typedValue, true); cut_points_and_increase_enemy_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_points_and_increase_enemy_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_total_time_x1, typedValue, true); cut_total_time_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_total_time_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_total_time_x2, typedValue, true); cut_total_time_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_total_time_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_total_time_y1, typedValue, true); cut_total_time_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_total_time_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_total_time_y2, typedValue, true); cut_total_time_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_total_time_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_early_win_x1, typedValue, true); cut_early_win_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_early_win_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_early_win_x2, typedValue, true); cut_early_win_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_early_win_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_early_win_y1, typedValue, true); cut_early_win_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_early_win_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_early_win_y2, typedValue, true); cut_early_win_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_early_win_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_team_name_our_x1, typedValue, true); cut_team_name_our_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_team_name_our_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_team_name_our_x2, typedValue, true); cut_team_name_our_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_team_name_our_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_team_name_our_y1, typedValue, true); cut_team_name_our_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_team_name_our_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_team_name_our_y2, typedValue, true); cut_team_name_our_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_team_name_our_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_team_name_enemy_x1, typedValue, true); cut_team_name_enemy_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_team_name_enemy_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_team_name_enemy_x2, typedValue, true); cut_team_name_enemy_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_team_name_enemy_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_team_name_enemy_y1, typedValue, true); cut_team_name_enemy_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_team_name_enemy_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_team_name_enemy_y2, typedValue, true); cut_team_name_enemy_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_team_name_enemy_y2), typedValue.getFloat());

        
        getResources().getValue(R.dimen.def_cut_building_lt_x1, typedValue, true); cut_building_lt_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_x2, typedValue, true); cut_building_lt_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_y1, typedValue, true); cut_building_lt_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_y2, typedValue, true); cut_building_lt_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lt_name_x1, typedValue, true); cut_building_lt_name_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_name_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_name_x2, typedValue, true); cut_building_lt_name_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_name_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_name_y1, typedValue, true); cut_building_lt_name_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_name_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_name_y2, typedValue, true); cut_building_lt_name_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_name_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lt_increase_x1, typedValue, true); cut_building_lt_increase_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_increase_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_increase_x2, typedValue, true); cut_building_lt_increase_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_increase_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_increase_y1, typedValue, true); cut_building_lt_increase_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_increase_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_increase_y2, typedValue, true); cut_building_lt_increase_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_increase_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lt_slots_x1, typedValue, true); cut_building_lt_slots_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_slots_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_slots_x2, typedValue, true); cut_building_lt_slots_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_slots_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_slots_y1, typedValue, true); cut_building_lt_slots_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_slots_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_slots_y2, typedValue, true); cut_building_lt_slots_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_slots_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lt_progress_x1, typedValue, true); cut_building_lt_progress_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_progress_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_progress_x2, typedValue, true); cut_building_lt_progress_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_progress_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_progress_y1, typedValue, true); cut_building_lt_progress_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_progress_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lt_progress_y2, typedValue, true); cut_building_lt_progress_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lt_progress_y2), typedValue.getFloat());


        getResources().getValue(R.dimen.def_cut_building_lc_x1, typedValue, true); cut_building_lc_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_x2, typedValue, true); cut_building_lc_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_y1, typedValue, true); cut_building_lc_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_y2, typedValue, true); cut_building_lc_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lc_name_x1, typedValue, true); cut_building_lc_name_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_name_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_name_x2, typedValue, true); cut_building_lc_name_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_name_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_name_y1, typedValue, true); cut_building_lc_name_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_name_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_name_y2, typedValue, true); cut_building_lc_name_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_name_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lc_increase_x1, typedValue, true); cut_building_lc_increase_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_increase_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_increase_x2, typedValue, true); cut_building_lc_increase_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_increase_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_increase_y1, typedValue, true); cut_building_lc_increase_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_increase_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_increase_y2, typedValue, true); cut_building_lc_increase_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_increase_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lc_slots_x1, typedValue, true); cut_building_lc_slots_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_slots_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_slots_x2, typedValue, true); cut_building_lc_slots_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_slots_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_slots_y1, typedValue, true); cut_building_lc_slots_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_slots_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_slots_y2, typedValue, true); cut_building_lc_slots_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_slots_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lc_progress_x1, typedValue, true); cut_building_lc_progress_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_progress_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_progress_x2, typedValue, true); cut_building_lc_progress_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_progress_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_progress_y1, typedValue, true); cut_building_lc_progress_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_progress_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lc_progress_y2, typedValue, true); cut_building_lc_progress_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lc_progress_y2), typedValue.getFloat());


        getResources().getValue(R.dimen.def_cut_building_lb_x1, typedValue, true); cut_building_lb_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_x2, typedValue, true); cut_building_lb_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_y1, typedValue, true); cut_building_lb_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_y2, typedValue, true); cut_building_lb_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lb_name_x1, typedValue, true); cut_building_lb_name_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_name_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_name_x2, typedValue, true); cut_building_lb_name_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_name_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_name_y1, typedValue, true); cut_building_lb_name_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_name_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_name_y2, typedValue, true); cut_building_lb_name_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_name_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lb_increase_x1, typedValue, true); cut_building_lb_increase_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_increase_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_increase_x2, typedValue, true); cut_building_lb_increase_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_increase_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_increase_y1, typedValue, true); cut_building_lb_increase_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_increase_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_increase_y2, typedValue, true); cut_building_lb_increase_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_increase_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lb_slots_x1, typedValue, true); cut_building_lb_slots_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_slots_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_slots_x2, typedValue, true); cut_building_lb_slots_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_slots_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_slots_y1, typedValue, true); cut_building_lb_slots_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_slots_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_slots_y2, typedValue, true); cut_building_lb_slots_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_slots_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_lb_progress_x1, typedValue, true); cut_building_lb_progress_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_progress_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_progress_x2, typedValue, true); cut_building_lb_progress_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_progress_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_progress_y1, typedValue, true); cut_building_lb_progress_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_progress_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_lb_progress_y2, typedValue, true); cut_building_lb_progress_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_lb_progress_y2), typedValue.getFloat());


        getResources().getValue(R.dimen.def_cut_building_rt_x1, typedValue, true); cut_building_rt_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_x2, typedValue, true); cut_building_rt_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_y1, typedValue, true); cut_building_rt_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_y2, typedValue, true); cut_building_rt_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rt_name_x1, typedValue, true); cut_building_rt_name_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_name_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_name_x2, typedValue, true); cut_building_rt_name_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_name_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_name_y1, typedValue, true); cut_building_rt_name_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_name_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_name_y2, typedValue, true); cut_building_rt_name_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_name_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rt_increase_x1, typedValue, true); cut_building_rt_increase_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_increase_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_increase_x2, typedValue, true); cut_building_rt_increase_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_increase_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_increase_y1, typedValue, true); cut_building_rt_increase_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_increase_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_increase_y2, typedValue, true); cut_building_rt_increase_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_increase_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rt_slots_x1, typedValue, true); cut_building_rt_slots_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_slots_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_slots_x2, typedValue, true); cut_building_rt_slots_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_slots_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_slots_y1, typedValue, true); cut_building_rt_slots_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_slots_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_slots_y2, typedValue, true); cut_building_rt_slots_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_slots_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rt_progress_x1, typedValue, true); cut_building_rt_progress_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_progress_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_progress_x2, typedValue, true); cut_building_rt_progress_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_progress_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_progress_y1, typedValue, true); cut_building_rt_progress_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_progress_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rt_progress_y2, typedValue, true); cut_building_rt_progress_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rt_progress_y2), typedValue.getFloat());


        getResources().getValue(R.dimen.def_cut_building_rc_x1, typedValue, true); cut_building_rc_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_x2, typedValue, true); cut_building_rc_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_y1, typedValue, true); cut_building_rc_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_y2, typedValue, true); cut_building_rc_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rc_name_x1, typedValue, true); cut_building_rc_name_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_name_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_name_x2, typedValue, true); cut_building_rc_name_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_name_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_name_y1, typedValue, true); cut_building_rc_name_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_name_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_name_y2, typedValue, true); cut_building_rc_name_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_name_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rc_increase_x1, typedValue, true); cut_building_rc_increase_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_increase_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_increase_x2, typedValue, true); cut_building_rc_increase_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_increase_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_increase_y1, typedValue, true); cut_building_rc_increase_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_increase_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_increase_y2, typedValue, true); cut_building_rc_increase_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_increase_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rc_slots_x1, typedValue, true); cut_building_rc_slots_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_slots_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_slots_x2, typedValue, true); cut_building_rc_slots_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_slots_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_slots_y1, typedValue, true); cut_building_rc_slots_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_slots_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_slots_y2, typedValue, true); cut_building_rc_slots_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_slots_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rc_progress_x1, typedValue, true); cut_building_rc_progress_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_progress_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_progress_x2, typedValue, true); cut_building_rc_progress_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_progress_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_progress_y1, typedValue, true); cut_building_rc_progress_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_progress_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rc_progress_y2, typedValue, true); cut_building_rc_progress_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rc_progress_y2), typedValue.getFloat());


        getResources().getValue(R.dimen.def_cut_building_rb_x1, typedValue, true); cut_building_rb_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_x2, typedValue, true); cut_building_rb_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_y1, typedValue, true); cut_building_rb_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_y2, typedValue, true); cut_building_rb_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rb_name_x1, typedValue, true); cut_building_rb_name_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_name_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_name_x2, typedValue, true); cut_building_rb_name_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_name_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_name_y1, typedValue, true); cut_building_rb_name_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_name_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_name_y2, typedValue, true); cut_building_rb_name_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_name_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rb_increase_x1, typedValue, true); cut_building_rb_increase_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_increase_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_increase_x2, typedValue, true); cut_building_rb_increase_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_increase_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_increase_y1, typedValue, true); cut_building_rb_increase_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_increase_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_increase_y2, typedValue, true); cut_building_rb_increase_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_increase_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rb_slots_x1, typedValue, true); cut_building_rb_slots_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_slots_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_slots_x2, typedValue, true); cut_building_rb_slots_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_slots_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_slots_y1, typedValue, true); cut_building_rb_slots_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_slots_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_slots_y2, typedValue, true); cut_building_rb_slots_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_slots_y2), typedValue.getFloat());

        getResources().getValue(R.dimen.def_cut_building_rb_progress_x1, typedValue, true); cut_building_rb_progress_x1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_progress_x1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_progress_x2, typedValue, true); cut_building_rb_progress_x2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_progress_x2), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_progress_y1, typedValue, true); cut_building_rb_progress_y1 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_progress_y1), typedValue.getFloat());
        getResources().getValue(R.dimen.def_cut_building_rb_progress_y2, typedValue, true); cut_building_rb_progress_y2 = sharedPreferences.getFloat(getString(R.string.pref_cut_building_rb_progress_y2), typedValue.getFloat());
        
    }

    public void writePreferences() {

        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.pref_screenshot_folder), pathToScreenshotDir);
        editor.putBoolean(getString(R.string.pref_listen_last_file), isListenToNewFileInFolder);
        editor.putBoolean(getString(R.string.pref_debug_mode), isDebugMode);
        editor.putInt(getString(R.string.pref_calibrate_x), calibrateX);
        editor.putInt(getString(R.string.pref_calibrate_y), calibrateY);

        editor.putInt(getString(R.string.pref_rgb_total_us), rgb_points_our);
        editor.putInt(getString(R.string.pref_rgb_total_us_threshold_plus), rgb_points_our_thp);
        editor.putInt(getString(R.string.pref_rgb_total_us_threshold_minus), rgb_points_our_thm);

        editor.putInt(getString(R.string.pref_rgb_total_they), rgb_points_enemy);
        editor.putInt(getString(R.string.pref_rgb_total_they_threshold_plus), rgb_points_enemy_thp);
        editor.putInt(getString(R.string.pref_rgb_total_they_threshold_minus), rgb_points_enemy_thm);

        editor.putInt(getString(R.string.pref_rgb_plus_us), rgb_increase_our);
        editor.putInt(getString(R.string.pref_rgb_plus_us_threshold_plus), rgb_increase_our_thp);
        editor.putInt(getString(R.string.pref_rgb_plus_us_threshold_minus), rgb_increase_our_thm);

        editor.putInt(getString(R.string.pref_rgb_plus_they), rgb_increase_enemy);
        editor.putInt(getString(R.string.pref_rgb_plus_they_threshold_plus), rgb_increase_enemy_thp);
        editor.putInt(getString(R.string.pref_rgb_plus_they_threshold_minus), rgb_increase_enemy_thm);

        editor.putInt(getString(R.string.pref_rgb_total_time), rgb_total_time);
        editor.putInt(getString(R.string.pref_rgb_total_time_threshold_plus), rgb_total_time_thp);
        editor.putInt(getString(R.string.pref_rgb_total_time_threshold_minus), rgb_total_time_thm);

        editor.putInt(getString(R.string.pref_rgb_early_win), rgb_early_win);
        editor.putInt(getString(R.string.pref_rgb_early_win_threshold_plus), rgb_early_win_thp);
        editor.putInt(getString(R.string.pref_rgb_early_win_threshold_minus), rgb_early_win_thm);

        editor.putFloat(getString(R.string.pref_cut_city_x1), cut_city_x1);
        editor.putFloat(getString(R.string.pref_cut_city_x2), cut_city_x2);
        editor.putFloat(getString(R.string.pref_cut_city_y1), cut_city_y1);
        editor.putFloat(getString(R.string.pref_cut_city_y2), cut_city_y2);

        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_x1), cut_points_and_increase_our_x1);
        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_x2), cut_points_and_increase_our_x2);
        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_y1), cut_points_and_increase_our_y1);
        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_y2), cut_points_and_increase_our_y2);

        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_x1), cut_points_and_increase_enemy_x1);
        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_x2), cut_points_and_increase_enemy_x2);
        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_y1), cut_points_and_increase_enemy_y1);
        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_y2), cut_points_and_increase_enemy_y2);

        editor.putFloat(getString(R.string.pref_cut_total_time_x1), cut_total_time_x1);
        editor.putFloat(getString(R.string.pref_cut_total_time_x2), cut_total_time_x2);
        editor.putFloat(getString(R.string.pref_cut_total_time_y1), cut_total_time_y1);
        editor.putFloat(getString(R.string.pref_cut_total_time_y2), cut_total_time_y2);

        editor.putFloat(getString(R.string.pref_cut_early_win_x1), cut_early_win_x1);
        editor.putFloat(getString(R.string.pref_cut_early_win_x2), cut_early_win_x2);
        editor.putFloat(getString(R.string.pref_cut_early_win_y1), cut_early_win_y1);
        editor.putFloat(getString(R.string.pref_cut_early_win_y2), cut_early_win_y2);

        editor.apply();

    }

    private String recognizePicture(Bitmap bitmap) {

        String result = ""; // результат
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build(); // создаем текстрекогнайзер
        if (textRecognizer.isOperational()) {   // если текстрекогнайзер может что-то распознать
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();    // создаем фрейм на основе переданного битмапа
            SparseArray<TextBlock> items = textRecognizer.detect(frame);    // передаем фрейм в текстрекогнайзер, на выходе - массив текстовых блоков
            for (int i = 0; i < items.size(); ++i) {                        // проходимся по массиву текстовых блоков
                result = result + items.valueAt(i).getValue() + " ";        // добавляем к результату значение текста в очередном блоке, разделяем пробелами
            }
        }
        return result;  // возвращаем результат. Если не было ни одного блока или они все были пустыми - результатом будет пустая строка
    }


    /**
     *  Вырезаем из строки все не-числовые символы и возвращаем результат
     */

    private String cutNotNumericSymbols(String str) {
        String result = "";                 // результат
        for (char ch : str.toCharArray()) { // цикл по символам строки
            if (ch >= '0' && ch <='9') {    // если символ - числовой
                result = result + ch;       // приклеиваем его к результату
            }
        }
        return result;                      // возвращаем результат
    }


    /**
     * Вырезание картинок
     */

    public void cutPicture() {

        if (fileScreenshot != null && !fileScreenshot.equals(fileScreenshotPrevious)) {  // если текущий скриншот не нулл



            fileScreenshotPrevious = fileScreenshot;
            Bitmap sourceBitmap = BitmapFactory.decodeFile(fileScreenshot.getAbsolutePath());   // получаем битмап из файла скриншота

            try {
                File output = new File(MainActivity.pathToCATScalcFolder, "last_screenshot.PNG");
                if (!output.equals(fileScreenshot)) {
                    InputStream in = new FileInputStream(fileScreenshot);
                    OutputStream out = new FileOutputStream(output);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                }
            } catch (IOException ignored) {
            }


            CuttedPictures cuttedPictures = CutPicture.cutPictire(sourceBitmap);

            ivCity.setImageBitmap(cuttedPictures.croppingBitmap);  // выводим битмат игры в контрол

            String strTotalUs = recognizePicture(cuttedPictures.croppingBitmapTotalUs);         // распознаем "Наши очки"
            String strPlusUs = recognizePicture(cuttedPictures.croppingBitmapPlusUs);         // распознаем "Наши очки"
            String strTotalThey = recognizePicture(cuttedPictures.croppingBitmapTotalThey);     // распознаем "Их очки"
            String strPlusThey = recognizePicture(cuttedPictures.croppingBitmapPlusThey);     // распознаем "Их очки"
            String strTotalTime = recognizePicture(cuttedPictures.croppingBitmapTotalTime);            // распознаем "Время"
            String strInstanceVic = recognizePicture(cuttedPictures.croppingBitmapEarlyWin);        // распознаем "Досрочка"

            if (isDebugMode) { // если включен дебаг-мод
                // выводим битмапы и распознанные данные в контрлы дебаг-мода
                dbgIvTime.setImageBitmap(cuttedPictures.croppingBitmapTotalTime);
                dbgIvInstanceVic.setImageBitmap(cuttedPictures.croppingBitmapEarlyWin);
                dbgIvTotalUs.setImageBitmap(cuttedPictures.croppingBitmapTotalUs);
                dbgIvPlusUs.setImageBitmap(cuttedPictures.croppingBitmapPlusUs);
                dbgIvTotalThey.setImageBitmap(cuttedPictures.croppingBitmapTotalThey);
                dbgIvPlusThey.setImageBitmap(cuttedPictures.croppingBitmapPlusThey);

                dbgTvTime.setText(strTotalTime);
                dbgTvInstanceVic.setText(strInstanceVic);
                dbgTvTotalUs.setText(strTotalUs);
                dbgTvPlusUs.setText(strPlusUs);
                dbgTvTotalThey.setText(strTotalThey);
                dbgTvPlusThey.setText(strPlusThey);

                dbgTvFileName.setText(fileScreenshot.getName());
                dbgTvFileDate.setText("" + new Date(fileScreenshot.lastModified()));
            }

            strTotalUs = strTotalUs.equals("") ? "0" : cutNotNumericSymbols(strTotalUs);
            strPlusUs = strPlusUs.equals("") ? "0" : cutNotNumericSymbols(strPlusUs);
            strTotalThey = strTotalThey.equals("") ? "0" : cutNotNumericSymbols(strTotalThey);
            strPlusThey = strPlusThey.equals("") ? "0" : cutNotNumericSymbols(strPlusThey);
            strInstanceVic = strInstanceVic.equals("") ? "0" : cutNotNumericSymbols(strInstanceVic);

            // парсинг "Время"
            String[] arrTotalTime = strTotalTime.split(" ");    // парсим с разделителем "пробел"
            List<String> listTotalTime = new ArrayList<>();            // лист значений
            for (int i = 0; i < arrTotalTime.length; i++) {             // проходим по распарсенному массиву
                if (!arrTotalTime[i].equals("")) {                      // если элемент массива не пустой
                    listTotalTime.add(arrTotalTime[i]);                 // добавляем текущий элемент массива в лист
                }
            }

            if (listTotalTime.size() > 1) {                         // если в списке больше 1 элемента
                for (int i = 0; i < listTotalTime.size(); i++) {    // проходим по списку
                    listTotalTime.set(i, listTotalTime.get(i).substring(0, listTotalTime.get(i).length() - 1)); // отрезаем у текущего элемента списка последний символ
                }
            }

            if (listTotalTime.size() > 0) {     // если в списке есть элементы
                if (listTotalTime.size() == 1) {    // если в списке 1 элемент
                    if (listTotalTime.get(0).substring(listTotalTime.get(0).length()-1).toLowerCase().equals("m")) {    // если последний символ элемента равен букве "М"
                        String hours = "00";    // часы = "00"
                        String minutes = listTotalTime.get(0).substring(0, listTotalTime.get(0).length()-1);    // минуты = элемент без последнего символа
                        if (minutes.length() == 1) minutes = "0" + minutes;    // если минуты состоят из 1 символа -  дописываем "0" в начало минут
                        strTotalTime = hours + ":" + minutes;   // время = часы : минуты
                    } else {    // если последний символ элемента не равен букве "М"
                        String hours = listTotalTime.get(0).substring(0, listTotalTime.get(0).length()-1);  // часы = элемент без последнего символа
                        String minutes = "00";  // минуты = "00"
                        strTotalTime = hours + ":" + minutes; // время = часы : минуты
                    }
                } else {    // если в списке больше 1 элемента
                    String hours = listTotalTime.get(0);    // часы - первый элемент из списка
                    String minutes = listTotalTime.get(1);  // минуты - второй элемент из списка
                    if (minutes.length() == 1) minutes = "0" + minutes;    // если минуты состоят из 1 символа -  дописываем "0" в начало минут
                    strTotalTime = hours + ":" + minutes; // время = часы : минуты
                }
            } else { // если в списке нет элементов
                strTotalTime = "00:00"; // время нулевое
            }

            // если значения пустые - выводим в них "??", если нет - удаляем лишние пробелы в начале и в конце
            strPlusUs = strPlusUs.equals("") ? "?" : strPlusUs.trim();
            strPlusThey = strPlusThey.equals("") ? "?" : strPlusThey.trim();
            strTotalUs = strTotalUs.equals("") ? "?" : strTotalUs.trim();
            strTotalThey = strTotalThey.equals("") ? "?" : strTotalThey.trim();
            strInstanceVic = strInstanceVic.equals("") ? "?" : strInstanceVic.trim();
            strTotalTime = strTotalTime.equals("") ? "?:?" : strTotalTime.trim();

            // выводим распознанные данные в контролы
            etInPlusUs.setText(strPlusUs);
            etInPlusThey.setText(strPlusThey);
            etInTotalUs.setText(strTotalUs);
            etInTotalThey.setText(strTotalThey);
            etInTimeLeft.setText(strTotalTime);
            etInInstantVic.setText(strInstanceVic);
            tvResult.setText("");
        }

    }

    /**
     *  Получение самого свежего файла из папки
     *  На вход - папка
     *  На выход - файл
     */
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
     *  Выбор скриншота
     */
    private void selectScreenshot() {

        OpenFileDialog fileDialog = new OpenFileDialog(this, pathToScreenshotDir)   // диалог выбора скриншота по переданному пути
                .setFolderIcon(getResources().getDrawable(R.drawable.ic_folder))            // иконка папки
                .setFileIcon(getResources().getDrawable(R.drawable.ic_file))                // иконка файла
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {
                        fileScreenshot = new File(fileName); // файл скриншота - возавращенный из диалога
                        isListenToNewFileInFolder = false; // снимаем флажок "Следить за файлами в папке"
                        swListenNewFiles.setChecked(false); // устанавливаем контрол флажка
                        fileLastInFolder = null;    // сбрасываем последний файл в папке
                        cutPicture();   // вырезаем картинку
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


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        Toast.makeText(this, "onNewIntent",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Toast.makeText(this, "onRestart",Toast.LENGTH_LONG).show();
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
     * Возврат в текущую активность из какой-то другой активности
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // если произошел возврат со страницы настрок - обновляем контролы в текущей активности
        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY) {

            readPreferences();

            swListenNewFiles.setChecked(isListenToNewFileInFolder);
            dbgLayout.setVisibility(isDebugMode ? View.VISIBLE : View.INVISIBLE);
            fileScreenshotPrevious = null;
            cutPicture();
        }
    }

    /**
     * Инициализация текущей активности
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // инициализирукм контентвью

        initMaps();

        // РЕКЛАМА
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
//        MobileAds.setRequestConfiguration(
//                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
//                        .build());

        // рекламный блок
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        // конец рекламы



        // если "программа устарела" - выходим
        if (Calendar.getInstance().getTime().after(DATE_EXPIRED)) {
            Toast.makeText(MainActivity.this, R.string.program_depricated, Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }

//        while (true) {

        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add(getString(R.string.read_external_storage));
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add(getString(R.string.write_external_storage));
        if (!addPermission(permissionsList, Manifest.permission.INTERNET))
            permissionsNeeded.add(getString(R.string.internet));

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = getString(R.string.you_need_to_grant_access_to) + " " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        MY_PERMISSIONS_REQUESTREAD_MULTIPERMISIONS);
                            }
                        });
//                return;
            }
//            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), MY_PERMISSIONS_REQUESTREAD_MULTIPERMISIONS);
//            return;
        }

        // путь к папке программы в корне файловой системы. Если такой папки нет - создаем её
        pathToCATScalcFolder = Environment.getExternalStorageDirectory().getPath() + "/CATScalc";
        File tempDir = new File(pathToCATScalcFolder);
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        if (tempDir.exists()) {
            File tmp = new File(MainActivity.pathToCATScalcFolder, "last_screenshot.PNG");       // файл картинки - путь к папке программы + имя файла
            if (!tmp.exists()) {
                Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.raw.stub_screenshot);
                try {
                    OutputStream fOutScreenshot = new FileOutputStream(tmp);
                    sourceBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutScreenshot); // сжимаем картинку в ПНГ с качеством 100%
                    fOutScreenshot.flush();                                                            // сохраняем данные из потока
                    fOutScreenshot.close();// закрываем поток
                    fileScreenshot = tmp;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                fileScreenshot = tmp;
            }
        }

        // нотификейшт менеджер
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // привязка контролов
        ivCity = findViewById(R.id.iv_city);

        etInTimeLeft = findViewById(R.id.et_in_time_left);
        etInPlusUs = findViewById(R.id.et_in_plus_us);
        etInPlusThey = findViewById(R.id.et_in_plus_they);
        etInTotalUs = findViewById(R.id.et_in_total_us);
        etInTotalThey = findViewById(R.id.et_in_total_they);
        etInInstantVic = findViewById(R.id.et_in_instant_vic);
        swListenNewFiles = findViewById(R.id.sw_listen_new_file);
        tvResult =  findViewById(R.id.tv_result);

        // инитные значения контролов
        etInTimeLeft.setText("00:00");
        etInPlusUs.setText("0");
        etInPlusThey.setText("0");
        etInTotalUs.setText("0");
        etInTotalThey.setText("0");
        etInInstantVic.setText("0");
        tvResult.setText("");

        // контролы дебаг-мода
        dbgLayout = findViewById(R.id.dbg_layout);
        TextView dbgTvHeader = findViewById(R.id.dbg_tv_header);
        dbgTvFileName = findViewById(R.id.dbg_tv_file_name);
        dbgTvFileDate = findViewById(R.id.dbg_tv_file_date);

        dbgIvTime = findViewById(R.id.dbg_img_time);
        dbgTvTime = findViewById(R.id.dbg_tv_time);

        dbgIvInstanceVic = findViewById(R.id.dbg_img_instance_vic);
        dbgTvInstanceVic = findViewById(R.id.dbg_tv_instance_vic);

        dbgIvTotalUs = findViewById(R.id.dbg_img_total_us);
        dbgIvPlusUs = findViewById(R.id.dbg_img_plus_us);

        dbgTvTotalUs = findViewById(R.id.dbg_tv_total_us);
        dbgTvPlusUs = findViewById(R.id.dbg_tv_plus_us);

        dbgIvTotalThey = findViewById(R.id.dbg_img_total_they);
        dbgIvPlusThey = findViewById(R.id.dbg_img_plus_they);

        dbgTvTotalThey = findViewById(R.id.dbg_tv_total_they);
        dbgTvPlusThey = findViewById(R.id.dbg_tv_plus_they);

        // отслеживание изменения свича "Следить за файлами в папке"
        swListenNewFiles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    // если свич переключили
                // обновляем соответствующий пермишн и переменную
                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_listen_last_file), isChecked);
                editor.apply();
                isListenToNewFileInFolder = isChecked;
            }
        });


        // считываем преференцы, пихаем их в переменные и в контролы

        readPreferences();

        swListenNewFiles.setChecked(isListenToNewFileInFolder);

        // устанавливаем видимость группы контролов дебаг-мода, если он включен
        dbgLayout.setVisibility(isDebugMode ? View.VISIBLE : View.INVISIBLE);

        startTimer();   // стартуем таймер

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUESTREAD_MULTIPERMISIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted

                    Toast.makeText(MainActivity.this, R.string.all_perm_is_granted, Toast.LENGTH_SHORT)
                            .show();

                    // путь к папке программы в корне файловой системы. Если такой папки нет - создаем её
                    pathToCATScalcFolder = Environment.getExternalStorageDirectory().getPath() + "/CATScalc";
                    File tempDir = new File(pathToCATScalcFolder);
                    if (!tempDir.exists()) {
                        tempDir.mkdir();
                    }

                    if (tempDir.exists()) {
                        File tmp = new File(MainActivity.pathToCATScalcFolder, "last_screenshot.PNG");       // файл картинки - путь к папке программы + имя файла
                        if (!tmp.exists()) {
                            Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.raw.stub_screenshot);
                            try {
                                OutputStream fOutScreenshot = new FileOutputStream(tmp);
                                sourceBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutScreenshot); // сжимаем картинку в ПНГ с качеством 100%
                                fOutScreenshot.flush();                                                            // сохраняем данные из потока
                                fOutScreenshot.close();// закрываем поток
                                fileScreenshot = tmp;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            fileScreenshot = tmp;
                        }
                    }


                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, R.string.some_perm_is_denied, Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    /**
     * Калькулятор
     */

    private void calculate() {

        String calcResult;      // результат

        if (isAllFieldsCorrect) {   // если все поля заполненны верно

            Date currentTime = Calendar.getInstance().getTime();    // текущее время
            long timeLeftInMillis = currentTime.getTime() - timeStartGame.getTime(); // кол-во миллисекунд, прошедшех от начала игры до текущего времени
            long currentSecsAndMillis = 60000 - timeLeftInMillis % 60_000; // кол-во секунд и миллисекунд (в миллисекундах)
            int currentTotalUs = (int) ((timeLeftInMillis / 60000) * initPlusUs + initTotalUs);            // на данный момент очков у нас
            int currentTotalThey = (int) ((timeLeftInMillis / 60000) * initPlusThey + initTotalThey);      // на данный момент очков у них
            long leftMillsToInstanceUs = initPlusUs == 0 ? 25*60*60*1000 : ((initInstantVic - currentTotalUs) / initPlusUs) * 60000 + currentSecsAndMillis;            // осталось миллисекунд до досрочной победы нам
            long leftMillsToInstanceThey = initPlusThey == 0 ? 25*60*60*1000 : ((initInstantVic - currentTotalThey) / initPlusThey) * 60000 + currentSecsAndMillis;      // осталось миллисекунд до досрочной победы им
            long leftMillsToInstanceGame = Math.min(leftMillsToInstanceUs, leftMillsToInstanceThey);
            long leftMillsToEndGame = (timeEndGame.getTime() - currentTime.getTime()); // осталось миллисекунд до окончания игры по времени
            long willTotalUs = currentTotalUs + initPlusUs * (leftMillsToEndGame / 60000);   // будет очков у нас по окончании игры по времени
            long willTotalThey = currentTotalThey + initPlusThey * (leftMillsToEndGame / 60000);   // будет очков у них по окончании игры по времени
            long willTotalInstanceUs = currentTotalUs + initPlusUs * (leftMillsToInstanceGame / 60000);   // будет очков у нас по окончании игры по времени
            long willTotalInstanceThey = currentTotalThey + initPlusThey * (leftMillsToInstanceGame / 60000);   // будет очков у них по окончании игры по времени
            boolean isGameOver = (leftMillsToInstanceUs - 1000) <= 0 || (leftMillsToInstanceThey - 1000) <= 0 || leftMillsToEndGame <= 0; // закончилась ли игра?
            boolean isGameOverInstance = (leftMillsToInstanceUs - 1000) <= 0 ||  (leftMillsToInstanceThey - 1000) <= 0; // закончилась ли игра досрочно?

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // форматтер строки даты

            if (isGameOver) {   // если игра закончилась
                currentTotalUs += initPlusUs;       // наши текущие очки
                currentTotalThey += initPlusThey;   // их текущие очки

                if (isGameOverInstance) {   // если игра закончилась досрочно
                    if (currentTotalUs > currentTotalThey) {    // досрочно выиграли мы
                        calcResult = getString(R.string.we_instance_win);
                    } else if (currentTotalUs < currentTotalThey) { // досрочно выиграли они
                        calcResult = getString(R.string.we_instance_lose);
                    } else { // досрочная ничья
                        calcResult = getString(R.string.instance_nowinner);
                    }
                } else { // если игра закончилась по времени
                    if (currentTotalUs > currentTotalThey) { // выиграли мы
                        calcResult = getString(R.string.we_win);
                    } else if (currentTotalUs < currentTotalThey) { // выиграли они
                        calcResult = getString(R.string.we_lost);
                    } else { // ничья
                        calcResult = getString(R.string.nowin);
                    }
                }

            } else { // если игра не закончилась
                // часы, минуты, секунды и общее время до конца игры
                int hoursToEnd = (int)((leftMillsToEndGame - 1000) / (1000 * 60 * 60));
                int minutesToEnd = (int)(((leftMillsToEndGame - 1000) - hoursToEnd * (1000 * 60 * 60)) / (1000 * 60));
                int secondsToEnd = (int)(((leftMillsToEndGame - 1000) - hoursToEnd * (1000 * 60 * 60) - minutesToEnd * (1000 * 60)) / 1000);
                String strTimeToEnd = String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursToEnd, minutesToEnd, secondsToEnd);

                // время окончания игры (локальное)
                long timeEndGame = currentTime.getTime() + leftMillsToEndGame;
                String strTimeEndGame = simpleDateFormat.format(new Date(timeEndGame));

                if (leftMillsToEndGame - leftMillsToInstanceUs > 0 || leftMillsToEndGame - leftMillsToInstanceThey > 0) { // если игра закончится досрочно


                    long leftMillsToInstanceEndGame = Math.min((leftMillsToInstanceUs), (leftMillsToInstanceThey)); // осталось миллисекунд до досрочного окончания
                    // часы, минуты, секунды и общее время до досрочного конца игры
                    int hoursToInstanceEnd = (int)((leftMillsToInstanceEndGame - 1000) / (1000 * 60 * 60));
                    int minutesToInstanceEnd = (int)(((leftMillsToInstanceEndGame - 1000) - hoursToInstanceEnd * (1000 * 60 * 60)) / (1000 * 60));
                    int secondsToInstanceEnd = (int)(((leftMillsToInstanceEndGame - 1000) - hoursToInstanceEnd * (1000 * 60 * 60) - minutesToInstanceEnd * (1000 * 60)) / 1000);
                    String strTimeToInstanceEnd = String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursToInstanceEnd, minutesToInstanceEnd, secondsToInstanceEnd);

                    // время досрочного окончания игры (локальное)
                    long timeEndGameInstance = currentTime.getTime() + leftMillsToInstanceEndGame;
                    String strTimeEndGameInstance = simpleDateFormat.format(new Date(timeEndGameInstance));

                    if (leftMillsToInstanceUs < leftMillsToInstanceThey) { // Мы победим досрочно
                        calcResult = strTimeToInstanceEnd + " " + getString(R.string.we_will_instance_win_with_diff_in) + " " + (willTotalInstanceUs - willTotalInstanceThey) + " " + getString(R.string.points) + " " + strTimeEndGameInstance;
                    } else if (leftMillsToInstanceUs > leftMillsToInstanceThey) { // Мы проиграем досрочно
                        calcResult = strTimeToInstanceEnd + " " + getString(R.string.we_will_instance_lose_with_diff_in) + " " + (willTotalInstanceThey - willTotalInstanceUs) + " " + getString(R.string.points) + " " + strTimeEndGameInstance;
                    } else { // Будет досрочная ничья
                        calcResult = getString(R.string.will_instance_nowin_after) + " " + strTimeToInstanceEnd;
                    }

                } else { // если игра закончится по времени
                    if (willTotalUs > willTotalThey) { // Мы победим
                        calcResult = strTimeToEnd + " " + getString(R.string.we_will_win_with_diff_in) + " " + (willTotalUs - willTotalThey) + " " + getString(R.string.points) + " " + strTimeEndGame;
                    } else if (willTotalUs < willTotalThey) { // Мы проиграем
                        calcResult = strTimeToEnd + " " + getString(R.string.we_will_lose_with_diff_in) + " " + (willTotalThey - willTotalUs) + " " + getString(R.string.points) + " " + strTimeEndGame;
                    } else { // Будет ничья
                        calcResult = strTimeToEnd + " " + getString(R.string.will_nowin) + " " + strTimeEndGame;
                    }
                }

            }
        } else { // если не все поля заполненны верно
            calcResult = getString(R.string.error_entered_data);
        }

        tvResult.setText(calcResult); // выводим текс результата в контрол

        // Создаем нотификейшен
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_catscalciconsmall)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentText(calcResult)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(calcResult));
                createChannelIfNeeded(notificationManager);
                notificationManager.notify(NOTIFY_ID, notificationBuilder.build());

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
     * Класс задачи таймера
     */

    class firstTask extends TimerTask {

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {



                    if (isListenToNewFileInFolder) {    // если установлен флажок "Следить за файлами в папке"
                        File tmpFile = getLastFileInFolder(pathToScreenshotDir);    // получаем последний файл из папки
                        if (tmpFile != null) {  // если он не пустой
                            if (!tmpFile.equals(fileScreenshot)) {  // если он не равен текущем скриншоту
                                fileScreenshot = tmpFile;   // текущий скриншот = последнему файлу в папке
                                cutPicture();               // вырезам картинки
                            }
                        }

                    }

                    if (fileScreenshot != null) {   // если скринщот выбран

                        try {
                            // считываем переменные из контролов и если не возникло ошибки устанавливаем флажок "Данные введены верно"
                            timeStartGame = new Date(fileScreenshot.lastModified()); // время начала игры
                            initMinutesToEndGame = parseTimeLeft(etInTimeLeft.getText().toString());
                            timeEndGame = new Date(timeStartGame.getTime() + ((long)initMinutesToEndGame*60*1000)); // время окончания игры (по времени)
                            initPlusUs = Integer.parseInt(etInPlusUs.getText().toString());
                            initInstantVic = Integer.parseInt(etInInstantVic.getText().toString());
                            initTotalUs = Integer.parseInt(etInTotalUs.getText().toString());
                            initTotalThey = Integer.parseInt(etInTotalThey.getText().toString());
                            initPlusThey = Integer.parseInt(etInPlusThey.getText().toString());
                            isAllFieldsCorrect = true;
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            // если возникла ощибка считывания из контролов - сбрасываем флажок "Данные введены верно"
                            isAllFieldsCorrect = false;
                        }

                        calculate();    // вызываем калькулятор
                    }

                }
            });
        }
    };

    /**
     * Стартуем таймер
     */
    private void startTimer() {

        if (timer == null) {    // если таймер не запущен
            timer = new Timer();    // запускаем таймер
            timer.schedule(new firstTask(), 1000,1000); // запускаем такс таймера
        }

    }

    /**
     * Парсим время
     */
    private int parseTimeLeft(String str) {
        int result  = 0;
        String[] words = str.split(":");
        result = Integer.parseInt(words[0])* 60 + Integer.parseInt(words[1]);
        return result;
    }




}
