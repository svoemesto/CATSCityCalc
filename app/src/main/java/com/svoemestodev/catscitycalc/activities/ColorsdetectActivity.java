package com.svoemestodev.catscitycalc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.svoemestodev.catscitycalc.citycalcclasses.Area;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalc;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalcArea;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalcType;
import com.svoemestodev.catscitycalc.citycalcclasses.MyArea;
import com.svoemestodev.catscitycalc.utils.ObservableString;
import com.svoemestodev.catscitycalc.utils.OnStringChangeListener;
import com.svoemestodev.catscitycalc.R;

import java.util.HashMap;
import java.util.Map;

//import com.google.android.gms.vision.Frame;
//import com.google.android.gms.vision.text.TextBlock;
//import com.google.android.gms.vision.text.TextRecognizer;

public class ColorsdetectActivity extends AppCompatActivity {

    public TextView tvR;
    public TextView tvG;
    public TextView tvB;
    public EditText etRGB;
    public TextView tvTHM;
    public TextView tvTHP;
    public TextView tvColor;
    public ImageView ivOriginal;
    public ImageView ivProcessed;
    public TextView tvRecognize;
    public TextView tvFinal;
    public static Bitmap fullBitmap;

    public static int color_A = 255;
    public static int color_R;
    public static int color_G;
    public static int color_B;
    public static int color_ARGB;
    public static int color_RGB;
    public static int color_THM;
    public static int color_THP;

    public static CityCalcArea cityCalcArea;

    public static Bitmap originalBitmap;
    public static Bitmap processedBitmap;

    public static ObservableString areaName = new ObservableString();
    public static final String PREF_COLORS_AREA = "pref_colors_area";

    public static Map<String, MyArea> mapAreas = new HashMap<>();

    public static CityCalc mainCityCalc;




