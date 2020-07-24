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
import com.svoemestodev.catscitycalc.utils.Utils;

public class SSA_Rules_Area_Condition_Activity extends AppCompatActivity {

    private static final int REQUEST_CODE_SSA_RAC = 400;
    Button assarac_bt_add_new_rac;
    ListView assarac_lv_rac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_racs);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();

        displayRecords();

    }

    private void initializeViews() {
        assarac_bt_add_new_rac = findViewById(R.id.assarac_bt_add_new_rac);
        assarac_lv_rac = findViewById(R.id.assarac_lv_rac);
    }

    private void displayRecords() {
        assarac_lv_rac.setAdapter(new ListRACAdapter(this));
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
        if (requestCode == REQUEST_CODE_SSA_RAC) {
            displayRecords();
        }
    }

    public void addNewRAC(View view) {

        SSA_Rule_Area_Condition ssaRAC = new SSA_Rule_Area_Condition();

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Rules_Area_Condition_Activity.this);
        builder.setTitle("RAC KEY");
        String defaultValue = ssaRAC.getKey();
        final EditText input = new EditText(SSA_Rules_Area_Condition_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();

                SSA_Rule_Area_Condition tmp = SSA_Rules_Area_Condition.getRuleAreaCondition(newValue);
                if (tmp == null) {
                    ssaRAC.setKey(newValue);
                    ssaRAC.setName(newValue);
                    SSA_Rules_Area_Condition.putRuleAreaCondition(ssaRAC);
                    SSA_Rule_Area_Condition_Activity.ssaRAC = ssaRAC;
                    Intent intent = new Intent(SSA_Rules_Area_Condition_Activity.this, SSA_Rule_Area_Condition_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE_SSA_RAC);
                } else {
                    Toast.makeText(SSA_Rules_Area_Condition_Activity.this,"RAC с таким ключом уже существует.", Toast.LENGTH_LONG).show();
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

    private class ListRACAdapter extends ArrayAdapter<SSA_Rule_Area_Condition> {

        public ListRACAdapter(@NonNull Context context) {
            super(context, R.layout.layout_ssa_racs, SSA_Rules_Area_Condition.getRulesAreaConditionList());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final SSA_Rule_Area_Condition ssaRAC = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_racs, null);
            }

            ImageButton lssarac_bt_delete = convertView.findViewById(R.id.lssarac_bt_delete);
            ImageButton lssarac_bt_edit = convertView.findViewById(R.id.lssarac_bt_edit);
            TextView lssarac_tv_key_value = convertView.findViewById(R.id.lssarac_tv_key_value);
            TextView lssarac_tv_name_value = convertView.findViewById(R.id.lssarac_tv_name_value);
            TextView lssarac_tv_color = convertView.findViewById(R.id.lssarac_tv_color);
            TextView lssarac_tv_area_key_value = convertView.findViewById(R.id.lssarac_tv_area_key_value);
            TextView lssarac_tv_condition_key_value = convertView.findViewById(R.id.lssarac_tv_condition_key_value);

            String racKey = ssaRAC.getKey() == null ? "N/A" : ssaRAC.getKey();
            String racName = ssaRAC.getName() == null ? "N/A" : ssaRAC.getName();
            String areaKey = ssaRAC.getSsaArea().getKey() == null ? "N/A" : ssaRAC.getSsaArea().getKey();
            String condKey = ssaRAC.getSsaCondition().getKey() == null ? "N/A" : ssaRAC.getSsaCondition().getKey();

            lssarac_tv_key_value.setText(racKey);
            lssarac_tv_name_value.setText(racName);
            lssarac_tv_area_key_value.setText(areaKey);
            lssarac_tv_condition_key_value.setText(condKey);
            lssarac_tv_color.setTextColor(ssaRAC.getSsaCondition().getSsaColor().getColor());

            lssarac_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SSA_Rules_Area_Condition.delRuleAreaCondition(ssaRAC);
                    displayRecords();
                }
            });

            lssarac_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SSA_Rule_Area_Condition_Activity.ssaRAC = ssaRAC;
                    Intent intent = new Intent(SSA_Rules_Area_Condition_Activity.this, SSA_Rule_Area_Condition_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE_SSA_RAC);
                }
            });

            return convertView;

        }
    }

}