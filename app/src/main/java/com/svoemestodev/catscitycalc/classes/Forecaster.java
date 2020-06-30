package com.svoemestodev.catscitycalc.classes;

import com.svoemestodev.catscitycalc.citycalcclasses.CCAGame;

import java.util.HashMap;
import java.util.Map;

public class Forecaster {

    long timeDiff;
    
    boolean isPresent_blt;
    int needAddCarsToBuildingOur_blt;
    int needAddCarsToBuildingEnemy_blt;
    int diffSlots_blt_our;
    int diffSlots_blt_enemy;
    ForecastStates diffBuildingIsOur_blt;
    ForecastStates diffBuildingIsEnemy_blt;
    ForecastStates state_blt;

    boolean isPresent_blc;
    int needAddCarsToBuildingOur_blc;
    int needAddCarsToBuildingEnemy_blc;
    int diffSlots_blc_our;
    int diffSlots_blc_enemy;
    ForecastStates diffBuildingIsOur_blc;
    ForecastStates diffBuildingIsEnemy_blc;
    ForecastStates state_blc;

    boolean isPresent_blb;
    int needAddCarsToBuildingOur_blb;
    int needAddCarsToBuildingEnemy_blb;
    int diffSlots_blb_our;
    int diffSlots_blb_enemy;
    ForecastStates diffBuildingIsOur_blb;
    ForecastStates diffBuildingIsEnemy_blb;
    ForecastStates state_blb;

    boolean isPresent_brt;
    int needAddCarsToBuildingOur_brt;
    int needAddCarsToBuildingEnemy_brt;
    int diffSlots_brt_our;
    int diffSlots_brt_enemy;
    ForecastStates diffBuildingIsOur_brt;
    ForecastStates diffBuildingIsEnemy_brt;
    ForecastStates state_brt;

    boolean isPresent_brc;
    int needAddCarsToBuildingOur_brc;
    int needAddCarsToBuildingEnemy_brc;
    int diffSlots_brc_our;
    int diffSlots_brc_enemy;
    ForecastStates diffBuildingIsOur_brc;
    ForecastStates diffBuildingIsEnemy_brc;
    ForecastStates state_brc;

    boolean isPresent_brb;
    int needAddCarsToBuildingOur_brb;
    int needAddCarsToBuildingEnemy_brb;
    int diffSlots_brb_our;
    int diffSlots_brb_enemy;
    ForecastStates diffBuildingIsOur_brb;
    ForecastStates diffBuildingIsEnemy_brb;
    ForecastStates state_brb;
    
    public Forecaster(CCAGame ccaGamePrev, CCAGame ccaGameNew) {

        if (ccaGamePrev != null && ccaGameNew != null) {

            this.timeDiff = ccaGameNew.getDateScreenshot().getTime() - ccaGamePrev.getDateScreenshot().getTime();
            
            isPresent_blt = (ccaGameNew.isPresent_blt() && ccaGamePrev.isPresent_blt());
            if (isPresent_blt) {

                state_blt = ccaGameNew.isBuildingIsOur_blt() ? ForecastStates.BUILDING_IS_OUR : ccaGameNew.isBuildingIsEnemy_blt() ? ForecastStates.BUILDING_IS_ENEMY : ForecastStates.BUILDING_IS_EMPTY;
                
                this.diffSlots_blt_our = ccaGameNew.getSlots_blt_our() - ccaGamePrev.getSlots_blt_our();
                this.diffSlots_blt_enemy = ccaGameNew.getSlots_blt_enemy() - ccaGamePrev.getSlots_blt_enemy();

                if (ccaGamePrev.isBuildingIsOur_blt() && ccaGameNew.isBuildingIsOur_blt()) this.diffBuildingIsOur_blt = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsOur_blt() && !ccaGameNew.isBuildingIsOur_blt()) this.diffBuildingIsOur_blt = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsOur_blt() && ccaGameNew.isBuildingIsOur_blt()) this.diffBuildingIsOur_blt = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsOur_blt() && !ccaGameNew.isBuildingIsOur_blt()) this.diffBuildingIsOur_blt = ForecastStates.WAS_NOT_CAPTURED;

