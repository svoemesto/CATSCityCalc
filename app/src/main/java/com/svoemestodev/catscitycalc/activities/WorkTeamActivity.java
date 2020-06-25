package com.svoemestodev.catscitycalc.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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

import static com.svoemestodev.catscitycalc.activities.GameActivity.mainDbTeam;

public class WorkTeamActivity extends AppCompatActivity {

    AdView ta_ad_banner;
    Button ta_bt_clear_tasks_cars;
    ListView ta_lv_users;
    TextView ta_tv_name;

    private static final int REQUEST_CODE_TEAM_ACTIVITY = 100;
    private static List<DbTeamUser> listDbTeamUsers = new ArrayList<>();
    public static DbTeam dbTeam;
    private static final String TAG = "TeamActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_team);

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
        ta_bt_clear_tasks_cars = findViewById(R.id.ta_bt_clear_tasks_cars);
        ta_lv_users = findViewById(R.id.ta_lv_users);
        ta_tv_name = findViewById(R.id.ta_tv_name);
        ta_bt_clear_tasks_cars.setEnabled(GameActivity.userRole.equals(UserRole.LEADER));
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
                        ta_lv_users.setAdapter(new ListDbTeamUsersAdapter( WorkTeamActivity.this));
                    }
                }
            }

        });

    }

    public void inviteUser(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(WorkTeamActivity.this);
        builder.setTitle(R.string.uuid);
        String defaultValue = "";
        final EditText input = new EditText(WorkTeamActivity.this);
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
                    Toast.makeText(WorkTeamActivity.this, getString(R.string.unable_add_self_to_team), Toast.LENGTH_LONG).show();
                } else {
                    boolean isFind = false;
                    for (DbTeamUser teamUser : listDbTeamUsers) {
                        if (teamUser.getUserID().equals(userUID)) {
                            isFind = true;
                            break;
                        }
                    }
                    if (isFind) {
                        Toast.makeText(WorkTeamActivity.this, getString(R.string.user_already_in_team), Toast.LENGTH_LONG).show();
                    } else {
                        final DocumentReference drUser = GameActivity.fbDb.collection("users").document(userUID);
                        drUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String userLeaderUID = (String)documentSnapshot.get("leaderUID");
                                String userTeamID = (String)documentSnapshot.get("teamID");
                                String userNIC = (String)documentSnapshot.get("userNIC");

                                if (userTeamID != null && !userTeamID.equals("")) {
                                    Toast.makeText(WorkTeamActivity.this, getString(R.string.user_already_in_another_team), Toast.LENGTH_LONG).show();
                                } else {
                                    if (!userLeaderUID.equals(leaderUID)) {
                                        Toast.makeText(WorkTeamActivity.this, getString(R.string.not_equals_uid), Toast.LENGTH_LONG).show();
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

    public void clearTaskCars(View view) {

        AlertDialog.Builder builderConfirmation = new AlertDialog.Builder(WorkTeamActivity.this);
        builderConfirmation.setCancelable(true);
        builderConfirmation.setTitle(R.string.clear_tasks_cars);
        builderConfirmation.setMessage(R.string.sure_clear_tasks_cars);
        builderConfirmation.setPositiveButton(R.string.yes_sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Query queryTeamUsers = GameActivity.fbDb.collection("teams").document(mainDbTeam.getTeamID()).collection("teamUsers");
                queryTeamUsers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DbTeamUser dbTeamUser = document.toObject(DbTeamUser.class);
                                Map<String, Object> map = new HashMap<>();
                                map.put("carBuildingTask", -1);

                                DocumentReference drUserCar1 = GameActivity.fbDb.collection("users").document(dbTeamUser.getUserID()).collection("userCars").document("car1");
                                drUserCar1.update(map);
                                DocumentReference drUserCar2 = GameActivity.fbDb.collection("users").document(dbTeamUser.getUserID()).collection("userCars").document("car2");
                                drUserCar2.update(map);
                                DocumentReference drUserCar3 = GameActivity.fbDb.collection("users").document(dbTeamUser.getUserID()).collection("userCars").document("car3");
                                drUserCar3.update(map);
                            }
                        }
                    }
                });


            }
        });
        builderConfirmation.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builderConfirmation.create();
        dialog.show();
        
    }

    private class ListDbTeamUsersAdapter extends ArrayAdapter<DbTeamUser> {

        public ListDbTeamUsersAdapter(@NonNull Context context) {
            super(context, R.layout.layout_work_team_user, listDbTeamUsers);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final DbTeamUser dbTeamUser = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_work_team_user, null);
            }


            TextView ltu_tv_nic_value = convertView.findViewById(R.id.ltu_tv_nic_value);

            ImageButton ltu_ib_car1 = convertView.findViewById(R.id.ltu_ib_car1);
            TextView ltu_tv_car1_repair = convertView.findViewById(R.id.ltu_tv_car1_repair);
            ImageView ltu_iv_car1_building_icon = convertView.findViewById(R.id.ltu_iv_car1_building_icon);
            ImageView ltu_iv_car1_task_icon = convertView.findViewById(R.id.ltu_iv_car1_task_icon);

            ImageButton ltu_ib_car2 = convertView.findViewById(R.id.ltu_ib_car2);
            TextView ltu_tv_car2_repair = convertView.findViewById(R.id.ltu_tv_car2_repair);
            ImageView ltu_iv_car2_building_icon = convertView.findViewById(R.id.ltu_iv_car2_building_icon);
            ImageView ltu_iv_car2_task_icon = convertView.findViewById(R.id.ltu_iv_car2_task_icon);

            ImageButton ltu_ib_car3 = convertView.findViewById(R.id.ltu_ib_car3);
            TextView ltu_tv_car3_repair = convertView.findViewById(R.id.ltu_tv_car3_repair);
            ImageView ltu_iv_car3_building_icon = convertView.findViewById(R.id.ltu_iv_car3_building_icon);
            ImageView ltu_iv_car3_task_icon = convertView.findViewById(R.id.ltu_iv_car3_task_icon);


            ltu_ib_car1.setEnabled(!GameActivity.userRole.equals(UserRole.MEAT));
            ltu_ib_car2.setEnabled(!GameActivity.userRole.equals(UserRole.MEAT));
            ltu_ib_car3.setEnabled(!GameActivity.userRole.equals(UserRole.MEAT));

            String userUID = dbTeamUser.getUserID() == null ? "" : dbTeamUser.getUserID();
            String userNIC = dbTeamUser.getUserNIC() == null ? "N/A" : dbTeamUser.getUserNIC();
            String userRoleString = dbTeamUser.getUserRole() == null ? "N/A" : dbTeamUser.getUserRole();
            
            UserRole userRole = userRoleString.equals("leader") ? UserRole.LEADER : userRoleString.equals("captain") ? UserRole.CAPTAIN : UserRole.MEAT;
            ltu_tv_nic_value.setText(userNIC + " [" + userRole + "]");
            

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
                            Car car1;
                            if (userUID.equals(GameActivity.fbUser.getUid())) {
                                car1 = Car.loadCar(1);
                            } else {
                                car1 = Car.loadCar(1, userUID);
                            }

                            DbCar dbCar1 = new DbCar(documentSnapshot);
                            
