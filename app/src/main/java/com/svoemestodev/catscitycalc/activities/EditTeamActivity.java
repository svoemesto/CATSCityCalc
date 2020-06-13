package com.svoemestodev.catscitycalc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.svoemestodev.catscitycalc.R;

import java.util.HashMap;
import java.util.Map;

public class EditTeamActivity extends AppCompatActivity {

    AdView te_ad_banner;
    TextView te_tv_teamname_value;
    Switch te_sw_team_is_public;
    Switch te_sw_team_is_opened;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();
        loadDataToViews();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        te_ad_banner.loadAd(adRequest);

        te_sw_team_is_public.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TeamActivity.dbTeam.setTeamIsPublic(isChecked);
                Map<String, Object> updateTeam = new HashMap<>();
                updateTeam.put("teamIsPublic", isChecked);
                GameActivity.fbDb.collection("teams").document(TeamActivity.dbTeam.getTeamID()).update(updateTeam);
            }
        });

        te_sw_team_is_opened.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TeamActivity.dbTeam.setTeamIsOpened(isChecked);
                Map<String, Object> updateTeam = new HashMap<>();
                updateTeam.put("teamIsOpened", isChecked);
                GameActivity.fbDb.collection("teams").document(TeamActivity.dbTeam.getTeamID()).update(updateTeam);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // индекс нажатой кнопки
        if (id == android.R.id.home) { //если в шапке нажата кнопка "Назад"
            onBackPressed();    // вызываем метод "Назад"
            return true;        // возвращаем Истину
        }
        return super.onOptionsItemSelected(item);   // возвращаем супер-метод
    }

    private void initializeViews() {
        te_ad_banner = findViewById(R.id.te_ad_banner);
        te_tv_teamname_value = findViewById(R.id.te_tv_teamname_value);
        te_sw_team_is_public = findViewById(R.id.te_sw_team_is_public);
        te_sw_team_is_opened = findViewById(R.id.te_sw_team_is_opened);
    }

    private void loadDataToViews() {

        te_tv_teamname_value.setText(TeamActivity.dbTeam.getTeamName());
        te_sw_team_is_public.setChecked(TeamActivity.dbTeam.isTeamIsPublic());
        te_sw_team_is_opened.setChecked(TeamActivity.dbTeam.isTeamIsOpened());

    }

    public void setTeamName(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(EditTeamActivity.this);
        builder.setTitle(R.string.team);
        String defaultValue = TeamActivity.dbTeam.getTeamName();
        final EditText input = new EditText(EditTeamActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                TeamActivity.dbTeam.setTeamName(newValue);
                Map<String, Object> updateTeam = new HashMap<>();
                updateTeam.put("teamName", newValue);
                GameActivity.fbDb.collection("teams").document(TeamActivity.dbTeam.getTeamID()).update(updateTeam);
                te_tv_teamname_value.setText(newValue);
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
}