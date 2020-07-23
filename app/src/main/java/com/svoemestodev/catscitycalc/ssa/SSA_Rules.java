package com.svoemestodev.catscitycalc.ssa;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SSA_Rules implements Serializable {

    private static final long serialVersionUID = 12L;

    transient public static String pathToFile;
    transient public static SSA_Rules ssaRules = new SSA_Rules();

    private Map<String, SSA_Rule> map = new HashMap<>();

    public SSA_Rules() {
    }

    /****************************************
     * Возврещает list из ssaRules.map *
     * @return - List<SSA_Rule>        *
     ****************************************/
    public static List<SSA_Rule> getRulesList() {
        load();
        List<SSA_Rule> list = new ArrayList<>(ssaRules.map.values());
        Collections.sort(list);
        return list;
    }


    /*******************************************************************
     * Удаляет ssaRule из ssaRules.map, сохраняет изменения  *
     * @param ssaRule                                             *
     *******************************************************************/
    public static void delRule(SSA_Rule ssaRule) {
        if (ssaRules != null && ssaRule != null) {
            if (ssaRules.map.containsKey(ssaRule.getKey())) {
                ssaRules.map.remove(ssaRule.getKey());
                save();
            }
        }
    }

    /*****************************************************************************
     * Добавляет/изменяет ssaRule в ssaRules.map, сохраняет изменения  *
     * @param ssaRule                                                       *
     *****************************************************************************/
    public static void putRule(SSA_Rule ssaRule) {
        if (ssaRules != null && ssaRule != null) {
            ssaRules.map.put(ssaRule.getKey(), ssaRule);
            save();
        }
    }

    /*************************************************************
     *  Возвращает SSA_Rule из ssaRules.map по ключу   *
     * @param key - ключ                                         *
     * @return - SSA_Rule если ключ найден, иначе null      *
     *************************************************************/
    public static SSA_Rule getRule(String key) {
        if (ssaRules != null && key != null) {
            if (ssaRules.map.containsKey(key)) {
                return ssaRules.map.get(key);
            }
        }
        return null;
    }

    /***********************************************
     *  Десериализация ssaRules               *
     * @return true/false в зависимости от успеха  *
     ***********************************************/
    public static boolean load() {

        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            ssaRules = (SSA_Rules) ois.readObject();
            ois.close();
            return true;
        } catch (ClassNotFoundException | IOException e) {
            Log.e("SSA_Rules", "load. Ошибка десериализации. Берем список по-умолчанию.");
            ssaRules = SSA_Utils.getDefaultRules();
            save();
            return true;
        }

    }

    /***********************************************
     *  Сериализация ssaRules                 *
     * @return true/false в зависимости от успеха  *
     ***********************************************/
    public static boolean save() {

        File file = new File(pathToFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(ssaRules);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("SSA_Rules", "save. Ошибка сериализации");
        }
        return false;
    }

    /***********************
     *  GETTERS & SETTERS  *
     ***********************/

    public Map<String, SSA_Rule> getMap() {
        return map;
    }

    public void setMap(Map<String, SSA_Rule> map) {
        this.map = map;
    }

}
