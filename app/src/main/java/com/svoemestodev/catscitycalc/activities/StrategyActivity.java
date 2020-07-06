package com.svoemestodev.catscitycalc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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

import java.util.Calendar;
import java.util.Date;

public class StrategyActivity extends AppCompatActivity {

    RelativeLayout[] lgb_rl_bld = new RelativeLayout[6];
    ImageView[] lsc_iv_bld_icon = new ImageView[6];
    SeekBar[] lsc_sb_bld = new SeekBar[6];
    
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

        for (int buildingIndex = 0; buildingIndex < 6; buildingIndex++) {
            int finalBuildingIndex = buildingIndex;
            lsc_sb_bld[buildingIndex].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Context context = GlobalApplication.getAppContext();
                    if (progress == 0) {
                        lsc_sb_bld[finalBuildingIndex].setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_our_36dp));
                    } else if (progress == 1) {
                        lsc_sb_bld[finalBuildingIndex].setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_empty_36dp));
                    } else {
                        lsc_sb_bld[finalBuildingIndex].setThumb(ContextCompat.getDrawable(context, R.drawable.ic_car_enemy_36dp));
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
            });
        }
        
        
        
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

        for (int buildingIndex = 0; buildingIndex < 6; buildingIndex++) {
            if (ccaGame.getBuildings()[buildingIndex].isPresent()) {
                if (ccaGame.getBuildings()[buildingIndex].isBuildingIsOur()) lsc_sb_bld[buildingIndex].setProgress(0);
                if (ccaGame.getBuildings()[buildingIndex].isBuildingIsEmpty()) lsc_sb_bld[buildingIndex].setProgress(1);
                if (ccaGame.getBuildings()[buildingIndex].isBuildingIsEnemy()) lsc_sb_bld[buildingIndex].setProgress(2);
                slotsTotal += ccaGame.getBuildings()[buildingIndex].getSlots();
                slotsOur += ccaGame.getBuildings()[buildingIndex].getSlots_our();
                slotsEnemy += ccaGame.getBuildings()[buildingIndex].getSlots_enemy();
            }
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

            for (int buildingIndex = 0; buildingIndex < 6; buildingIndex++) {
                if (ccaGame.getBuildings()[buildingIndex].isPresent()) {
                    countCars_total += ccaGame.getBuildings()[buildingIndex].getSlots();
                    countCarsInBuilding = (ccaGame.getBuildings()[buildingIndex].getSlots() / 2 + 1);
                    ccaGame.getBuildings()[buildingIndex].setBuildingIsOur(lsc_sb_bld[buildingIndex].getProgress() == 0);
                    ccaGame.getBuildings()[buildingIndex].setBuildingIsEmpty(lsc_sb_bld[buildingIndex].getProgress() == 1);
                    ccaGame.getBuildings()[buildingIndex].setBuildingIsEnemy(lsc_sb_bld[buildingIndex].getProgress() == 2);
                    ccaGame.getBuildings()[buildingIndex].setSlots_our(countCarsInBuilding * (ccaGame.getBuildings()[buildingIndex].isBuildingIsOur() ? 1 : 0));
                    ccaGame.getBuildings()[buildingIndex].setSlots_enemy(countCarsInBuilding * (ccaGame.getBuildings()[buildingIndex].isBuildingIsEnemy() ? 1 : 0));
                    ccaGame.getBuildings()[buildingIndex].setSlots_empty(ccaGame.getBuildings()[buildingIndex].getSlots() - countCarsInBuilding * (ccaGame.getBuildings()[buildingIndex].isBuildingIsEmpty() ? 0 : 1));
                    countCars_our += ccaGame.getBuildings()[buildingIndex].isBuildingIsOur() ? countCarsInBuilding : 0;
                    countCars_enemy += ccaGame.getBuildings()[buildingIndex].isBuildingIsEnemy() ? countCarsInBuilding : 0;
                    countX2_total += ccaGame.getBuildings()[buildingIndex].isMayX2() ? 1 : 0;
                    countX2_our += ccaGame.getBuildings()[buildingIndex].isMayX2() && ccaGame.getBuildings()[buildingIndex].isBuildingIsOur() ? 1 : 0;
                    countX2_enemy += ccaGame.getBuildings()[buildingIndex].isMayX2() && ccaGame.getBuildings()[buildingIndex].isBuildingIsEnemy() ? 1 : 0;
                    slotsTotal += ccaGame.getBuildings()[buildingIndex].getSlots();
                    slotsOur += ccaGame.getBuildings()[buildingIndex].getSlots_our();
                    slotsEnemy += ccaGame.getBuildings()[buildingIndex].getSlots_enemy();
                }
            }

            for (int buildingIndex = 0; buildingIndex < 6; buildingIndex++) {
                if (ccaGame.getBuildings()[buildingIndex].isPresent()) {
                    ccaGame.getBuildings()[buildingIndex].setX2(ccaGame.getBuildings()[buildingIndex].isMayX2() && ((countX2_our == countX2_total) || (countX2_enemy == countX2_total)));
                    ccaGame.getBuildings()[buildingIndex].setOur_points(ccaGame.getBuildings()[buildingIndex].isBuildingIsOur() ? ccaGame.getBuildings()[buildingIndex].getSlots() * (ccaGame.getBuildings()[buildingIndex].isX2() ? 2 : 1) : 0);
                    ccaGame.getBuildings()[buildingIndex].setEnemy_points(ccaGame.getBuildings()[buildingIndex].isBuildingIsEnemy() ? ccaGame.getBuildings()[buildingIndex].getSlots() * (ccaGame.getBuildings()[buildingIndex].isX2() ? 2 : 1) : 0);
                    increaseOur += ccaGame.getBuildings()[buildingIndex].getOur_points();
                    increaseEnemy += ccaGame.getBuildings()[buildingIndex].getEnemy_points();
                }
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

            int[] bldIconsBlue = new int[6];
            int[] bldIconsRed = new int[6];
            int[] bldIconsGray = new int[6];

            bldIconsBlue[0] = R.drawable.ic_bld1_blue; bldIconsRed[0] = R.drawable.ic_bld1_red; bldIconsGray[0] = R.drawable.ic_bld1_gray;
            bldIconsBlue[1] = R.drawable.ic_bld2_blue; bldIconsRed[1] = R.drawable.ic_bld2_red; bldIconsGray[1] = R.drawable.ic_bld2_gray;
            bldIconsBlue[2] = R.drawable.ic_bld3_blue; bldIconsRed[2] = R.drawable.ic_bld3_red; bldIconsGray[2] = R.drawable.ic_bld3_gray;
            bldIconsBlue[3] = R.drawable.ic_bld4_blue; bldIconsRed[3] = R.drawable.ic_bld4_red; bldIconsGray[3] = R.drawable.ic_bld4_gray;
            bldIconsBlue[4] = R.drawable.ic_bld5_blue; bldIconsRed[4] = R.drawable.ic_bld5_red; bldIconsGray[4] = R.drawable.ic_bld5_gray;
            bldIconsBlue[5] = R.drawable.ic_bld6_blue; bldIconsRed[5] = R.drawable.ic_bld6_red; bldIconsGray[5] = R.drawable.ic_bld6_gray;

            
            int color_progress_our = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_our_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_our), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_our_main), 16)));
            int color_progress_enemy = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_enemy_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_enemy), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_enemy_main), 16)));
            int color_progress_empty = sharedPreferences.getInt(context.getString(R.string.pref_rgb_bxx_progress_empty_main),sharedPreferences.getInt(context.getString(R.string.pref_def_rgb_bxx_progress_empty), (int)Long.parseLong(context.getString(R.string.def_rgb_bxx_progress_empty_main), 16)));
            int progressBitmapWidth = 300;
            int progressBitmapHeight = 20;

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
                lsc_iv_bld_icon[buildingIndex].setVisibility(ccaGame.getBuildings()[buildingIndex].isPresent() ? View.VISIBLE : View.INVISIBLE);
                lsc_sb_bld[buildingIndex].setVisibility(ccaGame.getBuildings()[buildingIndex].isPresent() ? View.VISIBLE : View.INVISIBLE);
                
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

        lsc_iv_bld_icon[0] = findViewById(R.id.lsc_iv_bld1_icon);
        lsc_iv_bld_icon[1] = findViewById(R.id.lsc_iv_bld2_icon);
        lsc_iv_bld_icon[2] = findViewById(R.id.lsc_iv_bld3_icon);
        lsc_iv_bld_icon[3] = findViewById(R.id.lsc_iv_bld4_icon);
        lsc_iv_bld_icon[4] = findViewById(R.id.lsc_iv_bld5_icon);
        lsc_iv_bld_icon[5] = findViewById(R.id.lsc_iv_bld6_icon);

        lsc_sb_bld[0] = findViewById(R.id.lsc_sb_bld1);
        lsc_sb_bld[1] = findViewById(R.id.lsc_sb_bld2);
        lsc_sb_bld[2] = findViewById(R.id.lsc_sb_bld3);
        lsc_sb_bld[3] = findViewById(R.id.lsc_sb_bld4);
        lsc_sb_bld[4] = findViewById(R.id.lsc_sb_bld5);
        lsc_sb_bld[5] = findViewById(R.id.lsc_sb_bld6);

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

        lgci_iv_game_car_black = findViewById(R.id.lgci_iv_game_car_black);     // машинка черная большая (картинка)
        lgci_iv_game_car_our = findViewById(R.id.lgci_iv_game_car_our);       // машинка синяя большая (картинка)
        lgci_iv_game_car_empty = findViewById(R.id.lgci_iv_game_car_empty);     // машинка серая большая (картинка)
        lgci_iv_game_car_enemy = findViewById(R.id.lgci_iv_game_car_enemy);     // машинка красная большая (картинка)
        lgci_tv_game_slots = findViewById(R.id.lgci_tv_game_slots);          // слотов в игре всего
        lgci_tv_game_slots_our = findViewById(R.id.lgci_tv_game_slots_our);      // слотов в игре наших
        lgci_tv_game_slots_empty = findViewById(R.id.lgci_tv_game_slots_empty);    // слотов в игре пустых
        lgci_tv_game_slots_enemy = findViewById(R.id.lgci_tv_game_slots_enemy);    // слотов в игре противника

        lgb_rl_bld[0] = findViewById(R.id.lgb_rl_bld1);
        lgb_rl_bld[1] = findViewById(R.id.lgb_rl_bld2);
        lgb_rl_bld[2] = findViewById(R.id.lgb_rl_bld3);
        lgb_rl_bld[3] = findViewById(R.id.lgb_rl_bld4);
        lgb_rl_bld[4] = findViewById(R.id.lgb_rl_bld5);
        lgb_rl_bld[5] = findViewById(R.id.lgb_rl_bld6);

        sa_iv_calc_timed_win = findViewById(R.id.sa_iv_calc_timed_win);
        sa_iv_calc_timed_win_without_x2 = findViewById(R.id.sa_iv_calc_timed_win_without_x2);
        sa_iv_calc_early_win = findViewById(R.id.sa_iv_calc_early_win);
        sa_iv_calc_early_win_without_x2 = findViewById(R.id.sa_iv_calc_early_win_without_x2);


    }


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

            int bld1_progress = ccaGame.getBuildings()[0].isNeedToWin() ? 0 : 2;
            int bld2_progress = ccaGame.getBuildings()[1].isNeedToWin() ? 0 : 2;
            int bld3_progress = ccaGame.getBuildings()[2].isNeedToWin() ? 0 : 2;
            int bld4_progress = ccaGame.getBuildings()[3].isNeedToWin() ? 0 : 2;
            int bld5_progress = ccaGame.getBuildings()[4].isNeedToWin() ? 0 : 2;
            int bld6_progress = ccaGame.getBuildings()[5].isNeedToWin() ? 0 : 2;

            needUpdateSlots = false;
            lsc_sb_bld[0].setProgress(bld1_progress);
            lsc_sb_bld[1].setProgress(bld2_progress);
            lsc_sb_bld[2].setProgress(bld3_progress);
            lsc_sb_bld[3].setProgress(bld4_progress);
            lsc_sb_bld[4].setProgress(bld5_progress);
            lsc_sb_bld[0].setProgress(bld6_progress);
            needUpdateSlots = true;

            setCarsInBuildings();

        }


    }

    public void calcTimedWinWithoutx2(View view) {
        

        CCAGame ccaGame = GameActivity.mainCCAGame;

        if (ccaGame.isCanWinWithoutX2()) {

            int bld1_progress = ccaGame.getBuildings()[0].isNeedToWinWithoutX2() ? 0 : 2;
            int bld2_progress = ccaGame.getBuildings()[1].isNeedToWinWithoutX2() ? 0 : 2;
            int bld3_progress = ccaGame.getBuildings()[2].isNeedToWinWithoutX2() ? 0 : 2;
            int bld4_progress = ccaGame.getBuildings()[3].isNeedToWinWithoutX2() ? 0 : 2;
            int bld5_progress = ccaGame.getBuildings()[4].isNeedToWinWithoutX2() ? 0 : 2;
            int bld6_progress = ccaGame.getBuildings()[5].isNeedToWinWithoutX2() ? 0 : 2;

            needUpdateSlots = false;
            lsc_sb_bld[0].setProgress(bld1_progress);
            lsc_sb_bld[1].setProgress(bld2_progress);
            lsc_sb_bld[2].setProgress(bld3_progress);
            lsc_sb_bld[3].setProgress(bld4_progress);
            lsc_sb_bld[4].setProgress(bld5_progress);
            lsc_sb_bld[0].setProgress(bld6_progress);
            needUpdateSlots = true;

            setCarsInBuildings();

        }


    }

    public void calcEarlyWin(View view) {
        
        CCAGame ccaGame = GameActivity.mainCCAGame;

        if (ccaGame.isCanEarlyWin()) {

            int bld1_progress = ccaGame.getBuildings()[0].isNeedToEarlyWin() ? 0 : 2;
            int bld2_progress = ccaGame.getBuildings()[1].isNeedToEarlyWin() ? 0 : 2;
            int bld3_progress = ccaGame.getBuildings()[2].isNeedToEarlyWin() ? 0 : 2;
            int bld4_progress = ccaGame.getBuildings()[3].isNeedToEarlyWin() ? 0 : 2;
            int bld5_progress = ccaGame.getBuildings()[4].isNeedToEarlyWin() ? 0 : 2;
            int bld6_progress = ccaGame.getBuildings()[5].isNeedToEarlyWin() ? 0 : 2;

            needUpdateSlots = false;
            lsc_sb_bld[0].setProgress(bld1_progress);
            lsc_sb_bld[1].setProgress(bld2_progress);
            lsc_sb_bld[2].setProgress(bld3_progress);
            lsc_sb_bld[3].setProgress(bld4_progress);
            lsc_sb_bld[4].setProgress(bld5_progress);
            lsc_sb_bld[0].setProgress(bld6_progress);
            needUpdateSlots = true;

            setCarsInBuildings();

        }

    }

    public void calcEarlyWinWithoutx2(View view) {
        
        CCAGame ccaGame = GameActivity.mainCCAGame;

        if (ccaGame.isCanEarlyWinWithoutX2()) {

            int bld1_progress = ccaGame.getBuildings()[0].isNeedToEarlyWinWithoutX2() ? 0 : 2;
            int bld2_progress = ccaGame.getBuildings()[1].isNeedToEarlyWinWithoutX2() ? 0 : 2;
            int bld3_progress = ccaGame.getBuildings()[2].isNeedToEarlyWinWithoutX2() ? 0 : 2;
            int bld4_progress = ccaGame.getBuildings()[3].isNeedToEarlyWinWithoutX2() ? 0 : 2;
            int bld5_progress = ccaGame.getBuildings()[4].isNeedToEarlyWinWithoutX2() ? 0 : 2;
            int bld6_progress = ccaGame.getBuildings()[5].isNeedToEarlyWinWithoutX2() ? 0 : 2;

            needUpdateSlots = false;
            lsc_sb_bld[0].setProgress(bld1_progress);
            lsc_sb_bld[1].setProgress(bld2_progress);
            lsc_sb_bld[2].setProgress(bld3_progress);
            lsc_sb_bld[3].setProgress(bld4_progress);
            lsc_sb_bld[4].setProgress(bld5_progress);
            lsc_sb_bld[0].setProgress(bld6_progress);
            needUpdateSlots = true;

            setCarsInBuildings();

        }

    }


}
