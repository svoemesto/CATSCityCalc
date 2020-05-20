package com.svoemestodev.catscitycalc;

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
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

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


    public void loadArea() {
        
        if (areaName.get().equals(getString(R.string.borders_time))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TOTAL_TIME);
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.EARLY_WIN);
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_OUR);
        } else if (areaName.get().equals(getString(R.string.colors_plus_us))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.INCREASE_OUR);
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_ENEMY);
        } else if (areaName.get().equals(getString(R.string.colors_plus_they))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.INCREASE_ENEMY);
        }

        color_THM = cityCalcArea.ths[0];
        color_THP = cityCalcArea.ths[1];
        int value = cityCalcArea.colors[0];
        color_RGB = value;
        color_R = Color.red(value);
        color_G = Color.green(value);
        color_B = Color.blue(value);
        
        tvR.setText(String.valueOf(color_R));
        tvG.setText(String.valueOf(color_G));
        tvB.setText(String.valueOf(color_B));
        tvTHM.setText(String.valueOf(color_THM));
        tvTHP.setText(String.valueOf(color_THP));

//        originalBitmap = cityCalcArea.bmpSrc;
//        processedBitmap = cityCalcArea.bmpPrc;
//        ivOriginal.setImageBitmap(originalBitmap);
//        ivProcessed.setImageBitmap(processedBitmap);

        etRGB.setText(Integer.toHexString(color_RGB).toUpperCase());
