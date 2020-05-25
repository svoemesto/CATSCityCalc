package com.svoemestodev.catscitycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.MenuItem;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguageActivity extends AppCompatActivity {

    public static ObservableString languageInterface = new ObservableString();
    public static ObservableString languageScreenshot = new ObservableString();

    private static final String TAG = "LanguageActivity";

    public static Map<String, String> mapLangInterface;
    public static Map<String, String> mapLangScreenshot;

    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String logMsgPref = "onCreate: ";
        Log.i(TAG, logMsgPref + "start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        sharedPreferences = LanguageActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);

        String[] langInterfaceEntries;
        String[] langInterfaceValues;
        String[] langScreenshotEntries;
        String[] langScreenshotValues;

        mapLangInterface = new HashMap<>();
        mapLangScreenshot = new HashMap<>();
        langInterfaceEntries = getResources().getStringArray(R.array.pref_language_interface_entries);
        langInterfaceValues = getResources().getStringArray(R.array.pref_language_interface_values);
        langScreenshotEntries = getResources().getStringArray(R.array.pref_language_screenshot_entries);
        langScreenshotValues = getResources().getStringArray(R.array.pref_language_screenshot_values);

        for (int i = 0; i < langInterfaceEntries.length; i++) {
            if (langInterfaceValues.length > i) {
                mapLangInterface.put(langInterfaceValues[i], langInterfaceEntries[i]);
            }
        }
        for (int i = 0; i < langScreenshotEntries.length; i++) {
            if (langScreenshotValues.length > i) {
                mapLangScreenshot.put(langScreenshotValues[i], langScreenshotEntries[i]);
            }
        }

        Log.i(TAG, logMsgPref + "actionBar");
        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        Log.i(TAG, logMsgPref + "FragmentManager");
        if (findViewById(R.id.frame_language) != null) {
            if (savedInstanceState != null) return;
            getFragmentManager().beginTransaction().add(R.id.frame_language, new LanguageActivity.LanguageFragment()).commit();

        }
        PreferenceManager.setDefaultValues(this, R.xml.pref_language, true);

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        languageInterface.set(getString(R.string.pref_language_interface));
        languageScreenshot.set(getString(R.string.pref_language_screenshot));

        Log.i(TAG, logMsgPref + "languageInterface = " + getString(R.string.pref_language_interface));
        Log.i(TAG, logMsgPref + "languageScreenshot = " + getString(R.string.pref_language_screenshot));


        languageInterface.setOnStringChangeListener(new OnStringChangeListener()
        {
            @Override
            public void onStringChanged(String newValue)
            {
                String logMsgPref = "onCreate: ";
                Log.i(TAG, logMsgPref + "languageInterface onStringChanged: newValue = " + newValue);

                if (mapLangInterface.get(newValue) != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.pref_language_interface), newValue);
                    editor.putString(getString(R.string.pref_def_language_interface), newValue);
                    editor.apply();

                    Locale locale = new Locale(newValue);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                } else {
                    String langCode = sharedPreferences.getString(newValue, String.valueOf(R.string.pref_languageInterfaceAreaName_default_value));
                    String langName = LanguageActivity.mapLangInterface.get(langCode);
                    if (langName != null) {
                        LanguageActivity.languageInterface.set(langCode);
                    }
                }

            }
        });

        languageScreenshot.setOnStringChangeListener(new OnStringChangeListener()
        {
            @Override
            public void onStringChanged(String newValue)
            {
                String logMsgPref = "onCreate: ";
                Log.i(TAG, logMsgPref + "languageScreenshot onStringChanged: newValue = " + newValue);

                if (mapLangScreenshot.get(newValue) != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.pref_language_screenshot), newValue);
                    editor.putString(getString(R.string.pref_def_language_screenshot), newValue);
                    editor.apply();

                    String pathToTessdataFile = GameActivity.pathToCATScalcFolder + "/tessdata/" + newValue + ".traineddata";
                    Log.i(TAG, logMsgPref + "languageScreenshot onStringChanged: языковой файл = " + GameActivity.pathToCATScalcFolder + "/tessdata/" + newValue + ".traineddata");
                    if (!(new File(pathToTessdataFile)).exists()) {
                        Log.i(TAG, logMsgPref + "languageScreenshot onStringChanged: файла нет, надо скачать");
                        String file_url = "https://github.com/tesseract-ocr/tessdata/raw/master/" + newValue + ".traineddata";
                        Log.i(TAG, logMsgPref + "languageScreenshot onStringChanged: сссылка для скачивания = " + file_url);
                        Log.i(TAG, logMsgPref + "languageScreenshot onStringChanged: Запускаем скачивание ффйла в папку " + GameActivity.pathToCATScalcFolder + "/tessdata/");
                        new DownloadTask(LanguageActivity.this, file_url, GameActivity.pathToCATScalcFolder + "/tessdata/");
                    } else {
                        Log.i(TAG, logMsgPref + "languageScreenshot onStringChanged: файлесть нет, скачивать не надо");
                    }
                } else {
                    String langCode = sharedPreferences.getString(newValue, String.valueOf(R.string.pref_languageScreenshotAreaName_default_value));
                    String langName = LanguageActivity.mapLangScreenshot.get(langCode);
                    if (langName != null) {
                        LanguageActivity.languageScreenshot.set(langCode);
                    }
                }

            }
        });

    }

    /**
     * Событие нажатия какой-то кнопки в шапке
     **/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String logMsgPref = "onOptionsItemSelected: ";
        Log.i(TAG, logMsgPref + "start");
        int id = item.getItemId();  // индекс нажакой кнопки
        if (id == android.R.id.home) { //если в шапке нажата кнопка "Назад"
            onBackPressed();    // вызываем метод "Назад"
            return true;        // возвращаем Истину
        }
        return super.onOptionsItemSelected(item);   // возвращаем супер-метод
    }

    public static class LanguageFragment extends PreferenceFragment {

        public static final String PREF_LANGUAGE_INTERFACE_AREA = "pref_language_interface_area";
        public static final String PREF_LANGUAGE_SCREENSHOT_AREA = "pref_language_screenshot_area";
        private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            String logMsgPref = "LanguageFragment: onCreate: ";
            Log.i(TAG, logMsgPref + "start");

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_language);

            onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    String logMsgPref = "LanguageFragment: onSharedPreferenceChange: ";
                    Log.i(TAG, logMsgPref + "start");
                    if (key.equals(PREF_LANGUAGE_INTERFACE_AREA)) {

                        Preference preference = findPreference(key);
                        String langCode = sharedPreferences.getString(key, String.valueOf(R.string.pref_languageInterfaceAreaName_default_value));
                        String langName = LanguageActivity.mapLangInterface.get(langCode);
                        preference.setSummary(langName);

                        Log.i(TAG, logMsgPref + "LANGUAGE_INTERFACE: Name = " + langName + ", code = " + langCode);

                        LanguageActivity.languageInterface.set(langCode);

                    } else if (key.equals(PREF_LANGUAGE_SCREENSHOT_AREA)) {
                        Log.i(TAG, logMsgPref + "key.equals(PREF_LANGUAGE_SCREENSHOT_AREA)");

                        Preference preference = findPreference(key);
                        String langCode = sharedPreferences.getString(key, String.valueOf(R.string.pref_languageScreenshotAreaName_default_value));
                        String langName = LanguageActivity.mapLangScreenshot.get(langCode);
                        preference.setSummary(langName);

                        Log.i(TAG, logMsgPref + "LANGUAGE_SCREENSHOT: Name = " + langName + ", code = " + langCode);

                        LanguageActivity.languageScreenshot.set(langCode);

                    }
                }
            };

        }

        @Override
        public void onResume() {
            super.onResume();
            String logMsgPref = "LanguageFragment: onResume: ";
            Log.i(TAG, logMsgPref + "start");
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            Preference preference = findPreference(PREF_LANGUAGE_INTERFACE_AREA);
            preference.setSummary(mapLangInterface.get(getPreferenceScreen().getSharedPreferences().getString(PREF_LANGUAGE_INTERFACE_AREA, String.valueOf(R.string.pref_languageInterfaceAreaName_default_value))));
            preference = findPreference(PREF_LANGUAGE_SCREENSHOT_AREA);
            preference.setSummary(mapLangScreenshot.get(getPreferenceScreen().getSharedPreferences().getString(PREF_LANGUAGE_SCREENSHOT_AREA, String.valueOf(R.string.pref_languageScreenshotAreaName_default_value))));
        }

        @Override
        public void onPause() {
            super.onPause();
            String logMsgPref = "LanguageFragment: onPause: ";
            Log.i(TAG, logMsgPref + "start");
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        }
    }


}
