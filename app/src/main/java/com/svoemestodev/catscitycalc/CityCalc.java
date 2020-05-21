package com.svoemestodev.catscitycalc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CityCalc extends Activity {

    File fileScreenshot;
    Bitmap bmpScreenshot;       // исходный скриншот
    int calibrateX;             // сдвиг центра по X
    int calibrateY;             // сдвиг центра по Y
    Map<Area, CityCalcArea> mapAreas = new HashMap<>(); // мап областей
    Context context;            // контекст

    // конструктор
    public CityCalc(File file, int calibrateX, int calibrateY, Context context) {

        this.fileScreenshot = file;
        this.calibrateX = calibrateX;
        this.calibrateY = calibrateY;
        this.context = context;

        Area area;          // идентификатор области
        float x1;           // x1
        float x2;           // x2
        float y1;           // y1
        float y2;           // y2
        int [] colors;      // цвета
        int [] ths;         // пороги
        boolean needOcr;    // надо распознавать
        CityCalcArea cca;   // область

        try {
            if (file != null) {         // если файл не нулл
                if (file.exists()) {    // если файл физически существует
                    this.bmpScreenshot = BitmapFactory.decodeFile(file.getAbsolutePath());   // загружаем битмап из файла скриншота
                    // City Area
                    area = Area.CITY;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_city_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_city_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_city_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_city_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCAGame ccaGame = new CCAGame(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaGame);

                    // Total Time Area
                    area = Area.TOTAL_TIME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_total_time_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_total_time_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_total_time_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_total_time_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_total_time_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_total_time_back1), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_total_time_back2), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_total_time_thm)), Integer.parseInt(context.getString(R.string.def_rgb_total_time_thp))};
                    needOcr = true;
                    CityCalcArea ccaTotalTime = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaTotalTime);

                    // Early Win Area
                    area = Area.EARLY_WIN;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_early_win_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_early_win_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_early_win_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_early_win_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_early_win_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_early_win_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_early_win_thm)), Integer.parseInt(context.getString(R.string.def_rgb_early_win_thp))};
                    needOcr = true;
                    CityCalcArea ccaEarlyWin = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaEarlyWin);

                    ccaGame.calc();


                    // Points And Increase Our Area
                    area = Area.POINTS_AND_INCREASE_OUR;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_our_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);
                    Bitmap[] ourPointsAndIncreaseBitmapArray = PictureProcessor.splitBitmap(
                            cca.bmpSrc,(int)Long.parseLong(context.getString(R.string.def_rgb_increase_our_back), 16),
                            Integer.parseInt(context.getString(R.string.def_rgb_points_our_thm)),
                            Integer.parseInt(context.getString(R.string.def_rgb_points_our_thp)),
                            0.85f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT);

                    CityCalcArea ccaPointsOur = null;
                    CityCalcArea ccaIncreaseOur = null;

                    if (ourPointsAndIncreaseBitmapArray != null) {

                        area = Area.POINTS_OUR;
                        colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_points_our_back1), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_points_our_back2), 16)};
                        ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_points_our_thp))};
                        needOcr = true;
                        ccaPointsOur = new CityCalcArea(this, area, colors, ths, needOcr);
                        ccaPointsOur.bmpSrc = ourPointsAndIncreaseBitmapArray[1];
                        ccaPointsOur.doOCR();
                        mapAreas.put(area, ccaPointsOur);

                        area = Area.INCREASE_OUR;
                        colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_increase_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_our_back), 16)};
                        ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_increase_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_increase_our_thp))};
                        needOcr = true;
                        ccaIncreaseOur = new CityCalcArea(this, area, colors, ths, needOcr);
                        ccaIncreaseOur.bmpSrc = ourPointsAndIncreaseBitmapArray[0];
                        ccaIncreaseOur.doOCR();
                        mapAreas.put(area, ccaIncreaseOur);

                    }

                    // Team Name Our Area
                    area = Area.TEAM_NAME_OUR;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_team_name_our_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_team_name_our_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_team_name_our_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_team_name_our_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCATeam ccaOurTeam = new CCATeam(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaOurTeam);


                    // Points And Increase Enemy Area
                    area = Area.POINTS_AND_INCREASE_ENEMY;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_points_and_increase_enemy_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);
                    Bitmap[] enemyPointsAndIncreaseBitmapArray = PictureProcessor.splitBitmap(
                            cca.bmpSrc,(int)Long.parseLong(context.getString(R.string.def_rgb_increase_enemy_back), 16),
                            Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thm)),
                            Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thp)),
                            0.85f, 0.0f, PictureProcessorDirection.FROM_RIGHT_TO_LEFT);

                    CityCalcArea ccaPointsEnemy = null;
                    CityCalcArea ccaIncreaseEnemy = null;

                    if (ourPointsAndIncreaseBitmapArray != null) {

                        area = Area.POINTS_ENEMY;
                        colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_back1), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_back2), 16)};
                        ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thp))};
                        needOcr = true;
                        ccaPointsEnemy = new CityCalcArea(this, area, colors, ths, needOcr);
                        ccaPointsEnemy.bmpSrc = enemyPointsAndIncreaseBitmapArray[1];
                        ccaPointsEnemy.doOCR();
                        mapAreas.put(area, ccaPointsEnemy);

                        area = Area.INCREASE_ENEMY;
                        colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_increase_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_enemy_back), 16)};
                        ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_increase_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_increase_enemy_thp))};
                        needOcr = true;
                        ccaIncreaseEnemy = new CityCalcArea(this, area, colors, ths, needOcr);
                        ccaIncreaseEnemy.bmpSrc = enemyPointsAndIncreaseBitmapArray[0];
                        ccaIncreaseEnemy.doOCR();
                        mapAreas.put(area, ccaIncreaseEnemy);

                    }

                    // Team Name Enemy Area
                    area = Area.TEAM_NAME_ENEMY;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCATeam ccaEnemyTeam = new CCATeam(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaEnemyTeam);

                    ccaGame.calcWin();

                    // BLT Area
                    area = Area.BLT;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCABuilding ccaBLT = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLT);

                    // BLT Name Area
                    area = Area.BLT_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBLTname = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLTname);

                    // BLT Progress Area
                    area = Area.BLT_PROGRESS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_progress_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_progress_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_progress_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_progress_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thp)), 
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thp))};
                    needOcr = false;
                    CityCalcArea ccaBLTprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLTprogress);

                    // BLT Points Area
                    area = Area.BLT_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = false;
                    CityCalcArea ccaBLTpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLTpoints);

                    // BLT Slots Area
                    area = Area.BLT_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    CityCalcArea ccaBLTslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLTslots);

                    ccaBLT.calc(ccaBLTpoints, ccaBLTslots, ccaBLTprogress);

                    // BLC Area
                    area = Area.BLC;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCABuilding ccaBLC = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLC);

                    // BLC Name Area
                    area = Area.BLC_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBLCname = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLCname);

                    // BLC Progress Area
                    area = Area.BLC_PROGRESS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_progress_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_progress_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_progress_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_progress_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thp))};
                    needOcr = false;
                    CityCalcArea ccaBLCprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLCprogress);

                    // BLC Points Area
                    area = Area.BLC_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = true;
                    CityCalcArea ccaBLCpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLCpoints);

                    // BLC Slots Area
                    area = Area.BLC_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    CityCalcArea ccaBLCslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLCslots);

                    ccaBLC.calc(ccaBLCpoints, ccaBLCslots, ccaBLCprogress);




                    // BLB Area
                    area = Area.BLB;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCABuilding ccaBLB = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLB);

                    // BLB Name Area
                    area = Area.BLB_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBLBname = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLBname);

                    // BLB Progress Area
                    area = Area.BLB_PROGRESS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_progress_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_progress_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_progress_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_progress_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thp))};
                    needOcr = false;
                    CityCalcArea ccaBLBprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLBprogress);

                    // BLB Points Area
                    area = Area.BLB_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = true;
                    CityCalcArea ccaBLBpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLBpoints);

                    // BLB Slots Area
                    area = Area.BLB_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    CityCalcArea ccaBLBslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBLBslots);

                    ccaBLB.calc(ccaBLBpoints, ccaBLBslots, ccaBLBprogress);



                    // BRT Area
                    area = Area.BRT;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCABuilding ccaBRT = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRT);

                    // BRT Name Area
                    area = Area.BRT_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBRTname = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRTname);

                    // BRT Progress Area
                    area = Area.BRT_PROGRESS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_progress_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_progress_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_progress_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_progress_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thp))};
                    needOcr = false;
                    CityCalcArea ccaBRTprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRTprogress);

                    // BRT Points Area
                    area = Area.BRT_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = true;
                    CityCalcArea ccaBRTpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRTpoints);

                    // BRT Slots Area
                    area = Area.BRT_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    CityCalcArea ccaBRTslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRTslots);

                    ccaBRT.calc(ccaBRTpoints, ccaBRTslots, ccaBRTprogress);





                    // BRC Area
                    area = Area.BRC;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCABuilding ccaBRC = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRC);

                    // BRC Name Area
                    area = Area.BRC_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBRCname = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRCname);

                    // BRC Progress Area
                    area = Area.BRC_PROGRESS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_progress_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_progress_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_progress_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_progress_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thp))};
                    needOcr = false;
                    CityCalcArea ccaBRCprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRCprogress);

                    // BRC Points Area
                    area = Area.BRC_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = true;
                    CityCalcArea ccaBRCpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRCpoints);

                    // BRC Slots Area
                    area = Area.BRC_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    CityCalcArea ccaBRCslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRCslots);

                    ccaBRC.calc(ccaBRCpoints, ccaBRCslots, ccaBRCprogress);




                    // BRB Area
                    area = Area.BRB;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CCABuilding ccaBRB = new CCABuilding(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRB);

                    // BRB Name Area
                    area = Area.BRB_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    CityCalcArea ccaBRBname = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRBname);

                    // BRB Progress Area
                    area = Area.BRB_PROGRESS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_progress_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_progress_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_progress_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_progress_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_enemy_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_progress_empty_thp))};
                    needOcr = false;
                    CityCalcArea ccaBRBprogress = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRBprogress);

                    // BRB Points Area
                    area = Area.BRB_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_doublepoints), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = true;
                    CityCalcArea ccaBRBpoints = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRBpoints);

                    // BRB Slots Area
                    area = Area.BRB_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    CityCalcArea ccaBRBslots = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, ccaBRBslots);

                    ccaBRB.calc(ccaBRBpoints, ccaBRBslots, ccaBRBprogress);


                } // кесли файл физически существует
            } // кесли файл не нулл
    
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
