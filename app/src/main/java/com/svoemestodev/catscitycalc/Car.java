package com.svoemestodev.catscitycalc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Car implements Serializable {
    UUID uuid = UUID.randomUUID();
    String name = "";
    int slot;       // слот (1-3)
    int health;     // здоровье
    int shield;     // защита
    int building;   // здание. 0 - свободно, 1-6 - BLT-BLC-BLB-BRT-BTC-BRB
    Date repair = null; // дата/время начала ремонта
    CarState carState = CarState.EMPTY;


    transient public static String pathToFile;
    transient public static String pathToCATScalcFolder;

    public Car() {
    }

    public Bitmap getPicture() {

        String pathToPictureFile = pathToCATScalcFolder + "/car" + this.slot + ".PNG";
        return BitmapFactory.decodeFile(pathToPictureFile);

    }

//    public void setPicture(Bitmap picture) {
//        this.picture = picture;
//    }

    public void savePicture(Bitmap picture) {
        String pathToPictureFile = pathToCATScalcFolder + "/car" + this.slot + ".PNG";
        try {
            FileOutputStream fOut = new FileOutputStream(pathToPictureFile);
            picture.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Car(String name, int slot, int health, int shield, int building, Date repair, CarState carState) {
        this.name = name;
        this.slot = slot;
        this.health = health;
        this.shield = shield;
        this.building = building;
        this.repair = repair;
        this.carState = carState;
    }

    public static List<Car> getDefaultList() {

        List<Car> list = new ArrayList<>();

        list.add(new Car("Car #1", 1, 0, 0, 0, null, CarState.EMPTY));
        list.add(new Car("Car #2", 2, 0, 0, 0, null, CarState.EMPTY));
        list.add(new Car("Car #3", 3, 0, 0, 0, null, CarState.EMPTY));

        return list;
    }

    public static List<Car> loadList() {
        List<Car> list;
        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            list = (List<Car>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            Log.e("Car", "loadList. Ошибка десериализации. Возвращаем список по-умолчанию.");
            list = getDefaultList();
        }
        return list;
    }

    public void save() {
        List<Car> list = loadList();
        List<Car> listNew = new ArrayList<>();
        boolean isFind = false;
        for (Car car : list) {
            if (car.getUuid().equals(this.getUuid())) {
                isFind = true;
                listNew.add(this);
            } else {
                listNew.add(car);
            }
        }
        if (!isFind) listNew.add(this);
        saveList(listNew);
    }

    public static boolean saveList(List<Car> list) {
        File file = new File(pathToFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(list);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("Car", "saveList. Ошибка сериализации");
            return false;
        }
    }

    public CarState getState() {
        if (getSecondsToEndRepairing() > 0) {
            return CarState.REPAIRING;
        } else {
            if (carState.equals(CarState.REPAIRING)) setStateFree();
            return carState;
        }
    }

    public void setStateEmpty() {
        this.carState = CarState.EMPTY;
        this.health = 0;
        this.shield = 0;
        this.building = 0;
        this.repair = null;
    }

    public void setStateFree() {
        this.carState = CarState.FREE;
        this.building = 0;
        this.repair = null;
    }

    public void setStateDefencing(int building) {
        this.carState = CarState.DEFENCING;
        this.building = building;
        this.repair = null;
    }

    public void setStateRepairingNow(long secondsToEndRepairing) {
        Date dateScreenshot = Calendar.getInstance().getTime();
        setStateRepairing(dateScreenshot, secondsToEndRepairing);
    }

    public void setStateRepairing(Date dateScreenshot, long secondsToEndRepairing) {
        if (secondsToEndRepairing > 0) {
            repair = new Date((dateScreenshot.getTime() / 1000 + secondsToEndRepairing) * 1000);
            carState = CarState.REPAIRING;
        } else {
            repair = null;
            carState = CarState.FREE;
        }
        building = 0;
    }

    public String getTimeStringToEndRepairing() {
        return Utils.convertSecondsToHHMMSS(getSecondsToEndRepairing());
    }

    public String getTimeStringToEndRepairingWithoutColon() {
        return Utils.convertSecondsToHHMMSSwithoutColon(getSecondsToEndRepairing());
    }

    public long getSecondsToEndRepairing() {
        if (repair == null) return 0;
        Date currDate = Calendar.getInstance().getTime();
        long result = (repair.getTime() - currDate.getTime()) / 1000;
        if (result < 0) result = 0;
        return result;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public Date getRepair() {
        return repair;
    }

    public void setRepair(Date repair) {
        this.repair = repair;
    }

}
