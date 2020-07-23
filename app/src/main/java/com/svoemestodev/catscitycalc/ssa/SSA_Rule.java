package com.svoemestodev.catscitycalc.ssa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SSA_Rule implements Serializable, Comparable<SSA_Rule> {

    private static final long serialVersionUID = 11L;

    private String key;
    private String name;
    private List<SSA_Rule_Area_Condition> listTrue = new ArrayList<>();
    private List<SSA_Rule_Area_Condition> listFalse = new ArrayList<>();
    private List<SSA_Rule_Area_Condition> listOneTrue = new ArrayList<>();
    private List<SSA_Rule_Area_Condition> listAnyTrue = new ArrayList<>();

    public SSA_Rule() {
        if (SSA_Rules.ssaRules != null) {
            this.key = "NEW_RULE_" + (SSA_Rules.getRulesList().size()+1);
        }
    }

    public SSA_Rule(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public SSA_Rule(String key) {
        this.key = key;
    }

    public SSA_Rule getClone() {
        SSA_Rule clone = new SSA_Rule();
        clone.setKey(this.getKey());
        clone.setName(this.getName());
        
        List<SSA_Rule_Area_Condition> listTrue = new ArrayList<>();
        for (SSA_Rule_Area_Condition ssaRuleAreaCondition : this.getListTrue()) {
            listTrue.add(ssaRuleAreaCondition.getClone());
        }
        clone.setListTrue(listTrue);

        List<SSA_Rule_Area_Condition> listFalse = new ArrayList<>();
        for (SSA_Rule_Area_Condition ssaRuleAreaCondition : this.getListFalse()) {
            listFalse.add(ssaRuleAreaCondition.getClone());
        }
        clone.setListFalse(listFalse);

        List<SSA_Rule_Area_Condition> listOneTrue = new ArrayList<>();
        for (SSA_Rule_Area_Condition ssaRuleAreaCondition : this.getListOneTrue()) {
            listOneTrue.add(ssaRuleAreaCondition.getClone());
        }
        clone.setListOneTrue(listOneTrue);

        List<SSA_Rule_Area_Condition> listAnyTrue = new ArrayList<>();
        for (SSA_Rule_Area_Condition ssaRuleAreaCondition : this.getListAnyTrue()) {
            listAnyTrue.add(ssaRuleAreaCondition.getClone());
        }
        clone.setListAnyTrue(listAnyTrue);
        
        return clone;
    }

    @Override
    public int compareTo(SSA_Rule o) {
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

    public List<SSA_Rule_Area_Condition> getListTrue() {
        return listTrue;
    }

    public void setListTrue(List<SSA_Rule_Area_Condition> listTrue) {
        this.listTrue = listTrue;
    }

    public List<SSA_Rule_Area_Condition> getListFalse() {
        return listFalse;
    }

    public void setListFalse(List<SSA_Rule_Area_Condition> listFalse) {
        this.listFalse = listFalse;
    }

    public List<SSA_Rule_Area_Condition> getListOneTrue() {
        return listOneTrue;
    }

    public void setListOneTrue(List<SSA_Rule_Area_Condition> listOneTrue) {
        this.listOneTrue = listOneTrue;
    }

    public List<SSA_Rule_Area_Condition> getListAnyTrue() {
        return listAnyTrue;
    }

    public void setListAnyTrue(List<SSA_Rule_Area_Condition> listAnyTrue) {
        this.listAnyTrue = listAnyTrue;
    }
}
