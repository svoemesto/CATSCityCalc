package com.svoemestodev.catscitycalc;

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
        CCAGame ccaGame = (CCAGame) mainCityCalc.mapAreas.get(Area.CITY);
        CCATeam ccaTeamOur = (CCATeam) mainCityCalc.mapAreas.get(Area.TEAM_NAME_OUR);
        CCATeam ccaTeamEnemy = (CCATeam) mainCityCalc.mapAreas.get(Area.TEAM_NAME_ENEMY);
        if (ccaGame != null) {
            dateScreenshotMain = ccaGame.ccagDateScreenshot;
        }
        if (ccaTeamOur != null) {
            increaseOurMain = ccaTeamOur.ccatIncrease;
            pointsInScreenshotOurMain = ccaTeamOur.ccatPointsInScreenshot;
        }
        if (ccaTeamEnemy != null) {
            increaseEnemyMain = ccaTeamEnemy.ccatIncrease;
            pointsInScreenshotEnemyMain = ccaTeamEnemy.ccatPointsInScreenshot;
        }


        CCABuilding ccaBLT = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLT);
        CCABuilding ccaBLC = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLC);
        CCABuilding ccaBLB = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLB);
        CCABuilding ccaBRT = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRT);
        CCABuilding ccaBRC = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRC);
        CCABuilding ccaBRB = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRB);

        slotsTotal = 0;
        slotsOur = 0;
        slotsEnemy = 0;

        needUpdateSlots = false;
        
        if (ccaBLT != null) {
            if (ccaBLT.isPresent) {
                if (ccaBLT.buildingIsOur) sb_sa_blt.setProgress(0);
                if (ccaBLT.buildingIsEmpty) sb_sa_blt.setProgress(1);
                if (ccaBLT.buildingIsEnemy) sb_sa_blt.setProgress(2);
                slotsTotal += ccaBLT.slots;
                slotsOur += ccaBLT.slots_our;
                slotsEnemy += ccaBLT.slots_enemy;
            }
        }

        if (ccaBLC != null) {
            if (ccaBLC.isPresent) {
                if (ccaBLC.buildingIsOur) sb_sa_blc.setProgress(0);
                if (ccaBLC.buildingIsEmpty) sb_sa_blc.setProgress(1);
                if (ccaBLC.buildingIsEnemy) sb_sa_blc.setProgress(2);
                slotsTotal += ccaBLC.slots;
                slotsOur += ccaBLC.slots_our;
                slotsEnemy += ccaBLC.slots_enemy;
            }
        }

        if (ccaBLB != null) {
            if (ccaBLB.isPresent) {
                if (ccaBLB.buildingIsOur) sb_sa_blb.setProgress(0);
                if (ccaBLB.buildingIsEmpty) sb_sa_blb.setProgress(1);
                if (ccaBLB.buildingIsEnemy) sb_sa_blb.setProgress(2);
                slotsTotal += ccaBLB.slots;
                slotsOur += ccaBLB.slots_our;
                slotsEnemy += ccaBLB.slots_enemy;
            }
        }

        if (ccaBRT != null) {
            if (ccaBRT.isPresent) {
                if (ccaBRT.buildingIsOur) sb_sa_brt.setProgress(0);
                if (ccaBRT.buildingIsEmpty) sb_sa_brt.setProgress(1);
                if (ccaBRT.buildingIsEnemy) sb_sa_brt.setProgress(2);
                slotsTotal += ccaBRT.slots;
                slotsOur += ccaBRT.slots_our;
                slotsEnemy += ccaBRT.slots_enemy;
            }
        }

        if (ccaBRC != null) {
            if (ccaBRC.isPresent) {
                if (ccaBRC.buildingIsOur) sb_sa_brc.setProgress(0);
                if (ccaBRC.buildingIsEmpty) sb_sa_brc.setProgress(1);
                if (ccaBRC.buildingIsEnemy) sb_sa_brc.setProgress(2);
                slotsTotal += ccaBRC.slots;
                slotsOur += ccaBRC.slots_our;
                slotsEnemy += ccaBRC.slots_enemy;
            }
        }

        if (ccaBRB != null) {
            if (ccaBRB.isPresent) {
                if (ccaBRB.buildingIsOur) sb_sa_brb.setProgress(0);
                if (ccaBRB.buildingIsEmpty) sb_sa_brb.setProgress(1);
                if (ccaBRB.buildingIsEnemy) sb_sa_brb.setProgress(2);
                slotsTotal += ccaBRB.slots;
                slotsOur += ccaBRB.slots_our;
                slotsEnemy += ccaBRB.slots_enemy;
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

            CCAGame ccaGame = (CCAGame) mainCityCalc.mapAreas.get(Area.CITY);
            CCATeam ccaTeamOur = (CCATeam) mainCityCalc.mapAreas.get(Area.TEAM_NAME_OUR);
            CCATeam ccaTeamEnemy = (CCATeam) mainCityCalc.mapAreas.get(Area.TEAM_NAME_ENEMY);
            CCABuilding ccaBLT = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLT);
            CCABuilding ccaBLC = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLC);
            CCABuilding ccaBLB = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLB);
            CCABuilding ccaBRT = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRT);
            CCABuilding ccaBRC = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRC);
            CCABuilding ccaBRB = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRB);

            slotsOur = 0;
            slotsEnemy = 0;
            slotsEmpty = 0;
            increaseOur = 0;
            increaseEnemy = 0;
            countMayX2 = 0;
            countMayX2our = 0;
            countMayX2enemy = 0;
            countMayX2empty = 0;
            
            if (ccaTeamOur != null) {
                ccaTeamOur.ccatIncrease = increaseOurMain;
                ccaTeamOur.ccatPointsInScreenshot = pointsInScreenshotOurMain;
            }

            if (ccaTeamEnemy != null) {
                ccaTeamEnemy.ccatIncrease = increaseEnemyMain;
                ccaTeamEnemy.ccatPointsInScreenshot = pointsInScreenshotEnemyMain;
            }
            
            if (ccaGame != null) {
                ccaGame.ccagDateScreenshot = dateScreenshotMain;
                ccaGame.calcWin();
            }

            if (ccaTeamOur != null) {
                ccaTeamOur.ccatPointsInScreenshot = ccaTeamOur.getPoints();
            }

            if (ccaTeamEnemy != null) {
                ccaTeamEnemy.ccatPointsInScreenshot = ccaTeamEnemy.getPoints();
            }

            
            if (ccaBLT != null) {
                if (ccaBLT.isPresent) {
                    ccaBLT.buildingIsOur = sb_sa_blt.getProgress() == 0;
                    ccaBLT.buildingIsEmpty = sb_sa_blt.getProgress() == 1;
                    ccaBLT.buildingIsEnemy = sb_sa_blt.getProgress() == 2;
                    ccaBLT.slots_our = ccaBLT.buildingIsOur ? (int)Math.ceil(ccaBLT.slots / 2.0d) : 0; 
                    ccaBLT.slots_empty = ccaBLT.buildingIsEmpty ? (int)Math.ceil(ccaBLT.slots / 2.0d) : 0; 
                    ccaBLT.slots_enemy = ccaBLT.buildingIsEnemy ? (int)Math.ceil(ccaBLT.slots / 2.0d) : 0;
                    slotsOur += ccaBLT.slots_our;
                    slotsEmpty += ccaBLT.slots_empty;
                    slotsEnemy += ccaBLT.slots_enemy;
                    countMayX2 += ccaBLT.mayX2 ? 1 : 0;
                    countMayX2our += ccaBLT.mayX2 && ccaBLT.buildingIsOur ? 1 : 0;
                    countMayX2empty += ccaBLT.mayX2 && ccaBLT.buildingIsEmpty ? 1 : 0;
                    countMayX2enemy += ccaBLT.mayX2 && ccaBLT.buildingIsEnemy ? 1 : 0;
                }
            }

            if (ccaBLC != null) {
                if (ccaBLC.isPresent) {
                    ccaBLC.buildingIsOur = sb_sa_blc.getProgress() == 0;
                    ccaBLC.buildingIsEmpty = sb_sa_blc.getProgress() == 1;
                    ccaBLC.buildingIsEnemy = sb_sa_blc.getProgress() == 2;
                    ccaBLC.slots_our = ccaBLC.buildingIsOur ? (int)Math.ceil(ccaBLC.slots / 2.0d) : 0;
                    ccaBLC.slots_empty = ccaBLC.buildingIsEmpty ? (int)Math.ceil(ccaBLC.slots / 2.0d) : 0;
                    ccaBLC.slots_enemy = ccaBLC.buildingIsEnemy ? (int)Math.ceil(ccaBLC.slots / 2.0d) : 0;
                    slotsOur += ccaBLC.slots_our;
                    slotsEmpty += ccaBLC.slots_empty;
                    slotsEnemy += ccaBLC.slots_enemy;
                    countMayX2 += ccaBLC.mayX2 ? 1 : 0;
                    countMayX2our += ccaBLC.mayX2 && ccaBLC.buildingIsOur ? 1 : 0;
                    countMayX2empty += ccaBLC.mayX2 && ccaBLC.buildingIsEmpty ? 1 : 0;
                    countMayX2enemy += ccaBLC.mayX2 && ccaBLC.buildingIsEnemy ? 1 : 0;
                }
            }

            if (ccaBLB != null) {
                if (ccaBLB.isPresent) {
                    ccaBLB.buildingIsOur = sb_sa_blb.getProgress() == 0;
                    ccaBLB.buildingIsEmpty = sb_sa_blb.getProgress() == 1;
                    ccaBLB.buildingIsEnemy = sb_sa_blb.getProgress() == 2;
                    ccaBLB.slots_our = ccaBLB.buildingIsOur ? (int)Math.ceil(ccaBLB.slots / 2.0d) : 0;
                    ccaBLB.slots_empty = ccaBLB.buildingIsEmpty ? (int)Math.ceil(ccaBLB.slots / 2.0d) : 0;
                    ccaBLB.slots_enemy = ccaBLB.buildingIsEnemy ? (int)Math.ceil(ccaBLB.slots / 2.0d) : 0;
                    slotsOur += ccaBLB.slots_our;
                    slotsEmpty += ccaBLB.slots_empty;
                    slotsEnemy += ccaBLB.slots_enemy;
                    countMayX2 += ccaBLB.mayX2 ? 1 : 0;
                    countMayX2our += ccaBLB.mayX2 && ccaBLB.buildingIsOur ? 1 : 0;
                    countMayX2empty += ccaBLB.mayX2 && ccaBLB.buildingIsEmpty ? 1 : 0;
                    countMayX2enemy += ccaBLB.mayX2 && ccaBLB.buildingIsEnemy ? 1 : 0;
                }
            }

            if (ccaBRT != null) {
                if (ccaBRT.isPresent) {
                    ccaBRT.buildingIsOur = sb_sa_brt.getProgress() == 0;
                    ccaBRT.buildingIsEmpty = sb_sa_brt.getProgress() == 1;
                    ccaBRT.buildingIsEnemy = sb_sa_brt.getProgress() == 2;
                    ccaBRT.slots_our = ccaBRT.buildingIsOur ? (int)Math.ceil(ccaBRT.slots / 2.0d) : 0;
                    ccaBRT.slots_empty = ccaBRT.buildingIsEmpty ? (int)Math.ceil(ccaBRT.slots / 2.0d) : 0;
                    ccaBRT.slots_enemy = ccaBRT.buildingIsEnemy ? (int)Math.ceil(ccaBRT.slots / 2.0d) : 0;
                    slotsOur += ccaBRT.slots_our;
                    slotsEmpty += ccaBRT.slots_empty;
                    slotsEnemy += ccaBRT.slots_enemy;
                    countMayX2 += ccaBRT.mayX2 ? 1 : 0;
                    countMayX2our += ccaBRT.mayX2 && ccaBRT.buildingIsOur ? 1 : 0;
                    countMayX2empty += ccaBRT.mayX2 && ccaBRT.buildingIsEmpty ? 1 : 0;
                    countMayX2enemy += ccaBRT.mayX2 && ccaBRT.buildingIsEnemy ? 1 : 0;
                }
            }

            if (ccaBRC != null) {
                if (ccaBRC.isPresent) {
                    ccaBRC.buildingIsOur = sb_sa_brc.getProgress() == 0;
                    ccaBRC.buildingIsEmpty = sb_sa_brc.getProgress() == 1;
                    ccaBRC.buildingIsEnemy = sb_sa_brc.getProgress() == 2;
                    ccaBRC.slots_our = ccaBRC.buildingIsOur ? (int)Math.ceil(ccaBRC.slots / 2.0d) : 0;
                    ccaBRC.slots_empty = ccaBRC.buildingIsEmpty ? (int)Math.ceil(ccaBRC.slots / 2.0d) : 0;
                    ccaBRC.slots_enemy = ccaBRC.buildingIsEnemy ? (int)Math.ceil(ccaBRC.slots / 2.0d) : 0;
                    slotsOur += ccaBRC.slots_our;
                    slotsEmpty += ccaBRC.slots_empty;
                    slotsEnemy += ccaBRC.slots_enemy;
                    countMayX2 += ccaBRC.mayX2 ? 1 : 0;
                    countMayX2our += ccaBRC.mayX2 && ccaBRC.buildingIsOur ? 1 : 0;
                    countMayX2empty += ccaBRC.mayX2 && ccaBRC.buildingIsEmpty ? 1 : 0;
                    countMayX2enemy += ccaBRC.mayX2 && ccaBRC.buildingIsEnemy ? 1 : 0;
                }
            }

            if (ccaBRB != null) {
                if (ccaBRB.isPresent) {
                    ccaBRB.buildingIsOur = sb_sa_brb.getProgress() == 0;
                    ccaBRB.buildingIsEmpty = sb_sa_brb.getProgress() == 1;
                    ccaBRB.buildingIsEnemy = sb_sa_brb.getProgress() == 2;
                    ccaBRB.slots_our = ccaBRB.buildingIsOur ? (int)Math.ceil(ccaBRB.slots / 2.0d) : 0;
                    ccaBRB.slots_empty = ccaBRB.buildingIsEmpty ? (int)Math.ceil(ccaBRB.slots / 2.0d) : 0;
                    ccaBRB.slots_enemy = ccaBRB.buildingIsEnemy ? (int)Math.ceil(ccaBRB.slots / 2.0d) : 0;
                    slotsOur += ccaBRB.slots_our;
                    slotsEmpty += ccaBRB.slots_empty;
                    slotsEnemy += ccaBRB.slots_enemy;
                    countMayX2 += ccaBRB.mayX2 ? 1 : 0;
                    countMayX2our += ccaBRB.mayX2 && ccaBRB.buildingIsOur ? 1 : 0;
                    countMayX2empty += ccaBRB.mayX2 && ccaBRB.buildingIsEmpty ? 1 : 0;
                    countMayX2enemy += ccaBRB.mayX2 && ccaBRB.buildingIsEnemy ? 1 : 0;
                }
            }


            if (ccaBLT != null) {
                if (ccaBLT.isPresent) {
                    ccaBLT.our_points = !ccaBLT.buildingIsOur ? 0 : ccaBLT.slots * (!ccaBLT.mayX2 ? 1 : countMayX2 == countMayX2our ? 2 : 1);
                    ccaBLT.enemy_points = !ccaBLT.buildingIsEnemy ? 0 : ccaBLT.slots * (!ccaBLT.mayX2 ? 1 : countMayX2 == countMayX2enemy ? 2 : 1);
                    increaseOur += ccaBLT.our_points;
                    increaseEnemy += ccaBLT.enemy_points;
                    ccaBLT.isX2 = ccaBLT.mayX2 && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy);
                }
            }

            if (ccaBLC != null) {
                if (ccaBLC.isPresent) {
                    ccaBLC.our_points = !ccaBLC.buildingIsOur ? 0 : ccaBLC.slots * (!ccaBLC.mayX2 ? 1 : countMayX2 == countMayX2our ? 2 : 1);
                    ccaBLC.enemy_points = !ccaBLC.buildingIsEnemy ? 0 : ccaBLC.slots * (!ccaBLC.mayX2 ? 1 : countMayX2 == countMayX2enemy ? 2 : 1);
                    increaseOur += ccaBLC.our_points;
                    increaseEnemy += ccaBLC.enemy_points;
                    ccaBLC.isX2 = ccaBLC.mayX2 && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy);
                }
            }

            if (ccaBLB != null) {
                if (ccaBLB.isPresent) {
                    ccaBLB.our_points = !ccaBLB.buildingIsOur ? 0 : ccaBLB.slots * (!ccaBLB.mayX2 ? 1 : countMayX2 == countMayX2our ? 2 : 1);
                    ccaBLB.enemy_points = !ccaBLB.buildingIsEnemy ? 0 : ccaBLB.slots * (!ccaBLB.mayX2 ? 1 : countMayX2 == countMayX2enemy ? 2 : 1);
                    increaseOur += ccaBLB.our_points;
                    increaseEnemy += ccaBLB.enemy_points;
                    ccaBLB.isX2 = ccaBLB.mayX2 && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy);
                }
            }

            if (ccaBRT != null) {
                if (ccaBRT.isPresent) {
                    ccaBRT.our_points = !ccaBRT.buildingIsOur ? 0 : ccaBRT.slots * (!ccaBRT.mayX2 ? 1 : countMayX2 == countMayX2our ? 2 : 1);
                    ccaBRT.enemy_points = !ccaBRT.buildingIsEnemy ? 0 : ccaBRT.slots * (!ccaBRT.mayX2 ? 1 : countMayX2 == countMayX2enemy ? 2 : 1);
                    increaseOur += ccaBRT.our_points;
                    increaseEnemy += ccaBRT.enemy_points;
                    ccaBRT.isX2 = ccaBRT.mayX2 && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy);
                }
            }

            if (ccaBRC != null) {
                if (ccaBRC.isPresent) {
                    ccaBRC.our_points = !ccaBRC.buildingIsOur ? 0 : ccaBRC.slots * (!ccaBRC.mayX2 ? 1 : countMayX2 == countMayX2our ? 2 : 1);
                    ccaBRC.enemy_points = !ccaBRC.buildingIsEnemy ? 0 : ccaBRC.slots * (!ccaBRC.mayX2 ? 1 : countMayX2 == countMayX2enemy ? 2 : 1);
                    increaseOur += ccaBRC.our_points;
                    increaseEnemy += ccaBRC.enemy_points;
                    ccaBRC.isX2 = ccaBRC.mayX2 && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy);
                }
            }

            if (ccaBRB != null) {
                if (ccaBRB.isPresent) {
                    ccaBRB.our_points = !ccaBRB.buildingIsOur ? 0 : ccaBRB.slots * (!ccaBRB.mayX2 ? 1 : countMayX2 == countMayX2our ? 2 : 1);
                    ccaBRB.enemy_points = !ccaBRB.buildingIsEnemy ? 0 : ccaBRB.slots * (!ccaBRB.mayX2 ? 1 : countMayX2 == countMayX2enemy ? 2 : 1);
                    increaseOur += ccaBRB.our_points;
                    increaseEnemy += ccaBRB.enemy_points;
                    ccaBRB.isX2 = ccaBRB.mayX2 && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy);
                }
            }
            
            if (ccaGame != null) {
                ccaGame.ccagDateScreenshot = new Date((Calendar.getInstance().getTime().getTime() / 60_000) * 60_000);
            }

            if (ccaTeamOur != null) {
                ccaTeamOur.ccatIncrease = increaseOur;
            }

            if (ccaTeamEnemy != null) {
                ccaTeamEnemy.ccatIncrease = increaseEnemy;
            }
            
        }

        loadDataToViews();
        
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
        String pattern = "dd MMM HH:mm";

        if (ccaGame != null) {

            ccaGame.calcWin();

            textStartGameTime = getString(R.string.start_game_at) + ": " + Utils.convertDateToString(ccaGame.ccagDateStartGame, pattern);    // дата/время начала игры
            textEndGameTime = getString(R.string.end_game_at) + ": "  + Utils.convertDateToString(ccaGame.ccagDateEndGame, pattern);          // дата/время окончания игры

            tv_sa_status.setText(ccaGame.ccagStatus);   // статус
            tv_sa_start_game_time.setText(textStartGameTime);   // дата/время начала игры
            tv_sa_end_game_time.setText(textEndGameTime);       // дата/время окончания игры
            if (ccaGame.ccagIsGameOver) {   // если игра закончена
                tv_sa_total_time.setText("");   // время игры - пустое
            } else { // если игра не закончена
                tv_sa_total_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToEndGame())); // время игры
            }

            tv_sa_early_win.setText(String.valueOf(ccaGame.ccagEarlyWin)); // очки до досрочной победы
            if (ccaOurTeam != null && ccaEnemyTeam != null) {   // если команды не пустые

                iv_sa_our_team_name.setImageBitmap(ccaOurTeam.bmpSrc);  // имя нашей команды
                tv_sa_our_increase.setText(ccaOurTeam.ccatIncrease == 0 ? "" : " +" + ccaOurTeam.ccatIncrease + " ");   // прирост нашей команды
                tv_sa_our_points.setText(String.valueOf(ccaOurTeam.getPoints()));  // очки нашей команды

                iv_sa_enemy_team_name.setImageBitmap(ccaEnemyTeam.bmpSrc);  // имя команды противника
                tv_sa_enemy_increase.setText(ccaEnemyTeam.ccatIncrease == 0 ? "" : " +" + ccaEnemyTeam.ccatIncrease + " "); // прирост команды противника
                tv_sa_enemy_points.setText(String.valueOf(ccaEnemyTeam.getPoints()));    // очки команды противника

                if (ccaGame.ccagIsGameOver) {   // если игра закончена
                    tv_sa_our_end_time.setText(""); // наше время пустое
                    tv_sa_enemy_end_time.setText(""); // время противника пустое
                } else { // если игра незакончена
                    if (ccaGame.ccagWillOurWin) {
                        tv_sa_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                        tv_sa_enemy_end_time.setText("");   // время противника пустое
                    } else if (ccaGame.ccagWillEnemyWin) {
                        tv_sa_our_end_time.setText(""); // наше время пустое
                        tv_sa_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                    } else if (ccaGame.ccagWillNobodyWin) {
                        tv_sa_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                        tv_sa_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                    }

                }

            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);
            int color_building_mayX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_mayX2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_mayX2), (int)Long.parseLong(context.getString(R.string.def_rgb_building_mayX2), 16)));
            int color_building_isX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_building_isX2),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_building_isX2), (int)Long.parseLong(context.getString(R.string.def_rgb_building_isX2), 16)));

            if (ccaBLT != null) {

                iv_sa_blt_name.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_x2.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_points.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_slots.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_slots_our.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_slots_empty.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blt_slots_enemy.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blt_car_black.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blt_car_our.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blt_car_empty.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blt_car_enemy.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);
                sb_sa_blt.setVisibility(ccaBLT.isPresent ? View.VISIBLE : View.INVISIBLE);


                if (ccaBLT.isPresent) {
                    CityCalcArea ccaBLTname = mainCityCalc.mapAreas.get(Area.BLT);
                    CityCalcArea ccaBLTprogress = mainCityCalc.mapAreas.get(Area.BLT_PROGRESS);
                    if (ccaBLTname != null) iv_sa_blt_name.setImageBitmap(ccaBLTname.bmpSrc);
                    tv_sa_blt_slots.setText(String.valueOf(ccaBLT.slots));
                    tv_sa_blt_slots_our.setText(String.valueOf(ccaBLT.slots_our));
                    tv_sa_blt_slots_empty.setText(String.valueOf(ccaBLT.slots_empty));
                    tv_sa_blt_slots_enemy.setText(String.valueOf(ccaBLT.slots_enemy));
//                    iv_sa_brt_name.setImageBitmap(ccaBLT.bmpSrc);
                    if (ccaBLT.buildingIsOur) {
                        tv_sa_blt_points.setText("+" + ccaBLT.our_points);
                        tv_sa_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
//                        iv_sa_blt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBLT.buildingIsEnemy) {
                        tv_sa_blt_points.setText("+" + ccaBLT.enemy_points);
                        tv_sa_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
//                        iv_sa_blt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBLT.buildingIsEmpty) {
                        tv_sa_blt_points.setText("");
                        tv_sa_blt_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_sa_blt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBLT.isX2) {
                        tv_sa_blt_x2.setText("X2");
                        tv_sa_blt_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBLT.mayX2) {
                            tv_sa_blt_x2.setText("X2");
                            tv_sa_blt_x2.setBackgroundColor(color_building_mayX2);
                        } else {
                            tv_sa_blt_x2.setText("");
                            tv_sa_blt_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }

            if (ccaBLC != null) {
                iv_sa_blc_name.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_x2.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_points.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_slots.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_slots_our.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_slots_empty.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blc_slots_enemy.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blc_car_black.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blc_car_our.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blc_car_empty.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blc_car_enemy.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);
                sb_sa_blc.setVisibility(ccaBLC.isPresent ? View.VISIBLE : View.INVISIBLE);

                if (ccaBLC.isPresent) {
                    CityCalcArea ccaBLCname = mainCityCalc.mapAreas.get(Area.BLC);
                    CityCalcArea ccaBLCprogress = mainCityCalc.mapAreas.get(Area.BLC_PROGRESS);
                    if (ccaBLCname != null) iv_sa_blc_name.setImageBitmap(ccaBLCname.bmpSrc);
                    tv_sa_blc_slots.setText(String.valueOf(ccaBLC.slots));
                    tv_sa_blc_slots_our.setText(String.valueOf(ccaBLC.slots_our));
                    tv_sa_blc_slots_empty.setText(String.valueOf(ccaBLC.slots_empty));
                    tv_sa_blc_slots_enemy.setText(String.valueOf(ccaBLC.slots_enemy));
                    if (ccaBLC.buildingIsOur) {
                        tv_sa_blc_points.setText("+" + ccaBLC.our_points);
                        tv_sa_blc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_sa_blc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBLC.buildingIsEnemy) {
                        tv_sa_blc_points.setText("+" + ccaBLC.enemy_points);
                        tv_sa_blc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_sa_blc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBLC.buildingIsEmpty) {
                        tv_sa_blc_points.setText("");
                        tv_sa_blc_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_sa_blc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBLC.isX2) {
                        tv_sa_blc_x2.setText("X2");
                        tv_sa_blc_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBLC.mayX2) {
                            tv_sa_blc_x2.setText("X2");
                            tv_sa_blc_x2.setBackgroundColor(color_building_mayX2);
                        } else {
                            tv_sa_blc_x2.setText("");
                            tv_sa_blc_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }


            if (ccaBLB != null) {
                iv_sa_blb_name.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_x2.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_points.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_slots.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_slots_our.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_slots_empty.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_blb_slots_enemy.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blb_car_black.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blb_car_our.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blb_car_empty.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_blb_car_enemy.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);
                sb_sa_blb.setVisibility(ccaBLB.isPresent ? View.VISIBLE : View.INVISIBLE);

                if (ccaBLB.isPresent) {
                    CityCalcArea ccaBLBname = mainCityCalc.mapAreas.get(Area.BLB);
                    CityCalcArea ccaBLBprogress = mainCityCalc.mapAreas.get(Area.BLB_PROGRESS);
                    if (ccaBLBname != null) iv_sa_blb_name.setImageBitmap(ccaBLBname.bmpSrc);
                    tv_sa_blb_slots.setText(String.valueOf(ccaBLB.slots));
                    tv_sa_blb_slots_our.setText(String.valueOf(ccaBLB.slots_our));
                    tv_sa_blb_slots_empty.setText(String.valueOf(ccaBLB.slots_empty));
                    tv_sa_blb_slots_enemy.setText(String.valueOf(ccaBLB.slots_enemy));
                    if (ccaBLB.buildingIsOur) {
                        tv_sa_blb_points.setText("+" + ccaBLB.our_points);
                        tv_sa_blb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_sa_blb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBLB.buildingIsEnemy) {
                        tv_sa_blb_points.setText("+" + ccaBLB.enemy_points);
                        tv_sa_blb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_sa_blb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBLB.buildingIsEmpty) {
                        tv_sa_blb_points.setText("");
                        tv_sa_blb_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_sa_blb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBLB.isX2) {
                        tv_sa_blb_x2.setText("X2");
                        tv_sa_blb_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBLB.mayX2) {
                            tv_sa_blb_x2.setText("X2");
                            tv_sa_blb_x2.setBackgroundColor(color_building_mayX2);
                        } else {
                            tv_sa_blb_x2.setText("");
                            tv_sa_blb_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }

            if (ccaBRT != null) {
                iv_sa_brt_name.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_x2.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_points.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_slots.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_slots_our.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_slots_empty.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brt_slots_enemy.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brt_car_black.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brt_car_our.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brt_car_empty.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brt_car_enemy.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);
                sb_sa_brt.setVisibility(ccaBRT.isPresent ? View.VISIBLE : View.INVISIBLE);

                if (ccaBRT.isPresent) {
                    CityCalcArea ccaBRTname = mainCityCalc.mapAreas.get(Area.BRT);
                    CityCalcArea ccaBRTprogress = mainCityCalc.mapAreas.get(Area.BRT_PROGRESS);
                    if (ccaBRTname != null) iv_sa_brt_name.setImageBitmap(ccaBRTname.bmpSrc);
                    tv_sa_brt_slots.setText(String.valueOf(ccaBRT.slots));
                    tv_sa_brt_slots_our.setText(String.valueOf(ccaBRT.slots_our));
                    tv_sa_brt_slots_empty.setText(String.valueOf(ccaBRT.slots_empty));
                    tv_sa_brt_slots_enemy.setText(String.valueOf(ccaBRT.slots_enemy));
                    if (ccaBRT.buildingIsOur) {
                        tv_sa_brt_points.setText("+" + ccaBRT.our_points);
                        tv_sa_brt_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_sa_brt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBRT.buildingIsEnemy) {
                        tv_sa_brt_points.setText("+" + ccaBRT.enemy_points);
                        tv_sa_brt_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_sa_brt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBRT.buildingIsEmpty) {
                        tv_sa_brt_points.setText("");
                        tv_sa_brt_points.setBackgroundColor(0xFFFFFFFF);
//                        iv_sa_brt_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBRT.isX2) {
                        tv_sa_brt_x2.setText("X2");
                        tv_sa_brt_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBRT.mayX2) {
                            tv_sa_brt_x2.setText("X2");
                            tv_sa_brt_x2.setBackgroundColor(color_building_mayX2);
                        } else {
                            tv_sa_brt_x2.setText("");
                            tv_sa_brt_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }

            if (ccaBRC != null) {
                iv_sa_brc_name.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_x2.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_points.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_slots.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_slots_our.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_slots_empty.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brc_slots_enemy.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brc_car_black.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brc_car_our.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brc_car_empty.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brc_car_enemy.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);
                sb_sa_brc.setVisibility(ccaBRC.isPresent ? View.VISIBLE : View.INVISIBLE);

                if (ccaBRC.isPresent) {
                    CityCalcArea ccaBRCname = mainCityCalc.mapAreas.get(Area.BRC);
                    CityCalcArea ccaBRCprogress = mainCityCalc.mapAreas.get(Area.BRC_PROGRESS);
                    if (ccaBRCname != null) iv_sa_brc_name.setImageBitmap(ccaBRCname.bmpSrc);
                    tv_sa_brc_slots.setText(String.valueOf(ccaBRC.slots));
                    tv_sa_brc_slots_our.setText(String.valueOf(ccaBRC.slots_our));
                    tv_sa_brc_slots_empty.setText(String.valueOf(ccaBRC.slots_empty));
                    tv_sa_brc_slots_enemy.setText(String.valueOf(ccaBRC.slots_enemy));
                    if (ccaBRC.buildingIsOur) {
                        tv_sa_brc_points.setText("+" + ccaBRC.our_points);
                        tv_sa_brc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_sa_brc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBRC.buildingIsEnemy) {
                        tv_sa_brc_points.setText("+" + ccaBRC.enemy_points);
                        tv_sa_brc_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_sa_brc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBRC.buildingIsEmpty) {
                        tv_sa_brc_points.setText("");
                        tv_sa_brc_points.setBackgroundColor(0xFFFFFF);
//                        iv_sa_brc_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBRC.isX2) {
                        tv_sa_brc_x2.setText("X2");
                        tv_sa_brc_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBRC.mayX2) {
                            tv_sa_brc_x2.setText("X2");
                            tv_sa_brc_x2.setBackgroundColor(color_building_mayX2);
                        } else {
                            tv_sa_brc_x2.setText("");
                            tv_sa_brc_x2.setBackgroundColor(0xFFFFFFFF);
                        }
                    }
                }
            }


            if (ccaBRB != null) {
                iv_sa_brb_name.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_x2.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_points.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_slots.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_slots_our.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_slots_empty.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                tv_sa_brb_slots_enemy.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brb_car_black.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brb_car_our.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brb_car_empty.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                iv_sa_brb_car_enemy.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);
                sb_sa_brb.setVisibility(ccaBRB.isPresent ? View.VISIBLE : View.INVISIBLE);

                if (ccaBRB.isPresent) {
                    CityCalcArea ccaBRBname = mainCityCalc.mapAreas.get(Area.BRB);
                    CityCalcArea ccaBRBprogress = mainCityCalc.mapAreas.get(Area.BRB_PROGRESS);
                    if (ccaBRBname != null) iv_sa_brb_name.setImageBitmap(ccaBRBname.bmpSrc);
                    tv_sa_brb_slots.setText(String.valueOf(ccaBRB.slots));
                    tv_sa_brb_slots_our.setText(String.valueOf(ccaBRB.slots_our));
                    tv_sa_brb_slots_empty.setText(String.valueOf(ccaBRB.slots_empty));
                    tv_sa_brb_slots_enemy.setText(String.valueOf(ccaBRB.slots_enemy));
                    if (ccaBRB.buildingIsOur) {
                        tv_sa_brb_points.setText("+" + ccaBRB.our_points);
                        tv_sa_brb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_our_main), 16));
//                        iv_sa_brb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_our),16));
                    } else if (ccaBRB.buildingIsEnemy) {
                        tv_sa_brb_points.setText("+" + ccaBRB.enemy_points);
                        tv_sa_brb_points.setBackgroundColor((int) Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main), 16));
