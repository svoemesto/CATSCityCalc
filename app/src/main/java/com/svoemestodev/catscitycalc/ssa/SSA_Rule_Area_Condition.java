package com.svoemestodev.catscitycalc.ssa;

import java.io.Serializable;

public class SSA_Rule_Area_Condition implements Serializable, Comparable<SSA_Rule_Area_Condition> {

    private static final long serialVersionUID = 9L;

    private String key;
    private String name;
    private SSA_Area ssaArea;
    private SSA_Condition ssaCondition;

    public SSA_Rule_Area_Condition() {
        if (SSA_Rules_Area_Condition.ssaRulesAreaCondition != null) {
            this.key = "NEW_RAC_" + (SSA_Rules_Area_Condition.getRulesAreaConditionList().size()+1);
        }
    }

    public SSA_Rule_Area_Condition(String key, String name, SSA_Area ssaArea, SSA_Condition ssaCondition) {
        this.key = key;
        this.name = name;
        this.ssaArea = ssaArea;
        this.ssaCondition = ssaCondition;
    }

    public SSA_Rule_Area_Condition(String key) {
        this.key = key;
    }

    public SSA_Rule_Area_Condition getClone() {
        SSA_Rule_Area_Condition clone = new SSA_Rule_Area_Condition();
        clone.setKey(this.getKey());
        clone.setName(this.getName());
        clone.setSsaArea(this.getSsaArea().getClone());
        clone.setSsaCondition(this.getSsaCondition().getClone());
        return clone;
    }

    @Override
    public int compareTo(SSA_Rule_Area_Condition o) {
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

    public SSA_Area getSsaArea() {
        return ssaArea;
    }

    public void setSsaArea(SSA_Area ssaArea) {
        this.ssaArea = ssaArea;
    }

    public SSA_Condition getSsaCondition() {
        return ssaCondition;
    }

    public void setSsaCondition(SSA_Condition ssaCondition) {
        this.ssaCondition = ssaCondition;
    }
}
