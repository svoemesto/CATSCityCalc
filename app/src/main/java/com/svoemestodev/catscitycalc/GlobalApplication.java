package com.svoemestodev.catscitycalc;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;

public class GlobalApplication extends Application {

    public transient static String pathToCATScalcFolder;        // путь к папке программы

    private static Context appContext;
    private static SharedPreferences sharedPreferences;

    private static int rgb_progress_our;
    private static int rgb_progress_enemy;
    private static int rgb_progress_empty;
    private static int thm_progress_our;
    private static int thp_progress_our;
    private static int thm_progress_enemy;
    private static int thp_progress_enemy;
    private static int thm_progress_empty;
    private static int thp_progress_empty;
    private static int rgb_bxx_mayX2;
    private static int rgb_bxx_isX2;
    private static int rgb_bxx_slot_main;
    private static int rgb_bxx_slot_back;
    private static int thm_bxx_slot;
    private static int thp_bxx_slot;
    private static int rgb_box_info_city_main;
    private static int rgb_box_info_city_back;
    private static int rgb_box_info_city_thm;
    private static int rgb_box_info_city_thp;
    private static int rgb_box_back_main;
    private static int rgb_box_back_back;
    private static int rgb_box_back_thm;
    private static int rgb_box_back_thp;
    private static int rgb_city_main;
    private static int rgb_city_back;
    private static int rgb_city_thm;
    private static int rgb_city_thp;
    private static int rgb_total_time_main;
    private static int rgb_total_time_back1;
    private static int rgb_total_time_back2;
    private static int rgb_total_time_thm;
    private static int rgb_total_time_thp;
    private static int rgb_early_win_main;
    private static int rgb_early_win_back;
    private static int rgb_early_win_thm;
    private static int rgb_early_win_thp;
    private static int rgb_points_our_main;
    private static int rgb_points_our_back1;
    private static int rgb_points_our_back2;
    private static int rgb_points_our_thm;
    private static int rgb_points_our_thp;
    private static int rgb_increase_our_main;
    private static int rgb_increase_our_back;
    private static int rgb_increase_our_thm;
    private static int rgb_increase_our_thp;
    private static int rgb_team_name_our_main;
    private static int rgb_team_name_our_back;
    private static int rgb_team_name_our_thm;
    private static int rgb_team_name_our_thp;
    private static int rgb_points_enemy_main;
    private static int rgb_points_enemy_back1;
    private static int rgb_points_enemy_back2;
    private static int rgb_points_enemy_thm;
    private static int rgb_points_enemy_thp;
    private static int rgb_increase_enemy_main;
    private static int rgb_increase_enemy_back;
    private static int rgb_increase_enemy_thm;
    private static int rgb_increase_enemy_thp;
    private static int rgb_team_name_enemy_main;
    private static int rgb_team_name_enemy_back;
    private static int rgb_team_name_enemy_thm;
    private static int rgb_team_name_enemy_thp;
    private static int rgb_bxx_main;
    private static int rgb_bxx_back;
    private static int rgb_bxx_thm;
    private static int rgb_bxx_thp;
    private static int rgb_box_info_garage_main;
    private static int rgb_box_info_garage_back1;
    private static int rgb_box_info_garage_back2;
    private static int rgb_box_info_garage_thm;
    private static int rgb_box_info_garage_thp;
    private static int rgb_box_info_car_main;
    private static int rgb_box_info_car_back;
    private static int rgb_box_info_car_thm;
    private static int rgb_box_info_car_thp;
    private static int rgb_car_in_city_box1_main;
    private static int rgb_car_in_city_box1_thm;
    private static int rgb_car_in_city_box1_thp;
    private static int rgb_car_in_city_box2_main;
    private static int rgb_car_in_city_box2_thm;
    private static int rgb_car_in_city_box2_thp;
    private static int rgb_car_in_city_building_main;
    private static int rgb_car_in_city_building_back;
    private static int rgb_car_in_city_building_thm;
    private static int rgb_car_in_city_building_thp;
    private static int rgb_car_in_city_slot_main;
    private static int rgb_car_in_city_slot_back;
    private static int rgb_car_in_city_slot_thm;
    private static int rgb_car_in_city_slot_thp;
    private static int rgb_car_in_garage_slot_main;
    private static int rgb_car_in_garage_slot_back;
    private static int rgb_car_in_garage_slot_thm;
    private static int rgb_car_in_garage_slot_thp;
    private static int rgb_car_in_city_health_main;
    private static int rgb_car_in_city_health_back;
    private static int rgb_car_in_city_health_thm;
    private static int rgb_car_in_city_health_thp;
    private static int rgb_car_in_city_shield_main;
    private static int rgb_car_in_city_shield_back;
    private static int rgb_car_in_city_shield_thm;
    private static int rgb_car_in_city_shield_thp;
    private static int rgb_car_in_garage_health_main;
    private static int rgb_car_in_garage_health_back1;
    private static int rgb_car_in_garage_health_back2;
    private static int rgb_car_in_garage_health_thm;
    private static int rgb_car_in_garage_health_thp;
    private static int rgb_car_in_garage_shield_main;
    private static int rgb_car_in_garage_shield_back1;
    private static int rgb_car_in_garage_shield_back2;
    private static int rgb_car_in_garage_shield_thm;
    private static int rgb_car_in_garage_shield_thp;
    private static int rgb_car_in_city_state_main;
    private static int rgb_car_in_city_state_back;
    private static int rgb_car_in_city_state_thm;
    private static int rgb_car_in_city_state_thp;
    private static int rgb_car_in_city_healbox_main;
    private static int rgb_car_in_city_healbox_back;
    private static int rgb_car_in_city_healbox_thm;
    private static int rgb_car_in_city_healbox_thp;
    private static int rgb_car_in_city_timebox1_main;
    private static int rgb_car_in_city_timebox1_back;
    private static int rgb_car_in_city_timebox1_thm;
    private static int rgb_car_in_city_timebox1_thp;
    private static int rgb_car_in_city_timebox2_main;
    private static int rgb_car_in_city_timebox2_back;
    private static int rgb_car_in_city_timebox2_thm;
    private static int rgb_car_in_city_timebox2_thp;
    private static int rgb_car_in_city_statebox1_main;
    private static int rgb_car_in_city_statebox1_back;
    private static int rgb_car_in_city_statebox1_thm;
    private static int rgb_car_in_city_statebox1_thp;
    private static int rgb_car_in_city_statebox2_main;
    private static int rgb_car_in_city_statebox2_back;
    private static int rgb_car_in_city_statebox2_thm;
    private static int rgb_car_in_city_statebox2_thp;
    private static int rgb_car_in_city_statebox3_main;
    private static int rgb_car_in_city_statebox3_back;
    private static int rgb_car_in_city_statebox3_thm;
    private static int rgb_car_in_city_statebox3_thp;


