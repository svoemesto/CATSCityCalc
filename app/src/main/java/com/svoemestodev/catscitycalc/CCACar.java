package com.svoemestodev.catscitycalc;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CCACar extends CityCalcArea {

    Car car = null;

    public CCACar(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr, boolean needBW) {
        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOcr, needBW);
    }


    public void parseCar() {

        car = new Car();

        CityCalcArea areaSlot1 = this.cityCalc.mapAreas.get(Area.CAR_SLOT1);
        CityCalcArea areaSlot2 = this.cityCalc.mapAreas.get(Area.CAR_SLOT2);
        CityCalcArea areaSlot3 = this.cityCalc.mapAreas.get(Area.CAR_SLOT3);
        CityCalcArea areaHealth = this.cityCalc.mapAreas.get(Area.CAR_HEALTH);
        CityCalcArea areaShield = this.cityCalc.mapAreas.get(Area.CAR_SHIELD);
        CityCalcArea areaState = this.cityCalc.mapAreas.get(Area.CAR_STATE);
        CityCalcArea areaHealbox = this.cityCalc.mapAreas.get(Area.CAR_HEALBOX);
        CityCalcArea areaTimebox = this.cityCalc.mapAreas.get(Area.CAR_TIMEBOX);
        CityCalcArea areaPicture = this.cityCalc.mapAreas.get(Area.CAR_PICTURE);
        long secondsToEndRepairing = 0;
        Date screenshotDate = Calendar.getInstance().getTime();

        if (PictureProcessor.frequencyPixelInBitmap(areaSlot1.bmpSrc, areaSlot1.colors[0],areaSlot1.ths[0], areaSlot1.ths[1]) > 0.01f) car.setSlot(1);
        if (PictureProcessor.frequencyPixelInBitmap(areaSlot2.bmpSrc, areaSlot2.colors[0],areaSlot2.ths[0], areaSlot2.ths[1]) > 0.01f) car.setSlot(2);
        if (PictureProcessor.frequencyPixelInBitmap(areaSlot3.bmpSrc, areaSlot3.colors[0],areaSlot3.ths[0], areaSlot3.ths[1]) > 0.01f) car.setSlot(3);
        if (PictureProcessor.frequencyPixelInBitmap(areaState.bmpSrc, areaState.colors[0],areaState.ths[0], areaState.ths[1]) > 0.01f) {
            car.setStateDefencing();
        } else if (PictureProcessor.frequencyPixelInBitmap(areaHealbox.bmpSrc, areaHealbox.colors[0],areaHealbox.ths[0], areaHealbox.ths[1]) > 0.01f) {
            areaTimebox.needOcr = true;
            areaTimebox.doOCR();
            secondsToEndRepairing = Utils.conversTimeStringWithoutColonsToSeconds(areaTimebox.ocrText);
            screenshotDate = new Date(this.cityCalc.fileScreenshot.lastModified());
            car.setStateRepairing(screenshotDate,secondsToEndRepairing);
        } else {
            car.setStateFree();
//            car.savePicture(areaPicture.bmpSrc);
            car.setPicture(areaPicture.bmpSrc);
        }

        areaHealth.needOcr = true;
        areaShield.needOcr = true;
        areaHealth.needBW = true;
        areaShield.needBW = true;
        areaHealth.doOCR();
        areaShield.doOCR();
        car.setHealth(Integer.parseInt(areaHealth.finText));
        car.setShield(Integer.parseInt(areaShield.finText));

        if (car.getSlot() != 0) {
            List<Car> listCars = Car.loadList();
            Car updatedCar = listCars.get(car.getSlot()-1);
            updatedCar.setHealth(car.getHealth());
            updatedCar.setShield(car.getShield());
            if (car.getState().equals(CarState.FREE)) {
                updatedCar.setPicture(car.getPicture());
                updatedCar.setStateFree();
            } else if (car.getState().equals(CarState.DEFENCING)) {
                updatedCar.setStateDefencing();
            } else if (car.getState().equals(CarState.REPAIRING)) {
                updatedCar.setStateRepairing(screenshotDate,secondsToEndRepairing);
            }
            updatedCar.save();
        }
    }
}
