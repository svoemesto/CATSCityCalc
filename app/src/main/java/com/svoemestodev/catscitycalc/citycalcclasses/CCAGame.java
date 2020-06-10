package com.svoemestodev.catscitycalc.citycalcclasses;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.Calendar;
import java.util.Date;

public class CCAGame extends CityCalcArea {

    private Date ccagDateStartGame;     // дата начала игры
    private Date ccagDateScreenshot;    // дата создания скриншота
    private Date ccagDateCurrent;       // дата текущая
    private Date ccagDateEarlyWin;      // дата досрочного окончания игры
    private Date ccagDateEndGame;       // дата окончания игры по времени
    private Date ccagDateFinal;         // дата реального окончания игры
    private int ccagEarlyWin;           // нужно очков до досрочной победы

    private boolean ccagIsGameOver;         // игра окончена
    private boolean ccagIsGameOverEarly;    // игра окончена досрочно
    private boolean ccagIsWinOur;           // победили мы
    private boolean ccagIsWinEnemy;         // победил противник
    private boolean ccagIsWinNobody;        // ничья
    private boolean ccagIsErrorRecognize;        // ничья

    private boolean ccagWillEarlyWin;
    private boolean ccagWillOurWin;
    private boolean ccagWillEnemyWin;
    private boolean ccagWillNobodyWin;

    private int differentPoints;

    private String ccagStatus;

    public CCAGame(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr, boolean needBW) {
        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
        if (cityCalc.getFileScreenshot() != null) {
            if (cityCalc.getFileScreenshot().exists()) {
                ccagDateScreenshot = new Date((cityCalc.getFileScreenshot().lastModified() / 60_000) * 60_000); // дата/время создания скриншота с точностью до минуты
            }
        }

    }

