package com.svoemestodev.catscitycalc.citycalcclasses;


import android.graphics.Bitmap;
import android.util.Log;

import com.svoemestodev.catscitycalc.utils.CropPosition;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;
import com.svoemestodev.catscitycalc.utils.Utils;

public class CityCalcArea {

    private CityCalc cityCalc;  // родительский объект
    private Area area;
    private Bitmap bmpSrc;      // кропнутая картинка (исходник)
    private CropPosition cropPosition = CropPosition.CENTER_VERTICAL;
    private float x1;           // x1
    private float x2;           // x2
    private float y1;           // y1
    private float y2;           // y2
    private int [] colors;      // цвета
    private int [] ths;         // пороги
    private boolean needOcr;    // надо распознавать
    private boolean needBW;    // надо BW
    private boolean isGeneric = false;  // расчетная картинка
    private Bitmap bmpPrc;      // кропнутая картинка (обработанная для распознавания)
    private String ocrText = "";     // распознанный текст
    private String finText = "";     // распознанный финальный текст

    private static final String TAG = "CityCalcArea";

    private CropPosition getCropPosition(Area area) {
        switch (area) {
            case CAR_IN_CITY_INFO:
            case CAR_IN_CITY_PICTURE:
            case CAR_IN_CITY_BOX1:
            case CAR_IN_CITY_BOX2:
            case CAR_IN_CITY_STATEBOX1:
            case CAR_IN_CITY_STATEBOX2:
            case CAR_IN_CITY_STATEBOX3:
            case CAR_IN_CITY_SLOT1:
            case CAR_IN_CITY_SLOT2:
            case CAR_IN_CITY_SLOT3:
            case CAR_IN_CITY_HEALTH:
            case CAR_IN_CITY_SHIELD:
            case CAR_IN_CITY_STATE:
            case CAR_IN_CITY_HEALBOX:
            case CAR_IN_CITY_TIMEBOX1:
            case CAR_IN_CITY_TIMEBOX2:
            case BOX_BACK:
                return CropPosition.LEFT;

            case CAR_IN_GARAGE_SLOT1:
            case CAR_IN_GARAGE_SLOT2:
            case CAR_IN_GARAGE_SLOT3:
                return CropPosition.RIGHT;

            case CAR_IN_CITY_BUILDING:
            case CAR_IN_GARAGE_PICTURE:
            case CAR_IN_GARAGE_HEALTH:
            case CAR_IN_GARAGE_SHIELD:
            case BOX_INFO_CITY:
            case BOX_INFO_CAR:
            case BOX_INFO_GARAGE:
            case CITY:
            case TOTAL_TIME:
            case EARLY_WIN:
            case TEAM_NAME_OUR:
            case TEAM_NAME_ENEMY:
            case POINTS_AND_INCREASE_OUR:
            case POINTS_OUR:
            case POINTS_AND_INCREASE_ENEMY:
            case POINTS_ENEMY:
            case BLT_AREA:
            case BLT:
            case BLT_SLOTS:
            case BLT_PROGRESS:
            case BLC_AREA:
            case BLC:
            case BLC_SLOTS:
            case BLC_PROGRESS:
            case BLB_AREA:
            case BLB:
            case BLB_SLOTS:
            case BLB_PROGRESS:
            case BRT_AREA:
            case BRT:
            case BRT_SLOTS:
            case BRT_PROGRESS:
            case BRC_AREA:
            case BRC:
            case BRC_SLOTS:
            case BRC_PROGRESS:
            case BRB_AREA:
            case BRB:
            case BRB_SLOTS:
            case BRB_PROGRESS:
            default:
                return CropPosition.CENTER_VERTICAL;
                
        }
    }

    public CityCalcArea() {
    }

    public CityCalcArea getClone(CityCalc parent) {

        CityCalcArea clone = new CityCalcArea();

        clone.cityCalc = parent;
        clone.area = this.area;
        clone.bmpSrc = this.bmpSrc;
        clone.cropPosition = this.cropPosition;
        clone.x1 = this.x1;
        clone.x2 = this.x2;
        clone.y1 = this.y1;
        clone.y2 = this.y2;
        clone.colors = this.colors;
        clone.ths = this.ths;
        clone.needOcr = this.needOcr;
        clone.needBW = this.needBW;
        clone.isGeneric = this.isGeneric;
        clone.bmpPrc = this.bmpPrc;
        clone.ocrText = this.ocrText;
        clone.finText = this.finText;

        return clone;

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

        if (area.equals(Area.CAR_IN_CITY_INFO)) this.cropPosition = CropPosition.LEFT;
        this.isGeneric = true;
        this.cityCalc = cityCalc;
        this.area = area;
        this.colors = colors;
        this.ths = ths;
        this.needOcr = needOcr;
        this.needBW = needBW;
    }

