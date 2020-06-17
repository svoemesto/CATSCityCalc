package com.svoemestodev.catscitycalc.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
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

import com.svoemestodev.catscitycalc.BuildConfig;
import com.svoemestodev.catscitycalc.utils.OpenFileDialog;
import com.svoemestodev.catscitycalc.R;

/**
* Класс активити "Настройки"
**/

public class SettingsActivity extends AppCompatActivity {


    TextView st_tv_version;

    Switch st_sw_get_last_screenshot;
    Button st_btn_select_screenshot_folder;
    TextView st_tv_screenshot_folder;

    Switch st_sw_listen_data_folder;
    Button st_btn_select_data_folder;
    TextView st_tv_data_folder;

    Switch st_sw_listen_whatsapp_folder;
    Button st_btn_whatsapp_data_folder;
    TextView st_tv_telegram_folder;

    Switch st_sw_listen_telegram_folder;
    Button st_btn_telegram_data_folder;
    TextView st_tv_whatsapp_folder;

    Switch st_sw_debug_mode;
    Button st_btn_open_calibrate;
    Button st_btn_open_borders_settings;
    Button st_btn_open_colors_settings;

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

        st_tv_version = findViewById(R.id.st_tv_version);
        st_sw_get_last_screenshot = findViewById(R.id.st_sw_get_last_screenshot);
        st_btn_select_screenshot_folder = findViewById(R.id.st_btn_select_screenshot_folder);
        st_tv_screenshot_folder = findViewById(R.id.st_tv_screenshot_folder);
        st_sw_listen_data_folder = findViewById(R.id.st_sw_listen_data_folder);
        st_btn_select_data_folder = findViewById(R.id.st_btn_select_data_folder);
        st_tv_data_folder = findViewById(R.id.st_tv_data_folder);
        st_sw_listen_whatsapp_folder = findViewById(R.id.st_sw_listen_whatsapp_folder);
        st_btn_whatsapp_data_folder = findViewById(R.id.st_btn_whatsapp_data_folder);
        st_tv_telegram_folder = findViewById(R.id.st_tv_telegram_folder);
        st_sw_listen_telegram_folder = findViewById(R.id.st_sw_listen_telegram_folder);
        st_btn_telegram_data_folder = findViewById(R.id.st_btn_telegram_data_folder);
        st_tv_whatsapp_folder = findViewById(R.id.st_tv_whatsapp_folder);
        st_sw_debug_mode = findViewById(R.id.st_sw_debug_mode);
        st_btn_open_calibrate = findViewById(R.id.st_btn_open_calibrate);
        st_btn_open_borders_settings = findViewById(R.id.st_btn_open_borders_settings);
        st_btn_open_colors_settings = findViewById(R.id.st_btn_open_colors_settings);

        context = st_tv_screenshot_folder.getContext();

                // устанавливаем значения контролов
        st_tv_version.setText(BuildConfig.VERSION_NAME);
        st_tv_screenshot_folder.setText(GameActivity.pathToScreenshotDir);
        st_tv_data_folder.setText(GameActivity.pathToDataDir);
        st_tv_whatsapp_folder.setText(GameActivity.pathToWhatsappDir);
        st_tv_telegram_folder.setText(GameActivity.pathToTelegramDir);
        st_sw_get_last_screenshot.setChecked(GameActivity.isListenToNewFileInFolder);
        st_sw_listen_data_folder.setChecked(GameActivity.isListenDataFolder);
        st_sw_listen_whatsapp_folder.setChecked(GameActivity.isListenWhatsappFolder);
        st_sw_listen_telegram_folder.setChecked(GameActivity.isListenTelegramFolder);
        st_sw_debug_mode.setChecked(GameActivity.isDebugMode);

        st_btn_open_calibrate.setVisibility(st_sw_debug_mode.isChecked() ? View.VISIBLE : View.INVISIBLE);
        st_btn_open_borders_settings.setVisibility(st_sw_debug_mode.isChecked() ? View.VISIBLE : View.INVISIBLE);
        st_btn_open_colors_settings.setVisibility(st_sw_debug_mode.isChecked() ? View.VISIBLE : View.INVISIBLE);

        st_btn_select_data_folder.setEnabled(GameActivity.isListenDataFolder);
        st_btn_whatsapp_data_folder.setEnabled(GameActivity.isListenWhatsappFolder);
        st_btn_telegram_data_folder.setEnabled(GameActivity.isListenTelegramFolder);

