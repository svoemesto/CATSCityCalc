package com.svoemestodev.catscitycalc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CCAGame extends CityCalcArea {

    String strTotalTime;
    String strStartTime;
    String strEndTime;
    String strEarlyEndTime;
    String strEarlyTotalTime;
    String status;
    Date startTime;
    Date endTime;
    Date endEarlyTime;
    Date screenshotTimeCreation;
    int earlyWin;
    int minutesToEndGame;
    int minutesToEarlyEndGame;
    boolean isTimeOver;
    boolean isGameOver;
    boolean isGameOverEarly;
    boolean isOurWin;
    boolean isEnemyWin;
    boolean isNobodyWin;
    boolean willEarlyWin;
    boolean willOurEarlyWin;
    boolean willEnemyEarlyWin;
    boolean willNobodyEarlyWin;
    boolean willOurWin;
    boolean willEnemyWin;
    boolean willNobodyWin;
    int differentPoints;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());      // форматтер даты

    public CCAGame(CityCalc cityCalc, Area area, float x1, float x2, float y1, float y2, int[] colors, int[] ths, boolean needOcr) {
        super(cityCalc, area, x1, x2, y1, y2, colors, ths, needOcr);
    }

    public void calc(File screenshot, CityCalcArea ccaTotalTime, CityCalcArea ccaEarlyWin) {

        this.screenshotTimeCreation = new Date(screenshot.lastModified());                                              // дата/время создания скриншота
        int fullGameTimeInMinutes = 24*60;                                                                              // кол-во минут в игре - 24 часа
        String[] words = ccaTotalTime.finText.split(":");                                                         // разделяем строку на часы и минуты
        int minutesToEndGameFromScreenshot = Integer.parseInt(words[0])* 60 + Integer.parseInt(words[1]);               // осталось минут до конца игры по скриншоту
        int minutesFromStartGameToCreateScreenshot = fullGameTimeInMinutes - minutesToEndGameFromScreenshot;            // прошло минут с начала игры по скриншоту
        this.startTime = new Date(screenshot.lastModified() - 60_000 * minutesFromStartGameToCreateScreenshot);         // дата/время начала игры
        this.strStartTime = simpleDateFormat.format(this.startTime);                                                    // дата/время начала игры в строковом виде
        this.earlyWin = Integer.parseInt(ccaEarlyWin.finText);                                                          // очки до досрочной победы
        this.updateTime();
    }

    public void updateTime() {

        int fullGameTimeInMinutes = 24*60;                                                                              // кол-во минут в игре - 24 часа
        this.endTime = new Date(this.startTime.getTime() + 60_000 * fullGameTimeInMinutes);                             // дата/время окончания игры
        this.strEndTime = simpleDateFormat.format(this.endTime);                                                        // дата/время окончания игры в строковом виде
        long timeToEndGameInMills = this.endTime.getTime() - Calendar.getInstance().getTime().getTime();                // кол-во миллисекунд до конца игры (от реального времени)
        this.minutesToEndGame = timeToEndGameInMills > 0 ? (int)timeToEndGameInMills / 60_000 : 0;
        int hoursToEndGame = (int) timeToEndGameInMills / (60 * 60 * 1000);                                             // часов до конца игры
        int minutesToEndGame = ((int) timeToEndGameInMills - hoursToEndGame * (60 * 60 * 1000)) / (60 * 1000);          // минут до конца игры
        this.strTotalTime = timeToEndGameInMills >= 0 ? String.format(Locale.getDefault(), "%02d:%02d", hoursToEndGame, minutesToEndGame) : "00:00";  // время до конца игры в строковом формате
        this.isTimeOver = timeToEndGameInMills < 0;
    }

    public void calcWin(CCATeam ccaOurTeam, CCATeam ccaEnemyTeam) {

        ccaOurTeam.updateTime(this);
        ccaEnemyTeam.updateTime(this);

        if (this.isTimeOver && !this.isGameOver) {
            if (this.minutesToEndGame >= 0 && ccaOurTeam.minutesToEarlyWin <= 0) { // если минуты до конца игры не закончились, а наши минуты до досрочной победы уже прошли
                // наша досрочная победа
                this.isGameOver = true;
                this.isGameOverEarly = true;
                this.isOurWin = true;
                this.isEnemyWin = false;
                this.isNobodyWin = false;
                ccaOurTeam.points = this.earlyWin;

                this.endEarlyTime = new Date(this.screenshotTimeCreation.getTime() + ((this.earlyWin - ccaOurTeam.pointsInScreenshot) / (ccaOurTeam.increase == 0 ? 1 : ccaOurTeam.increase) * 60_000));
                this.strEarlyEndTime = simpleDateFormat.format(this.endEarlyTime);

            } else if (this.minutesToEndGame >= 0 && ccaEnemyTeam.minutesToEarlyWin <= 0) { // если минуты до конца игры не закончились, а минуты до досрочной победы противника уже прошли
                // досрочная победа противника
                this.isGameOver = true;
                this.isGameOverEarly = true;
                this.isOurWin = false;
                this.isEnemyWin = true;
                this.isNobodyWin = false;
                ccaEnemyTeam.points = this.earlyWin;

                this.endEarlyTime = new Date(this.screenshotTimeCreation.getTime() + ((this.earlyWin - ccaEnemyTeam.pointsInScreenshot) / (ccaEnemyTeam.increase == 0 ? 1 : ccaEnemyTeam.increase) * 60_000));
                this.strEarlyEndTime = simpleDateFormat.format(this.endEarlyTime);

            }
        }

        if (this.isGameOver && !this.isGameOverEarly) { // если игра закончена не досрочно
            if (ccaOurTeam.points > ccaEnemyTeam.points) {  // если наши очки больше очков противника
                // наша победа
                this.isOurWin = true;
                this.isEnemyWin = false;
                this.isNobodyWin = false;
            } else if (ccaOurTeam.points < ccaEnemyTeam.points) { // если наши очки меньше очков противника
                // победа противника
                this.isOurWin = false;
                this.isEnemyWin = true;
                this.isNobodyWin = false;
            } else {    // если наши очки равны очкам противника
                // ничья
                this.isOurWin = false;
                this.isEnemyWin = false;
                this.isNobodyWin = true;
            }
        }

        this.differentPoints = Math.abs(ccaOurTeam.points - ccaEnemyTeam.points);

        if (this.isGameOver) { // если игра закончена

            if (this.isOurWin) {    // если мы победили
                if (this.isGameOverEarly) {  // если победа досрочная
                    this.status = this.cityCalc.context.getString(R.string.we_instance_win)  + " " + this.differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " (" + this.strEarlyEndTime + ")";
                } else {    // если победа по времени
                    this.status = this.cityCalc.context.getString(R.string.we_win)  + " " + this.differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " (" + this.strEndTime + ")";
                }
            } else if (this.isEnemyWin) { // если победил противник
                if (this.isGameOverEarly) { // если победа досрочная
                    this.status = this.cityCalc.context.getString(R.string.we_instance_lose)  + " " + this.differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " (" + this.strEarlyEndTime + ")";
                } else { // если победа по времени
                    this.status = this.cityCalc.context.getString(R.string.we_lost)  + " " + this.differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " (" + this.strEndTime + ")";
                }
            } else if (this.isNobodyWin) { // если ничья
                if (this.isGameOverEarly) { // если ничья досрочкая
                    this.status = this.cityCalc.context.getString(R.string.instance_nowinner) + " (" + this.strEarlyEndTime + ")";
                } else { // если ничья по времени
                    this.status = this.cityCalc.context.getString(R.string.nowin) + " (" + this.strEndTime + ")";
                }
            }

        } else { // если игра еще идет

            this.willEarlyWin = (ccaOurTeam.minutesToEarlyWin < this.minutesToEndGame || ccaEnemyTeam.minutesToEarlyWin < this.minutesToEndGame); // будет ли досрочная победа
            if (this.willEarlyWin) {    // если будет досрочная победа

                this.minutesToEarlyEndGame = Math.min(ccaOurTeam.minutesToEarlyWin, ccaEnemyTeam.minutesToEarlyWin);

                if (ccaOurTeam.minutesToEarlyWin < this.minutesToEndGame && ccaOurTeam.minutesToEarlyWin < ccaEnemyTeam.minutesToEarlyWin) { // если досрочно победим мы

//                    this.minutesToEarlyEndGame = this.minutesToEndGame - ccaOurTeam.minutesToEarlyWin;
                    this.strEarlyTotalTime = String.format(Locale.getDefault(), "%02d:%02d", this.minutesToEarlyEndGame / 60, this.minutesToEarlyEndGame % 60);
                    ccaOurTeam.strTimeToEarlyWin = this.strEarlyTotalTime;
                    this.endEarlyTime = new Date(Calendar.getInstance().getTime().getTime() + this.minutesToEarlyEndGame * 60_000);
                    this.strEarlyEndTime = simpleDateFormat.format(this.endEarlyTime);

                    this.willOurWin = true;
                    this.willEnemyWin = false;
                    this.willNobodyWin = false;

                    this.willOurEarlyWin = true;
                    this.willEnemyEarlyWin = false;
                    this.willNobodyEarlyWin = false;

                } else if (ccaEnemyTeam.minutesToEarlyWin < ccaOurTeam.minutesToEarlyWin) { // если досрочно победит противник

//                    this.minutesToEarlyEndGame = this.minutesToEndGame - ccaEnemyTeam.minutesToEarlyWin;
                    this.strEarlyTotalTime = String.format(Locale.getDefault(), "%02d:%02d", this.minutesToEarlyEndGame / 60, this.minutesToEarlyEndGame % 60);
                    ccaEnemyTeam.strTimeToEarlyWin = this.strEarlyTotalTime;
                    this.endEarlyTime = new Date(Calendar.getInstance().getTime().getTime() + this.minutesToEarlyEndGame * 60_000);
                    this.strEarlyEndTime = simpleDateFormat.format(this.endEarlyTime);

                    this.willOurWin = false;
                    this.willEnemyWin = true;
                    this.willNobodyWin = false;

                    this.willOurEarlyWin = false;
                    this.willEnemyEarlyWin = true;
                    this.willNobodyEarlyWin = false;

                } else if (ccaEnemyTeam.minutesToEarlyWin == ccaOurTeam.minutesToEarlyWin){ // если будет досрочная ничья

//                    this.minutesToEarlyEndGame = this.minutesToEndGame - ccaOurTeam.minutesToEarlyWin;
                    this.strEarlyTotalTime = String.format(Locale.getDefault(), "%02d:%02d", this.minutesToEarlyEndGame / 60, this.minutesToEarlyEndGame % 60);
                    ccaOurTeam.strTimeToEarlyWin = this.strEarlyTotalTime;
                    ccaEnemyTeam.strTimeToEarlyWin = this.strEarlyTotalTime;
                    this.endEarlyTime = new Date(Calendar.getInstance().getTime().getTime() + this.minutesToEarlyEndGame * 60_000);
                    this.strEarlyEndTime = simpleDateFormat.format(this.endEarlyTime);

                    this.willOurWin = false;
                    this.willEnemyWin = false;
                    this.willNobodyWin = true;

                    this.willOurEarlyWin = false;
                    this.willEnemyEarlyWin = false;
                    this.willNobodyEarlyWin = true;

                }

                ccaOurTeam.willPointsToEndGame = ccaOurTeam.points + ccaOurTeam.increase * this.minutesToEarlyEndGame;
                ccaEnemyTeam.willPointsToEndGame = ccaEnemyTeam.points + ccaEnemyTeam.increase * this.minutesToEarlyEndGame;
                this.differentPoints = Math.abs(ccaOurTeam.willPointsToEndGame - ccaEnemyTeam.willPointsToEndGame);

            } else { // если будет победа по времени

                this.differentPoints = Math.abs(ccaOurTeam.willPointsToEndGame - ccaEnemyTeam.willPointsToEndGame);

                if (ccaOurTeam.willPointsToEndGame > ccaEnemyTeam.willPointsToEndGame) {    // если мы победим

                    this.willOurWin = true;
                    this.willEnemyWin = false;
                    this.willNobodyWin = false;

                    this.willOurEarlyWin = false;
                    this.willEnemyEarlyWin = false;
                    this.willNobodyEarlyWin = false;

                } else if (ccaOurTeam.willPointsToEndGame < ccaEnemyTeam.willPointsToEndGame) { // если победит противник

                    this.willOurWin = false;
                    this.willEnemyWin = true;
                    this.willNobodyWin = false;

                    this.willOurEarlyWin = false;
                    this.willEnemyEarlyWin = false;
                    this.willNobodyEarlyWin = false;

                } else { // если будет ничья

                    this.willOurWin = false;
                    this.willEnemyWin = false;
                    this.willNobodyWin = true;

                    this.willOurEarlyWin = false;
                    this.willEnemyEarlyWin = false;
                    this.willNobodyEarlyWin = false;

                }
            }


            if (this.willEarlyWin) { // если игра закончится досрочно
                if (this.willOurEarlyWin) { // если победим мы
                    this.status = this.cityCalc.context.getString(R.string.we_will_instance_win_with_diff_in) + " " + this.differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " " + this.cityCalc.context.getString(R.string.after) + " " + this.strEarlyTotalTime + " (" + this.strEarlyEndTime + ")";
                } else if (this.willEnemyEarlyWin) { // если победит противник
                    this.status = this.cityCalc.context.getString(R.string.we_will_instance_lose_with_diff_in) + " " + this.differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " " + this.cityCalc.context.getString(R.string.after) + " " + this.strEarlyTotalTime + " (" + this.strEarlyEndTime + ")";
                } else if (this.willNobodyEarlyWin) { // если будет ничья
                    this.status = this.cityCalc.context.getString(R.string.will_instance_nowin_after) + this.strEarlyTotalTime + "(" + this.strEarlyEndTime + ")";
                }
            } else { // если игра закончится по времени
                if (this.willOurWin) { // если победим мы
                    this.status = this.cityCalc.context.getString(R.string.we_will_win_with_diff_in) + " " + this.differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " " + this.cityCalc.context.getString(R.string.after) + " " + this.strTotalTime + " (" + this.strEndTime + ")";
                } else if (this.willEnemyWin) { // если победит противник
                    this.status = this.cityCalc.context.getString(R.string.we_will_lose_with_diff_in) + " " + this.differentPoints + " " + this.cityCalc.context.getString(R.string.points) + " " + this.cityCalc.context.getString(R.string.after) + " " + this.strTotalTime + " (" + this.strEndTime + ")";
                } else if (this.willNobodyWin){ // если будет ничья
                    this.status = this.cityCalc.context.getString(R.string.will_nowin)  + " " + this.cityCalc.context.getString(R.string.after) + " " + this.strTotalTime + "(" + this.strEndTime + ")";
                }
            }
        }


    }

}
