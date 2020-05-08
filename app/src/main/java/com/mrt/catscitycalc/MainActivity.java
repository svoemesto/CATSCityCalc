package com.mrt.catscitycalc;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.io.File;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    TextView etTimeLeft;
    TextView tvResultUs;           //обязательная
    TextView tvResultThey;           //обязательная
    TextView tvResult;           //обязательная
    TextView etInstantVic;          //обязательная
    TextView etPlusUs;
    TextView etTotalUs;             //обязательная
    TextView etTotalThey;
    TextView etPlusThey;
    TextView tvTest;


    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    private long timeLeftInMillisGlobal = 0;
    private long timeLeftInMinutesGlobal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTimeLeft = findViewById(R.id.et_time_left);
        tvResultUs = findViewById(R.id.tv_result_us);
        tvResultThey = findViewById(R.id.tv_result_they);
        etInstantVic = findViewById(R.id.et_instant_vic);
        etPlusUs = findViewById(R.id.et_plus_us);
        etTotalUs = findViewById(R.id.et_total_us);
        etTotalThey = findViewById(R.id.et_total_they);
        etPlusThey = findViewById(R.id.et_plus_they);
        tvResult = findViewById(R.id.tv_result);
        tvTest = findViewById(R.id.tv_test);

        etTimeLeft.setText("24:00");
        etTotalUs.setText("0");
        etTotalThey.setText("0");
        etInstantVic.setText("0");
        etPlusThey.setText("0");
        etPlusUs.setText("0");
        tvTest.setText(getListFiles());



        timeLeftInMinutesGlobal = 24*60;
        timeLeftInMillisGlobal = timeLeftInMinutesGlobal  * 60*1000;

        etPlusUs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showResult();
            }
        });

        etPlusThey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showResult();
            }
        });

        etTimeLeft.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showResult();
            }
        });

        etInstantVic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showResult();
            }
        });

        etTotalThey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showResult();
            }
        });

        etTotalUs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showResult();
            }
        });



        showResult();

        startTimer();

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillisGlobal, 60*1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timeLeftInMillisGlobal = millisUntilFinished;
                timeLeftInMinutesGlobal = timeLeftInMillisGlobal / 1000 / 60;

                int minutesToEndGame = 0;
                int plusUs = 0;
                int instantVic = 0;
                int totalUs = 0;
                int totalThey = 0;
                int plusThey = 0;

                try {
                    minutesToEndGame = parseTimeLeft(etTimeLeft.getText().toString());
                    plusUs = Integer.parseInt(etPlusUs.getText().toString());
                    instantVic = Integer.parseInt(etInstantVic.getText().toString());
                    totalUs = Integer.parseInt(etTotalUs.getText().toString());
                    totalThey = Integer.parseInt(etTotalThey.getText().toString());
                    plusThey = Integer.parseInt(etPlusThey.getText().toString());
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    return;
                }

//                if (minutesToEndGame - timeLeftInMinutesGlobal == 1) {
                minutesToEndGame -= 1;
                totalUs += plusUs;
                totalThey += plusThey;

                etTimeLeft.setText(String.format(Locale.getDefault(), "%02d:%02d",  minutesToEndGame/60, minutesToEndGame % 60));
                etTotalUs.setText(String.valueOf(totalUs));
                etTotalThey.setText(String.valueOf(totalThey));

                showResult();
