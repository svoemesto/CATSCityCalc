package com.mrt.catscitycalc;

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

public class SettingsActivity extends AppCompatActivity {

    String pathToScreenshotDir = "";
    boolean listenLastFile;
    TextView tvScreenshotFolder;
    Switch swListenLastFile;
    Switch swLDebugMode;
    boolean isDebugMode;
    Button btnSelectScreenshotFolder;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //если в шапке нажата кнопка "Назад" - вызываем метод "Назад"
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        tvScreenshotFolder = findViewById(R.id.tv_screenshotfolder);
        swListenLastFile = findViewById(R.id.sw_get_last_screenshot);
        swLDebugMode = findViewById(R.id.sw_debug_mode);
        btnSelectScreenshotFolder = findViewById(R.id.btn_select_screenshot_folder);

        SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
        pathToScreenshotDir = sharedPreferences.getString(getString(R.string.pref_screenshot_folder),"");
        listenLastFile = sharedPreferences.getBoolean(getString(R.string.pref_listen_last_file),false);
        isDebugMode = sharedPreferences.getBoolean(getString(R.string.pref_debug_mode),false);
        tvScreenshotFolder.setText(pathToScreenshotDir);
        swListenLastFile.setChecked(listenLastFile);
        swLDebugMode.setChecked(isDebugMode);


        swListenLastFile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_listen_last_file), isChecked);
                editor.apply();
            }
        });

        swLDebugMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_debug_mode), isChecked);
                editor.apply();
            }
        });


    }



    public void selectScreenshotFolder(View view) {
        OpenFileDialog fileDialog = new OpenFileDialog(this, pathToScreenshotDir)
                .setOnlyFoldersFilter()
                .setFolderIcon(getResources().getDrawable(R.drawable.ic_folder))
                .setFileIcon(getResources().getDrawable(R.drawable.ic_file))
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {
//                        Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();
                        SharedPreferences sharedPreferences = SettingsActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getString(R.string.pref_screenshot_folder), fileName);
                        editor.apply();
                        pathToScreenshotDir = fileName;
                        tvScreenshotFolder.setText(pathToScreenshotDir);
                    }
                });
        fileDialog.show();
    }

}