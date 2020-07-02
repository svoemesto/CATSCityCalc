package com.svoemestodev.catscitycalc.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseArray;
import android.graphics.Matrix;

//import com.google.android.gms.vision.Frame;
//import com.google.android.gms.vision.text.TextBlock;
//import com.google.android.gms.vision.text.TextRecognizer;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.svoemestodev.catscitycalc.GlobalApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PictureProcessor extends Activity {
    /**
     * Разрезает изображение на 2, анализируя цвета
     *
     * @param sourceBitmap      - исходное изображение
     * @param color             - цвет
     * @param thm               - порог в минус
     * @param thp               - порог в плюс
     * @param maxSensitivity    - максимальная чувствительность
     * @param minSensitivity    - минимальная чувствительность
     * @param direction         - направление при анализе
     * @return                  - массив из 2-х разрезанных изображений
     */
    public static Bitmap[] splitBitmap(Bitmap sourceBitmap, int color, int thm, int thp, float maxSensitivity, float minSensitivity, PictureProcessorDirection direction, boolean colorIsBackground) {
        Bitmap[] arrBitmap = null;
        Bitmap firstBitmap, secondBitmap;
        if (sourceBitmap != null) {

            int sourceWidth = sourceBitmap.getWidth();      // ширина исходной картинки
            int sourceHeight = sourceBitmap.getHeight();    // высота исходной картинки

            boolean horizontalMove = direction.equals(PictureProcessorDirection.FROM_LEFT_TO_RIGHT) || direction.equals(PictureProcessorDirection.FROM_RIGHT_TO_LEFT);  // движемся по горизонтали или по вертикали?

            int firstDirectionStart, firstDirectionEnd, firstDirectionIncrement;
            int secondDirectionStart, secondDirectionEnd, secondDirectionIncrement;

            if (horizontalMove) {
                firstDirectionStart = 0;
                firstDirectionEnd = sourceWidth;
                firstDirectionIncrement = 1;
                secondDirectionStart = 0;
                secondDirectionEnd = sourceHeight;
                secondDirectionIncrement = 1;
            } else {
                firstDirectionStart = 0;
                firstDirectionEnd = sourceHeight;
                firstDirectionIncrement = 1;
                secondDirectionStart = 0;
                secondDirectionEnd = sourceWidth;
                secondDirectionIncrement = 1;
            }

            boolean findStartSplit = false, findEndSplit = false;
            int startSplit = 0, endSplit = 0;
            int pixel = 0, x, y;

            for (int i = firstDirectionStart; i < firstDirectionEnd; i += firstDirectionIncrement) {
                int countTruePixelsInLine = 0;
                for (int j = secondDirectionStart; j < secondDirectionEnd ; j += secondDirectionIncrement) {
                    x = horizontalMove ? i : j;
                    y = horizontalMove ? j : i;

                    if (direction.equals(PictureProcessorDirection.FROM_LEFT_TO_RIGHT) || direction.equals(PictureProcessorDirection.FROM_TOP_TO_BOTTOM)) {
                        pixel = sourceBitmap.getPixel(x, y);
                    } else if (direction.equals(PictureProcessorDirection.FROM_RIGHT_TO_LEFT)) {
                        pixel = sourceBitmap.getPixel(firstDirectionEnd - x - 1, y);
                    } else {
                        pixel = sourceBitmap.getPixel(x, firstDirectionEnd - y - 1);
                    }

//                    pixel = sourceBitmap.getPixel(x, y);
                    countTruePixelsInLine += isPixelTrue(pixel, color, thm, thp) ? 1 : 0;
                }
                float filling = (float)countTruePixelsInLine / secondDirectionEnd;

                if (colorIsBackground) {
                    if (!findStartSplit) { // если начало кропа еще не найдено
                        if (filling <= (1 - maxSensitivity)) { // если текущая линия подходит
                            findStartSplit = true;
                            startSplit = i;
                        }
                    } else if (!findEndSplit){ // если начало кропа уже найдено, а конец еще нет
                        if (filling >= (1 - minSensitivity)) { // если текущая линия подходит
                            findEndSplit = true;
                            endSplit = i;
                        }
                    }
                } else {
                    if (!findStartSplit) { // если начало кропа еще не найдено
                        if (filling > maxSensitivity) { // если текущая линия подходит
                            findStartSplit = true;
                            startSplit = i;
                        }
                    } else if (!findEndSplit){ // если начало кропа уже найдено, а конец еще нет
                        if (filling <= minSensitivity) { // если текущая линия подходит
                            findEndSplit = true;
                            endSplit = i;
                        }
                    }
                }


                if (findStartSplit && findEndSplit) break; // если найдены начало и конец - выходим из цикла
            }

            if (findStartSplit && findEndSplit) { // если найдены начало и конец

                int firstPicStart = startSplit;
                int firstPicEnd = endSplit - firstDirectionIncrement;
                int secondPicStart = endSplit;
                int secondPicEnd = firstDirectionEnd;
                if (firstDirectionIncrement < 0) {
                    int tmp = firstPicStart;
                    firstPicStart = firstPicEnd;
                    firstPicEnd = tmp;
                    tmp = secondPicStart;
                    secondPicStart = secondPicEnd;
                    secondPicEnd = tmp;
                }

                int firstPicWidth = horizontalMove ? firstPicEnd - firstPicStart : sourceWidth;
                int firstPicHeight = horizontalMove ? sourceHeight : firstPicEnd - firstPicStart;
                int secondPicWidth = horizontalMove ? secondPicEnd - secondPicStart : sourceWidth;
                int secondPicHeight = horizontalMove ? sourceHeight : secondPicEnd - secondPicStart;

                if (firstPicWidth > 0 && firstPicHeight > 0 && secondPicWidth > 0 && secondPicHeight > 0) {

                    firstBitmap = Bitmap.createBitmap(firstPicWidth, firstPicHeight, sourceBitmap.getConfig());
                    secondBitmap = Bitmap.createBitmap(secondPicWidth, secondPicHeight, sourceBitmap.getConfig());

                    for (x = 0; x < firstPicWidth; x++) {
                        for (y = 0; y < firstPicHeight; y++) {

                            if (direction.equals(PictureProcessorDirection.FROM_LEFT_TO_RIGHT) || direction.equals(PictureProcessorDirection.FROM_TOP_TO_BOTTOM)) {
                                pixel = horizontalMove ? sourceBitmap.getPixel(x + firstPicStart, y) : sourceBitmap.getPixel(x, y + firstPicStart);
                                firstBitmap.setPixel(x, y, pixel);
                            } else if (direction.equals(PictureProcessorDirection.FROM_RIGHT_TO_LEFT)) {
                                pixel = sourceBitmap.getPixel((firstDirectionEnd - 1) - (x + firstPicStart), y);
                                firstBitmap.setPixel(firstPicWidth - x - 1, y, pixel);
                            } else {
                                pixel = sourceBitmap.getPixel(x, (firstDirectionEnd - 1) - (y + firstPicStart));
                                firstBitmap.setPixel(x, firstPicHeight - y - 1, pixel);
                            }

                        }
                    }

                    for (x = 0; x < secondPicWidth; x++) {
                        for (y = 0; y < secondPicHeight; y++) {

                            if (direction.equals(PictureProcessorDirection.FROM_LEFT_TO_RIGHT) || direction.equals(PictureProcessorDirection.FROM_TOP_TO_BOTTOM)) {
                                pixel = horizontalMove ? sourceBitmap.getPixel(x + secondPicStart, y) : sourceBitmap.getPixel(x, y + secondPicStart);
                                secondBitmap.setPixel(x, y, pixel);
                            } else if (direction.equals(PictureProcessorDirection.FROM_RIGHT_TO_LEFT)) {
                                pixel = sourceBitmap.getPixel((firstDirectionEnd - 1) - (x + secondPicStart), y);
                                secondBitmap.setPixel(secondPicWidth - x - 1, y, pixel);
                            } else {
                                pixel = sourceBitmap.getPixel(x, (firstDirectionEnd - 1) - (y + secondPicStart));
                                secondBitmap.setPixel(x, secondPicHeight - y - 1, pixel);
                            }
                        }
                    }

                } else {
                    firstBitmap = null;
                    secondBitmap = null;
                }


            } else {
                firstBitmap = Bitmap.createBitmap(sourceWidth, sourceHeight, sourceBitmap.getConfig());
                for (x = 0; x < sourceWidth; x++) {
                    for (y = 0; y < sourceHeight; y++) {
                        firstBitmap.setPixel(x, y, 0xFFFFFFFF);
                    }
                }
                secondBitmap = sourceBitmap;
            }

            arrBitmap = new Bitmap[] {firstBitmap, secondBitmap};

        }


        return arrBitmap;
    }


    public static String doOCR(Bitmap sourceBitmap) {

        String result = ""; // результат
        Context context = GlobalApplication.getAppContext();
        if (sourceBitmap!= null) {
            TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build(); // создаем текстрекогнайзер
            if (textRecognizer.isOperational()) {   // если текстрекогнайзер может что-то распознать
                Frame frame = new Frame.Builder().setBitmap(sourceBitmap).build();    // создаем фрейм на основе переданного битмапа
                SparseArray<TextBlock> items = textRecognizer.detect(frame);    // передаем фрейм в текстрекогнайзер, на выходе - массив текстовых блоков
                for (int i = 0; i < items.size(); ++i) {                        // проходимся по массиву текстовых блоков
                    result = result + items.valueAt(i).getValue() + " ";        // добавляем к результату значение текста в очередном блоке, разделяем пробелами
                }
            }
        }
        return result;  // возвращаем результат. Если не было ни одного блока или они все были пустыми - результатом будет пустая строка

    }

