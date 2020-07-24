package com.svoemestodev.catscitycalc.ssa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SSA_Rule_Area_Condition_Activity extends AppCompatActivity {

    TextView assarace_tv_key_value;
    TextView assarace_tv_name_value;
    TextView assarace_tv_area_value;
    TextView assarace_tv_condition_value;

    public static SSA_Rule_Area_Condition ssaRAC;
    public static SSA_Screenshot ssaScreenshot;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // индекс нажатой кнопки
        if (id == android.R.id.home) { //если в шапке нажата кнопка "Назад"
            onBackPressed();    // вызываем метод "Назад"
            return true;        // возвращаем Истину
        }
        return super.onOptionsItemSelected(item);   // возвращаем супер-метод
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        SSA_Rules_Area_Condition.putRuleAreaCondition(ssaRAC);


    }

    private void initializeViews() {

        assarace_tv_key_value = findViewById(R.id.assarace_tv_key_value);
        assarace_tv_name_value = findViewById(R.id.assarace_tv_name_value);
        assarace_tv_area_value = findViewById(R.id.assarace_tv_area_value);
        assarace_tv_condition_value = findViewById(R.id.assarace_tv_condition_value);

    }

    private void loadDataToViews() {

        String racKey = ssaRAC.getKey() == null ? "N/A" : ssaRAC.getKey();
        String racName = ssaRAC.getName() == null ? "N/A" : ssaRAC.getName();
        String areaKey = ssaRAC.getSsaArea().getKey() == null ? "N/A" : ssaRAC.getSsaArea().getKey();
        String condKey = ssaRAC.getSsaCondition().getKey() == null ? "N/A" : ssaRAC.getSsaCondition().getKey();
        assarace_tv_key_value.setText(racKey);
        assarace_tv_name_value.setText(racName);
        assarace_tv_area_value.setText(areaKey);
        assarace_tv_condition_value.setText(condKey);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_rac);
        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }
        ssaScreenshot = SSA_Buttons_Activity.ssaScreenshot;
        initializeViews();
        loadDataToViews();

    }

    public void setKey(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Rule_Area_Condition_Activity.this);
        builder.setTitle("RAC KEY");
        String defaultValue = ssaRAC.getKey();
        final EditText input = new EditText(SSA_Rule_Area_Condition_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (!newValue.equals(defaultValue)) {
                    SSA_Rule_Area_Condition tmp = SSA_Rules_Area_Condition.getRuleAreaCondition(newValue);
                    if (tmp == null) {
                        SSA_Rules_Area_Condition.delRuleAreaCondition(ssaRAC);
                        ssaRAC.setKey(newValue);
                        SSA_Rules_Area_Condition.putRuleAreaCondition(ssaRAC);
                        loadDataToViews();
                    } else {
                        Toast.makeText(SSA_Rule_Area_Condition_Activity.this,"RAC с таким ключом уже существует.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void setName(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Rule_Area_Condition_Activity.this);
        builder.setTitle("Condition name");
        String defaultValue = ssaRAC.getName();
        final EditText input = new EditText(SSA_Rule_Area_Condition_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (!newValue.equals(defaultValue)) {
                    SSA_Rule_Area_Condition tmp = SSA_Rules_Area_Condition.getRuleAreaCondition(newValue);
                    if (tmp == null) {
                        ssaRAC.setName(newValue);
                        loadDataToViews();
                    } else {
                        Toast.makeText(SSA_Rule_Area_Condition_Activity.this,"RAC с таким именем уже существует.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void setArea(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Areas");

        final SSA_AreasListAdapter arrayAdapter = new SSA_AreasListAdapter(SSA_Rule_Area_Condition_Activity.this, SSA_Areas.getAreasList());
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SSA_Area ssaArea = arrayAdapter.getItem(which);
                if (!ssaArea.getKey().equals(ssaRAC.getSsaArea().getKey())) {
                    ssaRAC.setSsaArea(ssaArea);
                    loadDataToViews();
                }
            }
        });
        builder.show();
        
    }

    public void setCondition(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Conditions");

        final SSA_ConditionsListAdapter arrayAdapter = new SSA_ConditionsListAdapter(SSA_Rule_Area_Condition_Activity.this, SSA_Conditions.getConditionsList());
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SSA_Condition ssaCondition = arrayAdapter.getItem(which);
                if (!ssaCondition.getKey().equals(ssaRAC.getSsaCondition().getKey())) {
                    ssaRAC.setSsaCondition(ssaCondition);
                    loadDataToViews();
                }
            }
        });
        builder.show();
        
    }
}