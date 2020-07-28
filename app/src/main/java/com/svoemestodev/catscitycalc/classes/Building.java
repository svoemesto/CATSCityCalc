package com.svoemestodev.catscitycalc.classes;

import android.graphics.Bitmap;

import com.svoemestodev.catscitycalc.activities.GameActivity;
import com.svoemestodev.catscitycalc.citycalcclasses.Area;
import com.svoemestodev.catscitycalc.citycalcclasses.CCABuilding;
import com.svoemestodev.catscitycalc.ssa.SSA_Key;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private int slot;
    private Bitmap bitmap;

    public Building() {
    }

    public Building(int slot, Bitmap bitmap) {
        this.slot = slot;
        this.bitmap = bitmap;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public static List<Building> loadList() {
        List<Building> list = new ArrayList<>();

        list.add(new Building(-1, PictureProcessor.generateBitmapByOnePixel(0xFF000000,1,1)));

        try {
            if (((CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD1.getKey())).isPresent()) list.add(new Building(1, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD1.getKey())).getBmpSrc()));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD2.getKey())).isPresent()) list.add(new Building(2, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD2.getKey())).getBmpSrc()));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD3.getKey())).isPresent()) list.add(new Building(3, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD3.getKey())).getBmpSrc()));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD4.getKey())).isPresent()) list.add(new Building(4, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD4.getKey())).getBmpSrc()));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD5.getKey())).isPresent()) list.add(new Building(5, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD5.getKey())).getBmpSrc()));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD6.getKey())).isPresent()) list.add(new Building(6, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(SSA_Key.AREA_CITY_BLD6.getKey())).getBmpSrc()));
        } catch (Exception ignored) {
        }

        return list;
    }

}
