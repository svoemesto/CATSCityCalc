package com.svoemestodev.catscitycalc.activities;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.svoemestodev.catscitycalc.adapters.ListBuildingAdapter;
import com.svoemestodev.catscitycalc.citycalcclasses.Area;
import com.svoemestodev.catscitycalc.citycalcclasses.CCAGame;
import com.svoemestodev.catscitycalc.classes.Building;
import com.svoemestodev.catscitycalc.classes.Car;
import com.svoemestodev.catscitycalc.database.DbCar;
import com.svoemestodev.catscitycalc.database.DbTeam;
import com.svoemestodev.catscitycalc.database.DbTeamUser;
import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.database.UserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TeamActivity extends AppCompatActivity {

    AdView ta_ad_banner;
    Button ta_bt_invite_user;
    ImageButton ta_bt_edit_team;
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
        ta_bt_edit_team = findViewById(R.id.ta_bt_edit_team);
        ta_lv_users = findViewById(R.id.ta_lv_users);
        ta_tv_name = findViewById(R.id.ta_tv_name);
        ta_bt_invite_user.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
        ta_bt_edit_team.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
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

        String symbolOpened = "\uD83D\uDD13";
        String symbolClosed = "\uD83D\uDD12";
        String symbolEye = "\uD83D\uDC41";

        String textName = (dbTeam.isTeamIsPublic() ? symbolEye :"") + (dbTeam.isTeamIsOpened() ? symbolOpened : symbolClosed) + dbTeam.getTeamName();

        ta_tv_name.setText(textName);

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
                    Toast.makeText(TeamActivity.this, getString(R.string.unable_add_self_to_team), Toast.LENGTH_LONG).show();
                } else {
                    boolean isFind = false;
                    for (DbTeamUser teamUser : listDbTeamUsers) {
                        if (teamUser.getUserID().equals(userUID)) {
                            isFind = true;
                            break;
                        }
                    }
                    if (isFind) {
                        Toast.makeText(TeamActivity.this, getString(R.string.user_already_in_team), Toast.LENGTH_LONG).show();
                    } else {
                        final DocumentReference drUser = GameActivity.fbDb.collection("users").document(userUID);
                        drUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String userLeaderUID = (String)documentSnapshot.get("leaderUID");
                                String userTeamID = (String)documentSnapshot.get("teamID");
                                String userNIC = (String)documentSnapshot.get("userNIC");

                                if (userTeamID != null && !userTeamID.equals("")) {
                                    Toast.makeText(TeamActivity.this, getString(R.string.user_already_in_another_team), Toast.LENGTH_LONG).show();
                                } else {
                                    if (!userLeaderUID.equals(leaderUID)) {
                                        Toast.makeText(TeamActivity.this, getString(R.string.not_equals_uid), Toast.LENGTH_LONG).show();
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

    public void editTeam(View view) {

        Intent intent = new Intent(this, EditTeamActivity.class);
        startActivityForResult(intent, REQUEST_CODE_TEAM_ACTIVITY);

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

            ImageButton ltu_ib_car1 = convertView.findViewById(R.id.ltu_ib_car1);
            TextView ltu_tv_car1_number = convertView.findViewById(R.id.ltu_tv_car1_number);
            TextView ltu_tv_car1_name = convertView.findViewById(R.id.ltu_tv_car1_name);
            ImageView ltu_iv_car1_health = convertView.findViewById(R.id.ltu_iv_car1_health);
            TextView ltu_tv_car1_health = convertView.findViewById(R.id.ltu_tv_car1_health);
            ImageView ltu_iv_car1_shield = convertView.findViewById(R.id.ltu_iv_car1_shield);
            TextView ltu_tv_car1_shield = convertView.findViewById(R.id.ltu_tv_car1_shield);
            ImageView ltu_iv_car1_building = convertView.findViewById(R.id.ltu_iv_car1_building);
            TextView ltu_tv_car1_building = convertView.findViewById(R.id.ltu_tv_car1_building);
            ImageView ltu_iv_car1_task = convertView.findViewById(R.id.ltu_iv_car1_task);
            TextView ltu_tv_car1_task = convertView.findViewById(R.id.ltu_tv_car1_task);
            TextView ltu_tv_car1_repair = convertView.findViewById(R.id.ltu_tv_car1_repair);
            TextView ltu_tv_car1_star = convertView.findViewById(R.id.ltu_tv_car1_star);

            ImageButton ltu_ib_car2 = convertView.findViewById(R.id.ltu_ib_car2);
            TextView ltu_tv_car2_number = convertView.findViewById(R.id.ltu_tv_car2_number);
            TextView ltu_tv_car2_name = convertView.findViewById(R.id.ltu_tv_car2_name);
            ImageView ltu_iv_car2_health = convertView.findViewById(R.id.ltu_iv_car2_health);
            TextView ltu_tv_car2_health = convertView.findViewById(R.id.ltu_tv_car2_health);
            ImageView ltu_iv_car2_shield = convertView.findViewById(R.id.ltu_iv_car2_shield);
            TextView ltu_tv_car2_shield = convertView.findViewById(R.id.ltu_tv_car2_shield);
            ImageView ltu_iv_car2_building = convertView.findViewById(R.id.ltu_iv_car2_building);
            TextView ltu_tv_car2_building = convertView.findViewById(R.id.ltu_tv_car2_building);
            ImageView ltu_iv_car2_task = convertView.findViewById(R.id.ltu_iv_car2_task);
            TextView ltu_tv_car2_task = convertView.findViewById(R.id.ltu_tv_car2_task);
            TextView ltu_tv_car2_repair = convertView.findViewById(R.id.ltu_tv_car2_repair);
            TextView ltu_tv_car2_star = convertView.findViewById(R.id.ltu_tv_car2_star);

            ImageButton ltu_ib_car3 = convertView.findViewById(R.id.ltu_ib_car3);
            TextView ltu_tv_car3_number = convertView.findViewById(R.id.ltu_tv_car3_number);
            TextView ltu_tv_car3_name = convertView.findViewById(R.id.ltu_tv_car3_name);
            ImageView ltu_iv_car3_health = convertView.findViewById(R.id.ltu_iv_car3_health);
            TextView ltu_tv_car3_health = convertView.findViewById(R.id.ltu_tv_car3_health);
            ImageView ltu_iv_car3_shield = convertView.findViewById(R.id.ltu_iv_car3_shield);
            TextView ltu_tv_car3_shield = convertView.findViewById(R.id.ltu_tv_car3_shield);
            ImageView ltu_iv_car3_building = convertView.findViewById(R.id.ltu_iv_car3_building);
            TextView ltu_tv_car3_building = convertView.findViewById(R.id.ltu_tv_car3_building);
            ImageView ltu_iv_car3_task = convertView.findViewById(R.id.ltu_iv_car3_task);
            TextView ltu_tv_car3_task = convertView.findViewById(R.id.ltu_tv_car3_task);
            TextView ltu_tv_car3_repair = convertView.findViewById(R.id.ltu_tv_car3_repair);
            TextView ltu_tv_car3_star = convertView.findViewById(R.id.ltu_tv_car3_star);
            
            ltu_bt_delete.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
            ltu_bt_edit.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
            ltu_bt_set_leader.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
            ltu_bt_set_captain.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
            ltu_bt_set_meat.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
            
            ltu_ib_car1.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
            ltu_ib_car2.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
            ltu_ib_car3.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));

            String userUID = dbTeamUser.getUserID() == null ? "" : dbTeamUser.getUserID();
            String userNIC = dbTeamUser.getUserNIC() == null ? "N/A" : dbTeamUser.getUserNIC();
            String userRole = dbTeamUser.getUserRole() == null ? "N/A" : dbTeamUser.getUserRole();

            ltu_tv_nic_value.setText(userNIC);
            ltu_tv_role_value.setText(userRole);

            DocumentReference docRefCar1 = GameActivity.fbDb.collection("users").document(userUID).collection("userCars").document("car1");

            docRefCar1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen car1 failed.", e);
                        return;
                    }
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Log.d(TAG, "Current car1 data: " + documentSnapshot.getData());

                        if (GameActivity.mainCityCalc != null) {
                            List<Car> listCars = Car.loadList(userUID);
                            Car car1 = listCars.get(0);
                            DbCar dbCar1 = new DbCar(documentSnapshot);
                            
                            car1.setUuid(UUID.fromString(dbCar1.getCarUID()));
                            car1.setSlot(dbCar1.getCarSlot());
                            car1.setName(dbCar1.getCarName());
                            car1.setHealth(dbCar1.getCarHealth());
                            car1.setShield(dbCar1.getCarShield());
                            car1.setBuilding(dbCar1.getCarBuilding());
                            car1.setBuildingTask(dbCar1.getCarBuildingTask());
                            car1.setRepair(dbCar1.getCarRepair());
                            
                            CCAGame ccaGame = (CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY);
                            
                            Bitmap taskBitmap = null;
                            if (dbCar1.getCarBuildingTask() == 1 && ccaGame.isPresent_blt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (dbCar1.getCarBuildingTask() == 2 && ccaGame.isPresent_blc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (dbCar1.getCarBuildingTask() == 3 && ccaGame.isPresent_blb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (dbCar1.getCarBuildingTask() == 4 && ccaGame.isPresent_brt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (dbCar1.getCarBuildingTask() == 5 && ccaGame.isPresent_brc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (dbCar1.getCarBuildingTask() == 6 && ccaGame.isPresent_brb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car1.setTaskPicture(taskBitmap);
                            
                            Bitmap bldBitmap = null;
                            if (dbCar1.getCarBuilding() == 1 && ccaGame.isPresent_blt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (dbCar1.getCarBuilding() == 2 && ccaGame.isPresent_blc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (dbCar1.getCarBuilding() == 3 && ccaGame.isPresent_blb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (dbCar1.getCarBuilding() == 4 && ccaGame.isPresent_brt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (dbCar1.getCarBuilding() == 5 && ccaGame.isPresent_brc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (dbCar1.getCarBuilding() == 6 && ccaGame.isPresent_brb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car1.setBuildingPicture(bldBitmap);
                            
                            car1.save(userUID);

                            ltu_tv_car1_name.setText(car1.getName());
                            ltu_tv_car1_health.setText(String.valueOf(car1.getHealth()));
                            ltu_tv_car1_shield.setText(String.valueOf(car1.getShield()));
                            ltu_tv_car1_star.setVisibility(car1.getBuilding() == car1.getBuildingTask() ? View.VISIBLE : View.INVISIBLE);

                            if (((CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY)).isGameOver()) {
                                if (!car1.isFree()) {
                                    car1.setStateFree();
                                    car1.save(userUID);
                                }
                            }

                            Bitmap car1CarBitmap = car1.isFree() ? car1.getCarPicture() : car1.isRepairing() ? car1.getCarPictureRepairing() : car1.isDefencing() ? car1.getCarPictureDefencing() : car1.getCarPicture() ;
                            Bitmap car1BuildingBitmap = car1.getBuildingPicture();
                            Bitmap car1TaskBitmap = car1.getTaskPicture();
                            if (car1CarBitmap != null) ltu_ib_car1.setImageBitmap(car1CarBitmap);
                            ltu_ib_car1.setImageBitmap(car1.getCarPicture());
                            if (car1.isFree()) ltu_tv_car1_number.setTextColor(Color.BLUE);
                            if (car1.isRepairing() && !car1.isDefencing()) ltu_tv_car1_number.setTextColor(Color.RED);
                            if (!car1.isRepairing() && car1.isDefencing()) ltu_tv_car1_number.setTextColor(Color.GREEN);
                            if (car1.isRepairing() && car1.isDefencing()) ltu_tv_car1_number.setTextColor(Color.YELLOW);
                            String car1textRepair = car1.isRepairing() ? "\uD83D\uDD27" + " " + car1.getTimeStringToEndRepairing() : "";
                            ltu_tv_car1_repair.setText(car1textRepair);
                            if (car1.isDefencing()) {
                                ltu_iv_car1_building.setVisibility(car1BuildingBitmap != null ? View.VISIBLE : View.INVISIBLE);
                                if (car1BuildingBitmap != null) ltu_iv_car1_building.setImageBitmap(car1BuildingBitmap);
                            } else {
                                ltu_iv_car1_building.setVisibility(View.INVISIBLE);
                            }
                            if (car1.getBuildingTask() > 0) {
                                ltu_iv_car1_task.setVisibility(car1TaskBitmap != null ? View.VISIBLE : View.INVISIBLE);
                                if (car1TaskBitmap != null) ltu_iv_car1_task.setImageBitmap(car1TaskBitmap);
                            }

                            ltu_ib_car1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(TeamActivity.this);
                                    builder.setCancelable(true);
                                    builder.setTitle(R.string.building);

                                    final ListBuildingAdapter arrayAdapter = new ListBuildingAdapter(TeamActivity.this);
                                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });

                                    builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Building item = arrayAdapter.getItem(which);
                                            car1.setTaskPicture(item.getBitmap());
                                            car1.setBuildingTask(item.getSlot());
                                            car1.save(userUID);
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("carBuildingTask", item.getSlot());
                                            docRefCar1.update(map);
                                        }
                                    });
                                    builder.show();
                                }
                            });

                            StorageReference storRefCar1Free = GameActivity.fbStor.getReference().child("users/" + userUID + "/car1_free");
                            storRefCar1Free.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                @Override
                                public void onSuccess(StorageMetadata storageMetadata) {
                                    long countBytes = storageMetadata.getSizeBytes();
                                    if (countBytes > 0) {
                                        Task<byte[]> taskBytes = storRefCar1Free.getBytes(countBytes);
                                        taskBytes.addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                car1.setImageByteArrayCar(bytes);
                                                car1.save(userUID);
                                                ltu_ib_car1.setImageBitmap(car1.getCarPicture());
                                            }
                                        });
                                    }
                                }
                            });

                        }

                    } else {
                        Log.d(TAG, "Current car1 data: null");
                    }
                }
            });



            DocumentReference docRefCar2 = GameActivity.fbDb.collection("users").document(userUID).collection("userCars").document("car2");

            docRefCar2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen car2 failed.", e);
                        return;
                    }
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Log.d(TAG, "Current car2 data: " + documentSnapshot.getData());

                        if (GameActivity.mainCityCalc != null) {
                            List<Car> listCars = Car.loadList(userUID);
                            Car car2 = listCars.get(1);
                            DbCar dbCar2 = new DbCar(documentSnapshot);
                            car2.setUuid(UUID.fromString(dbCar2.getCarUID()));
                            car2.setSlot(dbCar2.getCarSlot());
                            car2.setName(dbCar2.getCarName());
                            car2.setHealth(dbCar2.getCarHealth());
                            car2.setShield(dbCar2.getCarShield());
                            car2.setBuilding(dbCar2.getCarBuilding());
                            car2.setBuildingTask(dbCar2.getCarBuildingTask());
                            car2.setRepair(dbCar2.getCarRepair());

                            CCAGame ccaGame = (CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY);

                            Bitmap taskBitmap = null;
                            if (dbCar2.getCarBuildingTask() == 1 && ccaGame.isPresent_blt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (dbCar2.getCarBuildingTask() == 2 && ccaGame.isPresent_blc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (dbCar2.getCarBuildingTask() == 3 && ccaGame.isPresent_blb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (dbCar2.getCarBuildingTask() == 4 && ccaGame.isPresent_brt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (dbCar2.getCarBuildingTask() == 5 && ccaGame.isPresent_brc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (dbCar2.getCarBuildingTask() == 6 && ccaGame.isPresent_brb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car2.setTaskPicture(taskBitmap);

                            Bitmap bldBitmap = null;
                            if (dbCar2.getCarBuilding() == 1 && ccaGame.isPresent_blt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (dbCar2.getCarBuilding() == 2 && ccaGame.isPresent_blc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (dbCar2.getCarBuilding() == 3 && ccaGame.isPresent_blb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (dbCar2.getCarBuilding() == 4 && ccaGame.isPresent_brt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (dbCar2.getCarBuilding() == 5 && ccaGame.isPresent_brc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (dbCar2.getCarBuilding() == 6 && ccaGame.isPresent_brb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car2.setBuildingPicture(bldBitmap);

                            car2.save(userUID);
                            
                            ltu_tv_car2_name.setText(car2.getName());
                            ltu_tv_car2_health.setText(String.valueOf(car2.getHealth()));
                            ltu_tv_car2_shield.setText(String.valueOf(car2.getShield()));
                            ltu_tv_car2_star.setVisibility(car2.getBuilding() == car2.getBuildingTask() ? View.VISIBLE : View.INVISIBLE);

                            if (((CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY)).isGameOver()) {
                                if (!car2.isFree()) {
                                    car2.setStateFree();
                                    car2.save(userUID);
                                }
                            }

                            Bitmap car2CarBitmap = car2.isFree() ? car2.getCarPicture() : car2.isRepairing() ? car2.getCarPictureRepairing() : car2.isDefencing() ? car2.getCarPictureDefencing() : car2.getCarPicture() ;
                            Bitmap car2BuildingBitmap = car2.getBuildingPicture();
                            Bitmap car2TaskBitmap = car2.getTaskPicture();
                            if (car2CarBitmap != null) ltu_ib_car2.setImageBitmap(car2CarBitmap);
                            ltu_ib_car2.setImageBitmap(car2.getCarPicture());
                            if (car2.isFree()) ltu_tv_car2_number.setTextColor(Color.BLUE);
                            if (car2.isRepairing() && !car2.isDefencing()) ltu_tv_car2_number.setTextColor(Color.RED);
                            if (!car2.isRepairing() && car2.isDefencing()) ltu_tv_car2_number.setTextColor(Color.GREEN);
                            if (car2.isRepairing() && car2.isDefencing()) ltu_tv_car2_number.setTextColor(Color.YELLOW);
                            String car2textRepair = car2.isRepairing() ? "\uD83D\uDD27" + " " + car2.getTimeStringToEndRepairing() : "";
                            ltu_tv_car2_repair.setText(car2textRepair);
                            if (car2.isDefencing()) {
                                ltu_iv_car2_building.setVisibility(car2BuildingBitmap != null ? View.VISIBLE : View.INVISIBLE);
                                if (car2BuildingBitmap != null) ltu_iv_car2_building.setImageBitmap(car2BuildingBitmap);
                            } else {
                                ltu_iv_car2_building.setVisibility(View.INVISIBLE);
                            }
                            if (car2.getBuildingTask() > 0) {
                                ltu_iv_car2_task.setVisibility(car2TaskBitmap != null ? View.VISIBLE : View.INVISIBLE);
                                if (car2TaskBitmap != null) ltu_iv_car2_task.setImageBitmap(car2TaskBitmap);
                            }

                            ltu_ib_car2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(TeamActivity.this);
                                    builder.setCancelable(true);
                                    builder.setTitle(R.string.building);

                                    final ListBuildingAdapter arrayAdapter = new ListBuildingAdapter(TeamActivity.this);
                                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });

                                    builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Building item = arrayAdapter.getItem(which);
                                            car2.setTaskPicture(item.getBitmap());
                                            car2.setBuildingTask(item.getSlot());
                                            car2.save(userUID);
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("carBuildingTask", item.getSlot());
                                            docRefCar2.update(map);
                                        }
                                    });
                                    builder.show();
                                }
                            });

                            StorageReference storRefCar2Free = GameActivity.fbStor.getReference().child("users/" + userUID + "/car2_free");
                            storRefCar2Free.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                @Override
                                public void onSuccess(StorageMetadata storageMetadata) {
                                    long countBytes = storageMetadata.getSizeBytes();
                                    if (countBytes > 0) {
                                        Task<byte[]> taskBytes = storRefCar2Free.getBytes(countBytes);
                                        taskBytes.addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                car2.setImageByteArrayCar(bytes);
                                                car2.save(userUID);
                                                ltu_ib_car2.setImageBitmap(car2.getCarPicture());
                                            }
                                        });
                                    }
                                }
                            });
                        }

                    } else {
                        Log.d(TAG, "Current car2 data: null");
                    }
                }
            });

            DocumentReference docRefCar3 = GameActivity.fbDb.collection("users").document(userUID).collection("userCars").document("car3");

            docRefCar3.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen car3 failed.", e);
                        return;
                    }
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Log.d(TAG, "Current car3 data: " + documentSnapshot.getData());

                        if (GameActivity.mainCityCalc != null) {
                            List<Car> listCars = Car.loadList(userUID);
                            Car car3 = listCars.get(2);
                            DbCar dbCar3 = new DbCar(documentSnapshot);
                            car3.setUuid(UUID.fromString(dbCar3.getCarUID()));
                            car3.setSlot(dbCar3.getCarSlot());
                            car3.setName(dbCar3.getCarName());
                            car3.setHealth(dbCar3.getCarHealth());
                            car3.setShield(dbCar3.getCarShield());
                            car3.setBuilding(dbCar3.getCarBuilding());
                            car3.setBuildingTask(dbCar3.getCarBuildingTask());
                            car3.setRepair(dbCar3.getCarRepair());

                            CCAGame ccaGame = (CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY);

                            Bitmap taskBitmap = null;
                            if (dbCar3.getCarBuildingTask() == 1 && ccaGame.isPresent_blt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (dbCar3.getCarBuildingTask() == 2 && ccaGame.isPresent_blc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (dbCar3.getCarBuildingTask() == 3 && ccaGame.isPresent_blb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (dbCar3.getCarBuildingTask() == 4 && ccaGame.isPresent_brt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (dbCar3.getCarBuildingTask() == 5 && ccaGame.isPresent_brc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (dbCar3.getCarBuildingTask() == 6 && ccaGame.isPresent_brb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car3.setTaskPicture(taskBitmap);

                            Bitmap bldBitmap = null;
                            if (dbCar3.getCarBuilding() == 1 && ccaGame.isPresent_blt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (dbCar3.getCarBuilding() == 2 && ccaGame.isPresent_blc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (dbCar3.getCarBuilding() == 3 && ccaGame.isPresent_blb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (dbCar3.getCarBuilding() == 4 && ccaGame.isPresent_brt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (dbCar3.getCarBuilding() == 5 && ccaGame.isPresent_brc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (dbCar3.getCarBuilding() == 6 && ccaGame.isPresent_brb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car3.setBuildingPicture(bldBitmap);

                            car3.save(userUID);
                            
                            ltu_tv_car3_name.setText(car3.getName());
                            ltu_tv_car3_health.setText(String.valueOf(car3.getHealth()));
                            ltu_tv_car3_shield.setText(String.valueOf(car3.getShield()));
                            ltu_tv_car3_star.setVisibility(car3.getBuilding() == car3.getBuildingTask() ? View.VISIBLE : View.INVISIBLE);

                            if (((CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY)).isGameOver()) {
                                if (!car3.isFree()) {
                                    car3.setStateFree();
                                    car3.save(userUID);
                                }
                            }

                            Bitmap car3CarBitmap = car3.isFree() ? car3.getCarPicture() : car3.isRepairing() ? car3.getCarPictureRepairing() : car3.isDefencing() ? car3.getCarPictureDefencing() : car3.getCarPicture() ;
                            Bitmap car3BuildingBitmap = car3.getBuildingPicture();
                            Bitmap car3TaskBitmap = car3.getTaskPicture();
                            if (car3CarBitmap != null) ltu_ib_car3.setImageBitmap(car3CarBitmap);
                            ltu_ib_car3.setImageBitmap(car3.getCarPicture());
                            if (car3.isFree()) ltu_tv_car3_number.setTextColor(Color.BLUE);
                            if (car3.isRepairing() && !car3.isDefencing()) ltu_tv_car3_number.setTextColor(Color.RED);
                            if (!car3.isRepairing() && car3.isDefencing()) ltu_tv_car3_number.setTextColor(Color.GREEN);
                            if (car3.isRepairing() && car3.isDefencing()) ltu_tv_car3_number.setTextColor(Color.YELLOW);
                            String car3textRepair = car3.isRepairing() ? "\uD83D\uDD27" + " " + car3.getTimeStringToEndRepairing() : "";
                            ltu_tv_car3_repair.setText(car3textRepair);
                            if (car3.isDefencing()) {
                                ltu_iv_car3_building.setVisibility(car3BuildingBitmap != null ? View.VISIBLE : View.INVISIBLE);
                                if (car3BuildingBitmap != null) ltu_iv_car3_building.setImageBitmap(car3BuildingBitmap);
                            } else {
                                ltu_iv_car3_building.setVisibility(View.INVISIBLE);
                            }
                            if (car3.getBuildingTask() > 0) {
                                ltu_iv_car3_task.setVisibility(car3TaskBitmap != null ? View.VISIBLE : View.INVISIBLE);
                                if (car3TaskBitmap != null) ltu_iv_car3_task.setImageBitmap(car3TaskBitmap);
                            }

                            ltu_ib_car3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(TeamActivity.this);
                                    builder.setCancelable(true);
                                    builder.setTitle(R.string.building);

                                    final ListBuildingAdapter arrayAdapter = new ListBuildingAdapter(TeamActivity.this);
                                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });

                                    builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Building item = arrayAdapter.getItem(which);
                                            car3.setTaskPicture(item.getBitmap());
                                            car3.setBuildingTask(item.getSlot());
                                            car3.save(userUID);
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("carBuildingTask", item.getSlot());
                                            docRefCar3.update(map);
                                        }
                                    });
                                    builder.show();
                                }
                            });

                            StorageReference storRefCar3Free = GameActivity.fbStor.getReference().child("users/" + userUID + "/car3_free");
                            storRefCar3Free.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                @Override
                                public void onSuccess(StorageMetadata storageMetadata) {
                                    long countBytes = storageMetadata.getSizeBytes();
                                    if (countBytes > 0) {
                                        Task<byte[]> taskBytes = storRefCar3Free.getBytes(countBytes);
                                        taskBytes.addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                car3.setImageByteArrayCar(bytes);
                                                car3.save(userUID);
                                                ltu_ib_car3.setImageBitmap(car3.getCarPicture());
                                            }
                                        });
                                    }
                                }
                            });
                            
                        }

                    } else {
                        Log.d(TAG, "Current car3 data: null");
                    }
                }
            });


            
            
            ltu_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dbTeamUser.getUserID().equals(GameActivity.fbUser.getUid())) {
                        Toast.makeText(TeamActivity.this, getString(R.string.unable_delete_self), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TeamActivity.this, getString(R.string.unable_chage_self_role), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TeamActivity.this, getString(R.string.unable_chage_self_role), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TeamActivity.this, getString(R.string.unable_chage_self_role), Toast.LENGTH_LONG).show();
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