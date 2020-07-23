package com.svoemestodev.catscitycalc.ssa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SSA_Condition_Activity extends AppCompatActivity {

    ProgressBar assacnd_pb_progress;
    TextView assacnd_tv_key_value;
    TextView assacnd_tv_name_value;
    TextView assacnd_tv_color;
    TextView assacnd_tv_color_key_value;
    TextView assacnd_tv_color_name_value;
    TextView assacnd_tv_threshold_value;
    SeekBar assacnd_sb_threshold;
    TextView assacnd_tv_min_freq_value;
    SeekBar assacnd_sb_min_freq;
    TextView assacnd_tv_max_freq_value;
    SeekBar assacnd_sb_max_freq;
    ListView assacnd_lv_areas;

    Button assacnd_bt_threshold_minus;
    Button assacnd_bt_threshold_plus;
    Button assacnd_bt_min_freq_minus10;
    Button assacnd_bt_min_freq_minus1;
    Button assacnd_bt_min_freq_plus1;
    Button assacnd_bt_min_freq_plus10;
    Button assacnd_bt_max_freq_minus10;
    Button assacnd_bt_max_freq_minus1;
    Button assacnd_bt_max_freq_plus1;
    Button assacnd_bt_max_freq_plus10;

    public static SSA_Condition ssaCondition;
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
        SSA_Conditions.putCondition(ssaCondition);


    }

    private void initializeViews() {

        assacnd_pb_progress = findViewById(R.id.assacnd_pb_progress);
        assacnd_tv_key_value = findViewById(R.id.assacnd_tv_key_value);
        assacnd_tv_name_value = findViewById(R.id.assacnd_tv_name_value);
        assacnd_tv_color = findViewById(R.id.assacnd_tv_color);
        assacnd_tv_color_key_value = findViewById(R.id.assacnd_tv_color_key_value);
        assacnd_tv_color_name_value = findViewById(R.id.assacnd_tv_color_name_value);
        assacnd_tv_threshold_value = findViewById(R.id.assacnd_tv_threshold_value);
        assacnd_sb_threshold = findViewById(R.id.assacnd_sb_threshold);
        assacnd_tv_min_freq_value = findViewById(R.id.assacnd_tv_min_freq_value);
        assacnd_sb_min_freq = findViewById(R.id.assacnd_sb_min_freq);
        assacnd_tv_max_freq_value = findViewById(R.id.assacnd_tv_max_freq_value);
        assacnd_sb_max_freq = findViewById(R.id.assacnd_sb_max_freq);
        assacnd_lv_areas = findViewById(R.id.assacnd_lv_areas);
        assacnd_bt_threshold_minus = findViewById(R.id.assacnd_bt_threshold_minus);
        assacnd_bt_threshold_plus = findViewById(R.id.assacnd_bt_threshold_plus);
        assacnd_bt_min_freq_minus10 = findViewById(R.id.assacnd_bt_min_freq_minus10);
        assacnd_bt_min_freq_minus1 = findViewById(R.id.assacnd_bt_min_freq_minus1);
        assacnd_bt_min_freq_plus1 = findViewById(R.id.assacnd_bt_min_freq_plus1);
        assacnd_bt_min_freq_plus10 = findViewById(R.id.assacnd_bt_min_freq_plus10);
        assacnd_bt_max_freq_minus10 = findViewById(R.id.assacnd_bt_max_freq_minus10);
        assacnd_bt_max_freq_minus1 = findViewById(R.id.assacnd_bt_max_freq_minus1);
        assacnd_bt_max_freq_plus1 = findViewById(R.id.assacnd_bt_max_freq_plus1);
        assacnd_bt_max_freq_plus10 = findViewById(R.id.assacnd_bt_max_freq_plus10);

    }

    private void loadDataToViews() {

        String areaKey = ssaCondition.getKey() == null ? "N/A" : ssaCondition.getKey();
        String areaName = ssaCondition.getName() == null ? "N/A" : ssaCondition.getName();
        assacnd_tv_key_value.setText(areaKey);
        assacnd_tv_name_value.setText(areaName);

        assacnd_tv_color.setTextColor(ssaCondition.getSsaColor().getColor());
        assacnd_tv_color_key_value.setText(ssaCondition.getSsaColor().getKey());
        assacnd_tv_color_name_value.setText(ssaCondition.getSsaColor().getName());
        assacnd_tv_threshold_value.setText(String.valueOf(ssaCondition.getThreshold()));
        assacnd_sb_threshold.setProgress(ssaCondition.getThreshold());
        assacnd_tv_min_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMinFrequency()));
        assacnd_sb_min_freq.setProgress((int)(ssaCondition.getMinFrequency()*100));
        assacnd_tv_max_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMaxFrequency()));
        assacnd_sb_max_freq.setProgress((int)(ssaCondition.getMaxFrequency()*100));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_condition);
        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }
        ssaScreenshot = SSA_Buttons_Activity.ssaScreenshot;
        initializeViews();
        loadDataToViews();

        assacnd_tv_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Condition_Activity.this);
                builder.setCancelable(true);
                builder.setTitle("Colors");

                final SSA_ColorsListAdapter arrayAdapter = new SSA_ColorsListAdapter(SSA_Condition_Activity.this);
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SSA_Color ssaColor = arrayAdapter.getItem(which);
                        ssaCondition.setSsaColor(ssaColor);
                        assacnd_tv_color.setTextColor(ssaCondition.getSsaColor().getColor());
                        assacnd_tv_color_key_value.setText(ssaCondition.getSsaColor().getKey());
                        assacnd_tv_color_name_value.setText(ssaCondition.getSsaColor().getName());
                    }
                });
                builder.show();

            }
        });

        assacnd_sb_threshold.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ssaCondition.setThreshold(progress);
                    assacnd_tv_threshold_value.setText(String.valueOf(ssaCondition.getThreshold()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        assacnd_sb_min_freq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ssaCondition.setMinFrequency(((float)progress)/100);
                    assacnd_tv_min_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMinFrequency()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        assacnd_sb_max_freq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ssaCondition.setMaxFrequency(((float)progress)/100);
                    assacnd_tv_max_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMaxFrequency()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        assacnd_bt_threshold_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssaCondition.getThreshold() > 0) {
                    ssaCondition.setThreshold(ssaCondition.getThreshold()-1);
                    assacnd_tv_threshold_value.setText(String.valueOf(ssaCondition.getThreshold()));
                    assacnd_sb_threshold.setProgress(ssaCondition.getThreshold());
                }
            }
        });

        assacnd_bt_threshold_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssaCondition.getThreshold() < 255) {
                    ssaCondition.setThreshold(ssaCondition.getThreshold()+1);
                    assacnd_tv_threshold_value.setText(String.valueOf(ssaCondition.getThreshold()));
                    assacnd_sb_threshold.setProgress(ssaCondition.getThreshold());
                }
            }
        });

        assacnd_bt_min_freq_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssaCondition.getMinFrequency() >= 0.01) {
                    ssaCondition.setMinFrequency(ssaCondition.getMinFrequency()-0.01f);
                    assacnd_tv_min_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMinFrequency()));
                    assacnd_sb_min_freq.setProgress((int)(ssaCondition.getMinFrequency()*100));
                }
            }
        });

        assacnd_bt_min_freq_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssaCondition.getMinFrequency() >= 0.001) {
                    ssaCondition.setMinFrequency(ssaCondition.getMinFrequency()-0.001f);
                    assacnd_tv_min_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMinFrequency()));
                    assacnd_sb_min_freq.setProgress((int)(ssaCondition.getMinFrequency()*100));
                }
            }
        });

        assacnd_bt_min_freq_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssaCondition.getMinFrequency() <= 0.999) {
                    ssaCondition.setMinFrequency(ssaCondition.getMinFrequency()+0.001f);
                    assacnd_tv_min_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMinFrequency()));
                    assacnd_sb_min_freq.setProgress((int)(ssaCondition.getMinFrequency()*100));
                }
            }
        });

        assacnd_bt_min_freq_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssaCondition.getMinFrequency() <= 0.99) {
                    ssaCondition.setMinFrequency(ssaCondition.getMinFrequency()+0.01f);
                    assacnd_tv_min_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMinFrequency()));
                    assacnd_sb_min_freq.setProgress((int)(ssaCondition.getMinFrequency()*100));
                }
            }
        });

        assacnd_bt_max_freq_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssaCondition.getMaxFrequency() >= 0.01) {
                    ssaCondition.setMaxFrequency(ssaCondition.getMaxFrequency()-0.01f);
                    assacnd_tv_max_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMaxFrequency()));
                    assacnd_sb_max_freq.setProgress((int)(ssaCondition.getMaxFrequency()*100));
                }
            }
        });

        assacnd_bt_max_freq_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssaCondition.getMaxFrequency() >= 0.001) {
                    ssaCondition.setMaxFrequency(ssaCondition.getMaxFrequency()-0.001f);
                    assacnd_tv_max_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMaxFrequency()));
                    assacnd_sb_max_freq.setProgress((int)(ssaCondition.getMaxFrequency()*100));
                }
            }
        });

        assacnd_bt_max_freq_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssaCondition.getMaxFrequency() <= 0.999) {
                    ssaCondition.setMaxFrequency(ssaCondition.getMaxFrequency()+0.001f);
                    assacnd_tv_max_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMaxFrequency()));
                    assacnd_sb_max_freq.setProgress((int)(ssaCondition.getMaxFrequency()*100));
                }
            }
        });

        assacnd_bt_max_freq_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssaCondition.getMaxFrequency() <= 0.99) {
                    ssaCondition.setMaxFrequency(ssaCondition.getMaxFrequency()+0.01f);
                    assacnd_tv_max_freq_value.setText(Utils.convertFloatToStringFormatter3digit(ssaCondition.getMaxFrequency()));
                    assacnd_sb_max_freq.setProgress((int)(ssaCondition.getMaxFrequency()*100));
                }
            }
        });


    }

    public void setKey(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Condition_Activity.this);
        builder.setTitle("Area KEY");
        String defaultValue = ssaCondition.getKey();
        final EditText input = new EditText(SSA_Condition_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (!newValue.equals(defaultValue)) {
                    SSA_Condition tmp = SSA_Conditions.getCondition(newValue);
                    if (tmp == null) {
                        SSA_Conditions.delCondition(ssaCondition);
                        ssaCondition.setKey(newValue);
                        SSA_Conditions.putCondition(ssaCondition);
                        loadDataToViews();
                    } else {
                        Toast.makeText(SSA_Condition_Activity.this,"Условие с таким ключом уже существует.", Toast.LENGTH_LONG).show();
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

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Condition_Activity.this);
        builder.setTitle("Condition name");
        String defaultValue = ssaCondition.getName();
        final EditText input = new EditText(SSA_Condition_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (!newValue.equals(defaultValue)) {
                    SSA_Condition tmp = SSA_Conditions.getCondition(newValue);
                    if (tmp == null) {
                        ssaCondition.setName(newValue);
                        loadDataToViews();
                    } else {
                        Toast.makeText(SSA_Condition_Activity.this,"Условие с таким именем уже существует.", Toast.LENGTH_LONG).show();
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

    public void showAreasSatisfiedCondition(View view) {
        GetListAreasSatisfiedCondition thread = new GetListAreasSatisfiedCondition();
        thread.execute();
    }

    private List<SSA_Area> getListAreasSatisfiedCondition() {
        List<SSA_Area> sourceList = SSA_Areas.getAreasList();
        List<SSA_Area> resultList = new ArrayList<>();
        for (SSA_Area ssaArea: sourceList) {
            if (ssaArea.isSatisfiesCondition(ssaScreenshot, ssaCondition)) resultList.add(ssaArea);
        }
        return resultList;
    }

    class GetListAreasSatisfiedCondition extends AsyncTask<Void, Integer, Void> {

        List<SSA_Area> sourceList = new ArrayList<>();
        List<SSA_Area> resultList = new ArrayList<>();

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sourceList = SSA_Areas.getAreasList();
            assacnd_pb_progress.setMin(0);
            assacnd_pb_progress.setMax(sourceList.size());
            assacnd_pb_progress.setProgress(0);
            assacnd_pb_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            assacnd_pb_progress.setVisibility(View.INVISIBLE);
            assacnd_lv_areas.setAdapter(new SSA_AreasListAdapter( SSA_Condition_Activity.this, resultList));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            assacnd_pb_progress.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < sourceList.size(); i++) {
                publishProgress(i);
                SSA_Area ssaArea = sourceList.get(i);
                if (ssaArea.isSatisfiesCondition(ssaScreenshot, ssaCondition)) resultList.add(ssaArea);
            }
            return null;
        }
    }


}