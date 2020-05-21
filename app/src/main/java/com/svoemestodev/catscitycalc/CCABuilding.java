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


    public CCABuilding(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr) {
        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOcr);
    }

    public void calc(CityCalcArea ccaBuildingName, CityCalcArea ccaBuildingPoints, CityCalcArea ccaBuildingSlots, CityCalcArea ccaBuildingProgress) {

        this.isPresent = this.buildingIsOur = PictureProcessor.frequencyPixelInBitmap(this.bmpSrc, 0xFFFFFFFF) > 0.2f;

        if (this.isPresent) {
            this.buildingIsOur = PictureProcessor.frequencyPixelInBitmap(ccaBuildingPoints.bmpSrc, ccaBuildingPoints.colors[1]) > 0.2f;
            this.buildingIsEnemy = PictureProcessor.frequencyPixelInBitmap(ccaBuildingPoints.bmpSrc, ccaBuildingPoints.colors[3]) > 0.2f;
            this.mayX2 = PictureProcessor.frequencyPixelInBitmap(ccaBuildingName.bmpSrc, ccaBuildingPoints.colors[5]) > 0.01f || PictureProcessor.frequencyPixelInBitmap(ccaBuildingName.bmpSrc, ccaBuildingPoints.colors[6]) > 0.01f;
            this.isX2 = PictureProcessor.frequencyPixelInBitmap(ccaBuildingName.bmpSrc, ccaBuildingPoints.colors[6]) > 0.01f;
            this.buildingIsEmpty = !this.buildingIsOur && !this.buildingIsEnemy;
            this.slots = Integer.parseInt(ccaBuildingSlots.finText);
            float frqOurSlots = PictureProcessor.frequencyPixelInBitmap(ccaBuildingProgress.bmpSrc, ccaBuildingProgress.colors[0]);
            float frqEnemySlots = PictureProcessor.frequencyPixelInBitmap(ccaBuildingProgress.bmpSrc, ccaBuildingProgress.colors[1]);
            float frqEmptySlots = PictureProcessor.frequencyPixelInBitmap(ccaBuildingProgress.bmpSrc, ccaBuildingProgress.colors[2]);
            this.slots_our = (int)Math.ceil((float)this.slots * frqOurSlots);
            this.slots_enemy = (int)Math.ceil((float)this.slots * frqEnemySlots);
            this.slots_empty = this.slots - this.slots_our - this.slots_enemy;

            if (this.buildingIsOur) {
                ccaBuildingPoints.needOcr = true;
                ccaBuildingPoints.doOCR(0,0,1);
                this.our_points = Integer.parseInt(ccaBuildingPoints.finText);
            } else {
                this.our_points = 0;
            }

            if (this.buildingIsEnemy) {
                ccaBuildingPoints.needOcr = true;
                ccaBuildingPoints.doOCR(2,2,3);
                this.enemy_points = Integer.parseInt(ccaBuildingPoints.finText);
            } else {
                this.enemy_points = 0;
            }
        }

    }
}
