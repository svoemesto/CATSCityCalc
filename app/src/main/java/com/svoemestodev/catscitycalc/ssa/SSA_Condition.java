package com.svoemestodev.catscitycalc.ssa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SSA_Condition implements Serializable, Comparable<SSA_Condition> {

    private static final long serialVersionUID = 3L;

    private String key;
    private String name;
    private SSA_Color ssaColor = SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey());
    private int threshold = 10;
    private float minFrequency = 0.001f;
    private float maxFrequency = 1.000f;

    public SSA_Condition() {
        if (SSA_Conditions.ssaConditions != null) {
            this.key = "NEW_CONDITION_" + (SSA_Conditions.getConditionsList().size()+1);
        }
    }

    public SSA_Condition(String key) {
        this.key = key;
    }

    public SSA_Condition getClone() {
        SSA_Condition clone = new SSA_Condition();
        clone.setKey(this.getKey());
        clone.setName(this.getName());
        clone.setSsaColor(this.getSsaColor().getClone());
        clone.setThreshold(this.getThreshold());
        clone.setMinFrequency(this.getMinFrequency());
        clone.setMaxFrequency(this.getMaxFrequency());
        return clone;
    }

    @Override
    public int compareTo(SSA_Condition o) {
        if (o == null) {
            return 1;
        } else if (o.getKey() == null) {
            return 1;
        } else {
            String a = this.getKey();
            String b = o.getKey();
            return a.compareTo(b);
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public float getMinFrequency() {
        return minFrequency;
    }

    public void setMinFrequency(float minFrequency) {
        this.minFrequency = minFrequency;
    }

    public float getMaxFrequency() {
        return maxFrequency;
    }

    public void setMaxFrequency(float maxFrequency) {
        this.maxFrequency = maxFrequency;
    }

}
