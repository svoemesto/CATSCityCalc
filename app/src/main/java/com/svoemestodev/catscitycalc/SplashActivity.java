package com.svoemestodev.catscitycalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private static final int PERMISSION_ALL = 0;
    private Handler h;
    private Runnable r;
    private static final String TAG = "Splash";

    SharedPreferences mPrefs;
    final String settingScreenShownPref = "settingScreenShown";
    final String versionCheckedPref = "versionChecked";


    TextView tv_sp_title;
    ImageView iv_sp_logo;
    TextView tv_sp_version;
    TextView tv_sp_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv_sp_title = findViewById(R.id.tv_sp_title);
        iv_sp_logo = findViewById(R.id.iv_sp_logo);
        tv_sp_version = findViewById(R.id.tv_sp_version);
        tv_sp_status = findViewById(R.id.tv_sp_status);

        String versionText = getString(R.string.version) + ": " + BuildConfig.VERSION_NAME;
        tv_sp_version.setText(versionText);
        tv_sp_status.setText(R.string.loading);

        h = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(Splash.this, "Runnable started", Toast.LENGTH_SHORT).show();

//            // (OPTIONAL) these lines to check if the `First run` ativity is required
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;

                mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = mPrefs.edit();

                Boolean settingScreenShown = mPrefs.getBoolean(settingScreenShownPref, false);
                int savedVersionCode = mPrefs.getInt(versionCheckedPref, 1);

                if (!settingScreenShown || savedVersionCode != versionCode) {

                    editor.putBoolean(settingScreenShownPref, true);
                    editor.putInt(versionCheckedPref, versionCode);
                    editor.commit();

                }

                createProgramDir();

//                Log.i(TAG, "sharedPreferences...");
//                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
//                String languageToLoad = sharedPreferences.getString(getString(R.string.pref_language_interface),sharedPreferences.getString(getString(R.string.pref_def_language_interface),"en"));
//                Log.i(TAG, "languageToLoad: " + languageToLoad);
//
//                Locale locale = new Locale(languageToLoad);
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
//                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                startActivity(new Intent(SplashActivity.this, GameActivity.class));

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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

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

        String logMsgPref = "createProgramDir: ";
        Log.i(TAG, logMsgPref + "start");

        // путь к папке программы в корне файловой системы. Если такой папки нет - создаем её
        String pathToCATScalcFolder = Environment.getExternalStorageDirectory().getPath() + "/" + getString(R.string.program_folder);
        Log.i(TAG, logMsgPref + "pathToCATScalcFolder = " + pathToCATScalcFolder);


        File cityCatDir = new File(pathToCATScalcFolder);
        if (!cityCatDir.exists()) {
            Log.i(TAG, logMsgPref + "папки " + pathToCATScalcFolder + " не существует, создаем папку");
            cityCatDir.mkdir();
            Log.i(TAG, logMsgPref + "Создана папка " + pathToCATScalcFolder);
        }

        if (cityCatDir.exists()) { // если папка есть

            Car.pathToFile = Environment.getExternalStorageDirectory().getPath() + "/" + getString(R.string.program_folder) + "/" + getString(R.string.file_list_cars);
            Car.pathToCATScalcFolder = Environment.getExternalStorageDirectory().getPath() + "/" + getString(R.string.program_folder);

            File plastics = new File(Car.pathToFile);
            if (!plastics.exists()) {
                Car.saveList(Car.getDefaultList());
            }


            File tmp = new File(pathToCATScalcFolder, "last_screenshot.PNG");       // файл картинки - путь к папке программы + имя файла

            if (!tmp.exists()) {    // если файла нет
                Log.i(TAG, logMsgPref + "Файл " + tmp.getAbsolutePath() + " не существует, надо взять из рессурса.");
//                Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.raw.stub_screenshot);  // открываем битмап из ресурса
                try {
                    Log.i(TAG, logMsgPref + "Копирование файла " + tmp.getAbsolutePath() + " из рессурса.");


                    InputStream inputStream = getAssets().open("stub_screenshot.jpg");
                    OutputStream outputStream = new FileOutputStream(tmp);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                    inputStream.close();
                    outputStream.close();

                    Log.i(TAG, logMsgPref + "Файл " + tmp.getAbsolutePath() + " успешно скопирован из рессурса.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