    private void initMap() {
        mapAreas = new HashMap<>();
        String key;

        key = getString(R.string.borders_box1_info); mapAreas.put(key, new MyArea(key, Area.BOX1_INFO,
                getString(R.string.pref_cut_box1_info_x1), getString(R.string.pref_cut_box1_info_x2), getString(R.string.pref_cut_box1_info_y1), getString(R.string.pref_cut_box1_info_y2),
                getString(R.string.def_cut_box1_info_x1), getString(R.string.def_cut_box1_info_x2), getString(R.string.def_cut_box1_info_y1), getString(R.string.def_cut_box1_info_y2),
                getString(R.string.pref_rgb_box1_info_main), getString(R.string.pref_rgb_box1_info_back1), getString(R.string.pref_rgb_box1_info_thm), getString(R.string.pref_rgb_box1_info_thp),
                getString(R.string.def_rgb_box1_info_main), getString(R.string.def_rgb_box1_info_back1), getString(R.string.def_rgb_box1_info_thm), getString(R.string.def_rgb_box1_info_thp)));

        key = getString(R.string.borders_box2_info); mapAreas.put(key, new MyArea(key, Area.BOX2_INFO,
                getString(R.string.pref_cut_box2_info_x1), getString(R.string.pref_cut_box2_info_x2), getString(R.string.pref_cut_box2_info_y1), getString(R.string.pref_cut_box2_info_y2),
                getString(R.string.def_cut_box2_info_x1), getString(R.string.def_cut_box2_info_x2), getString(R.string.def_cut_box2_info_y1), getString(R.string.def_cut_box2_info_y2),
                getString(R.string.pref_rgb_box2_info_main), getString(R.string.pref_rgb_box2_info_back1), getString(R.string.pref_rgb_box2_info_thm), getString(R.string.pref_rgb_box2_info_thp),
                getString(R.string.def_rgb_box2_info_main), getString(R.string.def_rgb_box2_info_back1), getString(R.string.def_rgb_box2_info_thm), getString(R.string.def_rgb_box2_info_thp)));

        key = getString(R.string.borders_car_info); mapAreas.put(key, new MyArea(key, Area.CAR_INFO,
                getString(R.string.pref_cut_car_info_x1), getString(R.string.pref_cut_car_info_x2), getString(R.string.pref_cut_car_info_y1), getString(R.string.pref_cut_car_info_y2),
                getString(R.string.def_cut_car_info_x1), getString(R.string.def_cut_car_info_x2), getString(R.string.def_cut_car_info_y1), getString(R.string.def_cut_car_info_y2),
                getString(R.string.pref_rgb_car_info_main), getString(R.string.pref_rgb_car_info_back1), getString(R.string.pref_rgb_car_info_thm), getString(R.string.pref_rgb_car_info_thp),
                getString(R.string.def_rgb_car_info_main), getString(R.string.def_rgb_car_info_back1), getString(R.string.def_rgb_car_info_thm), getString(R.string.def_rgb_car_info_thp)));

        key = getString(R.string.borders_car_building); mapAreas.put(key, new MyArea(key, Area.CAR_BUILDING,
                getString(R.string.pref_cut_car_building_x1), getString(R.string.pref_cut_car_building_x2), getString(R.string.pref_cut_car_building_y1), getString(R.string.pref_cut_car_building_y2),
                getString(R.string.def_cut_car_building_x1), getString(R.string.def_cut_car_building_x2), getString(R.string.def_cut_car_building_y1), getString(R.string.def_cut_car_building_y2),
                getString(R.string.pref_rgb_car_building_main), getString(R.string.pref_rgb_car_building_back1), getString(R.string.pref_rgb_car_building_thm), getString(R.string.pref_rgb_car_building_thp),
                getString(R.string.def_rgb_car_building_main), getString(R.string.def_rgb_car_building_back1), getString(R.string.def_rgb_car_building_thm), getString(R.string.def_rgb_car_building_thp)));

        key = getString(R.string.borders_car_picture); mapAreas.put(key, new MyArea(key, Area.CAR_PICTURE,
                getString(R.string.pref_cut_car_picture_x1), getString(R.string.pref_cut_car_picture_x2), getString(R.string.pref_cut_car_picture_y1), getString(R.string.pref_cut_car_picture_y2),
                getString(R.string.def_cut_car_picture_x1), getString(R.string.def_cut_car_picture_x2), getString(R.string.def_cut_car_picture_y1), getString(R.string.def_cut_car_picture_y2),
                getString(R.string.pref_rgb_car_picture_main), getString(R.string.pref_rgb_car_picture_back1), getString(R.string.pref_rgb_car_picture_thm), getString(R.string.pref_rgb_car_picture_thp),
                getString(R.string.def_rgb_car_picture_main), getString(R.string.def_rgb_car_picture_back1), getString(R.string.def_rgb_car_picture_thm), getString(R.string.def_rgb_car_picture_thp)));

        key = getString(R.string.borders_car_box1); mapAreas.put(key, new MyArea(key, Area.CAR_BOX1,
                getString(R.string.pref_cut_car_box1_x1), getString(R.string.pref_cut_car_box1_x2), getString(R.string.pref_cut_car_box1_y1), getString(R.string.pref_cut_car_box1_y2),
                getString(R.string.def_cut_car_box1_x1), getString(R.string.def_cut_car_box1_x2), getString(R.string.def_cut_car_box1_y1), getString(R.string.def_cut_car_box1_y2),
                getString(R.string.pref_rgb_car_box1_main), getString(R.string.pref_rgb_car_box1_back1), getString(R.string.pref_rgb_car_box1_thm), getString(R.string.pref_rgb_car_box1_thp),
                getString(R.string.def_rgb_car_box1_main), getString(R.string.def_rgb_car_box1_back1), getString(R.string.def_rgb_car_box1_thm), getString(R.string.def_rgb_car_box1_thp)));

        key = getString(R.string.borders_car_box2); mapAreas.put(key, new MyArea(key, Area.CAR_BOX2,
                getString(R.string.pref_cut_car_box2_x1), getString(R.string.pref_cut_car_box2_x2), getString(R.string.pref_cut_car_box2_y1), getString(R.string.pref_cut_car_box2_y2),
                getString(R.string.def_cut_car_box2_x1), getString(R.string.def_cut_car_box2_x2), getString(R.string.def_cut_car_box2_y1), getString(R.string.def_cut_car_box2_y2),
                getString(R.string.pref_rgb_car_box2_main), getString(R.string.pref_rgb_car_box2_back1), getString(R.string.pref_rgb_car_box2_thm), getString(R.string.pref_rgb_car_box2_thp),
                getString(R.string.def_rgb_car_box2_main), getString(R.string.def_rgb_car_box2_back1), getString(R.string.def_rgb_car_box2_thm), getString(R.string.def_rgb_car_box2_thp)));

        key = getString(R.string.borders_car_slot1); mapAreas.put(key, new MyArea(key, Area.CAR_SLOT1,
                getString(R.string.pref_cut_car_slot1_x1), getString(R.string.pref_cut_car_slot1_x2), getString(R.string.pref_cut_car_slot1_y1), getString(R.string.pref_cut_car_slot1_y2),
                getString(R.string.def_cut_car_slot1_x1), getString(R.string.def_cut_car_slot1_x2), getString(R.string.def_cut_car_slot1_y1), getString(R.string.def_cut_car_slot1_y2),
                getString(R.string.pref_rgb_car_slot_main), getString(R.string.pref_rgb_car_slot_back1), getString(R.string.pref_rgb_car_slot_thm), getString(R.string.pref_rgb_car_slot_thp),
                getString(R.string.def_rgb_car_slot_main), getString(R.string.def_rgb_car_slot_back1), getString(R.string.def_rgb_car_slot_thm), getString(R.string.def_rgb_car_slot_thp)));

        key = getString(R.string.borders_car_slot2); mapAreas.put(key, new MyArea(key, Area.CAR_SLOT2,
                getString(R.string.pref_cut_car_slot2_x1), getString(R.string.pref_cut_car_slot2_x2), getString(R.string.pref_cut_car_slot2_y1), getString(R.string.pref_cut_car_slot2_y2),
                getString(R.string.def_cut_car_slot2_x1), getString(R.string.def_cut_car_slot2_x2), getString(R.string.def_cut_car_slot2_y1), getString(R.string.def_cut_car_slot2_y2),
                getString(R.string.pref_rgb_car_slot_main), getString(R.string.pref_rgb_car_slot_back1), getString(R.string.pref_rgb_car_slot_thm), getString(R.string.pref_rgb_car_slot_thp),
                getString(R.string.def_rgb_car_slot_main), getString(R.string.def_rgb_car_slot_back1), getString(R.string.def_rgb_car_slot_thm), getString(R.string.def_rgb_car_slot_thp)));

        key = getString(R.string.borders_car_slot3); mapAreas.put(key, new MyArea(key, Area.CAR_SLOT3,
                getString(R.string.pref_cut_car_slot3_x1), getString(R.string.pref_cut_car_slot3_x2), getString(R.string.pref_cut_car_slot3_y1), getString(R.string.pref_cut_car_slot3_y2),
                getString(R.string.def_cut_car_slot3_x1), getString(R.string.def_cut_car_slot3_x2), getString(R.string.def_cut_car_slot3_y1), getString(R.string.def_cut_car_slot3_y2),
                getString(R.string.pref_rgb_car_slot_main), getString(R.string.pref_rgb_car_slot_back1), getString(R.string.pref_rgb_car_slot_thm), getString(R.string.pref_rgb_car_slot_thp),
                getString(R.string.def_rgb_car_slot_main), getString(R.string.def_rgb_car_slot_back1), getString(R.string.def_rgb_car_slot_thm), getString(R.string.def_rgb_car_slot_thp)));

        key = getString(R.string.borders_car_health); mapAreas.put(key, new MyArea(key, Area.CAR_HEALTH,
                getString(R.string.pref_cut_car_health_x1), getString(R.string.pref_cut_car_health_x2), getString(R.string.pref_cut_car_health_y1), getString(R.string.pref_cut_car_health_y2),
                getString(R.string.def_cut_car_health_x1), getString(R.string.def_cut_car_health_x2), getString(R.string.def_cut_car_health_y1), getString(R.string.def_cut_car_health_y2),
                getString(R.string.pref_rgb_car_health_main), getString(R.string.pref_rgb_car_health_back1), getString(R.string.pref_rgb_car_health_thm), getString(R.string.pref_rgb_car_health_thp),
                getString(R.string.def_rgb_car_health_main), getString(R.string.def_rgb_car_health_back1), getString(R.string.def_rgb_car_health_thm), getString(R.string.def_rgb_car_health_thp)));

        key = getString(R.string.borders_car_shield); mapAreas.put(key, new MyArea(key, Area.CAR_SHIELD,
                getString(R.string.pref_cut_car_shield_x1), getString(R.string.pref_cut_car_shield_x2), getString(R.string.pref_cut_car_shield_y1), getString(R.string.pref_cut_car_shield_y2),
                getString(R.string.def_cut_car_shield_x1), getString(R.string.def_cut_car_shield_x2), getString(R.string.def_cut_car_shield_y1), getString(R.string.def_cut_car_shield_y2),
                getString(R.string.pref_rgb_car_shield_main), getString(R.string.pref_rgb_car_shield_back1), getString(R.string.pref_rgb_car_shield_thm), getString(R.string.pref_rgb_car_shield_thp),
                getString(R.string.def_rgb_car_shield_main), getString(R.string.def_rgb_car_shield_back1), getString(R.string.def_rgb_car_shield_thm), getString(R.string.def_rgb_car_shield_thp)));

        key = getString(R.string.borders_car_state); mapAreas.put(key, new MyArea(key, Area.CAR_STATE,
                getString(R.string.pref_cut_car_state_x1), getString(R.string.pref_cut_car_state_x2), getString(R.string.pref_cut_car_state_y1), getString(R.string.pref_cut_car_state_y2),
                getString(R.string.def_cut_car_state_x1), getString(R.string.def_cut_car_state_x2), getString(R.string.def_cut_car_state_y1), getString(R.string.def_cut_car_state_y2),
                getString(R.string.pref_rgb_car_state_main), getString(R.string.pref_rgb_car_state_back1), getString(R.string.pref_rgb_car_state_thm), getString(R.string.pref_rgb_car_state_thp),
                getString(R.string.def_rgb_car_state_main), getString(R.string.def_rgb_car_state_back1), getString(R.string.def_rgb_car_state_thm), getString(R.string.def_rgb_car_state_thp)));

        key = getString(R.string.borders_car_healbox); mapAreas.put(key, new MyArea(key, Area.CAR_HEALBOX,
                getString(R.string.pref_cut_car_healbox_x1), getString(R.string.pref_cut_car_healbox_x2), getString(R.string.pref_cut_car_healbox_y1), getString(R.string.pref_cut_car_healbox_y2),
                getString(R.string.def_cut_car_healbox_x1), getString(R.string.def_cut_car_healbox_x2), getString(R.string.def_cut_car_healbox_y1), getString(R.string.def_cut_car_healbox_y2),
                getString(R.string.pref_rgb_car_healbox_main), getString(R.string.pref_rgb_car_healbox_back1), getString(R.string.pref_rgb_car_healbox_thm), getString(R.string.pref_rgb_car_healbox_thp),
                getString(R.string.def_rgb_car_healbox_main), getString(R.string.def_rgb_car_healbox_back1), getString(R.string.def_rgb_car_healbox_thm), getString(R.string.def_rgb_car_healbox_thp)));

        key = getString(R.string.borders_car_timebox1); mapAreas.put(key, new MyArea(key, Area.CAR_TIMEBOX1,
                getString(R.string.pref_cut_car_timebox1_x1), getString(R.string.pref_cut_car_timebox1_x2), getString(R.string.pref_cut_car_timebox1_y1), getString(R.string.pref_cut_car_timebox1_y2),
                getString(R.string.def_cut_car_timebox1_x1), getString(R.string.def_cut_car_timebox1_x2), getString(R.string.def_cut_car_timebox1_y1), getString(R.string.def_cut_car_timebox1_y2),
                getString(R.string.pref_rgb_car_timebox1_main), getString(R.string.pref_rgb_car_timebox1_back1), getString(R.string.pref_rgb_car_timebox1_thm), getString(R.string.pref_rgb_car_timebox1_thp),
                getString(R.string.def_rgb_car_timebox1_main), getString(R.string.def_rgb_car_timebox1_back1), getString(R.string.def_rgb_car_timebox1_thm), getString(R.string.def_rgb_car_timebox1_thp)));

        key = getString(R.string.borders_car_timebox2); mapAreas.put(key, new MyArea(key, Area.CAR_TIMEBOX2,
                getString(R.string.pref_cut_car_timebox2_x1), getString(R.string.pref_cut_car_timebox2_x2), getString(R.string.pref_cut_car_timebox2_y1), getString(R.string.pref_cut_car_timebox2_y2),
                getString(R.string.def_cut_car_timebox2_x1), getString(R.string.def_cut_car_timebox2_x2), getString(R.string.def_cut_car_timebox2_y1), getString(R.string.def_cut_car_timebox2_y2),
                getString(R.string.pref_rgb_car_timebox2_main), getString(R.string.pref_rgb_car_timebox2_back1), getString(R.string.pref_rgb_car_timebox2_thm), getString(R.string.pref_rgb_car_timebox2_thp),
                getString(R.string.def_rgb_car_timebox2_main), getString(R.string.def_rgb_car_timebox2_back1), getString(R.string.def_rgb_car_timebox2_thm), getString(R.string.def_rgb_car_timebox2_thp)));

        key = getString(R.string.borders_car_statebox1); mapAreas.put(key, new MyArea(key, Area.CAR_STATEBOX1,
                getString(R.string.pref_cut_car_statebox1_x1), getString(R.string.pref_cut_car_statebox1_x2), getString(R.string.pref_cut_car_statebox1_y1), getString(R.string.pref_cut_car_statebox1_y2),
                getString(R.string.def_cut_car_statebox1_x1), getString(R.string.def_cut_car_statebox1_x2), getString(R.string.def_cut_car_statebox1_y1), getString(R.string.def_cut_car_statebox1_y2),
                getString(R.string.pref_rgb_car_statebox1_main), getString(R.string.pref_rgb_car_statebox1_back1), getString(R.string.pref_rgb_car_statebox1_thm), getString(R.string.pref_rgb_car_statebox1_thp),
                getString(R.string.def_rgb_car_statebox1_main), getString(R.string.def_rgb_car_statebox1_back1), getString(R.string.def_rgb_car_statebox1_thm), getString(R.string.def_rgb_car_statebox1_thp)));

        key = getString(R.string.borders_car_statebox2); mapAreas.put(key, new MyArea(key, Area.CAR_STATEBOX2,
                getString(R.string.pref_cut_car_statebox2_x1), getString(R.string.pref_cut_car_statebox2_x2), getString(R.string.pref_cut_car_statebox2_y1), getString(R.string.pref_cut_car_statebox2_y2),
                getString(R.string.def_cut_car_statebox2_x1), getString(R.string.def_cut_car_statebox2_x2), getString(R.string.def_cut_car_statebox2_y1), getString(R.string.def_cut_car_statebox2_y2),
                getString(R.string.pref_rgb_car_statebox2_main), getString(R.string.pref_rgb_car_statebox2_back1), getString(R.string.pref_rgb_car_statebox2_thm), getString(R.string.pref_rgb_car_statebox2_thp),
                getString(R.string.def_rgb_car_statebox2_main), getString(R.string.def_rgb_car_statebox2_back1), getString(R.string.def_rgb_car_statebox2_thm), getString(R.string.def_rgb_car_statebox2_thp)));

        key = getString(R.string.borders_car_statebox3); mapAreas.put(key, new MyArea(key, Area.CAR_STATEBOX3,
                getString(R.string.pref_cut_car_statebox3_x1), getString(R.string.pref_cut_car_statebox3_x2), getString(R.string.pref_cut_car_statebox3_y1), getString(R.string.pref_cut_car_statebox3_y2),
                getString(R.string.def_cut_car_statebox3_x1), getString(R.string.def_cut_car_statebox3_x2), getString(R.string.def_cut_car_statebox3_y1), getString(R.string.def_cut_car_statebox3_y2),
                getString(R.string.pref_rgb_car_statebox3_main), getString(R.string.pref_rgb_car_statebox3_back1), getString(R.string.pref_rgb_car_statebox3_thm), getString(R.string.pref_rgb_car_statebox3_thp),
                getString(R.string.def_rgb_car_statebox3_main), getString(R.string.def_rgb_car_statebox3_back1), getString(R.string.def_rgb_car_statebox3_thm), getString(R.string.def_rgb_car_statebox3_thp)));

        key = getString(R.string.borders_city); mapAreas.put(key, new MyArea(key, Area.CITY,
                getString(R.string.pref_cut_city_x1), getString(R.string.pref_cut_city_x2), getString(R.string.pref_cut_city_y1), getString(R.string.pref_cut_city_y2),
                getString(R.string.def_cut_city_x1), getString(R.string.def_cut_city_x2), getString(R.string.def_cut_city_y1), getString(R.string.def_cut_city_y2),
                getString(R.string.pref_rgb_city_main), getString(R.string.pref_rgb_city_back1), getString(R.string.pref_rgb_city_thm), getString(R.string.pref_rgb_city_thp),
                getString(R.string.def_rgb_city_main), getString(R.string.def_rgb_city_back1), getString(R.string.def_rgb_city_thm), getString(R.string.def_rgb_city_thp)));

        key = getString(R.string.borders_time); mapAreas.put(key, new MyArea(key, Area.TOTAL_TIME,
                getString(R.string.pref_cut_total_time_x1), getString(R.string.pref_cut_total_time_x2), getString(R.string.pref_cut_total_time_y1), getString(R.string.pref_cut_total_time_y2),
                getString(R.string.def_cut_total_time_x1), getString(R.string.def_cut_total_time_x2), getString(R.string.def_cut_total_time_y1), getString(R.string.def_cut_total_time_y2),
                getString(R.string.pref_rgb_total_time_main), getString(R.string.pref_rgb_total_time_back1), getString(R.string.pref_rgb_total_time_thm), getString(R.string.pref_rgb_total_time_thp),
                getString(R.string.def_rgb_total_time_main), getString(R.string.def_rgb_total_time_back1), getString(R.string.def_rgb_total_time_thm), getString(R.string.def_rgb_total_time_thp)));

        key = getString(R.string.borders_scores_to_early_win); mapAreas.put(key, new MyArea(key, Area.EARLY_WIN,
                getString(R.string.pref_cut_early_win_x1), getString(R.string.pref_cut_early_win_x2), getString(R.string.pref_cut_early_win_y1), getString(R.string.pref_cut_early_win_y2),
                getString(R.string.def_cut_early_win_x1), getString(R.string.def_cut_early_win_x2), getString(R.string.def_cut_early_win_y1), getString(R.string.def_cut_early_win_y2),
                getString(R.string.pref_rgb_early_win_main), getString(R.string.pref_rgb_early_win_back1), getString(R.string.pref_rgb_early_win_thm), getString(R.string.pref_rgb_early_win_thp),
                getString(R.string.def_rgb_early_win_main), getString(R.string.def_rgb_early_win_back1), getString(R.string.def_rgb_early_win_thm), getString(R.string.def_rgb_early_win_thp)));

        key = getString(R.string.borders_our_scores); mapAreas.put(key, new MyArea(key, Area.POINTS_AND_INCREASE_OUR,
                getString(R.string.pref_cut_points_and_increase_our_x1), getString(R.string.pref_cut_points_and_increase_our_x2), getString(R.string.pref_cut_points_and_increase_our_y1), getString(R.string.pref_cut_points_and_increase_our_y2),
                getString(R.string.def_cut_points_and_increase_our_x1), getString(R.string.def_cut_points_and_increase_our_x2), getString(R.string.def_cut_points_and_increase_our_y1), getString(R.string.def_cut_points_and_increase_our_y2),
                getString(R.string.pref_rgb_points_our_main), getString(R.string.pref_rgb_points_our_back1), getString(R.string.pref_rgb_points_our_thm), getString(R.string.pref_rgb_points_our_thp),
                getString(R.string.def_rgb_points_our_main), getString(R.string.def_rgb_points_our_back1), getString(R.string.def_rgb_points_our_thm), getString(R.string.def_rgb_points_our_thp)));

        key = getString(R.string.borders_enemy_scores); mapAreas.put(key, new MyArea(key, Area.POINTS_AND_INCREASE_ENEMY,
                getString(R.string.pref_cut_points_and_increase_enemy_x1), getString(R.string.pref_cut_points_and_increase_enemy_x2), getString(R.string.pref_cut_points_and_increase_enemy_y1), getString(R.string.pref_cut_points_and_increase_enemy_y2),
                getString(R.string.def_cut_points_and_increase_enemy_x1), getString(R.string.def_cut_points_and_increase_enemy_x2), getString(R.string.def_cut_points_and_increase_enemy_y1), getString(R.string.def_cut_points_and_increase_enemy_y2),
                getString(R.string.pref_rgb_points_enemy_main), getString(R.string.pref_rgb_points_enemy_back1), getString(R.string.pref_rgb_points_enemy_thm), getString(R.string.pref_rgb_points_enemy_thp),
                getString(R.string.def_rgb_points_enemy_main), getString(R.string.def_rgb_points_enemy_back1), getString(R.string.def_rgb_points_enemy_thm), getString(R.string.def_rgb_points_enemy_thp)));

        key = getString(R.string.borders_enemy_team_name); mapAreas.put(key, new MyArea(key, Area.TEAM_NAME_ENEMY,
                getString(R.string.pref_cut_team_name_enemy_x1), getString(R.string.pref_cut_team_name_enemy_x2), getString(R.string.pref_cut_team_name_enemy_y1), getString(R.string.pref_cut_team_name_enemy_y2),
                getString(R.string.def_cut_team_name_enemy_x1), getString(R.string.def_cut_team_name_enemy_x2), getString(R.string.def_cut_team_name_enemy_y1), getString(R.string.def_cut_team_name_enemy_y2),
                getString(R.string.pref_rgb_team_name_enemy_main), getString(R.string.pref_rgb_team_name_enemy_back1), getString(R.string.pref_rgb_team_name_enemy_thm), getString(R.string.pref_rgb_team_name_enemy_thp),
                getString(R.string.def_rgb_team_name_enemy_main), getString(R.string.def_rgb_team_name_enemy_back1), getString(R.string.def_rgb_team_name_enemy_thm), getString(R.string.def_rgb_team_name_enemy_thp)));

        key = getString(R.string.borders_our_team_name); mapAreas.put(key, new MyArea(key, Area.TEAM_NAME_OUR,
                getString(R.string.pref_cut_team_name_our_x1), getString(R.string.pref_cut_team_name_our_x2), getString(R.string.pref_cut_team_name_our_y1), getString(R.string.pref_cut_team_name_our_y2),
                getString(R.string.def_cut_team_name_our_x1), getString(R.string.def_cut_team_name_our_x2), getString(R.string.def_cut_team_name_our_y1), getString(R.string.def_cut_team_name_our_y2),
                getString(R.string.pref_rgb_team_name_our_main), getString(R.string.pref_rgb_team_name_our_back1), getString(R.string.pref_rgb_team_name_our_thm), getString(R.string.pref_rgb_team_name_our_thp),
                getString(R.string.def_rgb_team_name_our_main), getString(R.string.def_rgb_team_name_our_back1), getString(R.string.def_rgb_team_name_our_thm), getString(R.string.def_rgb_team_name_our_thp)));

        key = getString(R.string.borders_blt); mapAreas.put(key, new MyArea(key, Area.BLT_AREA,
                getString(R.string.pref_cut_blt_x1), getString(R.string.pref_cut_blt_x2), getString(R.string.pref_cut_blt_y1), getString(R.string.pref_cut_blt_y2),
                getString(R.string.def_cut_blt_x1), getString(R.string.def_cut_blt_x2), getString(R.string.def_cut_blt_y1), getString(R.string.def_cut_blt_y2),
                getString(R.string.pref_rgb_bxx_main), getString(R.string.pref_rgb_bxx_back1), getString(R.string.pref_rgb_bxx_thm), getString(R.string.pref_rgb_bxx_thp),
                getString(R.string.def_rgb_bxx_main), getString(R.string.def_rgb_bxx_back1), getString(R.string.def_rgb_bxx_thm), getString(R.string.def_rgb_bxx_thp)));

        key = getString(R.string.borders_blt_name); mapAreas.put(key, new MyArea(key, Area.BLT,
                getString(R.string.pref_cut_blt_name_x1), getString(R.string.pref_cut_blt_name_x2), getString(R.string.pref_cut_blt_name_y1), getString(R.string.pref_cut_blt_name_y2),
                getString(R.string.def_cut_blt_name_x1), getString(R.string.def_cut_blt_name_x2), getString(R.string.def_cut_blt_name_y1), getString(R.string.def_cut_blt_name_y2),
                getString(R.string.pref_rgb_bxx_name_main), getString(R.string.pref_rgb_bxx_name_back1), getString(R.string.pref_rgb_bxx_name_thm), getString(R.string.pref_rgb_bxx_name_thp),
                getString(R.string.def_rgb_bxx_name_our_main), getString(R.string.def_rgb_bxx_name_our_back1), getString(R.string.def_rgb_bxx_name_our_thm), getString(R.string.def_rgb_bxx_name_our_thp)));

//        key = getString(R.string.borders_blt_increase); mapAreas.put(key, new MyArea(key, Area.BLT_POINTS,
//                getString(R.string.pref_cut_blt_increase_x1), getString(R.string.pref_cut_blt_increase_x2), getString(R.string.pref_cut_blt_increase_y1), getString(R.string.pref_cut_blt_increase_y2),
//                getString(R.string.def_cut_blt_points_x1), getString(R.string.def_cut_blt_points_x2), getString(R.string.def_cut_blt_points_y1), getString(R.string.def_cut_blt_points_y2),
//                getString(R.string.pref_rgb_bxx_increase_main), getString(R.string.pref_rgb_bxx_increase_back1), getString(R.string.pref_rgb_bxx_increase_thm), getString(R.string.pref_rgb_bxx_increase_thp),
//                getString(R.string.def_rgb_bxx_points_our_main), getString(R.string.def_rgb_bxx_points_our_back1), getString(R.string.def_rgb_bxx_points_our_thm), getString(R.string.def_rgb_bxx_points_our_thp)));

        key = getString(R.string.borders_blt_slots); mapAreas.put(key, new MyArea(key, Area.BLT_SLOTS,
                getString(R.string.pref_cut_blt_slots_x1), getString(R.string.pref_cut_blt_slots_x2), getString(R.string.pref_cut_blt_slots_y1), getString(R.string.pref_cut_blt_slots_y2),
                getString(R.string.def_cut_blt_slots_x1), getString(R.string.def_cut_blt_slots_x2), getString(R.string.def_cut_blt_slots_y1), getString(R.string.def_cut_blt_slots_y2),
                getString(R.string.pref_rgb_bxx_slots_main), getString(R.string.pref_rgb_bxx_slots_back1), getString(R.string.pref_rgb_bxx_slots_thm), getString(R.string.pref_rgb_bxx_slots_thp),
                getString(R.string.def_rgb_bxx_slots_main), getString(R.string.def_rgb_bxx_slots_back1), getString(R.string.def_rgb_bxx_slots_thm), getString(R.string.def_rgb_bxx_slots_thp)));

        key = getString(R.string.borders_blt_progress); mapAreas.put(key, new MyArea(key, Area.BLT_PROGRESS,
                getString(R.string.pref_cut_blt_progress_x1), getString(R.string.pref_cut_blt_progress_x2), getString(R.string.pref_cut_blt_progress_y1), getString(R.string.pref_cut_blt_progress_y2),
                getString(R.string.def_cut_blt_progress_x1), getString(R.string.def_cut_blt_progress_x2), getString(R.string.def_cut_blt_progress_y1), getString(R.string.def_cut_blt_progress_y2),
                getString(R.string.pref_rgb_bxx_progress_our_main), getString(R.string.pref_rgb_bxx_progress_our_back1), getString(R.string.pref_rgb_bxx_progress_our_thm), getString(R.string.pref_rgb_bxx_progress_our_thp),
                getString(R.string.def_rgb_bxx_progress_our_main), getString(R.string.def_rgb_bxx_progress_our_back1), getString(R.string.def_rgb_bxx_progress_our_thm), getString(R.string.def_rgb_bxx_progress_our_thp)));


        key = getString(R.string.borders_blc); mapAreas.put(key, new MyArea(key, Area.BLC_AREA,
                getString(R.string.pref_cut_blc_x1), getString(R.string.pref_cut_blc_x2), getString(R.string.pref_cut_blc_y1), getString(R.string.pref_cut_blc_y2),
                getString(R.string.def_cut_blc_x1), getString(R.string.def_cut_blc_x2), getString(R.string.def_cut_blc_y1), getString(R.string.def_cut_blc_y2),
                getString(R.string.pref_rgb_bxx_main), getString(R.string.pref_rgb_bxx_back1), getString(R.string.pref_rgb_bxx_thm), getString(R.string.pref_rgb_bxx_thp),
                getString(R.string.def_rgb_bxx_main), getString(R.string.def_rgb_bxx_back1), getString(R.string.def_rgb_bxx_thm), getString(R.string.def_rgb_bxx_thp)));

        key = getString(R.string.borders_blc_name); mapAreas.put(key, new MyArea(key, Area.BLC,
                getString(R.string.pref_cut_blc_name_x1), getString(R.string.pref_cut_blc_name_x2), getString(R.string.pref_cut_blc_name_y1), getString(R.string.pref_cut_blc_name_y2),
                getString(R.string.def_cut_blc_name_x1), getString(R.string.def_cut_blc_name_x2), getString(R.string.def_cut_blc_name_y1), getString(R.string.def_cut_blc_name_y2),
                getString(R.string.pref_rgb_bxx_name_main), getString(R.string.pref_rgb_bxx_name_back1), getString(R.string.pref_rgb_bxx_name_thm), getString(R.string.pref_rgb_bxx_name_thp),
                getString(R.string.def_rgb_bxx_name_our_main), getString(R.string.def_rgb_bxx_name_our_back1), getString(R.string.def_rgb_bxx_name_our_thm), getString(R.string.def_rgb_bxx_name_our_thp)));

//        key = getString(R.string.borders_blc_increase); mapAreas.put(key, new MyArea(key, Area.BLC_POINTS,
//                getString(R.string.pref_cut_blc_increase_x1), getString(R.string.pref_cut_blc_increase_x2), getString(R.string.pref_cut_blc_increase_y1), getString(R.string.pref_cut_blc_increase_y2),
//                getString(R.string.def_cut_blc_points_x1), getString(R.string.def_cut_blc_points_x2), getString(R.string.def_cut_blc_points_y1), getString(R.string.def_cut_blc_points_y2),
//                getString(R.string.pref_rgb_bxx_increase_main), getString(R.string.pref_rgb_bxx_increase_back1), getString(R.string.pref_rgb_bxx_increase_thm), getString(R.string.pref_rgb_bxx_increase_thp),
//                getString(R.string.def_rgb_bxx_points_our_main), getString(R.string.def_rgb_bxx_points_our_back1), getString(R.string.def_rgb_bxx_points_our_thm), getString(R.string.def_rgb_bxx_points_our_thp)));

        key = getString(R.string.borders_blc_slots); mapAreas.put(key, new MyArea(key, Area.BLC_SLOTS,
                getString(R.string.pref_cut_blc_slots_x1), getString(R.string.pref_cut_blc_slots_x2), getString(R.string.pref_cut_blc_slots_y1), getString(R.string.pref_cut_blc_slots_y2),
                getString(R.string.def_cut_blc_slots_x1), getString(R.string.def_cut_blc_slots_x2), getString(R.string.def_cut_blc_slots_y1), getString(R.string.def_cut_blc_slots_y2),
                getString(R.string.pref_rgb_bxx_slots_main), getString(R.string.pref_rgb_bxx_slots_back1), getString(R.string.pref_rgb_bxx_slots_thm), getString(R.string.pref_rgb_bxx_slots_thp),
                getString(R.string.def_rgb_bxx_slots_main), getString(R.string.def_rgb_bxx_slots_back1), getString(R.string.def_rgb_bxx_slots_thm), getString(R.string.def_rgb_bxx_slots_thp)));

        key = getString(R.string.borders_blc_progress); mapAreas.put(key, new MyArea(key, Area.BLC_PROGRESS,
                getString(R.string.pref_cut_blc_progress_x1), getString(R.string.pref_cut_blc_progress_x2), getString(R.string.pref_cut_blc_progress_y1), getString(R.string.pref_cut_blc_progress_y2),
                getString(R.string.def_cut_blc_progress_x1), getString(R.string.def_cut_blc_progress_x2), getString(R.string.def_cut_blc_progress_y1), getString(R.string.def_cut_blc_progress_y2),
                getString(R.string.pref_rgb_bxx_progress_our_main), getString(R.string.pref_rgb_bxx_progress_our_back1), getString(R.string.pref_rgb_bxx_progress_our_thm), getString(R.string.pref_rgb_bxx_progress_our_thp),
                getString(R.string.def_rgb_bxx_progress_our_main), getString(R.string.def_rgb_bxx_progress_our_back1), getString(R.string.def_rgb_bxx_progress_our_thm), getString(R.string.def_rgb_bxx_progress_our_thp)));


        key = getString(R.string.borders_blb); mapAreas.put(key, new MyArea(key, Area.BLB_AREA,
                getString(R.string.pref_cut_blb_x1), getString(R.string.pref_cut_blb_x2), getString(R.string.pref_cut_blb_y1), getString(R.string.pref_cut_blb_y2),
                getString(R.string.def_cut_blb_x1), getString(R.string.def_cut_blb_x2), getString(R.string.def_cut_blb_y1), getString(R.string.def_cut_blb_y2),
                getString(R.string.pref_rgb_bxx_main), getString(R.string.pref_rgb_bxx_back1), getString(R.string.pref_rgb_bxx_thm), getString(R.string.pref_rgb_bxx_thp),
                getString(R.string.def_rgb_bxx_main), getString(R.string.def_rgb_bxx_back1), getString(R.string.def_rgb_bxx_thm), getString(R.string.def_rgb_bxx_thp)));

        key = getString(R.string.borders_blb_name); mapAreas.put(key, new MyArea(key, Area.BLB,
                getString(R.string.pref_cut_blb_name_x1), getString(R.string.pref_cut_blb_name_x2), getString(R.string.pref_cut_blb_name_y1), getString(R.string.pref_cut_blb_name_y2),
                getString(R.string.def_cut_blb_name_x1), getString(R.string.def_cut_blb_name_x2), getString(R.string.def_cut_blb_name_y1), getString(R.string.def_cut_blb_name_y2),
                getString(R.string.pref_rgb_bxx_name_main), getString(R.string.pref_rgb_bxx_name_back1), getString(R.string.pref_rgb_bxx_name_thm), getString(R.string.pref_rgb_bxx_name_thp),
                getString(R.string.def_rgb_bxx_name_our_main), getString(R.string.def_rgb_bxx_name_our_back1), getString(R.string.def_rgb_bxx_name_our_thm), getString(R.string.def_rgb_bxx_name_our_thp)));

//        key = getString(R.string.borders_blb_increase); mapAreas.put(key, new MyArea(key, Area.BLB_POINTS,
//                getString(R.string.pref_cut_blb_increase_x1), getString(R.string.pref_cut_blb_increase_x2), getString(R.string.pref_cut_blb_increase_y1), getString(R.string.pref_cut_blb_increase_y2),
//                getString(R.string.def_cut_blb_points_x1), getString(R.string.def_cut_blb_points_x2), getString(R.string.def_cut_blb_points_y1), getString(R.string.def_cut_blb_points_y2),
//                getString(R.string.pref_rgb_bxx_increase_main), getString(R.string.pref_rgb_bxx_increase_back1), getString(R.string.pref_rgb_bxx_increase_thm), getString(R.string.pref_rgb_bxx_increase_thp),
//                getString(R.string.def_rgb_bxx_points_our_main), getString(R.string.def_rgb_bxx_points_our_back1), getString(R.string.def_rgb_bxx_points_our_thm), getString(R.string.def_rgb_bxx_points_our_thp)));

        key = getString(R.string.borders_blb_slots); mapAreas.put(key, new MyArea(key, Area.BLB_SLOTS,
                getString(R.string.pref_cut_blb_slots_x1), getString(R.string.pref_cut_blb_slots_x2), getString(R.string.pref_cut_blb_slots_y1), getString(R.string.pref_cut_blb_slots_y2),
                getString(R.string.def_cut_blb_slots_x1), getString(R.string.def_cut_blb_slots_x2), getString(R.string.def_cut_blb_slots_y1), getString(R.string.def_cut_blb_slots_y2),
                getString(R.string.pref_rgb_bxx_slots_main), getString(R.string.pref_rgb_bxx_slots_back1), getString(R.string.pref_rgb_bxx_slots_thm), getString(R.string.pref_rgb_bxx_slots_thp),
                getString(R.string.def_rgb_bxx_slots_main), getString(R.string.def_rgb_bxx_slots_back1), getString(R.string.def_rgb_bxx_slots_thm), getString(R.string.def_rgb_bxx_slots_thp)));

        key = getString(R.string.borders_blb_progress); mapAreas.put(key, new MyArea(key, Area.BLB_PROGRESS,
                getString(R.string.pref_cut_blb_progress_x1), getString(R.string.pref_cut_blb_progress_x2), getString(R.string.pref_cut_blb_progress_y1), getString(R.string.pref_cut_blb_progress_y2),
                getString(R.string.def_cut_blb_progress_x1), getString(R.string.def_cut_blb_progress_x2), getString(R.string.def_cut_blb_progress_y1), getString(R.string.def_cut_blb_progress_y2),
                getString(R.string.pref_rgb_bxx_progress_our_main), getString(R.string.pref_rgb_bxx_progress_our_back1), getString(R.string.pref_rgb_bxx_progress_our_thm), getString(R.string.pref_rgb_bxx_progress_our_thp),
                getString(R.string.def_rgb_bxx_progress_our_main), getString(R.string.def_rgb_bxx_progress_our_back1), getString(R.string.def_rgb_bxx_progress_our_thm), getString(R.string.def_rgb_bxx_progress_our_thp)));


        key = getString(R.string.borders_brt); mapAreas.put(key, new MyArea(key, Area.BRT_AREA,
                getString(R.string.pref_cut_brt_x1), getString(R.string.pref_cut_brt_x2), getString(R.string.pref_cut_brt_y1), getString(R.string.pref_cut_brt_y2),
                getString(R.string.def_cut_brt_x1), getString(R.string.def_cut_brt_x2), getString(R.string.def_cut_brt_y1), getString(R.string.def_cut_brt_y2),
                getString(R.string.pref_rgb_bxx_main), getString(R.string.pref_rgb_bxx_back1), getString(R.string.pref_rgb_bxx_thm), getString(R.string.pref_rgb_bxx_thp),
                getString(R.string.def_rgb_bxx_main), getString(R.string.def_rgb_bxx_back1), getString(R.string.def_rgb_bxx_thm), getString(R.string.def_rgb_bxx_thp)));

        key = getString(R.string.borders_brt_name); mapAreas.put(key, new MyArea(key, Area.BRT,
                getString(R.string.pref_cut_brt_name_x1), getString(R.string.pref_cut_brt_name_x2), getString(R.string.pref_cut_brt_name_y1), getString(R.string.pref_cut_brt_name_y2),
                getString(R.string.def_cut_brt_name_x1), getString(R.string.def_cut_brt_name_x2), getString(R.string.def_cut_brt_name_y1), getString(R.string.def_cut_brt_name_y2),
                getString(R.string.pref_rgb_bxx_name_main), getString(R.string.pref_rgb_bxx_name_back1), getString(R.string.pref_rgb_bxx_name_thm), getString(R.string.pref_rgb_bxx_name_thp),
                getString(R.string.def_rgb_bxx_name_our_main), getString(R.string.def_rgb_bxx_name_our_back1), getString(R.string.def_rgb_bxx_name_our_thm), getString(R.string.def_rgb_bxx_name_our_thp)));

//        key = getString(R.string.borders_brt_increase); mapAreas.put(key, new MyArea(key, Area.BRT_POINTS,
//                getString(R.string.pref_cut_brt_increase_x1), getString(R.string.pref_cut_brt_increase_x2), getString(R.string.pref_cut_brt_increase_y1), getString(R.string.pref_cut_brt_increase_y2),
//                getString(R.string.def_cut_brt_points_x1), getString(R.string.def_cut_brt_points_x2), getString(R.string.def_cut_brt_points_y1), getString(R.string.def_cut_brt_points_y2),
//                getString(R.string.pref_rgb_bxx_increase_main), getString(R.string.pref_rgb_bxx_increase_back1), getString(R.string.pref_rgb_bxx_increase_thm), getString(R.string.pref_rgb_bxx_increase_thp),
//                getString(R.string.def_rgb_bxx_points_our_main), getString(R.string.def_rgb_bxx_points_our_back1), getString(R.string.def_rgb_bxx_points_our_thm), getString(R.string.def_rgb_bxx_points_our_thp)));

        key = getString(R.string.borders_brt_slots); mapAreas.put(key, new MyArea(key, Area.BRT_SLOTS,
                getString(R.string.pref_cut_brt_slots_x1), getString(R.string.pref_cut_brt_slots_x2), getString(R.string.pref_cut_brt_slots_y1), getString(R.string.pref_cut_brt_slots_y2),
                getString(R.string.def_cut_brt_slots_x1), getString(R.string.def_cut_brt_slots_x2), getString(R.string.def_cut_brt_slots_y1), getString(R.string.def_cut_brt_slots_y2),
                getString(R.string.pref_rgb_bxx_slots_main), getString(R.string.pref_rgb_bxx_slots_back1), getString(R.string.pref_rgb_bxx_slots_thm), getString(R.string.pref_rgb_bxx_slots_thp),
                getString(R.string.def_rgb_bxx_slots_main), getString(R.string.def_rgb_bxx_slots_back1), getString(R.string.def_rgb_bxx_slots_thm), getString(R.string.def_rgb_bxx_slots_thp)));

        key = getString(R.string.borders_brt_progress); mapAreas.put(key, new MyArea(key, Area.BRT_PROGRESS,
                getString(R.string.pref_cut_brt_progress_x1), getString(R.string.pref_cut_brt_progress_x2), getString(R.string.pref_cut_brt_progress_y1), getString(R.string.pref_cut_brt_progress_y2),
                getString(R.string.def_cut_brt_progress_x1), getString(R.string.def_cut_brt_progress_x2), getString(R.string.def_cut_brt_progress_y1), getString(R.string.def_cut_brt_progress_y2),
                getString(R.string.pref_rgb_bxx_progress_our_main), getString(R.string.pref_rgb_bxx_progress_our_back1), getString(R.string.pref_rgb_bxx_progress_our_thm), getString(R.string.pref_rgb_bxx_progress_our_thp),
                getString(R.string.def_rgb_bxx_progress_our_main), getString(R.string.def_rgb_bxx_progress_our_back1), getString(R.string.def_rgb_bxx_progress_our_thm), getString(R.string.def_rgb_bxx_progress_our_thp)));


        key = getString(R.string.borders_brc); mapAreas.put(key, new MyArea(key, Area.BRC_AREA,
                getString(R.string.pref_cut_brc_x1), getString(R.string.pref_cut_brc_x2), getString(R.string.pref_cut_brc_y1), getString(R.string.pref_cut_brc_y2),
                getString(R.string.def_cut_brc_x1), getString(R.string.def_cut_brc_x2), getString(R.string.def_cut_brc_y1), getString(R.string.def_cut_brc_y2),
                getString(R.string.pref_rgb_bxx_main), getString(R.string.pref_rgb_bxx_back1), getString(R.string.pref_rgb_bxx_thm), getString(R.string.pref_rgb_bxx_thp),
                getString(R.string.def_rgb_bxx_main), getString(R.string.def_rgb_bxx_back1), getString(R.string.def_rgb_bxx_thm), getString(R.string.def_rgb_bxx_thp)));

        key = getString(R.string.borders_brc_name); mapAreas.put(key, new MyArea(key, Area.BRC,
                getString(R.string.pref_cut_brc_name_x1), getString(R.string.pref_cut_brc_name_x2), getString(R.string.pref_cut_brc_name_y1), getString(R.string.pref_cut_brc_name_y2),
                getString(R.string.def_cut_brc_name_x1), getString(R.string.def_cut_brc_name_x2), getString(R.string.def_cut_brc_name_y1), getString(R.string.def_cut_brc_name_y2),
                getString(R.string.pref_rgb_bxx_name_main), getString(R.string.pref_rgb_bxx_name_back1), getString(R.string.pref_rgb_bxx_name_thm), getString(R.string.pref_rgb_bxx_name_thp),
                getString(R.string.def_rgb_bxx_name_our_main), getString(R.string.def_rgb_bxx_name_our_back1), getString(R.string.def_rgb_bxx_name_our_thm), getString(R.string.def_rgb_bxx_name_our_thp)));

//        key = getString(R.string.borders_brc_increase); mapAreas.put(key, new MyArea(key, Area.BRC_POINTS,
//                getString(R.string.pref_cut_brc_increase_x1), getString(R.string.pref_cut_brc_increase_x2), getString(R.string.pref_cut_brc_increase_y1), getString(R.string.pref_cut_brc_increase_y2),
//                getString(R.string.def_cut_brc_points_x1), getString(R.string.def_cut_brc_points_x2), getString(R.string.def_cut_brc_points_y1), getString(R.string.def_cut_brc_points_y2),
//                getString(R.string.pref_rgb_bxx_increase_main), getString(R.string.pref_rgb_bxx_increase_back1), getString(R.string.pref_rgb_bxx_increase_thm), getString(R.string.pref_rgb_bxx_increase_thp),
//                getString(R.string.def_rgb_bxx_points_our_main), getString(R.string.def_rgb_bxx_points_our_back1), getString(R.string.def_rgb_bxx_points_our_thm), getString(R.string.def_rgb_bxx_points_our_thp)));

        key = getString(R.string.borders_brc_slots); mapAreas.put(key, new MyArea(key, Area.BRC_SLOTS,
                getString(R.string.pref_cut_brc_slots_x1), getString(R.string.pref_cut_brc_slots_x2), getString(R.string.pref_cut_brc_slots_y1), getString(R.string.pref_cut_brc_slots_y2),
                getString(R.string.def_cut_brc_slots_x1), getString(R.string.def_cut_brc_slots_x2), getString(R.string.def_cut_brc_slots_y1), getString(R.string.def_cut_brc_slots_y2),
                getString(R.string.pref_rgb_bxx_slots_main), getString(R.string.pref_rgb_bxx_slots_back1), getString(R.string.pref_rgb_bxx_slots_thm), getString(R.string.pref_rgb_bxx_slots_thp),
                getString(R.string.def_rgb_bxx_slots_main), getString(R.string.def_rgb_bxx_slots_back1), getString(R.string.def_rgb_bxx_slots_thm), getString(R.string.def_rgb_bxx_slots_thp)));

        key = getString(R.string.borders_brc_progress); mapAreas.put(key, new MyArea(key, Area.BRC_PROGRESS,
                getString(R.string.pref_cut_brc_progress_x1), getString(R.string.pref_cut_brc_progress_x2), getString(R.string.pref_cut_brc_progress_y1), getString(R.string.pref_cut_brc_progress_y2),
                getString(R.string.def_cut_brc_progress_x1), getString(R.string.def_cut_brc_progress_x2), getString(R.string.def_cut_brc_progress_y1), getString(R.string.def_cut_brc_progress_y2),
                getString(R.string.pref_rgb_bxx_progress_our_main), getString(R.string.pref_rgb_bxx_progress_our_back1), getString(R.string.pref_rgb_bxx_progress_our_thm), getString(R.string.pref_rgb_bxx_progress_our_thp),
                getString(R.string.def_rgb_bxx_progress_our_main), getString(R.string.def_rgb_bxx_progress_our_back1), getString(R.string.def_rgb_bxx_progress_our_thm), getString(R.string.def_rgb_bxx_progress_our_thp)));


        key = getString(R.string.borders_brb); mapAreas.put(key, new MyArea(key, Area.BRB_AREA,
                getString(R.string.pref_cut_brb_x1), getString(R.string.pref_cut_brb_x2), getString(R.string.pref_cut_brb_y1), getString(R.string.pref_cut_brb_y2),
                getString(R.string.def_cut_brb_x1), getString(R.string.def_cut_brb_x2), getString(R.string.def_cut_brb_y1), getString(R.string.def_cut_brb_y2),
                getString(R.string.pref_rgb_bxx_main), getString(R.string.pref_rgb_bxx_back1), getString(R.string.pref_rgb_bxx_thm), getString(R.string.pref_rgb_bxx_thp),
                getString(R.string.def_rgb_bxx_main), getString(R.string.def_rgb_bxx_back1), getString(R.string.def_rgb_bxx_thm), getString(R.string.def_rgb_bxx_thp)));

        key = getString(R.string.borders_brb_name); mapAreas.put(key, new MyArea(key, Area.BRB,
                getString(R.string.pref_cut_brb_name_x1), getString(R.string.pref_cut_brb_name_x2), getString(R.string.pref_cut_brb_name_y1), getString(R.string.pref_cut_brb_name_y2),
                getString(R.string.def_cut_brb_name_x1), getString(R.string.def_cut_brb_name_x2), getString(R.string.def_cut_brb_name_y1), getString(R.string.def_cut_brb_name_y2),
                getString(R.string.pref_rgb_bxx_name_main), getString(R.string.pref_rgb_bxx_name_back1), getString(R.string.pref_rgb_bxx_name_thm), getString(R.string.pref_rgb_bxx_name_thp),
                getString(R.string.def_rgb_bxx_name_our_main), getString(R.string.def_rgb_bxx_name_our_back1), getString(R.string.def_rgb_bxx_name_our_thm), getString(R.string.def_rgb_bxx_name_our_thp)));

//        key = getString(R.string.borders_brb_increase); mapAreas.put(key, new MyArea(key, Area.BRB_POINTS,
//                getString(R.string.pref_cut_brb_increase_x1), getString(R.string.pref_cut_brb_increase_x2), getString(R.string.pref_cut_brb_increase_y1), getString(R.string.pref_cut_brb_increase_y2),
//                getString(R.string.def_cut_brb_points_x1), getString(R.string.def_cut_brb_points_x2), getString(R.string.def_cut_brb_points_y1), getString(R.string.def_cut_brb_points_y2),
//                getString(R.string.pref_rgb_bxx_increase_main), getString(R.string.pref_rgb_bxx_increase_back1), getString(R.string.pref_rgb_bxx_increase_thm), getString(R.string.pref_rgb_bxx_increase_thp),
//                getString(R.string.def_rgb_bxx_points_our_main), getString(R.string.def_rgb_bxx_points_our_back1), getString(R.string.def_rgb_bxx_points_our_thm), getString(R.string.def_rgb_bxx_points_our_thp)));

        key = getString(R.string.borders_brb_slots); mapAreas.put(key, new MyArea(key, Area.BRB_SLOTS,
                getString(R.string.pref_cut_brb_slots_x1), getString(R.string.pref_cut_brb_slots_x2), getString(R.string.pref_cut_brb_slots_y1), getString(R.string.pref_cut_brb_slots_y2),
                getString(R.string.def_cut_brb_slots_x1), getString(R.string.def_cut_brb_slots_x2), getString(R.string.def_cut_brb_slots_y1), getString(R.string.def_cut_brb_slots_y2),
                getString(R.string.pref_rgb_bxx_slots_main), getString(R.string.pref_rgb_bxx_slots_back1), getString(R.string.pref_rgb_bxx_slots_thm), getString(R.string.pref_rgb_bxx_slots_thp),
                getString(R.string.def_rgb_bxx_slots_main), getString(R.string.def_rgb_bxx_slots_back1), getString(R.string.def_rgb_bxx_slots_thm), getString(R.string.def_rgb_bxx_slots_thp)));

        key = getString(R.string.borders_brb_progress); mapAreas.put(key, new MyArea(key, Area.BRB_PROGRESS,
                getString(R.string.pref_cut_brb_progress_x1), getString(R.string.pref_cut_brb_progress_x2), getString(R.string.pref_cut_brb_progress_y1), getString(R.string.pref_cut_brb_progress_y2),
                getString(R.string.def_cut_brb_progress_x1), getString(R.string.def_cut_brb_progress_x2), getString(R.string.def_cut_brb_progress_y1), getString(R.string.def_cut_brb_progress_y2),
                getString(R.string.pref_rgb_bxx_progress_our_main), getString(R.string.pref_rgb_bxx_progress_our_back1), getString(R.string.pref_rgb_bxx_progress_our_thm), getString(R.string.pref_rgb_bxx_progress_our_thp),
                getString(R.string.def_rgb_bxx_progress_our_main), getString(R.string.def_rgb_bxx_progress_our_back1), getString(R.string.def_rgb_bxx_progress_our_thm), getString(R.string.def_rgb_bxx_progress_our_thp)));

    }
    
    
    public void loadArea() {

        cityCalcArea = mainCityCalc.getMapAreas().get(mapAreas.get(areaName.get()).getArea());

        color_THM = cityCalcArea.getThs()[0];
        color_THP = cityCalcArea.getThs()[1];
        int value = cityCalcArea.getColors()[0];
        color_RGB = value;
        color_R = Color.red(value);
        color_G = Color.green(value);
        color_B = Color.blue(value);
        
        tvR.setText(String.valueOf(color_R));
        tvG.setText(String.valueOf(color_G));
        tvB.setText(String.valueOf(color_B));
        tvTHM.setText(String.valueOf(color_THM));
        tvTHP.setText(String.valueOf(color_THP));

        etRGB.setText(Integer.toHexString(color_RGB).toUpperCase());


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorsdetect);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        if (findViewById(R.id.frame_colors) != null) {
            if (savedInstanceState != null) return;
            getFragmentManager().beginTransaction().add(R.id.frame_colors, new ColorsdetectActivity.ColorsFragment()).commit();

        }
        PreferenceManager.setDefaultValues(this, R.xml.pref_colors, true);

