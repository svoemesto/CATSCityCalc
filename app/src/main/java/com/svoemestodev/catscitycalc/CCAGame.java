package com.svoemestodev.catscitycalc;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CCAGame extends CityCalcArea {

    Date ccagDateStartGame;     // дата начала игры
    Date ccagDateScreenshot;    // дата создания скриншота
    Date ccagDateCurrent;       // дата текущая
    Date ccagDateEarlyWin;      // дата досрочного окончания игры
    Date ccagDateEndGame;       // дата окончания игры по времени
    Date ccagDateFinal;         // дата реального окончания игры
    int ccagEarlyWin;           // нужно очков до досрочной победы

    boolean ccagIsGameOver;         // игра окончена
    boolean ccagIsGameOverEarly;    // игра окончена досрочно
    boolean ccagIsWinOur;           // победили мы
    boolean ccagIsWinEnemy;         // победил противник
    boolean ccagIsWinNobody;        // ничья

    boolean ccagWillEarlyWin;
    boolean ccagWillOurWin;
    boolean ccagWillEnemyWin;
    boolean ccagWillNobodyWin;

    String ccagStatus;

    public CCAGame(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr) {
        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOcr);
        if (cityCalc.fileScreenshot != null) {
            if (cityCalc.fileScreenshot.exists()) {
                ccagDateScreenshot = new Date((cityCalc.fileScreenshot.lastModified() / 60_000) * 60_000); // дата/время создания скриншота с точностью до минуты
            }
        }

    }

    public void calc() {

        CityCalcArea ccaTotalTime = this.cityCalc.mapAreas.get(Area.TOTAL_TIME); // время
        CityCalcArea ccaEarlyWin = this.cityCalc.mapAreas.get(Area.EARLY_WIN);   // очки досрочки

        if (ccaTotalTime != null && ccaEarlyWin != null) {

            String[] words = ccaTotalTime.finText.split(":"); // разделяем строку на часы и минуты
            int minFromStartToScreenshot = 24*60 - (Integer.parseInt(words[0])* 60 + Integer.parseInt(words[1]));// прошло минут с начала игры по скриншоту
            this.ccagDateStartGame = Utils.addMinutesToDate(this.ccagDateScreenshot, -minFromStartToScreenshot); // дата начала игры
            this.ccagDateEndGame = Utils.addMinutesToDate(this.ccagDateStartGame, 24*60); // дата конца игры по времени
            this.ccagEarlyWin = Integer.parseInt(ccaEarlyWin.finText); // очки до досрочной победы
        }

    }

    public int getMinutesToFinalGame() {
        int minutes = Utils.getMinutesBetweenDates(this.ccagDateCurrent, this.ccagDateFinal);
        if (minutes < 0) minutes = 0;
        return minutes;
    }

    public int getMinutesToEndGame() {
        int minutes = Utils.getMinutesBetweenDates(this.ccagDateCurrent, this.ccagDateEndGame);
        if (minutes < 0) minutes = 0;
        return minutes;
    }

    public void calcWin() {

        String pattern = "dd MMM HH:mm";

        CCATeam ccatOurTeam = (CCATeam) this.cityCalc.mapAreas.get(Area.TEAM_NAME_OUR);
        CCATeam ccatEnemyTeam = (CCATeam)  this.cityCalc.mapAreas.get(Area.TEAM_NAME_ENEMY);

        if (ccatOurTeam != null && ccatEnemyTeam != null) {

            this.ccagDateCurrent = new Date((Calendar.getInstance().getTime().getTime() / 60_000) * 60_000); // текущая дата
            Date dateEarlyOutTeam = ccatOurTeam.getDateEarlyWin(); // дата досрочной победы нашей команды
            Date dateEarlyEnemyTeam = ccatEnemyTeam.getDateEarlyWin(); // дата досрочной победы команды противника
            this.ccagWillEarlyWin = (dateEarlyOutTeam.getTime() < this.ccagDateEndGame.getTime() || dateEarlyEnemyTeam.getTime() < this.ccagDateEndGame.getTime()); // будет ли досрочная победа
            if (this.ccagWillEarlyWin) { // если будет досрочная победа
                this.ccagWillOurWin = (dateEarlyOutTeam.getTime() < dateEarlyEnemyTeam.getTime()); // будет ли наша досрочная победа
                this.ccagWillEnemyWin = (dateEarlyOutTeam.getTime() > dateEarlyEnemyTeam.getTime()); // будет ли досрочная победа противника
                this.ccagWillNobodyWin = (dateEarlyOutTeam.getTime() == dateEarlyEnemyTeam.getTime()); // будет ли досрочная ничья
                this.ccagDateFinal = this.ccagWillOurWin || this.ccagWillNobodyWin ? dateEarlyOutTeam : dateEarlyEnemyTeam;
            } else {
                this.ccagDateFinal = this.ccagDateEndGame;
            }

            this.ccagIsGameOver = this.ccagDateFinal.getTime() <= this.ccagDateCurrent.getTime(); // закончилась ли игра
            this.ccagIsGameOverEarly = this.ccagIsGameOver && this.ccagWillEarlyWin; // закончилась ли игра досрочно

            if (this.ccagIsGameOver) { // если игра закончилась
                this.ccagIsWinOur = ccatOurTeam.getPoints(this.ccagDateFinal) > ccatEnemyTeam.getPoints(this.ccagDateFinal); // победили ли мы
                this.ccagIsWinEnemy = ccatOurTeam.getPoints(this.ccagDateFinal) < ccatEnemyTeam.getPoints(this.ccagDateFinal); // победил ли противник
                this.ccagIsWinNobody = ccatOurTeam.getPoints(this.ccagDateFinal) == ccatEnemyTeam.getPoints(this.ccagDateFinal); // была ли ничья
            }

            int differentPoints = Math.abs(ccatOurTeam.getPoints(this.ccagDateFinal) - ccatEnemyTeam.getPoints(this.ccagDateFinal)); // разница в очках на момент окончания игры

            if (this.ccagIsGameOver) { // игра закончена
                if (this.ccagIsGameOverEarly) { // игра закончена досрочно
                    if (this.ccagIsWinOur) { // досрочная наша победа
                        this.ccagStatus = this.cityCalc.context.getString(R.string.we_instance_win)  + " " + differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagIsWinEnemy) { // досрочная победа противника
                        this.ccagStatus = this.cityCalc.context.getString(R.string.we_instance_lose)  + " " + differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagIsWinNobody) { // досрочная ничья
                        this.ccagStatus = this.cityCalc.context.getString(R.string.instance_nowinner)  + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    }
                } else { // игра закончена по времени
                    if (this.ccagIsWinOur) { // наша победа
                        this.ccagStatus = this.cityCalc.context.getString(R.string.we_win)  + " " + differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagIsWinEnemy) { // победа противника
                        this.ccagStatus = this.cityCalc.context.getString(R.string.we_lost)  + " " + differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagIsWinNobody) { // ничья
                        this.ccagStatus = this.cityCalc.context.getString(R.string.nowin)  + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    }
                }
            } else { // игра еще идет
                int minutesToEndGame = Utils.getMinutesBetweenDates(this.ccagDateCurrent, this.ccagDateFinal);
                if (this.ccagWillEarlyWin) { // игра будет закончена досрочно
                    if (this.ccagWillOurWin) { // будет досрочная наша победа
                        this.ccagStatus = this.cityCalc.context.getString(R.string.we_will_instance_win_with_diff_in)  + " " + differentPoints + " " + this.cityCalc.context.getString(R.string.points)  + " " + this.cityCalc.context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagWillEnemyWin) { // будет досрочная победа противника
                        this.ccagStatus = this.cityCalc.context.getString(R.string.we_will_instance_lose_with_diff_in)  + " " + differentPoints + " " + this.cityCalc.context.getString(R.string.points)  + " " + this.cityCalc.context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagWillNobodyWin) { // будет досрочная ничья
                        this.ccagStatus = this.cityCalc.context.getString(R.string.will_instance_nowin_after)  + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    }
                } else { // игра будет закончена по времени
                    if (this.ccagWillOurWin) { // будет наша победа
                        this.ccagStatus = this.cityCalc.context.getString(R.string.we_will_win_with_diff_in)  + " " + differentPoints + " " + this.cityCalc.context.getString(R.string.points)  + " " + this.cityCalc.context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagWillEnemyWin) { // будет победа противника
                        this.ccagStatus = this.cityCalc.context.getString(R.string.we_will_lose_with_diff_in)  + " " + differentPoints + " " + this.cityCalc.context.getString(R.string.points)  + " " + this.cityCalc.context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagWillNobodyWin) { // будет ничья
                        this.ccagStatus = this.cityCalc.context.getString(R.string.will_nowin) + " " + this.cityCalc.context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    }
                }
            } // игра закончена


        }



    }

}
