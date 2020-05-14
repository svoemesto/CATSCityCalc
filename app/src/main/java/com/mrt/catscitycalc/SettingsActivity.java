package com.mrt.catscitycalc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
* Класс активити "Настройки"
**/

public class SettingsActivity extends AppCompatActivity {

    String pathToScreenshotDir = "";    // путь к папке скриншотов
    boolean listenLastFile;             // флаг "следить за последним файлом в папке"
    TextView tvScreenshotFolder;        // текствью пути к папке скриншотов
    Switch swListenLastFile;            // свич "следить за последним файлом в папке"
    Switch swLDebugMode;                // свич "дебаг мод"
    boolean isDebugMode;                // флаг "дебаг мод"
    Button btnSelectScreenshotFolder;   // кнопка "Выбрать папку скриншотов"

    TextView tvCalibrate;               // вьюшка надписи "Калибровка..."
    Button btnCalibrateReset;           // кнопка "Сброс калибровки"
    ImageButton btnCalibrateMinus10;         // кнопка шаг -10
    ImageButton btnCalibrateMinus1;          // кнопка шаг -1
    ImageButton btnCalibratePlus1;           // кнопка шаг +1
    ImageButton btnCalibratePlus10;           // кнопка шаг +10
    EditText etCalibrate;               // текстовое поле калибровки
    ImageView ivCalibrate;              // иможвьюшка калибровки


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


    /**
     * Событие создания активити
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);             // вызваем супер-метод
        setContentView(R.layout.settings_activity);     // устанавливаем текущий вью лайаутом страницы "Настройки"

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        // биндим контролы
        tvScreenshotFolder = findViewById(R.id.tv_screenshotfolder);
        swListenLastFile = findViewById(R.id.sw_get_last_screenshot);
        swLDebugMode = findViewById(R.id.sw_debug_mode);
        btnSelectScreenshotFolder = findViewById(R.id.btn_select_screenshot_folder);
        tvCalibrate = findViewById(R.id.tv_calibrate);
        etCalibrate = findViewById(R.id.et_calibrate);
        ivCalibrate = findViewById(R.id.iv_calibrate);
        btnCalibrateReset = findViewById(R.id.btn_calibrate_reset);
        btnCalibrateMinus10 = findViewById(R.id.btn_calibrate_minus10);
        btnCalibrateMinus1 = findViewById(R.id.btn_calibrate_minus1);
        btnCalibratePlus1 = findViewById(R.id.btn_calibrate_plus1);
        btnCalibratePlus10 = findViewById(R.id.btn_calibrate_plus10);

        // считываем проперти в соответствующие переменные
        SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
        pathToScreenshotDir = sharedPreferences.getString(getString(R.string.pref_screenshot_folder),"");
        listenLastFile = sharedPreferences.getBoolean(getString(R.string.pref_listen_last_file),false);
        isDebugMode = sharedPreferences.getBoolean(getString(R.string.pref_debug_mode),false);
        MainActivity.calibrate = sharedPreferences.getInt(getString(R.string.pref_calibrate), 0);

        // устанавливаем значения контролов
        tvScreenshotFolder.setText(pathToScreenshotDir);
        swListenLastFile.setChecked(listenLastFile);
        swLDebugMode.setChecked(isDebugMode);
        etCalibrate.setText(String.valueOf(MainActivity.calibrate));

        etCalibrate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    MainActivity.calibrate = Integer.parseInt(String.valueOf(s));
                } catch (NumberFormatException e) {
                    MainActivity.calibrate = 0;
                }
                // изменяем проперти PREF_CALIBRATE ввыдкнным значением
                SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(getString(R.string.pref_calibrate), MainActivity.calibrate);
                editor.apply();
                showCalibratedImage();
            }
        });

        // лисенер на переключение свича "Следить за последним файлом"
        swListenLastFile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    // если свич переключился
                // изменяем проперти PREF_LISTEN_LAST_FILE значением свича
                SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_listen_last_file), isChecked);
                editor.apply();
            }
        });

        // лисенер на переключение свича "дебаг мод"
        swLDebugMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {     // если свич переключился
                // изменяем проперти PREF_DEBUG_MODE значением свича
                SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_debug_mode), isChecked);
                editor.apply();
            }
        });

        showCalibratedImage();

    }

    // метод выбора папки скриншотов (вызов прописан в XML)
    public void selectScreenshotFolder(View view) {
        // создаем диалог выбора папки, инициализируем его текущим значением папки скриншотов
        OpenFileDialog fileDialog = new OpenFileDialog(this, pathToScreenshotDir)
                .setOnlyFoldersFilter()                                             // показывать только папки
                .setFolderIcon(getResources().getDrawable(R.drawable.ic_folder))    // икнока для папок
                .setFileIcon(getResources().getDrawable(R.drawable.ic_file))        // иконка для файлов
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    // когда диалог выбора папки вернул новое значение
                    public void OnSelectedFile(String fileName) {
                        // изменяем проперти PREF_SCREENSHOT_FOLDER значением, которое вернул диалог выбора папки
                        SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getString(R.string.pref_screenshot_folder), fileName);
                        editor.apply();
                        pathToScreenshotDir = fileName;                     // устанавливаем переменную новым значением
                        tvScreenshotFolder.setText(pathToScreenshotDir);    // устанавливаем текс контрола
                    }
                });
        fileDialog.show();
    }

    private void showCalibratedImage() {

        Bitmap sourceBitmap = BitmapFactory.decodeFile(MainActivity.fileScreenshot.getAbsolutePath());   // получаем битмап из файла скриншота
        CuttedPictures cuttedPictures = CutPicture.cutPictire(sourceBitmap);
        ivCalibrate.setImageBitmap(cuttedPictures.croppingBitmap);

    }

    public void calibrateReset(View view) {
        etCalibrate.setText(String.valueOf(0));
    }

    public void calibrateMinus10(View view) {
        etCalibrate.setText(String.valueOf(MainActivity.calibrate - 10));
    }

    public void calibrateMinus1(View view) {
        etCalibrate.setText(String.valueOf(MainActivity.calibrate - 1));
    }

    public void calibratePlus1(View view) {
        etCalibrate.setText(String.valueOf(MainActivity.calibrate + 1));
    }

    public void calibratePlus10(View view) {
        etCalibrate.setText(String.valueOf(MainActivity.calibrate + 10));
    }
}