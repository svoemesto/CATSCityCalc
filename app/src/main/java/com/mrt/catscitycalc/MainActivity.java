package com.mrt.catscitycalc;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    ImageView ivCity; // кропнутая картинка
    TextView etInTimeLeft;  // время до конца игры (по скриншоту)
    TextView etInPlusUs; // прирост очков у нас (по скриншоту)
    TextView etInPlusThey; // прирост очков у них (по скриншоту)
    TextView etInTotalUs; // всего очнов у нас (по скриншоту)
    TextView etInTotalThey; // всего очков у них (по скриншоту)
    TextView etInInstantVic; // очков до досрочной победы (по скриншоту)

    Switch swSyncTime; // переключатель "Синхронизировать время по скриншоту"
    Button btStart; // кнопка "Старт"

    TextView tvCalcTotalUs; // всего очков у нас (рассчетное)
    TextView tvCalcTotalThey; // всего очков у них (рассчетное)
    TextView tvCalcTimeLeft; // время до конца игры (расчетное)
    TextView tvCalcStatusUs; // статус наш (рассчетный)
    TextView tvCalcStatusThey; // статус их (рассчетный)
    TextView tvCalcStatusGame; // статус игры (рассчетный)
    TextView tvResult; // результат игры
    ListView lvFiles; // список файлов

    private String fileName;

    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    private long timeLeftInMillisGlobal = 0;
    private long timeLeftInMinutesGlobal = 0;
    private String pathToScreenshotDir;

    private static final double XD1 = -0.565d;
    private static final double XD2 = +0.565d;
    private static final double YD1 = -0.800d;
    private static final double YD2 = -0.450d;
    private static final String PATH_TO_CROPPED_FILE = "@drawable/crop_city";

    private void cutPicture(String fileName) {

        Bitmap sourceBitmap = BitmapFactory.decodeFile(pathToScreenshotDir + "/" + fileName);
        int widthSource = sourceBitmap.getWidth();
        int heightSource = sourceBitmap.getHeight();

        int x1 =(int)((double) widthSource / 2 + XD1 * heightSource);
        int x2 =(int)((double) widthSource / 2 + XD2 * heightSource);
        int y1 =(int)((double) heightSource / 2 + YD1 * ((double) heightSource / 2));
        int y2 =(int)((double) heightSource / 2 + YD2 * ((double) heightSource / 2));

        Bitmap croppingBitmap = Bitmap.createBitmap(sourceBitmap, x1, y1, x2-x1, y2-y1);

        ivCity.setImageBitmap(croppingBitmap);

//
//        File file = new File(PATH_TO_CROPPED_FILE + ".png");
//
//        FileOutputStream ostream = null;
//        try {
//            file.createNewFile();
//            ostream = new FileOutputStream(file);
//            croppingBitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
//            ostream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivCity = findViewById(R.id.iv_city);
        etInTimeLeft = findViewById(R.id.et_in_time_left);
        etInPlusUs = findViewById(R.id.et_in_plus_us);
        etInPlusThey = findViewById(R.id.et_in_plus_they);
        etInTotalUs = findViewById(R.id.et_in_total_us);
        etInTotalThey = findViewById(R.id.et_in_total_they);
        etInInstantVic = findViewById(R.id.et_in_instant_vic);
        swSyncTime = findViewById(R.id.sw_sync_time);
        btStart = findViewById(R.id.bt_start);
        tvCalcTotalUs =  findViewById(R.id.tv_calc_total_us);
        tvCalcTotalThey =  findViewById(R.id.tv_calc_total_they);
        tvCalcTimeLeft =  findViewById(R.id.tv_calc_time_left);
        tvCalcStatusUs =  findViewById(R.id.tv_calc_status_us);
        tvCalcStatusThey =  findViewById(R.id.tv_calc_status_they);
        tvCalcStatusGame =  findViewById(R.id.tv_calc_status_game);
        tvResult =  findViewById(R.id.tv_result);
        lvFiles = findViewById(R.id.lv_files);

        final List<String> listFiles = getListFiles();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listFiles);
        lvFiles.setAdapter(arrayAdapter);
        lvFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Выбран файл: " + listFiles.get(position), Toast.LENGTH_SHORT).show();
                fileName = listFiles.get(position);
                cutPicture(fileName);
            }
        });


