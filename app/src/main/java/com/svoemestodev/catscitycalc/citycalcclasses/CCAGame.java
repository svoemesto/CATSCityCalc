package com.svoemestodev.catscitycalc.citycalcclasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.svoemestodev.catscitycalc.GlobalApplication;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.activities.GameActivity;
import com.svoemestodev.catscitycalc.activities.StrategyActivity;
import com.svoemestodev.catscitycalc.classes.LastModified;
import com.svoemestodev.catscitycalc.database.DbTeamGame;
import com.svoemestodev.catscitycalc.utils.CropPosition;
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

//    private CCABuilding[] buildings = new CCABuilding[6];

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

    private boolean needToWin_blt;
    private boolean needToWinWithoutX2_blt;
    private boolean needToEarlyWin_blt;
    private boolean needToEarlyWinWithoutX2_blt;
    private boolean needToWin_blc;
    private boolean needToWinWithoutX2_blc;
    private boolean needToEarlyWin_blc;
    private boolean needToEarlyWinWithoutX2_blc;
    private boolean needToWin_blb;
    private boolean needToWinWithoutX2_blb;
    private boolean needToEarlyWin_blb;
    private boolean needToEarlyWinWithoutX2_blb;
    private boolean needToWin_brt;
    private boolean needToWinWithoutX2_brt;
    private boolean needToEarlyWin_brt;
    private boolean needToEarlyWinWithoutX2_brt;
    private boolean needToWin_brc;
    private boolean needToWinWithoutX2_brc;
    private boolean needToEarlyWin_brc;
    private boolean needToEarlyWinWithoutX2_brc;
    private boolean needToWin_brb;
    private boolean needToWinWithoutX2_brb;
    private boolean needToEarlyWin_brb;
    private boolean needToEarlyWinWithoutX2_brb;
    
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

    public CCAGame(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr, boolean needBW) {
        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
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
    }

    public CCAGame() {
    }

    public CCAGame getClone(CityCalc parent) {

        CCAGame clone = new CCAGame();

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
        clone.isPresent_blt = this.isPresent_blt;
        clone.isPresent_blc = this.isPresent_blc;
        clone.isPresent_blb = this.isPresent_blb;
        clone.isPresent_brt = this.isPresent_brt;
        clone.isPresent_brc = this.isPresent_brc;
        clone.isPresent_brb = this.isPresent_brb;
        clone.name_blt = this.name_blt;
        clone.name_blc = this.name_blc;
        clone.name_blb = this.name_blb;
        clone.name_brt = this.name_brt;
        clone.name_brc = this.name_brc;
        clone.name_brb = this.name_brb;
        clone.isX2_blt = this.isX2_blt;
        clone.isX2_blc = this.isX2_blc;
        clone.isX2_blb = this.isX2_blb;
        clone.isX2_brt = this.isX2_brt;
        clone.isX2_brc = this.isX2_brc;
        clone.isX2_brb = this.isX2_brb;
        clone.mayX2_blt = this.mayX2_blt;
        clone.mayX2_blc = this.mayX2_blc;
        clone.mayX2_blb = this.mayX2_blb;
        clone.mayX2_brt = this.mayX2_brt;
        clone.mayX2_brc = this.mayX2_brc;
        clone.mayX2_brb = this.mayX2_brb;
        clone.buildingIsOur_blt = this.buildingIsOur_blt;
        clone.buildingIsOur_blc = this.buildingIsOur_blc;
        clone.buildingIsOur_blb = this.buildingIsOur_blb;
        clone.buildingIsOur_brt = this.buildingIsOur_brt;
        clone.buildingIsOur_brc = this.buildingIsOur_brc;
        clone.buildingIsOur_brb = this.buildingIsOur_brb;
        clone.buildingIsEmpty_blt = this.buildingIsEmpty_blt;
        clone.buildingIsEmpty_blc = this.buildingIsEmpty_blc;
        clone.buildingIsEmpty_blb = this.buildingIsEmpty_blb;
        clone.buildingIsEmpty_brt = this.buildingIsEmpty_brt;
        clone.buildingIsEmpty_brc = this.buildingIsEmpty_brc;
        clone.buildingIsEmpty_brb = this.buildingIsEmpty_brb;
        clone.buildingIsEnemy_blt = this.buildingIsEnemy_blt;
        clone.buildingIsEnemy_blc = this.buildingIsEnemy_blc;
        clone.buildingIsEnemy_blb = this.buildingIsEnemy_blb;
        clone.buildingIsEnemy_brt = this.buildingIsEnemy_brt;
        clone.buildingIsEnemy_brc = this.buildingIsEnemy_brc;
        clone.buildingIsEnemy_brb = this.buildingIsEnemy_brb;
        clone.our_points_blt = this.our_points_blt;
        clone.our_points_blc = this.our_points_blc;
        clone.our_points_blb = this.our_points_blb;
        clone.our_points_brt = this.our_points_brt;
        clone.our_points_brc = this.our_points_brc;
        clone.our_points_brb = this.our_points_brb;
        clone.enemy_points_blt = this.enemy_points_blt;
        clone.enemy_points_blc = this.enemy_points_blc;
        clone.enemy_points_blb = this.enemy_points_blb;
        clone.enemy_points_brt = this.enemy_points_brt;
        clone.enemy_points_brc = this.enemy_points_brc;
        clone.enemy_points_brb = this.enemy_points_brb;
        clone.slots_blt = this.slots_blt;
        clone.slots_blt_our = this.slots_blt_our;
        clone.slots_blt_empty = this.slots_blt_empty;
        clone.slots_blt_enemy = this.slots_blt_enemy;
        clone.slots_blc = this.slots_blc;
        clone.slots_blc_our = this.slots_blc_our;
        clone.slots_blc_empty = this.slots_blc_empty;
        clone.slots_blc_enemy = this.slots_blc_enemy;
        clone.slots_blb = this.slots_blb;
        clone.slots_blb_our = this.slots_blb_our;
        clone.slots_blb_empty = this.slots_blb_empty;
        clone.slots_blb_enemy = this.slots_blb_enemy;
        clone.slots_brt = this.slots_brt;
        clone.slots_brt_our = this.slots_brt_our;
        clone.slots_brt_empty = this.slots_brt_empty;
        clone.slots_brt_enemy = this.slots_brt_enemy;
        clone.slots_brc = this.slots_brc;
        clone.slots_brc_our = this.slots_brc_our;
        clone.slots_brc_empty = this.slots_brc_empty;
        clone.slots_brc_enemy = this.slots_brc_enemy;
        clone.slots_brb = this.slots_brb;
        clone.slots_brb_our = this.slots_brb_our;
        clone.slots_brb_empty = this.slots_brb_empty;
        clone.slots_brb_enemy = this.slots_brb_enemy;
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

        clone.needToWin_blt = this.needToWin_blt;
        clone.needToWinWithoutX2_blt = this.needToWinWithoutX2_blt;
        clone.needToEarlyWin_blt = this.needToEarlyWin_blt;
        clone.needToEarlyWinWithoutX2_blt = this.needToEarlyWinWithoutX2_blt;
        clone.needToWin_blc = this.needToWin_blc;
        clone.needToWinWithoutX2_blc = this.needToWinWithoutX2_blc;
        clone.needToEarlyWin_blc = this.needToEarlyWin_blc;
        clone.needToEarlyWinWithoutX2_blc = this.needToEarlyWinWithoutX2_blc;
        clone.needToWin_blb = this.needToWin_blb;
        clone.needToWinWithoutX2_blb = this.needToWinWithoutX2_blb;
        clone.needToEarlyWin_blb = this.needToEarlyWin_blb;
        clone.needToEarlyWinWithoutX2_blb = this.needToEarlyWinWithoutX2_blb;
        clone.needToWin_brt = this.needToWin_brt;
        clone.needToWinWithoutX2_brt = this.needToWinWithoutX2_brt;
        clone.needToEarlyWin_brt = this.needToEarlyWin_brt;
        clone.needToEarlyWinWithoutX2_brt = this.needToEarlyWinWithoutX2_brt;
        clone.needToWin_brc = this.needToWin_brc;
        clone.needToWinWithoutX2_brc = this.needToWinWithoutX2_brc;
        clone.needToEarlyWin_brc = this.needToEarlyWin_brc;
        clone.needToEarlyWinWithoutX2_brc = this.needToEarlyWinWithoutX2_brc;
        clone.needToWin_brb = this.needToWin_brb;
        clone.needToWinWithoutX2_brb = this.needToWinWithoutX2_brb;
        clone.needToEarlyWin_brb = this.needToEarlyWin_brb;
        clone.needToEarlyWinWithoutX2_brb = this.needToEarlyWinWithoutX2_brb;
        
        
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

        this.needToWin_blt = dbTeamGame.isNeedToWin_blt();
        this.needToWinWithoutX2_blt = dbTeamGame.isNeedToWinWithoutX2_blt();
        this.needToEarlyWin_blt = dbTeamGame.isNeedToEarlyWin_blt();
        this.needToEarlyWinWithoutX2_blt = dbTeamGame.isNeedToEarlyWinWithoutX2_blt();
        this.needToWin_blc = dbTeamGame.isNeedToWin_blc();
        this.needToWinWithoutX2_blc = dbTeamGame.isNeedToWinWithoutX2_blc();
        this.needToEarlyWin_blc = dbTeamGame.isNeedToEarlyWin_blc();
        this.needToEarlyWinWithoutX2_blc = dbTeamGame.isNeedToEarlyWinWithoutX2_blc();
        this.needToWin_blb = dbTeamGame.isNeedToWin_blb();
        this.needToWinWithoutX2_blb = dbTeamGame.isNeedToWinWithoutX2_blb();
        this.needToEarlyWin_blb = dbTeamGame.isNeedToEarlyWin_blb();
        this.needToEarlyWinWithoutX2_blb = dbTeamGame.isNeedToEarlyWinWithoutX2_blb();
        this.needToWin_brt = dbTeamGame.isNeedToWin_brt();
        this.needToWinWithoutX2_brt = dbTeamGame.isNeedToWinWithoutX2_brt();
        this.needToEarlyWin_brt = dbTeamGame.isNeedToEarlyWin_brt();
        this.needToEarlyWinWithoutX2_brt = dbTeamGame.isNeedToEarlyWinWithoutX2_brt();
        this.needToWin_brc = dbTeamGame.isNeedToWin_brc();
        this.needToWinWithoutX2_brc = dbTeamGame.isNeedToWinWithoutX2_brc();
        this.needToEarlyWin_brc = dbTeamGame.isNeedToEarlyWin_brc();
        this.needToEarlyWinWithoutX2_brc = dbTeamGame.isNeedToEarlyWinWithoutX2_brc();
        this.needToWin_brb = dbTeamGame.isNeedToWin_brb();
        this.needToWinWithoutX2_brb = dbTeamGame.isNeedToWinWithoutX2_brb();
        this.needToEarlyWin_brb = dbTeamGame.isNeedToEarlyWin_brb();
        this.needToEarlyWinWithoutX2_brb = dbTeamGame.isNeedToEarlyWinWithoutX2_brb();
        
        
        this.bytesScreenshot = dbTeamGame.getBytesScreenshot();

        calcWin(true);

    }

    public void doForecast() {

        List<ForecastMatrix> list = new ArrayList<>();

        for (int x1 = 0; x1 <= 1; x1++) {
            for (int x2 = 0; x2 <= 1; x2++) {
                for (int x3 = 0; x3 <= 1; x3++) {
                    for (int x4 = 0; x4 <= 1; x4++) {
                        for (int x5 = 0; x5 <= 1; x5++) {
                            for (int x6 = 0; x6 <= 1; x6++) {
                                list.add(new ForecastMatrix(this.getClone(this.getCityCalc()), x1==1,x2==1,x3==1,x4==1,x5==1,x6==1));
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
        this.needToWin_blt = isFound && matrix.ccaGame.isBuildingIsOur_blt();
        this.needToWin_blc = isFound && matrix.ccaGame.isBuildingIsOur_blc();
        this.needToWin_blb = isFound && matrix.ccaGame.isBuildingIsOur_blb();
        this.needToWin_brt = isFound && matrix.ccaGame.isBuildingIsOur_brt();
        this.needToWin_brc = isFound && matrix.ccaGame.isBuildingIsOur_brc();
        this.needToWin_brb = isFound && matrix.ccaGame.isBuildingIsOur_brb();

        isFound = false;
        for (int i = 0; i < forecastMatrixArray.length; i++) {
            matrix = (ForecastMatrix) forecastMatrixArray[i];
            if (matrix.ccaGame.willOurWin && !matrix.ccaGame.willEarlyWin && matrix.ccaGame.countOurX2 == 0) {
                isFound = true;
                break;
            }
        }

        this.canWinWithoutX2 = isFound;
        this.needToWinWithoutX2_blt = isFound && matrix.ccaGame.isBuildingIsOur_blt();
        this.needToWinWithoutX2_blc = isFound && matrix.ccaGame.isBuildingIsOur_blc();
        this.needToWinWithoutX2_blb = isFound && matrix.ccaGame.isBuildingIsOur_blb();
        this.needToWinWithoutX2_brt = isFound && matrix.ccaGame.isBuildingIsOur_brt();
        this.needToWinWithoutX2_brc = isFound && matrix.ccaGame.isBuildingIsOur_brc();
        this.needToWinWithoutX2_brb = isFound && matrix.ccaGame.isBuildingIsOur_brb();

        isFound = false;
        for (int i = 0; i < forecastMatrixArray.length; i++) {
            matrix = (ForecastMatrix) forecastMatrixArray[i];
            if (matrix.ccaGame.willOurWin && matrix.ccaGame.willEarlyWin) {
                isFound = true;
                break;
            }
        }

        this.canEarlyWin = isFound;
        this.needToEarlyWin_blt = isFound && matrix.ccaGame.isBuildingIsOur_blt();
        this.needToEarlyWin_blc = isFound && matrix.ccaGame.isBuildingIsOur_blc();
        this.needToEarlyWin_blb = isFound && matrix.ccaGame.isBuildingIsOur_blb();
        this.needToEarlyWin_brt = isFound && matrix.ccaGame.isBuildingIsOur_brt();
        this.needToEarlyWin_brc = isFound && matrix.ccaGame.isBuildingIsOur_brc();
        this.needToEarlyWin_brb = isFound && matrix.ccaGame.isBuildingIsOur_brb();

        isFound = false;
        for (int i = 0; i < forecastMatrixArray.length; i++) {
            matrix = (ForecastMatrix) forecastMatrixArray[i];
            if (matrix.ccaGame.willOurWin && matrix.ccaGame.willEarlyWin && matrix.ccaGame.countOurX2 == 0) {
                isFound = true;
                break;
            }
        }

        this.canEarlyWinWithoutX2 = isFound;
        this.needToEarlyWin_blt = isFound && matrix.ccaGame.isBuildingIsOur_blt();
        this.needToEarlyWin_blc = isFound && matrix.ccaGame.isBuildingIsOur_blc();
        this.needToEarlyWin_blb = isFound && matrix.ccaGame.isBuildingIsOur_blb();
        this.needToEarlyWin_brt = isFound && matrix.ccaGame.isBuildingIsOur_brt();
        this.needToEarlyWin_brc = isFound && matrix.ccaGame.isBuildingIsOur_brc();
        this.needToEarlyWin_brb = isFound && matrix.ccaGame.isBuildingIsOur_brb();

    }

    class ForecastMatrix implements Comparable<ForecastMatrix>{
        boolean isOur_blt;
        boolean isOur_blc;
        boolean isOur_blb;
        boolean isOur_brt;
        boolean isOur_brc;
        boolean isOur_brb;
        CCAGame ccaGame;

        public ForecastMatrix(CCAGame ccaGame, boolean isOur_blt, boolean isOur_blc, boolean isOur_blb, boolean isOur_brt, boolean isOur_brc, boolean isOur_brb) {
            this.isOur_blt = isOur_blt;
            this.isOur_blc = isOur_blc;
            this.isOur_blb = isOur_blb;
            this.isOur_brt = isOur_brt;
            this.isOur_brc = isOur_brc;
            this.isOur_brb = isOur_brb;
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
            
            
            if (ccaGame.isPresent_blt) {
                countCars_total += ccaGame.slots_blt;
                countCarsInBuilding = (ccaGame.slots_blt / 2 + 1);
                ccaGame.buildingIsOur_blt = isOur_blt;
                ccaGame.buildingIsEnemy_blt = !isOur_blt;
                ccaGame.buildingIsEmpty_blt = false;
                ccaGame.slots_blt_our = countCarsInBuilding * (isOur_blt ? 1 : 0);
                ccaGame.slots_blt_enemy = countCarsInBuilding * (!isOur_blt ? 1 : 0);
                ccaGame.slots_blt_empty = 0;
                countCars_our += isOur_blt ? countCarsInBuilding : 0;
                countCars_enemy += !isOur_blt ? countCarsInBuilding : 0;
                countX2_total += ccaGame.mayX2_blt ? 1 : 0;
                countX2_our += ccaGame.mayX2_blt && isOur_blt ? 1 : 0;
                countX2_enemy += ccaGame.mayX2_blt && !isOur_blt ? 1 : 0;
            }

            if (ccaGame.isPresent_blc) {
                countCars_total += ccaGame.slots_blc;
                countCarsInBuilding = (ccaGame.slots_blc / 2 + 1);
                ccaGame.buildingIsOur_blc = isOur_blc;
                ccaGame.buildingIsEnemy_blc = !isOur_blc;
                ccaGame.buildingIsEmpty_blc = false;
                ccaGame.slots_blc_our = countCarsInBuilding * (isOur_blc ? 1 : 0);
                ccaGame.slots_blc_enemy = countCarsInBuilding * (!isOur_blc ? 1 : 0);
                ccaGame.slots_blc_empty = 0;
                countCars_our += isOur_blc ? countCarsInBuilding : 0;
                countCars_enemy += !isOur_blc ? countCarsInBuilding : 0;
                countX2_total += ccaGame.mayX2_blc ? 1 : 0;
                countX2_our += ccaGame.mayX2_blc && isOur_blc ? 1 : 0;
                countX2_enemy += ccaGame.mayX2_blc && !isOur_blc ? 1 : 0;
            }

            if (ccaGame.isPresent_blb) {
                countCars_total += ccaGame.slots_blb;
                countCarsInBuilding = (ccaGame.slots_blb / 2 + 1);
                ccaGame.buildingIsOur_blb = isOur_blb;
                ccaGame.buildingIsEnemy_blb = !isOur_blb;
                ccaGame.buildingIsEmpty_blb = false;
                ccaGame.slots_blb_our = countCarsInBuilding * (isOur_blb ? 1 : 0);
                ccaGame.slots_blb_enemy = countCarsInBuilding * (!isOur_blb ? 1 : 0);
                ccaGame.slots_blb_empty = 0;
                countCars_our += isOur_blb ? countCarsInBuilding : 0;
                countCars_enemy += !isOur_blb ? countCarsInBuilding : 0;
                countX2_total += ccaGame.mayX2_blb ? 1 : 0;
                countX2_our += ccaGame.mayX2_blb && isOur_blb ? 1 : 0;
                countX2_enemy += ccaGame.mayX2_blb && !isOur_blb ? 1 : 0;
            }

            if (ccaGame.isPresent_brt) {
                countCars_total += ccaGame.slots_brt;
                countCarsInBuilding = (ccaGame.slots_brt / 2 + 1);
                ccaGame.buildingIsOur_brt = isOur_brt;
                ccaGame.buildingIsEnemy_brt = !isOur_brt;
                ccaGame.buildingIsEmpty_brt = false;
                ccaGame.slots_brt_our = countCarsInBuilding * (isOur_brt ? 1 : 0);
                ccaGame.slots_brt_enemy = countCarsInBuilding * (!isOur_brt ? 1 : 0);
                ccaGame.slots_brt_empty = 0;
                countCars_our += isOur_brt ? countCarsInBuilding : 0;
                countCars_enemy += !isOur_brt ? countCarsInBuilding : 0;
                countX2_total += ccaGame.mayX2_brt ? 1 : 0;
                countX2_our += ccaGame.mayX2_brt && isOur_brt ? 1 : 0;
                countX2_enemy += ccaGame.mayX2_brt && !isOur_brt ? 1 : 0;
            }

            if (ccaGame.isPresent_brc) {
                countCars_total += ccaGame.slots_brc;
                countCarsInBuilding = (ccaGame.slots_brc / 2 + 1);
                ccaGame.buildingIsOur_brc = isOur_brc;
                ccaGame.buildingIsEnemy_brc = !isOur_brc;
                ccaGame.buildingIsEmpty_brc = false;
                ccaGame.slots_brc_our = countCarsInBuilding * (isOur_brc ? 1 : 0);
                ccaGame.slots_brc_enemy = countCarsInBuilding * (!isOur_brc ? 1 : 0);
                ccaGame.slots_brc_empty = 0;
                countCars_our += isOur_brc ? countCarsInBuilding : 0;
                countCars_enemy += !isOur_brc ? countCarsInBuilding : 0;
                countX2_total += ccaGame.mayX2_brc ? 1 : 0;
                countX2_our += ccaGame.mayX2_brc && isOur_brc ? 1 : 0;
                countX2_enemy += ccaGame.mayX2_brc && !isOur_brc ? 1 : 0;
            }

            if (ccaGame.isPresent_brb) {
                countCars_total += ccaGame.slots_brb;
                countCarsInBuilding = (ccaGame.slots_brb / 2 + 1);
                ccaGame.buildingIsOur_brb = isOur_brb;
                ccaGame.buildingIsEnemy_brb = !isOur_brb;
                ccaGame.buildingIsEmpty_brb = false;
                ccaGame.slots_brb_our = countCarsInBuilding * (isOur_brb ? 1 : 0);
                ccaGame.slots_brb_enemy = countCarsInBuilding * (!isOur_brb ? 1 : 0);
                ccaGame.slots_brb_empty = 0;
                countCars_our += isOur_brb ? countCarsInBuilding : 0;
                countCars_enemy += !isOur_brb ? countCarsInBuilding : 0;
                countX2_total += ccaGame.mayX2_brb ? 1 : 0;
                countX2_our += ccaGame.mayX2_brb && isOur_brb ? 1 : 0;
                countX2_enemy += ccaGame.mayX2_brb && !isOur_brb ? 1 : 0;
            }

            if (ccaGame.isPresent_blt) {
                ccaGame.isX2_blt = (countX2_our == countX2_total) || (countX2_enemy == countX2_total);
                ccaGame.our_points_blt = isOur_blt ? ccaGame.slots_blt * (ccaGame.isX2_blt ? 2 : 1) : 0;
                ccaGame.enemy_points_blt = !isOur_blt ? ccaGame.slots_blt * (ccaGame.isX2_blt ? 2 : 1) : 0;
                increaseOur += ccaGame.our_points_blt;
                increaseEnemy += ccaGame.enemy_points_blt;
            }

            if (ccaGame.isPresent_blc) {
                ccaGame.isX2_blc = (countX2_our == countX2_total) || (countX2_enemy == countX2_total);
                ccaGame.our_points_blc = isOur_blc ? ccaGame.slots_blc * (ccaGame.isX2_blc ? 2 : 1) : 0;
                ccaGame.enemy_points_blc = !isOur_blc ? ccaGame.slots_blc * (ccaGame.isX2_blc ? 2 : 1) : 0;
                increaseOur += ccaGame.our_points_blc;
                increaseEnemy += ccaGame.enemy_points_blc;
            }

            if (ccaGame.isPresent_blb) {
                ccaGame.isX2_blb = (countX2_our == countX2_total) || (countX2_enemy == countX2_total);
                ccaGame.our_points_blb = isOur_blb ? ccaGame.slots_blb * (ccaGame.isX2_blb ? 2 : 1) : 0;
                ccaGame.enemy_points_blb = !isOur_blb ? ccaGame.slots_blb * (ccaGame.isX2_blb ? 2 : 1) : 0;
                increaseOur += ccaGame.our_points_blb;
                increaseEnemy += ccaGame.enemy_points_blb;
            }

            if (ccaGame.isPresent_brt) {
                ccaGame.isX2_brt = (countX2_our == countX2_total) || (countX2_enemy == countX2_total);
                ccaGame.our_points_brt = isOur_brt ? ccaGame.slots_brt * (ccaGame.isX2_brt ? 2 : 1) : 0;
                ccaGame.enemy_points_brt = !isOur_brt ? ccaGame.slots_brt * (ccaGame.isX2_brt ? 2 : 1) : 0;
                increaseOur += ccaGame.our_points_brt;
                increaseEnemy += ccaGame.enemy_points_brt;
            }

            if (ccaGame.isPresent_brc) {
                ccaGame.isX2_brc = (countX2_our == countX2_total) || (countX2_enemy == countX2_total);
                ccaGame.our_points_brc = isOur_brc ? ccaGame.slots_brc * (ccaGame.isX2_brc ? 2 : 1) : 0;
                ccaGame.enemy_points_brc = !isOur_brc ? ccaGame.slots_brc * (ccaGame.isX2_brc ? 2 : 1) : 0;
                increaseOur += ccaGame.our_points_brc;
                increaseEnemy += ccaGame.enemy_points_brc;
            }

            if (ccaGame.isPresent_brb) {
                ccaGame.isX2_brb = (countX2_our == countX2_total) || (countX2_enemy == countX2_total);
                ccaGame.our_points_brb = isOur_brb ? ccaGame.slots_brb * (ccaGame.isX2_brb ? 2 : 1) : 0;
                ccaGame.enemy_points_brb = !isOur_brb ? ccaGame.slots_brb * (ccaGame.isX2_brb ? 2 : 1) : 0;
                increaseOur += ccaGame.our_points_brb;
                increaseEnemy += ccaGame.enemy_points_brb;
            }

            ccaGame.increaseOur = increaseOur;
            ccaGame.increaseEnemy = increaseEnemy;
            ccaGame.slotsOur = countCars_our;
            ccaGame.slotsEnemy = countCars_enemy;
            ccaGame.slotsTotal = countCars_total;
            ccaGame.personsOur = (int)Math.ceil (ccaGame.slotsOur / 3.0d);
            ccaGame.personsEnemy = (int)Math.ceil (ccaGame.slotsEnemy / 3.0d);

            ccaGame.calc(false);
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

        CityCalcArea ccaTotalTime = this.getCityCalc().getMapAreas().get(Area.TOTAL_TIME); // время
        CityCalcArea ccaEarlyWin = this.getCityCalc().getMapAreas().get(Area.EARLY_WIN);   // очки досрочки
        int minFromStartToScreenshot = 0;
        
        if (ccaTotalTime != null && ccaEarlyWin != null) {

            String[] words = ccaTotalTime.getFinText().split(":"); // разделяем строку на часы и минуты
            if (words.length == 2) {
                minFromStartToScreenshot = 24*60 - (Integer.parseInt(words[0])* 60 + Integer.parseInt(words[1]));// прошло минут с начала игры по скриншоту
            } else {
                this.isErrorRecognize = true;
            }
            this.dateStartGame = Utils.addMinutesToDate(this.dateScreenshot, -minFromStartToScreenshot); // дата начала игры
            this.dateEndGame = Utils.addMinutesToDate(this.dateStartGame, 24*60); // дата конца игры по времени
            this.earlyWin = Integer.parseInt(ccaEarlyWin.getFinText()); // очки до досрочной победы

            this.countOurX2 = ((this.isPresent_blt() && this.isMayX2_blt() && this.isBuildingIsOur_blt()) ? 1 : 0) +
                    ((this.isPresent_blc() && this.isMayX2_blc() && this.isBuildingIsOur_blc()) ? 1 : 0) +
                    ((this.isPresent_blb() && this.isMayX2_blb() && this.isBuildingIsOur_blb()) ? 1 : 0) +
                    ((this.isPresent_brt() && this.isMayX2_brt() && this.isBuildingIsOur_brt()) ? 1 : 0) +
                    ((this.isPresent_brc() && this.isMayX2_brc() && this.isBuildingIsOur_brc()) ? 1 : 0) +
                    ((this.isPresent_brb() && this.isMayX2_brb() && this.isBuildingIsOur_brb()) ? 1 : 0);

            this.countEnemyX2 = ((this.isPresent_blt() && this.isMayX2_blt() && this.isBuildingIsEnemy_blt()) ? 1 : 0) +
                    ((this.isPresent_blc() && this.isMayX2_blc() && this.isBuildingIsEnemy_blc()) ? 1 : 0) +
                    ((this.isPresent_blb() && this.isMayX2_blb() && this.isBuildingIsEnemy_blb()) ? 1 : 0) +
                    ((this.isPresent_brt() && this.isMayX2_brt() && this.isBuildingIsEnemy_brt()) ? 1 : 0) +
                    ((this.isPresent_brc() && this.isMayX2_brc() && this.isBuildingIsEnemy_brc()) ? 1 : 0) +
                    ((this.isPresent_brb() && this.isMayX2_brb() && this.isBuildingIsEnemy_brb()) ? 1 : 0);
            
            this.countX2 = ((this.isPresent_blt() && this.isMayX2_blt()) ? 1 : 0) +
                    ((this.isPresent_blc() && this.isMayX2_blc()) ? 1 : 0) +
                    ((this.isPresent_blb() && this.isMayX2_blb()) ? 1 : 0) +
                    ((this.isPresent_brt() && this.isMayX2_brt()) ? 1 : 0) +
                    ((this.isPresent_brc() && this.isMayX2_brc()) ? 1 : 0) +
                    ((this.isPresent_brb() && this.isMayX2_brb()) ? 1 : 0);

            this.slotsTotal = (this.isPresent_blt() ? this.getSlots_blt() : 0) +
                    (this.isPresent_blc() ? this.getSlots_blc() : 0) +
                    (this.isPresent_blb() ? this.getSlots_blb() : 0) +
                    (this.isPresent_brt() ? this.getSlots_brt() : 0) +
                    (this.isPresent_brc() ? this.getSlots_brc() : 0) +
                    (this.isPresent_brb() ? this.getSlots_brb() : 0);

            this.slotsOur = (this.isPresent_blt() ? this.getSlots_blt_our() : 0) +
                    (this.isPresent_blc() ? this.getSlots_blc_our() : 0) +
                    (this.isPresent_blb() ? this.getSlots_blb_our() : 0) +
                    (this.isPresent_brt() ? this.getSlots_brt_our() : 0) +
                    (this.isPresent_brc() ? this.getSlots_brc_our() : 0) +
                    (this.isPresent_brb() ? this.getSlots_brb_our() : 0);

            this.slotsEnemy = (this.isPresent_blt() ? this.getSlots_blt_enemy() : 0) +
                    (this.isPresent_blc() ? this.getSlots_blc_enemy() : 0) +
                    (this.isPresent_blb() ? this.getSlots_blb_enemy() : 0) +
                    (this.isPresent_brt() ? this.getSlots_brt_enemy() : 0) +
                    (this.isPresent_brc() ? this.getSlots_brc_enemy() : 0) +
                    (this.isPresent_brb() ? this.getSlots_brb_enemy() : 0);

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
        }

        differentPoints = Math.abs(getPointsOur(this.dateFinal) - getPointsEnemy(this.dateFinal)); // разница в очках на момент окончания игры

        if (this.isGameOver) { // игра закончена
            if (this.isGameOverEarly) { // игра закончена досрочно
                if (this.isWinOur) { // досрочная наша победа
                    this.status = context.getString(R.string.we_instance_win)  + " " + differentPoints + " " + context.getString(R.string.points) + " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.isWinEnemy) { // досрочная победа противника
                    this.status = context.getString(R.string.we_instance_lose)  + " " + differentPoints + " " + context.getString(R.string.points) + " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.isWinNobody) { // досрочная ничья
                    this.status = context.getString(R.string.instance_nowinner)  + " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                }
            } else { // игра закончена по времени
                if (this.isWinOur) { // наша победа
                    this.status = context.getString(R.string.we_win)  + " " + differentPoints + " " + context.getString(R.string.points) + " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.isWinEnemy) { // победа противника
                    this.status = context.getString(R.string.we_lost)  + " " + differentPoints + " " + context.getString(R.string.points) + " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.isWinNobody) { // ничья
                    this.status = context.getString(R.string.nowin)  + " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                }
            }
        } else { // игра еще идет
            int minutesToEndGame = Utils.getMinutesBetweenDates(this.dateCurrent, this.dateFinal);
            if (this.willEarlyWin) { // игра будет закончена досрочно
                if (this.willOurWin) { // будет досрочная наша победа
                    this.status = context.getString(R.string.we_will_instance_win_with_diff_in)  + " " + differentPoints + " " + context.getString(R.string.points)  + " " + context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.willEnemyWin) { // будет досрочная победа противника
                    this.status = context.getString(R.string.we_will_instance_lose_with_diff_in)  + " " + differentPoints + " " + context.getString(R.string.points)  + " " + context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.willNobodyWin) { // будет досрочная ничья
                    this.status = context.getString(R.string.will_instance_nowin_after)  + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                }
            } else { // игра будет закончена по времени
                if (this.willOurWin) { // будет наша победа
                    this.status = context.getString(R.string.we_will_win_with_diff_in)  + " " + differentPoints + " " + context.getString(R.string.points)  + " " + context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.willEnemyWin) { // будет победа противника
                    this.status = context.getString(R.string.we_will_lose_with_diff_in)  + " " + differentPoints + " " + context.getString(R.string.points)  + " " + context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                } else if (this.willNobodyWin) { // будет ничья
                    this.status = context.getString(R.string.will_nowin) + " " + context.getString(R.string.after) + " " + Utils.convertMinutesToHHMM(minutesToEndGame) +  " (" + Utils.convertDateToString(this.dateFinal, pattern) + ")";
                }
            }
        } // игра закончена

        if  (this.isErrorRecognize) this.status = context.getString(R.string.error_recognizing) + "  " + this.status;

        if (doForecast) this.doForecast();

    }

    public String getSlots_blt_our_toView() {
        int countCarsToOwn = slots_blt / 2 + 1;
        if (slots_blt_our >= countCarsToOwn) {
            return String.valueOf(slots_blt_our);
        } else {
            return slots_blt_our + "/" + (countCarsToOwn - slots_blt_our);
        }
    }

    public String getSlots_blc_our_toView() {
        int countCarsToOwn = slots_blc / 2 + 1;
        if (slots_blc_our >= countCarsToOwn) {
            return String.valueOf(slots_blc_our);
        } else {
            return slots_blc_our + "/" + (countCarsToOwn - slots_blc_our);
        }
    }

    public String getSlots_blb_our_toView() {
        int countCarsToOwn = slots_blb / 2 + 1;
        if (slots_blb_our >= countCarsToOwn) {
            return String.valueOf(slots_blb_our);
        } else {
            return slots_blb_our + "/" + (countCarsToOwn - slots_blb_our);
        }
    }

    public String getSlots_brt_our_toView() {
        int countCarsToOwn = slots_brt / 2 + 1;
        if (slots_brt_our >= countCarsToOwn) {
            return String.valueOf(slots_brt_our);
        } else {
            return slots_brt_our + "/" + (countCarsToOwn - slots_brt_our);
        }
    }

    public String getSlots_brc_our_toView() {
        int countCarsToOwn = slots_brc / 2 + 1;
        if (slots_brc_our >= countCarsToOwn) {
            return String.valueOf(slots_brc_our);
        } else {
            return slots_brc_our + "/" + (countCarsToOwn - slots_brc_our);
        }
    }

    public String getSlots_brb_our_toView() {
        int countCarsToOwn = slots_brb / 2 + 1;
        if (slots_brb_our >= countCarsToOwn) {
            return String.valueOf(slots_brb_our);
        } else {
            return slots_brb_our + "/" + (countCarsToOwn - slots_brb_our);
        }
    }


    public String getSlots_blt_enemy_toView() {
        int countCarsToOwn = slots_blt / 2 + 1;
        if (slots_blt_enemy >= countCarsToOwn) {
            return String.valueOf(slots_blt_enemy);
        } else {
            return slots_blt_enemy + "/" + (countCarsToOwn - slots_blt_enemy);
        }
    }

    public String getSlots_blc_enemy_toView() {
        int countCarsToOwn = slots_blc / 2 + 1;
        if (slots_blc_enemy >= countCarsToOwn) {
            return String.valueOf(slots_blc_enemy);
        } else {
            return slots_blc_enemy + "/" + (countCarsToOwn - slots_blc_enemy);
        }
    }

    public String getSlots_blb_enemy_toView() {
        int countCarsToOwn = slots_blb / 2 + 1;
        if (slots_blb_enemy >= countCarsToOwn) {
            return String.valueOf(slots_blb_enemy);
        } else {
            return slots_blb_enemy + "/" + (countCarsToOwn - slots_blb_enemy);
        }
    }

    public String getSlots_brt_enemy_toView() {
        int countCarsToOwn = slots_brt / 2 + 1;
        if (slots_brt_enemy >= countCarsToOwn) {
            return String.valueOf(slots_brt_enemy);
        } else {
            return slots_brt_enemy + "/" + (countCarsToOwn - slots_brt_enemy);
        }
    }

    public String getSlots_brc_enemy_toView() {
        int countCarsToOwn = slots_brc / 2 + 1;
        if (slots_brc_enemy >= countCarsToOwn) {
            return String.valueOf(slots_brc_enemy);
        } else {
            return slots_brc_enemy + "/" + (countCarsToOwn - slots_brc_enemy);
        }
    }

    public String getSlots_brb_enemy_toView() {
        int countCarsToOwn = slots_brb / 2 + 1;
        if (slots_brb_enemy >= countCarsToOwn) {
            return String.valueOf(slots_brb_enemy);
        } else {
            return slots_brb_enemy + "/" + (countCarsToOwn - slots_brb_enemy);
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

    public boolean isNeedToWin_blt() {
        return needToWin_blt;
    }

    public void setNeedToWin_blt(boolean needToWin_blt) {
        this.needToWin_blt = needToWin_blt;
    }

    public boolean isNeedToWinWithoutX2_blt() {
        return needToWinWithoutX2_blt;
    }

    public void setNeedToWinWithoutX2_blt(boolean needToWinWithoutX2_blt) {
        this.needToWinWithoutX2_blt = needToWinWithoutX2_blt;
    }

    public boolean isNeedToEarlyWin_blt() {
        return needToEarlyWin_blt;
    }

    public void setNeedToEarlyWin_blt(boolean needToEarlyWin_blt) {
        this.needToEarlyWin_blt = needToEarlyWin_blt;
    }

    public boolean isNeedToEarlyWinWithoutX2_blt() {
        return needToEarlyWinWithoutX2_blt;
    }

    public void setNeedToEarlyWinWithoutX2_blt(boolean needToEarlyWinWithoutX2_blt) {
        this.needToEarlyWinWithoutX2_blt = needToEarlyWinWithoutX2_blt;
    }

    public boolean isNeedToWin_blc() {
        return needToWin_blc;
    }

    public void setNeedToWin_blc(boolean needToWin_blc) {
        this.needToWin_blc = needToWin_blc;
    }

    public boolean isNeedToWinWithoutX2_blc() {
        return needToWinWithoutX2_blc;
    }

    public void setNeedToWinWithoutX2_blc(boolean needToWinWithoutX2_blc) {
        this.needToWinWithoutX2_blc = needToWinWithoutX2_blc;
    }

    public boolean isNeedToEarlyWin_blc() {
        return needToEarlyWin_blc;
    }

    public void setNeedToEarlyWin_blc(boolean needToEarlyWin_blc) {
        this.needToEarlyWin_blc = needToEarlyWin_blc;
    }

    public boolean isNeedToEarlyWinWithoutX2_blc() {
        return needToEarlyWinWithoutX2_blc;
    }

    public void setNeedToEarlyWinWithoutX2_blc(boolean needToEarlyWinWithoutX2_blc) {
        this.needToEarlyWinWithoutX2_blc = needToEarlyWinWithoutX2_blc;
    }

    public boolean isNeedToWin_blb() {
        return needToWin_blb;
    }

    public void setNeedToWin_blb(boolean needToWin_blb) {
        this.needToWin_blb = needToWin_blb;
    }

    public boolean isNeedToWinWithoutX2_blb() {
        return needToWinWithoutX2_blb;
    }

    public void setNeedToWinWithoutX2_blb(boolean needToWinWithoutX2_blb) {
        this.needToWinWithoutX2_blb = needToWinWithoutX2_blb;
    }

    public boolean isNeedToEarlyWin_blb() {
        return needToEarlyWin_blb;
    }

    public void setNeedToEarlyWin_blb(boolean needToEarlyWin_blb) {
        this.needToEarlyWin_blb = needToEarlyWin_blb;
    }

    public boolean isNeedToEarlyWinWithoutX2_blb() {
        return needToEarlyWinWithoutX2_blb;
    }

    public void setNeedToEarlyWinWithoutX2_blb(boolean needToEarlyWinWithoutX2_blb) {
        this.needToEarlyWinWithoutX2_blb = needToEarlyWinWithoutX2_blb;
    }

    public boolean isNeedToWin_brt() {
        return needToWin_brt;
    }

    public void setNeedToWin_brt(boolean needToWin_brt) {
        this.needToWin_brt = needToWin_brt;
    }

    public boolean isNeedToWinWithoutX2_brt() {
        return needToWinWithoutX2_brt;
    }

    public void setNeedToWinWithoutX2_brt(boolean needToWinWithoutX2_brt) {
        this.needToWinWithoutX2_brt = needToWinWithoutX2_brt;
    }

    public boolean isNeedToEarlyWin_brt() {
        return needToEarlyWin_brt;
    }

    public void setNeedToEarlyWin_brt(boolean needToEarlyWin_brt) {
        this.needToEarlyWin_brt = needToEarlyWin_brt;
    }

    public boolean isNeedToEarlyWinWithoutX2_brt() {
        return needToEarlyWinWithoutX2_brt;
    }

    public void setNeedToEarlyWinWithoutX2_brt(boolean needToEarlyWinWithoutX2_brt) {
        this.needToEarlyWinWithoutX2_brt = needToEarlyWinWithoutX2_brt;
    }

    public boolean isNeedToWin_brc() {
        return needToWin_brc;
    }

    public void setNeedToWin_brc(boolean needToWin_brc) {
        this.needToWin_brc = needToWin_brc;
    }

    public boolean isNeedToWinWithoutX2_brc() {
        return needToWinWithoutX2_brc;
    }

    public void setNeedToWinWithoutX2_brc(boolean needToWinWithoutX2_brc) {
        this.needToWinWithoutX2_brc = needToWinWithoutX2_brc;
    }

    public boolean isNeedToEarlyWin_brc() {
        return needToEarlyWin_brc;
    }

    public void setNeedToEarlyWin_brc(boolean needToEarlyWin_brc) {
        this.needToEarlyWin_brc = needToEarlyWin_brc;
    }

    public boolean isNeedToEarlyWinWithoutX2_brc() {
        return needToEarlyWinWithoutX2_brc;
    }

    public void setNeedToEarlyWinWithoutX2_brc(boolean needToEarlyWinWithoutX2_brc) {
        this.needToEarlyWinWithoutX2_brc = needToEarlyWinWithoutX2_brc;
    }

    public boolean isNeedToWin_brb() {
        return needToWin_brb;
    }

    public void setNeedToWin_brb(boolean needToWin_brb) {
        this.needToWin_brb = needToWin_brb;
    }

    public boolean isNeedToWinWithoutX2_brb() {
        return needToWinWithoutX2_brb;
    }

    public void setNeedToWinWithoutX2_brb(boolean needToWinWithoutX2_brb) {
        this.needToWinWithoutX2_brb = needToWinWithoutX2_brb;
    }

    public boolean isNeedToEarlyWin_brb() {
        return needToEarlyWin_brb;
    }

    public void setNeedToEarlyWin_brb(boolean needToEarlyWin_brb) {
        this.needToEarlyWin_brb = needToEarlyWin_brb;
    }

    public boolean isNeedToEarlyWinWithoutX2_brb() {
        return needToEarlyWinWithoutX2_brb;
    }

    public void setNeedToEarlyWinWithoutX2_brb(boolean needToEarlyWinWithoutX2_brb) {
        this.needToEarlyWinWithoutX2_brb = needToEarlyWinWithoutX2_brb;
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

    //    public CCABuilding[] getBuildings() {
//        return buildings;
//    }
//
//    public void setBuildings(CCABuilding[] buildings) {
//        this.buildings = buildings;
//    }
//
//    public CCABuilding getBuilding(int buildingIndex) {
//        return buildings[buildingIndex];
//    }
//
//    public void setBuilding(CCABuilding building, int buildingIndex) {
//        this.buildings[buildingIndex] = building;
//    }

}
