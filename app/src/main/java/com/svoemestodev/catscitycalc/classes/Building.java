package com.svoemestodev.catscitycalc.classes;

import android.graphics.Bitmap;

import com.svoemestodev.catscitycalc.activities.GameActivity;
import com.svoemestodev.catscitycalc.citycalcclasses.Area;
import com.svoemestodev.catscitycalc.citycalcclasses.CCABuilding;
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
            if (((CCABuilding) GameActivity.mainCityCalc.getMapAreas().get(Area.BLT)).isPresent()) list.add(new Building(1, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(Area.BLT)).getBmpSrc()));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(Area.BLC)).isPresent()) list.add(new Building(2, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(Area.BLC)).getBmpSrc()));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(Area.BLB)).isPresent()) list.add(new Building(3, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(Area.BLB)).getBmpSrc()));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(Area.BRT)).isPresent()) list.add(new Building(4, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(Area.BRT)).getBmpSrc()));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(Area.BRC)).isPresent()) list.add(new Building(5, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(Area.BRC)).getBmpSrc()));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(Area.BRB)).isPresent()) list.add(new Building(6, ((CCABuilding)GameActivity.mainCityCalc.getMapAreas().get(Area.BRB)).getBmpSrc()));
        } catch (Exception ignored) {
        }

        return list;
    }

}
