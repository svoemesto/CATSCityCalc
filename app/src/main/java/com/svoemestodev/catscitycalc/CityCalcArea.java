package com.svoemestodev.catscitycalc;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class CityCalcArea {

    CityCalc cityCalc;  // родительский объект
    Area area;
    Bitmap bmpSrc;      // кропнутая картинка (исходник)
    float x1;           // x1
    float x2;           // x2
    float y1;           // y1
    float y2;           // y2
    int [] colors;      // цвета
    int [] ths;         // пороги
    boolean needOcr;    // надо распознавать
    boolean isGeneric = false;  // расчетная картинка
    Bitmap bmpPrc;      // кропнутая картинка (обработанная для распознавания)
    String ocrText = "";     // распознанный текст
    String finText = "";     // распознанный финальный текст

    // конструктор "обычных" картинок
    public CityCalcArea(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int [] ths, boolean needOcr) {

        this.cityCalc = cityCalc;
        this.area = area;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.colors = colors;
        this.ths = ths;
        this.needOcr = needOcr;
        doCut();
        doOCR();

    }

    // конструктор "дженериков"
    public CityCalcArea(CityCalc cityCalc, Area area, int[] colors, int [] ths, boolean needOcr) {
        this.isGeneric = true;
        this.cityCalc = cityCalc;
        this.area = area;
        this.colors = colors;
        this.ths = ths;
        this.needOcr = needOcr;
    }

    public void doOCR() {
        doOCR(0,0,1);
    }

    public void doOCR(int colorIndex, int thmIndex, int thpIndex) {
        if (this.needOcr) {
            this.bmpPrc = PictureProcessor.doBW(this.bmpSrc, this.colors[colorIndex], this.ths[thmIndex], this.ths[thpIndex]);
            this.ocrText = PictureProcessor.doOCR(this.bmpPrc, this.cityCalc.context);

            if (area.equals(Area.TOTAL_TIME)) {
                this.finText = parseTime(this.ocrText);
            } else {
                this.finText = parseNumbers(this.ocrText);
            }
        }
    }

    private String parseNumbers(String str) {
        String result = "";                 // результат
        for (char ch : str.toCharArray()) { // цикл по символам строки
            if (ch >= '0' && ch <='9') {    // если символ - числовой
                result = result + ch;       // приклеиваем его к результату
            }
        }
        if (result.equals("")) result = "0";
        return result;                      // возвращаем результат
    }

    private String parseTime(String str) {
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

    private Bitmap cutSrc() {
        if (this.cityCalc != null) {
            if (this.cityCalc.bmpScreenshot != null) {
                if (!this.isGeneric) {
                    Bitmap sourceBitmap = this.cityCalc.bmpScreenshot;
                    int widthSource = sourceBitmap.getWidth();      // ширина исходной картинки
                    int heightSource = sourceBitmap.getHeight();   // высота исходной картинки

                    int realCalibrateX = (widthSource / 2) > Math.abs(this.cityCalc.calibrateX) ? this.cityCalc.calibrateX : 0;
                    int realCalibrateY = (heightSource / 2) > Math.abs(this.cityCalc.calibrateY) ? this.cityCalc.calibrateY : 0;

                    // координаты для вырезания картинки информации об игре
                    int x1 = (int) ((double) widthSource / 2 + this.x1 * heightSource) + realCalibrateX;
                    int x2 = (int) ((double) widthSource / 2 + this.x2 * heightSource) + realCalibrateX;
                    int y1 = (int) ((double) heightSource / 2 + this.y1 * ((double) heightSource / 2)) + realCalibrateY;
                    int y2 = (int) ((double) heightSource / 2 + this.y2 * ((double) heightSource / 2)) + realCalibrateY;

                    // если координаты вылезаю за границы скриншота - приводим их к границам
                    x1 = Math.max(x1, 0); x2 = Math.min(x2, widthSource);
                    y1 = Math.max(y1, 0);  y2 = Math.min(y2, heightSource);

                    return Bitmap.createBitmap(sourceBitmap, x1, y1, x2 - x1, y2 - y1);
                }
            }
        }
        return null;
    }

    public void doCut() {
        this.bmpSrc = cutSrc();
    }


}
