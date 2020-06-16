package com.svoemestodev.catscitycalc.citycalcclasses;

import android.content.Context;
import android.content.SharedPreferences;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.classes.Car;
import com.svoemestodev.catscitycalc.activities.GameActivity;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CCACar extends CityCalcArea {

    private Car car = null;

    public CCACar(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr, boolean needBW) {
        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
    }


    public void parseCar() {

        car = new Car();

        long secondsToEndRepairing = 0;
        Date screenshotDate = Calendar.getInstance().getTime();

        CityCalcArea areaCarInCityBox1 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_BOX1);

        SharedPreferences sharedPreferences = this.getCityCalc().getContext().getSharedPreferences(this.getCityCalc().getContext().getResources().getString(R.string.pref_preferences_file), Context.MODE_PRIVATE);
        int color_car_in_city_box1_main = sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_rgb_car_in_city_box1_main),sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_def_rgb_car_in_city_box1_main), (int)Long.parseLong(this.getCityCalc().getContext().getString(R.string.def_rgb_car_in_city_box1_main), 16)));
        int color_car_in_city_box1_thm = sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_rgb_car_in_city_box1_thm),sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_def_rgb_car_in_city_box1_thm), Integer.parseInt(this.getCityCalc().getContext().getString(R.string.def_rgb_car_in_city_box1_thm))));
        int color_car_in_city_box1_thp = sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_rgb_car_in_city_box1_thp),sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_def_rgb_car_in_city_box1_thp), Integer.parseInt(this.getCityCalc().getContext().getString(R.string.def_rgb_car_in_city_box1_thp))));
        int color_box_info_car_main = sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_rgb_box_info_car_main),sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_def_rgb_box_info_car_main), (int)Long.parseLong(this.getCityCalc().getContext().getString(R.string.def_rgb_box_info_car_main), 16)));
        int color_box_info_car_thm = sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_rgb_box_info_car_thm),sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_def_rgb_box_info_car_thm), Integer.parseInt(this.getCityCalc().getContext().getString(R.string.def_rgb_box_info_car_thm))));
        int color_box_info_car_thp = sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_rgb_box_info_car_thp),sharedPreferences.getInt(this.getCityCalc().getContext().getString(R.string.pref_def_rgb_box_info_car_thp), Integer.parseInt(this.getCityCalc().getContext().getString(R.string.def_rgb_box_info_car_thp))));

        boolean isCarInCity = PictureProcessor.frequencyPixelInBitmap(areaCarInCityBox1.getBmpSrc(), color_car_in_city_box1_main, color_car_in_city_box1_thm, color_car_in_city_box1_thp) > 0.50f;

        if (isCarInCity) { // машина в городе

            CityCalcArea areaCarInCitySlot1 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_SLOT1);
            CityCalcArea areaCarInCitySlot2 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_SLOT2);
            CityCalcArea areaCarInCitySlot3 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_SLOT3);
            CityCalcArea areaCarInCityHealth = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_HEALTH);
            CityCalcArea areaCarInCityShield = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_SHIELD);
            CityCalcArea areaCarInCityStatebox1 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_STATEBOX1);
            CityCalcArea areaCarInCityStatebox2 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_STATEBOX2);
            CityCalcArea areaCarInCityStatebox3 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_STATEBOX3);
            CityCalcArea areaCarInCityHealbox = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_HEALBOX);
            CityCalcArea areaCarInCityTimebox1 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_TIMEBOX1);
            CityCalcArea areaCarInCityTimebox2 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_TIMEBOX2);
            CityCalcArea areaCarInCityPicture = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_PICTURE);
            CityCalcArea areaCarInCityBuilding = this.getCityCalc().getMapAreas().get(Area.CAR_IN_CITY_BUILDING);

            car.setHealth(Integer.parseInt(areaCarInCityHealth.getFinText()));
            car.setShield(Integer.parseInt(areaCarInCityShield.getFinText()));

            // распознаем слоты
            boolean isSlot1 = PictureProcessor.frequencyPixelInBitmap(areaCarInCitySlot1.getBmpSrc(), areaCarInCitySlot1.getColors()[0],areaCarInCitySlot1.getThs()[0], areaCarInCitySlot1.getThs()[1]) > 0.01f;    // обнаружен слот 1
            boolean isSlot2 = PictureProcessor.frequencyPixelInBitmap(areaCarInCitySlot2.getBmpSrc(), areaCarInCitySlot2.getColors()[0],areaCarInCitySlot2.getThs()[0], areaCarInCitySlot2.getThs()[1]) > 0.01f;    // обнаружен слот 2
            boolean isSlot3 = PictureProcessor.frequencyPixelInBitmap(areaCarInCitySlot3.getBmpSrc(), areaCarInCitySlot3.getColors()[0],areaCarInCitySlot3.getThs()[0], areaCarInCitySlot3.getThs()[1]) > 0.01f;    // обнаружен слот 3

            if (isSlot1) car.setSlot(1); // есть белый цвет в слоте1 - машина №1
            if (isSlot2) car.setSlot(2); // есть белый цвет в слоте2 - машина №2
            if (isSlot3) car.setSlot(3); // есть белый цвет в слоте3 - машина №3

            // распознаем стейтбоксы
            boolean isStatebox1 = PictureProcessor.frequencyPixelInBitmap(areaCarInCityStatebox1.getBmpSrc(), areaCarInCityStatebox1.getColors()[0],areaCarInCityStatebox1.getThs()[0], areaCarInCityStatebox1.getThs()[1]) > 0.28f; // обнаружен стейтбокс1 - в боксе есть ключ - починка в защите
            boolean isStatebox2 = PictureProcessor.frequencyPixelInBitmap(areaCarInCityStatebox2.getBmpSrc(), areaCarInCityStatebox2.getColors()[0],areaCarInCityStatebox2.getThs()[0], areaCarInCityStatebox2.getThs()[1]) > 0.50f; // обнаружен стейтбокс2 - в боксе есть белый цвет
            boolean isStatebox3 = PictureProcessor.frequencyPixelInBitmap(areaCarInCityStatebox3.getBmpSrc(), areaCarInCityStatebox3.getColors()[0],areaCarInCityStatebox3.getThs()[0], areaCarInCityStatebox3.getThs()[1]) > 0.28f; // обнаружен стейтбокс3 - в боксе есть ключ - починка
            boolean isHealbox = PictureProcessor.frequencyPixelInBitmap(areaCarInCityHealbox.getBmpSrc(), areaCarInCityHealbox.getColors()[0],areaCarInCityHealbox.getThs()[0], areaCarInCityHealbox.getThs()[1]) > 0.01f; // обнаружен хилбокс - в боксе есть красный цвет

            if (!isStatebox2) { // если нет стейтбокса2 - машина гарантированно свободна и починена
                // устанавливаем свободный статус машины и забираем ее картинку
                car.setStateFree();
                car.setCarPicture(areaCarInCityPicture.getBmpSrc());
            } else {

                if (isStatebox1) { // если есть стейтбокс1 - машина гарантированно ремонтируется
                    // парсим и устанавливаем время ремонта
                    secondsToEndRepairing = Utils.conversTimeStringWithoutColonsToSeconds(areaCarInCityTimebox1.getOcrText());
                    screenshotDate = new Date(this.getCityCalc().getFileScreenshot().lastModified());
                    car.setRepairingState(screenshotDate,secondsToEndRepairing);
                    car.setCarPictureRepairing(areaCarInCityPicture.getBmpSrc());
                    car.setCarPictureDefencing(areaCarInCityPicture.getBmpSrc());
                }

                if (isStatebox3) { // если есть стейтбокс1 - машина гарантированно ремонтируется
                    // парсим и устанавливаем время ремонта
                    secondsToEndRepairing = Utils.conversTimeStringWithoutColonsToSeconds(areaCarInCityTimebox2.getOcrText());
                    screenshotDate = new Date(this.getCityCalc().getFileScreenshot().lastModified());
                    car.setRepairingState(screenshotDate,secondsToEndRepairing);
                    car.setCarPictureRepairing(areaCarInCityPicture.getBmpSrc());


                }

                if (!isHealbox) { // если при этом нет хилбокса - значит машина стоит в здании
                    // устанавливаем нулевое здание и его картинку
//                    CityCalcArea areaBoxInfoCar = this.getCityCalc().getMapAreas().get(Area.BOX_INFO_CAR);
                    boolean isCarInBuilding = PictureProcessor.frequencyPixelInBitmap(areaCarInCityBuilding.getBmpSrc(), color_box_info_car_main, color_box_info_car_thm, color_box_info_car_thp) > 0.01f;
                    if (isCarInBuilding) {
                        car.setBuilding(0);
                        car.setBuildingPicture(areaCarInCityBuilding.getBmpSrc());
                        car.setCarPictureDefencing(areaCarInCityPicture.getBmpSrc());
                    }
                }

            }

            if (car.isDefencing()) {
                // если машина в защите - попытаемся найти в каком она здании
                String carBuildingName = areaCarInCityBuilding.getOcrText();
                CCABuilding ccaBuilding;

                if (GameActivity.mainCityCalc != null) {

                    ccaBuilding = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BLT);
                    if (ccaBuilding.isPresent()) {
                        if (ccaBuilding.getOcrText().equals(carBuildingName)) {
                            car.setBuilding(1);
                            car.setBuildingPicture(ccaBuilding.getBmpSrc());
                        }
                    }

                    ccaBuilding = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BLC);
                    if (ccaBuilding.isPresent()) {
                        if (ccaBuilding.getOcrText().equals(carBuildingName)) {
                            car.setBuilding(2);
                            car.setBuildingPicture(ccaBuilding.getBmpSrc());
                        }
                    }

                    ccaBuilding = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BLB);
                    if (ccaBuilding.isPresent()) {
                        if (ccaBuilding.getOcrText().equals(carBuildingName)) {
                            car.setBuilding(3);
                            car.setBuildingPicture(ccaBuilding.getBmpSrc());
                        }
                    }

                    ccaBuilding = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BRT);
                    if (ccaBuilding.isPresent()) {
                        if (ccaBuilding.getOcrText().equals(carBuildingName)) {
                            car.setBuilding(4);
                            car.setBuildingPicture(ccaBuilding.getBmpSrc());
                        }
                    }

                    ccaBuilding = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BRC);
                    if (ccaBuilding.isPresent()) {
                        if (ccaBuilding.getOcrText().equals(carBuildingName)) {
                            car.setBuilding(5);
                            car.setBuildingPicture(ccaBuilding.getBmpSrc());
                        }
                    }

                    ccaBuilding = (CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BRB);
                    if (ccaBuilding.isPresent()) {
                        if (ccaBuilding.getOcrText().equals(carBuildingName)) {
                            car.setBuilding(6);
                            car.setBuildingPicture(ccaBuilding.getBmpSrc());
                        }
                    }

                }

            }

        } else { // машина в гараже

            CityCalcArea areaCarInGaragePicture = this.getCityCalc().getMapAreas().get(Area.CAR_IN_GARAGE_PICTURE);
            CityCalcArea areaCarInGarageSlot1 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_GARAGE_SLOT1);
            CityCalcArea areaCarInGarageSlot2 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_GARAGE_SLOT2);
            CityCalcArea areaCarInGarageSlot3 = this.getCityCalc().getMapAreas().get(Area.CAR_IN_GARAGE_SLOT3);
            CityCalcArea areaCarInGarageHealth = this.getCityCalc().getMapAreas().get(Area.CAR_IN_GARAGE_HEALTH);
            CityCalcArea areaCarInGarageShield = this.getCityCalc().getMapAreas().get(Area.CAR_IN_GARAGE_SHIELD);
            // распознаем слоты
            boolean isSlot1 = PictureProcessor.frequencyPixelInBitmap(areaCarInGarageSlot1.getBmpSrc(), areaCarInGarageSlot1.getColors()[0],areaCarInGarageSlot1.getThs()[0], areaCarInGarageSlot1.getThs()[1]) > 0.01f;    // обнаружен слот 1
            boolean isSlot2 = PictureProcessor.frequencyPixelInBitmap(areaCarInGarageSlot2.getBmpSrc(), areaCarInGarageSlot2.getColors()[0],areaCarInGarageSlot2.getThs()[0], areaCarInGarageSlot2.getThs()[1]) > 0.01f;    // обнаружен слот 2
            boolean isSlot3 = PictureProcessor.frequencyPixelInBitmap(areaCarInGarageSlot3.getBmpSrc(), areaCarInGarageSlot3.getColors()[0],areaCarInGarageSlot3.getThs()[0], areaCarInGarageSlot3.getThs()[1]) > 0.01f;    // обнаружен слот 3

            if (isSlot1) car.setSlot(1); // есть белый цвет в слоте1 - машина №1
            if (isSlot2) car.setSlot(2); // есть белый цвет в слоте2 - машина №2
            if (isSlot3) car.setSlot(3); // есть белый цвет в слоте3 - машина №3

            car.setCarPicture(areaCarInGaragePicture.getBmpSrc());
            try {
                car.setHealth(Integer.parseInt(areaCarInGarageHealth.getFinText()));
                car.setShield(Integer.parseInt(areaCarInGarageShield.getFinText()));
            } catch (NumberFormatException e) {
                car.setHealth(0);
                car.setShield(0);
            }

        }


        if (car.getSlot() != 0) {
            List<Car> listCars = Car.loadList();
            Car updatedCar = listCars.get(car.getSlot()-1);
            updatedCar.setHealth(car.getHealth());
            updatedCar.setShield(car.getShield());
            if (!isCarInCity) {
                updatedCar.setCarPicture(car.getCarPicture());
            } else {
                if (car.isFree()) {
                    updatedCar.setStateFree();
                    updatedCar.setCarPicture(car.getCarPicture());
                } else {
                    if (car.isDefencing()) {
                        updatedCar.setBuilding(car.getBuilding());
                        updatedCar.setBuildingPicture(car.getBuildingPicture());
                        updatedCar.setCarPictureDefencing(car.getCarPictureDefencing());
                    }
                    if (car.isRepairing()) {
                        updatedCar.setRepairingState(screenshotDate,secondsToEndRepairing);
                        updatedCar.setCarPictureRepairing(car.getCarPictureRepairing());
                    }
                }
            }

            updatedCar.save();
        }
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
