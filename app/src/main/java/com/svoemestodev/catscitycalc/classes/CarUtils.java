package com.svoemestodev.catscitycalc.classes;

import java.util.ArrayList;
import java.util.List;

public class CarUtils {

    public static boolean carIsPresentInList(List<Car> list, Car car) {
        boolean isPresent = false;
        for (Car item: list) {
            if (item.getSlot() == car.getSlot() && item.getUserUID().equals(car.getUserUID())) {
                isPresent = true;
                break;
            }
        }
        return  isPresent;
    }

    public static List<Car> removeCarFromList(List<Car> list, Car car) {
        List<Car> result = new ArrayList<>();
        for (Car item: list) {
            if (!(item.getSlot() == car.getSlot() && item.getUserUID().equals(car.getUserUID()))) {
                result.add(item);
            }
        }
        return result;
    }

}
