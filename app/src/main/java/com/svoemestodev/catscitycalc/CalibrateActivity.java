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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CalibrateActivity extends AppCompatActivity {

    TextView tvCalibrateX;               // вьюшка надписи "Калибровка..."
    Button btnCalibrateResetX;           // кнопка "Сброс калибровки"
    ImageButton btnCalibrateMinus10x;         // кнопка шаг -10
    ImageButton btnCalibrateMinus1x;          // кнопка шаг -1
    ImageButton btnCalibratePlus1x;           // кнопка шаг +1
    ImageButton btnCalibratePlus10x;           // кнопка шаг +10
    EditText etCalibrateX;               // текстовое поле калибровки

    TextView tvCalibrateY;               // вьюшка надписи "Калибровка..."
    Button btnCalibrateResetY;           // кнопка "Сброс калибровки"
    ImageButton btnCalibrateMinus10y;         // кнопка шаг -10
    ImageButton btnCalibrateMinus1y;          // кнопка шаг -1
    ImageButton btnCalibratePlus1y;           // кнопка шаг +1
    ImageButton btnCalibratePlus10y;           // кнопка шаг +10
    EditText etCalibrateY;               // текстовое поле калибровки

    ImageView ivCalibrate;              // иможвьюшка калибровки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        tvCalibrateX = findViewById(R.id.tv_calibrate_x);
        etCalibrateX = findViewById(R.id.et_calibrate_x);
        btnCalibrateResetX = findViewById(R.id.btn_calibrate_x_reset);
        btnCalibrateMinus10x = findViewById(R.id.btn_calibrate_x_minus10);
        btnCalibrateMinus1x = findViewById(R.id.btn_calibrate_x_minus1);
        btnCalibratePlus1x = findViewById(R.id.btn_calibrate_x_plus1);
        btnCalibratePlus10x = findViewById(R.id.btn_calibrate_x_plus10);
        etCalibrateX.setText(String.valueOf(MainActivity.calibrateX));

        tvCalibrateY = findViewById(R.id.tv_calibrate_y);
        etCalibrateY = findViewById(R.id.et_calibrate_y);
        btnCalibrateResetY = findViewById(R.id.btn_calibrate_y_reset);
        btnCalibrateMinus10y = findViewById(R.id.btn_calibrate_y_minus10);
        btnCalibrateMinus1y = findViewById(R.id.btn_calibrate_y_minus1);
        btnCalibratePlus1y = findViewById(R.id.btn_calibrate_y_plus1);
        btnCalibratePlus10y = findViewById(R.id.btn_calibrate_y_plus10);
        etCalibrateY.setText(String.valueOf(MainActivity.calibrateY));

        ivCalibrate = findViewById(R.id.iv_calibrate);


        etCalibrateX.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    MainActivity.calibrateX = Integer.parseInt(String.valueOf(s));
                } catch (NumberFormatException e) {
                    MainActivity.calibrateX = 0;
                }
                // изменяем проперти PREF_CALIBRATE ввыдкнным значением
                SharedPreferences sharedPreferences = CalibrateActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(getString(R.string.pref_calibrate_x), MainActivity.calibrateX);
                editor.apply();
                MainActivity.mainCityCalc = new CityCalc(MainActivity.fileScreenshot, MainActivity.calibrateX, MainActivity.calibrateY, ivCalibrate.getContext());
                ivCalibrate.setImageBitmap(MainActivity.mainCityCalc.mapAreas.get(Area.CITY).bmpSrc);
            }
        });

        etCalibrateY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    MainActivity.calibrateY = Integer.parseInt(String.valueOf(s));
                } catch (NumberFormatException e) {
                    MainActivity.calibrateY = 0;
                }
                // изменяем проперти PREF_CALIBRATE ввыдкнным значением
                SharedPreferences sharedPreferences = CalibrateActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(getString(R.string.pref_calibrate_y), MainActivity.calibrateY);
                editor.apply();
                MainActivity.mainCityCalc = new CityCalc(MainActivity.fileScreenshot, MainActivity.calibrateX, MainActivity.calibrateY, ivCalibrate.getContext());
                ivCalibrate.setImageBitmap(MainActivity.mainCityCalc.mapAreas.get(Area.CITY).bmpSrc);

            }
        });

        ivCalibrate.setImageBitmap(MainActivity.mainCityCalc.mapAreas.get(Area.CITY).bmpSrc);

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



    public void calibrateResetX(View view) {
        etCalibrateX.setText(String.valueOf(0));
    }

    public void calibrateMinus10x(View view) {
        etCalibrateX.setText(String.valueOf(MainActivity.calibrateX - 10));
    }

    public void calibrateMinus1x(View view) {
        etCalibrateX.setText(String.valueOf(MainActivity.calibrateX - 1));
    }

    public void calibratePlus1x(View view) {
        etCalibrateX.setText(String.valueOf(MainActivity.calibrateX + 1));
    }

    public void calibratePlus10x(View view) {
        etCalibrateX.setText(String.valueOf(MainActivity.calibrateX + 10));
    }

    public void calibrateResetY(View view) {
        etCalibrateY.setText(String.valueOf(0));
    }

    public void calibrateMinus10y(View view) {
        etCalibrateY.setText(String.valueOf(MainActivity.calibrateY - 10));
    }

    public void calibrateMinus1y(View view) {
        etCalibrateY.setText(String.valueOf(MainActivity.calibrateY - 1));
    }

    public void calibratePlus1y(View view) {
        etCalibrateY.setText(String.valueOf(MainActivity.calibrateY + 1));
    }

    public void calibratePlus10y(View view) {
        etCalibrateY.setText(String.valueOf(MainActivity.calibrateY + 10));
    }
}
