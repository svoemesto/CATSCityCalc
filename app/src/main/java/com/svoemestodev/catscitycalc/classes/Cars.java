package com.svoemestodev.catscitycalc.classes;

import com.svoemestodev.catscitycalc.classes.Car;

import java.io.Serializable;
import java.util.Date;

public class Cars implements Serializable {

    private Car car1;
    private Car car2;
    private Car car3;

    private String userUID;
    private Date dateCreation;

    public Car getCar1() {
        return car1;
    }

    public void setCar1(Car car1) {
        this.car1 = car1;
    }

    public Car getCar2() {
        return car2;
    }

    public void setCar2(Car car2) {
        this.car2 = car2;
    }

    public Car getCar3() {
        return car3;
    }

    public void setCar3(Car car3) {
        this.car3 = car3;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
