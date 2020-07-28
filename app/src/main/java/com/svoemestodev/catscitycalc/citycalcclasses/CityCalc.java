package com.svoemestodev.catscitycalc.citycalcclasses;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.svoemestodev.catscitycalc.GlobalApplication;
import com.svoemestodev.catscitycalc.database.DbTeamGame;
import com.svoemestodev.catscitycalc.ssa.SSA_Area;
import com.svoemestodev.catscitycalc.ssa.SSA_Areas;
import com.svoemestodev.catscitycalc.ssa.SSA_Colors;
import com.svoemestodev.catscitycalc.ssa.SSA_Key;
import com.svoemestodev.catscitycalc.ssa.SSA_Rules;
import com.svoemestodev.catscitycalc.ssa.SSA_Screenshot;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;
import com.svoemestodev.catscitycalc.utils.PictureProcessorDirection;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CityCalc { //extends Activity {

    private File fileScreenshot;
    private SSA_Screenshot ssaScreenshot;
    private CityCalcType cityCalcType;
    private String userNIC = "";
    private String userUID = "";
    private String teamID = "";

    private Map<String, CityCalcArea> mapAreas = new HashMap<>(); // мап областей
    private static final String TAG = "CityCalc";
    private CityCalc thisCityCalc = null;

    public CityCalc() {
    }

    public CityCalc getClone() {
        CityCalc clone = new CityCalc();

        clone.fileScreenshot = this.fileScreenshot;
        clone.ssaScreenshot = this.ssaScreenshot.getClone();
        clone.cityCalcType = this.cityCalcType;
        clone.userNIC = this.userNIC;
        clone.userUID = this.userUID;
        clone.teamID = this.teamID;
        clone.mapAreas = new HashMap<>(); // мап областей
        for (Map.Entry<String, CityCalcArea> pair: this.mapAreas.entrySet()) {
            String areaKey = pair.getKey();
            CityCalcArea cityCalcArea = pair.getValue();
            
            if (areaKey.equals(SSA_Key.AREA_CITY.getKey())) {
                CCAGame ccaGame = ((CCAGame)cityCalcArea).getClone(clone);
                clone.mapAreas.put(ccaGame.getSsaArea().getKey(), ccaGame);
            } else if (areaKey.equals(SSA_Key.AREA_CITY_TEAM_NAME_OUR.getKey()) || areaKey.equals(SSA_Key.AREA_CITY_TEAM_NAME_ENEMY.getKey())) {
                CCATeam ccaTeam = ((CCATeam)cityCalcArea).getClone(clone);
                clone.mapAreas.put(ccaTeam.getSsaArea().getKey(), ccaTeam);
            } else if (areaKey.equals(SSA_Key.AREA_CITY_BLD1.getKey()) ||
                    areaKey.equals(SSA_Key.AREA_CITY_BLD2.getKey()) ||
                    areaKey.equals(SSA_Key.AREA_CITY_BLD3.getKey()) ||
                    areaKey.equals(SSA_Key.AREA_CITY_BLD4.getKey()) ||
                    areaKey.equals(SSA_Key.AREA_CITY_BLD5.getKey()) ||
                    areaKey.equals(SSA_Key.AREA_CITY_BLD6.getKey())) {
                CCABuilding ccaBuilding = ((CCABuilding)cityCalcArea).getClone(clone);
                clone.mapAreas.put(ccaBuilding.getSsaArea().getKey(), ccaBuilding);
            } else if (areaKey.equals(SSA_Key.AREA_CAR_BOX_IN_CITY.getKey()) || areaKey.equals(SSA_Key.AREA_GARAGE_HEALTH_SHIELD_ENERGY.getKey())) {
                CCACar ccaCar = ((CCACar)cityCalcArea).getClone(clone);
                clone.mapAreas.put(ccaCar.getSsaArea().getKey(), ccaCar);
            } else {
                CityCalcArea cca = cityCalcArea.getClone(clone);
                clone.mapAreas.put(cca.getSsaArea().getKey(), cca);
            }
            
        }


        return clone;
    }


    public File getFileScreenshot() {
        return fileScreenshot;
    }

    public void setFileScreenshot(File fileScreenshot) {
        this.fileScreenshot = fileScreenshot;
    }

    public SSA_Screenshot getSsaScreenshot() {
        return ssaScreenshot;
    }

    public void setSsaScreenshot(SSA_Screenshot ssaScreenshot) {
        this.ssaScreenshot = ssaScreenshot;
    }

    public CityCalcType getCityCalcType() {
        return cityCalcType;
    }

    public void setCityCalcType(CityCalcType cityCalcType) {
        this.cityCalcType = cityCalcType;
    }

    public Map<String, CityCalcArea> getMapAreas() {
        return mapAreas;
    }

    public void setMapAreas(Map<String, CityCalcArea> mapAreas) {
        this.mapAreas = mapAreas;
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

    public String getUserNIC() {
        return userNIC;
    }

    public void setUserNIC(String userNIC) {
        this.userNIC = userNIC;
    }

    // конструктор для калибровки центра экрана
    public CityCalc(File file, int calibrateX, int calibrateY, CityCalcType cityCalcType) {
        String logMsgPref = "конструктор для калибровки центра экрана, бордюров и т.п.: ";
        Log.i(TAG, logMsgPref + "start");

        this.cityCalcType = cityCalcType;
        this.fileScreenshot = file;
        thisCityCalc = this;

        if (fileScreenshot != null) {         // если файл не нулл
            if (fileScreenshot.exists()) {    // если файл физически существует
                Bitmap bmpScreenshot = BitmapFactory.decodeFile(fileScreenshot.getAbsolutePath());
                if (bmpScreenshot != null) {
                    if (bmpScreenshot.getWidth() > bmpScreenshot.getHeight()) {
                        this.ssaScreenshot = new SSA_Screenshot(bmpScreenshot, calibrateX, calibrateY);
                        thisCityCalc = new CityCalc(this, false);

                        this.mapAreas = thisCityCalc.mapAreas;

                    }

                }
            }
        }
    }

    // конструктор для проверки боксов
    public CityCalc(File file, int calibrateX, int calibrateY, String userNIC, String userUID, String teamID) {
        String logMsgPref = "конструктор для для проверки боксов: ";
        Log.i(TAG, logMsgPref + "start");


        this.userNIC = userNIC;
        this.userUID = userUID;
        this.teamID = teamID;
        this.cityCalcType = CityCalcType.ERROR;
        this.fileScreenshot = file;
        thisCityCalc = this;

        if (fileScreenshot != null) {         // если файл не нулл
            if (fileScreenshot.exists()) {    // если файл физически существует
                Bitmap bmpScreenshot = BitmapFactory.decodeFile(fileScreenshot.getAbsolutePath());
                if (bmpScreenshot != null) {
                    if (bmpScreenshot.getWidth() > bmpScreenshot.getHeight()) { //если пропорции экрана правильные - скрин может быть из игры
                        this.ssaScreenshot = new SSA_Screenshot(bmpScreenshot, calibrateX, calibrateY);

//                        setAreaToMap(SSA_Key.AREA_GAME_BOX_BACK.getKey());
//                        setAreaToMap(SSA_Key.AREA_CITY_BOX_INFO.getKey());
//                        setAreaToMap(SSA_Key.AREA_CITY_BOX_GRAY.getKey());
//                        setAreaToMap(SSA_Key.AREA_CAR_BOX_IN_CITY.getKey());
//                        setAreaToMap(SSA_Key.AREA_GARAGE_HEALTH_SHIELD_ENERGY.getKey());
//

                        SSA_Screenshot ssaScr = this.ssaScreenshot;

                        if (SSA_Rules.getRule(SSA_Key.RULE_SCR_IS_GAME_IN_CITY.getKey()).check(ssaScr)) {
                            this.cityCalcType = CityCalcType.GAME;
                        } else if (SSA_Rules.getRule(SSA_Key.RULE_SCR_IS_CAR_IN_CITY.getKey()).check(ssaScr)) {
                            this.cityCalcType = CityCalcType.CAR;
                        } else if (SSA_Rules.getRule(SSA_Key.RULE_SCR_IS_CAR_IN_GARAGE.getKey()).check(ssaScr)) {
                            this.cityCalcType = CityCalcType.CAR;
                        } else {
                            this.cityCalcType = CityCalcType.ERROR;
                        }
                    } else { //если пропорции экрана неправильные - скрин не из игры
                        this.cityCalcType = CityCalcType.ERROR;
                    }

                }
            }
        }


    }

    
    private void setAreaToMap(String areaKey) {

        if (areaKey.equals(SSA_Key.AREA_CITY.getKey())) {
            mapAreas.put(areaKey, new CCAGame(thisCityCalc, SSA_Areas.getArea(areaKey)));
        } else if (areaKey.equals(SSA_Key.AREA_CITY_TEAM_NAME_OUR.getKey()) || areaKey.equals(SSA_Key.AREA_CITY_TEAM_NAME_ENEMY.getKey())) {
            mapAreas.put(areaKey, new CCATeam(thisCityCalc, SSA_Areas.getArea(areaKey)));
        } else if (areaKey.equals(SSA_Key.AREA_CITY_BLD1.getKey()) ||
                areaKey.equals(SSA_Key.AREA_CITY_BLD2.getKey()) ||
                areaKey.equals(SSA_Key.AREA_CITY_BLD3.getKey()) ||
                areaKey.equals(SSA_Key.AREA_CITY_BLD4.getKey()) ||
                areaKey.equals(SSA_Key.AREA_CITY_BLD5.getKey()) ||
                areaKey.equals(SSA_Key.AREA_CITY_BLD6.getKey())) {
            mapAreas.put(areaKey, new CCABuilding(thisCityCalc, SSA_Areas.getArea(areaKey)));
        } else if (areaKey.equals(SSA_Key.AREA_CAR_PICTURE.getKey()) || areaKey.equals(SSA_Key.AREA_GARAGE_CAR.getKey())) {
            mapAreas.put(areaKey, new CCACar(thisCityCalc, SSA_Areas.getArea(areaKey)));
        } else {
            mapAreas.put(areaKey, new CityCalcArea(thisCityCalc, SSA_Areas.getArea(areaKey)));
        }

        
    }
    
    // конструктор
    public CityCalc(CityCalc checkedCityCalc, boolean isRealtimeScreenshot) {

        String logMsgPref = "конструктор: ";
        Log.i(TAG, logMsgPref + "start");

        CityCalcType cityCalcType = checkedCityCalc.cityCalcType;
        File file = checkedCityCalc.fileScreenshot;
        this.userNIC = checkedCityCalc.userNIC;
        this.userUID = checkedCityCalc.userUID;
        this.teamID = checkedCityCalc.teamID;
        this.cityCalcType = cityCalcType;
        this.fileScreenshot = file;
        this.ssaScreenshot = checkedCityCalc.ssaScreenshot.getClone();
        thisCityCalc = this;

        try {
            if (fileScreenshot != null) {         // если файл не нулл
                if (fileScreenshot.exists()) {    // если файл физически существует

                    if (cityCalcType.equals(CityCalcType.GAME)) {
                        setAreaToMap(SSA_Key.AREA_CITY.getKey());
                        if (isRealtimeScreenshot) new DbTeamGame((CCAGame)mapAreas.get(SSA_Key.AREA_CITY.getKey()));
                        ((CCAGame)mapAreas.get(SSA_Key.AREA_CITY.getKey())).calcWin(true);

                        if (!fileScreenshot.getAbsolutePath().equals(GlobalApplication.pathToCATScalcFolder + "/last_screenshot.PNG")) {
                            Utils.copyFile(fileScreenshot.getAbsolutePath(), GlobalApplication.pathToCATScalcFolder + "/last_screenshot.PNG");
                        }

                    } else if (cityCalcType.equals(CityCalcType.CAR)) {

                        boolean isCarInCity = SSA_Rules.getRule(SSA_Key.RULE_SCR_IS_CAR_IN_CITY.getKey()).check(this.getSsaScreenshot());
                        boolean isCarInGarage = SSA_Rules.getRule(SSA_Key.RULE_SCR_IS_CAR_IN_GARAGE.getKey()).check(this.getSsaScreenshot());

                        if (isCarInCity) {
                            setAreaToMap(SSA_Key.AREA_CAR_PICTURE.getKey());
                        } else if (isCarInGarage) {
                            setAreaToMap(SSA_Key.AREA_GARAGE_CAR.getKey());
                        }

                    }

                } // кесли файл физически существует
            } // кесли файл не нулл

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
