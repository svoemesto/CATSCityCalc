package com.svoemestodev.catscitycalc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CityCalc extends Activity {

    File fileScreenshot;
    Bitmap bmpScreenshot;       // исходный скриншот
    int calibrateX;             // сдвиг центра по X
    int calibrateY;             // сдвиг центра по Y
    CityCalcType cityCalcType;

    Map<Area, CityCalcArea> mapAreas = new HashMap<>(); // мап областей
    Context context;            // контекст
    private static final String TAG = "CityCalc";
    private ProgressDialog progressDialog;
    private CityCalc thisCityCalc = null;

    private SharedPreferences sharedPreferences;

    private int color_progress_our;
    private int color_progress_enemy;
    private int color_progress_empty;

    private int thm_progress_our;
    private int thp_progress_our;
    private int thm_progress_enemy;
    private int thp_progress_enemy;
    private int thm_progress_empty;
    private int thp_progress_empty;

    private int color_building_points_our_main;
    private int color_building_points_our_back;
    private int color_building_points_enemy_main;
    private int color_building_points_enemy_back;
    private int color_building_doublepoints;
    private int color_building_mayX2;
    private int color_building_isX2;

    private int thm_building_points_our;
    private int thp_building_points_our;
    private int thm_building_points_enemy;
    private int thp_building_points_enemy;

    private int color_building_slot_main;
    private int color_building_slot_back;

    private int thm_building_slot;
    private int thp_building_slot;

    private int color_box_info_main;
    private int color_box_info_thm;
    private int color_box_info_thp;
    private int color_car_box_main;
    private int color_car_box_thm;
    private int color_car_box_thp;

    private void initVariables() {

        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);

        color_progress_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_our),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_our), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our), 16)));
        color_progress_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_enemy),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_enemy), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy), 16)));
        color_progress_empty = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_empty),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_empty), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty), 16)));

        thm_progress_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_our_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_our_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thm))));
        thp_progress_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_our_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_our_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thp))));
        thm_progress_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_enemy_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_enemy_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thm))));
        thp_progress_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_enemy_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_enemy_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thp))));
        thm_progress_empty = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_empty_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_empty_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thm))));
        thp_progress_empty = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_empty_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_empty_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thp))));

        color_building_points_our_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_our_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_our_main), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16)));
        color_building_points_our_back = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_our_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_our_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back1), 16)));
        color_building_points_enemy_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_enemy_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_enemy_main), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16)));
        color_building_points_enemy_back = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_enemy_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_enemy_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back1), 16)));
        color_building_doublepoints = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_doublepoints),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_doublepoints), (int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints), 16)));
        color_building_mayX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_mayX2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_mayX2), (int)Long.parseLong(context.getString(R.string.def_rgb_building_mayX2), 16)));
        color_building_isX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_isX2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_isX2), (int)Long.parseLong(context.getString(R.string.def_rgb_building_isX2), 16)));

        thm_building_points_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_our_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_our_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm))));
        thp_building_points_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_our_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_our_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp))));
        thm_building_points_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_enemy_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_enemy_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm))));
        thp_building_points_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_enemy_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_enemy_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))));

        color_building_slot_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_slot_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_slot_main), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16)));
        color_building_slot_back = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_slot_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_slot_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back1), 16)));

        thm_building_slot = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_slot_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_slot_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm))));
        thp_building_slot = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_slot_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_slot_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))));

        color_box_info_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_box_info_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_box_info_main), (int)Long.parseLong(context.getString(R.string.def_rgb_box_info_main), 16)));
        color_box_info_thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_box_info_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_box_info_thm), Integer.parseInt(context.getString(R.string.def_rgb_box_info_thm))));
        color_box_info_thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_box_info_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_box_info_thp), Integer.parseInt(context.getString(R.string.def_rgb_box_info_thp))));
        color_car_box_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_car_box_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_car_box_main), (int)Long.parseLong(context.getString(R.string.def_rgb_car_box_main), 16)));
        color_car_box_thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_car_box_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_car_box_thm), Integer.parseInt(context.getString(R.string.def_rgb_car_box_thm))));
        color_car_box_thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_car_box_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_car_box_thp), Integer.parseInt(context.getString(R.string.def_rgb_car_box_thp))));

    }

    // конструктор для калибровки центра экрана
    public CityCalc(File file, int calibrateX, int calibrateY, Context context, CityCalcType cityCalcType) {
        String logMsgPref = "конструктор для калибровки центра экрана, бордюров и т.п.: ";
        Log.i(TAG, logMsgPref + "start");

        this.cityCalcType = cityCalcType;
        this.fileScreenshot = file;
        this.calibrateX = calibrateX;
        this.calibrateY = calibrateY;
        this.context = context;

        initVariables();

        thisCityCalc = this;

        if (fileScreenshot != null) {         // если файл не нулл
            if (fileScreenshot.exists()) {    // если файл физически существует
                bmpScreenshot = BitmapFactory.decodeFile(fileScreenshot.getAbsolutePath());
                if (this.bmpScreenshot != null) {
                    if (this.bmpScreenshot.getWidth() > this.bmpScreenshot.getHeight()) {

                        thisCityCalc = new CityCalc(this);

                        this.mapAreas = thisCityCalc.mapAreas;


                    }

                }
            }
        }
    }


    // конструктор для проверки боксов
    public CityCalc(File file, int calibrateX, int calibrateY, Context context) {
        String logMsgPref = "конструктор для для проверки боксов: ";
        Log.i(TAG, logMsgPref + "start");



        this.cityCalcType = CityCalcType.ERROR;
        this.fileScreenshot = file;
        this.calibrateX = calibrateX;
        this.calibrateY = calibrateY;
        this.context = context;

        initVariables();

        thisCityCalc = this;

        if (fileScreenshot != null) {         // если файл не нулл
            if (fileScreenshot.exists()) {    // если файл физически существует
                bmpScreenshot = BitmapFactory.decodeFile(fileScreenshot.getAbsolutePath());
                if (this.bmpScreenshot != null) {
                    if (this.bmpScreenshot.getWidth() > this.bmpScreenshot.getHeight()) {

                        setAreaToMap(Area.BOX_INFO);
                        setAreaToMap(Area.CAR_BOX);

                        CityCalcArea ccaBoxInfo = mapAreas.get(Area.BOX_INFO);
                        CityCalcArea ccaCarBox = mapAreas.get(Area.CAR_BOX);

                        boolean isGame = PictureProcessor.frequencyPixelInBitmap(ccaBoxInfo.bmpSrc, color_box_info_main,color_box_info_thm, color_box_info_thp) > 0.50f;
                        boolean isCar = PictureProcessor.frequencyPixelInBitmap(ccaCarBox.bmpSrc, color_car_box_main, color_car_box_thm, color_car_box_thp) > 0.50f;

                        if (isGame && isCar) {
                            this.cityCalcType = CityCalcType.CAR;
                        } else if (isGame) {
                            this.cityCalcType = CityCalcType.GAME;
                        } else {
                            this.cityCalcType = CityCalcType.ERROR;
                        }

                    } else {
                        this.cityCalcType = CityCalcType.ERROR;
                    }

                }
            }
        }


    }

    
    private void setAreaToMap(Area area) {

        float x1;           // x1
        float x2;           // x2
        float y1;           // y1
        float y2;           // y2
        int [] colors;      // цвета
        int [] ths;         // пороги
        boolean needOcr;    // надо распознавать
        boolean needBW;    // надо BW
        int color_main, color_back1, color_back2, thm, thp;
        CityCalcArea cca;   // область


        switch (area) {
            case BOX_INFO:
                // Box Info Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_box_info_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_box_info_x1), Float.parseFloat(context.getString(R.string.def_cut_box_info_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_box_info_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_box_info_x2), Float.parseFloat(context.getString(R.string.def_cut_box_info_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_box_info_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_box_info_y1), Float.parseFloat(context.getString(R.string.def_cut_box_info_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_box_info_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_box_info_y2), Float.parseFloat(context.getString(R.string.def_cut_box_info_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBoxInfo = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBoxInfo);
                break;
                
            case CITY:
                // City Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_x1), Float.parseFloat(context.getString(R.string.def_cut_city_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_x2), Float.parseFloat(context.getString(R.string.def_cut_city_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_y1), Float.parseFloat(context.getString(R.string.def_cut_city_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_y2), Float.parseFloat(context.getString(R.string.def_cut_city_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CCAGame ccaGame = new CCAGame(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaGame);
                break;
                
            case TOTAL_TIME:
                // Total Time Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_total_time_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_total_time_x1), Float.parseFloat(context.getString(R.string.def_cut_total_time_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_total_time_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_total_time_x2), Float.parseFloat(context.getString(R.string.def_cut_total_time_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_total_time_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_total_time_y1), Float.parseFloat(context.getString(R.string.def_cut_total_time_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_total_time_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_total_time_y2), Float.parseFloat(context.getString(R.string.def_cut_total_time_y2))));

                color_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_total_time_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_total_time_main), (int)Long.parseLong(context.getString(R.string.def_rgb_total_time_main), 16)));
                color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_total_time_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_total_time_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_total_time_back1), 16)));
                color_back2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_total_time_back2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_total_time_back2), (int)Long.parseLong(context.getString(R.string.def_rgb_total_time_back2), 16)));

                thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_total_time_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_total_time_thm), Integer.parseInt(context.getString(R.string.def_rgb_total_time_thm))));
                thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_total_time_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_total_time_thp), Integer.parseInt(context.getString(R.string.def_rgb_total_time_thp))));

                colors = new int[] {color_main, color_back1, color_back2};
                ths = new int[] {thm, thp};
                needOcr = true;
                needBW = false;
                CityCalcArea ccaTotalTime = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaTotalTime);
                break;
                
            case EARLY_WIN:
                // Early Win Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_early_win_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_early_win_x1), Float.parseFloat(context.getString(R.string.def_cut_early_win_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_early_win_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_early_win_x2), Float.parseFloat(context.getString(R.string.def_cut_early_win_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_early_win_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_early_win_y1), Float.parseFloat(context.getString(R.string.def_cut_early_win_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_early_win_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_early_win_y2), Float.parseFloat(context.getString(R.string.def_cut_early_win_y2))));

                color_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_early_win_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_early_win_main), (int)Long.parseLong(context.getString(R.string.def_rgb_early_win_main), 16)));
                color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_early_win_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_early_win_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_early_win_back1), 16)));

                thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_early_win_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_early_win_thm), Integer.parseInt(context.getString(R.string.def_rgb_early_win_thm))));
                thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_early_win_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_early_win_thp), Integer.parseInt(context.getString(R.string.def_rgb_early_win_thp))));

                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = true;
                needBW = true;
                CityCalcArea ccaEarlyWin = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaEarlyWin);
                break;
                
            case POINTS_AND_INCREASE_OUR:
                // Points And Increase Our Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_our_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_our_x1), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_our_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_our_x2), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_our_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_our_y1), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_our_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_our_y2), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_y2))));

                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                cca = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, cca);

                color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_points_our_back1), 16)));

                thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_thm), Integer.parseInt(context.getString(R.string.def_rgb_points_our_thm))));
                thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_thp), Integer.parseInt(context.getString(R.string.def_rgb_points_our_thp))));

                Bitmap[] ourPointsAndIncreaseBitmapArray = PictureProcessor.splitBitmap(cca.bmpSrc, color_back1, thm, thp, 0.85f, 0.01f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, true);

                CityCalcArea ccaPointsOur = null;
                CityCalcArea ccaIncreaseOur = null;

                if (ourPointsAndIncreaseBitmapArray != null) {

                    color_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_main), (int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16)));
                    color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_points_our_back1), 16)));
                    color_back2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_back2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_back2), (int)Long.parseLong(context.getString(R.string.def_rgb_points_our_back2), 16)));

                    thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_thm), Integer.parseInt(context.getString(R.string.def_rgb_points_our_thm))));
                    thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_thp), Integer.parseInt(context.getString(R.string.def_rgb_points_our_thp))));

                    colors = new int[] {color_main, color_back1, color_back2};
                    ths = new int[] {thm, thp};

                    needOcr = true;
                    needBW = false;
                    ccaPointsOur = new CityCalcArea(thisCityCalc, Area.POINTS_OUR, colors, ths, needOcr, needBW);
                    ccaPointsOur.bmpSrc = ourPointsAndIncreaseBitmapArray[1];
                    ccaPointsOur.doOCR();
                    mapAreas.put(Area.POINTS_OUR, ccaPointsOur);
                    
                    color_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_our_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_our_main), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_our_main), 16)));
                    color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_our_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_our_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_our_back1), 16)));

                    thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_our_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_our_thm), Integer.parseInt(context.getString(R.string.def_rgb_increase_our_thm))));
                    thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_our_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_our_thp), Integer.parseInt(context.getString(R.string.def_rgb_increase_our_thp))));

                    colors = new int[] {color_main, color_back1};
                    ths = new int[] {thm, thp};

                    needOcr = true;
                    needBW = false;
                    ccaIncreaseOur = new CityCalcArea(thisCityCalc, Area.INCREASE_OUR, colors, ths, needOcr,needBW);
                    ccaIncreaseOur.bmpSrc = ourPointsAndIncreaseBitmapArray[0];
                    ccaIncreaseOur.doOCR();
                    mapAreas.put(Area.INCREASE_OUR, ccaIncreaseOur);

                }
                break;
                
            case TEAM_NAME_OUR:
                // Team Name Our Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_our_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_our_x1), Float.parseFloat(context.getString(R.string.def_cut_team_name_our_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_our_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_our_x2), Float.parseFloat(context.getString(R.string.def_cut_team_name_our_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_our_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_our_y1), Float.parseFloat(context.getString(R.string.def_cut_team_name_our_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_our_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_our_y2), Float.parseFloat(context.getString(R.string.def_cut_team_name_our_y2))));

                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CCATeam ccaOurTeam = new CCATeam(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaOurTeam);
                break;
                
            case POINTS_AND_INCREASE_ENEMY:
                // Points And Increase Enemy Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_enemy_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_enemy_x1), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_enemy_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_enemy_x2), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_enemy_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_enemy_y1), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_enemy_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_enemy_y2), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_y2))));

                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                cca = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, cca);

                color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_back1), 16)));

                thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_thm), Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thm))));
                thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_thp), Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thp))));

                Bitmap[] enemyPointsAndIncreaseBitmapArray = PictureProcessor.splitBitmap(cca.bmpSrc, color_back1, thm, thp, 0.85f, 0.01f, PictureProcessorDirection.FROM_RIGHT_TO_LEFT, true);

                CityCalcArea ccaPointsEnemy = null;
                CityCalcArea ccaIncreaseEnemy = null;

                if (enemyPointsAndIncreaseBitmapArray != null) {

                    color_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_main), (int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16)));
                    color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_back1), 16)));
                    color_back2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_back2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_back2), (int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_back2), 16)));

                    thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_thm), Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thm))));
                    thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_thp), Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thp))));

                    colors = new int[] {color_main, color_back1, color_back2};
                    ths = new int[] {thm, thp};

                    needOcr = true;
                    needBW = false;
                    ccaPointsEnemy = new CityCalcArea(thisCityCalc, Area.POINTS_ENEMY, colors, ths, needOcr, needBW);
                    ccaPointsEnemy.bmpSrc = enemyPointsAndIncreaseBitmapArray[1];
                    ccaPointsEnemy.doOCR();
                    mapAreas.put(Area.POINTS_ENEMY, ccaPointsEnemy);

                    color_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_enemy_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_enemy_main), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_enemy_main), 16)));
                    color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_enemy_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_enemy_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_enemy_back1), 16)));

                    thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_enemy_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_enemy_thm), Integer.parseInt(context.getString(R.string.def_rgb_increase_enemy_thm))));
                    thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_enemy_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_enemy_thp), Integer.parseInt(context.getString(R.string.def_rgb_increase_enemy_thp))));

                    colors = new int[] {color_main, color_back1};
                    ths = new int[] {thm, thp};

                    needOcr = true;
                    needBW = false;
                    ccaIncreaseEnemy = new CityCalcArea(thisCityCalc, Area.INCREASE_ENEMY, colors, ths, needOcr, needBW);
                    ccaIncreaseEnemy.bmpSrc = enemyPointsAndIncreaseBitmapArray[0];
                    ccaIncreaseEnemy.doOCR();
                    mapAreas.put(Area.INCREASE_ENEMY, ccaIncreaseEnemy);

                }
                break;
                
            case TEAM_NAME_ENEMY:
                // Team Name Enemy Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_enemy_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_enemy_x1), Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_enemy_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_enemy_x2), Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_enemy_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_enemy_y1), Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_enemy_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_enemy_y2), Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_y2))));

                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CCATeam ccaEnemyTeam = new CCATeam(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaEnemyTeam);
                break;
                
            case BLT_AREA:
                // BLT Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_x1), Float.parseFloat(context.getString(R.string.def_cut_blt_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_x2), Float.parseFloat(context.getString(R.string.def_cut_blt_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_y1), Float.parseFloat(context.getString(R.string.def_cut_blt_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_y2), Float.parseFloat(context.getString(R.string.def_cut_blt_y2))));

                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLT = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLT);
                break;
                
            case BLT:
                // BLT Name Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_name_x1), Float.parseFloat(context.getString(R.string.def_cut_blt_name_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_name_x2), Float.parseFloat(context.getString(R.string.def_cut_blt_name_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_name_y1), Float.parseFloat(context.getString(R.string.def_cut_blt_name_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_name_y2), Float.parseFloat(context.getString(R.string.def_cut_blt_name_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CCABuilding ccaBLTname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLTname);
                break;
                
            
                
            case BLT_PROGRESS:
                // BLT Progress Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_blt_progress_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_blt_progress_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_blt_progress_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_blt_progress_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLTprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLTprogress);
                break;
                
            case BLT_POINTS:
                // BLT Points Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_points_x1), Float.parseFloat(context.getString(R.string.def_cut_blt_points_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_points_x2), Float.parseFloat(context.getString(R.string.def_cut_blt_points_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_points_y1), Float.parseFloat(context.getString(R.string.def_cut_blt_points_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_points_y2), Float.parseFloat(context.getString(R.string.def_cut_blt_points_y2))));

                colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back};
                ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLTpoints = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLTpoints);
                break;
                
            case BLT_SLOTS:
                // BLT Slots Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_blt_slots_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_blt_slots_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_blt_slots_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_blt_slots_y2))));


                colors = new int[] {color_building_slot_main, color_building_slot_back};
                ths = new int[] {thm_building_slot, thp_building_slot};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLTslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBLTslots.needOcr = true;
                Bitmap[] ccaBLTslotsBitmapArray = PictureProcessor.splitBitmap(ccaBLTslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBLTslotsBitmapArray != null) {
                    if (ccaBLTslotsBitmapArray.length == 2) {
                        ccaBLTslots.bmpSrc = ccaBLTslotsBitmapArray[1];
                        ccaBLTslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBLTslots);
                break;



            case BLC_AREA:
                // BLC Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_x1), Float.parseFloat(context.getString(R.string.def_cut_blc_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_x2), Float.parseFloat(context.getString(R.string.def_cut_blc_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_y1), Float.parseFloat(context.getString(R.string.def_cut_blc_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_y2), Float.parseFloat(context.getString(R.string.def_cut_blc_y2))));

                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLC = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLC);
                break;

            case BLC:
                // BLC Name Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_name_x1), Float.parseFloat(context.getString(R.string.def_cut_blc_name_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_name_x2), Float.parseFloat(context.getString(R.string.def_cut_blc_name_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_name_y1), Float.parseFloat(context.getString(R.string.def_cut_blc_name_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_name_y2), Float.parseFloat(context.getString(R.string.def_cut_blc_name_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CCABuilding ccaBLCname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLCname);
                break;

            case BLC_PROGRESS:
                // BLC Progress Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_blc_progress_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_blc_progress_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_blc_progress_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_blc_progress_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLCprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLCprogress);
                break;

            case BLC_POINTS:
                // BLC Points Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_points_x1), Float.parseFloat(context.getString(R.string.def_cut_blc_points_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_points_x2), Float.parseFloat(context.getString(R.string.def_cut_blc_points_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_points_y1), Float.parseFloat(context.getString(R.string.def_cut_blc_points_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_points_y2), Float.parseFloat(context.getString(R.string.def_cut_blc_points_y2))));

                colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back};
                ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLCpoints = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLCpoints);
                break;

            case BLC_SLOTS:
                // BLC Slots Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_blc_slots_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_blc_slots_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_blc_slots_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_blc_slots_y2))));


                colors = new int[] {color_building_slot_main, color_building_slot_back};
                ths = new int[] {thm_building_slot, thp_building_slot};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLCslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBLCslots.needOcr = true;
                Bitmap[] ccaBLCslotsBitmapArray = PictureProcessor.splitBitmap(ccaBLCslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBLCslotsBitmapArray != null) {
                    if (ccaBLCslotsBitmapArray.length == 2) {
                        ccaBLCslots.bmpSrc = ccaBLCslotsBitmapArray[1];
                        ccaBLCslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBLCslots);
                break;



            case BLB_AREA:
                // BLB Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_x1), Float.parseFloat(context.getString(R.string.def_cut_blb_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_x2), Float.parseFloat(context.getString(R.string.def_cut_blb_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_y1), Float.parseFloat(context.getString(R.string.def_cut_blb_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_y2), Float.parseFloat(context.getString(R.string.def_cut_blb_y2))));

                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLB = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLB);
                break;

            case BLB:
                // BLB Name Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_name_x1), Float.parseFloat(context.getString(R.string.def_cut_blb_name_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_name_x2), Float.parseFloat(context.getString(R.string.def_cut_blb_name_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_name_y1), Float.parseFloat(context.getString(R.string.def_cut_blb_name_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_name_y2), Float.parseFloat(context.getString(R.string.def_cut_blb_name_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CCABuilding ccaBLBname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLBname);
                break;

            case BLB_PROGRESS:
                // BLB Progress Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_blb_progress_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_blb_progress_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_blb_progress_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_blb_progress_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLBprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLBprogress);
                break;

            case BLB_POINTS:
                // BLB Points Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_points_x1), Float.parseFloat(context.getString(R.string.def_cut_blb_points_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_points_x2), Float.parseFloat(context.getString(R.string.def_cut_blb_points_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_points_y1), Float.parseFloat(context.getString(R.string.def_cut_blb_points_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_points_y2), Float.parseFloat(context.getString(R.string.def_cut_blb_points_y2))));

                colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back};
                ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLBpoints = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLBpoints);
                break;

            case BLB_SLOTS:
                // BLB Slots Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_blb_slots_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_blb_slots_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_blb_slots_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_blb_slots_y2))));


                colors = new int[] {color_building_slot_main, color_building_slot_back};
                ths = new int[] {thm_building_slot, thp_building_slot};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLBslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBLBslots.needOcr = true;
                Bitmap[] ccaBLBslotsBitmapArray = PictureProcessor.splitBitmap(ccaBLBslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBLBslotsBitmapArray != null) {
                    if (ccaBLBslotsBitmapArray.length == 2) {
                        ccaBLBslots.bmpSrc = ccaBLBslotsBitmapArray[1];
                        ccaBLBslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBLBslots);
                break;



            case BRT_AREA:
                // BRT Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_x1), Float.parseFloat(context.getString(R.string.def_cut_brt_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_x2), Float.parseFloat(context.getString(R.string.def_cut_brt_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_y1), Float.parseFloat(context.getString(R.string.def_cut_brt_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_y2), Float.parseFloat(context.getString(R.string.def_cut_brt_y2))));

                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRT = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRT);
                break;

            case BRT:
                // BRT Name Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_name_x1), Float.parseFloat(context.getString(R.string.def_cut_brt_name_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_name_x2), Float.parseFloat(context.getString(R.string.def_cut_brt_name_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_name_y1), Float.parseFloat(context.getString(R.string.def_cut_brt_name_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_name_y2), Float.parseFloat(context.getString(R.string.def_cut_brt_name_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CCABuilding ccaBRTname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRTname);
                break;

            case BRT_PROGRESS:
                // BRT Progress Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_brt_progress_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_brt_progress_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_brt_progress_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_brt_progress_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRTprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRTprogress);
                break;

            case BRT_POINTS:
                // BRT Points Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_points_x1), Float.parseFloat(context.getString(R.string.def_cut_brt_points_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_points_x2), Float.parseFloat(context.getString(R.string.def_cut_brt_points_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_points_y1), Float.parseFloat(context.getString(R.string.def_cut_brt_points_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_points_y2), Float.parseFloat(context.getString(R.string.def_cut_brt_points_y2))));

                colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back};
                ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRTpoints = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRTpoints);
                break;

            case BRT_SLOTS:
                // BRT Slots Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_brt_slots_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_brt_slots_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_brt_slots_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_brt_slots_y2))));


                colors = new int[] {color_building_slot_main, color_building_slot_back};
                ths = new int[] {thm_building_slot, thp_building_slot};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRTslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBRTslots.needOcr = true;
                Bitmap[] ccaBRTslotsBitmapArray = PictureProcessor.splitBitmap(ccaBRTslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBRTslotsBitmapArray != null) {
                    if (ccaBRTslotsBitmapArray.length == 2) {
                        ccaBRTslots.bmpSrc = ccaBRTslotsBitmapArray[1];
                        ccaBRTslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBRTslots);
                break;



            case BRC_AREA:
                // BRC Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_x1), Float.parseFloat(context.getString(R.string.def_cut_brc_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_x2), Float.parseFloat(context.getString(R.string.def_cut_brc_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_y1), Float.parseFloat(context.getString(R.string.def_cut_brc_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_y2), Float.parseFloat(context.getString(R.string.def_cut_brc_y2))));

                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRC = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRC);
                break;

            case BRC:
                // BRC Name Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_name_x1), Float.parseFloat(context.getString(R.string.def_cut_brc_name_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_name_x2), Float.parseFloat(context.getString(R.string.def_cut_brc_name_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_name_y1), Float.parseFloat(context.getString(R.string.def_cut_brc_name_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_name_y2), Float.parseFloat(context.getString(R.string.def_cut_brc_name_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CCABuilding ccaBRCname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRCname);
                break;

            case BRC_PROGRESS:
                // BRC Progress Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_brc_progress_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_brc_progress_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_brc_progress_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_brc_progress_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRCprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRCprogress);
                break;

            case BRC_POINTS:
                // BRC Points Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_points_x1), Float.parseFloat(context.getString(R.string.def_cut_brc_points_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_points_x2), Float.parseFloat(context.getString(R.string.def_cut_brc_points_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_points_y1), Float.parseFloat(context.getString(R.string.def_cut_brc_points_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_points_y2), Float.parseFloat(context.getString(R.string.def_cut_brc_points_y2))));

                colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back};
                ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRCpoints = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRCpoints);
                break;

            case BRC_SLOTS:
                // BRC Slots Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_brc_slots_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_brc_slots_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_brc_slots_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_brc_slots_y2))));


                colors = new int[] {color_building_slot_main, color_building_slot_back};
                ths = new int[] {thm_building_slot, thp_building_slot};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRCslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBRCslots.needOcr = true;
                Bitmap[] ccaBRCslotsBitmapArray = PictureProcessor.splitBitmap(ccaBRCslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBRCslotsBitmapArray != null) {
                    if (ccaBRCslotsBitmapArray.length == 2) {
                        ccaBRCslots.bmpSrc = ccaBRCslotsBitmapArray[1];
                        ccaBRCslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBRCslots);
                break;



            case BRB_AREA:
                // BRB Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_x1), Float.parseFloat(context.getString(R.string.def_cut_brb_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_x2), Float.parseFloat(context.getString(R.string.def_cut_brb_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_y1), Float.parseFloat(context.getString(R.string.def_cut_brb_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_y2), Float.parseFloat(context.getString(R.string.def_cut_brb_y2))));

                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRB = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRB);
                break;

            case BRB:
                // BRB Name Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_name_x1), Float.parseFloat(context.getString(R.string.def_cut_brb_name_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_name_x2), Float.parseFloat(context.getString(R.string.def_cut_brb_name_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_name_y1), Float.parseFloat(context.getString(R.string.def_cut_brb_name_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_name_y2), Float.parseFloat(context.getString(R.string.def_cut_brb_name_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CCABuilding ccaBRBname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRBname);
                break;

            case BRB_PROGRESS:
                // BRB Progress Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_brb_progress_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_brb_progress_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_brb_progress_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_brb_progress_y2))));

                colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRBprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRBprogress);
                break;

            case BRB_POINTS:
                // BRB Points Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_points_x1), Float.parseFloat(context.getString(R.string.def_cut_brb_points_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_points_x2), Float.parseFloat(context.getString(R.string.def_cut_brb_points_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_points_y1), Float.parseFloat(context.getString(R.string.def_cut_brb_points_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_points_y2), Float.parseFloat(context.getString(R.string.def_cut_brb_points_y2))));

                colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back};
                ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRBpoints = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRBpoints);
                break;

            case BRB_SLOTS:
                // BRB Slots Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_brb_slots_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_brb_slots_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_brb_slots_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_brb_slots_y2))));


                colors = new int[] {color_building_slot_main, color_building_slot_back};
                ths = new int[] {thm_building_slot, thp_building_slot};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRBslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBRBslots.needOcr = true;
                Bitmap[] ccaBRBslotsBitmapArray = PictureProcessor.splitBitmap(ccaBRBslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBRBslotsBitmapArray != null) {
                    if (ccaBRBslotsBitmapArray.length == 2) {
                        ccaBRBslots.bmpSrc = ccaBRBslotsBitmapArray[1];
                        ccaBRBslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBRBslots);
                break;


            case CAR_BOX:
                // Car Box Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_box_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_box_x1), Float.parseFloat(context.getString(R.string.def_cut_car_box_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_box_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_box_x2), Float.parseFloat(context.getString(R.string.def_cut_car_box_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_box_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_box_y1), Float.parseFloat(context.getString(R.string.def_cut_car_box_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_box_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_box_y2), Float.parseFloat(context.getString(R.string.def_cut_car_box_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarBox = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarBox);
                break;

            case CAR_INFO:
                // Car Info Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_info_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_info_x1), Float.parseFloat(context.getString(R.string.def_cut_car_info_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_info_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_info_x2), Float.parseFloat(context.getString(R.string.def_cut_car_info_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_info_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_info_y1), Float.parseFloat(context.getString(R.string.def_cut_car_info_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_info_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_info_y2), Float.parseFloat(context.getString(R.string.def_cut_car_info_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarInfo = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarInfo);
                break;

            case CAR_SLOT1:
                // Car Slot1 Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot1_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot1_x1), Float.parseFloat(context.getString(R.string.def_cut_car_slot1_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot1_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot1_x2), Float.parseFloat(context.getString(R.string.def_cut_car_slot1_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot1_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot1_y1), Float.parseFloat(context.getString(R.string.def_cut_car_slot1_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot1_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot1_y2), Float.parseFloat(context.getString(R.string.def_cut_car_slot1_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarSlot1 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarSlot1);
                break;

            case CAR_SLOT2:
                // Car Slot2 Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot2_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot2_x1), Float.parseFloat(context.getString(R.string.def_cut_car_slot2_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot2_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot2_x2), Float.parseFloat(context.getString(R.string.def_cut_car_slot2_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot2_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot2_y1), Float.parseFloat(context.getString(R.string.def_cut_car_slot2_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot2_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot2_y2), Float.parseFloat(context.getString(R.string.def_cut_car_slot2_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarSlot2 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarSlot2);
                break;

            case CAR_SLOT3:
                // Car Slot3 Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot3_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot3_x1), Float.parseFloat(context.getString(R.string.def_cut_car_slot3_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot3_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot3_x2), Float.parseFloat(context.getString(R.string.def_cut_car_slot3_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot3_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot3_y1), Float.parseFloat(context.getString(R.string.def_cut_car_slot3_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_slot3_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_slot3_y2), Float.parseFloat(context.getString(R.string.def_cut_car_slot3_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarSlot3 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarSlot3);
                break;

            case CAR_HEALTH:
                // Car Health Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_health_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_health_x1), Float.parseFloat(context.getString(R.string.def_cut_car_health_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_health_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_health_x2), Float.parseFloat(context.getString(R.string.def_cut_car_health_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_health_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_health_y1), Float.parseFloat(context.getString(R.string.def_cut_car_health_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_health_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_health_y2), Float.parseFloat(context.getString(R.string.def_cut_car_health_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarHealth = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarHealth);
                break;

            case CAR_SHIELD:
                // Car Shield Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_shield_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_shield_x1), Float.parseFloat(context.getString(R.string.def_cut_car_shield_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_shield_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_shield_x2), Float.parseFloat(context.getString(R.string.def_cut_car_shield_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_shield_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_shield_y1), Float.parseFloat(context.getString(R.string.def_cut_car_shield_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_shield_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_shield_y2), Float.parseFloat(context.getString(R.string.def_cut_car_shield_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarShield = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarShield);
                break;

            case CAR_STATE:
                // Car State Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_state_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_state_x1), Float.parseFloat(context.getString(R.string.def_cut_car_state_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_state_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_state_x2), Float.parseFloat(context.getString(R.string.def_cut_car_state_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_state_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_state_y1), Float.parseFloat(context.getString(R.string.def_cut_car_state_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_state_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_state_y2), Float.parseFloat(context.getString(R.string.def_cut_car_state_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarState = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarState);
                break;

            case CAR_HEALBOX:
                // Car Healbox Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_healbox_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_healbox_x1), Float.parseFloat(context.getString(R.string.def_cut_car_healbox_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_healbox_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_healbox_x2), Float.parseFloat(context.getString(R.string.def_cut_car_healbox_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_healbox_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_healbox_y1), Float.parseFloat(context.getString(R.string.def_cut_car_healbox_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_healbox_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_healbox_y2), Float.parseFloat(context.getString(R.string.def_cut_car_healbox_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarHealbox = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarHealbox);
                break;

            case CAR_TIMEBOX:
                // Car Timebox Area
                x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_timebox_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_timebox_x1), Float.parseFloat(context.getString(R.string.def_cut_car_timebox_x1))));
                x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_timebox_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_timebox_x2), Float.parseFloat(context.getString(R.string.def_cut_car_timebox_x2))));
                y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_timebox_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_timebox_y1), Float.parseFloat(context.getString(R.string.def_cut_car_timebox_y1))));
                y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_car_timebox_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_car_timebox_y2), Float.parseFloat(context.getString(R.string.def_cut_car_timebox_y2))));
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarTimebox = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarTimebox);
                break;


            default:
        }
        
        
        
    }
    
    // конструктор
    public CityCalc(CityCalc checkedCityCalc) {

        String logMsgPref = "конструктор: ";
        Log.i(TAG, logMsgPref + "start");

        CityCalcType cityCalcType = checkedCityCalc.cityCalcType;
        File file = checkedCityCalc.fileScreenshot;
        int calibrateX = checkedCityCalc.calibrateX;
        int calibrateY = checkedCityCalc.calibrateY;
        Context context = checkedCityCalc.context;

        this.cityCalcType = cityCalcType;
        this.fileScreenshot = file;
        this.bmpScreenshot = checkedCityCalc.bmpScreenshot;
        this.calibrateX = calibrateX;
        this.calibrateY = calibrateY;
        this.context = context;

        initVariables();

        thisCityCalc = this;

        try {
            if (fileScreenshot != null) {         // если файл не нулл
                if (fileScreenshot.exists()) {    // если файл физически существует

                    if (cityCalcType.equals(CityCalcType.GAME)) {
                        setAreaToMap(Area.BOX_INFO);
                        setAreaToMap(Area.CITY);
                        setAreaToMap(Area.TOTAL_TIME);
                        setAreaToMap(Area.EARLY_WIN);
                        setAreaToMap(Area.POINTS_AND_INCREASE_OUR);
                        setAreaToMap(Area.TEAM_NAME_OUR);
                        setAreaToMap(Area.POINTS_AND_INCREASE_ENEMY);
                        setAreaToMap(Area.TEAM_NAME_ENEMY);
                        setAreaToMap(Area.BLT_AREA);
                        setAreaToMap(Area.BLT);
                        setAreaToMap(Area.BLT_PROGRESS);
                        setAreaToMap(Area.BLT_POINTS);
                        setAreaToMap(Area.BLT_SLOTS);
                        setAreaToMap(Area.BLC_AREA);
                        setAreaToMap(Area.BLC);
                        setAreaToMap(Area.BLC_PROGRESS);
                        setAreaToMap(Area.BLC_POINTS);
                        setAreaToMap(Area.BLC_SLOTS);
                        setAreaToMap(Area.BLB_AREA);
                        setAreaToMap(Area.BLB);
                        setAreaToMap(Area.BLB_PROGRESS);
                        setAreaToMap(Area.BLB_POINTS);
                        setAreaToMap(Area.BLB_SLOTS);
                        setAreaToMap(Area.BRT_AREA);
                        setAreaToMap(Area.BRT);
                        setAreaToMap(Area.BRT_PROGRESS);
                        setAreaToMap(Area.BRT_POINTS);
                        setAreaToMap(Area.BRT_SLOTS);
                        setAreaToMap(Area.BRC_AREA);
                        setAreaToMap(Area.BRC);
                        setAreaToMap(Area.BRC_PROGRESS);
                        setAreaToMap(Area.BRC_POINTS);
                        setAreaToMap(Area.BRC_SLOTS);
                        setAreaToMap(Area.BRB_AREA);
                        setAreaToMap(Area.BRB);
                        setAreaToMap(Area.BRB_PROGRESS);
                        setAreaToMap(Area.BRB_POINTS);
                        setAreaToMap(Area.BRB_SLOTS);

                        ((CCABuilding)mapAreas.get(Area.BLT)).calc(mapAreas.get(Area.BLT_POINTS), mapAreas.get(Area.BLT_SLOTS), mapAreas.get(Area.BLT_PROGRESS));
                        ((CCABuilding)mapAreas.get(Area.BLC)).calc(mapAreas.get(Area.BLC_POINTS), mapAreas.get(Area.BLC_SLOTS), mapAreas.get(Area.BLC_PROGRESS));
                        ((CCABuilding)mapAreas.get(Area.BLB)).calc(mapAreas.get(Area.BLB_POINTS), mapAreas.get(Area.BLB_SLOTS), mapAreas.get(Area.BLB_PROGRESS));
                        ((CCABuilding)mapAreas.get(Area.BRT)).calc(mapAreas.get(Area.BRT_POINTS), mapAreas.get(Area.BRT_SLOTS), mapAreas.get(Area.BRT_PROGRESS));
                        ((CCABuilding)mapAreas.get(Area.BRC)).calc(mapAreas.get(Area.BRC_POINTS), mapAreas.get(Area.BRC_SLOTS), mapAreas.get(Area.BRC_PROGRESS));
                        ((CCABuilding)mapAreas.get(Area.BRB)).calc(mapAreas.get(Area.BRB_POINTS), mapAreas.get(Area.BRB_SLOTS), mapAreas.get(Area.BRB_PROGRESS));

                        ((CCATeam)mapAreas.get(Area.TEAM_NAME_OUR)).ccatIncrease =
                                (((CCABuilding)mapAreas.get(Area.BLT)).isPresent ? ((CCABuilding)mapAreas.get(Area.BLT)).our_points : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BLC)).isPresent ? ((CCABuilding)mapAreas.get(Area.BLC)).our_points : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BLB)).isPresent ? ((CCABuilding)mapAreas.get(Area.BLB)).our_points : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRT)).isPresent ? ((CCABuilding)mapAreas.get(Area.BRT)).our_points : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRC)).isPresent ? ((CCABuilding)mapAreas.get(Area.BRC)).our_points : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRB)).isPresent ? ((CCABuilding)mapAreas.get(Area.BRB)).our_points : 0);

                        ((CCATeam)mapAreas.get(Area.TEAM_NAME_ENEMY)).ccatIncrease =
                                (((CCABuilding)mapAreas.get(Area.BLT)).isPresent ? ((CCABuilding)mapAreas.get(Area.BLT)).enemy_points : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BLC)).isPresent ? ((CCABuilding)mapAreas.get(Area.BLC)).enemy_points : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BLB)).isPresent ? ((CCABuilding)mapAreas.get(Area.BLB)).enemy_points : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRT)).isPresent ? ((CCABuilding)mapAreas.get(Area.BRT)).enemy_points : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRC)).isPresent ? ((CCABuilding)mapAreas.get(Area.BRC)).enemy_points : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRB)).isPresent ? ((CCABuilding)mapAreas.get(Area.BRB)).enemy_points : 0);

                        ((CCAGame)mapAreas.get(Area.CITY)).calc();
                        ((CCAGame)mapAreas.get(Area.CITY)).calcWin();

                    } else if (cityCalcType.equals(CityCalcType.CAR)) {

                        setAreaToMap(Area.CAR_BOX);
                        setAreaToMap(Area.CAR_INFO);
                        setAreaToMap(Area.CAR_SLOT1);
                        setAreaToMap(Area.CAR_SLOT2);
                        setAreaToMap(Area.CAR_SLOT3);
                        setAreaToMap(Area.CAR_HEALTH);
                        setAreaToMap(Area.CAR_SHIELD);
                        setAreaToMap(Area.CAR_STATE);
                        setAreaToMap(Area.CAR_HEALBOX);
                        setAreaToMap(Area.CAR_TIMEBOX);


                    } else if (cityCalcType.equals(CityCalcType.BORDERS)) {

                        setAreaToMap(Area.BOX_INFO);
                        setAreaToMap(Area.CITY);
                        setAreaToMap(Area.TOTAL_TIME);
                        setAreaToMap(Area.EARLY_WIN);
                        setAreaToMap(Area.POINTS_AND_INCREASE_OUR);
                        setAreaToMap(Area.TEAM_NAME_OUR);
                        setAreaToMap(Area.POINTS_AND_INCREASE_ENEMY);
                        setAreaToMap(Area.TEAM_NAME_ENEMY);
                        setAreaToMap(Area.BLT_AREA);
                        setAreaToMap(Area.BLT);
                        setAreaToMap(Area.BLT_PROGRESS);
                        setAreaToMap(Area.BLT_POINTS);
                        setAreaToMap(Area.BLT_SLOTS);
                        setAreaToMap(Area.BLC_AREA);
                        setAreaToMap(Area.BLC);
                        setAreaToMap(Area.BLC_PROGRESS);
                        setAreaToMap(Area.BLC_POINTS);
                        setAreaToMap(Area.BLC_SLOTS);
                        setAreaToMap(Area.BLB_AREA);
                        setAreaToMap(Area.BLB);
                        setAreaToMap(Area.BLB_PROGRESS);
                        setAreaToMap(Area.BLB_POINTS);
                        setAreaToMap(Area.BLB_SLOTS);
                        setAreaToMap(Area.BRT_AREA);
                        setAreaToMap(Area.BRT);
                        setAreaToMap(Area.BRT_PROGRESS);
                        setAreaToMap(Area.BRT_POINTS);
                        setAreaToMap(Area.BRT_SLOTS);
                        setAreaToMap(Area.BRC_AREA);
                        setAreaToMap(Area.BRC);
                        setAreaToMap(Area.BRC_PROGRESS);
                        setAreaToMap(Area.BRC_POINTS);
                        setAreaToMap(Area.BRC_SLOTS);
                        setAreaToMap(Area.BRB_AREA);
                        setAreaToMap(Area.BRB);
                        setAreaToMap(Area.BRB_PROGRESS);
                        setAreaToMap(Area.BRB_POINTS);
                        setAreaToMap(Area.BRB_SLOTS);
                        setAreaToMap(Area.CAR_BOX);
                        setAreaToMap(Area.CAR_INFO);
                        setAreaToMap(Area.CAR_SLOT1);
                        setAreaToMap(Area.CAR_SLOT2);
                        setAreaToMap(Area.CAR_SLOT3);
                        setAreaToMap(Area.CAR_HEALTH);
                        setAreaToMap(Area.CAR_SHIELD);
                        setAreaToMap(Area.CAR_STATE);
                        setAreaToMap(Area.CAR_HEALBOX);
                        setAreaToMap(Area.CAR_TIMEBOX);

                    } else if (cityCalcType.equals(CityCalcType.COLORS)) {

                        setAreaToMap(Area.BOX_INFO);
                        setAreaToMap(Area.CITY);
                        setAreaToMap(Area.TOTAL_TIME);
                        setAreaToMap(Area.EARLY_WIN);
                        setAreaToMap(Area.POINTS_AND_INCREASE_OUR);
                        setAreaToMap(Area.TEAM_NAME_OUR);
                        setAreaToMap(Area.POINTS_AND_INCREASE_ENEMY);
                        setAreaToMap(Area.TEAM_NAME_ENEMY);
                        setAreaToMap(Area.BLT_AREA);
                        setAreaToMap(Area.BLT);
                        setAreaToMap(Area.BLT_PROGRESS);
                        setAreaToMap(Area.BLT_POINTS);
                        setAreaToMap(Area.BLT_SLOTS);
                        setAreaToMap(Area.BLC_AREA);
                        setAreaToMap(Area.BLC);
                        setAreaToMap(Area.BLC_PROGRESS);
                        setAreaToMap(Area.BLC_POINTS);
                        setAreaToMap(Area.BLC_SLOTS);
                        setAreaToMap(Area.BLB_AREA);
                        setAreaToMap(Area.BLB);
                        setAreaToMap(Area.BLB_PROGRESS);
                        setAreaToMap(Area.BLB_POINTS);
                        setAreaToMap(Area.BLB_SLOTS);
                        setAreaToMap(Area.BRT_AREA);
                        setAreaToMap(Area.BRT);
                        setAreaToMap(Area.BRT_PROGRESS);
                        setAreaToMap(Area.BRT_POINTS);
                        setAreaToMap(Area.BRT_SLOTS);
                        setAreaToMap(Area.BRC_AREA);
                        setAreaToMap(Area.BRC);
                        setAreaToMap(Area.BRC_PROGRESS);
                        setAreaToMap(Area.BRC_POINTS);
                        setAreaToMap(Area.BRC_SLOTS);
                        setAreaToMap(Area.BRB_AREA);
                        setAreaToMap(Area.BRB);
                        setAreaToMap(Area.BRB_PROGRESS);
                        setAreaToMap(Area.BRB_POINTS);
                        setAreaToMap(Area.BRB_SLOTS);
                        setAreaToMap(Area.CAR_BOX);
                        setAreaToMap(Area.CAR_INFO);
                        setAreaToMap(Area.CAR_SLOT1);
                        setAreaToMap(Area.CAR_SLOT2);
                        setAreaToMap(Area.CAR_SLOT3);
                        setAreaToMap(Area.CAR_HEALTH);
                        setAreaToMap(Area.CAR_SHIELD);
                        setAreaToMap(Area.CAR_STATE);
                        setAreaToMap(Area.CAR_HEALBOX);
                        setAreaToMap(Area.CAR_TIMEBOX);

                    } else if (cityCalcType.equals(CityCalcType.CALIBRATE)) {

                        setAreaToMap(Area.CITY);

                    }


                } // кесли файл физически существует
            } // кесли файл не нулл

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
