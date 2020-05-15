package com.mrt.catscitycalc;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CutPicture {

    public static CuttedPictures cutPictire(Bitmap sourceBitmap) {

        int widthSource = sourceBitmap.getWidth();      // ширина исходной картинки
        int heightSource = sourceBitmap.getHeight();   // высота исходной картинки

        // координаты для вырезания картинки информации об игре
        int x1 = (int) ((double) widthSource / 2 + MainActivity.XD1 * heightSource) + MainActivity.calibrate;
        int x2 = (int) ((double) widthSource / 2 + MainActivity.XD2 * heightSource) + MainActivity.calibrate;
        int y1 = (int) ((double) heightSource / 2 + MainActivity.YD1 * ((double) heightSource / 2));
        int y2 = (int) ((double) heightSource / 2 + MainActivity.YD2 * ((double) heightSource / 2));

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1 = Math.max(x1, 0); x2 = Math.min(x2, widthSource);
        y1 = Math.max(y1, 0);  y2 = Math.min(y2, heightSource);

        // координаты для вырезания картинки Наши очки
        int x1totalus = (int) ((double) widthSource / 2 + MainActivity.XD1_TOTALUS * heightSource) + MainActivity.calibrate;
        int x2totalus = (int) ((double) widthSource / 2 + MainActivity.XD2_TOTALUS * heightSource) + MainActivity.calibrate;
        int y1totalus = (int) ((double) heightSource / 2 + MainActivity.YD1_TOTALUS * ((double) heightSource / 2));
        int y2totalus = (int) ((double) heightSource / 2 + MainActivity.YD2_TOTALUS * ((double) heightSource / 2));

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1totalus = Math.max(x1totalus, 0); x2totalus = Math.min(x2totalus, widthSource);
        y1totalus = Math.max(y1totalus, 0);  y2totalus = Math.min(y2totalus, heightSource);

        // координаты для вырезания картинки Их очки
        int x1totalthey = (int) ((double) widthSource / 2 + MainActivity.XD1_TOTALTHEY * heightSource) + MainActivity.calibrate;
        int x2totalthey = (int) ((double) widthSource / 2 + MainActivity.XD2_TOTALTHEY * heightSource) + MainActivity.calibrate;
        int y1totalthey = (int) ((double) heightSource / 2 + MainActivity.YD1_TOTALTHEY * ((double) heightSource / 2));
        int y2totalthey = (int) ((double) heightSource / 2 + MainActivity.YD2_TOTALTHEY * ((double) heightSource / 2));

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1totalthey = Math.max(x1totalthey, 0); x2totalthey = Math.min(x2totalthey, widthSource);
        y1totalthey = Math.max(y1totalthey, 0);  y2totalthey = Math.min(y2totalthey, heightSource);

        // координаты для вырезания картинки Время
        int x1totaltime = (int) ((double) widthSource / 2 + MainActivity.XD1_TOTALTIME * heightSource) + MainActivity.calibrate;
        int x2totaltime = (int) ((double) widthSource / 2 + MainActivity.XD2_TOTALTIME * heightSource) + MainActivity.calibrate;
        int y1totaltime = (int) ((double) heightSource / 2 + MainActivity.YD1_TOTALTIME * ((double) heightSource / 2));
        int y2totaltime = (int) ((double) heightSource / 2 + MainActivity.YD2_TOTALTIME * ((double) heightSource / 2));

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1totaltime = Math.max(x1totaltime, 0); x2totaltime = Math.min(x2totaltime, widthSource);
        y1totaltime = Math.max(y1totaltime, 0);  y2totaltime = Math.min(y2totaltime, heightSource);

        // координаты для вырезания картинки Досрочка
        int x1instancevic = (int) ((double) widthSource / 2 + MainActivity.XD1_INSTANCEVIN * heightSource) + MainActivity.calibrate;
        int x2instancevic = (int) ((double) widthSource / 2 + MainActivity.XD2_INSTANCEVIN * heightSource) + MainActivity.calibrate;
        int y1instancevic = (int) ((double) heightSource / 2 + MainActivity.YD1_INSTANCEVIN * ((double) heightSource / 2));
        int y2instancevic = (int) ((double) heightSource / 2 + MainActivity.YD2_INSTANCEVIN * ((double) heightSource / 2));

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

//        try {

//            File fileCity = new File(MainActivity.pathToCATScalcFolder, "city.PNG");                   // файл картинки - путь к папке программы + имя файла
//            OutputStream fOutCity = new FileOutputStream(fileCity);                             // аутпутстрим на файл
//            croppingBitmap.compress(Bitmap.CompressFormat.PNG, 90, fOutCity);           // сжимаем картинку в ПНГ с качеством 90%
//            fOutCity.flush();                                                                   // сохраняем данные из потока
//            fOutCity.close();                                                                   // закрываем поток
//
//            File fileTotalUs = new File(MainActivity.pathToCATScalcFolder, "totalus.PNG");             // файл картинки - путь к папке программы + имя файла
//            OutputStream fOutTotalUs = new FileOutputStream(fileTotalUs);                       // аутпутстрим на файл
//            croppingBitmapTotalUs.compress(Bitmap.CompressFormat.PNG, 90, fOutTotalUs);  // сжимаем картинку в ПНГ с качеством 90%
//            fOutTotalUs.flush();                                                                // сохраняем данные из потока
//            fOutTotalUs.close();                                                                // закрываем поток
//
//            File fileTotalThey = new File(MainActivity.pathToCATScalcFolder, "totalthey.PNG");           // файл картинки - путь к папке программы + имя файла
//            OutputStream fOutTotalThey = new FileOutputStream(fileTotalThey);                       // аутпутстрим на файл
//            croppingBitmapTotalThey.compress(Bitmap.CompressFormat.PNG, 90, fOutTotalThey); // сжимаем картинку в ПНГ с качеством 90%
//            fOutTotalThey.flush();                                                               // сохраняем данные из потока
//            fOutTotalThey.close();                                                              // закрываем поток
//
//            File fileTotalTime = new File(MainActivity.pathToCATScalcFolder, "totaltime.PNG");           // файл картинки - путь к папке программы + имя файла
//            OutputStream fOutTotalTime = new FileOutputStream(fileTotalTime);                       // аутпутстрим на файл
//            croppingBitmapTotalTime.compress(Bitmap.CompressFormat.PNG, 90, fOutTotalTime); // сжимаем картинку в ПНГ с качеством 90%
//            fOutTotalTime.flush();                                                              // сохраняем данные из потока
//            fOutTotalTime.close();                                                              // закрываем поток
//
//            File fileInstanceVic = new File(MainActivity.pathToCATScalcFolder, "instainevic.PNG");       // файл картинки - путь к папке программы + имя файла
//            OutputStream fOutInstanceVic = new FileOutputStream(fileInstanceVic);                   // аутпутстрим на файл
//            croppingBitmapInstanceVic.compress(Bitmap.CompressFormat.PNG, 90, fOutInstanceVic); // сжимаем картинку в ПНГ с качеством 90%
//            fOutInstanceVic.flush();                                                            // сохраняем данные из потока
//            fOutInstanceVic.close();                                                            // закрываем поток

//            File fileScreenshot = new File(MainActivity.pathToCATScalcFolder, "last_screenshot.PNG");       // файл картинки - путь к папке программы + имя файла
//            OutputStream fOutScreenshot = new FileOutputStream(fileScreenshot);                   // аутпутстрим на файл
//            sourceBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutScreenshot); // сжимаем картинку в ПНГ с качеством 100%
//            fOutScreenshot.flush();                                                            // сохраняем данные из потока
//            fOutScreenshot.close();                                                            // закрываем поток

//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        CuttedPictures cuttedPictures = new CuttedPictures();
        cuttedPictures.croppingBitmap = croppingBitmap;
        cuttedPictures.croppingBitmapInstanceVic = croppingBitmapInstanceVic;
        cuttedPictures.croppingBitmapTotalUs = croppingBitmapTotalUs;
        cuttedPictures.croppingBitmapTotalThey = croppingBitmapTotalThey;
        cuttedPictures.croppingBitmapTotalTime = croppingBitmapTotalTime;

        return cuttedPictures;


    }


}