        // лисенер на переключение свича "Следить за последним файлом"
        st_sw_get_last_screenshot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        st_sw_listen_data_folder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    // если свич переключился
                // изменяем проперти PREF_LISTEN_LAST_FILE значением свича
                SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_listen_data_folder), isChecked);
                editor.apply();
                GameActivity.isListenDataFolder = isChecked;
                st_btn_select_data_folder.setEnabled(isChecked);
            }
        });

        st_sw_listen_whatsapp_folder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    // если свич переключился
                // изменяем проперти PREF_LISTEN_LAST_FILE значением свича
                SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_listen_whatsapp_folder), isChecked);
                editor.apply();
                GameActivity.isListenWhatsappFolder = isChecked;
                st_btn_whatsapp_data_folder.setEnabled(isChecked);
                if (isChecked) {
                    if (GameActivity.pathToWhatsappDir.equals("")) {
                        String fileName = Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/WhatsApp Documents";
                        editor.putString(getString(R.string.pref_whatsapp_folder), fileName);
                        editor.apply();
                        GameActivity.pathToWhatsappDir = fileName;                     // устанавливаем переменную новым значением
                        st_tv_whatsapp_folder.setText(GameActivity.pathToWhatsappDir);    // устанавливаем текс контрола
                    }

                }
            }
        });

        st_sw_listen_telegram_folder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    // если свич переключился
                // изменяем проперти PREF_LISTEN_LAST_FILE значением свича
                SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_listen_telegram_folder), isChecked);
                editor.apply();
                GameActivity.isListenTelegramFolder = isChecked;
                st_btn_telegram_data_folder.setEnabled(isChecked);
                if (isChecked) {
                    if (GameActivity.pathToTelegramDir.equals("")) {
                        String fileName = Environment.getExternalStorageDirectory().getPath() + "/Telegram/Telegram Documents";
                        editor.putString(getString(R.string.pref_telegram_folder), fileName);
                        editor.apply();
                        GameActivity.pathToTelegramDir = fileName;                     // устанавливаем переменную новым значением
                        st_tv_telegram_folder.setText(GameActivity.pathToTelegramDir);    // устанавливаем текс контрола
                    }
                }
            }
        });


        // лисенер на переключение свича "дебаг мод"
        st_sw_debug_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {     // если свич переключился
                // изменяем проперти PREF_DEBUG_MODE значением свича
                SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_debug_mode), isChecked);
                editor.apply();
                GameActivity.isDebugMode = isChecked;
                st_btn_open_calibrate.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                st_btn_open_borders_settings.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                st_btn_open_colors_settings.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
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
                        st_tv_screenshot_folder.setText(GameActivity.pathToScreenshotDir);    // устанавливаем текс контрола
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

    public void selectDataFolder(View view) {
        // создаем диалог выбора папки, инициализируем его текущим значением папки скриншотов
        OpenFileDialog fileDialog = new OpenFileDialog(this, GameActivity.pathToDataDir)
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
                        editor.putString(getString(R.string.pref_data_folder), fileName);
                        editor.apply();
                        GameActivity.pathToDataDir = fileName;                     // устанавливаем переменную новым значением
                        st_tv_data_folder.setText(GameActivity.pathToDataDir);    // устанавливаем текс контрола
                    }
                });
        fileDialog.show();
    }

    public void selectWhatsappFolder(View view) {
        // создаем диалог выбора папки, инициализируем его текущим значением папки скриншотов
        OpenFileDialog fileDialog = new OpenFileDialog(this, GameActivity.pathToWhatsappDir)
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
                        editor.putString(getString(R.string.pref_whatsapp_folder), fileName);
                        editor.apply();
                        GameActivity.pathToWhatsappDir = fileName;                     // устанавливаем переменную новым значением
                        st_tv_whatsapp_folder.setText(GameActivity.pathToWhatsappDir);    // устанавливаем текс контрола
                    }
                });
        fileDialog.show();
    }

    public void selectTelegramFolder(View view) {
        // создаем диалог выбора папки, инициализируем его текущим значением папки скриншотов
        OpenFileDialog fileDialog = new OpenFileDialog(this, GameActivity.pathToTelegramDir)
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
                        editor.putString(getString(R.string.pref_telegram_folder), fileName);
                        editor.apply();
                        GameActivity.pathToTelegramDir = fileName;                     // устанавливаем переменную новым значением
                        st_tv_telegram_folder.setText(GameActivity.pathToTelegramDir);    // устанавливаем текс контрола
                    }
                });
        fileDialog.show();
    }

}