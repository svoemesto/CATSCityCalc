package com.svoemestodev.catscitycalc.utils;

public class ColorFrequency {
    private float frequency;
    private int color;

    public ColorFrequency(float frequency, int color) {
        this.frequency = frequency;
        this.color = color;
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
}
