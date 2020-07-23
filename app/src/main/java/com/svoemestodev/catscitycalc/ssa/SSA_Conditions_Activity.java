package com.svoemestodev.catscitycalc.ssa;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.svoemestodev.catscitycalc.R;

public class SSA_Conditions_Activity extends AppCompatActivity {

    private static final int REQUEST_CODE_SSA_CONDITIONS_ACTIVITY = 300;
    Button assac_bt_add_new_condition;
    ListView assac_lv_conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_conditions);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();

        displayRecords();

    }

    private void initializeViews() {
        assac_bt_add_new_condition = findViewById(R.id.assac_bt_add_new_condition);
        assac_lv_conditions = findViewById(R.id.assac_lv_conditions);
    }

    private void displayRecords() {
        assac_lv_conditions.setAdapter(new ListconditionsAdapter(this));
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
        if (requestCode == REQUEST_CODE_SSA_CONDITIONS_ACTIVITY) {
            displayRecords();
        }
    }

    public void addNewCondition(View view) {

        SSA_Condition ssaCondition = new SSA_Condition();

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Conditions_Activity.this);
        builder.setTitle("condition KEY");
        String defaultValue = ssaCondition.getKey();
        final EditText input = new EditText(SSA_Conditions_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();

                SSA_Condition tmp = SSA_Conditions.getCondition(newValue);
                if (tmp == null) {
                    ssaCondition.setKey(newValue);
                    ssaCondition.setName(newValue);
                    SSA_Conditions.putCondition(ssaCondition);
                    SSA_Condition_Activity.ssaCondition = ssaCondition;
                    Intent intent = new Intent(SSA_Conditions_Activity.this, SSA_Condition_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE_SSA_CONDITIONS_ACTIVITY);
                } else {
                    Toast.makeText(SSA_Conditions_Activity.this,"Условие с таким ключом уже существует.", Toast.LENGTH_LONG).show();
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

    private class ListconditionsAdapter extends ArrayAdapter<SSA_Condition> {

        public ListconditionsAdapter(@NonNull Context context) {
            super(context, R.layout.layout_ssa_conditions, SSA_Conditions.getConditionsList());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final SSA_Condition ssaCondition = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_conditions, null);
            }

            ImageButton lssac_bt_delete = convertView.findViewById(R.id.lssac_bt_delete);
            ImageButton lssac_bt_edit = convertView.findViewById(R.id.lssac_bt_edit);
            TextView lssac_tv_key_value = convertView.findViewById(R.id.lssac_tv_key_value);
            TextView lssac_tv_name_value = convertView.findViewById(R.id.lssac_tv_name_value);

            String conditionKey = ssaCondition.getKey() == null ? "N/A" : ssaCondition.getKey();
            String conditionName = ssaCondition.getName() == null ? "N/A" : ssaCondition.getName();

            lssac_tv_key_value.setText(conditionKey);
            lssac_tv_name_value.setText(conditionName);

            lssac_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SSA_Conditions.delCondition(ssaCondition);
                    displayRecords();
                }
            });

            lssac_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SSA_Condition_Activity.ssaCondition = ssaCondition;
                    Intent intent = new Intent(SSA_Conditions_Activity.this, SSA_Condition_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE_SSA_CONDITIONS_ACTIVITY);
                }
            });

            return convertView;

        }
    }

}