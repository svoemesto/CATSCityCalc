package com.svoemestodev.catscitycalc.ssa;

import android.graphics.Bitmap;
import android.util.Log;

import com.svoemestodev.catscitycalc.utils.PictureProcessor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SSA_Area implements Serializable, Comparable<SSA_Area> {

    private static final long serialVersionUID = 1L;

    private SSA_Area parentArea = null;
    private String key = "NEW_AREA";
    private String name = "New Area";
    private int snap = 0;   // -1 LEFT, 0 CENTER, +1 RIGHT
    private double rX1 = 0;
    private double rX2 = 0;
    private double rY1 = 0;
    private double rY2 = 0;
    private List<SSA_Crop_Condition> listCropConditions = new ArrayList<>();
    private List<SSA_RBT_Condition> listRBTConditions = new ArrayList<>();
    private transient Bitmap areaBitmap = null;
    private transient Bitmap areaBitmapRBT = null;
    public SSA_Area() {
        if (SSA_Areas.ssaAreas != null) {
            this.key = "NEW_AREA_" + (SSA_Areas.getAreasList().size()+1);
        }
    }

    public SSA_Area(String key) {
        this.key = key;
    }

    public SSA_Area getClone() {
        SSA_Area clone = new SSA_Area();
        clone.setParentArea(this.getParentArea() == null ? null : this.getParentArea().getClone());
        clone.setKey(this.getKey());
        clone.setName(this.getName());
        clone.setSnap(this.getSnap());
        clone.setrX1(this.getrX1());
        clone.setrX2(this.getrX2());
        clone.setrY1(this.getrY1());
        clone.setrY2(this.getrY2());
        clone.setAreaBitmap(this.getAreaBitmap());
        clone.setAreaBitmapRBT(this.getAreaBitmapRBT());
        clone.setrY2(this.getrY2());

        List<SSA_Crop_Condition> listCropConditions = new ArrayList<>();
        for (SSA_Crop_Condition ssaCropCondition : this.getListCropConditions()) {
            listCropConditions.add(ssaCropCondition.getClone());
        }
        clone.setListCropConditions(listCropConditions);

        List<SSA_RBT_Condition> listRBTConditions = new ArrayList<>();
        for (SSA_RBT_Condition ssaRbtCondition : this.getListRBTConditions()) {
            listRBTConditions.add(ssaRbtCondition.getClone());
        }
        clone.setListRBTConditions(listRBTConditions);

        return clone;
    }

    public boolean isSatisfiesCondition(Bitmap sourceBitmap, SSA_Condition ssaCondition) {
        boolean result = false;
        if (ssaCondition != null) {
            result = PictureProcessor.isConditionTrue(sourceBitmap, ssaCondition);
        }
        return result;
    }

    public boolean isSatisfiesCondition(SSA_Screenshot ssaScreenshot, SSA_Condition ssaCondition) {
        boolean result = false;
        if (ssaCondition != null) {
            Bitmap bitmap = this.getAreaBitmap(ssaScreenshot);
            result = PictureProcessor.isConditionTrue(bitmap, ssaCondition);
        }
        return result;
    }

    public Bitmap getAreaBitmap(SSA_Screenshot ssaScreenshot) {
        return getAreaBitmap(ssaScreenshot, null, false);
    }

    public Bitmap getAreaBitmap(Bitmap sourceBitmap) {
        return getAreaBitmap(new SSA_Screenshot(sourceBitmap), null, true);
    }

    public Bitmap getAreaBitmap(SSA_Screenshot ssaScreenshot, SSA_Crop_Condition ssaCropCondition) {
        return getAreaBitmap(ssaScreenshot, ssaCropCondition, false);
    }
    public Bitmap getAreaBitmap(SSA_Screenshot ssaScreenshot, SSA_Crop_Condition ssaCropCondition, boolean withoutParents) {
//        Bitmap areaBitmap = this.getAreaBitmap();
        Bitmap areaBitmap = null;

        if (areaBitmap == null) {
            SSA_Screenshot tmpScreenshot = ssaScreenshot.getClone();
            if (!withoutParents) {
                Stack<SSA_Area> ssaAreaStack = new Stack<>();
                SSA_Area ssaArea = this.getClone();
                while (ssaArea.parentArea != null) {
                    ssaAreaStack.push(ssaArea.parentArea);
                    ssaArea = ssaArea.parentArea.getClone();
                }

                while (!ssaAreaStack.isEmpty()) {
                    ssaArea = ssaAreaStack.pop();
                    areaBitmap = ssaArea.getAreaBitmap(tmpScreenshot);
                    tmpScreenshot = new SSA_Screenshot(areaBitmap);
                }
            }

            SSA_AbsoluteCoordinates ssaAC = new SSA_AbsoluteCoordinates(tmpScreenshot, this);
            int width = ssaAC.getAbsoluteX2() - ssaAC.getAbsoluteX1();
            int height = ssaAC.getAbsoluteY2() - ssaAC.getAbsoluteY1();
            if (width > 0 && height > 0 && (ssaAC.getAbsoluteX1() + width) <= tmpScreenshot.getBitmap().getWidth() && (ssaAC.getAbsoluteY1() + height) <= tmpScreenshot.getBitmap().getHeight()) {
                areaBitmap = Bitmap.createBitmap(tmpScreenshot.getBitmap(), ssaAC.getAbsoluteX1(), ssaAC.getAbsoluteY1(), width, height);
                areaBitmap = applyCropConditions(areaBitmap, ssaCropCondition);
            }
        }

        return areaBitmap;
    }

    public Bitmap getAreaBitmapRBT(Bitmap sourceBitmap) {
        return getAreaBitmapRBT(new SSA_Screenshot(sourceBitmap), null, true);
    }

    public Bitmap getAreaBitmapRBT(SSA_Screenshot ssaScreenshot) {
        return getAreaBitmapRBT(ssaScreenshot, null, false);
    }

    public Bitmap getAreaBitmapRBT(SSA_Screenshot ssaScreenshot, SSA_RBT_Condition ssaRbtCondition) {
        return getAreaBitmapRBT(ssaScreenshot, ssaRbtCondition, false);
    }
    public Bitmap getAreaBitmapRBT(SSA_Screenshot ssaScreenshot, SSA_RBT_Condition ssaRbtCondition, boolean withoutParents) {
//        Bitmap result = this.getAreaBitmapRBT();
        Bitmap result = null;
        if (result == null) {
            result = getAreaBitmap(ssaScreenshot, null, withoutParents);

            if (listRBTConditions != null) {
                for (SSA_RBT_Condition ssaRC: listRBTConditions) {
                    Bitmap tmp = PictureProcessor.applyRbtCondition(result, ssaRC);
                    result = tmp == null ? result : tmp;
                    if (ssaRbtCondition != null && ssaRbtCondition.getKey().equals(ssaRC.getKey())) break;
                }
            }
        }

        return result;
    }

    private Bitmap applyCropConditions(Bitmap sourceBitmap) {
        return applyCropConditions(sourceBitmap, null);
    }

    private Bitmap applyCropConditions(Bitmap sourceBitmap, SSA_Crop_Condition ssaCropCondition) {
        Bitmap result = sourceBitmap;
        if (listCropConditions != null) {
            for (SSA_Crop_Condition ssaCC: listCropConditions) {
                Bitmap tmp = PictureProcessor.applyCropCondition(result, ssaCC);
                result = tmp == null ? result : tmp;
                if (ssaCropCondition != null && ssaCropCondition.getKey().equals(ssaCC.getKey())) break;
            }
        }
        return result;
    }

    public String getOCR(Bitmap sourceBitmap) {
        Log.e("Start OCR", "Based on bitmap. Area: " + this.getKey());
        return PictureProcessor.doOCR(sourceBitmap);
    }

    public String getOCR(SSA_Screenshot ssaScreenshot) {
        Log.e("Start OCR", "Based on screenshot. Area: " + this.getKey());
        return PictureProcessor.doOCR(getAreaBitmapRBT(ssaScreenshot));
    }

    @Override
    public int compareTo(SSA_Area o) {
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

    public List<SSA_Crop_Condition> getListCropConditions() {
        if (listCropConditions == null) listCropConditions = new ArrayList<>();
        return listCropConditions;
    }

    public void setListCropConditions(List<SSA_Crop_Condition> listCropConditions) {
        this.listCropConditions = listCropConditions;
    }

    public void setAreaBitmap(Bitmap areaBitmap) {
        this.areaBitmap = areaBitmap;
    }

    public void setAreaBitmapRBT(Bitmap areaBitmapRBT) {
        this.areaBitmapRBT = areaBitmapRBT;
    }

    public Bitmap getAreaBitmap() {
        return areaBitmap;
    }

    public Bitmap getAreaBitmapRBT() {
        return areaBitmapRBT;
    }

    public List<SSA_RBT_Condition> getListRBTConditions() {
        if (listRBTConditions == null) listRBTConditions = new ArrayList<>();
        return listRBTConditions;
    }

    public void setListRBTConditions(List<SSA_RBT_Condition> listRBTConditions) {
        this.listRBTConditions = listRBTConditions;
    }

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

    public int getSnap() {
        return snap;
    }

    public void setSnap(int snap) {
        this.snap = snap;
    }

    public double getrX1() {
        return rX1;
    }

    public void setrX1(double rX1) {
        this.rX1 = rX1;
    }

    public double getrX2() {
        return rX2;
    }

    public void setrX2(double rX2) {
        this.rX2 = rX2;
    }

    public double getrY1() {
        return rY1;
    }

    public void setrY1(double rY1) {
        this.rY1 = rY1;
    }

    public double getrY2() {
        return rY2;
    }

    public void setrY2(double rY2) {
        this.rY2 = rY2;
    }

    public SSA_Area getParentArea() {
        return parentArea;
    }

    public void setParentArea(SSA_Area parentArea) {
        this.parentArea = parentArea;
    }


}
