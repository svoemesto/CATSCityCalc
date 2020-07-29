package com.svoemestodev.catscitycalc.citycalcclasses;

import android.graphics.Bitmap;
import android.util.Log;

import com.svoemestodev.catscitycalc.ssa.SSA_Area;
import com.svoemestodev.catscitycalc.ssa.SSA_Areas;
import com.svoemestodev.catscitycalc.ssa.SSA_Colors;
import com.svoemestodev.catscitycalc.ssa.SSA_Key;
import com.svoemestodev.catscitycalc.ssa.SSA_Rules;
import com.svoemestodev.catscitycalc.ssa.SSA_Screenshot;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;
import com.svoemestodev.catscitycalc.utils.Utils;

public class CCABuilding extends CityCalcArea {

    private String name;
    private int index;
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
    private boolean needToWin;
    private boolean needToWinWithoutX2;
    private boolean needToEarlyWin;
    private boolean needToEarlyWinWithoutX2;
    private boolean useInForecast = true;

    public CCABuilding() {
    }

    public CCABuilding(CityCalc cityCalc, SSA_Area ssaArea) { //}, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOCR, boolean needBW) {
        super(cityCalc, ssaArea); //area, x1, x2, y1, y2, colors, ths, needOCR, needBW);
        SSA_Screenshot ssaScr = cityCalc.getSsaScreenshot();

        int index = 0;
        String keyAreaBldName = "";
        String keyAreaBldSlots = "";
        String keyAreaBldProgress = "";
        String keyRuleBldIsPresent = "";
        String keyRuleBldIsX2 = "";
        String keyRuleBldMayX2 = "";
        
        if (ssaArea.getKey().equals(SSA_Key.AREA_CITY_BLD1.getKey())) {
            index = 1;
            keyAreaBldName = SSA_Key.AREA_CITY_BLD1_NAME.getKey();
            keyAreaBldSlots = SSA_Key.AREA_CITY_BLD1_SLOTS.getKey();
            keyAreaBldProgress = SSA_Key.AREA_CITY_BLD1_PROGRESS.getKey();
            keyRuleBldIsPresent = SSA_Key.RULE_BLD1_PRESENT.getKey();
            keyRuleBldIsX2 = SSA_Key.RULE_BLD1_IS_X2.getKey();
            keyRuleBldMayX2 = SSA_Key.RULE_BLD1_MAY_X2.getKey();
        } else if (ssaArea.getKey().equals(SSA_Key.AREA_CITY_BLD2.getKey())) {
            index = 2;
            keyAreaBldName = SSA_Key.AREA_CITY_BLD2_NAME.getKey();
            keyAreaBldSlots = SSA_Key.AREA_CITY_BLD2_SLOTS.getKey();
            keyAreaBldProgress = SSA_Key.AREA_CITY_BLD2_PROGRESS.getKey();
            keyRuleBldIsPresent = SSA_Key.RULE_BLD2_PRESENT.getKey();
            keyRuleBldIsX2 = SSA_Key.RULE_BLD2_IS_X2.getKey();
            keyRuleBldMayX2 = SSA_Key.RULE_BLD2_MAY_X2.getKey();
        } else if (ssaArea.getKey().equals(SSA_Key.AREA_CITY_BLD3.getKey())) {
            index = 3;
            keyAreaBldName = SSA_Key.AREA_CITY_BLD3_NAME.getKey();
            keyAreaBldSlots = SSA_Key.AREA_CITY_BLD3_SLOTS.getKey();
            keyAreaBldProgress = SSA_Key.AREA_CITY_BLD3_PROGRESS.getKey();
            keyRuleBldIsPresent = SSA_Key.RULE_BLD3_PRESENT.getKey();
            keyRuleBldIsX2 = SSA_Key.RULE_BLD3_IS_X2.getKey();
            keyRuleBldMayX2 = SSA_Key.RULE_BLD3_MAY_X2.getKey();
        } else if (ssaArea.getKey().equals(SSA_Key.AREA_CITY_BLD4.getKey())) {
            index = 4;
            keyAreaBldName = SSA_Key.AREA_CITY_BLD4_NAME.getKey();
            keyAreaBldSlots = SSA_Key.AREA_CITY_BLD4_SLOTS.getKey();
            keyAreaBldProgress = SSA_Key.AREA_CITY_BLD4_PROGRESS.getKey();
            keyRuleBldIsPresent = SSA_Key.RULE_BLD4_PRESENT.getKey();
            keyRuleBldIsX2 = SSA_Key.RULE_BLD4_IS_X2.getKey();
            keyRuleBldMayX2 = SSA_Key.RULE_BLD4_MAY_X2.getKey();
        } else if (ssaArea.getKey().equals(SSA_Key.AREA_CITY_BLD5.getKey())) {
            index = 5;
            keyAreaBldName = SSA_Key.AREA_CITY_BLD5_NAME.getKey();
            keyAreaBldSlots = SSA_Key.AREA_CITY_BLD5_SLOTS.getKey();
            keyAreaBldProgress = SSA_Key.AREA_CITY_BLD5_PROGRESS.getKey();
            keyRuleBldIsPresent = SSA_Key.RULE_BLD5_PRESENT.getKey();
            keyRuleBldIsX2 = SSA_Key.RULE_BLD5_IS_X2.getKey();
            keyRuleBldMayX2 = SSA_Key.RULE_BLD5_MAY_X2.getKey();
        } else if (ssaArea.getKey().equals(SSA_Key.AREA_CITY_BLD6.getKey())) {
            index = 6;
            keyAreaBldName = SSA_Key.AREA_CITY_BLD6_NAME.getKey();
            keyAreaBldSlots = SSA_Key.AREA_CITY_BLD6_SLOTS.getKey();
            keyAreaBldProgress = SSA_Key.AREA_CITY_BLD6_PROGRESS.getKey();
            keyRuleBldIsPresent = SSA_Key.RULE_BLD6_PRESENT.getKey();
            keyRuleBldIsX2 = SSA_Key.RULE_BLD6_IS_X2.getKey();
            keyRuleBldMayX2 = SSA_Key.RULE_BLD6_MAY_X2.getKey();
        }

        Bitmap bldBitmap = this.getBmpSrc();
        Bitmap bldNameBitmap = SSA_Areas.getArea(keyAreaBldName).getAreaBitmap(bldBitmap);
        Bitmap bldSlotsBitmap = SSA_Areas.getArea(keyAreaBldSlots).getAreaBitmapRBT(bldBitmap);

        this.isPresent = SSA_Rules.getRule(keyRuleBldIsPresent).check(bldBitmap);
        this.index = index;

        if (this.isPresent) {
            this.mayX2 = SSA_Rules.getRule(keyRuleBldMayX2).check(bldBitmap);
            this.isX2 = SSA_Rules.getRule(keyRuleBldIsX2).check(bldBitmap);
            this.name = SSA_Areas.getArea(keyAreaBldName).getOCR(bldNameBitmap).trim();
            String slotsText = Utils.parseNumbers(SSA_Areas.getArea(keyAreaBldSlots).getOCR(bldSlotsBitmap));
            if (slotsText.equals("")) {
                Log.e("CCABuilding", "Распозналось пустое количество слотов");
                this.slots = 1;
            } else {
                this.slots = Integer.parseInt(slotsText);
            }


            int[] colors = new int[]{SSA_Colors.getColor(SSA_Key.COLOR_PROGRESS_OUR.getKey()).getColor(), SSA_Colors.getColor(SSA_Key.COLOR_PROGRESS_ENEMY.getKey()).getColor(), SSA_Colors.getColor(SSA_Key.COLOR_PROGRESS_EMPTY.getKey()).getColor()};
            long[] countColors = PictureProcessor.countPixelInBitmap(SSA_Areas.getArea(keyAreaBldProgress).getAreaBitmap(ssaScr), colors,10, 10);
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
            this.our_points = this.buildingIsOur ? this.slots * (this.isX2 ? 2 : 1) : 0;
            this.enemy_points = this.buildingIsEnemy ? this.slots * (this.isX2 ? 2 : 1) : 0;

            this.setBmpSrc(bldNameBitmap);
        }

//        CCAGame ccaGame = (CCAGame) cityCalc.getMapAreas().get(SSA_Key.AREA_CITY.getKey());
//        CCABuilding[] buildings = ccaGame.getBuildings();
//        buildings[index-1] = this;
//        ccaGame.setBuildings(buildings);

    }