    private static float cut_box_info_city_x1;
    private static float cut_box_info_city_x2;
    private static float cut_box_info_city_y1;
    private static float cut_box_info_city_y2;

    private static float cut_box_info_car_x1;
    private static float cut_box_info_car_x2;
    private static float cut_box_info_car_y1;
    private static float cut_box_info_car_y2;

    private static float cut_box_info_garage_x1;
    private static float cut_box_info_garage_x2;
    private static float cut_box_info_garage_y1;
    private static float cut_box_info_garage_y2;

    private static float cut_box_back_x1;
    private static float cut_box_back_x2;
    private static float cut_box_back_y1;
    private static float cut_box_back_y2;

    private static float cut_city_x1;
    private static float cut_city_x2;
    private static float cut_city_y1;
    private static float cut_city_y2;

    private static float cut_total_time_x1;
    private static float cut_total_time_x2;
    private static float cut_total_time_y1;
    private static float cut_total_time_y2;

    private static float cut_early_win_x1;
    private static float cut_early_win_x2;
    private static float cut_early_win_y1;
    private static float cut_early_win_y2;

    private static float cut_points_and_increase_our_x1;
    private static float cut_points_and_increase_our_x2;
    private static float cut_points_and_increase_our_y1;
    private static float cut_points_and_increase_our_y2;

    private static float cut_team_name_our_x1;
    private static float cut_team_name_our_x2;
    private static float cut_team_name_our_y1;
    private static float cut_team_name_our_y2;

    private static float cut_points_and_increase_enemy_x1;
    private static float cut_points_and_increase_enemy_x2;
    private static float cut_points_and_increase_enemy_y1;
    private static float cut_points_and_increase_enemy_y2;

    private static float cut_team_name_enemy_x1;
    private static float cut_team_name_enemy_x2;
    private static float cut_team_name_enemy_y1;
    private static float cut_team_name_enemy_y2;

    private static float cut_blt_x1;
    private static float cut_blt_x2;
    private static float cut_blt_y1;
    private static float cut_blt_y2;

    private static float cut_blt_name_x1;
    private static float cut_blt_name_x2;
    private static float cut_blt_name_y1;
    private static float cut_blt_name_y2;

    private static float cut_blt_progress_x1;
    private static float cut_blt_progress_x2;
    private static float cut_blt_progress_y1;
    private static float cut_blt_progress_y2;

    private static float cut_blt_slots_x1;
    private static float cut_blt_slots_x2;
    private static float cut_blt_slots_y1;
    private static float cut_blt_slots_y2;

    private static float cut_blc_x1;
    private static float cut_blc_x2;
    private static float cut_blc_y1;
    private static float cut_blc_y2;

    private static float cut_blc_name_x1;
    private static float cut_blc_name_x2;
    private static float cut_blc_name_y1;
    private static float cut_blc_name_y2;

    private static float cut_blc_progress_x1;
    private static float cut_blc_progress_x2;
    private static float cut_blc_progress_y1;
    private static float cut_blc_progress_y2;

    private static float cut_blc_slots_x1;
    private static float cut_blc_slots_x2;
    private static float cut_blc_slots_y1;
    private static float cut_blc_slots_y2;

    private static float cut_blb_x1;
    private static float cut_blb_x2;
    private static float cut_blb_y1;
    private static float cut_blb_y2;

    private static float cut_blb_name_x1;
    private static float cut_blb_name_x2;
    private static float cut_blb_name_y1;
    private static float cut_blb_name_y2;

    private static float cut_blb_progress_x1;
    private static float cut_blb_progress_x2;
    private static float cut_blb_progress_y1;
    private static float cut_blb_progress_y2;

    private static float cut_blb_slots_x1;
    private static float cut_blb_slots_x2;
    private static float cut_blb_slots_y1;
    private static float cut_blb_slots_y2;

    private static float cut_brt_x1;
    private static float cut_brt_x2;
    private static float cut_brt_y1;
    private static float cut_brt_y2;

    private static float cut_brt_name_x1;
    private static float cut_brt_name_x2;
    private static float cut_brt_name_y1;
    private static float cut_brt_name_y2;

    private static float cut_brt_progress_x1;
    private static float cut_brt_progress_x2;
    private static float cut_brt_progress_y1;
    private static float cut_brt_progress_y2;

    private static float cut_brt_slots_x1;
    private static float cut_brt_slots_x2;
    private static float cut_brt_slots_y1;
    private static float cut_brt_slots_y2;

    private static float cut_brc_x1;
    private static float cut_brc_x2;
    private static float cut_brc_y1;
    private static float cut_brc_y2;

    private static float cut_brc_name_x1;
    private static float cut_brc_name_x2;
    private static float cut_brc_name_y1;
    private static float cut_brc_name_y2;

    private static float cut_brc_progress_x1;
    private static float cut_brc_progress_x2;
    private static float cut_brc_progress_y1;
    private static float cut_brc_progress_y2;

    private static float cut_brc_slots_x1;
    private static float cut_brc_slots_x2;
    private static float cut_brc_slots_y1;
    private static float cut_brc_slots_y2;

    private static float cut_brb_x1;
    private static float cut_brb_x2;
    private static float cut_brb_y1;
    private static float cut_brb_y2;

    private static float cut_brb_name_x1;
    private static float cut_brb_name_x2;
    private static float cut_brb_name_y1;
    private static float cut_brb_name_y2;

    private static float cut_brb_progress_x1;
    private static float cut_brb_progress_x2;
    private static float cut_brb_progress_y1;
    private static float cut_brb_progress_y2;

    private static float cut_brb_slots_x1;
    private static float cut_brb_slots_x2;
    private static float cut_brb_slots_y1;
    private static float cut_brb_slots_y2;

    private static float cut_car_in_city_box1_x1;
    private static float cut_car_in_city_box1_x2;
    private static float cut_car_in_city_box1_y1;
    private static float cut_car_in_city_box1_y2;

    private static float cut_car_in_city_box2_x1;
    private static float cut_car_in_city_box2_x2;
    private static float cut_car_in_city_box2_y1;
    private static float cut_car_in_city_box2_y2;

    private static float cut_car_in_city_info_x1;
    private static float cut_car_in_city_info_x2;
    private static float cut_car_in_city_info_y1;
    private static float cut_car_in_city_info_y2;

    private static float cut_car_in_city_building_x1;
    private static float cut_car_in_city_building_x2;
    private static float cut_car_in_city_building_y1;
    private static float cut_car_in_city_building_y2;

    private static float cut_car_in_city_picture_x1;
    private static float cut_car_in_city_picture_x2;
    private static float cut_car_in_city_picture_y1;
    private static float cut_car_in_city_picture_y2;

