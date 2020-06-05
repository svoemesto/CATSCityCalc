package com.svoemestodev.catscitycalc;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CityCalcArea {

    CityCalc cityCalc;  // родительский объект
    Area area;
    Bitmap bmpSrc;      // кропнутая картинка (исходник)
    CropPosition cropPosition = CropPosition.CENTER_VERTICAL;
    float x1;           // x1
    float x2;           // x2
    float y1;           // y1
    float y2;           // y2
    int [] colors;      // цвета
    int [] ths;         // пороги
    boolean needOcr;    // надо распознавать
    boolean needBW;    // надо BW
    boolean isGeneric = false;  // расчетная картинка
    Bitmap bmpPrc;      // кропнутая картинка (обработанная для распознавания)
    String ocrText = "";     // распознанный текст
    String finText = "";     // распознанный финальный текст

    private static final String TAG = "CityCalcArea";

    private CropPosition getCropPosition(Area area) {
        switch (area) {
            case CAR_INFO:
            case CAR_PICTURE:
            case CAR_BOX:
            case CAR_SLOT1:
            case CAR_SLOT2:
            case CAR_SLOT3:
            case CAR_HEALTH:
            case CAR_SHIELD:
            case CAR_STATE:
            case CAR_HEALBOX:
            case CAR_TIMEBOX:
                return CropPosition.LEFT;

            case BOX_INFO:
            case CITY:
            case TOTAL_TIME:
            case EARLY_WIN:
            case TEAM_NAME_OUR:
            case TEAM_NAME_ENEMY:
            case POINTS_AND_INCREASE_OUR:
            case POINTS_OUR:
            case POINTS_AND_INCREASE_ENEMY:
            case POINTS_ENEMY:
            case INCREASE_OUR:
            case INCREASE_ENEMY:
            case BLT_AREA:
            case BLT:
            case BLT_POINTS:
            case BLT_SLOTS:
            case BLT_PROGRESS:
            case BLC_AREA:
            case BLC:
            case BLC_POINTS:
            case BLC_SLOTS:
            case BLC_PROGRESS:
            case BLB_AREA:
            case BLB:
            case BLB_POINTS:
            case BLB_SLOTS:
            case BLB_PROGRESS:
            case BRT_AREA:
            case BRT:
            case BRT_POINTS:
            case BRT_SLOTS:
            case BRT_PROGRESS:
            case BRC_AREA:
            case BRC:
            case BRC_POINTS:
            case BRC_SLOTS:
            case BRC_PROGRESS:
            case BRB_AREA:
            case BRB:
            case BRB_POINTS:
            case BRB_SLOTS:
            case BRB_PROGRESS:
            default:
                return CropPosition.CENTER_VERTICAL;
                
        }
    }
    
    // конструктор "обычных" картинок
    public CityCalcArea(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int [] ths, boolean needOcr, boolean needBW) {

        this.cropPosition = getCropPosition(area);
        this.cityCalc = cityCalc;
        this.area = area;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.colors = colors;
        this.ths = ths;
        this.needOcr = needOcr;
        this.needBW = needBW;
        doCut();
        doOCR();

    }

    // конструктор "дженериков"
    public CityCalcArea(CityCalc cityCalc, Area area, int[] colors, int [] ths, boolean needOcr, boolean needBW) {

        if (area.equals(Area.CAR_INFO)) this.cropPosition = CropPosition.LEFT;
        this.isGeneric = true;
        this.cityCalc = cityCalc;
        this.area = area;
        this.colors = colors;
        this.ths = ths;
        this.needOcr = needOcr;
        this.needBW = needBW;
    }

    public void doOCR() {
        doOCR(0,0,1, true, 6.0f, 4.0f);
    }

    public void doOCR(int colorIndex, int thmIndex, int thpIndex, boolean doScale, float scaleX, float scaleY) {
        if (this.needOcr) {
            this.bmpPrc = this.needBW ? PictureProcessor.doBW(this.bmpSrc, this.colors[colorIndex], this.ths[thmIndex], this.ths[thpIndex]) : this.bmpSrc;
            this.bmpPrc = doScale ? PictureProcessor.doScale(this.bmpPrc, scaleX, scaleY) : this.bmpPrc;
            this.ocrText = PictureProcessor.doOCR(this.bmpPrc, this.cityCalc.context);

            if (area.equals(Area.TOTAL_TIME)) {
                this.finText = Utils.parseTime(this.ocrText);
            } else {
                this.finText = Utils.parseNumbers(this.ocrText);
            }
            String logMsgPref = "doOCR: ";
            Log.i(TAG, logMsgPref + "AREA = " + this.area + ", ocrText = " + this.ocrText + ", finText = " + this.finText);

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
                    int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

                    if (this.cropPosition == CropPosition.CENTER_VERTICAL) {
                        x1 = (int) ((double) widthSource / 2 + this.x1 * heightSource) + realCalibrateX;
                        x2 = (int) ((double) widthSource / 2 + this.x2 * heightSource) + realCalibrateX;
                        y1 = (int) ((double) heightSource / 2 + this.y1 * ((double) heightSource / 2)) + realCalibrateY;
                        y2 = (int) ((double) heightSource / 2 + this.y2 * ((double) heightSource / 2)) + realCalibrateY;
                    } else if (this.cropPosition == CropPosition.LEFT) {
                        x1 = (int) (heightSource * this.x1);
                        x2 = (int) (heightSource * this.x2);
                        y1 = (int) (heightSource + heightSource * this.y1);
                        y2 = (int) (heightSource + heightSource * this.y2);
                    }

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