//        tvRecognize.setText(cityCalcArea.ocrText);

    }

    private String recognizePicture(Bitmap bitmap) {

        String result = ""; // результат
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build(); // создаем текстрекогнайзер
        if (textRecognizer.isOperational()) {   // если текстрекогнайзер может что-то распознать
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();    // создаем фрейм на основе переданного битмапа
            SparseArray<TextBlock> items = textRecognizer.detect(frame);    // передаем фрейм в текстрекогнайзер, на выходе - массив текстовых блоков
            for (int i = 0; i < items.size(); ++i) {                        // проходимся по массиву текстовых блоков
                result = result + items.valueAt(i).getValue() + " ";        // добавляем к результату значение текста в очередном блоке, разделяем пробелами
            }
        }
        return result;  // возвращаем результат. Если не было ни одного блока или они все были пустыми - результатом будет пустая строка
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

        fullBitmap = BitmapFactory.decodeFile(GameActivity.fileScreenshot.getAbsolutePath());   // получаем битмап из файла скриншота

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        areaName.set(sharedPreferences.getString(PREF_COLORS_AREA, String.valueOf(R.string.pref_colorsAreaName_default_value)));

        if (areaName.get().equals(getString(R.string.borders_time))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TOTAL_TIME);
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.EARLY_WIN);
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_OUR);
        } else if (areaName.get().equals(getString(R.string.colors_plus_us))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.INCREASE_OUR);
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_ENEMY);
        } else if (areaName.get().equals(getString(R.string.colors_plus_they))) {
            cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.INCREASE_ENEMY);
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
                    
                    if (areaName.get().equals(getString(R.string.borders_time))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.TOTAL_TIME);
                        editor.putInt(getString(R.string.pref_rgb_total_time_main), color_RGB);
                    } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.EARLY_WIN);
                        editor.putInt(getString(R.string.pref_rgb_early_win_main), color_RGB);
                    } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_OUR);
                        editor.putInt(getString(R.string.pref_rgb_points_our_main), color_RGB);
                    } else if (areaName.get().equals(getString(R.string.colors_plus_us))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.INCREASE_OUR);
                        editor.putInt(getString(R.string.pref_rgb_increase_our_main), color_RGB);
                    } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.POINTS_ENEMY);
                        editor.putInt(getString(R.string.pref_rgb_points_enemy_main), color_RGB);
                    } else if (areaName.get().equals(getString(R.string.colors_plus_they))) {
                        cityCalcArea = GameActivity.mainCityCalc.mapAreas.get(Area.INCREASE_ENEMY);
                        editor.putInt(getString(R.string.pref_rgb_increase_enemy_main), color_RGB);
                    }
                    editor.apply();
                    cityCalcArea.colors[0] = color_RGB;
                    cityCalcArea.doOCR();

                    originalBitmap = cityCalcArea.bmpSrc;
                    processedBitmap = cityCalcArea.bmpPrc;
                    ivOriginal.setImageBitmap(originalBitmap);
                    ivProcessed.setImageBitmap(processedBitmap);
                    tvRecognize.setText(cityCalcArea.ocrText);

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
        
        if (areaName.get().equals(getString(R.string.borders_time))) {
            editor.putInt(getString(R.string.pref_rgb_total_time_thm), color_THM);
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
            editor.putInt(getString(R.string.pref_rgb_early_win_thm), color_THM);
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
            editor.putInt(getString(R.string.pref_rgb_points_our_thm), color_THM);
        } else if (areaName.get().equals(getString(R.string.colors_plus_us))) {
            editor.putInt(getString(R.string.pref_rgb_increase_our_thm), color_THM);
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
            editor.putInt(getString(R.string.pref_rgb_points_enemy_thm), color_THM);
        } else if (areaName.get().equals(getString(R.string.colors_plus_they))) {
            editor.putInt(getString(R.string.pref_rgb_increase_enemy_thm), color_THM);
        }
        
        cityCalcArea.ths[0] = color_THM;
        editor.apply();
        
        cityCalcArea.doOCR();

        originalBitmap = cityCalcArea.bmpSrc;
        processedBitmap = cityCalcArea.bmpPrc;
        ivOriginal.setImageBitmap(originalBitmap);
        ivProcessed.setImageBitmap(processedBitmap);
        tvRecognize.setText(cityCalcArea.ocrText);

    }

    public void updateTHP() {

        SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (areaName.get().equals(getString(R.string.borders_time))) {
            editor.putInt(getString(R.string.pref_rgb_total_time_thp), color_THP);
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
            editor.putInt(getString(R.string.pref_rgb_early_win_thp), color_THP);
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
            editor.putInt(getString(R.string.pref_rgb_points_our_thp), color_THP);
        } else if (areaName.get().equals(getString(R.string.colors_plus_us))) {
            editor.putInt(getString(R.string.pref_rgb_increase_our_thp), color_THP);
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
            editor.putInt(getString(R.string.pref_rgb_points_enemy_thp), color_THP);
        } else if (areaName.get().equals(getString(R.string.colors_plus_they))) {
            editor.putInt(getString(R.string.pref_rgb_increase_enemy_thp), color_THP);
        }

        cityCalcArea.ths[1] = color_THP;
        editor.apply();

        cityCalcArea.doOCR();

        originalBitmap = cityCalcArea.bmpSrc;
        processedBitmap = cityCalcArea.bmpPrc;
        ivOriginal.setImageBitmap(originalBitmap);
        ivProcessed.setImageBitmap(processedBitmap);
        tvRecognize.setText(cityCalcArea.ocrText);


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
        if (areaName.get().equals(getString(R.string.borders_time))) {
            color_THM = Integer.parseInt(getString(R.string.def_rgb_total_time_thm));
            color_THP = Integer.parseInt(getString(R.string.def_rgb_total_time_thp));
            value = Integer.parseInt(getString(R.string.def_rgb_total_time_main));
        } else if (areaName.get().equals(getString(R.string.borders_scores_to_early_win))) {
            color_THM = Integer.parseInt(getString(R.string.def_rgb_early_win_thm));
            color_THP = Integer.parseInt(getString(R.string.def_rgb_early_win_thp));
            value = Integer.parseInt(getString(R.string.def_rgb_early_win_main));
        } else if (areaName.get().equals(getString(R.string.borders_our_scores))) {
            color_THM = Integer.parseInt(getString(R.string.def_rgb_points_our_thm));
            color_THP = Integer.parseInt(getString(R.string.def_rgb_points_our_thp));
            value = Integer.parseInt(getString(R.string.def_rgb_points_our_main));
        } else if (areaName.get().equals(getString(R.string.colors_plus_us))) {
            color_THM = Integer.parseInt(getString(R.string.def_rgb_increase_our_thm));
            color_THP = Integer.parseInt(getString(R.string.def_rgb_increase_our_thp));
            value = Integer.parseInt(getString(R.string.def_rgb_increase_our_main));
        } else if (areaName.get().equals(getString(R.string.borders_enemy_scores))) {
            color_THM = Integer.parseInt(getString(R.string.def_rgb_points_enemy_thm));
            color_THP = Integer.parseInt(getString(R.string.def_rgb_points_enemy_thp));
            value = Integer.parseInt(getString(R.string.def_rgb_points_enemy_main));
        } else if (areaName.get().equals(getString(R.string.colors_plus_they))) {
            color_THM = Integer.parseInt(getString(R.string.def_rgb_increase_enemy_thm));
            color_THP = Integer.parseInt(getString(R.string.def_rgb_increase_enemy_thp));
            value = Integer.parseInt(getString(R.string.def_rgb_increase_enemy_main));
        }

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
