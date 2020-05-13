package com.mrt.catscitycalc;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_SECOND_ACTIVITY = 100;

    private static final Date DATE_EXPIRED = new GregorianCalendar(2020, 5, 1).getTime();;

    private static final int MY_PERMISSIONS_REQUESTREAD_EXTERNAL_STORAGE = 1;

    private ImageView ivCity; // кропнутая картинка

    private TextView etInTimeLeft;  // время до конца игры (по скриншоту)
    private TextView etInPlusUs; // прирост очков у нас (по скриншоту)
    private TextView etInPlusThey; // прирост очков у них (по скриншоту)
    private TextView etInTotalUs; // всего очнов у нас (по скриншоту)
    private TextView etInTotalThey; // всего очков у них (по скриншоту)
    private TextView etInInstantVic; // очков до досрочной победы (по скриншоту)

    public Switch swListenNewFiles; // переключатель "Отслеживать новые файлы"
    private TextView tvResult; // результат игры

    private File fileScreenshot;

    private Timer timer;
    private long timeLeftInMillis = 0;
    private long timeLeftInMinutesGlobal = 0;
    private String pathToScreenshotDir = "";
    private String pathToCATScalcFolder = "";
    private boolean isListenToNewFileInFolder;
    private boolean isAllFieldsCorrect;

    private static final double XD1 = -0.565d;
    private static final double XD2 = +0.565d;
    private static final double YD1 = -0.800d;
    private static final double YD2 = -0.450d;

    private static final double XD1_TOTALUS = -0.565d;
    private static final double XD2_TOTALUS = -0.180d;
    private static final double YD1_TOTALUS = -0.610d;
    private static final double YD2_TOTALUS = -0.470d;

    private static final double XD1_TOTALTHEY = +0.180d;
    private static final double XD2_TOTALTHEY = +0.565d;
    private static final double YD1_TOTALTHEY = -0.610d;
    private static final double YD2_TOTALTHEY = -0.470d;

    private static final double XD1_TOTALTIME = -0.130d;
    private static final double XD2_TOTALTIME = +0.060d;
    private static final double YD1_TOTALTIME = -0.780d;
    private static final double YD2_TOTALTIME = -0.660d;

    private static final double XD1_INSTANCEVIN = -0.170d;
    private static final double XD2_INSTANCEVIN = +0.170d;
    private static final double YD1_INSTANCEVIN = -0.570d;
    private static final double YD2_INSTANCEVIN = -0.450d;

    
    private Date timeStartGame;
    private Date timeEndGame;
    private Date timeStartTimer;

    private int initMinutesToEndGame;
    private int initPlusUs;
    private int initInstantVic;
    private int initTotalUs;
    private int initTotalThey;
    private int initPlusThey;

    private int currentTotalUs;
    private int currentTotalThey;

    private String calcStatusUs;
    private String calcStatusThey;
    private String calcStatusGame;
    private String calcResult;

    private static final String PATH_TO_CROPPED_FILE = "@drawable/crop_city";

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "chan1";


    // DEBUG MODE
    private boolean isDebugMode;
    private LinearLayout dbgLayout;
    private TextView dbgTvHeader;
    private TextView dbgTvFileName;
    private TextView dbgTvFileDate;
    private ImageView dbgIvTime;
    private TextView dbgTvTime;
    private ImageView dbgIvInstanceVic;
    private TextView dbgTvInstanceVic;
    private ImageView dbgIvUs;
    private TextView dbgTvUs;
    private ImageView dbgIvThey;
    private TextView dbgTvThey;

    private AdView mAdView;

    private String recognizePicture(Bitmap bitmap) {

        String result = "";
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
//            System.out.println("Не могу распознать текст");
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            for (int i = 0; i < items.size(); ++i) {
                result = result + items.valueAt(i).getValue() + " ";
            }
        }
        return result;
    }

    private String cutNotNumericSymbols(String str) {
        String result = "";
        for (char ch : str.toCharArray()) {
            if (ch >= '0' && ch <='9') {
                result = result + ch;
            }
        }
        return result;
    }


    private void cutPicture() {

        if (fileScreenshot != null) {

            Bitmap sourceBitmap = BitmapFactory.decodeFile(fileScreenshot.getAbsolutePath());
            int widthSource = sourceBitmap.getWidth();
            int heightSource = sourceBitmap.getHeight();

            int x1 = (int) ((double) widthSource / 2 + XD1 * heightSource);
            int x2 = (int) ((double) widthSource / 2 + XD2 * heightSource);
            int y1 = (int) ((double) heightSource / 2 + YD1 * ((double) heightSource / 2));
            int y2 = (int) ((double) heightSource / 2 + YD2 * ((double) heightSource / 2));

            if (x1 < 0) x1 = 0;
            if ((x2 > widthSource)) x2 = widthSource;

            int x1totalus = (int) ((double) widthSource / 2 + XD1_TOTALUS * heightSource);
            int x2totalus = (int) ((double) widthSource / 2 + XD2_TOTALUS * heightSource);
            int y1totalus = (int) ((double) heightSource / 2 + YD1_TOTALUS * ((double) heightSource / 2));
            int y2totalus = (int) ((double) heightSource / 2 + YD2_TOTALUS * ((double) heightSource / 2));

            if (x1totalus < 0) x1totalus = 0;
            if ((x2totalus > widthSource)) x2totalus = widthSource;

            int x1totalthey = (int) ((double) widthSource / 2 + XD1_TOTALTHEY * heightSource);
            int x2totalthey = (int) ((double) widthSource / 2 + XD2_TOTALTHEY * heightSource);
            int y1totalthey = (int) ((double) heightSource / 2 + YD1_TOTALTHEY * ((double) heightSource / 2));
            int y2totalthey = (int) ((double) heightSource / 2 + YD2_TOTALTHEY * ((double) heightSource / 2));

            if (x1totalthey < 0) x1totalthey = 0;
            if ((x2totalthey > widthSource)) x2totalthey = widthSource;

            int x1totaltime = (int) ((double) widthSource / 2 + XD1_TOTALTIME * heightSource);
            int x2totaltime = (int) ((double) widthSource / 2 + XD2_TOTALTIME * heightSource);
            int y1totaltime = (int) ((double) heightSource / 2 + YD1_TOTALTIME * ((double) heightSource / 2));
            int y2totaltime = (int) ((double) heightSource / 2 + YD2_TOTALTIME * ((double) heightSource / 2));

            if (x1totaltime < 0) x1totaltime = 0;
            if ((x2totaltime > widthSource)) x2totaltime = widthSource;

            int x1instancevic = (int) ((double) widthSource / 2 + XD1_INSTANCEVIN * heightSource);
            int x2instancevic = (int) ((double) widthSource / 2 + XD2_INSTANCEVIN * heightSource);
            int y1instancevic = (int) ((double) heightSource / 2 + YD1_INSTANCEVIN * ((double) heightSource / 2));
            int y2instancevic = (int) ((double) heightSource / 2 + YD2_INSTANCEVIN * ((double) heightSource / 2));

            if (x1instancevic < 0) x1instancevic = 0;
            if ((x2instancevic > widthSource)) x2instancevic = widthSource;

            Matrix matrix = new Matrix();
            matrix.postScale(4.0f, 4.0f);

            Bitmap croppingBitmap = Bitmap.createBitmap(sourceBitmap, x1, y1, x2 - x1, y2 - y1);
            Bitmap croppingBitmapTotalUs = Bitmap.createBitmap(sourceBitmap, x1totalus, y1totalus, x2totalus - x1totalus, y2totalus - y1totalus, matrix, false);
            Bitmap croppingBitmapTotalThey = Bitmap.createBitmap(sourceBitmap, x1totalthey, y1totalthey, x2totalthey - x1totalthey, y2totalthey - y1totalthey, matrix, false);
            Bitmap croppingBitmapTotalTime = Bitmap.createBitmap(sourceBitmap, x1totaltime, y1totaltime, x2totaltime - x1totaltime, y2totaltime - y1totaltime, matrix, false);
            Bitmap croppingBitmapInstanceVic = Bitmap.createBitmap(sourceBitmap, x1instancevic, y1instancevic, x2instancevic - x1instancevic, y2instancevic - y1instancevic, matrix, false);

            try {

                File fileCity = new File(pathToCATScalcFolder, "city.PNG");
                OutputStream fOutCity = new FileOutputStream(fileCity);
                croppingBitmap.compress(Bitmap.CompressFormat.PNG, 90, fOutCity);
                fOutCity.flush();
                fOutCity.close();

                File fileTotalUs = new File(pathToCATScalcFolder, "totalus.PNG");
                OutputStream fOutTotalUs = new FileOutputStream(fileTotalUs);
                croppingBitmapTotalUs.compress(Bitmap.CompressFormat.PNG, 90, fOutTotalUs);
                fOutTotalUs.flush();
                fOutTotalUs.close();

                File fileTotalThey = new File(pathToCATScalcFolder, "totalthey.PNG");
                OutputStream fOutTotalThey = new FileOutputStream(fileTotalThey);
                croppingBitmapTotalThey.compress(Bitmap.CompressFormat.PNG, 90, fOutTotalThey);
                fOutTotalThey.flush();
                fOutTotalThey.close();

                File fileTotalTime = new File(pathToCATScalcFolder, "totaltime.PNG");
                OutputStream fOutTotalTime = new FileOutputStream(fileTotalTime);
                croppingBitmapTotalTime.compress(Bitmap.CompressFormat.PNG, 90, fOutTotalTime);
                fOutTotalTime.flush();
                fOutTotalTime.close();

                File fileInstanceVic = new File(pathToCATScalcFolder, "instainevic.PNG");
                OutputStream fOutInstanceVic = new FileOutputStream(fileInstanceVic);
                croppingBitmapInstanceVic.compress(Bitmap.CompressFormat.PNG, 90, fOutInstanceVic);
                fOutInstanceVic.flush();
                fOutInstanceVic.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ivCity.setImageBitmap(croppingBitmap);

            String strTotalUs = "0";
            String strPlusUs = "0";
            String strTotalThey = "0";
            String strPlusThey = "0";

            String strTotalUsAndPlus = recognizePicture(croppingBitmapTotalUs);
            String strTotalTheyAndPlus = recognizePicture(croppingBitmapTotalThey);
            String strTotalTime = recognizePicture(croppingBitmapTotalTime);
            String strInstanceVic = recognizePicture(croppingBitmapInstanceVic);

//            if (isDebugMode) {
                dbgIvTime.setImageBitmap(croppingBitmapTotalTime);
                dbgIvInstanceVic.setImageBitmap(croppingBitmapInstanceVic);
                dbgIvUs.setImageBitmap(croppingBitmapTotalUs);
                dbgIvThey.setImageBitmap(croppingBitmapTotalThey);

                dbgTvTime.setText(strTotalTime);
                dbgTvInstanceVic.setText(strInstanceVic);
                dbgTvUs.setText(strTotalUsAndPlus);
                dbgTvThey.setText(strTotalTheyAndPlus);

                dbgTvFileName.setText(fileScreenshot.getName());
                dbgTvFileDate.setText("" + new Date(fileScreenshot.lastModified()));

//            }


            String[] arrTotalUs = strTotalUsAndPlus.split("\\D");

            List<String> listToUs = new ArrayList<>();
            for (int i = 0; i < arrTotalUs.length; i++) {
                if (!arrTotalUs[i].equals("")) {
                    listToUs.add(arrTotalUs[i]);
                }
            }

            if (listToUs.size() > 0) {
                if (listToUs.size() == 1) {
                    strPlusUs = "0";
                    strTotalUs = listToUs.get(0);
                } else if (listToUs.size() == 2) {
                    strPlusUs = listToUs.get(0);
                    strTotalUs = listToUs.get(1);
                }
            }


            String[] arrTotalThey = strTotalTheyAndPlus.split("\\D");

            List<String> listToThey = new ArrayList<>();
            for (int i = 0; i < arrTotalThey.length; i++) {
                if (!arrTotalThey[i].equals("")) {
                    listToThey.add(arrTotalThey[i]);
                }
            }

            if (listToThey.size() > 0) {
                if (listToThey.size() == 1) {
                    strPlusThey = "0";
                    strTotalThey = listToThey.get(0);
                } else if (listToThey.size() == 2) {
                    strPlusThey = listToThey.get(1);
                    strTotalThey = listToThey.get(0);
                }
            }

            if (strInstanceVic.equals("")) {
                strInstanceVic = "0";
            } else {
                strInstanceVic = cutNotNumericSymbols(strInstanceVic);
            }


            String[] arrTotalTime = strTotalTime.split(" ");

            List<String> listTotalTime = new ArrayList<>();

            for (int i = 0; i < arrTotalTime.length; i++) {
                if (!arrTotalTime[i].equals("")) {
                    listTotalTime.add(arrTotalTime[i]);
//                    listTotalTime.add(arrTotalTime[i].substring(0, arrTotalTime[i].length() - 1));
                }
            }

            if (listTotalTime.size() > 1) {
                for (int i = 0; i < listTotalTime.size(); i++) {
                    listTotalTime.set(i, listTotalTime.get(i).substring(0, listTotalTime.get(i).length() - 1));
                }
            }

            if (listTotalTime.size() > 0) {
                if (listTotalTime.size() == 1) {
                    if (listTotalTime.get(0).substring(listTotalTime.get(0).length()-1).toLowerCase().equals("m")) {
                        String hours = "00";
                        String minutes = listTotalTime.get(0).substring(0, listTotalTime.get(0).length()-1);
                        strTotalTime = hours + ":" + minutes;
                    } else {
                        String hours = listTotalTime.get(0).substring(0, listTotalTime.get(0).length()-1);
                        String minutes = "00";
                        strTotalTime = hours + ":" + minutes;
                    }
                } else if (listTotalTime.size() == 2) {
                    String hours = listTotalTime.get(0);
                    String minutes = listTotalTime.get(1);

                    if (minutes.length() == 1) {
                        minutes = "0" + minutes;
                    }
                    strTotalTime = hours + ":" + minutes;
                } else if (listTotalTime.size() == 0) {
                    strTotalTime = "00:00";
                }
            } else {
                strTotalTime = "00:00";
            }

            strPlusUs = strPlusUs.equals("") ? "??" : strPlusUs.trim();
            strPlusThey = strPlusThey.equals("") ? "??" : strPlusThey.trim();
            strTotalUs = strTotalUs.equals("") ? "??" : strTotalUs.trim();
            strTotalThey = strTotalThey.equals("") ? "??" : strTotalThey.trim();
            strInstanceVic = strInstanceVic.equals("") ? "??" : strInstanceVic.trim();
            strTotalTime = strTotalTime.equals("") ? "??:??" : strTotalTime.trim();

            etInPlusUs.setText(strPlusUs);
            etInPlusThey.setText(strPlusThey);
            etInTotalUs.setText(strTotalUs);
            etInTotalThey.setText(strTotalThey);
            etInTimeLeft.setText(strTotalTime);
            etInInstantVic.setText(strInstanceVic);
            tvResult.setText("");
        }


    }


    private File getLastFileInFolder(String pathToFolder) {

        File dir = new File(pathToFolder);
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png");
            }
        });
        List<File> listFiles = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                listFiles.add(file);
            }
        }

        if  (listFiles.size() > 0) {

            long maxLastModified = 0;
            File temp = null;
            for (File file : listFiles) {
                if (file.lastModified() > maxLastModified) {
                    maxLastModified = file.lastModified();
                    temp = file;
                }
            }

            return temp;

        } else {
            return null;
        }

    }

    private void selectScreenshot() {

        OpenFileDialog fileDialog = new OpenFileDialog(this, pathToScreenshotDir)
                .setFolderIcon(getResources().getDrawable(R.drawable.ic_folder))
                .setFileIcon(getResources().getDrawable(R.drawable.ic_file))
//                .setFilter(".*\\.png, .*\\jpg")
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {
//                        Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();
                        fileScreenshot = new File(fileName);

                        isListenToNewFileInFolder = false; // sharedPreferences.getBoolean("pref_get_last_screenshot",false);
                        swListenNewFiles.setChecked(isListenToNewFileInFolder);

                        cutPicture();
                    }
                });
        fileDialog.show();

    }


    private void openSettings() {

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.menu_open_settings :
//                Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show();
                openSettings();
                return true;
            case R.id.menu_open_screenshot :
//                Toast.makeText(this, "Выбор скриншота", Toast.LENGTH_SHORT).show();
                selectScreenshot();
                return true;
//            case R.id.menu_about:
////                Toast.makeText(this, "О программе", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.menu_exit:
////                Toast.makeText(this, "Выход", Toast.LENGTH_SHORT).show();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // если произошел возврат со страницы настрок - обновляем контролы в текущей активности
        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY) {
            SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
            pathToScreenshotDir = sharedPreferences.getString(getString(R.string.pref_screenshot_folder),"");
            isListenToNewFileInFolder = sharedPreferences.getBoolean(getString(R.string.pref_listen_last_file),false);
            isDebugMode = sharedPreferences.getBoolean(getString(R.string.pref_debug_mode),false);
            swListenNewFiles.setChecked(isListenToNewFileInFolder);
            dbgLayout.setVisibility(isDebugMode ? View.VISIBLE : View.INVISIBLE);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
//        MobileAds.setRequestConfiguration(
//                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
//                        .build());

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        if (Calendar.getInstance().getTime().after(DATE_EXPIRED)) {
            Toast.makeText(MainActivity.this,"Программа устарела", Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUESTREAD_EXTERNAL_STORAGE);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUESTREAD_EXTERNAL_STORAGE);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUESTREAD_EXTERNAL_STORAGE);
            }
        }

        pathToCATScalcFolder = Environment.getExternalStorageDirectory().getPath() + "/CATScalc";
        File tempDir = new File(pathToCATScalcFolder);
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // привязка контролов
        ivCity = findViewById(R.id.iv_city);

        etInTimeLeft = findViewById(R.id.et_in_time_left);
        etInPlusUs = findViewById(R.id.et_in_plus_us);
        etInPlusThey = findViewById(R.id.et_in_plus_they);
        etInTotalUs = findViewById(R.id.et_in_total_us);
        etInTotalThey = findViewById(R.id.et_in_total_they);
        etInInstantVic = findViewById(R.id.et_in_instant_vic);
        swListenNewFiles = findViewById(R.id.sw_listen_new_file);
        tvResult =  findViewById(R.id.tv_result);

        etInTimeLeft.setText("00:00");
        etInPlusUs.setText("0");
        etInPlusThey.setText("0");
        etInTotalUs.setText("0");
        etInTotalThey.setText("0");
        etInInstantVic.setText("0");
        tvResult.setText("");

        dbgLayout = findViewById(R.id.dbg_layout);
        dbgTvHeader = findViewById(R.id.dbg_tv_header);
        dbgTvFileName = findViewById(R.id.dbg_tv_file_name);
        dbgTvFileDate = findViewById(R.id.dbg_tv_file_date);
        dbgIvTime = findViewById(R.id.dbg_img_time);
        dbgTvTime = findViewById(R.id.dbg_tv_time);
        dbgIvInstanceVic = findViewById(R.id.dbg_img_instance_vic);
        dbgTvInstanceVic = findViewById(R.id.dbg_tv_instance_vic);
        dbgIvUs = findViewById(R.id.dbg_img_us);
        dbgTvUs = findViewById(R.id.dbg_tv_us);
        dbgIvThey = findViewById(R.id.dbg_img_they);
        dbgTvThey = findViewById(R.id.dbg_tv_they);









        startTimer();

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
        pathToScreenshotDir = sharedPreferences.getString(getString(R.string.pref_screenshot_folder),"");
        isListenToNewFileInFolder = sharedPreferences.getBoolean(getString(R.string.pref_listen_last_file),false);
        isDebugMode = sharedPreferences.getBoolean(getString(R.string.pref_debug_mode),false);
        swListenNewFiles.setChecked(isListenToNewFileInFolder);

        dbgLayout.setVisibility(isDebugMode ? View.VISIBLE : View.INVISIBLE);

        swListenNewFiles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_listen_last_file), isChecked);
                editor.apply();
                isListenToNewFileInFolder = isChecked;
            }
        });


        File initFile = getLastFileInFolder(pathToScreenshotDir);
        if (initFile != null) {
            fileScreenshot = initFile;
            cutPicture();

            try {
                timeStartTimer = Calendar.getInstance().getTime();  // текущее время старта таймера
                timeStartGame = new Date(fileScreenshot.lastModified()); // время начала игры
                initMinutesToEndGame = parseTimeLeft(etInTimeLeft.getText().toString());
                timeEndGame = new Date(timeStartGame.getTime() + ((long)initMinutesToEndGame*60*1000)); // время окончания игры (по времени)
                initPlusUs = Integer.parseInt(etInPlusUs.getText().toString());
                initInstantVic = Integer.parseInt(etInInstantVic.getText().toString());
                initTotalUs = Integer.parseInt(etInTotalUs.getText().toString());
                initTotalThey = Integer.parseInt(etInTotalThey.getText().toString());
                initPlusThey = Integer.parseInt(etInPlusThey.getText().toString());
                isAllFieldsCorrect = true;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
//                Toast.makeText(MainActivity.this,"Ошибка ввода данных, невозможно запустить таймер.", Toast.LENGTH_SHORT).show();
                isAllFieldsCorrect = false;
            }

        }

    }



    private void calculate() {

        if (isAllFieldsCorrect) {
            Date currentTime = Calendar.getInstance().getTime();    // текущее время
            timeLeftInMillis = currentTime.getTime() - timeStartGame.getTime(); // кол-во миллисекунд, прошедшех от начала игры до текущего времени
            long currentSecsAndMillis = 60000 - timeLeftInMillis % 60_000; // кол-во секунд и миллисекунд (в миллисекундах)
            currentTotalUs = (int)((timeLeftInMillis / 60000) * initPlusUs + initTotalUs);            // на данный момент очков у нас
            currentTotalThey = (int)((timeLeftInMillis / 60000) * initPlusThey + initTotalThey);      // на данный момент очков у них
            long leftMillsToInstanceUs = initPlusUs == 0 ? 25*60*60*1000 : ((initInstantVic - currentTotalUs) / initPlusUs) * 60000 + currentSecsAndMillis;            // осталось миллисекунд до досрочной победы нам
            long leftMillsToInstanceThey = initPlusThey == 0 ? 25*60*60*1000 : ((initInstantVic - currentTotalThey) / initPlusThey) * 60000 + currentSecsAndMillis;      // осталось миллисекунд до досрочной победы им
            long leftMillsToInstanceGame = Math.min(leftMillsToInstanceUs, leftMillsToInstanceThey);
            long leftMillsToEndGame = (timeEndGame.getTime() - currentTime.getTime()); // осталось миллисекунд до окончания игры по времени
            long willTotalUs = currentTotalUs + initPlusUs * (leftMillsToEndGame / 60000);   // будет очков у нас по окончании игры по времени
            long willTotalThey = currentTotalThey + initPlusThey * (leftMillsToEndGame / 60000);   // будет очков у них по окончании игры по времени
            long willTotalInstanceUs = currentTotalUs + initPlusUs * (leftMillsToInstanceGame / 60000);   // будет очков у нас по окончании игры по времени
            long willTotalInstanceThey = currentTotalThey + initPlusThey * (leftMillsToInstanceGame / 60000);   // будет очков у них по окончании игры по времени
            boolean isGameOver = (leftMillsToInstanceUs - 1000) <= 0 || (leftMillsToInstanceThey - 1000) <= 0 || leftMillsToEndGame <= 0; // закончилась ли игра?
            boolean isGameOverInstance = (leftMillsToInstanceUs - 1000) <= 0 ||  (leftMillsToInstanceThey - 1000) <= 0; // закончилась ли игра досрочно?

            if (isGameOver) {
                // если игра закончилась
                currentTotalUs += initPlusUs;
                currentTotalThey += initPlusThey;

                if (isGameOverInstance) {
                    // если игра закончилась досрочно
                    calcStatusGame = "Досрочное окончание";
                    if (currentTotalUs > currentTotalThey) {
                        // досрочно выиграли мы
                        calcStatusUs = "Досрочно выиграли";
                        calcStatusThey = "Досрочно проиграли";
                        calcResult = "Мы досрочно выиграли";
                    } else if (currentTotalUs < currentTotalThey) {
                        // досрочно выиграли они
                        calcStatusUs = "Досрочно проиграли";
                        calcStatusThey = "Досрочно выиграли";
                        calcResult = "Мы досрочно проиграли";
                    } else {
                        // досрочная ничья
                        calcStatusUs = "Досрочная ничья";
                        calcStatusThey = "Досрочная ничья";
                        calcResult = "Досрочная ничья";
                    }
                } else {
                    // если игра закончилась по времени
                    calcStatusGame = "Игра окончена";
                    if (currentTotalUs > currentTotalThey) {
                        // выиграли мы
                        calcStatusUs = "Выиграли";
                        calcStatusThey = "Проиграли";
                        calcResult = "Мы выиграли";
                    } else if (currentTotalUs < currentTotalThey) {
                        // выиграли они
                        calcStatusUs = "Проиграли";
                        calcStatusThey = "Выиграли";
                        calcResult = "Мы проиграли";
                    } else {
                        // ничья
                        calcStatusUs = "Ничья";
                        calcStatusThey = "Ничья";
                        calcResult = "Ничья";
                    }
                }

            } else {
                // если игра не закончилась

                int hoursToEnd = (int)((leftMillsToEndGame - 1000) / (1000 * 60 * 60));
                int minutesToEnd = (int)(((leftMillsToEndGame - 1000) - hoursToEnd * (1000 * 60 * 60)) / (1000 * 60));
                int secondsToEnd = (int)(((leftMillsToEndGame - 1000) - hoursToEnd * (1000 * 60 * 60) - minutesToEnd * (1000 * 60)) / 1000);
                String strTimeToEnd = String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursToEnd, minutesToEnd, secondsToEnd);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                long timeEndGame = currentTime.getTime() + leftMillsToEndGame;
                String strTimeEndGame = simpleDateFormat.format(new Date(timeEndGame));

                if (leftMillsToEndGame - leftMillsToInstanceUs > 0 || leftMillsToEndGame - leftMillsToInstanceThey > 0) {
                    // если игра закончится досрочно
                    long leftMillsToInstanceEndGame = Math.min((leftMillsToInstanceUs), (leftMillsToInstanceThey));
                    int hoursToInstanceEnd = (int)((leftMillsToInstanceEndGame - 1000) / (1000 * 60 * 60));
                    int minutesToInstanceEnd = (int)(((leftMillsToInstanceEndGame - 1000) - hoursToInstanceEnd * (1000 * 60 * 60)) / (1000 * 60));
                    int secondsToInstanceEnd = (int)(((leftMillsToInstanceEndGame - 1000) - hoursToInstanceEnd * (1000 * 60 * 60) - minutesToInstanceEnd * (1000 * 60)) / 1000);
                    String strTimeToInstanceEnd = String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursToInstanceEnd, minutesToInstanceEnd, secondsToInstanceEnd);

                    long timeEndGameInstance = currentTime.getTime() + leftMillsToInstanceEndGame;
                    String strTimeEndGameInstance = simpleDateFormat.format(new Date(timeEndGameInstance));

                    calcStatusGame = strTimeToEnd + " / " + strTimeToInstanceEnd;


                    if (leftMillsToInstanceUs < leftMillsToInstanceThey) {
                        // Мы победим досрочно
                        calcStatusUs = "Мы победим досрочно";
                        calcStatusThey = "Они проиграют досрочно";
                        calcResult = strTimeToInstanceEnd + " " + calcStatusUs + " с разницей в " + (willTotalInstanceUs - willTotalInstanceThey) + " очков " + strTimeEndGameInstance;
                    } else if (leftMillsToInstanceUs > leftMillsToInstanceThey) {
                        // Мы проиграем досрочно
                        calcStatusUs = "Мы проиграем досрочно";
                        calcStatusThey = "Они победят досрочно";
                        calcResult = strTimeToInstanceEnd + " " + calcStatusThey + " с разницей в " + (willTotalInstanceThey - willTotalInstanceUs) + " очков " + strTimeEndGameInstance;
                    } else {
                        // Будет досрочная ничья
                        calcStatusUs = "Будет досрочная ничья "  + strTimeEndGameInstance;
                        calcStatusThey = "Будет досрочная ничья "  + strTimeEndGameInstance;
                        calcResult = calcStatusUs + " через " + strTimeToInstanceEnd;
                    }

                } else {
                    // если игра закончится по времени
                    calcStatusGame = strTimeToEnd;


                    if (willTotalUs > willTotalThey) {
                        // Мы победим
                        calcStatusUs = "Мы победим";
                        calcStatusThey = "Они проиграют";
                        calcResult = strTimeToEnd + " " + calcStatusUs + " с разницей в " + (willTotalUs - willTotalThey) + " очков " + strTimeEndGame;
                    } else if (willTotalUs < willTotalThey) {
                        // Мы проиграем
                        calcStatusUs = "Мы проиграем";
                        calcStatusThey = "Они победят";
                        calcResult = strTimeToEnd + " " + calcStatusThey + " с разницей в " + (willTotalThey - willTotalUs) + " очков " + strTimeEndGame;
                    } else {
                        // Будет ничья
                        calcStatusUs = "Будет ничья";
                        calcStatusThey = "Будет ничья";
                        calcResult = strTimeToEnd + calcStatusUs + " " + strTimeEndGame;
                    }
                }

            }
        } else {
            calcStatusUs = "";
            calcStatusThey = "";
            calcStatusGame = "";
            calcResult = "Ошибка ввода данных";
        }


        tvResult.setText(calcResult);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_catscalciconsmall)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_catscalcicon))
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
//                        .setContentTitle("Заголовок")
                        .setContentText(calcResult)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(calcResult));
                createChannelIfNeeded(notificationManager);
                notificationManager.notify(NOTIFY_ID, notificationBuilder.build());

    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
    class firstTask extends TimerTask {

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if (isListenToNewFileInFolder) {
                        File tmpFile = getLastFileInFolder(pathToScreenshotDir);
                        if (tmpFile != null) {
                            if (!tmpFile.equals(fileScreenshot)) {
                                fileScreenshot = tmpFile;
                                cutPicture();
                            }
                        }

                    }

                    if (fileScreenshot != null) {

                        try {
                            timeStartTimer = Calendar.getInstance().getTime();  // текущее время старта таймера
                            timeStartGame = new Date(fileScreenshot.lastModified()); // время начала игры
                            initMinutesToEndGame = parseTimeLeft(etInTimeLeft.getText().toString());
                            timeEndGame = new Date(timeStartGame.getTime() + ((long)initMinutesToEndGame*60*1000)); // время окончания игры (по времени)
                            initPlusUs = Integer.parseInt(etInPlusUs.getText().toString());
                            initInstantVic = Integer.parseInt(etInInstantVic.getText().toString());
                            initTotalUs = Integer.parseInt(etInTotalUs.getText().toString());
                            initTotalThey = Integer.parseInt(etInTotalThey.getText().toString());
                            initPlusThey = Integer.parseInt(etInPlusThey.getText().toString());
                            isAllFieldsCorrect = true;
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
//                        Toast.makeText(MainActivity.this,"Ошибка ввода данных.", Toast.LENGTH_SHORT).show();
                            isAllFieldsCorrect = false;
                        }

                        calculate();
                    }


                }
            });
        }
    };

    private void startTimer() {

        if (timer == null) {
            timer = new Timer();
            timer.schedule(new firstTask(), 1000,1000);
        }

    }


    private int parseTimeLeft(String str) {
        int result  = 0;

        String[] words = str.split(":");
        result = Integer.parseInt(words[0])* 60 + Integer.parseInt(words[1]);
        return result;
    }




}
