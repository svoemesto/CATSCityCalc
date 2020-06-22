package com.svoemestodev.catscitycalc.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.svoemestodev.catscitycalc.citycalcclasses.Area;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalc;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalcArea;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalcType;
import com.svoemestodev.catscitycalc.utils.ColorFrequency;
import com.svoemestodev.catscitycalc.citycalcclasses.MyArea;
import com.svoemestodev.catscitycalc.utils.ObservableString;
import com.svoemestodev.catscitycalc.utils.OnStringChangeListener;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.utils.Utils;

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

    public static Map<String, MyArea> mapAreas = new HashMap<>();

    public static List<ColorFrequency> colorFrequencyList;

    private void initMap() {
        mapAreas = new HashMap<>();
        String key;

        key = getString(R.string.borders_box_info_city); mapAreas.put(key, new MyArea(key, Area.BOX_INFO_CITY,
                getString(R.string.pref_cut_box_info_city_x1), getString(R.string.pref_cut_box_info_city_x2), getString(R.string.pref_cut_box_info_city_y1), getString(R.string.pref_cut_box_info_city_y2),
                getString(R.string.def_cut_box_info_city_x1), getString(R.string.def_cut_box_info_city_x2), getString(R.string.def_cut_box_info_city_y1), getString(R.string.def_cut_box_info_city_y2),
                getString(R.string.pref_rgb_box_info_city_main), getString(R.string.pref_rgb_box_info_city_back1), getString(R.string.pref_rgb_box_info_city_thm), getString(R.string.pref_rgb_box_info_city_thp),
                getString(R.string.def_rgb_box_info_city_main), getString(R.string.def_rgb_box_info_city_back1), getString(R.string.def_rgb_box_info_city_thm), getString(R.string.def_rgb_box_info_city_thp)));

        key = getString(R.string.borders_box_info_car); mapAreas.put(key, new MyArea(key, Area.BOX_INFO_CAR,
                getString(R.string.pref_cut_box_info_car_x1), getString(R.string.pref_cut_box_info_car_x2), getString(R.string.pref_cut_box_info_car_y1), getString(R.string.pref_cut_box_info_car_y2),
                getString(R.string.def_cut_box_info_car_x1), getString(R.string.def_cut_box_info_car_x2), getString(R.string.def_cut_box_info_car_y1), getString(R.string.def_cut_box_info_car_y2),
                getString(R.string.pref_rgb_box_info_car_main), getString(R.string.pref_rgb_box_info_car_back1), getString(R.string.pref_rgb_box_info_car_thm), getString(R.string.pref_rgb_box_info_car_thp),
                getString(R.string.def_rgb_box_info_car_main), getString(R.string.def_rgb_box_info_car_back1), getString(R.string.def_rgb_box_info_car_thm), getString(R.string.def_rgb_box_info_car_thp)));

        key = getString(R.string.borders_box_info_garage); mapAreas.put(key, new MyArea(key, Area.BOX_INFO_GARAGE,
                getString(R.string.pref_cut_box_info_garage_x1), getString(R.string.pref_cut_box_info_garage_x2), getString(R.string.pref_cut_box_info_garage_y1), getString(R.string.pref_cut_box_info_garage_y2),
                getString(R.string.def_cut_box_info_garage_x1), getString(R.string.def_cut_box_info_garage_x2), getString(R.string.def_cut_box_info_garage_y1), getString(R.string.def_cut_box_info_garage_y2),
                getString(R.string.pref_rgb_box_info_garage_main), getString(R.string.pref_rgb_box_info_garage_back1), getString(R.string.pref_rgb_box_info_garage_thm), getString(R.string.pref_rgb_box_info_garage_thp),
                getString(R.string.def_rgb_box_info_garage_main), getString(R.string.def_rgb_box_info_garage_back1), getString(R.string.def_rgb_box_info_garage_thm), getString(R.string.def_rgb_box_info_garage_thp)));

        key = getString(R.string.borders_box_back); mapAreas.put(key, new MyArea(key, Area.BOX_BACK,
                getString(R.string.pref_cut_box_back_x1), getString(R.string.pref_cut_box_back_x2), getString(R.string.pref_cut_box_back_y1), getString(R.string.pref_cut_box_back_y2),
                getString(R.string.def_cut_box_back_x1), getString(R.string.def_cut_box_back_x2), getString(R.string.def_cut_box_back_y1), getString(R.string.def_cut_box_back_y2),
                getString(R.string.pref_rgb_box_back_main), getString(R.string.pref_rgb_box_back_back1), getString(R.string.pref_rgb_box_back_thm), getString(R.string.pref_rgb_box_back_thp),
                getString(R.string.def_rgb_box_back_main), getString(R.string.def_rgb_box_back_back1), getString(R.string.def_rgb_box_back_thm), getString(R.string.def_rgb_box_back_thp)));
        
        key = getString(R.string.borders_car_in_city_info); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_INFO,
                getString(R.string.pref_cut_car_in_city_info_x1), getString(R.string.pref_cut_car_in_city_info_x2), getString(R.string.pref_cut_car_in_city_info_y1), getString(R.string.pref_cut_car_in_city_info_y2),
                getString(R.string.def_cut_car_in_city_info_x1), getString(R.string.def_cut_car_in_city_info_x2), getString(R.string.def_cut_car_in_city_info_y1), getString(R.string.def_cut_car_in_city_info_y2),
                getString(R.string.pref_rgb_car_in_city_info_main), getString(R.string.pref_rgb_car_in_city_info_back1), getString(R.string.pref_rgb_car_in_city_info_thm), getString(R.string.pref_rgb_car_in_city_info_thp),
                getString(R.string.def_rgb_car_in_city_info_main), getString(R.string.def_rgb_car_in_city_info_back1), getString(R.string.def_rgb_car_in_city_info_thm), getString(R.string.def_rgb_car_in_city_info_thp)));

        key = getString(R.string.borders_car_in_city_building); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_BUILDING,
                getString(R.string.pref_cut_car_in_city_building_x1), getString(R.string.pref_cut_car_in_city_building_x2), getString(R.string.pref_cut_car_in_city_building_y1), getString(R.string.pref_cut_car_in_city_building_y2),
                getString(R.string.def_cut_car_in_city_building_x1), getString(R.string.def_cut_car_in_city_building_x2), getString(R.string.def_cut_car_in_city_building_y1), getString(R.string.def_cut_car_in_city_building_y2),
                getString(R.string.pref_rgb_car_in_city_building_main), getString(R.string.pref_rgb_car_in_city_building_back1), getString(R.string.pref_rgb_car_in_city_building_thm), getString(R.string.pref_rgb_car_in_city_building_thp),
                getString(R.string.def_rgb_car_in_city_building_main), getString(R.string.def_rgb_car_in_city_building_back1), getString(R.string.def_rgb_car_in_city_building_thm), getString(R.string.def_rgb_car_in_city_building_thp)));
        
        key = getString(R.string.borders_car_in_city_picture); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_PICTURE,
                getString(R.string.pref_cut_car_in_city_picture_x1), getString(R.string.pref_cut_car_in_city_picture_x2), getString(R.string.pref_cut_car_in_city_picture_y1), getString(R.string.pref_cut_car_in_city_picture_y2),
                getString(R.string.def_cut_car_in_city_picture_x1), getString(R.string.def_cut_car_in_city_picture_x2), getString(R.string.def_cut_car_in_city_picture_y1), getString(R.string.def_cut_car_in_city_picture_y2),
                getString(R.string.pref_rgb_car_in_city_picture_main), getString(R.string.pref_rgb_car_in_city_picture_back1), getString(R.string.pref_rgb_car_in_city_picture_thm), getString(R.string.pref_rgb_car_in_city_picture_thp),
                getString(R.string.def_rgb_car_in_city_picture_main), getString(R.string.def_rgb_car_in_city_picture_back1), getString(R.string.def_rgb_car_in_city_picture_thm), getString(R.string.def_rgb_car_in_city_picture_thp)));

        key = getString(R.string.borders_car_in_garage_picture); mapAreas.put(key, new MyArea(key, Area.CAR_IN_GARAGE_PICTURE,
                getString(R.string.pref_cut_car_in_garage_picture_x1), getString(R.string.pref_cut_car_in_garage_picture_x2), getString(R.string.pref_cut_car_in_garage_picture_y1), getString(R.string.pref_cut_car_in_garage_picture_y2),
                getString(R.string.def_cut_car_in_garage_picture_x1), getString(R.string.def_cut_car_in_garage_picture_x2), getString(R.string.def_cut_car_in_garage_picture_y1), getString(R.string.def_cut_car_in_garage_picture_y2),
                getString(R.string.pref_rgb_car_in_garage_picture_main), getString(R.string.pref_rgb_car_in_garage_picture_back1), getString(R.string.pref_rgb_car_in_garage_picture_thm), getString(R.string.pref_rgb_car_in_garage_picture_thp),
                getString(R.string.def_rgb_car_in_garage_picture_main), getString(R.string.def_rgb_car_in_garage_picture_back1), getString(R.string.def_rgb_car_in_garage_picture_thm), getString(R.string.def_rgb_car_in_garage_picture_thp)));
        
        key = getString(R.string.borders_car_in_city_box1); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_BOX1,
                getString(R.string.pref_cut_car_in_city_box1_x1), getString(R.string.pref_cut_car_in_city_box1_x2), getString(R.string.pref_cut_car_in_city_box1_y1), getString(R.string.pref_cut_car_in_city_box1_y2),
                getString(R.string.def_cut_car_in_city_box1_x1), getString(R.string.def_cut_car_in_city_box1_x2), getString(R.string.def_cut_car_in_city_box1_y1), getString(R.string.def_cut_car_in_city_box1_y2),
                getString(R.string.pref_rgb_car_in_city_box1_main), getString(R.string.pref_rgb_car_in_city_box1_back1), getString(R.string.pref_rgb_car_in_city_box1_thm), getString(R.string.pref_rgb_car_in_city_box1_thp),
                getString(R.string.def_rgb_car_in_city_box1_main), getString(R.string.def_rgb_car_in_city_box1_back1), getString(R.string.def_rgb_car_in_city_box1_thm), getString(R.string.def_rgb_car_in_city_box1_thp)));

        key = getString(R.string.borders_car_in_city_box2); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_BOX2,
                getString(R.string.pref_cut_car_in_city_box2_x1), getString(R.string.pref_cut_car_in_city_box2_x2), getString(R.string.pref_cut_car_in_city_box2_y1), getString(R.string.pref_cut_car_in_city_box2_y2),
                getString(R.string.def_cut_car_in_city_box2_x1), getString(R.string.def_cut_car_in_city_box2_x2), getString(R.string.def_cut_car_in_city_box2_y1), getString(R.string.def_cut_car_in_city_box2_y2),
                getString(R.string.pref_rgb_car_in_city_box2_main), getString(R.string.pref_rgb_car_in_city_box2_back1), getString(R.string.pref_rgb_car_in_city_box2_thm), getString(R.string.pref_rgb_car_in_city_box2_thp),
                getString(R.string.def_rgb_car_in_city_box2_main), getString(R.string.def_rgb_car_in_city_box2_back1), getString(R.string.def_rgb_car_in_city_box2_thm), getString(R.string.def_rgb_car_in_city_box2_thp)));
        
        key = getString(R.string.borders_car_in_city_slot1); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_SLOT1,
                getString(R.string.pref_cut_car_in_city_slot1_x1), getString(R.string.pref_cut_car_in_city_slot1_x2), getString(R.string.pref_cut_car_in_city_slot1_y1), getString(R.string.pref_cut_car_in_city_slot1_y2),
                getString(R.string.def_cut_car_in_city_slot1_x1), getString(R.string.def_cut_car_in_city_slot1_x2), getString(R.string.def_cut_car_in_city_slot1_y1), getString(R.string.def_cut_car_in_city_slot1_y2),
                getString(R.string.pref_rgb_car_in_city_slot_main), getString(R.string.pref_rgb_car_in_city_slot_back1), getString(R.string.pref_rgb_car_in_city_slot_thm), getString(R.string.pref_rgb_car_in_city_slot_thp),
                getString(R.string.def_rgb_car_in_city_slot_main), getString(R.string.def_rgb_car_in_city_slot_back1), getString(R.string.def_rgb_car_in_city_slot_thm), getString(R.string.def_rgb_car_in_city_slot_thp)));

        key = getString(R.string.borders_car_in_city_slot2); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_SLOT2,
                getString(R.string.pref_cut_car_in_city_slot2_x1), getString(R.string.pref_cut_car_in_city_slot2_x2), getString(R.string.pref_cut_car_in_city_slot2_y1), getString(R.string.pref_cut_car_in_city_slot2_y2),
                getString(R.string.def_cut_car_in_city_slot2_x1), getString(R.string.def_cut_car_in_city_slot2_x2), getString(R.string.def_cut_car_in_city_slot2_y1), getString(R.string.def_cut_car_in_city_slot2_y2),
                getString(R.string.pref_rgb_car_in_city_slot_main), getString(R.string.pref_rgb_car_in_city_slot_back1), getString(R.string.pref_rgb_car_in_city_slot_thm), getString(R.string.pref_rgb_car_in_city_slot_thp),
                getString(R.string.def_rgb_car_in_city_slot_main), getString(R.string.def_rgb_car_in_city_slot_back1), getString(R.string.def_rgb_car_in_city_slot_thm), getString(R.string.def_rgb_car_in_city_slot_thp)));

        key = getString(R.string.borders_car_in_city_slot3); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_SLOT3,
                getString(R.string.pref_cut_car_in_city_slot3_x1), getString(R.string.pref_cut_car_in_city_slot3_x2), getString(R.string.pref_cut_car_in_city_slot3_y1), getString(R.string.pref_cut_car_in_city_slot3_y2),
                getString(R.string.def_cut_car_in_city_slot3_x1), getString(R.string.def_cut_car_in_city_slot3_x2), getString(R.string.def_cut_car_in_city_slot3_y1), getString(R.string.def_cut_car_in_city_slot3_y2),
                getString(R.string.pref_rgb_car_in_city_slot_main), getString(R.string.pref_rgb_car_in_city_slot_back1), getString(R.string.pref_rgb_car_in_city_slot_thm), getString(R.string.pref_rgb_car_in_city_slot_thp),
                getString(R.string.def_rgb_car_in_city_slot_main), getString(R.string.def_rgb_car_in_city_slot_back1), getString(R.string.def_rgb_car_in_city_slot_thm), getString(R.string.def_rgb_car_in_city_slot_thp)));

        key = getString(R.string.borders_car_in_garage_slot1); mapAreas.put(key, new MyArea(key, Area.CAR_IN_GARAGE_SLOT1,
                getString(R.string.pref_cut_car_in_garage_slot1_x1), getString(R.string.pref_cut_car_in_garage_slot1_x2), getString(R.string.pref_cut_car_in_garage_slot1_y1), getString(R.string.pref_cut_car_in_garage_slot1_y2),
                getString(R.string.def_cut_car_in_garage_slot1_x1), getString(R.string.def_cut_car_in_garage_slot1_x2), getString(R.string.def_cut_car_in_garage_slot1_y1), getString(R.string.def_cut_car_in_garage_slot1_y2),
                getString(R.string.pref_rgb_car_in_garage_slot_main), getString(R.string.pref_rgb_car_in_garage_slot_back1), getString(R.string.pref_rgb_car_in_garage_slot_thm), getString(R.string.pref_rgb_car_in_garage_slot_thp),
                getString(R.string.def_rgb_car_in_garage_slot_main), getString(R.string.def_rgb_car_in_garage_slot_back1), getString(R.string.def_rgb_car_in_garage_slot_thm), getString(R.string.def_rgb_car_in_garage_slot_thp)));

        key = getString(R.string.borders_car_in_garage_slot2); mapAreas.put(key, new MyArea(key, Area.CAR_IN_GARAGE_SLOT2,
                getString(R.string.pref_cut_car_in_garage_slot2_x1), getString(R.string.pref_cut_car_in_garage_slot2_x2), getString(R.string.pref_cut_car_in_garage_slot2_y1), getString(R.string.pref_cut_car_in_garage_slot2_y2),
                getString(R.string.def_cut_car_in_garage_slot2_x1), getString(R.string.def_cut_car_in_garage_slot2_x2), getString(R.string.def_cut_car_in_garage_slot2_y1), getString(R.string.def_cut_car_in_garage_slot2_y2),
                getString(R.string.pref_rgb_car_in_garage_slot_main), getString(R.string.pref_rgb_car_in_garage_slot_back1), getString(R.string.pref_rgb_car_in_garage_slot_thm), getString(R.string.pref_rgb_car_in_garage_slot_thp),
                getString(R.string.def_rgb_car_in_garage_slot_main), getString(R.string.def_rgb_car_in_garage_slot_back1), getString(R.string.def_rgb_car_in_garage_slot_thm), getString(R.string.def_rgb_car_in_garage_slot_thp)));

        key = getString(R.string.borders_car_in_garage_slot3); mapAreas.put(key, new MyArea(key, Area.CAR_IN_GARAGE_SLOT3,
                getString(R.string.pref_cut_car_in_garage_slot3_x1), getString(R.string.pref_cut_car_in_garage_slot3_x2), getString(R.string.pref_cut_car_in_garage_slot3_y1), getString(R.string.pref_cut_car_in_garage_slot3_y2),
                getString(R.string.def_cut_car_in_garage_slot3_x1), getString(R.string.def_cut_car_in_garage_slot3_x2), getString(R.string.def_cut_car_in_garage_slot3_y1), getString(R.string.def_cut_car_in_garage_slot3_y2),
                getString(R.string.pref_rgb_car_in_garage_slot_main), getString(R.string.pref_rgb_car_in_garage_slot_back1), getString(R.string.pref_rgb_car_in_garage_slot_thm), getString(R.string.pref_rgb_car_in_garage_slot_thp),
                getString(R.string.def_rgb_car_in_garage_slot_main), getString(R.string.def_rgb_car_in_garage_slot_back1), getString(R.string.def_rgb_car_in_garage_slot_thm), getString(R.string.def_rgb_car_in_garage_slot_thp)));
        
        key = getString(R.string.borders_car_in_city_health); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_HEALTH,
                getString(R.string.pref_cut_car_in_city_health_x1), getString(R.string.pref_cut_car_in_city_health_x2), getString(R.string.pref_cut_car_in_city_health_y1), getString(R.string.pref_cut_car_in_city_health_y2),
                getString(R.string.def_cut_car_in_city_health_x1), getString(R.string.def_cut_car_in_city_health_x2), getString(R.string.def_cut_car_in_city_health_y1), getString(R.string.def_cut_car_in_city_health_y2),
                getString(R.string.pref_rgb_car_in_city_health_main), getString(R.string.pref_rgb_car_in_city_health_back1), getString(R.string.pref_rgb_car_in_city_health_thm), getString(R.string.pref_rgb_car_in_city_health_thp),
                getString(R.string.def_rgb_car_in_city_health_main), getString(R.string.def_rgb_car_in_city_health_back1), getString(R.string.def_rgb_car_in_city_health_thm), getString(R.string.def_rgb_car_in_city_health_thp)));

        key = getString(R.string.borders_car_in_city_shield); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_SHIELD,
                getString(R.string.pref_cut_car_in_city_shield_x1), getString(R.string.pref_cut_car_in_city_shield_x2), getString(R.string.pref_cut_car_in_city_shield_y1), getString(R.string.pref_cut_car_in_city_shield_y2),
                getString(R.string.def_cut_car_in_city_shield_x1), getString(R.string.def_cut_car_in_city_shield_x2), getString(R.string.def_cut_car_in_city_shield_y1), getString(R.string.def_cut_car_in_city_shield_y2),
                getString(R.string.pref_rgb_car_in_city_shield_main), getString(R.string.pref_rgb_car_in_city_shield_back1), getString(R.string.pref_rgb_car_in_city_shield_thm), getString(R.string.pref_rgb_car_in_city_shield_thp),
                getString(R.string.def_rgb_car_in_city_shield_main), getString(R.string.def_rgb_car_in_city_shield_back1), getString(R.string.def_rgb_car_in_city_shield_thm), getString(R.string.def_rgb_car_in_city_shield_thp)));

        key = getString(R.string.borders_car_in_garage_health); mapAreas.put(key, new MyArea(key, Area.CAR_IN_GARAGE_HEALTH,
                getString(R.string.pref_cut_car_in_garage_health_x1), getString(R.string.pref_cut_car_in_garage_health_x2), getString(R.string.pref_cut_car_in_garage_health_y1), getString(R.string.pref_cut_car_in_garage_health_y2),
                getString(R.string.def_cut_car_in_garage_health_x1), getString(R.string.def_cut_car_in_garage_health_x2), getString(R.string.def_cut_car_in_garage_health_y1), getString(R.string.def_cut_car_in_garage_health_y2),
                getString(R.string.pref_rgb_car_in_garage_health_main), getString(R.string.pref_rgb_car_in_garage_health_back1), getString(R.string.pref_rgb_car_in_garage_health_thm), getString(R.string.pref_rgb_car_in_garage_health_thp),
                getString(R.string.def_rgb_car_in_garage_health_main), getString(R.string.def_rgb_car_in_garage_health_back1), getString(R.string.def_rgb_car_in_garage_health_thm), getString(R.string.def_rgb_car_in_garage_health_thp)));

        key = getString(R.string.borders_car_in_garage_shield); mapAreas.put(key, new MyArea(key, Area.CAR_IN_GARAGE_SHIELD,
                getString(R.string.pref_cut_car_in_garage_shield_x1), getString(R.string.pref_cut_car_in_garage_shield_x2), getString(R.string.pref_cut_car_in_garage_shield_y1), getString(R.string.pref_cut_car_in_garage_shield_y2),
                getString(R.string.def_cut_car_in_garage_shield_x1), getString(R.string.def_cut_car_in_garage_shield_x2), getString(R.string.def_cut_car_in_garage_shield_y1), getString(R.string.def_cut_car_in_garage_shield_y2),
                getString(R.string.pref_rgb_car_in_garage_shield_main), getString(R.string.pref_rgb_car_in_garage_shield_back1), getString(R.string.pref_rgb_car_in_garage_shield_thm), getString(R.string.pref_rgb_car_in_garage_shield_thp),
                getString(R.string.def_rgb_car_in_garage_shield_main), getString(R.string.def_rgb_car_in_garage_shield_back1), getString(R.string.def_rgb_car_in_garage_shield_thm), getString(R.string.def_rgb_car_in_garage_shield_thp)));
        
        key = getString(R.string.borders_car_in_city_state); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_STATE,
                getString(R.string.pref_cut_car_in_city_state_x1), getString(R.string.pref_cut_car_in_city_state_x2), getString(R.string.pref_cut_car_in_city_state_y1), getString(R.string.pref_cut_car_in_city_state_y2),
                getString(R.string.def_cut_car_in_city_state_x1), getString(R.string.def_cut_car_in_city_state_x2), getString(R.string.def_cut_car_in_city_state_y1), getString(R.string.def_cut_car_in_city_state_y2),
                getString(R.string.pref_rgb_car_in_city_state_main), getString(R.string.pref_rgb_car_in_city_state_back1), getString(R.string.pref_rgb_car_in_city_state_thm), getString(R.string.pref_rgb_car_in_city_state_thp),
                getString(R.string.def_rgb_car_in_city_state_main), getString(R.string.def_rgb_car_in_city_state_back1), getString(R.string.def_rgb_car_in_city_state_thm), getString(R.string.def_rgb_car_in_city_state_thp)));

        key = getString(R.string.borders_car_in_city_healbox); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_HEALBOX,
                getString(R.string.pref_cut_car_in_city_healbox_x1), getString(R.string.pref_cut_car_in_city_healbox_x2), getString(R.string.pref_cut_car_in_city_healbox_y1), getString(R.string.pref_cut_car_in_city_healbox_y2),
                getString(R.string.def_cut_car_in_city_healbox_x1), getString(R.string.def_cut_car_in_city_healbox_x2), getString(R.string.def_cut_car_in_city_healbox_y1), getString(R.string.def_cut_car_in_city_healbox_y2),
                getString(R.string.pref_rgb_car_in_city_healbox_main), getString(R.string.pref_rgb_car_in_city_healbox_back1), getString(R.string.pref_rgb_car_in_city_healbox_thm), getString(R.string.pref_rgb_car_in_city_healbox_thp),
                getString(R.string.def_rgb_car_in_city_healbox_main), getString(R.string.def_rgb_car_in_city_healbox_back1), getString(R.string.def_rgb_car_in_city_healbox_thm), getString(R.string.def_rgb_car_in_city_healbox_thp)));

        key = getString(R.string.borders_car_in_city_timebox1); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_TIMEBOX1,
                getString(R.string.pref_cut_car_in_city_timebox1_x1), getString(R.string.pref_cut_car_in_city_timebox1_x2), getString(R.string.pref_cut_car_in_city_timebox1_y1), getString(R.string.pref_cut_car_in_city_timebox1_y2),
                getString(R.string.def_cut_car_in_city_timebox1_x1), getString(R.string.def_cut_car_in_city_timebox1_x2), getString(R.string.def_cut_car_in_city_timebox1_y1), getString(R.string.def_cut_car_in_city_timebox1_y2),
                getString(R.string.pref_rgb_car_in_city_timebox1_main), getString(R.string.pref_rgb_car_in_city_timebox1_back1), getString(R.string.pref_rgb_car_in_city_timebox1_thm), getString(R.string.pref_rgb_car_in_city_timebox1_thp),
                getString(R.string.def_rgb_car_in_city_timebox1_main), getString(R.string.def_rgb_car_in_city_timebox1_back1), getString(R.string.def_rgb_car_in_city_timebox1_thm), getString(R.string.def_rgb_car_in_city_timebox1_thp)));

        key = getString(R.string.borders_car_in_city_timebox2); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_TIMEBOX2,
                getString(R.string.pref_cut_car_in_city_timebox2_x1), getString(R.string.pref_cut_car_in_city_timebox2_x2), getString(R.string.pref_cut_car_in_city_timebox2_y1), getString(R.string.pref_cut_car_in_city_timebox2_y2),
                getString(R.string.def_cut_car_in_city_timebox2_x1), getString(R.string.def_cut_car_in_city_timebox2_x2), getString(R.string.def_cut_car_in_city_timebox2_y1), getString(R.string.def_cut_car_in_city_timebox2_y2),
                getString(R.string.pref_rgb_car_in_city_timebox2_main), getString(R.string.pref_rgb_car_in_city_timebox2_back1), getString(R.string.pref_rgb_car_in_city_timebox2_thm), getString(R.string.pref_rgb_car_in_city_timebox2_thp),
                getString(R.string.def_rgb_car_in_city_timebox2_main), getString(R.string.def_rgb_car_in_city_timebox2_back1), getString(R.string.def_rgb_car_in_city_timebox2_thm), getString(R.string.def_rgb_car_in_city_timebox2_thp)));
        
        key = getString(R.string.borders_car_in_city_statebox1); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_STATEBOX1,
                getString(R.string.pref_cut_car_in_city_statebox1_x1), getString(R.string.pref_cut_car_in_city_statebox1_x2), getString(R.string.pref_cut_car_in_city_statebox1_y1), getString(R.string.pref_cut_car_in_city_statebox1_y2),
                getString(R.string.def_cut_car_in_city_statebox1_x1), getString(R.string.def_cut_car_in_city_statebox1_x2), getString(R.string.def_cut_car_in_city_statebox1_y1), getString(R.string.def_cut_car_in_city_statebox1_y2),
                getString(R.string.pref_rgb_car_in_city_statebox1_main), getString(R.string.pref_rgb_car_in_city_statebox1_back1), getString(R.string.pref_rgb_car_in_city_statebox1_thm), getString(R.string.pref_rgb_car_in_city_statebox1_thp),
                getString(R.string.def_rgb_car_in_city_statebox1_main), getString(R.string.def_rgb_car_in_city_statebox1_back1), getString(R.string.def_rgb_car_in_city_statebox1_thm), getString(R.string.def_rgb_car_in_city_statebox1_thp)));

        key = getString(R.string.borders_car_in_city_statebox2); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_STATEBOX2,
                getString(R.string.pref_cut_car_in_city_statebox2_x1), getString(R.string.pref_cut_car_in_city_statebox2_x2), getString(R.string.pref_cut_car_in_city_statebox2_y1), getString(R.string.pref_cut_car_in_city_statebox2_y2),
                getString(R.string.def_cut_car_in_city_statebox2_x1), getString(R.string.def_cut_car_in_city_statebox2_x2), getString(R.string.def_cut_car_in_city_statebox2_y1), getString(R.string.def_cut_car_in_city_statebox2_y2),
                getString(R.string.pref_rgb_car_in_city_statebox2_main), getString(R.string.pref_rgb_car_in_city_statebox2_back1), getString(R.string.pref_rgb_car_in_city_statebox2_thm), getString(R.string.pref_rgb_car_in_city_statebox2_thp),
                getString(R.string.def_rgb_car_in_city_statebox2_main), getString(R.string.def_rgb_car_in_city_statebox2_back1), getString(R.string.def_rgb_car_in_city_statebox2_thm), getString(R.string.def_rgb_car_in_city_statebox2_thp)));

        key = getString(R.string.borders_car_in_city_statebox3); mapAreas.put(key, new MyArea(key, Area.CAR_IN_CITY_STATEBOX3,
                getString(R.string.pref_cut_car_in_city_statebox3_x1), getString(R.string.pref_cut_car_in_city_statebox3_x2), getString(R.string.pref_cut_car_in_city_statebox3_y1), getString(R.string.pref_cut_car_in_city_statebox3_y2),
                getString(R.string.def_cut_car_in_city_statebox3_x1), getString(R.string.def_cut_car_in_city_statebox3_x2), getString(R.string.def_cut_car_in_city_statebox3_y1), getString(R.string.def_cut_car_in_city_statebox3_y2),
                getString(R.string.pref_rgb_car_in_city_statebox3_main), getString(R.string.pref_rgb_car_in_city_statebox3_back1), getString(R.string.pref_rgb_car_in_city_statebox3_thm), getString(R.string.pref_rgb_car_in_city_statebox3_thp),
                getString(R.string.def_rgb_car_in_city_statebox3_main), getString(R.string.def_rgb_car_in_city_statebox3_back1), getString(R.string.def_rgb_car_in_city_statebox3_thm), getString(R.string.def_rgb_car_in_city_statebox3_thp)));
        
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

    public void getFrequency(View view) {

        CityCalcArea cityCalcArea = mainCityCalc.getMapAreas().get(mapAreas.get(areaName.get()).getArea());
        colorFrequencyList = PictureProcessor.getFrequencyMap(cityCalcArea.getBmpSrc());
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

            cfl_stub.setTextColor(item.getColor());
            cfl_color.setText(Long.toHexString(item.getColor()).toUpperCase().substring(10));
            cfl_freq.setText(Utils.convertFloatToStringFormatter5digit(item.getFrequency()));

            return convertView;

        }

    }

    
    public void loadArea() {

//        cut_x1 = GameActivity.mapFloats.get();

        CityCalcArea cityCalcArea = null;

        try {
            cityCalcArea = mainCityCalc.getMapAreas().get(mapAreas.get(areaName.get()).getArea());
            cityCalcArea.doCut();
            cut_x1 = cityCalcArea.getX1();
            cut_x2 = cityCalcArea.getX2();
            cut_y1 = cityCalcArea.getY1();
            cut_y2 = cityCalcArea.getY2();

            etX1.setText(String.valueOf(cut_x1));
            etX2.setText(String.valueOf(cut_x2));
            etY1.setText(String.valueOf(cut_y1));
            etY2.setText(String.valueOf(cut_y2));
            ivPicture.setImageBitmap(cityCalcArea.getBmpSrc());  // выводим битмап игры в контрол

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("loadArea", areaName.get());
        }



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

//        mainCityCalc = new CityCalc(GameActivity.fileLastScreenshot, GameActivity.calibrateX, GameActivity.calibrateY, GameActivity.context, CityCalcType.BORDERS);
        mainCityCalc = new CityCalc(GameActivity.fileLastScreenshot, GameActivity.calibrateX, GameActivity.calibrateY, CityCalcType.BORDERS);

        initMap();
        
        fullBitmap = mainCityCalc.getBmpScreenshot();

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

                    CityCalcArea cityCalcArea = mainCityCalc.getMapAreas().get(mapAreas.get(areaName.get()).getArea());
                    editor.putFloat(mapAreas.get(areaName.get()).getPrefX1(), value);

                    editor.apply();
                    
                    cityCalcArea.setX1(value);
                    cityCalcArea.doCut();
                    ivPicture.setImageBitmap(cityCalcArea.getBmpSrc());

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

                    CityCalcArea cityCalcArea = mainCityCalc.getMapAreas().get(mapAreas.get(areaName.get()).getArea());
                    editor.putFloat(mapAreas.get(areaName.get()).getPrefX2(), value);

                    editor.apply();

                    cityCalcArea.setX2(value);
                    cityCalcArea.doCut();
                    ivPicture.setImageBitmap(cityCalcArea.getBmpSrc());
                    
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

                    CityCalcArea cityCalcArea = mainCityCalc.getMapAreas().get(mapAreas.get(areaName.get()).getArea());
                    editor.putFloat(mapAreas.get(areaName.get()).getPrefY1(), value);

                    editor.apply();

                    cityCalcArea.setY1(value);
                    cityCalcArea.doCut();
                    ivPicture.setImageBitmap(cityCalcArea.getBmpSrc());
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

                    CityCalcArea cityCalcArea = mainCityCalc.getMapAreas().get(mapAreas.get(areaName.get()).getArea());
                    editor.putFloat(mapAreas.get(areaName.get()).getPrefY2(), value);

                    editor.apply();

                    cityCalcArea.setY2(value);
                    cityCalcArea.doCut();
                    ivPicture.setImageBitmap(cityCalcArea.getBmpSrc());
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

        cut_x1 = Float.parseFloat(mapAreas.get(areaName.get()).getDefX1());
        etX1.setText(String.valueOf(cut_x1));

    }

    public void resetX2(View view) {

        cut_x2 = Float.parseFloat(mapAreas.get(areaName.get()).getDefX2());
        etX2.setText(String.valueOf(cut_x2));

    }

    public void resetY1(View view) {

        cut_y1 = Float.parseFloat(mapAreas.get(areaName.get()).getDefY1());
        etY1.setText(String.valueOf(cut_y1));

    }

    public void resetY2(View view) {

        cut_y2 = Float.parseFloat(mapAreas.get(areaName.get()).getDefY2());
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

