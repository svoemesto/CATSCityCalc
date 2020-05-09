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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private ImageView ivCity; // кропнутая картинка
    private TextView etInTimeLeft;  // время до конца игры (по скриншоту)
    private TextView etInPlusUs; // прирост очков у нас (по скриншоту)
    private TextView etInPlusThey; // прирост очков у них (по скриншоту)
    private TextView etInTotalUs; // всего очнов у нас (по скриншоту)
    private TextView etInTotalThey; // всего очков у них (по скриншоту)
    private TextView etInInstantVic; // очков до досрочной победы (по скриншоту)

    private Switch swSyncTime; // переключатель "Синхронизировать время по скриншоту"
    private Button btStart; // кнопка "Старт"

    private TextView tvCalcTotalUs; // всего очков у нас (рассчетное)
    private TextView tvCalcTotalThey; // всего очков у них (рассчетное)
    private TextView tvCalcTimeLeft; // время до конца игры (расчетное)
    private TextView tvCalcStatusUs; // статус наш (рассчетный)
    private TextView tvCalcStatusThey; // статус их (рассчетный)
    private TextView tvCalcStatusGame; // статус игры (рассчетный)
    private TextView tvResult; // результат игры
    private TextView tvTest; // результат игры
    private ListView lvFiles; // список файлов

    private File fileScreenshot;

    private Timer timer;
    private boolean isTimerRunning;
    private long timeLeftInMillis = 0;
    private long timeLeftInMinutesGlobal = 0;
    private String pathToScreenshotDir;

    private static final double XD1 = -0.565d;
    private static final double XD2 = +0.565d;
    private static final double YD1 = -0.800d;
    private static final double YD2 = -0.450d;

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

    private void cutPicture() {

        if (fileScreenshot != null) {

            Bitmap sourceBitmap = BitmapFactory.decodeFile(fileScreenshot.getAbsolutePath());
            int widthSource = sourceBitmap.getWidth();
            int heightSource = sourceBitmap.getHeight();

            int x1 =(int)((double) widthSource / 2 + XD1 * heightSource);
            int x2 =(int)((double) widthSource / 2 + XD2 * heightSource);
            int y1 =(int)((double) heightSource / 2 + YD1 * ((double) heightSource / 2));
            int y2 =(int)((double) heightSource / 2 + YD2 * ((double) heightSource / 2));

            if (x1 < 0) x1 = 0;
            if ((x2 > widthSource)) x2 = widthSource;

            Bitmap croppingBitmap = Bitmap.createBitmap(sourceBitmap, x1, y1, x2-x1, y2-y1);

            ivCity.setImageBitmap(croppingBitmap);

            // Здесь надо разобраться, как сохранять буффередимадж в файл
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


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // привязка контролов
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
        tvTest =  findViewById(R.id.tv_test);
        lvFiles = findViewById(R.id.lv_files);

        final List<File> listFiles = getListFiles();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listFiles);
        lvFiles.setAdapter(arrayAdapter);
        lvFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Выбран файл: " + listFiles.get(position), Toast.LENGTH_SHORT).show();
                fileScreenshot = listFiles.get(position);
                cutPicture();
                tvTest.setText((new Date(fileScreenshot.lastModified())).toString());
            }
        });

        // событие нажатие кнопки "Старт"
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTimerRunning) {

                    try {
                        timeStartTimer = Calendar.getInstance().getTime();  // текущее время старта таймера
                        timeStartGame = swSyncTime.isChecked() ? new Date(fileScreenshot.lastModified()) : timeStartTimer; // время начала игры
                        initMinutesToEndGame = parseTimeLeft(etInTimeLeft.getText().toString());
                        timeEndGame = new Date(timeStartGame.getTime() + ((long)initMinutesToEndGame*60*1000)); // время окончания игры (по времени)
                        initPlusUs = Integer.parseInt(etInPlusUs.getText().toString());
                        initInstantVic = Integer.parseInt(etInInstantVic.getText().toString());
                        initTotalUs = Integer.parseInt(etInTotalUs.getText().toString());
                        initTotalThey = Integer.parseInt(etInTotalThey.getText().toString());
                        initPlusThey = Integer.parseInt(etInPlusThey.getText().toString());
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        Toast.makeText(MainActivity.this,"Ошибка ввода данных, невозможно запустить таймер.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    startTimer();
                } else {
                    stopTimer();
                }
            }
        });

    }

    private void calculate() {

        timeLeftInMillis = Calendar.getInstance().getTime().getTime() - timeStartGame.getTime(); // кол-во миллисекунд, прошедшех от начала игры до текущего времени
        currentTotalUs = (int)(timeLeftInMillis / 60000) * initPlusUs + initTotalUs;            // на данный момент очков у нас
        currentTotalThey = (int)(timeLeftInMillis / 60000) * initPlusThey + initTotalThey;      // на данный момент очков у них
        int leftMillsToInstantUs = initPlusUs == 0 ? Integer.MAX_VALUE : ((initInstantVic - currentTotalUs) / initPlusUs) * 60000;            // осталось миллисекунд до досрочной победы нам
        int leftMillsToInstantThey = initPlusThey == 0 ? Integer.MAX_VALUE : ((initInstantVic - currentTotalThey) / initPlusThey) * 60000;      // осталось миллисекунд до досрочной победы им
        int leftMillsToEndGame = (int)(timeEndGame.getTime() - Calendar.getInstance().getTime().getTime()); // осталось миллисекунд до окончания игры по времени
        int willTotalUs = currentTotalUs + initPlusUs * (leftMillsToEndGame / 60000);   // будет очков у нас по окончании игры по времени
        int willTotalThey = currentTotalThey + initPlusThey * (leftMillsToEndGame / 60000);   // будет очков у них по окончании игры по времени

        boolean isGameOver = leftMillsToInstantUs < 0 || leftMillsToInstantThey < 0 || leftMillsToEndGame < 0; // закончилась ли игра?
        boolean isGameOverInstance = leftMillsToInstantUs < 0 || leftMillsToInstantThey < 0; // закончилась ли игра досрочно?

        if (isGameOver) {
            // если игра закончилась
            if (isGameOverInstance) {
                // если игра закончилась досрочно
                calcStatusGame = "Досрочное окончание";
                if (leftMillsToInstantUs - leftMillsToInstantThey < 0) {
                    // досрочно выиграли мы
                    calcStatusUs = "Досрочно выиграли";
                    calcStatusThey = "Досрочно проиграли";
                    calcResult = "Мы досрочно выиграли";
                } if (leftMillsToInstantUs - leftMillsToInstantThey > 0) {
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

            int hoursToEnd = leftMillsToEndGame / (1000 * 60 * 60);
            int minutesToEnd = (leftMillsToEndGame - hoursToEnd * (1000 * 60 * 60)) / (1000 * 60);
            int secondsToEnd = (leftMillsToEndGame - hoursToEnd * (1000 * 60 * 60) - minutesToEnd * (1000 * 60)) / 1000;
            String strTimeToEnd = String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursToEnd, minutesToEnd, secondsToEnd);

            if (leftMillsToEndGame - leftMillsToInstantUs > 0 || leftMillsToEndGame - leftMillsToInstantThey > 0) {
                // если игра закончится досрочно
                int leftMillsToInstanceEndGame = Math.min(leftMillsToInstantUs, leftMillsToInstantThey);
                int hoursToInstanceEnd = leftMillsToInstanceEndGame / (1000 * 60 * 60);
                int minutesToInstanceEnd = (leftMillsToInstanceEndGame - hoursToInstanceEnd * (1000 * 60 * 60)) / (1000 * 60);
                int secondsToInstanceEnd = (leftMillsToInstanceEndGame - hoursToInstanceEnd * (1000 * 60 * 60) - minutesToInstanceEnd * (1000 * 60)) / 1000;
                String strTimeToInstanceEnd = String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursToInstanceEnd, minutesToInstanceEnd, secondsToInstanceEnd);

                calcStatusGame = strTimeToEnd + " / " + strTimeToInstanceEnd;

                if (leftMillsToInstantUs < leftMillsToInstantThey) {
                    // Мы победим досрочно
                    calcStatusUs = "Мы победим досрочно";
                    calcStatusThey = "Они проиграют досрочно";
                    calcResult = calcStatusUs + " через " + strTimeToInstanceEnd;
                } else if (leftMillsToInstantUs > leftMillsToInstantThey) {
                    // Мы проиграем досрочно
                    calcStatusUs = "Мы проиграем досрочно";
                    calcStatusThey = "Они победят досрочно";
                    calcResult = calcStatusThey + " через " + strTimeToInstanceEnd;
                } else {
                    // Будет досрочная ничья
                    calcStatusUs = "Будет досрочная ничья";
                    calcStatusThey = "Будет досрочная ничья";
                    calcResult = calcStatusUs + " через " + strTimeToInstanceEnd;
                }

            } else {
                // если игра закончится по времени
                calcStatusGame = strTimeToEnd;

                if (willTotalUs > willTotalThey) {
                    // Мы победим
                    calcStatusUs = "Мы победим";
                    calcStatusThey = "Они проиграют";
                    calcResult = calcStatusUs + " через " + strTimeToEnd;
                } else if (willTotalUs < willTotalThey) {
                    // Мы проиграем
                    calcStatusUs = "Мы проиграем";
                    calcStatusThey = "Они победят";
                    calcResult = calcStatusThey + " через " + strTimeToEnd;
                } else {
                    // Будет ничья
                    calcStatusUs = "Будет ничья";
                    calcStatusThey = "Будет ничья";
                    calcResult = calcStatusUs + " через " + strTimeToEnd;
                }
            }

        }

        tvCalcStatusUs.setText(calcStatusUs);
        tvCalcStatusThey.setText(calcStatusThey);
        tvCalcStatusGame.setText(calcStatusGame);
        tvResult.setText(calcResult);

    }

    class firstTask extends TimerTask {

        @Override
        public void run() {

//            if (isTimerRunning) {
//                calculate();
//            }

            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if (isTimerRunning) {
                        calculate();
                    }

                }
            });
        }
    };

    private void startTimer() {

        timer = new Timer();
        timer.schedule(new firstTask(), 0,1000);

        btStart.setText("Стоп");
        isTimerRunning = true;

    }

    private void stopTimer() {

        isTimerRunning = false;
        btStart.setText("Старт!");

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

    private List<File> getListFiles() {

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

        File[] files = dir.listFiles();
        List<File> result = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                result.add(file);
            }
        }

        return result;
    }


}