//                            car1.setUuid(UUID.fromString(dbCar1.getCarUID()));
                            car1.setSlot(dbCar1.getCarSlot());
                            car1.setName(dbCar1.getCarName());
                            car1.setHealth(dbCar1.getCarHealth());
                            car1.setShield(dbCar1.getCarShield());
                            car1.setBuilding(dbCar1.getCarBuilding());
                            car1.setBuildingTask(dbCar1.getCarBuildingTask());
                            car1.setRepair(dbCar1.getCarRepair());
                            car1.setTeamID(dbCar1.getTeamID());
                            car1.setUserUID(dbCar1.getUserUID());
                            car1.setUserNIC(dbCar1.getUserNIC());
                            
                            CCAGame ccaGame = (CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY);
                            
                            Bitmap taskBitmap = null;
                            if (car1.getBuildingTask() == 1 && ccaGame.isPresent_blt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (car1.getBuildingTask() == 2 && ccaGame.isPresent_blc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (car1.getBuildingTask() == 3 && ccaGame.isPresent_blb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (car1.getBuildingTask() == 4 && ccaGame.isPresent_brt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (car1.getBuildingTask() == 5 && ccaGame.isPresent_brc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (car1.getBuildingTask() == 6 && ccaGame.isPresent_brb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car1.setTaskPicture(taskBitmap);
                            
                            Bitmap bldBitmap = null;
                            if (car1.getBuilding() == 1 && ccaGame.isPresent_blt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (car1.getBuilding() == 2 && ccaGame.isPresent_blc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (car1.getBuilding() == 3 && ccaGame.isPresent_blb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (car1.getBuilding() == 4 && ccaGame.isPresent_brt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (car1.getBuilding() == 5 && ccaGame.isPresent_brc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (car1.getBuilding() == 6 && ccaGame.isPresent_brb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car1.setBuildingPicture(bldBitmap);

                            if (userUID.equals(GameActivity.fbUser.getUid())) {
//                                car1.save();
                            } else {
                                car1.save(userUID);
                            }


                            
                            if (((CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY)).isGameOver()) {
                                if (!car1.isFree()) {
                                    car1.setStateFree();
                                    if (userUID.equals(GameActivity.fbUser.getUid())) {
//                                        car1.save();
                                    } else {
                                        car1.save(userUID);
                                    }
                                }
                            }

                            Bitmap car1CarBitmap = car1.isFree() ? car1.getCarPicture() : car1.isRepairing() ? car1.getCarPictureRepairing() : car1.isDefencing() ? car1.getCarPictureDefencing() : car1.getCarPicture() ;
                            if (car1CarBitmap != null) ltu_ib_car1.setImageBitmap(car1CarBitmap);
                            ltu_ib_car1.setImageBitmap(car1.getCarPicture());
                            String car1textRepair = car1.isRepairing() ? "" + car1.getTimeStringToEndRepairing() : "";
                            ltu_tv_car1_repair.setText(car1textRepair);
                            ltu_iv_car1_building_icon.setVisibility(car1.getBuilding() >= 0 ? View.VISIBLE : View.INVISIBLE);
                            ltu_iv_car1_task_icon.setVisibility(car1.getBuildingTask() > 0 ? View.VISIBLE : View.INVISIBLE);
                            switch (car1.getBuilding()) {
                                case 0:
                                    ltu_iv_car1_building_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                                    break;
                                case 1:
                                    ltu_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_blt_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_blt_blue : R.drawable.ic_blt_red));
                                    break;
                                case 2:
                                    ltu_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_blc_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_blc_blue : R.drawable.ic_blc_red));
                                    break;
                                case 3:
                                    ltu_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_blb_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_blb_blue : R.drawable.ic_blb_red));
                                    break;
                                case 4:
                                    ltu_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_brt_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_brt_blue : R.drawable.ic_brt_red));
                                    break;
                                case 5:
                                    ltu_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_brc_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_brc_blue : R.drawable.ic_brc_red));
                                    break;
                                case 6:
                                    ltu_iv_car1_building_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_brb_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_brb_blue : R.drawable.ic_brb_red));
                                    break;
                                default:
                            }
                            switch (car1.getBuildingTask()) {
                                case 0:
                                    ltu_iv_car1_task_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                                    break;
                                case 1:
                                    ltu_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_blt_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_blt_blue : R.drawable.ic_blt_red));
                                    break;
                                case 2:
                                    ltu_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_blc_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_blc_blue : R.drawable.ic_blc_red));
                                    break;
                                case 3:
                                    ltu_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_blb_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_blb_blue : R.drawable.ic_blb_red));
                                    break;
                                case 4:
                                    ltu_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_brt_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_brt_blue : R.drawable.ic_brt_red));
                                    break;
                                case 5:
                                    ltu_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_brc_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_brc_blue : R.drawable.ic_brc_red));
                                    break;
                                case 6:
                                    ltu_iv_car1_task_icon.setImageDrawable(getDrawable(car1.getBuildingTask() == -1 ? R.drawable.ic_brb_gray : car1.getBuilding() == car1.getBuildingTask() ? R.drawable.ic_brb_blue : R.drawable.ic_brb_red));
                                    break;
                                default:
                            }

                            ltu_ib_car1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(WorkTeamActivity.this);
                                    builder.setCancelable(true);
                                    builder.setTitle(R.string.building);

                                    final ListBuildingAdapter arrayAdapter = new ListBuildingAdapter(WorkTeamActivity.this);
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
                                            if (userUID.equals(GameActivity.fbUser.getUid())) {
//                                                car1.save();
                                            } else {
                                                car1.save(userUID);
                                            }
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("carBuildingTask", item.getSlot());
                                            docRefCar1.update(map);
                                        }
                                    });
                                    builder.show();
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
                            Car car2;
                            if (userUID.equals(GameActivity.fbUser.getUid())) {
                                car2 = Car.loadCar(2);
                            } else {
                                car2 = Car.loadCar(2, userUID);
                            }

                            DbCar dbCar2 = new DbCar(documentSnapshot);

