package com.svoemestodev.catscitycalc.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.activities.GameActivity;
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
    private UUID uuid = UUID.randomUUID();
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
    
    public Car(String name, int slot, int health, int shield, byte[] imageByteArrayCar) {
        this.name = name;
        this.slot = slot;
        this.health = health;
        this.shield = shield;
        this.imageByteArrayCar = imageByteArrayCar;
    }

    public static List<Car> getDefaultList() {

        List<Car> list = new ArrayList<>();

        Bitmap picture1 = BitmapFactory.decodeFile( pathToCATScalcFolder + "/stub_car1.jpg");
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        picture1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] imageByteArrayBuilding1 = stream1.toByteArray();
        list.add(new Car("Car #1", 1, 0, 0, imageByteArrayBuilding1));

        Bitmap picture2 = BitmapFactory.decodeFile(pathToCATScalcFolder + "/stub_car2.jpg");
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        picture2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] imageByteArrayBuilding2 = stream2.toByteArray();
        list.add(new Car("Car #2", 2, 0, 0,imageByteArrayBuilding2));

        Bitmap picture3 = BitmapFactory.decodeFile(pathToCATScalcFolder + "/stub_car3.jpg");
        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
        picture3.compress(Bitmap.CompressFormat.PNG, 100, stream3);
        byte[] imageByteArrayBuilding3 = stream3.toByteArray();
        list.add(new Car("Car #3", 3, 0, 0, imageByteArrayBuilding3));

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
            saveList(list);
        }
        return list;
    }

    public static List<Car> loadList(String userUID) {
        List<Car> list;
        File file = new File(pathToFile + "_" + userUID);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            list = (List<Car>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            Log.e("Car", "loadList. Ошибка десериализации. Возвращаем список по-умолчанию.");
            list = getDefaultList();
            saveList(list, userUID);
        }
        return list;
    }

    public static List<Car> loadListFromFile(String pathToFile) {
        List<Car> list;
        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            list = (List<Car>) ois.readObject();
            ois.close();
            return list;
        } catch (ClassNotFoundException | IOException e) {
            Log.e("Car", "loadListFromFile. Ошибка десериализации. Возвращаем список по-умолчанию.");
        }
        return null;
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

        if (GameActivity.fbUser != null) {
            if (GameActivity.fbUser.isEmailVerified()) {
                final String userUID = GameActivity.fbUser.getUid();
                CollectionReference userCars = GameActivity.fbDb.collection("users").document(userUID).collection("userCars");
                String docRefCarName = "car" + this.slot;
                DocumentReference docRefCar = userCars.document(docRefCarName);
                docRefCar.set(getMap());
//                String pathToFileOnServer = "users/" + userUID + "/" + docRefCarName;
//                StorageReference storRefCarFree = GameActivity.fbStor.getReference().child(pathToFileOnServer + "_free");
//                StorageReference storRefCarDef = GameActivity.fbStor.getReference().child(pathToFileOnServer + "_def");
//                StorageReference storRefCarRep = GameActivity.fbStor.getReference().child(pathToFileOnServer + "_rep");
//                if (this.imageByteArrayCar != null) storRefCarFree.putBytes(this.imageByteArrayCar);
//                if (this.imageByteArrayCarDefencing != null) storRefCarDef.putBytes(this.imageByteArrayCarDefencing);
//                if (this.imageByteArrayCarRepairing != null) storRefCarRep.putBytes(this.imageByteArrayCarRepairing);
            }
        }

    }

    public void save(String userUID) {
        List<Car> list = loadList(userUID);
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
        saveList(listNew, userUID);

//        if (GameActivity.fbUser != null) {
//            if (GameActivity.fbUser.isEmailVerified()) {
//                CollectionReference userCars = GameActivity.fbDb.collection("users").document(userUID).collection("userCars");
//                String docRefCarName = "car" + this.slot;
//                DocumentReference docRefCar = userCars.document(docRefCarName);
//                docRefCar.set(getMap());
//            }
//        }

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

    public static boolean saveList(List<Car> list, String userUID) {
        File file = new File(pathToFile + "_" + userUID);
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

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("timestamp", FieldValue.serverTimestamp());
        map.put("carUID", uuid.toString());
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
}
