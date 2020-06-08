package com.svoemestodev.catscitycalc;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.firebase.ui.auth.AuthUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    public static DbUser dbUser = new DbUser();

    AdView ua_ad_banner;
    Button ua_bt_logout;
    TextView ua_tv_usernic_value;
    TextView ua_tv_username_value;
    TextView ua_tv_useremail_value;
    TextView ua_tv_uuid_value;

    private static final int SIGN_IN_REQUEST_CODE = 1;
    private static final String TAG = "UserActivity";

    private static DocumentReference userDocument;

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

            final String userUID = GameActivity.fbUser.getUid();
            CollectionReference users = GameActivity.fbDb.collection("users");
            Query query = users.whereEqualTo("userUID", userUID);
            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.i(TAG, document.getId() + " => " + document.getData());
                                    dbUser.userID = document.getId();
                                }
                                if (task.getResult().isEmpty()) {
                                    // результат запроса пустой, такого юзера еще нет - создаем его
                                    dbUser.userUID = userUID;
                                    dbUser.userName = GameActivity.fbUser.getDisplayName();
                                    dbUser.userEmail = GameActivity.fbUser.getEmail();
                                    dbUser.userNIC = GameActivity.fbUser.getDisplayName();

                                    Map<String, Object> newUser = new HashMap<>();
                                    newUser.put("userID", dbUser.userID);
                                    newUser.put("userUID", dbUser.userUID);
                                    newUser.put("userName", dbUser.userName);
                                    newUser.put("userEmail", dbUser.userEmail);
                                    newUser.put("userNIC", dbUser.userNIC);

                                    // Add a new document with a generated ID
                                    GameActivity.fbDb.collection("users")
                                            .add(newUser)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                    dbUser.userID = documentReference.getId();
                                                    CollectionReference users = GameActivity.fbDb.collection("users");
                                                    Map<String, Object> updateUser = new HashMap<>();
                                                    updateUser.put("userID", dbUser.userID);
                                                    GameActivity.fbDb.collection("users").document(dbUser.getUserID()).update(updateUser);
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
                final String userUID = GameActivity.fbUser.getUid();
                CollectionReference users = GameActivity.fbDb.collection("users");
                Query query = users.whereEqualTo("userUID", userUID);
                query.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.i(TAG, document.getId() + " => " + document.getData());
                                        dbUser.userID = document.getId();
                                    }
                                    if (task.getResult().isEmpty()) {
                                        // результат запроса пустой, такого юзера еще нет - создаем его
                                        dbUser.userUID = userUID;
                                        dbUser.userName = GameActivity.fbUser.getDisplayName();
                                        dbUser.userEmail = GameActivity.fbUser.getEmail();
                                        dbUser.userNIC = GameActivity.fbUser.getDisplayName();

                                        Map<String, Object> newUser = new HashMap<>();
                                        newUser.put("userID", dbUser.userID);
                                        newUser.put("userUID", dbUser.userUID);
                                        newUser.put("userName", dbUser.userName);
                                        newUser.put("userEmail", dbUser.userEmail);
                                        newUser.put("userNIC", dbUser.userNIC);

                                        // Add a new document with a generated ID
                                        GameActivity.fbDb.collection("users")
                                                .add(newUser)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                        dbUser.userID = documentReference.getId();
                                                        CollectionReference users = GameActivity.fbDb.collection("users");
                                                        Map<String, Object> updateUser = new HashMap<>();
                                                        updateUser.put("userID", dbUser.userID);
                                                        GameActivity.fbDb.collection("users").document(dbUser.getUserID()).update(updateUser);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();  // индекс нажакой кнопки
        if (id == android.R.id.home) { //если в шапке нажата кнопка "Назад"
            onBackPressed();    // вызываем метод "Назад"
            return true;        // возвращаем Истину
        }
        return super.onOptionsItemSelected(item);   // возвращаем супер-метод
    }


    private void loadDataToViews() {

        ua_tv_usernic_value.setText(dbUser.getUserNIC());
        ua_tv_username_value.setText(dbUser.getUserName());
        ua_tv_useremail_value.setText(dbUser.getUserEmail());
        ua_tv_uuid_value.setText(dbUser.getUserUID());

    }

    private void initializeViews() {
        ua_ad_banner = findViewById(R.id.ua_ad_banner);
        ua_bt_logout = findViewById(R.id.ua_bt_logout);
        ua_tv_usernic_value = findViewById(R.id.ua_tv_usernic_value);
        ua_tv_username_value = findViewById(R.id.ua_tv_username_value);
        ua_tv_useremail_value = findViewById(R.id.ua_tv_useremail_value);
        ua_tv_uuid_value = findViewById(R.id.ua_tv_uuid_value);
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
                CollectionReference users = GameActivity.fbDb.collection("users");
                Map<String, Object> updateUser = new HashMap<>();
                updateUser.put("userNIC", dbUser.userNIC);
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

    public void logout(View view) {
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UserActivity.this,
                                R.string.your_signed_out,
                                Toast.LENGTH_LONG)
                                .show();

                        // Close activity
                        finish();
                    }
                });
    }
}