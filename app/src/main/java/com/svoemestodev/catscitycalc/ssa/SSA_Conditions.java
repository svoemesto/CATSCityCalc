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

public class SSA_Conditions implements Serializable {

    private static final long serialVersionUID = 4L;

    transient public static String pathToFile;
    transient public static SSA_Conditions ssaConditions = new SSA_Conditions();

    private Map<String, SSA_Condition> map = new HashMap<>();

    public SSA_Conditions() {
    }

    /****************************************
     * Возврещает list из ssaConditions.map *
     * @return - List<SSA_Condition>        *
     ****************************************/
    public static List<SSA_Condition> getConditionsList() {
        load();
        List<SSA_Condition> list = new ArrayList<>(ssaConditions.map.values());
        Collections.sort(list);
        return list;
    }


    /*******************************************************************
     * Удаляет ssaCondition из ssaConditions.map, сохраняет изменения  *
     * @param ssaCondition                                             *
     *******************************************************************/
    public static void delCondition(SSA_Condition ssaCondition) {
        if (ssaConditions != null && ssaCondition != null) {
            if (ssaConditions.map.containsKey(ssaCondition.getKey())) {
                ssaConditions.map.remove(ssaCondition.getKey());
                save();
            }
        }
    }

    /*****************************************************************************
     * Добавляет/изменяет ssaCondition в ssaConditions.map, сохраняет изменения  *
     * @param ssaCondition                                                       *
     *****************************************************************************/
    public static void putCondition(SSA_Condition ssaCondition) {
        if (ssaConditions != null && ssaCondition != null) {
            ssaConditions.map.put(ssaCondition.getKey(), ssaCondition);
            save();
        }
    }

    /*************************************************************
     *  Возвращает SSA_Condition из ssaConditions.map по ключу   *
     * @param key - ключ                                         *
     * @return - SSA_Condition если ключ найден, иначе null      *
     *************************************************************/
    public static SSA_Condition getCondition(String key) {
        if (ssaConditions != null && key != null) {
            if (ssaConditions.map.containsKey(key)) {
                return ssaConditions.map.get(key);
            }
        }
        return null;
    }

    /***********************************************
     *  Десериализация ssaConditions               *
     * @return true/false в зависимости от успеха  *
     ***********************************************/
    public static boolean load() {

        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            ssaConditions = (SSA_Conditions) ois.readObject();
            ois.close();
            return true;
        } catch (ClassNotFoundException | IOException e) {
            Log.e("SSA_Conditions", "load. Ошибка десериализации. Берем список по-умолчанию.");
            ssaConditions = SSA_Utils.getDefaultConditions();
            save();
            return true;
        }

    }

    /***********************************************
     *  Сериализация ssaConditions                 *
     * @return true/false в зависимости от успеха  *
     ***********************************************/
    public static boolean save() {

        File file = new File(pathToFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(ssaConditions);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("SSA_Conditions", "save. Ошибка сериализации");
        }
        return false;
    }

    /***********************
     *  GETTERS & SETTERS  *
     ***********************/

    public Map<String, SSA_Condition> getMap() {
        return map;
    }

    public void setMap(Map<String, SSA_Condition> map) {
        this.map = map;
    }

}
