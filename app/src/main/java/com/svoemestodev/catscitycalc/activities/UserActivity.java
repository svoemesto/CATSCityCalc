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
import com.svoemestodev.catscitycalc.database.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

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

    private static final String TAG = "UserActivity";

    /**
     * Событие нажатия какой-то кнопки в шапке
     **/
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
        setContentView(R.layout.activity_user);

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
        ua_ad_banner.loadAd(adRequest);

    }


    private void loadDataToViews() {

        ua_tv_usernic_value.setText(GameActivity.mainDbUser.getUserNIC());
        ua_tv_username_value.setText(GameActivity.mainDbUser.getUserName());
        ua_tv_useremail_value.setText(GameActivity.mainDbUser.getUserEmail());
        ua_tv_uuid_value.setText(GameActivity.mainDbUser.getUserUID());
        String leaderUID = GameActivity.mainDbUser.getLeaderUID() == null ? "" : GameActivity.mainDbUser.getLeaderUID();
        ua_tv_leaderuid_value.setText(leaderUID);

        if (GameActivity.userHaveTeam) {
            ua_tv_team_value.setText(GameActivity.mainDbTeam.getTeamName());
            ua_tv_role_value.setText(GameActivity.mainDbTeamUser.getUserRole());
        } else {
            ua_tv_team_value.setText(R.string.you_hane_no_team);
            ua_tv_role_value.setText(R.string.you_hane_no_role);
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
        String defaultValue = GameActivity.mainDbUser.getUserNIC();
        final EditText input = new EditText(UserActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                GameActivity.mainDbUser.setUserNIC(newValue);
                Map<String, Object> updateUser = new HashMap<>();
                updateUser.put("userNIC", GameActivity.mainDbUser.getUserNIC());
                GameActivity.fbDb.collection("users").document(GameActivity.mainDbUser.getUserID()).update(updateUser);

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
        String defaultValue = GameActivity.mainDbUser.getLeaderUID();
        final EditText input = new EditText(UserActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = null;
                GameActivity.mainDbUser.setLeaderUID(newValue);
                Map<String, Object> updateUser = new HashMap<>();
                updateUser.put("leaderUID", GameActivity.mainDbUser.getLeaderUID());
                GameActivity.fbDb.collection("users").document(GameActivity.mainDbUser.getUserID()).update(updateUser);

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