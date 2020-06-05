package com.svoemestodev.catscitycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class CarActivity extends AppCompatActivity {

    AdView ca_ad_banner;
    ImageView ca_iv_car_picture;
    ImageView ca_iv_car;
    TextView ca_tv_name_value;
    TextView ca_tv_health_value;
    TextView ca_tv_shield_value;
    TextView ca_tv_repair_value;
    ImageView ca_iv_building_value;

    public static int slot;
    public static Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        if (slot == 0) slot = 1;
        car = Car.loadList().get(slot-1);

        initializeViews();
        loadDataToViews();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        ca_ad_banner.loadAd(adRequest);
    }

    private void loadDataToViews() {

        String carName = car.getName() == null ? "N/A" : car.getName();

        ca_tv_name_value.setText(carName);
        ca_tv_health_value.setText(Utils.convertIntToStringFormatter(car.getHealth()));
        ca_tv_shield_value.setText(Utils.convertIntToStringFormatter(car.getShield()));
        ca_tv_repair_value.setText(car.getTimeStringToEndRepairing());
        Bitmap houseBitmap = null;
        try {
            if (car.building == 1) houseBitmap = GameActivity.mainCityCalc.mapAreas.get(Area.BLT).bmpSrc;
            if (car.building == 2) houseBitmap = GameActivity.mainCityCalc.mapAreas.get(Area.BLC).bmpSrc;
            if (car.building == 3) houseBitmap = GameActivity.mainCityCalc.mapAreas.get(Area.BLB).bmpSrc;
            if (car.building == 4) houseBitmap = GameActivity.mainCityCalc.mapAreas.get(Area.BRT).bmpSrc;
            if (car.building == 5) houseBitmap = GameActivity.mainCityCalc.mapAreas.get(Area.BRC).bmpSrc;
            if (car.building == 6) houseBitmap = GameActivity.mainCityCalc.mapAreas.get(Area.BRB).bmpSrc;
            if (houseBitmap != null) {
                ca_iv_building_value.setImageBitmap(houseBitmap);
                ca_iv_building_value.setVisibility(View.VISIBLE);
            } else {
                ca_iv_building_value.setVisibility(View.INVISIBLE);
            }
        } catch (Exception ignored) {
        }

        CarState carState = car.getState();
        if (carState.equals(CarState.EMPTY)) {
            if (car.getSlot() == 1) {
                ca_iv_car.setImageResource(R.drawable.ic_car1_gray);
            } else if (car.getSlot() == 2) {
                ca_iv_car.setImageResource(R.drawable.ic_car2_gray);
            } else if (car.getSlot() == 3) {
                ca_iv_car.setImageResource(R.drawable.ic_car3_gray);
            }

        } else if (carState.equals(CarState.FREE)) {
            if (car.getSlot() == 1) {
                ca_iv_car.setImageResource(R.drawable.ic_car1_blue);
            } else if (car.getSlot() == 2) {
                ca_iv_car.setImageResource(R.drawable.ic_car2_blue);
            } else if (car.getSlot() == 3) {
                ca_iv_car.setImageResource(R.drawable.ic_car3_blue);
            }

        } else if (carState.equals(CarState.DEFENCING)) {
            if (car.getSlot() == 1) {
                ca_iv_car.setImageResource(R.drawable.ic_car1_green);
            } else if (car.getSlot() == 2) {
                ca_iv_car.setImageResource(R.drawable.ic_car2_green);
            } else if (car.getSlot() == 3) {
                ca_iv_car.setImageResource(R.drawable.ic_car3_green);
            }

        } else if (carState.equals(CarState.REPAIRING)) {
            if (car.getSlot() == 1) {
                ca_iv_car.setImageResource(R.drawable.ic_car1_red);
            } else if (car.getSlot() == 2) {
                ca_iv_car.setImageResource(R.drawable.ic_car2_red);
            } else if (car.getSlot() == 3) {
                ca_iv_car.setImageResource(R.drawable.ic_car3_red);
            }

        }

        car.save();

    }

    private void initializeViews() {
        ca_ad_banner = findViewById(R.id.ca_ad_banner);
        ca_iv_car_picture = findViewById(R.id.ca_iv_car_picture);
        ca_iv_car = findViewById(R.id.ca_iv_car);
        ca_tv_name_value = findViewById(R.id.ca_tv_name_value);
        ca_tv_health_value = findViewById(R.id.ca_tv_health_value);
        ca_tv_shield_value = findViewById(R.id.ca_tv_shield_value);
        ca_tv_repair_value = findViewById(R.id.ca_tv_repair_value);
        ca_iv_building_value = findViewById(R.id.ca_iv_building_value);
    }


    public void setName(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(CarActivity.this);
        builder.setTitle(R.string.name_car);
        String defaultValue = car.getName();
        final EditText input = new EditText(CarActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                car.setName(newValue);
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

    public void setHealth(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(CarActivity.this);
        builder.setTitle(R.string.health);
        String defaultValue = String.valueOf(car.getHealth());
        final EditText input = new EditText(CarActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                car.setHealth(Integer.parseInt(newValue));
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

    public void setShield(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(CarActivity.this);
        builder.setTitle(R.string.attack);
        String defaultValue = String.valueOf(car.getShield());
        final EditText input = new EditText(CarActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                car.setShield(Integer.parseInt(newValue));
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

    public void setRepair(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(CarActivity.this);
        builder.setTitle(R.string.time_reparing_hhmmss);
        String defaultValue = String.valueOf("15959");
        final EditText input = new EditText(CarActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (newValue.equals("")) newValue = "0";
                long seconds = Utils.conversTimeStringWithoutColonsToSeconds(newValue);
                car.setStateRepairingNow(seconds);
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

    public void setBuilding(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.building);

        final ListBuildingAdapter arrayAdapter = new ListBuildingAdapter(this);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Building item = arrayAdapter.getItem(which);
                car.setStateDefencing(item.getSlot());
                loadDataToViews();
            }
        });
        builder.show();

    }

    public void setFree(View view) {
        car.setStateFree();
        loadDataToViews();
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

    @Override
    public void onBackPressed() {

        car.save();
        super.onBackPressed();

    }

}