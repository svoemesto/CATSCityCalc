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

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY.getKey());
        ssaArea.setName("Город");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.560);
        ssaArea.setrX2(+0.560);
        ssaArea.setrY1(-0.800);
        ssaArea.setrY2(-0.450);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_TIME.getKey());
        ssaArea.setName("Время до конца игры");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.130);
        ssaArea.setrX2(+0.055);
        ssaArea.setrY1(-0.770);
        ssaArea.setrY2(-0.665);
        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BOX_GRAY.getKey());
        ssaArea.setName("Серый квадрат слева от времени");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.170);
        ssaArea.setrX2(-0.140);
        ssaArea.setrY1(-0.770);
        ssaArea.setrY2(-0.700);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_BOX_IN_CITY.getKey());
        ssaArea.setName("Голубой квадрат машины в городе");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(-1);
        ssaArea.setrX1(0.350);
        ssaArea.setrX2(0.440);
        ssaArea.setrY1(-0.440);
        ssaArea.setrY2(-0.300);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_TEAM_NAME_OUR.getKey());
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
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.750f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.750f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);


        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_TEAM_NAME_ENEMY.getKey());
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
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.750f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.750f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_EARLY_WIN.getKey());
        ssaArea.setName("Очки для досрочной победы");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.170);
        ssaArea.setrX2(+0.170);
        ssaArea.setrY1(-0.560);
        ssaArea.setrY2(-0.450);
        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_POINTS_AND_INCREASE_OUR.getKey());
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
        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_POINTS_OUR.getKey());
        ssaArea.setName("Очки нашей команды");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-2.379);
        ssaArea.setrX2(+2.379);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_INCREASE_PLATE_OUR.getKey()),5,0.760f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_INCREASE_PLATE_OUR.getKey()),5,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.700f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor(SSA_Key.COLOR_POINTS_OUR.getKey()), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_POINTS_AND_INCREASE_ENEMY.getKey());
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
        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_POINTS_ENEMY.getKey());
        ssaArea.setName("Очки команды противника");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-2.379);
        ssaArea.setrX2(+2.379);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_INCREASE_PLATE_ENEMY.getKey()),5,0.760f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_INCREASE_PLATE_ENEMY.getKey()),5,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.700f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),10,0.000f,0.700f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor(SSA_Key.COLOR_POINTS_ENEMY.getKey()), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD1.getKey());
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
        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD1_NAME.getKey());
        ssaArea.setName("Здание №1 (BRT): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.225);
        ssaArea.setrX2(+3.225);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.850f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),5,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD1_SLOTS.getKey());
        ssaArea.setName("Здание №1 (BRT): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.225);
        ssaArea.setrX2(+3.225);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.700f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.000f,0.950f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);


        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD1_PROGRESS.getKey());
        ssaArea.setName("Здание №1 (BRT): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.472);
        ssaArea.setrX2(+0.703);
        ssaArea.setrY1(-0.081);
        ssaArea.setrY2(-0.029);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD2.getKey());
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
        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD2_NAME.getKey());
        ssaArea.setName("Здание №2 (BRC): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.104);
        ssaArea.setrX2(+3.104);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.850f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),5,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD2_SLOTS.getKey());
        ssaArea.setName("Здание №2 (BRC): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.104);
        ssaArea.setrX2(+3.104);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.700f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.000f,0.950f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaArea.setListCropConditions(listCropConditions);


        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);


        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD2_PROGRESS.getKey());
        ssaArea.setName("Здание №2 (BRC): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.495);
        ssaArea.setrX2(+0.728);
        ssaArea.setrY1(+0.419);
        ssaArea.setrY2(+0.471);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD3.getKey());
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
        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD3_NAME.getKey());
        ssaArea.setName("Здание №3 (BRB): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.158);
        ssaArea.setrX2(+3.158);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.850f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),5,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD3_SLOTS.getKey());
        ssaArea.setName("Здание №3 (BRB): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.158);
        ssaArea.setrX2(+3.158);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.700f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.000f,0.950f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaArea.setListCropConditions(listCropConditions);


        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);


        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD3_PROGRESS.getKey());
        ssaArea.setName("Здание №3 (BRB): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.195);
        ssaArea.setrX2(+0.428);
        ssaArea.setrY1(+0.699);
        ssaArea.setrY2(+0.752);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD4.getKey());
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
        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD4_NAME.getKey());
        ssaArea.setName("Здание №3 (BLB): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.225);
        ssaArea.setrX2(+3.225);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.850f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),5,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD4_SLOTS.getKey());
        ssaArea.setName("Здание №4 (BLB): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.225);
        ssaArea.setrX2(+3.225);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.700f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.000f,0.950f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);


        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD4_PROGRESS.getKey());
        ssaArea.setName("Здание №4 (BLB): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.208);
        ssaArea.setrX2(+0.024);
        ssaArea.setrY1(+0.669);
        ssaArea.setrY2(+0.721);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD5.getKey());
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
        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD5_NAME.getKey());
        ssaArea.setName("Здание №5 (BLC): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.233);
        ssaArea.setrX2(+3.233);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.850f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),5,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD5_SLOTS.getKey());
        ssaArea.setName("Здание №5 (BLC): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.233);
        ssaArea.setrX2(+3.233);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.700f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.000f,0.950f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);


        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD5_PROGRESS.getKey());
        ssaArea.setName("Здание №5 (BLC): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.567);
        ssaArea.setrX2(-0.335);
        ssaArea.setrY1(+0.408);
        ssaArea.setrY2(+0.475);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD6.getKey());
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
        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD6_NAME.getKey());
        ssaArea.setName("Здание №6 (BLT): название");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.104);
        ssaArea.setrX2(+3.104);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),0,0.000f,0.000f,true,true);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.850f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),10,0.000f,0.850f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()),15,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(),PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),5,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()),105,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD6_SLOTS.getKey());
        ssaArea.setName("Здание №6 (BLT): слоты");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-3.104);
        ssaArea.setrX2(+3.104);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()),10,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.700f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.000f,0.950f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);
        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BLD6_PROGRESS.getKey());
        ssaArea.setName("Здание №6 (BLT): прогресс");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.486);
        ssaArea.setrX2(-0.254);
        ssaArea.setrY1(-0.035);
        ssaArea.setrY2(+0.017);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CITY_BOX_INFO.getKey());
        ssaArea.setName("Красный квадрат с буквой i");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.640);
        ssaArea.setrX2(-0.580);
        ssaArea.setrY1(-0.800);
        ssaArea.setrY2(-0.690);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_GAME_BOX_BACK.getKey());
        ssaArea.setName("Красная кнопка НАЗАД");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(-1);
        ssaArea.setrX1(+0.050);
        ssaArea.setrX2(+0.170);
        ssaArea.setrY1(+0.720);
        ssaArea.setrY2(+0.960);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_GARAGE_HEALTH_SHIELD_ENERGY.getKey());
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
        ssaArea = new SSA_Area(SSA_Key.AREA_GARAGE_HEALTH.getKey());
        ssaArea.setName("Здоровье в гараже");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-4.691);
        ssaArea.setrX2(+4.691);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_HEALTH_RED_LIGHT.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_SHIELD_BROWN.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_GARAGE_SHIELD.getKey());
        ssaArea.setName("Атака в гараже");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-4.691);
        ssaArea.setrX2(+4.691);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SHIELD_BLUE.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_ENERGY_YELLOW_LIGHT.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_GARAGE_ENERGY.getKey());
        ssaArea.setName("Энергия в гараже");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-4.691);
        ssaArea.setrX2(+4.691);
        ssaArea.setrY1(-1.000);
        ssaArea.setrY2(+1.000);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_ENERGY_YELLOW_LIGHT.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),1,0.000f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);


        ssaArea = new SSA_Area(SSA_Key.AREA_GARAGE_SLOT1.getKey());
        ssaArea.setName("Слот №1 в гараже");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(1);
        ssaArea.setrX1(-0.379);
        ssaArea.setrX2(-0.290);
        ssaArea.setrY1(+0.740);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_GARAGE_SLOT2.getKey());
        ssaArea.setName("Слот №2 в гараже");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(1);
        ssaArea.setrX1(-0.248);
        ssaArea.setrX2(-0.161);
        ssaArea.setrY1(+0.740);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_GARAGE_SLOT3.getKey());
        ssaArea.setName("Слот №3 в гараже");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(1);
        ssaArea.setrX1(-0.121);
        ssaArea.setrX2(-0.032);
        ssaArea.setrY1(+0.740);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_GARAGE_CAR.getKey());
        ssaArea.setName("Машина в гараже");
        parentArea = null;
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.500);
        ssaArea.setrX2(+0.500);
        ssaArea.setrY1(-0.480);
        ssaArea.setrY2(+0.700);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_ATTACK_DEFENCE_BUILDING_NAME.getKey());
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
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_GRAY_CITY_EARLY_WIN.getKey()),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_ATTACK_DEFENCE_BOX.getKey());
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
        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_IN_CITY_ATTACK_DEFENCE.getKey());
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

        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_SLOT1.getKey());
        ssaArea.setName("Слот №1 машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.300);
        ssaArea.setrX2(-0.145);
        ssaArea.setrY1(+0.590);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_SLOT2.getKey());
        ssaArea.setName("Слот №2 машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.110);
        ssaArea.setrX2(+0.050);
        ssaArea.setrY1(+0.590);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_SLOT3.getKey());
        ssaArea.setName("Слот №3 машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.090);
        ssaArea.setrX2(+0.240);
        ssaArea.setrY1(+0.590);
        ssaArea.setrY2(+0.910);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_HEALTH.getKey());
        ssaArea.setName("Здоровье машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.400);
        ssaArea.setrX2(+0.400);
        ssaArea.setrY1(+0.240);
        ssaArea.setrY2(+0.440);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_HEALTH_RED_LIGHT.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_SHIELD_BROWN.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_SHIELD.getKey());
        ssaArea.setName("Атака машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.400);
        ssaArea.setrX2(+0.400);
        ssaArea.setrY1(+0.240);
        ssaArea.setrY2(+0.440);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_SHIELD_BROWN.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()),10,1.000f,1.000f,false,false);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 2, SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()), 90, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_PICTURE.getKey());
        ssaArea.setName("Машина");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.300);
        ssaArea.setrX2(+0.300);
        ssaArea.setrY1(-0.430);
        ssaArea.setrY2(+0.210);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_HEALBOX.getKey());
        ssaArea.setName("Аптечка");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.100);
        ssaArea.setrX2(+0.250);
        ssaArea.setrY1(-0.720);
        ssaArea.setrY2(-0.410);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_BOX_DEFENCE_BREAK.getKey());
        ssaArea.setName("Бокс защиты/ремонта машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(+0.140);
        ssaArea.setrX2(+0.270);
        ssaArea.setrY1(-0.130);
        ssaArea.setrY2(+0.160);
        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_TIMELINE.getKey());
        ssaArea.setName("Бокс защиты/ремонта машины");
        ssaArea.setParentArea(parentArea);
        ssaArea.setSnap(0);
        ssaArea.setrX1(-0.400);
        ssaArea.setrX2(+0.400);
        ssaArea.setrY1(-0.650);
        ssaArea.setrY2(-0.480);
        listCropConditions = new ArrayList<>();
        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_LEFT_TO_RIGHT,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()),55,0.000f,0.000f,false,false);
        listCropConditions.add(ssaCropCondition);

        ssaCropCondition = new SSA_Crop_Condition(ssaArea.getKey(), PictureProcessorDirection.FROM_RIGHT_TO_LEFT,
                SSA_Colors.getColor(SSA_Key.COLOR_TIMELINE_BLUE.getKey()),1,0.001f,1.000f,
                SSA_Colors.getColor(SSA_Key.COLOR_TIMELINE_BLUE.getKey()),0,0.000f,0.000f,false,true);
        listCropConditions.add(ssaCropCondition);
        ssaArea.setListCropConditions(listCropConditions);

        listRbtConditions = new ArrayList<>();
        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 0, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 10, 7.0f, 5.0f));
