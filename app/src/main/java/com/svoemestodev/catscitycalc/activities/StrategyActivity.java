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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.svoemestodev.catscitycalc.GlobalApplication;
import com.svoemestodev.catscitycalc.citycalcclasses.Area;
import com.svoemestodev.catscitycalc.citycalcclasses.CCABuilding;
import com.svoemestodev.catscitycalc.citycalcclasses.CCAGame;
import com.svoemestodev.catscitycalc.citycalcclasses.CCATeam;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalc;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StrategyActivity extends AppCompatActivity {

    RelativeLayout lgb_rl_blt;
    RelativeLayout lgb_rl_blc;
    RelativeLayout lgb_rl_blb;
    RelativeLayout lgb_rl_brt;
    RelativeLayout lgb_rl_brc;
    RelativeLayout lgb_rl_brb;

    ImageView lsc_iv_blt_icon;
    ImageView lsc_iv_blc_icon;
    ImageView lsc_iv_blb_icon;
    ImageView lsc_iv_brt_icon;
    ImageView lsc_iv_brc_icon;
    ImageView lsc_iv_brb_icon;
    
    SeekBar lsc_sb_blt;
    SeekBar lsc_sb_blc;
    SeekBar lsc_sb_blb;
    SeekBar lsc_sb_brt;
    SeekBar lsc_sb_brc;
    SeekBar lsc_sb_brb;
    
    SeekBar lsp_sb_person_our;
    ImageView lsp_iv_person_our;
    TextView lsp_tv_person_our;
    ImageView lsp_iv_car_our;
    TextView lsp_tv_car_our;

    SeekBar lsp_sb_person_enemy;
    ImageView lsp_iv_person_enemy;
    TextView lsp_tv_person_enemy;
    ImageView lsp_iv_car_enemy;
    TextView lsp_tv_car_enemy;

    TextView ga_tv_status;
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

    // BLT views
    ImageView lgb_iv_blt_icon;           // blt - иконка здания (картинка)
    ImageView lgb_iv_blt_name;           // blt - название здания (картинка)
    TextView lgb_tv_blt_x2;              // blt - х2
    TextView lgb_tv_blt_points;          // blt - очки
    TextView lgb_tv_blt_slots;           // blt - слоты 
    TextView lgb_tv_blt_slots_our;       // blt - наши слоты
    TextView lgb_tv_blt_slots_empty;     // blt - слоты противника
    TextView lgb_tv_blt_slots_enemy;     // blt - пустые слоты
    ImageView lgb_iv_blt_progress;       // blt - прогресс
    ImageView lgb_iv_blt_can_win_with_x2;
    ImageView lgb_iv_blt_can_early_win_with_x2;



    // BLC views
    ImageView lgb_iv_blc_icon;           // blc - иконка здания (картинка)
    ImageView lgb_iv_blc_name;           // blc - название здания (картинка)
    TextView lgb_tv_blc_x2;              // blc - х2
    TextView lgb_tv_blc_points;          // blc - очки
    TextView lgb_tv_blc_slots;           // blc - слоты 
    TextView lgb_tv_blc_slots_our;       // blc - наши слоты
    TextView lgb_tv_blc_slots_empty;     // blc - слоты противника
    TextView lgb_tv_blc_slots_enemy;     // blc - пустые слоты
    ImageView lgb_iv_blc_progress;       // blc - прогресс
    ImageView lgb_iv_blc_can_win_with_x2;
    ImageView lgb_iv_blc_can_early_win_with_x2;

    // BLB views
    ImageView lgb_iv_blb_icon;           // blb - иконка здания (картинка)
    ImageView lgb_iv_blb_name;           // blb - название здания (картинка)
    TextView lgb_tv_blb_x2;              // blb - х2
    TextView lgb_tv_blb_points;          // blb - очки
    TextView lgb_tv_blb_slots;           // blb - слоты 
    TextView lgb_tv_blb_slots_our;       // blb - наши слоты
    TextView lgb_tv_blb_slots_empty;     // blb - слоты противника
    TextView lgb_tv_blb_slots_enemy;     // blb - пустые слоты
    ImageView lgb_iv_blb_progress;       // blb - прогресс
    ImageView lgb_iv_blb_can_win_with_x2;
    ImageView lgb_iv_blb_can_early_win_with_x2;

    // BRT views
    ImageView lgb_iv_brt_icon;           // brt - иконка здания (картинка)
    ImageView lgb_iv_brt_name;           // brt - название здания (картинка)
    TextView lgb_tv_brt_x2;              // brt - х2
    TextView lgb_tv_brt_points;          // brt - очки
    TextView lgb_tv_brt_slots;           // brt - слоты 
    TextView lgb_tv_brt_slots_our;       // brt - наши слоты
    TextView lgb_tv_brt_slots_empty;     // brt - слоты противника
    TextView lgb_tv_brt_slots_enemy;     // brt - пустые слоты
    ImageView lgb_iv_brt_progress;       // brt - прогресс
    ImageView lgb_iv_brt_can_win_with_x2;
    ImageView lgb_iv_brt_can_early_win_with_x2;

    // BRC views
    ImageView lgb_iv_brc_icon;           // brc - иконка здания (картинка)
    ImageView lgb_iv_brc_name;           // brc - название здания (картинка)
    TextView lgb_tv_brc_x2;              // brc - х2
    TextView lgb_tv_brc_points;          // brc - очки
    TextView lgb_tv_brc_slots;           // brc - слоты 
    TextView lgb_tv_brc_slots_our;       // brc - наши слоты
    TextView lgb_tv_brc_slots_empty;     // brc - слоты противника
    TextView lgb_tv_brc_slots_enemy;     // brc - пустые слоты
    ImageView lgb_iv_brc_progress;       // brc - прогресс
    ImageView lgb_iv_brc_can_win_with_x2;
    ImageView lgb_iv_brc_can_early_win_with_x2;

    // BRB views
    ImageView lgb_iv_brb_icon;           // brb - иконка здания (картинка)
    ImageView lgb_iv_brb_name;           // brb - название здания (картинка)
    TextView lgb_tv_brb_x2;              // brb - х2
    TextView lgb_tv_brb_points;          // brb - очки
    TextView lgb_tv_brb_slots;           // brb - слоты 
    TextView lgb_tv_brb_slots_our;       // brb - наши слоты
    TextView lgb_tv_brb_slots_empty;     // brb - слоты противника
    TextView lgb_tv_brb_slots_enemy;     // brb - пустые слоты
    ImageView lgb_iv_brb_progress;       // blt - прогресс
    ImageView lgb_iv_brb_can_win_with_x2;
    ImageView lgb_iv_brb_can_early_win_with_x2;

    ImageView lgci_iv_game_car_black;     // машинка черная большая (картинка)
    ImageView lgci_iv_game_car_our;       // машинка синяя большая (картинка)
    ImageView lgci_iv_game_car_empty;     // машинка серая большая (картинка)
    ImageView lgci_iv_game_car_enemy;     // машинка красная большая (картинка)
    TextView lgci_tv_game_slots;          // слотов в игре всего
    TextView lgci_tv_game_slots_our;      // слотов в игре наших
    TextView lgci_tv_game_slots_empty;    // слотов в игре пустых
    TextView lgci_tv_game_slots_enemy;    // слотов в игре противника

    AdView sa_ad_banner;


    ImageView sa_iv_calc_timed_win;
    ImageView sa_iv_calc_timed_win_without_x2;
    ImageView sa_iv_calc_early_win;
    ImageView sa_iv_calc_early_win_without_x2;


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


    public static CityCalc mainCityCalc;
    public static CCAGame mainCCAGame;

//    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();
//        context = sa_tv_start_game_time.getContext();

        mainCityCalc = GameActivity.mainCityCalc.getClone();
        mainCCAGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);
        CCAGame ccaGame = mainCCAGame;

        sa_iv_calc_timed_win.setImageDrawable(getDrawable(ccaGame.isCanWin() ? R.drawable.ic_can_win_with_x2_true : R.drawable.ic_can_win_with_x2_false));
        sa_iv_calc_timed_win_without_x2.setImageDrawable(getDrawable(ccaGame.isCanWinWithoutX2() ? R.drawable.ic_can_win_without_x2_true : R.drawable.ic_can_win_without_x2_false));
        sa_iv_calc_early_win.setImageDrawable(getDrawable(ccaGame.isCanEarlyWin() ? R.drawable.ic_can_early_win_with_x2_true : R.drawable.ic_can_early_win_with_x2_false));
        sa_iv_calc_early_win_without_x2.setImageDrawable(getDrawable(ccaGame.isCanEarlyWinWithoutX2() ? R.drawable.ic_can_early_win_without_x2_true : R.drawable.ic_can_early_win_without_x2_false));

        lsp_sb_person_our.setEnabled(false);
        lsp_sb_person_enemy.setEnabled(false);

        // инициализация рекламного блока
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        sa_ad_banner.loadAd(adRequest);

        lsc_sb_blt.setOnSeekBarChangeListener(bltSeekBarChangeListener);
        lsc_sb_blc.setOnSeekBarChangeListener(blcSeekBarChangeListener);
        lsc_sb_blb.setOnSeekBarChangeListener(blbSeekBarChangeListener);
        lsc_sb_brt.setOnSeekBarChangeListener(brtSeekBarChangeListener);
        lsc_sb_brc.setOnSeekBarChangeListener(brcSeekBarChangeListener);
        lsc_sb_brb.setOnSeekBarChangeListener(brbSeekBarChangeListener);
        lsp_sb_person_our.setOnSeekBarChangeListener(personOurSeekBarChangeListener);
        lsp_sb_person_enemy.setOnSeekBarChangeListener(personEnemySeekBarChangeListener);


        dateScreenshotMain = ccaGame.getDateScreenshot();
        increaseOurMain = ccaGame.getIncreaseOur();
        increaseEnemyMain = ccaGame.getIncreaseEnemy();
        pointsInScreenshotOurMain = ccaGame.getPointsOurInScreenshot();
        pointsInScreenshotEnemyMain = ccaGame.getPointsEnemyInScreenshot();

        slotsTotal = 0;
        slotsOur = 0;
        slotsEnemy = 0;

        needUpdateSlots = false;

        if (ccaGame.isPresent_blt()) {
            if (ccaGame.isBuildingIsOur_blt()) lsc_sb_blt.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_blt()) lsc_sb_blt.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_blt()) lsc_sb_blt.setProgress(2);
            slotsTotal += ccaGame.getSlots_blt();
            slotsOur += ccaGame.getSlots_blt_our();
            slotsEnemy += ccaGame.getSlots_blt_enemy();
        }

        if (ccaGame.isPresent_blc()) {
            if (ccaGame.isBuildingIsOur_blc()) lsc_sb_blc.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_blc()) lsc_sb_blc.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_blc()) lsc_sb_blc.setProgress(2);
            slotsTotal += ccaGame.getSlots_blc();
            slotsOur += ccaGame.getSlots_blc_our();
            slotsEnemy += ccaGame.getSlots_blc_enemy();
        }

        if (ccaGame.isPresent_blb()) {
            if (ccaGame.isBuildingIsOur_blb()) lsc_sb_blb.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_blb()) lsc_sb_blb.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_blb()) lsc_sb_blb.setProgress(2);
            slotsTotal += ccaGame.getSlots_blb();
            slotsOur += ccaGame.getSlots_blb_our();
            slotsEnemy += ccaGame.getSlots_blb_enemy();
        }

        if (ccaGame.isPresent_brt()) {
            if (ccaGame.isBuildingIsOur_brt()) lsc_sb_brt.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_brt()) lsc_sb_brt.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_brt()) lsc_sb_brt.setProgress(2);
            slotsTotal += ccaGame.getSlots_brt();
            slotsOur += ccaGame.getSlots_brt_our();
            slotsEnemy += ccaGame.getSlots_brt_enemy();
        }

        if (ccaGame.isPresent_brc()) {
            if (ccaGame.isBuildingIsOur_brc()) lsc_sb_brc.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_brc()) lsc_sb_brc.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_brc()) lsc_sb_brc.setProgress(2);
            slotsTotal += ccaGame.getSlots_brc();
            slotsOur += ccaGame.getSlots_brc_our();
            slotsEnemy += ccaGame.getSlots_brc_enemy();
        }

        if (ccaGame.isPresent_brb()) {
            if (ccaGame.isBuildingIsOur_brb()) lsc_sb_brb.setProgress(0);
            if (ccaGame.isBuildingIsEmpty_brb()) lsc_sb_brb.setProgress(1);
            if (ccaGame.isBuildingIsEnemy_brb()) lsc_sb_brb.setProgress(2);
            slotsTotal += ccaGame.getSlots_brb();
            slotsOur += ccaGame.getSlots_brb_our();
            slotsEnemy += ccaGame.getSlots_brb_enemy();
        }

        maxPersons = (int)Math.ceil (slotsTotal / 3.0d);
        lsp_sb_person_our.setMax(maxPersons);
        lsp_sb_person_enemy.setMax(maxPersons);

        personsOur = (int)Math.ceil (slotsOur / 3.0d);
        personsEnemy = (int)Math.ceil (slotsEnemy / 3.0d);

        lsp_sb_person_our.setProgress(personsOur);
        lsp_sb_person_enemy.setProgress(personsEnemy);

        lsp_tv_person_our.setText(String.valueOf(personsOur));
        lsp_tv_person_enemy.setText(String.valueOf(personsEnemy));
        lsp_tv_car_our.setText(String.valueOf(personsOur*3));
        lsp_tv_car_enemy.setText(String.valueOf(personsEnemy*3));

        needUpdateSlots = true;

        setCarsInBuildings();

    }

    private void setCarsInBuildings() {
        
        if (mainCityCalc != null) {

            CCAGame ccaGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);

            int countCars_our = 0;
            int countCars_enemy = 0;
            int countCars_total = 0;
            int countCarsInBuilding = 0;

            int countX2_our = 0;
            int countX2_enemy = 0;
            int countX2_total = 0;
            int increaseOur = 0;
            int increaseEnemy = 0;


            int pointsOur = ccaGame.getPointsOur();
            int pointsEnemy = ccaGame.getPointsEnemy();

            slotsTotal = 0;
            slotsOur = 0;
            slotsEnemy = 0;

            if (ccaGame.isPresent_blt()) {

                countCars_total += ccaGame.getSlots_blt();
                countCarsInBuilding = (ccaGame.getSlots_blt() / 2 + 1);
                ccaGame.setBuildingIsOur_blt(lsc_sb_blt.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_blt(lsc_sb_blt.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_blt(lsc_sb_blt.getProgress() == 2);
                ccaGame.setSlots_blt_our(countCarsInBuilding * (ccaGame.isBuildingIsOur_blt() ? 1 : 0));
                ccaGame.setSlots_blt_enemy(countCarsInBuilding * (ccaGame.isBuildingIsEnemy_blt() ? 1 : 0));
                ccaGame.setSlots_blt_empty(ccaGame.getSlots_blt() - countCarsInBuilding * (ccaGame.isBuildingIsEmpty_blt() ? 0 : 1));
                countCars_our += ccaGame.isBuildingIsOur_blt() ? countCarsInBuilding : 0;
                countCars_enemy += ccaGame.isBuildingIsEnemy_blt() ? countCarsInBuilding : 0;
                countX2_total += ccaGame.isMayX2_blt() ? 1 : 0;
                countX2_our += ccaGame.isMayX2_blt() && ccaGame.isBuildingIsOur_blt() ? 1 : 0;
                countX2_enemy += ccaGame.isMayX2_blt() && ccaGame.isBuildingIsEnemy_blt() ? 1 : 0;
                slotsTotal += ccaGame.getSlots_blt();
                slotsOur += ccaGame.getSlots_blt_our();
                slotsEnemy += ccaGame.getSlots_blt_enemy();
            }

            if (ccaGame.isPresent_blc()) {

                countCars_total += ccaGame.getSlots_blc();
                countCarsInBuilding = (ccaGame.getSlots_blc() / 2 + 1);
                ccaGame.setBuildingIsOur_blc(lsc_sb_blc.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_blc(lsc_sb_blc.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_blc(lsc_sb_blc.getProgress() == 2);
                ccaGame.setSlots_blc_our(countCarsInBuilding * (ccaGame.isBuildingIsOur_blc() ? 1 : 0));
                ccaGame.setSlots_blc_enemy(countCarsInBuilding * (ccaGame.isBuildingIsEnemy_blc() ? 1 : 0));
                ccaGame.setSlots_blc_empty(ccaGame.getSlots_blc() - countCarsInBuilding * (ccaGame.isBuildingIsEmpty_blc() ? 0 : 1));
                countCars_our += ccaGame.isBuildingIsOur_blc() ? countCarsInBuilding : 0;
                countCars_enemy += ccaGame.isBuildingIsEnemy_blc() ? countCarsInBuilding : 0;
                countX2_total += ccaGame.isMayX2_blc() ? 1 : 0;
                countX2_our += ccaGame.isMayX2_blc() && ccaGame.isBuildingIsOur_blc() ? 1 : 0;
                countX2_enemy += ccaGame.isMayX2_blc() && ccaGame.isBuildingIsEnemy_blc() ? 1 : 0;
                slotsTotal += ccaGame.getSlots_blc();
                slotsOur += ccaGame.getSlots_blc_our();
                slotsEnemy += ccaGame.getSlots_blc_enemy();
            }

            if (ccaGame.isPresent_blb()) {

                countCars_total += ccaGame.getSlots_blb();
                countCarsInBuilding = (ccaGame.getSlots_blb() / 2 + 1);
                ccaGame.setBuildingIsOur_blb(lsc_sb_blb.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_blb(lsc_sb_blb.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_blb(lsc_sb_blb.getProgress() == 2);
                ccaGame.setSlots_blb_our(countCarsInBuilding * (ccaGame.isBuildingIsOur_blb() ? 1 : 0));
                ccaGame.setSlots_blb_enemy(countCarsInBuilding * (ccaGame.isBuildingIsEnemy_blb() ? 1 : 0));
                ccaGame.setSlots_blb_empty(ccaGame.getSlots_blb() - countCarsInBuilding * (ccaGame.isBuildingIsEmpty_blb() ? 0 : 1));
                countCars_our += ccaGame.isBuildingIsOur_blb() ? countCarsInBuilding : 0;
                countCars_enemy += ccaGame.isBuildingIsEnemy_blb() ? countCarsInBuilding : 0;
                countX2_total += ccaGame.isMayX2_blb() ? 1 : 0;
                countX2_our += ccaGame.isMayX2_blb() && ccaGame.isBuildingIsOur_blb() ? 1 : 0;
                countX2_enemy += ccaGame.isMayX2_blb() && ccaGame.isBuildingIsEnemy_blb() ? 1 : 0;
                slotsTotal += ccaGame.getSlots_blb();
                slotsOur += ccaGame.getSlots_blb_our();
                slotsEnemy += ccaGame.getSlots_blb_enemy();
            }

            if (ccaGame.isPresent_brt()) {

                countCars_total += ccaGame.getSlots_brt();
                countCarsInBuilding = (ccaGame.getSlots_brt() / 2 + 1);
                ccaGame.setBuildingIsOur_brt(lsc_sb_brt.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_brt(lsc_sb_brt.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_brt(lsc_sb_brt.getProgress() == 2);
                ccaGame.setSlots_brt_our(countCarsInBuilding * (ccaGame.isBuildingIsOur_brt() ? 1 : 0));
                ccaGame.setSlots_brt_enemy(countCarsInBuilding * (ccaGame.isBuildingIsEnemy_brt() ? 1 : 0));
                ccaGame.setSlots_brt_empty(ccaGame.getSlots_brt() - countCarsInBuilding * (ccaGame.isBuildingIsEmpty_brt() ? 0 : 1));
                countCars_our += ccaGame.isBuildingIsOur_brt() ? countCarsInBuilding : 0;
                countCars_enemy += ccaGame.isBuildingIsEnemy_brt() ? countCarsInBuilding : 0;
                countX2_total += ccaGame.isMayX2_brt() ? 1 : 0;
                countX2_our += ccaGame.isMayX2_brt() && ccaGame.isBuildingIsOur_brt() ? 1 : 0;
                countX2_enemy += ccaGame.isMayX2_brt() && ccaGame.isBuildingIsEnemy_brt() ? 1 : 0;
                slotsTotal += ccaGame.getSlots_brt();
                slotsOur += ccaGame.getSlots_brt_our();
                slotsEnemy += ccaGame.getSlots_brt_enemy();
            }

            if (ccaGame.isPresent_brc()) {

                countCars_total += ccaGame.getSlots_brc();
                countCarsInBuilding = (ccaGame.getSlots_brc() / 2 + 1);
                ccaGame.setBuildingIsOur_brc(lsc_sb_brc.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_brc(lsc_sb_brc.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_brc(lsc_sb_brc.getProgress() == 2);
                ccaGame.setSlots_brc_our(countCarsInBuilding * (ccaGame.isBuildingIsOur_brc() ? 1 : 0));
                ccaGame.setSlots_brc_enemy(countCarsInBuilding * (ccaGame.isBuildingIsEnemy_brc() ? 1 : 0));
                ccaGame.setSlots_brc_empty(ccaGame.getSlots_brc() - countCarsInBuilding * (ccaGame.isBuildingIsEmpty_brc() ? 0 : 1));
                countCars_our += ccaGame.isBuildingIsOur_brc() ? countCarsInBuilding : 0;
                countCars_enemy += ccaGame.isBuildingIsEnemy_brc() ? countCarsInBuilding : 0;
                countX2_total += ccaGame.isMayX2_brc() ? 1 : 0;
                countX2_our += ccaGame.isMayX2_brc() && ccaGame.isBuildingIsOur_brc() ? 1 : 0;
                countX2_enemy += ccaGame.isMayX2_brc() && ccaGame.isBuildingIsEnemy_brc() ? 1 : 0;
                slotsTotal += ccaGame.getSlots_brc();
                slotsOur += ccaGame.getSlots_brc_our();
                slotsEnemy += ccaGame.getSlots_brc_enemy();
            }

            if (ccaGame.isPresent_brb()) {

                countCars_total += ccaGame.getSlots_brb();
                countCarsInBuilding = (ccaGame.getSlots_brb() / 2 + 1);
                ccaGame.setBuildingIsOur_brb(lsc_sb_brb.getProgress() == 0);
                ccaGame.setBuildingIsEmpty_brb(lsc_sb_brb.getProgress() == 1);
                ccaGame.setBuildingIsEnemy_brb(lsc_sb_brb.getProgress() == 2);
                ccaGame.setSlots_brb_our(countCarsInBuilding * (ccaGame.isBuildingIsOur_brb() ? 1 : 0));
                ccaGame.setSlots_brb_enemy(countCarsInBuilding * (ccaGame.isBuildingIsEnemy_brb() ? 1 : 0));
                ccaGame.setSlots_brb_empty(ccaGame.getSlots_brb() - countCarsInBuilding * (ccaGame.isBuildingIsEmpty_brb() ? 0 : 1));
                countCars_our += ccaGame.isBuildingIsOur_brb() ? countCarsInBuilding : 0;
                countCars_enemy += ccaGame.isBuildingIsEnemy_brb() ? countCarsInBuilding : 0;
                countX2_total += ccaGame.isMayX2_brb() ? 1 : 0;
                countX2_our += ccaGame.isMayX2_brb() && ccaGame.isBuildingIsOur_brb() ? 1 : 0;
                countX2_enemy += ccaGame.isMayX2_brb() && ccaGame.isBuildingIsEnemy_brb() ? 1 : 0;
                slotsTotal += ccaGame.getSlots_brb();
                slotsOur += ccaGame.getSlots_brb_our();
                slotsEnemy += ccaGame.getSlots_brb_enemy();
            }
            

            if (ccaGame.isPresent_blt()) {

                ccaGame.setX2_blt(ccaGame.isMayX2_blt() && ((countX2_our == countX2_total) || (countX2_enemy == countX2_total)));
                ccaGame.setOur_points_blt(ccaGame.isBuildingIsOur_blt() ? ccaGame.getSlots_blt() * (ccaGame.isX2_blt() ? 2 : 1) : 0);
                ccaGame.setEnemy_points_blt(ccaGame.isBuildingIsEnemy_blt() ? ccaGame.getSlots_blt() * (ccaGame.isX2_blt() ? 2 : 1) : 0);
                increaseOur += ccaGame.getOur_points_blt();
                increaseEnemy += ccaGame.getEnemy_points_blt();
                
            }


            if (ccaGame.isPresent_blc()) {

                ccaGame.setX2_blc(ccaGame.isMayX2_blc() && ((countX2_our == countX2_total) || (countX2_enemy == countX2_total)));
                ccaGame.setOur_points_blc(ccaGame.isBuildingIsOur_blc() ? ccaGame.getSlots_blc() * (ccaGame.isX2_blc() ? 2 : 1) : 0);
                ccaGame.setEnemy_points_blc(ccaGame.isBuildingIsEnemy_blc() ? ccaGame.getSlots_blc() * (ccaGame.isX2_blc() ? 2 : 1) : 0);
                increaseOur += ccaGame.getOur_points_blc();
                increaseEnemy += ccaGame.getEnemy_points_blc();
                
            }

            if (ccaGame.isPresent_blb()) {

                ccaGame.setX2_blb(ccaGame.isMayX2_blb() && ((countX2_our == countX2_total) || (countX2_enemy == countX2_total)));
                ccaGame.setOur_points_blb(ccaGame.isBuildingIsOur_blb() ? ccaGame.getSlots_blb() * (ccaGame.isX2_blb() ? 2 : 1) : 0);
                ccaGame.setEnemy_points_blb(ccaGame.isBuildingIsEnemy_blb() ? ccaGame.getSlots_blb() * (ccaGame.isX2_blb() ? 2 : 1) : 0);
                increaseOur += ccaGame.getOur_points_blb();
                increaseEnemy += ccaGame.getEnemy_points_blb();
                
            }

            if (ccaGame.isPresent_brt()) {

                ccaGame.setX2_brt(ccaGame.isMayX2_brt() && ((countX2_our == countX2_total) || (countX2_enemy == countX2_total)));
                ccaGame.setOur_points_brt(ccaGame.isBuildingIsOur_brt() ? ccaGame.getSlots_brt() * (ccaGame.isX2_brt() ? 2 : 1) : 0);
                ccaGame.setEnemy_points_brt(ccaGame.isBuildingIsEnemy_brt() ? ccaGame.getSlots_brt() * (ccaGame.isX2_brt() ? 2 : 1) : 0);
                increaseOur += ccaGame.getOur_points_brt();
                increaseEnemy += ccaGame.getEnemy_points_brt();
                
            }

            if (ccaGame.isPresent_brc()) {

                ccaGame.setX2_brc(ccaGame.isMayX2_brc() && ((countX2_our == countX2_total) || (countX2_enemy == countX2_total)));
                ccaGame.setOur_points_brc(ccaGame.isBuildingIsOur_brc() ? ccaGame.getSlots_brc() * (ccaGame.isX2_brc() ? 2 : 1) : 0);
                ccaGame.setEnemy_points_brc(ccaGame.isBuildingIsEnemy_brc() ? ccaGame.getSlots_brc() * (ccaGame.isX2_brc() ? 2 : 1) : 0);
                increaseOur += ccaGame.getOur_points_brc();
                increaseEnemy += ccaGame.getEnemy_points_brc();
                
            }

            if (ccaGame.isPresent_brb()) {

                ccaGame.setX2_brb(ccaGame.isMayX2_brb() && ((countX2_our == countX2_total) || (countX2_enemy == countX2_total)));
                ccaGame.setOur_points_brb(ccaGame.isBuildingIsOur_brb() ? ccaGame.getSlots_brb() * (ccaGame.isX2_brb() ? 2 : 1) : 0);
                ccaGame.setEnemy_points_brb(ccaGame.isBuildingIsEnemy_brb() ? ccaGame.getSlots_brb() * (ccaGame.isX2_brb() ? 2 : 1) : 0);
                increaseOur += ccaGame.getOur_points_brb();
                increaseEnemy += ccaGame.getEnemy_points_brb();
                
            }
            
            if (ccaGame != null) {


                ccaGame.setIncreaseOur(increaseOur);
                ccaGame.setIncreaseEnemy(increaseEnemy);
                ccaGame.setSlotsOur(countCars_our);
                ccaGame.setSlotsEnemy(countCars_enemy);
                ccaGame.setSlotsTotal(countCars_total);

                ccaGame.setCountX2(countX2_total);
                ccaGame.setCountOurX2(countX2_our);
                ccaGame.setCountEnemyX2(countX2_enemy);

                ccaGame.setPersonsOur((int)Math.ceil (ccaGame.getSlotsOur() / 3.0d));
                ccaGame.setPersonsEnemy((int)Math.ceil (ccaGame.getSlotsEnemy() / 3.0d));

                ccaGame.setPointsOurInScreenshot(pointsOur);
                ccaGame.setPointsEnemyInScreenshot(pointsEnemy);

                ccaGame.setDateScreenshot(Calendar.getInstance().getTime());

                ccaGame.calcWin(false);


            }

        }

        loadDataToViews();
        
    }




    public void loadDataToViews() {

        String textStartGameTime;
        String textEndGameTime;
//        CCAGame ccaGame = (CCAGame) mainCityCalc.getMapAreas().get(Area.CITY);
        CCAGame ccaGame = mainCCAGame;
        CCATeam ccaOurTeam = (CCATeam) mainCityCalc.getMapAreas().get(Area.TEAM_NAME_OUR);
        CCATeam ccaEnemyTeam = (CCATeam) mainCityCalc.getMapAreas().get(Area.TEAM_NAME_ENEMY);
        CCABuilding ccaBLT = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLT);
        CCABuilding ccaBLC = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLC);
        CCABuilding ccaBLB = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BLB);
        CCABuilding ccaBRT = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRT);
        CCABuilding ccaBRC = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRC);
        CCABuilding ccaBRB = (CCABuilding) mainCityCalc.getMapAreas().get(Area.BRB);
        String pattern = "dd MMM HH:mm";

        if (ccaGame != null && ccaGame.getDateStartGame() != null) {

            ccaGame.calcWin(true);


            Date dateScreenshot = ccaGame.getDateScreenshot();
            int minutesFromTakingScreenshot = (int)((Calendar.getInstance().getTime().getTime() - dateScreenshot.getTime()) / 60000);

            textStartGameTime = getString(R.string.start_game_at) + ": " + Utils.convertDateToString(ccaGame.getDateStartGame(), pattern);    // дата/время начала игры

            textEndGameTime = getString(R.string.end_game_at) + ": "  + Utils.convertDateToString(ccaGame.getDateEndGame(), pattern);          // дата/время окончания игры

            ga_tv_status.setBackground(getDrawable((ccaGame.isWillOurWin() || ccaGame.isWinOur()) ? R.drawable.rounded_small_corner_color_our_dark : R.drawable.rounded_small_corner_color_enemy_dark));
            ga_tv_status.setText(ccaGame.getStatus());   // статус
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

            int slots = 0, slots_our = 0, slots_empty = 0, slots_enemy = 0;

            lgb_iv_blt_icon.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_blt_name.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blt_x2.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blt_points.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blt_slots.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blt_slots_our.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blt_slots_empty.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blt_slots_enemy.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_blt_progress.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            lsc_iv_blt_icon.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);
            lsc_sb_blt.setVisibility(ccaGame.isPresent_blt() ? View.VISIBLE : View.INVISIBLE);

            int color_progress_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_our_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_our), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_our_main), 16)));
            int color_progress_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_enemy_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_enemy), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_enemy_main), 16)));
            int color_progress_empty = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_empty_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_empty), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_empty_main), 16)));
            int progressBitmapWidth = 300;
            int progressBitmapHeight = 20;

            if (ccaGame.isPresent_blt()) {

                lgb_rl_blt.setBackground(getDrawable(ccaGame.isUseInForecast_blt() ? R.drawable.rounded_small_corner_light_gray : R.drawable.rounded_small_corner_gray));

                lgb_iv_blt_can_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToWin_blt() ? R.drawable.ic_can_win_with_x2_true : R.drawable.ic_can_win_with_x2_false));
                lgb_iv_blt_can_early_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToEarlyWin_blt() ? R.drawable.ic_can_early_win_with_x2_true : R.drawable.ic_can_early_win_with_x2_false));

                if (ccaBLT != null) lgb_iv_blt_name.setImageBitmap(PictureProcessor.makeTransparent(ccaBLT.getBmpSrc(),0xFFFFFF));

                lgb_iv_blt_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                        new int[]{ccaGame.getSlots_blt_our(), ccaGame.getSlots_blt_empty(), ccaGame.getSlots_blt_enemy()}));

                lgb_tv_blt_slots.setText(String.valueOf(ccaGame.getSlots_blt()));
                lgb_tv_blt_slots_our.setText(String.valueOf(ccaGame.getSlots_blt_our_toView()));
                lgb_tv_blt_slots_empty.setText(String.valueOf(ccaGame.getSlots_blt_empty()));
                lgb_tv_blt_slots_enemy.setText(String.valueOf(ccaGame.getSlots_blt_enemy_toView()));

                slots += ccaGame.getSlots_blt();
                slots_our += ccaGame.getSlots_blt_our();
                slots_empty += ccaGame.getSlots_blt_empty();
                slots_enemy += ccaGame.getSlots_blt_enemy();

                if (ccaGame.isBuildingIsOur_blt()) {
                    lgb_tv_blt_points.setText("+" + ccaGame.getOur_points_blt());
                    lgb_tv_blt_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_our_dark));
                    lgb_iv_blt_icon.setImageDrawable(getDrawable(R.drawable.ic_blt_blue));
                    lsc_iv_blt_icon.setImageDrawable(getDrawable(R.drawable.ic_blt_blue));
                } else if (ccaGame.isBuildingIsEnemy_blt()) {
                    lgb_tv_blt_points.setText("+" + ccaGame.getEnemy_points_blt());
                    lgb_tv_blt_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_enemy_dark));
                    lgb_iv_blt_icon.setImageDrawable(getDrawable(R.drawable.ic_blt_red));
                    lsc_iv_blt_icon.setImageDrawable(getDrawable(R.drawable.ic_blt_red));
                } else if (ccaGame.isBuildingIsEmpty_blt()) {
                    lgb_tv_blt_points.setText("");
                    lgb_tv_blt_points.setBackgroundColor(0x00000000);
                    lgb_iv_blt_icon.setImageDrawable(getDrawable(R.drawable.ic_blt_gray));
                    lsc_iv_blt_icon.setImageDrawable(getDrawable(R.drawable.ic_blt_gray));
                }
                if (ccaGame.isX2_blt()) {
                    lgb_tv_blt_x2.setText("X2");
                    lgb_tv_blt_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_isx2));
                } else {
                    if (ccaGame.isMayX2_blt()) {
                        lgb_tv_blt_x2.setText("X2");
                        lgb_tv_blt_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_empty_light));
                    } else {
                        lgb_tv_blt_x2.setText("");
                        lgb_tv_blt_x2.setBackgroundColor(0x00000000);
                    }
                }
            }

            lgb_iv_blc_icon.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_blc_name.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blc_x2.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blc_points.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blc_slots.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blc_slots_our.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blc_slots_empty.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blc_slots_enemy.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_blc_progress.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            lsc_iv_blc_icon.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            lsc_sb_blc.setVisibility(ccaGame.isPresent_blc() ? View.VISIBLE : View.INVISIBLE);
            
            if (ccaGame.isPresent_blc()) {

                lgb_rl_blc.setBackground(getDrawable(ccaGame.isUseInForecast_blc() ? R.drawable.rounded_small_corner_light_gray : R.drawable.rounded_small_corner_gray));

                lgb_iv_blc_can_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToWin_blc() ? R.drawable.ic_can_win_with_x2_true : R.drawable.ic_can_win_with_x2_false));
                lgb_iv_blc_can_early_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToEarlyWin_blc() ? R.drawable.ic_can_early_win_with_x2_true : R.drawable.ic_can_early_win_with_x2_false));

                if (ccaBLC != null) lgb_iv_blc_name.setImageBitmap(PictureProcessor.makeTransparent(ccaBLC.getBmpSrc(),0xFFFFFF));
                lgb_iv_blc_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                        new int[]{ccaGame.getSlots_blc_our(), ccaGame.getSlots_blc_empty(), ccaGame.getSlots_blc_enemy()}));

                lgb_tv_blc_slots.setText(String.valueOf(ccaGame.getSlots_blc()));
                lgb_tv_blc_slots_our.setText(String.valueOf(ccaGame.getSlots_blc_our_toView()));
                lgb_tv_blc_slots_empty.setText(String.valueOf(ccaGame.getSlots_blc_empty()));
                lgb_tv_blc_slots_enemy.setText(String.valueOf(ccaGame.getSlots_blc_enemy_toView()));

                slots += ccaGame.getSlots_blc();
                slots_our += ccaGame.getSlots_blc_our();
                slots_empty += ccaGame.getSlots_blc_empty();
                slots_enemy += ccaGame.getSlots_blc_enemy();

                if (ccaGame.isBuildingIsOur_blc()) {
                    lgb_tv_blc_points.setText("+" + ccaGame.getOur_points_blc());
                    lgb_tv_blc_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_our_dark));
                    lgb_iv_blc_icon.setImageDrawable(getDrawable(R.drawable.ic_blc_blue));
                    lsc_iv_blc_icon.setImageDrawable(getDrawable(R.drawable.ic_blc_blue));
                } else if (ccaGame.isBuildingIsEnemy_blc()) {
                    lgb_tv_blc_points.setText("+" + ccaGame.getEnemy_points_blc());
                    lgb_tv_blc_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_enemy_dark));
                    lgb_iv_blc_icon.setImageDrawable(getDrawable(R.drawable.ic_blc_red));
                    lsc_iv_blc_icon.setImageDrawable(getDrawable(R.drawable.ic_blc_red));
                } else if (ccaGame.isBuildingIsEmpty_blc()) {
                    lgb_tv_blc_points.setText("");
                    lgb_tv_blc_points.setBackgroundColor(0x00000000);
                    lgb_iv_blc_icon.setImageDrawable(getDrawable(R.drawable.ic_blc_gray));
                    lsc_iv_blc_icon.setImageDrawable(getDrawable(R.drawable.ic_blc_gray));
                }
                if (ccaGame.isX2_blc()) {
                    lgb_tv_blc_x2.setText("X2");
                    lgb_tv_blc_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_isx2));
                } else {
                    if (ccaGame.isMayX2_blc()) {
                        lgb_tv_blc_x2.setText("X2");
                        lgb_tv_blc_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_empty_light));
                    } else {
                        lgb_tv_blc_x2.setText("");
                        lgb_tv_blc_x2.setBackgroundColor(0x00000000);
                    }
                }
            }


            lgb_iv_blb_icon.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_blb_name.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blb_x2.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blb_points.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blb_slots.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blb_slots_our.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blb_slots_empty.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_blb_slots_enemy.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_blb_progress.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            lsc_iv_blb_icon.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            lsc_sb_blb.setVisibility(ccaGame.isPresent_blb() ? View.VISIBLE : View.INVISIBLE);
            
            if (ccaGame.isPresent_blb()) {

                lgb_rl_blb.setBackground(getDrawable(ccaGame.isUseInForecast_blb() ? R.drawable.rounded_small_corner_light_gray : R.drawable.rounded_small_corner_gray));

                lgb_iv_blb_can_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToWin_blb() ? R.drawable.ic_can_win_with_x2_true : R.drawable.ic_can_win_with_x2_false));
                lgb_iv_blb_can_early_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToEarlyWin_blb() ? R.drawable.ic_can_early_win_with_x2_true : R.drawable.ic_can_early_win_with_x2_false));

                if (ccaBLB != null) lgb_iv_blb_name.setImageBitmap(PictureProcessor.makeTransparent(ccaBLB.getBmpSrc(),0xFFFFFF));

                lgb_iv_blb_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                        new int[]{ccaGame.getSlots_blb_our(), ccaGame.getSlots_blb_empty(), ccaGame.getSlots_blb_enemy()}));

                lgb_tv_blb_slots.setText(String.valueOf(ccaGame.getSlots_blb()));
                lgb_tv_blb_slots_our.setText(String.valueOf(ccaGame.getSlots_blb_our_toView()));
                lgb_tv_blb_slots_empty.setText(String.valueOf(ccaGame.getSlots_blb_empty()));
                lgb_tv_blb_slots_enemy.setText(String.valueOf(ccaGame.getSlots_blb_enemy_toView()));

                slots += ccaGame.getSlots_blb();
                slots_our += ccaGame.getSlots_blb_our();
                slots_empty += ccaGame.getSlots_blb_empty();
                slots_enemy += ccaGame.getSlots_blb_enemy();

                if (ccaGame.isBuildingIsOur_blb()) {
                    lgb_tv_blb_points.setText("+" + ccaGame.getOur_points_blb());
                    lgb_tv_blb_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_our_dark));
                    lgb_iv_blb_icon.setImageDrawable(getDrawable(R.drawable.ic_blb_blue));
                    lsc_iv_blb_icon.setImageDrawable(getDrawable(R.drawable.ic_blb_blue));
                } else if (ccaGame.isBuildingIsEnemy_blb()) {
                    lgb_tv_blb_points.setText("+" + ccaGame.getEnemy_points_blb());
                    lgb_tv_blb_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_enemy_dark));
                    lgb_iv_blb_icon.setImageDrawable(getDrawable(R.drawable.ic_blb_red));
                    lsc_iv_blb_icon.setImageDrawable(getDrawable(R.drawable.ic_blb_red));
                } else if (ccaGame.isBuildingIsEmpty_blb()) {
                    lgb_tv_blb_points.setText("");
                    lgb_tv_blb_points.setBackgroundColor(0x00000000);
                    lgb_iv_blb_icon.setImageDrawable(getDrawable(R.drawable.ic_blb_gray));
                    lsc_iv_blb_icon.setImageDrawable(getDrawable(R.drawable.ic_blb_gray));
                }
                if (ccaGame.isX2_blb()) {
                    lgb_tv_blb_x2.setText("X2");
                    lgb_tv_blb_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_isx2));
                } else {
                    if (ccaGame.isMayX2_blb()) {
                        lgb_tv_blb_x2.setText("X2");
                        lgb_tv_blb_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_empty_light));
                    } else {
                        lgb_tv_blb_x2.setText("");
                        lgb_tv_blb_x2.setBackgroundColor(0x00000000);
                    }
                }
            }


            lgb_iv_brt_icon.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_brt_name.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brt_x2.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brt_points.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brt_slots.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brt_slots_our.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brt_slots_empty.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brt_slots_enemy.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_brt_progress.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            lsc_iv_brt_icon.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            lsc_sb_brt.setVisibility(ccaGame.isPresent_brt() ? View.VISIBLE : View.INVISIBLE);
            
            if (ccaGame.isPresent_brt()) {

                lgb_rl_brt.setBackground(getDrawable(ccaGame.isUseInForecast_brt() ? R.drawable.rounded_small_corner_light_gray : R.drawable.rounded_small_corner_gray));

                lgb_iv_brt_can_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToWin_brt() ? R.drawable.ic_can_win_with_x2_true : R.drawable.ic_can_win_with_x2_false));
                lgb_iv_brt_can_early_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToEarlyWin_brt() ? R.drawable.ic_can_early_win_with_x2_true : R.drawable.ic_can_early_win_with_x2_false));

                if (ccaBRT != null) lgb_iv_brt_name.setImageBitmap(PictureProcessor.makeTransparent(ccaBRT.getBmpSrc(),0xFFFFFF));

                lgb_iv_brt_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                        new int[]{ccaGame.getSlots_brt_our(), ccaGame.getSlots_brt_empty(), ccaGame.getSlots_brt_enemy()}));

                lgb_tv_brt_slots.setText(String.valueOf(ccaGame.getSlots_brt()));
                lgb_tv_brt_slots_our.setText(String.valueOf(ccaGame.getSlots_brt_our_toView()));
                lgb_tv_brt_slots_empty.setText(String.valueOf(ccaGame.getSlots_brt_empty()));
                lgb_tv_brt_slots_enemy.setText(String.valueOf(ccaGame.getSlots_brt_enemy_toView()));

                slots += ccaGame.getSlots_brt();
                slots_our += ccaGame.getSlots_brt_our();
                slots_empty += ccaGame.getSlots_brt_empty();
                slots_enemy += ccaGame.getSlots_brt_enemy();

                if (ccaGame.isBuildingIsOur_brt()) {
                    lgb_tv_brt_points.setText("+" + ccaGame.getOur_points_brt());
                    lgb_tv_brt_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_our_dark));
                    lgb_iv_brt_icon.setImageDrawable(getDrawable(R.drawable.ic_brt_blue));
                    lsc_iv_brt_icon.setImageDrawable(getDrawable(R.drawable.ic_brt_blue));
                } else if (ccaGame.isBuildingIsEnemy_brt()) {
                    lgb_tv_brt_points.setText("+" + ccaGame.getEnemy_points_brt());
                    lgb_tv_brt_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_enemy_dark));
                    lgb_iv_brt_icon.setImageDrawable(getDrawable(R.drawable.ic_brt_red));
                    lsc_iv_brt_icon.setImageDrawable(getDrawable(R.drawable.ic_brt_red));
                } else if (ccaGame.isBuildingIsEmpty_brt()) {
                    lgb_tv_brt_points.setText("");
                    lgb_tv_brt_points.setBackgroundColor(0x00000000);
                    lgb_iv_brt_icon.setImageDrawable(getDrawable(R.drawable.ic_brt_gray));
                    lsc_iv_brt_icon.setImageDrawable(getDrawable(R.drawable.ic_brt_gray));
                }
                if (ccaGame.isX2_brt()) {
                    lgb_tv_brt_x2.setText("X2");
                    lgb_tv_brt_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_isx2));
                } else {
                    if (ccaGame.isMayX2_brt()) {
                        lgb_tv_brt_x2.setText("X2");
                        lgb_tv_brt_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_empty_light));
                    } else {
                        lgb_tv_brt_x2.setText("");
                        lgb_tv_brt_x2.setBackgroundColor(0x00000000);
                    }
                }
            }

            lgb_iv_brc_icon.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_brc_name.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brc_x2.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brc_points.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brc_slots.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brc_slots_our.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brc_slots_empty.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brc_slots_enemy.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_brc_progress.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            lsc_iv_brc_icon.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            lsc_sb_brc.setVisibility(ccaGame.isPresent_brc() ? View.VISIBLE : View.INVISIBLE);
            
            if (ccaGame.isPresent_brc()) {

                lgb_rl_brc.setBackground(getDrawable(ccaGame.isUseInForecast_brc() ? R.drawable.rounded_small_corner_light_gray : R.drawable.rounded_small_corner_gray));

                lgb_iv_brc_can_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToWin_brc() ? R.drawable.ic_can_win_with_x2_true : R.drawable.ic_can_win_with_x2_false));
                lgb_iv_brc_can_early_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToEarlyWin_brc() ? R.drawable.ic_can_early_win_with_x2_true : R.drawable.ic_can_early_win_with_x2_false));

                if (ccaBRC != null) lgb_iv_brc_name.setImageBitmap(PictureProcessor.makeTransparent(ccaBRC.getBmpSrc(),0xFFFFFF));

                lgb_iv_brc_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                        new int[]{ccaGame.getSlots_brc_our(), ccaGame.getSlots_brc_empty(), ccaGame.getSlots_brc_enemy()}));

                lgb_tv_brc_slots.setText(String.valueOf(ccaGame.getSlots_brc()));
                lgb_tv_brc_slots_our.setText(String.valueOf(ccaGame.getSlots_brc_our_toView()));
                lgb_tv_brc_slots_empty.setText(String.valueOf(ccaGame.getSlots_brc_empty()));
                lgb_tv_brc_slots_enemy.setText(String.valueOf(ccaGame.getSlots_brc_enemy_toView()));

                slots += ccaGame.getSlots_brc();
                slots_our += ccaGame.getSlots_brc_our();
                slots_empty += ccaGame.getSlots_brc_empty();
                slots_enemy += ccaGame.getSlots_brc_enemy();

                if (ccaGame.isBuildingIsOur_brc()) {
                    lgb_tv_brc_points.setText("+" + ccaGame.getOur_points_brc());
                    lgb_tv_brc_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_our_dark));
                    lgb_iv_brc_icon.setImageDrawable(getDrawable(R.drawable.ic_brc_blue));
                    lsc_iv_brc_icon.setImageDrawable(getDrawable(R.drawable.ic_brc_blue));
                } else if (ccaGame.isBuildingIsEnemy_brc()) {
                    lgb_tv_brc_points.setText("+" + ccaGame.getEnemy_points_brc());
                    lgb_tv_brc_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_enemy_dark));
                    lgb_iv_brc_icon.setImageDrawable(getDrawable(R.drawable.ic_brc_red));
                    lsc_iv_brc_icon.setImageDrawable(getDrawable(R.drawable.ic_brc_red));
                } else if (ccaGame.isBuildingIsEmpty_brc()) {
                    lgb_tv_brc_points.setText("");
                    lgb_tv_brc_points.setBackgroundColor(0x00000000);
                    lgb_iv_brc_icon.setImageDrawable(getDrawable(R.drawable.ic_brc_gray));
                    lsc_iv_brc_icon.setImageDrawable(getDrawable(R.drawable.ic_brc_gray));
                }
                if (ccaGame.isX2_brc()) {
                    lgb_tv_brc_x2.setText("X2");
                    lgb_tv_brc_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_isx2));
                } else {
                    if (ccaGame.isMayX2_brc()) {
                        lgb_tv_brc_x2.setText("X2");
                        lgb_tv_brc_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_empty_light));
                    } else {
                        lgb_tv_brc_x2.setText("");
                        lgb_tv_brc_x2.setBackgroundColor(0x00000000);
                    }
                }
            }


            lgb_iv_brb_icon.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_brb_name.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brb_x2.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brb_points.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brb_slots.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brb_slots_our.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brb_slots_empty.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            lgb_tv_brb_slots_enemy.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            lgb_iv_brb_progress.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            lsc_iv_brb_icon.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            lsc_sb_brb.setVisibility(ccaGame.isPresent_brb() ? View.VISIBLE : View.INVISIBLE);
            
            if (ccaGame.isPresent_brb()) {

                lgb_rl_brb.setBackground(getDrawable(ccaGame.isUseInForecast_brb() ? R.drawable.rounded_small_corner_light_gray : R.drawable.rounded_small_corner_gray));

                lgb_iv_brb_can_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToWin_brb() ? R.drawable.ic_can_win_with_x2_true : R.drawable.ic_can_win_with_x2_false));
                lgb_iv_brb_can_early_win_with_x2.setImageDrawable(getDrawable(ccaGame.isNeedToEarlyWin_brb() ? R.drawable.ic_can_early_win_with_x2_true : R.drawable.ic_can_early_win_with_x2_false));

                if (ccaBRB != null) lgb_iv_brb_name.setImageBitmap(PictureProcessor.makeTransparent(ccaBRB.getBmpSrc(),0xFFFFFF));

                lgb_iv_brb_progress.setImageBitmap(PictureProcessor.getProgressBitmap(progressBitmapWidth, progressBitmapHeight,
                        new int[]{color_progress_our, color_progress_empty, color_progress_enemy},
                        new int[]{ccaGame.getSlots_brb_our(), ccaGame.getSlots_brb_empty(), ccaGame.getSlots_brb_enemy()}));

                lgb_tv_brb_slots.setText(String.valueOf(ccaGame.getSlots_brb()));
                lgb_tv_brb_slots_our.setText(String.valueOf(ccaGame.getSlots_brb_our_toView()));
                lgb_tv_brb_slots_empty.setText(String.valueOf(ccaGame.getSlots_brb_empty()));
                lgb_tv_brb_slots_enemy.setText(String.valueOf(ccaGame.getSlots_brb_enemy_toView()));

                slots += ccaGame.getSlots_brb();
                slots_our += ccaGame.getSlots_brb_our();
                slots_empty += ccaGame.getSlots_brb_empty();
                slots_enemy += ccaGame.getSlots_brb_enemy();

                if (ccaGame.isBuildingIsOur_brb()) {
                    lgb_tv_brb_points.setText("+" + ccaGame.getOur_points_brb());
                    lgb_tv_brb_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_our_dark));
                    lgb_iv_brb_icon.setImageDrawable(getDrawable(R.drawable.ic_brb_blue));
                    lsc_iv_brb_icon.setImageDrawable(getDrawable(R.drawable.ic_brb_blue));
                } else if (ccaGame.isBuildingIsEnemy_brb()) {
                    lgb_tv_brb_points.setText("+" + ccaGame.getEnemy_points_brb());
                    lgb_tv_brb_points.setBackground(getDrawable(R.drawable.rounded_small_corner_color_enemy_dark));
                    lgb_iv_brb_icon.setImageDrawable(getDrawable(R.drawable.ic_brb_red));
                    lsc_iv_brb_icon.setImageDrawable(getDrawable(R.drawable.ic_brb_red));
                } else if (ccaGame.isBuildingIsEmpty_brb()) {
                    lgb_tv_brb_points.setText("");
                    lgb_tv_brb_points.setBackgroundColor(0x00000000);
                    lgb_iv_brb_icon.setImageDrawable(getDrawable(R.drawable.ic_brb_gray));
                    lsc_iv_brb_icon.setImageDrawable(getDrawable(R.drawable.ic_brb_gray));
                }
                if (ccaGame.isX2_brb()) {
                    lgb_tv_brb_x2.setText("X2");
                    lgb_tv_brb_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_isx2));
                } else {
                    if (ccaGame.isMayX2_brb()) {
                        lgb_tv_brb_x2.setText("X2");
                        lgb_tv_brb_x2.setBackground(getDrawable(R.drawable.rounded_small_corner_color_empty_light));
                    } else {
                        lgb_tv_brb_x2.setText("");
                        lgb_tv_brb_x2.setBackgroundColor(0x00000000);
                    }
                }
            }


            lgci_tv_game_slots.setText(String.valueOf(slots));
            lgci_tv_game_slots_our.setText(String.valueOf(slots_our));
            lgci_tv_game_slots_empty.setText(String.valueOf(slots_empty));
            lgci_tv_game_slots_enemy.setText(String.valueOf(slots_enemy));


            personsOur = (int)Math.ceil (slotsOur / 3.0d);
            personsEnemy = (int)Math.ceil (slotsEnemy / 3.0d);

            lsp_sb_person_our.setProgress(personsOur);
            lsp_sb_person_enemy.setProgress(personsEnemy);

            lsp_tv_person_our.setText(String.valueOf(personsOur));
            lsp_tv_person_enemy.setText(String.valueOf(personsEnemy));
            lsp_tv_car_our.setText(String.valueOf(personsOur*3));
            lsp_tv_car_enemy.setText(String.valueOf(personsEnemy*3));

            
        }


    }


    public void initializeViews() {

        sa_ad_banner = findViewById(R.id.sa_ad_banner);

        lsc_iv_blt_icon = findViewById(R.id.lsc_iv_blt_icon);
        lsc_iv_blc_icon = findViewById(R.id.lsc_iv_blc_icon);
        lsc_iv_blb_icon = findViewById(R.id.lsc_iv_blb_icon);
        lsc_iv_brt_icon = findViewById(R.id.lsc_iv_brt_icon);
        lsc_iv_brc_icon = findViewById(R.id.lsc_iv_brc_icon);
        lsc_iv_brb_icon = findViewById(R.id.lsc_iv_brb_icon);

        lsc_sb_blt = findViewById(R.id.lsc_sb_blt);
        lsc_sb_blc = findViewById(R.id.lsc_sb_blc);
        lsc_sb_blb = findViewById(R.id.lsc_sb_blb);
        lsc_sb_brt = findViewById(R.id.lsc_sb_brt);
        lsc_sb_brc = findViewById(R.id.lsc_sb_brc);
        lsc_sb_brb = findViewById(R.id.lsc_sb_brb);

        lsp_sb_person_our = findViewById(R.id.lsp_sb_person_our);
        lsp_iv_person_our = findViewById(R.id.lsp_iv_person_our);
        lsp_tv_person_our = findViewById(R.id.lsp_tv_person_our);
        lsp_iv_car_our = findViewById(R.id.lsp_iv_car_our);
        lsp_tv_car_our = findViewById(R.id.lsp_tv_car_our);

        lsp_sb_person_enemy = findViewById(R.id.lsp_sb_person_enemy);
        lsp_iv_person_enemy = findViewById(R.id.lsp_iv_person_enemy);
        lsp_tv_person_enemy = findViewById(R.id.lsp_tv_person_enemy);
        lsp_iv_car_enemy = findViewById(R.id.lsp_iv_car_enemy);
        lsp_tv_car_enemy = findViewById(R.id.lsp_tv_car_enemy);

        ga_tv_status = findViewById(R.id.ga_tv_status);
        lgi_tv_start_game_time = findViewById(R.id.lgi_tv_start_game_time);     // время начала игры
        lgi_tv_end_game_time = findViewById(R.id.lgi_tv_end_game_time);       // время конца игры
        lgi_tv_vs = findViewById(R.id.lgi_tv_vs);                  // "против"

        // Time views
        lgi_tv_total_time = findViewById(R.id.lgi_tv_total_time);          // время до конца игры
        lgi_tv_early_win = findViewById(R.id.lgi_tv_early_win);           // очки до досрочной победы

        // Our team views
        lgi_iv_our_team_name = findViewById(R.id.lgi_iv_our_team_name);      // название нашей команды (картинка)
        lgi_tv_our_increase = findViewById(R.id.lgi_tv_our_increase);        // прибавка нашей команды
        lgi_tv_our_points = findViewById(R.id.lgi_tv_our_points);          // очки нашей команды
        lgi_tv_our_end_time = findViewById(R.id.lgi_tv_our_end_time);        // время до конца игры для нашей команды

        // Enemy team views
        lgi_iv_enemy_team_name = findViewById(R.id.lgi_iv_enemy_team_name);    // название команды противника (картинка)
        lgi_tv_enemy_increase = findViewById(R.id.lgi_tv_enemy_increase);      // прибавка команды противника
        lgi_tv_enemy_points = findViewById(R.id.lgi_tv_enemy_points);        // очки команды противника
        lgi_tv_enemy_end_time = findViewById(R.id.lgi_tv_enemy_end_time);      // время до конца игры для команды противника

        // BLT views
        lgb_iv_blt_icon = findViewById(R.id.lgb_iv_blt_icon);           // blt - иконка здания (картинка)
        lgb_iv_blt_name = findViewById(R.id.lgb_iv_blt_name);           // blt - название здания (картинка)
        lgb_tv_blt_x2 = findViewById(R.id.lgb_tv_blt_x2);              // blt - х2
        lgb_tv_blt_points = findViewById(R.id.lgb_tv_blt_points);          // blt - очки
        lgb_tv_blt_slots = findViewById(R.id.lgb_tv_blt_slots);           // blt - слоты
        lgb_tv_blt_slots_our = findViewById(R.id.lgb_tv_blt_slots_our);       // blt - наши слоты
        lgb_tv_blt_slots_empty = findViewById(R.id.lgb_tv_blt_slots_empty);     // blt - слоты противника
        lgb_tv_blt_slots_enemy = findViewById(R.id.lgb_tv_blt_slots_enemy);     // blt - пустые слоты
        lgb_iv_blt_progress = findViewById(R.id.lgb_iv_blt_progress);       // blt - прогресс
        lgb_iv_blt_can_win_with_x2 = findViewById(R.id.lgb_iv_blt_can_win_with_x2);
        lgb_iv_blt_can_early_win_with_x2 = findViewById(R.id.lgb_iv_blt_can_early_win_with_x2);



        // BLC views
        lgb_iv_blc_icon = findViewById(R.id.lgb_iv_blc_icon);           // blc - иконка здания (картинка)
        lgb_iv_blc_name = findViewById(R.id.lgb_iv_blc_name);           // blc - название здания (картинка)
        lgb_tv_blc_x2 = findViewById(R.id.lgb_tv_blc_x2);              // blc - х2
        lgb_tv_blc_points = findViewById(R.id.lgb_tv_blc_points);          // blc - очки
        lgb_tv_blc_slots = findViewById(R.id.lgb_tv_blc_slots);           // blc - слоты
        lgb_tv_blc_slots_our = findViewById(R.id.lgb_tv_blc_slots_our);       // blc - наши слоты
        lgb_tv_blc_slots_empty = findViewById(R.id.lgb_tv_blc_slots_empty);     // blc - слоты противника
        lgb_tv_blc_slots_enemy = findViewById(R.id.lgb_tv_blc_slots_enemy);     // blc - пустые слоты
        lgb_iv_blc_progress = findViewById(R.id.lgb_iv_blc_progress);       // blc - прогресс
        lgb_iv_blc_can_win_with_x2 = findViewById(R.id.lgb_iv_blc_can_win_with_x2);
        lgb_iv_blc_can_early_win_with_x2 = findViewById(R.id.lgb_iv_blc_can_early_win_with_x2);

        // BLB views
        lgb_iv_blb_icon = findViewById(R.id.lgb_iv_blb_icon);           // blb - иконка здания (картинка)
        lgb_iv_blb_name = findViewById(R.id.lgb_iv_blb_name);           // blb - название здания (картинка)
        lgb_tv_blb_x2 = findViewById(R.id.lgb_tv_blb_x2);              // blb - х2
        lgb_tv_blb_points = findViewById(R.id.lgb_tv_blb_points);          // blb - очки
        lgb_tv_blb_slots = findViewById(R.id.lgb_tv_blb_slots);           // blb - слоты
        lgb_tv_blb_slots_our = findViewById(R.id.lgb_tv_blb_slots_our);       // blb - наши слоты
        lgb_tv_blb_slots_empty = findViewById(R.id.lgb_tv_blb_slots_empty);     // blb - слоты противника
        lgb_tv_blb_slots_enemy = findViewById(R.id.lgb_tv_blb_slots_enemy);     // blb - пустые слоты
        lgb_iv_blb_progress = findViewById(R.id.lgb_iv_blb_progress);       // blb - прогресс
        lgb_iv_blb_can_win_with_x2 = findViewById(R.id.lgb_iv_blb_can_win_with_x2);
        lgb_iv_blb_can_early_win_with_x2 = findViewById(R.id.lgb_iv_blb_can_early_win_with_x2);

        // BRT views
        lgb_iv_brt_icon = findViewById(R.id.lgb_iv_brt_icon);           // brt - иконка здания (картинка)
        lgb_iv_brt_name = findViewById(R.id.lgb_iv_brt_name);           // brt - название здания (картинка)
        lgb_tv_brt_x2 = findViewById(R.id.lgb_tv_brt_x2);              // brt - х2
        lgb_tv_brt_points = findViewById(R.id.lgb_tv_brt_points);          // brt - очки
        lgb_tv_brt_slots = findViewById(R.id.lgb_tv_brt_slots);           // brt - слоты
        lgb_tv_brt_slots_our = findViewById(R.id.lgb_tv_brt_slots_our);       // brt - наши слоты
        lgb_tv_brt_slots_empty = findViewById(R.id.lgb_tv_brt_slots_empty);     // brt - слоты противника
        lgb_tv_brt_slots_enemy = findViewById(R.id.lgb_tv_brt_slots_enemy);     // brt - пустые слоты
        lgb_iv_brt_progress = findViewById(R.id.lgb_iv_brt_progress);       // brt - прогресс
        lgb_iv_brt_can_win_with_x2 = findViewById(R.id.lgb_iv_brt_can_win_with_x2);
        lgb_iv_brt_can_early_win_with_x2 = findViewById(R.id.lgb_iv_brt_can_early_win_with_x2);

        // BRC views
        lgb_iv_brc_icon = findViewById(R.id.lgb_iv_brc_icon);           // brc - иконка здания (картинка)
        lgb_iv_brc_name = findViewById(R.id.lgb_iv_brc_name);           // brc - название здания (картинка)
        lgb_tv_brc_x2 = findViewById(R.id.lgb_tv_brc_x2);              // brc - х2
        lgb_tv_brc_points = findViewById(R.id.lgb_tv_brc_points);          // brc - очки
        lgb_tv_brc_slots = findViewById(R.id.lgb_tv_brc_slots);           // brc - слоты
        lgb_tv_brc_slots_our = findViewById(R.id.lgb_tv_brc_slots_our);       // brc - наши слоты
        lgb_tv_brc_slots_empty = findViewById(R.id.lgb_tv_brc_slots_empty);     // brc - слоты противника
        lgb_tv_brc_slots_enemy = findViewById(R.id.lgb_tv_brc_slots_enemy);     // brc - пустые слоты
        lgb_iv_brc_progress = findViewById(R.id.lgb_iv_brc_progress);       // brc - прогресс
        lgb_iv_brc_can_win_with_x2 = findViewById(R.id.lgb_iv_brc_can_win_with_x2);
        lgb_iv_brc_can_early_win_with_x2 = findViewById(R.id.lgb_iv_brc_can_early_win_with_x2);

        // BRB views
        lgb_iv_brb_icon = findViewById(R.id.lgb_iv_brb_icon);           // brb - иконка здания (картинка)
        lgb_iv_brb_name = findViewById(R.id.lgb_iv_brb_name);           // brb - название здания (картинка)
        lgb_tv_brb_x2 = findViewById(R.id.lgb_tv_brb_x2);              // brb - х2
        lgb_tv_brb_points = findViewById(R.id.lgb_tv_brb_points);          // brb - очки
        lgb_tv_brb_slots = findViewById(R.id.lgb_tv_brb_slots);           // brb - слоты
        lgb_tv_brb_slots_our = findViewById(R.id.lgb_tv_brb_slots_our);       // brb - наши слоты
        lgb_tv_brb_slots_empty = findViewById(R.id.lgb_tv_brb_slots_empty);     // brb - слоты противника
        lgb_tv_brb_slots_enemy = findViewById(R.id.lgb_tv_brb_slots_enemy);     // brb - пустые слоты
        lgb_iv_brb_progress = findViewById(R.id.lgb_iv_brb_progress);       // blt - прогресс
        lgb_iv_brb_can_win_with_x2 = findViewById(R.id.lgb_iv_brb_can_win_with_x2);
        lgb_iv_brb_can_early_win_with_x2 = findViewById(R.id.lgb_iv_brb_can_early_win_with_x2);

        lgci_iv_game_car_black = findViewById(R.id.lgci_iv_game_car_black);     // машинка черная большая (картинка)
        lgci_iv_game_car_our = findViewById(R.id.lgci_iv_game_car_our);       // машинка синяя большая (картинка)
        lgci_iv_game_car_empty = findViewById(R.id.lgci_iv_game_car_empty);     // машинка серая большая (картинка)
        lgci_iv_game_car_enemy = findViewById(R.id.lgci_iv_game_car_enemy);     // машинка красная большая (картинка)
        lgci_tv_game_slots = findViewById(R.id.lgci_tv_game_slots);          // слотов в игре всего
        lgci_tv_game_slots_our = findViewById(R.id.lgci_tv_game_slots_our);      // слотов в игре наших
        lgci_tv_game_slots_empty = findViewById(R.id.lgci_tv_game_slots_empty);    // слотов в игре пустых
        lgci_tv_game_slots_enemy = findViewById(R.id.lgci_tv_game_slots_enemy);    // слотов в игре противника

        lgb_rl_blt = findViewById(R.id.lgb_rl_blt);    // слотов в игре противника
        lgb_rl_blc = findViewById(R.id.lgb_rl_blc);    // слотов в игре противника
        lgb_rl_blb = findViewById(R.id.lgb_rl_blb);    // слотов в игре противника
        lgb_rl_brt = findViewById(R.id.lgb_rl_brt);    // слотов в игре противника
        lgb_rl_brc = findViewById(R.id.lgb_rl_brc);    // слотов в игре противника
        lgb_rl_brb = findViewById(R.id.lgb_rl_brb);    // слотов в игре противника

        sa_iv_calc_timed_win = findViewById(R.id.sa_iv_calc_timed_win);
        sa_iv_calc_timed_win_without_x2 = findViewById(R.id.sa_iv_calc_timed_win_without_x2);
        sa_iv_calc_early_win = findViewById(R.id.sa_iv_calc_early_win);
        sa_iv_calc_early_win_without_x2 = findViewById(R.id.sa_iv_calc_early_win_without_x2);


    }

    private SeekBar.OnSeekBarChangeListener bltSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Context context = GlobalApplication.getAppContext();
            if (progress == 0) {
                lsc_sb_blt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                lsc_sb_blt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                lsc_sb_blt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
            Context context = GlobalApplication.getAppContext();
            if (progress == 0) {
                lsc_sb_blc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                lsc_sb_blc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                lsc_sb_blc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
            Context context = GlobalApplication.getAppContext();
            if (progress == 0) {
                lsc_sb_blb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                lsc_sb_blb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                lsc_sb_blb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
            Context context = GlobalApplication.getAppContext();
            if (progress == 0) {
                lsc_sb_brt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                lsc_sb_brt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                lsc_sb_brt.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
            Context context = GlobalApplication.getAppContext();
            if (progress == 0) {
                lsc_sb_brc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                lsc_sb_brc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                lsc_sb_brc.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
            Context context = GlobalApplication.getAppContext();
            if (progress == 0) {
                lsc_sb_brb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
            } else if (progress == 1) {
                lsc_sb_brb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
            } else {
                lsc_sb_brb.setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
                lsp_tv_person_our.setText(String.valueOf(progress));
                lsp_tv_car_our.setText(String.valueOf(progress*3));
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
                lsp_tv_person_enemy.setText(String.valueOf(progress));
                lsp_tv_car_enemy.setText(String.valueOf(progress*3));
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

        CCAGame ccaGame = GameActivity.mainCCAGame;

        if (ccaGame.isCanWin()) {

            int blt_progress = ccaGame.isNeedToWin_blt() ? 0 : 2;
            int blc_progress = ccaGame.isNeedToWin_blc() ? 0 : 2;
            int blb_progress = ccaGame.isNeedToWin_blb() ? 0 : 2;
            int brt_progress = ccaGame.isNeedToWin_brt() ? 0 : 2;
            int brc_progress = ccaGame.isNeedToWin_brc() ? 0 : 2;
            int brb_progress = ccaGame.isNeedToWin_brb() ? 0 : 2;

            needUpdateSlots = false;
            lsc_sb_blt.setProgress(blt_progress);
            lsc_sb_blc.setProgress(blc_progress);
            lsc_sb_blb.setProgress(blb_progress);
            lsc_sb_brt.setProgress(brt_progress);
            lsc_sb_brc.setProgress(brc_progress);
            lsc_sb_brb.setProgress(brb_progress);
            needUpdateSlots = true;

            setCarsInBuildings();

        }


    }

    public void calcTimedWinWithoutx2(View view) {
        

        CCAGame ccaGame = GameActivity.mainCCAGame;

        if (ccaGame.isCanWinWithoutX2()) {

            int blt_progress = ccaGame.isNeedToWinWithoutX2_blt() ? 0 : 2;
            int blc_progress = ccaGame.isNeedToWinWithoutX2_blc() ? 0 : 2;
            int blb_progress = ccaGame.isNeedToWinWithoutX2_blb() ? 0 : 2;
            int brt_progress = ccaGame.isNeedToWinWithoutX2_brt() ? 0 : 2;
            int brc_progress = ccaGame.isNeedToWinWithoutX2_brc() ? 0 : 2;
            int brb_progress = ccaGame.isNeedToWinWithoutX2_brb() ? 0 : 2;

            needUpdateSlots = false;
            lsc_sb_blt.setProgress(blt_progress);
            lsc_sb_blc.setProgress(blc_progress);
            lsc_sb_blb.setProgress(blb_progress);
            lsc_sb_brt.setProgress(brt_progress);
            lsc_sb_brc.setProgress(brc_progress);
            lsc_sb_brb.setProgress(brb_progress);
            needUpdateSlots = true;

            setCarsInBuildings();

        }


    }

    public void calcEarlyWin(View view) {
        
        CCAGame ccaGame = GameActivity.mainCCAGame;

        if (ccaGame.isCanEarlyWin()) {

            int blt_progress = ccaGame.isNeedToEarlyWin_blt() ? 0 : 2;
            int blc_progress = ccaGame.isNeedToEarlyWin_blc() ? 0 : 2;
            int blb_progress = ccaGame.isNeedToEarlyWin_blb() ? 0 : 2;
            int brt_progress = ccaGame.isNeedToEarlyWin_brt() ? 0 : 2;
            int brc_progress = ccaGame.isNeedToEarlyWin_brc() ? 0 : 2;
            int brb_progress = ccaGame.isNeedToEarlyWin_brb() ? 0 : 2;

            needUpdateSlots = false;
            lsc_sb_blt.setProgress(blt_progress);
            lsc_sb_blc.setProgress(blc_progress);
            lsc_sb_blb.setProgress(blb_progress);
            lsc_sb_brt.setProgress(brt_progress);
            lsc_sb_brc.setProgress(brc_progress);
            lsc_sb_brb.setProgress(brb_progress);
            needUpdateSlots = true;

            setCarsInBuildings();

        }

    }

    public void calcEarlyWinWithoutx2(View view) {
        
        CCAGame ccaGame = GameActivity.mainCCAGame;

        if (ccaGame.isCanEarlyWinWithoutX2()) {

            int blt_progress = ccaGame.isNeedToEarlyWinWithoutX2_blt() ? 0 : 2;
            int blc_progress = ccaGame.isNeedToEarlyWinWithoutX2_blc() ? 0 : 2;
            int blb_progress = ccaGame.isNeedToEarlyWinWithoutX2_blb() ? 0 : 2;
            int brt_progress = ccaGame.isNeedToEarlyWinWithoutX2_brt() ? 0 : 2;
            int brc_progress = ccaGame.isNeedToEarlyWinWithoutX2_brc() ? 0 : 2;
            int brb_progress = ccaGame.isNeedToEarlyWinWithoutX2_brb() ? 0 : 2;

            needUpdateSlots = false;
            lsc_sb_blt.setProgress(blt_progress);
            lsc_sb_blc.setProgress(blc_progress);
            lsc_sb_blb.setProgress(blb_progress);
            lsc_sb_brt.setProgress(brt_progress);
            lsc_sb_brc.setProgress(brc_progress);
            lsc_sb_brb.setProgress(brb_progress);
            needUpdateSlots = true;

            setCarsInBuildings();

        }

    }




}
