package com.svoemestodev.catscitycalc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.svoemestodev.catscitycalc.citycalcclasses.Area;
import com.svoemestodev.catscitycalc.citycalcclasses.CCABuilding;
import com.svoemestodev.catscitycalc.citycalcclasses.CCAGame;
import com.svoemestodev.catscitycalc.citycalcclasses.CCATeam;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalc;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalcArea;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StrategyActivity extends AppCompatActivity {

    // Game views
    Switch sw_sa_listen_new_file;
    TextView tv_sa_start_game_time;
    TextView tv_sa_end_game_time;
    TextView tv_sa_status;
    TextView tv_sa_vs;

    // Time views
    TextView tv_sa_total_time;
    TextView tv_sa_early_win;

    // Our team views
    ImageView iv_sa_our_team_name;
    TextView tv_sa_our_increase;
    TextView tv_sa_our_points;
    TextView tv_sa_our_end_time;

    // Enemy team views
    ImageView iv_sa_enemy_team_name;
    TextView tv_sa_enemy_increase;
    TextView tv_sa_enemy_points;
    TextView tv_sa_enemy_end_time;

    // BLT views
    ImageView iv_sa_blt_name;
    TextView tv_sa_blt_x2;
    TextView tv_sa_blt_points;
    TextView tv_sa_blt_slots;
    TextView tv_sa_blt_slots_our;
    TextView tv_sa_blt_slots_empty;
    TextView tv_sa_blt_slots_enemy;
    ImageView iv_sa_blt_car_black;
    ImageView iv_sa_blt_car_our;
    ImageView iv_sa_blt_car_empty;
    ImageView iv_sa_blt_car_enemy;
    SeekBar sb_sa_blt;

    // BLC views
    ImageView iv_sa_blc_name;
    TextView tv_sa_blc_x2;
    TextView tv_sa_blc_points;
    TextView tv_sa_blc_slots;
    TextView tv_sa_blc_slots_our;
    TextView tv_sa_blc_slots_empty;
    TextView tv_sa_blc_slots_enemy;
    ImageView iv_sa_blc_car_black;
    ImageView iv_sa_blc_car_our;
    ImageView iv_sa_blc_car_empty;
    ImageView iv_sa_blc_car_enemy;
    SeekBar sb_sa_blc;

    // BLB views
    ImageView iv_sa_blb_name;
    TextView tv_sa_blb_x2;
    TextView tv_sa_blb_points;
    TextView tv_sa_blb_slots;
    TextView tv_sa_blb_slots_our;
    TextView tv_sa_blb_slots_empty;
    TextView tv_sa_blb_slots_enemy;
    ImageView iv_sa_blb_car_black;
    ImageView iv_sa_blb_car_our;
    ImageView iv_sa_blb_car_empty;
    ImageView iv_sa_blb_car_enemy;
    SeekBar sb_sa_blb;

    // BRT views
    ImageView iv_sa_brt_name;
    TextView tv_sa_brt_x2;
    TextView tv_sa_brt_points;
    TextView tv_sa_brt_slots;
    TextView tv_sa_brt_slots_our;
    TextView tv_sa_brt_slots_empty;
    TextView tv_sa_brt_slots_enemy;
    ImageView iv_sa_brt_car_black;
    ImageView iv_sa_brt_car_our;
    ImageView iv_sa_brt_car_empty;
    ImageView iv_sa_brt_car_enemy;
    SeekBar sb_sa_brt;

    // BRC views
    ImageView iv_sa_brc_name;
    TextView tv_sa_brc_x2;
    TextView tv_sa_brc_points;
    TextView tv_sa_brc_slots;
    TextView tv_sa_brc_slots_our;
    TextView tv_sa_brc_slots_empty;
    TextView tv_sa_brc_slots_enemy;
    ImageView iv_sa_brc_car_black;
    ImageView iv_sa_brc_car_our;
    ImageView iv_sa_brc_car_empty;
    ImageView iv_sa_brc_car_enemy;
    SeekBar sb_sa_brc;

    // BRB views
    ImageView iv_sa_brb_name;
    TextView tv_sa_brb_x2;
    TextView tv_sa_brb_points;
    TextView tv_sa_brb_slots;
    TextView tv_sa_brb_slots_our;
    TextView tv_sa_brb_slots_empty;
    TextView tv_sa_brb_slots_enemy;
    ImageView iv_sa_brb_car_black;
    ImageView iv_sa_brb_car_our;
    ImageView iv_sa_brb_car_empty;
    ImageView iv_sa_brb_car_enemy;
    SeekBar sb_sa_brb;

    SeekBar sb_sa_person_our;
    ImageView iv_sa_person_our;
    TextView tv_sa_person_our;
    ImageView iv_sa_car_our;
    TextView tv_sa_car_our;

    SeekBar sb_sa_person_enemy;
    ImageView iv_sa_person_enemy;
    TextView tv_sa_person_enemy;
    ImageView iv_sa_car_enemy;
    TextView tv_sa_car_enemy;

    Button bt_sa_calc_timed_win;
    Button bt_sa_calc_early_win;

    int maxPersons;
    int personsOur;
    int personsEnemy;
    int slotsTotal;
    int slotsOur;
    int slotsEnemy;
    int slotsEmpty;
    int increaseOur;
    int increaseEnemy;
    int countMayX2;
    int countMayX2our;
    int countMayX2enemy;
    int countMayX2empty;
    int increaseOurMain;
    int increaseEnemyMain;
    int pointsInScreenshotOurMain;
    int pointsInScreenshotEnemyMain;
    Date dateScreenshotMain;


    boolean needUpdateSlots;
    
    // Рекламный блок
    AdView ad_sa_banner;

    public static CityCalc mainCityCalc;
    
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();
        context = tv_sa_start_game_time.getContext();
        sb_sa_person_our.setEnabled(false);
        sb_sa_person_enemy.setEnabled(false);

        // инициализация рекламного блока
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        ad_sa_banner.loadAd(adRequest);

        sb_sa_blt.setOnSeekBarChangeListener(bltSeekBarChangeListener);
        sb_sa_blc.setOnSeekBarChangeListener(blcSeekBarChangeListener);
        sb_sa_blb.setOnSeekBarChangeListener(blbSeekBarChangeListener);
        sb_sa_brt.setOnSeekBarChangeListener(brtSeekBarChangeListener);
        sb_sa_brc.setOnSeekBarChangeListener(brcSeekBarChangeListener);
        sb_sa_brb.setOnSeekBarChangeListener(brbSeekBarChangeListener);
        sb_sa_person_our.setOnSeekBarChangeListener(personOurSeekBarChangeListener);
        sb_sa_person_enemy.setOnSeekBarChangeListener(personEnemySeekBarChangeListener);



        mainCityCalc = new CityCalc(new CityCalc(GameActivity.fileGameScreenshot, GameActivity.calibrateX, GameActivity.calibrateY, context));
        CCAGame ccaGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);

        if (ccaGame != null) {
            dateScreenshotMain = ccaGame.getCcagDateScreenshot();
            increaseOurMain = ccaGame.getCcagIncreaseOur();
            increaseEnemyMain = ccaGame.getCcagIncreaseEnemy();
            pointsInScreenshotOurMain = ccaGame.getCcagPointsOurInScreenshot();
            pointsInScreenshotEnemyMain = ccaGame.getCcagPointsEnemyInScreenshot();
        }

        CCABuilding ccaBLT = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLT);
        CCABuilding ccaBLC = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLC);
        CCABuilding ccaBLB = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLB);
        CCABuilding ccaBRT = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRT);
        CCABuilding ccaBRC = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRC);
        CCABuilding ccaBRB = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRB);

        slotsTotal = 0;
        slotsOur = 0;
        slotsEnemy = 0;

        needUpdateSlots = false;
        
        if (ccaBLT != null) {
            if (ccaBLT.isPresent()) {
                if (ccaBLT.isBuildingIsOur()) sb_sa_blt.setProgress(0);
                if (ccaBLT.isBuildingIsEmpty()) sb_sa_blt.setProgress(1);
                if (ccaBLT.isBuildingIsEnemy()) sb_sa_blt.setProgress(2);
                slotsTotal += ccaBLT.getSlots();
                slotsOur += ccaBLT.getSlots_our();
                slotsEnemy += ccaBLT.getSlots_enemy();
            }
        }

        if (ccaBLC != null) {
            if (ccaBLC.isPresent()) {
                if (ccaBLC.isBuildingIsOur()) sb_sa_blc.setProgress(0);
                if (ccaBLC.isBuildingIsEmpty()) sb_sa_blc.setProgress(1);
                if (ccaBLC.isBuildingIsEnemy()) sb_sa_blc.setProgress(2);
                slotsTotal += ccaBLC.getSlots();
                slotsOur += ccaBLC.getSlots_our();
                slotsEnemy += ccaBLC.getSlots_enemy();
            }
        }

        if (ccaBLB != null) {
            if (ccaBLB.isPresent()) {
                if (ccaBLB.isBuildingIsOur()) sb_sa_blb.setProgress(0);
                if (ccaBLB.isBuildingIsEmpty()) sb_sa_blb.setProgress(1);
                if (ccaBLB.isBuildingIsEnemy()) sb_sa_blb.setProgress(2);
                slotsTotal += ccaBLB.getSlots();
                slotsOur += ccaBLB.getSlots_our();
                slotsEnemy += ccaBLB.getSlots_enemy();
            }
        }

        if (ccaBRT != null) {
            if (ccaBRT.isPresent()) {
                if (ccaBRT.isBuildingIsOur()) sb_sa_brt.setProgress(0);
                if (ccaBRT.isBuildingIsEmpty()) sb_sa_brt.setProgress(1);
                if (ccaBRT.isBuildingIsEnemy()) sb_sa_brt.setProgress(2);
                slotsTotal += ccaBRT.getSlots();
                slotsOur += ccaBRT.getSlots_our();
                slotsEnemy += ccaBRT.getSlots_enemy();
            }
        }

        if (ccaBRC != null) {
            if (ccaBRC.isPresent()) {
                if (ccaBRC.isBuildingIsOur()) sb_sa_brc.setProgress(0);
                if (ccaBRC.isBuildingIsEmpty()) sb_sa_brc.setProgress(1);
                if (ccaBRC.isBuildingIsEnemy()) sb_sa_brc.setProgress(2);
                slotsTotal += ccaBRC.getSlots();
                slotsOur += ccaBRC.getSlots_our();
                slotsEnemy += ccaBRC.getSlots_enemy();
            }
        }

        if (ccaBRB != null) {
            if (ccaBRB.isPresent()) {
                if (ccaBRB.isBuildingIsOur()) sb_sa_brb.setProgress(0);
                if (ccaBRB.isBuildingIsEmpty()) sb_sa_brb.setProgress(1);
                if (ccaBRB.isBuildingIsEnemy()) sb_sa_brb.setProgress(2);
                slotsTotal += ccaBRB.getSlots();
                slotsOur += ccaBRB.getSlots_our();
                slotsEnemy += ccaBRB.getSlots_enemy();
            }
        }

        maxPersons = (int)Math.ceil (slotsTotal / 3.0d);
        sb_sa_person_our.setMax(maxPersons);
        sb_sa_person_enemy.setMax(maxPersons);

        personsOur = (int)Math.ceil (slotsOur / 3.0d);
        personsEnemy = (int)Math.ceil (slotsEnemy / 3.0d);

        sb_sa_person_our.setProgress(personsOur);
        sb_sa_person_enemy.setProgress(personsEnemy);

        tv_sa_person_our.setText(String.valueOf(personsOur));
        tv_sa_person_enemy.setText(String.valueOf(personsEnemy));
        tv_sa_car_our.setText(String.valueOf(personsOur*3));
        tv_sa_car_enemy.setText(String.valueOf(personsEnemy*3));

        needUpdateSlots = true;
        
        setCarsInBuildings();


    }

    private void setCarsInBuildings() {
        
        if (mainCityCalc != null) {

            CCAGame ccaGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);
            CCABuilding ccaBLT = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLT);
            CCABuilding ccaBLC = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLC);
            CCABuilding ccaBLB = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLB);
            CCABuilding ccaBRT = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRT);
            CCABuilding ccaBRC = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRC);
            CCABuilding ccaBRB = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRB);

            slotsOur = 0;
            slotsEnemy = 0;
            slotsEmpty = 0;
            increaseOur = 0;
            increaseEnemy = 0;
            countMayX2 = 0;
            countMayX2our = 0;
            countMayX2enemy = 0;
            countMayX2empty = 0;

            if (ccaGame != null) {
                ccaGame.setCcagIncreaseOur(increaseOurMain);
                ccaGame.setCcagPointsOurInScreenshot(pointsInScreenshotOurMain);
                ccaGame.setCcagIncreaseEnemy(increaseEnemyMain);
                ccaGame.setCcagPointsEnemyInScreenshot(pointsInScreenshotEnemyMain);
                ccaGame.setCcagDateScreenshot(dateScreenshotMain);

                ccaGame.calcWin();

                ccaGame.setCcagPointsOurInScreenshot(ccaGame.getPointsOur());
                ccaGame.setCcagPointsEnemyInScreenshot(ccaGame.getPointsEnemy());

            }
            
            if (ccaBLT != null) {
                if (ccaBLT.isPresent()) {
                    ccaBLT.setBuildingIsOur(sb_sa_blt.getProgress() == 0);
                    ccaBLT.setBuildingIsEmpty(sb_sa_blt.getProgress() == 1);
                    ccaBLT.setBuildingIsEnemy(sb_sa_blt.getProgress() == 2);
                    ccaBLT.setSlots_our(ccaBLT.isBuildingIsOur() ? (int)Math.ceil(ccaBLT.getSlots() / 2.0d) : 0); 
                    ccaBLT.setSlots_empty(ccaBLT.isBuildingIsEmpty() ? (int)Math.ceil(ccaBLT.getSlots() / 2.0d) : 0); 
                    ccaBLT.setSlots_enemy(ccaBLT.isBuildingIsEnemy() ? (int)Math.ceil(ccaBLT.getSlots() / 2.0d) : 0);
                    slotsOur += ccaBLT.getSlots_our();
                    slotsEmpty += ccaBLT.getSlots_empty();
                    slotsEnemy += ccaBLT.getSlots_enemy();
                    countMayX2 += ccaBLT.isMayX2() ? 1 : 0;
                    countMayX2our += ccaBLT.isMayX2() && ccaBLT.isBuildingIsOur() ? 1 : 0;
                    countMayX2empty += ccaBLT.isMayX2() && ccaBLT.isBuildingIsEmpty() ? 1 : 0;
                    countMayX2enemy += ccaBLT.isMayX2() && ccaBLT.isBuildingIsEnemy() ? 1 : 0;
                }
            }

            if (ccaBLC != null) {
                if (ccaBLC.isPresent()) {
                    ccaBLC.setBuildingIsOur(sb_sa_blc.getProgress() == 0);
                    ccaBLC.setBuildingIsEmpty(sb_sa_blc.getProgress() == 1);
                    ccaBLC.setBuildingIsEnemy(sb_sa_blc.getProgress() == 2);
                    ccaBLC.setSlots_our(ccaBLC.isBuildingIsOur() ? (int)Math.ceil(ccaBLC.getSlots() / 2.0d) : 0);
                    ccaBLC.setSlots_empty(ccaBLC.isBuildingIsEmpty() ? (int)Math.ceil(ccaBLC.getSlots() / 2.0d) : 0);
                    ccaBLC.setSlots_enemy(ccaBLC.isBuildingIsEnemy() ? (int)Math.ceil(ccaBLC.getSlots() / 2.0d) : 0);
                    slotsOur += ccaBLC.getSlots_our();
                    slotsEmpty += ccaBLC.getSlots_empty();
                    slotsEnemy += ccaBLC.getSlots_enemy();
                    countMayX2 += ccaBLC.isMayX2() ? 1 : 0;
                    countMayX2our += ccaBLC.isMayX2() && ccaBLC.isBuildingIsOur() ? 1 : 0;
                    countMayX2empty += ccaBLC.isMayX2() && ccaBLC.isBuildingIsEmpty() ? 1 : 0;
                    countMayX2enemy += ccaBLC.isMayX2() && ccaBLC.isBuildingIsEnemy() ? 1 : 0;
                }
            }

            if (ccaBLB != null) {
                if (ccaBLB.isPresent()) {
                    ccaBLB.setBuildingIsOur(sb_sa_blb.getProgress() == 0);
                    ccaBLB.setBuildingIsEmpty(sb_sa_blb.getProgress() == 1);
                    ccaBLB.setBuildingIsEnemy(sb_sa_blb.getProgress() == 2);
                    ccaBLB.setSlots_our(ccaBLB.isBuildingIsOur() ? (int)Math.ceil(ccaBLB.getSlots() / 2.0d) : 0);
                    ccaBLB.setSlots_empty(ccaBLB.isBuildingIsEmpty() ? (int)Math.ceil(ccaBLB.getSlots() / 2.0d) : 0);
                    ccaBLB.setSlots_enemy(ccaBLB.isBuildingIsEnemy() ? (int)Math.ceil(ccaBLB.getSlots() / 2.0d) : 0);
                    slotsOur += ccaBLB.getSlots_our();
                    slotsEmpty += ccaBLB.getSlots_empty();
                    slotsEnemy += ccaBLB.getSlots_enemy();
                    countMayX2 += ccaBLB.isMayX2() ? 1 : 0;
                    countMayX2our += ccaBLB.isMayX2() && ccaBLB.isBuildingIsOur() ? 1 : 0;
                    countMayX2empty += ccaBLB.isMayX2() && ccaBLB.isBuildingIsEmpty() ? 1 : 0;
                    countMayX2enemy += ccaBLB.isMayX2() && ccaBLB.isBuildingIsEnemy() ? 1 : 0;
                }
            }

            if (ccaBRT != null) {
                if (ccaBRT.isPresent()) {
                    ccaBRT.setBuildingIsOur(sb_sa_brt.getProgress() == 0);
                    ccaBRT.setBuildingIsEmpty(sb_sa_brt.getProgress() == 1);
                    ccaBRT.setBuildingIsEnemy(sb_sa_brt.getProgress() == 2);
                    ccaBRT.setSlots_our(ccaBRT.isBuildingIsOur() ? (int)Math.ceil(ccaBRT.getSlots() / 2.0d) : 0);
                    ccaBRT.setSlots_empty(ccaBRT.isBuildingIsEmpty() ? (int)Math.ceil(ccaBRT.getSlots() / 2.0d) : 0);
                    ccaBRT.setSlots_enemy(ccaBRT.isBuildingIsEnemy() ? (int)Math.ceil(ccaBRT.getSlots() / 2.0d) : 0);
                    slotsOur += ccaBRT.getSlots_our();
                    slotsEmpty += ccaBRT.getSlots_empty();
                    slotsEnemy += ccaBRT.getSlots_enemy();
                    countMayX2 += ccaBRT.isMayX2() ? 1 : 0;
                    countMayX2our += ccaBRT.isMayX2() && ccaBRT.isBuildingIsOur() ? 1 : 0;
                    countMayX2empty += ccaBRT.isMayX2() && ccaBRT.isBuildingIsEmpty() ? 1 : 0;
                    countMayX2enemy += ccaBRT.isMayX2() && ccaBRT.isBuildingIsEnemy() ? 1 : 0;
                }
            }

            if (ccaBRC != null) {
                if (ccaBRC.isPresent()) {
                    ccaBRC.setBuildingIsOur(sb_sa_brc.getProgress() == 0);
                    ccaBRC.setBuildingIsEmpty(sb_sa_brc.getProgress() == 1);
                    ccaBRC.setBuildingIsEnemy(sb_sa_brc.getProgress() == 2);
                    ccaBRC.setSlots_our(ccaBRC.isBuildingIsOur() ? (int)Math.ceil(ccaBRC.getSlots() / 2.0d) : 0);
                    ccaBRC.setSlots_empty(ccaBRC.isBuildingIsEmpty() ? (int)Math.ceil(ccaBRC.getSlots() / 2.0d) : 0);
                    ccaBRC.setSlots_enemy(ccaBRC.isBuildingIsEnemy() ? (int)Math.ceil(ccaBRC.getSlots() / 2.0d) : 0);
                    slotsOur += ccaBRC.getSlots_our();
                    slotsEmpty += ccaBRC.getSlots_empty();
                    slotsEnemy += ccaBRC.getSlots_enemy();
                    countMayX2 += ccaBRC.isMayX2() ? 1 : 0;
                    countMayX2our += ccaBRC.isMayX2() && ccaBRC.isBuildingIsOur() ? 1 : 0;
                    countMayX2empty += ccaBRC.isMayX2() && ccaBRC.isBuildingIsEmpty() ? 1 : 0;
                    countMayX2enemy += ccaBRC.isMayX2() && ccaBRC.isBuildingIsEnemy() ? 1 : 0;
                }
            }

            if (ccaBRB != null) {
                if (ccaBRB.isPresent()) {
                    ccaBRB.setBuildingIsOur(sb_sa_brb.getProgress() == 0);
                    ccaBRB.setBuildingIsEmpty(sb_sa_brb.getProgress() == 1);
                    ccaBRB.setBuildingIsEnemy(sb_sa_brb.getProgress() == 2);
                    ccaBRB.setSlots_our(ccaBRB.isBuildingIsOur() ? (int)Math.ceil(ccaBRB.getSlots() / 2.0d) : 0);
                    ccaBRB.setSlots_empty(ccaBRB.isBuildingIsEmpty() ? (int)Math.ceil(ccaBRB.getSlots() / 2.0d) : 0);
                    ccaBRB.setSlots_enemy(ccaBRB.isBuildingIsEnemy() ? (int)Math.ceil(ccaBRB.getSlots() / 2.0d) : 0);
                    slotsOur += ccaBRB.getSlots_our();
                    slotsEmpty += ccaBRB.getSlots_empty();
                    slotsEnemy += ccaBRB.getSlots_enemy();
                    countMayX2 += ccaBRB.isMayX2() ? 1 : 0;
                    countMayX2our += ccaBRB.isMayX2() && ccaBRB.isBuildingIsOur() ? 1 : 0;
                    countMayX2empty += ccaBRB.isMayX2() && ccaBRB.isBuildingIsEmpty() ? 1 : 0;
                    countMayX2enemy += ccaBRB.isMayX2() && ccaBRB.isBuildingIsEnemy() ? 1 : 0;
                }
            }


            if (ccaBLT != null) {
                if (ccaBLT.isPresent()) {
                    ccaBLT.setOur_points(!ccaBLT.isBuildingIsOur() ? 0 : ccaBLT.getSlots() * (!ccaBLT.isMayX2() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                    ccaBLT.setEnemy_points(!ccaBLT.isBuildingIsEnemy() ? 0 : ccaBLT.getSlots() * (!ccaBLT.isMayX2() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                    increaseOur += ccaBLT.getOur_points();
                    increaseEnemy += ccaBLT.getEnemy_points();
                    ccaBLT.setX2(ccaBLT.isMayX2() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
                }
            }

            if (ccaBLC != null) {
                if (ccaBLC.isPresent()) {
                    ccaBLC.setOur_points(!ccaBLC.isBuildingIsOur() ? 0 : ccaBLC.getSlots() * (!ccaBLC.isMayX2() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                    ccaBLC.setEnemy_points(!ccaBLC.isBuildingIsEnemy() ? 0 : ccaBLC.getSlots() * (!ccaBLC.isMayX2() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                    increaseOur += ccaBLC.getOur_points();
                    increaseEnemy += ccaBLC.getEnemy_points();
                    ccaBLC.setX2(ccaBLC.isMayX2() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
                }
            }

            if (ccaBLB != null) {
                if (ccaBLB.isPresent()) {
                    ccaBLB.setOur_points(!ccaBLB.isBuildingIsOur() ? 0 : ccaBLB.getSlots() * (!ccaBLB.isMayX2() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                    ccaBLB.setEnemy_points(!ccaBLB.isBuildingIsEnemy() ? 0 : ccaBLB.getSlots() * (!ccaBLB.isMayX2() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                    increaseOur += ccaBLB.getOur_points();
                    increaseEnemy += ccaBLB.getEnemy_points();
                    ccaBLB.setX2(ccaBLB.isMayX2() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
                }
            }

            if (ccaBRT != null) {
                if (ccaBRT.isPresent()) {
                    ccaBRT.setOur_points(!ccaBRT.isBuildingIsOur() ? 0 : ccaBRT.getSlots() * (!ccaBRT.isMayX2() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                    ccaBRT.setEnemy_points(!ccaBRT.isBuildingIsEnemy() ? 0 : ccaBRT.getSlots() * (!ccaBRT.isMayX2() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                    increaseOur += ccaBRT.getOur_points();
                    increaseEnemy += ccaBRT.getEnemy_points();
                    ccaBRT.setX2(ccaBRT.isMayX2() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
                }
            }

            if (ccaBRC != null) {
                if (ccaBRC.isPresent()) {
                    ccaBRC.setOur_points(!ccaBRC.isBuildingIsOur() ? 0 : ccaBRC.getSlots() * (!ccaBRC.isMayX2() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                    ccaBRC.setEnemy_points(!ccaBRC.isBuildingIsEnemy() ? 0 : ccaBRC.getSlots() * (!ccaBRC.isMayX2() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                    increaseOur += ccaBRC.getOur_points();
                    increaseEnemy += ccaBRC.getEnemy_points();
                    ccaBRC.setX2(ccaBRC.isMayX2() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
                }
            }

            if (ccaBRB != null) {
                if (ccaBRB.isPresent()) {
                    ccaBRB.setOur_points(!ccaBRB.isBuildingIsOur() ? 0 : ccaBRB.getSlots() * (!ccaBRB.isMayX2() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                    ccaBRB.setEnemy_points(!ccaBRB.isBuildingIsEnemy() ? 0 : ccaBRB.getSlots() * (!ccaBRB.isMayX2() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                    increaseOur += ccaBRB.getOur_points();
                    increaseEnemy += ccaBRB.getEnemy_points();
                    ccaBRB.setX2(ccaBRB.isMayX2() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
                }
            }
            
            if (ccaGame != null) {
                ccaGame.setCcagDateScreenshot(new Date((Calendar.getInstance().getTime().getTime() / 60_000) * 60_000));
                ccaGame.setCcagIncreaseOur(increaseOur);
                ccaGame.setCcagIncreaseEnemy(increaseEnemy);
            }
            
        }

        loadDataToViews();
        
    }




    public void loadDataToViews() {

        String textStartGameTime;
        String textEndGameTime;
        String textStatus;
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

        if (ccaGame != null) {

            ccaGame.calcWin();

            textStartGameTime = getString(R.string.start_game_at) + ": " + Utils.convertDateToString(ccaGame.getCcagDateStartGame(), pattern);    // дата/время начала игры
            textEndGameTime = getString(R.string.end_game_at) + ": "  + Utils.convertDateToString(ccaGame.getCcagDateEndGame(), pattern);          // дата/время окончания игры

            tv_sa_status.setText(ccaGame.getCcagStatus());   // статус
            tv_sa_start_game_time.setText(textStartGameTime);   // дата/время начала игры
            tv_sa_end_game_time.setText(textEndGameTime);       // дата/время окончания игры
            if (ccaGame.isCcagIsGameOver()) {   // если игра закончена
                tv_sa_total_time.setText("");   // время игры - пустое
            } else { // если игра не закончена
                tv_sa_total_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToEndGame())); // время игры
            }

            if (ccaOurTeam != null) iv_sa_our_team_name.setImageBitmap(ccaOurTeam.getBmpSrc());  // имя нашей команды
            if (ccaEnemyTeam != null) iv_sa_enemy_team_name.setImageBitmap(ccaEnemyTeam.getBmpSrc());  // имя команды противника

            tv_sa_early_win.setText(String.valueOf(ccaGame.getCcagEarlyWin())); // очки до досрочной победы

            tv_sa_our_increase.setText(ccaGame.getCcagIncreaseOur() == 0 ? "" : " +" + ccaGame.getCcagIncreaseOur() + " ");   // прирост нашей команды
            tv_sa_our_points.setText(String.valueOf(ccaGame.getPointsOur()));  // очки нашей команды


            tv_sa_enemy_increase.setText(ccaGame.getCcagIncreaseEnemy() == 0 ? "" : " +" + ccaGame.getCcagIncreaseEnemy() + " "); // прирост команды противника
            tv_sa_enemy_points.setText(String.valueOf(ccaGame.getPointsEnemy()));    // очки команды противника

            if (ccaGame.isCcagIsGameOver()) {   // если игра закончена
                tv_sa_our_end_time.setText(""); // наше время пустое
                tv_sa_enemy_end_time.setText(""); // время противника пустое
            } else { // если игра незакончена
                if (ccaGame.isCcagWillOurWin()) {
                    tv_sa_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                    tv_sa_enemy_end_time.setText("");   // время противника пустое
                } else if (ccaGame.isCcagWillEnemyWin()) {
                    tv_sa_our_end_time.setText(""); // наше время пустое
                    tv_sa_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                } else if (ccaGame.isCcagWillNobodyWin()) {
                    tv_sa_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                    tv_sa_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                }

            }


            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);
            int color_bxx_mayX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_mayX2_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_mayX2_main), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_mayX2), 16)));
            int color_bxx_isX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_isX2_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_isX2_main), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_isX2), 16)));

            if (ccaBLT != null) {

                iv_sa_blt_name.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_x2.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_points.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_slots.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_slots_our.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_slots_empty.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_slots_enemy.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blt_car_black.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blt_car_our.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blt_car_empty.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blt_car_enemy.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                sb_sa_blt.setVisibility(ccaBLT.isPresent() ? View.VISIBLE : View.INVISIBLE);


                if (ccaBLT.isPresent()) {
                    CityCalcArea ccaBLTname = mainCityCalc.getMapAreas().get(Area.BLT);
                    CityCalcArea ccaBLTprogress = mainCityCalc.getMapAreas().get(Area.BLT_PROGRESS);
                    if (ccaBLTname != null) iv_sa_blt_name.setImageBitmap(ccaBLTname.getBmpSrc());
                    tv_sa_blt_slots.setText(String.valueOf(ccaBLT.getSlots()));
                    tv_sa_blt_slots_our.setText(String.valueOf(ccaBLT.getSlots_our()));
                    tv_sa_blt_slots_empty.setText(String.valueOf(ccaBLT.getSlots_empty()));
                    tv_sa_blt_slots_enemy.setText(String.valueOf(ccaBLT.getSlots_enemy()));
//                    iv_sa_brt_name.setImageBitmap(ccaBLT.bmpSrc);
                    if (ccaBLT.isBuildingIsOur()) {
                        tv_sa_blt_points.setText("+" + ccaBLT.getOur_points());
                        tv_sa_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
//                        iv_sa_blt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBLT.isBuildingIsEnemy()) {
                        tv_sa_blt_points.setText("+" + ccaBLT.getEnemy_points());
                        tv_sa_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
//                        iv_sa_blt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBLT.isBuildingIsEmpty()) {
                        tv_sa_blt_points.setText("");
                        tv_sa_blt_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_sa_blt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBLT.isX2()) {
                        tv_sa_blt_x2.setText("X2");
                        tv_sa_blt_x2.setBackgroundColor(color_bxx_isX2);
                    } else {
                        if (ccaBLT.isMayX2()) {
                            tv_sa_blt_x2.setText("X2");
                            tv_sa_blt_x2.setBackgroundColor(color_bxx_mayX2);
                        } else {
                            tv_sa_blt_x2.setText("");
                            tv_sa_blt_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }

            if (ccaBLC != null) {
                iv_sa_blc_name.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_x2.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_points.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_slots.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_slots_our.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_slots_empty.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_slots_enemy.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blc_car_black.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blc_car_our.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blc_car_empty.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blc_car_enemy.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                sb_sa_blc.setVisibility(ccaBLC.isPresent() ? View.VISIBLE : View.INVISIBLE);

                if (ccaBLC.isPresent()) {
                    CityCalcArea ccaBLCname = mainCityCalc.getMapAreas().get(Area.BLC);
                    CityCalcArea ccaBLCprogress = mainCityCalc.getMapAreas().get(Area.BLC_PROGRESS);
                    if (ccaBLCname != null) iv_sa_blc_name.setImageBitmap(ccaBLCname.getBmpSrc());
                    tv_sa_blc_slots.setText(String.valueOf(ccaBLC.getSlots()));
                    tv_sa_blc_slots_our.setText(String.valueOf(ccaBLC.getSlots_our()));
                    tv_sa_blc_slots_empty.setText(String.valueOf(ccaBLC.getSlots_empty()));
                    tv_sa_blc_slots_enemy.setText(String.valueOf(ccaBLC.getSlots_enemy()));
                    if (ccaBLC.isBuildingIsOur()) {
                        tv_sa_blc_points.setText("+" + ccaBLC.getOur_points());
                        tv_sa_blc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_sa_blc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBLC.isBuildingIsEnemy()) {
                        tv_sa_blc_points.setText("+" + ccaBLC.getEnemy_points());
                        tv_sa_blc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_sa_blc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBLC.isBuildingIsEmpty()) {
                        tv_sa_blc_points.setText("");
                        tv_sa_blc_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_sa_blc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBLC.isX2()) {
                        tv_sa_blc_x2.setText("X2");
                        tv_sa_blc_x2.setBackgroundColor(color_bxx_isX2);
                    } else {
                        if (ccaBLC.isMayX2()) {
                            tv_sa_blc_x2.setText("X2");
                            tv_sa_blc_x2.setBackgroundColor(color_bxx_mayX2);
                        } else {
                            tv_sa_blc_x2.setText("");
                            tv_sa_blc_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }


            if (ccaBLB != null) {
                iv_sa_blb_name.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_x2.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_points.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_slots.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_slots_our.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_slots_empty.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_slots_enemy.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blb_car_black.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blb_car_our.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blb_car_empty.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blb_car_enemy.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                sb_sa_blb.setVisibility(ccaBLB.isPresent() ? View.VISIBLE : View.INVISIBLE);

                if (ccaBLB.isPresent()) {
                    CityCalcArea ccaBLBname = mainCityCalc.getMapAreas().get(Area.BLB);
                    CityCalcArea ccaBLBprogress = mainCityCalc.getMapAreas().get(Area.BLB_PROGRESS);
                    if (ccaBLBname != null) iv_sa_blb_name.setImageBitmap(ccaBLBname.getBmpSrc());
                    tv_sa_blb_slots.setText(String.valueOf(ccaBLB.getSlots()));
                    tv_sa_blb_slots_our.setText(String.valueOf(ccaBLB.getSlots_our()));
                    tv_sa_blb_slots_empty.setText(String.valueOf(ccaBLB.getSlots_empty()));
                    tv_sa_blb_slots_enemy.setText(String.valueOf(ccaBLB.getSlots_enemy()));
                    if (ccaBLB.isBuildingIsOur()) {
                        tv_sa_blb_points.setText("+" + ccaBLB.getOur_points());
                        tv_sa_blb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_sa_blb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBLB.isBuildingIsEnemy()) {
                        tv_sa_blb_points.setText("+" + ccaBLB.getEnemy_points());
                        tv_sa_blb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_sa_blb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBLB.isBuildingIsEmpty()) {
                        tv_sa_blb_points.setText("");
                        tv_sa_blb_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_sa_blb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBLB.isX2()) {
                        tv_sa_blb_x2.setText("X2");
                        tv_sa_blb_x2.setBackgroundColor(color_bxx_isX2);
                    } else {
                        if (ccaBLB.isMayX2()) {
                            tv_sa_blb_x2.setText("X2");
                            tv_sa_blb_x2.setBackgroundColor(color_bxx_mayX2);
                        } else {
                            tv_sa_blb_x2.setText("");
                            tv_sa_blb_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }

            if (ccaBRT != null) {
                iv_sa_brt_name.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_x2.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_points.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_slots.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_slots_our.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_slots_empty.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_slots_enemy.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brt_car_black.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brt_car_our.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brt_car_empty.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brt_car_enemy.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);
                sb_sa_brt.setVisibility(ccaBRT.isPresent() ? View.VISIBLE : View.INVISIBLE);

                if (ccaBRT.isPresent()) {
                    CityCalcArea ccaBRTname = mainCityCalc.getMapAreas().get(Area.BRT);
                    CityCalcArea ccaBRTprogress = mainCityCalc.getMapAreas().get(Area.BRT_PROGRESS);
                    if (ccaBRTname != null) iv_sa_brt_name.setImageBitmap(ccaBRTname.getBmpSrc());
                    tv_sa_brt_slots.setText(String.valueOf(ccaBRT.getSlots()));
                    tv_sa_brt_slots_our.setText(String.valueOf(ccaBRT.getSlots_our()));
                    tv_sa_brt_slots_empty.setText(String.valueOf(ccaBRT.getSlots_empty()));
                    tv_sa_brt_slots_enemy.setText(String.valueOf(ccaBRT.getSlots_enemy()));
                    if (ccaBRT.isBuildingIsOur()) {
                        tv_sa_brt_points.setText("+" + ccaBRT.getOur_points());
                        tv_sa_brt_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_sa_brt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBRT.isBuildingIsEnemy()) {
                        tv_sa_brt_points.setText("+" + ccaBRT.getEnemy_points());
                        tv_sa_brt_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_sa_brt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBRT.isBuildingIsEmpty()) {
                        tv_sa_brt_points.setText("");
                        tv_sa_brt_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_sa_brt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBRT.isX2()) {
                        tv_sa_brt_x2.setText("X2");
                        tv_sa_brt_x2.setBackgroundColor(color_bxx_isX2);
                    } else {
                        if (ccaBRT.isMayX2()) {
                            tv_sa_brt_x2.setText("X2");
                            tv_sa_brt_x2.setBackgroundColor(color_bxx_mayX2);
                        } else {
                            tv_sa_brt_x2.setText("");
                            tv_sa_brt_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }

            if (ccaBRC != null) {
                iv_sa_brc_name.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_x2.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_points.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_slots.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_slots_our.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_slots_empty.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_slots_enemy.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brc_car_black.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brc_car_our.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brc_car_empty.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brc_car_enemy.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);
                sb_sa_brc.setVisibility(ccaBRC.isPresent() ? View.VISIBLE : View.INVISIBLE);

                if (ccaBRC.isPresent()) {
                    CityCalcArea ccaBRCname = mainCityCalc.getMapAreas().get(Area.BRC);
                    CityCalcArea ccaBRCprogress = mainCityCalc.getMapAreas().get(Area.BRC_PROGRESS);
                    if (ccaBRCname != null) iv_sa_brc_name.setImageBitmap(ccaBRCname.getBmpSrc());
                    tv_sa_brc_slots.setText(String.valueOf(ccaBRC.getSlots()));
                    tv_sa_brc_slots_our.setText(String.valueOf(ccaBRC.getSlots_our()));
                    tv_sa_brc_slots_empty.setText(String.valueOf(ccaBRC.getSlots_empty()));
                    tv_sa_brc_slots_enemy.setText(String.valueOf(ccaBRC.getSlots_enemy()));
                    if (ccaBRC.isBuildingIsOur()) {
                        tv_sa_brc_points.setText("+" + ccaBRC.getOur_points());
                        tv_sa_brc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_sa_brc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBRC.isBuildingIsEnemy()) {
                        tv_sa_brc_points.setText("+" + ccaBRC.getEnemy_points());
                        tv_sa_brc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_sa_brc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBRC.isBuildingIsEmpty()) {
                        tv_sa_brc_points.setText("");
                        tv_sa_brc_points.setBackgroundColor(0xFFFFFF);
//                        iv_sa_brc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBRC.isX2()) {
                        tv_sa_brc_x2.setText("X2");
                        tv_sa_brc_x2.setBackgroundColor(color_bxx_isX2);
                    } else {
                        if (ccaBRC.isMayX2()) {
                            tv_sa_brc_x2.setText("X2");
                            tv_sa_brc_x2.setBackgroundColor(color_bxx_mayX2);
                        } else {
                            tv_sa_brc_x2.setText("");
                            tv_sa_brc_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }


            if (ccaBRB != null) {
                iv_sa_brb_name.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_x2.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_points.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_slots.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_slots_our.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_slots_empty.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_slots_enemy.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brb_car_black.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brb_car_our.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brb_car_empty.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brb_car_enemy.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);
                sb_sa_brb.setVisibility(ccaBRB.isPresent() ? View.VISIBLE : View.INVISIBLE);

                if (ccaBRB.isPresent()) {
                    CityCalcArea ccaBRBname = mainCityCalc.getMapAreas().get(Area.BRB);
                    CityCalcArea ccaBRBprogress = mainCityCalc.getMapAreas().get(Area.BRB_PROGRESS);
                    if (ccaBRBname != null) iv_sa_brb_name.setImageBitmap(ccaBRBname.getBmpSrc());
                    tv_sa_brb_slots.setText(String.valueOf(ccaBRB.getSlots()));
                    tv_sa_brb_slots_our.setText(String.valueOf(ccaBRB.getSlots_our()));
                    tv_sa_brb_slots_empty.setText(String.valueOf(ccaBRB.getSlots_empty()));
                    tv_sa_brb_slots_enemy.setText(String.valueOf(ccaBRB.getSlots_enemy()));
                    if (ccaBRB.isBuildingIsOur()) {
                        tv_sa_brb_points.setText("+" + ccaBRB.getOur_points());
                        tv_sa_brb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_sa_brb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBRB.isBuildingIsEnemy()) {
                        tv_sa_brb_points.setText("+" + ccaBRB.getEnemy_points());
                        tv_sa_brb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_sa_brb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBRB.isBuildingIsEmpty()) {
                        tv_sa_brb_points.setText("");
                        tv_sa_brb_points.setBackgroundColor(0xFFFFFF);
//                        iv_sa_brb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBRB.isX2()) {
                        tv_sa_brb_x2.setText("X2");
                        tv_sa_brb_x2.setBackgroundColor(color_bxx_isX2);
                    } else {
                        if (ccaBRB.isMayX2()) {
                            tv_sa_brb_x2.setText("X2");
                            tv_sa_brb_x2.setBackgroundColor(color_bxx_mayX2);
                        } else {
                            tv_sa_brb_x2.setText("");
                            tv_sa_brb_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }

            personsOur = (int)Math.ceil (slotsOur / 3.0d);
            personsEnemy = (int)Math.ceil (slotsEnemy / 3.0d);

            sb_sa_person_our.setProgress(personsOur);
            sb_sa_person_enemy.setProgress(personsEnemy);

            tv_sa_person_our.setText(String.valueOf(personsOur));
            tv_sa_person_enemy.setText(String.valueOf(personsEnemy));
            tv_sa_car_our.setText(String.valueOf(personsOur*3));
            tv_sa_car_enemy.setText(String.valueOf(personsEnemy*3));

        }
        

    }


    public void initializeViews() {

        // Game views
        tv_sa_start_game_time = findViewById(R.id.tv_sa_start_game_time);
        tv_sa_end_game_time = findViewById(R.id.tv_sa_end_game_time);
        tv_sa_status = findViewById(R.id.tv_sa_status);
        tv_sa_vs = findViewById(R.id.tv_sa_vs);

        // Time views
        tv_sa_total_time = findViewById(R.id.tv_sa_total_time);
        tv_sa_early_win = findViewById(R.id.tv_sa_early_win);

        // Our team views
        iv_sa_our_team_name = findViewById(R.id.iv_sa_our_team_name);
        tv_sa_our_increase = findViewById(R.id.tv_sa_our_increase);
        tv_sa_our_points = findViewById(R.id.tv_sa_our_points);
        tv_sa_our_end_time = findViewById(R.id.tv_sa_our_end_time);

        // Enemy team views
        iv_sa_enemy_team_name = findViewById(R.id.iv_sa_enemy_team_name);
        tv_sa_enemy_increase = findViewById(R.id.tv_sa_enemy_increase);
        tv_sa_enemy_points = findViewById(R.id.tv_sa_enemy_points);
        tv_sa_enemy_end_time = findViewById(R.id.tv_sa_enemy_end_time);

        // BLT views
        iv_sa_blt_name = findViewById(R.id.iv_sa_blt_name);
        tv_sa_blt_x2 = findViewById(R.id.tv_sa_blt_x2);
        tv_sa_blt_points = findViewById(R.id.tv_sa_blt_points);
        tv_sa_blt_slots = findViewById(R.id.tv_sa_blt_slots);
        tv_sa_blt_slots_our = findViewById(R.id.tv_sa_blt_slots_our);
        tv_sa_blt_slots_empty = findViewById(R.id.tv_sa_blt_slots_empty);
        tv_sa_blt_slots_enemy = findViewById(R.id.tv_sa_blt_slots_enemy);
        iv_sa_blt_car_black = findViewById(R.id.iv_sa_blt_car_black);
        iv_sa_blt_car_our = findViewById(R.id.iv_sa_blt_car_our);
        iv_sa_blt_car_empty = findViewById(R.id.iv_sa_blt_car_empty);
        iv_sa_blt_car_enemy = findViewById(R.id.iv_sa_blt_car_enemy);
        sb_sa_blt = findViewById(R.id.sb_sa_blt);

        // BLC views
        iv_sa_blc_name = findViewById(R.id.iv_sa_blc_name);
        tv_sa_blc_x2 = findViewById(R.id.tv_sa_blc_x2);
        tv_sa_blc_points = findViewById(R.id.tv_sa_blc_points);
        tv_sa_blc_slots = findViewById(R.id.tv_sa_blc_slots);
        tv_sa_blc_slots_our = findViewById(R.id.tv_sa_blc_slots_our);
        tv_sa_blc_slots_empty = findViewById(R.id.tv_sa_blc_slots_empty);
        tv_sa_blc_slots_enemy = findViewById(R.id.tv_sa_blc_slots_enemy);
        iv_sa_blc_car_black = findViewById(R.id.iv_sa_blc_car_black);
        iv_sa_blc_car_our = findViewById(R.id.iv_sa_blc_car_our);
        iv_sa_blc_car_empty = findViewById(R.id.iv_sa_blc_car_empty);
        iv_sa_blc_car_enemy = findViewById(R.id.iv_sa_blc_car_enemy);
        sb_sa_blc = findViewById(R.id.sb_sa_blc);

        // BLB views
        iv_sa_blb_name = findViewById(R.id.iv_sa_blb_name);
        tv_sa_blb_x2 = findViewById(R.id.tv_sa_blb_x2);
        tv_sa_blb_points = findViewById(R.id.tv_sa_blb_points);
        tv_sa_blb_slots = findViewById(R.id.tv_sa_blb_slots);
        tv_sa_blb_slots_our = findViewById(R.id.tv_sa_blb_slots_our);
        tv_sa_blb_slots_empty = findViewById(R.id.tv_sa_blb_slots_empty);
        tv_sa_blb_slots_enemy = findViewById(R.id.tv_sa_blb_slots_enemy);
        iv_sa_blb_car_black = findViewById(R.id.iv_sa_blb_car_black);
        iv_sa_blb_car_our = findViewById(R.id.iv_sa_blb_car_our);
        iv_sa_blb_car_empty = findViewById(R.id.iv_sa_blb_car_empty);
        iv_sa_blb_car_enemy = findViewById(R.id.iv_sa_blb_car_enemy);
        sb_sa_blb = findViewById(R.id.sb_sa_blb);

        // BRT views
        iv_sa_brt_name = findViewById(R.id.iv_sa_brt_name);
        tv_sa_brt_x2 = findViewById(R.id.tv_sa_brt_x2);
        tv_sa_brt_points = findViewById(R.id.tv_sa_brt_points);
        tv_sa_brt_slots = findViewById(R.id.tv_sa_brt_slots);
        tv_sa_brt_slots_our = findViewById(R.id.tv_sa_brt_slots_our);
        tv_sa_brt_slots_empty = findViewById(R.id.tv_sa_brt_slots_empty);
        tv_sa_brt_slots_enemy = findViewById(R.id.tv_sa_brt_slots_enemy);
        iv_sa_brt_car_black = findViewById(R.id.iv_sa_brt_car_black);
        iv_sa_brt_car_our = findViewById(R.id.iv_sa_brt_car_our);
        iv_sa_brt_car_empty = findViewById(R.id.iv_sa_brt_car_empty);
        iv_sa_brt_car_enemy = findViewById(R.id.iv_sa_brt_car_enemy);
        sb_sa_brt = findViewById(R.id.sb_sa_brt);

        // BRC views
        iv_sa_brc_name = findViewById(R.id.iv_sa_brc_name);
        tv_sa_brc_x2 = findViewById(R.id.tv_sa_brc_x2);
        tv_sa_brc_points = findViewById(R.id.tv_sa_brc_points);
        tv_sa_brc_slots = findViewById(R.id.tv_sa_brc_slots);
        tv_sa_brc_slots_our = findViewById(R.id.tv_sa_brc_slots_our);
        tv_sa_brc_slots_empty = findViewById(R.id.tv_sa_brc_slots_empty);
        tv_sa_brc_slots_enemy = findViewById(R.id.tv_sa_brc_slots_enemy);
        iv_sa_brc_car_black = findViewById(R.id.iv_sa_brc_car_black);
        iv_sa_brc_car_our = findViewById(R.id.iv_sa_brc_car_our);
        iv_sa_brc_car_empty = findViewById(R.id.iv_sa_brc_car_empty);
        iv_sa_brc_car_enemy = findViewById(R.id.iv_sa_brc_car_enemy);
        sb_sa_brc = findViewById(R.id.sb_sa_brc);

        // BRB views
        iv_sa_brb_name = findViewById(R.id.iv_sa_brb_name);
        tv_sa_brb_x2 = findViewById(R.id.tv_sa_brb_x2);
        tv_sa_brb_points = findViewById(R.id.tv_sa_brb_points);
        tv_sa_brb_slots = findViewById(R.id.tv_sa_brb_slots);
        tv_sa_brb_slots_our = findViewById(R.id.tv_sa_brb_slots_our);
        tv_sa_brb_slots_empty = findViewById(R.id.tv_sa_brb_slots_empty);
        tv_sa_brb_slots_enemy = findViewById(R.id.tv_sa_brb_slots_enemy);
        iv_sa_brb_car_black = findViewById(R.id.iv_sa_brb_car_black);
        iv_sa_brb_car_our = findViewById(R.id.iv_sa_brb_car_our);
        iv_sa_brb_car_empty = findViewById(R.id.iv_sa_brb_car_empty);
        iv_sa_brb_car_enemy = findViewById(R.id.iv_sa_brb_car_enemy);
        sb_sa_brb = findViewById(R.id.sb_sa_brb);

        sb_sa_person_our = findViewById(R.id.sb_sa_person_our);
        iv_sa_person_our = findViewById(R.id.iv_sa_person_our);
        tv_sa_person_our = findViewById(R.id.tv_sa_person_our);
        iv_sa_car_our = findViewById(R.id.iv_sa_car_our);
        tv_sa_car_our = findViewById(R.id.tv_sa_car_our);

        sb_sa_person_enemy = findViewById(R.id.sb_sa_person_enemy);
        iv_sa_person_enemy = findViewById(R.id.iv_sa_person_enemy);
        tv_sa_person_enemy = findViewById(R.id.tv_sa_person_enemy);
        iv_sa_car_enemy = findViewById(R.id.iv_sa_car_enemy);
        tv_sa_car_enemy = findViewById(R.id.tv_sa_car_enemy);

        bt_sa_calc_timed_win = findViewById(R.id.bt_sa_calc_timed_win);
        bt_sa_calc_early_win = findViewById(R.id.bt_sa_calc_early_win);

        // Рекламный блок
        ad_sa_banner = findViewById(R.id.ad_sa_banner);
    }

    private SeekBar.OnSeekBarChangeListener bltSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress == 0) {
                sb_sa_blt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sb_sa_blt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sb_sa_blt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
            }
            if (needUpdateSlots) {
                setCarsInBuildings();
            }
            
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener blcSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress == 0) {
                sb_sa_blc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sb_sa_blc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sb_sa_blc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
            }
            if (needUpdateSlots) {
                setCarsInBuildings();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener blbSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress == 0) {
                sb_sa_blb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sb_sa_blb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sb_sa_blb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
            }
            if (needUpdateSlots) {
                setCarsInBuildings();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener brtSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress == 0) {
                sb_sa_brt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sb_sa_brt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sb_sa_brt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
            }
            if (needUpdateSlots) {
                setCarsInBuildings();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener brcSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress == 0) {
                sb_sa_brc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sb_sa_brc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sb_sa_brc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
            }
            if (needUpdateSlots) {
                setCarsInBuildings();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener brbSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress == 0) {
                sb_sa_brb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sb_sa_brb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sb_sa_brb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
            }
            if (needUpdateSlots) {
                setCarsInBuildings();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener personOurSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            if (needUpdateSlots) {
                tv_sa_person_our.setText(String.valueOf(progress));
                tv_sa_car_our.setText(String.valueOf(progress*3));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener personEnemySeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (needUpdateSlots) {
                tv_sa_person_enemy.setText(String.valueOf(progress));
                tv_sa_car_enemy.setText(String.valueOf(progress*3));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    /**
     * Событие нажатия какой-то кнопки в шапке
     **/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // индекс нажакой кнопки
        if (id == android.R.id.home) { //если в шапке нажата кнопка "Назад"
            onBackPressed();    // вызываем метод "Назад"
            return true;        // возвращаем Истину
        }
        return super.onOptionsItemSelected(item);   // возвращаем супер-метод
    }

    public void calcTimedWin(View view) {

        Object[] matrixArray = getMaxtrixProgressArray();
        Arrays.sort(matrixArray);
        MatrixProgress matrix = null;
        boolean isFound = false;
        int index=0;
        for (int i = 0; i < matrixArray.length; i++) {
            matrix = (MatrixProgress) matrixArray[i];
            if (matrix.willOurWin && !matrix.willEarlyWin) {
                index = i;
                isFound = true;
                break;
            }
        }
        if (isFound) {
            new MatrixProgress(matrix.blt_progress, matrix.blc_progress, matrix.blb_progress, matrix.brt_progress, matrix.brc_progress, matrix.brb_progress);
        } else {
            Toast toast = Toast.makeText(this, R.string.there_no_options, Toast.LENGTH_LONG);
            View viewToast = toast.getView();
            viewToast.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            TextView textView = viewToast.findViewById(android.R.id.message);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(Color.WHITE);
            toast.show();
        }
    }

    public void calcTimedWinWithoutx2(View view) {

        Object[] matrixArray = getMaxtrixProgressArray();
        Arrays.sort(matrixArray);
        MatrixProgress matrix = null;
        boolean isFound = false;
        int index=0;
        for (int i = 0; i < matrixArray.length; i++) {
            matrix = (MatrixProgress) matrixArray[i];
            if (matrix.willOurWin && !matrix.willEarlyWin && matrix.countOurX2 == 0) {
                index = i;
                isFound = true;
                break;
            }
        }
        if (isFound) {
            new MatrixProgress(matrix.blt_progress, matrix.blc_progress, matrix.blb_progress, matrix.brt_progress, matrix.brc_progress, matrix.brb_progress);
        } else {
            Toast toast = Toast.makeText(this, R.string.there_no_options, Toast.LENGTH_LONG);
            View viewToast = toast.getView();
            viewToast.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            TextView textView = viewToast.findViewById(android.R.id.message);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(Color.WHITE);
            toast.show();
        }
    }

    public void calcEarlyWin(View view) {

        Object[] matrixArray = getMaxtrixProgressArray();
        Arrays.sort(matrixArray);
        MatrixProgress matrix = null;
        boolean isFound = false;
        int index=0;
        for (int i = 0; i < matrixArray.length; i++) {
            matrix = (MatrixProgress) matrixArray[i];
            if (matrix.willOurWin && matrix.willEarlyWin) {
                index = i;
                isFound = true;
                break;
            }
        }
        if (isFound) {
            new MatrixProgress(matrix.blt_progress, matrix.blc_progress, matrix.blb_progress, matrix.brt_progress, matrix.brc_progress, matrix.brb_progress);
        } else {
            Toast toast = Toast.makeText(this, R.string.there_no_options, Toast.LENGTH_LONG);
            View viewToast = toast.getView();
            viewToast.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            TextView textView = viewToast.findViewById(android.R.id.message);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(Color.WHITE);
            toast.show();
        }
    }

    public void calcEarlyWinWithoutx2(View view) {

        Object[] matrixArray = getMaxtrixProgressArray();
        Arrays.sort(matrixArray);
        MatrixProgress matrix = null;
        boolean isFound = false;
        int index=0;
        for (int i = 0; i < matrixArray.length; i++) {
            matrix = (MatrixProgress) matrixArray[i];
            if (matrix.willOurWin && matrix.willEarlyWin && matrix.countOurX2 == 0) {
                index = i;
                isFound = true;
                break;
            }
        }
        if (isFound) {
            new MatrixProgress(matrix.blt_progress, matrix.blc_progress, matrix.blb_progress, matrix.brt_progress, matrix.brc_progress, matrix.brb_progress);
        } else {
            Toast toast = Toast.makeText(this, R.string.there_no_options, Toast.LENGTH_LONG);
            View viewToast = toast.getView();
            viewToast.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            TextView textView = viewToast.findViewById(android.R.id.message);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(Color.WHITE);
            toast.show();
        }
    }

    private Object[] getMaxtrixProgressArray() {
        List<MatrixProgress> list = new ArrayList<>();

        for (int x1 = 0; x1 <= 1; x1++) {
            for (int x2 = 0; x2 <= 1; x2++) {
                for (int x3 = 0; x3 <= 1; x3++) {
                    for (int x4 = 0; x4 <= 1; x4++) {
                        for (int x5 = 0; x5 <= 1; x5++) {
                            for (int x6 = 0; x6 <= 1; x6++) {
                                list.add(new MatrixProgress(x1*2,x2*2,x3*2,x4*2,x5*2,x6*2));
                            }
                        }
                    }
                }
            }
        }

        return list.toArray();

    }


    class MatrixProgress implements Comparable<MatrixProgress>{

        int personsOur;
        int blt_progress;
        int blc_progress;
        int blb_progress;
        int brt_progress;
        int brc_progress;
        int brb_progress;
        int differentPoints;
        boolean willEarlyWin;
        boolean willOurWin;
        int countOurX2;

        public MatrixProgress() {
        }

        public MatrixProgress(int blt_progress, int blc_progress, int blb_progress, int brt_progress, int brc_progress, int brb_progress) {

            this.blt_progress = blt_progress;
            this.blc_progress = blc_progress;
            this.blb_progress = blb_progress;
            this.brt_progress = brt_progress;
            this.brc_progress = brc_progress;
            this.brb_progress = brb_progress;


            needUpdateSlots = false;
            sb_sa_blt.setProgress(blt_progress);
            sb_sa_blc.setProgress(blc_progress);
            sb_sa_blb.setProgress(blb_progress);
            sb_sa_brt.setProgress(blt_progress);
            sb_sa_brc.setProgress(brc_progress);
            sb_sa_brb.setProgress(brb_progress);
            needUpdateSlots = true;

            setCarsInBuildings();

            this.personsOur = sb_sa_person_our.getProgress();
            CCAGame ccaGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);
            if (ccaGame != null) {
                int countOurX2 = 0;
                this.willEarlyWin = ccaGame.isCcagWillEarlyWin();
                this.willOurWin = ccaGame.isCcagWillOurWin();
                this.differentPoints = ccaGame.getDifferentPoints();
                CCABuilding ccab;
                ccab = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLT); if (ccab.isPresent() && ccab.isMayX2() && ccab.isBuildingIsOur()) countOurX2++;
                ccab = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLC); if (ccab.isPresent() && ccab.isMayX2() && ccab.isBuildingIsOur()) countOurX2++;
                ccab = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLB); if (ccab.isPresent() && ccab.isMayX2() && ccab.isBuildingIsOur()) countOurX2++;
                ccab = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRT); if (ccab.isPresent() && ccab.isMayX2() && ccab.isBuildingIsOur()) countOurX2++;
                ccab = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRC); if (ccab.isPresent() && ccab.isMayX2() && ccab.isBuildingIsOur()) countOurX2++;
                ccab = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRB); if (ccab.isPresent() && ccab.isMayX2() && ccab.isBuildingIsOur()) countOurX2++;
                this.countOurX2 = countOurX2;
            }

        }

        @Override
        public int compareTo(MatrixProgress o) {
            Integer a, b, compare;
            a = this.personsOur;
            b = o.personsOur;
            compare = a.compareTo(b);
            if (compare == 0) {
                a = this.differentPoints;
                b = o.differentPoints;
                compare = a.compareTo(b);
            }
            return compare;
        }
    }

}