//        timeLeftInMinutesGlobal = 24*60;
//        timeLeftInMillisGlobal = timeLeftInMinutesGlobal  * 60*1000;
//
//        etPlusUs.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                showResult();
//            }
//        });
//
//        etPlusThey.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                showResult();
//            }
//        });
//
//        etTimeLeft.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                showResult();
//            }
//        });
//
//        etInstantVic.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                showResult();
//            }
//        });
//
//        etTotalThey.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                showResult();
//            }
//        });
//
//        etTotalUs.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                showResult();
//            }
//        });
//
//
//
//        showResult();
//
//        startTimer();
//
    }

    private void startTimer() {
//        countDownTimer = new CountDownTimer(timeLeftInMillisGlobal, 60*1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//                timeLeftInMillisGlobal = millisUntilFinished;
//                timeLeftInMinutesGlobal = timeLeftInMillisGlobal / 1000 / 60;
//
//                int minutesToEndGame = 0;
//                int plusUs = 0;
//                int instantVic = 0;
//                int totalUs = 0;
//                int totalThey = 0;
//                int plusThey = 0;
//
//                try {
//                    minutesToEndGame = parseTimeLeft(etTimeLeft.getText().toString());
//                    plusUs = Integer.parseInt(etPlusUs.getText().toString());
//                    instantVic = Integer.parseInt(etInstantVic.getText().toString());
//                    totalUs = Integer.parseInt(etTotalUs.getText().toString());
//                    totalThey = Integer.parseInt(etTotalThey.getText().toString());
//                    plusThey = Integer.parseInt(etPlusThey.getText().toString());
//                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
//                    return;
//                }
//
////                if (minutesToEndGame - timeLeftInMinutesGlobal == 1) {
//                minutesToEndGame -= 1;
//                totalUs += plusUs;
//                totalThey += plusThey;
//
//                etTimeLeft.setText(String.format(Locale.getDefault(), "%02d:%02d",  minutesToEndGame/60, minutesToEndGame % 60));
//                etTotalUs.setText(String.valueOf(totalUs));
//                etTotalThey.setText(String.valueOf(totalThey));
//
//                showResult();
////                }
//
//
//            }
//
//            @Override
//            public void onFinish() {
//                isTimerRunning = false;
//                showResult();
//            }
//        }.start();
//
//        isTimerRunning = true;

    }

    private int parseTimeLeft(String str) {
        int result  = 0;

        String[] words = str.split(":");
        result = Integer.parseInt(words[0])* 60 + Integer.parseInt(words[1]);
        return result;
    }


    private void showResult() {

//        if (!isTimerRunning) startTimer();
//
//        String textTimeLeft = etTimeLeft.getText().toString();
//
//        String textUsState = "";
//        String textTheyState = "";
//        String result = "";
//
//        int minutesToEndGame = 0;
//        int plusUs = 0;
//        int instantVic = 0;
//        int totalUs = 0;
//        int totalThey = 0;
//        int plusThey = 0;
//        try {
//            minutesToEndGame = parseTimeLeft(textTimeLeft);
//            plusUs = Integer.parseInt(etPlusUs.getText().toString());
//            instantVic = Integer.parseInt(etInstantVic.getText().toString());
//            totalUs = Integer.parseInt(etTotalUs.getText().toString());
//            totalThey = Integer.parseInt(etTotalThey.getText().toString());
//            plusThey = Integer.parseInt(etPlusThey.getText().toString());
//        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
//            textUsState = "";
//            textTheyState = "";
//            result = "Ошибка ввода";
//            tvResult.setText(result);
//            tvResultUs.setText(textUsState);
//            tvResultThey.setText(textTheyState);
//            return;
//        }
//
//
//
//        int minutesUsToInstantWin = (instantVic-totalUs)/(plusUs == 0 ? 1 : plusUs);
//        int minutesTheyToInstantWin = (instantVic-totalThey)/(plusThey == 0 ? 1 : plusThey);
//
//        String textMinUsToFinalWin =  String.format(Locale.getDefault(), "%02d:%02d",  minutesUsToInstantWin/60, minutesUsToInstantWin % 60);
//        String textMinTheyToFinalWin = String.format(Locale.getDefault(), "%02d:%02d",  minutesTheyToInstantWin/60, minutesTheyToInstantWin % 60);
//        String textMinToEndGame = String.format(Locale.getDefault(), "%02d:%02d",  minutesToEndGame/60, minutesToEndGame % 60);
//
//        boolean isGameOver = (minutesUsToInstantWin*minutesTheyToInstantWin*minutesToEndGame) == 0;
//
//        boolean isIvstanceWin = minutesToEndGame > minutesUsToInstantWin || minutesToEndGame > minutesTheyToInstantWin;
//        boolean isNoOne = minutesUsToInstantWin == minutesTheyToInstantWin;
//
//        if (isNoOne) {
//            textUsState = "Мы не выиграем";
//            textTheyState = "Они не выиграют";
//            result = "Будет ничья";
//        } else {
//            if (isIvstanceWin) {
//                if (minutesUsToInstantWin < minutesTheyToInstantWin) {
//                    textUsState =  "Наша досрочная победа через: " + textMinUsToFinalWin;
//                    textTheyState = "Они не выиграют досрочно";
//                    result = textUsState;
//                } else {
//                    textUsState = "Мы не выиграем досрочно";
//                    textTheyState =  "Их досрочная победа через: " + textMinTheyToFinalWin;
//                    result = textTheyState;
//                }
//            } else {
//                if (minutesUsToInstantWin < minutesTheyToInstantWin) {
//                    textUsState =  "Наша победа через: " + textMinToEndGame;
//                    textTheyState = "Они не выиграют";
//                    result = textUsState;
//                } else {
//                    textUsState = "Мы не выиграем";
//                    textTheyState =  "Их победа через: " + textMinToEndGame;
//                    result = textTheyState;
//                }
//            }
//        }
//
//        tvResult.setText(result);
//        tvResultUs.setText(textUsState);
//        tvResultThey.setText(textTheyState);
//
//        if (isGameOver) {
//            isTimerRunning = false;
//            countDownTimer.cancel();
////            etTimeLeft.setText("00:00");
//        }

    }

    private List<String> getListFiles() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }



        pathToScreenshotDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/Screenshots" ;
        File dir = new File(pathToScreenshotDir);

        String[] files = dir.list();
        List<String> result = new ArrayList<>();

        for (String file : files) {
            result.add(file);
        }
        return result;
    }


}
