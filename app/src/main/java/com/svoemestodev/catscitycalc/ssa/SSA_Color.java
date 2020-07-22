package com.svoemestodev.catscitycalc.ssa;

import java.io.Serializable;

public class SSA_Color implements Serializable, Comparable<SSA_Color> {

    private static final long serialVersionUID = 5L;
    private String key = "NEW_KEY";
    private String name = "NEW_NAME";
    private int color = 0x000000;

    public SSA_Color() {
    }

    public SSA_Color(String key) {
        this.key = key;
    }

    public SSA_Color getClone() {
        SSA_Color clone = new SSA_Color();
        clone.setKey(this.getKey());
        clone.setName(this.getName());
        clone.setColor(this.getColor());
        return clone;
    }

    @Override
    public int compareTo(SSA_Color o) {
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
