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
import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    public static ObservableString languageInterface = new ObservableString();
    public static ObservableString languageScreenshot = new ObservableString();

    public static final String PREF_LANGUAGE_INTERFACE_AREA = "pref_language_interface_area";
    public static final String PREF_LANGUAGE_SCREENSHOT_AREA = "pref_language_screenshot_area";

    // Progress Dialog
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    // File url to download
    private static String file_url = "";
    private static String file_destination = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        if (findViewById(R.id.frame_language) != null) {
            if (savedInstanceState != null) return;
            getFragmentManager().beginTransaction().add(R.id.frame_language, new LanguageActivity.LanguageFragment()).commit();

        }
        PreferenceManager.setDefaultValues(this, R.xml.pref_language, true);

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences sharedPreferences = LanguageActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
        languageInterface.set(getString(R.string.pref_language_interface));
        languageScreenshot.set(getString(R.string.pref_language_screenshot));

        languageInterface.setOnStringChangeListener(new OnStringChangeListener()
        {
            @Override
            public void onStringChanged(String newValue)
            {
                SharedPreferences sharedPreferences = LanguageActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.pref_language_interface), newValue);
                editor.putString(getString(R.string.pref_def_language_interface), newValue);
                editor.apply();

                Locale locale = new Locale(newValue);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            }
        });

        languageScreenshot.setOnStringChangeListener(new OnStringChangeListener()
        {
            @Override
            public void onStringChanged(String newValue)
            {
                SharedPreferences sharedPreferences = LanguageActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.pref_language_screenshot), newValue);
                editor.putString(getString(R.string.pref_def_language_screenshot), newValue);
                editor.apply();

                String pathToTessdataFile = GameActivity.pathToCATScalcFolder + "/tessdata/" + newValue + ".traineddata";
                if (!(new File(pathToTessdataFile)).exists()) {
                    file_url = "https://github.com/tesseract-ocr/tessdata/raw/master/" + newValue + ".traineddata";
                    file_destination = pathToTessdataFile;
                    new DownloadFileFromURL().execute(file_url);
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

    public static class LanguageFragment extends PreferenceFragment {

        public static final String PREF_LANGUAGE_INTERFACE_AREA = "pref_language_interface_area";
        public static final String PREF_LANGUAGE_SCREENSHOT_AREA = "pref_language_screenshot_area";
        private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_language);

            onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals(PREF_LANGUAGE_INTERFACE_AREA)) {
                        Preference preference = findPreference(key);
                        preference.setSummary(sharedPreferences.getString(key, String.valueOf(R.string.pref_languageInterfaceAreaName_default_value)));
                        String strLanguageInterface = sharedPreferences.getString(key, String.valueOf(R.string.pref_languageInterfaceAreaName_default_value));

                        LanguageActivity.languageInterface.set(strLanguageInterface);

                    } else if (key.equals(PREF_LANGUAGE_SCREENSHOT_AREA)) {

                        Preference preference = findPreference(key);
                        preference.setSummary(sharedPreferences.getString(key, String.valueOf(R.string.pref_languageScreenshotAreaName_default_value)));
                        String strLanguageScreenshot = sharedPreferences.getString(key, String.valueOf(R.string.pref_languageScreenshotAreaName_default_value));

                        LanguageActivity.languageScreenshot.set(strLanguageScreenshot);


                    }
                }
            };

        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            Preference preference = findPreference(PREF_LANGUAGE_INTERFACE_AREA);
            preference.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_LANGUAGE_INTERFACE_AREA, String.valueOf(R.string.pref_languageInterfaceAreaName_default_value)));
            preference = findPreference(PREF_LANGUAGE_SCREENSHOT_AREA);
            preference.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_LANGUAGE_SCREENSHOT_AREA, String.valueOf(R.string.pref_languageScreenshotAreaName_default_value)));
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        }
    }




    /**
     * Showing Dialog
     * */

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                OutputStream output = new FileOutputStream(file_destination);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);

        }

    }


}
