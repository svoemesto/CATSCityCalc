package com.svoemestodev.catscitycalc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CityCalc extends Activity {

    File fileScreenshot;
    Bitmap bmpScreenshot;       // исходный скриншот
    int calibrateX;             // сдвиг центра по X
    int calibrateY;             // сдвиг центра по Y
    Map<Area, CityCalcArea> mapAreas = new HashMap<>(); // мап областей
    Context context;            // контекст

    // конструктор для калибровки центра экрана
    public CityCalc(CityCalc mainCityCalc, int calibrateX, int calibrateY) {
        this.fileScreenshot = mainCityCalc.fileScreenshot;
        this.bmpScreenshot = mainCityCalc.bmpScreenshot;
        this.calibrateX = calibrateX;
        this.calibrateY = calibrateY;
        this.context = mainCityCalc.context;

        Context context = this.context;

        Area area;          // идентификатор области
        float x1;           // x1
        float x2;           // x2
        float y1;           // y1
        float y2;           // y2
        int [] colors;      // цвета
        int [] ths;         // пороги
        boolean needOcr;    // надо распознавать
        CityCalcArea cca;   // область

        if (this.bmpScreenshot != null) {

            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);

            // City Area
            area = Area.CITY;
            x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_x1), Float.parseFloat(context.getString(R.string.def_cut_city_x1))));
            x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_x2), Float.parseFloat(context.getString(R.string.def_cut_city_x2))));
            y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_y1), Float.parseFloat(context.getString(R.string.def_cut_city_y1))));
            y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_y2), Float.parseFloat(context.getString(R.string.def_cut_city_y2))));
            colors = null;
            ths = null;
            needOcr = false;
            CCAGame ccaGame = new CCAGame(this, area, x1, x2, y1, y2, colors, ths, needOcr);
            mapAreas.put(area, ccaGame);

        }



    }

    // конструктор
    public CityCalc(File file, int calibrateX, int calibrateY, Context context) {

        this.fileScreenshot = file;
        this.calibrateX = calibrateX;
        this.calibrateY = calibrateY;
        this.context = context;

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);
        String languageToLoad = sharedPreferences.getString(context.getResources().getString(R.string.pref_language_interface),sharedPreferences.getString(context.getResources().getString(R.string.pref_def_language_interface),"en"));
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.context.getResources().updateConfiguration(config, this.context.getResources().getDisplayMetrics());

        Area area;          // идентификатор области
        float x1;           // x1
        float x2;           // x2
        float y1;           // y1
        float y2;           // y2
        int [] colors;      // цвета
        int [] ths;         // пороги
        boolean needOcr;    // надо распознавать
        CityCalcArea cca;   // область

        int color_main, color_back1, color_back2, thm, thp;
        
        try {
            if (file != null) {         // если файл не нулл
                if (file.exists()) {    // если файл физически существует

//                    SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);
                    
                    this.bmpScreenshot = BitmapFactory.decodeFile(file.getAbsolutePath());   // загружаем битмап из файла скриншота
                    // City Area
                    area = Area.CITY;
                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_x1), Float.parseFloat(context.getString(R.string.def_cut_city_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_x2), Float.parseFloat(context.getString(R.string.def_cut_city_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_y1), Float.parseFloat(context.getString(R.string.def_cut_city_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_city_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_city_y2), Float.parseFloat(context.getString(R.string.def_cut_city_y2))));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCAGame ccaGame = new CCAGame(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaGame);

                    // Total Time Area
                    area = Area.TOTAL_TIME;
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
                    CityCalcArea ccaTotalTime = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaTotalTime);

                    // Early Win Area
                    area = Area.EARLY_WIN;
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
                    CityCalcArea ccaEarlyWin = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaEarlyWin);

                    ccaGame.calc();


                    // Points And Increase Our Area
                    area = Area.POINTS_AND_INCREASE_OUR;
                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_our_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_our_x1), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_our_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_our_x2), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_our_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_our_y1), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_our_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_our_y2), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_y2))));

                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_our_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_our_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_our_back1), 16)));

                    thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_thm), Integer.parseInt(context.getString(R.string.def_rgb_points_our_thm))));
                    thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_thp), Integer.parseInt(context.getString(R.string.def_rgb_points_our_thp))));
                    
                    Bitmap[] ourPointsAndIncreaseBitmapArray = PictureProcessor.splitBitmap(cca.bmpSrc, color_back1, thm, thp, 0.85f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT);

                    CityCalcArea ccaPointsOur = null;
                    CityCalcArea ccaIncreaseOur = null;

                    if (ourPointsAndIncreaseBitmapArray != null) {

                        area = Area.POINTS_OUR;

                        color_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_main), (int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16)));
                        color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_points_our_back1), 16)));
                        color_back2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_back2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_back2), (int)Long.parseLong(context.getString(R.string.def_rgb_points_our_back2), 16)));

                        thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_thm), Integer.parseInt(context.getString(R.string.def_rgb_points_our_thm))));
                        thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_our_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_our_thp), Integer.parseInt(context.getString(R.string.def_rgb_points_our_thp))));

                        colors = new int[] {color_main, color_back1, color_back2};
                        ths = new int[] {thm, thp};

                        needOcr = true;
                        ccaPointsOur = new CityCalcArea(this, area, colors, ths, needOcr);
                        ccaPointsOur.bmpSrc = ourPointsAndIncreaseBitmapArray[1];
                        ccaPointsOur.doOCR();
                        mapAreas.put(area, ccaPointsOur);

                        area = Area.INCREASE_OUR;

                        color_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_our_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_our_main), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_our_main), 16)));
                        color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_our_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_our_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_our_back1), 16)));

                        thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_our_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_our_thm), Integer.parseInt(context.getString(R.string.def_rgb_increase_our_thm))));
                        thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_our_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_our_thp), Integer.parseInt(context.getString(R.string.def_rgb_increase_our_thp))));

                        colors = new int[] {color_main, color_back1};
                        ths = new int[] {thm, thp};

                        needOcr = true;
                        ccaIncreaseOur = new CityCalcArea(this, area, colors, ths, needOcr);
                        ccaIncreaseOur.bmpSrc = ourPointsAndIncreaseBitmapArray[0];
                        ccaIncreaseOur.doOCR();
                        mapAreas.put(area, ccaIncreaseOur);

                    }

                    // Team Name Our Area
                    area = Area.TEAM_NAME_OUR;
                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_our_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_our_x1), Float.parseFloat(context.getString(R.string.def_cut_team_name_our_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_our_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_our_x2), Float.parseFloat(context.getString(R.string.def_cut_team_name_our_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_our_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_our_y1), Float.parseFloat(context.getString(R.string.def_cut_team_name_our_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_our_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_our_y2), Float.parseFloat(context.getString(R.string.def_cut_team_name_our_y2))));

                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCATeam ccaOurTeam = new CCATeam(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaOurTeam);


                    // Points And Increase Enemy Area
                    area = Area.POINTS_AND_INCREASE_ENEMY;
                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_enemy_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_enemy_x1), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_enemy_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_enemy_x2), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_enemy_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_enemy_y1), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_points_and_increase_enemy_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_points_and_increase_enemy_y2), Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_y2))));
                    
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_enemy_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_enemy_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_enemy_back1), 16)));

                    thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_thm), Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thm))));
                    thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_thp), Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thp))));

                    Bitmap[] enemyPointsAndIncreaseBitmapArray = PictureProcessor.splitBitmap(cca.bmpSrc, color_back1, thm, thp, 0.85f, 0.0f, PictureProcessorDirection.FROM_RIGHT_TO_LEFT);

                    CityCalcArea ccaPointsEnemy = null;
                    CityCalcArea ccaIncreaseEnemy = null;

                    if (ourPointsAndIncreaseBitmapArray != null) {

                        area = Area.POINTS_ENEMY;

                        color_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_main), (int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16)));
                        color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_back1), 16)));
                        color_back2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_back2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_back2), (int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_back2), 16)));

                        thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_thm), Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thm))));
                        thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_points_enemy_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_points_enemy_thp), Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thp))));

                        colors = new int[] {color_main, color_back1, color_back2};
                        ths = new int[] {thm, thp};
                        
                        needOcr = true;
                        ccaPointsEnemy = new CityCalcArea(this, area, colors, ths, needOcr);
                        ccaPointsEnemy.bmpSrc = enemyPointsAndIncreaseBitmapArray[1];
                        ccaPointsEnemy.doOCR();
                        mapAreas.put(area, ccaPointsEnemy);

                        area = Area.INCREASE_ENEMY;

                        color_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_enemy_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_enemy_main), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_enemy_main), 16)));
                        color_back1 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_enemy_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_enemy_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_enemy_back1), 16)));

                        thm = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_enemy_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_enemy_thm), Integer.parseInt(context.getString(R.string.def_rgb_increase_enemy_thm))));
                        thp = sharedPreferences.getInt(context.getString(R.string.pref_rgb_increase_enemy_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_increase_enemy_thp), Integer.parseInt(context.getString(R.string.def_rgb_increase_enemy_thp))));

                        colors = new int[] {color_main, color_back1};
                        ths = new int[] {thm, thp};
                        
                        needOcr = true;
                        ccaIncreaseEnemy = new CityCalcArea(this, area, colors, ths, needOcr);
                        ccaIncreaseEnemy.bmpSrc = enemyPointsAndIncreaseBitmapArray[0];
                        ccaIncreaseEnemy.doOCR();
                        mapAreas.put(area, ccaIncreaseEnemy);

                    }

                    // Team Name Enemy Area
                    area = Area.TEAM_NAME_ENEMY;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_enemy_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_enemy_x1), Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_enemy_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_enemy_x2), Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_enemy_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_enemy_y1), Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_team_name_enemy_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_team_name_enemy_y2), Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_y2))));
                    
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCATeam ccaEnemyTeam = new CCATeam(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaEnemyTeam);

                    ccaGame.calcWin();

                    int color_progress_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_our),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_our), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our), 16)));
                    int color_progress_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_enemy),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_enemy), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy), 16)));
                    int color_progress_empty = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_empty),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_empty), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty), 16)));

                    int thm_progress_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_our_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_our_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thm))));
                    int thp_progress_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_our_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_our_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thp))));
                    int thm_progress_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_enemy_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_enemy_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thm))));
                    int thp_progress_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_enemy_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_enemy_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thp))));
                    int thm_progress_empty = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_empty_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_empty_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thm))));
                    int thp_progress_empty = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_progress_empty_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_progress_empty_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thp))));

                    int color_building_points_our_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_our_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_our_main), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16)));
                    int color_building_points_our_back = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_our_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_our_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back1), 16)));
                    int color_building_points_enemy_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_enemy_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_enemy_main), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16)));
                    int color_building_points_enemy_back = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_enemy_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_enemy_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back1), 16)));
                    int color_building_doublepoints = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_doublepoints),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_doublepoints), (int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints), 16)));
                    int color_building_mayX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_mayX2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_mayX2), (int)Long.parseLong(context.getString(R.string.def_rgb_building_mayX2), 16)));
                    int color_building_isX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_isX2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_isX2), (int)Long.parseLong(context.getString(R.string.def_rgb_building_isX2), 16)));

                    int thm_building_points_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_our_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_our_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm))));
                    int thp_building_points_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_our_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_our_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp))));
                    int thm_building_points_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_enemy_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_enemy_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm))));
                    int thp_building_points_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_points_enemy_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_points_enemy_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))));

                    int color_building_slot_main = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_slot_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_slot_main), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16)));
                    int color_building_slot_back = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_slot_back1),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_slot_back1), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back1), 16)));

                    int thm_building_slot = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_slot_thm),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_slot_thm), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm))));
                    int thp_building_slot = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_slot_thp),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_slot_thp), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))));
                    
                    
                    // BLT Area
                    area = Area.BLT_ARAE;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_x1), Float.parseFloat(context.getString(R.string.def_cut_blt_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_x2), Float.parseFloat(context.getString(R.string.def_cut_blt_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_y1), Float.parseFloat(context.getString(R.string.def_cut_blt_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_y2), Float.parseFloat(context.getString(R.string.def_cut_blt_y2))));
                    
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBLT = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLT);

                    // BLT Name Area
                    area = Area.BLT;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_name_x1), Float.parseFloat(context.getString(R.string.def_cut_blt_name_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_name_x2), Float.parseFloat(context.getString(R.string.def_cut_blt_name_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_name_y1), Float.parseFloat(context.getString(R.string.def_cut_blt_name_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_name_y2), Float.parseFloat(context.getString(R.string.def_cut_blt_name_y2))));
                    
                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    CCABuilding ccaBLTname = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths);
                    mapAreas.put(area, ccaBLTname);

                    // BLT Progress Area
                    area = Area.BLT_PROGRESS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_blt_progress_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_blt_progress_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_blt_progress_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_blt_progress_y2))));
                    
                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    needOcr = false;
                    CityCalcArea ccaBLTprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLTprogress);

                    // BLT Points Area
                    area = Area.BLT_POINTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_points_x1), Float.parseFloat(context.getString(R.string.def_cut_blt_points_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_points_x2), Float.parseFloat(context.getString(R.string.def_cut_blt_points_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_points_y1), Float.parseFloat(context.getString(R.string.def_cut_blt_points_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_points_y2), Float.parseFloat(context.getString(R.string.def_cut_blt_points_y2))));
                    
                    colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back};
                    ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                    needOcr = false;
                    CityCalcArea ccaBLTpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLTpoints);

                    // BLT Slots Area
                    area = Area.BLT_SLOTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_blt_slots_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_blt_slots_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_blt_slots_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blt_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blt_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_blt_slots_y2))));

                    
                    colors = new int[] {color_building_slot_main, color_building_slot_back};
                    ths = new int[] {thm_building_slot, thp_building_slot};
                    needOcr = false;
                    CityCalcArea ccaBLTslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    ccaBLTslots.needOcr = true;
                    Bitmap[] ccaBLTslotsBitmapArray = PictureProcessor.splitBitmap(ccaBLTslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT);
                    if (ccaBLTslotsBitmapArray != null) {
                        if (ccaBLTslotsBitmapArray.length == 2) {
                            ccaBLTslots.bmpSrc = ccaBLTslotsBitmapArray[1];
                            ccaBLTslots.doOCR();
                        }
                    }
                    mapAreas.put(area, ccaBLTslots);
                    
                    ccaBLTname.calc(ccaBLTpoints, ccaBLTslots, ccaBLTprogress);




                    // BLC Area
                    area = Area.BLC_ARAE;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_x1), Float.parseFloat(context.getString(R.string.def_cut_blc_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_x2), Float.parseFloat(context.getString(R.string.def_cut_blc_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_y1), Float.parseFloat(context.getString(R.string.def_cut_blc_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_y2), Float.parseFloat(context.getString(R.string.def_cut_blc_y2))));

                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBLC = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLC);

                    // BLC Name Area
                    area = Area.BLC;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_name_x1), Float.parseFloat(context.getString(R.string.def_cut_blc_name_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_name_x2), Float.parseFloat(context.getString(R.string.def_cut_blc_name_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_name_y1), Float.parseFloat(context.getString(R.string.def_cut_blc_name_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_name_y2), Float.parseFloat(context.getString(R.string.def_cut_blc_name_y2))));

                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    CCABuilding ccaBLCname = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths);
                    mapAreas.put(area, ccaBLCname);
                    
                    // BLC Progress Area
                    area = Area.BLC_PROGRESS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_blc_progress_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_blc_progress_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_blc_progress_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_blc_progress_y2))));

                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    needOcr = false;
                    CityCalcArea ccaBLCprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLCprogress);

                    // BLC Points Area
                    area = Area.BLC_POINTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_points_x1), Float.parseFloat(context.getString(R.string.def_cut_blc_points_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_points_x2), Float.parseFloat(context.getString(R.string.def_cut_blc_points_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_points_y1), Float.parseFloat(context.getString(R.string.def_cut_blc_points_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_points_y2), Float.parseFloat(context.getString(R.string.def_cut_blc_points_y2))));

                    colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back, color_building_doublepoints, color_building_mayX2, color_building_isX2};
                    ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                    needOcr = false;
                    CityCalcArea ccaBLCpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLCpoints);

                    // BLC Slots Area
                    area = Area.BLC_SLOTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_blc_slots_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_blc_slots_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_blc_slots_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blc_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blc_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_blc_slots_y2))));


                    colors = new int[] {color_building_slot_main, color_building_slot_back};
                    ths = new int[] {thm_building_slot, thp_building_slot};
                    needOcr = false;
                    CityCalcArea ccaBLCslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    ccaBLCslots.needOcr = true;
                    Bitmap[] ccaBLCslotsBitmapArray = PictureProcessor.splitBitmap(ccaBLCslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT);
                    if (ccaBLCslotsBitmapArray != null) {
                        if (ccaBLCslotsBitmapArray.length == 2) {
                            ccaBLCslots.bmpSrc = ccaBLCslotsBitmapArray[1];
                            ccaBLCslots.doOCR();
                        }
                    }
                    mapAreas.put(area, ccaBLCslots);

                    ccaBLCname.calc(ccaBLCpoints, ccaBLCslots, ccaBLCprogress);



                    // BLB Area
                    area = Area.BLB_ARAE;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_x1), Float.parseFloat(context.getString(R.string.def_cut_blb_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_x2), Float.parseFloat(context.getString(R.string.def_cut_blb_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_y1), Float.parseFloat(context.getString(R.string.def_cut_blb_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_y2), Float.parseFloat(context.getString(R.string.def_cut_blb_y2))));

                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBLB = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLB);

                    // BLB Name Area
                    area = Area.BLB;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_name_x1), Float.parseFloat(context.getString(R.string.def_cut_blb_name_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_name_x2), Float.parseFloat(context.getString(R.string.def_cut_blb_name_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_name_y1), Float.parseFloat(context.getString(R.string.def_cut_blb_name_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_name_y2), Float.parseFloat(context.getString(R.string.def_cut_blb_name_y2))));

                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    CCABuilding ccaBLBname = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths);
                    mapAreas.put(area, ccaBLBname);
                    
                    // BLB Progress Area
                    area = Area.BLB_PROGRESS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_blb_progress_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_blb_progress_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_blb_progress_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_blb_progress_y2))));

                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    needOcr = false;
                    CityCalcArea ccaBLBprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLBprogress);

                    // BLB Points Area
                    area = Area.BLB_POINTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_points_x1), Float.parseFloat(context.getString(R.string.def_cut_blb_points_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_points_x2), Float.parseFloat(context.getString(R.string.def_cut_blb_points_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_points_y1), Float.parseFloat(context.getString(R.string.def_cut_blb_points_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_points_y2), Float.parseFloat(context.getString(R.string.def_cut_blb_points_y2))));

                    colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back, color_building_doublepoints, color_building_mayX2, color_building_isX2};
                    ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                    needOcr = false;
                    CityCalcArea ccaBLBpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLBpoints);

                    // BLB Slots Area
                    area = Area.BLB_SLOTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_blb_slots_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_blb_slots_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_blb_slots_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_blb_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_blb_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_blb_slots_y2))));


                    colors = new int[] {color_building_slot_main, color_building_slot_back};
                    ths = new int[] {thm_building_slot, thp_building_slot};
                    needOcr = false;
                    CityCalcArea ccaBLBslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    ccaBLBslots.needOcr = true;
                    Bitmap[] ccaBLBslotsBitmapArray = PictureProcessor.splitBitmap(ccaBLBslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT);
                    if (ccaBLBslotsBitmapArray != null) {
                        if (ccaBLBslotsBitmapArray.length == 2) {
                            ccaBLBslots.bmpSrc = ccaBLBslotsBitmapArray[1];
                            ccaBLBslots.doOCR();
                        }
                    }
                    mapAreas.put(area, ccaBLBslots);

                    ccaBLBname.calc(ccaBLBpoints, ccaBLBslots, ccaBLBprogress);


                    // BRT Area
                    area = Area.BRT_ARAE;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_x1), Float.parseFloat(context.getString(R.string.def_cut_brt_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_x2), Float.parseFloat(context.getString(R.string.def_cut_brt_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_y1), Float.parseFloat(context.getString(R.string.def_cut_brt_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_y2), Float.parseFloat(context.getString(R.string.def_cut_brt_y2))));

                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBRT = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRT);

                    // BRT Name Area
                    area = Area.BRT;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_name_x1), Float.parseFloat(context.getString(R.string.def_cut_brt_name_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_name_x2), Float.parseFloat(context.getString(R.string.def_cut_brt_name_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_name_y1), Float.parseFloat(context.getString(R.string.def_cut_brt_name_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_name_y2), Float.parseFloat(context.getString(R.string.def_cut_brt_name_y2))));

                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    CCABuilding ccaBRTname = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths);
                    mapAreas.put(area, ccaBRTname);

                    // BRT Progress Area
                    area = Area.BRT_PROGRESS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_brt_progress_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_brt_progress_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_brt_progress_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_brt_progress_y2))));

                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    needOcr = false;
                    CityCalcArea ccaBRTprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRTprogress);

                    // BRT Points Area
                    area = Area.BRT_POINTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_points_x1), Float.parseFloat(context.getString(R.string.def_cut_brt_points_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_points_x2), Float.parseFloat(context.getString(R.string.def_cut_brt_points_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_points_y1), Float.parseFloat(context.getString(R.string.def_cut_brt_points_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_points_y2), Float.parseFloat(context.getString(R.string.def_cut_brt_points_y2))));

                    colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back, color_building_doublepoints, color_building_mayX2, color_building_isX2};
                    ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                    needOcr = false;
                    CityCalcArea ccaBRTpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRTpoints);

                    // BRT Slots Area
                    area = Area.BRT_SLOTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_brt_slots_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_brt_slots_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_brt_slots_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brt_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brt_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_brt_slots_y2))));


                    colors = new int[] {color_building_slot_main, color_building_slot_back};
                    ths = new int[] {thm_building_slot, thp_building_slot};
                    needOcr = false;
                    CityCalcArea ccaBRTslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    ccaBRTslots.needOcr = true;
                    Bitmap[] ccaBRTslotsBitmapArray = PictureProcessor.splitBitmap(ccaBRTslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT);
                    if (ccaBRTslotsBitmapArray != null) {
                        if (ccaBRTslotsBitmapArray.length == 2) {
                            ccaBRTslots.bmpSrc = ccaBRTslotsBitmapArray[1];
                            ccaBRTslots.doOCR();
                        }
                    }
                    mapAreas.put(area, ccaBRTslots);

                    ccaBRTname.calc(ccaBRTpoints, ccaBRTslots, ccaBRTprogress);




                    // BRC Area
                    area = Area.BRC_ARAE;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_x1), Float.parseFloat(context.getString(R.string.def_cut_brc_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_x2), Float.parseFloat(context.getString(R.string.def_cut_brc_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_y1), Float.parseFloat(context.getString(R.string.def_cut_brc_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_y2), Float.parseFloat(context.getString(R.string.def_cut_brc_y2))));

                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBRC = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRC);

                    // BRC Name Area
                    area = Area.BRC;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_name_x1), Float.parseFloat(context.getString(R.string.def_cut_brc_name_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_name_x2), Float.parseFloat(context.getString(R.string.def_cut_brc_name_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_name_y1), Float.parseFloat(context.getString(R.string.def_cut_brc_name_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_name_y2), Float.parseFloat(context.getString(R.string.def_cut_brc_name_y2))));

                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    CCABuilding ccaBRCname = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths);
                    mapAreas.put(area, ccaBRCname);

                    // BRC Progress Area
                    area = Area.BRC_PROGRESS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_brc_progress_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_brc_progress_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_brc_progress_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_brc_progress_y2))));

                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    needOcr = false;
                    CityCalcArea ccaBRCprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRCprogress);

                    // BRC Points Area
                    area = Area.BRC_POINTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_points_x1), Float.parseFloat(context.getString(R.string.def_cut_brc_points_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_points_x2), Float.parseFloat(context.getString(R.string.def_cut_brc_points_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_points_y1), Float.parseFloat(context.getString(R.string.def_cut_brc_points_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_points_y2), Float.parseFloat(context.getString(R.string.def_cut_brc_points_y2))));

                    colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back, color_building_doublepoints, color_building_mayX2, color_building_isX2};
                    ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                    needOcr = false;
                    CityCalcArea ccaBRCpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRCpoints);

                    // BRC Slots Area
                    area = Area.BRC_SLOTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_brc_slots_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_brc_slots_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_brc_slots_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brc_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brc_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_brc_slots_y2))));


                    colors = new int[] {color_building_slot_main, color_building_slot_back};
                    ths = new int[] {thm_building_slot, thp_building_slot};
                    needOcr = false;
                    CityCalcArea ccaBRCslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    ccaBRCslots.needOcr = true;
                    Bitmap[] ccaBRCslotsBitmapArray = PictureProcessor.splitBitmap(ccaBRCslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT);
                    if (ccaBRCslotsBitmapArray != null) {
                        if (ccaBRCslotsBitmapArray.length == 2) {
                            ccaBRCslots.bmpSrc = ccaBRCslotsBitmapArray[1];
                            ccaBRCslots.doOCR();
                        }
                    }
                    mapAreas.put(area, ccaBRCslots);

                    ccaBRCname.calc(ccaBRCpoints, ccaBRCslots, ccaBRCprogress);


                    // BRB Area
                    area = Area.BRB_ARAE;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_x1), Float.parseFloat(context.getString(R.string.def_cut_brb_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_x2), Float.parseFloat(context.getString(R.string.def_cut_brb_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_y1), Float.parseFloat(context.getString(R.string.def_cut_brb_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_y2), Float.parseFloat(context.getString(R.string.def_cut_brb_y2))));

                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBRB = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRB);

                    // BRB Name Area
                    area = Area.BRB;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_name_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_name_x1), Float.parseFloat(context.getString(R.string.def_cut_brb_name_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_name_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_name_x2), Float.parseFloat(context.getString(R.string.def_cut_brb_name_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_name_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_name_y1), Float.parseFloat(context.getString(R.string.def_cut_brb_name_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_name_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_name_y2), Float.parseFloat(context.getString(R.string.def_cut_brb_name_y2))));

                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty, color_building_mayX2, color_building_isX2};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    CCABuilding ccaBRBname = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths);
                    mapAreas.put(area, ccaBRBname);

                    // BRB Progress Area
                    area = Area.BRB_PROGRESS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_progress_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_progress_x1), Float.parseFloat(context.getString(R.string.def_cut_brb_progress_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_progress_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_progress_x2), Float.parseFloat(context.getString(R.string.def_cut_brb_progress_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_progress_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_progress_y1), Float.parseFloat(context.getString(R.string.def_cut_brb_progress_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_progress_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_progress_y2), Float.parseFloat(context.getString(R.string.def_cut_brb_progress_y2))));

                    colors = new int[] {color_progress_our, color_progress_enemy, color_progress_empty};
                    ths = new int[] {thm_progress_our, thp_progress_our, thm_progress_enemy, thp_progress_enemy, thm_progress_empty, thp_progress_empty};
                    needOcr = false;
                    CityCalcArea ccaBRBprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRBprogress);

                    // BRB Points Area
                    area = Area.BRB_POINTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_points_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_points_x1), Float.parseFloat(context.getString(R.string.def_cut_brb_points_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_points_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_points_x2), Float.parseFloat(context.getString(R.string.def_cut_brb_points_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_points_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_points_y1), Float.parseFloat(context.getString(R.string.def_cut_brb_points_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_points_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_points_y2), Float.parseFloat(context.getString(R.string.def_cut_brb_points_y2))));

                    colors = new int[] {color_building_points_our_main, color_building_points_our_back, color_building_points_enemy_main, color_building_points_enemy_back, color_building_doublepoints, color_building_mayX2, color_building_isX2};
                    ths = new int[] {thm_building_points_our, thp_building_points_our, thm_building_points_enemy, thp_building_points_enemy};
                    needOcr = false;
                    CityCalcArea ccaBRBpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRBpoints);

                    // BRB Slots Area
                    area = Area.BRB_SLOTS;

                    x1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_slots_x1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_slots_x1), Float.parseFloat(context.getString(R.string.def_cut_brb_slots_x1))));
                    x2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_slots_x2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_slots_x2), Float.parseFloat(context.getString(R.string.def_cut_brb_slots_x2))));
                    y1 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_slots_y1),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_slots_y1), Float.parseFloat(context.getString(R.string.def_cut_brb_slots_y1))));
                    y2 = sharedPreferences.getFloat(context.getString(R.string.pref_cut_brb_slots_y2),sharedPreferences.getFloat(context.getString(R.string.pref_def_cut_brb_slots_y2), Float.parseFloat(context.getString(R.string.def_cut_brb_slots_y2))));


                    colors = new int[] {color_building_slot_main, color_building_slot_back};
                    ths = new int[] {thm_building_slot, thp_building_slot};
                    needOcr = false;
                    CityCalcArea ccaBRBslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    ccaBRBslots.needOcr = true;
                    Bitmap[] ccaBRBslotsBitmapArray = PictureProcessor.splitBitmap(ccaBRBslots.bmpSrc, color_building_slot_main, thm_building_slot, thp_building_slot, 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT);
                    if (ccaBRBslotsBitmapArray != null) {
                        if (ccaBRBslotsBitmapArray.length == 2) {
                            ccaBRBslots.bmpSrc = ccaBRBslotsBitmapArray[1];
                            ccaBRBslots.doOCR();
                        }
                    }
                    mapAreas.put(area, ccaBRBslots);

                    ccaBRBname.calc(ccaBRBpoints, ccaBRBslots, ccaBRBprogress);



                } // кесли файл физически существует
            } // кесли файл не нулл
    
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
