package com.svoemestodev.catscitycalc.citycalcclasses;

import com.svoemestodev.catscitycalc.ssa.SSA_Area;
import com.svoemestodev.catscitycalc.ssa.SSA_Areas;
import com.svoemestodev.catscitycalc.ssa.SSA_Key;
import com.svoemestodev.catscitycalc.utils.Utils;

public class CCATeam extends CityCalcArea {

    private String name;
    private int ccatPointsInScreenshot;

    public CCATeam(CityCalc cityCalc, SSA_Area ssaArea) { //}, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr, boolean needBW) {

        super(cityCalc, ssaArea); //, x1, x2, y1, y2, colors, ths, needOcr, needBW);
        this.name = this.getFinText();
        if (ssaArea.getKey().equals(SSA_Key.AREA_CITY_TEAM_NAME_OUR.getKey())) {
            this.ccatPointsInScreenshot = Integer.parseInt(Utils.parseNumbers(SSA_Areas.getArea(SSA_Key.AREA_CITY_POINTS_OUR.getKey()).getOCR(cityCalc.getSsaScreenshot())));
        } else if (ssaArea.getKey().equals(SSA_Key.AREA_CITY_TEAM_NAME_ENEMY.getKey())) {
            this.ccatPointsInScreenshot = Integer.parseInt(Utils.parseNumbers(SSA_Areas.getArea(SSA_Key.AREA_CITY_POINTS_ENEMY.getKey()).getOCR(cityCalc.getSsaScreenshot())));
        }

    }

    public CCATeam() {
    }

    public CCATeam getClone(CityCalc parent) {

        CCATeam clone = new CCATeam();

        clone.setCityCalc(parent);
        clone.setSsaArea(this.getSsaArea().getClone());
//        clone.setArea(this.getArea());
        clone.setBmpSrc(this.getBmpSrc());
//        clone.setCropPosition(this.getCropPosition());
//        clone.setX1(this.getX1());
//        clone.setX2(this.getX2());
//        clone.setY1(this.getY1());
//        clone.setY2(this.getY2());
//        clone.setColors(this.getColors());
//        clone.setThs(this.getThs());
//        clone.setNeedOcr(this.isNeedOcr());
//        clone.setNeedBW(this.isNeedBW());
//        clone.setGeneric(this.isGeneric());
        clone.setBmpPrc(this.getBmpPrc());
        clone.setOcrText(this.getOcrText());
        clone.setFinText(this.getFinText());

        clone.ccatPointsInScreenshot = this.ccatPointsInScreenshot;

        return clone;
    }

    public int getCcatPointsInScreenshot() {
        return ccatPointsInScreenshot;
    }

    public void setCcatPointsInScreenshot(int ccatPointsInScreenshot) {
        this.ccatPointsInScreenshot = ccatPointsInScreenshot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
