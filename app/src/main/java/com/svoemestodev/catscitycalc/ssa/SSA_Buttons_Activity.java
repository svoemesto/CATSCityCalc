package com.svoemestodev.catscitycalc.ssa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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

public class SSA_Buttons_Activity extends AppCompatActivity {

    public static SSA_Screenshot ssaScreenshot;
    ImageView assab_iv_screenshot;

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
    }
}