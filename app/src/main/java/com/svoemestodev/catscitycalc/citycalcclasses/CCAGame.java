package com.svoemestodev.catscitycalc.citycalcclasses;

import android.content.Context;

import com.svoemestodev.catscitycalc.GlobalApplication;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.activities.GameActivity;
import com.svoemestodev.catscitycalc.classes.LastModified;
import com.svoemestodev.catscitycalc.database.DbTeamGame;
import com.svoemestodev.catscitycalc.ssa.SSA_Area;
import com.svoemestodev.catscitycalc.ssa.SSA_Key;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CCAGame extends CityCalcArea {

    private Date dateStartGame;     // дата начала игры
    private Date dateScreenshot;    // дата создания скриншота
    private Date dateCurrent;       // дата текущая
    private Date dateEarlyWin;      // дата досрочного окончания игры
    private Date dateEndGame;       // дата окончания игры по времени
    private Date dateFinal;         // дата реального окончания игры
    private int earlyWin;           // нужно очков до досрочной победы
    private int source;             // 0 - скриншот, 1 - сервер, 2 - датафайл

    private String userNIC;
    private String userUID;
    private String teamID;
    private File screenshotFile;

    private int pointsOurInScreenshot;
    private int pointsEnemyInScreenshot;
    private int increaseOur;
    private int increaseEnemy;

    private CCABuilding[] buildings = new CCABuilding[6];

    private boolean isGameOver;         // игра окончена
    private boolean isGameOverEarly;    // игра окончена досрочно
    private boolean isWinOur;           // победили мы
    private boolean isWinEnemy;         // победил противник
    private boolean isWinNobody;        // ничья
    private boolean isErrorRecognize;

    private boolean willEarlyWin;
    private boolean willOurWin;
    private boolean willEnemyWin;
    private boolean willNobodyWin;

    private int differentPoints;

    private int countOurX2;
    private int countEnemyX2;
    private int countX2;
    private int personsOur;
    private int personsEnemy;
    private int personsTotal;
    private int slotsTotal;
    private int slotsOur;
    private int slotsEnemy;

    private boolean canWin;
    private boolean canWinWithoutX2;
    private boolean canEarlyWin;
    private boolean canEarlyWinWithoutX2;

    private String status;

    private String forecastText = "";

    private byte[] bytesScreenshot = null;

    public CCAGame(CityCalc cityCalc, SSA_Area ssaArea) { //} Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr, boolean needBW) {
        super(cityCalc, ssaArea); //area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
        if (cityCalc.getFileScreenshot() != null) {
            if (cityCalc.getFileScreenshot().exists()) {
//                this.dateScreenshot = new Date((cityCalc.getFileScreenshot().lastModified() / 60_000) * 60_000); // дата/время создания скриншота с точностью до минуты
                this.screenshotFile = cityCalc.getFileScreenshot();
                this.dateScreenshot = LastModified.getLastModified(this.screenshotFile);
                this.userNIC = cityCalc.getUserNIC();
            }
        }
        if (GameActivity.fbUser != null) {
            if (GameActivity.fbUser.isEmailVerified()) {
                this.userUID = GameActivity.fbUser.getUid();
                if (GameActivity.mainDbTeam != null) {
                    this.teamID = GameActivity.mainDbTeam.getTeamID();
                }
            }
        }

        CCABuilding[] ccaBuildings = new CCABuilding[6];
        for (int buildingIndex = 0; buildingIndex < 6; buildingIndex++) {
            ccaBuildings[buildingIndex] = new CCABuilding();
        }
        this.setBuildings(ccaBuildings);

    }

    public CCAGame() {

        CCABuilding[] ccaBuildings = new CCABuilding[6];
        for (int buildingIndex = 0; buildingIndex < 6; buildingIndex++) {
            ccaBuildings[buildingIndex] = new CCABuilding();
        }
        this.setBuildings(ccaBuildings);
    }

    public CCAGame getClone(CityCalc parent) {
        
        CCAGame clone = new CCAGame();

        clone.setCityCalc(parent);
        clone.setSsaArea(this.getSsaArea().getClone());
        clone.setBmpSrc(this.getBmpSrc());
        clone.setBmpPrc(this.getBmpPrc());
        clone.setOcrText(this.getOcrText());
        clone.setFinText(this.getFinText());

        clone.dateStartGame = this.dateStartGame;
        clone.dateScreenshot = this.dateScreenshot;
        clone.dateCurrent = this.dateCurrent;
        clone.dateEarlyWin = this.dateEarlyWin;
        clone.dateEndGame = this.dateEndGame;
        clone.dateFinal = this.dateFinal;
        clone.earlyWin = this.earlyWin;
        clone.source = this.source;
        clone.userNIC = this.userNIC;
        clone.userUID = this.userUID;
        clone.teamID = this.teamID;
        clone.screenshotFile = this.screenshotFile;
        clone.pointsOurInScreenshot = this.pointsOurInScreenshot;
        clone.pointsEnemyInScreenshot = this.pointsEnemyInScreenshot;
        clone.increaseOur = this.increaseOur;
        clone.increaseEnemy = this.increaseEnemy;

        for (int index = 0; index < 6; index++) {
            clone.buildings[index].setPresent(this.buildings[index].isPresent());
            clone.buildings[index].setName(this.buildings[index].getName());
            clone.buildings[index].setX2(this.buildings[index].isX2());
            clone.buildings[index].setMayX2(this.buildings[index].isMayX2());
            clone.buildings[index].setBuildingIsOur(this.buildings[index].isBuildingIsOur());
            clone.buildings[index].setBuildingIsEnemy(this.buildings[index].isBuildingIsEnemy());
            clone.buildings[index].setBuildingIsEmpty(this.buildings[index].isBuildingIsEmpty());
            clone.buildings[index].setOur_points(this.buildings[index].getOur_points());
            clone.buildings[index].setEnemy_points(this.buildings[index].getEnemy_points());
            clone.buildings[index].setSlots(this.buildings[index].getSlots());
            clone.buildings[index].setSlots_our(this.buildings[index].getSlots_our());
            clone.buildings[index].setSlots_enemy(this.buildings[index].getSlots_enemy());
            clone.buildings[index].setSlots_empty(this.buildings[index].getSlots_empty());
            clone.buildings[index].setNeedToWin(this.buildings[index].isNeedToWin());
            clone.buildings[index].setNeedToWinWithoutX2(this.buildings[index].isNeedToWinWithoutX2());
            clone.buildings[index].setNeedToEarlyWin(this.buildings[index].isNeedToEarlyWin());
            clone.buildings[index].setNeedToEarlyWinWithoutX2(this.buildings[index].isNeedToEarlyWinWithoutX2());
            clone.buildings[index].setUseInForecast(this.buildings[index].isUseInForecast());
        }
        

        clone.isGameOver = this.isGameOver;
        clone.isGameOverEarly = this.isGameOverEarly;
        clone.isWinOur = this.isWinOur;
        clone.isWinEnemy = this.isWinEnemy;
        clone.isWinNobody = this.isWinNobody;
        clone.isErrorRecognize = this.isErrorRecognize;
        clone.willEarlyWin = this.willEarlyWin;
        clone.willOurWin = this.willOurWin;
        clone.willEnemyWin = this.willEnemyWin;
        clone.willNobodyWin = this.willNobodyWin;
        clone.differentPoints = this.differentPoints;
        clone.countOurX2 = this.countOurX2;
        clone.countEnemyX2 = this.countEnemyX2;
        clone.countX2 = this.countX2;
        clone.personsOur = this.personsOur;
        clone.personsEnemy = this.personsEnemy;
        clone.personsTotal = this.personsTotal;
        clone.slotsTotal = this.slotsTotal;
        clone.slotsOur = this.slotsOur;
        clone.slotsEnemy = this.slotsEnemy;
        clone.status = this.status;
        clone.bytesScreenshot = this.bytesScreenshot;
        clone.canWin = this.canWin;
        clone.canWinWithoutX2 = this.canWinWithoutX2;
        clone.canEarlyWin = this.canEarlyWin;
        clone.canEarlyWinWithoutX2 = this.canEarlyWinWithoutX2;
        
        return clone;
    }
    
    
    public void updateFromDb(DbTeamGame dbTeamGame) {

        this.source = 1;
        this.userNIC = dbTeamGame.getUserNIC();
        this.userUID = dbTeamGame.getUserUID();
        this.teamID = dbTeamGame.getTeamID();
        this.dateStartGame = dbTeamGame.getDateStartGame();
        this.dateScreenshot = dbTeamGame.getDateScreenshot();
        this.dateEndGame = dbTeamGame.getDateEndGame();
        this.earlyWin = dbTeamGame.getEarlyWin();

        this.pointsOurInScreenshot = dbTeamGame.getPointsOurInScreenshot();
        this.pointsEnemyInScreenshot = dbTeamGame.getPointsEnemyInScreenshot();
        this.increaseOur = dbTeamGame.getIncreaseOur();
        this.increaseEnemy = dbTeamGame.getIncreaseEnemy();

        int index = 0;
        this.buildings[index].setPresent(dbTeamGame.isPresent_blt());
        this.buildings[index].setX2(dbTeamGame.isX2_blt());
        this.buildings[index].setMayX2(dbTeamGame.isMayX2_blt());
        this.buildings[index].setBuildingIsOur(dbTeamGame.isBuildingIsOur_blt());
        this.buildings[index].setBuildingIsEnemy(dbTeamGame.isBuildingIsEnemy_blt());
        this.buildings[index].setBuildingIsEmpty(dbTeamGame.isBuildingIsEmpty_blt());
        this.buildings[index].setOur_points(dbTeamGame.getOur_points_blt());
        this.buildings[index].setEnemy_points(dbTeamGame.getEnemy_points_blt());
        this.buildings[index].setSlots(dbTeamGame.getSlots_blt());
        this.buildings[index].setSlots_our(dbTeamGame.getSlots_blt_our());
        this.buildings[index].setSlots_enemy(dbTeamGame.getSlots_blt_enemy());
        this.buildings[index].setSlots_empty(dbTeamGame.getSlots_blt_empty());
        this.buildings[index].setNeedToWin(dbTeamGame.isNeedToWin_blt());
        this.buildings[index].setNeedToWinWithoutX2(dbTeamGame.isNeedToWinWithoutX2_blt());
        this.buildings[index].setNeedToEarlyWin(dbTeamGame.isNeedToEarlyWin_blt());
        this.buildings[index].setNeedToEarlyWinWithoutX2(dbTeamGame.isNeedToEarlyWinWithoutX2_blt());
        this.buildings[index].setUseInForecast(dbTeamGame.isUseInForecast_blt());

        index = 1;
        this.buildings[index].setPresent(dbTeamGame.isPresent_blc());
        this.buildings[index].setX2(dbTeamGame.isX2_blc());
        this.buildings[index].setMayX2(dbTeamGame.isMayX2_blc());
        this.buildings[index].setBuildingIsOur(dbTeamGame.isBuildingIsOur_blc());
        this.buildings[index].setBuildingIsEnemy(dbTeamGame.isBuildingIsEnemy_blc());
        this.buildings[index].setBuildingIsEmpty(dbTeamGame.isBuildingIsEmpty_blc());
        this.buildings[index].setOur_points(dbTeamGame.getOur_points_blc());
        this.buildings[index].setEnemy_points(dbTeamGame.getEnemy_points_blc());
        this.buildings[index].setSlots(dbTeamGame.getSlots_blc());
        this.buildings[index].setSlots_our(dbTeamGame.getSlots_blc_our());
        this.buildings[index].setSlots_enemy(dbTeamGame.getSlots_blc_enemy());
        this.buildings[index].setSlots_empty(dbTeamGame.getSlots_blc_empty());
        this.buildings[index].setNeedToWin(dbTeamGame.isNeedToWin_blc());
        this.buildings[index].setNeedToWinWithoutX2(dbTeamGame.isNeedToWinWithoutX2_blc());
        this.buildings[index].setNeedToEarlyWin(dbTeamGame.isNeedToEarlyWin_blc());
        this.buildings[index].setNeedToEarlyWinWithoutX2(dbTeamGame.isNeedToEarlyWinWithoutX2_blc());
        this.buildings[index].setUseInForecast(dbTeamGame.isUseInForecast_blc());

        index = 2;
        this.buildings[index].setPresent(dbTeamGame.isPresent_blb());
        this.buildings[index].setX2(dbTeamGame.isX2_blb());
        this.buildings[index].setMayX2(dbTeamGame.isMayX2_blb());
        this.buildings[index].setBuildingIsOur(dbTeamGame.isBuildingIsOur_blb());
        this.buildings[index].setBuildingIsEnemy(dbTeamGame.isBuildingIsEnemy_blb());
        this.buildings[index].setBuildingIsEmpty(dbTeamGame.isBuildingIsEmpty_blb());
        this.buildings[index].setOur_points(dbTeamGame.getOur_points_blb());
        this.buildings[index].setEnemy_points(dbTeamGame.getEnemy_points_blb());
        this.buildings[index].setSlots(dbTeamGame.getSlots_blb());
        this.buildings[index].setSlots_our(dbTeamGame.getSlots_blb_our());
        this.buildings[index].setSlots_enemy(dbTeamGame.getSlots_blb_enemy());
        this.buildings[index].setSlots_empty(dbTeamGame.getSlots_blb_empty());
        this.buildings[index].setNeedToWin(dbTeamGame.isNeedToWin_blb());
        this.buildings[index].setNeedToWinWithoutX2(dbTeamGame.isNeedToWinWithoutX2_blb());
        this.buildings[index].setNeedToEarlyWin(dbTeamGame.isNeedToEarlyWin_blb());
        this.buildings[index].setNeedToEarlyWinWithoutX2(dbTeamGame.isNeedToEarlyWinWithoutX2_blb());
        this.buildings[index].setUseInForecast(dbTeamGame.isUseInForecast_blb());

        index = 3;
        this.buildings[index].setPresent(dbTeamGame.isPresent_brt());
        this.buildings[index].setX2(dbTeamGame.isX2_brt());
        this.buildings[index].setMayX2(dbTeamGame.isMayX2_brt());
        this.buildings[index].setBuildingIsOur(dbTeamGame.isBuildingIsOur_brt());
        this.buildings[index].setBuildingIsEnemy(dbTeamGame.isBuildingIsEnemy_brt());
        this.buildings[index].setBuildingIsEmpty(dbTeamGame.isBuildingIsEmpty_brt());
        this.buildings[index].setOur_points(dbTeamGame.getOur_points_brt());
        this.buildings[index].setEnemy_points(dbTeamGame.getEnemy_points_brt());
        this.buildings[index].setSlots(dbTeamGame.getSlots_brt());
        this.buildings[index].setSlots_our(dbTeamGame.getSlots_brt_our());
        this.buildings[index].setSlots_enemy(dbTeamGame.getSlots_brt_enemy());
        this.buildings[index].setSlots_empty(dbTeamGame.getSlots_brt_empty());
        this.buildings[index].setNeedToWin(dbTeamGame.isNeedToWin_brt());
        this.buildings[index].setNeedToWinWithoutX2(dbTeamGame.isNeedToWinWithoutX2_brt());
        this.buildings[index].setNeedToEarlyWin(dbTeamGame.isNeedToEarlyWin_brt());
        this.buildings[index].setNeedToEarlyWinWithoutX2(dbTeamGame.isNeedToEarlyWinWithoutX2_brt());
        this.buildings[index].setUseInForecast(dbTeamGame.isUseInForecast_brt());

        index = 4;
        this.buildings[index].setPresent(dbTeamGame.isPresent_brc());
        this.buildings[index].setX2(dbTeamGame.isX2_brc());
        this.buildings[index].setMayX2(dbTeamGame.isMayX2_brc());
        this.buildings[index].setBuildingIsOur(dbTeamGame.isBuildingIsOur_brc());
        this.buildings[index].setBuildingIsEnemy(dbTeamGame.isBuildingIsEnemy_brc());
        this.buildings[index].setBuildingIsEmpty(dbTeamGame.isBuildingIsEmpty_brc());
        this.buildings[index].setOur_points(dbTeamGame.getOur_points_brc());
        this.buildings[index].setEnemy_points(dbTeamGame.getEnemy_points_brc());
        this.buildings[index].setSlots(dbTeamGame.getSlots_brc());
        this.buildings[index].setSlots_our(dbTeamGame.getSlots_brc_our());
        this.buildings[index].setSlots_enemy(dbTeamGame.getSlots_brc_enemy());
        this.buildings[index].setSlots_empty(dbTeamGame.getSlots_brc_empty());
        this.buildings[index].setNeedToWin(dbTeamGame.isNeedToWin_brc());
        this.buildings[index].setNeedToWinWithoutX2(dbTeamGame.isNeedToWinWithoutX2_brc());
        this.buildings[index].setNeedToEarlyWin(dbTeamGame.isNeedToEarlyWin_brc());
        this.buildings[index].setNeedToEarlyWinWithoutX2(dbTeamGame.isNeedToEarlyWinWithoutX2_brc());
        this.buildings[index].setUseInForecast(dbTeamGame.isUseInForecast_brc());

        index = 5;
        this.buildings[index].setPresent(dbTeamGame.isPresent_brb());
        this.buildings[index].setX2(dbTeamGame.isX2_brb());
        this.buildings[index].setMayX2(dbTeamGame.isMayX2_brb());
        this.buildings[index].setBuildingIsOur(dbTeamGame.isBuildingIsOur_brb());
        this.buildings[index].setBuildingIsEnemy(dbTeamGame.isBuildingIsEnemy_brb());
        this.buildings[index].setBuildingIsEmpty(dbTeamGame.isBuildingIsEmpty_brb());
        this.buildings[index].setOur_points(dbTeamGame.getOur_points_brb());
        this.buildings[index].setEnemy_points(dbTeamGame.getEnemy_points_brb());
        this.buildings[index].setSlots(dbTeamGame.getSlots_brb());
        this.buildings[index].setSlots_our(dbTeamGame.getSlots_brb_our());
        this.buildings[index].setSlots_enemy(dbTeamGame.getSlots_brb_enemy());
        this.buildings[index].setSlots_empty(dbTeamGame.getSlots_brb_empty());
        this.buildings[index].setNeedToWin(dbTeamGame.isNeedToWin_brb());
        this.buildings[index].setNeedToWinWithoutX2(dbTeamGame.isNeedToWinWithoutX2_brb());
        this.buildings[index].setNeedToEarlyWin(dbTeamGame.isNeedToEarlyWin_brb());
        this.buildings[index].setNeedToEarlyWinWithoutX2(dbTeamGame.isNeedToEarlyWinWithoutX2_brb());
        this.buildings[index].setUseInForecast(dbTeamGame.isUseInForecast_brb());

        this.countOurX2 = dbTeamGame.getCountOurX2();
        this.countEnemyX2 = dbTeamGame.getCountEnemyX2();
        this.countX2 = dbTeamGame.getCountX2();
        this.personsOur = dbTeamGame.getPersonsOur();
        this.personsEnemy = dbTeamGame.getPersonsEnemy();
        this.personsTotal = dbTeamGame.getPersonsTotal();
        this.slotsTotal = dbTeamGame.getSlotsTotal();
        this.slotsOur = dbTeamGame.getSlotsOur();
        this.slotsEnemy = dbTeamGame.getSlotsEnemy();

        this.canWin = dbTeamGame.isCanWin();
        this.canWinWithoutX2 = dbTeamGame.isCanWinWithoutX2();
        this.canEarlyWin = dbTeamGame.isCanEarlyWin();
        this.canEarlyWinWithoutX2 = dbTeamGame.isCanEarlyWinWithoutX2();

        
        this.bytesScreenshot = dbTeamGame.getBytesScreenshot();

        calcWin(true);

    }

    public void doForecast() {

        List<ForecastMatrix> list = new ArrayList<>();

        CityCalc cityCalcClone = this.getCityCalc().getClone();

        for (int x1 = 0; x1 <= 1; x1++) {
            for (int x2 = 0; x2 <= 1; x2++) {
                for (int x3 = 0; x3 <= 1; x3++) {
                    for (int x4 = 0; x4 <= 1; x4++) {
                        for (int x5 = 0; x5 <= 1; x5++) {
                            for (int x6 = 0; x6 <= 1; x6++) {
                                CCAGame ccaGame = this.getClone(cityCalcClone);

                                list.add(new ForecastMatrix(ccaGame,
                                        x1==1 && ccaGame.buildings[0].isUseInForecast(),
                                        x2==1 && ccaGame.buildings[1].isUseInForecast(),
                                        x3==1 && ccaGame.buildings[2].isUseInForecast(),
                                        x4==1 && ccaGame.buildings[3].isUseInForecast(),
                                        x5==1 && ccaGame.buildings[4].isUseInForecast(),
                                        x6==1 && ccaGame.buildings[5].isUseInForecast()));
                                
                                
                            }
                        }
                    }
                }
            }
        }

        Object[] forecastMatrixArray = list.toArray();
        Arrays.sort(forecastMatrixArray);
        ForecastMatrix matrix = null;
        boolean isFound = false;
        for (int i = 0; i < forecastMatrixArray.length; i++) {
            matrix = (ForecastMatrix) forecastMatrixArray[i];
            if (matrix.ccaGame.willOurWin && !matrix.ccaGame.willEarlyWin) {
                isFound = true;
                break;
            }
        }
        this.canWin = isFound;
        for (int index = 0; index < 6; index++) {
            this.buildings[index].setNeedToWin(isFound && matrix.ccaGame.buildings[index].isBuildingIsOur());
        }

        isFound = false;
        for (int i = 0; i < forecastMatrixArray.length; i++) {
            matrix = (ForecastMatrix) forecastMatrixArray[i];
            if (matrix.ccaGame.willOurWin && !matrix.ccaGame.willEarlyWin && matrix.ccaGame.countOurX2 == 0) {
                isFound = true;
                break;
            }
        }

        this.canWinWithoutX2 = isFound;
        for (int index = 0; index < 6; index++) {
            this.buildings[index].setNeedToWinWithoutX2(isFound && matrix.ccaGame.buildings[index].isBuildingIsOur());
        }

        isFound = false;
        for (int i = 0; i < forecastMatrixArray.length; i++) {
            matrix = (ForecastMatrix) forecastMatrixArray[i];
            if (matrix.ccaGame.willOurWin && matrix.ccaGame.willEarlyWin) {
                isFound = true;
                break;
            }
        }

        this.canEarlyWin = isFound;
        for (int index = 0; index < 6; index++) {
            this.buildings[index].setNeedToEarlyWin(isFound && matrix.ccaGame.buildings[index].isBuildingIsOur());
        }

        isFound = false;
        for (int i = 0; i < forecastMatrixArray.length; i++) {
            matrix = (ForecastMatrix) forecastMatrixArray[i];
            if (matrix.ccaGame.willOurWin && matrix.ccaGame.willEarlyWin && matrix.ccaGame.countOurX2 == 0) {
                isFound = true;
                break;
            }
        }

        this.canEarlyWinWithoutX2 = isFound;
        for (int index = 0; index < 6; index++) {
            this.buildings[index].setNeedToEarlyWinWithoutX2(isFound && matrix.ccaGame.buildings[index].isBuildingIsOur());
        }

    }

    class ForecastMatrix implements Comparable<ForecastMatrix>{
        boolean[] isOur = new boolean[6];
        CCAGame ccaGame;

        public ForecastMatrix(CCAGame ccaGame, boolean isOur_bld1, boolean isOur_bld2, boolean isOur_bld3, boolean isOur_bld4, boolean isOur_bld5, boolean isOur_bld6) {

            this.isOur[0] = isOur_bld1;
            this.isOur[1] = isOur_bld2;
            this.isOur[2] = isOur_bld3;
            this.isOur[3] = isOur_bld4;
            this.isOur[4] = isOur_bld5;
            this.isOur[5] = isOur_bld6;
            this.ccaGame = ccaGame;

            int countCars_our = 0;
            int countCars_enemy = 0;
            int countCars_total = 0;
            int countCarsInBuilding = 0;

            int countX2_our = 0;
            int countX2_enemy = 0;
            int countX2_total = 0;
            int increaseOur = 0;
            int increaseEnemy = 0;
            
            int pointsOur = ccaGame.getPointsOur();
            int pointsEnemy = ccaGame.getPointsEnemy();

            for (int index = 0; index < 6; index++) {
                if (ccaGame.buildings[index].isPresent()) {
                    countCars_total += ccaGame.buildings[index].getSlots();
                    countCarsInBuilding = (ccaGame.buildings[index].getSlots() / 2 + 1);
                    ccaGame.buildings[index].setBuildingIsOur(isOur[index]);
                    ccaGame.buildings[index].setBuildingIsEnemy(!isOur[index]);
                    ccaGame.buildings[index].setBuildingIsEmpty(false);
                    ccaGame.buildings[index].setSlots_our(countCarsInBuilding * (isOur[index] ? 1 : 0));
                    ccaGame.buildings[index].setSlots_enemy(countCarsInBuilding * (!isOur[index] ? 1 : 0));
                    ccaGame.buildings[index].setSlots_empty(0);
                    countCars_our += isOur[index] ? countCarsInBuilding : 0;
                    countCars_enemy += !isOur[index] ? countCarsInBuilding : 0;
                    countX2_total += ccaGame.buildings[index].isMayX2() ? 1 : 0;
                    countX2_our += ccaGame.buildings[index].isMayX2() && isOur[index] ? 1 : 0;
                    countX2_enemy += ccaGame.buildings[index].isMayX2() && !isOur[index] ? 1 : 0;
                }
            }

            for (int index = 0; index < 6; index++) {
                if (ccaGame.buildings[index].isPresent()) {
                    ccaGame.buildings[index].setX2(ccaGame.buildings[index].isMayX2() && ((countX2_our == countX2_total) || (countX2_enemy == countX2_total)));
                    ccaGame.buildings[index].setOur_points(isOur[index] ? ccaGame.buildings[index].getSlots() * (ccaGame.buildings[index].isX2() ? 2 : 1) : 0);
                    ccaGame.buildings[index].setEnemy_points(!isOur[index] ? ccaGame.buildings[index].getSlots() * (ccaGame.buildings[index].isX2() ? 2 : 1) : 0);
                    increaseOur += ccaGame.buildings[index].getOur_points();
                    increaseEnemy += ccaGame.buildings[index].getEnemy_points();
                }
            }

            ccaGame.increaseOur = increaseOur;
            ccaGame.increaseEnemy = increaseEnemy;
            ccaGame.slotsOur = countCars_our;
            ccaGame.slotsEnemy = countCars_enemy;
            ccaGame.slotsTotal = countCars_total;

            ccaGame.countX2 = countX2_total;
            ccaGame.countOurX2 = countX2_our;
            ccaGame.countEnemyX2 = countX2_enemy;

            ccaGame.personsOur = (int)Math.ceil (ccaGame.slotsOur / 3.0d);
            ccaGame.personsEnemy = (int)Math.ceil (ccaGame.slotsEnemy / 3.0d);

            ccaGame.pointsOurInScreenshot = pointsOur;
            ccaGame.pointsEnemyInScreenshot = pointsEnemy;
            ccaGame.dateScreenshot = Calendar.getInstance().getTime();

//            ccaGame.calc(false);
            ccaGame.calcWin(false);

        }

        @Override
        public int compareTo(ForecastMatrix o) {
            Integer a, b, compare;
            a = this.ccaGame.personsOur;
            b = o.ccaGame.personsOur;
            compare = a.compareTo(b);
            if (compare == 0) {
                a = this.ccaGame.differentPoints;
                b = o.ccaGame.differentPoints;
                compare = a.compareTo(b);
            }
            return compare;
        }
    }


    public void calc(boolean isRealtimeScreenshot) {

        CityCalcArea ccaTotalTime = this.getCityCalc().getMapAreas().get(SSA_Key.AREA_CITY_TIME.getKey()); // время
        CityCalcArea ccaEarlyWin = this.getCityCalc().getMapAreas().get(SSA_Key.AREA_CITY_EARLY_WIN.getKey());   // очки досрочки
        int minFromStartToScreenshot = 0;
        
        if (ccaTotalTime != null && ccaEarlyWin != null) {

            ccaTotalTime.setFinText(Utils.parseTime(ccaTotalTime.getOcrText()));
            ccaEarlyWin.setFinText(Utils.parseNumbers(ccaEarlyWin.getOcrText()));

            String[] words = ccaTotalTime.getFinText().split(":"); // разделяем строку на часы и минуты
            if (words.length == 2) {
                minFromStartToScreenshot = 24*60 - (Integer.parseInt(words[0])* 60 + Integer.parseInt(words[1]));// прошло минут с начала игры по скриншоту
            } else {
                this.isErrorRecognize = true;
            }

            this.dateStartGame = Utils.addMinutesToDate(this.dateScreenshot, -minFromStartToScreenshot); // дата начала игры
            this.dateEndGame = Utils.addMinutesToDate(this.dateStartGame, 24*60); // дата конца игры по времени
            this.earlyWin = Integer.parseInt(ccaEarlyWin.getFinText()); // очки до досрочной победы

            this.countOurX2 = ((this.buildings[0].isPresent() && this.buildings[0].isMayX2() && this.buildings[0].isBuildingIsOur()) ? 1 : 0) +
                    ((this.buildings[1].isPresent() && this.buildings[1].isMayX2() && this.buildings[1].isBuildingIsOur()) ? 1 : 0) +
                    ((this.buildings[2].isPresent() && this.buildings[2].isMayX2() && this.buildings[2].isBuildingIsOur()) ? 1 : 0) +
                    ((this.buildings[3].isPresent() && this.buildings[3].isMayX2() && this.buildings[3].isBuildingIsOur()) ? 1 : 0) +
                    ((this.buildings[4].isPresent() && this.buildings[4].isMayX2() && this.buildings[4].isBuildingIsOur()) ? 1 : 0) +
                    ((this.buildings[5].isPresent() && this.buildings[5].isMayX2() && this.buildings[5].isBuildingIsOur()) ? 1 : 0);

            this.countEnemyX2 = ((this.buildings[0].isPresent() && this.buildings[0].isMayX2() && this.buildings[0].isBuildingIsEnemy()) ? 1 : 0) +
                    ((this.buildings[1].isPresent() && this.buildings[1].isMayX2() && this.buildings[1].isBuildingIsEnemy()) ? 1 : 0) +
                    ((this.buildings[2].isPresent() && this.buildings[2].isMayX2() && this.buildings[2].isBuildingIsEnemy()) ? 1 : 0) +
                    ((this.buildings[3].isPresent() && this.buildings[3].isMayX2() && this.buildings[3].isBuildingIsEnemy()) ? 1 : 0) +
                    ((this.buildings[4].isPresent() && this.buildings[4].isMayX2() && this.buildings[4].isBuildingIsEnemy()) ? 1 : 0) +
                    ((this.buildings[5].isPresent() && this.buildings[5].isMayX2() && this.buildings[5].isBuildingIsEnemy()) ? 1 : 0);
            
            this.countX2 = ((this.buildings[0].isPresent() && this.buildings[0].isMayX2()) ? 1 : 0) +
                    ((this.buildings[1].isPresent() && this.buildings[1].isMayX2()) ? 1 : 0) +
                    ((this.buildings[2].isPresent() && this.buildings[2].isMayX2()) ? 1 : 0) +
                    ((this.buildings[3].isPresent() && this.buildings[3].isMayX2()) ? 1 : 0) +
                    ((this.buildings[4].isPresent() && this.buildings[4].isMayX2()) ? 1 : 0) +
                    ((this.buildings[5].isPresent() && this.buildings[5].isMayX2()) ? 1 : 0);

            this.slotsTotal = (this.buildings[0].isPresent() ? this.buildings[0].getSlots() : 0) +
                    (this.buildings[1].isPresent() ? this.buildings[1].getSlots() : 0) +
                    (this.buildings[2].isPresent() ? this.buildings[2].getSlots() : 0) +
                    (this.buildings[3].isPresent() ? this.buildings[3].getSlots() : 0) +
                    (this.buildings[4].isPresent() ? this.buildings[4].getSlots() : 0) +
                    (this.buildings[5].isPresent() ? this.buildings[5].getSlots() : 0);

            this.slotsOur = (this.buildings[0].isPresent() ? this.buildings[0].getSlots_our() : 0) +
                    (this.buildings[1].isPresent() ? this.buildings[1].getSlots_our() : 0) +
                    (this.buildings[2].isPresent() ? this.buildings[2].getSlots_our() : 0) +
                    (this.buildings[3].isPresent() ? this.buildings[3].getSlots_our() : 0) +
                    (this.buildings[4].isPresent() ? this.buildings[4].getSlots_our() : 0) +
                    (this.buildings[5].isPresent() ? this.buildings[5].getSlots_our() : 0);

            this.slotsEnemy = (this.buildings[0].isPresent() ? this.buildings[0].getSlots_enemy() : 0) +
                    (this.buildings[1].isPresent() ? this.buildings[1].getSlots_enemy() : 0) +
                    (this.buildings[2].isPresent() ? this.buildings[2].getSlots_enemy() : 0) +
                    (this.buildings[3].isPresent() ? this.buildings[3].getSlots_enemy() : 0) +
                    (this.buildings[4].isPresent() ? this.buildings[4].getSlots_enemy() : 0) +
                    (this.buildings[5].isPresent() ? this.buildings[5].getSlots_enemy() : 0);

            this.personsOur = (int)Math.ceil (this.slotsOur / 3.0d);
            this.personsEnemy = (int)Math.ceil (this.slotsEnemy / 3.0d);
            this.personsTotal = (int)Math.ceil (this.slotsTotal / 3.0d);
            
        }
        if (isRealtimeScreenshot) new DbTeamGame(this);

    }

    public int getMinutesToFinalGame() {
        int minutes = Utils.getMinutesBetweenDates(this.dateCurrent, this.dateFinal);
        if (minutes < 0) minutes = 0;
        return minutes;
    }

    public int getMinutesToEndGame() {
        int minutes = Utils.getMinutesBetweenDates(this.dateCurrent, this.dateEndGame);
        if (minutes < 0) minutes = 0;
        return minutes;
    }

    /**
     * Возвращаем количество очков на дату. Не может быть меньше нуля или больше очков досрочки
     * @param date - дата
     * @return - очки
     */
    public int getPointsOur(Date date) {
        int minutes = Utils.getMinutesBetweenDates(this.getDateScreenshot(), date);
        int points = this.pointsOurInScreenshot + this.increaseOur * minutes;
        if (points < 0) points = 0;
        if (points > this.getEarlyWin()) points = this.getEarlyWin();
        return points;
    }

    public int getPointsOur() {
        Date date = this.getDateFinal().getTime() > this.getDateCurrent().getTime() ? this.getDateCurrent() : this.getDateFinal();
        return getPointsOur(date);
    }

    public int getPointsEnemy(Date date) {
        int minutes = Utils.getMinutesBetweenDates(this.getDateScreenshot(), date);
        int points = this.pointsEnemyInScreenshot + this.increaseEnemy * minutes;
        if (points < 0) points = 0;
        if (points > this.getEarlyWin()) points = this.getEarlyWin();
        return points;
    }

    public int getPointsEnemy() {
        Date date = this.getDateFinal().getTime() > this.getDateCurrent().getTime() ? this.getDateCurrent() : this.getDateFinal();
        return getPointsEnemy(date);
    }

    /**
     * @return - дата досрочной победы. Если она невозможно - +месяц он начала игры
     */
    public Date getDateEarlyWinOur() {
        if (this.increaseOur == 0) {
            return new Date(this.getDateStartGame().getTime() + 25*60*60*1000);
        } else {
            return Utils.addMinutesToDate(this.getDateScreenshot(), (this.getEarlyWin() - this.pointsOurInScreenshot) / this.increaseOur);
        }
    }

    public Date getDateEarlyWinEnemy() {
        if (this.increaseEnemy == 0) {
            return new Date(this.getDateStartGame().getTime() + 25*60*60*1000);
        } else {
            return Utils.addMinutesToDate(this.getDateScreenshot(), (this.getEarlyWin() - this.pointsEnemyInScreenshot) / this.increaseEnemy);
        }
    }

    public void calcWin(boolean doForecast) {

        String pattern = "dd MMM HH:mm";
        Context context = GlobalApplication.getAppContext();

        this.dateCurrent = new Date((Calendar.getInstance().getTime().getTime() / 60_000) * 60_000); // текущая дата
        Date dateEarlyOutTeam = getDateEarlyWinOur(); // дата досрочной победы нашей команды
        Date dateEarlyEnemyTeam = getDateEarlyWinEnemy(); // дата досрочной победы команды противника
        this.willEarlyWin = (dateEarlyOutTeam.getTime() < this.dateEndGame.getTime() || dateEarlyEnemyTeam.getTime() < this.dateEndGame.getTime()); // будет ли досрочная победа
        if (this.willEarlyWin) { // если будет досрочная победа
            this.willOurWin = (dateEarlyOutTeam.getTime() < dateEarlyEnemyTeam.getTime()); // будет ли наша досрочная победа
            this.willEnemyWin = (dateEarlyOutTeam.getTime() > dateEarlyEnemyTeam.getTime()); // будет ли досрочная победа противника
            this.willNobodyWin = (dateEarlyOutTeam.getTime() == dateEarlyEnemyTeam.getTime()); // будет ли досрочная ничья
            this.dateFinal = this.willOurWin || this.willNobodyWin ? dateEarlyOutTeam : dateEarlyEnemyTeam;
        } else {
            this.dateFinal = this.dateEndGame;
            this.willOurWin = (getPointsOur(this.dateFinal) > getPointsEnemy(this.dateFinal)); // будет ли наша победа
            this.willEnemyWin = (getPointsOur(this.dateFinal) < getPointsEnemy(this.dateFinal)); // будет ли победа противника
            this.willNobodyWin = (getPointsOur(this.dateFinal) == getPointsEnemy(this.dateFinal)); // будет ли ничья
        }

        this.isGameOver = this.dateFinal.getTime() <= this.dateCurrent.getTime(); // закончилась ли игра
        this.isGameOverEarly = this.isGameOver && this.willEarlyWin; // закончилась ли игра досрочно

        if (this.isGameOver) { // если игра закончилась
            this.isWinOur = getPointsOur(this.dateFinal) > getPointsEnemy(this.dateFinal); // победили ли мы
            this.isWinEnemy = getPointsOur(this.dateFinal) < getPointsEnemy(this.dateFinal); // победил ли противник
            this.isWinNobody = getPointsOur(this.dateFinal) == getPointsEnemy(this.dateFinal); // была ли ничья
        } else {
            this.isWinOur = false;
            this.isWinEnemy = false;
            this.isWinNobody = false;
        }

        differentPoints = Math.abs(getPointsOur(this.dateFinal) - getPointsEnemy(this.dateFinal)); // разница в очках на момент окончания игры

        if (this.isGameOver) { // игра закончена
            if (this.isGameOverEarly) { // игра закончена досрочно
                if (this.isWinOur) { // досрочная наша победа
                    this.status = context.getString(R.string.we_instance_win)  + " " + differentPoints + " " + context.getString(R.string.points) + "\n(" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.isWinEnemy) { // досрочная победа противника
                    this.status = context.getString(R.string.we_instance_lose)  + " " + differentPoints + " " + context.getString(R.string.points) + "\n(" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.isWinNobody) { // досрочная ничья
                    this.status = context.getString(R.string.instance_nowinner)  + "\n(" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                }
            } else { // игра закончена по времени
                if (this.isWinOur) { // наша победа
                    this.status = context.getString(R.string.we_win)  + " " + differentPoints + " " + context.getString(R.string.points) + "\n(" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.isWinEnemy) { // победа противника
                    this.status = context.getString(R.string.we_lost)  + " " + differentPoints + " " + context.getString(R.string.points) + "\n(" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.isWinNobody) { // ничья
                    this.status = context.getString(R.string.nowin)  + "\n(" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                }
            }
        } else { // игра еще идет
            int minutesToEndGame = Utils.getMinutesBetweenDates(this.dateCurrent, this.dateFinal);
            if (this.willEarlyWin) { // игра будет закончена досрочно
                if (this.willOurWin) { // будет досрочная наша победа
                    this.status = context.getString(R.string.we_will_instance_win_with_diff_in)  + " " + differentPoints + " " + context.getString(R.string.points)  + "\n" + context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.willEnemyWin) { // будет досрочная победа противника
                    this.status = context.getString(R.string.we_will_instance_lose_with_diff_in)  + " " + differentPoints + " " + context.getString(R.string.points)  + "\n" + context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.willNobodyWin) { // будет досрочная ничья
                    this.status = context.getString(R.string.will_instance_nowin_after)  + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  "\n(" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                }
            } else { // игра будет закончена по времени
                if (this.willOurWin) { // будет наша победа
                    this.status = context.getString(R.string.we_will_win_with_diff_in)  + " " + differentPoints + " " + context.getString(R.string.points)  + "\n" + context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.willEnemyWin) { // будет победа противника
                    this.status = context.getString(R.string.we_will_lose_with_diff_in)  + " " + differentPoints + " " + context.getString(R.string.points)  + "\n" + context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.willNobodyWin) { // будет ничья
                    this.status = context.getString(R.string.will_nowin) + " " + context.getString(R.string.after) + "\n" + Utils.convertMinutesToHHMM(minutesToEndGame) +  "\n(" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                }
            }
        } // игра закончена

        if  (this.isErrorRecognize) this.status = context.getString(R.string.error_recognizing) + "  " + this.status;

        if (doForecast) this.doForecast();

    }


    public String getSlots_our_toView(int buildingIndex) {
        int countCarsToOwn = this.buildings[buildingIndex].getSlots() / 2 + 1;
        if (this.buildings[buildingIndex].getSlots_our() >= countCarsToOwn) {
            return String.valueOf(this.buildings[buildingIndex].getSlots_our());
        } else {
            return this.buildings[buildingIndex].getSlots_our() + "/" + (countCarsToOwn - this.buildings[buildingIndex].getSlots_our());
        }
    }

    public String getSlots_enemy_toView(int buildingIndex) {
        int countCarsToOwn = this.buildings[buildingIndex].getSlots() / 2 + 1;
        if (this.buildings[buildingIndex].getSlots_enemy() >= countCarsToOwn) {
            return String.valueOf(this.buildings[buildingIndex].getSlots_enemy());
        } else {
            return this.buildings[buildingIndex].getSlots_enemy() + "/" + (countCarsToOwn - this.buildings[buildingIndex].getSlots_enemy());
        }
    }



    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public File getScreenshotFile() {
        return screenshotFile;
    }

    public void setScreenshotFile(File screenshotFile) {
        this.screenshotFile = screenshotFile;
    }

    public Date getDateStartGame() {
        return dateStartGame;
    }

    public void setDateStartGame(Date dateStartGame) {
        this.dateStartGame = dateStartGame;
    }

    public Date getDateScreenshot() {
        return dateScreenshot;
    }

    public void setDateScreenshot(Date dateScreenshot) {
        this.dateScreenshot = dateScreenshot;
    }

    public Date getDateCurrent() {
        return dateCurrent;
    }

    public void setDateCurrent(Date dateCurrent) {
        this.dateCurrent = dateCurrent;
    }

    public Date getDateEarlyWin() {
        return dateEarlyWin;
    }

    public void setDateEarlyWin(Date dateEarlyWin) {
        this.dateEarlyWin = dateEarlyWin;
    }

    public Date getDateEndGame() {
        return dateEndGame;
    }

    public void setDateEndGame(Date dateEndGame) {
        this.dateEndGame = dateEndGame;
    }

    public Date getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(Date dateFinal) {
        this.dateFinal = dateFinal;
    }

    public int getEarlyWin() {
        return earlyWin;
    }

    public void setEarlyWin(int earlyWin) {
        this.earlyWin = earlyWin;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;
    }

    public boolean isGameOverEarly() {
        return isGameOverEarly;
    }

    public void setGameOverEarly(boolean gameOverEarly) {
        this.isGameOverEarly = gameOverEarly;
    }

    public boolean isWinOur() {
        return isWinOur;
    }

    public void setWinOur(boolean winOur) {
        this.isWinOur = winOur;
    }

    public boolean isWinEnemy() {
        return isWinEnemy;
    }

    public void setWinEnemy(boolean winEnemy) {
        this.isWinEnemy = winEnemy;
    }

    public boolean isWinNobody() {
        return isWinNobody;
    }

    public void setWinNobody(boolean winNobody) {
        this.isWinNobody = winNobody;
    }

    public boolean isErrorRecognize() {
        return isErrorRecognize;
    }

    public void setErrorRecognize(boolean errorRecognize) {
        this.isErrorRecognize = errorRecognize;
    }

    public boolean isWillEarlyWin() {
        return willEarlyWin;
    }

    public void setWillEarlyWin(boolean willEarlyWin) {
        this.willEarlyWin = willEarlyWin;
    }

    public boolean isWillOurWin() {
        return willOurWin;
    }

    public void setWillOurWin(boolean willOurWin) {
        this.willOurWin = willOurWin;
    }

    public boolean isWillEnemyWin() {
        return willEnemyWin;
    }

    public void setWillEnemyWin(boolean willEnemyWin) {
        this.willEnemyWin = willEnemyWin;
    }

    public boolean isWillNobodyWin() {
        return willNobodyWin;
    }

    public void setWillNobodyWin(boolean willNobodyWin) {
        this.willNobodyWin = willNobodyWin;
    }

    public int getDifferentPoints() {
        return differentPoints;
    }

    public void setDifferentPoints(int differentPoints) {
        this.differentPoints = differentPoints;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPointsOurInScreenshot() {
        return pointsOurInScreenshot;
    }

    public void setPointsOurInScreenshot(int pointsOurInScreenshot) {
        this.pointsOurInScreenshot = pointsOurInScreenshot;
    }

    public int getPointsEnemyInScreenshot() {
        return pointsEnemyInScreenshot;
    }

    public void setPointsEnemyInScreenshot(int pointsEnemyInScreenshot) {
        this.pointsEnemyInScreenshot = pointsEnemyInScreenshot;
    }

    public int getIncreaseOur() {
        return increaseOur;
    }

    public void setIncreaseOur(int increaseOur) {
        this.increaseOur = increaseOur;
    }

    public int getIncreaseEnemy() {
        return increaseEnemy;
    }

    public void setIncreaseEnemy(int increaseEnemy) {
        this.increaseEnemy = increaseEnemy;
    }


    public String getUserNIC() {
        return userNIC;
    }

    public void setUserNIC(String userNIC) {
        this.userNIC = userNIC;
    }

    public byte[] getBytesScreenshot() {
        return bytesScreenshot;
    }

    public void setBytesScreenshot(byte[] bytesScreenshot) {
        this.bytesScreenshot = bytesScreenshot;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getCountOurX2() {
        return countOurX2;
    }

    public void setCountOurX2(int countOurX2) {
        this.countOurX2 = countOurX2;
    }

    public int getPersonsOur() {
        return personsOur;
    }

    public void setPersonsOur(int personsOur) {
        this.personsOur = personsOur;
    }

    public int getCountEnemyX2() {
        return countEnemyX2;
    }

    public void setCountEnemyX2(int countEnemyX2) {
        this.countEnemyX2 = countEnemyX2;
    }

    public int getCountX2() {
        return countX2;
    }

    public void setCountX2(int countX2) {
        this.countX2 = countX2;
    }

    public int getPersonsEnemy() {
        return personsEnemy;
    }

    public void setPersonsEnemy(int personsEnemy) {
        this.personsEnemy = personsEnemy;
    }

    public int getSlotsTotal() {
        return slotsTotal;
    }

    public void setSlotsTotal(int slotsTotal) {
        this.slotsTotal = slotsTotal;
    }

    public int getSlotsOur() {
        return slotsOur;
    }

    public void setSlotsOur(int slotsOur) {
        this.slotsOur = slotsOur;
    }

    public int getSlotsEnemy() {
        return slotsEnemy;
    }

    public void setSlotsEnemy(int slotsEnemy) {
        this.slotsEnemy = slotsEnemy;
    }

    public int getPersonsTotal() {
        return personsTotal;
    }

    public void setPersonsTotal(int personsTotal) {
        this.personsTotal = personsTotal;
    }

    public String getForecastText() {
        return forecastText;
    }

    public void setForecastText(String forecastText) {
        this.forecastText = forecastText;
    }

    public boolean isCanWin() {
        return canWin;
    }

    public void setCanWin(boolean canWin) {
        this.canWin = canWin;
    }

    public boolean isCanWinWithoutX2() {
        return canWinWithoutX2;
    }

    public void setCanWinWithoutX2(boolean canWinWithoutX2) {
        this.canWinWithoutX2 = canWinWithoutX2;
    }

    public boolean isCanEarlyWin() {
        return canEarlyWin;
    }

    public void setCanEarlyWin(boolean canEarlyWin) {
        this.canEarlyWin = canEarlyWin;
    }

    public boolean isCanEarlyWinWithoutX2() {
        return canEarlyWinWithoutX2;
    }

    public void setCanEarlyWinWithoutX2(boolean canEarlyWinWithoutX2) {
        this.canEarlyWinWithoutX2 = canEarlyWinWithoutX2;
    }


    public CCABuilding[] getBuildings() {
        return buildings;
    }

    public void setBuildings(CCABuilding[] buildings) {
        this.buildings = buildings;
    }
}
