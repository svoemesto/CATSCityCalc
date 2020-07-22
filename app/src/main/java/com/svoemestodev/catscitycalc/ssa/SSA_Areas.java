package com.svoemestodev.catscitycalc.ssa;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.File;
import java.util.List;
import java.util.Map;

public class SSA_Areas implements Serializable {

    private static final long serialVersionUID = 2L;

    transient public static String pathToFile;
    transient public static SSA_Areas ssaAreas = new SSA_Areas();

    private Map<String, SSA_Area> map = new HashMap<>();

    public SSA_Areas() {
    }

    /***********************************
     * Возврещает list из ssaAreas.map *
     * @return - List<SSA_Area>        *
     ***********************************/
    public static List<SSA_Area> getAreasList() {
        load();
        List<SSA_Area> list = new ArrayList<>(ssaAreas.map.values());
        Collections.sort(list);
        return list;
    }


    /*********************************************************
     * Удаляет ssaArea из ssaAreas.map, сохраняет изменения  *
     * @param ssaArea                                        *
     *********************************************************/
    public static void delArea(SSA_Area ssaArea) {
        if (ssaAreas != null && ssaArea != null) {
            if (ssaAreas.map.containsKey(ssaArea.getKey())) {
                ssaAreas.map.remove(ssaArea.getKey());
                save();
            }
        }
    }

    /*******************************************************************
     * Добавляет/изменяет ssaArea в ssaAreas.map, сохраняет изменения  *
     * @param ssaArea                                                  *
     *******************************************************************/
    public static void putArea(SSA_Area ssaArea) {
        if (ssaAreas != null && ssaArea != null) {
            ssaAreas.map.put(ssaArea.getKey(), ssaArea);
            save();
        }
    }

    /***************************************************
     *  Возвращает SSA_Area из ssaAreas.map по ключу   *
     * @param key - ключ                               *
     * @return - SSA_Area если ключ найден, иначе null *
     ***************************************************/
    public static SSA_Area getArea(String key) {
        if (ssaAreas != null && key != null) {
            if (ssaAreas.map.containsKey(key)) {
                return ssaAreas.map.get(key);
            }
        }
        return null;
    }

    /***********************************************
     *  Десериализация ssaAreas                    *
     * @return true/false в зависимости от успеха  *
     ***********************************************/
    public static boolean load() {

        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            ssaAreas = (SSA_Areas) ois.readObject();
            ois.close();
            return true;
        } catch (ClassNotFoundException | IOException e) {
            Log.e("SSA_Areas", "load. Ошибка десериализации. Берем список по-умолчанию.");
            ssaAreas = SSA_Utils.getDefaultAreas();
            save();
            return true;
        }

    }

    /***********************************************
     *  Сериализация ssaAreas                      *
     * @return true/false в зависимости от успеха  *
     ***********************************************/
    public static boolean save() {

        File file = new File(pathToFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(ssaAreas);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("SSA_Areas", "save. Ошибка сериализации");
        }
        return false;
    }

    /***********************
     *  GETTERS & SETTERS  *
     ***********************/

    public Map<String, SSA_Area> getMap() {
        return map;
    }

    public void setMap(Map<String, SSA_Area> map) {
        this.map = map;
    }
}