        tvR = findViewById(R.id.tv_R);
        tvG = findViewById(R.id.tv_G);
        tvB = findViewById(R.id.tv_B);
        etRGB = findViewById(R.id.et_RGB);
        tvTHM = findViewById(R.id.tv_THM);
        tvTHP = findViewById(R.id.tv_THP);
        tvColor = findViewById(R.id.tv_color);
        ivOriginal = findViewById(R.id.iv_original_image);
        ivProcessed = findViewById(R.id.iv_processed_image);
        tvRecognize = findViewById(R.id.tv_recognized);
        tvFinal = findViewById(R.id.tv_final);

        mainCityCalc = new CityCalc(GameActivity.fileLast, GameActivity.calibrateX, GameActivity.calibrateY, GameActivity.context, CityCalcType.COLORS);

        initMap();

        fullBitmap = BitmapFactory.decodeFile(GameActivity.fileGameScreenshot.getAbsolutePath());   // получаем битмап из файла скриншота

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        areaName.set(sharedPreferences.getString(PREF_COLORS_AREA, String.valueOf(R.string.pref_colorsAreaName_default_value)));

        try {
            cityCalcArea = mainCityCalc.getMapAreas().get(mapAreas.get(areaName.get()).getArea());
        } catch (Exception e) {
            areaName.set("Time");
            cityCalcArea = mainCityCalc.getMapAreas().get(mapAreas.get(areaName.get()).getArea());
        }

