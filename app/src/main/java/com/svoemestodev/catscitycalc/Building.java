package com.svoemestodev.catscitycalc;

import android.graphics.Bitmap;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Building {
    int slot;
    Bitmap bitmap;

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

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BLT)).isPresent) list.add(new Building(1, ((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BLT)).bmpSrc));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BLC)).isPresent) list.add(new Building(2, ((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BLC)).bmpSrc));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BLB)).isPresent) list.add(new Building(3, ((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BLB)).bmpSrc));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BRT)).isPresent) list.add(new Building(4, ((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BRT)).bmpSrc));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BRC)).isPresent) list.add(new Building(5, ((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BRC)).bmpSrc));
        } catch (Exception ignored) {
        }

        try {
            if (((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BRB)).isPresent) list.add(new Building(6, ((CCABuilding)GameActivity.mainCityCalc.mapAreas.get(Area.BRB)).bmpSrc));
        } catch (Exception ignored) {
        }

        return list;
    }

}
