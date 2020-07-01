package com.svoemestodev.catscitycalc.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.io.ByteArrayDataOutput;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.svoemestodev.catscitycalc.GlobalApplication;
import com.svoemestodev.catscitycalc.activities.GameActivity;
import com.svoemestodev.catscitycalc.citycalcclasses.CCAGame;
import com.svoemestodev.catscitycalc.classes.Car;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DbTeamGame implements Serializable {

    private Date timestamp;
    private String userUID;
    private String userNIC;
    private String teamID;

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

    private int countOurX2;
    private int countEnemyX2;
    private int countX2;
    private int personsOur;
    private int personsEnemy;
    private int personsTotal;
    private int slotsTotal;
    private int slotsOur;
    private int slotsEnemy;

    private boolean canWin;
    private boolean canWinWithoutX2;
    private boolean canEarlyWin;
    private boolean canEarlyWinWithoutX2;
    private boolean needToWin_blt;
    private boolean needToWinWithoutX2_blt;
    private boolean needToEarlyWin_blt;
    private boolean needToEarlyWinWithoutX2_blt;
    private boolean needToWin_blc;
    private boolean needToWinWithoutX2_blc;
    private boolean needToEarlyWin_blc;
    private boolean needToEarlyWinWithoutX2_blc;
    private boolean needToWin_blb;
    private boolean needToWinWithoutX2_blb;
    private boolean needToEarlyWin_blb;
    private boolean needToEarlyWinWithoutX2_blb;
    private boolean needToWin_brt;
    private boolean needToWinWithoutX2_brt;
    private boolean needToEarlyWin_brt;
    private boolean needToEarlyWinWithoutX2_brt;
    private boolean needToWin_brc;
    private boolean needToWinWithoutX2_brc;
    private boolean needToEarlyWin_brc;
    private boolean needToEarlyWinWithoutX2_brc;
    private boolean needToWin_brb;
    private boolean needToWinWithoutX2_brb;
    private boolean needToEarlyWin_brb;
    private boolean needToEarlyWinWithoutX2_brb;
    

    private byte[] bytesScreenshot = null;

    private static final transient String TAG = "DbTeamGame";

    public String save(String pathToFolder) {
        String result = null;
        String fileName = "";

        if (pathToFolder != null && !pathToFolder.equals("")) {
            File dirToSave = new File(pathToFolder);
            if (dirToSave.exists()) {
                if (dirToSave.isDirectory()) {
                    fileName = pathToFolder + "/game_" + UUID.randomUUID().toString() + ".citycalcteamgame";
//                    fileName = pathToFolder + "/game.citycalcteamgame";
                    try {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        GameActivity.mainCityCalc.getBmpScreenshot().compress(Bitmap.CompressFormat.JPEG, 90, stream);
                        this.bytesScreenshot = stream.toByteArray();
                        stream.close();

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
                        oos.writeObject(this);
                        oos.close();

                        byte[] array = byteArrayOutputStream.toByteArray();
                        byte[] cryptedArray = Utils.cryptArray(array, this.teamID);

                        ByteArrayOutputStream byteArrayOutputStreamCrypted = new ByteArrayOutputStream();
                        byteArrayOutputStreamCrypted.write(cryptedArray);

                        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                        byteArrayOutputStreamCrypted.writeTo(fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();

//                        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
//                        ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
//                        oos.writeObject(this);
//                        oos.close();
                        result = fileName;

                    } catch (IOException e) {
                        Log.e(TAG, "save. Ошибка сериализации");
                        e.printStackTrace();
                    }
                    return result;
                }
            }
        }
        return result;
    }

    public static DbTeamGame load(String pathToFile, String teamID) {

        if (pathToFile != null && !pathToFile.equals("")) {
            File file = new File(pathToFile);
            if (file.exists()) {
                try {

                    FileInputStream fileInputStream = new FileInputStream(file);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = fileInputStream.read(buf)) > 0) {
                        byteArrayOutputStream.write(buf, 0, len);
                    }
                    fileInputStream.close();

                    byte[] array = byteArrayOutputStream.toByteArray();
                    byte[] cryptedArray = Utils.cryptArray(array, teamID);

                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cryptedArray);
                    ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);

                    DbTeamGame loadedDbTeamGame = (DbTeamGame) ois.readObject();
                    ois.close();
                    return loadedDbTeamGame;

//                    FileInputStream fileInputStream = new FileInputStream(file);
//                    ObjectInputStream ois = new ObjectInputStream(fileInputStream);
//                    DbTeamGame loadedDbTeamGame = (DbTeamGame) ois.readObject();
//                    ois.close();
//                    return loadedDbTeamGame;
                } catch (ClassNotFoundException | IOException e) {
                    Log.e(TAG, "Ошибка десериализации.");
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static DbTeamGame load(File file, String teamID) {
        return load(file.getAbsolutePath(), teamID);
    }

    
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
        key = "increaseOur"; if (map.containsKey(key)) this.increaseOur = ((Long) map.get(key)).intValue();
        key = "increaseEnemy"; if (map.containsKey(key)) this.increaseEnemy = ((Long) map.get(key)).intValue();

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

        key = "countOurX2"; if (map.containsKey(key)) this.countOurX2 = ((Long) map.get(key)).intValue();
        key = "countEnemyX2"; if (map.containsKey(key)) this.countEnemyX2 = ((Long) map.get(key)).intValue();
        key = "countX2"; if (map.containsKey(key)) this.countX2 = ((Long) map.get(key)).intValue();
        key = "personsOur"; if (map.containsKey(key)) this.personsOur = ((Long) map.get(key)).intValue();
        key = "personsEnemy"; if (map.containsKey(key)) this.personsEnemy = ((Long) map.get(key)).intValue();
        key = "personsTotal"; if (map.containsKey(key)) this.personsTotal = ((Long) map.get(key)).intValue();
        key = "slotsTotal"; if (map.containsKey(key)) this.slotsTotal = ((Long) map.get(key)).intValue();
        key = "slotsOur"; if (map.containsKey(key)) this.slotsOur = ((Long) map.get(key)).intValue();
        key = "slotsEnemy"; if (map.containsKey(key)) this.slotsEnemy = ((Long) map.get(key)).intValue();

        key = "canWin"; if (map.containsKey(key)) this.canWin = (boolean) map.get(key);
        key = "canWinWithoutX2"; if (map.containsKey(key)) this.canWinWithoutX2 = (boolean) map.get(key);
        key = "canEarlyWin"; if (map.containsKey(key)) this.canEarlyWin = (boolean) map.get(key);
        key = "canEarlyWinWithoutX2"; if (map.containsKey(key)) this.canEarlyWinWithoutX2 = (boolean) map.get(key);
        key = "needToWin_blt"; if (map.containsKey(key)) this.needToWin_blt = (boolean) map.get(key);
        key = "needToWinWithoutX2_blt"; if (map.containsKey(key)) this.needToWinWithoutX2_blt = (boolean) map.get(key);
        key = "needToEarlyWin_blt"; if (map.containsKey(key)) this.needToEarlyWin_blt = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_blt"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_blt = (boolean) map.get(key);
        key = "needToWin_blc"; if (map.containsKey(key)) this.needToWin_blc = (boolean) map.get(key);
        key = "needToWinWithoutX2_blc"; if (map.containsKey(key)) this.needToWinWithoutX2_blc = (boolean) map.get(key);
        key = "needToEarlyWin_blc"; if (map.containsKey(key)) this.needToEarlyWin_blc = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_blc"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_blc = (boolean) map.get(key);
        key = "needToWin_blb"; if (map.containsKey(key)) this.needToWin_blb = (boolean) map.get(key);
        key = "needToWinWithoutX2_blb"; if (map.containsKey(key)) this.needToWinWithoutX2_blb = (boolean) map.get(key);
        key = "needToEarlyWin_blb"; if (map.containsKey(key)) this.needToEarlyWin_blb = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_blb"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_blb = (boolean) map.get(key);
        key = "needToWin_brt"; if (map.containsKey(key)) this.needToWin_brt = (boolean) map.get(key);
        key = "needToWinWithoutX2_brt"; if (map.containsKey(key)) this.needToWinWithoutX2_brt = (boolean) map.get(key);
        key = "needToEarlyWin_brt"; if (map.containsKey(key)) this.needToEarlyWin_brt = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_brt"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_brt = (boolean) map.get(key);
        key = "needToWin_brc"; if (map.containsKey(key)) this.needToWin_brc = (boolean) map.get(key);
        key = "needToWinWithoutX2_brc"; if (map.containsKey(key)) this.needToWinWithoutX2_brc = (boolean) map.get(key);
        key = "needToEarlyWin_brc"; if (map.containsKey(key)) this.needToEarlyWin_brc = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_brc"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_brc = (boolean) map.get(key);
        key = "needToWin_brb"; if (map.containsKey(key)) this.needToWin_brb = (boolean) map.get(key);
        key = "needToWinWithoutX2_brb"; if (map.containsKey(key)) this.needToWinWithoutX2_brb = (boolean) map.get(key);
        key = "needToEarlyWin_brb"; if (map.containsKey(key)) this.needToEarlyWin_brb = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_brb"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_brb = (boolean) map.get(key);
        
    }
    
    public DbTeamGame(CCAGame ccaGame) {

        this.dateStartGame = ccaGame.getDateStartGame();
        this.dateScreenshot = ccaGame.getDateScreenshot();
        this.dateEndGame = ccaGame.getDateEndGame();
        this.earlyWin = ccaGame.getEarlyWin();

        this.calibrateX = GameActivity.calibrateX;
        this.calibrateY = GameActivity.calibrateY;

        this.pointsOurInScreenshot = ccaGame.getPointsOurInScreenshot();
        this.pointsEnemyInScreenshot = ccaGame.getPointsEnemyInScreenshot();
        this.increaseOur = ccaGame.getIncreaseOur();
        this.increaseEnemy = ccaGame.getIncreaseEnemy();

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

        this.countOurX2 = ccaGame.getCountOurX2();
        this.countEnemyX2 = ccaGame.getCountEnemyX2();
        this.countX2 = ccaGame.getCountX2();
        this.personsOur = ccaGame.getPersonsOur();
        this.personsEnemy = ccaGame.getPersonsEnemy();
        this.personsTotal = ccaGame.getPersonsTotal();
        this.slotsTotal = ccaGame.getSlotsTotal();
        this.slotsOur = ccaGame.getSlotsOur();
        this.slotsEnemy = ccaGame.getSlotsEnemy();

        this.canWin = ccaGame.isCanWin();
        this.canWinWithoutX2 = ccaGame.isCanWinWithoutX2();
        this.canEarlyWin = ccaGame.isCanEarlyWin();
        this.canEarlyWinWithoutX2 = ccaGame.isCanEarlyWinWithoutX2();

        this.needToWin_blt = ccaGame.isNeedToWin_blt();
        this.needToWinWithoutX2_blt = ccaGame.isNeedToWinWithoutX2_blt();
        this.needToEarlyWin_blt = ccaGame.isNeedToEarlyWin_blt();
        this.needToEarlyWinWithoutX2_blt = ccaGame.isNeedToEarlyWinWithoutX2_blt();
        this.needToWin_blc = ccaGame.isNeedToWin_blc();
        this.needToWinWithoutX2_blc = ccaGame.isNeedToWinWithoutX2_blc();
        this.needToEarlyWin_blc = ccaGame.isNeedToEarlyWin_blc();
        this.needToEarlyWinWithoutX2_blc = ccaGame.isNeedToEarlyWinWithoutX2_blc();
        this.needToWin_blb = ccaGame.isNeedToWin_blb();
        this.needToWinWithoutX2_blb = ccaGame.isNeedToWinWithoutX2_blb();
        this.needToEarlyWin_blb = ccaGame.isNeedToEarlyWin_blb();
        this.needToEarlyWinWithoutX2_blb = ccaGame.isNeedToEarlyWinWithoutX2_blb();
        this.needToWin_brt = ccaGame.isNeedToWin_brt();
        this.needToWinWithoutX2_brt = ccaGame.isNeedToWinWithoutX2_brt();
        this.needToEarlyWin_brt = ccaGame.isNeedToEarlyWin_brt();
        this.needToEarlyWinWithoutX2_brt = ccaGame.isNeedToEarlyWinWithoutX2_brt();
        this.needToWin_brc = ccaGame.isNeedToWin_brc();
        this.needToWinWithoutX2_brc = ccaGame.isNeedToWinWithoutX2_brc();
        this.needToEarlyWin_brc = ccaGame.isNeedToEarlyWin_brc();
        this.needToEarlyWinWithoutX2_brc = ccaGame.isNeedToEarlyWinWithoutX2_brc();
        this.needToWin_brb = ccaGame.isNeedToWin_brb();
        this.needToWinWithoutX2_brb = ccaGame.isNeedToWinWithoutX2_brb();
        this.needToEarlyWin_brb = ccaGame.isNeedToEarlyWin_brb();
        this.needToEarlyWinWithoutX2_brb = ccaGame.isNeedToEarlyWinWithoutX2_brb();

        if (!ccaGame.isGameOver()) {

            if (!GameActivity.mainCityCalc.getFileScreenshot().getAbsolutePath().equals(GlobalApplication.pathToCATScalcFolder + "/last_screenshot.PNG") && !GameActivity.mainCityCalc.getFileScreenshot().getAbsolutePath().equals(GlobalApplication.pathToCATScalcFolder + "/last_screenshot")) {
                if (GameActivity.fbUser != null) { // есть юзер
                    if (GameActivity.fbUser.isEmailVerified()) { // емейл подтвержден
//                        final String userUID = GameActivity.fbUser.getUid();

                        this.userUID = GameActivity.fbUser.getUid();
                        if (GameActivity.mainDbTeamUser != null) {
                            this.userNIC = GameActivity.mainDbTeamUser.getUserNIC();
                            this.teamID = GameActivity.mainDbTeamUser.getTeamID();
                        }
                        final DbTeamGame dbTeamGame = this;

                        GameActivity.fbDb.collection("users").document(userUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                if (!ccaGame.isGameOver()) {

                                    final String teamID = (String)documentSnapshot.get("teamID");
                                    final String userNIC = (String)documentSnapshot.get("userNIC");
                                    if (teamID != null && !teamID.equals("")) {

                                        dbTeamGame.userNIC = userNIC;
                                        dbTeamGame.teamID = teamID;

                                        DocumentReference doc = GameActivity.fbDb.collection("teams").document(teamID).collection("teamGames").document("teamGame");
                                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().exists()) {
                                                        DocumentSnapshot documentSnapshot = task.getResult();
                                                        Map<String, Object> map = dbTeamGame.getMap(userUID, userNIC, teamID);
                                                        if (documentSnapshot.getTimestamp("dateScreenshot").toDate().getTime() < ((Date)map.get("dateScreenshot")).getTime()) {
                                                            GameActivity.fbDb.collection("teams").document(teamID).collection("teamGames").document("teamGame").set(dbTeamGame.getMap(userUID, userNIC, teamID));
                                                        }
                                                    } else {
                                                        GameActivity.fbDb.collection("teams").document(teamID).collection("teamGames").document("teamGame").set(dbTeamGame.getMap(userUID, userNIC, teamID));
                                                    }
                                                }
                                            }
                                        });

                                    }

                                }

                            }
                        });
                    }
                }
            }


        }




    }

    public Map<String, Object> getMap(String userUID, String userNIC, String teamID) {
        Map<String, Object> map = new HashMap<>();

        map.put("timestamp", FieldValue.serverTimestamp());
        map.put("userUID", userUID);
        map.put("userNIC", userNIC);
        map.put("teamID", teamID);

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

        map.put("countOurX2", countOurX2);
        map.put("countEnemyX2", countEnemyX2);
        map.put("countX2", countX2);
        map.put("personsOur", personsOur);
        map.put("personsEnemy", personsEnemy);
        map.put("personsTotal", personsTotal);
        map.put("slotsTotal", slotsTotal);
        map.put("slotsOur", slotsOur);
        map.put("slotsEnemy", slotsEnemy);

        map.put("canWin", canWin);
        map.put("canWinWithoutX2", canWinWithoutX2);
        map.put("canEarlyWin", canEarlyWin);
        map.put("canEarlyWinWithoutX2", canEarlyWinWithoutX2);
        map.put("needToWin_blt", needToWin_blt);
        map.put("needToWinWithoutX2_blt", needToWinWithoutX2_blt);
        map.put("needToEarlyWin_blt", needToEarlyWin_blt);
        map.put("needToEarlyWinWithoutX2_blt", needToEarlyWinWithoutX2_blt);
        map.put("needToWin_blc", needToWin_blc);
        map.put("needToWinWithoutX2_blc", needToWinWithoutX2_blc);
        map.put("needToEarlyWin_blc", needToEarlyWin_blc);
        map.put("needToEarlyWinWithoutX2_blc", needToEarlyWinWithoutX2_blc);
        map.put("needToWin_blb", needToWin_blb);
        map.put("needToWinWithoutX2_blb", needToWinWithoutX2_blb);
        map.put("needToEarlyWin_blb", needToEarlyWin_blb);
        map.put("needToEarlyWinWithoutX2_blb", needToEarlyWinWithoutX2_blb);
        map.put("needToWin_brt", needToWin_brt);
        map.put("needToWinWithoutX2_brt", needToWinWithoutX2_brt);
        map.put("needToEarlyWin_brt", needToEarlyWin_brt);
        map.put("needToEarlyWinWithoutX2_brt", needToEarlyWinWithoutX2_brt);
        map.put("needToWin_brc", needToWin_brc);
        map.put("needToWinWithoutX2_brc", needToWinWithoutX2_brc);
        map.put("needToEarlyWin_brc", needToEarlyWin_brc);
        map.put("needToEarlyWinWithoutX2_brc", needToEarlyWinWithoutX2_brc);
        map.put("needToWin_brb", needToWin_brb);
        map.put("needToWinWithoutX2_brb", needToWinWithoutX2_brb);
        map.put("needToEarlyWin_brb", needToEarlyWin_brb);
        map.put("needToEarlyWinWithoutX2_brb", needToEarlyWinWithoutX2_brb);
        
        return map;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
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

    public byte[] getBytesScreenshot() {
        return bytesScreenshot;
    }

    public void setBytesScreenshot(byte[] bytesScreenshot) {
        this.bytesScreenshot = bytesScreenshot;
    }

    public int getCountOurX2() {
        return countOurX2;
    }

    public void setCountOurX2(int countOurX2) {
        this.countOurX2 = countOurX2;
    }

    public int getCountEnemyX2() {
        return countEnemyX2;
    }

    public void setCountEnemyX2(int countEnemyX2) {
        this.countEnemyX2 = countEnemyX2;
    }

    public int getCountX2() {
        return countX2;
    }

    public void setCountX2(int countX2) {
        this.countX2 = countX2;
    }

    public int getPersonsOur() {
        return personsOur;
    }

    public void setPersonsOur(int personsOur) {
        this.personsOur = personsOur;
    }

    public int getPersonsEnemy() {
        return personsEnemy;
    }

    public void setPersonsEnemy(int personsEnemy) {
        this.personsEnemy = personsEnemy;
    }

    public int getPersonsTotal() {
        return personsTotal;
    }

    public void setPersonsTotal(int personsTotal) {
        this.personsTotal = personsTotal;
    }

    public int getSlotsTotal() {
        return slotsTotal;
    }

    public void setSlotsTotal(int slotsTotal) {
        this.slotsTotal = slotsTotal;
    }

    public int getSlotsOur() {
        return slotsOur;
    }

    public void setSlotsOur(int slotsOur) {
        this.slotsOur = slotsOur;
    }

    public int getSlotsEnemy() {
        return slotsEnemy;
    }

    public void setSlotsEnemy(int slotsEnemy) {
        this.slotsEnemy = slotsEnemy;
    }

    public boolean isCanWin() {
        return canWin;
    }

    public void setCanWin(boolean canWin) {
        this.canWin = canWin;
    }

    public boolean isCanWinWithoutX2() {
        return canWinWithoutX2;
    }

    public void setCanWinWithoutX2(boolean canWinWithoutX2) {
        this.canWinWithoutX2 = canWinWithoutX2;
    }

    public boolean isCanEarlyWin() {
        return canEarlyWin;
    }

    public void setCanEarlyWin(boolean canEarlyWin) {
        this.canEarlyWin = canEarlyWin;
    }

    public boolean isCanEarlyWinWithoutX2() {
        return canEarlyWinWithoutX2;
    }

    public void setCanEarlyWinWithoutX2(boolean canEarlyWinWithoutX2) {
        this.canEarlyWinWithoutX2 = canEarlyWinWithoutX2;
    }

    public boolean isNeedToWin_blt() {
        return needToWin_blt;
    }

    public void setNeedToWin_blt(boolean needToWin_blt) {
        this.needToWin_blt = needToWin_blt;
    }

    public boolean isNeedToWinWithoutX2_blt() {
        return needToWinWithoutX2_blt;
    }

    public void setNeedToWinWithoutX2_blt(boolean needToWinWithoutX2_blt) {
        this.needToWinWithoutX2_blt = needToWinWithoutX2_blt;
    }

    public boolean isNeedToEarlyWin_blt() {
        return needToEarlyWin_blt;
    }

    public void setNeedToEarlyWin_blt(boolean needToEarlyWin_blt) {
        this.needToEarlyWin_blt = needToEarlyWin_blt;
    }

    public boolean isNeedToEarlyWinWithoutX2_blt() {
        return needToEarlyWinWithoutX2_blt;
    }

    public void setNeedToEarlyWinWithoutX2_blt(boolean needToEarlyWinWithoutX2_blt) {
        this.needToEarlyWinWithoutX2_blt = needToEarlyWinWithoutX2_blt;
    }

    public boolean isNeedToWin_blc() {
        return needToWin_blc;
    }

    public void setNeedToWin_blc(boolean needToWin_blc) {
        this.needToWin_blc = needToWin_blc;
    }

    public boolean isNeedToWinWithoutX2_blc() {
        return needToWinWithoutX2_blc;
    }

    public void setNeedToWinWithoutX2_blc(boolean needToWinWithoutX2_blc) {
        this.needToWinWithoutX2_blc = needToWinWithoutX2_blc;
    }

    public boolean isNeedToEarlyWin_blc() {
        return needToEarlyWin_blc;
    }

    public void setNeedToEarlyWin_blc(boolean needToEarlyWin_blc) {
        this.needToEarlyWin_blc = needToEarlyWin_blc;
    }

    public boolean isNeedToEarlyWinWithoutX2_blc() {
        return needToEarlyWinWithoutX2_blc;
    }

    public void setNeedToEarlyWinWithoutX2_blc(boolean needToEarlyWinWithoutX2_blc) {
        this.needToEarlyWinWithoutX2_blc = needToEarlyWinWithoutX2_blc;
    }

    public boolean isNeedToWin_blb() {
        return needToWin_blb;
    }

    public void setNeedToWin_blb(boolean needToWin_blb) {
        this.needToWin_blb = needToWin_blb;
    }

    public boolean isNeedToWinWithoutX2_blb() {
        return needToWinWithoutX2_blb;
    }

    public void setNeedToWinWithoutX2_blb(boolean needToWinWithoutX2_blb) {
        this.needToWinWithoutX2_blb = needToWinWithoutX2_blb;
    }

    public boolean isNeedToEarlyWin_blb() {
        return needToEarlyWin_blb;
    }

    public void setNeedToEarlyWin_blb(boolean needToEarlyWin_blb) {
        this.needToEarlyWin_blb = needToEarlyWin_blb;
    }

    public boolean isNeedToEarlyWinWithoutX2_blb() {
        return needToEarlyWinWithoutX2_blb;
    }

    public void setNeedToEarlyWinWithoutX2_blb(boolean needToEarlyWinWithoutX2_blb) {
        this.needToEarlyWinWithoutX2_blb = needToEarlyWinWithoutX2_blb;
    }

    public boolean isNeedToWin_brt() {
        return needToWin_brt;
    }

    public void setNeedToWin_brt(boolean needToWin_brt) {
        this.needToWin_brt = needToWin_brt;
    }

    public boolean isNeedToWinWithoutX2_brt() {
        return needToWinWithoutX2_brt;
    }

    public void setNeedToWinWithoutX2_brt(boolean needToWinWithoutX2_brt) {
        this.needToWinWithoutX2_brt = needToWinWithoutX2_brt;
    }

    public boolean isNeedToEarlyWin_brt() {
        return needToEarlyWin_brt;
    }

    public void setNeedToEarlyWin_brt(boolean needToEarlyWin_brt) {
        this.needToEarlyWin_brt = needToEarlyWin_brt;
    }

    public boolean isNeedToEarlyWinWithoutX2_brt() {
        return needToEarlyWinWithoutX2_brt;
    }

    public void setNeedToEarlyWinWithoutX2_brt(boolean needToEarlyWinWithoutX2_brt) {
        this.needToEarlyWinWithoutX2_brt = needToEarlyWinWithoutX2_brt;
    }

    public boolean isNeedToWin_brc() {
        return needToWin_brc;
    }

    public void setNeedToWin_brc(boolean needToWin_brc) {
        this.needToWin_brc = needToWin_brc;
    }

    public boolean isNeedToWinWithoutX2_brc() {
        return needToWinWithoutX2_brc;
    }

    public void setNeedToWinWithoutX2_brc(boolean needToWinWithoutX2_brc) {
        this.needToWinWithoutX2_brc = needToWinWithoutX2_brc;
    }

    public boolean isNeedToEarlyWin_brc() {
        return needToEarlyWin_brc;
    }

    public void setNeedToEarlyWin_brc(boolean needToEarlyWin_brc) {
        this.needToEarlyWin_brc = needToEarlyWin_brc;
    }

    public boolean isNeedToEarlyWinWithoutX2_brc() {
        return needToEarlyWinWithoutX2_brc;
    }

    public void setNeedToEarlyWinWithoutX2_brc(boolean needToEarlyWinWithoutX2_brc) {
        this.needToEarlyWinWithoutX2_brc = needToEarlyWinWithoutX2_brc;
    }

    public boolean isNeedToWin_brb() {
        return needToWin_brb;
    }

    public void setNeedToWin_brb(boolean needToWin_brb) {
        this.needToWin_brb = needToWin_brb;
    }

    public boolean isNeedToWinWithoutX2_brb() {
        return needToWinWithoutX2_brb;
    }

    public void setNeedToWinWithoutX2_brb(boolean needToWinWithoutX2_brb) {
        this.needToWinWithoutX2_brb = needToWinWithoutX2_brb;
    }

    public boolean isNeedToEarlyWin_brb() {
        return needToEarlyWin_brb;
    }

    public void setNeedToEarlyWin_brb(boolean needToEarlyWin_brb) {
        this.needToEarlyWin_brb = needToEarlyWin_brb;
    }

    public boolean isNeedToEarlyWinWithoutX2_brb() {
        return needToEarlyWinWithoutX2_brb;
    }

    public void setNeedToEarlyWinWithoutX2_brb(boolean needToEarlyWinWithoutX2_brb) {
        this.needToEarlyWinWithoutX2_brb = needToEarlyWinWithoutX2_brb;
    }
}