    private static float cut_car_in_garage_picture_x1;
    private static float cut_car_in_garage_picture_x2;
    private static float cut_car_in_garage_picture_y1;
    private static float cut_car_in_garage_picture_y2;

    private static float cut_car_in_city_slot1_x1;
    private static float cut_car_in_city_slot1_x2;
    private static float cut_car_in_city_slot1_y1;
    private static float cut_car_in_city_slot1_y2;

    private static float cut_car_in_city_slot2_x1;
    private static float cut_car_in_city_slot2_x2;
    private static float cut_car_in_city_slot2_y1;
    private static float cut_car_in_city_slot2_y2;

    private static float cut_car_in_city_slot3_x1;
    private static float cut_car_in_city_slot3_x2;
    private static float cut_car_in_city_slot3_y1;
    private static float cut_car_in_city_slot3_y2;

    private static float cut_car_in_garage_slot1_x1;
    private static float cut_car_in_garage_slot1_x2;
    private static float cut_car_in_garage_slot1_y1;
    private static float cut_car_in_garage_slot1_y2;

    private static float cut_car_in_garage_slot2_x1;
    private static float cut_car_in_garage_slot2_x2;
    private static float cut_car_in_garage_slot2_y1;
    private static float cut_car_in_garage_slot2_y2;

    private static float cut_car_in_garage_slot3_x1;
    private static float cut_car_in_garage_slot3_x2;
    private static float cut_car_in_garage_slot3_y1;
    private static float cut_car_in_garage_slot3_y2;

    private static float cut_car_in_city_health_x1;
    private static float cut_car_in_city_health_x2;
    private static float cut_car_in_city_health_y1;
    private static float cut_car_in_city_health_y2;

    private static float cut_car_in_city_shield_x1;
    private static float cut_car_in_city_shield_x2;
    private static float cut_car_in_city_shield_y1;
    private static float cut_car_in_city_shield_y2;

    private static float cut_car_in_garage_health_x1;
    private static float cut_car_in_garage_health_x2;
    private static float cut_car_in_garage_health_y1;
    private static float cut_car_in_garage_health_y2;

    private static float cut_car_in_garage_shield_x1;
    private static float cut_car_in_garage_shield_x2;
    private static float cut_car_in_garage_shield_y1;
    private static float cut_car_in_garage_shield_y2;

    private static float cut_car_in_city_state_x1;
    private static float cut_car_in_city_state_x2;
    private static float cut_car_in_city_state_y1;
    private static float cut_car_in_city_state_y2;

    private static float cut_car_in_city_healbox_x1;
    private static float cut_car_in_city_healbox_x2;
    private static float cut_car_in_city_healbox_y1;
    private static float cut_car_in_city_healbox_y2;

    private static float cut_car_in_city_timebox1_x1;
    private static float cut_car_in_city_timebox1_x2;
    private static float cut_car_in_city_timebox1_y1;
    private static float cut_car_in_city_timebox1_y2;

    private static float cut_car_in_city_timebox2_x1;
    private static float cut_car_in_city_timebox2_x2;
    private static float cut_car_in_city_timebox2_y1;
    private static float cut_car_in_city_timebox2_y2;

    private static float cut_car_in_city_statebox1_x1;
    private static float cut_car_in_city_statebox1_x2;
    private static float cut_car_in_city_statebox1_y1;
    private static float cut_car_in_city_statebox1_y2;

    private static float cut_car_in_city_statebox2_x1;
    private static float cut_car_in_city_statebox2_x2;
    private static float cut_car_in_city_statebox2_y1;
    private static float cut_car_in_city_statebox2_y2;

    private static float cut_car_in_city_statebox3_x1;
    private static float cut_car_in_city_statebox3_x2;
    private static float cut_car_in_city_statebox3_y1;
    private static float cut_car_in_city_statebox3_y2;

    public static CityCalcService mService;
    public static boolean mBound = false;

    public static Intent serviceIntent;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            CityCalcService.LocalBinder binder = (CityCalcService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        serviceIntent = new Intent(this, CityCalcService.class);
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);



        appContext = getApplicationContext();
        sharedPreferences = appContext.getSharedPreferences(appContext.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);

        /* If you has other classes that need context object to initialize when application is created,
         you can use the appContext here to process. */
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static int getRgb_progress_our() {
        return rgb_progress_our;
    }

    public static int getRgb_progress_enemy() {
        return rgb_progress_enemy;
    }

    public static int getRgb_progress_empty() {
        return rgb_progress_empty;
    }

    public static int getThm_progress_our() {
        return thm_progress_our;
    }

    public static int getThp_progress_our() {
        return thp_progress_our;
    }

    public static int getThm_progress_enemy() {
        return thm_progress_enemy;
    }

    public static int getThp_progress_enemy() {
        return thp_progress_enemy;
    }

    public static int getThm_progress_empty() {
        return thm_progress_empty;
    }

    public static int getThp_progress_empty() {
        return thp_progress_empty;
    }

    public static int getRgb_bxx_mayX2() {
        return rgb_bxx_mayX2;
    }

    public static int getRgb_bxx_isX2() {
        return rgb_bxx_isX2;
    }

    public static int getRgb_bxx_slot_main() {
        return rgb_bxx_slot_main;
    }

    public static int getRgb_bxx_slot_back() {
        return rgb_bxx_slot_back;
    }

    public static int getThm_bxx_slot() {
        return thm_bxx_slot;
    }

    public static int getThp_bxx_slot() {
        return thp_bxx_slot;
    }

    public static int getRgb_box_info_city_main() {
        return rgb_box_info_city_main;
    }

    public static int getRgb_box_info_city_thm() {
        return rgb_box_info_city_thm;
    }

    public static int getRgb_box_info_city_thp() {
        return rgb_box_info_city_thp;
    }

    public static int getRgb_box_back_main() {
        return rgb_box_back_main;
    }

    public static int getRgb_box_back_back() {
        return rgb_box_back_back;
    }

    public static int getRgb_box_back_thm() {
        return rgb_box_back_thm;
    }

    public static int getRgb_box_back_thp() {
        return rgb_box_back_thp;
    }

    public static int getRgb_box_info_garage_main() {
        return rgb_box_info_garage_main;
    }

    public static int getRgb_box_info_garage_back1() {
        return rgb_box_info_garage_back1;
    }

    public static int getRgb_box_info_garage_back2() {
        return rgb_box_info_garage_back2;
    }

    public static int getRgb_box_info_garage_thm() {
        return rgb_box_info_garage_thm;
    }

    public static int getRgb_box_info_garage_thp() {
        return rgb_box_info_garage_thp;
    }

    public static int getRgb_box_info_car_main() {
        return rgb_box_info_car_main;
    }

    public static int getRgb_box_info_car_thm() {
        return rgb_box_info_car_thm;
    }

    public static int getRgb_box_info_car_thp() {
        return rgb_box_info_car_thp;
    }

