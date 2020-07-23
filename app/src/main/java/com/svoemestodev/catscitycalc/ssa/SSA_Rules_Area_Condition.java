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

public class SSA_Rules_Area_Condition implements Serializable {

    private static final long serialVersionUID = 10L;

    transient public static String pathToFile;
    transient public static SSA_Rules_Area_Condition ssaRulesAreaCondition = new SSA_Rules_Area_Condition();

    private Map<String, SSA_Rule_Area_Condition> map = new HashMap<>();

    public SSA_Rules_Area_Condition() {
    }

    /****************************************
     * Возврещает list из ssaConditions.map *
     * @return - List<SSA_Condition>        *
     ****************************************/
    public static List<SSA_Rule_Area_Condition> getRulesAreaConditionList() {
        load();
        List<SSA_Rule_Area_Condition> list = new ArrayList<>(ssaRulesAreaCondition.map.values());
        Collections.sort(list);
        return list;
    }


    /*******************************************************************
     * Удаляет ssaCondition из ssaConditions.map, сохраняет изменения  *
     * @param ssaRuleAreaCondition                                             *
     *******************************************************************/
    public static void delRuleAreaCondition(SSA_Rule_Area_Condition ssaRuleAreaCondition) {
        if (ssaRulesAreaCondition != null && ssaRuleAreaCondition != null) {
            if (ssaRulesAreaCondition.map.containsKey(ssaRuleAreaCondition.getKey())) {
                ssaRulesAreaCondition.map.remove(ssaRuleAreaCondition.getKey());
                save();
            }
        }
    }

    /*****************************************************************************
     * Добавляет/изменяет ssaCondition в ssaConditions.map, сохраняет изменения  *
     * @param ssaRuleAreaCondition                                                       *
     *****************************************************************************/
    public static void putRuleAreaCondition(SSA_Rule_Area_Condition ssaRuleAreaCondition) {
        if (ssaRulesAreaCondition != null && ssaRuleAreaCondition != null) {
            ssaRulesAreaCondition.map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);
            save();
        }
    }

    /*************************************************************
     *  Возвращает SSA_Condition из ssaConditions.map по ключу   *
     * @param key - ключ                                         *
     * @return - SSA_Condition если ключ найден, иначе null      *
     *************************************************************/
    public static SSA_Rule_Area_Condition getRuleAreaCondition(String key) {
        if (ssaRulesAreaCondition != null && key != null) {
            if (ssaRulesAreaCondition.map.containsKey(key)) {
                return ssaRulesAreaCondition.map.get(key);
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
            ssaRulesAreaCondition = (SSA_Rules_Area_Condition) ois.readObject();
            ois.close();
            return true;
        } catch (ClassNotFoundException | IOException e) {
            Log.e("SSA_RAC", "load. Ошибка десериализации. Берем список по-умолчанию.");
            ssaRulesAreaCondition = SSA_Utils.getDefaultRulesAreasConditions();
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
            oos.writeObject(ssaRulesAreaCondition);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("SSA_RAC", "save. Ошибка сериализации");
        }
        return false;
    }

    /***********************
     *  GETTERS & SETTERS  *
     ***********************/

    public Map<String, SSA_Rule_Area_Condition> getMap() {
        return map;
    }

    public void setMap(Map<String, SSA_Rule_Area_Condition> map) {
        this.map = map;
    }

}
