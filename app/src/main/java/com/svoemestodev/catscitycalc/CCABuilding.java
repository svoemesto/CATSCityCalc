package com.svoemestodev.catscitycalc;

public class CCABuilding extends CityCalcArea{
    int slots;
    int slots_our;
    int slots_enemy;
    int slots_empty;
    int our_points;
    int enemy_points;
    boolean buildingIsOur;
    boolean buildingIsEnemy;
    boolean buildingIsEmpty;
    boolean mayX2;
    boolean isX2;
    boolean isPresent;


    public CCABuilding(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOCR, boolean needBW) {
        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOCR, needBW);
        this.isPresent = PictureProcessor.frequencyPixelInBitmap(this.bmpSrc, 0xFFFFFFFF, 10, 10) > 0.2f;
        if (this.isPresent) {
            this.mayX2 = PictureProcessor.frequencyPixelInBitmap(this.bmpSrc, this.colors[3],10, 10) > 0.01f || PictureProcessor.frequencyPixelInBitmap(this.bmpSrc, this.colors[4],10, 10) > 0.01f;
            this.isX2 = PictureProcessor.frequencyPixelInBitmap(this.bmpSrc, this.colors[4],10, 10) > 0.01f;
            this.buildingIsOur = PictureProcessor.frequencyPixelInBitmap(this.bmpSrc, this.colors[0],10, 10) > 0.01f;
            this.buildingIsEnemy = PictureProcessor.frequencyPixelInBitmap(this.bmpSrc, this.colors[1],10, 10) > 0.01f;
            this.buildingIsEmpty = !this.buildingIsOur && !this.buildingIsEnemy;
            this.needOcr = needOCR;
            this.needBW = needBW;
            if (this.buildingIsOur) {
                this.doOCR(0, 0, 1, true, 7.0f, 4.0f);
            } else if (this.buildingIsEnemy) {
                this.doOCR(1, 2, 3, true, 7.0f, 4.0f);
            } else {
                this.doOCR(2, 4, 5, true, 7.0f, 4.0f);
            }
            this.finText = this.ocrText.trim();
        }
    }

    public void calc(CityCalcArea ccaBuildingPoints, CityCalcArea ccaBuildingSlots, CityCalcArea ccaBuildingProgress) {

        if (this.isPresent) {
            this.slots = Integer.parseInt(ccaBuildingSlots.finText);
            float frqOurSlots = PictureProcessor.frequencyPixelInBitmap(ccaBuildingProgress.bmpSrc, ccaBuildingProgress.colors[0],10, 10);
            float frqEnemySlots = PictureProcessor.frequencyPixelInBitmap(ccaBuildingProgress.bmpSrc, ccaBuildingProgress.colors[1],10, 10);
            this.slots_our = (int)Math.ceil((float)this.slots * frqOurSlots);
            this.slots_enemy = (int)Math.ceil((float)this.slots * frqEnemySlots);
            this.slots_empty = this.slots - this.slots_our - this.slots_enemy;
            this.our_points = this.buildingIsOur ? this.slots * (this.isX2 ? 2 : 1) : 0;
            this.enemy_points = this.buildingIsEnemy ? this.slots * (this.isX2 ? 2 : 1) : 0;
        }

    }
}