//                }


            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                showResult();
            }
        }.start();

        isTimerRunning = true;

    }

    private int parseTimeLeft(String str) {
        int result  = 0;

        String[] words = str.split(":");
        result = Integer.parseInt(words[0])* 60 + Integer.parseInt(words[1]);
        return result;
    }


    private void showResult() {

        if (!isTimerRunning) startTimer();

        String textTimeLeft = etTimeLeft.getText().toString();

        String textUsState = "";
        String textTheyState = "";
        String result = "";

        int minutesToEndGame = 0;
        int plusUs = 0;
        int instantVic = 0;
        int totalUs = 0;
        int totalThey = 0;
        int plusThey = 0;
        try {
            minutesToEndGame = parseTimeLeft(textTimeLeft);
            plusUs = Integer.parseInt(etPlusUs.getText().toString());
            instantVic = Integer.parseInt(etInstantVic.getText().toString());
            totalUs = Integer.parseInt(etTotalUs.getText().toString());
            totalThey = Integer.parseInt(etTotalThey.getText().toString());
            plusThey = Integer.parseInt(etPlusThey.getText().toString());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            textUsState = "";
            textTheyState = "";
            result = "Ошибка ввода";
            tvResult.setText(result);
            tvResultUs.setText(textUsState);
            tvResultThey.setText(textTheyState);
            return;
        }



        int minutesUsToInstantWin = (instantVic-totalUs)/(plusUs == 0 ? 1 : plusUs);
        int minutesTheyToInstantWin = (instantVic-totalThey)/(plusThey == 0 ? 1 : plusThey);

        String textMinUsToFinalWin =  String.format(Locale.getDefault(), "%02d:%02d",  minutesUsToInstantWin/60, minutesUsToInstantWin % 60);
        String textMinTheyToFinalWin = String.format(Locale.getDefault(), "%02d:%02d",  minutesTheyToInstantWin/60, minutesTheyToInstantWin % 60);
        String textMinToEndGame = String.format(Locale.getDefault(), "%02d:%02d",  minutesToEndGame/60, minutesToEndGame % 60);

        boolean isGameOver = (minutesUsToInstantWin*minutesTheyToInstantWin*minutesToEndGame) == 0;

        boolean isIvstanceWin = minutesToEndGame > minutesUsToInstantWin || minutesToEndGame > minutesTheyToInstantWin;
        boolean isNoOne = minutesUsToInstantWin == minutesTheyToInstantWin;

        if (isNoOne) {
            textUsState = "Мы не выиграем";
            textTheyState = "Они не выиграют";
            result = "Будет ничья";
        } else {
            if (isIvstanceWin) {
                if (minutesUsToInstantWin < minutesTheyToInstantWin) {
                    textUsState =  "Наша досрочная победа через: " + textMinUsToFinalWin;
                    textTheyState = "Они не выиграют досрочно";
                    result = textUsState;
                } else {
                    textUsState = "Мы не выиграем досрочно";
                    textTheyState =  "Их досрочная победа через: " + textMinTheyToFinalWin;
                    result = textTheyState;
                }
            } else {
                if (minutesUsToInstantWin < minutesTheyToInstantWin) {
                    textUsState =  "Наша победа через: " + textMinToEndGame;
                    textTheyState = "Они не выиграют";
                    result = textUsState;
                } else {
                    textUsState = "Мы не выиграем";
                    textTheyState =  "Их победа через: " + textMinToEndGame;
                    result = textTheyState;
                }
            }
        }

        tvResult.setText(result);
        tvResultUs.setText(textUsState);
        tvResultThey.setText(textTheyState);

        if (isGameOver) {
            isTimerRunning = false;
            countDownTimer.cancel();
//            etTimeLeft.setText("00:00");
        }

    }

    private String getListFiles() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }



//        Environment.getDataDirectory();
        String result = "";
        String pathToDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/Screenshots" ;
//        String pathToDir = "/storage/self/primary/Pictures/Screenshots";
//        File dir = new File(pathToDir);
        File dir = new File(pathToDir);

//        requestPermission("READ_EXTERNAL_STORAGE",)
        String[] files = dir.list();
        for (String file : files) {
            result = result + file + "\n";
        }
        return result;
    }

//    private boolean isPermissionGranted(String permission) {
//        // проверяем разрешение - есть ли оно у нашего приложения
//        int permissionCheck = ActivityCompat.checkSelfPermission(this, permission);
//        // true - если есть, false - если нет
//        return permissionCheck == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermission(String permission, int requestCode) {
//        // запрашиваем разрешение
//        ActivityCompat.requestPermissions(this,
//                new String[]{permission}, requestCode);
//    }
//
//    public void requestMultiplePermissions() {
//        ActivityCompat.requestPermissions(this,
//                new String[] {
//                        Manifest.permission.READ_PHONE_STATE
//                        Manifest.permission.READ_SMS
//                },
//                PERMISSION_REQUEST_CODE);
//    }


}
