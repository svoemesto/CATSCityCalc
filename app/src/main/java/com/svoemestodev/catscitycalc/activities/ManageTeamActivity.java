package com.svoemestodev.catscitycalc.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.adapters.ListBuildingAdapter;
import com.svoemestodev.catscitycalc.citycalcclasses.Area;
import com.svoemestodev.catscitycalc.citycalcclasses.CCAGame;
import com.svoemestodev.catscitycalc.classes.Building;
import com.svoemestodev.catscitycalc.classes.Car;
import com.svoemestodev.catscitycalc.database.DbCar;
import com.svoemestodev.catscitycalc.database.DbTeam;
import com.svoemestodev.catscitycalc.database.DbTeamUser;
import com.svoemestodev.catscitycalc.database.UserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ManageTeamActivity extends AppCompatActivity {

    AdView mt_ad_banner;
    Button mt_bt_invite_user;
    ImageButton mt_bt_edit_team;
    ListView mt_lv_users;
    TextView mt_tv_name;

    private static final int REQUEST_CODE_TEAM_ACTIVITY = 100;
    private static List<DbTeamUser> listDbTeamUsers = new ArrayList<>();
    public static DbTeam dbTeam;
    private static final String TAG = "ManageTeamActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_team);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();

        displayRecords();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mt_ad_banner.loadAd(adRequest);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TEAM_ACTIVITY) {
            displayRecords();
        }

    }

    private void initializeViews() {
        mt_ad_banner = findViewById(R.id.mt_ad_banner);
        mt_bt_invite_user = findViewById(R.id.mt_bt_invite_user);
        mt_bt_edit_team = findViewById(R.id.mt_bt_edit_team);
        mt_lv_users = findViewById(R.id.mt_lv_users);
        mt_tv_name = findViewById(R.id.mt_tv_name);
        mt_bt_invite_user.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
        mt_bt_edit_team.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
    }

    private void displayRecords() {

        String symbolOpened = "\uD83D\uDD13";
        String symbolClosed = "\uD83D\uDD12";
        String symbolEye = "\uD83D\uDC41";

        String textName = (dbTeam.isTeamIsPublic() ? symbolEye :"") + (dbTeam.isTeamIsOpened() ? symbolOpened : symbolClosed) + dbTeam.getTeamName();

        mt_tv_name.setText(textName);

        CollectionReference crTeamUsers = GameActivity.fbDb.collection("teams").document(dbTeam.getTeamID()).collection("teamUsers");
        crTeamUsers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        listDbTeamUsers = task.getResult().toObjects(DbTeamUser.class);
                        mt_lv_users.setAdapter(new ManageTeamActivity.ListDbTeamUsersAdapter( ManageTeamActivity.this));
                    }
                }
            }

        });

    }

    public void editTeam(View view) {

        Intent intent = new Intent(this, EditTeamActivity.class);
        startActivityForResult(intent, REQUEST_CODE_TEAM_ACTIVITY);

    }

    public void inviteUser(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ManageTeamActivity.this);
        builder.setTitle(R.string.uuid);
        String defaultValue = "";
        final EditText input = new EditText(ManageTeamActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                final String userUID = newValue;
                final String leaderUID = GameActivity.fbUser.getUid();
                final String teamID = dbTeam.getTeamID();

                if (leaderUID.equals(userUID)) {
                    Toast.makeText(ManageTeamActivity.this, getString(R.string.unable_add_self_to_team), Toast.LENGTH_LONG).show();
                } else {
                    boolean isFind = false;
                    for (DbTeamUser teamUser : listDbTeamUsers) {
                        if (teamUser.getUserID().equals(userUID)) {
                            isFind = true;
                            break;
                        }
                    }
                    if (isFind) {
                        Toast.makeText(ManageTeamActivity.this, getString(R.string.user_already_in_team), Toast.LENGTH_LONG).show();
                    } else {
                        final DocumentReference drUser = GameActivity.fbDb.collection("users").document(userUID);
                        drUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String userLeaderUID = (String)documentSnapshot.get("leaderUID");
                                String userTeamID = (String)documentSnapshot.get("teamID");
                                String userNIC = (String)documentSnapshot.get("userNIC");

                                if (userTeamID != null && !userTeamID.equals("")) {
                                    Toast.makeText(ManageTeamActivity.this, getString(R.string.user_already_in_another_team), Toast.LENGTH_LONG).show();
                                } else {
                                    if (!userLeaderUID.equals(leaderUID)) {
                                        Toast.makeText(ManageTeamActivity.this, getString(R.string.not_equals_uid), Toast.LENGTH_LONG).show();
                                    } else {
                                        Map<String, Object> mapUpdateItem = new HashMap<>();
                                        mapUpdateItem.put("teamID", teamID);
                                        drUser.update(mapUpdateItem);

                                        CollectionReference collectionReference = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers");
                                        Map<String, Object> mapNewItem = new HashMap<>();
                                        mapNewItem.put("teamID", teamID);
                                        mapNewItem.put("userID", userUID);
                                        mapNewItem.put("userRole", "meat");
                                        mapNewItem.put("userNIC", userNIC);
                                        mapNewItem.put("timestamp", FieldValue.serverTimestamp());

                                        // Add a new document with a generated ID
                                        collectionReference.add(mapNewItem).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                String newDocumentID = documentReference.getId();
                                                CollectionReference collectionReference = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers");
                                                Map<String, Object> mapUpdateItem = new HashMap<>();
                                                mapUpdateItem.put("teamUserID", newDocumentID);
                                                mapUpdateItem.put("timestamp", FieldValue.serverTimestamp());
                                                collectionReference.document(newDocumentID).update(mapUpdateItem);

                                                displayRecords();

                                            }
                                        })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e(TAG, "Error adding document", e);
                                                    }
                                                });
                                    }
                                }


                            }
                        });

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


    private class ListDbTeamUsersAdapter extends ArrayAdapter<DbTeamUser> {

        public ListDbTeamUsersAdapter(@NonNull Context context) {
            super(context, R.layout.layout_manage_team_users, listDbTeamUsers);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final DbTeamUser dbTeamUser = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_manage_team_users, null);
            }

            ImageButton mtu_bt_delete = convertView.findViewById(R.id.mtu_bt_delete);
            ImageButton mtu_bt_edit = convertView.findViewById(R.id.mtu_bt_edit);
            TextView mtu_tv_nic_value = convertView.findViewById(R.id.mtu_tv_nic_value);


            mtu_bt_delete.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
            mtu_bt_edit.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));

            String userUID = dbTeamUser.getUserID() == null ? "" : dbTeamUser.getUserID();
            String userNIC = dbTeamUser.getUserNIC() == null ? "N/A" : dbTeamUser.getUserNIC();
            String userRoleString = dbTeamUser.getUserRole() == null ? "N/A" : dbTeamUser.getUserRole();

            mtu_tv_nic_value.setText(userNIC + " [" + userRoleString + "]");

            mtu_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dbTeamUser.getUserID().equals(GameActivity.fbUser.getUid())) {
                        Toast.makeText(ManageTeamActivity.this, getString(R.string.unable_delete_self), Toast.LENGTH_LONG).show();
                    } else {
                        final String userID = dbTeamUser.getUserID();
                        final String teamID = dbTeam.getTeamID();

                        Query query = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").whereEqualTo("userID", userID);
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DbTeamUser> listTeamUsers = task.getResult().toObjects(DbTeamUser.class);
                                if (listTeamUsers.size() > 0) {
                                    DbTeamUser dbTeamUser = listTeamUsers.get(0);
                                    GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").document(dbTeamUser.getTeamUserID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            displayRecords();
                                        }
                                    });
                                    Map<String, Object> mapUpdateItem = new HashMap<>();
                                    mapUpdateItem.put("teamID", null);
                                    mapUpdateItem.put("leaderUID", null);
                                    GameActivity.fbDb.collection("users").document(userID).update(mapUpdateItem);

                                }
                            }
                        });
                    }

                }
            });

            mtu_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeamUserActivity.mainDbTeamUser = dbTeamUser;
                    Intent intent = new Intent(ManageTeamActivity.this, TeamUserActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_TEAM_ACTIVITY);

                }
            });

            return convertView;

        }
    }


}