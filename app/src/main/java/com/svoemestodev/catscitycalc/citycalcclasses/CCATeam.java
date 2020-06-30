package com.svoemestodev.catscitycalc.citycalcclasses;

public class CCATeam extends CityCalcArea {

    private int ccatPointsInScreenshot;

    public CCATeam(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr, boolean needBW) {

        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
        CityCalcArea ccaPoints = null;

        if (area.equals(Area.TEAM_NAME_OUR)) {
            ccaPoints = cityCalc.getMapAreas().get(Area.POINTS_OUR);
        } else if (area.equals(Area.TEAM_NAME_ENEMY)) {
            ccaPoints = cityCalc.getMapAreas().get(Area.POINTS_ENEMY);
        }
        if (ccaPoints != null) {
            this.ccatPointsInScreenshot = Integer.parseInt(ccaPoints.getFinText());
        }

    }

    public CCATeam() {
    }

    public CCATeam getClone(CityCalc parent) {

        CCATeam clone = new CCATeam();

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

        clone.ccatPointsInScreenshot = this.ccatPointsInScreenshot;

        return clone;
    }

    public int getCcatPointsInScreenshot() {
        return ccatPointsInScreenshot;
    }

    public void setCcatPointsInScreenshot(int ccatPointsInScreenshot) {
        this.ccatPointsInScreenshot = ccatPointsInScreenshot;
    }

}