                if (ccaGamePrev.isBuildingIsEnemy_blt() && ccaGameNew.isBuildingIsEnemy_blt()) this.diffBuildingIsEnemy_blt = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsEnemy_blt() && !ccaGameNew.isBuildingIsEnemy_blt()) this.diffBuildingIsEnemy_blt = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsEnemy_blt() && ccaGameNew.isBuildingIsEnemy_blt()) this.diffBuildingIsEnemy_blt = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsEnemy_blt() && !ccaGameNew.isBuildingIsEnemy_blt()) this.diffBuildingIsEnemy_blt = ForecastStates.WAS_NOT_CAPTURED;

                int needCarsToCapture_blt = ccaGameNew.getSlots_blt()/2+1;
                this.needAddCarsToBuildingOur_blt = Math.max((needCarsToCapture_blt - ccaGameNew.getSlots_blt_our()), 0);
                this.needAddCarsToBuildingEnemy_blt = Math.max((needCarsToCapture_blt - ccaGameNew.getSlots_blt_enemy()), 0);
                
            }

            isPresent_blc = (ccaGameNew.isPresent_blc() && ccaGamePrev.isPresent_blc());
            if (isPresent_blc) {

                state_blc = ccaGameNew.isBuildingIsOur_blc() ? ForecastStates.BUILDING_IS_OUR : ccaGameNew.isBuildingIsEnemy_blc() ? ForecastStates.BUILDING_IS_ENEMY : ForecastStates.BUILDING_IS_EMPTY;

                this.diffSlots_blc_our = ccaGameNew.getSlots_blc_our() - ccaGamePrev.getSlots_blc_our();
                this.diffSlots_blc_enemy = ccaGameNew.getSlots_blc_enemy() - ccaGamePrev.getSlots_blc_enemy();

                if (ccaGamePrev.isBuildingIsOur_blc() && ccaGameNew.isBuildingIsOur_blc()) this.diffBuildingIsOur_blc = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsOur_blc() && !ccaGameNew.isBuildingIsOur_blc()) this.diffBuildingIsOur_blc = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsOur_blc() && ccaGameNew.isBuildingIsOur_blc()) this.diffBuildingIsOur_blc = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsOur_blc() && !ccaGameNew.isBuildingIsOur_blc()) this.diffBuildingIsOur_blc = ForecastStates.WAS_NOT_CAPTURED;

                if (ccaGamePrev.isBuildingIsEnemy_blc() && ccaGameNew.isBuildingIsEnemy_blc()) this.diffBuildingIsEnemy_blc = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsEnemy_blc() && !ccaGameNew.isBuildingIsEnemy_blc()) this.diffBuildingIsEnemy_blc = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsEnemy_blc() && ccaGameNew.isBuildingIsEnemy_blc()) this.diffBuildingIsEnemy_blc = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsEnemy_blc() && !ccaGameNew.isBuildingIsEnemy_blc()) this.diffBuildingIsEnemy_blc = ForecastStates.WAS_NOT_CAPTURED;

                int needCarsToCapture_blc = ccaGameNew.getSlots_blc()/2+1;
                this.needAddCarsToBuildingOur_blc = Math.max((needCarsToCapture_blc - ccaGameNew.getSlots_blc_our()), 0);
                this.needAddCarsToBuildingEnemy_blc = Math.max((needCarsToCapture_blc - ccaGameNew.getSlots_blc_enemy()), 0);

            }

            isPresent_blb = (ccaGameNew.isPresent_blb() && ccaGamePrev.isPresent_blb());
            if (isPresent_blb) {

                state_blb = ccaGameNew.isBuildingIsOur_blb() ? ForecastStates.BUILDING_IS_OUR : ccaGameNew.isBuildingIsEnemy_blb() ? ForecastStates.BUILDING_IS_ENEMY : ForecastStates.BUILDING_IS_EMPTY;

                this.diffSlots_blb_our = ccaGameNew.getSlots_blb_our() - ccaGamePrev.getSlots_blb_our();
                this.diffSlots_blb_enemy = ccaGameNew.getSlots_blb_enemy() - ccaGamePrev.getSlots_blb_enemy();

                if (ccaGamePrev.isBuildingIsOur_blb() && ccaGameNew.isBuildingIsOur_blb()) this.diffBuildingIsOur_blb = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsOur_blb() && !ccaGameNew.isBuildingIsOur_blb()) this.diffBuildingIsOur_blb = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsOur_blb() && ccaGameNew.isBuildingIsOur_blb()) this.diffBuildingIsOur_blb = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsOur_blb() && !ccaGameNew.isBuildingIsOur_blb()) this.diffBuildingIsOur_blb = ForecastStates.WAS_NOT_CAPTURED;

                if (ccaGamePrev.isBuildingIsEnemy_blb() && ccaGameNew.isBuildingIsEnemy_blb()) this.diffBuildingIsEnemy_blb = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsEnemy_blb() && !ccaGameNew.isBuildingIsEnemy_blb()) this.diffBuildingIsEnemy_blb = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsEnemy_blb() && ccaGameNew.isBuildingIsEnemy_blb()) this.diffBuildingIsEnemy_blb = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsEnemy_blb() && !ccaGameNew.isBuildingIsEnemy_blb()) this.diffBuildingIsEnemy_blb = ForecastStates.WAS_NOT_CAPTURED;

                int needCarsToCapture_blb = ccaGameNew.getSlots_blb()/2+1;
                this.needAddCarsToBuildingOur_blb = Math.max((needCarsToCapture_blb - ccaGameNew.getSlots_blb_our()), 0);
                this.needAddCarsToBuildingEnemy_blb = Math.max((needCarsToCapture_blb - ccaGameNew.getSlots_blb_enemy()), 0);

            }

            isPresent_brt = (ccaGameNew.isPresent_brt() && ccaGamePrev.isPresent_brt());
            if (isPresent_brt) {

                state_brt = ccaGameNew.isBuildingIsOur_brt() ? ForecastStates.BUILDING_IS_OUR : ccaGameNew.isBuildingIsEnemy_brt() ? ForecastStates.BUILDING_IS_ENEMY : ForecastStates.BUILDING_IS_EMPTY;

                this.diffSlots_brt_our = ccaGameNew.getSlots_brt_our() - ccaGamePrev.getSlots_brt_our();
                this.diffSlots_brt_enemy = ccaGameNew.getSlots_brt_enemy() - ccaGamePrev.getSlots_brt_enemy();

                if (ccaGamePrev.isBuildingIsOur_brt() && ccaGameNew.isBuildingIsOur_brt()) this.diffBuildingIsOur_brt = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsOur_brt() && !ccaGameNew.isBuildingIsOur_brt()) this.diffBuildingIsOur_brt = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsOur_brt() && ccaGameNew.isBuildingIsOur_brt()) this.diffBuildingIsOur_brt = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsOur_brt() && !ccaGameNew.isBuildingIsOur_brt()) this.diffBuildingIsOur_brt = ForecastStates.WAS_NOT_CAPTURED;

                if (ccaGamePrev.isBuildingIsEnemy_brt() && ccaGameNew.isBuildingIsEnemy_brt()) this.diffBuildingIsEnemy_brt = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsEnemy_brt() && !ccaGameNew.isBuildingIsEnemy_brt()) this.diffBuildingIsEnemy_brt = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsEnemy_brt() && ccaGameNew.isBuildingIsEnemy_brt()) this.diffBuildingIsEnemy_brt = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsEnemy_brt() && !ccaGameNew.isBuildingIsEnemy_brt()) this.diffBuildingIsEnemy_brt = ForecastStates.WAS_NOT_CAPTURED;

                int needCarsToCapture_brt = ccaGameNew.getSlots_brt()/2+1;
                this.needAddCarsToBuildingOur_brt = Math.max((needCarsToCapture_brt - ccaGameNew.getSlots_brt_our()), 0);
                this.needAddCarsToBuildingEnemy_brt = Math.max((needCarsToCapture_brt - ccaGameNew.getSlots_brt_enemy()), 0);

            }

            isPresent_brc = (ccaGameNew.isPresent_brc() && ccaGamePrev.isPresent_brc());
            if (isPresent_brc) {

                state_brc = ccaGameNew.isBuildingIsOur_brc() ? ForecastStates.BUILDING_IS_OUR : ccaGameNew.isBuildingIsEnemy_brc() ? ForecastStates.BUILDING_IS_ENEMY : ForecastStates.BUILDING_IS_EMPTY;

                this.diffSlots_brc_our = ccaGameNew.getSlots_brc_our() - ccaGamePrev.getSlots_brc_our();
                this.diffSlots_brc_enemy = ccaGameNew.getSlots_brc_enemy() - ccaGamePrev.getSlots_brc_enemy();

                if (ccaGamePrev.isBuildingIsOur_brc() && ccaGameNew.isBuildingIsOur_brc()) this.diffBuildingIsOur_brc = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsOur_brc() && !ccaGameNew.isBuildingIsOur_brc()) this.diffBuildingIsOur_brc = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsOur_brc() && ccaGameNew.isBuildingIsOur_brc()) this.diffBuildingIsOur_brc = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsOur_brc() && !ccaGameNew.isBuildingIsOur_brc()) this.diffBuildingIsOur_brc = ForecastStates.WAS_NOT_CAPTURED;

                if (ccaGamePrev.isBuildingIsEnemy_brc() && ccaGameNew.isBuildingIsEnemy_brc()) this.diffBuildingIsEnemy_brc = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsEnemy_brc() && !ccaGameNew.isBuildingIsEnemy_brc()) this.diffBuildingIsEnemy_brc = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsEnemy_brc() && ccaGameNew.isBuildingIsEnemy_brc()) this.diffBuildingIsEnemy_brc = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsEnemy_brc() && !ccaGameNew.isBuildingIsEnemy_brc()) this.diffBuildingIsEnemy_brc = ForecastStates.WAS_NOT_CAPTURED;

                int needCarsToCapture_brc = ccaGameNew.getSlots_brc()/2+1;
                this.needAddCarsToBuildingOur_brc = Math.max((needCarsToCapture_brc - ccaGameNew.getSlots_brc_our()), 0);
                this.needAddCarsToBuildingEnemy_brc = Math.max((needCarsToCapture_brc - ccaGameNew.getSlots_brc_enemy()), 0);

            }

            isPresent_brb = (ccaGameNew.isPresent_brb() && ccaGamePrev.isPresent_brb());
            if (isPresent_brb) {

                state_brb = ccaGameNew.isBuildingIsOur_brb() ? ForecastStates.BUILDING_IS_OUR : ccaGameNew.isBuildingIsEnemy_brb() ? ForecastStates.BUILDING_IS_ENEMY : ForecastStates.BUILDING_IS_EMPTY;

                this.diffSlots_brb_our = ccaGameNew.getSlots_brb_our() - ccaGamePrev.getSlots_brb_our();
                this.diffSlots_brb_enemy = ccaGameNew.getSlots_brb_enemy() - ccaGamePrev.getSlots_brb_enemy();

                if (ccaGamePrev.isBuildingIsOur_brb() && ccaGameNew.isBuildingIsOur_brb()) this.diffBuildingIsOur_brb = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsOur_brb() && !ccaGameNew.isBuildingIsOur_brb()) this.diffBuildingIsOur_brb = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsOur_brb() && ccaGameNew.isBuildingIsOur_brb()) this.diffBuildingIsOur_brb = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsOur_brb() && !ccaGameNew.isBuildingIsOur_brb()) this.diffBuildingIsOur_brb = ForecastStates.WAS_NOT_CAPTURED;

                if (ccaGamePrev.isBuildingIsEnemy_brb() && ccaGameNew.isBuildingIsEnemy_brb()) this.diffBuildingIsEnemy_brb = ForecastStates.WAS_DEFENDED;
                if (ccaGamePrev.isBuildingIsEnemy_brb() && !ccaGameNew.isBuildingIsEnemy_brb()) this.diffBuildingIsEnemy_brb = ForecastStates.WAS_LOST;
                if (!ccaGamePrev.isBuildingIsEnemy_brb() && ccaGameNew.isBuildingIsEnemy_brb()) this.diffBuildingIsEnemy_brb = ForecastStates.WAS_CAPTURED;
                if (!ccaGamePrev.isBuildingIsEnemy_brb() && !ccaGameNew.isBuildingIsEnemy_brb()) this.diffBuildingIsEnemy_brb = ForecastStates.WAS_NOT_CAPTURED;

                int needCarsToCapture_brb = ccaGameNew.getSlots_brb()/2+1;
                this.needAddCarsToBuildingOur_brb = Math.max((needCarsToCapture_brb - ccaGameNew.getSlots_brb_our()), 0);
                this.needAddCarsToBuildingEnemy_brb = Math.max((needCarsToCapture_brb - ccaGameNew.getSlots_brb_enemy()), 0);

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
        txt = txt + "С момента получения предыдущих данных прошло " + timeDiff/60000 + " минут.\n\n";

        building = 1;
        if (isPresent_blt) {
            txt = txt + "Здание №" + building + ":\n";
            txt = txt + "-----------\n";
            if (diffSlots_blt_our < 0) txt = txt + "За это время из этого здания противник выбил " + Math.abs(diffSlots_blt_our) + " наших машин.\n";
            if (diffSlots_blt_our > 0) txt = txt + "За это время в это здание наша команда установила еще " + Math.abs(diffSlots_blt_our) + " машин.\n";
            if (diffSlots_blt_enemy < 0) txt = txt + "За это время из этого здания наша команда выбила " + Math.abs(diffSlots_blt_enemy) + " машин противника.\n";
            if (diffSlots_blt_enemy > 0) txt = txt + "За это время в это здание команда противника установила еще " + Math.abs(diffSlots_blt_enemy) + " машин.\n";
            txt = txt + "Это здание наша команда " + mapFCS.get(diffBuildingIsOur_blt) + ".\n";
            if (needAddCarsToBuildingOur_blt > 0) txt = txt + "Для захвата этого здания нашей команде нужно установить в него еще " + needAddCarsToBuildingOur_blt + " машин.\n";
            txt = txt + "Это здание команда противника " + mapFCS.get(diffBuildingIsEnemy_blt) + ".\n";
            if (needAddCarsToBuildingEnemy_blt > 0) txt = txt + "Для захвата этого здания команде противника нужно установить в него еще " + needAddCarsToBuildingEnemy_blt + " машин.\n";
            txt = txt + "Это здание на данный момент " + mapFCS.get(state_blt) + ".\n\n";
        }

        building = 2;
        if (isPresent_blc) {
            txt = txt + "Здание №" + building + ":\n";
            txt = txt + "-----------\n";
            if (diffSlots_blc_our < 0) txt = txt + "За это время из этого здания противник выбил " + Math.abs(diffSlots_blc_our) + " наших машин.\n";
            if (diffSlots_blc_our > 0) txt = txt + "За это время в это здание наша команда установила еще " + Math.abs(diffSlots_blc_our) + " машин.\n";
            if (diffSlots_blc_enemy < 0) txt = txt + "За это время из этого здания наша команда выбила " + Math.abs(diffSlots_blc_enemy) + " машин противника.\n";
            if (diffSlots_blc_enemy > 0) txt = txt + "За это время в это здание команда противника установила еще " + Math.abs(diffSlots_blc_enemy) + " машин.\n";
            txt = txt + "Это здание наша команда " + mapFCS.get(diffBuildingIsOur_blc) + ".\n";
            if (needAddCarsToBuildingOur_blc > 0) txt = txt + "Для захвата этого здания нашей команде нужно установить в него еще " + needAddCarsToBuildingOur_blc + " машин.\n";
            txt = txt + "Это здание команда противника " + mapFCS.get(diffBuildingIsEnemy_blc) + ".\n";
            if (needAddCarsToBuildingEnemy_blc > 0) txt = txt + "Для захвата этого здания команде противника нужно установить в него еще " + needAddCarsToBuildingEnemy_blc + " машин.\n";
            txt = txt + "Это здание на данный момент " + mapFCS.get(state_blc) + ".\n\n";
        }

        building = 3;
        if (isPresent_blb) {
            txt = txt + "Здание №" + building + ":\n";
            txt = txt + "-----------\n";
            if (diffSlots_blb_our < 0) txt = txt + "За это время из этого здания противник выбил " + Math.abs(diffSlots_blb_our) + " наших машин.\n";
            if (diffSlots_blb_our > 0) txt = txt + "За это время в это здание наша команда установила еще " + Math.abs(diffSlots_blb_our) + " машин.\n";
            if (diffSlots_blb_enemy < 0) txt = txt + "За это время из этого здания наша команда выбила " + Math.abs(diffSlots_blb_enemy) + " машин противника.\n";
            if (diffSlots_blb_enemy > 0) txt = txt + "За это время в это здание команда противника установила еще " + Math.abs(diffSlots_blb_enemy) + " машин.\n";
            txt = txt + "Это здание наша команда " + mapFCS.get(diffBuildingIsOur_blb) + ".\n";
            if (needAddCarsToBuildingOur_blb > 0) txt = txt + "Для захвата этого здания нашей команде нужно установить в него еще " + needAddCarsToBuildingOur_blb + " машин.\n";
            txt = txt + "Это здание команда противника " + mapFCS.get(diffBuildingIsEnemy_blb) + ".\n";
            if (needAddCarsToBuildingEnemy_blb > 0) txt = txt + "Для захвата этого здания команде противника нужно установить в него еще " + needAddCarsToBuildingEnemy_blb + " машин.\n";
            txt = txt + "Это здание на данный момент " + mapFCS.get(state_blb) + ".\n\n";
        }

        building = 4;
        if (isPresent_brt) {
            txt = txt + "Здание №" + building + ":\n";
            txt = txt + "-----------\n";
            if (diffSlots_brt_our < 0) txt = txt + "За это время из этого здания противник выбил " + Math.abs(diffSlots_brt_our) + " наших машин.\n";
            if (diffSlots_brt_our > 0) txt = txt + "За это время в это здание наша команда установила еще " + Math.abs(diffSlots_brt_our) + " машин.\n";
            if (diffSlots_brt_enemy < 0) txt = txt + "За это время из этого здания наша команда выбила " + Math.abs(diffSlots_brt_enemy) + " машин противника.\n";
            if (diffSlots_brt_enemy > 0) txt = txt + "За это время в это здание команда противника установила еще " + Math.abs(diffSlots_brt_enemy) + " машин.\n";
            txt = txt + "Это здание наша команда " + mapFCS.get(diffBuildingIsOur_brt) + ".\n";
            if (needAddCarsToBuildingOur_brt > 0) txt = txt + "Для захвата этого здания нашей команде нужно установить в него еще " + needAddCarsToBuildingOur_brt + " машин.\n";
            txt = txt + "Это здание команда противника " + mapFCS.get(diffBuildingIsEnemy_brt) + ".\n";
            if (needAddCarsToBuildingEnemy_brt > 0) txt = txt + "Для захвата этого здания команде противника нужно установить в него еще " + needAddCarsToBuildingEnemy_brt + " машин.\n";
            txt = txt + "Это здание на данный момент " + mapFCS.get(state_brt) + ".\n\n";
        }

        building = 5;
        if (isPresent_brc) {
            txt = txt + "Здание №" + building + ":\n";
            txt = txt + "-----------\n";
            if (diffSlots_brc_our < 0) txt = txt + "За это время из этого здания противник выбил " + Math.abs(diffSlots_brc_our) + " наших машин.\n";
            if (diffSlots_brc_our > 0) txt = txt + "За это время в это здание наша команда установила еще " + Math.abs(diffSlots_brc_our) + " машин.\n";
            if (diffSlots_brc_enemy < 0) txt = txt + "За это время из этого здания наша команда выбила " + Math.abs(diffSlots_brc_enemy) + " машин противника.\n";
            if (diffSlots_brc_enemy > 0) txt = txt + "За это время в это здание команда противника установила еще " + Math.abs(diffSlots_brc_enemy) + " машин.\n";
            txt = txt + "Это здание наша команда " + mapFCS.get(diffBuildingIsOur_brc) + ".\n";
            if (needAddCarsToBuildingOur_brc > 0) txt = txt + "Для захвата этого здания нашей команде нужно установить в него еще " + needAddCarsToBuildingOur_brc + " машин.\n";
            txt = txt + "Это здание команда противника " + mapFCS.get(diffBuildingIsEnemy_brc) + ".\n";
            if (needAddCarsToBuildingEnemy_brc > 0) txt = txt + "Для захвата этого здания команде противника нужно установить в него еще " + needAddCarsToBuildingEnemy_brc + " машин.\n";
            txt = txt + "Это здание на данный момент " + mapFCS.get(state_brc) + ".\n\n";
        }

        building = 6;
        if (isPresent_brb) {
            txt = txt + "Здание №" + building + ":\n";
            txt = txt + "-----------\n";
            if (diffSlots_brb_our < 0) txt = txt + "За это время из этого здания противник выбил " + Math.abs(diffSlots_brb_our) + " наших машин.\n";
            if (diffSlots_brb_our > 0) txt = txt + "За это время в это здание наша команда установила еще " + Math.abs(diffSlots_brb_our) + " машин.\n";
            if (diffSlots_brb_enemy < 0) txt = txt + "За это время из этого здания наша команда выбила " + Math.abs(diffSlots_brb_enemy) + " машин противника.\n";
            if (diffSlots_brb_enemy > 0) txt = txt + "За это время в это здание команда противника установила еще " + Math.abs(diffSlots_brb_enemy) + " машин.\n";
            txt = txt + "Это здание наша команда " + mapFCS.get(diffBuildingIsOur_brb) + ".\n";
            if (needAddCarsToBuildingOur_brb > 0) txt = txt + "Для захвата этого здания нашей команде нужно установить в него еще " + needAddCarsToBuildingOur_brb + " машин.\n";
            txt = txt + "Это здание команда противника " + mapFCS.get(diffBuildingIsEnemy_brb) + ".\n";
            if (needAddCarsToBuildingEnemy_brb > 0) txt = txt + "Для захвата этого здания команде противника нужно установить в него еще " + needAddCarsToBuildingEnemy_brb + " машин.\n";
            txt = txt + "Это здание на данный момент " + mapFCS.get(state_brb) + ".\n\n";
        }
        
        return txt;

    }
    
    
}