    public static int getRgb_car_in_city_box1_main() {
        return rgb_car_in_city_box1_main;
    }

    public static int getRgb_car_in_city_box1_thm() {
        return rgb_car_in_city_box1_thm;
    }

    public static int getRgb_car_in_city_box1_thp() {
        return rgb_car_in_city_box1_thp;
    }

    public static int getRgb_car_in_city_box2_main() {
        return rgb_car_in_city_box2_main;
    }

    public static int getRgb_car_in_city_box2_thm() {
        return rgb_car_in_city_box2_thm;
    }

    public static int getRgb_car_in_city_box2_thp() {
        return rgb_car_in_city_box2_thp;
    }

    public static int getRgb_car_in_city_building_main() {
        return rgb_car_in_city_building_main;
    }

    public static int getRgb_car_in_city_building_back() {
        return rgb_car_in_city_building_back;
    }

    public static int getRgb_car_in_city_building_thm() {
        return rgb_car_in_city_building_thm;
    }

    public static int getRgb_car_in_city_building_thp() {
        return rgb_car_in_city_building_thp;
    }

    public static int getRgb_car_in_city_slot_main() {
        return rgb_car_in_city_slot_main;
    }

    public static int getRgb_car_in_city_slot_back() {
        return rgb_car_in_city_slot_back;
    }

    public static int getRgb_car_in_city_slot_thm() {
        return rgb_car_in_city_slot_thm;
    }

    public static int getRgb_car_in_city_slot_thp() {
        return rgb_car_in_city_slot_thp;
    }

    public static int getRgb_car_in_garage_slot_main() {
        return rgb_car_in_garage_slot_main;
    }

    public static int getRgb_car_in_garage_slot_back() {
        return rgb_car_in_garage_slot_back;
    }

    public static int getRgb_car_in_garage_slot_thm() {
        return rgb_car_in_garage_slot_thm;
    }

    public static int getRgb_car_in_garage_slot_thp() {
        return rgb_car_in_garage_slot_thp;
    }

    public static int getRgb_car_in_city_health_main() {
        return rgb_car_in_city_health_main;
    }

    public static int getRgb_car_in_city_health_back() {
        return rgb_car_in_city_health_back;
    }

    public static int getRgb_car_in_city_health_thm() {
        return rgb_car_in_city_health_thm;
    }

    public static int getRgb_car_in_city_health_thp() {
        return rgb_car_in_city_health_thp;
    }

    public static int getRgb_car_in_city_shield_main() {
        return rgb_car_in_city_shield_main;
    }

    public static int getRgb_car_in_city_shield_back() {
        return rgb_car_in_city_shield_back;
    }

    public static int getRgb_car_in_city_shield_thm() {
        return rgb_car_in_city_shield_thm;
    }

    public static int getRgb_car_in_city_shield_thp() {
        return rgb_car_in_city_shield_thp;
    }

    public static int getRgb_car_in_garage_health_main() {
        return rgb_car_in_garage_health_main;
    }

    public static int getRgb_car_in_garage_health_back1() {
        return rgb_car_in_garage_health_back1;
    }

    public static int getRgb_car_in_garage_health_back2() {
        return rgb_car_in_garage_health_back2;
    }

    public static int getRgb_car_in_garage_health_thm() {
        return rgb_car_in_garage_health_thm;
    }

    public static int getRgb_car_in_garage_health_thp() {
        return rgb_car_in_garage_health_thp;
    }

    public static int getRgb_car_in_garage_shield_main() {
        return rgb_car_in_garage_shield_main;
    }

    public static int getRgb_car_in_garage_shield_back1() {
        return rgb_car_in_garage_shield_back1;
    }

    public static int getRgb_car_in_garage_shield_back2() {
        return rgb_car_in_garage_shield_back2;
    }

    public static int getRgb_car_in_garage_shield_thm() {
        return rgb_car_in_garage_shield_thm;
    }

    public static int getRgb_car_in_garage_shield_thp() {
        return rgb_car_in_garage_shield_thp;
    }

    public static int getRgb_car_in_city_state_main() {
        return rgb_car_in_city_state_main;
    }

    public static int getRgb_car_in_city_state_back() {
        return rgb_car_in_city_state_back;
    }

    public static int getRgb_car_in_city_state_thm() {
        return rgb_car_in_city_state_thm;
    }

    public static int getRgb_car_in_city_state_thp() {
        return rgb_car_in_city_state_thp;
    }

    public static int getRgb_car_in_city_healbox_main() {
        return rgb_car_in_city_healbox_main;
    }

    public static int getRgb_car_in_city_healbox_back() {
        return rgb_car_in_city_healbox_back;
    }

    public static int getRgb_car_in_city_healbox_thm() {
        return rgb_car_in_city_healbox_thm;
    }

    public static int getRgb_car_in_city_healbox_thp() {
        return rgb_car_in_city_healbox_thp;
    }

    public static int getRgb_car_in_city_timebox1_main() {
        return rgb_car_in_city_timebox1_main;
    }

    public static int getRgb_car_in_city_timebox1_back() {
        return rgb_car_in_city_timebox1_back;
    }

    public static int getRgb_car_in_city_timebox1_thm() {
        return rgb_car_in_city_timebox1_thm;
    }

    public static int getRgb_car_in_city_timebox1_thp() {
        return rgb_car_in_city_timebox1_thp;
    }

    public static int getRgb_car_in_city_timebox2_main() {
        return rgb_car_in_city_timebox2_main;
    }

    public static int getRgb_car_in_city_timebox2_back() {
        return rgb_car_in_city_timebox2_back;
    }

    public static int getRgb_car_in_city_timebox2_thm() {
        return rgb_car_in_city_timebox2_thm;
    }

    public static int getRgb_car_in_city_timebox2_thp() {
        return rgb_car_in_city_timebox2_thp;
    }

    public static int getRgb_car_in_city_statebox1_main() {
        return rgb_car_in_city_statebox1_main;
    }

    public static int getRgb_car_in_city_statebox1_back() {
        return rgb_car_in_city_statebox1_back;
    }

    public static int getRgb_car_in_city_statebox1_thm() {
        return rgb_car_in_city_statebox1_thm;
    }

    public static int getRgb_car_in_city_statebox1_thp() {
        return rgb_car_in_city_statebox1_thp;
    }

    public static int getRgb_car_in_city_statebox2_main() {
        return rgb_car_in_city_statebox2_main;
    }

    public static int getRgb_car_in_city_statebox2_back() {
        return rgb_car_in_city_statebox2_back;
    }

    public static int getRgb_car_in_city_statebox2_thm() {
        return rgb_car_in_city_statebox2_thm;
    }

    public static int getRgb_car_in_city_statebox2_thp() {
        return rgb_car_in_city_statebox2_thp;
    }

