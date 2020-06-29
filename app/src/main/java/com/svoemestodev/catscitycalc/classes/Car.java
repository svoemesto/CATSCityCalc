package com.svoemestodev.catscitycalc.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;
import com.svoemestodev.catscitycalc.GlobalApplication;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.activities.GameActivity;
import com.svoemestodev.catscitycalc.adapters.ListBuildingAdapter;
import com.svoemestodev.catscitycalc.adapters.ListCarsAdapter;
import com.svoemestodev.catscitycalc.utils.Utils;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Car implements Serializable {
//    private UUID uuid = UUID.randomUUID();
    private String name = "";

    private String userUID;
    private String userNIC;
    private String teamID;

    private int slot;       // слот (1-3)
    private int health;     // здоровье
    private int shield;     // защита
    private Date repair = null; // дата/время начала ремонта. если null - машина "здорова"
    private int building = -1;  // номер занятого здания. если -1 - машина свободна. 0 - занято неизвестное здание. 1-6 - занято конкретное здание.
    private int buildingTask = -1;  // номер занятого здания. если -1 - машина свободна. 0 - занято неизвестное здание. 1-6 - занято конкретное здание.
    private byte[] imageByteArrayCar = null; // картинка машины
    private byte[] imageByteArrayCarDefencing = null; // картинка машины
    private byte[] imageByteArrayCarRepairing = null; // картинка машины
    private byte[] imageByteArrayBuilding = null; // катинка здания
    private byte[] imageByteArrayTask = null; // катинка здания

    private transient boolean isChecked = false;
    public transient static String pathToFile;
    public transient static String pathToCATScalcFolder;


    public Car() {
    }



    public Bitmap getCarPicture() {

        if (imageByteArrayCar != null) {
            return BitmapFactory.decodeByteArray(imageByteArrayCar, 0, imageByteArrayCar.length);
        } else if (imageByteArrayCarDefencing != null) {
            setCarPicture(getCarPictureDefencing());
            save();
            return BitmapFactory.decodeByteArray(imageByteArrayCarDefencing, 0, imageByteArrayCarDefencing.length);
        } else if (imageByteArrayCarRepairing != null) {
            setCarPicture(getCarPictureRepairing());
            save();
            return BitmapFactory.decodeByteArray(imageByteArrayCarRepairing, 0, imageByteArrayCarRepairing.length);
        } else {
            return null;
        }

    }

    public void setCarPicture(Bitmap picture) {
        if (picture != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.JPEG, 85, stream);
            imageByteArrayCar = stream.toByteArray();
        }

    }

    public Bitmap getCarPictureDefencing() {

        if (imageByteArrayCarDefencing != null) {
            return BitmapFactory.decodeByteArray(imageByteArrayCarDefencing, 0, imageByteArrayCarDefencing.length);
        } else {
            return getCarPicture();
        }

    }

    public void setCarPictureDefencing(Bitmap picture) {
        if (picture != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.JPEG, 85, stream);
            imageByteArrayCarDefencing = stream.toByteArray();
        }

    }

    public Bitmap getCarPictureRepairing() {

        if (imageByteArrayCarRepairing != null) {
            return BitmapFactory.decodeByteArray(imageByteArrayCarRepairing, 0, imageByteArrayCarRepairing.length);
        } else {
            return getCarPicture();
        }

    }

    public void setCarPictureRepairing(Bitmap picture) {
        if (picture != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.JPEG, 85, stream);
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
            picture.compress(Bitmap.CompressFormat.JPEG, 85, stream);
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
            picture.compress(Bitmap.CompressFormat.JPEG, 85, stream);
            imageByteArrayTask = stream.toByteArray();
        }

    }
    
    public Car(String name, int slot, int health, int shield, byte[] imageByteArrayCar, String userUID) {
        this.name = name;
        this.slot = slot;
        this.health = health;
        this.shield = shield;
        this.imageByteArrayCar = imageByteArrayCar;
        this.userUID = userUID;

    }

    public static Car getDefaultCar(int slot) {

        String userUID = null;
        if (GameActivity.fbUser != null) {
            userUID = GameActivity.fbUser.getUid();
        }

        Bitmap carPicture = BitmapFactory.decodeFile( pathToCATScalcFolder + "/stub_car" + slot + ".jpg");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        carPicture.compress(Bitmap.CompressFormat.JPEG, 85, stream);
        byte[] imageByteArrayBuilding = stream.toByteArray();
        return new Car("Car #" + slot, slot, 0, 0, imageByteArrayBuilding, userUID);

    }

    public boolean save() {
        File file = new File(pathToFile + "_car" + this.slot);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(this);
            oos.close();

            if (GameActivity.fbUser != null) {
                if (GameActivity.fbUser.isEmailVerified()) {
                    final String userUID = GameActivity.fbUser.getUid();
                    CollectionReference userCars = GameActivity.fbDb.collection("users").document(userUID).collection("userCars");
                    String docRefCarName = "car" + this.slot;
                    DocumentReference docRefCar = userCars.document(docRefCarName);
                    docRefCar.set(getMap());
                }
            }

            return true;
        } catch (IOException e) {
            Log.e("Car", "save car#" + this.slot + ". Ошибка сериализации");
            return false;
        }
    }

    public boolean save(String userUID) {
        File file = new File(pathToFile + "_car" + slot + "_" + userUID);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(this);
            oos.close();

            return true;
        } catch (IOException e) {
            Log.e("Car", "save car#" + this.slot + " userUID=" + userUID + ". Ошибка сериализации");
            return false;
        }
    }

    public static Car loadCar(int slot) {
        Car car;
        File file = new File(pathToFile + "_car" + slot);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            car = (Car) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            Log.e("Car", "loadCar #" + slot + ". Ошибка десериализации. Возвращаем car по-умолчанию.");
            e.printStackTrace();
            car = getDefaultCar(slot);
            car.save();
        }
        return car;
    }

    public static Car loadCar(int slot, String userUID) {
        Car car;
        File file = new File(pathToFile + "_car" + slot + "_" + userUID);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            car = (Car) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            Log.e("Car", "loadCar #" + slot + ". Ошибка десериализации. Возвращаем car по-умолчанию.");
            e.printStackTrace();
            car = getDefaultCar(slot);
            car.setUserUID(userUID);
            car.save(userUID);
        }
        return car;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("timestamp", FieldValue.serverTimestamp());
        map.put("userUID", userUID);
        map.put("userNIC", userNIC);
        map.put("teamID", teamID);
        map.put("carName", name);
        map.put("carSlot", slot);
        map.put("carHealth", health);
        map.put("carShield", shield);
        map.put("carRepair", repair);
        map.put("carBuilding", building);
        map.put("carBuildingTask", buildingTask);

        return map;
    }


    public byte[] getImageByteArrayCar() {
        return imageByteArrayCar;
    }

    public void setImageByteArrayCar(byte[] imageByteArrayCar) {
        this.imageByteArrayCar = imageByteArrayCar;
    }

    public byte[] getImageByteArrayCarDefencing() {
        return imageByteArrayCarDefencing;
    }

    public void setImageByteArrayCarDefencing(byte[] imageByteArrayCarDefencing) {
        this.imageByteArrayCarDefencing = imageByteArrayCarDefencing;
    }

    public byte[] getImageByteArrayCarRepairing() {
        return imageByteArrayCarRepairing;
    }

    public void setImageByteArrayCarRepairing(byte[] imageByteArrayCarRepairing) {
        this.imageByteArrayCarRepairing = imageByteArrayCarRepairing;
    }

    public byte[] getImageByteArrayBuilding() {
        return imageByteArrayBuilding;
    }

    public void setImageByteArrayBuilding(byte[] imageByteArrayBuilding) {
        this.imageByteArrayBuilding = imageByteArrayBuilding;
    }

    public byte[] getImageByteArrayTask() {
        return imageByteArrayTask;
    }

    public void setImageByteArrayTask(byte[] imageByteArrayTask) {
        this.imageByteArrayTask = imageByteArrayTask;
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
//        return Utils.convertSecondsToHHMMSS(getSecondsToEndRepairing());
        return Utils.convertMinutesToHHMM((int)(getSecondsToEndRepairing()/60));
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

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUserNIC() {
        return userNIC;
    }

    public void setUserNIC(String userNIC) {
        this.userNIC = userNIC;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
