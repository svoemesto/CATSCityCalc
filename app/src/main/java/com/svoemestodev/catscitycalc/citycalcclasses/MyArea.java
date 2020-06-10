package com.svoemestodev.catscitycalc.citycalcclasses;

import com.svoemestodev.catscitycalc.citycalcclasses.Area;

public class MyArea {

    private String key;
    private Area area;
    private String prefX1;
    private String prefX2;
    private String prefY1;
    private String prefY2;
    private String defX1;
    private String defX2;
    private String defY1;
    private String defY2;
    private String prefMainColor;
    private String prefBackColor;
    private String prefTHM;
    private String prefTHP;
    private String defMainColor;
    private String defBackColor;
    private String defTHM;
    private String defTHP;

    public MyArea(String key, Area area, String prefX1, String prefX2, String prefY1, String prefY2, String defX1, String defX2, String defY1, String defY2, String prefMainColor, String prefBackColor, String prefTHM, String prefTHP, String defMainColor, String defBackColor, String defTHM, String defTHP) {
        this.key = key;
        this.area = area;
        this.prefX1 = prefX1;
        this.prefX2 = prefX2;
        this.prefY1 = prefY1;
        this.prefY2 = prefY2;
        this.defX1 = defX1;
        this.defX2 = defX2;
        this.defY1 = defY1;
        this.defY2 = defY2;
        this.prefMainColor = prefMainColor;
        this.prefBackColor = prefBackColor;
        this.prefTHM = prefTHM;
        this.prefTHP = prefTHP;
        this.defMainColor = defMainColor;
        this.defBackColor = defBackColor;
        this.defTHM = defTHM;
        this.defTHP = defTHP;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getPrefX1() {
        return prefX1;
    }

    public void setPrefX1(String prefX1) {
        this.prefX1 = prefX1;
    }

    public String getPrefX2() {
        return prefX2;
    }

    public void setPrefX2(String prefX2) {
        this.prefX2 = prefX2;
    }

    public String getPrefY1() {
        return prefY1;
    }

    public void setPrefY1(String prefY1) {
        this.prefY1 = prefY1;
    }

    public String getPrefY2() {
        return prefY2;
    }

    public void setPrefY2(String prefY2) {
        this.prefY2 = prefY2;
    }

    public String getDefX1() {
        return defX1;
    }

    public void setDefX1(String defX1) {
        this.defX1 = defX1;
    }

    public String getDefX2() {
        return defX2;
    }

    public void setDefX2(String defX2) {
        this.defX2 = defX2;
    }

    public String getDefY1() {
        return defY1;
    }

    public void setDefY1(String defY1) {
        this.defY1 = defY1;
    }

    public String getDefY2() {
        return defY2;
    }

    public void setDefY2(String defY2) {
        this.defY2 = defY2;
    }

    public String getPrefMainColor() {
        return prefMainColor;
    }

    public void setPrefMainColor(String prefMainColor) {
        this.prefMainColor = prefMainColor;
    }

    public String getPrefBackColor() {
        return prefBackColor;
    }

    public void setPrefBackColor(String prefBackColor) {
        this.prefBackColor = prefBackColor;
    }

    public String getPrefTHM() {
        return prefTHM;
    }

    public void setPrefTHM(String prefTHM) {
        this.prefTHM = prefTHM;
    }

    public String getPrefTHP() {
        return prefTHP;
    }

    public void setPrefTHP(String prefTHP) {
        this.prefTHP = prefTHP;
    }

    public String getDefMainColor() {
        return defMainColor;
    }

    public void setDefMainColor(String defMainColor) {
        this.defMainColor = defMainColor;
    }

    public String getDefBackColor() {
        return defBackColor;
    }

    public void setDefBackColor(String defBackColor) {
        this.defBackColor = defBackColor;
    }

    public String getDefTHM() {
        return defTHM;
    }

    public void setDefTHM(String defTHM) {
        this.defTHM = defTHM;
    }

    public String getDefTHP() {
        return defTHP;
    }

    public void setDefTHP(String defTHP) {
        this.defTHP = defTHP;
    }
}
