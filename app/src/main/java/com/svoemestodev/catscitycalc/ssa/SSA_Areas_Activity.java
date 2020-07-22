package com.svoemestodev.catscitycalc.ssa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

import com.svoemestodev.catscitycalc.R;

public class SSA_Areas_Activity extends AppCompatActivity {

    private static final int REQUEST_CODE_SSA_AREAS_ACTIVITY = 100;
    Button assaa_bt_add_new_area;
    ListView assaa_lv_areas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_areas);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();

        displayRecords();

    }

    private void initializeViews() {
        assaa_bt_add_new_area = findViewById(R.id.assaa_bt_add_new_area);
        assaa_lv_areas = findViewById(R.id.assaa_lv_areas);
    }

    private void displayRecords() {
        assaa_lv_areas.setAdapter(new ListAreasAdapter(this));
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
        if (requestCode == REQUEST_CODE_SSA_AREAS_ACTIVITY) {
            displayRecords();
        }
    }

    public void addNewArea(View view) {

        SSA_Area ssaArea = new SSA_Area();

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Areas_Activity.this);
        builder.setTitle("Area KEY");
        String defaultValue = ssaArea.getKey();
        final EditText input = new EditText(SSA_Areas_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();

                SSA_Area tmp = SSA_Areas.getArea(newValue);
                if (tmp == null) {
                    ssaArea.setKey(newValue);

                    int width = SSA_Buttons_Activity.ssaScreenshot.getBitmap().getWidth() - Math.abs(SSA_Buttons_Activity.ssaScreenshot.getCenterX()*2);
                    int height = SSA_Buttons_Activity.ssaScreenshot.getBitmap().getHeight() - Math.abs(SSA_Buttons_Activity.ssaScreenshot.getCenterY()*2);
                    double aspect = (double)width / (double)height;
                    double delta = Math.ceil((aspect / 2) * 1000)/1000;
                    ssaArea.setrX1(-delta);
                    ssaArea.setrX2(+delta);
                    ssaArea.setrY1(-1.0);
                    ssaArea.setrY2(+1.0);
                    SSA_Areas.putArea(ssaArea);
                    SSA_Area_Activity.ssaArea = ssaArea;
                    Intent intent = new Intent(SSA_Areas_Activity.this, SSA_Area_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE_SSA_AREAS_ACTIVITY);
                } else {
                    Toast.makeText(SSA_Areas_Activity.this,"Область с таким ключом уже существует.", Toast.LENGTH_LONG).show();
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

    private class ListAreasAdapter extends ArrayAdapter<SSA_Area> {

        public ListAreasAdapter(@NonNull Context context) {
            super(context, R.layout.layout_ssa_areas, SSA_Areas.getAreasList());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final SSA_Area ssaArea = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_areas, null);
            }

            ImageButton lssaa_bt_delete = convertView.findViewById(R.id.lssaa_bt_delete);
            ImageButton lssaa_bt_edit = convertView.findViewById(R.id.lssaa_bt_edit);
            TextView lssaa_tv_key_value = convertView.findViewById(R.id.lssaa_tv_key_value);
            TextView lssaa_tv_name_value = convertView.findViewById(R.id.lssaa_tv_name_value);

            String areaKey = ssaArea.getKey() == null ? "N/A" : ssaArea.getKey();
            String areaName = ssaArea.getName() == null ? "N/A" : ssaArea.getName();

            lssaa_tv_key_value.setText(areaKey);
            lssaa_tv_name_value.setText(areaName);

            lssaa_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SSA_Areas.delArea(ssaArea);
                    displayRecords();
                }
            });

            lssaa_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SSA_Area_Activity.ssaArea = ssaArea;
                    Intent intent = new Intent(SSA_Areas_Activity.this, SSA_Area_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE_SSA_AREAS_ACTIVITY);
                }
            });

            return convertView;

        }
    }

}