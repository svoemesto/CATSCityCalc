package com.svoemestodev.catscitycalc.citycalcclasses;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.svoemestodev.catscitycalc.GlobalApplication;
import com.svoemestodev.catscitycalc.ssa.SSA_Area;
import com.svoemestodev.catscitycalc.ssa.SSA_Areas;
import com.svoemestodev.catscitycalc.ssa.SSA_Colors;
import com.svoemestodev.catscitycalc.ssa.SSA_Screenshot;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;
import com.svoemestodev.catscitycalc.utils.PictureProcessorDirection;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CityCalc { //extends Activity {

    private File fileScreenshot;
    private Bitmap bmpScreenshot;       // исходный скриншот
    private int calibrateX;             // сдвиг центра по X
    private int calibrateY;             // сдвиг центра по Y
    private CityCalcType cityCalcType;
    private String userNIC = "";
    private String userUID = "";
    private String teamID = "";

    private Map<Area, CityCalcArea> mapAreas = new HashMap<>(); // мап областей
    private static final String TAG = "CityCalc";
    private CityCalc thisCityCalc = null;

    public CityCalc() {
    }

    public CityCalc getClone() {
        CityCalc clone = new CityCalc();

        clone.fileScreenshot = this.fileScreenshot;
        clone.bmpScreenshot = this.bmpScreenshot;
        clone.calibrateX = this.calibrateX;
        clone.calibrateY = this.calibrateY;
        clone.cityCalcType = this.cityCalcType;
        clone.userNIC = this.userNIC;
        clone.userUID = this.userUID;
        clone.teamID = this.teamID;
        clone.mapAreas = new HashMap<>(); // мап областей
        for (Map.Entry<Area, CityCalcArea> pair: this.mapAreas.entrySet()) {
            Area area = pair.getKey();
            CityCalcArea cityCalcArea = pair.getValue();
            switch (area) {
                case CITY:
                    CCAGame ccaGame = ((CCAGame)cityCalcArea).getClone(clone);
                    clone.mapAreas.put(ccaGame.getArea(), ccaGame);
                    break;
                case TEAM_NAME_OUR:
                case TEAM_NAME_ENEMY:
                    CCATeam ccaTeam = ((CCATeam)cityCalcArea).getClone(clone);
                    clone.mapAreas.put(ccaTeam.getArea(), ccaTeam);
                    break;
                case BLT:
                case BLC:
                case BLB:
                case BRT:
                case BRC:
                case BRB:
                    CCABuilding ccaBuilding = ((CCABuilding)cityCalcArea).getClone(clone);
                    clone.mapAreas.put(ccaBuilding.getArea(), ccaBuilding);
                    break;
                case CAR_IN_CITY_INFO:
                case CAR_IN_CITY_BUILDING:
                    CCACar ccaCar = ((CCACar)cityCalcArea).getClone(clone);
                    clone.mapAreas.put(ccaCar.getArea(), ccaCar);
                    break;
                default:
                    CityCalcArea cca = cityCalcArea.getClone(clone);
                    clone.mapAreas.put(cca.getArea(), cca);
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

    public Bitmap getBmpScreenshot() {
        return bmpScreenshot;
    }

    public void setBmpScreenshot(Bitmap bmpScreenshot) {
        this.bmpScreenshot = bmpScreenshot;
    }

    public int getCalibrateX() {
        return calibrateX;
    }

    public void setCalibrateX(int calibrateX) {
        this.calibrateX = calibrateX;
    }

    public int getCalibrateY() {
        return calibrateY;
    }

    public void setCalibrateY(int calibrateY) {
        this.calibrateY = calibrateY;
    }

    public CityCalcType getCityCalcType() {
        return cityCalcType;
    }

    public void setCityCalcType(CityCalcType cityCalcType) {
        this.cityCalcType = cityCalcType;
    }

    public Map<Area, CityCalcArea> getMapAreas() {
        return mapAreas;
    }

    public void setMapAreas(Map<Area, CityCalcArea> mapAreas) {
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

    //    public Context getContext() {
//        return context;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }

    public String getUserNIC() {
        return userNIC;
    }

    public void setUserNIC(String userNIC) {
        this.userNIC = userNIC;
    }
    
    public SSA_Screenshot getSsaScreenshot() {
        return new SSA_Screenshot(this.bmpScreenshot, this.calibrateX, this.calibrateY);
    }

    public boolean isAreaPresent(String areaKey) {
        return isAreaPresent(SSA_Areas.getArea(areaKey));
    }

    public boolean isAreaPresent(SSA_Area ssaArea) {
        boolean result = false;

        String colorKey;
        int threshold;
        float minFrequency, maxFrequency;

        String areaKey = ssaArea.getKey();
        SSA_Screenshot ssaScreenshot = getSsaScreenshot();
        Bitmap areaBitmap = ssaArea.getAreaBitmap(ssaScreenshot);
        if (areaBitmap != null) {
            switch (areaKey) {
                case "CITY_TIME":
                case "CITY_BOX_GRAY":
                    return  PictureProcessor.isConditionTrue(areaBitmap, SSA_Colors.getColor("GRAY_CITY_EARLY_WIN"),10,0.990f, 1.000f);
                case "CAR_BOX_IN_CITY":
                    return  PictureProcessor.isConditionTrue(areaBitmap, SSA_Colors.getColor("CAR_BLUE_BOX_IN_CITY"),10,0.990f, 1.000f);
                case "CITY_TEAM_NAME_OUR":
                case "CITY_TEAM_NAME_ENEMY":
                case "CITY_EARLY_WIN":
                case "CITY_POINTS_AND_INCREASE_OUR":
                case "CITY_POINTS_OUR":
                case "CITY_POINTS_AND_INCREASE_ENEMY":
                case "CITY_POINTS_ENEMY":
                case "CITY_BLD1":
                case "CITY_BLD1_NAME":
                case "CITY_BLD1_SLOTS":
                case "CITY_BLD1_PROGRESS":
                case "CITY_BLD2":
                case "CITY_BLD2_NAME":
                case "CITY_BLD2_SLOTS":
                case "CITY_BLD2_PROGRESS":
                case "CITY_BLD3":
                case "CITY_BLD3_NAME":
                case "CITY_BLD3_SLOTS":
                case "CITY_BLD3_PROGRESS":
                case "CITY_BLD4":
                case "CITY_BLD4_NAME":
                case "CITY_BLD4_SLOTS":
                case "CITY_BLD4_PROGRESS":
                case "CITY_BLD5":
                case "CITY_BLD5_NAME":
                case "CITY_BLD5_SLOTS":
                case "CITY_BLD5_PROGRESS":
                case "CITY_BLD6":
                case "CITY_BLD6_NAME":
                case "CITY_BLD6_SLOTS":
                case "CITY_BLD6_PROGRESS":
                case "CITY_BOX_INFO":
                    return  PictureProcessor.isConditionTrue(areaBitmap, SSA_Colors.getColor("HEALBOX_RED"),10,0.600f, 1.000f) &&
                            PictureProcessor.isConditionTrue(areaBitmap, SSA_Colors.getColor("WHITE"),10,0.001f, 1.000f);
                case "GAME_BOX_BACK":
                    return  PictureProcessor.isConditionTrue(areaBitmap, SSA_Colors.getColor("HEALBOX_RED"),10,0.700f, 1.000f) &&
                            PictureProcessor.isConditionTrue(areaBitmap, SSA_Colors.getColor("HEALBOX_YELLOW"),10,0.001f, 1.000f);
                case "GARAGE_HEALTH_SHIELD_ENERGY":
                    return  PictureProcessor.isConditionTrue(areaBitmap, SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),10,0.1f, 1.000f) &&
                            PictureProcessor.isConditionTrue(areaBitmap, SSA_Colors.getColor("HEALTH_RED_DARK"),10,0.0001f, 1.000f) &&
                            PictureProcessor.isConditionTrue(areaBitmap, SSA_Colors.getColor("SHIELD_BLUE"),10,0.0001f, 1.000f);
                case "GARAGE_HEALTH":
                case "GARAGE_SHIELD":
                case "GARAGE_ENERGY":
                case "GARAGE_SLOT1":
                case "GARAGE_SLOT2":
                case "GARAGE_SLOT3":
                case "GARAGE_CAR":
                case "ATTACK_DEFENCE_BUILDING_NAME":
                case "ATTACK_DEFENCE_BOX":
                case "CAR_IN_CITY_ATTACK_DEFENCE":
                case "CAR_SLOT1":
                case "CAR_SLOT2":
                case "CAR_SLOT3":
                case "CAR_HEALTH":
                case "CAR_SHIELD":
                case "CAR_PICTURE":
                case "CAR_HEALBOX":
                case "CAR_BOX_DEFENCE_BREAK":
                case "CAR_TIMELINE":
                case "CAR_REPAIRING_IN_DEFENCE":
                default:
            }
        }


        return result;
    }

    // конструктор для калибровки центра экрана
    public CityCalc(File file, int calibrateX, int calibrateY, CityCalcType cityCalcType) {
        String logMsgPref = "конструктор для калибровки центра экрана, бордюров и т.п.: ";
        Log.i(TAG, logMsgPref + "start");

        this.cityCalcType = cityCalcType;
        this.fileScreenshot = file;
        this.calibrateX = calibrateX;
        this.calibrateY = calibrateY;
//        this.context = context;

        thisCityCalc = this;

        if (fileScreenshot != null) {         // если файл не нулл
            if (fileScreenshot.exists()) {    // если файл физически существует
                bmpScreenshot = BitmapFactory.decodeFile(fileScreenshot.getAbsolutePath());
                if (this.bmpScreenshot != null) {
                    if (this.bmpScreenshot.getWidth() > this.bmpScreenshot.getHeight()) {

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
        this.calibrateX = calibrateX;
        this.calibrateY = calibrateY;
//        this.context = context;

        thisCityCalc = this;

        if (fileScreenshot != null) {         // если файл не нулл
            if (fileScreenshot.exists()) {    // если файл физически существует
                bmpScreenshot = BitmapFactory.decodeFile(fileScreenshot.getAbsolutePath());
                if (this.bmpScreenshot != null) {
                    if (this.bmpScreenshot.getWidth() > this.bmpScreenshot.getHeight()) { //если пропорции экрана правильные - скрин может быть из игры

                        if (isAreaPresent("GAME_BOX_BACK")) { // если найден квадрат "назад" - скрин из игры
                            if (isAreaPresent("CITY_BOX_INFO") && isAreaPresent("CITY_BOX_GRAY")) { // если нашлись красный квадрат с i и серый квадра рядом со времени - скрин из города
                                if (isAreaPresent("CAR_BOX_IN_CITY")) { // квадрат голубой - это скрин машинки в городе
                                    this.cityCalcType = CityCalcType.CAR;
                                } else { // квадрат не голубой - значит это скрин города
                                    this.cityCalcType = CityCalcType.GAME;
                                }
                            } else { // если не нашлись красный квадрат с i и серый квадрат рядом со времени - скрин не из города
                                if (isAreaPresent("CAR_BOX_IN_CITY")) { // квадрат голубой - это скрин машинки в городе
                                    this.cityCalcType = CityCalcType.CAR;
                                } else { // квадрат не голубой - то это или гараж, или ошибка
                                    if (isAreaPresent("GARAGE_HEALTH_SHIELD_ENERGY")) { // гараж
                                        this.cityCalcType = CityCalcType.CAR;
                                    } else { // ошибка
                                        this.cityCalcType = CityCalcType.ERROR;
                                    }
                                }
                            }
                        } else { // если не найден квадрат "назад" - скрин не из игры
                            this.cityCalcType = CityCalcType.ERROR;
                        }

//                        setAreaToMap(Area.BOX_BACK);
//                        CityCalcArea ccaBoxBack = mapAreas.get(Area.BOX_BACK);
//                        boolean isGameScreenshot = PictureProcessor.frequencyPixelInBitmap(ccaBoxBack.getBmpSrc(), GlobalApplication.getRgb_box_back_main() , GlobalApplication.getRgb_box_back_thm(), GlobalApplication.getRgb_box_back_thp()) > 0.50f &&
//                                PictureProcessor.frequencyPixelInBitmap(ccaBoxBack.getBmpSrc(), GlobalApplication.getRgb_box_back_back() ,GlobalApplication.getRgb_box_back_thm(), GlobalApplication.getRgb_box_back_thp()) > 0.01f;
//
//                        if (isGameScreenshot) { // если найден квадрат "назад" - скрин из игры
//
//                            setAreaToMap(Area.BOX_INFO_CITY); // красный квадрат с i
//                            setAreaToMap(Area.BOX_INFO_CAR);  // серый квадрат времени игры
//                            CityCalcArea ccaBox1Info = mapAreas.get(Area.BOX_INFO_CITY);
//                            CityCalcArea ccaBox2Info = mapAreas.get(Area.BOX_INFO_CAR);
//                            boolean isGame1 = PictureProcessor.frequencyPixelInBitmap(ccaBox1Info.getBmpSrc(), GlobalApplication.getRgb_box_info_city_main(),GlobalApplication.getRgb_box_info_city_thm(), GlobalApplication.getRgb_box_info_city_thp()) > 0.50f;
//                            boolean isGame2 = PictureProcessor.frequencyPixelInBitmap(ccaBox2Info.getBmpSrc(), GlobalApplication.getRgb_box_info_car_main(),GlobalApplication.getRgb_box_info_car_thm(), GlobalApplication.getRgb_box_info_car_thp()) > 0.50f;
//
//                            if (isGame1 && isGame2) { // если нашлись красный квадрат с i и серый квадра рядом со времени - скрин из города
//                                setAreaToMap(Area.CAR_IN_CITY_BOX1);
//                                CityCalcArea ccaCarBox1 = mapAreas.get(Area.CAR_IN_CITY_BOX1); // если на скрине машинка - этот квадрат голубой
//                                boolean isCar1 = PictureProcessor.frequencyPixelInBitmap(ccaCarBox1.getBmpSrc(), GlobalApplication.getRgb_car_in_city_box1_main(), GlobalApplication.getRgb_car_in_city_box1_thm(), GlobalApplication.getRgb_car_in_city_box1_thp()) > 0.50f;
//                                if (isCar1) { // квадрат голубой - это скрин машинки в городе
//                                    this.cityCalcType = CityCalcType.CAR;
//                                } else { // квадрат не голубой - значит это скрин города
//                                    this.cityCalcType = CityCalcType.GAME;
//                                }
//
//                            } else { // если не нашлись красный квадрат с i и серый квадра рядом со времени - скрин не из города
//
//                                setAreaToMap(Area.CAR_IN_CITY_BOX1);
//                                CityCalcArea ccaCarBox1 = mapAreas.get(Area.CAR_IN_CITY_BOX1); // если на скрине машинка - этот квадрат голубой
//                                boolean isCar1 = PictureProcessor.frequencyPixelInBitmap(ccaCarBox1.getBmpSrc(), GlobalApplication.getRgb_car_in_city_box1_main(), GlobalApplication.getRgb_car_in_city_box1_thm(), GlobalApplication.getRgb_car_in_city_box1_thp()) > 0.50f;
//                                if (isCar1) { // квадрат голубой - это скрин машинки в городе
//                                    this.cityCalcType = CityCalcType.CAR;
//                                } else { // квадрат не голубой - то это или гараж, или ошибка
//                                    setAreaToMap(Area.BOX_INFO_GARAGE);
//                                    CityCalcArea ccaGarage = mapAreas.get(Area.BOX_INFO_GARAGE);
//                                    boolean isGarage = PictureProcessor.frequencyPixelInBitmap(ccaGarage.getBmpSrc(), GlobalApplication.getRgb_box_info_garage_main(), GlobalApplication.getRgb_box_info_garage_thm(), GlobalApplication.getRgb_box_info_garage_thp()) > 0.20f &&
//                                            PictureProcessor.frequencyPixelInBitmap(ccaGarage.getBmpSrc(), GlobalApplication.getRgb_box_info_garage_back1(), GlobalApplication.getRgb_box_info_garage_thm(), GlobalApplication.getRgb_box_info_garage_thp()) > 0.001f &&
//                                            PictureProcessor.frequencyPixelInBitmap(ccaGarage.getBmpSrc(), GlobalApplication.getRgb_box_info_garage_back2(), GlobalApplication.getRgb_box_info_garage_thm(), GlobalApplication.getRgb_box_info_garage_thp()) > 0.001f;
//                                    if (isGarage) { // гараж
//                                        this.cityCalcType = CityCalcType.CAR;
//                                    } else { // ошибка
//                                        this.cityCalcType = CityCalcType.ERROR;
//                                    }
//                                }
//
//                            }
//
//
//                        } else { // если не найден квадрат "назад" - скрин не из игры
//                            this.cityCalcType = CityCalcType.ERROR;
//                        }

                    } else { //если пропорции экрана неправильные - скрин не из игры
                        this.cityCalcType = CityCalcType.ERROR;
                    }

                }
            }
        }


    }

    
    private void setAreaToMap(Area area) {

        float x1;           // x1
        float x2;           // x2
        float y1;           // y1
        float y2;           // y2
        int [] colors;      // цвета
        int [] ths;         // пороги
        boolean needOcr;    // надо распознавать
        boolean needBW;    // надо BW
        int color_main, color_back1, color_back2, thm, thp;
        CityCalcArea cca;   // область

//        Context context = GlobalApplication.getAppContext();

        switch (area) {
            case BOX_INFO_CITY:
                // Box Info City Area
                x1 = GlobalApplication.getCut_box_info_city_x1();
                x2 = GlobalApplication.getCut_box_info_city_x2();
                y1 = GlobalApplication.getCut_box_info_city_y1();
                y2 = GlobalApplication.getCut_box_info_city_y2();
                
                color_main = GlobalApplication.getRgb_box_info_city_main();
                color_back1 = GlobalApplication.getRgb_box_info_city_back();
                thm = GlobalApplication.getRgb_box_info_city_thm();
                thp = GlobalApplication.getRgb_box_info_city_thp();
                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBoxInfoCity = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBoxInfoCity);
                break;

            case BOX_INFO_CAR:
                // Box Info Car Area
                x1 = GlobalApplication.getCut_box_info_car_x1();
                x2 = GlobalApplication.getCut_box_info_car_x2();
                y1 = GlobalApplication.getCut_box_info_car_y1();
                y2 = GlobalApplication.getCut_box_info_car_y2();
                color_main = GlobalApplication.getRgb_box_info_car_main();
                color_back1 = GlobalApplication.getRgb_box_info_car_back();
                thm = GlobalApplication.getRgb_box_info_car_thm();
                thp = GlobalApplication.getRgb_box_info_car_thp();
                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBoxInfoCar = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBoxInfoCar);
                break;

            case BOX_INFO_GARAGE:
                // Box Info Garage Area
                x1 = GlobalApplication.getCut_box_info_garage_x1();
                x2 = GlobalApplication.getCut_box_info_garage_x2();
                y1 = GlobalApplication.getCut_box_info_garage_y1();
                y2 = GlobalApplication.getCut_box_info_garage_y2();
                color_main = GlobalApplication.getRgb_box_info_garage_main();
                color_back1 = GlobalApplication.getRgb_box_info_garage_back1();
                thm = GlobalApplication.getRgb_box_info_garage_thm();
                thp = GlobalApplication.getRgb_box_info_garage_thp();
                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBoxInfoGarage = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBoxInfoGarage);
                break;

            case BOX_BACK:
                // Box Back Area
                x1 = GlobalApplication.getCut_box_back_x1();
                x2 = GlobalApplication.getCut_box_back_x2();
                y1 = GlobalApplication.getCut_box_back_y1();
                y2 = GlobalApplication.getCut_box_back_y2();
                color_main = GlobalApplication.getRgb_box_back_main();
                color_back1 = GlobalApplication.getRgb_box_back_back();
                thm = GlobalApplication.getRgb_box_back_thm();
                thp = GlobalApplication.getRgb_box_back_thp();
                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBoxBack = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBoxBack);
                break;
                
            case CITY:
                // City Area
                x1 = GlobalApplication.getCut_city_x1();
                x2 = GlobalApplication.getCut_city_x2();
                y1 = GlobalApplication.getCut_city_y1();
                y2 = GlobalApplication.getCut_city_y2();
                color_main = GlobalApplication.getRgb_city_main();
                color_back1 = GlobalApplication.getRgb_city_back();
                thm = GlobalApplication.getRgb_city_thm();
                thp = GlobalApplication.getRgb_city_thp();
                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CCAGame ccaGame = new CCAGame(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaGame);
                break;
                
            case TOTAL_TIME:
                // Total Time Area

                x1 = GlobalApplication.getCut_total_time_x1();
                x2 = GlobalApplication.getCut_total_time_x2();
                y1 = GlobalApplication.getCut_total_time_y1();
                y2 = GlobalApplication.getCut_total_time_y2();
                color_main = GlobalApplication.getRgb_total_time_main();
                color_back1 = GlobalApplication.getRgb_total_time_back1();
                color_back2 = GlobalApplication.getRgb_total_time_back2();
                thm = GlobalApplication.getRgb_total_time_thm();
                thp = GlobalApplication.getRgb_total_time_thp();
                
                colors = new int[] {color_main, color_back1, color_back2};
                ths = new int[] {thm, thp};
                needOcr = true;
                needBW = true;
                CityCalcArea ccaTotalTime = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaTotalTime);
                break;
                
            case EARLY_WIN:
                // Early Win Area

                x1 = GlobalApplication.getCut_early_win_x1();
                x2 = GlobalApplication.getCut_early_win_x2();
                y1 = GlobalApplication.getCut_early_win_y1();
                y2 = GlobalApplication.getCut_early_win_y2();
                color_main = GlobalApplication.getRgb_early_win_main();
                color_back1 = GlobalApplication.getRgb_early_win_back();
                thm = GlobalApplication.getRgb_early_win_thm();
                thp = GlobalApplication.getRgb_early_win_thp();
                
                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = true;
                needBW = true;
                CityCalcArea ccaEarlyWin = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaEarlyWin);
                break;
                
            case POINTS_AND_INCREASE_OUR:
                // Points And Increase Our Area
                
                x1 = GlobalApplication.getCut_points_and_increase_our_x1();
                x2 = GlobalApplication.getCut_points_and_increase_our_x2();
                y1 = GlobalApplication.getCut_points_and_increase_our_y1();
                y2 = GlobalApplication.getCut_points_and_increase_our_y2();
                color_main = GlobalApplication.getRgb_points_our_main();
                color_back1 = GlobalApplication.getRgb_points_our_back1();
                thm = GlobalApplication.getRgb_points_our_thm();
                thp = GlobalApplication.getRgb_points_our_thp();
                
                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                
                needOcr = false;
                needBW = false;
                cca = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, cca);

                color_back1 = GlobalApplication.getRgb_increase_our_back();
                thm = GlobalApplication.getRgb_increase_our_thm();
                thp = GlobalApplication.getRgb_increase_our_thp();

                Bitmap[] ourPointsAndIncreaseBitmapArray = PictureProcessor.splitBitmap(cca.getBmpSrc(), color_back1, thm, thp, 0.80f, 0.01f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);

                CityCalcArea ccaPointsOur = null;
                CityCalcArea ccaIncreaseOur = null;

                if (ourPointsAndIncreaseBitmapArray != null) {

                    color_main = GlobalApplication.getRgb_points_our_main();
                    color_back1 = GlobalApplication.getRgb_points_our_back1();
                    color_back2 = GlobalApplication.getRgb_points_our_back2();
                    thm = GlobalApplication.getRgb_points_our_thm();
                    thp = GlobalApplication.getRgb_points_our_thp();
                    
                    colors = new int[] {color_main, color_back1, color_back2};
                    ths = new int[] {thm, thp};

                    needOcr = true;
                    needBW = false;
                    ccaPointsOur = new CityCalcArea(thisCityCalc, Area.POINTS_OUR, colors, ths, needOcr, needBW);
                    ccaPointsOur.setBmpSrc(ourPointsAndIncreaseBitmapArray[1]);
                    ccaPointsOur.doOCR();
                    mapAreas.put(Area.POINTS_OUR, ccaPointsOur);

                }
                break;
                
            case TEAM_NAME_OUR:
                // Team Name Our Area

                x1 = GlobalApplication.getCut_team_name_our_x1();
                x2 = GlobalApplication.getCut_team_name_our_x2();
                y1 = GlobalApplication.getCut_team_name_our_y1();
                y2 = GlobalApplication.getCut_team_name_our_y2();
                color_main = GlobalApplication.getRgb_team_name_our_main();
                color_back1 = GlobalApplication.getRgb_team_name_our_back();
                thm = GlobalApplication.getRgb_team_name_our_thm();
                thp = GlobalApplication.getRgb_team_name_our_thp();
                
                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CCATeam ccaOurTeam = new CCATeam(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaOurTeam);
                break;
                
            case POINTS_AND_INCREASE_ENEMY:
                // Points And Increase Enemy Area
                x1 = GlobalApplication.getCut_points_and_increase_enemy_x1();
                x2 = GlobalApplication.getCut_points_and_increase_enemy_x2();
                y1 = GlobalApplication.getCut_points_and_increase_enemy_y1();
                y2 = GlobalApplication.getCut_points_and_increase_enemy_y2();
                color_main = GlobalApplication.getRgb_points_enemy_main();
                color_back1 = GlobalApplication.getRgb_points_enemy_back1();
                thm = GlobalApplication.getRgb_points_enemy_thm();
                thp = GlobalApplication.getRgb_points_enemy_thp();
                
                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                cca = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, cca);

                color_back1 = GlobalApplication.getRgb_increase_enemy_back();
                thm = GlobalApplication.getRgb_increase_enemy_thm();
                thp = GlobalApplication.getRgb_increase_enemy_thp();
                
                Bitmap[] enemyPointsAndIncreaseBitmapArray = PictureProcessor.splitBitmap(cca.getBmpSrc(), color_back1, thm, thp, 0.80f, 0.01f, PictureProcessorDirection.FROM_RIGHT_TO_LEFT, false);

                CityCalcArea ccaPointsEnemy = null;
                CityCalcArea ccaIncreaseEnemy = null;

                if (enemyPointsAndIncreaseBitmapArray != null) {

                    color_main = GlobalApplication.getRgb_points_enemy_main();
                    color_back1 = GlobalApplication.getRgb_points_enemy_back1();
                    color_back2 = GlobalApplication.getRgb_points_enemy_back2();
                    thm = GlobalApplication.getRgb_points_enemy_thm();
                    thp = GlobalApplication.getRgb_points_enemy_thp();
                    
                    colors = new int[] {color_main, color_back1, color_back2};
                    ths = new int[] {thm, thp};

                    needOcr = true;
                    needBW = false;
                    ccaPointsEnemy = new CityCalcArea(thisCityCalc, Area.POINTS_ENEMY, colors, ths, needOcr, needBW);
                    ccaPointsEnemy.setBmpSrc(enemyPointsAndIncreaseBitmapArray[1]);
                    ccaPointsEnemy.doOCR();
                    mapAreas.put(Area.POINTS_ENEMY, ccaPointsEnemy);

                }
                break;
                
            case TEAM_NAME_ENEMY:
                // Team Name Enemy Area
                
                x1 = GlobalApplication.getCut_team_name_enemy_x1();
                x2 = GlobalApplication.getCut_team_name_enemy_x2();
                y1 = GlobalApplication.getCut_team_name_enemy_y1();
                y2 = GlobalApplication.getCut_team_name_enemy_y2();
                color_main = GlobalApplication.getRgb_team_name_enemy_main();
                color_back1 = GlobalApplication.getRgb_team_name_enemy_back();
                thm = GlobalApplication.getRgb_team_name_enemy_thm();
                thp = GlobalApplication.getRgb_team_name_enemy_thp();
                
                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CCATeam ccaEnemyTeam = new CCATeam(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaEnemyTeam);
                break;
                
            case BLT_AREA:
                // BLT Area
                x1 = GlobalApplication.getCut_blt_x1();
                x2 = GlobalApplication.getCut_blt_x2();
                y1 = GlobalApplication.getCut_blt_y1();
                y2 = GlobalApplication.getCut_blt_y2();
                color_main = GlobalApplication.getRgb_bxx_main();
                color_back1 = GlobalApplication.getRgb_bxx_back();
                thm = GlobalApplication.getRgb_bxx_thm();
                thp = GlobalApplication.getRgb_bxx_thp();
                
                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLT = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLT);
                break;
                
            case BLT:
                // BLT Name Area
                x1 = GlobalApplication.getCut_blt_name_x1();
                x2 = GlobalApplication.getCut_blt_name_x2();
                y1 = GlobalApplication.getCut_blt_name_y1();
                y2 = GlobalApplication.getCut_blt_name_y2();
                
                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty(), GlobalApplication.getRgb_bxx_mayX2(), GlobalApplication.getRgb_bxx_isX2()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = true;
                needBW = true;
                CCABuilding ccaBLTname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLTname);
                break;
                
            
                
            case BLT_PROGRESS:
                // BLT Progress Area
                x1 = GlobalApplication.getCut_blt_progress_x1();
                x2 = GlobalApplication.getCut_blt_progress_x2();
                y1 = GlobalApplication.getCut_blt_progress_y1();
                y2 = GlobalApplication.getCut_blt_progress_y2();

                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLTprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLTprogress);
                break;

            case BLT_SLOTS:
                // BLT Slots Area
                x1 = GlobalApplication.getCut_blt_slots_x1();
                x2 = GlobalApplication.getCut_blt_slots_x2();
                y1 = GlobalApplication.getCut_blt_slots_y1();
                y2 = GlobalApplication.getCut_blt_slots_y2();

                colors = new int[] {GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getRgb_bxx_slot_back()};
                ths = new int[] {GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLTslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBLTslots.setNeedOcr(true);
                Bitmap[] ccaBLTslotsBitmapArray = PictureProcessor.splitBitmap(ccaBLTslots.getBmpSrc(), GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot(), 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBLTslotsBitmapArray != null) {
                    if (ccaBLTslotsBitmapArray.length == 2) {
                        ccaBLTslots.setBmpSrc(ccaBLTslotsBitmapArray[1]);
                        ccaBLTslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBLTslots);
                break;

            case BLC_AREA:
                // BLC Area
                x1 = GlobalApplication.getCut_blc_x1();
                x2 = GlobalApplication.getCut_blc_x2();
                y1 = GlobalApplication.getCut_blc_y1();
                y2 = GlobalApplication.getCut_blc_y2();
                color_main = GlobalApplication.getRgb_bxx_main();
                color_back1 = GlobalApplication.getRgb_bxx_back();
                thm = GlobalApplication.getRgb_bxx_thm();
                thp = GlobalApplication.getRgb_bxx_thp();

                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLC = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLC);
                break;

            case BLC:
                // BLC Name Area
                x1 = GlobalApplication.getCut_blc_name_x1();
                x2 = GlobalApplication.getCut_blc_name_x2();
                y1 = GlobalApplication.getCut_blc_name_y1();
                y2 = GlobalApplication.getCut_blc_name_y2();

                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty(), GlobalApplication.getRgb_bxx_mayX2(), GlobalApplication.getRgb_bxx_isX2()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = true;
                needBW = true;
                CCABuilding ccaBLCname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLCname);
                break;



            case BLC_PROGRESS:
                // BLC Progress Area
                x1 = GlobalApplication.getCut_blc_progress_x1();
                x2 = GlobalApplication.getCut_blc_progress_x2();
                y1 = GlobalApplication.getCut_blc_progress_y1();
                y2 = GlobalApplication.getCut_blc_progress_y2();

                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLCprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLCprogress);
                break;

            case BLC_SLOTS:
                // BLC Slots Area
                x1 = GlobalApplication.getCut_blc_slots_x1();
                x2 = GlobalApplication.getCut_blc_slots_x2();
                y1 = GlobalApplication.getCut_blc_slots_y1();
                y2 = GlobalApplication.getCut_blc_slots_y2();

                colors = new int[] {GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getRgb_bxx_slot_back()};
                ths = new int[] {GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLCslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBLCslots.setNeedOcr(true);
                Bitmap[] ccaBLCslotsBitmapArray = PictureProcessor.splitBitmap(ccaBLCslots.getBmpSrc(), GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot(), 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBLCslotsBitmapArray != null) {
                    if (ccaBLCslotsBitmapArray.length == 2) {
                        ccaBLCslots.setBmpSrc(ccaBLCslotsBitmapArray[1]);
                        ccaBLCslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBLCslots);
                break;




            case BLB_AREA:
                // BLB Area
                x1 = GlobalApplication.getCut_blb_x1();
                x2 = GlobalApplication.getCut_blb_x2();
                y1 = GlobalApplication.getCut_blb_y1();
                y2 = GlobalApplication.getCut_blb_y2();
                color_main = GlobalApplication.getRgb_bxx_main();
                color_back1 = GlobalApplication.getRgb_bxx_back();
                thm = GlobalApplication.getRgb_bxx_thm();
                thp = GlobalApplication.getRgb_bxx_thp();

                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLB = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLB);
                break;

            case BLB:
                // BLB Name Area
                x1 = GlobalApplication.getCut_blb_name_x1();
                x2 = GlobalApplication.getCut_blb_name_x2();
                y1 = GlobalApplication.getCut_blb_name_y1();
                y2 = GlobalApplication.getCut_blb_name_y2();

                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty(), GlobalApplication.getRgb_bxx_mayX2(), GlobalApplication.getRgb_bxx_isX2()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = true;
                needBW = true;
                CCABuilding ccaBLBname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLBname);
                break;



            case BLB_PROGRESS:
                // BLB Progress Area
                x1 = GlobalApplication.getCut_blb_progress_x1();
                x2 = GlobalApplication.getCut_blb_progress_x2();
                y1 = GlobalApplication.getCut_blb_progress_y1();
                y2 = GlobalApplication.getCut_blb_progress_y2();

                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLBprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBLBprogress);
                break;

            case BLB_SLOTS:
                // BLB Slots Area
                x1 = GlobalApplication.getCut_blb_slots_x1();
                x2 = GlobalApplication.getCut_blb_slots_x2();
                y1 = GlobalApplication.getCut_blb_slots_y1();
                y2 = GlobalApplication.getCut_blb_slots_y2();

                colors = new int[] {GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getRgb_bxx_slot_back()};
                ths = new int[] {GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBLBslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBLBslots.setNeedOcr(true);
                Bitmap[] ccaBLBslotsBitmapArray = PictureProcessor.splitBitmap(ccaBLBslots.getBmpSrc(), GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot(), 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBLBslotsBitmapArray != null) {
                    if (ccaBLBslotsBitmapArray.length == 2) {
                        ccaBLBslots.setBmpSrc(ccaBLBslotsBitmapArray[1]);
                        ccaBLBslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBLBslots);
                break;




            case BRT_AREA:
                // BRT Area
                x1 = GlobalApplication.getCut_brt_x1();
                x2 = GlobalApplication.getCut_brt_x2();
                y1 = GlobalApplication.getCut_brt_y1();
                y2 = GlobalApplication.getCut_brt_y2();
                color_main = GlobalApplication.getRgb_bxx_main();
                color_back1 = GlobalApplication.getRgb_bxx_back();
                thm = GlobalApplication.getRgb_bxx_thm();
                thp = GlobalApplication.getRgb_bxx_thp();

                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRT = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRT);
                break;

            case BRT:
                // BRT Name Area
                x1 = GlobalApplication.getCut_brt_name_x1();
                x2 = GlobalApplication.getCut_brt_name_x2();
                y1 = GlobalApplication.getCut_brt_name_y1();
                y2 = GlobalApplication.getCut_brt_name_y2();

                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty(), GlobalApplication.getRgb_bxx_mayX2(), GlobalApplication.getRgb_bxx_isX2()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = true;
                needBW = true;
                CCABuilding ccaBRTname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRTname);
                break;



            case BRT_PROGRESS:
                // BRT Progress Area
                x1 = GlobalApplication.getCut_brt_progress_x1();
                x2 = GlobalApplication.getCut_brt_progress_x2();
                y1 = GlobalApplication.getCut_brt_progress_y1();
                y2 = GlobalApplication.getCut_brt_progress_y2();

                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRTprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRTprogress);
                break;

            case BRT_SLOTS:
                // BRT Slots Area
                x1 = GlobalApplication.getCut_brt_slots_x1();
                x2 = GlobalApplication.getCut_brt_slots_x2();
                y1 = GlobalApplication.getCut_brt_slots_y1();
                y2 = GlobalApplication.getCut_brt_slots_y2();

                colors = new int[] {GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getRgb_bxx_slot_back()};
                ths = new int[] {GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRTslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBRTslots.setNeedOcr(true);
                Bitmap[] ccaBRTslotsBitmapArray = PictureProcessor.splitBitmap(ccaBRTslots.getBmpSrc(), GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot(), 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBRTslotsBitmapArray != null) {
                    if (ccaBRTslotsBitmapArray.length == 2) {
                        ccaBRTslots.setBmpSrc(ccaBRTslotsBitmapArray[1]);
                        ccaBRTslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBRTslots);
                break;



            case BRC_AREA:
                // BRC Area
                x1 = GlobalApplication.getCut_brc_x1();
                x2 = GlobalApplication.getCut_brc_x2();
                y1 = GlobalApplication.getCut_brc_y1();
                y2 = GlobalApplication.getCut_brc_y2();
                color_main = GlobalApplication.getRgb_bxx_main();
                color_back1 = GlobalApplication.getRgb_bxx_back();
                thm = GlobalApplication.getRgb_bxx_thm();
                thp = GlobalApplication.getRgb_bxx_thp();

                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRC = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRC);
                break;

            case BRC:
                // BRC Name Area
                x1 = GlobalApplication.getCut_brc_name_x1();
                x2 = GlobalApplication.getCut_brc_name_x2();
                y1 = GlobalApplication.getCut_brc_name_y1();
                y2 = GlobalApplication.getCut_brc_name_y2();

                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty(), GlobalApplication.getRgb_bxx_mayX2(), GlobalApplication.getRgb_bxx_isX2()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = true;
                needBW = true;
                CCABuilding ccaBRCname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRCname);
                break;



            case BRC_PROGRESS:
                // BRC Progress Area
                x1 = GlobalApplication.getCut_brc_progress_x1();
                x2 = GlobalApplication.getCut_brc_progress_x2();
                y1 = GlobalApplication.getCut_brc_progress_y1();
                y2 = GlobalApplication.getCut_brc_progress_y2();

                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRCprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRCprogress);
                break;

            case BRC_SLOTS:
                // BRC Slots Area
                x1 = GlobalApplication.getCut_brc_slots_x1();
                x2 = GlobalApplication.getCut_brc_slots_x2();
                y1 = GlobalApplication.getCut_brc_slots_y1();
                y2 = GlobalApplication.getCut_brc_slots_y2();

                colors = new int[] {GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getRgb_bxx_slot_back()};
                ths = new int[] {GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRCslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBRCslots.setNeedOcr(true);
                Bitmap[] ccaBRCslotsBitmapArray = PictureProcessor.splitBitmap(ccaBRCslots.getBmpSrc(), GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot(), 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBRCslotsBitmapArray != null) {
                    if (ccaBRCslotsBitmapArray.length == 2) {
                        ccaBRCslots.setBmpSrc(ccaBRCslotsBitmapArray[1]);
                        ccaBRCslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBRCslots);
                break;



            case BRB_AREA:
                // BRB Area
                x1 = GlobalApplication.getCut_brb_x1();
                x2 = GlobalApplication.getCut_brb_x2();
                y1 = GlobalApplication.getCut_brb_y1();
                y2 = GlobalApplication.getCut_brb_y2();
                color_main = GlobalApplication.getRgb_bxx_main();
                color_back1 = GlobalApplication.getRgb_bxx_back();
                thm = GlobalApplication.getRgb_bxx_thm();
                thp = GlobalApplication.getRgb_bxx_thp();

                colors = new int[] {color_main, color_back1};
                ths = new int[] {thm, thp};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRB = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRB);
                break;

            case BRB:
                // BRB Name Area
                x1 = GlobalApplication.getCut_brb_name_x1();
                x2 = GlobalApplication.getCut_brb_name_x2();
                y1 = GlobalApplication.getCut_brb_name_y1();
                y2 = GlobalApplication.getCut_brb_name_y2();

                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty(), GlobalApplication.getRgb_bxx_mayX2(), GlobalApplication.getRgb_bxx_isX2()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = true;
                needBW = true;
                CCABuilding ccaBRBname = new CCABuilding(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRBname);
                break;



            case BRB_PROGRESS:
                // BRB Progress Area
                x1 = GlobalApplication.getCut_brb_progress_x1();
                x2 = GlobalApplication.getCut_brb_progress_x2();
                y1 = GlobalApplication.getCut_brb_progress_y1();
                y2 = GlobalApplication.getCut_brb_progress_y2();

                colors = new int[] {GlobalApplication.getRgb_progress_our(), GlobalApplication.getRgb_progress_enemy(), GlobalApplication.getRgb_progress_empty()};
                ths = new int[] {GlobalApplication.getThm_progress_our(), GlobalApplication.getThp_progress_our(), GlobalApplication.getThm_progress_enemy(), GlobalApplication.getThp_progress_enemy(), GlobalApplication.getThm_progress_empty(), GlobalApplication.getThp_progress_empty()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRBprogress = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaBRBprogress);
                break;

            case BRB_SLOTS:
                // BRB Slots Area
                x1 = GlobalApplication.getCut_brb_slots_x1();
                x2 = GlobalApplication.getCut_brb_slots_x2();
                y1 = GlobalApplication.getCut_brb_slots_y1();
                y2 = GlobalApplication.getCut_brb_slots_y2();

                colors = new int[] {GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getRgb_bxx_slot_back()};
                ths = new int[] {GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaBRBslots = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                ccaBRBslots.setNeedOcr(true);
                Bitmap[] ccaBRBslotsBitmapArray = PictureProcessor.splitBitmap(ccaBRBslots.getBmpSrc(), GlobalApplication.getRgb_bxx_slot_main(), GlobalApplication.getThm_bxx_slot(), GlobalApplication.getThp_bxx_slot(), 0.05f, 0.0f, PictureProcessorDirection.FROM_LEFT_TO_RIGHT, false);
                if (ccaBRBslotsBitmapArray != null) {
                    if (ccaBRBslotsBitmapArray.length == 2) {
                        ccaBRBslots.setBmpSrc(ccaBRBslotsBitmapArray[1]);
                        ccaBRBslots.doOCR();
                    }
                }
                mapAreas.put(area, ccaBRBslots);
                break;


            case CAR_IN_CITY_BOX1:
                // Car Box Area
                x1 = GlobalApplication.getCut_car_in_city_box1_x1();
                x2 = GlobalApplication.getCut_car_in_city_box1_x2();
                y1 = GlobalApplication.getCut_car_in_city_box1_y1();
                y2 = GlobalApplication.getCut_car_in_city_box1_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_box1_main()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_box1_thm(), GlobalApplication.getRgb_car_in_city_box1_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarBox1 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarBox1);
                break;

            case CAR_IN_CITY_BOX2:
                // Car Box Area
                x1 = GlobalApplication.getCut_car_in_city_box2_x1();
                x2 = GlobalApplication.getCut_car_in_city_box2_x2();
                y1 = GlobalApplication.getCut_car_in_city_box2_y1();
                y2 = GlobalApplication.getCut_car_in_city_box2_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_box2_main()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_box2_thm(), GlobalApplication.getRgb_car_in_city_box2_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarBox2 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarBox2);
                break;

            case CAR_IN_CITY_INFO:
                // Car Info Area
                x1 = GlobalApplication.getCut_car_in_city_info_x1();
                x2 = GlobalApplication.getCut_car_in_city_info_x2();
                y1 = GlobalApplication.getCut_car_in_city_info_y1();
                y2 = GlobalApplication.getCut_car_in_city_info_y2();
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CCACar ccaCarInfo = new CCACar(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarInfo);
                break;

            case CAR_IN_CITY_BUILDING:
                // Car Info Area
                x1 = GlobalApplication.getCut_car_in_city_building_x1();
                x2 = GlobalApplication.getCut_car_in_city_building_x2();
                y1 = GlobalApplication.getCut_car_in_city_building_y1();
                y2 = GlobalApplication.getCut_car_in_city_building_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_building_main(), GlobalApplication.getRgb_car_in_city_building_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_building_thm(), GlobalApplication.getRgb_car_in_city_building_thp()};
                needOcr = true;
                needBW = true;
                CCACar ccaCarBuilding = new CCACar(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarBuilding);
                break;

            case CAR_IN_CITY_PICTURE:
                // Car Picture Area
                x1 = GlobalApplication.getCut_car_in_city_picture_x1();
                x2 = GlobalApplication.getCut_car_in_city_picture_x2();
                y1 = GlobalApplication.getCut_car_in_city_picture_y1();
                y2 = GlobalApplication.getCut_car_in_city_picture_y2();
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarPicture = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarPicture);
                break;

            case CAR_IN_GARAGE_PICTURE:
                // Car Picture Area
                x1 = GlobalApplication.getCut_car_in_garage_picture_x1();
                x2 = GlobalApplication.getCut_car_in_garage_picture_x2();
                y1 = GlobalApplication.getCut_car_in_garage_picture_y1();
                y2 = GlobalApplication.getCut_car_in_garage_picture_y2();
                colors = null;
                ths = null;
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarPictureInGarage = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarPictureInGarage);
                break;
                
            case CAR_IN_CITY_SLOT1:
                // Car Slot1 Area
                x1 = GlobalApplication.getCut_car_in_city_slot1_x1();
                x2 = GlobalApplication.getCut_car_in_city_slot1_x2();
                y1 = GlobalApplication.getCut_car_in_city_slot1_y1();
                y2 = GlobalApplication.getCut_car_in_city_slot1_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_slot_main(), GlobalApplication.getRgb_car_in_city_slot_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_slot_thm(), GlobalApplication.getRgb_car_in_city_slot_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarSlot1 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarSlot1);
                break;

            case CAR_IN_CITY_SLOT2:
                // Car Slot2 Area
                x1 = GlobalApplication.getCut_car_in_city_slot2_x1();
                x2 = GlobalApplication.getCut_car_in_city_slot2_x2();
                y1 = GlobalApplication.getCut_car_in_city_slot2_y1();
                y2 = GlobalApplication.getCut_car_in_city_slot2_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_slot_main(), GlobalApplication.getRgb_car_in_city_slot_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_slot_thm(), GlobalApplication.getRgb_car_in_city_slot_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarSlot2 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarSlot2);
                break;

            case CAR_IN_CITY_SLOT3:
                // Car Slot3 Area
                x1 = GlobalApplication.getCut_car_in_city_slot3_x1();
                x2 = GlobalApplication.getCut_car_in_city_slot3_x2();
                y1 = GlobalApplication.getCut_car_in_city_slot3_y1();
                y2 = GlobalApplication.getCut_car_in_city_slot3_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_slot_main(), GlobalApplication.getRgb_car_in_city_slot_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_slot_thm(), GlobalApplication.getRgb_car_in_city_slot_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarSlot3 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarSlot3);
                break;

            case CAR_IN_GARAGE_SLOT1:
                // Car Slot1 Area
                x1 = GlobalApplication.getCut_car_in_garage_slot1_x1();
                x2 = GlobalApplication.getCut_car_in_garage_slot1_x2();
                y1 = GlobalApplication.getCut_car_in_garage_slot1_y1();
                y2 = GlobalApplication.getCut_car_in_garage_slot1_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_garage_slot_main(), GlobalApplication.getRgb_car_in_garage_slot_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_garage_slot_thm(), GlobalApplication.getRgb_car_in_garage_slot_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarInGarageSlot1 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarInGarageSlot1);
                break;

            case CAR_IN_GARAGE_SLOT2:
                // Car Slot2 Area
                x1 = GlobalApplication.getCut_car_in_garage_slot2_x1();
                x2 = GlobalApplication.getCut_car_in_garage_slot2_x2();
                y1 = GlobalApplication.getCut_car_in_garage_slot2_y1();
                y2 = GlobalApplication.getCut_car_in_garage_slot2_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_garage_slot_main(), GlobalApplication.getRgb_car_in_garage_slot_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_garage_slot_thm(), GlobalApplication.getRgb_car_in_garage_slot_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarInGarageSlot2 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarInGarageSlot2);
                break;

            case CAR_IN_GARAGE_SLOT3:
                // Car Slot3 Area
                x1 = GlobalApplication.getCut_car_in_garage_slot3_x1();
                x2 = GlobalApplication.getCut_car_in_garage_slot3_x2();
                y1 = GlobalApplication.getCut_car_in_garage_slot3_y1();
                y2 = GlobalApplication.getCut_car_in_garage_slot3_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_garage_slot_main(), GlobalApplication.getRgb_car_in_garage_slot_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_garage_slot_thm(), GlobalApplication.getRgb_car_in_garage_slot_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarInGarageSlot3 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarInGarageSlot3);
                break;
                
            case CAR_IN_CITY_HEALTH:
                // Car Health Area
                x1 = GlobalApplication.getCut_car_in_city_health_x1();
                x2 = GlobalApplication.getCut_car_in_city_health_x2();
                y1 = GlobalApplication.getCut_car_in_city_health_y1();
                y2 = GlobalApplication.getCut_car_in_city_health_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_health_main(), GlobalApplication.getRgb_car_in_city_health_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_health_thm(), GlobalApplication.getRgb_car_in_city_health_thp()};
                needOcr = true;
                needBW = true;
                CityCalcArea ccaCarHealth = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarHealth);
                break;

            case CAR_IN_CITY_SHIELD:
                // Car Shield Area
                x1 = GlobalApplication.getCut_car_in_city_shield_x1();
                x2 = GlobalApplication.getCut_car_in_city_shield_x2();
                y1 = GlobalApplication.getCut_car_in_city_shield_y1();
                y2 = GlobalApplication.getCut_car_in_city_shield_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_shield_main(), GlobalApplication.getRgb_car_in_city_shield_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_shield_thm(), GlobalApplication.getRgb_car_in_city_shield_thp()};
                needOcr = true;
                needBW = true;
                CityCalcArea ccaCarShield = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarShield);
                break;

            case CAR_IN_GARAGE_HEALTH:
                // Car Health Area
                x1 = GlobalApplication.getCut_car_in_garage_health_x1();
                x2 = GlobalApplication.getCut_car_in_garage_health_x2();
                y1 = GlobalApplication.getCut_car_in_garage_health_y1();
                y2 = GlobalApplication.getCut_car_in_garage_health_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_garage_health_main(), GlobalApplication.getRgb_car_in_garage_health_back1(), GlobalApplication.getRgb_car_in_garage_health_back2()};
                ths = new int[] {GlobalApplication.getRgb_car_in_garage_health_thm(), GlobalApplication.getRgb_car_in_garage_health_thp()};
                needOcr = true;
                needBW = true;
                CityCalcArea ccaCarInGarageHealth = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarInGarageHealth);
                break;

            case CAR_IN_GARAGE_SHIELD:
                // Car Shield Area
                x1 = GlobalApplication.getCut_car_in_garage_shield_x1();
                x2 = GlobalApplication.getCut_car_in_garage_shield_x2();
                y1 = GlobalApplication.getCut_car_in_garage_shield_y1();
                y2 = GlobalApplication.getCut_car_in_garage_shield_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_garage_shield_main(), GlobalApplication.getRgb_car_in_garage_shield_back1(), GlobalApplication.getRgb_car_in_garage_shield_back1()};
                ths = new int[] {GlobalApplication.getRgb_car_in_garage_shield_thm(), GlobalApplication.getRgb_car_in_garage_shield_thp()};
                needOcr = true;
                needBW = true;
                CityCalcArea ccaCarInGarageShield = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarInGarageShield);
                break;

                
            case CAR_IN_CITY_STATE:
                // Car State Area
                x1 = GlobalApplication.getCut_car_in_city_state_x1();
                x2 = GlobalApplication.getCut_car_in_city_state_x2();
                y1 = GlobalApplication.getCut_car_in_city_state_y1();
                y2 = GlobalApplication.getCut_car_in_city_state_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_state_main(), GlobalApplication.getRgb_car_in_city_state_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_state_thm(), GlobalApplication.getRgb_car_in_city_state_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarState = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarState);
                break;

            case CAR_IN_CITY_HEALBOX:
                // Car Healbox Area
                x1 = GlobalApplication.getCut_car_in_city_healbox_x1();
                x2 = GlobalApplication.getCut_car_in_city_healbox_x2();
                y1 = GlobalApplication.getCut_car_in_city_healbox_y1();
                y2 = GlobalApplication.getCut_car_in_city_healbox_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_healbox_main(), GlobalApplication.getRgb_car_in_city_healbox_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_healbox_thm(), GlobalApplication.getRgb_car_in_city_healbox_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarHealbox = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarHealbox);
                break;

            case CAR_IN_CITY_TIMEBOX1:
                // Car Timebox1 Area
                x1 = GlobalApplication.getCut_car_in_city_timebox1_x1();
                x2 = GlobalApplication.getCut_car_in_city_timebox1_x2();
                y1 = GlobalApplication.getCut_car_in_city_timebox1_y1();
                y2 = GlobalApplication.getCut_car_in_city_timebox1_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_timebox1_main(), GlobalApplication.getRgb_car_in_city_timebox1_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_timebox1_thm(), GlobalApplication.getRgb_car_in_city_timebox1_thp()};
                needOcr = true;
                needBW = true;
                CityCalcArea ccaCarTimebox1 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarTimebox1);
                break;

            case CAR_IN_CITY_TIMEBOX2:
                // Car Timebox2 Area
                x1 = GlobalApplication.getCut_car_in_city_timebox2_x1();
                x2 = GlobalApplication.getCut_car_in_city_timebox2_x2();
                y1 = GlobalApplication.getCut_car_in_city_timebox2_y1();
                y2 = GlobalApplication.getCut_car_in_city_timebox2_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_timebox2_main(), GlobalApplication.getRgb_car_in_city_timebox2_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_timebox2_thm(), GlobalApplication.getRgb_car_in_city_timebox2_thp()};
                needOcr = true;
                needBW = true;
                CityCalcArea ccaCarTimebox2 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarTimebox2);
                break;
                
            case CAR_IN_CITY_STATEBOX1:
                // Car Statebox1 Area
                x1 = GlobalApplication.getCut_car_in_city_statebox1_x1();
                x2 = GlobalApplication.getCut_car_in_city_statebox1_x2();
                y1 = GlobalApplication.getCut_car_in_city_statebox1_y1();
                y2 = GlobalApplication.getCut_car_in_city_statebox1_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_statebox1_main(), GlobalApplication.getRgb_car_in_city_statebox1_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_statebox1_thm(), GlobalApplication.getRgb_car_in_city_statebox1_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarStatebox1 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarStatebox1);
                break;

            case CAR_IN_CITY_STATEBOX2:
                // Car Statebox2 Area
                x1 = GlobalApplication.getCut_car_in_city_statebox2_x1();
                x2 = GlobalApplication.getCut_car_in_city_statebox2_x2();
                y1 = GlobalApplication.getCut_car_in_city_statebox2_y1();
                y2 = GlobalApplication.getCut_car_in_city_statebox2_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_statebox2_main(), GlobalApplication.getRgb_car_in_city_statebox2_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_statebox2_thm(), GlobalApplication.getRgb_car_in_city_statebox2_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarStatebox2 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarStatebox2);
                break;

            case CAR_IN_CITY_STATEBOX3:
                // Car Statebox3 Area
                x1 = GlobalApplication.getCut_car_in_city_statebox3_x1();
                x2 = GlobalApplication.getCut_car_in_city_statebox3_x2();
                y1 = GlobalApplication.getCut_car_in_city_statebox3_y1();
                y2 = GlobalApplication.getCut_car_in_city_statebox3_y2();
                colors = new int[] {GlobalApplication.getRgb_car_in_city_statebox3_main(), GlobalApplication.getRgb_car_in_city_statebox3_back()};
                ths = new int[] {GlobalApplication.getRgb_car_in_city_statebox3_thm(), GlobalApplication.getRgb_car_in_city_statebox3_thp()};
                needOcr = false;
                needBW = false;
                CityCalcArea ccaCarStatebox3 = new CityCalcArea(thisCityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
                mapAreas.put(area, ccaCarStatebox3);
                break;
                
            default:
        }
        
        
        
    }
    
    // конструктор
    public CityCalc(CityCalc checkedCityCalc, boolean isRealtimeScreenshot) {

        String logMsgPref = "конструктор: ";
        Log.i(TAG, logMsgPref + "start");

        CityCalcType cityCalcType = checkedCityCalc.cityCalcType;
        File file = checkedCityCalc.fileScreenshot;
        int calibrateX = checkedCityCalc.calibrateX;
        int calibrateY = checkedCityCalc.calibrateY;
//        Context context = checkedCityCalc.context;

        this.userNIC = checkedCityCalc.userNIC;
        this.userUID = checkedCityCalc.userUID;
        this.teamID = checkedCityCalc.teamID;
        this.cityCalcType = cityCalcType;
        this.fileScreenshot = file;
        this.bmpScreenshot = checkedCityCalc.bmpScreenshot;
        this.calibrateX = calibrateX;
        this.calibrateY = calibrateY;
//        this.context = context;

        thisCityCalc = this;

        try {
            if (fileScreenshot != null) {         // если файл не нулл
                if (fileScreenshot.exists()) {    // если файл физически существует

                    if (cityCalcType.equals(CityCalcType.GAME)) {
                        setAreaToMap(Area.BOX_INFO_CITY);
                        setAreaToMap(Area.BOX_INFO_CAR);
                        setAreaToMap(Area.CITY);
                        setAreaToMap(Area.TOTAL_TIME);
                        setAreaToMap(Area.EARLY_WIN);
                        setAreaToMap(Area.POINTS_AND_INCREASE_OUR);
                        setAreaToMap(Area.TEAM_NAME_OUR);
                        setAreaToMap(Area.POINTS_AND_INCREASE_ENEMY);
                        setAreaToMap(Area.TEAM_NAME_ENEMY);
                        setAreaToMap(Area.BLT_AREA);
                        setAreaToMap(Area.BLT);
                        setAreaToMap(Area.BLT_PROGRESS);
                        setAreaToMap(Area.BLT_SLOTS);
                        setAreaToMap(Area.BLC_AREA);
                        setAreaToMap(Area.BLC);
                        setAreaToMap(Area.BLC_PROGRESS);
                        setAreaToMap(Area.BLC_SLOTS);
                        setAreaToMap(Area.BLB_AREA);
                        setAreaToMap(Area.BLB);
                        setAreaToMap(Area.BLB_PROGRESS);
                        setAreaToMap(Area.BLB_SLOTS);
                        setAreaToMap(Area.BRT_AREA);
                        setAreaToMap(Area.BRT);
                        setAreaToMap(Area.BRT_PROGRESS);
                        setAreaToMap(Area.BRT_SLOTS);
                        setAreaToMap(Area.BRC_AREA);
                        setAreaToMap(Area.BRC);
                        setAreaToMap(Area.BRC_PROGRESS);
                        setAreaToMap(Area.BRC_SLOTS);
                        setAreaToMap(Area.BRB_AREA);
                        setAreaToMap(Area.BRB);
                        setAreaToMap(Area.BRB_PROGRESS);
                        setAreaToMap(Area.BRB_SLOTS);

                        ((CCABuilding)mapAreas.get(Area.BLT)).calc(mapAreas.get(Area.BLT_SLOTS), mapAreas.get(Area.BLT_PROGRESS));
                        ((CCABuilding)mapAreas.get(Area.BLC)).calc(mapAreas.get(Area.BLC_SLOTS), mapAreas.get(Area.BLC_PROGRESS));
                        ((CCABuilding)mapAreas.get(Area.BLB)).calc(mapAreas.get(Area.BLB_SLOTS), mapAreas.get(Area.BLB_PROGRESS));
                        ((CCABuilding)mapAreas.get(Area.BRT)).calc(mapAreas.get(Area.BRT_SLOTS), mapAreas.get(Area.BRT_PROGRESS));
                        ((CCABuilding)mapAreas.get(Area.BRC)).calc(mapAreas.get(Area.BRC_SLOTS), mapAreas.get(Area.BRC_PROGRESS));
                        ((CCABuilding)mapAreas.get(Area.BRB)).calc(mapAreas.get(Area.BRB_SLOTS), mapAreas.get(Area.BRB_PROGRESS));

                        ((CCAGame)mapAreas.get(Area.CITY)).setPointsOurInScreenshot(((CCATeam)mapAreas.get(Area.TEAM_NAME_OUR)).getCcatPointsInScreenshot());
                        ((CCAGame)mapAreas.get(Area.CITY)).setPointsEnemyInScreenshot(((CCATeam)mapAreas.get(Area.TEAM_NAME_ENEMY)).getCcatPointsInScreenshot());

                        Area[] areasBuildings = new Area[6];
                        areasBuildings[0] = Area.BLT;
                        areasBuildings[1] = Area.BLC;
                        areasBuildings[2] = Area.BLB;
                        areasBuildings[3] = Area.BRT;
                        areasBuildings[4] = Area.BRC;
                        areasBuildings[5] = Area.BRB;

                        for (int buildingIndex = 0; buildingIndex < 6; buildingIndex++) {
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setPresent(((CCABuilding)mapAreas.get(areasBuildings[buildingIndex])).isPresent());
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setSlots(((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() ? ((CCABuilding)mapAreas.get(areasBuildings[buildingIndex])).getSlots() : 0);
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setSlots_our(((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() ? ((CCABuilding)mapAreas.get(areasBuildings[buildingIndex])).getSlots_our() : 0);
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setSlots_empty(((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() ? ((CCABuilding)mapAreas.get(areasBuildings[buildingIndex])).getSlots_empty() : 0);
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setSlots_enemy(((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() ? ((CCABuilding)mapAreas.get(areasBuildings[buildingIndex])).getSlots_enemy() : 0);
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setX2(((CCAGame) mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() && ((CCABuilding) mapAreas.get(areasBuildings[buildingIndex])).isX2());
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setMayX2(((CCAGame) mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() && ((CCABuilding) mapAreas.get(areasBuildings[buildingIndex])).isMayX2());
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setName(((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() ? ((CCABuilding)mapAreas.get(areasBuildings[buildingIndex])).getFinText() : "");
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setBuildingIsOur(((CCAGame) mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() && ((CCABuilding) mapAreas.get(areasBuildings[buildingIndex])).isBuildingIsOur());
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setBuildingIsEmpty(((CCAGame) mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() && ((CCABuilding) mapAreas.get(areasBuildings[buildingIndex])).isBuildingIsEmpty());
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setBuildingIsEnemy(((CCAGame) mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() && ((CCABuilding) mapAreas.get(areasBuildings[buildingIndex])).isBuildingIsEnemy());
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setOur_points(((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() ? ((CCABuilding)mapAreas.get(areasBuildings[buildingIndex])).getOur_points() : 0);
                            ((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].setEnemy_points(((CCAGame)mapAreas.get(Area.CITY)).getBuildings()[buildingIndex].isPresent() ? ((CCABuilding)mapAreas.get(areasBuildings[buildingIndex])).getEnemy_points() : 0);
                        }

                        ((CCAGame)mapAreas.get(Area.CITY)).setIncreaseOur(
                                (((CCABuilding)mapAreas.get(Area.BLT)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BLT)).getOur_points() : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BLC)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BLC)).getOur_points() : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BLB)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BLB)).getOur_points() : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRT)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BRT)).getOur_points() : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRC)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BRC)).getOur_points() : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRB)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BRB)).getOur_points() : 0));

                        ((CCAGame)mapAreas.get(Area.CITY)).setIncreaseEnemy(
                                (((CCABuilding)mapAreas.get(Area.BLT)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BLT)).getEnemy_points() : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BLC)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BLC)).getEnemy_points() : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BLB)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BLB)).getEnemy_points() : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRT)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BRT)).getEnemy_points() : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRC)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BRC)).getEnemy_points() : 0) +
                                        (((CCABuilding)mapAreas.get(Area.BRB)).isPresent() ? ((CCABuilding)mapAreas.get(Area.BRB)).getEnemy_points() : 0));

                        ((CCAGame)mapAreas.get(Area.CITY)).calc(isRealtimeScreenshot);
                        ((CCAGame)mapAreas.get(Area.CITY)).calcWin(true);

                        if (!fileScreenshot.getAbsolutePath().equals(GlobalApplication.pathToCATScalcFolder + "/last_screenshot.PNG")) {
                            Utils.copyFile(fileScreenshot.getAbsolutePath(), GlobalApplication.pathToCATScalcFolder + "/last_screenshot.PNG");
                        }

                    } else if (cityCalcType.equals(CityCalcType.CAR)) {

                        setAreaToMap(Area.CAR_IN_CITY_BOX1);
                        setAreaToMap(Area.CAR_IN_CITY_BOX2);
                        setAreaToMap(Area.BOX_INFO_CAR);
                        setAreaToMap(Area.CAR_IN_CITY_STATEBOX1);
                        setAreaToMap(Area.CAR_IN_CITY_STATEBOX2);
                        setAreaToMap(Area.CAR_IN_CITY_STATEBOX3);
                        setAreaToMap(Area.CAR_IN_CITY_INFO);
                        setAreaToMap(Area.CAR_IN_CITY_BUILDING);
                        setAreaToMap(Area.CAR_IN_CITY_PICTURE);
                        setAreaToMap(Area.CAR_IN_CITY_SLOT1);
                        setAreaToMap(Area.CAR_IN_CITY_SLOT2);
                        setAreaToMap(Area.CAR_IN_CITY_SLOT3);
                        setAreaToMap(Area.CAR_IN_GARAGE_PICTURE);
                        setAreaToMap(Area.CAR_IN_GARAGE_SLOT1);
                        setAreaToMap(Area.CAR_IN_GARAGE_SLOT2);
                        setAreaToMap(Area.CAR_IN_GARAGE_SLOT3);
                        setAreaToMap(Area.CAR_IN_CITY_HEALTH);
                        setAreaToMap(Area.CAR_IN_CITY_SHIELD);
                        setAreaToMap(Area.CAR_IN_GARAGE_HEALTH);
                        setAreaToMap(Area.CAR_IN_GARAGE_SHIELD);
                        setAreaToMap(Area.CAR_IN_CITY_STATE);
                        setAreaToMap(Area.CAR_IN_CITY_HEALBOX);
                        setAreaToMap(Area.CAR_IN_CITY_TIMEBOX1);
                        setAreaToMap(Area.CAR_IN_CITY_TIMEBOX2);

                        ((CCACar)mapAreas.get(Area.CAR_IN_CITY_INFO)).parseCar();

                    } else if (cityCalcType.equals(CityCalcType.BORDERS)) {

                        setAreaToMap(Area.BOX_INFO_CITY);
                        setAreaToMap(Area.BOX_INFO_CAR);
                        setAreaToMap(Area.BOX_INFO_GARAGE);
                        setAreaToMap(Area.BOX_BACK);
                        setAreaToMap(Area.CITY);
                        setAreaToMap(Area.TOTAL_TIME);
                        setAreaToMap(Area.EARLY_WIN);
                        setAreaToMap(Area.POINTS_AND_INCREASE_OUR);
                        setAreaToMap(Area.TEAM_NAME_OUR);
                        setAreaToMap(Area.POINTS_AND_INCREASE_ENEMY);
                        setAreaToMap(Area.TEAM_NAME_ENEMY);
                        setAreaToMap(Area.BLT_AREA);
                        setAreaToMap(Area.BLT);
                        setAreaToMap(Area.BLT_PROGRESS);
                        setAreaToMap(Area.BLT_SLOTS);
                        setAreaToMap(Area.BLC_AREA);
                        setAreaToMap(Area.BLC);
                        setAreaToMap(Area.BLC_PROGRESS);
                        setAreaToMap(Area.BLC_SLOTS);
                        setAreaToMap(Area.BLB_AREA);
                        setAreaToMap(Area.BLB);
                        setAreaToMap(Area.BLB_PROGRESS);
                        setAreaToMap(Area.BLB_SLOTS);
                        setAreaToMap(Area.BRT_AREA);
                        setAreaToMap(Area.BRT);
                        setAreaToMap(Area.BRT_PROGRESS);
                        setAreaToMap(Area.BRT_SLOTS);
                        setAreaToMap(Area.BRC_AREA);
                        setAreaToMap(Area.BRC);
                        setAreaToMap(Area.BRC_PROGRESS);
                        setAreaToMap(Area.BRC_SLOTS);
                        setAreaToMap(Area.BRB_AREA);
                        setAreaToMap(Area.BRB);
                        setAreaToMap(Area.BRB_PROGRESS);
                        setAreaToMap(Area.BRB_SLOTS);
                        setAreaToMap(Area.CAR_IN_CITY_BOX1);
                        setAreaToMap(Area.CAR_IN_CITY_BOX2);
                        setAreaToMap(Area.CAR_IN_CITY_STATEBOX1);
                        setAreaToMap(Area.CAR_IN_CITY_STATEBOX2);
                        setAreaToMap(Area.CAR_IN_CITY_STATEBOX3);
                        setAreaToMap(Area.CAR_IN_CITY_INFO);
                        setAreaToMap(Area.CAR_IN_CITY_BUILDING);
                        setAreaToMap(Area.CAR_IN_CITY_PICTURE);
                        setAreaToMap(Area.CAR_IN_CITY_SLOT1);
                        setAreaToMap(Area.CAR_IN_CITY_SLOT2);
                        setAreaToMap(Area.CAR_IN_CITY_SLOT3);
                        setAreaToMap(Area.CAR_IN_GARAGE_PICTURE);
                        setAreaToMap(Area.CAR_IN_GARAGE_SLOT1);
                        setAreaToMap(Area.CAR_IN_GARAGE_SLOT2);
                        setAreaToMap(Area.CAR_IN_GARAGE_SLOT3);
                        setAreaToMap(Area.CAR_IN_CITY_HEALTH);
                        setAreaToMap(Area.CAR_IN_CITY_SHIELD);
                        setAreaToMap(Area.CAR_IN_GARAGE_HEALTH);
                        setAreaToMap(Area.CAR_IN_GARAGE_SHIELD);
                        setAreaToMap(Area.CAR_IN_CITY_STATE);
                        setAreaToMap(Area.CAR_IN_CITY_HEALBOX);
                        setAreaToMap(Area.CAR_IN_CITY_TIMEBOX1);
                        setAreaToMap(Area.CAR_IN_CITY_TIMEBOX2);

                    } else if (cityCalcType.equals(CityCalcType.COLORS)) {

                        setAreaToMap(Area.BOX_INFO_CITY);
                        setAreaToMap(Area.BOX_INFO_CAR);
                        setAreaToMap(Area.BOX_INFO_GARAGE);
                        setAreaToMap(Area.BOX_BACK);
                        setAreaToMap(Area.CITY);
                        setAreaToMap(Area.TOTAL_TIME);
                        setAreaToMap(Area.EARLY_WIN);
                        setAreaToMap(Area.POINTS_AND_INCREASE_OUR);
                        setAreaToMap(Area.TEAM_NAME_OUR);
                        setAreaToMap(Area.POINTS_AND_INCREASE_ENEMY);
                        setAreaToMap(Area.TEAM_NAME_ENEMY);
                        setAreaToMap(Area.BLT_AREA);
                        setAreaToMap(Area.BLT);
                        setAreaToMap(Area.BLT_PROGRESS);
                        setAreaToMap(Area.BLT_SLOTS);
                        setAreaToMap(Area.BLC_AREA);
                        setAreaToMap(Area.BLC);
                        setAreaToMap(Area.BLC_PROGRESS);
                        setAreaToMap(Area.BLC_SLOTS);
                        setAreaToMap(Area.BLB_AREA);
                        setAreaToMap(Area.BLB);
                        setAreaToMap(Area.BLB_PROGRESS);
                        setAreaToMap(Area.BLB_SLOTS);
                        setAreaToMap(Area.BRT_AREA);
                        setAreaToMap(Area.BRT);
                        setAreaToMap(Area.BRT_PROGRESS);
                        setAreaToMap(Area.BRT_SLOTS);
                        setAreaToMap(Area.BRC_AREA);
                        setAreaToMap(Area.BRC);
                        setAreaToMap(Area.BRC_PROGRESS);
                        setAreaToMap(Area.BRC_SLOTS);
                        setAreaToMap(Area.BRB_AREA);
                        setAreaToMap(Area.BRB);
                        setAreaToMap(Area.BRB_PROGRESS);
                        setAreaToMap(Area.BRB_SLOTS);
                        setAreaToMap(Area.CAR_IN_CITY_BOX1);
                        setAreaToMap(Area.CAR_IN_CITY_BOX2);
                        setAreaToMap(Area.CAR_IN_CITY_STATEBOX1);
                        setAreaToMap(Area.CAR_IN_CITY_STATEBOX2);
                        setAreaToMap(Area.CAR_IN_CITY_STATEBOX3);
                        setAreaToMap(Area.CAR_IN_CITY_INFO);
                        setAreaToMap(Area.CAR_IN_CITY_BUILDING);
                        setAreaToMap(Area.CAR_IN_CITY_PICTURE);
                        setAreaToMap(Area.CAR_IN_CITY_SLOT1);
                        setAreaToMap(Area.CAR_IN_CITY_SLOT2);
                        setAreaToMap(Area.CAR_IN_CITY_SLOT3);
                        setAreaToMap(Area.CAR_IN_GARAGE_PICTURE);
                        setAreaToMap(Area.CAR_IN_GARAGE_SLOT1);
                        setAreaToMap(Area.CAR_IN_GARAGE_SLOT2);
                        setAreaToMap(Area.CAR_IN_GARAGE_SLOT3);
                        setAreaToMap(Area.CAR_IN_CITY_HEALTH);
                        setAreaToMap(Area.CAR_IN_CITY_SHIELD);
                        setAreaToMap(Area.CAR_IN_GARAGE_HEALTH);
                        setAreaToMap(Area.CAR_IN_GARAGE_SHIELD);
                        setAreaToMap(Area.CAR_IN_CITY_STATE);
                        setAreaToMap(Area.CAR_IN_CITY_HEALBOX);
                        setAreaToMap(Area.CAR_IN_CITY_TIMEBOX1);
                        setAreaToMap(Area.CAR_IN_CITY_TIMEBOX2);

                    } else if (cityCalcType.equals(CityCalcType.CALIBRATE)) {

                        setAreaToMap(Area.CITY);

                    }


                } // кесли файл физически существует
            } // кесли файл не нулл

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
