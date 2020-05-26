package com.svoemestodev.catscitycalc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
* Класс активити "Настройки"
**/

public class SettingsActivity extends AppCompatActivity {

//    String pathToScreenshotDir = "";    // путь к папке скриншотов
//    boolean listenLastFile;             // флаг "следить за последним файлом в папке"
    TextView tvVersion;        // текствью пути к папке скриншотов
    TextView tvScreenshotFolder;        // текствью пути к папке скриншотов
    Switch swListenLastFile;            // свич "следить за последним файлом в папке"
    Switch swLDebugMode;                // свич "дебаг мод"
//    boolean isDebugMode;                // флаг "дебаг мод"
    Button btnSelectScreenshotFolder;   // кнопка "Выбрать папку скриншотов"
    Button btnOpenCalibrate;   // кнопка "Выбрать папку скриншотов"
    Button btnOpenBorder;   // кнопка "Выбрать папку скриншотов"
    Button btnOpenColors;   // кнопка "Выбрать папку скриншотов"

    public Context context;

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
        setContentView(R.layout.activity_settings);     // устанавливаем текущий вью лайаутом страницы "Настройки"

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        // биндим контролы
        tvVersion = findViewById(R.id.tv_version);
        tvScreenshotFolder = findViewById(R.id.tv_screenshotfolder);
        swListenLastFile = findViewById(R.id.sw_get_last_screenshot);
        swLDebugMode = findViewById(R.id.sw_debug_mode);
        btnSelectScreenshotFolder = findViewById(R.id.btn_select_screenshot_folder);
        btnOpenCalibrate = findViewById(R.id.btn_open_calibrate);
        btnOpenBorder = findViewById(R.id.btn_open_borders_settings);
        btnOpenColors = findViewById(R.id.btn_open_colors_settings);

        context = tvScreenshotFolder.getContext();

                // устанавливаем значения контролов
        tvVersion.setText(BuildConfig.VERSION_NAME);
        tvScreenshotFolder.setText(GameActivity.pathToScreenshotDir);
        swListenLastFile.setChecked(GameActivity.isListenToNewFileInFolder);
        swLDebugMode.setChecked(GameActivity.isDebugMode);

        btnOpenCalibrate.setVisibility(swLDebugMode.isChecked() ? View.VISIBLE : View.INVISIBLE);
        btnOpenBorder.setVisibility(swLDebugMode.isChecked() ? View.VISIBLE : View.INVISIBLE);
        btnOpenColors.setVisibility(swLDebugMode.isChecked() ? View.VISIBLE : View.INVISIBLE);

        // лисенер на переключение свича "Следить за последним файлом"
        swListenLastFile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    // если свич переключился
                // изменяем проперти PREF_LISTEN_LAST_FILE значением свича
                SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_listen_last_file), isChecked);
                editor.apply();
                GameActivity.isListenToNewFileInFolder = isChecked;
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
                GameActivity.isDebugMode = isChecked;
                btnOpenCalibrate.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                btnOpenBorder.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                btnOpenColors.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            }
        });



    }

    // метод выбора папки скриншотов (вызов прописан в XML)
    public void selectScreenshotFolder(View view) {
        // создаем диалог выбора папки, инициализируем его текущим значением папки скриншотов
        OpenFileDialog fileDialog = new OpenFileDialog(this, GameActivity.pathToScreenshotDir)
                .setOnlyFoldersFilter()                                             // показывать только папки
                .setFolderIcon(ContextCompat.getDrawable(context, R.drawable.ic_folder))    // икнока для папок
                .setFileIcon(ContextCompat.getDrawable(context, R.drawable.ic_file))        // иконка для файлов
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    // когда диалог выбора папки вернул новое значение
                    public void OnSelectedFile(String fileName) {
                        // изменяем проперти PREF_SCREENSHOT_FOLDER значением, которое вернул диалог выбора папки
                        SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getString(R.string.pref_screenshot_folder), fileName);
                        editor.apply();
                        GameActivity.pathToScreenshotDir = fileName;                     // устанавливаем переменную новым значением
                        tvScreenshotFolder.setText(GameActivity.pathToScreenshotDir);    // устанавливаем текс контрола
                    }
                });
        fileDialog.show();
    }

    public void openCalibrate(View view) {
        Intent intent = new Intent(this, CalibrateActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, 0);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }

    public void openBordersSettings(View view) {
        Intent intent = new Intent(this, BordersActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, 0);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }

    public void openColorsdetectSettings(View view) {
        Intent intent = new Intent(this, ColorsdetectActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, 0);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }
}