//                            car2.setUuid(UUID.fromString(dbCar2.getCarUID()));
                            car2.setSlot(dbCar2.getCarSlot());
                            car2.setName(dbCar2.getCarName());
                            car2.setHealth(dbCar2.getCarHealth());
                            car2.setShield(dbCar2.getCarShield());
                            car2.setBuilding(dbCar2.getCarBuilding());
                            car2.setBuildingTask(dbCar2.getCarBuildingTask());
                            car2.setRepair(dbCar2.getCarRepair());
                            car2.setTeamID(dbCar2.getTeamID());
                            car2.setUserUID(dbCar2.getUserUID());
                            car2.setUserNIC(dbCar2.getUserNIC());

                            CCAGame ccaGame = (CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY);

                            Bitmap taskBitmap = null;
                            if (car2.getBuildingTask() == 1 && ccaGame.isPresent_blt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (car2.getBuildingTask() == 2 && ccaGame.isPresent_blc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (car2.getBuildingTask() == 3 && ccaGame.isPresent_blb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (car2.getBuildingTask() == 4 && ccaGame.isPresent_brt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (car2.getBuildingTask() == 5 && ccaGame.isPresent_brc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (car2.getBuildingTask() == 6 && ccaGame.isPresent_brb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car2.setTaskPicture(taskBitmap);

                            Bitmap bldBitmap = null;
                            if (car2.getBuilding() == 1 && ccaGame.isPresent_blt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (car2.getBuilding() == 2 && ccaGame.isPresent_blc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (car2.getBuilding() == 3 && ccaGame.isPresent_blb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (car2.getBuilding() == 4 && ccaGame.isPresent_brt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (car2.getBuilding() == 5 && ccaGame.isPresent_brc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (car2.getBuilding() == 6 && ccaGame.isPresent_brb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car2.setBuildingPicture(bldBitmap);

                            if (userUID.equals(GameActivity.fbUser.getUid())) {
//                                car2.save();
                            } else {
                                car2.save(userUID);
                            }



                            if (((CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY)).isGameOver()) {
                                if (!car2.isFree()) {
                                    car2.setStateFree();
                                    if (userUID.equals(GameActivity.fbUser.getUid())) {
//                                        car2.save();
                                    } else {
                                        car2.save(userUID);
                                    }
                                }
                            }

                            Bitmap car2CarBitmap = car2.isFree() ? car2.getCarPicture() : car2.isRepairing() ? car2.getCarPictureRepairing() : car2.isDefencing() ? car2.getCarPictureDefencing() : car2.getCarPicture() ;
                            if (car2CarBitmap != null) ltu_ib_car2.setImageBitmap(car2CarBitmap);
                            ltu_ib_car2.setImageBitmap(car2.getCarPicture());
                            String car2textRepair = car2.isRepairing() ? "" + car2.getTimeStringToEndRepairing() : "";
                            ltu_tv_car2_repair.setText(car2textRepair);
                            ltu_iv_car2_building_icon.setVisibility(car2.getBuilding() >= 0 ? View.VISIBLE : View.INVISIBLE);
                            ltu_iv_car2_task_icon.setVisibility(car2.getBuildingTask() > 0 ? View.VISIBLE : View.INVISIBLE);
                            switch (car2.getBuilding()) {
                                case 0:
                                    ltu_iv_car2_building_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                                    break;
                                case 1:
                                    ltu_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_blt_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_blt_blue : R.drawable.ic_blt_red));
                                    break;
                                case 2:
                                    ltu_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_blc_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_blc_blue : R.drawable.ic_blc_red));
                                    break;
                                case 3:
                                    ltu_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_blb_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_blb_blue : R.drawable.ic_blb_red));
                                    break;
                                case 4:
                                    ltu_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_brt_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_brt_blue : R.drawable.ic_brt_red));
                                    break;
                                case 5:
                                    ltu_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_brc_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_brc_blue : R.drawable.ic_brc_red));
                                    break;
                                case 6:
                                    ltu_iv_car2_building_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_brb_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_brb_blue : R.drawable.ic_brb_red));
                                    break;
                                default:
                            }
                            switch (car2.getBuildingTask()) {
                                case 0:
                                    ltu_iv_car2_task_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                                    break;
                                case 1:
                                    ltu_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_blt_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_blt_blue : R.drawable.ic_blt_red));
                                    break;
                                case 2:
                                    ltu_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_blc_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_blc_blue : R.drawable.ic_blc_red));
                                    break;
                                case 3:
                                    ltu_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_blb_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_blb_blue : R.drawable.ic_blb_red));
                                    break;
                                case 4:
                                    ltu_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_brt_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_brt_blue : R.drawable.ic_brt_red));
                                    break;
                                case 5:
                                    ltu_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_brc_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_brc_blue : R.drawable.ic_brc_red));
                                    break;
                                case 6:
                                    ltu_iv_car2_task_icon.setImageDrawable(getDrawable(car2.getBuildingTask() == -1 ? R.drawable.ic_brb_gray : car2.getBuilding() == car2.getBuildingTask() ? R.drawable.ic_brb_blue : R.drawable.ic_brb_red));
                                    break;
                                default:
                            }

                            ltu_ib_car2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(WorkTeamActivity.this);
                                    builder.setCancelable(true);
                                    builder.setTitle(R.string.building);

                                    final ListBuildingAdapter arrayAdapter = new ListBuildingAdapter(WorkTeamActivity.this);
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
                                            if (userUID.equals(GameActivity.fbUser.getUid())) {
//                                                car2.save();
                                            } else {
                                                car2.save(userUID);
                                            }
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("carBuildingTask", item.getSlot());
                                            docRefCar2.update(map);
                                        }
                                    });
                                    builder.show();
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
                            Car car3;
                            if (userUID.equals(GameActivity.fbUser.getUid())) {
                                car3 = Car.loadCar(3);
                            } else {
                                car3 = Car.loadCar(3, userUID);
                            }

                            DbCar dbCar3 = new DbCar(documentSnapshot);

