package com.svoemestodev.catscitycalc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Utils {


    public static void copyFile(String sourcePath, String targetPath) {
        File sourceLocation = new File(sourcePath);
        File targetLocation = new File(targetPath);

        try {
            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String convertFloatToStringFormatter2digit(float number) {
        String pattern = "###,##0.00";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(number);
    }

    public static String convertFloatToStringFormatter3digit(float number) {
        String pattern = "###,##0.000";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(number);
    }

    public static String convertFloatToStringFormatter4digit(float number) {
        String pattern = "###,##0.0000";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(number);
    }

    public static String convertFloatToStringFormatter5digit(float number) {
        String pattern = "###,##0.00000";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(number);
    }

    public static String convertIntToStringFormatter(int number) {
        String pattern = "###,##0";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(number);
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

    public static String convertSecondsToHHMMSS(long secundes) {
        if (secundes > 0) {
            int hours = (int)secundes / 3600;
            int minutes = (int)(secundes - hours*3600) / 60;
            int seconds = (int)secundes - hours*3600 - minutes*60;
            return String.format(Locale.getDefault(), "%01d:%02d:%02d", hours, minutes, seconds);
        } else {
            return "";
        }

    }

    public static String convertSecondsToHHMMSSwithoutColon(long secundes) {
        if (secundes > 0) {
            int hours = (int)secundes / 3600;
            int minutes = (int)(secundes - hours*3600) / 60;
            int seconds = (int)secundes - hours*3600 - minutes*60;
            return String.format(Locale.getDefault(), "%01d%02d%02d", hours, minutes, seconds);
        } else {
            return "";
        }

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
        for (char ch : str.toUpperCase().toCharArray()) { // цикл по символам строки
            if (ch >= '0' && ch <='9') {    // если символ - числовой
                result = result + ch;       // приклеиваем его к результату
            }
            if (ch == 'З') result = result + '3';
            if (ch == 'О') result = result + '0';
        }
        if (result.equals("")) result = "0";
        return result;                      // возвращаем результат
    }

    public static String replaceSymbolsLikeNumbers(String str) {
        Map<Character, Character> mapSymbolsLikeNumbers = new HashMap<>();
        mapSymbolsLikeNumbers.put('i', '1');
        mapSymbolsLikeNumbers.put('I', '1');
        mapSymbolsLikeNumbers.put('l', '1');
        mapSymbolsLikeNumbers.put('Т', '1');
        mapSymbolsLikeNumbers.put('T', '1');
        mapSymbolsLikeNumbers.put('t', '1');
        mapSymbolsLikeNumbers.put('з', '3');
        mapSymbolsLikeNumbers.put('З', '3');
        mapSymbolsLikeNumbers.put('Б', '6');
        mapSymbolsLikeNumbers.put('б', '6');
        mapSymbolsLikeNumbers.put('о', '0');
        mapSymbolsLikeNumbers.put('О', '0');
        mapSymbolsLikeNumbers.put('o', '0');
        mapSymbolsLikeNumbers.put('O', '0');
        mapSymbolsLikeNumbers.put('H', '4');
        mapSymbolsLikeNumbers.put('h', '4');

        String result = "";                 // результат
        for (char ch : str.toUpperCase().toCharArray()) { // цикл по символам строки
            char num = mapSymbolsLikeNumbers.containsKey(ch) ? mapSymbolsLikeNumbers.get(ch) : ch;
            result = result + num;
        }
        return result;                      // возвращаем результат
    }

    /**
     * Из строки цифр разделенных пробелом возвращаем строку вида ЧЧ:ММ
     * @param str - строка
     * @return - строка вида ЧЧ:ММ
     */
    public static  String parseTime(String str) {
        // парсинг "Время"
        str = replaceSymbolsLikeNumbers(str).trim().toUpperCase();
        List<String> listWords = new ArrayList<>();
        boolean isNewWord = true;
        String word = "";
        boolean currSymInNum = false;
        boolean prevSymInNum = false;
        char currChar, prevChar;

        for (int i = 0; i < str.length(); i++) {
            currChar = str.charAt(i);
            currSymInNum = (currChar >= '0' && currChar <= '9');
            isNewWord = (currSymInNum != prevSymInNum || i == 0);
            if (i != 0 && isNewWord) {
                listWords.add(word.trim());
                word = "";
            }
            word = word + currChar;
            prevChar = currChar;
            prevSymInNum = currSymInNum;
            if (i == str.length() - 1) listWords.add(word.trim());
        }

        int hours = 0, minutes = 0;
        if (listWords.size() == 4) {
            try {
                listWords.set(0, listWords.get(0).substring(0,listWords.get(0).length()-1));
                hours = Integer.parseInt(listWords.get(0));
            } catch (NumberFormatException e) {
            }
            try {
                minutes = Integer.parseInt(listWords.get(2));
            } catch (NumberFormatException e) {
            }
        } else if (listWords.size() == 3) {
            try {
                listWords.set(0, listWords.get(0).substring(0,listWords.get(0).length()-1));
                hours = Integer.parseInt(listWords.get(0));
            } catch (NumberFormatException e) {
            }
            try {
                minutes = Integer.parseInt(listWords.get(1));
            } catch (NumberFormatException e) {
            }
        } else if (listWords.size() == 2) {
            char lastChar = listWords.get(1).charAt(listWords.get(1).length()-1); // запоминаем последний символ
            listWords.set(0, listWords.get(0).substring(0,listWords.get(0).length()-1));
            listWords.set(1, listWords.get(1).substring(0,listWords.get(1).length()-1));
            if (lastChar == 'M' || lastChar == 'М') {
                try {
                    minutes = Integer.parseInt(listWords.get(0));
                } catch (NumberFormatException e) {
                }
            } else {
                try {
                    hours = Integer.parseInt(listWords.get(0));
                } catch (NumberFormatException e) {
                }
            }
        } else if (listWords.size() == 1) { // если один - это часы. отрезам последний символ
            listWords.set(0, listWords.get(0).substring(0,listWords.get(0).length()-1)); // отрезаем последний символ
            try {
                hours = Integer.parseInt(listWords.get(0));
            } catch (NumberFormatException e) {
            }
        }

        return String.format(Locale.getDefault(), "%01d:%02d", hours, minutes);

    }

    public static long conversTimeStringWithoutColonsToSeconds(String timeStringWithoutColons) {

        String str = "00000" + parseNumbers(timeStringWithoutColons);
        int seconds = Integer.parseInt(str.substring(str.length()-2));
        int minutes = Integer.parseInt(str.substring(str.length()-4, str.length()-2));
        int hours = Integer.parseInt(str.substring(0, str.length()-4));
        return hours*3600 + minutes*60 + seconds;
    }
}