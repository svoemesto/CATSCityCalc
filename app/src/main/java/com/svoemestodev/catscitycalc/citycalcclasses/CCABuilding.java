package com.svoemestodev.catscitycalc.citycalcclasses;

import android.graphics.Bitmap;

import com.svoemestodev.catscitycalc.utils.PictureProcessor;

public class CCABuilding extends CityCalcArea {
    private int slots;
    private int slots_our;
    private int slots_enemy;
    private int slots_empty;
    private int our_points;
    private int enemy_points;
    private boolean buildingIsOur;
    private boolean buildingIsEnemy;
    private boolean buildingIsEmpty;
    private boolean mayX2;
    private boolean isX2;
    private boolean isPresent;

    public CCABuilding() {
    }


    public CCABuilding(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOCR, boolean needBW) {
        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOCR, needBW);
        this.isPresent = PictureProcessor.frequencyPixelInBitmap(this.getBmpSrc(), 0xFFFFFFFF, 10, 10) > 0.2f;
        if (this.isPresent) {
            this.mayX2 = PictureProcessor.frequencyPixelInBitmap(this.getBmpSrc(), this.getColors()[3],10, 10) > 0.02f || PictureProcessor.frequencyPixelInBitmap(this.getBmpSrc(), this.getColors()[4],10, 10) > 0.01f;
            this.isX2 = PictureProcessor.frequencyPixelInBitmap(this.getBmpSrc(), this.getColors()[4],10, 10) > 0.01f;
            this.buildingIsOur = PictureProcessor.frequencyPixelInBitmap(this.getBmpSrc(), this.getColors()[0],10, 10) > 0.01f;
            this.buildingIsEnemy = PictureProcessor.frequencyPixelInBitmap(this.getBmpSrc(), this.getColors()[1],10, 10) > 0.01f;
            this.buildingIsEmpty = !this.buildingIsOur && !this.buildingIsEnemy;
            this.setNeedOcr(needOCR);
            this.setNeedBW(needBW);
            if (this.buildingIsOur) {
                this.doOCR(0, 0, 1, true, 6.0f, 4.0f);
            } else if (this.buildingIsEnemy) {
                this.doOCR(1, 2, 3, true, 6.0f, 4.0f);
            } else {
                this.doOCR(2, 4, 5, true, 6.0f, 4.0f);
            }
            this.setFinText(this.getOcrText().trim());
        }
    }

    public CCABuilding getClone(CityCalc parent) {

        CCABuilding clone = new CCABuilding();

        clone.setCityCalc(parent);
        clone.setArea(this.getArea());
        clone.setBmpSrc(this.getBmpSrc());
        clone.setCropPosition(this.getCropPosition());
        clone.setX1(this.getX1());
        clone.setX2(this.getX2());
        clone.setY1(this.getY1());
        clone.setY2(this.getY2());
        clone.setColors(this.getColors());
        clone.setThs(this.getThs());
        clone.setNeedOcr(this.isNeedOcr());
        clone.setNeedBW(this.isNeedBW());
        clone.setGeneric(this.isGeneric());
        clone.setBmpPrc(this.getBmpPrc());
        clone.setOcrText(this.getOcrText());
        clone.setFinText(this.getFinText());

        clone.slots = this.slots;
        clone.slots_our = this.slots_our;
        clone.slots_enemy = this.slots_enemy;
        clone.slots_empty = this.slots_empty;
        clone.our_points = this.our_points;
        clone.enemy_points = this.enemy_points;
        clone.buildingIsOur = this.buildingIsOur;
        clone.buildingIsEnemy = this.buildingIsEnemy;
        clone.buildingIsEmpty = this.buildingIsEmpty;
        clone.mayX2 = this.mayX2;
        clone.isX2 = this.isX2;
        clone.isPresent = this.isPresent;

        return clone;
    }

    public void calc(CityCalcArea ccaBuildingSlots, CityCalcArea ccaBuildingProgress) {

        if (this.isPresent) {
            this.slots = Integer.parseInt(ccaBuildingSlots.getFinText());
            int[] colors = new int[]{ccaBuildingProgress.getColors()[0], ccaBuildingProgress.getColors()[1], ccaBuildingProgress.getColors()[2]};
            long[] countColors = PictureProcessor.countPixelInBitmap(ccaBuildingProgress.getBmpSrc(), colors,10, 10);
            long countPixelsOur = countColors[0];
            long countPixelsEnemy = countColors[1];
            long countPixelsEmpty = countColors[2];
            long countPixelsTotal = countPixelsOur + countPixelsEnemy + countPixelsEmpty;
            float frqOurSlots = (float) countPixelsOur / countPixelsTotal;
            float frqEnemySlots = (float) countPixelsEnemy / countPixelsTotal;
            float frqEmptySlots = (float) countPixelsEmpty / countPixelsTotal;
            this.slots_our = (int)Math.round((float)this.slots * frqOurSlots);
            this.slots_enemy = (int)Math.round((float)this.slots * frqEnemySlots);
            this.slots_empty = (int)Math.round((float)this.slots * frqEmptySlots);

            this.buildingIsOur = this.slots_our >= (this.slots/2 + 1);
            this.buildingIsEnemy = this.slots_enemy >= (this.slots/2 + 1);
            this.buildingIsEmpty = this.slots_empty >= (this.slots/2 + 1);

            int[] counts = new int[]{this.slots_our, this.slots_enemy, this.slots_empty};

//            float frqOurSlots = PictureProcessor.frequencyPixelInBitmap(ccaBuildingProgress.getBmpSrc(), ccaBuildingProgress.getColors()[0],10, 10);
//            float frqEnemySlots = PictureProcessor.frequencyPixelInBitmap(ccaBuildingProgress.getBmpSrc(), ccaBuildingProgress.getColors()[1],10, 10);
//            this.slots_our = (int)Math.ceil((float)this.slots * frqOurSlots);
//            this.slots_enemy = (int)Math.ceil((float)this.slots * frqEnemySlots);
//            this.slots_empty = this.slots - this.slots_our - this.slots_enemy;
            this.our_points = this.buildingIsOur ? this.slots * (this.isX2 ? 2 : 1) : 0;
            this.enemy_points = this.buildingIsEnemy ? this.slots * (this.isX2 ? 2 : 1) : 0;
        }

    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public int getSlots_our() {
        return slots_our;
    }

    public void setSlots_our(int slots_our) {
        this.slots_our = slots_our;
    }

    public int getSlots_enemy() {
        return slots_enemy;
    }

    public void setSlots_enemy(int slots_enemy) {
        this.slots_enemy = slots_enemy;
    }

    public int getSlots_empty() {
        return slots_empty;
    }

    public void setSlots_empty(int slots_empty) {
        this.slots_empty = slots_empty;
    }

    public int getOur_points() {
        return our_points;
    }

    public void setOur_points(int our_points) {
        this.our_points = our_points;
    }

    public int getEnemy_points() {
        return enemy_points;
    }

    public void setEnemy_points(int enemy_points) {
        this.enemy_points = enemy_points;
    }

    public boolean isBuildingIsOur() {
        return buildingIsOur;
    }

    public void setBuildingIsOur(boolean buildingIsOur) {
        this.buildingIsOur = buildingIsOur;
    }

    public boolean isBuildingIsEnemy() {
        return buildingIsEnemy;
    }

    public void setBuildingIsEnemy(boolean buildingIsEnemy) {
        this.buildingIsEnemy = buildingIsEnemy;
    }

    public boolean isBuildingIsEmpty() {
        return buildingIsEmpty;
    }

    public void setBuildingIsEmpty(boolean buildingIsEmpty) {
        this.buildingIsEmpty = buildingIsEmpty;
    }

    public boolean isMayX2() {
        return mayX2;
    }

    public void setMayX2(boolean mayX2) {
        this.mayX2 = mayX2;
    }

    public boolean isX2() {
        return isX2;
    }

    public void setX2(boolean x2) {
        isX2 = x2;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
