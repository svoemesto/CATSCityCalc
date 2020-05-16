package com.svoemestodev.catscitycalc;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class CutPicture {

    public static CuttedPictures cutPictire(Bitmap sourceBitmap) {

        int widthSource = sourceBitmap.getWidth();      // ширина исходной картинки
        int heightSource = sourceBitmap.getHeight();   // высота исходной картинки

        int realCalibrateX = (widthSource / 2) > Math.abs(MainActivity.calibrateX) ? MainActivity.calibrateX : 0;
        int realCalibrateY = (heightSource / 2) > Math.abs(MainActivity.calibrateY) ? MainActivity.calibrateY : 0;

        // координаты для вырезания картинки информации об игре
        int x1 = (int) ((double) widthSource / 2 + MainActivity.cut_city_x1 * heightSource) + realCalibrateX;
        int x2 = (int) ((double) widthSource / 2 + MainActivity.cut_city_x2 * heightSource) + realCalibrateX;
        int y1 = (int) ((double) heightSource / 2 + MainActivity.cut_city_y1 * ((double) heightSource / 2)) + realCalibrateY;
        int y2 = (int) ((double) heightSource / 2 + MainActivity.cut_city_y2 * ((double) heightSource / 2)) + realCalibrateY;

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1 = Math.max(x1, 0); x2 = Math.min(x2, widthSource);
        y1 = Math.max(y1, 0);  y2 = Math.min(y2, heightSource);

        // координаты для вырезания картинки Наши очки
        int x1totalus = (int) ((double) widthSource / 2 + MainActivity.cut_total_us_x1 * heightSource) + realCalibrateX;
        int x2totalus = (int) ((double) widthSource / 2 + MainActivity.cut_total_us_x2 * heightSource) + realCalibrateX;
        int y1totalus = (int) ((double) heightSource / 2 + MainActivity.cut_total_us_y1 * ((double) heightSource / 2)) + realCalibrateY;
        int y2totalus = (int) ((double) heightSource / 2 + MainActivity.cut_total_us_y2 * ((double) heightSource / 2)) + realCalibrateY;

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1totalus = Math.max(x1totalus, 0); x2totalus = Math.min(x2totalus, widthSource);
        y1totalus = Math.max(y1totalus, 0);  y2totalus = Math.min(y2totalus, heightSource);

        // координаты для вырезания картинки Их очки
        int x1totalthey = (int) ((double) widthSource / 2 + MainActivity.cut_total_they_x1 * heightSource) + realCalibrateX;
        int x2totalthey = (int) ((double) widthSource / 2 + MainActivity.cut_total_they_x2 * heightSource) + realCalibrateX;
        int y1totalthey = (int) ((double) heightSource / 2 + MainActivity.cut_total_they_y1 * ((double) heightSource / 2)) + realCalibrateY;
        int y2totalthey = (int) ((double) heightSource / 2 + MainActivity.cut_total_they_y2 * ((double) heightSource / 2)) + realCalibrateY;

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1totalthey = Math.max(x1totalthey, 0); x2totalthey = Math.min(x2totalthey, widthSource);
        y1totalthey = Math.max(y1totalthey, 0);  y2totalthey = Math.min(y2totalthey, heightSource);

        // координаты для вырезания картинки Время
        int x1totaltime = (int) ((double) widthSource / 2 + MainActivity.cut_total_time_x1 * heightSource) + realCalibrateX;
        int x2totaltime = (int) ((double) widthSource / 2 + MainActivity.cut_total_time_x2 * heightSource) + realCalibrateX;
        int y1totaltime = (int) ((double) heightSource / 2 + MainActivity.cut_total_time_y1 * ((double) heightSource / 2)) + realCalibrateY;
        int y2totaltime = (int) ((double) heightSource / 2 + MainActivity.cut_total_time_y2 * ((double) heightSource / 2)) + realCalibrateY;

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1totaltime = Math.max(x1totaltime, 0); x2totaltime = Math.min(x2totaltime, widthSource);
        y1totaltime = Math.max(y1totaltime, 0);  y2totaltime = Math.min(y2totaltime, heightSource);

        // координаты для вырезания картинки Досрочка
        int x1instancevic = (int) ((double) widthSource / 2 + MainActivity.cut_early_win_x1 * heightSource) + realCalibrateX;
        int x2instancevic = (int) ((double) widthSource / 2 + MainActivity.cut_early_win_x2 * heightSource) + realCalibrateX;
        int y1instancevic = (int) ((double) heightSource / 2 + MainActivity.cut_early_win_y1 * ((double) heightSource / 2)) + realCalibrateY;
        int y2instancevic = (int) ((double) heightSource / 2 + MainActivity.cut_early_win_y2 * ((double) heightSource / 2)) + realCalibrateY;

        // если координаты вылезаю за границы скриншота - приводим их к границам
        x1instancevic = Math.max(x1instancevic, 0); x2instancevic = Math.min(x2instancevic, widthSource);
        y1instancevic = Math.max(y1instancevic, 0);  y2instancevic = Math.min(y2instancevic, heightSource);
        
        // создаем вырезанные и ресайзные картинки
        Bitmap croppingBitmap = Bitmap.createBitmap(sourceBitmap, x1, y1, x2 - x1, y2 - y1);
        Bitmap croppingBitmapTotalUs = Bitmap.createBitmap(sourceBitmap, x1totalus, y1totalus, x2totalus - x1totalus, y2totalus - y1totalus);
        Bitmap croppingBitmapTotalThey = Bitmap.createBitmap(sourceBitmap, x1totalthey, y1totalthey, x2totalthey - x1totalthey, y2totalthey - y1totalthey);
        Bitmap croppingBitmapTotalTime = Bitmap.createBitmap(sourceBitmap, x1totaltime, y1totaltime, x2totaltime - x1totaltime, y2totaltime - y1totaltime);
        Bitmap croppingBitmapInstanceVic = Bitmap.createBitmap(sourceBitmap, x1instancevic, y1instancevic, x2instancevic - x1instancevic, y2instancevic - y1instancevic);

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
        cuttedPictures.croppingBitmapEarlyWin = doProcess(croppingBitmapInstanceVic, Color.red(MainActivity.rgb_early_win), Color.green(MainActivity.rgb_early_win), Color.blue(MainActivity.rgb_early_win), MainActivity.rgb_early_win_threshold_minus, MainActivity.rgb_early_win_threshold_plus);
        cuttedPictures.croppingBitmapTotalTime = doProcess(croppingBitmapTotalTime, Color.red(MainActivity.rgb_total_time), Color.green(MainActivity.rgb_total_time), Color.blue(MainActivity.rgb_total_time), MainActivity.rgb_total_time_threshold_minus, MainActivity.rgb_total_time_threshold_plus);
        cuttedPictures.croppingBitmapTotalUs = doProcess(croppingBitmapTotalUs, Color.red(MainActivity.rgb_total_us), Color.green(MainActivity.rgb_total_us), Color.blue(MainActivity.rgb_total_us), MainActivity.rgb_total_us_threshold_minus, MainActivity.rgb_total_us_threshold_plus);
        cuttedPictures.croppingBitmapTotalThey = doProcess(croppingBitmapTotalThey, Color.red(MainActivity.rgb_total_they), Color.green(MainActivity.rgb_total_they), Color.blue(MainActivity.rgb_total_they), MainActivity.rgb_total_they_threshold_minus, MainActivity.rgb_total_they_threshold_plus);
        cuttedPictures.croppingBitmapPlusUs = doProcess(croppingBitmapTotalUs, Color.red(MainActivity.rgb_plus_us), Color.green(MainActivity.rgb_plus_us), Color.blue(MainActivity.rgb_plus_us), MainActivity.rgb_plus_us_threshold_minus, MainActivity.rgb_plus_us_threshold_plus);
        cuttedPictures.croppingBitmapPlusThey = doProcess(croppingBitmapTotalThey, Color.red(MainActivity.rgb_plus_they), Color.green(MainActivity.rgb_plus_they), Color.blue(MainActivity.rgb_plus_they), MainActivity.rgb_plus_they_threshold_minus, MainActivity.rgb_plus_they_threshold_plus);

        return cuttedPictures;


    }

    public static Bitmap doProcess(Bitmap originalBitmap, int color_R, int color_G, int color_B, int color_THM, int color_THP) {

        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Matrix matrix = new Matrix();           // матрица ресайза
        matrix.postScale(5.0f, 4.0f);   // будем ресайзать в 4 раза

        Bitmap tmpBitmap = Bitmap.createBitmap(width, height, originalBitmap.getConfig());

        Canvas c = new Canvas();
        c.setBitmap(tmpBitmap);

        int A, R, G, B;
        int pixel;

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = originalBitmap.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                if ((R > (color_R - color_THM) && R < (color_R + color_THP)) && (G > (color_G - color_THM) && G < (color_G + color_THP)) && (B > (color_B - color_THM) && B < (color_B + color_THP))) {
                    R=0;
                    G=0;
                    B=0;
                } else {
                    R=255;
                    G=255;
                    B=255;
                }

                tmpBitmap.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        return Bitmap.createBitmap(tmpBitmap, 0, 0, width, height, matrix, false);
        
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
