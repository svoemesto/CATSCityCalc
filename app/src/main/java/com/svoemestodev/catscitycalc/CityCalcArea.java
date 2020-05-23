package com.svoemestodev.catscitycalc;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class CityCalcArea {

    CityCalc cityCalc;  // родительский объект
    Area area;
    Bitmap bmpSrc;      // кропнутая картинка (исходник)
    float x1;           // x1
    float x2;           // x2
    float y1;           // y1
    float y2;           // y2
    int [] colors;      // цвета
    int [] ths;         // пороги
    boolean needOcr;    // надо распознавать
    boolean isGeneric = false;  // расчетная картинка
    Bitmap bmpPrc;      // кропнутая картинка (обработанная для распознавания)
    String ocrText = "";     // распознанный текст
    String finText = "";     // распознанный финальный текст

    // конструктор "обычных" картинок
    public CityCalcArea(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int [] ths, boolean needOcr) {

        this.cityCalc = cityCalc;
        this.area = area;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.colors = colors;
        this.ths = ths;
        this.needOcr = needOcr;
        doCut();
        doOCR();

    }

    // конструктор "дженериков"
    public CityCalcArea(CityCalc cityCalc, Area area, int[] colors, int [] ths, boolean needOcr) {
        this.isGeneric = true;
        this.cityCalc = cityCalc;
        this.area = area;
        this.colors = colors;
        this.ths = ths;
        this.needOcr = needOcr;
    }

    public void doOCR() {
        doOCR(0,0,1, true, true, 5.0f, 4.0f);
    }

    public void doOCR(int colorIndex, int thmIndex, int thpIndex, boolean doBW, boolean doScale, float scaleX, float scaleY) {
        if (this.needOcr) {
            this.bmpPrc = doBW ? PictureProcessor.doBW(this.bmpSrc, this.colors[colorIndex], this.ths[thmIndex], this.ths[thpIndex]) : this.bmpSrc;
            this.bmpPrc = doScale ? PictureProcessor.doScale(this.bmpPrc, scaleX, scaleY) : this.bmpPrc;
            this.ocrText = PictureProcessor.doOCR(this.bmpPrc, this.cityCalc.context);

            if (area.equals(Area.TOTAL_TIME)) {
                this.finText = Utils.parseTime(this.ocrText);
            } else {
                this.finText = Utils.parseNumbers(this.ocrText);
            }
        }
    }



    private Bitmap cutSrc() {
        if (this.cityCalc != null) {
            if (this.cityCalc.bmpScreenshot != null) {
                if (!this.isGeneric) {
                    Bitmap sourceBitmap = this.cityCalc.bmpScreenshot;
                    int widthSource = sourceBitmap.getWidth();      // ширина исходной картинки
                    int heightSource = sourceBitmap.getHeight();   // высота исходной картинки

                    int realCalibrateX = (widthSource / 2) > Math.abs(this.cityCalc.calibrateX) ? this.cityCalc.calibrateX : 0;
                    int realCalibrateY = (heightSource / 2) > Math.abs(this.cityCalc.calibrateY) ? this.cityCalc.calibrateY : 0;

                    // координаты для вырезания картинки информации об игре
                    int x1 = (int) ((double) widthSource / 2 + this.x1 * heightSource) + realCalibrateX;
                    int x2 = (int) ((double) widthSource / 2 + this.x2 * heightSource) + realCalibrateX;
                    int y1 = (int) ((double) heightSource / 2 + this.y1 * ((double) heightSource / 2)) + realCalibrateY;
                    int y2 = (int) ((double) heightSource / 2 + this.y2 * ((double) heightSource / 2)) + realCalibrateY;

                    // если координаты вылезаю за границы скриншота - приводим их к границам
                    x1 = Math.max(x1, 0); x2 = Math.min(x2, widthSource);
                    y1 = Math.max(y1, 0);  y2 = Math.min(y2, heightSource);

                    return Bitmap.createBitmap(sourceBitmap, x1, y1, x2 - x1, y2 - y1);
                }
            }
        }
        return null;
    }

    public void doCut() {
        this.bmpSrc = cutSrc();
    }


}