    public void calc() {

        CityCalcArea ccaTotalTime = this.getCityCalc().getMapAreas().get(Area.TOTAL_TIME); // время
        CityCalcArea ccaEarlyWin = this.getCityCalc().getMapAreas().get(Area.EARLY_WIN);   // очки досрочки
        int minFromStartToScreenshot = 0;
        
        if (ccaTotalTime != null && ccaEarlyWin != null) {

            String[] words = ccaTotalTime.getFinText().split(":"); // разделяем строку на часы и минуты
            if (words.length == 2) {
                minFromStartToScreenshot = 24*60 - (Integer.parseInt(words[0])* 60 + Integer.parseInt(words[1]));// прошло минут с начала игры по скриншоту
            } else {
                this.ccagIsErrorRecognize = true;
            }
            this.ccagDateStartGame = Utils.addMinutesToDate(this.ccagDateScreenshot, -minFromStartToScreenshot); // дата начала игры
            this.ccagDateEndGame = Utils.addMinutesToDate(this.ccagDateStartGame, 24*60); // дата конца игры по времени
            this.ccagEarlyWin = Integer.parseInt(ccaEarlyWin.getFinText()); // очки до досрочной победы
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

        CCATeam ccatOurTeam = (CCATeam) this.getCityCalc().getMapAreas().get(Area.TEAM_NAME_OUR);
        CCATeam ccatEnemyTeam = (CCATeam)  this.getCityCalc().getMapAreas().get(Area.TEAM_NAME_ENEMY);

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
                this.ccagWillOurWin = (ccatOurTeam.getPoints(this.ccagDateFinal) > ccatEnemyTeam.getPoints(this.ccagDateFinal)); // будет ли наша победа
                this.ccagWillEnemyWin = (ccatOurTeam.getPoints(this.ccagDateFinal) < ccatEnemyTeam.getPoints(this.ccagDateFinal)); // будет ли победа противника
                this.ccagWillNobodyWin = (ccatOurTeam.getPoints(this.ccagDateFinal) == ccatEnemyTeam.getPoints(this.ccagDateFinal)); // будет ли ничья
            }

            this.ccagIsGameOver = this.ccagDateFinal.getTime() <= this.ccagDateCurrent.getTime(); // закончилась ли игра
            this.ccagIsGameOverEarly = this.ccagIsGameOver && this.ccagWillEarlyWin; // закончилась ли игра досрочно

            if (this.ccagIsGameOver) { // если игра закончилась
                this.ccagIsWinOur = ccatOurTeam.getPoints(this.ccagDateFinal) > ccatEnemyTeam.getPoints(this.ccagDateFinal); // победили ли мы
                this.ccagIsWinEnemy = ccatOurTeam.getPoints(this.ccagDateFinal) < ccatEnemyTeam.getPoints(this.ccagDateFinal); // победил ли противник
                this.ccagIsWinNobody = ccatOurTeam.getPoints(this.ccagDateFinal) == ccatEnemyTeam.getPoints(this.ccagDateFinal); // была ли ничья
            }

            differentPoints = Math.abs(ccatOurTeam.getPoints(this.ccagDateFinal) - ccatEnemyTeam.getPoints(this.ccagDateFinal)); // разница в очках на момент окончания игры

            if (this.ccagIsGameOver) { // игра закончена
                if (this.ccagIsGameOverEarly) { // игра закончена досрочно
                    if (this.ccagIsWinOur) { // досрочная наша победа
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.we_instance_win)  + " " + differentPoints + " " + this.getCityCalc().getContext().getString(R.string.points) + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagIsWinEnemy) { // досрочная победа противника
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.we_instance_lose)  + " " + differentPoints + " " + this.getCityCalc().getContext().getString(R.string.points) + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagIsWinNobody) { // досрочная ничья
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.instance_nowinner)  + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    }
                } else { // игра закончена по времени
                    if (this.ccagIsWinOur) { // наша победа
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.we_win)  + " " + differentPoints + " " + this.getCityCalc().getContext().getString(R.string.points) + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagIsWinEnemy) { // победа противника
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.we_lost)  + " " + differentPoints + " " + this.getCityCalc().getContext().getString(R.string.points) + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagIsWinNobody) { // ничья
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.nowin)  + " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    }
                }
            } else { // игра еще идет
                int minutesToEndGame = Utils.getMinutesBetweenDates(this.ccagDateCurrent, this.ccagDateFinal);
                if (this.ccagWillEarlyWin) { // игра будет закончена досрочно
                    if (this.ccagWillOurWin) { // будет досрочная наша победа
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.we_will_instance_win_with_diff_in)  + " " + differentPoints + " " + this.getCityCalc().getContext().getString(R.string.points)  + " " + this.getCityCalc().getContext().getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagWillEnemyWin) { // будет досрочная победа противника
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.we_will_instance_lose_with_diff_in)  + " " + differentPoints + " " + this.getCityCalc().getContext().getString(R.string.points)  + " " + this.getCityCalc().getContext().getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagWillNobodyWin) { // будет досрочная ничья
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.will_instance_nowin_after)  + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    }
                } else { // игра будет закончена по времени
                    if (this.ccagWillOurWin) { // будет наша победа
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.we_will_win_with_diff_in)  + " " + differentPoints + " " + this.getCityCalc().getContext().getString(R.string.points)  + " " + this.getCityCalc().getContext().getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagWillEnemyWin) { // будет победа противника
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.we_will_lose_with_diff_in)  + " " + differentPoints + " " + this.getCityCalc().getContext().getString(R.string.points)  + " " + this.getCityCalc().getContext().getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    } else if (this.ccagWillNobodyWin) { // будет ничья
                        this.ccagStatus = this.getCityCalc().getContext().getString(R.string.will_nowin) + " " + this.getCityCalc().getContext().getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.ccagDateFinal, pattern) + ")";
                    }
                }
            } // игра закончена
            
            if  (this.ccagIsErrorRecognize) this.ccagStatus = getCityCalc().getContext().getString(R.string.error_recognizing) + "  " + this.ccagStatus;
            
        }



    }


    public Date getCcagDateStartGame() {
        return ccagDateStartGame;
    }

    public void setCcagDateStartGame(Date ccagDateStartGame) {
        this.ccagDateStartGame = ccagDateStartGame;
    }

    public Date getCcagDateScreenshot() {
        return ccagDateScreenshot;
    }

    public void setCcagDateScreenshot(Date ccagDateScreenshot) {
        this.ccagDateScreenshot = ccagDateScreenshot;
    }

    public Date getCcagDateCurrent() {
        return ccagDateCurrent;
    }

    public void setCcagDateCurrent(Date ccagDateCurrent) {
        this.ccagDateCurrent = ccagDateCurrent;
    }

    public Date getCcagDateEarlyWin() {
        return ccagDateEarlyWin;
    }

    public void setCcagDateEarlyWin(Date ccagDateEarlyWin) {
        this.ccagDateEarlyWin = ccagDateEarlyWin;
    }

    public Date getCcagDateEndGame() {
        return ccagDateEndGame;
    }

    public void setCcagDateEndGame(Date ccagDateEndGame) {
        this.ccagDateEndGame = ccagDateEndGame;
    }

    public Date getCcagDateFinal() {
        return ccagDateFinal;
    }

    public void setCcagDateFinal(Date ccagDateFinal) {
        this.ccagDateFinal = ccagDateFinal;
    }

    public int getCcagEarlyWin() {
        return ccagEarlyWin;
    }

    public void setCcagEarlyWin(int ccagEarlyWin) {
        this.ccagEarlyWin = ccagEarlyWin;
    }

    public boolean isCcagIsGameOver() {
        return ccagIsGameOver;
    }

    public void setCcagIsGameOver(boolean ccagIsGameOver) {
        this.ccagIsGameOver = ccagIsGameOver;
    }

    public boolean isCcagIsGameOverEarly() {
        return ccagIsGameOverEarly;
    }

    public void setCcagIsGameOverEarly(boolean ccagIsGameOverEarly) {
        this.ccagIsGameOverEarly = ccagIsGameOverEarly;
    }

    public boolean isCcagIsWinOur() {
        return ccagIsWinOur;
    }

    public void setCcagIsWinOur(boolean ccagIsWinOur) {
        this.ccagIsWinOur = ccagIsWinOur;
    }

    public boolean isCcagIsWinEnemy() {
        return ccagIsWinEnemy;
    }

    public void setCcagIsWinEnemy(boolean ccagIsWinEnemy) {
        this.ccagIsWinEnemy = ccagIsWinEnemy;
    }

    public boolean isCcagIsWinNobody() {
        return ccagIsWinNobody;
    }

    public void setCcagIsWinNobody(boolean ccagIsWinNobody) {
        this.ccagIsWinNobody = ccagIsWinNobody;
    }

    public boolean isCcagIsErrorRecognize() {
        return ccagIsErrorRecognize;
    }

    public void setCcagIsErrorRecognize(boolean ccagIsErrorRecognize) {
        this.ccagIsErrorRecognize = ccagIsErrorRecognize;
    }

    public boolean isCcagWillEarlyWin() {
        return ccagWillEarlyWin;
    }

    public void setCcagWillEarlyWin(boolean ccagWillEarlyWin) {
        this.ccagWillEarlyWin = ccagWillEarlyWin;
    }

    public boolean isCcagWillOurWin() {
        return ccagWillOurWin;
    }

    public void setCcagWillOurWin(boolean ccagWillOurWin) {
        this.ccagWillOurWin = ccagWillOurWin;
    }

    public boolean isCcagWillEnemyWin() {
        return ccagWillEnemyWin;
    }

    public void setCcagWillEnemyWin(boolean ccagWillEnemyWin) {
        this.ccagWillEnemyWin = ccagWillEnemyWin;
    }

    public boolean isCcagWillNobodyWin() {
        return ccagWillNobodyWin;
    }

    public void setCcagWillNobodyWin(boolean ccagWillNobodyWin) {
        this.ccagWillNobodyWin = ccagWillNobodyWin;
    }

    public int getDifferentPoints() {
        return differentPoints;
    }

    public void setDifferentPoints(int differentPoints) {
        this.differentPoints = differentPoints;
    }

    public String getCcagStatus() {
        return ccagStatus;
    }

    public void setCcagStatus(String ccagStatus) {
        this.ccagStatus = ccagStatus;
    }
}
