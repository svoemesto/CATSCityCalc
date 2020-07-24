package com.svoemestodev.catscitycalc.ssa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.activities.GameActivity;
import com.svoemestodev.catscitycalc.citycalcclasses.Area;
import com.svoemestodev.catscitycalc.citycalcclasses.CCACar;
import com.svoemestodev.catscitycalc.citycalcclasses.CCAGame;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalc;
import com.svoemestodev.catscitycalc.citycalcclasses.CityCalcType;
import com.svoemestodev.catscitycalc.utils.OpenFileDialog;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SSA_Buttons_Activity extends AppCompatActivity {

    public static SSA_Screenshot ssaScreenshot;
    ImageView assab_iv_screenshot;
    Button assab_btn_racs_check;
    Button assab_btn_rules_check;
    ProgressBar assab_pb_progress_racs;
    ProgressBar assab_pb_progress_rules;
    TextView assab_tv_list_racs;
    TextView assab_tv_list_rules;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // индекс нажакой кнопки
        if (id == android.R.id.home) { //если в шапке нажата кнопка "Назад"
            onBackPressed();    // вызываем метод "Назад"
            return true;        // возвращаем Истину
        }
        return super.onOptionsItemSelected(item);   // возвращаем супер-метод
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_buttons);
        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        ssaScreenshot = new SSA_Screenshot(GameActivity.fileGameScreenshot.getAbsolutePath(), GameActivity.calibrateX, GameActivity.calibrateY);
        initializeViews();
        loadDataToViews();

    }

    private void loadDataToViews() {
        assab_iv_screenshot.setImageBitmap(ssaScreenshot.getBitmap());
    }

    private void initializeViews() {
        assab_iv_screenshot = findViewById(R.id.assab_iv_screenshot);
        assab_pb_progress_racs = findViewById(R.id.assab_pb_progress_racs);
        assab_pb_progress_rules = findViewById(R.id.assab_pb_progress_rules);
        assab_tv_list_racs = findViewById(R.id.assab_tv_list_racs);
        assab_tv_list_rules = findViewById(R.id.assab_tv_list_rules);
        assab_btn_racs_check = findViewById(R.id.assab_btn_racs_check);
        assab_btn_rules_check = findViewById(R.id.assab_btn_rules_check);
    }

    public void doOpenScreenshot(View view) {

        OpenFileDialog fileDialog = new OpenFileDialog(this, GameActivity.pathToScreenshotDir)   // диалог выбора скриншота по переданному пути
                .setFolderIcon(ContextCompat.getDrawable(this, R.drawable.ic_folder))            // иконка папки
                .setFileIcon(ContextCompat.getDrawable(this, R.drawable.ic_file))                // иконка файла
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {
                        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
                        ssaScreenshot.setBitmap(bitmap);
                        loadDataToViews();
                    }
                });
        fileDialog.show();

    }

    public void openAreas(View view) {
        Intent intent = new Intent(this, SSA_Areas_Activity.class);
        startActivityForResult(intent, 0);
    }

    public void openColors(View view) {
        Intent intent = new Intent(this, SSA_Colors_Activity.class);
        startActivityForResult(intent, 0);
    }

    public void openConditions(View view) {
        Intent intent = new Intent(this, SSA_Conditions_Activity.class);
        startActivityForResult(intent, 0);
    }

    public void openRACs(View view) {
        Intent intent = new Intent(this, SSA_Rules_Area_Condition_Activity.class);
        startActivityForResult(intent, 0);
    }

    public void openRules(View view) {
        Intent intent = new Intent(this, SSA_Rules_Activity.class);
        startActivityForResult(intent, 0);
    }

    public void checkRACs(View view) {

        GetListSatisfiedRACs thread = new GetListSatisfiedRACs();
        thread.execute();

    }

    public void checkRules(View view) {

        GetListSatisfiedRules thread = new GetListSatisfiedRules();
        thread.execute();

    }


    class GetListSatisfiedRACs extends AsyncTask<Void, Integer, Void> {

        List<SSA_Rule_Area_Condition> sourceList = new ArrayList<>();
        List<SSA_Rule_Area_Condition> resultList = new ArrayList<>();

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            assab_btn_racs_check.setEnabled(false);
            sourceList = SSA_Rules_Area_Condition.getRulesAreaConditionList();
            assab_pb_progress_racs.setMin(0);
            assab_pb_progress_racs.setMax(sourceList.size());
            assab_pb_progress_racs.setProgress(0);
            assab_pb_progress_racs.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            assab_btn_racs_check.setEnabled(true);
            assab_pb_progress_racs.setVisibility(View.INVISIBLE);
            String text = "";
            for (SSA_Rule_Area_Condition item: resultList) {
                text = text + item.getKey() + " (" + item.getName() + ")\n";
            }
            assab_tv_list_racs.setText(text);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            assab_pb_progress_racs.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < sourceList.size(); i++) {
                publishProgress(i);
                SSA_Rule_Area_Condition item = sourceList.get(i);
                if (item.check(ssaScreenshot)) resultList.add(item);
            }
            return null;
        }
    }

    class GetListSatisfiedRules extends AsyncTask<Void, Integer, Void> {

        List<SSA_Rule> sourceList = new ArrayList<>();
        List<SSA_Rule> resultList = new ArrayList<>();

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            assab_btn_rules_check.setEnabled(false);
            sourceList = SSA_Rules.getRulesList();
            assab_pb_progress_rules.setMin(0);
            assab_pb_progress_rules.setMax(sourceList.size());
            assab_pb_progress_rules.setProgress(0);
            assab_pb_progress_rules.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            assab_btn_rules_check.setEnabled(true);
            assab_pb_progress_rules.setVisibility(View.INVISIBLE);
            String text = "";
            for (SSA_Rule item: resultList) {
                text = text + item.getKey() + " (" + item.getName() + ")\n";
            }
            assab_tv_list_rules.setText(text);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            assab_pb_progress_rules.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < sourceList.size(); i++) {
                publishProgress(i);
                SSA_Rule item = sourceList.get(i);
                if (item.check(ssaScreenshot)) resultList.add(item);
            }
            return null;
        }
    }

}