    public static int getRgb_car_in_city_statebox3_main() {
        return rgb_car_in_city_statebox3_main;
    }

    public static int getRgb_car_in_city_statebox3_back() {
        return rgb_car_in_city_statebox3_back;
    }

    public static int getRgb_car_in_city_statebox3_thm() {
        return rgb_car_in_city_statebox3_thm;
    }

    public static int getRgb_car_in_city_statebox3_thp() {
        return rgb_car_in_city_statebox3_thp;
    }

    public static int getRgb_box_info_city_back() {
        return rgb_box_info_city_back;
    }

    public static float getCut_box_info_city_x1() {
        return cut_box_info_city_x1;
    }

    public static float getCut_box_info_city_x2() {
        return cut_box_info_city_x2;
    }

    public static float getCut_box_info_city_y1() {
        return cut_box_info_city_y1;
    }

    public static float getCut_box_info_city_y2() {
        return cut_box_info_city_y2;
    }

    public static float getCut_box_info_car_x1() {
        return cut_box_info_car_x1;
    }

    public static float getCut_box_info_car_x2() {
        return cut_box_info_car_x2;
    }

    public static float getCut_box_info_car_y1() {
        return cut_box_info_car_y1;
    }

    public static float getCut_box_info_car_y2() {
        return cut_box_info_car_y2;
    }

    public static float getCut_box_info_garage_x1() {
        return cut_box_info_garage_x1;
    }

    public static float getCut_box_info_garage_x2() {
        return cut_box_info_garage_x2;
    }

    public static float getCut_box_info_garage_y1() {
        return cut_box_info_garage_y1;
    }

    public static float getCut_box_info_garage_y2() {
        return cut_box_info_garage_y2;
    }

    public static float getCut_box_back_x1() {
        return cut_box_back_x1;
    }

    public static float getCut_box_back_x2() {
        return cut_box_back_x2;
    }

    public static float getCut_box_back_y1() {
        return cut_box_back_y1;
    }

    public static float getCut_box_back_y2() {
        return cut_box_back_y2;
    }

    public static float getCut_city_x1() {
        return cut_city_x1;
    }

    public static float getCut_city_x2() {
        return cut_city_x2;
    }

    public static float getCut_city_y1() {
        return cut_city_y1;
    }

    public static float getCut_city_y2() {
        return cut_city_y2;
    }

    public static float getCut_total_time_x1() {
        return cut_total_time_x1;
    }

    public static float getCut_total_time_x2() {
        return cut_total_time_x2;
    }

    public static float getCut_total_time_y1() {
        return cut_total_time_y1;
    }

    public static float getCut_total_time_y2() {
        return cut_total_time_y2;
    }

    public static float getCut_early_win_x1() {
        return cut_early_win_x1;
    }

    public static float getCut_early_win_x2() {
        return cut_early_win_x2;
    }

    public static float getCut_early_win_y1() {
        return cut_early_win_y1;
    }

    public static float getCut_early_win_y2() {
        return cut_early_win_y2;
    }

    public static float getCut_points_and_increase_our_x1() {
        return cut_points_and_increase_our_x1;
    }

    public static float getCut_points_and_increase_our_x2() {
        return cut_points_and_increase_our_x2;
    }

    public static float getCut_points_and_increase_our_y1() {
        return cut_points_and_increase_our_y1;
    }

    public static float getCut_points_and_increase_our_y2() {
        return cut_points_and_increase_our_y2;
    }

    public static float getCut_team_name_our_x1() {
        return cut_team_name_our_x1;
    }

    public static float getCut_team_name_our_x2() {
        return cut_team_name_our_x2;
    }

    public static float getCut_team_name_our_y1() {
        return cut_team_name_our_y1;
    }

    public static float getCut_team_name_our_y2() {
        return cut_team_name_our_y2;
    }

    public static float getCut_points_and_increase_enemy_x1() {
        return cut_points_and_increase_enemy_x1;
    }

    public static float getCut_points_and_increase_enemy_x2() {
        return cut_points_and_increase_enemy_x2;
    }

    public static float getCut_points_and_increase_enemy_y1() {
        return cut_points_and_increase_enemy_y1;
    }

    public static float getCut_points_and_increase_enemy_y2() {
        return cut_points_and_increase_enemy_y2;
    }

    public static float getCut_team_name_enemy_x1() {
        return cut_team_name_enemy_x1;
    }

    public static float getCut_team_name_enemy_x2() {
        return cut_team_name_enemy_x2;
    }

    public static float getCut_team_name_enemy_y1() {
        return cut_team_name_enemy_y1;
    }

    public static float getCut_team_name_enemy_y2() {
        return cut_team_name_enemy_y2;
    }

    public static float getCut_blt_x1() {
        return cut_blt_x1;
    }

    public static float getCut_blt_x2() {
        return cut_blt_x2;
    }

    public static float getCut_blt_y1() {
        return cut_blt_y1;
    }

    public static float getCut_blt_y2() {
        return cut_blt_y2;
    }

    public static float getCut_blt_name_x1() {
        return cut_blt_name_x1;
    }

    public static float getCut_blt_name_x2() {
        return cut_blt_name_x2;
    }

    public static float getCut_blt_name_y1() {
        return cut_blt_name_y1;
    }

    public static float getCut_blt_name_y2() {
        return cut_blt_name_y2;
    }

    public static float getCut_blt_progress_x1() {
        return cut_blt_progress_x1;
    }

    public static float getCut_blt_progress_x2() {
        return cut_blt_progress_x2;
    }

    public static float getCut_blt_progress_y1() {
        return cut_blt_progress_y1;
    }

    public static float getCut_blt_progress_y2() {
        return cut_blt_progress_y2;
    }

    public static float getCut_blt_slots_x1() {
        return cut_blt_slots_x1;
    }

    public static float getCut_blt_slots_x2() {
        return cut_blt_slots_x2;
    }

    public static float getCut_blt_slots_y1() {
        return cut_blt_slots_y1;
    }

    public static float getCut_blt_slots_y2() {
        return cut_blt_slots_y2;
    }

    public static float getCut_blc_x1() {
        return cut_blc_x1;
    }

    public static float getCut_blc_x2() {
        return cut_blc_x2;
    }

    public static float getCut_blc_y1() {
        return cut_blc_y1;
    }

    public static float getCut_blc_y2() {
        return cut_blc_y2;
    }

    public static float getCut_blc_name_x1() {
        return cut_blc_name_x1;
    }

    public static float getCut_blc_name_x2() {
        return cut_blc_name_x2;
    }

    public static float getCut_blc_name_y1() {
        return cut_blc_name_y1;
    }

    public static float getCut_blc_name_y2() {
        return cut_blc_name_y2;
    }

    public static float getCut_blc_progress_x1() {
        return cut_blc_progress_x1;
    }

