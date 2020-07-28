package com.svoemestodev.catscitycalc.citycalcclasses;


import android.graphics.Bitmap;
import android.util.Log;

import com.svoemestodev.catscitycalc.ssa.SSA_Area;
import com.svoemestodev.catscitycalc.ssa.SSA_Key;
import com.svoemestodev.catscitycalc.utils.CropPosition;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;
import com.svoemestodev.catscitycalc.utils.Utils;

public class CityCalcArea {

    private CityCalc cityCalc;  // родительский объект
    private SSA_Area ssaArea;
    private Bitmap bmpSrc = null;      // кропнутая картинка (исходник)
    private Bitmap bmpPrc = null;      // кропнутая картинка (обработанная для распознавания)
    private String ocrText = null;     // распознанный текст
    private String finText = null;     // распознанный финальный текст

    private static final String TAG = "CityCalcArea";

    public CityCalcArea() {
    }

    public CityCalcArea getClone(CityCalc parent) {

        CityCalcArea clone = new CityCalcArea();

        clone.cityCalc = parent;
        clone.ssaArea = this.ssaArea.getClone();
        clone.bmpSrc = this.bmpSrc;
        clone.bmpPrc = this.bmpPrc;
        clone.ocrText = this.ocrText;
        clone.finText = this.ocrText;

        return clone;

    }


    // конструктор "обычных" картинок
    public CityCalcArea(CityCalc cityCalc, SSA_Area ssaArea) { //} Area area, float x1, float x2, float y1, float y2, int[] colors, int [] ths, boolean needOcr, boolean needBW) {

        this.cityCalc = cityCalc;
        this.ssaArea = ssaArea;

    }


    public CityCalc getCityCalc() {
        return cityCalc;
    }

    public void setCityCalc(CityCalc cityCalc) {
        this.cityCalc = cityCalc;
    }

    public SSA_Area getSsaArea() {
        return ssaArea;
    }

    public void setSsaArea(SSA_Area ssaArea) {
        this.ssaArea = ssaArea;
    }

    public Bitmap getBmpSrc() {
        if (this.bmpSrc == null) this.bmpSrc = this.ssaArea.getAreaBitmap(cityCalc.getSsaScreenshot());
        return this.bmpSrc;
    }

    public void setBmpSrc(Bitmap bmpSrc) {
        this.bmpSrc = bmpSrc;
    }

    public Bitmap getBmpPrc() {
        if (this.bmpPrc == null) this.bmpPrc = this.ssaArea.getAreaBitmapRBT(this.getBmpSrc());
        return this.bmpPrc;
    }

    public void setBmpPrc(Bitmap bmpPrc) {
        this.bmpPrc = bmpPrc;
    }

    public String getOcrText() {
        if (this.ocrText == null) this.ocrText = ssaArea.getOCR(this.getBmpPrc());
        return this.ocrText;
    }

    public void setOcrText(String ocrText) {
        this.ocrText = ocrText;
    }

    public String getFinText() {
        if (this.finText == null) this.finText = this.getOcrText();
        return this.finText;
    }

    public void setFinText(String finText) {
        this.finText = finText;
    }
}
