package com.svoemestodev.catscitycalc;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class CutPicture {

    public static CuttedPictures cutPictire(Bitmap sourceBitmap) {

        int widthSource = sourceBitmap.getWidth();      // ширина исходной картинки
        int heightSource = sourceBitmap.getHeight();   // высота исходной картинки

        int realCalibrate = (widthSource / 2) > Math.abs(MainActivity.calibrate) ? MainActivity.calibrate : 0;

        // координаты для вырезания картинки информации об игре
        int x1 = (int) ((double) widthSource / 2 + MainActivity.XD1 * heightSource) + realCalibrate;
        int x2 = (int) ((double) widthSource / 2 + MainActivity.XD2 * heightSource) + realCalibrate;
        int y1 = (int) ((double) heightSource / 2 + MainActivity.YD1 * ((double) heightSource / 2));
        int y2 = (int) ((double) heightSource / 2 + MainActivity.YD2 * ((double) heightSource / 2));

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1 = Math.max(x1, 0); x2 = Math.min(x2, widthSource);
        y1 = Math.max(y1, 0);  y2 = Math.min(y2, heightSource);

        // координаты для вырезания картинки Наши очки
        int x1totalus = (int) ((double) widthSource / 2 + MainActivity.XD1_TOTALUS * heightSource) + realCalibrate;
        int x2totalus = (int) ((double) widthSource / 2 + MainActivity.XD2_TOTALUS * heightSource) + realCalibrate;
        int y1totalus = (int) ((double) heightSource / 2 + MainActivity.YD1_TOTALUS * ((double) heightSource / 2));
        int y2totalus = (int) ((double) heightSource / 2 + MainActivity.YD2_TOTALUS * ((double) heightSource / 2));

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1totalus = Math.max(x1totalus, 0); x2totalus = Math.min(x2totalus, widthSource);
        y1totalus = Math.max(y1totalus, 0);  y2totalus = Math.min(y2totalus, heightSource);

        // координаты для вырезания картинки Их очки
        int x1totalthey = (int) ((double) widthSource / 2 + MainActivity.XD1_TOTALTHEY * heightSource) + realCalibrate;
        int x2totalthey = (int) ((double) widthSource / 2 + MainActivity.XD2_TOTALTHEY * heightSource) + realCalibrate;
        int y1totalthey = (int) ((double) heightSource / 2 + MainActivity.YD1_TOTALTHEY * ((double) heightSource / 2));
        int y2totalthey = (int) ((double) heightSource / 2 + MainActivity.YD2_TOTALTHEY * ((double) heightSource / 2));

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1totalthey = Math.max(x1totalthey, 0); x2totalthey = Math.min(x2totalthey, widthSource);
        y1totalthey = Math.max(y1totalthey, 0);  y2totalthey = Math.min(y2totalthey, heightSource);

        // координаты для вырезания картинки Время
        int x1totaltime = (int) ((double) widthSource / 2 + MainActivity.XD1_TOTALTIME * heightSource) + realCalibrate;
        int x2totaltime = (int) ((double) widthSource / 2 + MainActivity.XD2_TOTALTIME * heightSource) + realCalibrate;
        int y1totaltime = (int) ((double) heightSource / 2 + MainActivity.YD1_TOTALTIME * ((double) heightSource / 2));
        int y2totaltime = (int) ((double) heightSource / 2 + MainActivity.YD2_TOTALTIME * ((double) heightSource / 2));

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1totaltime = Math.max(x1totaltime, 0); x2totaltime = Math.min(x2totaltime, widthSource);
        y1totaltime = Math.max(y1totaltime, 0);  y2totaltime = Math.min(y2totaltime, heightSource);

        // координаты для вырезания картинки Досрочка
        int x1instancevic = (int) ((double) widthSource / 2 + MainActivity.XD1_INSTANCEVIN * heightSource) + realCalibrate;
        int x2instancevic = (int) ((double) widthSource / 2 + MainActivity.XD2_INSTANCEVIN * heightSource) + realCalibrate;
        int y1instancevic = (int) ((double) heightSource / 2 + MainActivity.YD1_INSTANCEVIN * ((double) heightSource / 2));
        int y2instancevic = (int) ((double) heightSource / 2 + MainActivity.YD2_INSTANCEVIN * ((double) heightSource / 2));

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1instancevic = Math.max(x1instancevic, 0); x2instancevic = Math.min(x2instancevic, widthSource);
        y1instancevic = Math.max(y1instancevic, 0);  y2instancevic = Math.min(y2instancevic, heightSource);

        Matrix matrix = new Matrix();           // матрица ресайза
        matrix.postScale(5.0f, 4.0f);   // будем ресайзать в 4 раза

        // создаем вырезанные и ресайзные картинки
        Bitmap croppingBitmap = Bitmap.createBitmap(sourceBitmap, x1, y1, x2 - x1, y2 - y1);
        Bitmap croppingBitmapTotalUs = Bitmap.createBitmap(sourceBitmap, x1totalus, y1totalus, x2totalus - x1totalus, y2totalus - y1totalus, matrix, false);
        Bitmap croppingBitmapTotalThey = Bitmap.createBitmap(sourceBitmap, x1totalthey, y1totalthey, x2totalthey - x1totalthey, y2totalthey - y1totalthey, matrix, false);
        Bitmap croppingBitmapTotalTime = Bitmap.createBitmap(sourceBitmap, x1totaltime, y1totaltime, x2totaltime - x1totaltime, y2totaltime - y1totaltime, matrix, false);
        Bitmap croppingBitmapInstanceVic = Bitmap.createBitmap(sourceBitmap, x1instancevic, y1instancevic, x2instancevic - x1instancevic, y2instancevic - y1instancevic, matrix, false);

//        try {
//
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
//
//            File fileScreenshot = new File(MainActivity.pathToCATScalcFolder, "last_screenshot.PNG");       // файл картинки - путь к папке программы + имя файла
//            OutputStream fOutScreenshot = new FileOutputStream(fileScreenshot);                   // аутпутстрим на файл
//            sourceBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutScreenshot); // сжимаем картинку в ПНГ с качеством 100%
//            fOutScreenshot.flush();                                                            // сохраняем данные из потока
//            fOutScreenshot.close();                                                            // закрываем поток
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        CuttedPictures cuttedPictures = new CuttedPictures();
        cuttedPictures.croppingBitmap = croppingBitmap;
        cuttedPictures.croppingBitmapInstanceVic = croppingBitmapInstanceVic;
//        cuttedPictures.croppingBitmapTotalUs = croppingBitmapTotalUs;
        cuttedPictures.croppingBitmapTotalUs = adjustedContrast(croppingBitmapTotalUs,100);
//        cuttedPictures.croppingBitmapTotalThey = croppingBitmapTotalThey;
        cuttedPictures.croppingBitmapTotalThey = adjustedContrast(croppingBitmapTotalThey, 100);
        cuttedPictures.croppingBitmapTotalTime = croppingBitmapTotalTime;
//        int contrast = 250;
//        int threshold = 10;
//
//        cuttedPictures.croppingBitmap = croppingBitmap;
//        cuttedPictures.croppingBitmapTotalTime = bitmapToBW(croppingBitmapTotalTime, contrast,255, 255, 255, true,threshold);
//        cuttedPictures.croppingBitmapInstanceVic = bitmapToBW(croppingBitmapInstanceVic, contrast,255, 255, 255, true,threshold);
//        cuttedPictures.croppingBitmapTotalUs = bitmapToBW(croppingBitmapTotalUs, contrast,67, 122, 215, true,threshold);
//        cuttedPictures.croppingBitmapTotalThey = bitmapToBW(croppingBitmapTotalThey, contrast,246, 79, 73, true,threshold);



        return cuttedPictures;


    }

    private static Bitmap bitmapToBW(Bitmap src, int value, int chR, int chG, int chB, boolean invert, int threshold)
    {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap

        // create a mutable empty bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        // create a canvas so that we can draw the bmOut Bitmap from source bitmap
        Canvas c = new Canvas();
        c.setBitmap(bmOut);

        // draw bitmap to bmOut from src bitmap so we can modify it
//        c.drawBitmap(src, 0, 0, new Paint(Color.BLACK));


        // color information
        int A, R, G, B;
        int pixel;

//        Map<String, String> map = new TreeMap<>();


        // scan through all pixels
        for(int x = 0; x < width; ++x) {
             for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

//                 map.put(String.format(Locale.getDefault(),"%03d.%03d.%03d",R,G,B),"");

                if ((R > (chR - threshold) && R < (chR + threshold)) && (G > (chG - threshold) && G < (chG + threshold)) && (B > (chB - threshold) && B < (chB + threshold))) {
                    if (!invert) {
                        R=255;
                        G=255;
                        B=255;
                    } else {
                        R=0;
                        G=0;
                        B=0;
                    }
                } else {
                    if (!invert) {
                        R=0;
                        G=0;
                        B=0;
                    } else {
                        R=255;
                        G=255;
                        B=255;
                    }
                }

//                R = R < value ? 255 : 0;
//                G = G < value ? 255 : 0;
//                B = B < value ? 255 : 0;
//                A = 0;

                // set new pixel color to output bitmap
//                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                bmOut.setPixel(x, y, Color.rgb(R, G, B));
            }
        }
//        if (invert) System.out.println(map);
        return bmOut;
    }

    private static Bitmap adjustedContrast(Bitmap src, double value)
    {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap

        // create a mutable empty bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        // create a canvas so that we can draw the bmOut Bitmap from source bitmap
        Canvas c = new Canvas();
        c.setBitmap(bmOut);

        // draw bitmap to bmOut from src bitmap so we can modify it
        c.drawBitmap(src, 0, 0, new Paint(Color.BLACK));


        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.green(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.blue(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//                bmOut.setPixel(x, y, Color.rgb(R, G, B));
            }
        }
        return bmOut;
    }

}