        areaName.setOnStringChangeListener(new OnStringChangeListener()
        {
            @Override
            public void onStringChanged(String newValue)
            {
                loadArea();
            }
        });



        etRGB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    color_RGB = (int) Long.parseLong(s.toString(), 16);

                    color_R = Color.red(color_RGB);
                    color_G = Color.green(color_RGB);
                    color_B = Color.blue(color_RGB);

                    tvR.setText(String.valueOf(color_R));
                    tvG.setText(String.valueOf(color_G));
                    tvB.setText(String.valueOf(color_B));

                    color_ARGB = (color_A << 24) | (color_R << 16) | (color_G << 8) | color_B;

                    tvColor.setTextColor(color_ARGB);

                    SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    cityCalcArea = mainCityCalc.getMapAreas().get(mapAreas.get(areaName.get()).getArea());
                    editor.putInt(mapAreas.get(areaName.get()).getPrefMainColor(), color_RGB);

                    editor.apply();
                    cityCalcArea.getColors()[0] = color_RGB;
                    cityCalcArea.doOCR();

                    originalBitmap = cityCalcArea.getBmpSrc();
                    processedBitmap = cityCalcArea.getBmpPrc();
                    ivOriginal.setImageBitmap(originalBitmap);
                    ivProcessed.setImageBitmap(processedBitmap);
                    tvRecognize.setText(cityCalcArea.getOcrText());
                    tvFinal.setText(cityCalcArea.getFinText());

                } catch (NumberFormatException e) {
                }
            }
        });

        loadArea();

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
    
    public void colorRminus(View view) {
        if (color_R > 0) color_R--;
        color_RGB = (color_R << 16) | (color_G << 8) | color_B;
        etRGB.setText(Integer.toHexString(color_RGB).toUpperCase());
    }

    public void colorRplus(View view) {
        if (color_R < 255 ) color_R++;
        color_RGB = (color_R << 16) | (color_G << 8) | color_B;
        etRGB.setText(Integer.toHexString(color_RGB).toUpperCase());
    }

    public void colorGminus(View view) {
        if (color_G > 0) color_G--;
        color_RGB = (color_R << 16) | (color_G << 8) | color_B;
        etRGB.setText(Integer.toHexString(color_RGB).toUpperCase());
    }

    public void colorGplus(View view) {
        if (color_G < 255 ) color_G++;
        color_RGB = (color_R << 16) | (color_G << 8) | color_B;
        etRGB.setText(Integer.toHexString(color_RGB).toUpperCase());
    }

    public void colorBminus(View view) {
        if (color_B > 0) color_B--;
        color_RGB = (color_R << 16) | (color_G << 8) | color_B;
        etRGB.setText(Integer.toHexString(color_RGB).toUpperCase());
    }

    public void colorBplus(View view) {
        if (color_B < 255 ) color_B++;
        color_RGB = (color_R << 16) | (color_G << 8) | color_B;
        etRGB.setText(Integer.toHexString(color_RGB).toUpperCase());
    }

    public void updateTHM() {

        SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(mapAreas.get(areaName.get()).getPrefTHM(), color_THM);

        cityCalcArea.getThs()[0] = color_THM;
        editor.apply();
        
        cityCalcArea.doOCR();

        originalBitmap = cityCalcArea.getBmpSrc();
        processedBitmap = cityCalcArea.getBmpPrc();
        ivOriginal.setImageBitmap(originalBitmap);
        ivProcessed.setImageBitmap(processedBitmap);
        tvRecognize.setText(cityCalcArea.getOcrText());

    }

    public void updateTHP() {

        SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(mapAreas.get(areaName.get()).getPrefTHP(), color_THP);

        cityCalcArea.getThs()[1] = color_THP;
        editor.apply();

        cityCalcArea.doOCR();

        originalBitmap = cityCalcArea.getBmpSrc();
        processedBitmap = cityCalcArea.getBmpPrc();
        ivOriginal.setImageBitmap(originalBitmap);
        ivProcessed.setImageBitmap(processedBitmap);
        tvRecognize.setText(cityCalcArea.getOcrText());


    }
    
    public void colorTHMminus(View view) {
        if (color_THM > 0) color_THM--;
        tvTHM.setText(String.valueOf(color_THM));
        updateTHM();
        
    }

    public void colorTHMplus(View view) {
        color_THM++;
        tvTHM.setText(String.valueOf(color_THM));
        updateTHM();
    }

    public void colorTHPminus(View view) {
        if (color_THP > 0) color_THP--;
        tvTHP.setText(String.valueOf(color_THP));
        updateTHP();
    }

    public void colorTHPplus(View view) {
        color_THP++;
        tvTHP.setText(String.valueOf(color_THP));
        updateTHP();
    }

    public void setDefaultColor(View view) {


        int value = 0;

        color_THM = Integer.parseInt(mapAreas.get(areaName.get()).getDefTHM());
        color_THP = Integer.parseInt(mapAreas.get(areaName.get()).getDefTHP());
        value = (int)Long.parseLong((mapAreas.get(areaName.get()).getDefMainColor()),16);

        color_RGB = value;
        color_R = Color.red(value);
        color_G = Color.green(value);
        color_B = Color.blue(value);

        tvR.setText(String.valueOf(color_R));
        tvG.setText(String.valueOf(color_G));
        tvB.setText(String.valueOf(color_B));
        tvTHM.setText(String.valueOf(color_THM));
        tvTHP.setText(String.valueOf(color_THP));

        etRGB.setText(Integer.toHexString(color_RGB).toUpperCase());

    }


    public static class ColorsFragment extends PreferenceFragment {

        public static final String PREF_COLORS_AREA = "pref_colors_area";
        private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_colors);

            onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals(PREF_COLORS_AREA)) {
                        Preference preference = findPreference(key);
                        preference.setSummary(sharedPreferences.getString(key, String.valueOf(R.string.pref_colorsAreaName_default_value)));
                        ColorsdetectActivity.areaName.set(sharedPreferences.getString(key, String.valueOf(R.string.pref_colorsAreaName_default_value)));
                    }
                }
            };

        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            Preference preference = findPreference(PREF_COLORS_AREA);
            preference.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_COLORS_AREA, String.valueOf(R.string.pref_colorsAreaName_default_value)));
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        }
    }

}
