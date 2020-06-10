package com.svoemestodev.catscitycalc.citycalcclasses;

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

        CityCalcArea areaSlot1 = this.getCityCalc().getMapAreas().get(Area.CAR_SLOT1);
        CityCalcArea areaSlot2 = this.getCityCalc().getMapAreas().get(Area.CAR_SLOT2);
        CityCalcArea areaSlot3 = this.getCityCalc().getMapAreas().get(Area.CAR_SLOT3);
        CityCalcArea areaHealth = this.getCityCalc().getMapAreas().get(Area.CAR_HEALTH);
        CityCalcArea areaShield = this.getCityCalc().getMapAreas().get(Area.CAR_SHIELD);
        CityCalcArea areaStatebox1 = this.getCityCalc().getMapAreas().get(Area.CAR_STATEBOX1);
        CityCalcArea areaStatebox2 = this.getCityCalc().getMapAreas().get(Area.CAR_STATEBOX2);
        CityCalcArea areaStatebox3 = this.getCityCalc().getMapAreas().get(Area.CAR_STATEBOX3);
        CityCalcArea areaHealbox = this.getCityCalc().getMapAreas().get(Area.CAR_HEALBOX);
        CityCalcArea areaTimebox1 = this.getCityCalc().getMapAreas().get(Area.CAR_TIMEBOX1);
        CityCalcArea areaTimebox2 = this.getCityCalc().getMapAreas().get(Area.CAR_TIMEBOX2);
        CityCalcArea areaPicture = this.getCityCalc().getMapAreas().get(Area.CAR_PICTURE);
        CityCalcArea areaBuilding = this.getCityCalc().getMapAreas().get(Area.CAR_BUILDING);

        long secondsToEndRepairing = 0;
        Date screenshotDate = Calendar.getInstance().getTime();

        car.setHealth(Integer.parseInt(areaHealth.getFinText()));
        car.setShield(Integer.parseInt(areaShield.getFinText()));

        // распознаем слоты
        boolean isSlot1 = PictureProcessor.frequencyPixelInBitmap(areaSlot1.getBmpSrc(), areaSlot1.getColors()[0],areaSlot1.getThs()[0], areaSlot1.getThs()[1]) > 0.01f;    // обнаружен слот 1
        boolean isSlot2 = PictureProcessor.frequencyPixelInBitmap(areaSlot2.getBmpSrc(), areaSlot2.getColors()[0],areaSlot2.getThs()[0], areaSlot2.getThs()[1]) > 0.01f;    // обнаружен слот 2
        boolean isSlot3 = PictureProcessor.frequencyPixelInBitmap(areaSlot3.getBmpSrc(), areaSlot3.getColors()[0],areaSlot3.getThs()[0], areaSlot3.getThs()[1]) > 0.01f;    // обнаружен слот 3

        if (isSlot1) car.setSlot(1); // есть белый цвет в слоте1 - машина №1
        if (isSlot2) car.setSlot(2); // есть белый цвет в слоте2 - машина №2
        if (isSlot3) car.setSlot(3); // есть белый цвет в слоте3 - машина №3

        // распознаем стейтбоксы
        boolean isStatebox1 = PictureProcessor.frequencyPixelInBitmap(areaStatebox1.getBmpSrc(), areaStatebox1.getColors()[0],areaStatebox1.getThs()[0], areaStatebox1.getThs()[1]) > 0.28f; // обнаружен стейтбокс1 - в боксе есть ключ - починка в защите
        boolean isStatebox2 = PictureProcessor.frequencyPixelInBitmap(areaStatebox2.getBmpSrc(), areaStatebox2.getColors()[0],areaStatebox2.getThs()[0], areaStatebox2.getThs()[1]) > 0.50f; // обнаружен стейтбокс2 - в боксе есть белый цвет
        boolean isStatebox3 = PictureProcessor.frequencyPixelInBitmap(areaStatebox3.getBmpSrc(), areaStatebox3.getColors()[0],areaStatebox3.getThs()[0], areaStatebox3.getThs()[1]) > 0.28f; // обнаружен стейтбокс3 - в боксе есть ключ - починка
        boolean isHealbox = PictureProcessor.frequencyPixelInBitmap(areaHealbox.getBmpSrc(), areaHealbox.getColors()[0],areaHealbox.getThs()[0], areaHealbox.getThs()[1]) > 0.01f; // обнаружен хилбокс - в боксе есть красный цвет

        if (!isStatebox2) { // если нет стейтбокса2 - машина гарантированно свободна и починена
            // устанавливаем свободный статус машины и забираем ее картинку
            car.setStateFree();
            car.setCarPicture(areaPicture.getBmpSrc());
        } else {

            if (isStatebox1) { // если есть стейтбокс1 - машина гарантированно ремонтируется
                // парсим и устанавливаем время ремонта
                secondsToEndRepairing = Utils.conversTimeStringWithoutColonsToSeconds(areaTimebox1.getOcrText());
                screenshotDate = new Date(this.getCityCalc().getFileScreenshot().lastModified());
                car.setRepairingState(screenshotDate,secondsToEndRepairing);
                car.setCarPictureRepairing(areaPicture.getBmpSrc());
                car.setCarPictureDefencing(areaPicture.getBmpSrc());
            }

            if (isStatebox3) { // если есть стейтбокс1 - машина гарантированно ремонтируется
                // парсим и устанавливаем время ремонта
                secondsToEndRepairing = Utils.conversTimeStringWithoutColonsToSeconds(areaTimebox2.getOcrText());
                screenshotDate = new Date(this.getCityCalc().getFileScreenshot().lastModified());
                car.setRepairingState(screenshotDate,secondsToEndRepairing);
                car.setCarPictureRepairing(areaPicture.getBmpSrc());


            }

            if (!isHealbox) { // если при этом нет хилбокса - значит машина стоит в здании
                // устанавливаем нулевое здание и его картинку
                car.setBuilding(0);
                car.setBuildingPicture(areaBuilding.getBmpSrc());
                car.setCarPictureDefencing(areaPicture.getBmpSrc());
            }

        }

        if (car.isDefencing()) {
            // если машина в защите - попытаемся найти в каком она здании
            String carBuildingName = areaBuilding.getOcrText();
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


        if (car.getSlot() != 0) {
            List<Car> listCars = Car.loadList();
            Car updatedCar = listCars.get(car.getSlot()-1);
            updatedCar.setHealth(car.getHealth());
            updatedCar.setShield(car.getShield());
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