    public static float getCut_blc_progress_x2() {
        return cut_blc_progress_x2;
    }

    public static float getCut_blc_progress_y1() {
        return cut_blc_progress_y1;
    }

    public static float getCut_blc_progress_y2() {
        return cut_blc_progress_y2;
    }

    public static float getCut_blc_slots_x1() {
        return cut_blc_slots_x1;
    }

    public static float getCut_blc_slots_x2() {
        return cut_blc_slots_x2;
    }

    public static float getCut_blc_slots_y1() {
        return cut_blc_slots_y1;
    }

    public static float getCut_blc_slots_y2() {
        return cut_blc_slots_y2;
    }

    public static float getCut_blb_x1() {
        return cut_blb_x1;
    }

    public static float getCut_blb_x2() {
        return cut_blb_x2;
    }

    public static float getCut_blb_y1() {
        return cut_blb_y1;
    }

    public static float getCut_blb_y2() {
        return cut_blb_y2;
    }

    public static float getCut_blb_name_x1() {
        return cut_blb_name_x1;
    }

    public static float getCut_blb_name_x2() {
        return cut_blb_name_x2;
    }

    public static float getCut_blb_name_y1() {
        return cut_blb_name_y1;
    }

    public static float getCut_blb_name_y2() {
        return cut_blb_name_y2;
    }

    public static float getCut_blb_progress_x1() {
        return cut_blb_progress_x1;
    }

    public static float getCut_blb_progress_x2() {
        return cut_blb_progress_x2;
    }

    public static float getCut_blb_progress_y1() {
        return cut_blb_progress_y1;
    }

    public static float getCut_blb_progress_y2() {
        return cut_blb_progress_y2;
    }

    public static float getCut_blb_slots_x1() {
        return cut_blb_slots_x1;
    }

    public static float getCut_blb_slots_x2() {
        return cut_blb_slots_x2;
    }

    public static float getCut_blb_slots_y1() {
        return cut_blb_slots_y1;
    }

    public static float getCut_blb_slots_y2() {
        return cut_blb_slots_y2;
    }

    public static float getCut_brt_x1() {
        return cut_brt_x1;
    }

    public static float getCut_brt_x2() {
        return cut_brt_x2;
    }

    public static float getCut_brt_y1() {
        return cut_brt_y1;
    }

    public static float getCut_brt_y2() {
        return cut_brt_y2;
    }

    public static float getCut_brt_name_x1() {
        return cut_brt_name_x1;
    }

    public static float getCut_brt_name_x2() {
        return cut_brt_name_x2;
    }

    public static float getCut_brt_name_y1() {
        return cut_brt_name_y1;
    }

    public static float getCut_brt_name_y2() {
        return cut_brt_name_y2;
    }

    public static float getCut_brt_progress_x1() {
        return cut_brt_progress_x1;
    }

    public static float getCut_brt_progress_x2() {
        return cut_brt_progress_x2;
    }

    public static float getCut_brt_progress_y1() {
        return cut_brt_progress_y1;
    }

    public static float getCut_brt_progress_y2() {
        return cut_brt_progress_y2;
    }

    public static float getCut_brt_slots_x1() {
        return cut_brt_slots_x1;
    }

    public static float getCut_brt_slots_x2() {
        return cut_brt_slots_x2;
    }

    public static float getCut_brt_slots_y1() {
        return cut_brt_slots_y1;
    }

    public static float getCut_brt_slots_y2() {
        return cut_brt_slots_y2;
    }

    public static float getCut_brc_x1() {
        return cut_brc_x1;
    }

    public static float getCut_brc_x2() {
        return cut_brc_x2;
    }

    public static float getCut_brc_y1() {
        return cut_brc_y1;
    }

    public static float getCut_brc_y2() {
        return cut_brc_y2;
    }

    public static float getCut_brc_name_x1() {
        return cut_brc_name_x1;
    }

    public static float getCut_brc_name_x2() {
        return cut_brc_name_x2;
    }

    public static float getCut_brc_name_y1() {
        return cut_brc_name_y1;
    }

    public static float getCut_brc_name_y2() {
        return cut_brc_name_y2;
    }

    public static float getCut_brc_progress_x1() {
        return cut_brc_progress_x1;
    }

    public static float getCut_brc_progress_x2() {
        return cut_brc_progress_x2;
    }

    public static float getCut_brc_progress_y1() {
        return cut_brc_progress_y1;
    }

    public static float getCut_brc_progress_y2() {
        return cut_brc_progress_y2;
    }

    public static float getCut_brc_slots_x1() {
        return cut_brc_slots_x1;
    }

    public static float getCut_brc_slots_x2() {
        return cut_brc_slots_x2;
    }

    public static float getCut_brc_slots_y1() {
        return cut_brc_slots_y1;
    }

    public static float getCut_brc_slots_y2() {
        return cut_brc_slots_y2;
    }

    public static float getCut_brb_x1() {
        return cut_brb_x1;
    }

    public static float getCut_brb_x2() {
        return cut_brb_x2;
    }

    public static float getCut_brb_y1() {
        return cut_brb_y1;
    }

    public static float getCut_brb_y2() {
        return cut_brb_y2;
    }

    public static float getCut_brb_name_x1() {
        return cut_brb_name_x1;
    }

    public static float getCut_brb_name_x2() {
        return cut_brb_name_x2;
    }

    public static float getCut_brb_name_y1() {
        return cut_brb_name_y1;
    }

    public static float getCut_brb_name_y2() {
        return cut_brb_name_y2;
    }

    public static float getCut_brb_progress_x1() {
        return cut_brb_progress_x1;
    }

    public static float getCut_brb_progress_x2() {
        return cut_brb_progress_x2;
    }

    public static float getCut_brb_progress_y1() {
        return cut_brb_progress_y1;
    }

    public static float getCut_brb_progress_y2() {
        return cut_brb_progress_y2;
    }

    public static float getCut_brb_slots_x1() {
        return cut_brb_slots_x1;
    }

    public static float getCut_brb_slots_x2() {
        return cut_brb_slots_x2;
    }

    public static float getCut_brb_slots_y1() {
        return cut_brb_slots_y1;
    }

    public static float getCut_brb_slots_y2() {
        return cut_brb_slots_y2;
    }

    public static float getCut_car_in_city_box1_x1() {
        return cut_car_in_city_box1_x1;
    }

    public static float getCut_car_in_city_box1_x2() {
        return cut_car_in_city_box1_x2;
    }

    public static float getCut_car_in_city_box1_y1() {
        return cut_car_in_city_box1_y1;
    }

    public static float getCut_car_in_city_box1_y2() {
        return cut_car_in_city_box1_y2;
    }

    public static float getCut_car_in_city_box2_x1() {
        return cut_car_in_city_box2_x1;
    }

    public static float getCut_car_in_city_box2_x2() {
        return cut_car_in_city_box2_x2;
    }

