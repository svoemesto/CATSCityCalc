package com.svoemestodev.catscitycalc.classes;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarList implements Serializable {
    private List<Car> listCars = new ArrayList<>();

    public static List<Car> loadListFromFile(String pathToFile) {
        CarList carList = new CarList();
        List<Car> list;
        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            carList = (CarList) ois.readObject();
            ois.close();
            list = carList.getListCars();
            return list;
        } catch (ClassNotFoundException | IOException e) {
            Log.e("Car", "loadListFromFile. Ошибка десериализации.");
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveList(List<Car> list, String userUID, String fileName) {
//        File file = new File(Car.pathToFile + "_" + userUID);
        File file = new File(fileName);
        try {
            CarList carList = new CarList();
            carList.setListCars(list);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(carList);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("Car", "saveList. Ошибка сериализации");
            return false;
        }
    }

    public List<Car> getListCars() {
        return listCars;
    }

    public void setListCars(List<Car> listCars) {
        this.listCars = listCars;
    }
}