//                            car3.setUuid(UUID.fromString(dbCar3.getCarUID()));
                            car3.setSlot(dbCar3.getCarSlot());
                            car3.setName(dbCar3.getCarName());
                            car3.setHealth(dbCar3.getCarHealth());
                            car3.setShield(dbCar3.getCarShield());
                            car3.setBuilding(dbCar3.getCarBuilding());
                            car3.setBuildingTask(dbCar3.getCarBuildingTask());
                            car3.setRepair(dbCar3.getCarRepair());
                            car3.setTeamID(dbCar3.getTeamID());
                            car3.setUserUID(dbCar3.getUserUID());
                            car3.setUserNIC(dbCar3.getUserNIC());

                            CCAGame ccaGame = (CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY);

                            Bitmap taskBitmap = null;
                            if (car3.getBuildingTask() == 1 && ccaGame.isPresent_blt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (car3.getBuildingTask() == 2 && ccaGame.isPresent_blc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (car3.getBuildingTask() == 3 && ccaGame.isPresent_blb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (car3.getBuildingTask() == 4 && ccaGame.isPresent_brt()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (car3.getBuildingTask() == 5 && ccaGame.isPresent_brc()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (car3.getBuildingTask() == 6 && ccaGame.isPresent_brb()) {
                                taskBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car3.setTaskPicture(taskBitmap);

                            Bitmap bldBitmap = null;
                            if (car3.getBuilding() == 1 && ccaGame.isPresent_blt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLT).getBmpSrc();
                            } else if (car3.getBuilding() == 2 && ccaGame.isPresent_blc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLC).getBmpSrc();
                            } else if (car3.getBuilding() == 3 && ccaGame.isPresent_blb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BLB).getBmpSrc();
                            } else if (car3.getBuilding() == 4 && ccaGame.isPresent_brt()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRT).getBmpSrc();
                            } else if (car3.getBuilding() == 5 && ccaGame.isPresent_brc()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRC).getBmpSrc();
                            } else if (car3.getBuilding() == 6 && ccaGame.isPresent_brb()) {
                                bldBitmap = GameActivity.mainCityCalc.getMapAreas().get(Area.BRB).getBmpSrc();
                            }
                            car3.setBuildingPicture(bldBitmap);

                            if (userUID.equals(GameActivity.fbUser.getUid())) {
//                                car3.save();
                            } else {
                                car3.save(userUID);
                            }



                            if (((CCAGame)GameActivity.mainCityCalc.getMapAreas().get(Area.CITY)).isGameOver()) {
                                if (!car3.isFree()) {
                                    car3.setStateFree();
                                    if (userUID.equals(GameActivity.fbUser.getUid())) {
//                                        car3.save();
                                    } else {
                                        car3.save(userUID);
                                    }
                                }
                            }

                            Bitmap car3CarBitmap = car3.isFree() ? car3.getCarPicture() : car3.isRepairing() ? car3.getCarPictureRepairing() : car3.isDefencing() ? car3.getCarPictureDefencing() : car3.getCarPicture() ;
                            if (car3CarBitmap != null) ltu_ib_car3.setImageBitmap(car3CarBitmap);
                            ltu_ib_car3.setImageBitmap(car3.getCarPicture());
                            String car3textRepair = car3.isRepairing() ? "" + car3.getTimeStringToEndRepairing() : "";
                            ltu_tv_car3_repair.setText(car3textRepair);
                            ltu_iv_car3_building_icon.setVisibility(car3.getBuilding() >= 0 ? View.VISIBLE : View.INVISIBLE);
                            ltu_iv_car3_task_icon.setVisibility(car3.getBuildingTask() > 0 ? View.VISIBLE : View.INVISIBLE);
                            switch (car3.getBuilding()) {
                                case 0:
                                    ltu_iv_car3_building_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                                    break;
                                case 1:
                                    ltu_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_blt_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_blt_blue : R.drawable.ic_blt_red));
                                    break;
                                case 2:
                                    ltu_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_blc_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_blc_blue : R.drawable.ic_blc_red));
                                    break;
                                case 3:
                                    ltu_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_blb_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_blb_blue : R.drawable.ic_blb_red));
                                    break;
                                case 4:
                                    ltu_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_brt_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_brt_blue : R.drawable.ic_brt_red));
                                    break;
                                case 5:
                                    ltu_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_brc_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_brc_blue : R.drawable.ic_brc_red));
                                    break;
                                case 6:
                                    ltu_iv_car3_building_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_brb_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_brb_blue : R.drawable.ic_brb_red));
                                    break;
                                default:
                            }
                            switch (car3.getBuildingTask()) {
                                case 0:
                                    ltu_iv_car3_task_icon.setImageDrawable(getDrawable(R.drawable.ic_bxx_black));
                                    break;
                                case 1:
                                    ltu_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_blt_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_blt_blue : R.drawable.ic_blt_red));
                                    break;
                                case 2:
                                    ltu_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_blc_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_blc_blue : R.drawable.ic_blc_red));
                                    break;
                                case 3:
                                    ltu_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_blb_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_blb_blue : R.drawable.ic_blb_red));
                                    break;
                                case 4:
                                    ltu_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_brt_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_brt_blue : R.drawable.ic_brt_red));
                                    break;
                                case 5:
                                    ltu_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_brc_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_brc_blue : R.drawable.ic_brc_red));
                                    break;
                                case 6:
                                    ltu_iv_car3_task_icon.setImageDrawable(getDrawable(car3.getBuildingTask() == -1 ? R.drawable.ic_brb_gray : car3.getBuilding() == car3.getBuildingTask() ? R.drawable.ic_brb_blue : R.drawable.ic_brb_red));
                                    break;
                                default:
                            }

                            ltu_ib_car3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(WorkTeamActivity.this);
                                    builder.setCancelable(true);
                                    builder.setTitle(R.string.building);

                                    final ListBuildingAdapter arrayAdapter = new ListBuildingAdapter(WorkTeamActivity.this);
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
                                            if (userUID.equals(GameActivity.fbUser.getUid())) {
//                                                car3.save();
                                            } else {
                                                car3.save(userUID);
                                            }
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("carBuildingTask", item.getSlot());
                                            docRefCar3.update(map);
                                        }
                                    });
                                    builder.show();
                                }
                            });



                        }

                    } else {
                        Log.d(TAG, "Current car3 data: null");
                    }
                }
            });


            return convertView;

        }
    }

}