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

public class SSA_Rules_Activity extends AppCompatActivity {

    private static final int REQUEST_CODE_SSA_RULES_ACTIVITY = 300;
    Button assar_bt_add_new_rule;
    ListView assar_lv_rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_rules);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();

        displayRecords();

    }

    private void initializeViews() {
        assar_bt_add_new_rule = findViewById(R.id.assar_bt_add_new_rule);
        assar_lv_rules = findViewById(R.id.assar_lv_rules);
    }

    private void displayRecords() {
        assar_lv_rules.setAdapter(new ListRulesAdapter(this));
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
        if (requestCode == REQUEST_CODE_SSA_RULES_ACTIVITY) {
            displayRecords();
        }
    }

    public void addNewRule(View view) {

        SSA_Rule ssaRule = new SSA_Rule();

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Rules_Activity.this);
        builder.setTitle("rule KEY");
        String defaultValue = ssaRule.getKey();
        final EditText input = new EditText(SSA_Rules_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();

                SSA_Rule tmp = SSA_Rules.getRule(newValue);
                if (tmp == null) {
                    ssaRule.setKey(newValue);
                    ssaRule.setName(newValue);
                    SSA_Rules.putRule(ssaRule);
                    SSA_Rule_Activity.ssaRule = ssaRule;
                    Intent intent = new Intent(SSA_Rules_Activity.this, SSA_Rule_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE_SSA_RULES_ACTIVITY);
                } else {
                    Toast.makeText(SSA_Rules_Activity.this,"Правило с таким ключом уже существует.", Toast.LENGTH_LONG).show();
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

    private class ListRulesAdapter extends ArrayAdapter<SSA_Rule> {

        public ListRulesAdapter(@NonNull Context context) {
            super(context, R.layout.layout_ssa_rules, SSA_Rules.getRulesList());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final SSA_Rule ssaRule = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_rules, null);
            }

            ImageButton lssar_bt_delete = convertView.findViewById(R.id.lssar_bt_delete);
            ImageButton lssar_bt_edit = convertView.findViewById(R.id.lssar_bt_edit);
            TextView lssar_tv_key_value = convertView.findViewById(R.id.lssar_tv_key_value);
            TextView lssar_tv_name_value = convertView.findViewById(R.id.lssar_tv_name_value);

            String ruleKey = ssaRule.getKey() == null ? "N/A" : ssaRule.getKey();
            String ruleName = ssaRule.getName() == null ? "N/A" : ssaRule.getName();

            lssar_tv_key_value.setText(ruleKey);
            lssar_tv_name_value.setText(ruleName);

            lssar_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SSA_Rules.delRule(ssaRule);
                    displayRecords();
                }
            });

            lssar_bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SSA_Rule_Activity.ssaRule = ssaRule;
                    Intent intent = new Intent(SSA_Rules_Activity.this, SSA_Rule_Activity.class);
                    startActivityForResult(intent, REQUEST_CODE_SSA_RULES_ACTIVITY);
                }
            });

            return convertView;

        }
    }

}