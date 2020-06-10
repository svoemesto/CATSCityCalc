package com.svoemestodev.catscitycalc.citycalcclasses;

import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.Date;

public class CCATeam extends CityCalcArea {

    private int ccatPointsInScreenshot;
    private int ccatIncrease;

    public CCATeam(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr, boolean needBW) {

        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
        CityCalcArea ccaPoints = null;
        CityCalcArea ccaIncrease = null;

        if (area.equals(Area.TEAM_NAME_OUR)) {
            ccaPoints = cityCalc.getMapAreas().get(Area.POINTS_OUR);
            ccaIncrease = cityCalc.getMapAreas().get(Area.INCREASE_OUR);
        } else if (area.equals(Area.TEAM_NAME_ENEMY)) {
            ccaPoints = cityCalc.getMapAreas().get(Area.POINTS_ENEMY);
            ccaIncrease = cityCalc.getMapAreas().get(Area.INCREASE_ENEMY);
        }
        if (ccaPoints != null && ccaIncrease != null) {
            this.ccatPointsInScreenshot = Integer.parseInt(ccaPoints.getFinText());
            this.ccatIncrease = Integer.parseInt(ccaIncrease.getFinText());
        }

    }

    /**
     * Возвращаем количество очков на дату. Не может быть меньше нуля или больше очков досрочки
     * @param date - дата
     * @return - очки
     */
    public int getPoints(Date date) {
        CCAGame ccaGame = (CCAGame) getCityCalc().getMapAreas().get(Area.CITY);
        if (ccaGame != null) {
            int minutes = Utils.getMinutesBetweenDates(ccaGame.getCcagDateScreenshot(), date);
            int points = this.ccatPointsInScreenshot + this.ccatIncrease * minutes;
            if (points < 0) points = 0;
            if (points > ccaGame.getCcagEarlyWin()) points = ccaGame.getCcagEarlyWin();
            return points;
        }
        return 0;
    }

    public int getPoints() {
        CCAGame ccaGame = (CCAGame) getCityCalc().getMapAreas().get(Area.CITY);
        if (ccaGame != null) {
            Date date = ccaGame.getCcagDateFinal().getTime() > ccaGame.getCcagDateCurrent().getTime() ? ccaGame.getCcagDateCurrent() : ccaGame.getCcagDateFinal();
            return getPoints(date);
        }
        return 0;
    }

    /**
     * @return - дата досрочной победы. Если она невозможно - +месяц он начала игры
     */
    public Date getDateEarlyWin() {
        CCAGame ccaGame = (CCAGame) getCityCalc().getMapAreas().get(Area.CITY);
        if (ccaGame != null) {
            if (this.ccatIncrease == 0) {
                return new Date(ccaGame.getCcagDateStartGame().getTime() + 25*60*60*1000);
            } else {
                return Utils.addMinutesToDate(ccaGame.getCcagDateScreenshot(), (ccaGame.getCcagEarlyWin() - this.ccatPointsInScreenshot) / this.ccatIncrease);
            }
        } else {
            return null;
        }
//        return ccaGame == null ? null : this.ccatIncrease == 0 ? new Date(ccaGame.ccagDateStartGame.getTime() + 25*60*60*1000) : Utils.addMinutesToDate(ccaGame.ccagDateScreenshot, (ccaGame.ccagEarlyWin - this.ccatPointsInScreenshot) / this.ccatIncrease);
    }


    public int getCcatPointsInScreenshot() {
        return ccatPointsInScreenshot;
    }

    public void setCcatPointsInScreenshot(int ccatPointsInScreenshot) {
        this.ccatPointsInScreenshot = ccatPointsInScreenshot;
    }

    public int getCcatIncrease() {
        return ccatIncrease;
    }

    public void setCcatIncrease(int ccatIncrease) {
        this.ccatIncrease = ccatIncrease;
    }
}
