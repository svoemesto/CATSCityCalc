package com.svoemestodev.catscitycalc.classes;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LastModified implements Serializable {
    private String pathToFile;
    private Date lastModified;

    public static boolean setLastModified(File file) {
        return setLastModified(file.getAbsolutePath());
    }

    public static boolean setLastModified(String pathToFile) {
        File file = new File(pathToFile);
        if (file.exists()) {
            File fileML = new File(pathToFile + ".lastModified");
            LastModified lastModified = new LastModified(pathToFile, new Date(file.lastModified()));
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(fileML);
                ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
                oos.writeObject(lastModified);
                oos.close();
                return true;
            } catch (IOException e) {
                Log.e("LastModified", "setLastModified. Ошибка сериализации");
                return false;
            }
        } else {
            return false;
        }

    }

    public LastModified() {
    }

    public LastModified(String pathToFile, Date lastModified) {
        this.pathToFile = pathToFile;
        this.lastModified = lastModified;
    }

    public static Date getLastModified(File file) {
        return getLastModified(file.getAbsolutePath());
    }

    public static Date getLastModified(String pathToFile) {
        File fileML = new File(pathToFile + ".lastModified");
        if (fileML.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(fileML);
                ObjectInputStream ois = new ObjectInputStream(fileInputStream);
                LastModified lastModified = (LastModified) ois.readObject();
                ois.close();
                return lastModified.lastModified;
            } catch (ClassNotFoundException | IOException e) {
                Log.e("LastModified", "getLastModified. Ошибка десериализации.");
                File file = new File(pathToFile);
                if (file.exists()) {
                    return new Date(file.lastModified());
                } else {
                    return null;
                }
            }
        } else {
            File file = new File(pathToFile);
            if (file.exists()) {
                return new Date(file.lastModified());
            } else {
                return null;
            }
        }
    }

}
