package com.svoemestodev.catscitycalc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseArray;
import android.graphics.Matrix;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

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
    public static Bitmap[] splitBitmap(Bitmap sourceBitmap, int color, int thm, int thp, float maxSensitivity, float minSensitivity, PictureProcessorDirection direction) {
        Bitmap[] arrBitmap = null;

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

                Bitmap firstBitmap = Bitmap.createBitmap(firstPicWidth, firstPicHeight, sourceBitmap.getConfig());
                Bitmap secondBitmap = Bitmap.createBitmap(secondPicWidth, secondPicHeight, sourceBitmap.getConfig());

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

//                        pixel = horizontalMove ? sourceBitmap.getPixel(x + firstPicStart, y) : sourceBitmap.getPixel(x, y + firstPicStart);
//                        firstBitmap.setPixel(x, y, pixel);
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
                        
//                        pixel = horizontalMove ? sourceBitmap.getPixel(x + secondPicStart, y) : sourceBitmap.getPixel(x, y + secondPicStart);
//                        secondBitmap.setPixel(x, y, pixel);
                    }
                }

                arrBitmap = new Bitmap[] {firstBitmap, secondBitmap};

            }

        }


        return arrBitmap;
    }


    public static String doOCR(Bitmap sourceBitmap, Context context) {
        String result = ""; // результат
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build(); // создаем текстрекогнайзер
        if (textRecognizer.isOperational()) {   // если текстрекогнайзер может что-то распознать
            Frame frame = new Frame.Builder().setBitmap(sourceBitmap).build();    // создаем фрейм на основе переданного битмапа
            SparseArray<TextBlock> items = textRecognizer.detect(frame);    // передаем фрейм в текстрекогнайзер, на выходе - массив текстовых блоков
            for (int i = 0; i < items.size(); ++i) {                        // проходимся по массиву текстовых блоков
                result = result + items.valueAt(i).getValue() + " ";        // добавляем к результату значение текста в очередном блоке, разделяем пробелами
            }
        }
        return result;  // возвращаем результат. Если не было ни одного блока или они все были пустыми - результатом будет пустая строка
    }

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

            Matrix matrix = new Matrix();
            matrix.setScale(5.0f, 4.0f);

            Bitmap scaledBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, width, height, matrix, true);

            return scaledBitmap;

        }
        return sourceBitmap;
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

}
