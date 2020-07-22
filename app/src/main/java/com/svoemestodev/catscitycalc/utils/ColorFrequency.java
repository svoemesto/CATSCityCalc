package com.svoemestodev.catscitycalc.utils;

import com.svoemestodev.catscitycalc.ssa.SSA_Color;
import com.svoemestodev.catscitycalc.ssa.SSA_Colors;

public class ColorFrequency {
    private float frequency;
    private int color;
    private SSA_Color ssaColor;

    public ColorFrequency(float frequency, int color) {
        this.frequency = frequency;
        this.color = color;
        this.ssaColor = SSA_Colors.getColor(color);
    }

    public ColorFrequency() {
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public SSA_Color getSsaColor() {
        return ssaColor;
    }

    public void setSsaColor(SSA_Color ssaColor) {
        this.ssaColor = ssaColor;
    }
}
