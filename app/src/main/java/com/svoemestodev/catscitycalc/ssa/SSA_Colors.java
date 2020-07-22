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

public class SSA_Colors implements Serializable {

    private static final long serialVersionUID = 6L;

    transient public static String pathToFile;
    transient public static SSA_Colors ssaColors = new SSA_Colors();

    private Map<String, SSA_Color> map = new HashMap<>();

    public SSA_Colors() {
    }

    /************************************
     * Возврещает list из ssaColors.map *
     * @return - List<SSA_Color>        *
     ************************************/
    public static List<SSA_Color> getColorsList() {
        load();
        List<SSA_Color> list = new ArrayList<>(ssaColors.map.values());
        Collections.sort(list);
        return list;
    }


    /***********************************************************
     * Удаляет ssaColor из ssaColors.map, сохраняет изменения  *
     * @param ssaColor                                         *
     ***********************************************************/
    public static void delColor(SSA_Color ssaColor) {
        if (ssaColors != null && ssaColor != null) {
            if (ssaColors.map.containsKey(ssaColor.getKey())) {
                ssaColors.map.remove(ssaColor.getKey());
                save();
            }
        }
    }

    /*********************************************************************
     * Добавляет/изменяет ssaColor в ssaColors.map, сохраняет изменения  *
     * @param ssaColor                                                   *
     *********************************************************************/
    public static void putColor(SSA_Color ssaColor) {
        if (ssaColors != null && ssaColor != null) {
            ssaColors.map.put(ssaColor.getKey(), ssaColor);
            save();
        }
    }

    /*****************************************************
     *  Возвращает SSA_Color из ssaColors.map по ключу   *
     * @param key - ключ                                 *
     * @return - SSA_Color если ключ найден, иначе null  *
     *****************************************************/
    public static SSA_Color getColor(String key) {
        if (ssaColors != null && key != null) {
            if (ssaColors.map.containsKey(key)) {
                return ssaColors.map.get(key);
            }
        }
        return null;
    }

    /*****************************************************
     *  Возвращает SSA_Color из ssaColors.map по цвету   *
     * @param color - цвет                               *
     * @return - SSA_Color если цвет найден, иначе null  *
     *****************************************************/
    public static SSA_Color getColor(int color) {
        if (ssaColors != null) {
            List<SSA_Color> list = getColorsList();
            for (SSA_Color ssaColor: list) {
                if (ssaColor.getColor() == color) {
                    return ssaColor;
                }
            }
        }
        return null;
    }

    /***********************************************
     *  Возвращает key из ssaColors.map по цвету   *
     * @param color - цвет                         *
     * @return - key если цвет найден, иначе ""    *
     ***********************************************/
    public static String getColorKey(int color) {
        SSA_Color ssaColor = getColor(color);
        return ssaColor == null ? "" : ssaColor.getKey();
    }

    /***********************************************
     *  Десериализация ssaColors                   *
     * @return true/false в зависимости от успеха  *
     ***********************************************/
    public static boolean load() {

        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            ssaColors = (SSA_Colors) ois.readObject();
            ois.close();
            return true;
        } catch (ClassNotFoundException | IOException e) {
            Log.e("SSA_Colors", "load. Ошибка десериализации. Берем список по-умолчанию.");
            ssaColors = SSA_Utils.getDefaultColors();
            save();
            return true;
        }

    }

    /***********************************************
     *  Сериализация ssaColors                     *
     * @return true/false в зависимости от успеха  *
     ***********************************************/
    public static boolean save() {

        File file = new File(pathToFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(ssaColors);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("SSA_Colors", "save. Ошибка сериализации");
        }
        return false;
    }

    /***********************
     *  GETTERS & SETTERS  *
     ***********************/

    public Map<String, SSA_Color> getMap() {
        return map;
    }

    public void setMap(Map<String, SSA_Color> map) {
        this.map = map;
    }
    
}
