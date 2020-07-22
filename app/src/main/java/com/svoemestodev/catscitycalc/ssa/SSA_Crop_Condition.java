package com.svoemestodev.catscitycalc.ssa;

import android.nfc.cardemulation.OffHostApduService;

import com.svoemestodev.catscitycalc.utils.CropPosition;
import com.svoemestodev.catscitycalc.utils.PictureProcessorDirection;

import java.io.Serializable;
import java.util.UUID;

public class SSA_Crop_Condition implements Serializable {

    private static final long serialVersionUID = 7L;

    private String key = UUID.randomUUID().toString();
    private String areaKey;
    private PictureProcessorDirection direction = PictureProcessorDirection.FROM_LEFT_TO_RIGHT;
    private SSA_Color ssaColorStart = SSA_Colors.getColor("WHITE");
    private int thresholdStart = 0;
    private float minFrequencyStart = 1.0f;
    private float maxFrequencyStart = 1.0f;
    private SSA_Color ssaColorEnd = SSA_Colors.getColor("WHITE");
    private int thresholdEnd = 0;
    private float minFrequencyEnd = 0.0f;
    private float maxFrequencyEnd = 0.9f;
    private boolean returnFirstFragment = false;
    private boolean onlyFirst = false;
    private boolean skip = false;

    public SSA_Crop_Condition() {
    }

    public SSA_Crop_Condition(String areaKey, PictureProcessorDirection direction, SSA_Color ssaColorStart, int thresholdStart, float minFrequencyStart, float maxFrequencyStart, SSA_Color ssaColorEnd, int thresholdEnd, float minFrequencyEnd, float maxFrequencyEnd, boolean returnFirstFragment, boolean onlyFirst) {
        this.areaKey = areaKey;
        this.direction = direction;
        this.ssaColorStart = ssaColorStart;
        this.thresholdStart = thresholdStart;
        this.minFrequencyStart = minFrequencyStart;
        this.maxFrequencyStart = maxFrequencyStart;
        this.ssaColorEnd = ssaColorEnd;
        this.thresholdEnd = thresholdEnd;
        this.minFrequencyEnd = minFrequencyEnd;
        this.maxFrequencyEnd = maxFrequencyEnd;
        this.returnFirstFragment = returnFirstFragment;
        this.onlyFirst = onlyFirst;
    }

    public SSA_Crop_Condition(String areaKey) {
        this.areaKey = areaKey;
    }

    public SSA_Crop_Condition getClone() {
        SSA_Crop_Condition clone = new SSA_Crop_Condition();
        clone.setAreaKey(this.getAreaKey());
        clone.setDirection(this.getDirection());
        clone.setSsaColorStart(this.getSsaColorStart().getClone());
        clone.setThresholdStart(this.getThresholdStart());
        clone.setMinFrequencyStart(this.getMinFrequencyStart());
        clone.setMaxFrequencyStart(this.getMaxFrequencyStart());
        clone.setSsaColorEnd(this.getSsaColorEnd().getClone());
        clone.setThresholdEnd(this.getThresholdEnd());
        clone.setMinFrequencyEnd(this.getMinFrequencyEnd());
        clone.setMaxFrequencyEnd(this.getMaxFrequencyEnd());
        clone.setReturnFirstFragment(this.isReturnFirstFragment());
        clone.setOnlyFirst(this.isOnlyFirst());
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

    public PictureProcessorDirection getDirection() {
        return direction;
    }

    public void setDirection(PictureProcessorDirection direction) {
        this.direction = direction;
    }

    public SSA_Color getSsaColorStart() {
        return ssaColorStart;
    }

    public void setSsaColorStart(SSA_Color ssaColorStart) {
        this.ssaColorStart = ssaColorStart;
    }

    public int getThresholdStart() {
        return thresholdStart;
    }

    public void setThresholdStart(int thresholdStart) {
        this.thresholdStart = thresholdStart;
    }

    public float getMinFrequencyStart() {
        return minFrequencyStart;
    }

    public void setMinFrequencyStart(float minFrequencyStart) {
        this.minFrequencyStart = minFrequencyStart;
    }

    public float getMaxFrequencyStart() {
        return maxFrequencyStart;
    }

    public void setMaxFrequencyStart(float maxFrequencyStart) {
        this.maxFrequencyStart = maxFrequencyStart;
    }

    public SSA_Color getSsaColorEnd() {
        return ssaColorEnd;
    }

    public void setSsaColorEnd(SSA_Color ssaColorEnd) {
        this.ssaColorEnd = ssaColorEnd;
    }

    public int getThresholdEnd() {
        return thresholdEnd;
    }

    public void setThresholdEnd(int thresholdEnd) {
        this.thresholdEnd = thresholdEnd;
    }

    public float getMinFrequencyEnd() {
        return minFrequencyEnd;
    }

    public void setMinFrequencyEnd(float minFrequencyEnd) {
        this.minFrequencyEnd = minFrequencyEnd;
    }

    public float getMaxFrequencyEnd() {
        return maxFrequencyEnd;
    }

    public void setMaxFrequencyEnd(float maxFrequencyEnd) {
        this.maxFrequencyEnd = maxFrequencyEnd;
    }

    public boolean isReturnFirstFragment() {
        return returnFirstFragment;
    }

    public void setReturnFirstFragment(boolean returnFirstFragment) {
        this.returnFirstFragment = returnFirstFragment;
    }

    public boolean isOnlyFirst() {
        return onlyFirst;
    }

    public void setOnlyFirst(boolean onlyFirst) {
        this.onlyFirst = onlyFirst;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }
}