//                        iv_sa_brb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_enemy),16));
                    } else if (ccaBRB.buildingIsEmpty) {
                        tv_sa_brb_points.setText("");
                        tv_sa_brb_points.setBackgroundColor(0xFFFFFF);
//                        iv_sa_brb_name.setTextColor((int)Long.parseLong(context.getString(R.string.def_rgb_building_progress_empty),16));
                    }
                    if (ccaBRB.isX2) {
                        tv_sa_brb_x2.setText("X2");
                        tv_sa_brb_x2.setBackgroundColor(color_building_isX2);
                    } else {
                        if (ccaBRB.mayX2) {
                            tv_sa_brb_x2.setText("X2");
                            tv_sa_brb_x2.setBackgroundColor(color_building_mayX2);
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
            CCAGame ccaGame = (CCAGame) mainCityCalc.mapAreas.get(Area.CITY);
            if (ccaGame != null) {
                int countOurX2 = 0;
                this.willEarlyWin = ccaGame.ccagWillEarlyWin;
                this.willOurWin = ccaGame.ccagWillOurWin;
                this.differentPoints = ccaGame.differentPoints;
                CCABuilding ccab;
                ccab = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLT); if (ccab.isPresent && ccab.mayX2 && ccab.buildingIsOur) countOurX2++;
                ccab = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLC); if (ccab.isPresent && ccab.mayX2 && ccab.buildingIsOur) countOurX2++;
                ccab = (CCABuilding) mainCityCalc.mapAreas.get(Area.BLB); if (ccab.isPresent && ccab.mayX2 && ccab.buildingIsOur) countOurX2++;
                ccab = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRT); if (ccab.isPresent && ccab.mayX2 && ccab.buildingIsOur) countOurX2++;
                ccab = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRC); if (ccab.isPresent && ccab.mayX2 && ccab.buildingIsOur) countOurX2++;
                ccab = (CCABuilding) mainCityCalc.mapAreas.get(Area.BRB); if (ccab.isPresent && ccab.mayX2 && ccab.buildingIsOur) countOurX2++;
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
