package com.svoemestodev.catscitycalc.citycalcclasses;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.database.DbTeamGame;
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

    private int ccagPointsOurInScreenshot;
    private int ccagPointsEnemyInScreenshot;
    private int ccagIncreaseOur;
    private int ccagIncreaseEnemy;

    private boolean isPresent_blt;
    private boolean isPresent_blc;
    private boolean isPresent_blb;
    private boolean isPresent_brt;
    private boolean isPresent_brc;
    private boolean isPresent_brb;

    private String name_blt;
    private String name_blc;
    private String name_blb;
    private String name_brt;
    private String name_brc;
    private String name_brb;

    private boolean isX2_blt;
    private boolean isX2_blc;
    private boolean isX2_blb;
    private boolean isX2_brt;
    private boolean isX2_brc;
    private boolean isX2_brb;

    private boolean mayX2_blt;
    private boolean mayX2_blc;
    private boolean mayX2_blb;
    private boolean mayX2_brt;
    private boolean mayX2_brc;
    private boolean mayX2_brb;

    private boolean buildingIsOur_blt;
    private boolean buildingIsOur_blc;
    private boolean buildingIsOur_blb;
    private boolean buildingIsOur_brt;
    private boolean buildingIsOur_brc;
    private boolean buildingIsOur_brb;

    private boolean buildingIsEmpty_blt;
    private boolean buildingIsEmpty_blc;
    private boolean buildingIsEmpty_blb;
    private boolean buildingIsEmpty_brt;
    private boolean buildingIsEmpty_brc;
    private boolean buildingIsEmpty_brb;

    private boolean buildingIsEnemy_blt;
    private boolean buildingIsEnemy_blc;
    private boolean buildingIsEnemy_blb;
    private boolean buildingIsEnemy_brt;
    private boolean buildingIsEnemy_brc;
    private boolean buildingIsEnemy_brb;

    private int our_points_blt;
    private int our_points_blc;
    private int our_points_blb;
    private int our_points_brt;
    private int our_points_brc;
    private int our_points_brb;

    private int enemy_points_blt;
    private int enemy_points_blc;
    private int enemy_points_blb;
    private int enemy_points_brt;
    private int enemy_points_brc;
    private int enemy_points_brb;

    private int slots_blt;
    private int slots_blt_our;
    private int slots_blt_empty;
    private int slots_blt_enemy;
    private int slots_blc;
    private int slots_blc_our;
    private int slots_blc_empty;
    private int slots_blc_enemy;
    private int slots_blb;
    private int slots_blb_our;
    private int slots_blb_empty;
    private int slots_blb_enemy;
    private int slots_brt;
    private int slots_brt_our;
    private int slots_brt_empty;
    private int slots_brt_enemy;
    private int slots_brc;
    private int slots_brc_our;
    private int slots_brc_empty;
    private int slots_brc_enemy;
    private int slots_brb;
    private int slots_brb_our;
    private int slots_brb_empty;
    private int slots_brb_enemy;

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

    public void updateFromDb(DbTeamGame dbTeamGame) {

        this.ccagDateStartGame = dbTeamGame.getDateStartGame();
        this.ccagDateScreenshot = dbTeamGame.getDateScreenshot();
        this.ccagDateEndGame = dbTeamGame.getDateEndGame();
        this.ccagEarlyWin = dbTeamGame.getEarlyWin();

        this.ccagPointsOurInScreenshot = dbTeamGame.getPointsOurInScreenshot();
        this.ccagPointsEnemyInScreenshot = dbTeamGame.getPointsEnemyInScreenshot();
        this.ccagIncreaseOur = dbTeamGame.getIncreaseOur();
        this.ccagIncreaseEnemy = dbTeamGame.getIncreaseEnemy();

        this.isPresent_blt = dbTeamGame.isPresent_blt();
        this.isPresent_blc = dbTeamGame.isPresent_blc();
        this.isPresent_blb = dbTeamGame.isPresent_blb();
        this.isPresent_brt = dbTeamGame.isPresent_brt();
        this.isPresent_brc = dbTeamGame.isPresent_brc();
        this.isPresent_brb = dbTeamGame.isPresent_brb();

        this.isX2_blt = dbTeamGame.isX2_blt();
        this.isX2_blc = dbTeamGame.isX2_blc();
        this.isX2_blb = dbTeamGame.isX2_blb();
        this.isX2_brt = dbTeamGame.isX2_brt();
        this.isX2_brc = dbTeamGame.isX2_brc();
        this.isX2_brb = dbTeamGame.isX2_brb();

        this.mayX2_blt = dbTeamGame.isMayX2_blt();
        this.mayX2_blc = dbTeamGame.isMayX2_blc();
        this.mayX2_blb = dbTeamGame.isMayX2_blb();
        this.mayX2_brt = dbTeamGame.isMayX2_brt();
        this.mayX2_brc = dbTeamGame.isMayX2_brc();
        this.mayX2_brb = dbTeamGame.isMayX2_brb();

        this.buildingIsOur_blt = dbTeamGame.isBuildingIsOur_blt();
        this.buildingIsOur_blc = dbTeamGame.isBuildingIsOur_blc();
        this.buildingIsOur_blb = dbTeamGame.isBuildingIsOur_blb();
        this.buildingIsOur_brt = dbTeamGame.isBuildingIsOur_brt();
        this.buildingIsOur_brc = dbTeamGame.isBuildingIsOur_brc();
        this.buildingIsOur_brb = dbTeamGame.isBuildingIsOur_brb();

        this.buildingIsEmpty_blt = dbTeamGame.isBuildingIsEmpty_blt();
        this.buildingIsEmpty_blc = dbTeamGame.isBuildingIsEmpty_blc();
        this.buildingIsEmpty_blb = dbTeamGame.isBuildingIsEmpty_blb();
        this.buildingIsEmpty_brt = dbTeamGame.isBuildingIsEmpty_brt();
        this.buildingIsEmpty_brc = dbTeamGame.isBuildingIsEmpty_brc();
        this.buildingIsEmpty_brb = dbTeamGame.isBuildingIsEmpty_brb();

        this.buildingIsEnemy_blt = dbTeamGame.isBuildingIsEnemy_blt();
        this.buildingIsEnemy_blc = dbTeamGame.isBuildingIsEnemy_blc();
        this.buildingIsEnemy_blb = dbTeamGame.isBuildingIsEnemy_blb();
        this.buildingIsEnemy_brt = dbTeamGame.isBuildingIsEnemy_brt();
        this.buildingIsEnemy_brc = dbTeamGame.isBuildingIsEnemy_brc();
        this.buildingIsEnemy_brb = dbTeamGame.isBuildingIsEnemy_brb();

        this.our_points_blt = dbTeamGame.getOur_points_blt();
        this.our_points_blc = dbTeamGame.getOur_points_blc();
        this.our_points_blb = dbTeamGame.getOur_points_blb();
        this.our_points_brt = dbTeamGame.getOur_points_brt();
        this.our_points_brc = dbTeamGame.getOur_points_brc();
        this.our_points_brb = dbTeamGame.getOur_points_brb();

        this.enemy_points_blt = dbTeamGame.getEnemy_points_blt();
        this.enemy_points_blc = dbTeamGame.getEnemy_points_blc();
        this.enemy_points_blb = dbTeamGame.getEnemy_points_blb();
        this.enemy_points_brt = dbTeamGame.getEnemy_points_brt();
        this.enemy_points_brc = dbTeamGame.getEnemy_points_brc();
        this.enemy_points_brb = dbTeamGame.getEnemy_points_brb();

        this.slots_blt = dbTeamGame.getSlots_blt();
        this.slots_blt_our = dbTeamGame.getSlots_blt_our();
        this.slots_blt_empty = dbTeamGame.getSlots_blt_empty();
        this.slots_blt_enemy = dbTeamGame.getSlots_blt_enemy();
        this.slots_blc = dbTeamGame.getSlots_blc();
        this.slots_blc_our = dbTeamGame.getSlots_blc_our();
        this.slots_blc_empty = dbTeamGame.getSlots_blc_empty();
        this.slots_blc_enemy = dbTeamGame.getSlots_blc_enemy();
        this.slots_blb = dbTeamGame.getSlots_blb();
        this.slots_blb_our = dbTeamGame.getSlots_blb_our();
        this.slots_blb_empty = dbTeamGame.getSlots_blb_empty();
        this.slots_blb_enemy = dbTeamGame.getSlots_blb_enemy();
        this.slots_brt = dbTeamGame.getSlots_brt();
        this.slots_brt_our = dbTeamGame.getSlots_brt_our();
        this.slots_brt_empty = dbTeamGame.getSlots_brt_empty();
        this.slots_brt_enemy = dbTeamGame.getSlots_brt_enemy();
        this.slots_brc = dbTeamGame.getSlots_brc();
        this.slots_brc_our = dbTeamGame.getSlots_brc_our();
        this.slots_brc_empty = dbTeamGame.getSlots_brc_empty();
        this.slots_brc_enemy = dbTeamGame.getSlots_brc_enemy();
        this.slots_brb = dbTeamGame.getSlots_brb();
        this.slots_brb_our = dbTeamGame.getSlots_brb_our();
        this.slots_brb_empty = dbTeamGame.getSlots_brb_empty();
        this.slots_brb_enemy = dbTeamGame.getSlots_brb_enemy();

        calcWin();

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

    /**
     * Возвращаем количество очков на дату. Не может быть меньше нуля или больше очков досрочки
     * @param date - дата
     * @return - очки
     */
    public int getPointsOur(Date date) {
        int minutes = Utils.getMinutesBetweenDates(this.getCcagDateScreenshot(), date);
        int points = this.ccagPointsOurInScreenshot + this.ccagIncreaseOur * minutes;
        if (points < 0) points = 0;
        if (points > this.getCcagEarlyWin()) points = this.getCcagEarlyWin();
        return points;
    }

    public int getPointsOur() {
        Date date = this.getCcagDateFinal().getTime() > this.getCcagDateCurrent().getTime() ? this.getCcagDateCurrent() : this.getCcagDateFinal();
        return getPointsOur(date);
    }

    public int getPointsEnemy(Date date) {
        int minutes = Utils.getMinutesBetweenDates(this.getCcagDateScreenshot(), date);
        int points = this.ccagPointsEnemyInScreenshot + this.ccagIncreaseEnemy * minutes;
        if (points < 0) points = 0;
        if (points > this.getCcagEarlyWin()) points = this.getCcagEarlyWin();
        return points;
    }

    public int getPointsEnemy() {
        Date date = this.getCcagDateFinal().getTime() > this.getCcagDateCurrent().getTime() ? this.getCcagDateCurrent() : this.getCcagDateFinal();
        return getPointsEnemy(date);
    }

    /**
     * @return - дата досрочной победы. Если она невозможно - +месяц он начала игры
     */
    public Date getDateEarlyWinOur() {
        if (this.ccagIncreaseOur == 0) {
            return new Date(this.getCcagDateStartGame().getTime() + 25*60*60*1000);
        } else {
            return Utils.addMinutesToDate(this.getCcagDateScreenshot(), (this.getCcagEarlyWin() - this.ccagPointsOurInScreenshot) / this.ccagIncreaseOur);
        }
    }

    public Date getDateEarlyWinEnemy() {
        if (this.ccagIncreaseEnemy == 0) {
            return new Date(this.getCcagDateStartGame().getTime() + 25*60*60*1000);
        } else {
            return Utils.addMinutesToDate(this.getCcagDateScreenshot(), (this.getCcagEarlyWin() - this.ccagPointsEnemyInScreenshot) / this.ccagIncreaseEnemy);
        }
    }

    public void calcWin() {

        String pattern = "dd MMM HH:mm";

        this.ccagDateCurrent = new Date((Calendar.getInstance().getTime().getTime() / 60_000) * 60_000); // текущая дата
        Date dateEarlyOutTeam = getDateEarlyWinOur(); // дата досрочной победы нашей команды
        Date dateEarlyEnemyTeam = getDateEarlyWinEnemy(); // дата досрочной победы команды противника
        this.ccagWillEarlyWin = (dateEarlyOutTeam.getTime() < this.ccagDateEndGame.getTime() || dateEarlyEnemyTeam.getTime() < this.ccagDateEndGame.getTime()); // будет ли досрочная победа
        if (this.ccagWillEarlyWin) { // если будет досрочная победа
            this.ccagWillOurWin = (dateEarlyOutTeam.getTime() < dateEarlyEnemyTeam.getTime()); // будет ли наша досрочная победа
            this.ccagWillEnemyWin = (dateEarlyOutTeam.getTime() > dateEarlyEnemyTeam.getTime()); // будет ли досрочная победа противника
            this.ccagWillNobodyWin = (dateEarlyOutTeam.getTime() == dateEarlyEnemyTeam.getTime()); // будет ли досрочная ничья
            this.ccagDateFinal = this.ccagWillOurWin || this.ccagWillNobodyWin ? dateEarlyOutTeam : dateEarlyEnemyTeam;
        } else {
            this.ccagDateFinal = this.ccagDateEndGame;
            this.ccagWillOurWin = (getPointsOur(this.ccagDateFinal) > getPointsEnemy(this.ccagDateFinal)); // будет ли наша победа
            this.ccagWillEnemyWin = (getPointsOur(this.ccagDateFinal) < getPointsEnemy(this.ccagDateFinal)); // будет ли победа противника
            this.ccagWillNobodyWin = (getPointsOur(this.ccagDateFinal) == getPointsEnemy(this.ccagDateFinal)); // будет ли ничья
        }

        this.ccagIsGameOver = this.ccagDateFinal.getTime() <= this.ccagDateCurrent.getTime(); // закончилась ли игра
        this.ccagIsGameOverEarly = this.ccagIsGameOver && this.ccagWillEarlyWin; // закончилась ли игра досрочно

        if (this.ccagIsGameOver) { // если игра закончилась
            this.ccagIsWinOur = getPointsOur(this.ccagDateFinal) > getPointsEnemy(this.ccagDateFinal); // победили ли мы
            this.ccagIsWinEnemy = getPointsOur(this.ccagDateFinal) < getPointsEnemy(this.ccagDateFinal); // победил ли противник
            this.ccagIsWinNobody = getPointsOur(this.ccagDateFinal) == getPointsEnemy(this.ccagDateFinal); // была ли ничья
        }

        differentPoints = Math.abs(getPointsOur(this.ccagDateFinal) - getPointsEnemy(this.ccagDateFinal)); // разница в очках на момент окончания игры

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

    public int getCcagPointsOurInScreenshot() {
        return ccagPointsOurInScreenshot;
    }

    public void setCcagPointsOurInScreenshot(int ccagPointsOurInScreenshot) {
        this.ccagPointsOurInScreenshot = ccagPointsOurInScreenshot;
    }

    public int getCcagPointsEnemyInScreenshot() {
        return ccagPointsEnemyInScreenshot;
    }

    public void setCcagPointsEnemyInScreenshot(int ccagPointsEnemyInScreenshot) {
        this.ccagPointsEnemyInScreenshot = ccagPointsEnemyInScreenshot;
    }

    public int getCcagIncreaseOur() {
        return ccagIncreaseOur;
    }

    public void setCcagIncreaseOur(int ccagIncreaseOur) {
        this.ccagIncreaseOur = ccagIncreaseOur;
    }

    public int getCcagIncreaseEnemy() {
        return ccagIncreaseEnemy;
    }

    public void setCcagIncreaseEnemy(int ccagIncreaseEnemy) {
        this.ccagIncreaseEnemy = ccagIncreaseEnemy;
    }


    public boolean isPresent_blt() {
        return isPresent_blt;
    }

    public void setPresent_blt(boolean present_blt) {
        isPresent_blt = present_blt;
    }

    public boolean isPresent_blc() {
        return isPresent_blc;
    }

    public void setPresent_blc(boolean present_blc) {
        isPresent_blc = present_blc;
    }

    public boolean isPresent_blb() {
        return isPresent_blb;
    }

    public void setPresent_blb(boolean present_blb) {
        isPresent_blb = present_blb;
    }

    public boolean isPresent_brt() {
        return isPresent_brt;
    }

    public void setPresent_brt(boolean present_brt) {
        isPresent_brt = present_brt;
    }

    public boolean isPresent_brc() {
        return isPresent_brc;
    }

    public void setPresent_brc(boolean present_brc) {
        isPresent_brc = present_brc;
    }

    public boolean isPresent_brb() {
        return isPresent_brb;
    }

    public void setPresent_brb(boolean present_brb) {
        isPresent_brb = present_brb;
    }

    public int getSlots_blt() {
        return slots_blt;
    }

    public void setSlots_blt(int slots_blt) {
        this.slots_blt = slots_blt;
    }

    public int getSlots_blt_our() {
        return slots_blt_our;
    }

    public void setSlots_blt_our(int slots_blt_our) {
        this.slots_blt_our = slots_blt_our;
    }

    public int getSlots_blt_empty() {
        return slots_blt_empty;
    }

    public void setSlots_blt_empty(int slots_blt_empty) {
        this.slots_blt_empty = slots_blt_empty;
    }

    public int getSlots_blt_enemy() {
        return slots_blt_enemy;
    }

    public void setSlots_blt_enemy(int slots_blt_enemy) {
        this.slots_blt_enemy = slots_blt_enemy;
    }

    public int getSlots_blc() {
        return slots_blc;
    }

    public void setSlots_blc(int slots_blc) {
        this.slots_blc = slots_blc;
    }

    public int getSlots_blc_our() {
        return slots_blc_our;
    }

    public void setSlots_blc_our(int slots_blc_our) {
        this.slots_blc_our = slots_blc_our;
    }

    public int getSlots_blc_empty() {
        return slots_blc_empty;
    }

    public void setSlots_blc_empty(int slots_blc_empty) {
        this.slots_blc_empty = slots_blc_empty;
    }

    public int getSlots_blc_enemy() {
        return slots_blc_enemy;
    }

    public void setSlots_blc_enemy(int slots_blc_enemy) {
        this.slots_blc_enemy = slots_blc_enemy;
    }

    public int getSlots_blb() {
        return slots_blb;
    }

    public void setSlots_blb(int slots_blb) {
        this.slots_blb = slots_blb;
    }

    public int getSlots_blb_our() {
        return slots_blb_our;
    }

    public void setSlots_blb_our(int slots_blb_our) {
        this.slots_blb_our = slots_blb_our;
    }

    public int getSlots_blb_empty() {
        return slots_blb_empty;
    }

    public void setSlots_blb_empty(int slots_blb_empty) {
        this.slots_blb_empty = slots_blb_empty;
    }

    public int getSlots_blb_enemy() {
        return slots_blb_enemy;
    }

    public void setSlots_blb_enemy(int slots_blb_enemy) {
        this.slots_blb_enemy = slots_blb_enemy;
    }

    public int getSlots_brt() {
        return slots_brt;
    }

    public void setSlots_brt(int slots_brt) {
        this.slots_brt = slots_brt;
    }

    public int getSlots_brt_our() {
        return slots_brt_our;
    }

    public void setSlots_brt_our(int slots_brt_our) {
        this.slots_brt_our = slots_brt_our;
    }

    public int getSlots_brt_empty() {
        return slots_brt_empty;
    }

    public void setSlots_brt_empty(int slots_brt_empty) {
        this.slots_brt_empty = slots_brt_empty;
    }

    public int getSlots_brt_enemy() {
        return slots_brt_enemy;
    }

    public void setSlots_brt_enemy(int slots_brt_enemy) {
        this.slots_brt_enemy = slots_brt_enemy;
    }

    public int getSlots_brc() {
        return slots_brc;
    }

    public void setSlots_brc(int slots_brc) {
        this.slots_brc = slots_brc;
    }

    public int getSlots_brc_our() {
        return slots_brc_our;
    }

    public void setSlots_brc_our(int slots_brc_our) {
        this.slots_brc_our = slots_brc_our;
    }

    public int getSlots_brc_empty() {
        return slots_brc_empty;
    }

    public void setSlots_brc_empty(int slots_brc_empty) {
        this.slots_brc_empty = slots_brc_empty;
    }

    public int getSlots_brc_enemy() {
        return slots_brc_enemy;
    }

    public void setSlots_brc_enemy(int slots_brc_enemy) {
        this.slots_brc_enemy = slots_brc_enemy;
    }

    public int getSlots_brb() {
        return slots_brb;
    }

    public void setSlots_brb(int slots_brb) {
        this.slots_brb = slots_brb;
    }

    public int getSlots_brb_our() {
        return slots_brb_our;
    }

    public void setSlots_brb_our(int slots_brb_our) {
        this.slots_brb_our = slots_brb_our;
    }

    public int getSlots_brb_empty() {
        return slots_brb_empty;
    }

    public void setSlots_brb_empty(int slots_brb_empty) {
        this.slots_brb_empty = slots_brb_empty;
    }

    public int getSlots_brb_enemy() {
        return slots_brb_enemy;
    }

    public void setSlots_brb_enemy(int slots_brb_enemy) {
        this.slots_brb_enemy = slots_brb_enemy;
    }

    public boolean isX2_blt() {
        return isX2_blt;
    }

    public void setX2_blt(boolean x2_blt) {
        isX2_blt = x2_blt;
    }

    public boolean isX2_blc() {
        return isX2_blc;
    }

    public void setX2_blc(boolean x2_blc) {
        isX2_blc = x2_blc;
    }

    public boolean isX2_blb() {
        return isX2_blb;
    }

    public void setX2_blb(boolean x2_blb) {
        isX2_blb = x2_blb;
    }

    public boolean isX2_brt() {
        return isX2_brt;
    }

    public void setX2_brt(boolean x2_brt) {
        isX2_brt = x2_brt;
    }

    public boolean isX2_brc() {
        return isX2_brc;
    }

    public void setX2_brc(boolean x2_brc) {
        isX2_brc = x2_brc;
    }

    public boolean isX2_brb() {
        return isX2_brb;
    }

    public void setX2_brb(boolean x2_brb) {
        isX2_brb = x2_brb;
    }

    public boolean isMayX2_blt() {
        return mayX2_blt;
    }

    public void setMayX2_blt(boolean mayX2_blt) {
        this.mayX2_blt = mayX2_blt;
    }

    public boolean isMayX2_blc() {
        return mayX2_blc;
    }

    public void setMayX2_blc(boolean mayX2_blc) {
        this.mayX2_blc = mayX2_blc;
    }

    public boolean isMayX2_blb() {
        return mayX2_blb;
    }

    public void setMayX2_blb(boolean mayX2_blb) {
        this.mayX2_blb = mayX2_blb;
    }

    public boolean isMayX2_brt() {
        return mayX2_brt;
    }

    public void setMayX2_brt(boolean mayX2_brt) {
        this.mayX2_brt = mayX2_brt;
    }

    public boolean isMayX2_brc() {
        return mayX2_brc;
    }

    public void setMayX2_brc(boolean mayX2_brc) {
        this.mayX2_brc = mayX2_brc;
    }

    public boolean isMayX2_brb() {
        return mayX2_brb;
    }

    public void setMayX2_brb(boolean mayX2_brb) {
        this.mayX2_brb = mayX2_brb;
    }

    public String getName_blt() {
        return name_blt;
    }

    public void setName_blt(String name_blt) {
        this.name_blt = name_blt;
    }

    public String getName_blc() {
        return name_blc;
    }

    public void setName_blc(String name_blc) {
        this.name_blc = name_blc;
    }

    public String getName_blb() {
        return name_blb;
    }

    public void setName_blb(String name_blb) {
        this.name_blb = name_blb;
    }

    public String getName_brt() {
        return name_brt;
    }

    public void setName_brt(String name_brt) {
        this.name_brt = name_brt;
    }

    public String getName_brc() {
        return name_brc;
    }

    public void setName_brc(String name_brc) {
        this.name_brc = name_brc;
    }

    public String getName_brb() {
        return name_brb;
    }

    public void setName_brb(String name_brb) {
        this.name_brb = name_brb;
    }

    public boolean isBuildingIsOur_blt() {
        return buildingIsOur_blt;
    }

    public void setBuildingIsOur_blt(boolean buildingIsOur_blt) {
        this.buildingIsOur_blt = buildingIsOur_blt;
    }

    public boolean isBuildingIsOur_blc() {
        return buildingIsOur_blc;
    }

    public void setBuildingIsOur_blc(boolean buildingIsOur_blc) {
        this.buildingIsOur_blc = buildingIsOur_blc;
    }

    public boolean isBuildingIsOur_blb() {
        return buildingIsOur_blb;
    }

    public void setBuildingIsOur_blb(boolean buildingIsOur_blb) {
        this.buildingIsOur_blb = buildingIsOur_blb;
    }

    public boolean isBuildingIsOur_brt() {
        return buildingIsOur_brt;
    }

    public void setBuildingIsOur_brt(boolean buildingIsOur_brt) {
        this.buildingIsOur_brt = buildingIsOur_brt;
    }

    public boolean isBuildingIsOur_brc() {
        return buildingIsOur_brc;
    }

    public void setBuildingIsOur_brc(boolean buildingIsOur_brc) {
        this.buildingIsOur_brc = buildingIsOur_brc;
    }

    public boolean isBuildingIsOur_brb() {
        return buildingIsOur_brb;
    }

    public void setBuildingIsOur_brb(boolean buildingIsOur_brb) {
        this.buildingIsOur_brb = buildingIsOur_brb;
    }

    public boolean isBuildingIsEmpty_blt() {
        return buildingIsEmpty_blt;
    }

    public void setBuildingIsEmpty_blt(boolean buildingIsEmpty_blt) {
        this.buildingIsEmpty_blt = buildingIsEmpty_blt;
    }

    public boolean isBuildingIsEmpty_blc() {
        return buildingIsEmpty_blc;
    }

    public void setBuildingIsEmpty_blc(boolean buildingIsEmpty_blc) {
        this.buildingIsEmpty_blc = buildingIsEmpty_blc;
    }

    public boolean isBuildingIsEmpty_blb() {
        return buildingIsEmpty_blb;
    }

    public void setBuildingIsEmpty_blb(boolean buildingIsEmpty_blb) {
        this.buildingIsEmpty_blb = buildingIsEmpty_blb;
    }

    public boolean isBuildingIsEmpty_brt() {
        return buildingIsEmpty_brt;
    }

    public void setBuildingIsEmpty_brt(boolean buildingIsEmpty_brt) {
        this.buildingIsEmpty_brt = buildingIsEmpty_brt;
    }

    public boolean isBuildingIsEmpty_brc() {
        return buildingIsEmpty_brc;
    }

    public void setBuildingIsEmpty_brc(boolean buildingIsEmpty_brc) {
        this.buildingIsEmpty_brc = buildingIsEmpty_brc;
    }

    public boolean isBuildingIsEmpty_brb() {
        return buildingIsEmpty_brb;
    }

    public void setBuildingIsEmpty_brb(boolean buildingIsEmpty_brb) {
        this.buildingIsEmpty_brb = buildingIsEmpty_brb;
    }

    public boolean isBuildingIsEnemy_blt() {
        return buildingIsEnemy_blt;
    }

    public void setBuildingIsEnemy_blt(boolean buildingIsEnemy_blt) {
        this.buildingIsEnemy_blt = buildingIsEnemy_blt;
    }

    public boolean isBuildingIsEnemy_blc() {
        return buildingIsEnemy_blc;
    }

    public void setBuildingIsEnemy_blc(boolean buildingIsEnemy_blc) {
        this.buildingIsEnemy_blc = buildingIsEnemy_blc;
    }

    public boolean isBuildingIsEnemy_blb() {
        return buildingIsEnemy_blb;
    }

    public void setBuildingIsEnemy_blb(boolean buildingIsEnemy_blb) {
        this.buildingIsEnemy_blb = buildingIsEnemy_blb;
    }

    public boolean isBuildingIsEnemy_brt() {
        return buildingIsEnemy_brt;
    }

    public void setBuildingIsEnemy_brt(boolean buildingIsEnemy_brt) {
        this.buildingIsEnemy_brt = buildingIsEnemy_brt;
    }

    public boolean isBuildingIsEnemy_brc() {
        return buildingIsEnemy_brc;
    }

    public void setBuildingIsEnemy_brc(boolean buildingIsEnemy_brc) {
        this.buildingIsEnemy_brc = buildingIsEnemy_brc;
    }

    public boolean isBuildingIsEnemy_brb() {
        return buildingIsEnemy_brb;
    }

    public void setBuildingIsEnemy_brb(boolean buildingIsEnemy_brb) {
        this.buildingIsEnemy_brb = buildingIsEnemy_brb;
    }

    public int getOur_points_blt() {
        return our_points_blt;
    }

    public void setOur_points_blt(int our_points_blt) {
        this.our_points_blt = our_points_blt;
    }

    public int getOur_points_blc() {
        return our_points_blc;
    }

    public void setOur_points_blc(int our_points_blc) {
        this.our_points_blc = our_points_blc;
    }

    public int getOur_points_blb() {
        return our_points_blb;
    }

    public void setOur_points_blb(int our_points_blb) {
        this.our_points_blb = our_points_blb;
    }

    public int getOur_points_brt() {
        return our_points_brt;
    }

    public void setOur_points_brt(int our_points_brt) {
        this.our_points_brt = our_points_brt;
    }

    public int getOur_points_brc() {
        return our_points_brc;
    }

    public void setOur_points_brc(int our_points_brc) {
        this.our_points_brc = our_points_brc;
    }

    public int getOur_points_brb() {
        return our_points_brb;
    }

    public void setOur_points_brb(int our_points_brb) {
        this.our_points_brb = our_points_brb;
    }

    public int getEnemy_points_blt() {
        return enemy_points_blt;
    }

    public void setEnemy_points_blt(int enemy_points_blt) {
        this.enemy_points_blt = enemy_points_blt;
    }

    public int getEnemy_points_blc() {
        return enemy_points_blc;
    }

    public void setEnemy_points_blc(int enemy_points_blc) {
        this.enemy_points_blc = enemy_points_blc;
    }

    public int getEnemy_points_blb() {
        return enemy_points_blb;
    }

    public void setEnemy_points_blb(int enemy_points_blb) {
        this.enemy_points_blb = enemy_points_blb;
    }

    public int getEnemy_points_brt() {
        return enemy_points_brt;
    }

    public void setEnemy_points_brt(int enemy_points_brt) {
        this.enemy_points_brt = enemy_points_brt;
    }

    public int getEnemy_points_brc() {
        return enemy_points_brc;
    }

    public void setEnemy_points_brc(int enemy_points_brc) {
        this.enemy_points_brc = enemy_points_brc;
    }

    public int getEnemy_points_brb() {
        return enemy_points_brb;
    }

    public void setEnemy_points_brb(int enemy_points_brb) {
        this.enemy_points_brb = enemy_points_brb;
    }
}