    public CCABuilding getClone(CityCalc parent) {

        CCABuilding clone = new CCABuilding();

        clone.setCityCalc(parent);
        clone.setIndex(this.getIndex());
        clone.setSsaArea(this.getSsaArea().getClone());
        clone.setBmpSrc(this.getBmpSrc());
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
        clone.needToWin = this.needToWin;
        clone.needToWinWithoutX2 = this.needToWinWithoutX2;
        clone.needToEarlyWin = this.needToEarlyWin;
        clone.needToEarlyWinWithoutX2 = this.needToEarlyWinWithoutX2;
        clone.useInForecast = this.useInForecast;

        return clone;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public boolean isNeedToWin() {
        return needToWin;
    }

    public void setNeedToWin(boolean needToWin) {
        this.needToWin = needToWin;
    }

    public boolean isNeedToWinWithoutX2() {
        return needToWinWithoutX2;
    }

    public void setNeedToWinWithoutX2(boolean needToWinWithoutX2) {
        this.needToWinWithoutX2 = needToWinWithoutX2;
    }

    public boolean isNeedToEarlyWin() {
        return needToEarlyWin;
    }

    public void setNeedToEarlyWin(boolean needToEarlyWin) {
        this.needToEarlyWin = needToEarlyWin;
    }

    public boolean isNeedToEarlyWinWithoutX2() {
        return needToEarlyWinWithoutX2;
    }

    public void setNeedToEarlyWinWithoutX2(boolean needToEarlyWinWithoutX2) {
        this.needToEarlyWinWithoutX2 = needToEarlyWinWithoutX2;
    }

    public boolean isUseInForecast() {
        return useInForecast;
    }

    public void setUseInForecast(boolean useInForecast) {
        this.useInForecast = useInForecast;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
