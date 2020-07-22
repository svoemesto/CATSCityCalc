package com.svoemestodev.catscitycalc.ssa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.utils.Utils;

public class SSA_Color_Activity extends AppCompatActivity {

    public static SSA_Color ssaColor;

    ImageButton assaclse_ib_get_key;
    TextView assaclse_tv_key_value;
    ImageButton assaclse_ib_get_name;
    TextView assaclse_tv_name_value;
    ImageButton assaclse_ib_get_value;
    TextView assaclse_tv_color_value;
    TextView assaclse_tv_color;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_color);
        
        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }
        
        initializeViews();
        loadDataToViews();
        
    }

    private void loadDataToViews() {
        String colorKey = ssaColor.getKey() == null ? "N/A" : ssaColor.getKey();
        String colorName = ssaColor.getName() == null ? "N/A" : ssaColor.getName();
        String colorValue = Integer.toHexString(ssaColor.getColor()).toUpperCase();
        assaclse_tv_key_value.setText(colorKey);
        assaclse_tv_name_value.setText(colorName);
        assaclse_tv_color_value.setText(colorValue);
        assaclse_tv_color.setTextColor(ssaColor.getColor());
    }
    
    private void initializeViews() {
        assaclse_ib_get_key = findViewById(R.id.assaclse_ib_get_key);
        assaclse_tv_key_value = findViewById(R.id.assaclse_tv_key_value);
        assaclse_ib_get_name = findViewById(R.id.assaclse_ib_get_name);
        assaclse_tv_name_value = findViewById(R.id.assaclse_tv_name_value);
        assaclse_ib_get_value = findViewById(R.id.assaclse_ib_get_value);
        assaclse_tv_color_value = findViewById(R.id.assaclse_tv_color_value);
        assaclse_tv_color = findViewById(R.id.assaclse_tv_color);
    }

    
    public void setKey(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Color_Activity.this);
        builder.setTitle("Color KEY");
        String defaultValue = ssaColor.getKey();
        final EditText input = new EditText(SSA_Color_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (!newValue.equals(defaultValue)) {
                    SSA_Color tmp = SSA_Colors.getColor(newValue);
                    if (tmp == null) {
                        SSA_Colors.delColor(ssaColor);
                        ssaColor.setKey(newValue);
                        SSA_Colors.putColor(ssaColor);
                        loadDataToViews();
                    } else {
                        Toast.makeText(SSA_Color_Activity.this,"Цвет с таким ключом уже существует.", Toast.LENGTH_LONG).show();
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

    public void setName(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Color_Activity.this);
        builder.setTitle("Color name");
        String defaultValue = ssaColor.getName();
        final EditText input = new EditText(SSA_Color_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (!newValue.equals(defaultValue)) {
                    ssaColor.setName(newValue);
                    loadDataToViews();
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

    public void setValue(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Color_Activity.this);
        builder.setTitle("Color HEX value");
        String defaultValue = Integer.toHexString(ssaColor.getColor()).toUpperCase();
        final EditText input = new EditText(SSA_Color_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { ;
                try {
                    int newValue = (int)Long.parseLong(input.getText().toString(), 16);
                    if (newValue != ssaColor.getColor()) {
                        ssaColor.setColor(newValue);
                        loadDataToViews();
                    }
                } catch (NumberFormatException ignored) { ;
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
        SSA_Colors.putColor(ssaColor);
        super.onBackPressed();
    }
    
}