    public static float getCut_car_in_city_box2_y1() {
        return cut_car_in_city_box2_y1;
    }

    public static float getCut_car_in_city_box2_y2() {
        return cut_car_in_city_box2_y2;
    }

    public static float getCut_car_in_city_info_x1() {
        return cut_car_in_city_info_x1;
    }

    public static float getCut_car_in_city_info_x2() {
        return cut_car_in_city_info_x2;
    }

    public static float getCut_car_in_city_info_y1() {
        return cut_car_in_city_info_y1;
    }

    public static float getCut_car_in_city_info_y2() {
        return cut_car_in_city_info_y2;
    }

    public static float getCut_car_in_city_building_x1() {
        return cut_car_in_city_building_x1;
    }

    public static float getCut_car_in_city_building_x2() {
        return cut_car_in_city_building_x2;
    }

    public static float getCut_car_in_city_building_y1() {
        return cut_car_in_city_building_y1;
    }

    public static float getCut_car_in_city_building_y2() {
        return cut_car_in_city_building_y2;
    }

    public static float getCut_car_in_city_picture_x1() {
        return cut_car_in_city_picture_x1;
    }

    public static float getCut_car_in_city_picture_x2() {
        return cut_car_in_city_picture_x2;
    }

    public static float getCut_car_in_city_picture_y1() {
        return cut_car_in_city_picture_y1;
    }

    public static float getCut_car_in_city_picture_y2() {
        return cut_car_in_city_picture_y2;
    }

    public static float getCut_car_in_garage_picture_x1() {
        return cut_car_in_garage_picture_x1;
    }

    public static float getCut_car_in_garage_picture_x2() {
        return cut_car_in_garage_picture_x2;
    }

    public static float getCut_car_in_garage_picture_y1() {
        return cut_car_in_garage_picture_y1;
    }

    public static float getCut_car_in_garage_picture_y2() {
        return cut_car_in_garage_picture_y2;
    }

    public static float getCut_car_in_city_slot1_x1() {
        return cut_car_in_city_slot1_x1;
    }

    public static float getCut_car_in_city_slot1_x2() {
        return cut_car_in_city_slot1_x2;
    }

    public static float getCut_car_in_city_slot1_y1() {
        return cut_car_in_city_slot1_y1;
    }

    public static float getCut_car_in_city_slot1_y2() {
        return cut_car_in_city_slot1_y2;
    }

    public static float getCut_car_in_city_slot2_x1() {
        return cut_car_in_city_slot2_x1;
    }

    public static float getCut_car_in_city_slot2_x2() {
        return cut_car_in_city_slot2_x2;
    }

    public static float getCut_car_in_city_slot2_y1() {
        return cut_car_in_city_slot2_y1;
    }

    public static float getCut_car_in_city_slot2_y2() {
        return cut_car_in_city_slot2_y2;
    }

    public static float getCut_car_in_city_slot3_x1() {
        return cut_car_in_city_slot3_x1;
    }

    public static float getCut_car_in_city_slot3_x2() {
        return cut_car_in_city_slot3_x2;
    }

    public static float getCut_car_in_city_slot3_y1() {
        return cut_car_in_city_slot3_y1;
    }

    public static float getCut_car_in_city_slot3_y2() {
        return cut_car_in_city_slot3_y2;
    }

    public static float getCut_car_in_garage_slot1_x1() {
        return cut_car_in_garage_slot1_x1;
    }

    public static float getCut_car_in_garage_slot1_x2() {
        return cut_car_in_garage_slot1_x2;
    }

    public static float getCut_car_in_garage_slot1_y1() {
        return cut_car_in_garage_slot1_y1;
    }

    public static float getCut_car_in_garage_slot1_y2() {
        return cut_car_in_garage_slot1_y2;
    }

    public static float getCut_car_in_garage_slot2_x1() {
        return cut_car_in_garage_slot2_x1;
    }

    public static float getCut_car_in_garage_slot2_x2() {
        return cut_car_in_garage_slot2_x2;
    }

    public static float getCut_car_in_garage_slot2_y1() {
        return cut_car_in_garage_slot2_y1;
    }

    public static float getCut_car_in_garage_slot2_y2() {
        return cut_car_in_garage_slot2_y2;
    }

    public static float getCut_car_in_garage_slot3_x1() {
        return cut_car_in_garage_slot3_x1;
    }

    public static float getCut_car_in_garage_slot3_x2() {
        return cut_car_in_garage_slot3_x2;
    }

    public static float getCut_car_in_garage_slot3_y1() {
        return cut_car_in_garage_slot3_y1;
    }

    public static float getCut_car_in_garage_slot3_y2() {
        return cut_car_in_garage_slot3_y2;
    }

    public static float getCut_car_in_city_health_x1() {
        return cut_car_in_city_health_x1;
    }

    public static float getCut_car_in_city_health_x2() {
        return cut_car_in_city_health_x2;
    }

    public static float getCut_car_in_city_health_y1() {
        return cut_car_in_city_health_y1;
    }

    public static float getCut_car_in_city_health_y2() {
        return cut_car_in_city_health_y2;
    }

    public static float getCut_car_in_city_shield_x1() {
        return cut_car_in_city_shield_x1;
    }

    public static float getCut_car_in_city_shield_x2() {
        return cut_car_in_city_shield_x2;
    }

    public static float getCut_car_in_city_shield_y1() {
        return cut_car_in_city_shield_y1;
    }

    public static float getCut_car_in_city_shield_y2() {
        return cut_car_in_city_shield_y2;
    }

    public static float getCut_car_in_garage_health_x1() {
        return cut_car_in_garage_health_x1;
    }

    public static float getCut_car_in_garage_health_x2() {
        return cut_car_in_garage_health_x2;
    }

    public static float getCut_car_in_garage_health_y1() {
        return cut_car_in_garage_health_y1;
    }

    public static float getCut_car_in_garage_health_y2() {
        return cut_car_in_garage_health_y2;
    }

    public static float getCut_car_in_garage_shield_x1() {
        return cut_car_in_garage_shield_x1;
    }

    public static float getCut_car_in_garage_shield_x2() {
        return cut_car_in_garage_shield_x2;
    }

    public static float getCut_car_in_garage_shield_y1() {
        return cut_car_in_garage_shield_y1;
    }

    public static float getCut_car_in_garage_shield_y2() {
        return cut_car_in_garage_shield_y2;
    }

    public static float getCut_car_in_city_state_x1() {
        return cut_car_in_city_state_x1;
    }

    public static float getCut_car_in_city_state_x2() {
        return cut_car_in_city_state_x2;
    }

    public static float getCut_car_in_city_state_y1() {
        return cut_car_in_city_state_y1;
    }

    public static float getCut_car_in_city_state_y2() {
        return cut_car_in_city_state_y2;
    }

