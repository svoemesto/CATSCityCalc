package com.svoemestodev.catscitycalc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
    Date repair = null; // дата/время начала ремонта. если null - машина "здорова"
    int building = -1;  // номер занятого здания. если -1 - машина свободна. 0 - занято неизвестное здание. 1-6 - занято конкретное здание.
    int buildingTask = -1;  // номер занятого здания. если -1 - машина свободна. 0 - занято неизвестное здание. 1-6 - занято конкретное здание.
    byte[] imageByteArrayCar = null; // картинка машины
    byte[] imageByteArrayCarDefencing = null; // картинка машины
    byte[] imageByteArrayCarRepairing = null; // картинка машины
    byte[] imageByteArrayBuilding = null; // катинка здания
    byte[] imageByteArrayTask = null; // катинка здания

    transient public static String pathToFile;
    transient public static String pathToCATScalcFolder;

    public Car() {
    }

    public Bitmap getCarPicture() {

        if (imageByteArrayCar != null) {
            return BitmapFactory.decodeByteArray(imageByteArrayCar, 0, imageByteArrayCar.length);
        } else if (imageByteArrayCarDefencing != null) {
            setCarPicture(getCarPictureDefencing());
            save();
            return BitmapFactory.decodeByteArray(imageByteArrayCar, 0, imageByteArrayCar.length);
        } else if (imageByteArrayCarRepairing != null) {
            setCarPicture(getCarPictureRepairing());
            save();
            return BitmapFactory.decodeByteArray(imageByteArrayCar, 0, imageByteArrayCar.length);
        } else {
            return null;
        }

    }

    public void setCarPicture(Bitmap picture) {
        if (picture != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageByteArrayCar = stream.toByteArray();
        }

    }

    public Bitmap getCarPictureDefencing() {

        if (imageByteArrayCarDefencing != null) {
            return BitmapFactory.decodeByteArray(imageByteArrayCarDefencing, 0, imageByteArrayCarDefencing.length);
        } else {
            return null;
        }

    }

    public void setCarPictureDefencing(Bitmap picture) {
        if (picture != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageByteArrayCarDefencing = stream.toByteArray();
        }

    }

    public Bitmap getCarPictureRepairing() {

        if (imageByteArrayCarRepairing != null) {
            return BitmapFactory.decodeByteArray(imageByteArrayCarRepairing, 0, imageByteArrayCarRepairing.length);
        } else {
            return null;
        }

    }

    public void setCarPictureRepairing(Bitmap picture) {
        if (picture != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageByteArrayCarRepairing = stream.toByteArray();
        }

    }

    public Bitmap getBuildingPicture() {
        if (imageByteArrayBuilding != null) {
            return BitmapFactory.decodeByteArray(imageByteArrayBuilding, 0, imageByteArrayBuilding.length);
        } else {
            return null;
        }
    }

    public void setBuildingPicture(Bitmap picture) {
        if (picture != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageByteArrayBuilding = stream.toByteArray();
        }

    }

    public Bitmap getTaskPicture() {
        if (imageByteArrayTask != null) {
            return BitmapFactory.decodeByteArray(imageByteArrayTask, 0, imageByteArrayTask.length);
        } else {
            return null;
        }
    }

    public void setTaskPicture(Bitmap picture) {
        if (picture != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageByteArrayTask = stream.toByteArray();
        }

    }
    
    public Car(String name, int slot, int health, int shield) {
        this.name = name;
        this.slot = slot;
        this.health = health;
        this.shield = shield;
    }

    public static List<Car> getDefaultList() {

        List<Car> list = new ArrayList<>();

        list.add(new Car("Car #1", 1, 0, 0));
        list.add(new Car("Car #2", 2, 0, 0));
        list.add(new Car("Car #3", 3, 0, 0));

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

    public void setStateFree() {
        this.building = -1;
        this.repair = null;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getBuilding() {
        return building;
    }

    public int getBuildingTask() {
        return buildingTask;
    }

    public void setBuildingTask(int buildingTask) {
        this.buildingTask = buildingTask;
    }

    public void setRepairingStateTillNow(long secondsToEndRepairing) {
        Date dateScreenshot = Calendar.getInstance().getTime();
        setRepairingState(dateScreenshot, secondsToEndRepairing);
    }


    public void setRepairingState(Date dateScreenshot, long secondsToEndRepairing) {
        if (secondsToEndRepairing > 0) {
            repair = new Date((dateScreenshot.getTime() / 1000 + secondsToEndRepairing) * 1000);
        } else {
            repair = null;
        }
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

    public boolean isFree() {
        return !isRepairing() && !isDefencing();
    }

    public boolean isDefencing() {
        return this.building != -1;
    }

    public boolean isRepairing() {
        return getSecondsToEndRepairing() > 0;
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

    public Date getRepair() {
        return repair;
    }

    public void setRepair(Date repair) {
        this.repair = repair;
    }

}
