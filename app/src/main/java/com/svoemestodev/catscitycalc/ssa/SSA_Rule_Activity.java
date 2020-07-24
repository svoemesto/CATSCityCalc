package com.svoemestodev.catscitycalc.ssa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
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
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SSA_Rule_Activity extends AppCompatActivity {

    TextView assarul_tv_key_value;
    TextView assarul_tv_name_value;
    ListView assarul_lv_list_true;
    ListView assarul_lv_list_false;
    ListView assarul_lv_list_one_true;
    ListView assarul_lv_list_any_true;


    public static SSA_Rule ssaRule;
    public static SSA_Screenshot ssaScreenshot;

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
    public void onBackPressed() {

        super.onBackPressed();
        SSA_Rules.putRule(ssaRule);


    }

    private void initializeViews() {

        assarul_tv_key_value = findViewById(R.id.assarul_tv_key_value);
        assarul_tv_name_value = findViewById(R.id.assarul_tv_name_value);
        assarul_lv_list_true = findViewById(R.id.assarul_lv_list_true);
        assarul_lv_list_false = findViewById(R.id.assarul_lv_list_false);
        assarul_lv_list_one_true = findViewById(R.id.assarul_lv_list_one_true);
        assarul_lv_list_any_true = findViewById(R.id.assarul_lv_list_any_true);

    }

    private void loadDataToViews() {

        String areaKey = ssaRule.getKey() == null ? "N/A" : ssaRule.getKey();
        String areaName = ssaRule.getName() == null ? "N/A" : ssaRule.getName();
        assarul_tv_key_value.setText(areaKey);
        assarul_tv_name_value.setText(areaName);

        assarul_lv_list_true.setAdapter(new SSA_RACListTrueAdapter(SSA_Rule_Activity.this, ssaRule.getListTrue()));
        assarul_lv_list_false.setAdapter(new SSA_RACListFalseAdapter(SSA_Rule_Activity.this, ssaRule.getListFalse()));
        assarul_lv_list_one_true.setAdapter(new SSA_RACListOneTrueAdapter(SSA_Rule_Activity.this, ssaRule.getListOneTrue()));
        assarul_lv_list_any_true.setAdapter(new SSA_RACListAnyTrueAdapter(SSA_Rule_Activity.this, ssaRule.getListAnyTrue()));

    }

    private void deleteRACfromListTrue(SSA_Rule_Area_Condition ssaRuleAreaCondition) {
        List<SSA_Rule_Area_Condition> listSource = ssaRule.getListTrue();
        List<SSA_Rule_Area_Condition> listResult = new ArrayList<>();
        for (SSA_Rule_Area_Condition item: listSource) {
            if (!item.getKey().equals(ssaRuleAreaCondition.getKey())) {
                listResult.add(item.getClone());
            }
        }
        ssaRule.setListTrue(listResult);
        assarul_lv_list_true.setAdapter(new SSA_RACListTrueAdapter(SSA_Rule_Activity.this, ssaRule.getListTrue()));
    }

    private void deleteRACfromListFalse(SSA_Rule_Area_Condition ssaRuleAreaCondition) {
        List<SSA_Rule_Area_Condition> listSource = ssaRule.getListFalse();
        List<SSA_Rule_Area_Condition> listResult = new ArrayList<>();
        for (SSA_Rule_Area_Condition item: listSource) {
            if (!item.getKey().equals(ssaRuleAreaCondition.getKey())) {
                listResult.add(item.getClone());
            }
        }
        ssaRule.setListFalse(listResult);
        assarul_lv_list_false.setAdapter(new SSA_RACListFalseAdapter(SSA_Rule_Activity.this, ssaRule.getListFalse()));
    }

    private void deleteRACfromListOneTrue(SSA_Rule_Area_Condition ssaRuleAreaCondition) {
        List<SSA_Rule_Area_Condition> listSource = ssaRule.getListOneTrue();
        List<SSA_Rule_Area_Condition> listResult = new ArrayList<>();
        for (SSA_Rule_Area_Condition item: listSource) {
            if (!item.getKey().equals(ssaRuleAreaCondition.getKey())) {
                listResult.add(item.getClone());
            }
        }
        ssaRule.setListOneTrue(listResult);
        assarul_lv_list_one_true.setAdapter(new SSA_RACListOneTrueAdapter(SSA_Rule_Activity.this, ssaRule.getListOneTrue()));
    }

    private void deleteRACfromListAnyTrue(SSA_Rule_Area_Condition ssaRuleAreaCondition) {
        List<SSA_Rule_Area_Condition> listSource = ssaRule.getListAnyTrue();
        List<SSA_Rule_Area_Condition> listResult = new ArrayList<>();
        for (SSA_Rule_Area_Condition item: listSource) {
            if (!item.getKey().equals(ssaRuleAreaCondition.getKey())) {
                listResult.add(item.getClone());
            }
        }
        ssaRule.setListAnyTrue(listResult);
        assarul_lv_list_any_true.setAdapter(new SSA_RACListAnyTrueAdapter(SSA_Rule_Activity.this, ssaRule.getListAnyTrue()));
    }

    private class SSA_RACListTrueAdapter extends ArrayAdapter<SSA_Rule_Area_Condition> {

        public SSA_RACListTrueAdapter(@NonNull Context context, List<SSA_Rule_Area_Condition> listRACs) {
            super(context, R.layout.layout_ssa_rules_list_true, listRACs);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final SSA_Rule_Area_Condition item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_rules_list_true, null);
            }

            TextView lssarlt_tv_key_value = convertView.findViewById(R.id.lssarlt_tv_key_value);
            TextView lssarlt_tv_name_value = convertView.findViewById(R.id.lssarlt_tv_name_value);
            ImageButton lssarlt_bt_delete = convertView.findViewById(R.id.lssarlt_bt_delete);
            
            String racKey = item.getKey() == null ? "N/A" : item.getKey();
            String racName = item.getName() == null ? "N/A" : item.getName();

            lssarlt_tv_key_value.setText(racKey);
            lssarlt_tv_name_value.setText(racName);

            lssarlt_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteRACfromListTrue(item);
                }
            });
            
            return convertView;

        }

    }

    private class SSA_RACListFalseAdapter extends ArrayAdapter<SSA_Rule_Area_Condition> {

        public SSA_RACListFalseAdapter(@NonNull Context context, List<SSA_Rule_Area_Condition> listRACs) {
            super(context, R.layout.layout_ssa_rules_list_false, listRACs);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final SSA_Rule_Area_Condition item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_rules_list_false, null);
            }

            TextView lssarlf_tv_key_value = convertView.findViewById(R.id.lssarlf_tv_key_value);
            TextView lssarlf_tv_name_value = convertView.findViewById(R.id.lssarlf_tv_name_value);
            ImageButton lssarlf_bt_delete = convertView.findViewById(R.id.lssarlf_bt_delete);

            String racKey = item.getKey() == null ? "N/A" : item.getKey();
            String racName = item.getName() == null ? "N/A" : item.getName();

            lssarlf_tv_key_value.setText(racKey);
            lssarlf_tv_name_value.setText(racName);

            lssarlf_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteRACfromListFalse(item);
                }
            });

            return convertView;

        }

    }

    private class SSA_RACListOneTrueAdapter extends ArrayAdapter<SSA_Rule_Area_Condition> {

        public SSA_RACListOneTrueAdapter(@NonNull Context context, List<SSA_Rule_Area_Condition> listRACs) {
            super(context, R.layout.layout_ssa_rules_list_one_true, listRACs);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final SSA_Rule_Area_Condition item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_rules_list_one_true, null);
            }

            TextView lssarlot_tv_key_value = convertView.findViewById(R.id.lssarlot_tv_key_value);
            TextView lssarlot_tv_name_value = convertView.findViewById(R.id.lssarlot_tv_name_value);
            ImageButton lssarlot_bt_delete = convertView.findViewById(R.id.lssarlot_bt_delete);

            String racKey = item.getKey() == null ? "N/A" : item.getKey();
            String racName = item.getName() == null ? "N/A" : item.getName();

            lssarlot_tv_key_value.setText(racKey);
            lssarlot_tv_name_value.setText(racName);

            lssarlot_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteRACfromListOneTrue(item);
                }
            });

            return convertView;

        }

    }

    private class SSA_RACListAnyTrueAdapter extends ArrayAdapter<SSA_Rule_Area_Condition> {

        public SSA_RACListAnyTrueAdapter(@NonNull Context context, List<SSA_Rule_Area_Condition> listRACs) {
            super(context, R.layout.layout_ssa_rules_list_any_true, listRACs);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final SSA_Rule_Area_Condition item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_rules_list_any_true, null);
            }

            TextView lssarlat_tv_key_value = convertView.findViewById(R.id.lssarlat_tv_key_value);
            TextView lssarlat_tv_name_value = convertView.findViewById(R.id.lssarlat_tv_name_value);
            ImageButton lssarlat_bt_delete = convertView.findViewById(R.id.lssarlat_bt_delete);

            String racKey = item.getKey() == null ? "N/A" : item.getKey();
            String racName = item.getName() == null ? "N/A" : item.getName();

            lssarlat_tv_key_value.setText(racKey);
            lssarlat_tv_name_value.setText(racName);

            lssarlat_bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteRACfromListAnyTrue(item);
                }
            });

            return convertView;

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_rule);
        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }
        ssaScreenshot = SSA_Buttons_Activity.ssaScreenshot;
        initializeViews();
        loadDataToViews();

    }

    public void setKey(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Rule_Activity.this);
        builder.setTitle("Area KEY");
        String defaultValue = ssaRule.getKey();
        final EditText input = new EditText(SSA_Rule_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (!newValue.equals(defaultValue)) {
                    SSA_Rule tmp = SSA_Rules.getRule(newValue);
                    if (tmp == null) {
                        SSA_Rules.delRule(ssaRule);
                        ssaRule.setKey(newValue);
                        SSA_Rules.putRule(ssaRule);
                        loadDataToViews();
                    } else {
                        Toast.makeText(SSA_Rule_Activity.this,"Правило с таким ключом уже существует.", Toast.LENGTH_LONG).show();
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

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Rule_Activity.this);
        builder.setTitle("Rule name");
        String defaultValue = ssaRule.getName();
        final EditText input = new EditText(SSA_Rule_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (!newValue.equals(defaultValue)) {
                    SSA_Rule tmp = SSA_Rules.getRule(newValue);
                    if (tmp == null) {
                        ssaRule.setName(newValue);
                        loadDataToViews();
                    } else {
                        Toast.makeText(SSA_Rule_Activity.this,"Правило с таким именем уже существует.", Toast.LENGTH_LONG).show();
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

    public void doAddRACToListTrue(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Rule_Activity.this);
        builder.setCancelable(true);
        builder.setTitle("RACs");

        final SSA_RACListAdapter arrayAdapter = new SSA_RACListAdapter(SSA_Rule_Activity.this, SSA_Rules_Area_Condition.getRulesAreaConditionList());
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SSA_Rule_Area_Condition ssaRAC = arrayAdapter.getItem(which);
                List<SSA_Rule_Area_Condition> list = ssaRule.getListTrue();
                boolean isFind = false;
                for (SSA_Rule_Area_Condition temp: list) {
                    if (temp.getKey().equals(ssaRAC.getKey())) {
                        isFind = true;
                        break;
                    }
                }
                if (!isFind) {
                    list.add(ssaRAC);
                    ssaRule.setListTrue(list);
                    assarul_lv_list_true.setAdapter(new SSA_RACListTrueAdapter(SSA_Rule_Activity.this, ssaRule.getListTrue()));
                }

            }
        });
        builder.show();

    }

    public void doAddRACToListFalse(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Rule_Activity.this);
        builder.setCancelable(true);
        builder.setTitle("RACs");

        final SSA_RACListAdapter arrayAdapter = new SSA_RACListAdapter(SSA_Rule_Activity.this, SSA_Rules_Area_Condition.getRulesAreaConditionList());
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SSA_Rule_Area_Condition ssaRAC = arrayAdapter.getItem(which);
                List<SSA_Rule_Area_Condition> list = ssaRule.getListFalse();
                boolean isFind = false;
                for (SSA_Rule_Area_Condition temp: list) {
                    if (temp.getKey().equals(ssaRAC.getKey())) {
                        isFind = true;
                        break;
                    }
                }
                if (!isFind) {
                    list.add(ssaRAC);
                    ssaRule.setListFalse(list);
                    assarul_lv_list_false.setAdapter(new SSA_RACListFalseAdapter(SSA_Rule_Activity.this, ssaRule.getListFalse()));
                }

            }
        });
        builder.show();
        
    }

    public void doAddRACToListOneTrue(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Rule_Activity.this);
        builder.setCancelable(true);
        builder.setTitle("RACs");

        final SSA_RACListAdapter arrayAdapter = new SSA_RACListAdapter(SSA_Rule_Activity.this, SSA_Rules_Area_Condition.getRulesAreaConditionList());
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SSA_Rule_Area_Condition ssaRAC = arrayAdapter.getItem(which);
                List<SSA_Rule_Area_Condition> list = ssaRule.getListOneTrue();
                boolean isFind = false;
                for (SSA_Rule_Area_Condition temp: list) {
                    if (temp.getKey().equals(ssaRAC.getKey())) {
                        isFind = true;
                        break;
                    }
                }
                if (!isFind) {
                    list.add(ssaRAC);
                    ssaRule.setListOneTrue(list);
                    assarul_lv_list_one_true.setAdapter(new SSA_RACListOneTrueAdapter(SSA_Rule_Activity.this, ssaRule.getListOneTrue()));
                }

            }
        });
        builder.show();
        
    }

    public void doAddRACToListAnyTrue(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Rule_Activity.this);
        builder.setCancelable(true);
        builder.setTitle("RACs");

        final SSA_RACListAdapter arrayAdapter = new SSA_RACListAdapter(SSA_Rule_Activity.this, SSA_Rules_Area_Condition.getRulesAreaConditionList());
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SSA_Rule_Area_Condition ssaRAC = arrayAdapter.getItem(which);
                List<SSA_Rule_Area_Condition> list = ssaRule.getListAnyTrue();
                boolean isFind = false;
                for (SSA_Rule_Area_Condition temp: list) {
                    if (temp.getKey().equals(ssaRAC.getKey())) {
                        isFind = true;
                        break;
                    }
                }
                if (!isFind) {
                    list.add(ssaRAC);
                    ssaRule.setListAnyTrue(list);
                    assarul_lv_list_any_true.setAdapter(new SSA_RACListAnyTrueAdapter(SSA_Rule_Activity.this, ssaRule.getListAnyTrue()));
                }

            }
        });
        builder.show();
        
    }
}