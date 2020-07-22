package com.svoemestodev.catscitycalc.ssa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.svoemestodev.catscitycalc.utils.CropPosition;

public class SSA_Screenshot {
    private Bitmap bitmap;
    private int centerX = 0;
    private int centerY = 0;

    
    /************************************
     * Конструктор на базе пути к файлу *
     * @param pathToFile - путь к файлу *
     * @param centerX - центр по X      *
     * @param centerY - центр по Y      *
     ************************************/
    public SSA_Screenshot(String pathToFile, int centerX, int centerY) {
        this.bitmap = BitmapFactory.decodeFile(pathToFile);
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /*******************************
     * Конструктор на базе битмапа *
     * @param bitmap - битмап      *
     *******************************/
    public SSA_Screenshot(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.centerX = 0;
        this.centerY = 0;
    }

    /*******************************
     * Конструктор на базе битмапа *
     * @param bitmap - битмап      *
     * @param centerX - центр по X *
     * @param centerY - центр по Y *
     *******************************/
    public SSA_Screenshot(Bitmap bitmap, int centerX, int centerY) {
        this.bitmap = bitmap;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /************************
     * Клонирование объекта *
     * @return клон         *
     ************************/
    public SSA_Screenshot getClone() {
        return new SSA_Screenshot(this.getBitmap(), this.getCenterX(), this.getCenterY());
    }

    /*****************************
     * Возвращает битмап области *
     * @param ssaArea            *
     * @return                   *
     *****************************/
//    public Bitmap getAreaBitmap(SSA_Area ssaArea) {
//        Bitmap areaBitmap = null;
//        if (this.isGood() && ssaArea != null) {
//            SSA_AbsoluteCoordinates ssaAC = new SSA_AbsoluteCoordinates(this, ssaArea);
//            int width = ssaAC.getAbsoluteX2() - ssaAC.getAbsoluteX1();
//            int height = ssaAC.getAbsoluteY2() - ssaAC.getAbsoluteY1();
//            if (width > 0 && height > 0 && (ssaAC.getAbsoluteX1() + width) <= this.bitmap.getWidth() && (ssaAC.getAbsoluteY1() + height) <= this.bitmap.getHeight()) {
//                areaBitmap = Bitmap.createBitmap(this.bitmap, ssaAC.getAbsoluteX1(), ssaAC.getAbsoluteY1(), width, height);
//            }
//        }
//        return areaBitmap;
//    }

    /***********************************************************
     * Правильный ли скриншот?                                 *
     * @return true если битмап не null и в ландшафтном режиме *
     ***********************************************************/
    public boolean isGood() {
        boolean isGood = false;
        if (this.bitmap != null) {
            if (this.bitmap.getWidth() >= this.bitmap.getHeight()) {
                isGood = true;
            }
        }
        return isGood;
    }

    /***********************
     *  GETTERS & SETTERS  *
     ***********************/


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }
}
