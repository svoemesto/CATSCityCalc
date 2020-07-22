package com.svoemestodev.catscitycalc.ssa;

import java.io.Serializable;
import java.util.UUID;

public class SSA_RBT_Condition implements Serializable {

    private static final long serialVersionUID = 8L;

    private String key = UUID.randomUUID().toString();
    private String areaKey;
    private int type = 0; // 0 - resize, 1 - black/white, 2 - white/black, 3 - transparent
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private SSA_Color ssaColor = SSA_Colors.getColor("WHITE");
    private int threshold = 10;
    private boolean skip = false;

    public SSA_RBT_Condition() {
    }

    public SSA_RBT_Condition(String areaKey, int type, SSA_Color ssaColor, int threshold, float scaleX, float scaleY) {
        this.areaKey = areaKey;
        this.type = type;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.ssaColor = ssaColor;
        this.threshold = threshold;
    }

    public SSA_RBT_Condition(String areaKey) {
        this.areaKey = areaKey;
    }

    public SSA_RBT_Condition getClone() {
        SSA_RBT_Condition clone = new SSA_RBT_Condition();
        clone.setAreaKey(this.getAreaKey());
        clone.setType(this.getType());
        clone.setScaleX(this.getScaleX());
        clone.setScaleY(this.getScaleY());
        clone.setSsaColor(this.getSsaColor().getClone());
        clone.setThreshold(this.getThreshold());
        clone.setSkip(this.isSkip());
        return clone;
    }

    /***********************
     *  GETTERS & SETTERS  *
     ***********************/

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAreaKey() {
        return areaKey;
    }

    public void setAreaKey(String areaKey) {
        this.areaKey = areaKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public SSA_Color getSsaColor() {
        return ssaColor;
    }

    public void setSsaColor(SSA_Color ssaColor) {
        this.ssaColor = ssaColor;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }
}
