package com.svoemestodev.catscitycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class BordersActivity extends AppCompatActivity {

    public EditText etX1;
    public EditText etX2;
    public EditText etY1;
    public EditText etY2;
    public ImageView ivPicture;
    public static float cut_x1;
    public static float cut_x2;
    public static float cut_y1;
    public static float cut_y2;

    public static ObservableString areaName = new ObservableString();

    public static Bitmap fullBitmap;

    public static final String PREF_BORDERS_AREA = "pref_borders_area";

    public void loadArea() {

//        cut_x1 = GameActivity.mapFloats.get();

        CityCalcArea cityCalcArea = null;

        if (areaName.get().equals(getString(R.string.borders_box_info))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BOX_INFO);
        } else if (areaName.get().equals(getString(R.string.borders_city))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.CITY);
        } else if (areaName.get().equals(getString(R.string.borders_time))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TOTAL_TIME);
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.EARLY_WIN);
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_AND_INCREASE_OUR); 
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_AND_INCREASE_ENEMY);
        } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TEAM_NAME_ENEMY);
        } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TEAM_NAME_OUR);
        } else if (areaName.get().equals(getString(R.string.borders_blt))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_ARAE);
        } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT);
        } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_POINTS);
        } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_SLOTS);
        } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_PROGRESS);
        } else if (areaName.get().equals(getString(R.string.borders_blc))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_ARAE);
        } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC);
        } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_POINTS);
        } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_SLOTS);
        } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_PROGRESS);
        } else if (areaName.get().equals(getString(R.string.borders_blb))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_ARAE);
        } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB);
        } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_POINTS);
        } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_SLOTS);
        } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_PROGRESS);
        } else if (areaName.get().equals(getString(R.string.borders_brt))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_ARAE);
        } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT);
        } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_POINTS);
        } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_SLOTS);
        } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_PROGRESS);
        } else if (areaName.get().equals(getString(R.string.borders_brc))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_ARAE);
        } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC);
        } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_POINTS);
        } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_SLOTS);
        } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_PROGRESS);
        } else if (areaName.get().equals(getString(R.string.borders_brb))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_ARAE);
        } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB);
        } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_POINTS);
        } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_SLOTS);
        } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_PROGRESS);
        }

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

        fullBitmap = BitmapFactory.decodeFile(GameActivity.fileScreenshot.getAbsolutePath());   // получаем битмап из файла скриншота

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
                    CityCalcArea cityCalcArea = null;
                    
                    if (areaName.get().equals(getString(R.string.borders_city))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.CITY);
                        editor.putFloat(getString(R.string.pref_cut_city_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_box_info))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BOX_INFO);
                        editor.putFloat(getString(R.string.pref_cut_box_info_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_time))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TOTAL_TIME);
                        editor.putFloat(getString(R.string.pref_cut_total_time_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.EARLY_WIN);
                        editor.putFloat(getString(R.string.pref_cut_early_win_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_AND_INCREASE_OUR);
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_AND_INCREASE_ENEMY);
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TEAM_NAME_ENEMY);
                        editor.putFloat(getString(R.string.pref_cut_team_name_enemy_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TEAM_NAME_OUR);
                        editor.putFloat(getString(R.string.pref_cut_team_name_our_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blt_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT);
                        editor.putFloat(getString(R.string.pref_cut_blt_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blt_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blt_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blt_progress_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blc_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC);
                        editor.putFloat(getString(R.string.pref_cut_blc_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blc_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blc_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blc_progress_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blb_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB);
                        editor.putFloat(getString(R.string.pref_cut_blb_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blb_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blb_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blb_progress_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brt_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT);
                        editor.putFloat(getString(R.string.pref_cut_brt_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brt_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brt_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brt_progress_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brc_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC);
                        editor.putFloat(getString(R.string.pref_cut_brc_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brc_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brc_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brc_progress_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brb_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB);
                        editor.putFloat(getString(R.string.pref_cut_brb_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brb_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brb_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brb_progress_x1), value);
                    }
                    
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

                    CityCalcArea cityCalcArea = null;

                    if (areaName.get().equals(getString(R.string.borders_city))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.CITY);
                        editor.putFloat(getString(R.string.pref_cut_city_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_box_info))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BOX_INFO);
                        editor.putFloat(getString(R.string.pref_cut_box_info_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_time))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TOTAL_TIME);
                        editor.putFloat(getString(R.string.pref_cut_total_time_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.EARLY_WIN);
                        editor.putFloat(getString(R.string.pref_cut_early_win_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_AND_INCREASE_OUR);
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_AND_INCREASE_ENEMY);
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TEAM_NAME_ENEMY);
                        editor.putFloat(getString(R.string.pref_cut_team_name_enemy_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TEAM_NAME_OUR);
                        editor.putFloat(getString(R.string.pref_cut_team_name_our_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blt_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT);
                        editor.putFloat(getString(R.string.pref_cut_blt_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blt_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blt_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blt_progress_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blc_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC);
                        editor.putFloat(getString(R.string.pref_cut_blc_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blc_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blc_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blc_progress_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blb_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB);
                        editor.putFloat(getString(R.string.pref_cut_blb_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blb_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blb_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blb_progress_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brt_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT);
                        editor.putFloat(getString(R.string.pref_cut_brt_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brt_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brt_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brt_progress_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brc_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC);
                        editor.putFloat(getString(R.string.pref_cut_brc_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brc_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brc_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brc_progress_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brb_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB);
                        editor.putFloat(getString(R.string.pref_cut_brb_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brb_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brb_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brb_progress_x2), value);
                    }

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

                    CityCalcArea cityCalcArea = null;

                    if (areaName.get().equals(getString(R.string.borders_city))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.CITY);
                        editor.putFloat(getString(R.string.pref_cut_city_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_box_info))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BOX_INFO);
                        editor.putFloat(getString(R.string.pref_cut_box_info_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_time))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TOTAL_TIME);
                        editor.putFloat(getString(R.string.pref_cut_total_time_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.EARLY_WIN);
                        editor.putFloat(getString(R.string.pref_cut_early_win_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_AND_INCREASE_OUR);
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_AND_INCREASE_ENEMY);
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TEAM_NAME_ENEMY);
                        editor.putFloat(getString(R.string.pref_cut_team_name_enemy_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TEAM_NAME_OUR);
                        editor.putFloat(getString(R.string.pref_cut_team_name_our_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blt_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT);
                        editor.putFloat(getString(R.string.pref_cut_blt_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blt_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blt_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blt_progress_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blc_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC);
                        editor.putFloat(getString(R.string.pref_cut_blc_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blc_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blc_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blc_progress_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blb_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB);
                        editor.putFloat(getString(R.string.pref_cut_blb_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blb_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blb_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blb_progress_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brt_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT);
                        editor.putFloat(getString(R.string.pref_cut_brt_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brt_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brt_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brt_progress_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brc_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC);
                        editor.putFloat(getString(R.string.pref_cut_brc_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brc_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brc_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brc_progress_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brb_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB);
                        editor.putFloat(getString(R.string.pref_cut_brb_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brb_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brb_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brb_progress_y1), value);
                    }

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

                    CityCalcArea cityCalcArea = null;

                    if (areaName.get().equals(getString(R.string.borders_city))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.CITY);
                        editor.putFloat(getString(R.string.pref_cut_city_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_box_info))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BOX_INFO);
                        editor.putFloat(getString(R.string.pref_cut_box_info_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_time))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TOTAL_TIME);
                        editor.putFloat(getString(R.string.pref_cut_total_time_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.EARLY_WIN);
                        editor.putFloat(getString(R.string.pref_cut_early_win_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_AND_INCREASE_OUR);
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_AND_INCREASE_ENEMY);
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TEAM_NAME_ENEMY);
                        editor.putFloat(getString(R.string.pref_cut_team_name_enemy_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TEAM_NAME_OUR);
                        editor.putFloat(getString(R.string.pref_cut_team_name_our_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blt_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT);
                        editor.putFloat(getString(R.string.pref_cut_blt_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blt_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blt_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLT_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blt_progress_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blc_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC);
                        editor.putFloat(getString(R.string.pref_cut_blc_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blc_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blc_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLC_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blc_progress_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_blb_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB);
                        editor.putFloat(getString(R.string.pref_cut_blb_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_blb_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_blb_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BLB_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_blb_progress_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brt_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT);
                        editor.putFloat(getString(R.string.pref_cut_brt_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brt_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brt_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRT_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brt_progress_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brc_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC);
                        editor.putFloat(getString(R.string.pref_cut_brc_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brc_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brc_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRC_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brc_progress_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_ARAE);
                        editor.putFloat(getString(R.string.pref_cut_brb_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB);
                        editor.putFloat(getString(R.string.pref_cut_brb_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_POINTS);
                        editor.putFloat(getString(R.string.pref_cut_brb_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_SLOTS);
                        editor.putFloat(getString(R.string.pref_cut_brb_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.BRB_PROGRESS);
                        editor.putFloat(getString(R.string.pref_cut_brb_progress_y2), value);
                    }

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

        if (areaName.get().equals(getString(R.string.borders_city))) { cut_x1 = Float.parseFloat(getString(R.string.def_cut_city_x1));
        } else if (areaName.get().equals(getString(R.string.borders_box_info))) { cut_x1 = Float.parseFloat(getString(R.string.def_cut_box_info_x1));
        } else if (areaName.get().equals(getString(R.string.borders_time))) { cut_x1 = Float.parseFloat(getString(R.string.def_cut_total_time_x1));
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) { cut_x1 = Float.parseFloat(getString(R.string.def_cut_early_win_x1));
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) { cut_x1 = Float.parseFloat(getString(R.string.def_cut_points_and_increase_our_x1));
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_points_and_increase_enemy_x1));
        } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_team_name_enemy_x1));
        } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_team_name_our_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blt))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blt_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blt_name_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blt_points_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blt_slots_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blt_progress_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blc))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blc_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blc_name_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blc_points_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blc_slots_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blc_progress_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blb))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blb_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blb_name_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blb_points_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blb_slots_x1));
        } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_blb_progress_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brt))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brt_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brt_name_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brt_points_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brt_slots_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brt_progress_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brc))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brc_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brc_name_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brc_points_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brc_slots_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brc_progress_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brb))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brb_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brb_name_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brb_points_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brb_slots_x1));
        } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {cut_x1 = Float.parseFloat(getString(R.string.def_cut_brb_progress_x1));
        }
        etX1.setText(String.valueOf(cut_x1));

    }

    public void resetX2(View view) {

        if (areaName.get().equals(getString(R.string.borders_city))) { cut_x2 = Float.parseFloat(getString(R.string.def_cut_city_x2));
        } else if (areaName.get().equals(getString(R.string.borders_box_info))) { cut_x2 = Float.parseFloat(getString(R.string.def_cut_box_info_x2));
        } else if (areaName.get().equals(getString(R.string.borders_time))) { cut_x2 = Float.parseFloat(getString(R.string.def_cut_total_time_x2));
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) { cut_x2 = Float.parseFloat(getString(R.string.def_cut_early_win_x2));
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) { cut_x2 = Float.parseFloat(getString(R.string.def_cut_points_and_increase_our_x2));
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_points_and_increase_enemy_x2));
        } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_team_name_enemy_x2));
        } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_team_name_our_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blt))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blt_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blt_name_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blt_points_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blt_slots_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blt_progress_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blc))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blc_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blc_name_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blc_points_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blc_slots_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blc_progress_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blb))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blb_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blb_name_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blb_points_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blb_slots_x2));
        } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_blb_progress_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brt))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brt_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brt_name_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brt_points_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brt_slots_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brt_progress_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brc))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brc_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brc_name_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brc_points_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brc_slots_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brc_progress_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brb))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brb_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brb_name_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brb_points_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brb_slots_x2));
        } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {cut_x2 = Float.parseFloat(getString(R.string.def_cut_brb_progress_x2));
        }
        etX2.setText(String.valueOf(cut_x2));

    }

    public void resetY1(View view) {

        if (areaName.get().equals(getString(R.string.borders_city))) { cut_y1 = Float.parseFloat(getString(R.string.def_cut_city_y1));
        } else if (areaName.get().equals(getString(R.string.borders_box_info))) { cut_y1 = Float.parseFloat(getString(R.string.def_cut_box_info_y1));
        } else if (areaName.get().equals(getString(R.string.borders_time))) { cut_y1 = Float.parseFloat(getString(R.string.def_cut_total_time_y1));
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) { cut_y1 = Float.parseFloat(getString(R.string.def_cut_early_win_y1));
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) { cut_y1 = Float.parseFloat(getString(R.string.def_cut_points_and_increase_our_y1));
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_points_and_increase_enemy_y1));
        } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_team_name_enemy_y1));
        } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_team_name_our_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blt))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blt_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blt_name_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blt_points_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blt_slots_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blt_progress_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blc))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blc_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blc_name_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blc_points_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blc_slots_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blc_progress_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blb))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blb_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blb_name_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blb_points_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blb_slots_y1));
        } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_blb_progress_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brt))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brt_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brt_name_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brt_points_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brt_slots_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brt_progress_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brc))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brc_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brc_name_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brc_points_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brc_slots_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brc_progress_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brb))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brb_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brb_name_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brb_points_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brb_slots_y1));
        } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {cut_y1 = Float.parseFloat(getString(R.string.def_cut_brb_progress_y1));
        }
        etY1.setText(String.valueOf(cut_y1));

    }

    public void resetY2(View view) {

        if (areaName.get().equals(getString(R.string.borders_city))) { cut_y2 = Float.parseFloat(getString(R.string.def_cut_city_y2));
        } else if (areaName.get().equals(getString(R.string.borders_box_info))) { cut_y2 = Float.parseFloat(getString(R.string.def_cut_box_info_y2));
        } else if (areaName.get().equals(getString(R.string.borders_time))) { cut_y2 = Float.parseFloat(getString(R.string.def_cut_total_time_y2));
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) { cut_y2 = Float.parseFloat(getString(R.string.def_cut_early_win_y2));
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) { cut_y2 = Float.parseFloat(getString(R.string.def_cut_points_and_increase_our_y2));
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_points_and_increase_enemy_y2));
        } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_team_name_enemy_y2));
        } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_team_name_our_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blt))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blt_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blt_name_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blt_points_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blt_slots_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blt_progress_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blc))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blc_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blc_name_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blc_points_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blc_slots_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blc_progress_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blb))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blb_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blb_name_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blb_points_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blb_slots_y2));
        } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_blb_progress_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brt))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brt_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brt_name_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brt_points_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brt_slots_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brt_progress_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brc))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brc_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brc_name_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brc_points_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brc_slots_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brc_progress_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brb))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brb_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brb_name_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brb_points_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brb_slots_y2));
        } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {cut_y2 = Float.parseFloat(getString(R.string.def_cut_brb_progress_y2));
        }
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

