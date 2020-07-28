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

    private boolean isPresent_bld1;
    private boolean isPresent_bld2;
    private boolean isPresent_bld3;
    private boolean isPresent_bld4;
    private boolean isPresent_bld5;
    private boolean isPresent_bld6;

    private boolean isX2_bld1;
    private boolean isX2_bld2;
    private boolean isX2_bld3;
    private boolean isX2_bld4;
    private boolean isX2_bld5;
    private boolean isX2_bld6;

    private boolean mayX2_bld1;
    private boolean mayX2_bld2;
    private boolean mayX2_bld3;
    private boolean mayX2_bld4;
    private boolean mayX2_bld5;
    private boolean mayX2_bld6;

    private boolean buildingIsOur_bld1;
    private boolean buildingIsOur_bld2;
    private boolean buildingIsOur_bld3;
    private boolean buildingIsOur_bld4;
    private boolean buildingIsOur_bld5;
    private boolean buildingIsOur_bld6;

    private boolean buildingIsEmpty_bld1;
    private boolean buildingIsEmpty_bld2;
    private boolean buildingIsEmpty_bld3;
    private boolean buildingIsEmpty_bld4;
    private boolean buildingIsEmpty_bld5;
    private boolean buildingIsEmpty_bld6;

    private boolean buildingIsEnemy_bld1;
    private boolean buildingIsEnemy_bld2;
    private boolean buildingIsEnemy_bld3;
    private boolean buildingIsEnemy_bld4;
    private boolean buildingIsEnemy_bld5;
    private boolean buildingIsEnemy_bld6;

    private int our_points_bld1;
    private int our_points_bld2;
    private int our_points_bld3;
    private int our_points_bld4;
    private int our_points_bld5;
    private int our_points_bld6;

    private int enemy_points_bld1;
    private int enemy_points_bld2;
    private int enemy_points_bld3;
    private int enemy_points_bld4;
    private int enemy_points_bld5;
    private int enemy_points_bld6;

    private int slots_bld1;
    private int slots_bld1_our;
    private int slots_bld1_empty;
    private int slots_bld1_enemy;
    private int slots_bld2;
    private int slots_bld2_our;
    private int slots_bld2_empty;
    private int slots_bld2_enemy;
    private int slots_bld3;
    private int slots_bld3_our;
    private int slots_bld3_empty;
    private int slots_bld3_enemy;
    private int slots_bld4;
    private int slots_bld4_our;
    private int slots_bld4_empty;
    private int slots_bld4_enemy;
    private int slots_bld5;
    private int slots_bld5_our;
    private int slots_bld5_empty;
    private int slots_bld5_enemy;
    private int slots_bld6;
    private int slots_bld6_our;
    private int slots_bld6_empty;
    private int slots_bld6_enemy;

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
    private boolean needToWin_bld1;
    private boolean needToWinWithoutX2_bld1;
    private boolean needToEarlyWin_bld1;
    private boolean needToEarlyWinWithoutX2_bld1;
    private boolean needToWin_bld2;
    private boolean needToWinWithoutX2_bld2;
    private boolean needToEarlyWin_bld2;
    private boolean needToEarlyWinWithoutX2_bld2;
    private boolean needToWin_bld3;
    private boolean needToWinWithoutX2_bld3;
    private boolean needToEarlyWin_bld3;
    private boolean needToEarlyWinWithoutX2_bld3;
    private boolean needToWin_bld4;
    private boolean needToWinWithoutX2_bld4;
    private boolean needToEarlyWin_bld4;
    private boolean needToEarlyWinWithoutX2_bld4;
    private boolean needToWin_bld5;
    private boolean needToWinWithoutX2_bld5;
    private boolean needToEarlyWin_bld5;
    private boolean needToEarlyWinWithoutX2_bld5;
    private boolean needToWin_bld6;
    private boolean needToWinWithoutX2_bld6;
    private boolean needToEarlyWin_bld6;
    private boolean needToEarlyWinWithoutX2_bld6;

    private boolean useInForecast_bld1 = true;
    private boolean useInForecast_bld2 = true;
    private boolean useInForecast_bld3 = true;
    private boolean useInForecast_bld4 = true;
    private boolean useInForecast_bld5 = true;
    private boolean useInForecast_bld6 = true;

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
                        GameActivity.mainCityCalc.getSsaScreenshot().getBitmap().compress(Bitmap.CompressFormat.JPEG, 90, stream);
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

        key = "isPresent_bld1"; if (map.containsKey(key)) this.isPresent_bld1 = (boolean) map.get(key);
        key = "isPresent_bld2"; if (map.containsKey(key)) this.isPresent_bld2 = (boolean) map.get(key);
        key = "isPresent_bld3"; if (map.containsKey(key)) this.isPresent_bld3 = (boolean) map.get(key);
        key = "isPresent_bld4"; if (map.containsKey(key)) this.isPresent_bld4 = (boolean) map.get(key);
        key = "isPresent_bld5"; if (map.containsKey(key)) this.isPresent_bld5 = (boolean) map.get(key);
        key = "isPresent_bld6"; if (map.containsKey(key)) this.isPresent_bld6 = (boolean) map.get(key);
        
        key = "isX2_bld1"; if (map.containsKey(key)) this.isX2_bld1 = (boolean) map.get(key);
        key = "isX2_bld2"; if (map.containsKey(key)) this.isX2_bld2 = (boolean) map.get(key);
        key = "isX2_bld3"; if (map.containsKey(key)) this.isX2_bld3 = (boolean) map.get(key);
        key = "isX2_bld4"; if (map.containsKey(key)) this.isX2_bld4 = (boolean) map.get(key);
        key = "isX2_bld5"; if (map.containsKey(key)) this.isX2_bld5 = (boolean) map.get(key);
        key = "isX2_bld6"; if (map.containsKey(key)) this.isX2_bld6 = (boolean) map.get(key);
        
        key = "mayX2_bld1"; if (map.containsKey(key)) this.mayX2_bld1 = (boolean) map.get(key);
        key = "mayX2_bld2"; if (map.containsKey(key)) this.mayX2_bld2 = (boolean) map.get(key);
        key = "mayX2_bld3"; if (map.containsKey(key)) this.mayX2_bld3 = (boolean) map.get(key);
        key = "mayX2_bld4"; if (map.containsKey(key)) this.mayX2_bld4 = (boolean) map.get(key);
        key = "mayX2_bld5"; if (map.containsKey(key)) this.mayX2_bld5 = (boolean) map.get(key);
        key = "mayX2_bld6"; if (map.containsKey(key)) this.mayX2_bld6 = (boolean) map.get(key);
        
        key = "buildingIsOur_bld1"; if (map.containsKey(key)) this.buildingIsOur_bld1 = (boolean) map.get(key);
        key = "buildingIsOur_bld2"; if (map.containsKey(key)) this.buildingIsOur_bld2 = (boolean) map.get(key);
        key = "buildingIsOur_bld3"; if (map.containsKey(key)) this.buildingIsOur_bld3 = (boolean) map.get(key);
        key = "buildingIsOur_bld4"; if (map.containsKey(key)) this.buildingIsOur_bld4 = (boolean) map.get(key);
        key = "buildingIsOur_bld5"; if (map.containsKey(key)) this.buildingIsOur_bld5 = (boolean) map.get(key);
        key = "buildingIsOur_bld6"; if (map.containsKey(key)) this.buildingIsOur_bld6 = (boolean) map.get(key);
        
        key = "buildingIsEmpty_bld1"; if (map.containsKey(key)) this.buildingIsEmpty_bld1 = (boolean) map.get(key);
        key = "buildingIsEmpty_bld2"; if (map.containsKey(key)) this.buildingIsEmpty_bld2 = (boolean) map.get(key);
        key = "buildingIsEmpty_bld3"; if (map.containsKey(key)) this.buildingIsEmpty_bld3 = (boolean) map.get(key);
        key = "buildingIsEmpty_bld4"; if (map.containsKey(key)) this.buildingIsEmpty_bld4 = (boolean) map.get(key);
        key = "buildingIsEmpty_bld5"; if (map.containsKey(key)) this.buildingIsEmpty_bld5 = (boolean) map.get(key);
        key = "buildingIsEmpty_bld6"; if (map.containsKey(key)) this.buildingIsEmpty_bld6 = (boolean) map.get(key);
        
        
        key = "buildingIsEnemy_bld1"; if (map.containsKey(key)) this.buildingIsEnemy_bld1 = (boolean) map.get(key);
        key = "buildingIsEnemy_bld2"; if (map.containsKey(key)) this.buildingIsEnemy_bld2 = (boolean) map.get(key);
        key = "buildingIsEnemy_bld3"; if (map.containsKey(key)) this.buildingIsEnemy_bld3 = (boolean) map.get(key);
        key = "buildingIsEnemy_bld4"; if (map.containsKey(key)) this.buildingIsEnemy_bld4 = (boolean) map.get(key);
        key = "buildingIsEnemy_bld5"; if (map.containsKey(key)) this.buildingIsEnemy_bld5 = (boolean) map.get(key);
        key = "buildingIsEnemy_bld6"; if (map.containsKey(key)) this.buildingIsEnemy_bld6 = (boolean) map.get(key);
        
        key = "our_points_bld1"; if (map.containsKey(key)) this.our_points_bld1 = ((Long) map.get(key)).intValue();
        key = "our_points_bld2"; if (map.containsKey(key)) this.our_points_bld2 = ((Long) map.get(key)).intValue();
        key = "our_points_bld3"; if (map.containsKey(key)) this.our_points_bld3 = ((Long) map.get(key)).intValue();
        key = "our_points_bld4"; if (map.containsKey(key)) this.our_points_bld4 = ((Long) map.get(key)).intValue();
        key = "our_points_bld5"; if (map.containsKey(key)) this.our_points_bld5 = ((Long) map.get(key)).intValue();
        key = "our_points_bld6"; if (map.containsKey(key)) this.our_points_bld6 = ((Long) map.get(key)).intValue();

        key = "enemy_points_bld1"; if (map.containsKey(key)) this.enemy_points_bld1 = ((Long) map.get(key)).intValue();
        key = "enemy_points_bld2"; if (map.containsKey(key)) this.enemy_points_bld2 = ((Long) map.get(key)).intValue();
        key = "enemy_points_bld3"; if (map.containsKey(key)) this.enemy_points_bld3 = ((Long) map.get(key)).intValue();
        key = "enemy_points_bld4"; if (map.containsKey(key)) this.enemy_points_bld4 = ((Long) map.get(key)).intValue();
        key = "enemy_points_bld5"; if (map.containsKey(key)) this.enemy_points_bld5 = ((Long) map.get(key)).intValue();
        key = "enemy_points_bld6"; if (map.containsKey(key)) this.enemy_points_bld6 = ((Long) map.get(key)).intValue();

        
        key = "slots_bld1"; if (map.containsKey(key)) this.slots_bld1 = ((Long) map.get(key)).intValue();
        key = "slots_bld1_our"; if (map.containsKey(key)) this.slots_bld1_our = ((Long) map.get(key)).intValue();
        key = "slots_bld1_empty"; if (map.containsKey(key)) this.slots_bld1_empty = ((Long) map.get(key)).intValue();
        key = "slots_bld1_enemy"; if (map.containsKey(key)) this.slots_bld1_enemy = ((Long) map.get(key)).intValue();

        key = "slots_bld2"; if (map.containsKey(key)) this.slots_bld2 = ((Long) map.get(key)).intValue();
        key = "slots_bld2_our"; if (map.containsKey(key)) this.slots_bld2_our = ((Long) map.get(key)).intValue();
        key = "slots_bld2_empty"; if (map.containsKey(key)) this.slots_bld2_empty = ((Long) map.get(key)).intValue();
        key = "slots_bld2_enemy"; if (map.containsKey(key)) this.slots_bld2_enemy = ((Long) map.get(key)).intValue();

        key = "slots_bld3"; if (map.containsKey(key)) this.slots_bld3 = ((Long) map.get(key)).intValue();
        key = "slots_bld3_our"; if (map.containsKey(key)) this.slots_bld3_our = ((Long) map.get(key)).intValue();
        key = "slots_bld3_empty"; if (map.containsKey(key)) this.slots_bld3_empty = ((Long) map.get(key)).intValue();
        key = "slots_bld3_enemy"; if (map.containsKey(key)) this.slots_bld3_enemy = ((Long) map.get(key)).intValue();

        key = "slots_bld4"; if (map.containsKey(key)) this.slots_bld4 = ((Long) map.get(key)).intValue();
        key = "slots_bld4_our"; if (map.containsKey(key)) this.slots_bld4_our = ((Long) map.get(key)).intValue();
        key = "slots_bld4_empty"; if (map.containsKey(key)) this.slots_bld4_empty = ((Long) map.get(key)).intValue();
        key = "slots_bld4_enemy"; if (map.containsKey(key)) this.slots_bld4_enemy = ((Long) map.get(key)).intValue();

        key = "slots_bld5"; if (map.containsKey(key)) this.slots_bld5 = ((Long) map.get(key)).intValue();
        key = "slots_bld5_our"; if (map.containsKey(key)) this.slots_bld5_our = ((Long) map.get(key)).intValue();
        key = "slots_bld5_empty"; if (map.containsKey(key)) this.slots_bld5_empty = ((Long) map.get(key)).intValue();
        key = "slots_bld5_enemy"; if (map.containsKey(key)) this.slots_bld5_enemy = ((Long) map.get(key)).intValue();

        key = "slots_bld6"; if (map.containsKey(key)) this.slots_bld6 = ((Long) map.get(key)).intValue();
        key = "slots_bld6_our"; if (map.containsKey(key)) this.slots_bld6_our = ((Long) map.get(key)).intValue();
        key = "slots_bld6_empty"; if (map.containsKey(key)) this.slots_bld6_empty = ((Long) map.get(key)).intValue();
        key = "slots_bld6_enemy"; if (map.containsKey(key)) this.slots_bld6_enemy = ((Long) map.get(key)).intValue();

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
        key = "needToWin_bld1"; if (map.containsKey(key)) this.needToWin_bld1 = (boolean) map.get(key);
        key = "needToWinWithoutX2_bld1"; if (map.containsKey(key)) this.needToWinWithoutX2_bld1 = (boolean) map.get(key);
        key = "needToEarlyWin_bld1"; if (map.containsKey(key)) this.needToEarlyWin_bld1 = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_bld1"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_bld1 = (boolean) map.get(key);
        key = "needToWin_bld2"; if (map.containsKey(key)) this.needToWin_bld2 = (boolean) map.get(key);
        key = "needToWinWithoutX2_bld2"; if (map.containsKey(key)) this.needToWinWithoutX2_bld2 = (boolean) map.get(key);
        key = "needToEarlyWin_bld2"; if (map.containsKey(key)) this.needToEarlyWin_bld2 = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_bld2"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_bld2 = (boolean) map.get(key);
        key = "needToWin_bld3"; if (map.containsKey(key)) this.needToWin_bld3 = (boolean) map.get(key);
        key = "needToWinWithoutX2_bld3"; if (map.containsKey(key)) this.needToWinWithoutX2_bld3 = (boolean) map.get(key);
        key = "needToEarlyWin_bld3"; if (map.containsKey(key)) this.needToEarlyWin_bld3 = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_bld3"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_bld3 = (boolean) map.get(key);
        key = "needToWin_bld4"; if (map.containsKey(key)) this.needToWin_bld4 = (boolean) map.get(key);
        key = "needToWinWithoutX2_bld4"; if (map.containsKey(key)) this.needToWinWithoutX2_bld4 = (boolean) map.get(key);
        key = "needToEarlyWin_bld4"; if (map.containsKey(key)) this.needToEarlyWin_bld4 = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_bld4"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_bld4 = (boolean) map.get(key);
        key = "needToWin_bld5"; if (map.containsKey(key)) this.needToWin_bld5 = (boolean) map.get(key);
        key = "needToWinWithoutX2_bld5"; if (map.containsKey(key)) this.needToWinWithoutX2_bld5 = (boolean) map.get(key);
        key = "needToEarlyWin_bld5"; if (map.containsKey(key)) this.needToEarlyWin_bld5 = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_bld5"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_bld5 = (boolean) map.get(key);
        key = "needToWin_bld6"; if (map.containsKey(key)) this.needToWin_bld6 = (boolean) map.get(key);
        key = "needToWinWithoutX2_bld6"; if (map.containsKey(key)) this.needToWinWithoutX2_bld6 = (boolean) map.get(key);
        key = "needToEarlyWin_bld6"; if (map.containsKey(key)) this.needToEarlyWin_bld6 = (boolean) map.get(key);
        key = "needToEarlyWinWithoutX2_bld6"; if (map.containsKey(key)) this.needToEarlyWinWithoutX2_bld6 = (boolean) map.get(key);

        key = "useInForecast_bld1"; if (map.containsKey(key)) this.useInForecast_bld1 = (boolean) map.get(key);
        key = "useInForecast_bld2"; if (map.containsKey(key)) this.useInForecast_bld2 = (boolean) map.get(key);
        key = "useInForecast_bld3"; if (map.containsKey(key)) this.useInForecast_bld3 = (boolean) map.get(key);
        key = "useInForecast_bld4"; if (map.containsKey(key)) this.useInForecast_bld4 = (boolean) map.get(key);
        key = "useInForecast_bld5"; if (map.containsKey(key)) this.useInForecast_bld5 = (boolean) map.get(key);
        key = "useInForecast_bld6"; if (map.containsKey(key)) this.useInForecast_bld6 = (boolean) map.get(key);

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

        this.isPresent_bld1 = ccaGame.getBuildings()[0].isPresent();
        this.isPresent_bld2 = ccaGame.getBuildings()[1].isPresent();
        this.isPresent_bld3 = ccaGame.getBuildings()[2].isPresent();
        this.isPresent_bld4 = ccaGame.getBuildings()[3].isPresent();
        this.isPresent_bld5 = ccaGame.getBuildings()[4].isPresent();
        this.isPresent_bld6 = ccaGame.getBuildings()[5].isPresent();

        this.isX2_bld1 = ccaGame.getBuildings()[0].isX2();
        this.isX2_bld2 = ccaGame.getBuildings()[1].isX2();
        this.isX2_bld3 = ccaGame.getBuildings()[2].isX2();
        this.isX2_bld4 = ccaGame.getBuildings()[3].isX2();
        this.isX2_bld5 = ccaGame.getBuildings()[4].isX2();
        this.isX2_bld6 = ccaGame.getBuildings()[5].isX2();

        this.mayX2_bld1 = ccaGame.getBuildings()[0].isMayX2();
        this.mayX2_bld2 = ccaGame.getBuildings()[1].isMayX2();
        this.mayX2_bld3 = ccaGame.getBuildings()[2].isMayX2();
        this.mayX2_bld4 = ccaGame.getBuildings()[3].isMayX2();
        this.mayX2_bld5 = ccaGame.getBuildings()[4].isMayX2();
        this.mayX2_bld6 = ccaGame.getBuildings()[5].isMayX2();

        this.buildingIsOur_bld1 = ccaGame.getBuildings()[0].isBuildingIsOur();
        this.buildingIsOur_bld2 = ccaGame.getBuildings()[1].isBuildingIsOur();
        this.buildingIsOur_bld3 = ccaGame.getBuildings()[2].isBuildingIsOur();
        this.buildingIsOur_bld4 = ccaGame.getBuildings()[3].isBuildingIsOur();
        this.buildingIsOur_bld5 = ccaGame.getBuildings()[4].isBuildingIsOur();
        this.buildingIsOur_bld6 = ccaGame.getBuildings()[5].isBuildingIsOur();

        this.buildingIsEmpty_bld1 = ccaGame.getBuildings()[0].isBuildingIsEmpty();
        this.buildingIsEmpty_bld2 = ccaGame.getBuildings()[1].isBuildingIsEmpty();
        this.buildingIsEmpty_bld3 = ccaGame.getBuildings()[2].isBuildingIsEmpty();
        this.buildingIsEmpty_bld4 = ccaGame.getBuildings()[3].isBuildingIsEmpty();
        this.buildingIsEmpty_bld5 = ccaGame.getBuildings()[4].isBuildingIsEmpty();
        this.buildingIsEmpty_bld6 = ccaGame.getBuildings()[5].isBuildingIsEmpty();

        this.buildingIsEnemy_bld1 = ccaGame.getBuildings()[0].isBuildingIsEnemy();
        this.buildingIsEnemy_bld2 = ccaGame.getBuildings()[1].isBuildingIsEnemy();
        this.buildingIsEnemy_bld3 = ccaGame.getBuildings()[2].isBuildingIsEnemy();
        this.buildingIsEnemy_bld4 = ccaGame.getBuildings()[3].isBuildingIsEnemy();
        this.buildingIsEnemy_bld5 = ccaGame.getBuildings()[4].isBuildingIsEnemy();
        this.buildingIsEnemy_bld6 = ccaGame.getBuildings()[5].isBuildingIsEnemy();

        this.our_points_bld1 = ccaGame.getBuildings()[0].getOur_points();
        this.our_points_bld2 = ccaGame.getBuildings()[1].getOur_points();
        this.our_points_bld3 = ccaGame.getBuildings()[2].getOur_points();
        this.our_points_bld4 = ccaGame.getBuildings()[3].getOur_points();
        this.our_points_bld5 = ccaGame.getBuildings()[4].getOur_points();
        this.our_points_bld6 = ccaGame.getBuildings()[5].getOur_points();

        this.enemy_points_bld1 = ccaGame.getBuildings()[0].getEnemy_points();
        this.enemy_points_bld2 = ccaGame.getBuildings()[1].getEnemy_points();
        this.enemy_points_bld3 = ccaGame.getBuildings()[2].getEnemy_points();
        this.enemy_points_bld4 = ccaGame.getBuildings()[3].getEnemy_points();
        this.enemy_points_bld5 = ccaGame.getBuildings()[4].getEnemy_points();
        this.enemy_points_bld6 = ccaGame.getBuildings()[5].getEnemy_points();

        this.slots_bld1 = ccaGame.getBuildings()[0].getSlots();
        this.slots_bld2 = ccaGame.getBuildings()[1].getSlots();
        this.slots_bld3 = ccaGame.getBuildings()[2].getSlots();
        this.slots_bld4 = ccaGame.getBuildings()[3].getSlots();
        this.slots_bld5 = ccaGame.getBuildings()[4].getSlots();
        this.slots_bld6 = ccaGame.getBuildings()[5].getSlots();

        this.slots_bld1_our = ccaGame.getBuildings()[0].getSlots_our();
        this.slots_bld2_our = ccaGame.getBuildings()[1].getSlots_our();
        this.slots_bld3_our = ccaGame.getBuildings()[2].getSlots_our();
        this.slots_bld4_our = ccaGame.getBuildings()[3].getSlots_our();
        this.slots_bld5_our = ccaGame.getBuildings()[4].getSlots_our();
        this.slots_bld6_our = ccaGame.getBuildings()[5].getSlots_our();

        this.slots_bld1_empty = ccaGame.getBuildings()[0].getSlots_empty();
        this.slots_bld2_empty = ccaGame.getBuildings()[1].getSlots_empty();
        this.slots_bld3_empty = ccaGame.getBuildings()[2].getSlots_empty();
        this.slots_bld4_empty = ccaGame.getBuildings()[3].getSlots_empty();
        this.slots_bld5_empty = ccaGame.getBuildings()[4].getSlots_empty();
        this.slots_bld6_empty = ccaGame.getBuildings()[5].getSlots_empty();

        this.slots_bld1_enemy = ccaGame.getBuildings()[0].getSlots_enemy();
        this.slots_bld2_enemy = ccaGame.getBuildings()[1].getSlots_enemy();
        this.slots_bld3_enemy = ccaGame.getBuildings()[2].getSlots_enemy();
        this.slots_bld4_enemy = ccaGame.getBuildings()[3].getSlots_enemy();
        this.slots_bld5_enemy = ccaGame.getBuildings()[4].getSlots_enemy();
        this.slots_bld6_enemy = ccaGame.getBuildings()[5].getSlots_enemy();


        this.needToWin_bld1 = ccaGame.getBuildings()[0].isNeedToWin();
        this.needToWin_bld2 = ccaGame.getBuildings()[1].isNeedToWin();
        this.needToWin_bld3 = ccaGame.getBuildings()[2].isNeedToWin();
        this.needToWin_bld4 = ccaGame.getBuildings()[3].isNeedToWin();
        this.needToWin_bld5 = ccaGame.getBuildings()[4].isNeedToWin();
        this.needToWin_bld6 = ccaGame.getBuildings()[5].isNeedToWin();

        this.needToWinWithoutX2_bld1 = ccaGame.getBuildings()[0].isNeedToWinWithoutX2();
        this.needToWinWithoutX2_bld2 = ccaGame.getBuildings()[1].isNeedToWinWithoutX2();
        this.needToWinWithoutX2_bld3 = ccaGame.getBuildings()[2].isNeedToWinWithoutX2();
        this.needToWinWithoutX2_bld4 = ccaGame.getBuildings()[3].isNeedToWinWithoutX2();
        this.needToWinWithoutX2_bld5 = ccaGame.getBuildings()[4].isNeedToWinWithoutX2();
        this.needToWinWithoutX2_bld6 = ccaGame.getBuildings()[5].isNeedToWinWithoutX2();

        this.needToEarlyWin_bld1 = ccaGame.getBuildings()[0].isNeedToEarlyWin();
        this.needToEarlyWin_bld2 = ccaGame.getBuildings()[1].isNeedToEarlyWin();
        this.needToEarlyWin_bld3 = ccaGame.getBuildings()[2].isNeedToEarlyWin();
        this.needToEarlyWin_bld4 = ccaGame.getBuildings()[3].isNeedToEarlyWin();
        this.needToEarlyWin_bld5 = ccaGame.getBuildings()[4].isNeedToEarlyWin();
        this.needToEarlyWin_bld6 = ccaGame.getBuildings()[5].isNeedToEarlyWin();

        this.needToEarlyWinWithoutX2_bld1 = ccaGame.getBuildings()[0].isNeedToEarlyWinWithoutX2();
        this.needToEarlyWinWithoutX2_bld2 = ccaGame.getBuildings()[1].isNeedToEarlyWinWithoutX2();
        this.needToEarlyWinWithoutX2_bld3 = ccaGame.getBuildings()[2].isNeedToEarlyWinWithoutX2();
        this.needToEarlyWinWithoutX2_bld4 = ccaGame.getBuildings()[3].isNeedToEarlyWinWithoutX2();
        this.needToEarlyWinWithoutX2_bld5 = ccaGame.getBuildings()[4].isNeedToEarlyWinWithoutX2();
        this.needToEarlyWinWithoutX2_bld6 = ccaGame.getBuildings()[5].isNeedToEarlyWinWithoutX2();

        this.useInForecast_bld1 = ccaGame.getBuildings()[0].isUseInForecast();
        this.useInForecast_bld2 = ccaGame.getBuildings()[1].isUseInForecast();
        this.useInForecast_bld3 = ccaGame.getBuildings()[2].isUseInForecast();
        this.useInForecast_bld4 = ccaGame.getBuildings()[3].isUseInForecast();
        this.useInForecast_bld5 = ccaGame.getBuildings()[4].isUseInForecast();
        this.useInForecast_bld6 = ccaGame.getBuildings()[5].isUseInForecast();

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

        map.put("isPresent_bld1", isPresent_bld1);
        map.put("isPresent_bld2", isPresent_bld2);
        map.put("isPresent_bld3", isPresent_bld3);
        map.put("isPresent_bld4", isPresent_bld4);
        map.put("isPresent_bld5", isPresent_bld5);
        map.put("isPresent_bld6", isPresent_bld6);

        map.put("isX2_bld1", isX2_bld1);
        map.put("isX2_bld2", isX2_bld2);
        map.put("isX2_bld3", isX2_bld3);
        map.put("isX2_bld4", isX2_bld4);
        map.put("isX2_bld5", isX2_bld5);
        map.put("isX2_bld6", isX2_bld6);

        map.put("mayX2_bld1", mayX2_bld1);
        map.put("mayX2_bld2", mayX2_bld2);
        map.put("mayX2_bld3", mayX2_bld3);
        map.put("mayX2_bld4", mayX2_bld4);
        map.put("mayX2_bld5", mayX2_bld5);
        map.put("mayX2_bld6", mayX2_bld6);

        map.put("buildingIsOur_bld1", buildingIsOur_bld1);
        map.put("buildingIsOur_bld2", buildingIsOur_bld2);
        map.put("buildingIsOur_bld3", buildingIsOur_bld3);
        map.put("buildingIsOur_bld4", buildingIsOur_bld4);
        map.put("buildingIsOur_bld5", buildingIsOur_bld5);
        map.put("buildingIsOur_bld6", buildingIsOur_bld6);

        map.put("buildingIsEmpty_bld1", buildingIsEmpty_bld1);
        map.put("buildingIsEmpty_bld2", buildingIsEmpty_bld2);
        map.put("buildingIsEmpty_bld3", buildingIsEmpty_bld3);
        map.put("buildingIsEmpty_bld4", buildingIsEmpty_bld4);
        map.put("buildingIsEmpty_bld5", buildingIsEmpty_bld5);
        map.put("buildingIsEmpty_bld6", buildingIsEmpty_bld6);

        map.put("buildingIsEnemy_bld1", buildingIsEnemy_bld1);
        map.put("buildingIsEnemy_bld2", buildingIsEnemy_bld2);
        map.put("buildingIsEnemy_bld3", buildingIsEnemy_bld3);
        map.put("buildingIsEnemy_bld4", buildingIsEnemy_bld4);
        map.put("buildingIsEnemy_bld5", buildingIsEnemy_bld5);
        map.put("buildingIsEnemy_bld6", buildingIsEnemy_bld6);

        map.put("our_points_bld1", our_points_bld1);
        map.put("our_points_bld2", our_points_bld2);
        map.put("our_points_bld3", our_points_bld3);
        map.put("our_points_bld4", our_points_bld4);
        map.put("our_points_bld5", our_points_bld5);
        map.put("our_points_bld6", our_points_bld6);

        map.put("enemy_points_bld1", enemy_points_bld1);
        map.put("enemy_points_bld2", enemy_points_bld2);
        map.put("enemy_points_bld3", enemy_points_bld3);
        map.put("enemy_points_bld4", enemy_points_bld4);
        map.put("enemy_points_bld5", enemy_points_bld5);
        map.put("enemy_points_bld6", enemy_points_bld6);

        map.put("slots_bld1", slots_bld1);
        map.put("slots_bld1_our", slots_bld1_our);
        map.put("slots_bld1_empty", slots_bld1_empty);
        map.put("slots_bld1_enemy", slots_bld1_enemy);
        map.put("slots_bld2", slots_bld2);
        map.put("slots_bld2_our", slots_bld2_our);
        map.put("slots_bld2_empty", slots_bld2_empty);
        map.put("slots_bld2_enemy", slots_bld2_enemy);
        map.put("slots_bld3", slots_bld3);
        map.put("slots_bld3_our", slots_bld3_our);
        map.put("slots_bld3_empty", slots_bld3_empty);
        map.put("slots_bld3_enemy", slots_bld3_enemy);
        map.put("slots_bld4", slots_bld4);
        map.put("slots_bld4_our", slots_bld4_our);
        map.put("slots_bld4_empty", slots_bld4_empty);
        map.put("slots_bld4_enemy", slots_bld4_enemy);
        map.put("slots_bld5", slots_bld5);
        map.put("slots_bld5_our", slots_bld5_our);
        map.put("slots_bld5_empty", slots_bld5_empty);
        map.put("slots_bld5_enemy", slots_bld5_enemy);
        map.put("slots_bld6", slots_bld6);
        map.put("slots_bld6_our", slots_bld6_our);
        map.put("slots_bld6_empty", slots_bld6_empty);
        map.put("slots_bld6_enemy", slots_bld6_enemy);

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
        map.put("needToWin_bld1", needToWin_bld1);
        map.put("needToWinWithoutX2_bld1", needToWinWithoutX2_bld1);
        map.put("needToEarlyWin_bld1", needToEarlyWin_bld1);
        map.put("needToEarlyWinWithoutX2_bld1", needToEarlyWinWithoutX2_bld1);
        map.put("needToWin_bld2", needToWin_bld2);
        map.put("needToWinWithoutX2_bld2", needToWinWithoutX2_bld2);
        map.put("needToEarlyWin_bld2", needToEarlyWin_bld2);
        map.put("needToEarlyWinWithoutX2_bld2", needToEarlyWinWithoutX2_bld2);
        map.put("needToWin_bld3", needToWin_bld3);
        map.put("needToWinWithoutX2_bld3", needToWinWithoutX2_bld3);
        map.put("needToEarlyWin_bld3", needToEarlyWin_bld3);
        map.put("needToEarlyWinWithoutX2_bld3", needToEarlyWinWithoutX2_bld3);
        map.put("needToWin_bld4", needToWin_bld4);
        map.put("needToWinWithoutX2_bld4", needToWinWithoutX2_bld4);
        map.put("needToEarlyWin_bld4", needToEarlyWin_bld4);
        map.put("needToEarlyWinWithoutX2_bld4", needToEarlyWinWithoutX2_bld4);
        map.put("needToWin_bld5", needToWin_bld5);
        map.put("needToWinWithoutX2_bld5", needToWinWithoutX2_bld5);
        map.put("needToEarlyWin_bld5", needToEarlyWin_bld5);
        map.put("needToEarlyWinWithoutX2_bld5", needToEarlyWinWithoutX2_bld5);
        map.put("needToWin_bld6", needToWin_bld6);
        map.put("needToWinWithoutX2_bld6", needToWinWithoutX2_bld6);
        map.put("needToEarlyWin_bld6", needToEarlyWin_bld6);
        map.put("needToEarlyWinWithoutX2_bld6", needToEarlyWinWithoutX2_bld6);

        map.put("useInForecast_bld1", useInForecast_bld1);
        map.put("useInForecast_bld2", useInForecast_bld2);
        map.put("useInForecast_bld3", useInForecast_bld3);
        map.put("useInForecast_bld4", useInForecast_bld4);
        map.put("useInForecast_bld5", useInForecast_bld5);
        map.put("useInForecast_bld6", useInForecast_bld6);

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

    public boolean isPresent_bld1() {
        return isPresent_bld1;
    }

    public void setPresent_bld1(boolean present_bld1) {
        isPresent_bld1 = present_bld1;
    }

    public boolean isPresent_bld2() {
        return isPresent_bld2;
    }

    public void setPresent_bld2(boolean present_bld2) {
        isPresent_bld2 = present_bld2;
    }

    public boolean isPresent_bld3() {
        return isPresent_bld3;
    }

    public void setPresent_bld3(boolean present_bld3) {
        isPresent_bld3 = present_bld3;
    }

    public boolean isPresent_bld4() {
        return isPresent_bld4;
    }

    public void setPresent_bld4(boolean present_bld4) {
        isPresent_bld4 = present_bld4;
    }

    public boolean isPresent_bld5() {
        return isPresent_bld5;
    }

    public void setPresent_bld5(boolean present_bld5) {
        isPresent_bld5 = present_bld5;
    }

    public boolean isPresent_bld6() {
        return isPresent_bld6;
    }

    public void setPresent_bld6(boolean present_bld6) {
        isPresent_bld6 = present_bld6;
    }

    public boolean isX2_bld1() {
        return isX2_bld1;
    }

    public void setX2_bld1(boolean x2_bld1) {
        isX2_bld1 = x2_bld1;
    }

    public boolean isX2_bld2() {
        return isX2_bld2;
    }

    public void setX2_bld2(boolean x2_bld2) {
        isX2_bld2 = x2_bld2;
    }

    public boolean isX2_bld3() {
        return isX2_bld3;
    }

    public void setX2_bld3(boolean x2_bld3) {
        isX2_bld3 = x2_bld3;
    }

    public boolean isX2_bld4() {
        return isX2_bld4;
    }

    public void setX2_bld4(boolean x2_bld4) {
        isX2_bld4 = x2_bld4;
    }

    public boolean isX2_bld5() {
        return isX2_bld5;
    }

    public void setX2_bld5(boolean x2_bld5) {
        isX2_bld5 = x2_bld5;
    }

    public boolean isX2_bld6() {
        return isX2_bld6;
    }

    public void setX2_bld6(boolean x2_bld6) {
        isX2_bld6 = x2_bld6;
    }

    public boolean isMayX2_bld1() {
        return mayX2_bld1;
    }

    public void setMayX2_bld1(boolean mayX2_bld1) {
        this.mayX2_bld1 = mayX2_bld1;
    }

    public boolean isMayX2_bld2() {
        return mayX2_bld2;
    }

    public void setMayX2_bld2(boolean mayX2_bld2) {
        this.mayX2_bld2 = mayX2_bld2;
    }

    public boolean isMayX2_bld3() {
        return mayX2_bld3;
    }

    public void setMayX2_bld3(boolean mayX2_bld3) {
        this.mayX2_bld3 = mayX2_bld3;
    }

    public boolean isMayX2_bld4() {
        return mayX2_bld4;
    }

    public void setMayX2_bld4(boolean mayX2_bld4) {
        this.mayX2_bld4 = mayX2_bld4;
    }

    public boolean isMayX2_bld5() {
        return mayX2_bld5;
    }

    public void setMayX2_bld5(boolean mayX2_bld5) {
        this.mayX2_bld5 = mayX2_bld5;
    }

    public boolean isMayX2_bld6() {
        return mayX2_bld6;
    }

    public void setMayX2_bld6(boolean mayX2_bld6) {
        this.mayX2_bld6 = mayX2_bld6;
    }

    public boolean isBuildingIsOur_bld1() {
        return buildingIsOur_bld1;
    }

    public void setBuildingIsOur_bld1(boolean buildingIsOur_bld1) {
        this.buildingIsOur_bld1 = buildingIsOur_bld1;
    }

    public boolean isBuildingIsOur_bld2() {
        return buildingIsOur_bld2;
    }

    public void setBuildingIsOur_bld2(boolean buildingIsOur_bld2) {
        this.buildingIsOur_bld2 = buildingIsOur_bld2;
    }

    public boolean isBuildingIsOur_bld3() {
        return buildingIsOur_bld3;
    }

    public void setBuildingIsOur_bld3(boolean buildingIsOur_bld3) {
        this.buildingIsOur_bld3 = buildingIsOur_bld3;
    }

    public boolean isBuildingIsOur_bld4() {
        return buildingIsOur_bld4;
    }

    public void setBuildingIsOur_bld4(boolean buildingIsOur_bld4) {
        this.buildingIsOur_bld4 = buildingIsOur_bld4;
    }

    public boolean isBuildingIsOur_bld5() {
        return buildingIsOur_bld5;
    }

    public void setBuildingIsOur_bld5(boolean buildingIsOur_bld5) {
        this.buildingIsOur_bld5 = buildingIsOur_bld5;
    }

    public boolean isBuildingIsOur_bld6() {
        return buildingIsOur_bld6;
    }

    public void setBuildingIsOur_bld6(boolean buildingIsOur_bld6) {
        this.buildingIsOur_bld6 = buildingIsOur_bld6;
    }

    public boolean isBuildingIsEmpty_bld1() {
        return buildingIsEmpty_bld1;
    }

    public void setBuildingIsEmpty_bld1(boolean buildingIsEmpty_bld1) {
        this.buildingIsEmpty_bld1 = buildingIsEmpty_bld1;
    }

    public boolean isBuildingIsEmpty_bld2() {
        return buildingIsEmpty_bld2;
    }

    public void setBuildingIsEmpty_bld2(boolean buildingIsEmpty_bld2) {
        this.buildingIsEmpty_bld2 = buildingIsEmpty_bld2;
    }

    public boolean isBuildingIsEmpty_bld3() {
        return buildingIsEmpty_bld3;
    }

    public void setBuildingIsEmpty_bld3(boolean buildingIsEmpty_bld3) {
        this.buildingIsEmpty_bld3 = buildingIsEmpty_bld3;
    }

    public boolean isBuildingIsEmpty_bld4() {
        return buildingIsEmpty_bld4;
    }

    public void setBuildingIsEmpty_bld4(boolean buildingIsEmpty_bld4) {
        this.buildingIsEmpty_bld4 = buildingIsEmpty_bld4;
    }

    public boolean isBuildingIsEmpty_bld5() {
        return buildingIsEmpty_bld5;
    }

    public void setBuildingIsEmpty_bld5(boolean buildingIsEmpty_bld5) {
        this.buildingIsEmpty_bld5 = buildingIsEmpty_bld5;
    }

    public boolean isBuildingIsEmpty_bld6() {
        return buildingIsEmpty_bld6;
    }

    public void setBuildingIsEmpty_bld6(boolean buildingIsEmpty_bld6) {
        this.buildingIsEmpty_bld6 = buildingIsEmpty_bld6;
    }

    public boolean isBuildingIsEnemy_bld1() {
        return buildingIsEnemy_bld1;
    }

    public void setBuildingIsEnemy_bld1(boolean buildingIsEnemy_bld1) {
        this.buildingIsEnemy_bld1 = buildingIsEnemy_bld1;
    }

    public boolean isBuildingIsEnemy_bld2() {
        return buildingIsEnemy_bld2;
    }

    public void setBuildingIsEnemy_bld2(boolean buildingIsEnemy_bld2) {
        this.buildingIsEnemy_bld2 = buildingIsEnemy_bld2;
    }

    public boolean isBuildingIsEnemy_bld3() {
        return buildingIsEnemy_bld3;
    }

    public void setBuildingIsEnemy_bld3(boolean buildingIsEnemy_bld3) {
        this.buildingIsEnemy_bld3 = buildingIsEnemy_bld3;
    }

    public boolean isBuildingIsEnemy_bld4() {
        return buildingIsEnemy_bld4;
    }

    public void setBuildingIsEnemy_bld4(boolean buildingIsEnemy_bld4) {
        this.buildingIsEnemy_bld4 = buildingIsEnemy_bld4;
    }

    public boolean isBuildingIsEnemy_bld5() {
        return buildingIsEnemy_bld5;
    }

    public void setBuildingIsEnemy_bld5(boolean buildingIsEnemy_bld5) {
        this.buildingIsEnemy_bld5 = buildingIsEnemy_bld5;
    }

    public boolean isBuildingIsEnemy_bld6() {
        return buildingIsEnemy_bld6;
    }

    public void setBuildingIsEnemy_bld6(boolean buildingIsEnemy_bld6) {
        this.buildingIsEnemy_bld6 = buildingIsEnemy_bld6;
    }

    public int getOur_points_bld1() {
        return our_points_bld1;
    }

    public void setOur_points_bld1(int our_points_bld1) {
        this.our_points_bld1 = our_points_bld1;
    }

    public int getOur_points_bld2() {
        return our_points_bld2;
    }

    public void setOur_points_bld2(int our_points_bld2) {
        this.our_points_bld2 = our_points_bld2;
    }

    public int getOur_points_bld3() {
        return our_points_bld3;
    }

    public void setOur_points_bld3(int our_points_bld3) {
        this.our_points_bld3 = our_points_bld3;
    }

    public int getOur_points_bld4() {
        return our_points_bld4;
    }

    public void setOur_points_bld4(int our_points_bld4) {
        this.our_points_bld4 = our_points_bld4;
    }

    public int getOur_points_bld5() {
        return our_points_bld5;
    }

    public void setOur_points_bld5(int our_points_bld5) {
        this.our_points_bld5 = our_points_bld5;
    }

    public int getOur_points_bld6() {
        return our_points_bld6;
    }

    public void setOur_points_bld6(int our_points_bld6) {
        this.our_points_bld6 = our_points_bld6;
    }

    public int getEnemy_points_bld1() {
        return enemy_points_bld1;
    }

    public void setEnemy_points_bld1(int enemy_points_bld1) {
        this.enemy_points_bld1 = enemy_points_bld1;
    }

    public int getEnemy_points_bld2() {
        return enemy_points_bld2;
    }

    public void setEnemy_points_bld2(int enemy_points_bld2) {
        this.enemy_points_bld2 = enemy_points_bld2;
    }

    public int getEnemy_points_bld3() {
        return enemy_points_bld3;
    }

    public void setEnemy_points_bld3(int enemy_points_bld3) {
        this.enemy_points_bld3 = enemy_points_bld3;
    }

    public int getEnemy_points_bld4() {
        return enemy_points_bld4;
    }

    public void setEnemy_points_bld4(int enemy_points_bld4) {
        this.enemy_points_bld4 = enemy_points_bld4;
    }

    public int getEnemy_points_bld5() {
        return enemy_points_bld5;
    }

    public void setEnemy_points_bld5(int enemy_points_bld5) {
        this.enemy_points_bld5 = enemy_points_bld5;
    }

    public int getEnemy_points_bld6() {
        return enemy_points_bld6;
    }

    public void setEnemy_points_bld6(int enemy_points_bld6) {
        this.enemy_points_bld6 = enemy_points_bld6;
    }

    public int getSlots_bld1() {
        return slots_bld1;
    }

    public void setSlots_bld1(int slots_bld1) {
        this.slots_bld1 = slots_bld1;
    }

    public int getSlots_bld1_our() {
        return slots_bld1_our;
    }

    public void setSlots_bld1_our(int slots_bld1_our) {
        this.slots_bld1_our = slots_bld1_our;
    }

    public int getSlots_bld1_empty() {
        return slots_bld1_empty;
    }

    public void setSlots_bld1_empty(int slots_bld1_empty) {
        this.slots_bld1_empty = slots_bld1_empty;
    }

    public int getSlots_bld1_enemy() {
        return slots_bld1_enemy;
    }

    public void setSlots_bld1_enemy(int slots_bld1_enemy) {
        this.slots_bld1_enemy = slots_bld1_enemy;
    }

    public int getSlots_bld2() {
        return slots_bld2;
    }

    public void setSlots_bld2(int slots_bld2) {
        this.slots_bld2 = slots_bld2;
    }

    public int getSlots_bld2_our() {
        return slots_bld2_our;
    }

    public void setSlots_bld2_our(int slots_bld2_our) {
        this.slots_bld2_our = slots_bld2_our;
    }

    public int getSlots_bld2_empty() {
        return slots_bld2_empty;
    }

    public void setSlots_bld2_empty(int slots_bld2_empty) {
        this.slots_bld2_empty = slots_bld2_empty;
    }

    public int getSlots_bld2_enemy() {
        return slots_bld2_enemy;
    }

    public void setSlots_bld2_enemy(int slots_bld2_enemy) {
        this.slots_bld2_enemy = slots_bld2_enemy;
    }

    public int getSlots_bld3() {
        return slots_bld3;
    }

    public void setSlots_bld3(int slots_bld3) {
        this.slots_bld3 = slots_bld3;
    }

    public int getSlots_bld3_our() {
        return slots_bld3_our;
    }

    public void setSlots_bld3_our(int slots_bld3_our) {
        this.slots_bld3_our = slots_bld3_our;
    }

    public int getSlots_bld3_empty() {
        return slots_bld3_empty;
    }

    public void setSlots_bld3_empty(int slots_bld3_empty) {
        this.slots_bld3_empty = slots_bld3_empty;
    }

    public int getSlots_bld3_enemy() {
        return slots_bld3_enemy;
    }

    public void setSlots_bld3_enemy(int slots_bld3_enemy) {
        this.slots_bld3_enemy = slots_bld3_enemy;
    }

    public int getSlots_bld4() {
        return slots_bld4;
    }

    public void setSlots_bld4(int slots_bld4) {
        this.slots_bld4 = slots_bld4;
    }

    public int getSlots_bld4_our() {
        return slots_bld4_our;
    }

    public void setSlots_bld4_our(int slots_bld4_our) {
        this.slots_bld4_our = slots_bld4_our;
    }

    public int getSlots_bld4_empty() {
        return slots_bld4_empty;
    }

    public void setSlots_bld4_empty(int slots_bld4_empty) {
        this.slots_bld4_empty = slots_bld4_empty;
    }

    public int getSlots_bld4_enemy() {
        return slots_bld4_enemy;
    }

    public void setSlots_bld4_enemy(int slots_bld4_enemy) {
        this.slots_bld4_enemy = slots_bld4_enemy;
    }

    public int getSlots_bld5() {
        return slots_bld5;
    }

    public void setSlots_bld5(int slots_bld5) {
        this.slots_bld5 = slots_bld5;
    }

    public int getSlots_bld5_our() {
        return slots_bld5_our;
    }

    public void setSlots_bld5_our(int slots_bld5_our) {
        this.slots_bld5_our = slots_bld5_our;
    }

    public int getSlots_bld5_empty() {
        return slots_bld5_empty;
    }

    public void setSlots_bld5_empty(int slots_bld5_empty) {
        this.slots_bld5_empty = slots_bld5_empty;
    }

    public int getSlots_bld5_enemy() {
        return slots_bld5_enemy;
    }

    public void setSlots_bld5_enemy(int slots_bld5_enemy) {
        this.slots_bld5_enemy = slots_bld5_enemy;
    }

    public int getSlots_bld6() {
        return slots_bld6;
    }

    public void setSlots_bld6(int slots_bld6) {
        this.slots_bld6 = slots_bld6;
    }

    public int getSlots_bld6_our() {
        return slots_bld6_our;
    }

    public void setSlots_bld6_our(int slots_bld6_our) {
        this.slots_bld6_our = slots_bld6_our;
    }

    public int getSlots_bld6_empty() {
        return slots_bld6_empty;
    }

    public void setSlots_bld6_empty(int slots_bld6_empty) {
        this.slots_bld6_empty = slots_bld6_empty;
    }

    public int getSlots_bld6_enemy() {
        return slots_bld6_enemy;
    }

    public void setSlots_bld6_enemy(int slots_bld6_enemy) {
        this.slots_bld6_enemy = slots_bld6_enemy;
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

    public boolean isNeedToWin_bld1() {
        return needToWin_bld1;
    }

    public void setNeedToWin_bld1(boolean needToWin_bld1) {
        this.needToWin_bld1 = needToWin_bld1;
    }

    public boolean isNeedToWinWithoutX2_bld1() {
        return needToWinWithoutX2_bld1;
    }

    public void setNeedToWinWithoutX2_bld1(boolean needToWinWithoutX2_bld1) {
        this.needToWinWithoutX2_bld1 = needToWinWithoutX2_bld1;
    }

    public boolean isNeedToEarlyWin_bld1() {
        return needToEarlyWin_bld1;
    }

    public void setNeedToEarlyWin_bld1(boolean needToEarlyWin_bld1) {
        this.needToEarlyWin_bld1 = needToEarlyWin_bld1;
    }

    public boolean isNeedToEarlyWinWithoutX2_bld1() {
        return needToEarlyWinWithoutX2_bld1;
    }

    public void setNeedToEarlyWinWithoutX2_bld1(boolean needToEarlyWinWithoutX2_bld1) {
        this.needToEarlyWinWithoutX2_bld1 = needToEarlyWinWithoutX2_bld1;
    }

    public boolean isNeedToWin_bld2() {
        return needToWin_bld2;
    }

    public void setNeedToWin_bld2(boolean needToWin_bld2) {
        this.needToWin_bld2 = needToWin_bld2;
    }

    public boolean isNeedToWinWithoutX2_bld2() {
        return needToWinWithoutX2_bld2;
    }

    public void setNeedToWinWithoutX2_bld2(boolean needToWinWithoutX2_bld2) {
        this.needToWinWithoutX2_bld2 = needToWinWithoutX2_bld2;
    }

    public boolean isNeedToEarlyWin_bld2() {
        return needToEarlyWin_bld2;
    }

    public void setNeedToEarlyWin_bld2(boolean needToEarlyWin_bld2) {
        this.needToEarlyWin_bld2 = needToEarlyWin_bld2;
    }

    public boolean isNeedToEarlyWinWithoutX2_bld2() {
        return needToEarlyWinWithoutX2_bld2;
    }

    public void setNeedToEarlyWinWithoutX2_bld2(boolean needToEarlyWinWithoutX2_bld2) {
        this.needToEarlyWinWithoutX2_bld2 = needToEarlyWinWithoutX2_bld2;
    }

    public boolean isNeedToWin_bld3() {
        return needToWin_bld3;
    }

    public void setNeedToWin_bld3(boolean needToWin_bld3) {
        this.needToWin_bld3 = needToWin_bld3;
    }

    public boolean isNeedToWinWithoutX2_bld3() {
        return needToWinWithoutX2_bld3;
    }

    public void setNeedToWinWithoutX2_bld3(boolean needToWinWithoutX2_bld3) {
        this.needToWinWithoutX2_bld3 = needToWinWithoutX2_bld3;
    }

    public boolean isNeedToEarlyWin_bld3() {
        return needToEarlyWin_bld3;
    }

    public void setNeedToEarlyWin_bld3(boolean needToEarlyWin_bld3) {
        this.needToEarlyWin_bld3 = needToEarlyWin_bld3;
    }

    public boolean isNeedToEarlyWinWithoutX2_bld3() {
        return needToEarlyWinWithoutX2_bld3;
    }

    public void setNeedToEarlyWinWithoutX2_bld3(boolean needToEarlyWinWithoutX2_bld3) {
        this.needToEarlyWinWithoutX2_bld3 = needToEarlyWinWithoutX2_bld3;
    }

    public boolean isNeedToWin_bld4() {
        return needToWin_bld4;
    }

    public void setNeedToWin_bld4(boolean needToWin_bld4) {
        this.needToWin_bld4 = needToWin_bld4;
    }

    public boolean isNeedToWinWithoutX2_bld4() {
        return needToWinWithoutX2_bld4;
    }

    public void setNeedToWinWithoutX2_bld4(boolean needToWinWithoutX2_bld4) {
        this.needToWinWithoutX2_bld4 = needToWinWithoutX2_bld4;
    }

    public boolean isNeedToEarlyWin_bld4() {
        return needToEarlyWin_bld4;
    }

    public void setNeedToEarlyWin_bld4(boolean needToEarlyWin_bld4) {
        this.needToEarlyWin_bld4 = needToEarlyWin_bld4;
    }

    public boolean isNeedToEarlyWinWithoutX2_bld4() {
        return needToEarlyWinWithoutX2_bld4;
    }

    public void setNeedToEarlyWinWithoutX2_bld4(boolean needToEarlyWinWithoutX2_bld4) {
        this.needToEarlyWinWithoutX2_bld4 = needToEarlyWinWithoutX2_bld4;
    }

    public boolean isNeedToWin_bld5() {
        return needToWin_bld5;
    }

    public void setNeedToWin_bld5(boolean needToWin_bld5) {
        this.needToWin_bld5 = needToWin_bld5;
    }

    public boolean isNeedToWinWithoutX2_bld5() {
        return needToWinWithoutX2_bld5;
    }

    public void setNeedToWinWithoutX2_bld5(boolean needToWinWithoutX2_bld5) {
        this.needToWinWithoutX2_bld5 = needToWinWithoutX2_bld5;
    }

    public boolean isNeedToEarlyWin_bld5() {
        return needToEarlyWin_bld5;
    }

    public void setNeedToEarlyWin_bld5(boolean needToEarlyWin_bld5) {
        this.needToEarlyWin_bld5 = needToEarlyWin_bld5;
    }

    public boolean isNeedToEarlyWinWithoutX2_bld5() {
        return needToEarlyWinWithoutX2_bld5;
    }

    public void setNeedToEarlyWinWithoutX2_bld5(boolean needToEarlyWinWithoutX2_bld5) {
        this.needToEarlyWinWithoutX2_bld5 = needToEarlyWinWithoutX2_bld5;
    }

    public boolean isNeedToWin_bld6() {
        return needToWin_bld6;
    }

    public void setNeedToWin_bld6(boolean needToWin_bld6) {
        this.needToWin_bld6 = needToWin_bld6;
    }

    public boolean isNeedToWinWithoutX2_bld6() {
        return needToWinWithoutX2_bld6;
    }

    public void setNeedToWinWithoutX2_bld6(boolean needToWinWithoutX2_bld6) {
        this.needToWinWithoutX2_bld6 = needToWinWithoutX2_bld6;
    }

    public boolean isNeedToEarlyWin_bld6() {
        return needToEarlyWin_bld6;
    }

    public void setNeedToEarlyWin_bld6(boolean needToEarlyWin_bld6) {
        this.needToEarlyWin_bld6 = needToEarlyWin_bld6;
    }

    public boolean isNeedToEarlyWinWithoutX2_bld6() {
        return needToEarlyWinWithoutX2_bld6;
    }

    public void setNeedToEarlyWinWithoutX2_bld6(boolean needToEarlyWinWithoutX2_bld6) {
        this.needToEarlyWinWithoutX2_bld6 = needToEarlyWinWithoutX2_bld6;
    }

    public boolean isUseInForecast_bld1() {
        return useInForecast_bld1;
    }

    public void setUseInForecast_bld1(boolean useInForecast_bld1) {
        this.useInForecast_bld1 = useInForecast_bld1;
    }

    public boolean isUseInForecast_bld2() {
        return useInForecast_bld2;
    }

    public void setUseInForecast_bld2(boolean useInForecast_bld2) {
        this.useInForecast_bld2 = useInForecast_bld2;
    }

    public boolean isUseInForecast_bld3() {
        return useInForecast_bld3;
    }

    public void setUseInForecast_bld3(boolean useInForecast_bld3) {
        this.useInForecast_bld3 = useInForecast_bld3;
    }

    public boolean isUseInForecast_bld4() {
        return useInForecast_bld4;
    }

    public void setUseInForecast_bld4(boolean useInForecast_bld4) {
        this.useInForecast_bld4 = useInForecast_bld4;
    }

    public boolean isUseInForecast_bld5() {
        return useInForecast_bld5;
    }

    public void setUseInForecast_bld5(boolean useInForecast_bld5) {
        this.useInForecast_bld5 = useInForecast_bld5;
    }

    public boolean isUseInForecast_bld6() {
        return useInForecast_bld6;
    }

    public void setUseInForecast_bld6(boolean useInForecast_bld6) {
        this.useInForecast_bld6 = useInForecast_bld6;
    }
}
