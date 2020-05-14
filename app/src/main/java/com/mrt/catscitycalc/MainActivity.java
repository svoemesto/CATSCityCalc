package com.mrt.catscitycalc;

import android.Manifest;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


/**
 * MainActivity
 */

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_SECOND_ACTIVITY = 100; // код реквеста для вызова Настроек
    private static final Date DATE_EXPIRED = new GregorianCalendar(2020, 5, 1).getTime();   // дата окончания жизни программы. После нее она не будет запускаться
    private static final int MY_PERMISSIONS_REQUESTREAD_READ_EXTERNAL_STORAGE = 1;   // код пермишенса (хз зачем)
    private static final int MY_PERMISSIONS_REQUESTREAD_WRITE_EXTERNAL_STORAGE = 1;   // код пермишенса (хз зачем)
    private static final int MY_PERMISSIONS_REQUESTREAD_INTERNET = 1;   // код пермишенса (хз зачем)

    private ImageView ivCity; // кропнутая картинка

    private TextView etInTimeLeft;  // время до конца игры (по скриншоту)
    private TextView etInPlusUs; // прирост очков у нас (по скриншоту)
    private TextView etInPlusThey; // прирост очков у них (по скриншоту)
    private TextView etInTotalUs; // всего очнов у нас (по скриншоту)
    private TextView etInTotalThey; // всего очков у них (по скриншоту)
    private TextView etInInstantVic; // очков до досрочной победы (по скриншоту)

    public Switch swListenNewFiles; // переключатель "Отслеживать новые файлы"
    private TextView tvResult; // результат игры

    private File fileScreenshot;    // текущий файл скриншота
    private File fileScreenshotPrevious;    // предыдущий файл скриншота
    private File fileLastInFolder;    // последний файл в папке

    private Timer timer;                        // таймер
    private String pathToScreenshotDir = "";    // путь к папке скриншотов
    private String pathToCATScalcFolder = "";   // путь к папке программы в корне флешки
    private boolean isListenToNewFileInFolder;  // флаг "Следить за новыми файлами в папке"
    private boolean isAllFieldsCorrect;         // флаг "Все поля заполнены правильно"

    // коэффициэнты кропа картинки
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

    private Date timeStartGame;         // дата начала игры
    private Date timeEndGame;           // дата конца игры

    private int initMinutesToEndGame;   // инитное значение кол-ва минут до конца игры
    private int initPlusUs;             // инитное значение ПлюсНам
    private int initInstantVic;         // инитное значение очков до досрочки
    private int initTotalUs;            // инитное значение НашиОчки
    private int initTotalThey;          // инитное значение ИхОчки
    private int initPlusThey;           // инитное значение ПлюсИм

    private NotificationManager notificationManager;    // нотификатор
    private static final int NOTIFY_ID = 1;             // айди нотификатора
    private static final String CHANNEL_ID = "chan1";   // канал нотификатора


    // DEBUG MODE - контролы
    private boolean isDebugMode;
    private LinearLayout dbgLayout;
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


    /**
     *  Распознование картинки
     *  На входе - битмап
     *  На выходе - строка
     */

    private String recognizePicture(Bitmap bitmap) {

        String result = ""; // результат
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build(); // создаем текстрекогнайзер
        if (textRecognizer.isOperational()) {   // если текстрекогнайзер может что-то распознать
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();    // создаем фрейм на основе переданного битмапа
            SparseArray<TextBlock> items = textRecognizer.detect(frame);    // передаем фрейм в текстрекогнайзер, на выходе - массив текстовых блоков
            for (int i = 0; i < items.size(); ++i) {                        // проходимся по массиву текстовых блоков
                result = result + items.valueAt(i).getValue() + " ";        // добавляем к результату значение текста в очередном блоке, разделяем пробелами
            }
        }
        return result;  // возвращаем результат. Если не было ни одного блока или они все были пустыми - результатом будет пустая строка
    }


    /**
     *  Вырезаем из строки все не-числовые символы и возвращаем результат
     */

    private String cutNotNumericSymbols(String str) {
        String result = "";                 // результат
        for (char ch : str.toCharArray()) { // цикл по символам строки
            if (ch >= '0' && ch <='9') {    // если символ - числовой
                result = result + ch;       // приклеиваем его к результату
            }
        }
        return result;                      // возвращаем результат
    }


    /**
     * Вырезание картинок
     */

    private void cutPicture() {

        if (fileScreenshot != null && !fileScreenshot.equals(fileScreenshotPrevious)) {  // если текущий скриншот не нулл

            fileScreenshotPrevious = fileScreenshot;
            Bitmap sourceBitmap = BitmapFactory.decodeFile(fileScreenshot.getAbsolutePath());   // получаем битмап из файла скриншота
            int widthSource = sourceBitmap.getWidth();      // ширина исходной картинки
            int heightSource = sourceBitmap.getHeight();   // высота исходной картинки

            // координаты для вырезания картинки информации об игре
            int x1 = (int) ((double) widthSource / 2 + XD1 * heightSource);
            int x2 = (int) ((double) widthSource / 2 + XD2 * heightSource);
            int y1 = (int) ((double) heightSource / 2 + YD1 * ((double) heightSource / 2));
            int y2 = (int) ((double) heightSource / 2 + YD2 * ((double) heightSource / 2));

            // если координаты вылезаю за границы скриншота - приводим их к границам
            x1 = Math.max(x1, 0); x2 = Math.min(x2, widthSource);
            y1 = Math.max(y1, 0);  y2 = Math.min(y2, heightSource);

            // координаты для вырезания картинки Наши очки
            int x1totalus = (int) ((double) widthSource / 2 + XD1_TOTALUS * heightSource);
            int x2totalus = (int) ((double) widthSource / 2 + XD2_TOTALUS * heightSource);
            int y1totalus = (int) ((double) heightSource / 2 + YD1_TOTALUS * ((double) heightSource / 2));
            int y2totalus = (int) ((double) heightSource / 2 + YD2_TOTALUS * ((double) heightSource / 2));

            // если координаты вылезаю за границы скриншота - приводим их к границам
            x1totalus = Math.max(x1totalus, 0); x2totalus = Math.min(x2totalus, widthSource);
            y1totalus = Math.max(y1totalus, 0);  y2totalus = Math.min(y2totalus, heightSource);

            // координаты для вырезания картинки Их очки
            int x1totalthey = (int) ((double) widthSource / 2 + XD1_TOTALTHEY * heightSource);
            int x2totalthey = (int) ((double) widthSource / 2 + XD2_TOTALTHEY * heightSource);
            int y1totalthey = (int) ((double) heightSource / 2 + YD1_TOTALTHEY * ((double) heightSource / 2));
            int y2totalthey = (int) ((double) heightSource / 2 + YD2_TOTALTHEY * ((double) heightSource / 2));

            // если координаты вылезаю за границы скриншота - приводим их к границам
            x1totalthey = Math.max(x1totalthey, 0); x2totalthey = Math.min(x2totalthey, widthSource);
            y1totalthey = Math.max(y1totalthey, 0);  y2totalthey = Math.min(y2totalthey, heightSource);

            // координаты для вырезания картинки Время
            int x1totaltime = (int) ((double) widthSource / 2 + XD1_TOTALTIME * heightSource);
            int x2totaltime = (int) ((double) widthSource / 2 + XD2_TOTALTIME * heightSource);
            int y1totaltime = (int) ((double) heightSource / 2 + YD1_TOTALTIME * ((double) heightSource / 2));
            int y2totaltime = (int) ((double) heightSource / 2 + YD2_TOTALTIME * ((double) heightSource / 2));

            // если координаты вылезаю за границы скриншота - приводим их к границам
            x1totaltime = Math.max(x1totaltime, 0); x2totaltime = Math.min(x2totaltime, widthSource);
            y1totaltime = Math.max(y1totaltime, 0);  y2totaltime = Math.min(y2totaltime, heightSource);

            // координаты для вырезания картинки Досрочка
            int x1instancevic = (int) ((double) widthSource / 2 + XD1_INSTANCEVIN * heightSource);
            int x2instancevic = (int) ((double) widthSource / 2 + XD2_INSTANCEVIN * heightSource);
            int y1instancevic = (int) ((double) heightSource / 2 + YD1_INSTANCEVIN * ((double) heightSource / 2));
            int y2instancevic = (int) ((double) heightSource / 2 + YD2_INSTANCEVIN * ((double) heightSource / 2));

            // если координаты вылезаю за границы скриншота - приводим их к границам
            x1instancevic = Math.max(x1instancevic, 0); x2instancevic = Math.min(x2instancevic, widthSource);
            y1instancevic = Math.max(y1instancevic, 0);  y2instancevic = Math.min(y2instancevic, heightSource);

            Matrix matrix = new Matrix();           // матрица ресайза
            matrix.postScale(4.0f, 4.0f);   // будем ресайзать в 4 раза

            // создаем вырезанные и ресайзные картинки
            Bitmap croppingBitmap = Bitmap.createBitmap(sourceBitmap, x1, y1, x2 - x1, y2 - y1);
            Bitmap croppingBitmapTotalUs = Bitmap.createBitmap(sourceBitmap, x1totalus, y1totalus, x2totalus - x1totalus, y2totalus - y1totalus, matrix, false);
            Bitmap croppingBitmapTotalThey = Bitmap.createBitmap(sourceBitmap, x1totalthey, y1totalthey, x2totalthey - x1totalthey, y2totalthey - y1totalthey, matrix, false);
            Bitmap croppingBitmapTotalTime = Bitmap.createBitmap(sourceBitmap, x1totaltime, y1totaltime, x2totaltime - x1totaltime, y2totaltime - y1totaltime, matrix, false);
            Bitmap croppingBitmapInstanceVic = Bitmap.createBitmap(sourceBitmap, x1instancevic, y1instancevic, x2instancevic - x1instancevic, y2instancevic - y1instancevic, matrix, false);

            try {

                File fileCity = new File(pathToCATScalcFolder, "city.PNG");                   // файл картинки - путь к папке программы + имя файла
                OutputStream fOutCity = new FileOutputStream(fileCity);                             // аутпутстрим на файл
                croppingBitmap.compress(Bitmap.CompressFormat.PNG, 90, fOutCity);           // сжимаем картинку в ПНГ с качеством 90%
                fOutCity.flush();                                                                   // сохраняем данные из потока
                fOutCity.close();                                                                   // закрываем поток

                File fileTotalUs = new File(pathToCATScalcFolder, "totalus.PNG");             // файл картинки - путь к папке программы + имя файла
                OutputStream fOutTotalUs = new FileOutputStream(fileTotalUs);                       // аутпутстрим на файл
                croppingBitmapTotalUs.compress(Bitmap.CompressFormat.PNG, 90, fOutTotalUs);  // сжимаем картинку в ПНГ с качеством 90%
                fOutTotalUs.flush();                                                                // сохраняем данные из потока
                fOutTotalUs.close();                                                                // закрываем поток

                File fileTotalThey = new File(pathToCATScalcFolder, "totalthey.PNG");           // файл картинки - путь к папке программы + имя файла
                OutputStream fOutTotalThey = new FileOutputStream(fileTotalThey);                       // аутпутстрим на файл
                croppingBitmapTotalThey.compress(Bitmap.CompressFormat.PNG, 90, fOutTotalThey); // сжимаем картинку в ПНГ с качеством 90%
                fOutTotalThey.flush();                                                               // сохраняем данные из потока
                fOutTotalThey.close();                                                              // закрываем поток

                File fileTotalTime = new File(pathToCATScalcFolder, "totaltime.PNG");           // файл картинки - путь к папке программы + имя файла
                OutputStream fOutTotalTime = new FileOutputStream(fileTotalTime);                       // аутпутстрим на файл
                croppingBitmapTotalTime.compress(Bitmap.CompressFormat.PNG, 90, fOutTotalTime); // сжимаем картинку в ПНГ с качеством 90%
                fOutTotalTime.flush();                                                              // сохраняем данные из потока
                fOutTotalTime.close();                                                              // закрываем поток

                File fileInstanceVic = new File(pathToCATScalcFolder, "instainevic.PNG");       // файл картинки - путь к папке программы + имя файла
                OutputStream fOutInstanceVic = new FileOutputStream(fileInstanceVic);                   // аутпутстрим на файл
                croppingBitmapInstanceVic.compress(Bitmap.CompressFormat.PNG, 90, fOutInstanceVic); // сжимаем картинку в ПНГ с качеством 90%
                fOutInstanceVic.flush();                                                            // сохраняем данные из потока
                fOutInstanceVic.close();                                                            // закрываем поток

                File fileScreenshot = new File(pathToCATScalcFolder, "last_screenshot.PNG");       // файл картинки - путь к папке программы + имя файла
                OutputStream fOutScreenshot = new FileOutputStream(fileScreenshot);                   // аутпутстрим на файл
                sourceBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutScreenshot); // сжимаем картинку в ПНГ с качеством 100%
                fOutScreenshot.flush();                                                            // сохраняем данные из потока
                fOutScreenshot.close();                                                            // закрываем поток

            } catch (IOException e) {
                e.printStackTrace();
            }

            ivCity.setImageBitmap(croppingBitmap);  // выводим битмат игры в контрол

            // стартовые занчения переменных
            String strTotalUs = "0";
            String strPlusUs = "0";
            String strTotalThey = "0";
            String strPlusThey = "0";

            String strTotalUsAndPlus = recognizePicture(croppingBitmapTotalUs);         // распознаем "Наши очки"
            String strTotalTheyAndPlus = recognizePicture(croppingBitmapTotalThey);     // распознаем "Их очки"
            String strTotalTime = recognizePicture(croppingBitmapTotalTime);            // распознаем "Время"
            String strInstanceVic = recognizePicture(croppingBitmapInstanceVic);        // распознаем "Досрочка"

            if (isDebugMode) { // если включен дебаг-мод
                // выводим битмапы и распознанные данные в контрлы дебаг-мода
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
            }


            // парсинг "Наши очки"
            String[] arrTotalUs = strTotalUsAndPlus.split("\\D");   // парсим с разделителем "не цифра"
            List<String> listToUs = new ArrayList<>();      // лист значений
            for (int i = 0; i < arrTotalUs.length; i++) {   // проходим по распарсенному массиву
                if (!arrTotalUs[i].equals("")) {            // если элемент массива не пустой
                    listToUs.add(arrTotalUs[i]);            // добавляем текущий элемент массива в лист
                }
            }

            if (listToUs.size() > 0) {                      // если в списке есть элементы
                if (listToUs.size() == 1) {                 // если в списке один элемент
                    strPlusUs = "0";                        // "ПлюсНам" = 0
                    strTotalUs = listToUs.get(0);           // "НашиОчки" = первый и единственный элемнет из списка
                } else {                                     // если в списке больше одного элемента
                    strPlusUs = listToUs.get(0);            // "ПлюсНам" = первый элемнет из списка
                    strTotalUs = listToUs.get(1);           // "НашиОчки" = второй элемнет из списка
                }
            }

            // парсинг "Их очки"
            String[] arrTotalThey = strTotalTheyAndPlus.split("\\D"); // парсим с разделителем "не цифра"
            List<String> listToThey = new ArrayList<>();     // лист значений
            for (int i = 0; i < arrTotalThey.length; i++) {  // проходим по распарсенному массиву
                if (!arrTotalThey[i].equals("")) {           // если элемент массива не пустой
                    listToThey.add(arrTotalThey[i]);        // добавляем текущий элемент массива в лист
                }
            }

            if (listToThey.size() > 0) {                // если в списке есть элементы
                if (listToThey.size() == 1) {           // если в списке один элемент
                    strPlusThey = "0";                  // "ПлюсИм" = 0
                    strTotalThey = listToThey.get(0);   // "ИхОчки" = первый и единственный элемнет из списка
                } else {                                // если в списке больше одного элемента
                    strPlusThey = listToThey.get(1);    // "ПлюсИм" = первый элемнет из списка
                    strTotalThey = listToThey.get(0);   // "ИхОчки" = второй элемнет из списка
                }
            }

            // парсинг "Досрочка" - если строка не пустая - вырезаем из нее не числовые символы
            strInstanceVic = strInstanceVic.equals("") ? "0" : cutNotNumericSymbols(strInstanceVic);

            // парсинг "Время"
            String[] arrTotalTime = strTotalTime.split(" ");    // парсим с разделителем "пробел"
            List<String> listTotalTime = new ArrayList<>();            // лист значений
            for (int i = 0; i < arrTotalTime.length; i++) {             // проходим по распарсенному массиву
                if (!arrTotalTime[i].equals("")) {                      // если элемент массива не пустой
                    listTotalTime.add(arrTotalTime[i]);                 // добавляем текущий элемент массива в лист
                }
            }

            if (listTotalTime.size() > 1) {                         // если в списке больше 1 элемента
                for (int i = 0; i < listTotalTime.size(); i++) {    // проходим по списку
                    listTotalTime.set(i, listTotalTime.get(i).substring(0, listTotalTime.get(i).length() - 1)); // отрезаем у текущего элемента списка последний символ
                }
            }

            if (listTotalTime.size() > 0) {     // если в списке есть элементы
                if (listTotalTime.size() == 1) {    // если в списке 1 элемент
                    if (listTotalTime.get(0).substring(listTotalTime.get(0).length()-1).toLowerCase().equals("m")) {    // если последний символ элемента равен букве "М"
                        String hours = "00";    // часы = "00"
                        String minutes = listTotalTime.get(0).substring(0, listTotalTime.get(0).length()-1);    // минуты = элемент без последнего символа
                        if (minutes.length() == 1) minutes = "0" + minutes;    // если минуты состоят из 1 символа -  дописываем "0" в начало минут
                        strTotalTime = hours + ":" + minutes;   // время = часы : минуты
                    } else {    // если последний символ элемента не равен букве "М"
                        String hours = listTotalTime.get(0).substring(0, listTotalTime.get(0).length()-1);  // часы = элемент без последнего символа
                        String minutes = "00";  // минуты = "00"
                        strTotalTime = hours + ":" + minutes; // время = часы : минуты
                    }
                } else {    // если в списке больше 1 элемента
                    String hours = listTotalTime.get(0);    // часы - первый элемент из списка
                    String minutes = listTotalTime.get(1);  // минуты - второй элемент из списка
                    if (minutes.length() == 1) minutes = "0" + minutes;    // если минуты состоят из 1 символа -  дописываем "0" в начало минут
                    strTotalTime = hours + ":" + minutes; // время = часы : минуты
                }
            } else { // если в списке нет элементов
                strTotalTime = "00:00"; // время нулевое
            }

            // если значения пустые - выводим в них "??", если нет - удаляем лишние пробелы в начале и в конце
            strPlusUs = strPlusUs.equals("") ? "?" : strPlusUs.trim();
            strPlusThey = strPlusThey.equals("") ? "?" : strPlusThey.trim();
            strTotalUs = strTotalUs.equals("") ? "?" : strTotalUs.trim();
            strTotalThey = strTotalThey.equals("") ? "?" : strTotalThey.trim();
            strInstanceVic = strInstanceVic.equals("") ? "?" : strInstanceVic.trim();
            strTotalTime = strTotalTime.equals("") ? "?:?" : strTotalTime.trim();

            // выводим распознанные данные в контролы
            etInPlusUs.setText(strPlusUs);
            etInPlusThey.setText(strPlusThey);
            etInTotalUs.setText(strTotalUs);
            etInTotalThey.setText(strTotalThey);
            etInTimeLeft.setText(strTotalTime);
            etInInstantVic.setText(strInstanceVic);
            tvResult.setText("");
        }

    }

    /**
     *  Получение самого свежего файла из папки
     *  На вход - папка
     *  На выход - файл
     */
    private File getLastFileInFolder(String pathToFolder) {

        File temp = null;           // временный файл
        File lastScreenshot = new File(pathToCATScalcFolder, "last_screenshot.PNG"); // последний скри
        File dir = new File(pathToFolder); // папка
        File[] files = dir.listFiles(new FilenameFilter() { // присок файлов в папке по фильтру
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png");  // фильтр по JPG и PNG
            }
        });
        List<File> listFiles = new ArrayList<>(); // лист
        if (files != null) {    // если файлы в папке есть
            for (File file : files) {   // цикл по файлам
                listFiles.add(file);    // добавляем файл в лист
            }
        }

        if  (listFiles.size() > 0) {    // если в листе есть файлы
            long maxLastModified = 0;   // максимальная дата (ноль для начала)

            for (File file : listFiles) {   // цикл по листу
                if (file.lastModified() > maxLastModified) {    // если дата создания файла из листа больше максимальной
                    maxLastModified = file.lastModified();      // максимальная дата = дате файла из листа
                    temp = file;    // временный файл равен файлу из листа
                }
            }

            if (temp != null) { // если найден самый свежий файл
                if (!temp.equals(fileLastInFolder)) {   // если найденный файл не совпадает с раенее найденным "последним файлом"
                    Bitmap sourceBitmap = BitmapFactory.decodeFile(temp.getAbsolutePath());   // получаем битмап из файла скриншота
                    int widthSource = sourceBitmap.getWidth();      // ширина исходной картинки
                    int heightSource = sourceBitmap.getHeight();   // высота исходной картинки

                    if (widthSource < heightSource) {
                        if (fileLastInFolder == null) {
                            temp = lastScreenshot;
                            if (!temp.exists()) temp = null;
                        } else {
                            temp = null;    // если ориентация картинки неправильная - найденный файл не подходит и будет равен нулл
                        }
                    } else {
                        fileLastInFolder = temp;    // последний найденный файл - текущий найденный
                    }

                }
            }
        }

        if (temp == null && lastScreenshot.exists()) return lastScreenshot;

        return temp;
    }

    /**
     *  Выбор скриншота
     */
    private void selectScreenshot() {

        OpenFileDialog fileDialog = new OpenFileDialog(this, pathToScreenshotDir)   // диалог выбора скриншота по переданному пути
                .setFolderIcon(getResources().getDrawable(R.drawable.ic_folder))            // иконка папки
                .setFileIcon(getResources().getDrawable(R.drawable.ic_file))                // иконка файла
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {
                        fileScreenshot = new File(fileName); // файл скриншота - возавращенный из диалога
                        isListenToNewFileInFolder = false; // снимаем флажок "Следить за файлами в папке"
                        swListenNewFiles.setChecked(false); // устанавливаем контрол флажка
                        fileLastInFolder = null;    // сбрасываем последний файл в папке
                        cutPicture();   // вырезаем картинку
                    }
                });
        fileDialog.show();

    }

    /**
     * Открытие Настроек
     */
    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);   // создаем интент активики Настроек
        startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);               // стартуем его и будем отслеживать REQUEST_CODE_SECOND_ACTIVITY после возвращения в текущую активити
    }

    /**
     *  Созданием меню
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);  // создаем меню
        return true;
    }

    /**
     * Выбор элементов меню
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // айди элемента меню
        switch(id){
            case R.id.menu_open_settings :  // "Настройки"
                openSettings();
                return true;
            case R.id.menu_open_screenshot :    // "Открыть скриншотк"
                selectScreenshot();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Возврат в текущую активность из какой-то другой активности
     */

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

    /**
     * Инициализация текущей активности
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // инициализирукм контентвью

        // РЕКЛАМА
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
//        MobileAds.setRequestConfiguration(
//                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
//                        .build());

        // рекламный блок
        AdView mAdView = findViewById(R.id.adView);
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

        // конец рекламы



        // если "программа устарела" - выходим
        if (Calendar.getInstance().getTime().after(DATE_EXPIRED)) {
            Toast.makeText(MainActivity.this,"Программа устарела", Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }


        // запрос разрешения на чтение файловой системы
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUESTREAD_READ_EXTERNAL_STORAGE);
            }
        }

        // запрос разрешения на запись файловой системы
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUESTREAD_WRITE_EXTERNAL_STORAGE);
            }
        }

        // запрос разрешения на интернет
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUESTREAD_INTERNET);
            }
        }

        // путь к папке программы в корне файловой системы. Если такой папки нет - создаем её
        pathToCATScalcFolder = Environment.getExternalStorageDirectory().getPath() + "/CATScalc";
        File tempDir = new File(pathToCATScalcFolder);
        if (!tempDir.exists()) tempDir.mkdir();

        // нотификейшт менеджер
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

        // инитные значения контролов
        etInTimeLeft.setText("00:00");
        etInPlusUs.setText("0");
        etInPlusThey.setText("0");
        etInTotalUs.setText("0");
        etInTotalThey.setText("0");
        etInInstantVic.setText("0");
        tvResult.setText("");

        // контролы дебаг-мода
        dbgLayout = findViewById(R.id.dbg_layout);
        TextView dbgTvHeader = findViewById(R.id.dbg_tv_header);
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

        // считываем преференцы, пихаем их в переменные и в контролы
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
        pathToScreenshotDir = sharedPreferences.getString(getString(R.string.pref_screenshot_folder),"");
        isListenToNewFileInFolder = sharedPreferences.getBoolean(getString(R.string.pref_listen_last_file),false);
        isDebugMode = sharedPreferences.getBoolean(getString(R.string.pref_debug_mode),false);
        swListenNewFiles.setChecked(isListenToNewFileInFolder);

        // устанавливаем видимость группы контролов дебаг-мода, если он включен
        dbgLayout.setVisibility(isDebugMode ? View.VISIBLE : View.INVISIBLE);

        // отслеживание изменения свича "Следить за файлами в папке"
        swListenNewFiles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    // если свич переключили
                // обновляем соответствующий пермишн и переменную
                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(getString(R.string.pref_preferences_file), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.pref_listen_last_file), isChecked);
                editor.apply();
                isListenToNewFileInFolder = isChecked;
            }
        });

        startTimer();   // стартуем таймер

    }

    /**
     * Калькулятор
     */

    private void calculate() {

        String calcStatusUs;    // наш статус
        String calcStatusThey;  // их статус
        String calcResult;      // результат

        if (isAllFieldsCorrect) {   // если все поля заполненны верно

            Date currentTime = Calendar.getInstance().getTime();    // текущее время
            long timeLeftInMillis = currentTime.getTime() - timeStartGame.getTime(); // кол-во миллисекунд, прошедшех от начала игры до текущего времени
            long currentSecsAndMillis = 60000 - timeLeftInMillis % 60_000; // кол-во секунд и миллисекунд (в миллисекундах)
            int currentTotalUs = (int) ((timeLeftInMillis / 60000) * initPlusUs + initTotalUs);            // на данный момент очков у нас
            int currentTotalThey = (int) ((timeLeftInMillis / 60000) * initPlusThey + initTotalThey);      // на данный момент очков у них
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

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // форматтер строки даты

            if (isGameOver) {   // если игра закончилась
                currentTotalUs += initPlusUs;       // наши текущие очки
                currentTotalThey += initPlusThey;   // их текущие очки

                if (isGameOverInstance) {   // если игра закончилась досрочно
                    if (currentTotalUs > currentTotalThey) {    // досрочно выиграли мы
                        calcResult = "Мы досрочно выиграли";
                    } else if (currentTotalUs < currentTotalThey) { // досрочно выиграли они
                        calcResult = "Мы досрочно проиграли";
                    } else { // досрочная ничья
                        calcResult = "Досрочная ничья";
                    }
                } else { // если игра закончилась по времени
                    if (currentTotalUs > currentTotalThey) { // выиграли мы
                        calcResult = "Мы выиграли";
                    } else if (currentTotalUs < currentTotalThey) { // выиграли они
                        calcResult = "Мы проиграли";
                    } else { // ничья
                        calcResult = "Ничья";
                    }
                }

            } else { // если игра не закончилась
                // часы, минуты, секунды и общее время до конца игры
                int hoursToEnd = (int)((leftMillsToEndGame - 1000) / (1000 * 60 * 60));
                int minutesToEnd = (int)(((leftMillsToEndGame - 1000) - hoursToEnd * (1000 * 60 * 60)) / (1000 * 60));
                int secondsToEnd = (int)(((leftMillsToEndGame - 1000) - hoursToEnd * (1000 * 60 * 60) - minutesToEnd * (1000 * 60)) / 1000);
                String strTimeToEnd = String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursToEnd, minutesToEnd, secondsToEnd);

                // время окончания игры (локальное)
                long timeEndGame = currentTime.getTime() + leftMillsToEndGame;
                String strTimeEndGame = simpleDateFormat.format(new Date(timeEndGame));

                if (leftMillsToEndGame - leftMillsToInstanceUs > 0 || leftMillsToEndGame - leftMillsToInstanceThey > 0) { // если игра закончится досрочно


                    long leftMillsToInstanceEndGame = Math.min((leftMillsToInstanceUs), (leftMillsToInstanceThey)); // осталось миллисекунд до досрочного окончания
                    // часы, минуты, секунды и общее время до досрочного конца игры
                    int hoursToInstanceEnd = (int)((leftMillsToInstanceEndGame - 1000) / (1000 * 60 * 60));
                    int minutesToInstanceEnd = (int)(((leftMillsToInstanceEndGame - 1000) - hoursToInstanceEnd * (1000 * 60 * 60)) / (1000 * 60));
                    int secondsToInstanceEnd = (int)(((leftMillsToInstanceEndGame - 1000) - hoursToInstanceEnd * (1000 * 60 * 60) - minutesToInstanceEnd * (1000 * 60)) / 1000);
                    String strTimeToInstanceEnd = String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursToInstanceEnd, minutesToInstanceEnd, secondsToInstanceEnd);

                    // время досрочного окончания игры (локальное)
                    long timeEndGameInstance = currentTime.getTime() + leftMillsToInstanceEndGame;
                    String strTimeEndGameInstance = simpleDateFormat.format(new Date(timeEndGameInstance));

                    if (leftMillsToInstanceUs < leftMillsToInstanceThey) { // Мы победим досрочно
                        calcResult = strTimeToInstanceEnd + " Мы победим досрочно с разницей в " + (willTotalInstanceUs - willTotalInstanceThey) + " очков " + strTimeEndGameInstance;
                    } else if (leftMillsToInstanceUs > leftMillsToInstanceThey) { // Мы проиграем досрочно
                        calcResult = strTimeToInstanceEnd + " Мы проиграем досрочно с разницей в " + (willTotalInstanceThey - willTotalInstanceUs) + " очков " + strTimeEndGameInstance;
                    } else { // Будет досрочная ничья
                        calcResult = "Будет досрочная ничья через " + strTimeToInstanceEnd;
                    }

                } else { // если игра закончится по времени
                    if (willTotalUs > willTotalThey) { // Мы победим
                        calcResult = strTimeToEnd + " Мы победим с разницей в " + (willTotalUs - willTotalThey) + " очков " + strTimeEndGame;
                    } else if (willTotalUs < willTotalThey) { // Мы проиграем
                        calcResult = strTimeToEnd + " Мы проиграем с разницей в " + (willTotalThey - willTotalUs) + " очков " + strTimeEndGame;
                    } else { // Будет ничья
                        calcResult = strTimeToEnd + " Будет ничья " + strTimeEndGame;
                    }
                }

            }
        } else { // если не все поля заполненны верно
            calcResult = "Ошибка ввода данных";
        }

        tvResult.setText(calcResult); // выводим текс результата в контрол

        // Создаем нотификейшен
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_catscalciconsmall)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentText(calcResult)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(calcResult));
                createChannelIfNeeded(notificationManager);
                notificationManager.notify(NOTIFY_ID, notificationBuilder.build());

    }

    /**
     * Создание канала нотификации
     */
    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * Класс задачи таймера
     */

    class firstTask extends TimerTask {

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if (isListenToNewFileInFolder) {    // если установлен флажок "Следить за файлами в папке"
                        File tmpFile = getLastFileInFolder(pathToScreenshotDir);    // получаем последний файл из папки
                        if (tmpFile != null) {  // если он не пустой
                            if (!tmpFile.equals(fileScreenshot)) {  // если он не равен текущем скриншоту
                                fileScreenshot = tmpFile;   // текущий скриншот = последнему файлу в папке
                                cutPicture();               // вырезам картинки
                            }
                        }

                    }

                    if (fileScreenshot != null) {   // если скринщот выбран

                        try {
                            // считываем переменные из контролов и если не возникло ошибки устанавливаем флажок "Данные введены верно"
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
                            // если возникла ощибка считывания из контролов - сбрасываем флажок "Данные введены верно"
                            isAllFieldsCorrect = false;
                        }

                        calculate();    // вызываем калькулятор
                    }

                }
            });
        }
    };

    /**
     * Стартуем таймер
     */
    private void startTimer() {

        if (timer == null) {    // если таймер не запущен
            timer = new Timer();    // запускаем таймер
            timer.schedule(new firstTask(), 1000,1000); // запускаем такс таймера
        }

    }

    /**
     * Парсим время
     */
    private int parseTimeLeft(String str) {
        int result  = 0;
        String[] words = str.split(":");
        result = Integer.parseInt(words[0])* 60 + Integer.parseInt(words[1]);
        return result;
    }




}