    public static float getCut_car_in_city_healbox_x1() {
        return cut_car_in_city_healbox_x1;
    }

    public static float getCut_car_in_city_healbox_x2() {
        return cut_car_in_city_healbox_x2;
    }

    public static float getCut_car_in_city_healbox_y1() {
        return cut_car_in_city_healbox_y1;
    }

    public static float getCut_car_in_city_healbox_y2() {
        return cut_car_in_city_healbox_y2;
    }

    public static float getCut_car_in_city_timebox1_x1() {
        return cut_car_in_city_timebox1_x1;
    }

    public static float getCut_car_in_city_timebox1_x2() {
        return cut_car_in_city_timebox1_x2;
    }

    public static float getCut_car_in_city_timebox1_y1() {
        return cut_car_in_city_timebox1_y1;
    }

    public static float getCut_car_in_city_timebox1_y2() {
        return cut_car_in_city_timebox1_y2;
    }

    public static float getCut_car_in_city_timebox2_x1() {
        return cut_car_in_city_timebox2_x1;
    }

    public static float getCut_car_in_city_timebox2_x2() {
        return cut_car_in_city_timebox2_x2;
    }

    public static float getCut_car_in_city_timebox2_y1() {
        return cut_car_in_city_timebox2_y1;
    }

    public static float getCut_car_in_city_timebox2_y2() {
        return cut_car_in_city_timebox2_y2;
    }

    public static float getCut_car_in_city_statebox1_x1() {
        return cut_car_in_city_statebox1_x1;
    }

    public static float getCut_car_in_city_statebox1_x2() {
        return cut_car_in_city_statebox1_x2;
    }

    public static float getCut_car_in_city_statebox1_y1() {
        return cut_car_in_city_statebox1_y1;
    }

    public static float getCut_car_in_city_statebox1_y2() {
        return cut_car_in_city_statebox1_y2;
    }

    public static float getCut_car_in_city_statebox2_x1() {
        return cut_car_in_city_statebox2_x1;
    }

    public static float getCut_car_in_city_statebox2_x2() {
        return cut_car_in_city_statebox2_x2;
    }

    public static float getCut_car_in_city_statebox2_y1() {
        return cut_car_in_city_statebox2_y1;
    }

    public static float getCut_car_in_city_statebox2_y2() {
        return cut_car_in_city_statebox2_y2;
    }

    public static float getCut_car_in_city_statebox3_x1() {
        return cut_car_in_city_statebox3_x1;
    }

    public static float getCut_car_in_city_statebox3_x2() {
        return cut_car_in_city_statebox3_x2;
    }

    public static float getCut_car_in_city_statebox3_y1() {
        return cut_car_in_city_statebox3_y1;
    }

    public static float getCut_car_in_city_statebox3_y2() {
        return cut_car_in_city_statebox3_y2;
    }

    public static int getRgb_box_info_car_back() {
        return rgb_box_info_car_back;
    }

    public static int getRgb_city_main() {
        return rgb_city_main;
    }

    public static int getRgb_city_back() {
        return rgb_city_back;
    }

    public static int getRgb_city_thm() {
        return rgb_city_thm;
    }

    public static int getRgb_city_thp() {
        return rgb_city_thp;
    }

    public static int getRgb_total_time_main() {
        return rgb_total_time_main;
    }

    public static int getRgb_total_time_back1() {
        return rgb_total_time_back1;
    }

    public static int getRgb_total_time_back2() {
        return rgb_total_time_back2;
    }
    public static int getRgb_total_time_thm() {
        return rgb_total_time_thm;
    }

    public static int getRgb_total_time_thp() {
        return rgb_total_time_thp;
    }

    public static int getRgb_early_win_main() {
        return rgb_early_win_main;
    }

    public static int getRgb_early_win_back() {
        return rgb_early_win_back;
    }

    public static int getRgb_early_win_thm() {
        return rgb_early_win_thm;
    }

    public static int getRgb_early_win_thp() {
        return rgb_early_win_thp;
    }

    public static int getRgb_points_our_main() {
        return rgb_points_our_main;
    }

    public static int getRgb_points_our_back1() {
        return rgb_points_our_back1;
    }
    public static int getRgb_points_our_back2() {
        return rgb_points_our_back2;
    }
    public static int getRgb_points_our_thm() {
        return rgb_points_our_thm;
    }

    public static int getRgb_points_our_thp() {
        return rgb_points_our_thp;
    }

    public static int getRgb_increase_our_main() {
        return rgb_increase_our_main;
    }

    public static int getRgb_increase_our_back() {
        return rgb_increase_our_back;
    }

    public static int getRgb_increase_our_thm() {
        return rgb_increase_our_thm;
    }

    public static int getRgb_increase_our_thp() {
        return rgb_increase_our_thp;
    }

    public static int getRgb_team_name_our_main() {
        return rgb_team_name_our_main;
    }

    public static int getRgb_team_name_our_back() {
        return rgb_team_name_our_back;
    }

    public static int getRgb_team_name_our_thm() {
        return rgb_team_name_our_thm;
    }

    public static int getRgb_team_name_our_thp() {
        return rgb_team_name_our_thp;
    }

    public static int getRgb_points_enemy_main() {
        return rgb_points_enemy_main;
    }

    public static int getRgb_points_enemy_back1() {
        return rgb_points_enemy_back1;
    }

    public static int getRgb_points_enemy_back2() {
        return rgb_points_enemy_back2;
    }

    public static int getRgb_points_enemy_thm() {
        return rgb_points_enemy_thm;
    }

    public static int getRgb_points_enemy_thp() {
        return rgb_points_enemy_thp;
    }

    public static int getRgb_increase_enemy_main() {
        return rgb_increase_enemy_main;
    }

    public static int getRgb_increase_enemy_back() {
        return rgb_increase_enemy_back;
    }

    public static int getRgb_increase_enemy_thm() {
        return rgb_increase_enemy_thm;
    }

    public static int getRgb_increase_enemy_thp() {
        return rgb_increase_enemy_thp;
    }

    public static int getRgb_team_name_enemy_main() {
        return rgb_team_name_enemy_main;
    }

    public static int getRgb_team_name_enemy_back() {
        return rgb_team_name_enemy_back;
    }

    public static int getRgb_team_name_enemy_thm() {
        return rgb_team_name_enemy_thm;
    }

    public static int getRgb_team_name_enemy_thp() {
        return rgb_team_name_enemy_thp;
    }

    public static int getRgb_bxx_main() {
        return rgb_bxx_main;
    }

    public static int getRgb_bxx_back() {
        return rgb_bxx_back;
    }

    public static int getRgb_bxx_thm() {
        return rgb_bxx_thm;
    }

    public static int getRgb_bxx_thp() {
        return rgb_bxx_thp;
    }
}
