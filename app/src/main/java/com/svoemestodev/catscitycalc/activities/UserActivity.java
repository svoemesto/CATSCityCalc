package com.svoemestodev.catscitycalc.activities;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.firebase.ui.auth.AuthUI;
import com.svoemestodev.catscitycalc.database.DbTeam;
import com.svoemestodev.catscitycalc.database.DbTeamUser;
import com.svoemestodev.catscitycalc.database.DbUser;
import com.svoemestodev.catscitycalc.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    public static DbUser dbUser = new DbUser();

    Menu userMenu;
    MenuItem menuItemLogout;
    MenuItem menuItemSendEmailVerification;
    MenuItem menuItemCreateTeam;
    MenuItem menuItemLeaveTeam;
    MenuItem menuItemManageTeam;


    AdView ua_ad_banner;
    ImageButton ua_ib_set_usernic;
    ImageButton ua_ib_copy_uuid;
    ImageButton ua_ib_set_leaderuid;
    TextView ua_tv_leaderuid_value;
    TextView ua_tv_usernic_value;
    TextView ua_tv_username_value;
    TextView ua_tv_useremail_value;
    TextView ua_tv_uuid_value;
    TextView ua_tv_team_value;
    TextView ua_tv_role_value;

    private static boolean isHaveTeam;
    private static boolean isTeamLeader;
    private static boolean isVerified;

    private static final int SIGN_IN_REQUEST_CODE = 1;
    private static final String TAG = "UserActivity";

    private static DbTeam dbTeam;
    private static DbTeamUser dbTeamUser;

    private static DocumentReference userDocument;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);  // создаем меню
        userMenu = menu;
        menuItemLogout = userMenu.findItem(R.id.menu_user_logout);
        menuItemSendEmailVerification = userMenu.findItem(R.id.menu_user_send_email_verification);
        menuItemCreateTeam = userMenu.findItem(R.id.menu_user_create_team);
        menuItemLeaveTeam = userMenu.findItem(R.id.menu_user_leave_team);
        menuItemManageTeam = userMenu.findItem(R.id.menu_user_manage_team);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // айди элемента меню
        switch(id){
            case android.R.id.home:
                onBackPressed();    // вызываем метод "Назад"
                return true;        // возвращаем Истину
            case R.id.menu_user_logout :  // "Настройки"
                doMenuLogout();
                return true;
            case R.id.menu_user_send_email_verification :    // "Открыть скриншот"
                doMenuSendEmailVerification();
                return true;
            case R.id.menu_user_create_team :    // "Открыть скриншот"
                doMenuCreateTeam();
                return true;
            case R.id.menu_user_leave_team :    // "Открыть скриншот"
                doMenuLeaveTeam();
                return true;
            case R.id.menu_user_manage_team :    // "Открыть скриншот"
                doMenuManageTeam();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void doMenuManageTeam() {

        TeamActivity.dbTeam = dbTeam;
        Intent intent = new Intent(this, TeamActivity.class);
        startActivityForResult(intent, 0);

    }

    private void doMenuLeaveTeam() {

        if (isHaveTeam) {
            if (isTeamLeader) {
                // если ты в банде лидер - надо проверить, есть ли еще в банде еще хоть кто-то. Если нет - удалить банду. Если да - проверить, есть ли среди них хоть один лидер. Если нет - выходить из банды нельзя.

                CollectionReference crTeamUsers = GameActivity.fbDb.collection("teams").document(dbTeam.getTeamID()).collection("teamUsers");
                crTeamUsers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                int countUsersInTeam = 0;
                                int countLeadersInTeam = 0;
                                List<DbTeamUser> listTeamUsers = task.getResult().toObjects(DbTeamUser.class);
                                if (listTeamUsers.size() > 0) {
                                    for (DbTeamUser teamUser: listTeamUsers) {
                                        countUsersInTeam++;
                                        if (teamUser.getUserRole().equals("leader")) {
                                            countLeadersInTeam++;
                                        }
                                    }
                                }
                                if (countUsersInTeam == 1) {
                                    dbUser.setTeamID(null);
                                    leaveTeam(dbTeam.getTeamID(), dbUser.getUserID(), dbTeamUser.getTeamUserID());
                                    deleteTeam(dbTeam.getTeamID());
                                } else if (countLeadersInTeam > 1) {
                                    dbUser.setTeamID(null);
                                    leaveTeam(dbTeam.getTeamID(), dbUser.getUserID(), dbTeamUser.getTeamUserID());
                                } else {
                                    Toast.makeText(UserActivity.this, "В банде есть еще пользователи, и ни один из них не является лидером. Прежде чем покинуть такую банду нужно или назначить кого-то лидером, или удалить всех пользователей из банды.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }

                });


            } else {
                // если ты в банде не лидер - смело можно покидать банду
                dbUser.setTeamID(null);
                leaveTeam(dbTeam.getTeamID(), dbUser.getUserID(), dbTeamUser.getTeamUserID());
            }
        }

    }

    private void deleteTeam(String teamID) {

        DocumentReference drTeam = GameActivity.fbDb.collection("teams").document(teamID);

        drTeam.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loadDataToViews();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadDataToViews();
            }
        });

    }

    private void leaveTeam(String teamID, String userID, String teamUserID) {

        DocumentReference drUser = GameActivity.fbDb.collection("users").document(userID);

        drUser.update("teamID", null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loadDataToViews();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadDataToViews();
            }
        });

        DocumentReference drTeamUser = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").document(teamUserID);
        drTeamUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loadDataToViews();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadDataToViews();
            }
        });

    }


    private void doMenuCreateTeam() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        builder.setTitle(R.string.team);
        String defaultValue = "New team";
        final EditText input = new EditText(UserActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();

                CollectionReference collectionReference = GameActivity.fbDb.collection("teams");

                Map<String, Object> mapNewItem = new HashMap<>();
                mapNewItem.put("teamID", null);
                mapNewItem.put("teamName", newValue);
                mapNewItem.put("timestamp", FieldValue.serverTimestamp());

                // Add a new document with a generated ID
                collectionReference.add(mapNewItem).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        final String teamID = documentReference.getId();
                        CollectionReference collectionReference = GameActivity.fbDb.collection("teams");
                        Map<String, Object> mapUpdateItem = new HashMap<>();
                        mapUpdateItem.put("teamID", teamID);
                        mapUpdateItem.put("timestamp", FieldValue.serverTimestamp());
                        collectionReference.document(teamID).update(mapUpdateItem);

                        CollectionReference users = GameActivity.fbDb.collection("users");
                        Map<String, Object> updateUser = new HashMap<>();
                        updateUser.put("teamID", teamID);
                        users.document(dbUser.getUserID()).update(updateUser);
                        dbUser.setTeamID(teamID);

                        collectionReference = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers");
                        Map<String, Object> mapNewItem = new HashMap<>();
                        mapNewItem.put("teamID", teamID);
                        mapNewItem.put("userID", dbUser.getUserID());
                        mapNewItem.put("userRole", "leader");
                        mapNewItem.put("userNIC", dbUser.getUserNIC());
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

                                loadDataToViews();

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "Error adding document", e);
                                    }
                                });

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Error adding document", e);
                            }
                        });

                loadDataToViews();
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

    private void doMenuSendEmailVerification() {
        GameActivity.fbUser.sendEmailVerification().addOnCompleteListener(UserActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(UserActivity.this, "Verification email sent to " + GameActivity.fbUser.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(UserActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void doMenuLogout() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UserActivity.this, R.string.your_signed_out, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        if(GameActivity.fbUser == null) {
            // Start sign in/sign up activity
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        } else {


            CollectionReference collectionReference = GameActivity.fbDb.collection("users");
            Query query = collectionReference.whereEqualTo("userUID", GameActivity.fbUser.getUid());
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            dbUser.setUserID(document.getId());
                        }
                        if (task.getResult().isEmpty()) {
                            // результат запроса пустой, такого юзера еще нет - создаем его
                            dbUser.setUserUID(GameActivity.fbUser.getUid());
                            dbUser.setUserName(GameActivity.fbUser.getDisplayName());
                            dbUser.setUserEmail(GameActivity.fbUser.getEmail());
                            dbUser.setUserNIC(GameActivity.fbUser.getDisplayName());
                            dbUser.setTeamID(null);

                            Map<String, Object> mapNewItem = new HashMap<>();
                            mapNewItem.put("userID", dbUser.getUserID());
                            mapNewItem.put("userUID", dbUser.getUserUID());
                            mapNewItem.put("userName", dbUser.getUserName());
                            mapNewItem.put("userEmail", dbUser.getUserEmail());
                            mapNewItem.put("userNIC", dbUser.getUserNIC());
                            mapNewItem.put("teamID", dbUser.getTeamID());
                            mapNewItem.put("timestamp", FieldValue.serverTimestamp());

                            GameActivity.fbDb.collection("users").document(dbUser.getUserUID()).set(mapNewItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String newDocumentID = dbUser.getUserUID();
                                    dbUser.setUserID(newDocumentID);
                                    CollectionReference collectionReference = GameActivity.fbDb.collection("users");
                                    Map<String, Object> mapUpdateItem = new HashMap<>();
                                    mapUpdateItem.put("userID", newDocumentID);
                                    mapUpdateItem.put("timestamp", FieldValue.serverTimestamp());
                                    collectionReference.document(newDocumentID).update(mapUpdateItem);

                                    loadDataToViews();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, "Error adding document", e);
                                }
                            });
                            loadDataToViews();

                        } else {
                            // результат запроса не пустой, такой юзер есть - считываем его
                            List<DbUser> listUsers = task.getResult().toObjects(DbUser.class);
                            if (listUsers.size() >0) {
                                dbUser = listUsers.get(0);
                                loadDataToViews();
                            }
                        }

                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());

                    }
                }
            });


            initializeViews();
            loadDataToViews();


            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            AdRequest adRequest = new AdRequest.Builder().build();
            ua_ad_banner.loadAd(adRequest);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {

                CollectionReference collectionReference = GameActivity.fbDb.collection("users");
                Query query = collectionReference.whereEqualTo("userUID", GameActivity.fbUser.getUid());
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                dbUser.setUserID(document.getId());
                            }
                            if (task.getResult().isEmpty()) {

                                // результат запроса пустой, такого юзера еще нет - создаем его
                                dbUser.setUserUID(GameActivity.fbUser.getUid());
                                dbUser.setUserName(GameActivity.fbUser.getDisplayName());
                                dbUser.setUserEmail(GameActivity.fbUser.getEmail());
                                dbUser.setUserNIC(GameActivity.fbUser.getDisplayName());
                                dbUser.setTeamID(null);

                                Map<String, Object> mapNewItem = new HashMap<>();
                                mapNewItem.put("userID", dbUser.getUserID());
                                mapNewItem.put("userUID", dbUser.getUserUID());
                                mapNewItem.put("userName", dbUser.getUserName());
                                mapNewItem.put("userEmail", dbUser.getUserEmail());
                                mapNewItem.put("userNIC", dbUser.getUserNIC());
                                mapNewItem.put("teamID", dbUser.getTeamID());
                                mapNewItem.put("timestamp", FieldValue.serverTimestamp());

                                GameActivity.fbDb.collection("users").document(dbUser.getUserUID()).set(mapNewItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        String newDocumentID = dbUser.getUserUID();
                                        dbUser.setUserID(newDocumentID);
                                        CollectionReference collectionReference = GameActivity.fbDb.collection("users");
                                        Map<String, Object> mapUpdateItem = new HashMap<>();
                                        mapUpdateItem.put("userID", newDocumentID);
                                        mapUpdateItem.put("timestamp", FieldValue.serverTimestamp());
                                        collectionReference.document(newDocumentID).update(mapUpdateItem);

                                        GameActivity.fbUser.sendEmailVerification().addOnCompleteListener(UserActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    Toast.makeText(UserActivity.this, "Verification email sent to " + GameActivity.fbUser.getEmail(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Log.e(TAG, "sendEmailVerification", task.getException());
                                                    Toast.makeText(UserActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                        loadDataToViews();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "Error adding document", e);
                                    }
                                });
                                loadDataToViews();

                            } else {
                                // результат запроса не пустой, такой юзер есть - считываем его
                                List<DbUser> listUsers = task.getResult().toObjects(DbUser.class);
                                if (listUsers.size() >0) {
                                    dbUser = listUsers.get(0);
                                    loadDataToViews();
                                }
                            }

                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());

                        }
                    }
                });


                initializeViews();
                loadDataToViews();


                MobileAds.initialize(this, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                    }
                });
                AdRequest adRequest = new AdRequest.Builder().build();
                ua_ad_banner.loadAd(adRequest);

            } else {
                Toast.makeText(UserActivity.this, R.string.could_not_sing_in, Toast.LENGTH_LONG).show();
                // Close the app
                finish();
            }
        }

    }


    private void loadDataToViews() {

        ua_tv_usernic_value.setText(dbUser.getUserNIC());
        ua_tv_username_value.setText(dbUser.getUserName());
        ua_tv_useremail_value.setText(dbUser.getUserEmail());
        ua_tv_uuid_value.setText(dbUser.getUserUID());
        String leaderUID = dbUser.getLeaderUID() == null ? "" : dbUser.getLeaderUID();
        ua_tv_leaderuid_value.setText(leaderUID);

        if (dbUser.getTeamID() != null) {
            isHaveTeam = true;

            CollectionReference collectionReference = GameActivity.fbDb.collection("teams");
            try {
                final DocumentReference documentReference = collectionReference.document(dbUser.getTeamID());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        dbTeam = documentSnapshot.toObject(DbTeam.class);
                        ua_tv_team_value.setText(dbTeam.getTeamName());

                        CollectionReference subCollectionReference = documentReference.collection("teamUsers");
                        Query query = subCollectionReference.whereEqualTo("userID", dbUser.getUserID());

                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        dbTeamUser = document.toObject(DbTeamUser.class);
                                        dbTeamUser.setTeamUserID(document.getId());
                                        ua_tv_role_value.setText(dbTeamUser.getUserRole());
                                        if (dbTeamUser.getUserRole().equals("leader")) {
                                            isTeamLeader = true;
                                        } else {
                                            isTeamLeader = false;
                                        }
                                        break;
                                    }
                                    loadDataToViews();
                                } else {
                                    Log.e(TAG, "Error getting documents: ", task.getException());

                                }
                            }
                        });

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            isHaveTeam = false;
        }

        isVerified = GameActivity.fbUser.isEmailVerified();


        if (!isVerified) {
            Toast.makeText(UserActivity.this, "Емейл не подтвержден!", Toast.LENGTH_LONG).show();
            ua_ib_set_usernic.setEnabled(false);
            ua_ib_copy_uuid.setEnabled(false);
            ua_ib_set_leaderuid.setEnabled(false);

            if (userMenu != null) menuItemLogout.setVisible(true);
            if (userMenu != null) menuItemSendEmailVerification.setVisible(true);
            if (userMenu != null) menuItemCreateTeam.setVisible(false);
            if (userMenu != null) menuItemLeaveTeam.setVisible(false);
            if (userMenu != null) menuItemManageTeam.setVisible(false);

        } else {
            ua_ib_set_usernic.setEnabled(true);
            ua_ib_copy_uuid.setEnabled(true);
            ua_ib_set_leaderuid.setEnabled(true);

            if (userMenu != null) menuItemLogout.setVisible(true);
            if (userMenu != null) menuItemSendEmailVerification.setVisible(false);

            if (!isHaveTeam) {
                ua_tv_team_value.setText("Вы не состоите ни в какой банде");
                ua_tv_role_value.setText("У вас нет роли");
                if (userMenu != null) menuItemCreateTeam.setVisible(true);
                if (userMenu != null) menuItemLeaveTeam.setVisible(false);
                if (userMenu != null) menuItemManageTeam.setVisible(false);
            } else {
                if (userMenu != null) menuItemCreateTeam.setVisible(false);
                if (userMenu != null) menuItemLeaveTeam.setVisible(true);
                if (isTeamLeader) {
                    if (userMenu != null) menuItemManageTeam.setVisible(true);
                } else {
                    if (userMenu != null) menuItemManageTeam.setVisible(false);
                }
            }

        }


    }

    private void initializeViews() {
        ua_ad_banner = findViewById(R.id.ua_ad_banner);
        ua_ib_set_usernic = findViewById(R.id.ua_ib_set_usernic);
        ua_ib_copy_uuid = findViewById(R.id.ua_ib_copy_uuid);
        ua_tv_usernic_value = findViewById(R.id.ua_tv_usernic_value);
        ua_tv_username_value = findViewById(R.id.ua_tv_username_value);
        ua_tv_useremail_value = findViewById(R.id.ua_tv_useremail_value);
        ua_tv_uuid_value = findViewById(R.id.ua_tv_uuid_value);
        ua_tv_team_value = findViewById(R.id.ua_tv_team_value);
        ua_tv_role_value = findViewById(R.id.ua_tv_role_value);
        ua_ib_set_leaderuid = findViewById(R.id.ua_ib_set_leaderuid);
        ua_tv_leaderuid_value = findViewById(R.id.ua_tv_leaderuid_value);

    }

    public void copyUUIDtoClipboard(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied UUID", ua_tv_uuid_value.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(UserActivity.this, R.string.uid_copied_to_clipboard, Toast.LENGTH_LONG).show();
    }

    public void setUserNIC(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        builder.setTitle(R.string.usernic);
        String defaultValue = dbUser.getUserNIC();
        final EditText input = new EditText(UserActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                dbUser.setUserNIC(newValue);
                Map<String, Object> updateUser = new HashMap<>();
                updateUser.put("userNIC", dbUser.getUserNIC());
                GameActivity.fbDb.collection("users").document(dbUser.getUserID()).update(updateUser);

                loadDataToViews();
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

    public void setLeaderUID(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        builder.setTitle(R.string.leaderuid);
        String defaultValue = dbUser.getLeaderUID();
        final EditText input = new EditText(UserActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = null;
                dbUser.setLeaderUID(newValue);
                Map<String, Object> updateUser = new HashMap<>();
                updateUser.put("leaderUID", dbUser.getLeaderUID());
                GameActivity.fbDb.collection("users").document(dbUser.getUserID()).update(updateUser);

                loadDataToViews();
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