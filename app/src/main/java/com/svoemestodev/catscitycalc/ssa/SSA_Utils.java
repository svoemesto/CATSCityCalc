package com.svoemestodev.catscitycalc.ssa;

import com.svoemestodev.catscitycalc.utils.PictureProcessorDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SSA_Utils {

    public static SSA_Areas getDefaultAreas() {
        SSA_Areas ssaAreas = new SSA_Areas();
        Map<String, SSA_Area> map = new HashMap<>();
        SSA_Area ssaArea;
        SSA_Area parentArea;
        List<SSA_Crop_Condition> listCropConditions;
        List<SSA_RBT_Condition> listRbtConditions;
        SSA_Crop_Condition ssaCropCondition;
        SSA_RBT_Condition ssaRbtCondition;

        ssaArea = new SSA_Area("CITY_TIME");
        ssaArea.setName("Время до конца игры");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.130);
        ssaArea.setrX2(+0.055);
        ssaArea.setrY1(-0.770);
        ssaArea.setrY2(-0.665);
        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor("WHITE"), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BOX_GRAY");
        ssaArea.setName("Серый квадрат слева от времени");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.170);
        ssaArea.setrX2(-0.140);
        ssaArea.setrY1(-0.770);
        ssaArea.setrY2(-0.700);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CAR_BOX_IN_CITY");
        ssaArea.setName("Голубой квадрат машины в городе");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(-1);
        ssaArea.setrX1(0.350);
        ssaArea.setrX2(0.440);
        ssaArea.setrY1(-0.440);
        ssaArea.setrY2(-0.300);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_TEAM_NAME_OUR");
        ssaArea.setName("Название нашей команды");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.560);
        ssaArea.setrX2(-0.250);
        ssaArea.setrY1(-0.760);
        ssaArea.setrY2(-0.640);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("WHITE"),1,0.075f,1.000f,
                SSA_Colors.getColor("WHITE"),1,0.000f,0.085f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("WHITE"),1,0.075f,1.000f,
                SSA_Colors.getColor("WHITE"),1,0.000f,0.085f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("WHITE"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);


        ssaArea = new SSA_Area("CITY_TEAM_NAME_ENEMY");
        ssaArea.setName("Название команды противника");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.240);
        ssaArea.setrX2(+0.490);
        ssaArea.setrY1(-0.760);
        ssaArea.setrY2(-0.640);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("WHITE"),1,0.075f,1.000f,
                SSA_Colors.getColor("WHITE"),1,0.000f,0.085f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("WHITE"),1,0.075f,1.000f,
                SSA_Colors.getColor("WHITE"),1,0.000f,0.085f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("WHITE"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_EARLY_WIN");
        ssaArea.setName("Очки для досрочной победы");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.170);
        ssaArea.setrX2(+0.170);
        ssaArea.setrY1(-0.560);
        ssaArea.setrY2(-0.450);
        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor("WHITE"), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_POINTS_AND_INCREASE_OUR");
        ssaArea.setName("Очки и прирост нашей команды");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.560);
        ssaArea.setrX2(-0.180);
        ssaArea.setrY1(-0.620);
        ssaArea.setrY2(-0.460);
        map.put(ssaArea.getKey(), ssaArea);

        parentArea = ssaArea;
        ssaArea = new SSA_Area("CITY_POINTS_OUR");
        ssaArea.setName("Очки нашей команды");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-2.379);
        ssaArea.setrX2(+2.379);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("INCREASE_PLATE_NUMBERS_OUR"),0,0.001f,1.000f,
                SSA_Colors.getColor("INCREASE_PLATE_OUR"),0,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("WHITE"),1,0.070f,1.000f,
                SSA_Colors.getColor("WHITE"),10,0.000f,0.085f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor("POINTS_OUR"), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_POINTS_AND_INCREASE_ENEMY");
        ssaArea.setName("Очки и прирост команды противника");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.180);
        ssaArea.setrX2(+0.560);
        ssaArea.setrY1(-0.620);
        ssaArea.setrY2(-0.460);
        map.put(ssaArea.getKey(), ssaArea);

        parentArea = ssaArea;
        ssaArea = new SSA_Area("CITY_POINTS_ENEMY");
        ssaArea.setName("Очки команды противника");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-2.379);
        ssaArea.setrX2(+2.379);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("INCREASE_PLATE_NUMBERS_ENEMY"),0,0.001f,1.000f,
                SSA_Colors.getColor("INCREASE_PLATE_ENEMY"),0,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("WHITE"),1,0.070f,1.000f,
                SSA_Colors.getColor("WHITE"),10,0.000f,0.070f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor("POINTS_ENEMY"), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD1");
        ssaArea.setName("Здание №1 (BRT)");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.460);
        ssaArea.setrX2(+0.720);
        ssaArea.setrY1(-0.170);
        ssaArea.setrY2(-0.090);
        map.put(ssaArea.getKey(), ssaArea);

        parentArea = ssaArea;
        ssaArea = new SSA_Area("CITY_BLD1_NAME");
        ssaArea.setName("Здание №1 (BRT): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.225);
        ssaArea.setrX2(+3.225);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("WHITE"),1,0.850f,1.000f,
                SSA_Colors.getColor("WHITE"),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("MAY_X2"),1,0.001f,1.000f,
                SSA_Colors.getColor("MAY_X2"),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("IS_X2"),5,0.001f,1.000f,
                SSA_Colors.getColor("IS_X2"),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("WHITE"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD1_SLOTS");
        ssaArea.setName("Здание №1 (BRT): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.225);
        ssaArea.setrX2(+3.225);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("WHITE"),1,0.075f,1.000f,
                SSA_Colors.getColor("WHITE"),1,0.000f,0.085f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD1_PROGRESS");
        ssaArea.setName("Здание №1 (BRT): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.472);
        ssaArea.setrX2(+0.703);
        ssaArea.setrY1(-0.081);
        ssaArea.setrY2(-0.029);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD2");
        ssaArea.setName("Здание №2 (BRC)");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.490);
        ssaArea.setrX2(+0.740);
        ssaArea.setrY1(+0.330);
        ssaArea.setrY2(+0.410);
        map.put(ssaArea.getKey(), ssaArea);

        parentArea = ssaArea;
        ssaArea = new SSA_Area("CITY_BLD2_NAME");
        ssaArea.setName("Здание №2 (BRC): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.104);
        ssaArea.setrX2(+3.104);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("WHITE"),1,0.850f,1.000f,
                SSA_Colors.getColor("WHITE"),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("MAY_X2"),1,0.001f,1.000f,
                SSA_Colors.getColor("MAY_X2"),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("IS_X2"),5,0.001f,1.000f,
                SSA_Colors.getColor("IS_X2"),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("WHITE"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD2_SLOTS");
        ssaArea.setName("Здание №2 (BRC): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.104);
        ssaArea.setrX2(+3.104);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("WHITE"),1,0.075f,1.000f,
                SSA_Colors.getColor("WHITE"),1,0.000f,0.085f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD2_PROGRESS");
        ssaArea.setName("Здание №2 (BRC): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.495);
        ssaArea.setrX2(+0.728);
        ssaArea.setrY1(+0.419);
        ssaArea.setrY2(+0.471);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD3");
        ssaArea.setName("Здание №3 (BRB)");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.190);
        ssaArea.setrX2(+0.440);
        ssaArea.setrY1(+0.610);
        ssaArea.setrY2(+0.690);
        map.put(ssaArea.getKey(), ssaArea);

        parentArea = ssaArea;
        ssaArea = new SSA_Area("CITY_BLD3_NAME");
        ssaArea.setName("Здание №3 (BRB): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.158);
        ssaArea.setrX2(+3.158);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("WHITE"),1,0.850f,1.000f,
                SSA_Colors.getColor("WHITE"),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("MAY_X2"),1,0.001f,1.000f,
                SSA_Colors.getColor("MAY_X2"),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("IS_X2"),5,0.001f,1.000f,
                SSA_Colors.getColor("IS_X2"),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("WHITE"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD3_SLOTS");
        ssaArea.setName("Здание №3 (BRB): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.158);
        ssaArea.setrX2(+3.158);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("WHITE"),1,0.075f,1.000f,
                SSA_Colors.getColor("WHITE"),1,0.000f,0.085f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD3_PROGRESS");
        ssaArea.setName("Здание №3 (BRB): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.195);
        ssaArea.setrX2(+0.428);
        ssaArea.setrY1(+0.699);
        ssaArea.setrY2(+0.752);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD4");
        ssaArea.setName("Здание №4 (BLB)");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.220);
        ssaArea.setrX2(+0.040);
        ssaArea.setrY1(+0.580);
        ssaArea.setrY2(+0.660);
        map.put(ssaArea.getKey(), ssaArea);

        parentArea = ssaArea;
        ssaArea = new SSA_Area("CITY_BLD4_NAME");
        ssaArea.setName("Здание №3 (BLB): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.225);
        ssaArea.setrX2(+3.225);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("WHITE"),1,0.850f,1.000f,
                SSA_Colors.getColor("WHITE"),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("MAY_X2"),1,0.001f,1.000f,
                SSA_Colors.getColor("MAY_X2"),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("IS_X2"),5,0.001f,1.000f,
                SSA_Colors.getColor("IS_X2"),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("WHITE"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD4_SLOTS");
        ssaArea.setName("Здание №4 (BLB): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.225);
        ssaArea.setrX2(+3.225);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("WHITE"),1,0.075f,1.000f,
                SSA_Colors.getColor("WHITE"),1,0.000f,0.085f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD4_PROGRESS");
        ssaArea.setName("Здание №4 (BLB): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.208);
        ssaArea.setrX2(+0.024);
        ssaArea.setrY1(+0.669);
        ssaArea.setrY2(+0.721);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD5");
        ssaArea.setName("Здание №5 (BLC)");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.580);
        ssaArea.setrX2(-0.320);
        ssaArea.setrY1(+0.320);
        ssaArea.setrY2(+0.400);
        map.put(ssaArea.getKey(), ssaArea);

        parentArea = ssaArea;
        ssaArea = new SSA_Area("CITY_BLD5_NAME");
        ssaArea.setName("Здание №5 (BLC): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.233);
        ssaArea.setrX2(+3.233);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("WHITE"),1,0.850f,1.000f,
                SSA_Colors.getColor("WHITE"),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("MAY_X2"),1,0.001f,1.000f,
                SSA_Colors.getColor("MAY_X2"),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("IS_X2"),5,0.001f,1.000f,
                SSA_Colors.getColor("IS_X2"),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("WHITE"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD5_SLOTS");
        ssaArea.setName("Здание №5 (BLC): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.233);
        ssaArea.setrX2(+3.233);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("WHITE"),1,0.075f,1.000f,
                SSA_Colors.getColor("WHITE"),1,0.000f,0.085f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD5_PROGRESS");
        ssaArea.setName("Здание №5 (BLC): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.567);
        ssaArea.setrX2(-0.335);
        ssaArea.setrY1(+0.408);
        ssaArea.setrY2(+0.475);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD6");
        ssaArea.setName("Здание №6 (BLT)");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.490);
        ssaArea.setrX2(-0.240);
        ssaArea.setrY1(-0.120);
        ssaArea.setrY2(-0.040);
        map.put(ssaArea.getKey(), ssaArea);

        parentArea = ssaArea;
        ssaArea = new SSA_Area("CITY_BLD6_NAME");
        ssaArea.setName("Здание №6 (BLT): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.104);
        ssaArea.setrX2(+3.104);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("WHITE"),1,0.850f,1.000f,
                SSA_Colors.getColor("WHITE"),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("MAY_X2"),1,0.001f,1.000f,
                SSA_Colors.getColor("MAY_X2"),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("IS_X2"),5,0.001f,1.000f,
                SSA_Colors.getColor("IS_X2"),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("WHITE"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);
        
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD6_SLOTS");
        ssaArea.setName("Здание №6 (BLT): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.104);
        ssaArea.setrX2(+3.104);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SLOTS"),0,0.001f,1.000f,
                SSA_Colors.getColor("SLOTS"),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("WHITE"),1,0.075f,1.000f,
                SSA_Colors.getColor("WHITE"),1,0.000f,0.085f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BLD6_PROGRESS");
        ssaArea.setName("Здание №6 (BLT): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.486);
        ssaArea.setrX2(-0.254);
        ssaArea.setrY1(-0.035);
        ssaArea.setrY2(+0.017);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CITY_BOX_INFO");
        ssaArea.setName("Красный квадрат с буквой i");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.640);
        ssaArea.setrX2(-0.580);
        ssaArea.setrY1(-0.800);
        ssaArea.setrY2(-0.690);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("GAME_BOX_BACK");
        ssaArea.setName("Красная кнопка НАЗАД");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(-1);
        ssaArea.setrX1(+0.050);
        ssaArea.setrX2(+0.170);
        ssaArea.setrY1(+0.720);
        ssaArea.setrY2(+0.960);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("GARAGE_HEALTH_SHIELD_ENERGY");
        ssaArea.setName("Информационная панель в гараже");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.280);
        ssaArea.setrX2(+0.380);
        ssaArea.setrY1(+0.760);
        ssaArea.setrY2(+0.900);
        map.put(ssaArea.getKey(), ssaArea);

        parentArea = ssaArea;
        ssaArea = new SSA_Area("GARAGE_HEALTH");
        ssaArea.setName("Здоровье в гараже");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-4.691);
        ssaArea.setrX2(+4.691);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("HEALTH_RED_LIGHT"),1,0.001f,1.000f,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("SHIELD_BROWN"),1,0.001f,1.000f,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("GARAGE_SHIELD");
        ssaArea.setName("Атака в гараже");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-4.691);
        ssaArea.setrX2(+4.691);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SHIELD_BLUE"),1,0.001f,1.000f,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("ENERGY_YELLOW_LIGHT"),1,0.001f,1.000f,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("GARAGE_ENERGY");
        ssaArea.setName("Энергия в гараже");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-4.691);
        ssaArea.setrX2(+4.691);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("ENERGY_YELLOW_LIGHT"),1,0.001f,1.000f,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),1,0.000f,1.000f,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);


        ssaArea = new SSA_Area("GARAGE_SLOT1");
        ssaArea.setName("Слот №1 в гараже");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(1);
        ssaArea.setrX1(-0.379);
        ssaArea.setrX2(-0.290);
        ssaArea.setrY1(+0.740);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("GARAGE_SLOT2");
        ssaArea.setName("Слот №2 в гараже");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(1);
        ssaArea.setrX1(-0.248);
        ssaArea.setrX2(-0.161);
        ssaArea.setrY1(+0.740);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("GARAGE_SLOT3");
        ssaArea.setName("Слот №3 в гараже");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(1);
        ssaArea.setrX1(-0.121);
        ssaArea.setrX2(-0.032);
        ssaArea.setrY1(+0.740);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("GARAGE_CAR");
        ssaArea.setName("Машина в гараже");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.500);
        ssaArea.setrX2(+0.500);
        ssaArea.setrY1(-0.480);
        ssaArea.setrY2(+0.700);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("ATTACK_DEFENCE_BUILDING_NAME");
        ssaArea.setName("Название здания в атаке/защите");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.120);
        ssaArea.setrX2(+0.110);
        ssaArea.setrY1(-0.950);
        ssaArea.setrY2(-0.830);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("WHITE"),1,0.001f,1.000f,
                SSA_Colors.getColor("GRAY_CITY_EARLY_WIN"),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor("WHITE"), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("ATTACK_DEFENCE_BOX");
        ssaArea.setName("Прямоугольник атаки/защиты");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(-1);
        ssaArea.setrX1(+0.035);
        ssaArea.setrX2(+0.085);
        ssaArea.setrY1(-0.720);
        ssaArea.setrY2(-0.618);
        map.put(ssaArea.getKey(), ssaArea);

        map.put(ssaArea.getKey(), ssaArea);
        ssaArea = new SSA_Area("CAR_IN_CITY_ATTACK_DEFENCE");
        ssaArea.setName("Область машины в городе/атаке/защите");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(-1);
        ssaArea.setrX1(+0.000);
        ssaArea.setrX2(+0.450);
        ssaArea.setrY1(-0.440);
        ssaArea.setrY2(+0.685);
        map.put(ssaArea.getKey(), ssaArea);

        parentArea = ssaArea;

        ssaArea = new SSA_Area("CAR_SLOT1");
        ssaArea.setName("Слот №1 машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.300);
        ssaArea.setrX2(-0.145);
        ssaArea.setrY1(+0.590);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CAR_SLOT2");
        ssaArea.setName("Слот №2 машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.110);
        ssaArea.setrX2(+0.050);
        ssaArea.setrY1(+0.590);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CAR_SLOT3");
        ssaArea.setName("Слот №3 машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.090);
        ssaArea.setrX2(+0.240);
        ssaArea.setrY1(+0.590);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CAR_HEALTH");
        ssaArea.setName("Здоровье машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.400);
        ssaArea.setrX2(+0.400);
        ssaArea.setrY1(+0.240);
        ssaArea.setrY2(+0.440);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("HEALTH_RED_LIGHT"),1,0.001f,1.000f,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("SHIELD_BROWN"),1,0.001f,1.000f,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CAR_SHIELD");
        ssaArea.setName("Атака машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.400);
        ssaArea.setrX2(+0.400);
        ssaArea.setrY1(+0.240);
        ssaArea.setrY2(+0.440);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("SHIELD_BROWN"),1,0.001f,1.000f,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),1,0.001f,1.000f,
                SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor("GARAGE_INFO_BACKGROUND"), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CAR_PICTURE");
        ssaArea.setName("Машина");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.300);
        ssaArea.setrX2(+0.300);
        ssaArea.setrY1(-0.430);
        ssaArea.setrY2(+0.210);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CAR_HEALBOX");
        ssaArea.setName("Аптечка");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.100);
        ssaArea.setrX2(+0.250);
        ssaArea.setrY1(-0.720);
        ssaArea.setrY2(-0.410);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CAR_BOX_DEFENCE_BREAK");
        ssaArea.setName("Бокс защиты/ремонта машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.100);
        ssaArea.setrX2(+0.250);
        ssaArea.setrY1(-0.720);
        ssaArea.setrY2(-0.410);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CAR_TIMELINE");
        ssaArea.setName("Бокс защиты/ремонта машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.400);
        ssaArea.setrX2(+0.400);
        ssaArea.setrY1(-0.650);
        ssaArea.setrY2(-0.480);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor("WHITE"),1,0.001f,1.000f,
                SSA_Colors.getColor("WHITE"),55,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor("TIMELINE_BLUE"),1,0.001f,1.000f,
                SSA_Colors.getColor("TIMELINE_BLUE"),0,0.000f,0.000f,false,true);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor("WHITE"), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor("WHITE"), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area("CAR_REPAIRING_IN_DEFENCE");
        ssaArea.setName("Ключ починки в защите");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.155);
        ssaArea.setrX2(-0.110);
        ssaArea.setrY1(-0.620);
        ssaArea.setrY2(-0.530);
        map.put(ssaArea.getKey(), ssaArea);


        ssaAreas.setMap(map);
        return ssaAreas;
    }

    public static SSA_Colors getDefaultColors() {
        SSA_Colors ssaColors = new SSA_Colors();
        Map<String, SSA_Color> map = new HashMap<>();
        SSA_Color ssaColor;

        ssaColor = new SSA_Color("WHITE");
        ssaColor.setName("Белый");
        ssaColor.setColor(0xFFFFFFFF);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("BLACK");
        ssaColor.setName("Черный");
        ssaColor.setColor(0xFF000000);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("RED");
        ssaColor.setName("Красный");
        ssaColor.setColor(0xFFFF0000);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("GREEN");
        ssaColor.setName("Зеленый");
        ssaColor.setColor(0xFF00FF00);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("BLUE");
        ssaColor.setName("Синий");
        ssaColor.setColor(0xFF0000FF);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("RED_CITY_BOX_INFO");
        ssaColor.setName("Красный инфобокс");
        ssaColor.setColor(0xFFD22425);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("GRAY_CITY_EARLY_WIN");
        ssaColor.setName("Серый досрочной победы");
        ssaColor.setColor(0xFF91A7BF);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("YELLOW_GAME_BOX_BACK");
        ssaColor.setName("Желтая стрелка на кнопке Назад");
        ssaColor.setColor(0xFFFFF6BF);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("PROGRESS_OUR");
        ssaColor.setName("Прогресс наш");
        ssaColor.setColor(0xFF90B3EB);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("PROGRESS_ENEMY");
        ssaColor.setName("Прогресс противника");
        ssaColor.setColor(0xFFFF6565);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("PROGRESS_EMPTY");
        ssaColor.setName("Прогресс пустой");
        ssaColor.setColor(0xFF5E6A84);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("IS_X2");
        ssaColor.setName("Желтая скрепка");
        ssaColor.setColor(0xFFECAD20);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("MAY_X2");
        ssaColor.setName("Серая скрепка");
        ssaColor.setColor(0xFFAFBED1);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("SLOTS");
        ssaColor.setName("Цвет машинки слотов");
        ssaColor.setColor(0xFF2C3852);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("POINTS_ENEMY");
        ssaColor.setName("Очки противника");
        ssaColor.setColor(0xFFF64F49);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("INCREASE_PLATE_ENEMY");
        ssaColor.setName("Плашка прироста противника");
        ssaColor.setColor(0xFFEB4C48);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("INCREASE_PLATE_NUMBERS_ENEMY");
        ssaColor.setName("Цифры на плашке прироста противника");
        ssaColor.setColor(0xFFFDD5D5);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("POINTS_OUR");
        ssaColor.setName("Очки наши");
        ssaColor.setColor(0xFF437AD7);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("INCREASE_PLATE_OUR");
        ssaColor.setName("Плашка прироста наша");
        ssaColor.setColor(0xFF4179D8);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("INCREASE_PLATE_NUMBERS_OUR");
        ssaColor.setName("Цифры на плашке прироста наша");
        ssaColor.setColor(0xFFD3DEF4);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("CAR_BLUE_BOX_IN_CITY");
        ssaColor.setName("Голубой квадрат машины в городе");
        ssaColor.setColor(0xFF8CB1F0);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("GARAGE_INFO_BACKGROUND");
        ssaColor.setName("Коричневый фон информационной области в гараже");
        ssaColor.setColor(0xFFECE0D2);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("HEALTH_RED_DARK");
        ssaColor.setName("Темно-красное сердце");
        ssaColor.setColor(0xFFC92A0D);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("HEALTH_RED_LIGHT");
        ssaColor.setName("Светло-красное сердце");
        ssaColor.setColor(0xFFEA3200);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("SHIELD_BLUE");
        ssaColor.setName("Голубой меч");
        ssaColor.setColor(0xFF7AC2DA);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("SHIELD_BROWN");
        ssaColor.setName("Коричневый меч");
        ssaColor.setColor(0xFF98441F);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("ENERGY_YELLOW_LIGHT");
        ssaColor.setName("Светло-желтая молния");
        ssaColor.setColor(0xFFE8A72C);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("ENERGY_YELLOW_DARK");
        ssaColor.setName("Темно-желтая молния");
        ssaColor.setColor(0xFFE78B17);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("GARAGE_INFO_NUMBERS_VIOLETTE");
        ssaColor.setName("Фиолетовые цифры информационной области в гараже");
        ssaColor.setColor(0xFF78508F);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("GARAGE_INFO_NUMBERS_BROWN");
        ssaColor.setName("Коричневые цифры информационной области в гараже");
        ssaColor.setColor(0xFF827368);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("DEFENCE_BLUE_LIGHT");
        ssaColor.setName("Светло-синяя защита");
        ssaColor.setColor(0xFFA9CAFA);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("DEFENCE_BLUE_DARK");
        ssaColor.setName("Темно-синяя защита");
        ssaColor.setColor(0xFF9BBCF5);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("ATTACK_RED_LIGHT");
        ssaColor.setName("Светло-красная атака");
        ssaColor.setColor(0xFFFF605A);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("ATTACK_RED_DARK");
        ssaColor.setName("Темно-красная атака");
        ssaColor.setColor(0xFFF64E48);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("HEALBOX_RED");
        ssaColor.setName("Красная аптечка");
        ssaColor.setColor(0xFFD22325);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("HEALBOX_YELLOW");
        ssaColor.setName("Желтая аптечка");
        ssaColor.setColor(0xFFFFF5BF);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("TIMELINE_BLUE");
        ssaColor.setName("Синий цвет временной области");
        ssaColor.setColor(0xFF5574CE);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color("TIMELINE_GREEN");
        ssaColor.setName("Зеленый цвет временной области");
        ssaColor.setColor(0xFF72D52C);
        map.put(ssaColor.getKey(), ssaColor);


        ssaColors.setMap(map);
        return ssaColors;
    }
    
    public static SSA_Conditions getDefaultConditions() {
        SSA_Conditions ssaConditions = new SSA_Conditions();
        Map<String, SSA_Condition> map = new HashMap<>();
        SSA_Condition ssaCondition;
        SSA_Color ssaColor;
        
        ssaCondition = new SSA_Condition("CITY_TIME");
        ssaCondition.setName("Время до конца игры");
        ssaCondition.setSsaColor(SSA_Colors.getColor("WHITE"));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.00);
        ssaCondition.setMaxFrequency(1.00);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaConditions.setMap(map);
        return ssaConditions;
    }
}
