package com.svoemestodev.catscitycalc.ssa;

import android.graphics.Bitmap;

public class SSA_AbsoluteCoordinates {
    private int absoluteX1;
    private int absoluteX2;
    private int absoluteY1;
    private int absoluteY2;

    /**********************************************************************
     * Конструктор создает объект класса абсолютных координат             *
     * для переданного скриншота и области с относительными координатами  *
     * @param ssaScreenshot - скриншот                                    *
     * @param ssaArea - область                                           *
     **********************************************************************/

    public SSA_AbsoluteCoordinates(SSA_Screenshot ssaScreenshot, SSA_Area ssaArea) {

        int widthSource = ssaScreenshot.getBitmap().getWidth();      // ширина исходной картинки
        int heightSource = ssaScreenshot.getBitmap().getHeight();   // высота исходной картинки

        int realCenterX = (widthSource / 2) > Math.abs(ssaScreenshot.getCenterX()) ? ssaScreenshot.getCenterX() : 0;
        int realCenterY = (heightSource / 2) > Math.abs(ssaScreenshot.getCenterY()) ? ssaScreenshot.getCenterY() : 0;

        this.absoluteY1 = (int) ((double) heightSource / 2 + ssaArea.getrY1() * ((double) heightSource / 2)) + realCenterY;
        this.absoluteY2 = (int) ((double) heightSource / 2 + ssaArea.getrY2() * ((double) heightSource / 2)) + realCenterY;

        if (ssaArea.getSnap() == 0) { // привязка - по центру
            this.absoluteX1 = (int) ((double) widthSource / 2 + ssaArea.getrX1() * heightSource) + realCenterX;
            this.absoluteX2 = (int) ((double) widthSource / 2 + ssaArea.getrX2() * heightSource) + realCenterX;
//            this.absoluteY1 = (int) ((double) heightSource / 2 + ssaArea.getrY1() * ((double) heightSource / 2)) + realCenterY;
//            this.absoluteY2 = (int) ((double) heightSource / 2 + ssaArea.getrY2() * ((double) heightSource / 2)) + realCenterY;
        } else if (ssaArea.getSnap() == -1) { // привязка - слева
            this.absoluteX1 = (int) (heightSource * ssaArea.getrX1());
            this.absoluteX2 = (int) (heightSource * ssaArea.getrX2());
//            this.absoluteY1 = (int) (heightSource + heightSource * ssaArea.getrY1());
//            this.absoluteY2 = (int) (heightSource + heightSource * ssaArea.getrY2());
        } else if (ssaArea.getSnap() == 1) { // привязка - справа
            this.absoluteX1 = widthSource + (int)(heightSource * ssaArea.getrX1());
            this.absoluteX2 = widthSource + (int)(heightSource * ssaArea.getrX2());
//            this.absoluteY1 = (int) (heightSource + heightSource * ssaArea.getrY1());
//            this.absoluteY2 = (int) (heightSource + heightSource * ssaArea.getrY2());
        }

        // если координаты вылезают за границы скриншота - приводим их к границам
        this.absoluteX1 = Math.max(this.absoluteX1, 0); this.absoluteX2 = Math.min(this.absoluteX2, widthSource);
        this.absoluteY1 = Math.max(this.absoluteY1, 0);  this.absoluteY2 = Math.min(this.absoluteY2, heightSource);

        if (this.absoluteX1 > this.absoluteX2) {
            int tmp = this.absoluteX1;
            this.absoluteX1 = this.absoluteX2;
            this.absoluteX2 = tmp;
        }

        if (this.absoluteY1 > this.absoluteY2) {
            int tmp = this.absoluteY1;
            this.absoluteY1 = this.absoluteY2;
            this.absoluteY2 = tmp;
        }

    }

    /***********************
     *  GETTERS & SETTERS  *
     ***********************/

    public int getAbsoluteX1() {
        return absoluteX1;
    }

    public void setAbsoluteX1(int absoluteX1) {
        this.absoluteX1 = absoluteX1;
    }

    public int getAbsoluteX2() {
        return absoluteX2;
    }

    public void setAbsoluteX2(int absoluteX2) {
        this.absoluteX2 = absoluteX2;
    }

    public int getAbsoluteY1() {
        return absoluteY1;
    }

    public void setAbsoluteY1(int absoluteY1) {
        this.absoluteY1 = absoluteY1;
    }

    public int getAbsoluteY2() {
        return absoluteY2;
    }

    public void setAbsoluteY2(int absoluteY2) {
        this.absoluteY2 = absoluteY2;
    }
}
