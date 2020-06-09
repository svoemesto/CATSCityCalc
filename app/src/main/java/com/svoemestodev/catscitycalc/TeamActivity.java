package com.svoemestodev.catscitycalc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamActivity extends AppCompatActivity {

    AdView ta_ad_banner;
    Button ta_bt_invite_user;
    ListView ta_lv_users;
    TextView ta_tv_name;

    private static final int REQUEST_CODE_TEAM_ACTIVITY = 100;
    private static List<DbTeamUser> listDbTeamUsers = new ArrayList<>();
    public static DbTeam dbTeam;
    private static final String TAG = "TeamActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

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
        ta_ad_banner.loadAd(adRequest);

    }

    private void initializeViews() {
        ta_ad_banner = findViewById(R.id.ta_ad_banner);
        ta_bt_invite_user = findViewById(R.id.ta_bt_invite_user);
        ta_lv_users = findViewById(R.id.ta_lv_users);
        ta_tv_name = findViewById(R.id.ta_tv_name);
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

    private void displayRecords() {

        ta_tv_name.setText(dbTeam.getTeamName());

        CollectionReference crTeamUsers = GameActivity.fbDb.collection("teams").document(dbTeam.getTeamID()).collection("teamUsers");
        crTeamUsers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        listDbTeamUsers = task.getResult().toObjects(DbTeamUser.class);
                        ta_lv_users.setAdapter(new ListDbTeamUsersAdapter( TeamActivity.this));
                    }
                }
            }

        });

    }

    public void inviteUser(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(TeamActivity.this);
        builder.setTitle(R.string.uuid);
        String defaultValue = "";
        final EditText input = new EditText(TeamActivity.this);
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
                    Toast.makeText(TeamActivity.this, "Нельзя добавить в банду самого себя.", Toast.LENGTH_LONG).show();
                } else {
                    boolean isFind = false;
                    for (DbTeamUser teamUser : listDbTeamUsers) {
                        if (teamUser.getUserID().equals(userUID)) {
                            isFind = true;
                            break;
                        }
                    }
                    if (isFind) {
                        Toast.makeText(TeamActivity.this, "Этот пользователь уже в банде.", Toast.LENGTH_LONG).show();
                    } else {
                        final DocumentReference drUser = GameActivity.fbDb.collection("users").document(userUID);
                        drUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String userLeaderUID = (String)documentSnapshot.get("leaderUID");
                                String userTeamID = (String)documentSnapshot.get("teamID");
                                String userNIC = (String)documentSnapshot.get("userNIC");

                                if (userTeamID != null && !userTeamID.equals("")) {
                                    Toast.makeText(TeamActivity.this, "Этот пользователь состоит в другой банде.", Toast.LENGTH_LONG).show();
                                } else {
                                    if (!userLeaderUID.equals(leaderUID)) {
                                        Toast.makeText(TeamActivity.this, "Этот пользователь не установил ваш UID как UID лидера.", Toast.LENGTH_LONG).show();
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
            super(context, R.layout.layout_teamuser, listDbTeamUsers);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final DbTeamUser dbTeamUser = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_teamuser, null);
            }

            ImageButton ltu_bt_delete = convertView.findViewById(R.id.ltu_bt_delete);
            ImageButton ltu_bt_edit = convertView.findViewById(R.id.ltu_bt_edit);
            ImageButton ltu_bt_set_leader = convertView.findViewById(R.id.ltu_bt_set_leader);
            ImageButton ltu_bt_set_captain = convertView.findViewById(R.id.ltu_bt_set_captain);
            ImageButton ltu_bt_set_meat = convertView.findViewById(R.id.ltu_bt_set_meat);
            TextView ltu_tv_nic_value = convertView.findViewById(R.id.ltu_tv_nic_value);
            TextView ltu_tv_role_value = convertView.findViewById(R.id.ltu_tv_role_value);

            String userNIC = dbTeamUser.getUserNIC() == null ? "N/A" : dbTeamUser.getUserNIC();
            String userRole = dbTeamUser.getUserRole() == null ? "N/A" : dbTeamUser.getUserRole();

            ltu_tv_nic_value.setText(userNIC);
            ltu_tv_role_value.setText(userRole);

            ltu_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dbTeamUser.getUserID().equals(GameActivity.fbUser.getUid())) {
                        Toast.makeText(TeamActivity.this, "Нельзя удалить из банды самого себя.", Toast.LENGTH_LONG).show();
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

            ltu_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(TeamActivity.this);
                    builder.setTitle(R.string.usernic);
                    String defaultValue = dbTeamUser.getUserNIC();
                    final EditText input = new EditText(TeamActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setText(defaultValue);
                    builder.setView(input);

                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String userNIC = input.getText().toString();
                            final String userID = dbTeamUser.getUserID();
                            final String teamID = dbTeam.getTeamID();

                            Query query = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").whereEqualTo("userID", userID);
                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    List<DbTeamUser> listTeamUsers = task.getResult().toObjects(DbTeamUser.class);
                                    if (listTeamUsers.size() > 0) {
                                        DbTeamUser dbTeamUser = listTeamUsers.get(0);
                                        Map<String, Object> mapUpdateItem = new HashMap<>();
                                        mapUpdateItem.put("userNIC", userNIC);
                                        GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").document(dbTeamUser.getTeamUserID()).update(mapUpdateItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                displayRecords();
                                            }
                                        });
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
            });

            ltu_bt_set_leader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dbTeamUser.getUserID().equals(GameActivity.fbUser.getUid())) {
                        Toast.makeText(TeamActivity.this, "Нельзя изменить роль самому себе.", Toast.LENGTH_LONG).show();
                    } else {
                        final String userRole = "leader";
                        final String userID = dbTeamUser.getUserID();
                        final String teamID = dbTeam.getTeamID();

                        Query query = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").whereEqualTo("userID", userID);
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DbTeamUser> listTeamUsers = task.getResult().toObjects(DbTeamUser.class);
                                if (listTeamUsers.size() > 0) {
                                    DbTeamUser dbTeamUser = listTeamUsers.get(0);
                                    Map<String, Object> mapUpdateItem = new HashMap<>();
                                    mapUpdateItem.put("userRole", userRole);
                                    GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").document(dbTeamUser.getTeamUserID()).update(mapUpdateItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            displayRecords();
                                        }
                                    });
                                }
                            }
                        });
                    }



                }
            });

            ltu_bt_set_captain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dbTeamUser.getUserID().equals(GameActivity.fbUser.getUid())) {
                        Toast.makeText(TeamActivity.this, "Нельзя изменить роль самому себе.", Toast.LENGTH_LONG).show();
                    } else {
                        final String userRole = "captain";
                        final String userID = dbTeamUser.getUserID();
                        final String teamID = dbTeam.getTeamID();

                        Query query = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").whereEqualTo("userID", userID);
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DbTeamUser> listTeamUsers = task.getResult().toObjects(DbTeamUser.class);
                                if (listTeamUsers.size() > 0) {
                                    DbTeamUser dbTeamUser = listTeamUsers.get(0);
                                    Map<String, Object> mapUpdateItem = new HashMap<>();
                                    mapUpdateItem.put("userRole", userRole);
                                    GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").document(dbTeamUser.getTeamUserID()).update(mapUpdateItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            displayRecords();
                                        }
                                    });
                                }
                            }
                        });
                    }

                }
            });

            ltu_bt_set_meat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dbTeamUser.getUserID().equals(GameActivity.fbUser.getUid())) {
                        Toast.makeText(TeamActivity.this, "Нельзя изменить роль самому себе.", Toast.LENGTH_LONG).show();
                    } else {
                        final String userRole = "meat";
                        final String userID = dbTeamUser.getUserID();
                        final String teamID = dbTeam.getTeamID();

                        Query query = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").whereEqualTo("userID", userID);
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DbTeamUser> listTeamUsers = task.getResult().toObjects(DbTeamUser.class);
                                if (listTeamUsers.size() > 0) {
                                    DbTeamUser dbTeamUser = listTeamUsers.get(0);
                                    Map<String, Object> mapUpdateItem = new HashMap<>();
                                    mapUpdateItem.put("userRole", userRole);
                                    GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").document(dbTeamUser.getTeamUserID()).update(mapUpdateItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            displayRecords();
                                        }
                                    });
                                }
                            }
                        });
                    }

                }
            });


            return convertView;

        }
    }

}