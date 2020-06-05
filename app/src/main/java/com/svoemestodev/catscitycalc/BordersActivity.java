package com.svoemestodev.catscitycalc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BordersActivity extends AppCompatActivity {

    public EditText etX1;
    public EditText etX2;
    public EditText etY1;
    public EditText etY2;
    public ImageView ivPicture;
    public Button btFreq;
    public ListView lvFreq;
    public static float cut_x1;
    public static float cut_x2;
    public static float cut_y1;
    public static float cut_y2;

    public static ObservableString areaName = new ObservableString();

    public static CityCalc mainCityCalc;

    public static Bitmap fullBitmap;

    public static final String PREF_BORDERS_AREA = "pref_borders_area";

    public static Map<String, myArea> mapAreas = new HashMap<>();

    public static List<ColorFrequency> colorFrequencyList;

    private void initMap() {
        mapAreas = new HashMap<>();
        String key;

        key = getString(R.string.borders_box_info); mapAreas.put(key, new myArea(key, Area.BOX_INFO, 
                getString(R.string.pref_cut_box_info_x1), getString(R.string.pref_cut_box_info_x2), getString(R.string.pref_cut_box_info_y1), getString(R.string.pref_cut_box_info_y2),
                getString(R.string.def_cut_box_info_x1), getString(R.string.def_cut_box_info_x2), getString(R.string.def_cut_box_info_y1), getString(R.string.def_cut_box_info_y2)));
        
        key = getString(R.string.borders_car_info); mapAreas.put(key, new myArea(key, Area.CAR_INFO, 
                getString(R.string.pref_cut_car_info_x1), getString(R.string.pref_cut_car_info_x2), getString(R.string.pref_cut_car_info_y1), getString(R.string.pref_cut_car_info_y2),
                getString(R.string.def_cut_car_info_x1), getString(R.string.def_cut_car_info_x2), getString(R.string.def_cut_car_info_y1), getString(R.string.def_cut_car_info_y2)));

        key = getString(R.string.borders_car_picture); mapAreas.put(key, new myArea(key, Area.CAR_PICTURE,
                getString(R.string.pref_cut_car_picture_x1), getString(R.string.pref_cut_car_picture_x2), getString(R.string.pref_cut_car_picture_y1), getString(R.string.pref_cut_car_picture_y2),
                getString(R.string.def_cut_car_picture_x1), getString(R.string.def_cut_car_picture_x2), getString(R.string.def_cut_car_picture_y1), getString(R.string.def_cut_car_picture_y2)));

        key = getString(R.string.borders_car_box); mapAreas.put(key, new myArea(key, Area.CAR_BOX,
                getString(R.string.pref_cut_car_box_x1), getString(R.string.pref_cut_car_box_x2), getString(R.string.pref_cut_car_box_y1), getString(R.string.pref_cut_car_box_y2),
                getString(R.string.def_cut_car_box_x1), getString(R.string.def_cut_car_box_x2), getString(R.string.def_cut_car_box_y1), getString(R.string.def_cut_car_box_y2)));

        key = getString(R.string.borders_car_slot1); mapAreas.put(key, new myArea(key, Area.CAR_SLOT1,
                getString(R.string.pref_cut_car_slot1_x1), getString(R.string.pref_cut_car_slot1_x2), getString(R.string.pref_cut_car_slot1_y1), getString(R.string.pref_cut_car_slot1_y2),
                getString(R.string.def_cut_car_slot1_x1), getString(R.string.def_cut_car_slot1_x2), getString(R.string.def_cut_car_slot1_y1), getString(R.string.def_cut_car_slot1_y2)));

        key = getString(R.string.borders_car_slot2); mapAreas.put(key, new myArea(key, Area.CAR_SLOT2,
                getString(R.string.pref_cut_car_slot2_x1), getString(R.string.pref_cut_car_slot2_x2), getString(R.string.pref_cut_car_slot2_y1), getString(R.string.pref_cut_car_slot2_y2),
                getString(R.string.def_cut_car_slot2_x1), getString(R.string.def_cut_car_slot2_x2), getString(R.string.def_cut_car_slot2_y1), getString(R.string.def_cut_car_slot2_y2)));

        key = getString(R.string.borders_car_slot3); mapAreas.put(key, new myArea(key, Area.CAR_SLOT3,
                getString(R.string.pref_cut_car_slot3_x1), getString(R.string.pref_cut_car_slot3_x2), getString(R.string.pref_cut_car_slot3_y1), getString(R.string.pref_cut_car_slot3_y2),
                getString(R.string.def_cut_car_slot3_x1), getString(R.string.def_cut_car_slot3_x2), getString(R.string.def_cut_car_slot3_y1), getString(R.string.def_cut_car_slot3_y2)));

        key = getString(R.string.borders_car_health); mapAreas.put(key, new myArea(key, Area.CAR_HEALTH,
                getString(R.string.pref_cut_car_health_x1), getString(R.string.pref_cut_car_health_x2), getString(R.string.pref_cut_car_health_y1), getString(R.string.pref_cut_car_health_y2),
                getString(R.string.def_cut_car_health_x1), getString(R.string.def_cut_car_health_x2), getString(R.string.def_cut_car_health_y1), getString(R.string.def_cut_car_health_y2)));

        key = getString(R.string.borders_car_shield); mapAreas.put(key, new myArea(key, Area.CAR_SHIELD,
                getString(R.string.pref_cut_car_shield_x1), getString(R.string.pref_cut_car_shield_x2), getString(R.string.pref_cut_car_shield_y1), getString(R.string.pref_cut_car_shield_y2),
                getString(R.string.def_cut_car_shield_x1), getString(R.string.def_cut_car_shield_x2), getString(R.string.def_cut_car_shield_y1), getString(R.string.def_cut_car_shield_y2)));

        key = getString(R.string.borders_car_state); mapAreas.put(key, new myArea(key, Area.CAR_STATE,
                getString(R.string.pref_cut_car_state_x1), getString(R.string.pref_cut_car_state_x2), getString(R.string.pref_cut_car_state_y1), getString(R.string.pref_cut_car_state_y2),
                getString(R.string.def_cut_car_state_x1), getString(R.string.def_cut_car_state_x2), getString(R.string.def_cut_car_state_y1), getString(R.string.def_cut_car_state_y2)));

        key = getString(R.string.borders_car_healbox); mapAreas.put(key, new myArea(key, Area.CAR_HEALBOX,
                getString(R.string.pref_cut_car_healbox_x1), getString(R.string.pref_cut_car_healbox_x2), getString(R.string.pref_cut_car_healbox_y1), getString(R.string.pref_cut_car_healbox_y2),
                getString(R.string.def_cut_car_healbox_x1), getString(R.string.def_cut_car_healbox_x2), getString(R.string.def_cut_car_healbox_y1), getString(R.string.def_cut_car_healbox_y2)));

        key = getString(R.string.borders_car_timebox); mapAreas.put(key, new myArea(key, Area.CAR_TIMEBOX,
                getString(R.string.pref_cut_car_timebox_x1), getString(R.string.pref_cut_car_timebox_x2), getString(R.string.pref_cut_car_timebox_y1), getString(R.string.pref_cut_car_timebox_y2),
                getString(R.string.def_cut_car_timebox_x1), getString(R.string.def_cut_car_timebox_x2), getString(R.string.def_cut_car_timebox_y1), getString(R.string.def_cut_car_timebox_y2)));



        key = getString(R.string.borders_city); mapAreas.put(key, new myArea(key, Area.CITY, 
                getString(R.string.pref_cut_city_x1), getString(R.string.pref_cut_city_x2), getString(R.string.pref_cut_city_y1), getString(R.string.pref_cut_city_y2),
                getString(R.string.def_cut_city_x1), getString(R.string.def_cut_city_x2), getString(R.string.def_cut_city_y1), getString(R.string.def_cut_city_y2)));
        
        key = getString(R.string.borders_time); mapAreas.put(key, new myArea(key, Area.TOTAL_TIME, 
                getString(R.string.pref_cut_total_time_x1), getString(R.string.pref_cut_total_time_x2), getString(R.string.pref_cut_total_time_y1), getString(R.string.pref_cut_total_time_y2),
                getString(R.string.def_cut_total_time_x1), getString(R.string.def_cut_total_time_x2), getString(R.string.def_cut_total_time_y1), getString(R.string.def_cut_total_time_y2)));
        
        key = getString(R.string.borders_scores_to_early_win); mapAreas.put(key, new myArea(key, Area.EARLY_WIN, 
                getString(R.string.pref_cut_early_win_x1), getString(R.string.pref_cut_early_win_x2), getString(R.string.pref_cut_early_win_y1), getString(R.string.pref_cut_early_win_y2),
                getString(R.string.def_cut_early_win_x1), getString(R.string.def_cut_early_win_x2), getString(R.string.def_cut_early_win_y1), getString(R.string.def_cut_early_win_y2)));
        
        key = getString(R.string.borders_our_scores); mapAreas.put(key, new myArea(key, Area.POINTS_AND_INCREASE_OUR, 
                getString(R.string.pref_cut_points_and_increase_our_x1), getString(R.string.pref_cut_points_and_increase_our_x2), getString(R.string.pref_cut_points_and_increase_our_y1), getString(R.string.pref_cut_points_and_increase_our_y2),
                getString(R.string.def_cut_points_and_increase_our_x1), getString(R.string.def_cut_points_and_increase_our_x2), getString(R.string.def_cut_points_and_increase_our_y1), getString(R.string.def_cut_points_and_increase_our_y2)));
        
        key = getString(R.string.borders_enemy_scores); mapAreas.put(key, new myArea(key, Area.POINTS_AND_INCREASE_ENEMY, 
                getString(R.string.pref_cut_points_and_increase_enemy_x1), getString(R.string.pref_cut_points_and_increase_enemy_x2), getString(R.string.pref_cut_points_and_increase_enemy_y1), getString(R.string.pref_cut_points_and_increase_enemy_y2),
                getString(R.string.def_cut_points_and_increase_enemy_x1), getString(R.string.def_cut_points_and_increase_enemy_x2), getString(R.string.def_cut_points_and_increase_enemy_y1), getString(R.string.def_cut_points_and_increase_enemy_y2)));
        
        key = getString(R.string.borders_enemy_team_name); mapAreas.put(key, new myArea(key, Area.TEAM_NAME_ENEMY, 
                getString(R.string.pref_cut_team_name_enemy_x1), getString(R.string.pref_cut_team_name_enemy_x2), getString(R.string.pref_cut_team_name_enemy_y1), getString(R.string.pref_cut_team_name_enemy_y2),
                getString(R.string.def_cut_team_name_enemy_x1), getString(R.string.def_cut_team_name_enemy_x2), getString(R.string.def_cut_team_name_enemy_y1), getString(R.string.def_cut_team_name_enemy_y2)));
        
        key = getString(R.string.borders_our_team_name); mapAreas.put(key, new myArea(key, Area.TEAM_NAME_OUR, 
                getString(R.string.pref_cut_team_name_our_x1), getString(R.string.pref_cut_team_name_our_x2), getString(R.string.pref_cut_team_name_our_y1), getString(R.string.pref_cut_team_name_our_y2),
                getString(R.string.def_cut_team_name_our_x1), getString(R.string.def_cut_team_name_our_x2), getString(R.string.def_cut_team_name_our_y1), getString(R.string.def_cut_team_name_our_y2)));
        
        key = getString(R.string.borders_blt); mapAreas.put(key, new myArea(key, Area.BLT_AREA,
                getString(R.string.pref_cut_blt_x1), getString(R.string.pref_cut_blt_x2), getString(R.string.pref_cut_blt_y1), getString(R.string.pref_cut_blt_y2),
                getString(R.string.def_cut_blt_x1), getString(R.string.def_cut_blt_x2), getString(R.string.def_cut_blt_y1), getString(R.string.def_cut_blt_y2)));
        
        key = getString(R.string.borders_blt_name); mapAreas.put(key, new myArea(key, Area.BLT, 
                getString(R.string.pref_cut_blt_name_x1), getString(R.string.pref_cut_blt_name_x2), getString(R.string.pref_cut_blt_name_y1), getString(R.string.pref_cut_blt_name_y2),
                getString(R.string.def_cut_blt_name_x1), getString(R.string.def_cut_blt_name_x2), getString(R.string.def_cut_blt_name_y1), getString(R.string.def_cut_blt_name_y2)));
        
        key = getString(R.string.borders_blt_increase); mapAreas.put(key, new myArea(key, Area.BLT_POINTS, 
                getString(R.string.pref_cut_blt_increase_x1), getString(R.string.pref_cut_blt_increase_x2), getString(R.string.pref_cut_blt_increase_y1), getString(R.string.pref_cut_blt_increase_y2),
                getString(R.string.def_cut_blt_points_x1), getString(R.string.def_cut_blt_points_x2), getString(R.string.def_cut_blt_points_y1), getString(R.string.def_cut_blt_points_y2)));
        
        key = getString(R.string.borders_blt_slots); mapAreas.put(key, new myArea(key, Area.BLT_SLOTS, 
                getString(R.string.pref_cut_blt_slots_x1), getString(R.string.pref_cut_blt_slots_x2), getString(R.string.pref_cut_blt_slots_y1), getString(R.string.pref_cut_blt_slots_y2),
                getString(R.string.def_cut_blt_slots_x1), getString(R.string.def_cut_blt_slots_x2), getString(R.string.def_cut_blt_slots_y1), getString(R.string.def_cut_blt_slots_y2)));
        
        key = getString(R.string.borders_blt_progress); mapAreas.put(key, new myArea(key, Area.BLT_PROGRESS, 
                getString(R.string.pref_cut_blt_progress_x1), getString(R.string.pref_cut_blt_progress_x2), getString(R.string.pref_cut_blt_progress_y1), getString(R.string.pref_cut_blt_progress_y2),
                getString(R.string.def_cut_blt_progress_x1), getString(R.string.def_cut_blt_progress_x2), getString(R.string.def_cut_blt_progress_y1), getString(R.string.def_cut_blt_progress_y2)));


        key = getString(R.string.borders_blc); mapAreas.put(key, new myArea(key, Area.BLC_AREA,
                getString(R.string.pref_cut_blc_x1), getString(R.string.pref_cut_blc_x2), getString(R.string.pref_cut_blc_y1), getString(R.string.pref_cut_blc_y2),
                getString(R.string.def_cut_blc_x1), getString(R.string.def_cut_blc_x2), getString(R.string.def_cut_blc_y1), getString(R.string.def_cut_blc_y2)));

        key = getString(R.string.borders_blc_name); mapAreas.put(key, new myArea(key, Area.BLC,
                getString(R.string.pref_cut_blc_name_x1), getString(R.string.pref_cut_blc_name_x2), getString(R.string.pref_cut_blc_name_y1), getString(R.string.pref_cut_blc_name_y2),
                getString(R.string.def_cut_blc_name_x1), getString(R.string.def_cut_blc_name_x2), getString(R.string.def_cut_blc_name_y1), getString(R.string.def_cut_blc_name_y2)));

        key = getString(R.string.borders_blc_increase); mapAreas.put(key, new myArea(key, Area.BLC_POINTS,
                getString(R.string.pref_cut_blc_increase_x1), getString(R.string.pref_cut_blc_increase_x2), getString(R.string.pref_cut_blc_increase_y1), getString(R.string.pref_cut_blc_increase_y2),
                getString(R.string.def_cut_blc_points_x1), getString(R.string.def_cut_blc_points_x2), getString(R.string.def_cut_blc_points_y1), getString(R.string.def_cut_blc_points_y2)));

        key = getString(R.string.borders_blc_slots); mapAreas.put(key, new myArea(key, Area.BLC_SLOTS,
                getString(R.string.pref_cut_blc_slots_x1), getString(R.string.pref_cut_blc_slots_x2), getString(R.string.pref_cut_blc_slots_y1), getString(R.string.pref_cut_blc_slots_y2),
                getString(R.string.def_cut_blc_slots_x1), getString(R.string.def_cut_blc_slots_x2), getString(R.string.def_cut_blc_slots_y1), getString(R.string.def_cut_blc_slots_y2)));

        key = getString(R.string.borders_blc_progress); mapAreas.put(key, new myArea(key, Area.BLC_PROGRESS,
                getString(R.string.pref_cut_blc_progress_x1), getString(R.string.pref_cut_blc_progress_x2), getString(R.string.pref_cut_blc_progress_y1), getString(R.string.pref_cut_blc_progress_y2),
                getString(R.string.def_cut_blc_progress_x1), getString(R.string.def_cut_blc_progress_x2), getString(R.string.def_cut_blc_progress_y1), getString(R.string.def_cut_blc_progress_y2)));


        key = getString(R.string.borders_blb); mapAreas.put(key, new myArea(key, Area.BLB_AREA,
                getString(R.string.pref_cut_blb_x1), getString(R.string.pref_cut_blb_x2), getString(R.string.pref_cut_blb_y1), getString(R.string.pref_cut_blb_y2),
                getString(R.string.def_cut_blb_x1), getString(R.string.def_cut_blb_x2), getString(R.string.def_cut_blb_y1), getString(R.string.def_cut_blb_y2)));

        key = getString(R.string.borders_blb_name); mapAreas.put(key, new myArea(key, Area.BLB,
                getString(R.string.pref_cut_blb_name_x1), getString(R.string.pref_cut_blb_name_x2), getString(R.string.pref_cut_blb_name_y1), getString(R.string.pref_cut_blb_name_y2),
                getString(R.string.def_cut_blb_name_x1), getString(R.string.def_cut_blb_name_x2), getString(R.string.def_cut_blb_name_y1), getString(R.string.def_cut_blb_name_y2)));

        key = getString(R.string.borders_blb_increase); mapAreas.put(key, new myArea(key, Area.BLB_POINTS,
                getString(R.string.pref_cut_blb_increase_x1), getString(R.string.pref_cut_blb_increase_x2), getString(R.string.pref_cut_blb_increase_y1), getString(R.string.pref_cut_blb_increase_y2),
                getString(R.string.def_cut_blb_points_x1), getString(R.string.def_cut_blb_points_x2), getString(R.string.def_cut_blb_points_y1), getString(R.string.def_cut_blb_points_y2)));

        key = getString(R.string.borders_blb_slots); mapAreas.put(key, new myArea(key, Area.BLB_SLOTS,
                getString(R.string.pref_cut_blb_slots_x1), getString(R.string.pref_cut_blb_slots_x2), getString(R.string.pref_cut_blb_slots_y1), getString(R.string.pref_cut_blb_slots_y2),
                getString(R.string.def_cut_blb_slots_x1), getString(R.string.def_cut_blb_slots_x2), getString(R.string.def_cut_blb_slots_y1), getString(R.string.def_cut_blb_slots_y2)));

        key = getString(R.string.borders_blb_progress); mapAreas.put(key, new myArea(key, Area.BLB_PROGRESS,
                getString(R.string.pref_cut_blb_progress_x1), getString(R.string.pref_cut_blb_progress_x2), getString(R.string.pref_cut_blb_progress_y1), getString(R.string.pref_cut_blb_progress_y2),
                getString(R.string.def_cut_blb_progress_x1), getString(R.string.def_cut_blb_progress_x2), getString(R.string.def_cut_blb_progress_y1), getString(R.string.def_cut_blb_progress_y2)));


        key = getString(R.string.borders_brt); mapAreas.put(key, new myArea(key, Area.BRT_AREA,
                getString(R.string.pref_cut_brt_x1), getString(R.string.pref_cut_brt_x2), getString(R.string.pref_cut_brt_y1), getString(R.string.pref_cut_brt_y2),
                getString(R.string.def_cut_brt_x1), getString(R.string.def_cut_brt_x2), getString(R.string.def_cut_brt_y1), getString(R.string.def_cut_brt_y2)));

        key = getString(R.string.borders_brt_name); mapAreas.put(key, new myArea(key, Area.BRT,
                getString(R.string.pref_cut_brt_name_x1), getString(R.string.pref_cut_brt_name_x2), getString(R.string.pref_cut_brt_name_y1), getString(R.string.pref_cut_brt_name_y2),
                getString(R.string.def_cut_brt_name_x1), getString(R.string.def_cut_brt_name_x2), getString(R.string.def_cut_brt_name_y1), getString(R.string.def_cut_brt_name_y2)));

        key = getString(R.string.borders_brt_increase); mapAreas.put(key, new myArea(key, Area.BRT_POINTS,
                getString(R.string.pref_cut_brt_increase_x1), getString(R.string.pref_cut_brt_increase_x2), getString(R.string.pref_cut_brt_increase_y1), getString(R.string.pref_cut_brt_increase_y2),
                getString(R.string.def_cut_brt_points_x1), getString(R.string.def_cut_brt_points_x2), getString(R.string.def_cut_brt_points_y1), getString(R.string.def_cut_brt_points_y2)));

        key = getString(R.string.borders_brt_slots); mapAreas.put(key, new myArea(key, Area.BRT_SLOTS,
                getString(R.string.pref_cut_brt_slots_x1), getString(R.string.pref_cut_brt_slots_x2), getString(R.string.pref_cut_brt_slots_y1), getString(R.string.pref_cut_brt_slots_y2),
                getString(R.string.def_cut_brt_slots_x1), getString(R.string.def_cut_brt_slots_x2), getString(R.string.def_cut_brt_slots_y1), getString(R.string.def_cut_brt_slots_y2)));

        key = getString(R.string.borders_brt_progress); mapAreas.put(key, new myArea(key, Area.BRT_PROGRESS,
                getString(R.string.pref_cut_brt_progress_x1), getString(R.string.pref_cut_brt_progress_x2), getString(R.string.pref_cut_brt_progress_y1), getString(R.string.pref_cut_brt_progress_y2),
                getString(R.string.def_cut_brt_progress_x1), getString(R.string.def_cut_brt_progress_x2), getString(R.string.def_cut_brt_progress_y1), getString(R.string.def_cut_brt_progress_y2)));


        key = getString(R.string.borders_brc); mapAreas.put(key, new myArea(key, Area.BRC_AREA,
                getString(R.string.pref_cut_brc_x1), getString(R.string.pref_cut_brc_x2), getString(R.string.pref_cut_brc_y1), getString(R.string.pref_cut_brc_y2),
                getString(R.string.def_cut_brc_x1), getString(R.string.def_cut_brc_x2), getString(R.string.def_cut_brc_y1), getString(R.string.def_cut_brc_y2)));

        key = getString(R.string.borders_brc_name); mapAreas.put(key, new myArea(key, Area.BRC,
                getString(R.string.pref_cut_brc_name_x1), getString(R.string.pref_cut_brc_name_x2), getString(R.string.pref_cut_brc_name_y1), getString(R.string.pref_cut_brc_name_y2),
                getString(R.string.def_cut_brc_name_x1), getString(R.string.def_cut_brc_name_x2), getString(R.string.def_cut_brc_name_y1), getString(R.string.def_cut_brc_name_y2)));

        key = getString(R.string.borders_brc_increase); mapAreas.put(key, new myArea(key, Area.BRC_POINTS,
                getString(R.string.pref_cut_brc_increase_x1), getString(R.string.pref_cut_brc_increase_x2), getString(R.string.pref_cut_brc_increase_y1), getString(R.string.pref_cut_brc_increase_y2),
                getString(R.string.def_cut_brc_points_x1), getString(R.string.def_cut_brc_points_x2), getString(R.string.def_cut_brc_points_y1), getString(R.string.def_cut_brc_points_y2)));

        key = getString(R.string.borders_brc_slots); mapAreas.put(key, new myArea(key, Area.BRC_SLOTS,
                getString(R.string.pref_cut_brc_slots_x1), getString(R.string.pref_cut_brc_slots_x2), getString(R.string.pref_cut_brc_slots_y1), getString(R.string.pref_cut_brc_slots_y2),
                getString(R.string.def_cut_brc_slots_x1), getString(R.string.def_cut_brc_slots_x2), getString(R.string.def_cut_brc_slots_y1), getString(R.string.def_cut_brc_slots_y2)));

        key = getString(R.string.borders_brc_progress); mapAreas.put(key, new myArea(key, Area.BRC_PROGRESS,
                getString(R.string.pref_cut_brc_progress_x1), getString(R.string.pref_cut_brc_progress_x2), getString(R.string.pref_cut_brc_progress_y1), getString(R.string.pref_cut_brc_progress_y2),
                getString(R.string.def_cut_brc_progress_x1), getString(R.string.def_cut_brc_progress_x2), getString(R.string.def_cut_brc_progress_y1), getString(R.string.def_cut_brc_progress_y2)));


        key = getString(R.string.borders_brb); mapAreas.put(key, new myArea(key, Area.BRB_AREA,
                getString(R.string.pref_cut_brb_x1), getString(R.string.pref_cut_brb_x2), getString(R.string.pref_cut_brb_y1), getString(R.string.pref_cut_brb_y2),
                getString(R.string.def_cut_brb_x1), getString(R.string.def_cut_brb_x2), getString(R.string.def_cut_brb_y1), getString(R.string.def_cut_brb_y2)));

        key = getString(R.string.borders_brb_name); mapAreas.put(key, new myArea(key, Area.BRB,
                getString(R.string.pref_cut_brb_name_x1), getString(R.string.pref_cut_brb_name_x2), getString(R.string.pref_cut_brb_name_y1), getString(R.string.pref_cut_brb_name_y2),
                getString(R.string.def_cut_brb_name_x1), getString(R.string.def_cut_brb_name_x2), getString(R.string.def_cut_brb_name_y1), getString(R.string.def_cut_brb_name_y2)));

        key = getString(R.string.borders_brb_increase); mapAreas.put(key, new myArea(key, Area.BRB_POINTS,
                getString(R.string.pref_cut_brb_increase_x1), getString(R.string.pref_cut_brb_increase_x2), getString(R.string.pref_cut_brb_increase_y1), getString(R.string.pref_cut_brb_increase_y2),
                getString(R.string.def_cut_brb_points_x1), getString(R.string.def_cut_brb_points_x2), getString(R.string.def_cut_brb_points_y1), getString(R.string.def_cut_brb_points_y2)));

        key = getString(R.string.borders_brb_slots); mapAreas.put(key, new myArea(key, Area.BRB_SLOTS,
                getString(R.string.pref_cut_brb_slots_x1), getString(R.string.pref_cut_brb_slots_x2), getString(R.string.pref_cut_brb_slots_y1), getString(R.string.pref_cut_brb_slots_y2),
                getString(R.string.def_cut_brb_slots_x1), getString(R.string.def_cut_brb_slots_x2), getString(R.string.def_cut_brb_slots_y1), getString(R.string.def_cut_brb_slots_y2)));

        key = getString(R.string.borders_brb_progress); mapAreas.put(key, new myArea(key, Area.BRB_PROGRESS,
                getString(R.string.pref_cut_brb_progress_x1), getString(R.string.pref_cut_brb_progress_x2), getString(R.string.pref_cut_brb_progress_y1), getString(R.string.pref_cut_brb_progress_y2),
                getString(R.string.def_cut_brb_progress_x1), getString(R.string.def_cut_brb_progress_x2), getString(R.string.def_cut_brb_progress_y1), getString(R.string.def_cut_brb_progress_y2)));

    }

    public void getFrequency(View view) {

        CityCalcArea cityCalcArea = mainCityCalc.mapAreas.get(mapAreas.get(areaName.get()).area);
        colorFrequencyList = PictureProcessor.getFrequencyMap(cityCalcArea.bmpSrc);
        if (colorFrequencyList.size() > 0) {
            lvFreq.setAdapter(new ListColorFrequencyAdapter(this));
        }

    }

    private class ListColorFrequencyAdapter extends ArrayAdapter<ColorFrequency> {

        public ListColorFrequencyAdapter(@NonNull Context context) {
            super(context, R.layout.color_freq_layout, colorFrequencyList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final ColorFrequency item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.color_freq_layout, null);
            }

            TextView cfl_stub = convertView.findViewById(R.id.cfl_stub);
            TextView cfl_color = convertView.findViewById(R.id.cfl_color);
            TextView cfl_freq = convertView.findViewById(R.id.cfl_freq);

            cfl_stub.setTextColor(item.color);
            cfl_color.setText(Long.toHexString(item.color).toUpperCase().substring(10));
            cfl_freq.setText(Utils.convertFloatToStringFormatter5digit(item.frequency));

            return convertView;

        }

    }

    private class myArea {
        String key;
        Area area;
        String prefX1;
        String prefX2;
        String prefY1;
        String prefY2;
        String defX1;
        String defX2;
        String defY1;
        String defY2;
        public myArea(String key, Area area, String prefX1, String prefX2, String prefY1, String prefY2, String defX1, String defX2, String defY1, String defY2) {
            this.key = key;
            this.area = area;
            this.prefX1 = prefX1;
            this.prefX2 = prefX2;
            this.prefY1 = prefY1;
            this.prefY2 = prefY2;
            this.defX1 = defX1;
            this.defX2 = defX2;
            this.defY1 = defY1;
            this.defY2 = defY2;
        }
    }
    
    public void loadArea() {

//        cut_x1 = GameActivity.mapFloats.get();

        CityCalcArea cityCalcArea = null;

        cityCalcArea = mainCityCalc.mapAreas.get(mapAreas.get(areaName.get()).area);

        cityCalcArea.doCut();
        cut_x1 = cityCalcArea.x1;
        cut_x2 = cityCalcArea.x2;
        cut_y1 = cityCalcArea.y1;
        cut_y2 = cityCalcArea.y2;
        
        etX1.setText(String.valueOf(cut_x1));
        etX2.setText(String.valueOf(cut_x2));
        etY1.setText(String.valueOf(cut_y1));
        etY2.setText(String.valueOf(cut_y2));
        ivPicture.setImageBitmap(cityCalcArea.bmpSrc);  // выводим битмап игры в контрол

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borders);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        if (findViewById(R.id.frame_borders) != null) {
            if (savedInstanceState != null) return;
            getFragmentManager().beginTransaction().add(R.id.frame_borders, new BordersFragment()).commit();

        }
        PreferenceManager.setDefaultValues(this, R.xml.pref_borders, true);

        etX1 = findViewById(R.id.et_border_x1);
        etX2 = findViewById(R.id.et_border_x2);
        etY1 = findViewById(R.id.et_border_y1);
        etY2 = findViewById(R.id.et_border_y2);
        ivPicture = findViewById(R.id.iv_borders);
        btFreq = findViewById(R.id.bt_freq);
        lvFreq = findViewById(R.id.lv_freq);

        mainCityCalc = new CityCalc(GameActivity.fileLast, GameActivity.calibrateX, GameActivity.calibrateY, GameActivity.context, CityCalcType.BORDERS);

        initMap();
        
        fullBitmap = mainCityCalc.bmpScreenshot;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        areaName.set(sharedPreferences.getString(PREF_BORDERS_AREA, String.valueOf(R.string.pref_bordersAreaName_default_value)));

        areaName.setOnStringChangeListener(new OnStringChangeListener()
        {
            @Override
            public void onStringChanged(String newValue)
            {
                loadArea();
            }
        });

        loadArea();

        etX1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    float value = Float.parseFloat(s.toString());
                    cut_x1 = value;
                    SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    CityCalcArea cityCalcArea = mainCityCalc.mapAreas.get(mapAreas.get(areaName.get()).area);
                    editor.putFloat(mapAreas.get(areaName.get()).prefX1, value);

                    editor.apply();
                    
                    cityCalcArea.x1 = value;
                    cityCalcArea.doCut();
                    ivPicture.setImageBitmap(cityCalcArea.bmpSrc);

                } catch (NumberFormatException e) {
                }

            }
        });

        etX2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    float value = Float.parseFloat(s.toString());
                    cut_x2 = value;

                    SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    CityCalcArea cityCalcArea = mainCityCalc.mapAreas.get(mapAreas.get(areaName.get()).area);
                    editor.putFloat(mapAreas.get(areaName.get()).prefX2, value);

                    editor.apply();

                    cityCalcArea.x2 = value;
                    cityCalcArea.doCut();
                    ivPicture.setImageBitmap(cityCalcArea.bmpSrc);
                    
                } catch (NumberFormatException e) {
                }

            }
        });

        etY1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    float value = Float.parseFloat(s.toString());
                    cut_y1 = value;

                    SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    CityCalcArea cityCalcArea = mainCityCalc.mapAreas.get(mapAreas.get(areaName.get()).area);
                    editor.putFloat(mapAreas.get(areaName.get()).prefY1, value);

                    editor.apply();

                    cityCalcArea.y1 = value;
                    cityCalcArea.doCut();
                    ivPicture.setImageBitmap(cityCalcArea.bmpSrc);
                } catch (NumberFormatException e) {
                }

            }
        });

        etY2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    float value = Float.parseFloat(s.toString());
                    cut_y2 = value;

                    SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    CityCalcArea cityCalcArea = mainCityCalc.mapAreas.get(mapAreas.get(areaName.get()).area);
                    editor.putFloat(mapAreas.get(areaName.get()).prefY2, value);

                    editor.apply();

                    cityCalcArea.y2 = value;
                    cityCalcArea.doCut();
                    ivPicture.setImageBitmap(cityCalcArea.bmpSrc);
                } catch (NumberFormatException e) {
                }

            }
        });

    }

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

    public static Bitmap cutBorders() {
        int widthSource = fullBitmap.getWidth();      // ширина исходной картинки
        int heightSource = fullBitmap.getHeight();   // высота исходной картинки

        int realCalibrateX = (widthSource / 2) > Math.abs(GameActivity.calibrateX) ? GameActivity.calibrateX : 0;
        int realCalibrateY = (heightSource / 2) > Math.abs(GameActivity.calibrateY) ? GameActivity.calibrateY : 0;

        // координаты для вырезания картинки информации об игре
        int x1 = (int) ((double) widthSource / 2 + cut_x1 * heightSource) + realCalibrateX;
        int x2 = (int) ((double) widthSource / 2 + cut_x2 * heightSource) + realCalibrateX;
        int y1 = (int) ((double) heightSource / 2 + cut_y1 * ((double) heightSource / 2)) + realCalibrateY;
        int y2 = (int) ((double) heightSource / 2 + cut_y2 * ((double) heightSource / 2)) + realCalibrateY;

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1 = Math.max(x1, 0); x2 = Math.min(x2, widthSource);
        y1 = Math.max(y1, 0);  y2 = Math.min(y2, heightSource);

        Bitmap croppingBitmap = Bitmap.createBitmap(fullBitmap, x1, y1, x2 - x1, y2 - y1);

        return croppingBitmap;

    }

    public void resetX1(View view) {

        cut_x1 = Float.parseFloat(mapAreas.get(areaName.get()).defX1);
        etX1.setText(String.valueOf(cut_x1));

    }

    public void resetX2(View view) {

        cut_x2 = Float.parseFloat(mapAreas.get(areaName.get()).defX2);
        etX2.setText(String.valueOf(cut_x2));

    }

    public void resetY1(View view) {

        cut_y1 = Float.parseFloat(mapAreas.get(areaName.get()).defY1);
        etY1.setText(String.valueOf(cut_y1));

    }

    public void resetY2(View view) {

        cut_y2 = Float.parseFloat(mapAreas.get(areaName.get()).defY2);
        etY2.setText(String.valueOf(cut_y2));

    }

    public void borderMinus10x1(View view) {

        float diff = -0.010f;
        cut_x1 += diff;
        etX1.setText(String.valueOf(cut_x1));

    }

    public void borderMinus1x1(View view) {

        float diff = -0.001f;
        cut_x1 += diff;
        etX1.setText(String.valueOf(cut_x1));

    }

    public void borderPlus1x1(View view) {

        float diff = +0.001f;
        cut_x1 += diff;
        etX1.setText(String.valueOf(cut_x1));

    }

    public void borderPlus10x1(View view) {

        float diff = +0.010f;
        cut_x1 += diff;
        etX1.setText(String.valueOf(cut_x1));

    }


    public void borderMinus10x2(View view) {

        float diff = -0.010f;
        cut_x2 += diff;
        etX2.setText(String.valueOf(cut_x2));

    }

    public void borderMinus1x2(View view) {

        float diff = -0.001f;
        cut_x2 += diff;
        etX2.setText(String.valueOf(cut_x2));

    }

    public void borderPlus1x2(View view) {

        float diff = +0.001f;
        cut_x2 += diff;
        etX2.setText(String.valueOf(cut_x2));

    }

    public void borderPlus10x2(View view) {

        float diff = +0.010f;
        cut_x2 += diff;
        etX2.setText(String.valueOf(cut_x2));

    }

    public void borderMinus10y1(View view) {

        float diff = -0.010f;
        cut_y1 += diff;
        etY1.setText(String.valueOf(cut_y1));

    }

    public void borderMinus1y1(View view) {

        float diff = -0.001f;
        cut_y1 += diff;
        etY1.setText(String.valueOf(cut_y1));

    }

    public void borderPlus1y1(View view) {

        float diff = +0.001f;
        cut_y1 += diff;
        etY1.setText(String.valueOf(cut_y1));

    }

    public void borderPlus10y1(View view) {

        float diff = +0.010f;
        cut_y1 += diff;
        etY1.setText(String.valueOf(cut_y1));

    }



    public void borderMinus10y2(View view) {

        float diff = -0.010f;
        cut_y2 += diff;
        etY2.setText(String.valueOf(cut_y2));

    }

    public void borderMinus1y2(View view) {

        float diff = -0.001f;
        cut_y2 += diff;
        etY2.setText(String.valueOf(cut_y2));

    }

    public void borderPlus1y2(View view) {

        float diff = +0.001f;
        cut_y2 += diff;
        etY2.setText(String.valueOf(cut_y2));

    }

    public void borderPlus10y2(View view) {

        float diff = +0.010f;
        cut_y2 += diff;
        etY2.setText(String.valueOf(cut_y2));

    }


    public static class BordersFragment extends PreferenceFragment {

        public static final String PREF_BORDERS_AREA = "pref_borders_area";
        private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_borders);

            onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals(PREF_BORDERS_AREA)) {
                        Preference preference = findPreference(key);
                        preference.setSummary(sharedPreferences.getString(key, String.valueOf(R.string.pref_bordersAreaName_default_value)));
                        BordersActivity.areaName.set(sharedPreferences.getString(key, String.valueOf(R.string.pref_bordersAreaName_default_value)));
                    }
                }
            };

        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            Preference preference = findPreference(PREF_BORDERS_AREA);
            preference.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_BORDERS_AREA, String.valueOf(R.string.pref_bordersAreaName_default_value)));
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        }
    }

}

