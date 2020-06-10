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

    public int getCcatPointsInScreenshot() {
        return ccatPointsInScreenshot;
    }

    public void setCcatPointsInScreenshot(int ccatPointsInScreenshot) {
        this.ccatPointsInScreenshot = ccatPointsInScreenshot;
    }

}
