package com.svoemestodev.catscitycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class ColorsdetectActivity extends AppCompatActivity {

    public RadioGroup radioGroup;
    public RadioButton radioButton;

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

    public static float cut_x1;
    public static float cut_x2;
    public static float cut_y1;
    public static float cut_y2;

    public static Bitmap originalBitmap;
    public static Bitmap processedBitmap;

    public void doProcess() {

        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Matrix matrix = new Matrix();           // матрица ресайза
        matrix.postScale(5.0f, 4.0f);   // будем ресайзать в 4 раза

        Bitmap tmpBitmap = Bitmap.createBitmap(width, height, originalBitmap.getConfig());

        Canvas c = new Canvas();
        c.setBitmap(tmpBitmap);

        int A, R, G, B;
        int pixel;

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = originalBitmap.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                if ((R > (color_R - color_THM) && R < (color_R + color_THP)) && (G > (color_G - color_THM) && G < (color_G + color_THP)) && (B > (color_B - color_THM) && B < (color_B + color_THP))) {
                    R=0;
                    G=0;
                    B=0;
                } else {
                    R=255;
                    G=255;
                    B=255;
                }

                tmpBitmap.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        processedBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, width, height, matrix, false);
        ivProcessed.setImageBitmap(processedBitmap);

        tvRecognize.setText(recognizePicture(processedBitmap));

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

        radioGroup = findViewById(R.id.rg_colors);
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

        fullBitmap = BitmapFactory.decodeFile(MainActivity.fileScreenshot.getAbsolutePath());   // получаем битмап из файла скриншота



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

                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioId);
                    String text = String.valueOf(radioButton.getText());

                    if (text.equals(getString(R.string.borders_time))) {

                        MainActivity.rgb_total_time = color_RGB;
                        SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.pref_rgb_total_time), color_RGB);
                        editor.apply();

                    } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {

                        MainActivity.rgb_early_win = color_RGB;
                        SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.pref_rgb_early_win), color_RGB);
                        editor.apply();

                    } else if (text.equals(getString(R.string.borders_our_scores))) {

                        MainActivity.rgb_total_us = color_RGB;
                        SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.pref_rgb_total_us), color_RGB);
                        editor.apply();

                    } else if (text.equals(getString(R.string.colors_plus_us))) {

                        MainActivity.rgb_plus_us = color_RGB;
                        SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.pref_rgb_plus_us), color_RGB);
                        editor.apply();

                    } else if (text.equals(getString(R.string.borders_enemy_scores))) {

                        MainActivity.rgb_total_they = color_RGB;
                        SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.pref_rgb_total_they), color_RGB);
                        editor.apply();

                    } else if (text.equals(getString(R.string.colors_plus_they))) {

                        MainActivity.rgb_plus_they = color_RGB;
                        SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.pref_rgb_plus_they), color_RGB);
                        editor.apply();

                    }

                    doProcess();

                } catch (NumberFormatException e) {
                }
            }
        });

        checkButton(radioGroup);

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

    public void checkButton(View view) {

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String text = String.valueOf(radioButton.getText());

        if (text.equals(getString(R.string.borders_time))) {

            color_THM = MainActivity.rgb_total_time_threshold_minus;
            color_THP = MainActivity.rgb_total_time_threshold_plus;
            int value = MainActivity.rgb_total_time;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

            cut_x1 = MainActivity.cut_total_time_x1;
            cut_x2 = MainActivity.cut_total_time_x2;
            cut_y1 = MainActivity.cut_total_time_y1;
            cut_y2 = MainActivity.cut_total_time_y2;

        } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {

            color_THM = MainActivity.rgb_early_win_threshold_minus;
            color_THP = MainActivity.rgb_early_win_threshold_plus;
            int value = MainActivity.rgb_early_win;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

            cut_x1 = MainActivity.cut_early_win_x1;
            cut_x2 = MainActivity.cut_early_win_x2;
            cut_y1 = MainActivity.cut_early_win_y1;
            cut_y2 = MainActivity.cut_early_win_y2;

        } else if (text.equals(getString(R.string.borders_our_scores))) {

            color_THM = MainActivity.rgb_total_us_threshold_minus;
            color_THP = MainActivity.rgb_total_us_threshold_plus;
            int value = MainActivity.rgb_total_us;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

            cut_x1 = MainActivity.cut_total_us_x1;
            cut_x2 = MainActivity.cut_total_us_x2;
            cut_y1 = MainActivity.cut_total_us_y1;
            cut_y2 = MainActivity.cut_total_us_y2;

        } else if (text.equals(getString(R.string.colors_plus_us))) {

            color_THM = MainActivity.rgb_plus_us_threshold_minus;
            color_THP = MainActivity.rgb_plus_us_threshold_plus;
            int value = MainActivity.rgb_plus_us;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

            cut_x1 = MainActivity.cut_total_us_x1;
            cut_x2 = MainActivity.cut_total_us_x2;
            cut_y1 = MainActivity.cut_total_us_y1;
            cut_y2 = MainActivity.cut_total_us_y2;

        } else if (text.equals(getString(R.string.borders_enemy_scores))) {

            color_THM = MainActivity.rgb_total_they_threshold_minus;
            color_THP = MainActivity.rgb_total_they_threshold_plus;
            int value = MainActivity.rgb_total_they;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

            cut_x1 = MainActivity.cut_total_they_x1;
            cut_x2 = MainActivity.cut_total_they_x2;
            cut_y1 = MainActivity.cut_total_they_y1;
            cut_y2 = MainActivity.cut_total_they_y2;

        } else if (text.equals(getString(R.string.colors_plus_they))) {

            color_THM = MainActivity.rgb_plus_they_threshold_minus;
            color_THP = MainActivity.rgb_plus_they_threshold_plus;
            int value = MainActivity.rgb_plus_they;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

            cut_x1 = MainActivity.cut_total_they_x1;
            cut_x2 = MainActivity.cut_total_they_x2;
            cut_y1 = MainActivity.cut_total_they_y1;
            cut_y2 = MainActivity.cut_total_they_y2;

        }

        tvR.setText(String.valueOf(color_R));
        tvG.setText(String.valueOf(color_G));
        tvB.setText(String.valueOf(color_B));
        tvTHM.setText(String.valueOf(color_THM));
        tvTHP.setText(String.valueOf(color_THP));

        originalBitmap = cutBorders();
        ivOriginal.setImageBitmap(originalBitmap);

        etRGB.setText(Integer.toHexString(color_RGB).toUpperCase());

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

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String text = String.valueOf(radioButton.getText());

        if (text.equals(getString(R.string.borders_time))) {

            MainActivity.rgb_total_time_threshold_minus = color_THM;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_total_time_threshold_minus), color_THM);
            editor.apply();

        } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {

            MainActivity.rgb_early_win_threshold_minus = color_THM;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_early_win_threshold_minus), color_THM);
            editor.apply();

        } else if (text.equals(getString(R.string.borders_our_scores))) {

            MainActivity.rgb_total_us_threshold_minus = color_THM;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_total_us_threshold_minus), color_THM);
            editor.apply();

        } else if (text.equals(getString(R.string.colors_plus_us))) {

            MainActivity.rgb_plus_us_threshold_minus = color_THM;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_plus_us_threshold_minus), color_THM);
            editor.apply();

        } else if (text.equals(getString(R.string.borders_enemy_scores))) {

            MainActivity.rgb_total_they_threshold_minus = color_THM;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_total_they_threshold_minus), color_THM);
            editor.apply();

        } else if (text.equals(getString(R.string.colors_plus_they))) {

            MainActivity.rgb_plus_they_threshold_minus = color_THM;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_plus_they_threshold_minus), color_THM);
            editor.apply();

        }

        doProcess();

    }

    public void updateTHP() {

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String text = String.valueOf(radioButton.getText());

        if (text.equals(getString(R.string.borders_time))) {

            MainActivity.rgb_total_time_threshold_plus = color_THP;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_total_time_threshold_plus), color_THP);
            editor.apply();

        } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {

            MainActivity.rgb_early_win_threshold_plus = color_THP;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_early_win_threshold_plus), color_THP);
            editor.apply();

        } else if (text.equals(getString(R.string.borders_our_scores))) {

            MainActivity.rgb_total_us_threshold_plus = color_THP;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_total_us_threshold_plus), color_THP);
            editor.apply();

        } else if (text.equals(getString(R.string.colors_plus_us))) {

            MainActivity.rgb_plus_us_threshold_plus = color_THP;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_plus_us_threshold_plus), color_THP);
            editor.apply();

        } else if (text.equals(getString(R.string.borders_enemy_scores))) {

            MainActivity.rgb_total_they_threshold_plus = color_THP;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_total_they_threshold_plus), color_THP);
            editor.apply();

        } else if (text.equals(getString(R.string.colors_plus_they))) {

            MainActivity.rgb_plus_they_threshold_plus = color_THP;
            SharedPreferences sharedPreferences = ColorsdetectActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.pref_rgb_plus_they_threshold_plus), color_THP);
            editor.apply();

        }

        doProcess();

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

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String text = String.valueOf(radioButton.getText());

        if (text.equals(getString(R.string.borders_time))) {

            color_THM = MainActivity.DEFAULT_PREF_RGB_TOTAL_TIME_THRESHOLD_MINUS;
            color_THP = MainActivity.DEFAULT_PREF_RGB_TOTAL_TIME_THRESHOLD_PLUS;
            int value = MainActivity.DEFAULT_PREF_RGB_TOTAL_TIME;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

        } else if (text.equals(getString(R.string.borders_scores_to_early_win))) {

            color_THM = MainActivity.DEFAULT_PREF_RGB_EARLY_WIN_THRESHOLD_MINUS;
            color_THP = MainActivity.DEFAULT_PREF_RGB_EARLY_WIN_THRESHOLD_PLUS;
            int value = MainActivity.DEFAULT_PREF_RGB_EARLY_WIN;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

        } else if (text.equals(getString(R.string.borders_our_scores))) {

            color_THM = MainActivity.DEFAULT_PREF_RGB_TOTAL_US_THRESHOLD_MINUS;
            color_THP = MainActivity.DEFAULT_PREF_RGB_TOTAL_US_THRESHOLD_PLUS;
            int value = MainActivity.DEFAULT_PREF_RGB_TOTAL_US;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

        } else if (text.equals(getString(R.string.colors_plus_us))) {

            color_THM = MainActivity.DEFAULT_PREF_RGB_PLUS_US_THRESHOLD_MINUS;
            color_THP = MainActivity.DEFAULT_PREF_RGB_PLUS_US_THRESHOLD_PLUS;
            int value = MainActivity.DEFAULT_PREF_RGB_PLUS_US;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

        } else if (text.equals(getString(R.string.borders_enemy_scores))) {

            color_THM = MainActivity.DEFAULT_PREF_RGB_TOTAL_THEY_THRESHOLD_MINUS;
            color_THP = MainActivity.DEFAULT_PREF_RGB_TOTAL_THEY_THRESHOLD_PLUS;
            int value = MainActivity.DEFAULT_PREF_RGB_TOTAL_THEY;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

        } else if (text.equals(getString(R.string.colors_plus_they))) {

            color_THM = MainActivity.DEFAULT_PREF_RGB_PLUS_THEY_THRESHOLD_MINUS;
            color_THP = MainActivity.DEFAULT_PREF_RGB_PLUS_THEY_THRESHOLD_PLUS;
            int value = MainActivity.DEFAULT_PREF_RGB_PLUS_THEY;
            color_RGB = value;
            color_R = Color.red(value);
            color_G = Color.green(value);
            color_B = Color.blue(value);

        }

        tvR.setText(String.valueOf(color_R));
        tvG.setText(String.valueOf(color_G));
        tvB.setText(String.valueOf(color_B));
        tvTHM.setText(String.valueOf(color_THM));
        tvTHP.setText(String.valueOf(color_THP));

        etRGB.setText(Integer.toHexString(color_RGB).toUpperCase());

    }
}
