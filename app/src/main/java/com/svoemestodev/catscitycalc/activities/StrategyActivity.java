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
import android.util.Log;
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
    TextView sa_tv_start_game_time;
    TextView sa_tv_end_game_time;
    TextView sa_tv_status;
    TextView sa_tv_vs;

    // Time views
    TextView sa_tv_total_time;
    TextView sa_tv_early_win;

    // Our team views
    ImageView sa_iv_our_team_name;
    TextView sa_tv_our_increase;
    TextView sa_tv_our_points;
    TextView sa_tv_our_end_time;

    // Enemy team views
    ImageView sa_iv_enemy_team_name;
    TextView sa_tv_enemy_increase;
    TextView sa_tv_enemy_points;
    TextView sa_tv_enemy_end_time;

    // BLT views
    ImageView sa_iv_blt_name;
    TextView sa_tv_blt_x2;
    TextView sa_tv_blt_points;
    TextView sa_tv_blt_slots;
    TextView sa_tv_blt_slots_our;
    TextView sa_tv_blt_slots_empty;
    TextView sa_tv_blt_slots_enemy;
    ImageView sa_iv_blt_car_black;
    ImageView sa_iv_blt_car_our;
    ImageView sa_iv_blt_car_empty;
    ImageView sa_iv_blt_car_enemy;
    SeekBar sa_sb_blt;

    // BLC views
    ImageView sa_iv_blc_name;
    TextView sa_tv_blc_x2;
    TextView sa_tv_blc_points;
    TextView sa_tv_blc_slots;
    TextView sa_tv_blc_slots_our;
    TextView sa_tv_blc_slots_empty;
    TextView sa_tv_blc_slots_enemy;
    ImageView sa_iv_blc_car_black;
    ImageView sa_iv_blc_car_our;
    ImageView sa_iv_blc_car_empty;
    ImageView sa_iv_blc_car_enemy;
    SeekBar sa_sb_blc;

    // BLB views
    ImageView sa_iv_blb_name;
    TextView sa_tv_blb_x2;
    TextView sa_tv_blb_points;
    TextView sa_tv_blb_slots;
    TextView sa_tv_blb_slots_our;
    TextView sa_tv_blb_slots_empty;
    TextView sa_tv_blb_slots_enemy;
    ImageView sa_iv_blb_car_black;
    ImageView sa_iv_blb_car_our;
    ImageView sa_iv_blb_car_empty;
    ImageView sa_iv_blb_car_enemy;
    SeekBar sa_sb_blb;

    // BRT views
    ImageView sa_iv_brt_name;
    TextView sa_tv_brt_x2;
    TextView sa_tv_brt_points;
    TextView sa_tv_brt_slots;
    TextView sa_tv_brt_slots_our;
    TextView sa_tv_brt_slots_empty;
    TextView sa_tv_brt_slots_enemy;
    ImageView sa_iv_brt_car_black;
    ImageView sa_iv_brt_car_our;
    ImageView sa_iv_brt_car_empty;
    ImageView sa_iv_brt_car_enemy;
    SeekBar sa_sb_brt;

    // BRC views
    ImageView sa_iv_brc_name;
    TextView sa_tv_brc_x2;
    TextView sa_tv_brc_points;
    TextView sa_tv_brc_slots;
    TextView sa_tv_brc_slots_our;
    TextView sa_tv_brc_slots_empty;
    TextView sa_tv_brc_slots_enemy;
    ImageView sa_iv_brc_car_black;
    ImageView sa_iv_brc_car_our;
    ImageView sa_iv_brc_car_empty;
    ImageView sa_iv_brc_car_enemy;
    SeekBar sa_sb_brc;

    // BRB views
    ImageView sa_iv_brb_name;
    TextView sa_tv_brb_x2;
    TextView sa_tv_brb_points;
    TextView sa_tv_brb_slots;
    TextView sa_tv_brb_slots_our;
    TextView sa_tv_brb_slots_empty;
    TextView sa_tv_brb_slots_enemy;
    ImageView sa_iv_brb_car_black;
    ImageView sa_iv_brb_car_our;
    ImageView sa_iv_brb_car_empty;
    ImageView sa_iv_brb_car_enemy;
    SeekBar sa_sb_brb;

    SeekBar sa_sb_person_our;
    ImageView sa_iv_person_our;
    TextView sa_tv_person_our;
    ImageView sa_iv_car_our;
    TextView sa_tv_car_our;

    SeekBar sa_sb_person_enemy;
    ImageView sa_iv_person_enemy;
    TextView sa_tv_person_enemy;
    ImageView sa_iv_car_enemy;
    TextView sa_tv_car_enemy;

    Button sa_bt_calc_timed_win;
    Button sa_bt_calc_early_win;

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
    AdView sa_ad_banner;

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
        context = sa_tv_start_game_time.getContext();
        sa_sb_person_our.setEnabled(false);
        sa_sb_person_enemy.setEnabled(false);

        // инициализация рекламного блока
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        sa_ad_banner.loadAd(adRequest);

        sa_sb_blt.setOnSeekBarChangeListener(bltSeekBarChangeListener);
        sa_sb_blc.setOnSeekBarChangeListener(blcSeekBarChangeListener);
        sa_sb_blb.setOnSeekBarChangeListener(blbSeekBarChangeListener);
        sa_sb_brt.setOnSeekBarChangeListener(brtSeekBarChangeListener);
        sa_sb_brc.setOnSeekBarChangeListener(brcSeekBarChangeListener);
        sa_sb_brb.setOnSeekBarChangeListener(brbSeekBarChangeListener);
        sa_sb_person_our.setOnSeekBarChangeListener(personOurSeekBarChangeListener);
        sa_sb_person_enemy.setOnSeekBarChangeListener(personEnemySeekBarChangeListener);
        
        mainCityCalc = new CityCalc(new CityCalc(GameActivity.fileGameScreenshot, GameActivity.calibrateX, GameActivity.calibrateY, context), false);
        CCAGame ccaGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);

        if (ccaGame != null) {
            dateScreenshotMain = ccaGame.getCcagDateScreenshot();
            increaseOurMain = ccaGame.getCcagIncreaseOur();
            increaseEnemyMain = ccaGame.getCcagIncreaseEnemy();
            pointsInScreenshotOurMain = ccaGame.getCcagPointsOurInScreenshot();
            pointsInScreenshotEnemyMain = ccaGame.getCcagPointsEnemyInScreenshot();
        }

        slotsTotal = 0;
        slotsOur = 0;
        slotsEnemy = 0;

        needUpdateSlots = false;
        
        if (ccaGame.isPresent_blt()) {
            if (ccaGame.isBuildingIsOur_blt()) sa_sb_blt.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_blt()) sa_sb_blt.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_blt()) sa_sb_blt.setProgress(2);
            slotsTotal += ccaGame.getSlots_blt();
            slotsOur += ccaGame.getSlots_blt_our();
            slotsEnemy += ccaGame.getSlots_blt_enemy();
        }

        if (ccaGame.isPresent_blc()) {
            if (ccaGame.isBuildingIsOur_blc()) sa_sb_blc.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_blc()) sa_sb_blc.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_blc()) sa_sb_blc.setProgress(2);
            slotsTotal += ccaGame.getSlots_blc();
            slotsOur += ccaGame.getSlots_blc_our();
            slotsEnemy += ccaGame.getSlots_blc_enemy();
        }

        if (ccaGame.isPresent_blb()) {
            if (ccaGame.isBuildingIsOur_blb()) sa_sb_blb.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_blb()) sa_sb_blb.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_blb()) sa_sb_blb.setProgress(2);
            slotsTotal += ccaGame.getSlots_blb();
            slotsOur += ccaGame.getSlots_blb_our();
            slotsEnemy += ccaGame.getSlots_blb_enemy();
        }

        if (ccaGame.isPresent_brt()) {
            if (ccaGame.isBuildingIsOur_brt()) sa_sb_brt.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_brt()) sa_sb_brt.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_brt()) sa_sb_brt.setProgress(2);
            slotsTotal += ccaGame.getSlots_brt();
            slotsOur += ccaGame.getSlots_brt_our();
            slotsEnemy += ccaGame.getSlots_brt_enemy();
        }

        if (ccaGame.isPresent_brc()) {
            if (ccaGame.isBuildingIsOur_brc()) sa_sb_brc.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_brc()) sa_sb_brc.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_brc()) sa_sb_brc.setProgress(2);
            slotsTotal += ccaGame.getSlots_brc();
            slotsOur += ccaGame.getSlots_brc_our();
            slotsEnemy += ccaGame.getSlots_brc_enemy();
        }

        if (ccaGame.isPresent_brb()) {
            if (ccaGame.isBuildingIsOur_brb()) sa_sb_brb.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_brb()) sa_sb_brb.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_brb()) sa_sb_brb.setProgress(2);
            slotsTotal += ccaGame.getSlots_brb();
            slotsOur += ccaGame.getSlots_brb_our();
            slotsEnemy += ccaGame.getSlots_brb_enemy();
        }

        maxPersons = (int)Math.ceil (slotsTotal / 3.0d);
        sa_sb_person_our.setMax(maxPersons);
        sa_sb_person_enemy.setMax(maxPersons);

        personsOur = (int)Math.ceil (slotsOur / 3.0d);
        personsEnemy = (int)Math.ceil (slotsEnemy / 3.0d);

        sa_sb_person_our.setProgress(personsOur);
        sa_sb_person_enemy.setProgress(personsEnemy);

        sa_tv_person_our.setText(String.valueOf(personsOur));
        sa_tv_person_enemy.setText(String.valueOf(personsEnemy));
        sa_tv_car_our.setText(String.valueOf(personsOur*3));
        sa_tv_car_enemy.setText(String.valueOf(personsEnemy*3));

        needUpdateSlots = true;
        
        setCarsInBuildings();


    }

    private void setCarsInBuildings() {
        
        if (mainCityCalc != null) {

            CCAGame ccaGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);

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

            if (ccaGame.isPresent_blt()) {
                ccaGame.setBuildingIsOur_blt(sa_sb_blt.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_blt(sa_sb_blt.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_blt(sa_sb_blt.getProgress() == 2);
                ccaGame.setSlots_blt_our(ccaGame.isBuildingIsOur_blt() ? (int)Math.ceil(ccaGame.getSlots_blt() / 2.0d) : 0);
                ccaGame.setSlots_blt_empty(ccaGame.isBuildingIsEmpty_blt() ? (int)Math.ceil(ccaGame.getSlots_blt() / 2.0d) : 0);
                ccaGame.setSlots_blt_enemy(ccaGame.isBuildingIsEnemy_blt() ? (int)Math.ceil(ccaGame.getSlots_blt() / 2.0d) : 0);
                slotsOur += ccaGame.getSlots_blt_our();
                slotsEmpty += ccaGame.getSlots_blt_empty();
                slotsEnemy += ccaGame.getSlots_blt_enemy();
                countMayX2 += ccaGame.isMayX2_blt() ? 1 : 0;
                countMayX2our += ccaGame.isMayX2_blt() && ccaGame.isBuildingIsOur_blt() ? 1 : 0;
                countMayX2empty += ccaGame.isMayX2_blt() && ccaGame.isBuildingIsEmpty_blt() ? 1 : 0;
                countMayX2enemy += ccaGame.isMayX2_blt() && ccaGame.isBuildingIsEnemy_blt() ? 1 : 0;
            }

            if (ccaGame.isPresent_blc()) {
                ccaGame.setBuildingIsOur_blc(sa_sb_blc.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_blc(sa_sb_blc.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_blc(sa_sb_blc.getProgress() == 2);
                ccaGame.setSlots_blc_our(ccaGame.isBuildingIsOur_blc() ? (int)Math.ceil(ccaGame.getSlots_blc() / 2.0d) : 0);
                ccaGame.setSlots_blc_empty(ccaGame.isBuildingIsEmpty_blc() ? (int)Math.ceil(ccaGame.getSlots_blc() / 2.0d) : 0);
                ccaGame.setSlots_blc_enemy(ccaGame.isBuildingIsEnemy_blc() ? (int)Math.ceil(ccaGame.getSlots_blc() / 2.0d) : 0);
                slotsOur += ccaGame.getSlots_blc_our();
                slotsEmpty += ccaGame.getSlots_blc_empty();
                slotsEnemy += ccaGame.getSlots_blc_enemy();
                countMayX2 += ccaGame.isMayX2_blc() ? 1 : 0;
                countMayX2our += ccaGame.isMayX2_blc() && ccaGame.isBuildingIsOur_blc() ? 1 : 0;
                countMayX2empty += ccaGame.isMayX2_blc() && ccaGame.isBuildingIsEmpty_blc() ? 1 : 0;
                countMayX2enemy += ccaGame.isMayX2_blc() && ccaGame.isBuildingIsEnemy_blc() ? 1 : 0;
            }

            if (ccaGame.isPresent_blb()) {
                ccaGame.setBuildingIsOur_blb(sa_sb_blb.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_blb(sa_sb_blb.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_blb(sa_sb_blb.getProgress() == 2);
                ccaGame.setSlots_blb_our(ccaGame.isBuildingIsOur_blb() ? (int)Math.ceil(ccaGame.getSlots_blb() / 2.0d) : 0);
                ccaGame.setSlots_blb_empty(ccaGame.isBuildingIsEmpty_blb() ? (int)Math.ceil(ccaGame.getSlots_blb() / 2.0d) : 0);
                ccaGame.setSlots_blb_enemy(ccaGame.isBuildingIsEnemy_blb() ? (int)Math.ceil(ccaGame.getSlots_blb() / 2.0d) : 0);
                slotsOur += ccaGame.getSlots_blb_our();
                slotsEmpty += ccaGame.getSlots_blb_empty();
                slotsEnemy += ccaGame.getSlots_blb_enemy();
                countMayX2 += ccaGame.isMayX2_blb() ? 1 : 0;
                countMayX2our += ccaGame.isMayX2_blb() && ccaGame.isBuildingIsOur_blb() ? 1 : 0;
                countMayX2empty += ccaGame.isMayX2_blb() && ccaGame.isBuildingIsEmpty_blb() ? 1 : 0;
                countMayX2enemy += ccaGame.isMayX2_blb() && ccaGame.isBuildingIsEnemy_blb() ? 1 : 0;
            }

            if (ccaGame.isPresent_brt()) {
                ccaGame.setBuildingIsOur_brt(sa_sb_brt.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_brt(sa_sb_brt.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_brt(sa_sb_brt.getProgress() == 2);
                ccaGame.setSlots_brt_our(ccaGame.isBuildingIsOur_brt() ? (int)Math.ceil(ccaGame.getSlots_brt() / 2.0d) : 0);
                ccaGame.setSlots_brt_empty(ccaGame.isBuildingIsEmpty_brt() ? (int)Math.ceil(ccaGame.getSlots_brt() / 2.0d) : 0);
                ccaGame.setSlots_brt_enemy(ccaGame.isBuildingIsEnemy_brt() ? (int)Math.ceil(ccaGame.getSlots_brt() / 2.0d) : 0);
                slotsOur += ccaGame.getSlots_brt_our();
                slotsEmpty += ccaGame.getSlots_brt_empty();
                slotsEnemy += ccaGame.getSlots_brt_enemy();
                countMayX2 += ccaGame.isMayX2_brt() ? 1 : 0;
                countMayX2our += ccaGame.isMayX2_brt() && ccaGame.isBuildingIsOur_brt() ? 1 : 0;
                countMayX2empty += ccaGame.isMayX2_brt() && ccaGame.isBuildingIsEmpty_brt() ? 1 : 0;
                countMayX2enemy += ccaGame.isMayX2_brt() && ccaGame.isBuildingIsEnemy_brt() ? 1 : 0;
            }

            if (ccaGame.isPresent_brc()) {
                ccaGame.setBuildingIsOur_brc(sa_sb_brc.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_brc(sa_sb_brc.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_brc(sa_sb_brc.getProgress() == 2);
                ccaGame.setSlots_brc_our(ccaGame.isBuildingIsOur_brc() ? (int)Math.ceil(ccaGame.getSlots_brc() / 2.0d) : 0);
                ccaGame.setSlots_brc_empty(ccaGame.isBuildingIsEmpty_brc() ? (int)Math.ceil(ccaGame.getSlots_brc() / 2.0d) : 0);
                ccaGame.setSlots_brc_enemy(ccaGame.isBuildingIsEnemy_brc() ? (int)Math.ceil(ccaGame.getSlots_brc() / 2.0d) : 0);
                slotsOur += ccaGame.getSlots_brc_our();
                slotsEmpty += ccaGame.getSlots_brc_empty();
                slotsEnemy += ccaGame.getSlots_brc_enemy();
                countMayX2 += ccaGame.isMayX2_brc() ? 1 : 0;
                countMayX2our += ccaGame.isMayX2_brc() && ccaGame.isBuildingIsOur_brc() ? 1 : 0;
                countMayX2empty += ccaGame.isMayX2_brc() && ccaGame.isBuildingIsEmpty_brc() ? 1 : 0;
                countMayX2enemy += ccaGame.isMayX2_brc() && ccaGame.isBuildingIsEnemy_brc() ? 1 : 0;
            }

            if (ccaGame.isPresent_brb()) {
                ccaGame.setBuildingIsOur_brb(sa_sb_brb.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_brb(sa_sb_brb.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_brb(sa_sb_brb.getProgress() == 2);
                ccaGame.setSlots_brb_our(ccaGame.isBuildingIsOur_brb() ? (int)Math.ceil(ccaGame.getSlots_brb() / 2.0d) : 0);
                ccaGame.setSlots_brb_empty(ccaGame.isBuildingIsEmpty_brb() ? (int)Math.ceil(ccaGame.getSlots_brb() / 2.0d) : 0);
                ccaGame.setSlots_brb_enemy(ccaGame.isBuildingIsEnemy_brb() ? (int)Math.ceil(ccaGame.getSlots_brb() / 2.0d) : 0);
                slotsOur += ccaGame.getSlots_brb_our();
                slotsEmpty += ccaGame.getSlots_brb_empty();
                slotsEnemy += ccaGame.getSlots_brb_enemy();
                countMayX2 += ccaGame.isMayX2_brb() ? 1 : 0;
                countMayX2our += ccaGame.isMayX2_brb() && ccaGame.isBuildingIsOur_brb() ? 1 : 0;
                countMayX2empty += ccaGame.isMayX2_brb() && ccaGame.isBuildingIsEmpty_brb() ? 1 : 0;
                countMayX2enemy += ccaGame.isMayX2_brb() && ccaGame.isBuildingIsEnemy_brb() ? 1 : 0;
            }
            

            if (ccaGame.isPresent_blt()) {
                ccaGame.setOur_points_blt(!ccaGame.isBuildingIsOur_blt() ? 0 : ccaGame.getSlots_blt() * (!ccaGame.isMayX2_blt() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                ccaGame.setEnemy_points_blt(!ccaGame.isBuildingIsEnemy_blt() ? 0 : ccaGame.getSlots_blt() * (!ccaGame.isMayX2_blt() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                increaseOur += ccaGame.getOur_points_blt();
                increaseEnemy += ccaGame.getEnemy_points_blt();
                ccaGame.setX2_blt(ccaGame.isMayX2_blt() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
            }


            if (ccaGame.isPresent_blc()) {
                ccaGame.setOur_points_blc(!ccaGame.isBuildingIsOur_blc() ? 0 : ccaGame.getSlots_blc() * (!ccaGame.isMayX2_blc() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                ccaGame.setEnemy_points_blc(!ccaGame.isBuildingIsEnemy_blc() ? 0 : ccaGame.getSlots_blc() * (!ccaGame.isMayX2_blc() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                increaseOur += ccaGame.getOur_points_blc();
                increaseEnemy += ccaGame.getEnemy_points_blc();
                ccaGame.setX2_blc(ccaGame.isMayX2_blc() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
            }

            if (ccaGame.isPresent_blb()) {
                ccaGame.setOur_points_blb(!ccaGame.isBuildingIsOur_blb() ? 0 : ccaGame.getSlots_blb() * (!ccaGame.isMayX2_blb() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                ccaGame.setEnemy_points_blb(!ccaGame.isBuildingIsEnemy_blb() ? 0 : ccaGame.getSlots_blb() * (!ccaGame.isMayX2_blb() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                increaseOur += ccaGame.getOur_points_blb();
                increaseEnemy += ccaGame.getEnemy_points_blb();
                ccaGame.setX2_blb(ccaGame.isMayX2_blb() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
            }

            if (ccaGame.isPresent_brt()) {
                ccaGame.setOur_points_brt(!ccaGame.isBuildingIsOur_brt() ? 0 : ccaGame.getSlots_brt() * (!ccaGame.isMayX2_brt() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                ccaGame.setEnemy_points_brt(!ccaGame.isBuildingIsEnemy_brt() ? 0 : ccaGame.getSlots_brt() * (!ccaGame.isMayX2_brt() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                increaseOur += ccaGame.getOur_points_brt();
                increaseEnemy += ccaGame.getEnemy_points_brt();
                ccaGame.setX2_brt(ccaGame.isMayX2_brt() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
            }

            if (ccaGame.isPresent_brc()) {
                ccaGame.setOur_points_brc(!ccaGame.isBuildingIsOur_brc() ? 0 : ccaGame.getSlots_brc() * (!ccaGame.isMayX2_brc() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                ccaGame.setEnemy_points_brc(!ccaGame.isBuildingIsEnemy_brc() ? 0 : ccaGame.getSlots_brc() * (!ccaGame.isMayX2_brc() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                increaseOur += ccaGame.getOur_points_brc();
                increaseEnemy += ccaGame.getEnemy_points_brc();
                ccaGame.setX2_brc(ccaGame.isMayX2_brc() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
            }

            if (ccaGame.isPresent_brb()) {
                ccaGame.setOur_points_brb(!ccaGame.isBuildingIsOur_brb() ? 0 : ccaGame.getSlots_brb() * (!ccaGame.isMayX2_brb() ? 1 : countMayX2 == countMayX2our ? 2 : 1));
                ccaGame.setEnemy_points_brb(!ccaGame.isBuildingIsEnemy_brb() ? 0 : ccaGame.getSlots_brb() * (!ccaGame.isMayX2_brb() ? 1 : countMayX2 == countMayX2enemy ? 2 : 1));
                increaseOur += ccaGame.getOur_points_brb();
                increaseEnemy += ccaGame.getEnemy_points_brb();
                ccaGame.setX2_brb(ccaGame.isMayX2_brb() && (countMayX2 == countMayX2our || countMayX2 == countMayX2enemy));
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

            sa_tv_status.setText(ccaGame.getCcagStatus());   // статус
            sa_tv_start_game_time.setText(textStartGameTime);   // дата/время начала игры
            sa_tv_end_game_time.setText(textEndGameTime);       // дата/время окончания игры
            if (ccaGame.isCcagIsGameOver()) {   // если игра закончена
                sa_tv_total_time.setText("");   // время игры - пустое
            } else { // если игра не закончена
                sa_tv_total_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToEndGame())); // время игры
            }

            if (ccaOurTeam != null) sa_iv_our_team_name.setImageBitmap(ccaOurTeam.getBmpSrc());  // имя нашей команды
            if (ccaEnemyTeam != null) sa_iv_enemy_team_name.setImageBitmap(ccaEnemyTeam.getBmpSrc());  // имя команды противника

            sa_tv_early_win.setText(String.valueOf(ccaGame.getCcagEarlyWin())); // очки до досрочной победы

            sa_tv_our_increase.setText(ccaGame.getCcagIncreaseOur() == 0 ? "" : " +" + ccaGame.getCcagIncreaseOur() + " ");   // прирост нашей команды
            sa_tv_our_points.setText(String.valueOf(ccaGame.getPointsOur()));  // очки нашей команды


            sa_tv_enemy_increase.setText(ccaGame.getCcagIncreaseEnemy() == 0 ? "" : " +" + ccaGame.getCcagIncreaseEnemy() + " "); // прирост команды противника
            sa_tv_enemy_points.setText(String.valueOf(ccaGame.getPointsEnemy()));    // очки команды противника

            if (ccaGame.isCcagIsGameOver()) {   // если игра закончена
                sa_tv_our_end_time.setText(""); // наше время пустое
                sa_tv_enemy_end_time.setText(""); // время противника пустое
            } else { // если игра незакончена
                if (ccaGame.isCcagWillOurWin()) {
                    sa_tv_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                    sa_tv_enemy_end_time.setText("");   // время противника пустое
                } else if (ccaGame.isCcagWillEnemyWin()) {
                    sa_tv_our_end_time.setText(""); // наше время пустое
                    sa_tv_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                } else if (ccaGame.isCcagWillNobodyWin()) {
                    sa_tv_our_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame())); // время до нашей победы
                    sa_tv_enemy_end_time.setText(Utils.convertMinutesToHHMM(ccaGame.getMinutesToFinalGame()));   // время до победы противника
                }

            }


            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);
            int color_bxx_mayX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_mayX2_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_mayX2_main), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_mayX2), 16)));
            int color_bxx_isX2 = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_isX2_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_isX2_main), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_isX2), 16)));


            sa_iv_blt_name.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blt_x2.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blt_points.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blt_slots.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blt_slots_our.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blt_slots_empty.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blt_slots_enemy.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blt_car_black.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blt_car_our.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blt_car_empty.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blt_car_enemy.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            sa_sb_blt.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);

            if (ccaGame.isPresent_blt()) {

                if (ccaBLT != null) sa_iv_blt_name.setImageBitmap(ccaBLT.getBmpSrc());

                sa_tv_blt_slots.setText(String.valueOf(ccaGame.getSlots_blt()));
                sa_tv_blt_slots_our.setText(String.valueOf(ccaGame.getSlots_blt_our()));
                sa_tv_blt_slots_empty.setText(String.valueOf(ccaGame.getSlots_blt_empty()));
                sa_tv_blt_slots_enemy.setText(String.valueOf(ccaGame.getSlots_blt_enemy()));

                if (ccaGame.isBuildingIsOur_blt()) {
                    sa_tv_blt_points.setText("+" + ccaGame.getOur_points_blt());
                    sa_tv_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_blt()) {
                    sa_tv_blt_points.setText("+" + ccaGame.getEnemy_points_blt());
                    sa_tv_blt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_blt()) {
                    sa_tv_blt_points.setText("");
                    sa_tv_blt_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_blt()) {
                    sa_tv_blt_x2.setText("X2");
                    sa_tv_blt_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_blt()) {
                        sa_tv_blt_x2.setText("X2");
                        sa_tv_blt_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        sa_tv_blt_x2.setText("");
                        sa_tv_blt_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }

            sa_iv_blc_name.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blc_x2.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blc_points.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blc_slots.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blc_slots_our.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blc_slots_empty.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blc_slots_enemy.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blc_car_black.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blc_car_our.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blc_car_empty.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blc_car_enemy.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            sa_sb_blc.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);

            if (ccaGame.isPresent_blc()) {

                if (ccaBLC != null) sa_iv_blc_name.setImageBitmap(ccaBLC.getBmpSrc());

                sa_tv_blc_slots.setText(String.valueOf(ccaGame.getSlots_blc()));
                sa_tv_blc_slots_our.setText(String.valueOf(ccaGame.getSlots_blc_our()));
                sa_tv_blc_slots_empty.setText(String.valueOf(ccaGame.getSlots_blc_empty()));
                sa_tv_blc_slots_enemy.setText(String.valueOf(ccaGame.getSlots_blc_enemy()));

                if (ccaGame.isBuildingIsOur_blc()) {
                    sa_tv_blc_points.setText("+" + ccaGame.getOur_points_blc());
                    sa_tv_blc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_blc()) {
                    sa_tv_blc_points.setText("+" + ccaGame.getEnemy_points_blc());
                    sa_tv_blc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_blc()) {
                    sa_tv_blc_points.setText("");
                    sa_tv_blc_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_blc()) {
                    sa_tv_blc_x2.setText("X2");
                    sa_tv_blc_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_blc()) {
                        sa_tv_blc_x2.setText("X2");
                        sa_tv_blc_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        sa_tv_blc_x2.setText("");
                        sa_tv_blc_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }


            sa_iv_blb_name.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blb_x2.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blb_points.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blb_slots.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blb_slots_our.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blb_slots_empty.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_blb_slots_enemy.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blb_car_black.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blb_car_our.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blb_car_empty.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_blb_car_enemy.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            sa_sb_blb.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);

            if (ccaGame.isPresent_blb()) {

                if (ccaBLB != null) sa_iv_blb_name.setImageBitmap(ccaBLB.getBmpSrc());

                sa_tv_blb_slots.setText(String.valueOf(ccaGame.getSlots_blb()));
                sa_tv_blb_slots_our.setText(String.valueOf(ccaGame.getSlots_blb_our()));
                sa_tv_blb_slots_empty.setText(String.valueOf(ccaGame.getSlots_blb_empty()));
                sa_tv_blb_slots_enemy.setText(String.valueOf(ccaGame.getSlots_blb_enemy()));

                if (ccaGame.isBuildingIsOur_blb()) {
                    sa_tv_blb_points.setText("+" + ccaGame.getOur_points_blb());
                    sa_tv_blb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_blb()) {
                    sa_tv_blb_points.setText("+" + ccaGame.getEnemy_points_blb());
                    sa_tv_blb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_blb()) {
                    sa_tv_blb_points.setText("");
                    sa_tv_blb_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_blb()) {
                    sa_tv_blb_x2.setText("X2");
                    sa_tv_blb_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_blb()) {
                        sa_tv_blb_x2.setText("X2");
                        sa_tv_blb_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        sa_tv_blb_x2.setText("");
                        sa_tv_blb_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }


            sa_iv_brt_name.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brt_x2.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brt_points.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brt_slots.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brt_slots_our.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brt_slots_empty.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brt_slots_enemy.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brt_car_black.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brt_car_our.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brt_car_empty.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brt_car_enemy.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            sa_sb_brt.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);

            if (ccaGame.isPresent_brt()) {

                if (ccaBRT != null) sa_iv_brt_name.setImageBitmap(ccaBRT.getBmpSrc());

                sa_tv_brt_slots.setText(String.valueOf(ccaGame.getSlots_brt()));
                sa_tv_brt_slots_our.setText(String.valueOf(ccaGame.getSlots_brt_our()));
                sa_tv_brt_slots_empty.setText(String.valueOf(ccaGame.getSlots_brt_empty()));
                sa_tv_brt_slots_enemy.setText(String.valueOf(ccaGame.getSlots_brt_enemy()));

                if (ccaGame.isBuildingIsOur_brt()) {
                    sa_tv_brt_points.setText("+" + ccaGame.getOur_points_brt());
                    sa_tv_brt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_brt()) {
                    sa_tv_brt_points.setText("+" + ccaGame.getEnemy_points_brt());
                    sa_tv_brt_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_brt()) {
                    sa_tv_brt_points.setText("");
                    sa_tv_brt_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_brt()) {
                    sa_tv_brt_x2.setText("X2");
                    sa_tv_brt_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_brt()) {
                        sa_tv_brt_x2.setText("X2");
                        sa_tv_brt_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        sa_tv_brt_x2.setText("");
                        sa_tv_brt_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }

            sa_iv_brc_name.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brc_x2.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brc_points.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brc_slots.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brc_slots_our.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brc_slots_empty.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brc_slots_enemy.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brc_car_black.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brc_car_our.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brc_car_empty.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brc_car_enemy.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            sa_sb_brc.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);

            if (ccaGame.isPresent_brc()) {

                if (ccaBRC != null) sa_iv_brc_name.setImageBitmap(ccaBRC.getBmpSrc());

                sa_tv_brc_slots.setText(String.valueOf(ccaGame.getSlots_brc()));
                sa_tv_brc_slots_our.setText(String.valueOf(ccaGame.getSlots_brc_our()));
                sa_tv_brc_slots_empty.setText(String.valueOf(ccaGame.getSlots_brc_empty()));
                sa_tv_brc_slots_enemy.setText(String.valueOf(ccaGame.getSlots_brc_enemy()));

                if (ccaGame.isBuildingIsOur_brc()) {
                    sa_tv_brc_points.setText("+" + ccaGame.getOur_points_brc());
                    sa_tv_brc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_brc()) {
                    sa_tv_brc_points.setText("+" + ccaGame.getEnemy_points_brc());
                    sa_tv_brc_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_brc()) {
                    sa_tv_brc_points.setText("");
                    sa_tv_brc_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_brc()) {
                    sa_tv_brc_x2.setText("X2");
                    sa_tv_brc_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_brc()) {
                        sa_tv_brc_x2.setText("X2");
                        sa_tv_brc_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        sa_tv_brc_x2.setText("");
                        sa_tv_brc_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }


            sa_iv_brb_name.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brb_x2.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brb_points.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brb_slots.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brb_slots_our.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brb_slots_empty.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            sa_tv_brb_slots_enemy.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brb_car_black.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brb_car_our.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brb_car_empty.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            sa_iv_brb_car_enemy.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            sa_sb_brb.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);

            if (ccaGame.isPresent_brb()) {

                if (ccaBRB != null) sa_iv_brb_name.setImageBitmap(ccaBRB.getBmpSrc());

                sa_tv_brb_slots.setText(String.valueOf(ccaGame.getSlots_brb()));
                sa_tv_brb_slots_our.setText(String.valueOf(ccaGame.getSlots_brb_our()));
                sa_tv_brb_slots_empty.setText(String.valueOf(ccaGame.getSlots_brb_empty()));
                sa_tv_brb_slots_enemy.setText(String.valueOf(ccaGame.getSlots_brb_enemy()));

                if (ccaGame.isBuildingIsOur_brb()) {
                    sa_tv_brb_points.setText("+" + ccaGame.getOur_points_brb());
                    sa_tv_brb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_our_main),16));
                } else if (ccaGame.isBuildingIsEnemy_brb()) {
                    sa_tv_brb_points.setText("+" + ccaGame.getEnemy_points_brb());
                    sa_tv_brb_points.setBackgroundColor((int)Long.parseLong(context.getString(R.string.def_rgb_points_enemy_main),16));
                } else if (ccaGame.isBuildingIsEmpty_brb()) {
                    sa_tv_brb_points.setText("");
                    sa_tv_brb_points.setBackgroundColor(0xFFFFFFFF);
                }
                if (ccaGame.isX2_brb()) {
                    sa_tv_brb_x2.setText("X2");
                    sa_tv_brb_x2.setBackgroundColor(color_bxx_isX2);
                } else {
                    if (ccaGame.isMayX2_brb()) {
                        sa_tv_brb_x2.setText("X2");
                        sa_tv_brb_x2.setBackgroundColor(color_bxx_mayX2);
                    } else {
                        sa_tv_brb_x2.setText("");
                        sa_tv_brb_x2.setBackgroundColor(0xFFFFFFFF);
                    }
                }
            }

            personsOur = (int)Math.ceil (slotsOur / 3.0d);
            personsEnemy = (int)Math.ceil (slotsEnemy / 3.0d);

            sa_sb_person_our.setProgress(personsOur);
            sa_sb_person_enemy.setProgress(personsEnemy);

            sa_tv_person_our.setText(String.valueOf(personsOur));
            sa_tv_person_enemy.setText(String.valueOf(personsEnemy));
            sa_tv_car_our.setText(String.valueOf(personsOur*3));
            sa_tv_car_enemy.setText(String.valueOf(personsEnemy*3));

        }
        

    }


    public void initializeViews() {

        // Game views
        sa_tv_start_game_time = findViewById(R.id.sa_tv_start_game_time);
        sa_tv_end_game_time = findViewById(R.id.sa_tv_end_game_time);
        sa_tv_status = findViewById(R.id.sa_tv_status);
        sa_tv_vs = findViewById(R.id.sa_tv_vs);

        // Time views
        sa_tv_total_time = findViewById(R.id.sa_tv_total_time);
        sa_tv_early_win = findViewById(R.id.sa_tv_early_win);

        // Our team views
        sa_iv_our_team_name = findViewById(R.id.sa_iv_our_team_name);
        sa_tv_our_increase = findViewById(R.id.sa_tv_our_increase);
        sa_tv_our_points = findViewById(R.id.sa_tv_our_points);
        sa_tv_our_end_time = findViewById(R.id.sa_tv_our_end_time);

        // Enemy team views
        sa_iv_enemy_team_name = findViewById(R.id.sa_iv_enemy_team_name);
        sa_tv_enemy_increase = findViewById(R.id.sa_tv_enemy_increase);
        sa_tv_enemy_points = findViewById(R.id.sa_tv_enemy_points);
        sa_tv_enemy_end_time = findViewById(R.id.sa_tv_enemy_end_time);

        // BLT views
        sa_iv_blt_name = findViewById(R.id.sa_iv_blt_name);
        sa_tv_blt_x2 = findViewById(R.id.sa_tv_blt_x2);
        sa_tv_blt_points = findViewById(R.id.sa_tv_blt_points);
        sa_tv_blt_slots = findViewById(R.id.sa_tv_blt_slots);
        sa_tv_blt_slots_our = findViewById(R.id.sa_tv_blt_slots_our);
        sa_tv_blt_slots_empty = findViewById(R.id.sa_tv_blt_slots_empty);
        sa_tv_blt_slots_enemy = findViewById(R.id.sa_tv_blt_slots_enemy);
        sa_iv_blt_car_black = findViewById(R.id.sa_iv_blt_car_black);
        sa_iv_blt_car_our = findViewById(R.id.sa_iv_blt_car_our);
        sa_iv_blt_car_empty = findViewById(R.id.sa_iv_blt_car_empty);
        sa_iv_blt_car_enemy = findViewById(R.id.sa_iv_blt_car_enemy);
        sa_sb_blt = findViewById(R.id.sa_sb_blt);

        // BLC views
        sa_iv_blc_name = findViewById(R.id.sa_iv_blc_name);
        sa_tv_blc_x2 = findViewById(R.id.sa_tv_blc_x2);
        sa_tv_blc_points = findViewById(R.id.sa_tv_blc_points);
        sa_tv_blc_slots = findViewById(R.id.sa_tv_blc_slots);
        sa_tv_blc_slots_our = findViewById(R.id.sa_tv_blc_slots_our);
        sa_tv_blc_slots_empty = findViewById(R.id.sa_tv_blc_slots_empty);
        sa_tv_blc_slots_enemy = findViewById(R.id.sa_tv_blc_slots_enemy);
        sa_iv_blc_car_black = findViewById(R.id.sa_iv_blc_car_black);
        sa_iv_blc_car_our = findViewById(R.id.sa_iv_blc_car_our);
        sa_iv_blc_car_empty = findViewById(R.id.sa_iv_blc_car_empty);
        sa_iv_blc_car_enemy = findViewById(R.id.sa_iv_blc_car_enemy);
        sa_sb_blc = findViewById(R.id.sa_sb_blc);

        // BLB views
        sa_iv_blb_name = findViewById(R.id.sa_iv_blb_name);
        sa_tv_blb_x2 = findViewById(R.id.sa_tv_blb_x2);
        sa_tv_blb_points = findViewById(R.id.sa_tv_blb_points);
        sa_tv_blb_slots = findViewById(R.id.sa_tv_blb_slots);
        sa_tv_blb_slots_our = findViewById(R.id.sa_tv_blb_slots_our);
        sa_tv_blb_slots_empty = findViewById(R.id.sa_tv_blb_slots_empty);
        sa_tv_blb_slots_enemy = findViewById(R.id.sa_tv_blb_slots_enemy);
        sa_iv_blb_car_black = findViewById(R.id.sa_iv_blb_car_black);
        sa_iv_blb_car_our = findViewById(R.id.sa_iv_blb_car_our);
        sa_iv_blb_car_empty = findViewById(R.id.sa_iv_blb_car_empty);
        sa_iv_blb_car_enemy = findViewById(R.id.sa_iv_blb_car_enemy);
        sa_sb_blb = findViewById(R.id.sa_sb_blb);

        // BRT views
        sa_iv_brt_name = findViewById(R.id.sa_iv_brt_name);
        sa_tv_brt_x2 = findViewById(R.id.sa_tv_brt_x2);
        sa_tv_brt_points = findViewById(R.id.sa_tv_brt_points);
        sa_tv_brt_slots = findViewById(R.id.sa_tv_brt_slots);
        sa_tv_brt_slots_our = findViewById(R.id.sa_tv_brt_slots_our);
        sa_tv_brt_slots_empty = findViewById(R.id.sa_tv_brt_slots_empty);
        sa_tv_brt_slots_enemy = findViewById(R.id.sa_tv_brt_slots_enemy);
        sa_iv_brt_car_black = findViewById(R.id.sa_iv_brt_car_black);
        sa_iv_brt_car_our = findViewById(R.id.sa_iv_brt_car_our);
        sa_iv_brt_car_empty = findViewById(R.id.sa_iv_brt_car_empty);
        sa_iv_brt_car_enemy = findViewById(R.id.sa_iv_brt_car_enemy);
        sa_sb_brt = findViewById(R.id.sa_sb_brt);

        // BRC views
        sa_iv_brc_name = findViewById(R.id.sa_iv_brc_name);
        sa_tv_brc_x2 = findViewById(R.id.sa_tv_brc_x2);
        sa_tv_brc_points = findViewById(R.id.sa_tv_brc_points);
        sa_tv_brc_slots = findViewById(R.id.sa_tv_brc_slots);
        sa_tv_brc_slots_our = findViewById(R.id.sa_tv_brc_slots_our);
        sa_tv_brc_slots_empty = findViewById(R.id.sa_tv_brc_slots_empty);
        sa_tv_brc_slots_enemy = findViewById(R.id.sa_tv_brc_slots_enemy);
        sa_iv_brc_car_black = findViewById(R.id.sa_iv_brc_car_black);
        sa_iv_brc_car_our = findViewById(R.id.sa_iv_brc_car_our);
        sa_iv_brc_car_empty = findViewById(R.id.sa_iv_brc_car_empty);
        sa_iv_brc_car_enemy = findViewById(R.id.sa_iv_brc_car_enemy);
        sa_sb_brc = findViewById(R.id.sa_sb_brc);

        // BRB views
        sa_iv_brb_name = findViewById(R.id.sa_iv_brb_name);
        sa_tv_brb_x2 = findViewById(R.id.sa_tv_brb_x2);
        sa_tv_brb_points = findViewById(R.id.sa_tv_brb_points);
        sa_tv_brb_slots = findViewById(R.id.sa_tv_brb_slots);
        sa_tv_brb_slots_our = findViewById(R.id.sa_tv_brb_slots_our);
        sa_tv_brb_slots_empty = findViewById(R.id.sa_tv_brb_slots_empty);
        sa_tv_brb_slots_enemy = findViewById(R.id.sa_tv_brb_slots_enemy);
        sa_iv_brb_car_black = findViewById(R.id.sa_iv_brb_car_black);
        sa_iv_brb_car_our = findViewById(R.id.sa_iv_brb_car_our);
        sa_iv_brb_car_empty = findViewById(R.id.sa_iv_brb_car_empty);
        sa_iv_brb_car_enemy = findViewById(R.id.sa_iv_brb_car_enemy);
        sa_sb_brb = findViewById(R.id.sa_sb_brb);

        sa_sb_person_our = findViewById(R.id.sa_sb_person_our);
        sa_iv_person_our = findViewById(R.id.sa_iv_person_our);
        sa_tv_person_our = findViewById(R.id.sa_tv_person_our);
        sa_iv_car_our = findViewById(R.id.sa_iv_car_our);
        sa_tv_car_our = findViewById(R.id.sa_tv_car_our);

        sa_sb_person_enemy = findViewById(R.id.sa_sb_person_enemy);
        sa_iv_person_enemy = findViewById(R.id.sa_iv_person_enemy);
        sa_tv_person_enemy = findViewById(R.id.sa_tv_person_enemy);
        sa_iv_car_enemy = findViewById(R.id.sa_iv_car_enemy);
        sa_tv_car_enemy = findViewById(R.id.sa_tv_car_enemy);

        sa_bt_calc_timed_win = findViewById(R.id.sa_bt_calc_timed_win);
        sa_bt_calc_early_win = findViewById(R.id.sa_bt_calc_early_win);

        // Рекламный блок
        sa_ad_banner = findViewById(R.id.sa_ad_banner);
    }

    private SeekBar.OnSeekBarChangeListener bltSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress == 0) {
                sa_sb_blt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sa_sb_blt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sa_sb_blt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
                sa_sb_blc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sa_sb_blc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sa_sb_blc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
                sa_sb_blb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sa_sb_blb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sa_sb_blb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
                sa_sb_brt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sa_sb_brt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sa_sb_brt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
                sa_sb_brc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sa_sb_brc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sa_sb_brc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
                sa_sb_brb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                sa_sb_brb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                sa_sb_brb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
                sa_tv_person_our.setText(String.valueOf(progress));
                sa_tv_car_our.setText(String.valueOf(progress*3));
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
                sa_tv_person_enemy.setText(String.valueOf(progress));
                sa_tv_car_enemy.setText(String.valueOf(progress*3));
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
            sa_sb_blt.setProgress(blt_progress);
            sa_sb_blc.setProgress(blc_progress);
            sa_sb_blb.setProgress(blb_progress);
            sa_sb_brt.setProgress(blt_progress);
            sa_sb_brc.setProgress(brc_progress);
            sa_sb_brb.setProgress(brb_progress);
            needUpdateSlots = true;

            setCarsInBuildings();

            this.personsOur = sa_sb_person_our.getProgress();
            CCAGame ccaGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);
            if (ccaGame != null) {
                int countOurX2 = 0;
                this.willEarlyWin = ccaGame.isCcagWillEarlyWin();
                this.willOurWin = ccaGame.isCcagWillOurWin();
                this.differentPoints = ccaGame.getDifferentPoints();
                if (ccaGame.isPresent_blt() && ccaGame.isMayX2_blt() && ccaGame.isBuildingIsOur_blt()) countOurX2++;
                if (ccaGame.isPresent_blc() && ccaGame.isMayX2_blc() && ccaGame.isBuildingIsOur_blc()) countOurX2++;
                if (ccaGame.isPresent_blb() && ccaGame.isMayX2_blb() && ccaGame.isBuildingIsOur_blb()) countOurX2++;
                if (ccaGame.isPresent_brt() && ccaGame.isMayX2_brt() && ccaGame.isBuildingIsOur_brt()) countOurX2++;
                if (ccaGame.isPresent_brc() && ccaGame.isMayX2_brc() && ccaGame.isBuildingIsOur_brc()) countOurX2++;
                if (ccaGame.isPresent_brb() && ccaGame.isMayX2_brb() && ccaGame.isBuildingIsOur_brb()) countOurX2++;
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
