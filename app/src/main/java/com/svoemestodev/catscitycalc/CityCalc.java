package com.svoemestodev.catscitycalc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CityCalc extends Activity {

    Bitmap bmpScreenshot;       // исходный скриншот
    int calibrateX;
    int calibrateY;
    Map<Area, CityCalcArea> mapAreas = new HashMap<>();
    Context context;

    public CityCalc(File file, int calibrateX, int calibrateY, Context context) {

        this.calibrateX = calibrateX;
        this.calibrateY = calibrateY;
        this.context = context;

        Area area;
        float x1;           // x1
        float x2;           // x2
        float y1;           // y1
        float y2;           // y2
        int [] colors;      // цвета
        int [] ths;         // пороги
        boolean needOcr;    // надо распознавать
        CityCalcArea cca;

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
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // Total Time Area
                    area = Area.TOTAL_TIME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_total_time_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_total_time_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_total_time_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_total_time_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_total_time_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_total_time_back1), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_total_time_back2), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_total_time_thm)), Integer.parseInt(context.getString(R.string.def_rgb_total_time_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // Early Win Area
                    area = Area.EARLY_WIN;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_early_win_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_early_win_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_early_win_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_early_win_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_early_win_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_early_win_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_early_win_thm)), Integer.parseInt(context.getString(R.string.def_rgb_early_win_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

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

                    if (ourPointsAndIncreaseBitmapArray != null) {

                        area = Area.POINTS_OUR;
                        colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_points_our_back1), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_points_our_back2), 16)};
                        ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_points_our_thp))};
                        needOcr = true;
                        cca = new CityCalcArea(this, area, colors, ths, needOcr);
                        cca.bmpSrc = ourPointsAndIncreaseBitmapArray[1];
                        cca.doORC();
                        mapAreas.put(area, cca);

                        area = Area.INCREASE_OUR;
                        colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_increase_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_our_back), 16)};
                        ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_increase_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_increase_our_thp))};
                        needOcr = true;
                        cca = new CityCalcArea(this, area, colors, ths, needOcr);
                        cca.bmpSrc = ourPointsAndIncreaseBitmapArray[0];
                        cca.doORC();
                        mapAreas.put(area, cca);

                    }


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

                    if (ourPointsAndIncreaseBitmapArray != null) {

                        area = Area.POINTS_ENEMY;
                        colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_back1), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_back2), 16)};
                        ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_points_enemy_thp))};
                        needOcr = true;
                        cca = new CityCalcArea(this, area, colors, ths, needOcr);
                        cca.bmpSrc = enemyPointsAndIncreaseBitmapArray[1];
                        cca.doORC();
                        mapAreas.put(area, cca);

                        area = Area.INCREASE_ENEMY;
                        colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_increase_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_increase_enemy_back), 16)};
                        ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_increase_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_increase_enemy_thp))};
                        needOcr = true;
                        cca = new CityCalcArea(this, area, colors, ths, needOcr);
                        cca.bmpSrc = enemyPointsAndIncreaseBitmapArray[0];
                        cca.doORC();
                        mapAreas.put(area, cca);

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
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // Team Name Enemy Area
                    area = Area.TEAM_NAME_ENEMY;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_team_name_enemy_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLT Area
                    area = Area.BLT;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLT Name Area
                    area = Area.BLT_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

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
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLT Points Area
                    area = Area.BLT_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLT Slots Area
                    area = Area.BLT_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lt_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLC Area
                    area = Area.BLC;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLC Name Area
                    area = Area.BLC_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

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
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLC Points Area
                    area = Area.BLC_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLC Slots Area
                    area = Area.BLC_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lc_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLB Area
                    area = Area.BLB;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLB Name Area
                    area = Area.BLB_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

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
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLB Points Area
                    area = Area.BLB_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BLB Slots Area
                    area = Area.BLB_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_lb_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BRT Area
                    area = Area.BRT;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BRT Name Area
                    area = Area.BRT_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

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
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BRT Points Area
                    area = Area.BRT_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BRT Slots Area
                    area = Area.BRT_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rt_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);


                    // BRC Area
                    area = Area.BRC;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BRC Name Area
                    area = Area.BRC_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

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
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BRC Points Area
                    area = Area.BRC_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BRC Slots Area
                    area = Area.BRC_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rc_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);


                    // BRB Area
                    area = Area.BRB;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BRB Name Area
                    area = Area.BRB_NAME;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_name_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_name_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_name_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_name_y2));
                    colors = null;
                    ths = null;
                    needOcr = false;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

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
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BRB Points Area
                    area = Area.BRB_POINTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_points_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_points_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_points_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_points_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_our_back), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_points_enemy_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_our_thp)),
                            Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_points_enemy_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);

                    // BRB Slots Area
                    area = Area.BRB_SLOTS;
                    x1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_slots_x1));
                    x2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_slots_x2));
                    y1 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_slots_y1));
                    y2 = Float.parseFloat(context.getString(R.string.def_cut_building_rb_slots_y2));
                    colors = new int[] {(int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_main), 16), (int)Long.parseLong(context.getString(R.string.def_rgb_building_slot_back), 16)};
                    ths = new int[] {Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thm)), Integer.parseInt(context.getString(R.string.def_rgb_building_slot_thp))};
                    needOcr = true;
                    cca = new CityCalcArea(this, area, x1, x2, y1, y2, colors, ths, needOcr);
                    mapAreas.put(area, cca);


                } // кесли файл физически существует
            } // кесли файл не нулл
    
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