//    public static String doOCR(Bitmap sourceBitmap, Context context) {
//        String result = ""; // результат
//        if (sourceBitmap!= null) {
//            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.pref_preferences_file), MODE_PRIVATE);
//            String language = "rus";
//            language = sharedPreferences.getString(context.getResources().getString(R.string.pref_language_screenshot),sharedPreferences.getString(context.getResources().getString(R.string.pref_def_language_screenshot),"eng"));
////            language = Locale.getDefault().getISO3Language().toLowerCase();
//            TessOCR mTessOCR = new TessOCR(context, language);
//            result = mTessOCR.getOCRResult(sourceBitmap);
//        }
//        if (result == null) result = "";
//        return result;  // возвращаем результат. Если не было ни одного блока или они все были пустыми - результатом будет пустая строка
//    }

    public static Bitmap doBW(Bitmap sourceBitmap, int color, int thm, int thp) {

        if (sourceBitmap != null) {
            int width = sourceBitmap.getWidth();
            int height = sourceBitmap.getHeight();
            Bitmap tmpBitmap = Bitmap.createBitmap(width, height, sourceBitmap.getConfig());
            int pixel;

            for(int x = 0; x < width; ++x) {
                for(int y = 0; y < height; ++y) {
                    pixel = sourceBitmap.getPixel(x, y);
                    int bw = isPixelTrue(pixel, color, thm, thp) ? 0xFF000000 : 0xFFFFFFFF;
                    tmpBitmap.setPixel(x, y, bw);
                }
            }

            return tmpBitmap;

        }
        return sourceBitmap;
    }

    public static Bitmap doScale(Bitmap sourceBitmap, float scaleX, float scaleY) {
        int width = sourceBitmap.getWidth();
        int height = sourceBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);
        return Bitmap.createBitmap(sourceBitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap generateBitmapByOnePixel(int color, int width, int height) {

        int[] pixels = new int[width*height];
        for (int pixel: pixels) {
            pixel = color;
        }
        Bitmap bitmap = Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
        return bitmap;

    }

    private static boolean isPixelTrue(int pixel, int color, int thm, int thp) {

        int pixR = Color.red(pixel);
        int pixG = Color.green(pixel);
        int pixB = Color.blue(pixel);
        int colR = Color.red(color);
        int colG = Color.green(color);
        int colB = Color.blue(color);

        return ((pixR >= (colR - thm) && pixR <= (colR + thp)) && (pixG >= (colG - thm) && pixG <= (colG + thp)) && (pixB >= (colB - thm) && pixB <= (colB + thp)));

    }

    public static float frequencyPixelInBitmap(Bitmap picture, int color, int thm, int thp) {

        if (picture != null) {
            int width = picture.getWidth();      // ширина исходной картинки
            int height = picture.getHeight();    // высота исходной картинки
            int countTruePixels = 0;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = picture.getPixel(x , y);
                    countTruePixels += isPixelTrue(pixel, color, thm, thp) ? 1 : 0;
                }
            }
            return (float)countTruePixels / (width * height);
        } else {
            return 0;
        }

    }

    public static long countPixelInBitmap(Bitmap picture, int color, int thm, int thp) {

        if (picture != null) {
            int width = picture.getWidth();      // ширина исходной картинки
            int height = picture.getHeight();    // высота исходной картинки
            long countTruePixels = 0;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = picture.getPixel(x , y);
                    countTruePixels += isPixelTrue(pixel, color, thm, thp) ? 1 : 0;
                }
            }
            return countTruePixels;
        } else {
            return 0;
        }

    }

    public static long[] countPixelInBitmap(Bitmap picture, int[] colors, int thm, int thp) {

        long[] countTruePixels = new long[colors.length];
        if (picture != null) {
            int width = picture.getWidth();      // ширина исходной картинки
            int height = picture.getHeight();    // высота исходной картинки
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = picture.getPixel(x , y);
                    for (int i = 0; i < colors.length; i++) {
                        countTruePixels[i] += isPixelTrue(pixel, colors[i], thm, thp) ? 1 : 0;
                    }
                }
            }
        }
        return countTruePixels;
    }

    public static Bitmap getProgressBitmap(int width, int height, int[] colors, int[] counts) {

        int colorBorder = 0xFFAAAAAA;
        int colorSeparator = 0xFF000000;
        int cb1 = 0;
        int cb2 = 0;

        int countBoxes = 0;
        for (int cnt: counts) {
            countBoxes += cnt;
        }
        if (countBoxes > 0) {
            int boxWidth = width / countBoxes;
            int[] pixels = new int[width*height];
            int x = 0, l = 0;
            int globalSegment = 0;
            for (int colorSegment = 0; colorSegment < colors.length; colorSegment++) {
                int segments = counts[colorSegment];
                for (int segment = 0; segment < segments; segment++) {
                    globalSegment++;
                    if (globalSegment == countBoxes / 2 + 1) {
                        cb1 = colorSeparator;
                        cb2 = colorSeparator;
                    } else if (globalSegment == (countBoxes / 2 + 1)-1) {
                        cb1 = colorBorder;
                        cb2 = colorSeparator;
                    } else if (globalSegment == (countBoxes / 2 + 1)+1) {
                        cb1 = colorSeparator;
                        cb2 = colorBorder;
                    } else {
                        cb1 = colorBorder;
                        cb2 = colorBorder;
                    }

                    l=1;
                    for (int j = 0; j < height; j++) {
                        int xFrom = width*j + x;
                        int xTo = xFrom + l;
                        Arrays.fill(pixels, xFrom, xTo, cb1);
                    }
                    x += l;

                    l=boxWidth-2;
                    for (int j = 0; j < height; j++) {
                        int xFrom = width*j + x;
                        int xTo = xFrom + l;
                        Arrays.fill(pixels, xFrom, xTo, colors[colorSegment]);
                    }
                    x += l;

                    l=1;
                    for (int j = 0; j < height; j++) {
                        int xFrom = width*j + x;
                        int xTo = xFrom + l;
                        Arrays.fill(pixels, xFrom, xTo, cb2);
                    }
                    x += l;

                }
//                l = boxWidth * counts[colorSegment];
//                if (l > 0) {
//                    for (int j = 0; j < height; j++) {
//                        int xFrom = width*j + x;
//                        int xTo = xFrom + l;
//                        Arrays.fill(pixels, xFrom, xTo, colors[colorSegment]);
//                    }
//                    x += l;
//                }
            }
            Bitmap bitmap = Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
            return bitmap;
        } else {
            return null;
        }


    }

    public static Bitmap replaceColor(Bitmap sourceBitmap,  int colorFrom, int colorTo, int thm, int thp) {
        if (sourceBitmap != null) {
            int width = sourceBitmap.getWidth();      // ширина исходной картинки
            int height = sourceBitmap.getHeight();    // высота исходной картинки
            Bitmap resultedBitmap = Bitmap.createBitmap(width, height, sourceBitmap.getConfig());
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixelSource = sourceBitmap.getPixel(x , y);
                    int pixelResult = isPixelTrue(pixelSource, colorFrom, thm,thp) ? getNewColorToReplace(pixelSource, colorFrom, colorTo) : pixelSource;
                    resultedBitmap.setPixel(x, y, pixelResult);
                }
            }
            return resultedBitmap;
        }
        return null;
    }

    private static int getNewColorToReplace(int colorPixel, int colorFrom, int colorTo) {

        int pixA = Color.alpha(colorPixel);
        int pixR = Color.red(colorPixel);
        int pixG = Color.green(colorPixel);
        int pixB = Color.blue(colorPixel);

        int fromR = Color.red(colorFrom);
        int fromG = Color.green(colorFrom);
        int fromB = Color.blue(colorFrom);

        int toR = Color.red(colorFrom);
        int toG = Color.green(colorFrom);
        int toB = Color.blue(colorFrom);

        int resultR = toR - (pixR - fromR);
        int resultG = toG - (pixG - fromG);
        int resultB = toB - (pixB - fromB);

        return Color.argb(pixA, resultR, resultG, resultB);

    }

    public static List<ColorFrequency> getFrequencyMap(Bitmap bitmap) {
        int thm = 13, thp = 13;
        return getFrequencyMap(bitmap, thm, thp);
    }

    public static List<ColorFrequency> getFrequencyMap(Bitmap bitmap, int thm, int thp) {
        List<ColorFrequency> colorFrequencyList = new ArrayList<>();
        if (bitmap != null) {
            int width = bitmap.getWidth();      // ширина исходной картинки
            int height = bitmap.getHeight();    // высота исходной картинки
            int countPixels = width * height;

            Map<Integer, Integer> mapPixels = new HashMap<>();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = bitmap.getPixel(x , y);
                    if (mapPixels.containsKey(pixel)) {
                        mapPixels.put(pixel, mapPixels.get(pixel)+1);
                    } else {
                        mapPixels.put(pixel, 1);
                    }
                }
            }

            for (Map.Entry<Integer, Integer> pair1 : mapPixels.entrySet()) {
                for (Map.Entry<Integer, Integer> pair2 : mapPixels.entrySet()) {
                    if (!pair1.equals(pair2) && pair1.getValue() !=0 && pair2.getValue() !=0) {
                        if (isPixelTrue(pair1.getKey(), pair2.getKey(), thm, thp)) {
                            pair1.setValue(pair1.getValue() + pair2.getValue());
                            pair2.setValue(0);
                        }
                    }
                }
            }

            Map<Float, Integer> mapFrequency = new TreeMap<>();
            for (Map.Entry<Integer, Integer> pair : mapPixels.entrySet()) {
                if (pair.getValue() !=0) {
                    mapFrequency.put(-(float)pair.getValue() / countPixels,  pair.getKey());
                }
            }

            for (Map.Entry<Float, Integer> pair : mapFrequency.entrySet()) {
                colorFrequencyList.add(new ColorFrequency(-pair.getKey(), pair.getValue()));
            }

        }
        return  colorFrequencyList;
    }

    public static Bitmap makeTransparent(Bitmap bit, int transparentColor) {
        int width =  bit.getWidth();
        int height = bit.getHeight();

        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int [] allpixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
        bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());
        myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

        for(int i =0; i<myBitmap.getHeight()*myBitmap.getWidth();i++){

            if(isPixelTrue(allpixels[i],transparentColor, 20, 20))
                allpixels[i] = Color.alpha(Color.TRANSPARENT);
        }

        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        return myBitmap;
    }

}
