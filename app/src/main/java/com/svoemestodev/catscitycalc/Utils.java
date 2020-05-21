package com.svoemestodev.catscitycalc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);

                }
            } else {
                copyFile(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }


    /**
     * Вычисляет количество минут между двумя датами.
     * Может быть отрицательным, если первая дата позднее второй
     * @param dateStart - первая дата
     * @param dateEnd - вторая дата
     * @return - количесвто минут между датами
     */
    public static int getMinutesBetweenDates(Date dateStart, Date dateEnd) {
        return  (int)(dateEnd.getTime() - dateStart.getTime()) / 60_000;
    }

    /**
     * Возвращает количество минут в виде строки формата ЧЧ:ММ
     * @param minutes - количество минут
     * @return - строка формата ЧЧ:ММ
     * Пример: 92 -> "1:32"
     */
    public static String convertMinutesToHHMM(int minutes) {
        return String.format(Locale.getDefault(), "%02d:%02d", Math.abs(minutes) / 60, Math.abs(minutes) % 60);
    }

    /**
     *  Возвращает сроку, состоящую из отформатированной по паттерну даты
     * @param date - дата
     * @param pattern - паттерн
     * @return - дата, отформатированная по паттерну, в строковом виде
     * Пример: 2020-05-14 15:02:09, "dd MMM HH:mm" -> "14 мая 15:02"
     */
    public static String convertDateToString(Date date, String pattern) {
        return  new SimpleDateFormat((pattern == null || pattern.equals("")) ? "dd MMM HH:mm" : pattern, Locale.getDefault()).format(date);
    }

    /**
     * Прибаляет к дате заданное количество минут и возвращает новую дату
     * Если количество минут отрицательное - отнимает
     * @param date - дата
     * @param minutes - минуты
     * @return - дата
     * Пример: 2020-05-14 15:02:09, 62 -> 2020-05-14 16:04:09
     */
    public static Date addMinutesToDate(Date date, int minutes) {
        return new Date(date.getTime() + minutes * 60_000);
    }

    /**
     *  Возвращает строку, состоящую только из цифор
     * @param str - строка
     * @return - строка цифр
     */
    public static  String parseNumbers(String str) {
        String result = "";                 // результат
        for (char ch : str.toCharArray()) { // цикл по символам строки
            if (ch >= '0' && ch <='9') {    // если символ - числовой
                result = result + ch;       // приклеиваем его к результату
            }
        }
        if (result.equals("")) result = "0";
        return result;                      // возвращаем результат
    }

    /**
     * Из строки цифр разделенных пробелом возвращаем строку вида ЧЧ:ММ
     * @param str - строка
     * @return - строка вида ЧЧ:ММ
     */
    public static  String parseTime(String str) {
        // парсинг "Время"
        String[] arrTotalTime = str.split(" ");             // парсим с разделителем "пробел"
        List<String> listTotalTime = new ArrayList<>();            // лист значений
        for (int i = 0; i < arrTotalTime.length; i++) {             // проходим по распарсенному массиву
            if (!arrTotalTime[i].equals("")) {                      // если элемент массива не пустой
                listTotalTime.add(arrTotalTime[i]);                 // добавляем текущий элемент массива в лист
            }
        }

        if (listTotalTime.size() > 1) {                         // если в списке больше 1 элемента
            for (int i = 0; i < listTotalTime.size(); i++) {    // проходим по списку
                listTotalTime.set(i, listTotalTime.get(i).substring(0, listTotalTime.get(i).length() - 1)); // отрезаем у текущего элемента списка последний символ
            }
        }

        if (listTotalTime.size() > 0) {     // если в списке есть элементы
            if (listTotalTime.size() == 1) {    // если в списке 1 элемент
                if (listTotalTime.get(0).substring(listTotalTime.get(0).length()-1).toLowerCase().equals("m")) {    // если последний символ элемента равен букве "М"
                    String hours = "00";    // часы = "00"
                    String minutes = listTotalTime.get(0).substring(0, listTotalTime.get(0).length()-1);    // минуты = элемент без последнего символа
                    if (minutes.length() == 1) minutes = "0" + minutes;    // если минуты состоят из 1 символа -  дописываем "0" в начало минут
                    return hours + ":" + minutes;   // время = часы : минуты
                } else {    // если последний символ элемента не равен букве "М"
                    String hours = listTotalTime.get(0).substring(0, listTotalTime.get(0).length()-1);  // часы = элемент без последнего символа
                    String minutes = "00";  // минуты = "00"
                    return hours + ":" + minutes; // время = часы : минуты
                }
            } else {    // если в списке больше 1 элемента
                String hours = listTotalTime.get(0);    // часы - первый элемент из списка
                String minutes = listTotalTime.get(1);  // минуты - второй элемент из списка
                if (minutes.length() == 1) minutes = "0" + minutes;    // если минуты состоят из 1 символа -  дописываем "0" в начало минут
                return hours + ":" + minutes; // время = часы : минуты
            }
        } else { // если в списке нет элементов
            return "00:00"; // время нулевое
        }
    }

}
