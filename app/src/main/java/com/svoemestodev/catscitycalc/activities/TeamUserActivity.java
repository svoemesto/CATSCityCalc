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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.database.DbTeamUser;
import com.svoemestodev.catscitycalc.database.UserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamUserActivity extends AppCompatActivity {

    AdView tu_ad_banner;
    ImageButton tu_ib_set_usernic;
    TextView tu_tv_usernic_value;
    Spinner tu_sp_role;

    public static DbTeamUser mainDbTeamUser;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // индекс нажакой кнопки
        if (id == android.R.id.home) { //если в шапке нажата кнопка "Назад"
            onBackPressed();    // вызываем метод "Назад"
            return true;        // возвращаем Истину
        }
        return super.onOptionsItemSelected(item);   // возвращаем супер-метод
    }
    
    private static final String TAG = "UserActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team_user);

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
        tu_ad_banner.loadAd(adRequest);

        String userRoleString = mainDbTeamUser.getUserRole() == null ? "N/A" : mainDbTeamUser.getUserRole();
        UserRole userRole = userRoleString.equals("leader") ? UserRole.LEADER : userRoleString.equals("captain") ? UserRole.CAPTAIN : UserRole.MEAT;

        List<UserRole> listRoles = new ArrayList<UserRole>();
        listRoles.add(UserRole.LEADER);
        listRoles.add(UserRole.CAPTAIN);
        listRoles.add(UserRole.MEAT);
        ArrayAdapter<UserRole> dataAdapterRoles = new ArrayAdapter<UserRole>(TeamUserActivity.this, android.R.layout.simple_spinner_item, listRoles);
        dataAdapterRoles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tu_sp_role.setAdapter(dataAdapterRoles);
        tu_sp_role.setSelection(dataAdapterRoles.getPosition(userRole));

        tu_sp_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserRole selectedRole = (UserRole) parent.getItemAtPosition(position);
                String selectedRoleString = selectedRole.equals(UserRole.LEADER) ? "leader" : selectedRole.equals(UserRole.CAPTAIN) ? "captain" : "meat";
                if  (!selectedRoleString.equals(mainDbTeamUser.getUserRole())) {
                    if (mainDbTeamUser.getUserID().equals(GameActivity.fbUser.getUid())) {
                        Toast.makeText(TeamUserActivity.this, getString(R.string.unable_chage_self_role), Toast.LENGTH_LONG).show();
                    } else {
                        final String userRole = selectedRole.equals(UserRole.LEADER) ? "leader" : selectedRole.equals(UserRole.CAPTAIN) ? "captain" : "meat";
                        final String userID = mainDbTeamUser.getUserID();
                        final String teamID = mainDbTeamUser.getTeamID();

                        Query query = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").whereEqualTo("userID", userID);
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DbTeamUser> listTeamUsers = task.getResult().toObjects(DbTeamUser.class);
                                if (listTeamUsers.size() > 0) {
                                    DbTeamUser dbTeamUser = listTeamUsers.get(0);
                                    Map<String, Object> mapUpdateItem = new HashMap<>();
                                    mapUpdateItem.put("userRole", userRole);
                                    GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").document(dbTeamUser.getTeamUserID()).update(mapUpdateItem);
                                }
                            }
                        });
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initializeViews() {
        tu_ad_banner = findViewById(R.id.tu_ad_banner);
        tu_ib_set_usernic = findViewById(R.id.tu_ib_set_usernic);
        tu_tv_usernic_value = findViewById(R.id.tu_tv_usernic_value);
        tu_sp_role = findViewById(R.id.tu_sp_role);
    }

    private void loadDataToViews() {

        tu_tv_usernic_value.setText(mainDbTeamUser.getUserNIC());

    }
    
    public void setUserNIC(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(TeamUserActivity.this);
        builder.setTitle(R.string.usernic);
        String defaultValue = mainDbTeamUser.getUserNIC();
        final EditText input = new EditText(TeamUserActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String userNIC = input.getText().toString();
                final String userID = mainDbTeamUser.getUserID();
                final String teamID = mainDbTeamUser.getTeamID();
                tu_tv_usernic_value.setText(userNIC);
                Query query = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").whereEqualTo("userID", userID);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<DbTeamUser> listTeamUsers = task.getResult().toObjects(DbTeamUser.class);
                        if (listTeamUsers.size() > 0) {
                            DbTeamUser dbTeamUser = listTeamUsers.get(0);
                            Map<String, Object> mapUpdateItem = new HashMap<>();
                            mapUpdateItem.put("userNIC", userNIC);
                            GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").document(dbTeamUser.getTeamUserID()).update(mapUpdateItem);
                        }
                    }
                });

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