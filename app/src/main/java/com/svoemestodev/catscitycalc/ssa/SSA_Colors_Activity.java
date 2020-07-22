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

public class SSA_Colors_Activity extends AppCompatActivity {

    private static final int REQUEST_CODE_SSA_COLORS_ACTIVITY = 200;
    Button assacls_bt_add_new_color;
    ListView assacls_lv_colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_colors);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();

        displayRecords();

    }

    private void initializeViews() {
        assacls_bt_add_new_color = findViewById(R.id.assacls_bt_add_new_color);
        assacls_lv_colors = findViewById(R.id.assacls_lv_colors);
    }

    private void displayRecords() {
        assacls_lv_colors.setAdapter(new SSA_Colors_Activity.ListColorsAdapter(this));
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
        if (requestCode == REQUEST_CODE_SSA_COLORS_ACTIVITY) {
            displayRecords();
        }
    }

    public void addNewColor(View view) {

        SSA_Color ssaColor = new SSA_Color();

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Colors_Activity.this);
        builder.setTitle("Color KEY");
        String defaultValue = ssaColor.getKey();
        final EditText input = new EditText(SSA_Colors_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                SSA_Color tmp = SSA_Colors.getColor(newValue);
                if (tmp == null) {
                    ssaColor.setKey(newValue);
                    SSA_Colors.putColor(ssaColor);
                    SSA_Color_Activity.ssaColor = ssaColor;
                    Intent intent = new Intent(SSA_Colors_Activity.this, SSA_Color_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE_SSA_COLORS_ACTIVITY);
                } else {
                    Toast.makeText(SSA_Colors_Activity.this,"Цвет с таким ключом уже существует.", Toast.LENGTH_LONG).show();
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

    public void addNewColorFromArea(View view) {
        Intent intent = new Intent(SSA_Colors_Activity.this, SSA_ColorDetect_Activity.class);
        startActivityForResult(intent, REQUEST_CODE_SSA_COLORS_ACTIVITY);
    }

    private class ListColorsAdapter extends ArrayAdapter<SSA_Color> {

        public ListColorsAdapter(@NonNull Context context) {
            super(context, R.layout.layout_ssa_colors, SSA_Colors.getColorsList());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final SSA_Color ssaColor = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_colors, null);
            }

            ImageButton lssacls_bt_delete = convertView.findViewById(R.id.lssacls_bt_delete);
            ImageButton lssacls_bt_edit = convertView.findViewById(R.id.lssacls_bt_edit);
            TextView lssacls_tv_key_value = convertView.findViewById(R.id.lssacls_tv_key_value);
            TextView lssacls_tv_name_value = convertView.findViewById(R.id.lssacls_tv_name_value);
            TextView lssacls_tv_color = convertView.findViewById(R.id.lssacls_tv_color);

            String colorKey = ssaColor.getKey() == null ? "N/A" : ssaColor.getKey();
            String colorName = ssaColor.getName() == null ? "N/A" : ssaColor.getName();

            lssacls_tv_key_value.setText(colorKey);
            lssacls_tv_name_value.setText(colorName);
            lssacls_tv_color.setTextColor(ssaColor.getColor());

            lssacls_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SSA_Colors.delColor(ssaColor);
                    displayRecords();
                }
            });

            lssacls_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SSA_Color_Activity.ssaColor = ssaColor;
                    Intent intent = new Intent(SSA_Colors_Activity.this, SSA_Color_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE_SSA_COLORS_ACTIVITY);
                }
            });

            return convertView;

        }
    }
    
}