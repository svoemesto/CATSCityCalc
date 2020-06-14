package com.svoemestodev.catscitycalc.database;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.svoemestodev.catscitycalc.activities.GameActivity;
import com.svoemestodev.catscitycalc.citycalcclasses.CCAGame;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbTeamGame {

    private Date timestamp;
    private String userUID;
    private String userNIC;

    private int calibrateX;
    private int calibrateY;

    private Date dateStartGame;     // дата начала игры
    private Date dateScreenshot;    // дата создания скриншота
    private Date dateEndGame;       // дата окончания игры по времени
    private int earlyWin;           // нужно очков до досрочной победы

    private int pointsOurInScreenshot;
    private int pointsEnemyInScreenshot;
    private int increaseOur;
    private int increaseEnemy;

    private boolean isPresent_blt;
    private boolean isPresent_blc;
    private boolean isPresent_blb;
    private boolean isPresent_brt;
    private boolean isPresent_brc;
    private boolean isPresent_brb;

    private boolean isX2_blt;
    private boolean isX2_blc;
    private boolean isX2_blb;
    private boolean isX2_brt;
    private boolean isX2_brc;
    private boolean isX2_brb;

    private boolean mayX2_blt;
    private boolean mayX2_blc;
    private boolean mayX2_blb;
    private boolean mayX2_brt;
    private boolean mayX2_brc;
    private boolean mayX2_brb;

    private boolean buildingIsOur_blt;
    private boolean buildingIsOur_blc;
    private boolean buildingIsOur_blb;
    private boolean buildingIsOur_brt;
    private boolean buildingIsOur_brc;
    private boolean buildingIsOur_brb;

    private boolean buildingIsEmpty_blt;
    private boolean buildingIsEmpty_blc;
    private boolean buildingIsEmpty_blb;
    private boolean buildingIsEmpty_brt;
    private boolean buildingIsEmpty_brc;
    private boolean buildingIsEmpty_brb;

    private boolean buildingIsEnemy_blt;
    private boolean buildingIsEnemy_blc;
    private boolean buildingIsEnemy_blb;
    private boolean buildingIsEnemy_brt;
    private boolean buildingIsEnemy_brc;
    private boolean buildingIsEnemy_brb;

    private int our_points_blt;
    private int our_points_blc;
    private int our_points_blb;
    private int our_points_brt;
    private int our_points_brc;
    private int our_points_brb;

    private int enemy_points_blt;
    private int enemy_points_blc;
    private int enemy_points_blb;
    private int enemy_points_brt;
    private int enemy_points_brc;
    private int enemy_points_brb;

    private int slots_blt;
    private int slots_blt_our;
    private int slots_blt_empty;
    private int slots_blt_enemy;
    private int slots_blc;
    private int slots_blc_our;
    private int slots_blc_empty;
    private int slots_blc_enemy;
    private int slots_blb;
    private int slots_blb_our;
    private int slots_blb_empty;
    private int slots_blb_enemy;
    private int slots_brt;
    private int slots_brt_our;
    private int slots_brt_empty;
    private int slots_brt_enemy;
    private int slots_brc;
    private int slots_brc_our;
    private int slots_brc_empty;
    private int slots_brc_enemy;
    private int slots_brb;
    private int slots_brb_our;
    private int slots_brb_empty;
    private int slots_brb_enemy;

    private static final String TAG = "DbTeamGame";

    public DbTeamGame() {
    }

    public DbTeamGame(DocumentSnapshot documentSnapshot) {

        String key;
        Map<String, Object> map = documentSnapshot.getData();
        key = "userNIC"; if (map.containsKey(key)) this.userNIC = map.get(key).toString();
        key = "userUID"; if (map.containsKey(key)) this.userUID = map.get(key).toString();

        key = "calibrateX"; if (map.containsKey(key)) this.calibrateX = ((Long) map.get(key)).intValue();
        key = "calibrateY"; if (map.containsKey(key)) this.calibrateY = ((Long) map.get(key)).intValue();

        key = "dateStartGame"; if (map.containsKey(key)) this.dateStartGame = documentSnapshot.getTimestamp(key).toDate();
        key = "dateScreenshot"; if (map.containsKey(key)) this.dateScreenshot = documentSnapshot.getTimestamp(key).toDate();
        key = "dateEndGame"; if (map.containsKey(key)) this.dateEndGame = documentSnapshot.getTimestamp(key).toDate();
        key = "earlyWin"; if (map.containsKey(key)) this.earlyWin = ((Long) map.get(key)).intValue();

        key = "pointsOurInScreenshot"; if (map.containsKey(key)) this.pointsOurInScreenshot = ((Long) map.get(key)).intValue();
        key = "pointsEnemyInScreenshot"; if (map.containsKey(key)) this.pointsEnemyInScreenshot = ((Long) map.get(key)).intValue();

        key = "isPresent_blt"; if (map.containsKey(key)) this.isPresent_blt = (boolean) map.get(key);
        key = "isPresent_blc"; if (map.containsKey(key)) this.isPresent_blc = (boolean) map.get(key);
        key = "isPresent_blb"; if (map.containsKey(key)) this.isPresent_blb = (boolean) map.get(key);
        key = "isPresent_brt"; if (map.containsKey(key)) this.isPresent_brt = (boolean) map.get(key);
        key = "isPresent_brc"; if (map.containsKey(key)) this.isPresent_brc = (boolean) map.get(key);
        key = "isPresent_brb"; if (map.containsKey(key)) this.isPresent_brb = (boolean) map.get(key);
        
        key = "isX2_blt"; if (map.containsKey(key)) this.isX2_blt = (boolean) map.get(key);
        key = "isX2_blc"; if (map.containsKey(key)) this.isX2_blc = (boolean) map.get(key);
        key = "isX2_blb"; if (map.containsKey(key)) this.isX2_blb = (boolean) map.get(key);
        key = "isX2_brt"; if (map.containsKey(key)) this.isX2_brt = (boolean) map.get(key);
        key = "isX2_brc"; if (map.containsKey(key)) this.isX2_brc = (boolean) map.get(key);
        key = "isX2_brb"; if (map.containsKey(key)) this.isX2_brb = (boolean) map.get(key);
        
        key = "mayX2_blt"; if (map.containsKey(key)) this.mayX2_blt = (boolean) map.get(key);
        key = "mayX2_blc"; if (map.containsKey(key)) this.mayX2_blc = (boolean) map.get(key);
        key = "mayX2_blb"; if (map.containsKey(key)) this.mayX2_blb = (boolean) map.get(key);
        key = "mayX2_brt"; if (map.containsKey(key)) this.mayX2_brt = (boolean) map.get(key);
        key = "mayX2_brc"; if (map.containsKey(key)) this.mayX2_brc = (boolean) map.get(key);
        key = "mayX2_brb"; if (map.containsKey(key)) this.mayX2_brb = (boolean) map.get(key);
        
        key = "buildingIsOur_blt"; if (map.containsKey(key)) this.buildingIsOur_blt = (boolean) map.get(key);
        key = "buildingIsOur_blc"; if (map.containsKey(key)) this.buildingIsOur_blc = (boolean) map.get(key);
        key = "buildingIsOur_blb"; if (map.containsKey(key)) this.buildingIsOur_blb = (boolean) map.get(key);
        key = "buildingIsOur_brt"; if (map.containsKey(key)) this.buildingIsOur_brt = (boolean) map.get(key);
        key = "buildingIsOur_brc"; if (map.containsKey(key)) this.buildingIsOur_brc = (boolean) map.get(key);
        key = "buildingIsOur_brb"; if (map.containsKey(key)) this.buildingIsOur_brb = (boolean) map.get(key);
        
        key = "buildingIsEmpty_blt"; if (map.containsKey(key)) this.buildingIsEmpty_blt = (boolean) map.get(key);
        key = "buildingIsEmpty_blc"; if (map.containsKey(key)) this.buildingIsEmpty_blc = (boolean) map.get(key);
        key = "buildingIsEmpty_blb"; if (map.containsKey(key)) this.buildingIsEmpty_blb = (boolean) map.get(key);
        key = "buildingIsEmpty_brt"; if (map.containsKey(key)) this.buildingIsEmpty_brt = (boolean) map.get(key);
        key = "buildingIsEmpty_brc"; if (map.containsKey(key)) this.buildingIsEmpty_brc = (boolean) map.get(key);
        key = "buildingIsEmpty_brb"; if (map.containsKey(key)) this.buildingIsEmpty_brb = (boolean) map.get(key);
        
        
        key = "buildingIsEnemy_blt"; if (map.containsKey(key)) this.buildingIsEnemy_blt = (boolean) map.get(key);
        key = "buildingIsEnemy_blc"; if (map.containsKey(key)) this.buildingIsEnemy_blc = (boolean) map.get(key);
        key = "buildingIsEnemy_blb"; if (map.containsKey(key)) this.buildingIsEnemy_blb = (boolean) map.get(key);
        key = "buildingIsEnemy_brt"; if (map.containsKey(key)) this.buildingIsEnemy_brt = (boolean) map.get(key);
        key = "buildingIsEnemy_brc"; if (map.containsKey(key)) this.buildingIsEnemy_brc = (boolean) map.get(key);
        key = "buildingIsEnemy_brb"; if (map.containsKey(key)) this.buildingIsEnemy_brb = (boolean) map.get(key);
        
        key = "our_points_blt"; if (map.containsKey(key)) this.our_points_blt = ((Long) map.get(key)).intValue();
        key = "our_points_blc"; if (map.containsKey(key)) this.our_points_blc = ((Long) map.get(key)).intValue();
        key = "our_points_blb"; if (map.containsKey(key)) this.our_points_blb = ((Long) map.get(key)).intValue();
        key = "our_points_brt"; if (map.containsKey(key)) this.our_points_brt = ((Long) map.get(key)).intValue();
        key = "our_points_brc"; if (map.containsKey(key)) this.our_points_brc = ((Long) map.get(key)).intValue();
        key = "our_points_brb"; if (map.containsKey(key)) this.our_points_brb = ((Long) map.get(key)).intValue();

        key = "enemy_points_blt"; if (map.containsKey(key)) this.enemy_points_blt = ((Long) map.get(key)).intValue();
        key = "enemy_points_blc"; if (map.containsKey(key)) this.enemy_points_blc = ((Long) map.get(key)).intValue();
        key = "enemy_points_blb"; if (map.containsKey(key)) this.enemy_points_blb = ((Long) map.get(key)).intValue();
        key = "enemy_points_brt"; if (map.containsKey(key)) this.enemy_points_brt = ((Long) map.get(key)).intValue();
        key = "enemy_points_brc"; if (map.containsKey(key)) this.enemy_points_brc = ((Long) map.get(key)).intValue();
        key = "enemy_points_brb"; if (map.containsKey(key)) this.enemy_points_brb = ((Long) map.get(key)).intValue();

        
        key = "slots_blt"; if (map.containsKey(key)) this.slots_blt = ((Long) map.get(key)).intValue();
        key = "slots_blt_our"; if (map.containsKey(key)) this.slots_blt_our = ((Long) map.get(key)).intValue();
        key = "slots_blt_empty"; if (map.containsKey(key)) this.slots_blt_empty = ((Long) map.get(key)).intValue();
        key = "slots_blt_enemy"; if (map.containsKey(key)) this.slots_blt_enemy = ((Long) map.get(key)).intValue();

        key = "slots_blc"; if (map.containsKey(key)) this.slots_blc = ((Long) map.get(key)).intValue();
        key = "slots_blc_our"; if (map.containsKey(key)) this.slots_blc_our = ((Long) map.get(key)).intValue();
        key = "slots_blc_empty"; if (map.containsKey(key)) this.slots_blc_empty = ((Long) map.get(key)).intValue();
        key = "slots_blc_enemy"; if (map.containsKey(key)) this.slots_blc_enemy = ((Long) map.get(key)).intValue();

        key = "slots_blb"; if (map.containsKey(key)) this.slots_blb = ((Long) map.get(key)).intValue();
        key = "slots_blb_our"; if (map.containsKey(key)) this.slots_blb_our = ((Long) map.get(key)).intValue();
        key = "slots_blb_empty"; if (map.containsKey(key)) this.slots_blb_empty = ((Long) map.get(key)).intValue();
        key = "slots_blb_enemy"; if (map.containsKey(key)) this.slots_blb_enemy = ((Long) map.get(key)).intValue();

        key = "slots_brt"; if (map.containsKey(key)) this.slots_brt = ((Long) map.get(key)).intValue();
        key = "slots_brt_our"; if (map.containsKey(key)) this.slots_brt_our = ((Long) map.get(key)).intValue();
        key = "slots_brt_empty"; if (map.containsKey(key)) this.slots_brt_empty = ((Long) map.get(key)).intValue();
        key = "slots_brt_enemy"; if (map.containsKey(key)) this.slots_brt_enemy = ((Long) map.get(key)).intValue();

        key = "slots_brc"; if (map.containsKey(key)) this.slots_brc = ((Long) map.get(key)).intValue();
        key = "slots_brc_our"; if (map.containsKey(key)) this.slots_brc_our = ((Long) map.get(key)).intValue();
        key = "slots_brc_empty"; if (map.containsKey(key)) this.slots_brc_empty = ((Long) map.get(key)).intValue();
        key = "slots_brc_enemy"; if (map.containsKey(key)) this.slots_brc_enemy = ((Long) map.get(key)).intValue();

        key = "slots_brb"; if (map.containsKey(key)) this.slots_brb = ((Long) map.get(key)).intValue();
        key = "slots_brb_our"; if (map.containsKey(key)) this.slots_brb_our = ((Long) map.get(key)).intValue();
        key = "slots_brb_empty"; if (map.containsKey(key)) this.slots_brb_empty = ((Long) map.get(key)).intValue();
        key = "slots_brb_enemy"; if (map.containsKey(key)) this.slots_brb_enemy = ((Long) map.get(key)).intValue();

    }
    
    public DbTeamGame(CCAGame ccaGame) {

        this.dateStartGame = ccaGame.getCcagDateStartGame();
        this.dateScreenshot = ccaGame.getCcagDateScreenshot();    
        this.dateEndGame = ccaGame.getCcagDateEndGame();       
        this.earlyWin = ccaGame.getCcagEarlyWin();           

        this.calibrateX = GameActivity.calibrateX;
        this.calibrateY = GameActivity.calibrateY;

        this.pointsOurInScreenshot = ccaGame.getCcagPointsOurInScreenshot();
        this.pointsEnemyInScreenshot = ccaGame.getCcagPointsEnemyInScreenshot();
        this.increaseOur = ccaGame.getCcagIncreaseOur();
        this.increaseEnemy = ccaGame.getCcagIncreaseEnemy();

        this.isPresent_blt = ccaGame.isPresent_blt();
        this.isPresent_blc = ccaGame.isPresent_blc();
        this.isPresent_blb = ccaGame.isPresent_blb();
        this.isPresent_brt = ccaGame.isPresent_brt();
        this.isPresent_brc = ccaGame.isPresent_brc();
        this.isPresent_brb = ccaGame.isPresent_brb();

        this.isX2_blt = ccaGame.isX2_blt();
        this.isX2_blc = ccaGame.isX2_blc();
        this.isX2_blb = ccaGame.isX2_blb();
        this.isX2_brt = ccaGame.isX2_brt();
        this.isX2_brc = ccaGame.isX2_brc();
        this.isX2_brb = ccaGame.isX2_brb();

        this.mayX2_blt = ccaGame.isMayX2_blt();
        this.mayX2_blc = ccaGame.isMayX2_blc();
        this.mayX2_blb = ccaGame.isMayX2_blb();
        this.mayX2_brt = ccaGame.isMayX2_brt();
        this.mayX2_brc = ccaGame.isMayX2_brc();
        this.mayX2_brb = ccaGame.isMayX2_brb();

        this.buildingIsOur_blt = ccaGame.isBuildingIsOur_blt();
        this.buildingIsOur_blc = ccaGame.isBuildingIsOur_blc();
        this.buildingIsOur_blb = ccaGame.isBuildingIsOur_blb();
        this.buildingIsOur_brt = ccaGame.isBuildingIsOur_brt();
        this.buildingIsOur_brc = ccaGame.isBuildingIsOur_brc();
        this.buildingIsOur_brb = ccaGame.isBuildingIsOur_brb();

        this.buildingIsEmpty_blt = ccaGame.isBuildingIsEmpty_blt();
        this.buildingIsEmpty_blc = ccaGame.isBuildingIsEmpty_blc();
        this.buildingIsEmpty_blb = ccaGame.isBuildingIsEmpty_blb();
        this.buildingIsEmpty_brt = ccaGame.isBuildingIsEmpty_brt();
        this.buildingIsEmpty_brc = ccaGame.isBuildingIsEmpty_brc();
        this.buildingIsEmpty_brb = ccaGame.isBuildingIsEmpty_brb();

        this.buildingIsEnemy_blt = ccaGame.isBuildingIsEnemy_blt();
        this.buildingIsEnemy_blc = ccaGame.isBuildingIsEnemy_blc();
        this.buildingIsEnemy_blb = ccaGame.isBuildingIsEnemy_blb();
        this.buildingIsEnemy_brt = ccaGame.isBuildingIsEnemy_brt();
        this.buildingIsEnemy_brc = ccaGame.isBuildingIsEnemy_brc();
        this.buildingIsEnemy_brb = ccaGame.isBuildingIsEnemy_brb();

        this.our_points_blt = ccaGame.getOur_points_blt();
        this.our_points_blc = ccaGame.getOur_points_blc();
        this.our_points_blb = ccaGame.getOur_points_blb();
        this.our_points_brt = ccaGame.getOur_points_brt();
        this.our_points_brc = ccaGame.getOur_points_brc();
        this.our_points_brb = ccaGame.getOur_points_brb();

        this.enemy_points_blt = ccaGame.getEnemy_points_blt();
        this.enemy_points_blc = ccaGame.getEnemy_points_blc();
        this.enemy_points_blb = ccaGame.getEnemy_points_blb();
        this.enemy_points_brt = ccaGame.getEnemy_points_brt();
        this.enemy_points_brc = ccaGame.getEnemy_points_brc();
        this.enemy_points_brb = ccaGame.getEnemy_points_brb();

        this.slots_blt = ccaGame.getSlots_blt();
        this.slots_blt_our = ccaGame.getSlots_blt_our();
        this.slots_blt_empty = ccaGame.getSlots_blt_empty();
        this.slots_blt_enemy = ccaGame.getSlots_blt_enemy();
        this.slots_blc = ccaGame.getSlots_blc();
        this.slots_blc_our = ccaGame.getSlots_blc_our();
        this.slots_blc_empty = ccaGame.getSlots_blc_empty();
        this.slots_blc_enemy = ccaGame.getSlots_blc_enemy();
        this.slots_blb = ccaGame.getSlots_blb();
        this.slots_blb_our = ccaGame.getSlots_blb_our();
        this.slots_blb_empty = ccaGame.getSlots_blb_empty();
        this.slots_blb_enemy = ccaGame.getSlots_blb_enemy();
        this.slots_brt = ccaGame.getSlots_brt();
        this.slots_brt_our = ccaGame.getSlots_brt_our();
        this.slots_brt_empty = ccaGame.getSlots_brt_empty();
        this.slots_brt_enemy = ccaGame.getSlots_brt_enemy();
        this.slots_brc = ccaGame.getSlots_brc();
        this.slots_brc_our = ccaGame.getSlots_brc_our();
        this.slots_brc_empty = ccaGame.getSlots_brc_empty();
        this.slots_brc_enemy = ccaGame.getSlots_brc_enemy();
        this.slots_brb = ccaGame.getSlots_brb();
        this.slots_brb_our = ccaGame.getSlots_brb_our();
        this.slots_brb_empty = ccaGame.getSlots_brb_empty();
        this.slots_brb_enemy = ccaGame.getSlots_brb_enemy();

        if (GameActivity.fbUser != null) { // есть юзер
            if (GameActivity.fbUser.isEmailVerified()) { // емейл подтвержден
                final String userUID = GameActivity.fbUser.getUid();
                GameActivity.fbDb.collection("users").document(userUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        // есть запись о юзере - сохраняем скрин на сервере по пути "users/userUID/last_screenshot"
                        Uri uriFile = Uri.fromFile(GameActivity.mainCityCalc.getFileScreenshot());
                        String storRefGamePathOnServer = "users/" + userUID + "/last_screenshot";
                        StorageReference storRefGame = GameActivity.fbStor.getReference().child(storRefGamePathOnServer);
                        storRefGame.putFile(uriFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // скрин удачно залился на сервер - можно заливать инфу об игре

                                final String teamID = (String)documentSnapshot.get("teamID");
                                final String userNIC = (String)documentSnapshot.get("userNIC");
                                if (teamID != null && !teamID.equals("")) {

                                    DocumentReference doc = GameActivity.fbDb.collection("teams").document(teamID).collection("teamGames").document("teamGame");
                                    doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                if (task.getResult().exists()) {
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    Map<String, Object> map = getMap(userUID, userNIC);
                                                    if (documentSnapshot.getTimestamp("dateScreenshot").toDate().getTime() < ((Date)map.get("dateScreenshot")).getTime()) {
                                                        GameActivity.fbDb.collection("teams").document(teamID).collection("teamGames").document("teamGame").set(getMap(userUID, userNIC)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.i(TAG, "added");
                                                            }
                                                        });
                                                    }
                                                } else {
                                                    GameActivity.fbDb.collection("teams").document(teamID).collection("teamGames").document("teamGame").set(getMap(userUID, userNIC)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.i(TAG, "added");
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });

                                }

                            }
                        });

                    }
                });
            }
        }

    }

    public Map<String, Object> getMap(String userUID, String userNIC) {
        Map<String, Object> map = new HashMap<>();

        map.put("timestamp", FieldValue.serverTimestamp());
        map.put("userUID", userUID);
        map.put("userNIC", userNIC);

        map.put("calibrateX", calibrateX);
        map.put("calibrateY", calibrateY);

        map.put("dateStartGame", dateStartGame);
        map.put("dateScreenshot", dateScreenshot);    
        map.put("dateEndGame", dateEndGame);       
        map.put("earlyWin", earlyWin);           

        map.put("pointsOurInScreenshot", pointsOurInScreenshot);
        map.put("pointsEnemyInScreenshot", pointsEnemyInScreenshot);
        map.put("increaseOur", increaseOur);
        map.put("increaseEnemy", increaseEnemy);

        map.put("isPresent_blt", isPresent_blt);
        map.put("isPresent_blc", isPresent_blc);
        map.put("isPresent_blb", isPresent_blb);
        map.put("isPresent_brt", isPresent_brt);
        map.put("isPresent_brc", isPresent_brc);
        map.put("isPresent_brb", isPresent_brb);

        map.put("isX2_blt", isX2_blt);
        map.put("isX2_blc", isX2_blc);
        map.put("isX2_blb", isX2_blb);
        map.put("isX2_brt", isX2_brt);
        map.put("isX2_brc", isX2_brc);
        map.put("isX2_brb", isX2_brb);

        map.put("mayX2_blt", mayX2_blt);
        map.put("mayX2_blc", mayX2_blc);
        map.put("mayX2_blb", mayX2_blb);
        map.put("mayX2_brt", mayX2_brt);
        map.put("mayX2_brc", mayX2_brc);
        map.put("mayX2_brb", mayX2_brb);

        map.put("buildingIsOur_blt", buildingIsOur_blt);
        map.put("buildingIsOur_blc", buildingIsOur_blc);
        map.put("buildingIsOur_blb", buildingIsOur_blb);
        map.put("buildingIsOur_brt", buildingIsOur_brt);
        map.put("buildingIsOur_brc", buildingIsOur_brc);
        map.put("buildingIsOur_brb", buildingIsOur_brb);

        map.put("buildingIsEmpty_blt", buildingIsEmpty_blt);
        map.put("buildingIsEmpty_blc", buildingIsEmpty_blc);
        map.put("buildingIsEmpty_blb", buildingIsEmpty_blb);
        map.put("buildingIsEmpty_brt", buildingIsEmpty_brt);
        map.put("buildingIsEmpty_brc", buildingIsEmpty_brc);
        map.put("buildingIsEmpty_brb", buildingIsEmpty_brb);

        map.put("buildingIsEnemy_blt", buildingIsEnemy_blt);
        map.put("buildingIsEnemy_blc", buildingIsEnemy_blc);
        map.put("buildingIsEnemy_blb", buildingIsEnemy_blb);
        map.put("buildingIsEnemy_brt", buildingIsEnemy_brt);
        map.put("buildingIsEnemy_brc", buildingIsEnemy_brc);
        map.put("buildingIsEnemy_brb", buildingIsEnemy_brb);

        map.put("our_points_blt", our_points_blt);
        map.put("our_points_blc", our_points_blc);
        map.put("our_points_blb", our_points_blb);
        map.put("our_points_brt", our_points_brt);
        map.put("our_points_brc", our_points_brc);
        map.put("our_points_brb", our_points_brb);

        map.put("enemy_points_blt", enemy_points_blt);
        map.put("enemy_points_blc", enemy_points_blc);
        map.put("enemy_points_blb", enemy_points_blb);
        map.put("enemy_points_brt", enemy_points_brt);
        map.put("enemy_points_brc", enemy_points_brc);
        map.put("enemy_points_brb", enemy_points_brb);

        map.put("slots_blt", slots_blt);
        map.put("slots_blt_our", slots_blt_our);
        map.put("slots_blt_empty", slots_blt_empty);
        map.put("slots_blt_enemy", slots_blt_enemy);
        map.put("slots_blc", slots_blc);
        map.put("slots_blc_our", slots_blc_our);
        map.put("slots_blc_empty", slots_blc_empty);
        map.put("slots_blc_enemy", slots_blc_enemy);
        map.put("slots_blb", slots_blb);
        map.put("slots_blb_our", slots_blb_our);
        map.put("slots_blb_empty", slots_blb_empty);
        map.put("slots_blb_enemy", slots_blb_enemy);
        map.put("slots_brt", slots_brt);
        map.put("slots_brt_our", slots_brt_our);
        map.put("slots_brt_empty", slots_brt_empty);
        map.put("slots_brt_enemy", slots_brt_enemy);
        map.put("slots_brc", slots_brc);
        map.put("slots_brc_our", slots_brc_our);
        map.put("slots_brc_empty", slots_brc_empty);
        map.put("slots_brc_enemy", slots_brc_enemy);
        map.put("slots_brb", slots_brb);
        map.put("slots_brb_our", slots_brb_our);
        map.put("slots_brb_empty", slots_brb_empty);
        map.put("slots_brb_enemy", slots_brb_enemy);

        return map;
    }

    public String getUserNIC() {
        return userNIC;
    }

    public void setUserNIC(String userNIC) {
        this.userNIC = userNIC;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public Date getDateStartGame() {
        return dateStartGame;
    }

    public void setDateStartGame(Date dateStartGame) {
        this.dateStartGame = dateStartGame;
    }

    public Date getDateScreenshot() {
        return dateScreenshot;
    }

    public void setDateScreenshot(Date dateScreenshot) {
        this.dateScreenshot = dateScreenshot;
    }

    public Date getDateEndGame() {
        return dateEndGame;
    }

    public void setDateEndGame(Date dateEndGame) {
        this.dateEndGame = dateEndGame;
    }

    public int getEarlyWin() {
        return earlyWin;
    }

    public void setEarlyWin(int earlyWin) {
        this.earlyWin = earlyWin;
    }

    public int getPointsOurInScreenshot() {
        return pointsOurInScreenshot;
    }

    public void setPointsOurInScreenshot(int pointsOurInScreenshot) {
        this.pointsOurInScreenshot = pointsOurInScreenshot;
    }

    public int getPointsEnemyInScreenshot() {
        return pointsEnemyInScreenshot;
    }

    public void setPointsEnemyInScreenshot(int pointsEnemyInScreenshot) {
        this.pointsEnemyInScreenshot = pointsEnemyInScreenshot;
    }

    public int getIncreaseOur() {
        return increaseOur;
    }

    public void setIncreaseOur(int increaseOur) {
        this.increaseOur = increaseOur;
    }

    public int getIncreaseEnemy() {
        return increaseEnemy;
    }

    public void setIncreaseEnemy(int increaseEnemy) {
        this.increaseEnemy = increaseEnemy;
    }

    public boolean isPresent_blt() {
        return isPresent_blt;
    }

    public void setPresent_blt(boolean present_blt) {
        isPresent_blt = present_blt;
    }

    public boolean isPresent_blc() {
        return isPresent_blc;
    }

    public void setPresent_blc(boolean present_blc) {
        isPresent_blc = present_blc;
    }

    public boolean isPresent_blb() {
        return isPresent_blb;
    }

    public void setPresent_blb(boolean present_blb) {
        isPresent_blb = present_blb;
    }

    public boolean isPresent_brt() {
        return isPresent_brt;
    }

    public void setPresent_brt(boolean present_brt) {
        isPresent_brt = present_brt;
    }

    public boolean isPresent_brc() {
        return isPresent_brc;
    }

    public void setPresent_brc(boolean present_brc) {
        isPresent_brc = present_brc;
    }

    public boolean isPresent_brb() {
        return isPresent_brb;
    }

    public void setPresent_brb(boolean present_brb) {
        isPresent_brb = present_brb;
    }

    public boolean isX2_blt() {
        return isX2_blt;
    }

    public void setX2_blt(boolean x2_blt) {
        isX2_blt = x2_blt;
    }

    public boolean isX2_blc() {
        return isX2_blc;
    }

    public void setX2_blc(boolean x2_blc) {
        isX2_blc = x2_blc;
    }

    public boolean isX2_blb() {
        return isX2_blb;
    }

    public void setX2_blb(boolean x2_blb) {
        isX2_blb = x2_blb;
    }

    public boolean isX2_brt() {
        return isX2_brt;
    }

    public void setX2_brt(boolean x2_brt) {
        isX2_brt = x2_brt;
    }

    public boolean isX2_brc() {
        return isX2_brc;
    }

    public void setX2_brc(boolean x2_brc) {
        isX2_brc = x2_brc;
    }

    public boolean isX2_brb() {
        return isX2_brb;
    }

    public void setX2_brb(boolean x2_brb) {
        isX2_brb = x2_brb;
    }

    public boolean isMayX2_blt() {
        return mayX2_blt;
    }

    public void setMayX2_blt(boolean mayX2_blt) {
        this.mayX2_blt = mayX2_blt;
    }

    public boolean isMayX2_blc() {
        return mayX2_blc;
    }

    public void setMayX2_blc(boolean mayX2_blc) {
        this.mayX2_blc = mayX2_blc;
    }

    public boolean isMayX2_blb() {
        return mayX2_blb;
    }

    public void setMayX2_blb(boolean mayX2_blb) {
        this.mayX2_blb = mayX2_blb;
    }

    public boolean isMayX2_brt() {
        return mayX2_brt;
    }

    public void setMayX2_brt(boolean mayX2_brt) {
        this.mayX2_brt = mayX2_brt;
    }

    public boolean isMayX2_brc() {
        return mayX2_brc;
    }

    public void setMayX2_brc(boolean mayX2_brc) {
        this.mayX2_brc = mayX2_brc;
    }

    public boolean isMayX2_brb() {
        return mayX2_brb;
    }

    public void setMayX2_brb(boolean mayX2_brb) {
        this.mayX2_brb = mayX2_brb;
    }

    public boolean isBuildingIsOur_blt() {
        return buildingIsOur_blt;
    }

    public void setBuildingIsOur_blt(boolean buildingIsOur_blt) {
        this.buildingIsOur_blt = buildingIsOur_blt;
    }

    public boolean isBuildingIsOur_blc() {
        return buildingIsOur_blc;
    }

    public void setBuildingIsOur_blc(boolean buildingIsOur_blc) {
        this.buildingIsOur_blc = buildingIsOur_blc;
    }

    public boolean isBuildingIsOur_blb() {
        return buildingIsOur_blb;
    }

    public void setBuildingIsOur_blb(boolean buildingIsOur_blb) {
        this.buildingIsOur_blb = buildingIsOur_blb;
    }

    public boolean isBuildingIsOur_brt() {
        return buildingIsOur_brt;
    }

    public void setBuildingIsOur_brt(boolean buildingIsOur_brt) {
        this.buildingIsOur_brt = buildingIsOur_brt;
    }

    public boolean isBuildingIsOur_brc() {
        return buildingIsOur_brc;
    }

    public void setBuildingIsOur_brc(boolean buildingIsOur_brc) {
        this.buildingIsOur_brc = buildingIsOur_brc;
    }

    public boolean isBuildingIsOur_brb() {
        return buildingIsOur_brb;
    }

    public void setBuildingIsOur_brb(boolean buildingIsOur_brb) {
        this.buildingIsOur_brb = buildingIsOur_brb;
    }

    public boolean isBuildingIsEmpty_blt() {
        return buildingIsEmpty_blt;
    }

    public void setBuildingIsEmpty_blt(boolean buildingIsEmpty_blt) {
        this.buildingIsEmpty_blt = buildingIsEmpty_blt;
    }

    public boolean isBuildingIsEmpty_blc() {
        return buildingIsEmpty_blc;
    }

    public void setBuildingIsEmpty_blc(boolean buildingIsEmpty_blc) {
        this.buildingIsEmpty_blc = buildingIsEmpty_blc;
    }

    public boolean isBuildingIsEmpty_blb() {
        return buildingIsEmpty_blb;
    }

    public void setBuildingIsEmpty_blb(boolean buildingIsEmpty_blb) {
        this.buildingIsEmpty_blb = buildingIsEmpty_blb;
    }

    public boolean isBuildingIsEmpty_brt() {
        return buildingIsEmpty_brt;
    }

    public void setBuildingIsEmpty_brt(boolean buildingIsEmpty_brt) {
        this.buildingIsEmpty_brt = buildingIsEmpty_brt;
    }

    public boolean isBuildingIsEmpty_brc() {
        return buildingIsEmpty_brc;
    }

    public void setBuildingIsEmpty_brc(boolean buildingIsEmpty_brc) {
        this.buildingIsEmpty_brc = buildingIsEmpty_brc;
    }

    public boolean isBuildingIsEmpty_brb() {
        return buildingIsEmpty_brb;
    }

    public void setBuildingIsEmpty_brb(boolean buildingIsEmpty_brb) {
        this.buildingIsEmpty_brb = buildingIsEmpty_brb;
    }

    public boolean isBuildingIsEnemy_blt() {
        return buildingIsEnemy_blt;
    }

    public void setBuildingIsEnemy_blt(boolean buildingIsEnemy_blt) {
        this.buildingIsEnemy_blt = buildingIsEnemy_blt;
    }

    public boolean isBuildingIsEnemy_blc() {
        return buildingIsEnemy_blc;
    }

    public void setBuildingIsEnemy_blc(boolean buildingIsEnemy_blc) {
        this.buildingIsEnemy_blc = buildingIsEnemy_blc;
    }

    public boolean isBuildingIsEnemy_blb() {
        return buildingIsEnemy_blb;
    }

    public void setBuildingIsEnemy_blb(boolean buildingIsEnemy_blb) {
        this.buildingIsEnemy_blb = buildingIsEnemy_blb;
    }

    public boolean isBuildingIsEnemy_brt() {
        return buildingIsEnemy_brt;
    }

    public void setBuildingIsEnemy_brt(boolean buildingIsEnemy_brt) {
        this.buildingIsEnemy_brt = buildingIsEnemy_brt;
    }

    public boolean isBuildingIsEnemy_brc() {
        return buildingIsEnemy_brc;
    }

    public void setBuildingIsEnemy_brc(boolean buildingIsEnemy_brc) {
        this.buildingIsEnemy_brc = buildingIsEnemy_brc;
    }

    public boolean isBuildingIsEnemy_brb() {
        return buildingIsEnemy_brb;
    }

    public void setBuildingIsEnemy_brb(boolean buildingIsEnemy_brb) {
        this.buildingIsEnemy_brb = buildingIsEnemy_brb;
    }

    public int getOur_points_blt() {
        return our_points_blt;
    }

    public void setOur_points_blt(int our_points_blt) {
        this.our_points_blt = our_points_blt;
    }

    public int getOur_points_blc() {
        return our_points_blc;
    }

    public void setOur_points_blc(int our_points_blc) {
        this.our_points_blc = our_points_blc;
    }

    public int getOur_points_blb() {
        return our_points_blb;
    }

    public void setOur_points_blb(int our_points_blb) {
        this.our_points_blb = our_points_blb;
    }

    public int getOur_points_brt() {
        return our_points_brt;
    }

    public void setOur_points_brt(int our_points_brt) {
        this.our_points_brt = our_points_brt;
    }

    public int getOur_points_brc() {
        return our_points_brc;
    }

    public void setOur_points_brc(int our_points_brc) {
        this.our_points_brc = our_points_brc;
    }

    public int getOur_points_brb() {
        return our_points_brb;
    }

    public void setOur_points_brb(int our_points_brb) {
        this.our_points_brb = our_points_brb;
    }

    public int getEnemy_points_blt() {
        return enemy_points_blt;
    }

    public void setEnemy_points_blt(int enemy_points_blt) {
        this.enemy_points_blt = enemy_points_blt;
    }

    public int getEnemy_points_blc() {
        return enemy_points_blc;
    }

    public void setEnemy_points_blc(int enemy_points_blc) {
        this.enemy_points_blc = enemy_points_blc;
    }

    public int getEnemy_points_blb() {
        return enemy_points_blb;
    }

    public void setEnemy_points_blb(int enemy_points_blb) {
        this.enemy_points_blb = enemy_points_blb;
    }

    public int getEnemy_points_brt() {
        return enemy_points_brt;
    }

    public void setEnemy_points_brt(int enemy_points_brt) {
        this.enemy_points_brt = enemy_points_brt;
    }

    public int getEnemy_points_brc() {
        return enemy_points_brc;
    }

    public void setEnemy_points_brc(int enemy_points_brc) {
        this.enemy_points_brc = enemy_points_brc;
    }

    public int getEnemy_points_brb() {
        return enemy_points_brb;
    }

    public void setEnemy_points_brb(int enemy_points_brb) {
        this.enemy_points_brb = enemy_points_brb;
    }

    public int getSlots_blt() {
        return slots_blt;
    }

    public void setSlots_blt(int slots_blt) {
        this.slots_blt = slots_blt;
    }

    public int getSlots_blt_our() {
        return slots_blt_our;
    }

    public void setSlots_blt_our(int slots_blt_our) {
        this.slots_blt_our = slots_blt_our;
    }

    public int getSlots_blt_empty() {
        return slots_blt_empty;
    }

    public void setSlots_blt_empty(int slots_blt_empty) {
        this.slots_blt_empty = slots_blt_empty;
    }

    public int getSlots_blt_enemy() {
        return slots_blt_enemy;
    }

    public void setSlots_blt_enemy(int slots_blt_enemy) {
        this.slots_blt_enemy = slots_blt_enemy;
    }

    public int getSlots_blc() {
        return slots_blc;
    }

    public void setSlots_blc(int slots_blc) {
        this.slots_blc = slots_blc;
    }

    public int getSlots_blc_our() {
        return slots_blc_our;
    }

    public void setSlots_blc_our(int slots_blc_our) {
        this.slots_blc_our = slots_blc_our;
    }

    public int getSlots_blc_empty() {
        return slots_blc_empty;
    }

    public void setSlots_blc_empty(int slots_blc_empty) {
        this.slots_blc_empty = slots_blc_empty;
    }

    public int getSlots_blc_enemy() {
        return slots_blc_enemy;
    }

    public void setSlots_blc_enemy(int slots_blc_enemy) {
        this.slots_blc_enemy = slots_blc_enemy;
    }

    public int getSlots_blb() {
        return slots_blb;
    }

    public void setSlots_blb(int slots_blb) {
        this.slots_blb = slots_blb;
    }

    public int getSlots_blb_our() {
        return slots_blb_our;
    }

    public void setSlots_blb_our(int slots_blb_our) {
        this.slots_blb_our = slots_blb_our;
    }

    public int getSlots_blb_empty() {
        return slots_blb_empty;
    }

    public void setSlots_blb_empty(int slots_blb_empty) {
        this.slots_blb_empty = slots_blb_empty;
    }

    public int getSlots_blb_enemy() {
        return slots_blb_enemy;
    }

    public void setSlots_blb_enemy(int slots_blb_enemy) {
        this.slots_blb_enemy = slots_blb_enemy;
    }

    public int getSlots_brt() {
        return slots_brt;
    }

    public void setSlots_brt(int slots_brt) {
        this.slots_brt = slots_brt;
    }

    public int getSlots_brt_our() {
        return slots_brt_our;
    }

    public void setSlots_brt_our(int slots_brt_our) {
        this.slots_brt_our = slots_brt_our;
    }

    public int getSlots_brt_empty() {
        return slots_brt_empty;
    }

    public void setSlots_brt_empty(int slots_brt_empty) {
        this.slots_brt_empty = slots_brt_empty;
    }

    public int getSlots_brt_enemy() {
        return slots_brt_enemy;
    }

    public void setSlots_brt_enemy(int slots_brt_enemy) {
        this.slots_brt_enemy = slots_brt_enemy;
    }

    public int getSlots_brc() {
        return slots_brc;
    }

    public void setSlots_brc(int slots_brc) {
        this.slots_brc = slots_brc;
    }

    public int getSlots_brc_our() {
        return slots_brc_our;
    }

    public void setSlots_brc_our(int slots_brc_our) {
        this.slots_brc_our = slots_brc_our;
    }

    public int getSlots_brc_empty() {
        return slots_brc_empty;
    }

    public void setSlots_brc_empty(int slots_brc_empty) {
        this.slots_brc_empty = slots_brc_empty;
    }

    public int getSlots_brc_enemy() {
        return slots_brc_enemy;
    }

    public void setSlots_brc_enemy(int slots_brc_enemy) {
        this.slots_brc_enemy = slots_brc_enemy;
    }

    public int getSlots_brb() {
        return slots_brb;
    }

    public void setSlots_brb(int slots_brb) {
        this.slots_brb = slots_brb;
    }

    public int getSlots_brb_our() {
        return slots_brb_our;
    }

    public void setSlots_brb_our(int slots_brb_our) {
        this.slots_brb_our = slots_brb_our;
    }

    public int getSlots_brb_empty() {
        return slots_brb_empty;
    }

    public void setSlots_brb_empty(int slots_brb_empty) {
        this.slots_brb_empty = slots_brb_empty;
    }

    public int getSlots_brb_enemy() {
        return slots_brb_enemy;
    }

    public void setSlots_brb_enemy(int slots_brb_enemy) {
        this.slots_brb_enemy = slots_brb_enemy;
    }

    public int getCalibrateX() {
        return calibrateX;
    }

    public void setCalibrateX(int calibrateX) {
        this.calibrateX = calibrateX;
    }

    public int getCalibrateY() {
        return calibrateY;
    }

    public void setCalibrateY(int calibrateY) {
        this.calibrateY = calibrateY;
    }
}
