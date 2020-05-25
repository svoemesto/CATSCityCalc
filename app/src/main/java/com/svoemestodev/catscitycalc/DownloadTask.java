package com.svoemestodev.catscitycalc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask {
    private static final String TAG = "DownloadTask";
    private Context context;

    private String downloadUrl = "", downloadFileName = "", downloadFilePath = "";
    private ProgressDialog progressDialog;

    public DownloadTask(Context context, String downloadUrl, String downloadFilePath) {
        String logMsgPref = "Конструктор: ";
        Log.i(TAG, logMsgPref + "start");

        this.context = context;
        this.downloadUrl = downloadUrl;
        this.downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/'), downloadUrl.length());//Create file name by picking download file name from URL
        this.downloadFilePath = downloadFilePath;

        Log.i(TAG, logMsgPref + "downloadUrl = " + downloadUrl);
        Log.i(TAG, logMsgPref + "downloadFileName = " + this.downloadFileName);
        Log.i(TAG, logMsgPref + "downloadFilePath = " + this.downloadFilePath);

        //Start Downloading Task
        Log.i(TAG, logMsgPref + "new DownloadingTask().execute()");
        new DownloadingTask().execute();
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        private static final String TAG = "DownloadingTask";
        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            String logMsgPref = "onPreExecute: ";
            Log.i(TAG, logMsgPref + "start");

            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Downloading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            String logMsgPref = "onPostExecute: ";
            Log.i(TAG, logMsgPref + "start");
            try {
                if (outputFile != null) {
                    Log.i(TAG, logMsgPref + "outputFile != null");
                    progressDialog.dismiss();
//                    ContextThemeWrapper ctw = new ContextThemeWrapper(context, R.style.AppTheme);
//                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
//                    alertDialogBuilder.setTitle("Document  ");
//                    alertDialogBuilder.setMessage("Document Downloaded Successfully ");
//                    alertDialogBuilder.setCancelable(false);
//                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            String logMsgPref = "onPostExecute: onClick: ";
//                            Log.i(TAG, logMsgPref + "start");
//                        }
//                    });

//                    alertDialogBuilder.setNegativeButton("Open report", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/CodePlayon/" + downloadFileName);  // -> filename = maven.pdf
//                            Uri path = Uri.fromFile(pdfFile);
//                            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//                            pdfIntent.setDataAndType(path, "application/pdf");
//                            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            try{
//                                context.startActivity(pdfIntent);
//                            }catch(ActivityNotFoundException e){
//                                Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    alertDialogBuilder.show();
//                    Toast.makeText(context, "Document Downloaded Successfully", Toast.LENGTH_SHORT).show();
                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);

                    Log.e(TAG, logMsgPref + "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
                Log.e(TAG, logMsgPref + "Download Failed with Exception - " + e.getLocalizedMessage());

            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String logMsgPref = "doInBackground: ";
            Log.i(TAG, logMsgPref + "start");
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.i(TAG, logMsgPref + "Server returned HTTP " + c.getResponseCode() + " " + c.getResponseMessage());

                }


                //Get File if SD card is present
                if (new CheckForSDCard().isSDCardPresent()) {
                    apkStorage = new File(downloadFilePath);
                } else
                    Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.i(TAG, logMsgPref + downloadFilePath + " Directory Created.");
                }

                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    Log.i(TAG, logMsgPref + "outputFile.exists()");
//                    outputFile.createNewFile();

                    Log.i(TAG, logMsgPref + "создаем " + outputFile.getAbsolutePath());
                    FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                    Log.i(TAG, logMsgPref + "открываем входящий поток");
                    InputStream is = c.getInputStream();//Get InputStream for connection

                    Log.i(TAG, logMsgPref + "начинаем считывать данные");
                    byte[] buffer = new byte[1024];//Set buffer type
                    int len1 = 0;//init length
                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1);//Write new file
                        Log.i(TAG, logMsgPref + "считали и записали очередные 1024 байта");
                    }

                    Log.i(TAG, logMsgPref + "закончили считывать данные");
                    //Close all connection after doing task
                    fos.close();
                    is.close();

                    Log.i(TAG, logMsgPref + "File Created");
                }



            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, logMsgPref + "Download Error Exception " + e.getMessage());
            }

            return null;
        }
    }
}
