package com.svoemestodev.catscitycalc.ssa;

import java.io.Serializable;

public class SSA_Condition implements Serializable, Comparable<SSA_Condition> {

    private static final long serialVersionUID = 3L;

    private String key;
    private String name;
    private SSA_Color ssaColor;
    private int threshold;
    private double minFrequency;
    private double maxFrequency;

    public SSA_Condition() {
    }

    public SSA_Condition(String key) {
        this.key = key;
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

    public double getMinFrequency() {
        return minFrequency;
    }

    public void setMinFrequency(double minFrequency) {
        this.minFrequency = minFrequency;
    }

    public double getMaxFrequency() {
        return maxFrequency;
    }

    public void setMaxFrequency(double maxFrequency) {
        this.maxFrequency = maxFrequency;
    }

}
