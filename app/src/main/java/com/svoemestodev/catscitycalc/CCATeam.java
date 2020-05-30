package com.svoemestodev.catscitycalc;

import java.util.Date;

public class CCATeam extends CityCalcArea {

    int ccatPointsInScreenshot;
    int ccatIncrease;

    public CCATeam(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr, boolean needBW) {

        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
        CityCalcArea ccaPoints = null;
        CityCalcArea ccaIncrease = null;

        if (area.equals(Area.TEAM_NAME_OUR)) {
            ccaPoints = cityCalc.mapAreas.get(Area.POINTS_OUR);
            ccaIncrease = cityCalc.mapAreas.get(Area.INCREASE_OUR);
        } else if (area.equals(Area.TEAM_NAME_ENEMY)) {
            ccaPoints = cityCalc.mapAreas.get(Area.POINTS_ENEMY);
            ccaIncrease = cityCalc.mapAreas.get(Area.INCREASE_ENEMY);
        }
        if (ccaPoints != null && ccaIncrease != null) {
            this.ccatPointsInScreenshot = Integer.parseInt(ccaPoints.finText);
            this.ccatIncrease = Integer.parseInt(ccaIncrease.finText);
        }

    }

    /**
     * Возвращаем количество очков на дату. Не может быть меньше нуля или больше очков досрочки
     * @param date - дата
     * @return - очки
     */
    public int getPoints(Date date) {
        CCAGame ccaGame = (CCAGame) cityCalc.mapAreas.get(Area.CITY);
        if (ccaGame != null) {
            int minutes = Utils.getMinutesBetweenDates(ccaGame.ccagDateScreenshot, date);
            int points = this.ccatPointsInScreenshot + this.ccatIncrease * minutes;
            if (points < 0) points = 0;
            if (points > ccaGame.ccagEarlyWin) points = ccaGame.ccagEarlyWin;
            return points;
        }
        return 0;
    }

    public int getPoints() {
        CCAGame ccaGame = (CCAGame) cityCalc.mapAreas.get(Area.CITY);
        if (ccaGame != null) {
            Date date = ccaGame.ccagDateFinal.getTime() > ccaGame.ccagDateCurrent.getTime() ? ccaGame.ccagDateCurrent : ccaGame.ccagDateFinal;
            return getPoints(date);
        }
        return 0;
    }

    /**
     * @return - дата досрочной победы. Если она невозможно - +месяц он начала игры
     */
    public Date getDateEarlyWin() {
        CCAGame ccaGame = (CCAGame) cityCalc.mapAreas.get(Area.CITY);
        if (ccaGame != null) {
            if (this.ccatIncrease == 0) {
                return new Date(ccaGame.ccagDateStartGame.getTime() + 25*60*60*1000);
            } else {
                return Utils.addMinutesToDate(ccaGame.ccagDateScreenshot, (ccaGame.ccagEarlyWin - this.ccatPointsInScreenshot) / this.ccatIncrease);
            }
        } else {
            return null;
        }
//        return ccaGame == null ? null : this.ccatIncrease == 0 ? new Date(ccaGame.ccagDateStartGame.getTime() + 25*60*60*1000) : Utils.addMinutesToDate(ccaGame.ccagDateScreenshot, (ccaGame.ccagEarlyWin - this.ccatPointsInScreenshot) / this.ccatIncrease);
    }

}
