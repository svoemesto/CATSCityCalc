package com.svoemestodev.catscitycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class BordersActivity extends AppCompatActivity {

    public RadioGroup radioGroup;
    public RadioButton radioButton;
    public EditText etX1;
    public EditText etX2;
    public EditText etY1;
    public EditText etY2;
    public ImageView ivPicture;
    public static float cut_x1;
    public static float cut_x2;
    public static float cut_y1;
    public static float cut_y2;

    public static Bitmap fullBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borders);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        radioGroup = findViewById(R.id.rg_borders);
        etX1 = findViewById(R.id.et_border_x1);
        etX2 = findViewById(R.id.et_border_x2);
        etY1 = findViewById(R.id.et_border_y1);
        etY2 = findViewById(R.id.et_border_y2);
        ivPicture = findViewById(R.id.iv_borders);

        fullBitmap = BitmapFactory.decodeFile(MainActivity.fileScreenshot.getAbsolutePath());   // получаем битмап из файла скриншота

        checkButton(radioGroup);

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
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioId);
                    String text = String.valueOf(radioButton.getText());
                    if (text.equals(getString(R.string.borders_city))) {
                        MainActivity.cut_city_x1 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_city_x1), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_time))) {
                        MainActivity.cut_total_time_x1 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_time_x1), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {
                        MainActivity.cut_early_win_x1 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_early_win_x1), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_our_scores))) {
                        MainActivity.cut_total_us_x1 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_us_x1), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_enemy_scores))) {
                        MainActivity.cut_total_they_x1 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_they_x1), value);
                        editor.apply();
                    }
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
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioId);
                    String text = String.valueOf(radioButton.getText());
                    if (text.equals(getString(R.string.borders_city))) {
                        MainActivity.cut_city_x2 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_city_x2), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_time))) {
                        MainActivity.cut_total_time_x2 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_time_x2), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {
                        MainActivity.cut_early_win_x2 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_early_win_x2), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_our_scores))) {
                        MainActivity.cut_total_us_x2 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_us_x2), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_enemy_scores))) {
                        MainActivity.cut_total_they_x2 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_they_x2), value);
                        editor.apply();
                    }
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
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioId);
                    String text = String.valueOf(radioButton.getText());
                    if (text.equals(getString(R.string.borders_city))) {
                        MainActivity.cut_city_y1 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_city_y1), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_time))) {
                        MainActivity.cut_total_time_y1 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_time_y1), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {
                        MainActivity.cut_early_win_y1 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_early_win_y1), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_our_scores))) {
                        MainActivity.cut_total_us_y1 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_us_y1), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_enemy_scores))) {
                        MainActivity.cut_total_they_y1 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_they_y1), value);
                        editor.apply();
                    }
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
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioId);
                    String text = String.valueOf(radioButton.getText());
                    if (text.equals(getString(R.string.borders_city))) {
                        MainActivity.cut_city_y2 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_city_y2), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_time))) {
                        MainActivity.cut_total_time_y2 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_time_y2), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {
                        MainActivity.cut_early_win_y2 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_early_win_y2), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_our_scores))) {
                        MainActivity.cut_total_us_y2 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_us_y2), value);
                        editor.apply();
                    } else if (text.equals(getString(R.string.borders_enemy_scores))) {
                        MainActivity.cut_total_they_y2 = value;
                        SharedPreferences sharedPreferences = BordersActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(getString(R.string.pref_cut_total_they_y2), value);
                        editor.apply();
                    }
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

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String text = String.valueOf(radioButton.getText());
        if (text.equals(getString(R.string.borders_city))) {
            cut_x1 = MainActivity.DEFAULT_PREF_CUT_CITY_X1;
        } else if (text.equals(getString(R.string.borders_time))) {
            cut_x1 = MainActivity.DEFAULT_PREF_CUT_TOTAL_TIME_X1;
        } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {
            cut_x1 = MainActivity.DEFAULT_PREF_CUT_EARLY_WIN_X1;
        } else if (text.equals(getString(R.string.borders_our_scores))) {
            cut_x1 = MainActivity.DEFAULT_PREF_CUT_TOTAL_US_X1;
        } else if (text.equals(getString(R.string.borders_enemy_scores))) {
            cut_x1 = MainActivity.DEFAULT_PREF_CUT_TOTAL_THEY_X1;
        }
        etX1.setText(String.valueOf(cut_x1));

    }

    public void resetX2(View view) {

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String text = String.valueOf(radioButton.getText());
        if (text.equals(getString(R.string.borders_city))) {
            cut_x2 = MainActivity.DEFAULT_PREF_CUT_CITY_X2;
        } else if (text.equals(getString(R.string.borders_time))) {
            cut_x2 = MainActivity.DEFAULT_PREF_CUT_TOTAL_TIME_X2;
        } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {
            cut_x2 = MainActivity.DEFAULT_PREF_CUT_EARLY_WIN_X2;
        } else if (text.equals(getString(R.string.borders_our_scores))) {
            cut_x2 = MainActivity.DEFAULT_PREF_CUT_TOTAL_US_X2;
        } else if (text.equals(getString(R.string.borders_enemy_scores))) {
            cut_x2 = MainActivity.DEFAULT_PREF_CUT_TOTAL_THEY_X2;
        }
        etX2.setText(String.valueOf(cut_x2));

    }

    public void resetY1(View view) {

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String text = String.valueOf(radioButton.getText());
        if (text.equals(getString(R.string.borders_city))) {
            cut_y1 = MainActivity.DEFAULT_PREF_CUT_CITY_Y1;
        } else if (text.equals(getString(R.string.borders_time))) {
            cut_y1 = MainActivity.DEFAULT_PREF_CUT_TOTAL_TIME_Y1;
        } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {
            cut_y1 = MainActivity.DEFAULT_PREF_CUT_EARLY_WIN_Y1;
        } else if (text.equals(getString(R.string.borders_our_scores))) {
            cut_y1 = MainActivity.DEFAULT_PREF_CUT_TOTAL_US_Y1;
        } else if (text.equals(getString(R.string.borders_enemy_scores))) {
            cut_y1 = MainActivity.DEFAULT_PREF_CUT_TOTAL_THEY_Y1;
        }
        etY1.setText(String.valueOf(cut_y1));

    }

    public void resetY2(View view) {

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String text = String.valueOf(radioButton.getText());
        if (text.equals(getString(R.string.borders_city))) {
            cut_y2 = MainActivity.DEFAULT_PREF_CUT_CITY_Y2;
        } else if (text.equals(getString(R.string.borders_time))) {
            cut_y2 = MainActivity.DEFAULT_PREF_CUT_TOTAL_TIME_Y2;
        } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {
            cut_y2 = MainActivity.DEFAULT_PREF_CUT_EARLY_WIN_Y2;
        } else if (text.equals(getString(R.string.borders_our_scores))) {
            cut_y2 = MainActivity.DEFAULT_PREF_CUT_TOTAL_US_Y2;
        } else if (text.equals(getString(R.string.borders_enemy_scores))) {
            cut_y2 = MainActivity.DEFAULT_PREF_CUT_TOTAL_THEY_Y2;
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

    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String text = String.valueOf(radioButton.getText());

        if (text.equals(getString(R.string.borders_city))) {
            cut_x1 = MainActivity.cut_city_x1;
            cut_x2 = MainActivity.cut_city_x2;
            cut_y1 = MainActivity.cut_city_y1;
            cut_y2 = MainActivity.cut_city_y2;
        } else if (text.equals(getString(R.string.borders_time))) {
            cut_x1 = MainActivity.cut_total_time_x1;
            cut_x2 = MainActivity.cut_total_time_x2;
            cut_y1 = MainActivity.cut_total_time_y1;
            cut_y2 = MainActivity.cut_total_time_y2;
        } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {
            cut_x1 = MainActivity.cut_early_win_x1;
            cut_x2 = MainActivity.cut_early_win_x2;
            cut_y1 = MainActivity.cut_early_win_y1;
            cut_y2 = MainActivity.cut_early_win_y2;
        } else if (text.equals(getString(R.string.borders_our_scores))) {
            cut_x1 = MainActivity.cut_total_us_x1;
            cut_x2 = MainActivity.cut_total_us_x2;
            cut_y1 = MainActivity.cut_total_us_y1;
            cut_y2 = MainActivity.cut_total_us_y2;
        } else if (text.equals(getString(R.string.borders_enemy_scores))) {
            cut_x1 = MainActivity.cut_total_they_x1;
            cut_x2 = MainActivity.cut_total_they_x2;
            cut_y1 = MainActivity.cut_total_they_y1;
            cut_y2 = MainActivity.cut_total_they_y2;
        }

        etX1.setText(String.valueOf(cut_x1));
        etX2.setText(String.valueOf(cut_x2));
        etY1.setText(String.valueOf(cut_y1));
        etY2.setText(String.valueOf(cut_y2));
        ivPicture.setImageBitmap(cutBorders());  // выводим битмат игры в контрол

    }


}