//        listRbtConditions.add(new SSA_RBT_Condition(ssaArea.getKey(), 1, SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()), 20, 1.0f, 1.0f));
        ssaArea.setListRBTConditions(listRbtConditions);

        map.put(ssaArea.getKey(), ssaArea);

        ssaArea = new SSA_Area(SSA_Key.AREA_CAR_REPAIRING_IN_DEFENCE.getKey());
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

        ssaColor = new SSA_Color(SSA_Key.COLOR_WHITE.getKey());
        ssaColor.setName("Белый");
        ssaColor.setColor(0xFFFFFFFF);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_BLACK.getKey());
        ssaColor.setName("Черный");
        ssaColor.setColor(0xFF000000);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_RED.getKey());
        ssaColor.setName("Красный");
        ssaColor.setColor(0xFFFF0000);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_GREEN.getKey());
        ssaColor.setName("Зеленый");
        ssaColor.setColor(0xFF00FF00);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_BLUE.getKey());
        ssaColor.setName("Синий");
        ssaColor.setColor(0xFF0000FF);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_RED_CITY_BOX_INFO.getKey());
        ssaColor.setName("Красный инфобокс");
        ssaColor.setColor(0xFFD22425);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_GRAY_CITY_EARLY_WIN.getKey());
        ssaColor.setName("Серый досрочной победы");
        ssaColor.setColor(0xFF91A7BF);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_YELLOW_GAME_BOX_BACK.getKey());
        ssaColor.setName("Желтая стрелка на кнопке Назад");
        ssaColor.setColor(0xFFFFF6BF);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_PROGRESS_OUR.getKey());
        ssaColor.setName("Прогресс наш");
        ssaColor.setColor(0xFF90B3EB);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_PROGRESS_ENEMY.getKey());
        ssaColor.setName("Прогресс противника");
        ssaColor.setColor(0xFFFF6565);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_PROGRESS_EMPTY.getKey());
        ssaColor.setName("Прогресс пустой");
        ssaColor.setColor(0xFF5E6A84);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_IS_X2.getKey());
        ssaColor.setName("Желтая скрепка");
        ssaColor.setColor(0xFFECAD20);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_MAY_X2.getKey());
        ssaColor.setName("Серая скрепка");
        ssaColor.setColor(0xFFAFBED1);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_SLOTS.getKey());
        ssaColor.setName("Цвет машинки слотов");
        ssaColor.setColor(0xFF2C3852);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_POINTS_ENEMY.getKey());
        ssaColor.setName("Очки противника");
        ssaColor.setColor(0xFFF64F49);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_INCREASE_PLATE_ENEMY.getKey());
        ssaColor.setName("Плашка прироста противника");
        ssaColor.setColor(0xFFEB4C48);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_INCREASE_PLATE_NUMBERS_ENEMY.getKey());
        ssaColor.setName("Цифры на плашке прироста противника");
        ssaColor.setColor(0xFFFDD5D5);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_POINTS_OUR.getKey());
        ssaColor.setName("Очки наши");
        ssaColor.setColor(0xFF437AD7);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_INCREASE_PLATE_OUR.getKey());
        ssaColor.setName("Плашка прироста наша");
        ssaColor.setColor(0xFF4179D8);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_INCREASE_PLATE_NUMBERS_OUR.getKey());
        ssaColor.setName("Цифры на плашке прироста наша");
        ssaColor.setColor(0xFFD3DEF4);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_CAR_BLUE_BOX_IN_CITY.getKey());
        ssaColor.setName("Голубой квадрат машины в городе");
        ssaColor.setColor(0xFF8CB1F0);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey());
        ssaColor.setName("Коричневый фон информационной области в гараже");
        ssaColor.setColor(0xFFECE0D2);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_HEALTH_RED_DARK.getKey());
        ssaColor.setName("Темно-красное сердце");
        ssaColor.setColor(0xFFC92A0D);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_HEALTH_RED_LIGHT.getKey());
        ssaColor.setName("Светло-красное сердце");
        ssaColor.setColor(0xFFEA3200);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_SHIELD_BLUE.getKey());
        ssaColor.setName("Голубой меч");
        ssaColor.setColor(0xFF7AC2DA);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_SHIELD_BROWN.getKey());
        ssaColor.setName("Коричневый меч");
        ssaColor.setColor(0xFF98441F);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_ENERGY_YELLOW_LIGHT.getKey());
        ssaColor.setName("Светло-желтая молния");
        ssaColor.setColor(0xFFE8A72C);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_ENERGY_YELLOW_DARK.getKey());
        ssaColor.setName("Темно-желтая молния");
        ssaColor.setColor(0xFFE78B17);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_GARAGE_INFO_NUMBERS_VIOLETTE.getKey());
        ssaColor.setName("Фиолетовые цифры информационной области в гараже");
        ssaColor.setColor(0xFF78508F);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_GARAGE_INFO_NUMBERS_BROWN.getKey());
        ssaColor.setName("Коричневые цифры информационной области в гараже");
        ssaColor.setColor(0xFF827368);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_DEFENCE_BLUE_LIGHT.getKey());
        ssaColor.setName("Светло-синяя защита");
        ssaColor.setColor(0xFFA9CAFA);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_DEFENCE_BLUE_DARK.getKey());
        ssaColor.setName("Темно-синяя защита");
        ssaColor.setColor(0xFF9BBCF5);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_ATTACK_RED_LIGHT.getKey());
        ssaColor.setName("Светло-красная атака");
        ssaColor.setColor(0xFFFF605A);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_ATTACK_RED_DARK.getKey());
        ssaColor.setName("Темно-красная атака");
        ssaColor.setColor(0xFFF64E48);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_HEALBOX_RED.getKey());
        ssaColor.setName("Красная аптечка");
        ssaColor.setColor(0xFFD22325);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_HEALBOX_YELLOW.getKey());
        ssaColor.setName("Желтая аптечка");
        ssaColor.setColor(0xFFFFF5BF);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_TIMELINE_BLUE.getKey());
        ssaColor.setName("Синий цвет временной области");
        ssaColor.setColor(0xFF5574CE);
        map.put(ssaColor.getKey(), ssaColor);

        ssaColor = new SSA_Color(SSA_Key.COLOR_TIMELINE_GREEN.getKey());
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

        ssaCondition = new SSA_Condition(SSA_Key.COND_SLOTS_PRESENT.getKey());
        ssaCondition.setName("Слоты есть");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_SLOTS.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.001f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_WHITE_PRESENT.getKey());
        ssaCondition.setName("Белый есть");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.001f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_WHITE_MORE_20.getKey());
        ssaCondition.setName("Белый >20%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.200f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_WHITE_MORE_30.getKey());
        ssaCondition.setName("Белый >30%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_WHITE.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.300f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_IS_X2_PRESENT.getKey());
        ssaCondition.setName("Есть желтая скрепка");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_IS_X2.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.001f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_MAY_X2_PRESENT.getKey());
        ssaCondition.setName("Есть серая скрепка");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_MAY_X2.getKey()));
        ssaCondition.setThreshold(5);
        ssaCondition.setMinFrequency(0.001f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_HEALBOX_RED_MORE_50.getKey());
        ssaCondition.setName("Красный хилбокс >50%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_HEALBOX_RED.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.500f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_HEALBOX_RED_MORE_60.getKey());
        ssaCondition.setName("Красный хилбокс >60%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_HEALBOX_RED.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.600f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_HEALBOX_RED_MORE_70.getKey());
        ssaCondition.setName("Красный хилбокс >70%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_HEALBOX_RED.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.700f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_HEALBOX_YELLOW_PRESENT.getKey());
        ssaCondition.setName("Желтый хилбокс есть");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_HEALBOX_YELLOW.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.001f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_GARAGE_INFO_BACKGROUND_MORE_10.getKey());
        ssaCondition.setName("Песочный фон гаража >10%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_GARAGE_INFO_BACKGROUND.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.100f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_HEALTH_RED_PRESENT.getKey());
        ssaCondition.setName("Темно-красный здоровья есть");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_HEALTH_RED_DARK.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.0001f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_SHIELD_BLUE_PRESENT.getKey());
        ssaCondition.setName("Голубой меча есть");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_SHIELD_BLUE.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.0001f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_GRAY_CITY_EARLY_WIN_100.getKey());
        ssaCondition.setName("Серый досрочной победы 100%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_GRAY_CITY_EARLY_WIN.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.990f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_CAR_BLUE_BOX_IN_CITY_100.getKey());
        ssaCondition.setName("Голубой инфобокса машины 100%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_CAR_BLUE_BOX_IN_CITY.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.990f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_ATTACK_RED_LIGHT_MORE_40.getKey());
        ssaCondition.setName("Атака красный светлый >40%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_ATTACK_RED_LIGHT.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.400f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_ATTACK_RED_DARK_MORE_40.getKey());
        ssaCondition.setName("Атака красный темный >40%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_ATTACK_RED_DARK.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.400f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_DEFENCE_BLUE_LIGHT_MORE_40.getKey());
        ssaCondition.setName("Защита синий светлый >40%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_DEFENCE_BLUE_LIGHT.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.400f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaCondition = new SSA_Condition(SSA_Key.COND_DEFENCE_BLUE_DARK_MORE_40.getKey());
        ssaCondition.setName("Защита синий темный >40%");
        ssaCondition.setSsaColor(SSA_Colors.getColor(SSA_Key.COLOR_DEFENCE_BLUE_DARK.getKey()));
        ssaCondition.setThreshold(10);
        ssaCondition.setMinFrequency(0.400f);
        ssaCondition.setMaxFrequency(1.000f);
        map.put(ssaCondition.getKey(), ssaCondition);

        ssaConditions.setMap(map);
        return ssaConditions;
    }

    public static SSA_Rules_Area_Condition getDefaultRulesAreasConditions() {
        SSA_Rules_Area_Condition ssaRulesAreaCondition = new SSA_Rules_Area_Condition();
        Map<String, SSA_Rule_Area_Condition> map = new HashMap<>();
        SSA_Rule_Area_Condition ssaRuleAreaCondition;

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD1_SLOTS_PRESENT.getKey(), "Здание №1 слоты есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD1.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_SLOTS_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD2_SLOTS_PRESENT.getKey(), "Здание №2 слоты есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD2.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_SLOTS_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD3_SLOTS_PRESENT.getKey(), "Здание №3 слоты есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD3.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_SLOTS_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD4_SLOTS_PRESENT.getKey(), "Здание №4 слоты есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD4.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_SLOTS_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD5_SLOTS_PRESENT.getKey(), "Здание №5 слоты есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD5.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_SLOTS_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD6_SLOTS_PRESENT.getKey(), "Здание №6 слоты есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD6.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_SLOTS_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD1_WHITE_MORE_20.getKey(), "Здание №1 белый есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD1.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD2_WHITE_MORE_20.getKey(), "Здание №2 белый есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD2.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD3_WHITE_MORE_20.getKey(), "Здание №3 белый есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD3.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD4_WHITE_MORE_20.getKey(), "Здание №4 белый есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD4.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD5_WHITE_MORE_20.getKey(), "Здание №5 белый есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD5.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD6_WHITE_MORE_20.getKey(), "Здание №6 белый есть", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD6.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);


        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD1_IS_X2.getKey(), "Здание №1 с золотой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD1.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_IS_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD1_MAY_X2.getKey(), "Здание №1 с серой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD1.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_MAY_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD2_IS_X2.getKey(), "Здание №2 с золотой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD2.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_IS_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD2_MAY_X2.getKey(), "Здание №2 с серой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD2.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_MAY_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD3_IS_X2.getKey(), "Здание №3 с золотой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD3.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_IS_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD3_MAY_X2.getKey(), "Здание №3 с серой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD3.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_MAY_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD4_IS_X2.getKey(), "Здание №4 с золотой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD4.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_IS_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD4_MAY_X2.getKey(), "Здание №4 с серой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD4.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_MAY_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD5_IS_X2.getKey(), "Здание №5 с золотой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD5.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_IS_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD5_MAY_X2.getKey(), "Здание №5 с серой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD5.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_MAY_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD6_IS_X2.getKey(), "Здание №6 с золотой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD6.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_IS_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_BLD6_MAY_X2.getKey(), "Здание №6 с серой скрепкой", SSA_Areas.getArea(SSA_Key.AREA_CITY_BLD6.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_MAY_X2_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_GAME_BOX_BACK_RED.getKey(), "Бэкбокс красный", SSA_Areas.getArea(SSA_Key.AREA_GAME_BOX_BACK.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_HEALBOX_RED_MORE_70.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_GAME_BOX_BACK_YELLOW.getKey(), "Бэкбокс желтый", SSA_Areas.getArea(SSA_Key.AREA_GAME_BOX_BACK.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_HEALBOX_YELLOW_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CITY_BOX_INFO_RED.getKey(), "Инфобокс красный", SSA_Areas.getArea(SSA_Key.AREA_CITY_BOX_INFO.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_HEALBOX_RED_MORE_60.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CITY_BOX_INFO_WHITE.getKey(), "Инфобокс желтый", SSA_Areas.getArea(SSA_Key.AREA_CITY_BOX_INFO.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_GARAGE_HSE_BACKGROUND.getKey(), "Инфобокс гаража бэкграунд", SSA_Areas.getArea(SSA_Key.AREA_GARAGE_HEALTH_SHIELD_ENERGY.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_GARAGE_INFO_BACKGROUND_MORE_10.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_GARAGE_HSE_RED.getKey(), "Инфобокс гаража сердце", SSA_Areas.getArea(SSA_Key.AREA_GARAGE_HEALTH_SHIELD_ENERGY.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_HEALTH_RED_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_GARAGE_HSE_BLUE.getKey(), "Инфобокс гаража меч", SSA_Areas.getArea(SSA_Key.AREA_GARAGE_HEALTH_SHIELD_ENERGY.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_SHIELD_BLUE_PRESENT.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CITY_BOX_GRAY.getKey(), "Серый бокс в голоде", SSA_Areas.getArea(SSA_Key.AREA_CITY_BOX_GRAY.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_GRAY_CITY_EARLY_WIN_100.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CAR_BOX_IN_CITY.getKey(), "Голубой бокс машины в городе", SSA_Areas.getArea(SSA_Key.AREA_CAR_BOX_IN_CITY.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_CAR_BLUE_BOX_IN_CITY_100.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CAR_IN_CITY_SLOT1.getKey(), "Машина в городе, слот №1", SSA_Areas.getArea(SSA_Key.AREA_CAR_SLOT1.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CAR_IN_CITY_SLOT2.getKey(), "Машина в городе, слот №2", SSA_Areas.getArea(SSA_Key.AREA_CAR_SLOT2.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CAR_IN_CITY_SLOT3.getKey(), "Машина в городе, слот №3", SSA_Areas.getArea(SSA_Key.AREA_CAR_SLOT3.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CAR_IN_GARAGE_SLOT1.getKey(), "Машина в гараже, слот №1", SSA_Areas.getArea(SSA_Key.AREA_GARAGE_SLOT1.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CAR_IN_GARAGE_SLOT2.getKey(), "Машина в гараже, слот №2", SSA_Areas.getArea(SSA_Key.AREA_GARAGE_SLOT2.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CAR_IN_GARAGE_SLOT3.getKey(), "Машина в гараже, слот №3", SSA_Areas.getArea(SSA_Key.AREA_GARAGE_SLOT3.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CAR_IN_CITY_HEALBOX.getKey(), "Машина в городе, есть хилбокс", SSA_Areas.getArea(SSA_Key.AREA_CAR_HEALBOX.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_HEALBOX_RED_MORE_50.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CAR_IN_CITY_DEFENCE_BREAK_BOX.getKey(), "Машина в городе, есть брейкбокс", SSA_Areas.getArea(SSA_Key.AREA_CAR_BOX_DEFENCE_BREAK.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_20.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_CAR_REPAIRING_IN_DEFENCE.getKey(), "Машина в городе, есть ключ починки", SSA_Areas.getArea(SSA_Key.AREA_CAR_REPAIRING_IN_DEFENCE.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_WHITE_MORE_30.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_ATTACK_DEFENCE_BOX_RED_LIGHT.getKey(), "Экран атаки, светло-красный бокс", SSA_Areas.getArea(SSA_Key.AREA_ATTACK_DEFENCE_BOX.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_ATTACK_RED_LIGHT_MORE_40.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_ATTACK_DEFENCE_BOX_RED_DARK.getKey(), "Экран атаки, темно-красный бокс", SSA_Areas.getArea(SSA_Key.AREA_ATTACK_DEFENCE_BOX.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_ATTACK_RED_DARK_MORE_40.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_ATTACK_DEFENCE_BOX_BLUE_LIGHT.getKey(), "Экран атаки, светло-синий бокс", SSA_Areas.getArea(SSA_Key.AREA_ATTACK_DEFENCE_BOX.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_DEFENCE_BLUE_LIGHT_MORE_40.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRuleAreaCondition = new SSA_Rule_Area_Condition(SSA_Key.RAC_IS_ATTACK_DEFENCE_BOX_BLUE_DARK.getKey(), "Экран атаки, темно-синий бокс", SSA_Areas.getArea(SSA_Key.AREA_ATTACK_DEFENCE_BOX.getKey()), SSA_Conditions.getCondition(SSA_Key.COND_DEFENCE_BLUE_DARK_MORE_40.getKey()));
        map.put(ssaRuleAreaCondition.getKey(), ssaRuleAreaCondition);

        ssaRulesAreaCondition.setMap(map);
        return ssaRulesAreaCondition;
    }

    public static SSA_Rules getDefaultRules() {
        SSA_Rules ssaRules = new SSA_Rules();
        SSA_Rule ssaRule;
        Map<String, SSA_Rule> map = new HashMap<>();
        List<SSA_Rule_Area_Condition> listTrue;
        List<SSA_Rule_Area_Condition> listFalse;
        List<SSA_Rule_Area_Condition> listOneTrue;
        List<SSA_Rule_Area_Condition> listAnyTrue;

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD1_PRESENT.getKey(), "Здание №1 есть");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD1_SLOTS_PRESENT.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD1_WHITE_MORE_20.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD2_PRESENT.getKey(), "Здание №2 есть");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD2_SLOTS_PRESENT.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD2_WHITE_MORE_20.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD3_PRESENT.getKey(), "Здание №3 есть");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD3_SLOTS_PRESENT.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD3_WHITE_MORE_20.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD4_PRESENT.getKey(), "Здание №4 есть");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD4_SLOTS_PRESENT.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD4_WHITE_MORE_20.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD5_PRESENT.getKey(), "Здание №5 есть");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD5_SLOTS_PRESENT.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD5_WHITE_MORE_20.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD6_PRESENT.getKey(), "Здание №6 есть");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD6_SLOTS_PRESENT.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD6_WHITE_MORE_20.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD1_MAY_X2.getKey(), "Здание №1 со скрепкой");
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD1_IS_X2.getKey()));
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD1_MAY_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD2_MAY_X2.getKey(), "Здание №2 со скрепкой");
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD2_IS_X2.getKey()));
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD2_MAY_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD3_MAY_X2.getKey(), "Здание №3 со скрепкой");
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD3_IS_X2.getKey()));
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD3_MAY_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD4_MAY_X2.getKey(), "Здание №4 со скрепкой");
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD4_IS_X2.getKey()));
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD4_MAY_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD5_MAY_X2.getKey(), "Здание №5 со скрепкой");
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD5_IS_X2.getKey()));
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD5_MAY_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD6_MAY_X2.getKey(), "Здание №6 со скрепкой");
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD6_IS_X2.getKey()));
        listOneTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD6_MAY_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD1_IS_X2.getKey(), "Здание №1 с золотой скрепкой");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD1_IS_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD2_IS_X2.getKey(), "Здание №2 с золотой скрепкой");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD2_IS_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD3_IS_X2.getKey(), "Здание №3 с золотой скрепкой");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD3_IS_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD4_IS_X2.getKey(), "Здание №4 с золотой скрепкой");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD4_IS_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD5_IS_X2.getKey(), "Здание №5 с золотой скрепкой");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD5_IS_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_BLD6_IS_X2.getKey(), "Здание №6 с золотой скрепкой");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_BLD6_IS_X2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);


        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_GAME_BOX_BACK.getKey(), "Бэкбокс");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GAME_BOX_BACK_RED.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GAME_BOX_BACK_YELLOW.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CITY_BOX_INFO.getKey(), "Инфобокс");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CITY_BOX_INFO_RED.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CITY_BOX_INFO_WHITE.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_GARAGE_HSE.getKey(), "Инфобокс гаража");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GARAGE_HSE_BACKGROUND.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GARAGE_HSE_RED.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GARAGE_HSE_BLUE.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CITY_BOX_GRAY.getKey(), "Серый бокс в городе");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CITY_BOX_GRAY.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CAR_BOX_IN_CITY.getKey(), "Голубой бокс машины в городе");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_BOX_IN_CITY.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_SCR_IS_GAME_IN_CITY.getKey(), "Скриншот игры в городе");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GAME_BOX_BACK_RED.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GAME_BOX_BACK_YELLOW.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CITY_BOX_INFO_RED.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CITY_BOX_INFO_WHITE.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CITY_BOX_GRAY.getKey()));
        listFalse.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_BOX_IN_CITY.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_SCR_IS_CAR_IN_CITY.getKey(), "Скриншот машины в городе");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GAME_BOX_BACK_RED.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GAME_BOX_BACK_YELLOW.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_BOX_IN_CITY.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_SCR_IS_CAR_IN_GARAGE.getKey(), "Скриншот машины в гараже");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GAME_BOX_BACK_RED.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GAME_BOX_BACK_YELLOW.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GARAGE_HSE_BACKGROUND.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GARAGE_HSE_RED.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_GARAGE_HSE_BLUE.getKey()));
        listFalse.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_BOX_IN_CITY.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CAR_IN_CITY_SLOT1.getKey(), "Машина в городе в слоте №1");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_IN_CITY_SLOT1.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CAR_IN_CITY_SLOT2.getKey(), "Машина в городе в слоте №2");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_IN_CITY_SLOT2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CAR_IN_CITY_SLOT3.getKey(), "Машина в городе в слоте №3");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_IN_CITY_SLOT3.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CAR_IN_GARAGE_SLOT1.getKey(), "Машина в гараже в слоте №1");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_IN_GARAGE_SLOT1.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CAR_IN_GARAGE_SLOT2.getKey(), "Машина в гараже в слоте №2");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_IN_GARAGE_SLOT2.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CAR_IN_GARAGE_SLOT3.getKey(), "Машина в гараже в слоте №3");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_IN_GARAGE_SLOT3.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CAR_IN_CITY_HEALBOX.getKey(), "Машина в городе, есть хилбокс");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_IN_CITY_HEALBOX.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CAR_IN_CITY_DEFENCE_BREAK_BOX.getKey(), "Машина в городе, есть брэйкбокс");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_IN_CITY_DEFENCE_BREAK_BOX.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_IS_CAR_REPAIRING_IN_DEFENCE.getKey(), "Машина в городе, есть ключ починки");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_CAR_REPAIRING_IN_DEFENCE.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_SCR_IS_ATTACK.getKey(), "Скриншот машины в атаке");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_ATTACK_DEFENCE_BOX_RED_LIGHT.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_ATTACK_DEFENCE_BOX_RED_DARK.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        listTrue = new ArrayList<>(); listFalse = new ArrayList<>(); listOneTrue = new ArrayList<>(); listAnyTrue = new ArrayList<>();
        ssaRule = new SSA_Rule(SSA_Key.RULE_SCR_IS_DEFENCE.getKey(), "Скриншот машины в защите");
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_ATTACK_DEFENCE_BOX_BLUE_LIGHT.getKey()));
        listTrue.add(SSA_Rules_Area_Condition.getRuleAreaCondition(SSA_Key.RAC_IS_ATTACK_DEFENCE_BOX_BLUE_DARK.getKey()));
        ssaRule.setListTrue(listTrue); ssaRule.setListFalse(listFalse); ssaRule.setListOneTrue(listOneTrue); ssaRule.setListAnyTrue(listAnyTrue);
        map.put(ssaRule.getKey(), ssaRule);

        ssaRules.setMap(map);
        return ssaRules;
    }
}
