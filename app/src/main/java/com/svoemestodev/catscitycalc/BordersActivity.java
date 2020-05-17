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

//        cut_x1 = MainActivity.mapFloats.get();


        if (areaName.get().equals(getString(R.string.borders_city))) {
            cut_x1 = MainActivity.cut_city_x1; cut_x2 = MainActivity.cut_city_x2; cut_y1 = MainActivity.cut_city_y1; cut_y2 = MainActivity.cut_city_y2;
        } else if (areaName.get().equals(getString(R.string.borders_time))) {
            cut_x1 = MainActivity.cut_total_time_x1; cut_x2 = MainActivity.cut_total_time_x2; cut_y1 = MainActivity.cut_total_time_y1; cut_y2 = MainActivity.cut_total_time_y2; 
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
            cut_x1 = MainActivity.cut_early_win_x1; cut_x2 = MainActivity.cut_early_win_x2; cut_y1 = MainActivity.cut_early_win_y1; cut_y2 = MainActivity.cut_early_win_y2;
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
            cut_x1 = MainActivity.cut_points_and_increase_our_x1; cut_x2 = MainActivity.cut_points_and_increase_our_x2; cut_y1 = MainActivity.cut_points_and_increase_our_y1; cut_y2 = MainActivity.cut_points_and_increase_our_y2; 
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
            cut_x1 = MainActivity.cut_points_and_increase_enemy_x1; cut_x2 = MainActivity.cut_points_and_increase_enemy_x2; cut_y1 = MainActivity.cut_points_and_increase_enemy_y1; cut_y2 = MainActivity.cut_points_and_increase_enemy_y2;
        } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {
            cut_x1 = MainActivity.cut_team_name_enemy_x1; cut_x2 = MainActivity.cut_team_name_enemy_x2; cut_y1 = MainActivity.cut_team_name_enemy_y1; cut_y2 = MainActivity.cut_team_name_enemy_y2;
        } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {
            cut_x1 = MainActivity.cut_team_name_our_x1; cut_x2 = MainActivity.cut_team_name_our_x2; cut_y1 = MainActivity.cut_team_name_our_y1; cut_y2 = MainActivity.cut_team_name_our_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blt))) {
            cut_x1 = MainActivity.cut_building_lt_x1; cut_x2 = MainActivity.cut_building_lt_x2; cut_y1 = MainActivity.cut_building_lt_y1; cut_y2 = MainActivity.cut_building_lt_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {
            cut_x1 = MainActivity.cut_building_lt_name_x1; cut_x2 = MainActivity.cut_building_lt_name_x2; cut_y1 = MainActivity.cut_building_lt_name_y1; cut_y2 = MainActivity.cut_building_lt_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {
            cut_x1 = MainActivity.cut_building_lt_increase_x1; cut_x2 = MainActivity.cut_building_lt_increase_x2; cut_y1 = MainActivity.cut_building_lt_increase_y1; cut_y2 = MainActivity.cut_building_lt_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {
            cut_x1 = MainActivity.cut_building_lt_slots_x1; cut_x2 = MainActivity.cut_building_lt_slots_x2; cut_y1 = MainActivity.cut_building_lt_slots_y1; cut_y2 = MainActivity.cut_building_lt_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {
            cut_x1 = MainActivity.cut_building_lt_progress_x1; cut_x2 = MainActivity.cut_building_lt_progress_x2; cut_y1 = MainActivity.cut_building_lt_progress_y1; cut_y2 = MainActivity.cut_building_lt_progress_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blc))) {
            cut_x1 = MainActivity.cut_building_lc_x1; cut_x2 = MainActivity.cut_building_lc_x2; cut_y1 = MainActivity.cut_building_lc_y1; cut_y2 = MainActivity.cut_building_lc_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {
            cut_x1 = MainActivity.cut_building_lc_name_x1; cut_x2 = MainActivity.cut_building_lc_name_x2; cut_y1 = MainActivity.cut_building_lc_name_y1; cut_y2 = MainActivity.cut_building_lc_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {
            cut_x1 = MainActivity.cut_building_lc_increase_x1; cut_x2 = MainActivity.cut_building_lc_increase_x2; cut_y1 = MainActivity.cut_building_lc_increase_y1; cut_y2 = MainActivity.cut_building_lc_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {
            cut_x1 = MainActivity.cut_building_lc_slots_x1; cut_x2 = MainActivity.cut_building_lc_slots_x2; cut_y1 = MainActivity.cut_building_lc_slots_y1; cut_y2 = MainActivity.cut_building_lc_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {
            cut_x1 = MainActivity.cut_building_lc_progress_x1; cut_x2 = MainActivity.cut_building_lc_progress_x2; cut_y1 = MainActivity.cut_building_lc_progress_y1; cut_y2 = MainActivity.cut_building_lc_progress_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blb))) {
            cut_x1 = MainActivity.cut_building_lb_x1; cut_x2 = MainActivity.cut_building_lb_x2; cut_y1 = MainActivity.cut_building_lb_y1; cut_y2 = MainActivity.cut_building_lb_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {
            cut_x1 = MainActivity.cut_building_lb_name_x1; cut_x2 = MainActivity.cut_building_lb_name_x2; cut_y1 = MainActivity.cut_building_lb_name_y1; cut_y2 = MainActivity.cut_building_lb_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {
            cut_x1 = MainActivity.cut_building_lb_increase_x1; cut_x2 = MainActivity.cut_building_lb_increase_x2; cut_y1 = MainActivity.cut_building_lb_increase_y1; cut_y2 = MainActivity.cut_building_lb_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {
            cut_x1 = MainActivity.cut_building_lb_slots_x1; cut_x2 = MainActivity.cut_building_lb_slots_x2; cut_y1 = MainActivity.cut_building_lb_slots_y1; cut_y2 = MainActivity.cut_building_lb_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {
            cut_x1 = MainActivity.cut_building_lb_progress_x1; cut_x2 = MainActivity.cut_building_lb_progress_x2; cut_y1 = MainActivity.cut_building_lb_progress_y1; cut_y2 = MainActivity.cut_building_lb_progress_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brt))) {
            cut_x1 = MainActivity.cut_building_rt_x1; cut_x2 = MainActivity.cut_building_rt_x2; cut_y1 = MainActivity.cut_building_rt_y1; cut_y2 = MainActivity.cut_building_rt_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {
            cut_x1 = MainActivity.cut_building_rt_name_x1; cut_x2 = MainActivity.cut_building_rt_name_x2; cut_y1 = MainActivity.cut_building_rt_name_y1; cut_y2 = MainActivity.cut_building_rt_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {
            cut_x1 = MainActivity.cut_building_rt_increase_x1; cut_x2 = MainActivity.cut_building_rt_increase_x2; cut_y1 = MainActivity.cut_building_rt_increase_y1; cut_y2 = MainActivity.cut_building_rt_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {
            cut_x1 = MainActivity.cut_building_rt_slots_x1; cut_x2 = MainActivity.cut_building_rt_slots_x2; cut_y1 = MainActivity.cut_building_rt_slots_y1; cut_y2 = MainActivity.cut_building_rt_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {
            cut_x1 = MainActivity.cut_building_rt_progress_x1; cut_x2 = MainActivity.cut_building_rt_progress_x2; cut_y1 = MainActivity.cut_building_rt_progress_y1; cut_y2 = MainActivity.cut_building_rt_progress_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brc))) {
            cut_x1 = MainActivity.cut_building_rc_x1; cut_x2 = MainActivity.cut_building_rc_x2; cut_y1 = MainActivity.cut_building_rc_y1; cut_y2 = MainActivity.cut_building_rc_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {
            cut_x1 = MainActivity.cut_building_rc_name_x1; cut_x2 = MainActivity.cut_building_rc_name_x2; cut_y1 = MainActivity.cut_building_rc_name_y1; cut_y2 = MainActivity.cut_building_rc_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {
            cut_x1 = MainActivity.cut_building_rc_increase_x1; cut_x2 = MainActivity.cut_building_rc_increase_x2; cut_y1 = MainActivity.cut_building_rc_increase_y1; cut_y2 = MainActivity.cut_building_rc_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {
            cut_x1 = MainActivity.cut_building_rc_slots_x1; cut_x2 = MainActivity.cut_building_rc_slots_x2; cut_y1 = MainActivity.cut_building_rc_slots_y1; cut_y2 = MainActivity.cut_building_rc_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {
            cut_x1 = MainActivity.cut_building_rc_progress_x1; cut_x2 = MainActivity.cut_building_rc_progress_x2; cut_y1 = MainActivity.cut_building_rc_progress_y1; cut_y2 = MainActivity.cut_building_rc_progress_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brb))) {
            cut_x1 = MainActivity.cut_building_rb_x1; cut_x2 = MainActivity.cut_building_rb_x2; cut_y1 = MainActivity.cut_building_rb_y1; cut_y2 = MainActivity.cut_building_rb_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {
            cut_x1 = MainActivity.cut_building_rb_name_x1; cut_x2 = MainActivity.cut_building_rb_name_x2; cut_y1 = MainActivity.cut_building_rb_name_y1; cut_y2 = MainActivity.cut_building_rb_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {
            cut_x1 = MainActivity.cut_building_rb_increase_x1; cut_x2 = MainActivity.cut_building_rb_increase_x2; cut_y1 = MainActivity.cut_building_rb_increase_y1; cut_y2 = MainActivity.cut_building_rb_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {
            cut_x1 = MainActivity.cut_building_rb_slots_x1; cut_x2 = MainActivity.cut_building_rb_slots_x2; cut_y1 = MainActivity.cut_building_rb_slots_y1; cut_y2 = MainActivity.cut_building_rb_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {
            cut_x1 = MainActivity.cut_building_rb_progress_x1; cut_x2 = MainActivity.cut_building_rb_progress_x2; cut_y1 = MainActivity.cut_building_rb_progress_y1; cut_y2 = MainActivity.cut_building_rb_progress_y2;
        }

        etX1.setText(String.valueOf(cut_x1));
        etX2.setText(String.valueOf(cut_x2));
        etY1.setText(String.valueOf(cut_y1));
        etY2.setText(String.valueOf(cut_y2));
        ivPicture.setImageBitmap(cutBorders());  // выводим битмат игры в контрол

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

        fullBitmap = BitmapFactory.decodeFile(MainActivity.fileScreenshot.getAbsolutePath());   // получаем битмап из файла скриншота

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

                    if (areaName.get().equals(getString(R.string.borders_city))) {
                        MainActivity.cut_city_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_city_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_time))) {
                        MainActivity.cut_total_time_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_total_time_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
                        MainActivity.cut_early_win_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_early_win_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
                        MainActivity.cut_points_and_increase_our_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
                        MainActivity.cut_points_and_increase_enemy_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {
                        MainActivity.cut_team_name_enemy_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_team_name_enemy_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {
                        MainActivity.cut_team_name_our_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_team_name_our_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt))) {
                        MainActivity.cut_building_lt_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {
                        MainActivity.cut_building_lt_name_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {
                        MainActivity.cut_building_lt_increase_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {
                        MainActivity.cut_building_lt_slots_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {
                        MainActivity.cut_building_lt_progress_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_progress_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc))) {
                        MainActivity.cut_building_lc_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {
                        MainActivity.cut_building_lc_name_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {
                        MainActivity.cut_building_lc_increase_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {
                        MainActivity.cut_building_lc_slots_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {
                        MainActivity.cut_building_lc_progress_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_progress_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb))) {
                        MainActivity.cut_building_lb_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {
                        MainActivity.cut_building_lb_name_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {
                        MainActivity.cut_building_lb_increase_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {
                        MainActivity.cut_building_lb_slots_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {
                        MainActivity.cut_building_lb_progress_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_progress_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt))) {
                        MainActivity.cut_building_rt_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {
                        MainActivity.cut_building_rt_name_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {
                        MainActivity.cut_building_rt_increase_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {
                        MainActivity.cut_building_rt_slots_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {
                        MainActivity.cut_building_rt_progress_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_progress_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc))) {
                        MainActivity.cut_building_rc_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {
                        MainActivity.cut_building_rc_name_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {
                        MainActivity.cut_building_rc_increase_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {
                        MainActivity.cut_building_rc_slots_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {
                        MainActivity.cut_building_rc_progress_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_progress_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb))) {
                        MainActivity.cut_building_rb_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {
                        MainActivity.cut_building_rb_name_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_name_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {
                        MainActivity.cut_building_rb_increase_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_increase_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {
                        MainActivity.cut_building_rb_slots_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_slots_x1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {
                        MainActivity.cut_building_rb_progress_x1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_progress_x1), value);


                    }

                    editor.apply();

                    ivPicture.setImageBitmap(cutBorders());

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

                    if (areaName.get().equals(getString(R.string.borders_city))) {
                        MainActivity.cut_city_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_city_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_time))) {
                        MainActivity.cut_total_time_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_total_time_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
                        MainActivity.cut_early_win_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_early_win_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
                        MainActivity.cut_points_and_increase_our_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
                        MainActivity.cut_points_and_increase_enemy_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {
                        MainActivity.cut_team_name_enemy_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_team_name_enemy_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {
                        MainActivity.cut_team_name_our_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_team_name_our_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt))) {
                        MainActivity.cut_building_lt_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {
                        MainActivity.cut_building_lt_name_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {
                        MainActivity.cut_building_lt_increase_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {
                        MainActivity.cut_building_lt_slots_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {
                        MainActivity.cut_building_lt_progress_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_progress_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc))) {
                        MainActivity.cut_building_lc_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {
                        MainActivity.cut_building_lc_name_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {
                        MainActivity.cut_building_lc_increase_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {
                        MainActivity.cut_building_lc_slots_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {
                        MainActivity.cut_building_lc_progress_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_progress_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb))) {
                        MainActivity.cut_building_lb_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {
                        MainActivity.cut_building_lb_name_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {
                        MainActivity.cut_building_lb_increase_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {
                        MainActivity.cut_building_lb_slots_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {
                        MainActivity.cut_building_lb_progress_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_progress_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt))) {
                        MainActivity.cut_building_rt_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {
                        MainActivity.cut_building_rt_name_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {
                        MainActivity.cut_building_rt_increase_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {
                        MainActivity.cut_building_rt_slots_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {
                        MainActivity.cut_building_rt_progress_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_progress_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc))) {
                        MainActivity.cut_building_rc_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {
                        MainActivity.cut_building_rc_name_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {
                        MainActivity.cut_building_rc_increase_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {
                        MainActivity.cut_building_rc_slots_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {
                        MainActivity.cut_building_rc_progress_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_progress_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb))) {
                        MainActivity.cut_building_rb_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {
                        MainActivity.cut_building_rb_name_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_name_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {
                        MainActivity.cut_building_rb_increase_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_increase_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {
                        MainActivity.cut_building_rb_slots_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_slots_x2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {
                        MainActivity.cut_building_rb_progress_x2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_progress_x2), value);
                    }

                    editor.apply();

                    ivPicture.setImageBitmap(cutBorders());
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

                    if (areaName.get().equals(getString(R.string.borders_city))) {
                        MainActivity.cut_city_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_city_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_time))) {
                        MainActivity.cut_total_time_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_total_time_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
                        MainActivity.cut_early_win_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_early_win_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
                        MainActivity.cut_points_and_increase_our_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
                        MainActivity.cut_points_and_increase_enemy_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {
                        MainActivity.cut_team_name_enemy_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_team_name_enemy_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {
                        MainActivity.cut_team_name_our_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_team_name_our_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt))) {
                        MainActivity.cut_building_lt_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {
                        MainActivity.cut_building_lt_name_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {
                        MainActivity.cut_building_lt_increase_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {
                        MainActivity.cut_building_lt_slots_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {
                        MainActivity.cut_building_lt_progress_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_progress_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc))) {
                        MainActivity.cut_building_lc_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {
                        MainActivity.cut_building_lc_name_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {
                        MainActivity.cut_building_lc_increase_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {
                        MainActivity.cut_building_lc_slots_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {
                        MainActivity.cut_building_lc_progress_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_progress_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb))) {
                        MainActivity.cut_building_lb_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {
                        MainActivity.cut_building_lb_name_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {
                        MainActivity.cut_building_lb_increase_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {
                        MainActivity.cut_building_lb_slots_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {
                        MainActivity.cut_building_lb_progress_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_progress_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt))) {
                        MainActivity.cut_building_rt_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {
                        MainActivity.cut_building_rt_name_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {
                        MainActivity.cut_building_rt_increase_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {
                        MainActivity.cut_building_rt_slots_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {
                        MainActivity.cut_building_rt_progress_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_progress_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc))) {
                        MainActivity.cut_building_rc_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {
                        MainActivity.cut_building_rc_name_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {
                        MainActivity.cut_building_rc_increase_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {
                        MainActivity.cut_building_rc_slots_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {
                        MainActivity.cut_building_rc_progress_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_progress_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb))) {
                        MainActivity.cut_building_rb_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {
                        MainActivity.cut_building_rb_name_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_name_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {
                        MainActivity.cut_building_rb_increase_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_increase_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {
                        MainActivity.cut_building_rb_slots_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_slots_y1), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {
                        MainActivity.cut_building_rb_progress_y1 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_progress_y1), value);                        
                        
                    }

                    editor.apply();

                    ivPicture.setImageBitmap(cutBorders());
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

                    if (areaName.get().equals(getString(R.string.borders_city))) {
                        MainActivity.cut_city_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_city_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_time))) {
                        MainActivity.cut_total_time_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_total_time_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
                        MainActivity.cut_early_win_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_early_win_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
                        MainActivity.cut_points_and_increase_our_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_our_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
                        MainActivity.cut_points_and_increase_enemy_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_points_and_increase_enemy_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {
                        MainActivity.cut_team_name_enemy_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_team_name_enemy_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {
                        MainActivity.cut_team_name_our_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_team_name_our_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt))) {
                        MainActivity.cut_building_lt_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {
                        MainActivity.cut_building_lt_name_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {
                        MainActivity.cut_building_lt_increase_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {
                        MainActivity.cut_building_lt_slots_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {
                        MainActivity.cut_building_lt_progress_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lt_progress_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc))) {
                        MainActivity.cut_building_lc_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {
                        MainActivity.cut_building_lc_name_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {
                        MainActivity.cut_building_lc_increase_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {
                        MainActivity.cut_building_lc_slots_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {
                        MainActivity.cut_building_lc_progress_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lc_progress_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb))) {
                        MainActivity.cut_building_lb_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {
                        MainActivity.cut_building_lb_name_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {
                        MainActivity.cut_building_lb_increase_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {
                        MainActivity.cut_building_lb_slots_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {
                        MainActivity.cut_building_lb_progress_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_lb_progress_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt))) {
                        MainActivity.cut_building_rt_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {
                        MainActivity.cut_building_rt_name_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {
                        MainActivity.cut_building_rt_increase_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {
                        MainActivity.cut_building_rt_slots_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {
                        MainActivity.cut_building_rt_progress_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rt_progress_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc))) {
                        MainActivity.cut_building_rc_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {
                        MainActivity.cut_building_rc_name_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {
                        MainActivity.cut_building_rc_increase_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {
                        MainActivity.cut_building_rc_slots_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {
                        MainActivity.cut_building_rc_progress_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rc_progress_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb))) {
                        MainActivity.cut_building_rb_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {
                        MainActivity.cut_building_rb_name_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_name_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {
                        MainActivity.cut_building_rb_increase_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_increase_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {
                        MainActivity.cut_building_rb_slots_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_slots_y2), value);
                    } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {
                        MainActivity.cut_building_rb_progress_y2 = value;
                        editor.putFloat(getString(R.string.pref_cut_building_rb_progress_y2), value);                        
                        
                    }

                    editor.apply();

                    ivPicture.setImageBitmap(cutBorders());
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

        int realCalibrateX = (widthSource / 2) > Math.abs(MainActivity.calibrateX) ? MainActivity.calibrateX : 0;
        int realCalibrateY = (heightSource / 2) > Math.abs(MainActivity.calibrateY) ? MainActivity.calibrateY : 0;

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

        if (areaName.get().equals(getString(R.string.borders_city))) { cut_x1 = MainActivity.def_city_x1;
        } else if (areaName.get().equals(getString(R.string.borders_time))) { cut_x1 = MainActivity.def_total_time_x1;
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) { cut_x1 = MainActivity.def_early_win_x1;
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) { cut_x1 = MainActivity.def_points_our_x1;
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {cut_x1 = MainActivity.def_points_enemy_x1;
        } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {cut_x1 = MainActivity.def_team_name_enemy_x1;
        } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {cut_x1 = MainActivity.def_team_name_our_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blt))) {cut_x1 = MainActivity.def_building_lt_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {cut_x1 = MainActivity.def_building_lt_name_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {cut_x1 = MainActivity.def_building_lt_increase_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {cut_x1 = MainActivity.def_building_lt_slots_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {cut_x1 = MainActivity.def_building_lt_progress_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blc))) {cut_x1 = MainActivity.def_building_lc_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {cut_x1 = MainActivity.def_building_lc_name_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {cut_x1 = MainActivity.def_building_lc_increase_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {cut_x1 = MainActivity.def_building_lc_slots_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {cut_x1 = MainActivity.def_building_lc_progress_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blb))) {cut_x1 = MainActivity.def_building_lb_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {cut_x1 = MainActivity.def_building_lb_name_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {cut_x1 = MainActivity.def_building_lb_increase_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {cut_x1 = MainActivity.def_building_lb_slots_x1;
        } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {cut_x1 = MainActivity.def_building_lb_progress_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brt))) {cut_x1 = MainActivity.def_building_rt_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {cut_x1 = MainActivity.def_building_rt_name_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {cut_x1 = MainActivity.def_building_rt_increase_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {cut_x1 = MainActivity.def_building_rt_slots_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {cut_x1 = MainActivity.def_building_rt_progress_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brc))) {cut_x1 = MainActivity.def_building_rc_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {cut_x1 = MainActivity.def_building_rc_name_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {cut_x1 = MainActivity.def_building_rc_increase_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {cut_x1 = MainActivity.def_building_rc_slots_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {cut_x1 = MainActivity.def_building_rc_progress_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brb))) {cut_x1 = MainActivity.def_building_rb_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {cut_x1 = MainActivity.def_building_rb_name_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {cut_x1 = MainActivity.def_building_rb_increase_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {cut_x1 = MainActivity.def_building_rb_slots_x1;
        } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {cut_x1 = MainActivity.def_building_rb_progress_x1;
        }
        etX1.setText(String.valueOf(cut_x1));

    }

    public void resetX2(View view) {

        if (areaName.get().equals(getString(R.string.borders_city))) { cut_x2 = MainActivity.def_city_x2;
        } else if (areaName.get().equals(getString(R.string.borders_time))) { cut_x2 = MainActivity.def_total_time_x2;
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) { cut_x2 = MainActivity.def_early_win_x2;
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) { cut_x2 = MainActivity.def_points_our_x2;
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {cut_x2 = MainActivity.def_points_enemy_x2;
        } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {cut_x2 = MainActivity.def_team_name_enemy_x2;
        } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {cut_x2 = MainActivity.def_team_name_our_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blt))) {cut_x2 = MainActivity.def_building_lt_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {cut_x2 = MainActivity.def_building_lt_name_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {cut_x2 = MainActivity.def_building_lt_increase_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {cut_x2 = MainActivity.def_building_lt_slots_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {cut_x2 = MainActivity.def_building_lt_progress_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blc))) {cut_x2 = MainActivity.def_building_lc_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {cut_x2 = MainActivity.def_building_lc_name_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {cut_x2 = MainActivity.def_building_lc_increase_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {cut_x2 = MainActivity.def_building_lc_slots_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {cut_x2 = MainActivity.def_building_lc_progress_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blb))) {cut_x2 = MainActivity.def_building_lb_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {cut_x2 = MainActivity.def_building_lb_name_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {cut_x2 = MainActivity.def_building_lb_increase_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {cut_x2 = MainActivity.def_building_lb_slots_x2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {cut_x2 = MainActivity.def_building_lb_progress_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brt))) {cut_x2 = MainActivity.def_building_rt_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {cut_x2 = MainActivity.def_building_rt_name_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {cut_x2 = MainActivity.def_building_rt_increase_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {cut_x2 = MainActivity.def_building_rt_slots_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {cut_x2 = MainActivity.def_building_rt_progress_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brc))) {cut_x2 = MainActivity.def_building_rc_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {cut_x2 = MainActivity.def_building_rc_name_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {cut_x2 = MainActivity.def_building_rc_increase_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {cut_x2 = MainActivity.def_building_rc_slots_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {cut_x2 = MainActivity.def_building_rc_progress_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brb))) {cut_x2 = MainActivity.def_building_rb_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {cut_x2 = MainActivity.def_building_rb_name_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {cut_x2 = MainActivity.def_building_rb_increase_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {cut_x2 = MainActivity.def_building_rb_slots_x2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {cut_x2 = MainActivity.def_building_rb_progress_x2;
        }
        etX2.setText(String.valueOf(cut_x2));

    }

    public void resetY1(View view) {

        if (areaName.get().equals(getString(R.string.borders_city))) { cut_y1 = MainActivity.def_city_y1;
        } else if (areaName.get().equals(getString(R.string.borders_time))) { cut_y1 = MainActivity.def_total_time_y1;
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) { cut_y1 = MainActivity.def_early_win_y1;
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) { cut_y1 = MainActivity.def_points_our_y1;
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {cut_y1 = MainActivity.def_points_enemy_y1;
        } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {cut_y1 = MainActivity.def_team_name_enemy_y1;
        } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {cut_y1 = MainActivity.def_team_name_our_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blt))) {cut_y1 = MainActivity.def_building_lt_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {cut_y1 = MainActivity.def_building_lt_name_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {cut_y1 = MainActivity.def_building_lt_increase_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {cut_y1 = MainActivity.def_building_lt_slots_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {cut_y1 = MainActivity.def_building_lt_progress_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blc))) {cut_y1 = MainActivity.def_building_lc_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {cut_y1 = MainActivity.def_building_lc_name_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {cut_y1 = MainActivity.def_building_lc_increase_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {cut_y1 = MainActivity.def_building_lc_slots_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {cut_y1 = MainActivity.def_building_lc_progress_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blb))) {cut_y1 = MainActivity.def_building_lb_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {cut_y1 = MainActivity.def_building_lb_name_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {cut_y1 = MainActivity.def_building_lb_increase_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {cut_y1 = MainActivity.def_building_lb_slots_y1;
        } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {cut_y1 = MainActivity.def_building_lb_progress_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brt))) {cut_y1 = MainActivity.def_building_rt_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {cut_y1 = MainActivity.def_building_rt_name_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {cut_y1 = MainActivity.def_building_rt_increase_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {cut_y1 = MainActivity.def_building_rt_slots_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {cut_y1 = MainActivity.def_building_rt_progress_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brc))) {cut_y1 = MainActivity.def_building_rc_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {cut_y1 = MainActivity.def_building_rc_name_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {cut_y1 = MainActivity.def_building_rc_increase_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {cut_y1 = MainActivity.def_building_rc_slots_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {cut_y1 = MainActivity.def_building_rc_progress_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brb))) {cut_y1 = MainActivity.def_building_rb_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {cut_y1 = MainActivity.def_building_rb_name_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {cut_y1 = MainActivity.def_building_rb_increase_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {cut_y1 = MainActivity.def_building_rb_slots_y1;
        } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {cut_y1 = MainActivity.def_building_rb_progress_y1;
        }
        etY1.setText(String.valueOf(cut_y1));

    }

    public void resetY2(View view) {

        if (areaName.get().equals(getString(R.string.borders_city))) { cut_y2 = MainActivity.def_city_y2;
        } else if (areaName.get().equals(getString(R.string.borders_time))) { cut_y2 = MainActivity.def_total_time_y2;
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) { cut_y2 = MainActivity.def_early_win_y2;
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) { cut_y2 = MainActivity.def_points_our_y2;
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {cut_y2 = MainActivity.def_points_enemy_y2;
        } else if (areaName.get().equals(getString(R.string.borders_enemy_team_name))) {cut_y2 = MainActivity.def_team_name_enemy_y2;
        } else if (areaName.get().equals(getString(R.string.borders_our_team_name))) {cut_y2 = MainActivity.def_team_name_our_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blt))) {cut_y2 = MainActivity.def_building_lt_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_name))) {cut_y2 = MainActivity.def_building_lt_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_increase))) {cut_y2 = MainActivity.def_building_lt_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_slots))) {cut_y2 = MainActivity.def_building_lt_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blt_progress))) {cut_y2 = MainActivity.def_building_lt_progress_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blc))) {cut_y2 = MainActivity.def_building_lc_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_name))) {cut_y2 = MainActivity.def_building_lc_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_increase))) {cut_y2 = MainActivity.def_building_lc_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_slots))) {cut_y2 = MainActivity.def_building_lc_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blc_progress))) {cut_y2 = MainActivity.def_building_lc_progress_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blb))) {cut_y2 = MainActivity.def_building_lb_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_name))) {cut_y2 = MainActivity.def_building_lb_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_increase))) {cut_y2 = MainActivity.def_building_lb_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_slots))) {cut_y2 = MainActivity.def_building_lb_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_blb_progress))) {cut_y2 = MainActivity.def_building_lb_progress_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brt))) {cut_y2 = MainActivity.def_building_rt_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_name))) {cut_y2 = MainActivity.def_building_rt_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_increase))) {cut_y2 = MainActivity.def_building_rt_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_slots))) {cut_y2 = MainActivity.def_building_rt_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brt_progress))) {cut_y2 = MainActivity.def_building_rt_progress_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brc))) {cut_y2 = MainActivity.def_building_rc_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_name))) {cut_y2 = MainActivity.def_building_rc_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_increase))) {cut_y2 = MainActivity.def_building_rc_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_slots))) {cut_y2 = MainActivity.def_building_rc_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brc_progress))) {cut_y2 = MainActivity.def_building_rc_progress_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brb))) {cut_y2 = MainActivity.def_building_rb_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_name))) {cut_y2 = MainActivity.def_building_rb_name_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_increase))) {cut_y2 = MainActivity.def_building_rb_increase_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_slots))) {cut_y2 = MainActivity.def_building_rb_slots_y2;
        } else if (areaName.get().equals(getString(R.string.borders_brb_progress))) {cut_y2 = MainActivity.def_building_rb_progress_y2;
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

