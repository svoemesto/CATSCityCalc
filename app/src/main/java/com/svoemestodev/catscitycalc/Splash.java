package com.svoemestodev.catscitycalc;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Splash extends Activity {
    private static final int PERMISSION_ALL = 0;
    private Handler h;
    private Runnable r;
    private static final String TAG = "Splash";
    /*
      SharedPreferences mPrefs;
      final String settingScreenShownPref = "settingScreenShown";
      final String versionCheckedPref = "versionChecked";
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        h = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(Splash.this, "Runnable started", Toast.LENGTH_SHORT).show();

//            // (OPTIONAL) these lines to check if the `First run` ativity is required
//                int versionCode = BuildConfig.VERSION_CODE;
//                String versionName = BuildConfig.VERSION_NAME;
//
//                mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                SharedPreferences.Editor editor = mPrefs.edit();
//
//                Boolean settingScreenShown = mPrefs.getBoolean(settingScreenShownPref, false);
//                int savedVersionCode = mPrefs.getInt(versionCheckedPref, 1);
//
//                if (!settingScreenShown || savedVersionCode != versionCode) {
//                    startActivity(new Intent(Splash.this, FirstRun.class));
//                    editor.putBoolean(settingScreenShownPref, true);
//                    editor.putInt(versionCheckedPref, versionCode);
//                    editor.commit();
//                }
//                else



                createProgramDir();
                Log.i(TAG, "sharedPreferences...");
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                String languageToLoad = sharedPreferences.getString(getString(R.string.pref_language_interface),sharedPreferences.getString(getString(R.string.pref_def_language_interface),"en"));
                Log.i(TAG, "languageToLoad: " + languageToLoad);

                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


                startActivity(new Intent(Splash.this, GameActivity.class));
                finish();
            }
        };

        String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if(!UtilPermissions.hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        else
            h.postDelayed(r, 1500);
    }

    // Put the below OnRequestPermissionsResult code here

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        int index = 0;
        Map<String, Integer> PermissionsMap = new HashMap<String, Integer>();
        for (String permission : permissions){
            PermissionsMap.put(permission, grantResults[index]);
            index++;
        }

        if((PermissionsMap.get(Manifest.permission.READ_EXTERNAL_STORAGE) != 0)
                || PermissionsMap.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) != 0){
            Toast.makeText(this, "Read and write storage permissions are a must", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            h.postDelayed(r, 1500);
        }
    }

    public void createProgramDir() {

//        String logMsgPref = "createProgramDir: ";
//        Log.i(TAG, logMsgPref + "start");
//
//        // путь к папке программы в корне файловой системы. Если такой папки нет - создаем её
//        String pathToCATScalcFolder = Environment.getExternalStorageDirectory().getPath() + "/CATScalc";
//        Log.i(TAG, logMsgPref + "pathToCATScalcFolder = " + pathToCATScalcFolder);
//
//        String pathToTessFolder = pathToCATScalcFolder + "/tessdata";
//        Log.i(TAG, logMsgPref + "pathToTessFolder = " + pathToTessFolder);
//
//        File cityCatDir = new File(pathToCATScalcFolder);
//        File tessDir = new File(pathToTessFolder);
//        if (!cityCatDir.exists()) {
//            Log.i(TAG, logMsgPref + "папки " + pathToCATScalcFolder + " не существует, создаем папку");
//            cityCatDir.mkdir();
//            Log.i(TAG, logMsgPref + "Создана папка " + pathToCATScalcFolder);
//        }
//
//        if (cityCatDir.exists()) { // если папка есть
//            File tmp = new File(pathToCATScalcFolder, "last_screenshot.PNG");       // файл картинки - путь к папке программы + имя файла
//            if (!tessDir.exists()) {
//                Log.i(TAG, logMsgPref + "папки " + pathToTessFolder + " не существует, создаем папку");
//                tessDir.mkdir();
//                Log.i(TAG, logMsgPref + "Создана папка " + pathToTessFolder);
//            }
//
//            File tessEng = new File(pathToTessFolder + "/eng.traineddata");
//            if (!tessEng.exists()) {
//                Log.i(TAG, logMsgPref + "Файл " + pathToTessFolder + "/eng.traineddata не существует, надо скачать.");
//                String file_url = "https://github.com/tesseract-ocr/tessdata/raw/master/eng.traineddata";
//                new DownloadTask(Splash.this, file_url, pathToCATScalcFolder + "/tessdata/");
//            }
//
//            if (!tmp.exists()) {    // если файла нет
//                Log.i(TAG, logMsgPref + "Файл " + tmp.getAbsolutePath() + " не существует, надо взять из рессурса.");
//                Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.raw.stub_screenshot);  // открываем битмап из ресурса
//                try {
//                    Log.i(TAG, logMsgPref + "Копирование файла " + tmp.getAbsolutePath() + " из рессурса.");
//                    OutputStream fOutScreenshot = new FileOutputStream(tmp);                       // открываем поток вывода
//                    sourceBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutScreenshot); // сжимаем картинку в ПНГ с качеством 100%
//                    fOutScreenshot.flush();                                                       // сохраняем данные из потока
//                    fOutScreenshot.close(); // закрываем поток
//                    Log.i(TAG, logMsgPref + "Файл " + tmp.getAbsolutePath() + " успешно скопирован из рессурса.");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

}