    public void doOCR() {
        if (this.area.equals(Area.CAR_IN_GARAGE_HEALTH) && this.area.equals(Area.CAR_IN_GARAGE_SHIELD)) {
            if (PictureProcessor.frequencyPixelInBitmap(this.bmpSrc,this.colors[1],this.ths[0], this.ths[1]) > 0.001f) {
                doOCR(1,0,1, true, 6.0f, 4.0f);
            } else {
                doOCR(2,0,1, true, 6.0f, 4.0f);
            }
        } else {
            doOCR(0,0,1, true, 6.0f, 4.0f);
        }
    }

    public void doOCR(int colorIndex, int thmIndex, int thpIndex, boolean doScale, float scaleX, float scaleY) {

        if (this.needOcr && this.bmpSrc != null) {
            this.bmpPrc = this.needBW ? PictureProcessor.doBW(this.bmpSrc, this.colors[colorIndex], this.ths[thmIndex], this.ths[thpIndex]) : this.bmpSrc;
            this.bmpPrc = doScale ? PictureProcessor.doScale(this.bmpPrc, scaleX, scaleY) : this.bmpPrc;
            this.ocrText = PictureProcessor.doOCR(this.bmpPrc);

            if (area.equals(Area.TOTAL_TIME)) {
                this.finText = Utils.parseTime(this.ocrText);
            } else {
                this.finText = Utils.parseNumbers(this.ocrText);
            }
            String logMsgPref = "doOCR: ";
            Log.i(TAG, logMsgPref + "AREA = " + this.area + ", ocrText = " + this.ocrText + ", finText = " + this.finText);

        }
    }

    private Bitmap  cutSrc() {
        if (this.cityCalc != null) {
            if (this.cityCalc.getBmpScreenshot() != null) {
                if (!this.isGeneric) {
                    Bitmap sourceBitmap = this.cityCalc.getBmpScreenshot();
                    int widthSource = sourceBitmap.getWidth();      // ширина исходной картинки
                    int heightSource = sourceBitmap.getHeight();   // высота исходной картинки

                    int realCalibrateX = (widthSource / 2) > Math.abs(this.cityCalc.getCalibrateX()) ? this.cityCalc.getCalibrateX() : 0;
                    int realCalibrateY = (heightSource / 2) > Math.abs(this.cityCalc.getCalibrateY()) ? this.cityCalc.getCalibrateY() : 0;

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
                    } else if (this.cropPosition == CropPosition.RIGHT) {
                        x1 = widthSource + (int)(heightSource * this.x1);
                        x2 = widthSource + (int)(heightSource * this.x2);
                        y1 = (int) (heightSource + heightSource * this.y1);
                        y2 = (int) (heightSource + heightSource * this.y2);
                    }

                    // если координаты вылезаю за границы скриншота - приводим их к границам
                    x1 = Math.max(x1, 0); x2 = Math.min(x2, widthSource);
                    y1 = Math.max(y1, 0);  y2 = Math.min(y2, heightSource);

                    if (x1 > x2) {
                        int tmp = x1;
                        x1 = x2;
                        x2 = tmp;
                    }

                    if (y1 > y2) {
                        int tmp = y1;
                        y1 = y2;
                        y2 = tmp;
                    }

                    return Bitmap.createBitmap(sourceBitmap, x1, y1, x2 - x1, y2 - y1);
                }
            }
        }
        return null;
    }

    public void doCut() {
        this.bmpSrc = cutSrc();
    }

    public CityCalc getCityCalc() {
        return cityCalc;
    }

    public void setCityCalc(CityCalc cityCalc) {
        this.cityCalc = cityCalc;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Bitmap getBmpSrc() {
        return bmpSrc;
    }

    public void setBmpSrc(Bitmap bmpSrc) {
        this.bmpSrc = bmpSrc;
    }

    public CropPosition getCropPosition() {
        return cropPosition;
    }

    public void setCropPosition(CropPosition cropPosition) {
        this.cropPosition = cropPosition;
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public int[] getThs() {
        return ths;
    }

    public void setThs(int[] ths) {
        this.ths = ths;
    }

    public boolean isNeedOcr() {
        return needOcr;
    }

    public void setNeedOcr(boolean needOcr) {
        this.needOcr = needOcr;
    }

    public boolean isNeedBW() {
        return needBW;
    }

    public void setNeedBW(boolean needBW) {
        this.needBW = needBW;
    }

    public boolean isGeneric() {
        return isGeneric;
    }

    public void setGeneric(boolean generic) {
        isGeneric = generic;
    }

    public Bitmap getBmpPrc() {
        return bmpPrc;
    }

    public void setBmpPrc(Bitmap bmpPrc) {
        this.bmpPrc = bmpPrc;
    }

    public String getOcrText() {
        return ocrText;
    }

    public void setOcrText(String ocrText) {
        this.ocrText = ocrText;
    }

    public String getFinText() {
        return finText;
    }

    public void setFinText(String finText) {
        this.finText = finText;
    }
}
