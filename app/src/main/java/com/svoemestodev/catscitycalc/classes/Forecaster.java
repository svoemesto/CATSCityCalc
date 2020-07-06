package com.svoemestodev.catscitycalc.classes;

import com.svoemestodev.catscitycalc.citycalcclasses.CCAGame;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class Forecaster {

    long timeDiff;
    
    boolean[] isPresent = new boolean[6];
    int[] needAddCarsToBuildingOur = new int[6];
    int[] needAddCarsToBuildingEnemy = new int[6];
    int[] diffSlots_our = new int[6];
    int[] diffSlots_enemy = new int[6];
    ForecastStates[] diffBuildingIsOur = new ForecastStates[6];
    ForecastStates[] diffBuildingIsEnemy = new ForecastStates[6];
    ForecastStates[] state = new ForecastStates[6];
    
    public Forecaster(CCAGame ccaGamePrev, CCAGame ccaGameNew) {

        if (ccaGamePrev != null && ccaGameNew != null) {

            this.timeDiff = ccaGameNew.getDateScreenshot().getTime() - ccaGamePrev.getDateScreenshot().getTime();

            for (int buildingIndex = 0; buildingIndex < 6; buildingIndex++) {
                this.isPresent[buildingIndex] = (ccaGameNew.getBuildings()[buildingIndex].isPresent() && ccaGamePrev.getBuildings()[buildingIndex].isPresent());
                if (isPresent[buildingIndex]) {

                    this.state[buildingIndex] = ccaGameNew.getBuildings()[buildingIndex].isBuildingIsOur() ? ForecastStates.BUILDING_IS_OUR : ccaGameNew.getBuildings()[buildingIndex].isBuildingIsEnemy() ? ForecastStates.BUILDING_IS_ENEMY : ForecastStates.BUILDING_IS_EMPTY;

                    this.diffSlots_our[buildingIndex] = ccaGameNew.getBuildings()[buildingIndex].getSlots_our() - ccaGamePrev.getBuildings()[buildingIndex].getSlots_our();
                    this.diffSlots_enemy[buildingIndex] = ccaGameNew.getBuildings()[buildingIndex].getSlots_enemy() - ccaGamePrev.getBuildings()[buildingIndex].getSlots_enemy();

                    if (ccaGamePrev.getBuildings()[buildingIndex].isBuildingIsOur() && ccaGameNew.getBuildings()[buildingIndex].isBuildingIsOur()) this.diffBuildingIsOur[buildingIndex] = ForecastStates.WAS_DEFENDED;
                    if (ccaGamePrev.getBuildings()[buildingIndex].isBuildingIsOur() && !ccaGameNew.getBuildings()[buildingIndex].isBuildingIsOur()) this.diffBuildingIsOur[buildingIndex] = ForecastStates.WAS_LOST;
                    if (!ccaGamePrev.getBuildings()[buildingIndex].isBuildingIsOur() && ccaGameNew.getBuildings()[buildingIndex].isBuildingIsOur()) this.diffBuildingIsOur[buildingIndex] = ForecastStates.WAS_CAPTURED;
                    if (!ccaGamePrev.getBuildings()[buildingIndex].isBuildingIsOur() && !ccaGameNew.getBuildings()[buildingIndex].isBuildingIsOur()) this.diffBuildingIsOur[buildingIndex] = ForecastStates.WAS_NOT_CAPTURED;

                    if (ccaGamePrev.getBuildings()[buildingIndex].isBuildingIsEnemy() && ccaGameNew.getBuildings()[buildingIndex].isBuildingIsEnemy()) this.diffBuildingIsEnemy[buildingIndex] = ForecastStates.WAS_DEFENDED;
                    if (ccaGamePrev.getBuildings()[buildingIndex].isBuildingIsEnemy() && !ccaGameNew.getBuildings()[buildingIndex].isBuildingIsEnemy()) this.diffBuildingIsEnemy[buildingIndex] = ForecastStates.WAS_LOST;
                    if (!ccaGamePrev.getBuildings()[buildingIndex].isBuildingIsEnemy() && ccaGameNew.getBuildings()[buildingIndex].isBuildingIsEnemy()) this.diffBuildingIsEnemy[buildingIndex] = ForecastStates.WAS_CAPTURED;
                    if (!ccaGamePrev.getBuildings()[buildingIndex].isBuildingIsEnemy() && !ccaGameNew.getBuildings()[buildingIndex].isBuildingIsEnemy()) this.diffBuildingIsEnemy[buildingIndex] = ForecastStates.WAS_NOT_CAPTURED;

                    int needCarsToCapture = ccaGameNew.getBuildings()[buildingIndex].getSlots()/2+1;
                    this.needAddCarsToBuildingOur[buildingIndex] = Math.max((needCarsToCapture - ccaGameNew.getBuildings()[buildingIndex].getSlots_our()), 0);
                    this.needAddCarsToBuildingEnemy[buildingIndex] = Math.max((needCarsToCapture - ccaGameNew.getBuildings()[buildingIndex].getSlots_enemy()), 0);

                }
            }

            ccaGameNew.setForecastText(this.getForecast());

        }
    }
    
    public String getForecast() {
        
        Map<ForecastStates, String> mapFCS = new HashMap<>();
        mapFCS.put(ForecastStates.WAS_DEFENDED, "отстояла");
        mapFCS.put(ForecastStates.WAS_LOST, "потеряла");
        mapFCS.put(ForecastStates.WAS_CAPTURED, "захватила");
        mapFCS.put(ForecastStates.WAS_NOT_CAPTURED, "так и не захватила");
        mapFCS.put(ForecastStates.BUILDING_IS_OUR, "принадлежит нашей команде");
        mapFCS.put(ForecastStates.BUILDING_IS_ENEMY, "принадлежит команде противника");
        mapFCS.put(ForecastStates.BUILDING_IS_EMPTY, "никому не принадлежит");
        
        String txt = "";
        int building = 0;
        txt = txt + "Между получением текущих и предыдущих данных прошло " + this.timeDiff/60000 + " минут" + Utils.getPluralSuffixIm((int)(this.timeDiff/60000)) + ".\n\n";

        for (int buildingIndex = 0; buildingIndex < 6; buildingIndex++) {
            building = buildingIndex + 1;
            if (this.isPresent[buildingIndex]) {
                txt = txt + "Здание №" + building + " " + mapFCS.get(this.state[buildingIndex]) + ":\n";
                txt = txt + "----------------------------------------\n";
                if (this.diffSlots_our[buildingIndex] < 0) txt = txt + "За это время из этого здания противник выбил " + Math.abs(this.diffSlots_our[buildingIndex]) + " машин" + Utils.getPluralSuffixDat(Math.abs(this.diffSlots_our[buildingIndex])) + " нашей команды.\n";
                if (this.diffSlots_our[buildingIndex] > 0) txt = txt + "За это время в это здание наша команда установила еще " + Math.abs(this.diffSlots_our[buildingIndex]) + " машин" + Utils.getPluralSuffixDat(Math.abs(this.diffSlots_our[buildingIndex])) + ".\n";
                if (this.diffSlots_enemy[buildingIndex] < 0) txt = txt + "За это время из этого здания наша команда выбила " + Math.abs(this.diffSlots_enemy[buildingIndex]) + " машин" + Utils.getPluralSuffixDat(Math.abs(this.diffSlots_enemy[buildingIndex])) + " противника.\n";
                if (this.diffSlots_enemy[buildingIndex] > 0) txt = txt + "За это время в это здание команда противника установила еще " + Math.abs(this.diffSlots_enemy[buildingIndex]) + " машин" + Utils.getPluralSuffixDat(Math.abs(this.diffSlots_enemy[buildingIndex])) + ".\n";
                txt = txt + "Это здание наша команда " + mapFCS.get(this.diffBuildingIsOur[buildingIndex]) + ".\n";
                if (this.needAddCarsToBuildingOur[buildingIndex] > 0) txt = txt + "Для захвата этого здания нашей команде нужно установить в него еще " + this.needAddCarsToBuildingOur[buildingIndex] + " машин" + Utils.getPluralSuffixDat(this.needAddCarsToBuildingOur[buildingIndex]) + ".\n";
                if (this.needAddCarsToBuildingEnemy[buildingIndex] > 0) txt = txt + "Для захвата этого здания команде противника нужно установить в него еще " + this.needAddCarsToBuildingEnemy[buildingIndex] + " машин" + Utils.getPluralSuffixDat(this.needAddCarsToBuildingEnemy[buildingIndex]) + ".\n";
                txt = txt + "\n";
            }
        }

        return txt;

    }
    
    
}
