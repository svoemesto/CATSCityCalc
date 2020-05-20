package com.svoemestodev.catscitycalc;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CCATeam extends CityCalcArea {

    int points;
    int increase;
    int minutesToEarlyWin;
    int needMinutesToEarlyWin;
    int pointsToEarlyWin;
    int willPointsToEndGame;
    int pointsInScreenshot;
    String strTimeToEarlyWin;

    public CCATeam(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr) {
        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOcr);
        CityCalcArea ccaPoints = null;
        CityCalcArea ccaIncrease = null;

        CCAGame ccaGame = (CCAGame) cityCalc.mapAreas.get(Area.CITY);
        if (area.equals(Area.TEAM_NAME_OUR)) {
            ccaPoints = cityCalc.mapAreas.get(Area.POINTS_OUR);
            ccaIncrease = cityCalc.mapAreas.get(Area.INCREASE_OUR);
        } else if (area.equals(Area.TEAM_NAME_ENEMY)) {
            ccaPoints = cityCalc.mapAreas.get(Area.POINTS_ENEMY);
            ccaIncrease = cityCalc.mapAreas.get(Area.INCREASE_ENEMY);
        }

        if (ccaGame != null && ccaPoints!= null && ccaIncrease != null) {

            this.pointsInScreenshot = Integer.parseInt(ccaPoints.finText);
            this.increase = Integer.parseInt(ccaIncrease.finText);

            Date screenshotTimeCreation = ccaGame.screenshotTimeCreation;   // дата создания скриншота
            Date endTime = ccaGame.endTime;                                 // дата окончания игры
            Date currentTime = Calendar.getInstance().getTime();            // текущая дата

            int minutesFromScreenshotEndGame =  (int)(endTime.getTime() - screenshotTimeCreation.getTime()) / 60_000;           // кол-во минут от создания скриншота до конца игры
            int minutesFromCurrentToEndGame = (int)(endTime.getTime() - currentTime.getTime()) / 60_000;                        // кол-во минут от текущего времени до конца игры (может быть отрицательное, тогда игра закончилась)

            int needPointsToEarlyWin = ccaGame.earlyWin - this.pointsInScreenshot; // кол-во очков, которое надо набрать до досрочной победы по данным со скриншота
            int needMinutesToEarlyWin = this.increase == 0 ? 25*60 : needPointsToEarlyWin / this.increase; // кол-во минут до досрочной победы (25 часов если она невозможна)
            if (minutesFromScreenshotEndGame > needMinutesToEarlyWin) needMinutesToEarlyWin = 25*60;
            this.needMinutesToEarlyWin = needMinutesToEarlyWin;

            // будет очков по окончании игры. если кол-во минут от текущего до конца игры положительное (игра не закончена) = очки + инкрис * минут_до_конца_игры ИНАЧЕ = очки + инкрис *  минут_от_создания_скриншота_до_конца_игры
            this.willPointsToEndGame = minutesFromCurrentToEndGame >= 0 ? this.pointsInScreenshot + this.increase * minutesFromCurrentToEndGame :  this.pointsInScreenshot + this.increase * minutesFromScreenshotEndGame;

        }

    }

    public void updateTime(CCAGame ccaGame) {

        Date screenshotTimeCreation = ccaGame.screenshotTimeCreation;   // дата создания скриншота
        Date endTime = ccaGame.endTime;                                 // дата окончания игры
        Date currentTime = Calendar.getInstance().getTime();            // текущая дата

        int minutesFromScreenshotCreationToNow =  (int)(currentTime.getTime() - screenshotTimeCreation.getTime()) / 60_000; // кол-во минут от создания скриншота до текущего времени
        int minutesFromScreenshotEndGame =  (int)(endTime.getTime() - screenshotTimeCreation.getTime()) / 60_000;           // кол-во минут от создания скриншота до конца игры
        int minutesFromCurrentToEndGame = (int)(endTime.getTime() - currentTime.getTime()) / 60_000;                        // кол-во минут от текущего времени до конца игры (может быть отрицательное, тогда игра закончилась)

        if (ccaGame.isGameOver) {   // если игра закончена
            this.points = this.pointsInScreenshot + this.increase * minutesFromScreenshotEndGame;  // очки + инкрис * минут_от_создания_скриншота_до_конца_игры
            if (this.points > ccaGame.earlyWin) this.points = ccaGame.earlyWin;
            this.strTimeToEarlyWin = "";
        } else {
            this.points = this.pointsInScreenshot + this.increase * minutesFromScreenshotCreationToNow;  // очки на текущий момент времени.
            this.pointsToEarlyWin = ccaGame.earlyWin - this.points;                           // очки_до_досрочной_победы = очки_досрочки - очки_на_текущий_момент.
            if (this.pointsToEarlyWin > ccaGame.earlyWin) this.points = ccaGame.earlyWin;
            this.minutesToEarlyWin = this.increase == 0 ? 25*60 : this.pointsToEarlyWin / this.increase;   // минут_до_досрочки = очки_до_досрочки / инкрис. -25 часов если инкрис = 0
            this.strTimeToEarlyWin = this.minutesToEarlyWin >= 0 ? String.format(Locale.getDefault(), "%02d:%02d", minutesFromCurrentToEndGame / 60, minutesFromCurrentToEndGame % 60) : "";
        }

    }
}
