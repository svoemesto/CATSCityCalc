package com.svoemestodev.catscitycalc.database;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DbCar {

    private String carUID;
    private String carName;
    private int carSlot;
    private int carHealth;
    private int carShield;
    private Date carRepair;
    private int carBuilding;
    private int carBuildingTask;

    public DbCar() {
    }

    public DbCar(DocumentSnapshot documentSnapshot) {

        String key;
        Map<String, Object> map = documentSnapshot.getData();

        key = "carUID"; if (map.containsKey(key)) this.carUID = map.get(key).toString();
        key = "carName"; if (map.containsKey(key)) this.carName = map.get(key).toString();
        key = "carRepair"; if (map.containsKey(key)) {
            if (documentSnapshot.getTimestamp(key) == null) {
                this.carRepair = null;
            } else {
                this.carRepair = documentSnapshot.getTimestamp(key).toDate();
            }
        }
        key = "carSlot"; if (map.containsKey(key)) this.carSlot = ((Long) map.get(key)).intValue();
        key = "carHealth"; if (map.containsKey(key)) this.carHealth = ((Long) map.get(key)).intValue();
        key = "carShield"; if (map.containsKey(key)) this.carShield = ((Long) map.get(key)).intValue();
        key = "carBuilding"; if (map.containsKey(key)) this.carBuilding = ((Long) map.get(key)).intValue();
        key = "carBuildingTask"; if (map.containsKey(key)) this.carBuildingTask = ((Long) map.get(key)).intValue();

    }


    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("timestamp", FieldValue.serverTimestamp());
        map.put("carUID", carUID);
        map.put("carName", carName);
        map.put("carSlot", carSlot);
        map.put("carHealth", carHealth);
        map.put("carShield", carShield);
        map.put("carRepair", carRepair);
        map.put("carBuilding", carBuilding);
        map.put("carBuildingTask", carBuildingTask);

        return map;
    }

    public String getCarUID() {
        return carUID;
    }

    public void setCarUID(String carUID) {
        this.carUID = carUID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getCarSlot() {
        return carSlot;
    }

    public void setCarSlot(int carSlot) {
        this.carSlot = carSlot;
    }

    public int getCarHealth() {
        return carHealth;
    }

    public void setCarHealth(int carHealth) {
        this.carHealth = carHealth;
    }

    public int getCarShield() {
        return carShield;
    }

    public void setCarShield(int carShield) {
        this.carShield = carShield;
    }

    public Date getCarRepair() {
        return carRepair;
    }

    public void setCarRepair(Date carRepair) {
        this.carRepair = carRepair;
    }

    public int getCarBuilding() {
        return carBuilding;
    }

    public void setCarBuilding(int carBuilding) {
        this.carBuilding = carBuilding;
    }

    public int getCarBuildingTask() {
        return carBuildingTask;
    }

    public void setCarBuildingTask(int carBuildingTask) {
        this.carBuildingTask = carBuildingTask;
    }
}