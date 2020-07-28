package com.svoemestodev.catscitycalc.ssa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.utils.ColorFrequency;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;
import com.svoemestodev.catscitycalc.utils.PictureProcessorDirection;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SSA_Area_Activity extends AppCompatActivity {


    TextView assaae_tv_key_value;
    TextView assaae_tv_name_value;
    TextView assaae_tv_parent_value;
    RadioGroup assaae_rg_snap;
    RadioButton assaae_rb_left;
    RadioButton assaae_rb_center;
    RadioButton assaae_rb_right;
    TextView assaae_tv_x1_value;
    TextView assaae_tv_x2_value;
    TextView assaae_tv_y1_value;
    TextView assaae_tv_y2_value;
    ImageView assaae_iv_image;
    SeekBar assaae_sb_x1;
    SeekBar assaae_sb_x2;
    SeekBar assaae_sb_y1;
    SeekBar assaae_sb_y2;
    ImageView assaae_iv_image_rbt;
    TextView assaae_tv_ocr;
    ImageView lssacrc_iv_image;
    ImageView lssarbt_iv_image;


    LinearLayout lssacrc_ll_layout;
    LinearLayout lssarbt_ll_layout;

    ImageButton assaae_ib_x1_minus10;
    ImageButton assaae_ib_x1_minus1;
    ImageButton assaae_ib_reset_x1;
    ImageButton assaae_ib_x1_plus1;
    ImageButton assaae_ib_x1_plus10;
    ImageButton assaae_ib_x2_minus10;
    ImageButton assaae_ib_x2_minus1;
    ImageButton assaae_ib_reset_x2;
    ImageButton assaae_ib_x2_plus1;
    ImageButton assaae_ib_x2_plus10;
    ImageButton assaae_ib_y1_minus10;
    ImageButton assaae_ib_y1_minus1;
    ImageButton assaae_ib_reset_y1;
    ImageButton assaae_ib_y1_plus1;
    ImageButton assaae_ib_y1_plus10;
    ImageButton assaae_ib_y2_minus10;
    ImageButton assaae_ib_y2_minus1;
    ImageButton assaae_ib_reset_y2;
    ImageButton assaae_ib_y2_plus1;
    ImageButton assaae_ib_y2_plus10;
    
    ImageButton lssacch_bt_previous;
    ImageButton lssacch_bt_next;
    ImageButton lssacch_bt_add_new;
    ImageButton lssacch_bt_delete;
    TextView lssacch_tv_index;

    Switch lssacrc_sw_use;
    RadioGroup lssacrc_rg_direction;
    RadioButton lssacrc_rb_LR;
    RadioButton lssacrc_rb_RL;
    RadioButton lssacrc_rb_TB;
    RadioButton lssacrc_rb_BT;
    CheckBox lssacrc_cb_return_first_fragment;
    CheckBox lssacrc_cb_only_first;
    ImageButton lssacrc_bt_delete;
    TextView lssacrc_tv_color_start;
    TextView lssacrc_tv_color_start_key_value;
    TextView lssacrc_tv_color_start_name_value;
    TextView lssacrc_tv_threshold_start_value;
    Button lssacrc_bt_threshold_start_minus;
    Button lssacrc_bt_threshold_start_plus;
    SeekBar lssacrc_sb_threshold_start;
    TextView lssacrc_tv_min_freq_start_value;
    Button lssacrc_bt_min_freq_start_minus10;
    Button lssacrc_bt_min_freq_start_minus1;
    Button lssacrc_bt_min_freq_start_plus1;
    Button lssacrc_bt_min_freq_start_plus10;
    SeekBar lssacrc_sb_min_freq_start;
    TextView lssacrc_tv_max_freq_start_value;
    Button lssacrc_bt_max_freq_start_minus10;
    Button lssacrc_bt_max_freq_start_minus1;
    Button lssacrc_bt_max_freq_start_plus1;
    Button lssacrc_bt_max_freq_start_plus10;
    SeekBar lssacrc_sb_max_freq_start;
    TextView lssacrc_tv_color_end;
    TextView lssacrc_tv_color_end_key_value;
    TextView lssacrc_tv_color_end_name_value;
    TextView lssacrc_tv_threshold_end_value;
    Button lssacrc_bt_threshold_end_minus;
    Button lssacrc_bt_threshold_end_plus;
    SeekBar lssacrc_sb_threshold_end;
    TextView lssacrc_tv_min_freq_end_value;
    Button lssacrc_bt_min_freq_end_minus10;
    Button lssacrc_bt_min_freq_end_minus1;
    Button lssacrc_bt_min_freq_end_plus1;
    Button lssacrc_bt_min_freq_end_plus10;
    SeekBar lssacrc_sb_min_freq_end;
    TextView lssacrc_tv_max_freq_end_value;
    Button lssacrc_bt_max_freq_end_minus10;
    Button lssacrc_bt_max_freq_end_minus1;
    Button lssacrc_bt_max_freq_end_plus1;
    Button lssacrc_bt_max_freq_end_plus10;
    SeekBar lssacrc_sb_max_freq_end;

    ImageButton lssarch_bt_previous;
    ImageButton lssarch_bt_next;
    ImageButton lssarch_bt_add_new;
    ImageButton lssarch_bt_delete;
    TextView lssarch_tv_index;

    Switch lssarbt_sw_use;
    RadioGroup lssarbt_rg_type;
    RadioButton lssarbt_rb_resize;
    RadioButton lssarbt_rb_BW;
    RadioButton lssarbt_rb_WB;
    RadioButton lssarbt_rb_transparent;
    TextView lssarbt_tv_color;
    TextView lssarbt_tv_color_key_value;
    TextView lssarbt_tv_color_name_value;
    TextView lssarbt_tv_threshold_value;
    Button lssarbt_bt_threshold_minus;
    Button lssarbt_bt_threshold_plus;
    SeekBar lssarbt_sb_threshold;
    TextView lssarbt_tv_scale_x_value;
    Button lssarbt_bt_scale_x_minus10;
    Button lssarbt_bt_scale_x_minus1;
    Button lssarbt_bt_scale_x_plus1;
    Button lssarbt_bt_scale_x_plus10;
    SeekBar lssarbt_sb_scale_x;
    TextView lssarbt_tv_scale_y_value;
    Button lssarbt_bt_scale_y_minus10;
    Button lssarbt_bt_scale_y_minus1;
    Button lssarbt_bt_scale_y_plus1;
    Button lssarbt_bt_scale_y_plus10;
    SeekBar lssarbt_sb_scale_y;

    Button assaae_bt_show_areas;
    ProgressBar assaae_pb_progress;
    TextView assaae_tv_conditions;


    public static SSA_Area ssaArea;
    int cropConditionIndex = 0;
    int rbtConditionIndex = 0;

    boolean manualChange = false;
    public static double minX, maxX, minY, maxY;
    public static SSA_Screenshot ssaScreenshot;

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
        super.onBackPressed();
        SSA_Areas.putArea(ssaArea);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_area);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        ssaScreenshot = SSA_Buttons_Activity.ssaScreenshot;
        setMinMax();
        initializeViews();
        loadDataToViews();

        assaae_rg_snap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.assaae_rb_left:
                        ssaArea.setSnap(-1);
                        setMinMax();
                        break;
                    case R.id.assaae_rb_center:
                        ssaArea.setSnap(0);
                        setMinMax();
                        break;
                    case R.id.assaae_rb_right:
                        ssaArea.setSnap(1);
                        setMinMax();
                        break;
                    default:
                }
            }
        });

        assaae_sb_x1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ssaArea.setrX1((double)(Math.ceil((progress * Math.abs((maxX-minX)/100) + minX)*1000))/1000);
                    assaae_tv_x1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX1()));
                    if (ssaArea.getrX1() >= ssaArea.getrX2()) {
                        ssaArea.setrX2(ssaArea.getrX1() + 0.001d);
                        assaae_sb_x2.setProgress((int)Math.abs((ssaArea.getrX2()-minX)/((maxX-minX)/100)));
                        assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
                    }
                    assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                    assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                    if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                    if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                    assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        assaae_sb_x2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ssaArea.setrX2((double)(Math.ceil((progress * Math.abs((maxX-minX)/100) + minX)*1000))/1000);
                    assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
                    if (ssaArea.getrX2() <= ssaArea.getrX1()) {
                        ssaArea.setrX1(ssaArea.getrX2() - 0.001d);
                        assaae_sb_x1.setProgress((int)Math.abs((ssaArea.getrX1()-minX)/((maxX-minX)/100)));
                        assaae_tv_x1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX1()));
                    }
                    assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                    assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                    if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                    if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                    assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        assaae_sb_y1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ssaArea.setrY1((double)(Math.ceil((progress * Math.abs((maxY-minY)/100) + minY)*1000))/1000);
                    assaae_tv_y1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY1()));
                    if (ssaArea.getrY1() >= ssaArea.getrY2()) {
                        ssaArea.setrY2(ssaArea.getrY1() + 0.001d);
                        assaae_sb_y2.setProgress((int)Math.abs((ssaArea.getrY2()-minY)/((maxY-minY)/100)));
                        assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
                    }
                    assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                    assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                    if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                    if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                    assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        assaae_sb_y2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ssaArea.setrY2((double)(Math.ceil((progress * Math.abs((maxY-minY)/100) + minY)*1000))/1000);
                    assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
                    if (ssaArea.getrY2() <= ssaArea.getrY1()) {
                        ssaArea.setrY1(ssaArea.getrY2() - 0.001d);
                        assaae_sb_y1.setProgress((int)Math.abs((ssaArea.getrY1()-minY)/((maxY-minY)/100)));
                        assaae_tv_y1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY1()));
                    }
                    assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                    assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                    if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                    if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                    assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        assaae_ib_x1_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = -0.01;
                double value = ssaArea.getrX1();
                value += increment;
                if (value < minX) value = minX;
                if (value > maxX) value = maxX;
                ssaArea.setrX1(value);
                assaae_tv_x1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX1()));
                assaae_sb_x1.setProgress((int)Math.abs((ssaArea.getrX1()-minX)/((maxX-minX)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_x1_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = -0.001;
                double value = ssaArea.getrX1();
                value += increment;
                if (value < minX) value = minX;
                if (value > maxX) value = maxX;
                ssaArea.setrX1(value);
                assaae_tv_x1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX1()));
                assaae_sb_x1.setProgress((int)Math.abs((ssaArea.getrX1()-minX)/((maxX-minX)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_reset_x1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = minX;
                ssaArea.setrX1(value);
                assaae_tv_x1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX1()));
                assaae_sb_x1.setProgress((int)Math.abs((ssaArea.getrX1()-minX)/((maxX-minX)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });


        assaae_ib_x1_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = +0.01;
                double value = ssaArea.getrX1();
                value += increment;
                if (value < minX) value = minX;
                if (value > maxX) value = maxX;
                ssaArea.setrX1(value);
                assaae_tv_x1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX1()));
                assaae_sb_x1.setProgress((int)Math.abs((ssaArea.getrX1()-minX)/((maxX-minX)/100)));
                if (ssaArea.getrX1() >= ssaArea.getrX2()) {
                    ssaArea.setrX2(ssaArea.getrX1() + 0.001d);
                    assaae_sb_x2.setProgress((int)Math.abs((ssaArea.getrX2()-minX)/((maxX-minX)/100)));
                    assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
                }
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_x1_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = +0.001;
                double value = ssaArea.getrX1();
                value += increment;
                if (value < minX) value = minX;
                if (value > maxX) value = maxX;
                ssaArea.setrX1(value);
                assaae_tv_x1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX1()));
                assaae_sb_x1.setProgress((int)Math.abs((ssaArea.getrX1()-minX)/((maxX-minX)/100)));
                if (ssaArea.getrX1() >= ssaArea.getrX2()) {
                    ssaArea.setrX2(ssaArea.getrX1() + 0.001d);
                    assaae_sb_x2.setProgress((int)Math.abs((ssaArea.getrX2()-minX)/((maxX-minX)/100)));
                    assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
                }
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });




        assaae_ib_x2_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = -0.01;
                double value = ssaArea.getrX2();
                value += increment;
                if (value < minX) value = minX;
                if (value > maxX) value = maxX;
                ssaArea.setrX2(value);
                assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
                assaae_sb_x2.setProgress((int)Math.abs((ssaArea.getrX2()-minX)/((maxX-minX)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_x2_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = -0.001;
                double value = ssaArea.getrX2();
                value += increment;
                if (value < minX) value = minX;
                if (value > maxX) value = maxX;
                ssaArea.setrX2(value);
                assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
                assaae_sb_x2.setProgress((int)Math.abs((ssaArea.getrX2()-minX)/((maxX-minX)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_reset_x2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = maxX;
                ssaArea.setrX2(value);
                assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
                assaae_sb_x2.setProgress((int)Math.abs((ssaArea.getrX2()-minX)/((maxX-minX)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });


        assaae_ib_x2_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = +0.01;
                double value = ssaArea.getrX2();
                value += increment;
                if (value < minX) value = minX;
                if (value > maxX) value = maxX;
                ssaArea.setrX2(value);
                assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
                assaae_sb_x2.setProgress((int)Math.abs((ssaArea.getrX2()-minX)/((maxX-minX)/100)));
                if (ssaArea.getrX2() >= ssaArea.getrX2()) {
                    ssaArea.setrX2(ssaArea.getrX2() + 0.001d);
                    assaae_sb_x2.setProgress((int)Math.abs((ssaArea.getrX2()-minX)/((maxX-minX)/100)));
                    assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
                }
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_x2_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = +0.001;
                double value = ssaArea.getrX2();
                value += increment;
                if (value < minX) value = minX;
                if (value > maxX) value = maxX;
                ssaArea.setrX2(value);
                assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
                assaae_sb_x2.setProgress((int)Math.abs((ssaArea.getrX2()-minX)/((maxX-minX)/100)));
                if (ssaArea.getrX2() >= ssaArea.getrX2()) {
                    ssaArea.setrX2(ssaArea.getrX2() + 0.001d);
                    assaae_sb_x2.setProgress((int)Math.abs((ssaArea.getrX2()-minX)/((maxX-minX)/100)));
                    assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
                }
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });



        assaae_ib_y1_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = -0.01;
                double value = ssaArea.getrY1();
                value += increment;
                if (value < minY) value = minY;
                if (value > maxY) value = maxY;
                ssaArea.setrY1(value);
                assaae_tv_y1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY1()));
                assaae_sb_y1.setProgress((int)Math.abs((ssaArea.getrY1()-minY)/((maxY-minY)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_y1_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = -0.001;
                double value = ssaArea.getrY1();
                value += increment;
                if (value < minY) value = minY;
                if (value > maxY) value = maxY;
                ssaArea.setrY1(value);
                assaae_tv_y1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY1()));
                assaae_sb_y1.setProgress((int)Math.abs((ssaArea.getrY1()-minY)/((maxY-minY)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_reset_y1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = minY;
                ssaArea.setrY1(value);
                assaae_tv_y1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY1()));
                assaae_sb_y1.setProgress((int)Math.abs((ssaArea.getrY1()-minY)/((maxY-minY)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });


        assaae_ib_y1_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = +0.01;
                double value = ssaArea.getrY1();
                value += increment;
                if (value < minY) value = minY;
                if (value > maxY) value = maxY;
                ssaArea.setrY1(value);
                assaae_tv_y1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY1()));
                assaae_sb_y1.setProgress((int)Math.abs((ssaArea.getrY1()-minY)/((maxY-minY)/100)));
                if (ssaArea.getrY1() >= ssaArea.getrY2()) {
                    ssaArea.setrY2(ssaArea.getrY1() + 0.001d);
                    assaae_sb_y2.setProgress((int)Math.abs((ssaArea.getrY2()-minY)/((maxY-minY)/100)));
                    assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
                }
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_y1_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = +0.001;
                double value = ssaArea.getrY1();
                value += increment;
                if (value < minY) value = minY;
                if (value > maxY) value = maxY;
                ssaArea.setrY1(value);
                assaae_tv_y1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY1()));
                assaae_sb_y1.setProgress((int)Math.abs((ssaArea.getrY1()-minY)/((maxY-minY)/100)));
                if (ssaArea.getrY1() >= ssaArea.getrY2()) {
                    ssaArea.setrY2(ssaArea.getrY1() + 0.001d);
                    assaae_sb_y2.setProgress((int)Math.abs((ssaArea.getrY2()-minY)/((maxY-minY)/100)));
                    assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
                }
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });




        assaae_ib_y2_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = -0.01;
                double value = ssaArea.getrY2();
                value += increment;
                if (value < minY) value = minY;
                if (value > maxY) value = maxY;
                ssaArea.setrY2(value);
                assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
                assaae_sb_y2.setProgress((int)Math.abs((ssaArea.getrY2()-minY)/((maxY-minY)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_y2_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = -0.001;
                double value = ssaArea.getrY2();
                value += increment;
                if (value < minY) value = minY;
                if (value > maxY) value = maxY;
                ssaArea.setrY2(value);
                assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
                assaae_sb_y2.setProgress((int)Math.abs((ssaArea.getrY2()-minY)/((maxY-minY)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_reset_y2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = maxY;
                ssaArea.setrY2(value);
                assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
                assaae_sb_y2.setProgress((int)Math.abs((ssaArea.getrY2()-minY)/((maxY-minY)/100)));
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });


        assaae_ib_y2_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = +0.01;
                double value = ssaArea.getrY2();
                value += increment;
                if (value < minY) value = minY;
                if (value > maxY) value = maxY;
                ssaArea.setrY2(value);
                assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
                assaae_sb_y2.setProgress((int)Math.abs((ssaArea.getrY2()-minY)/((maxY-minY)/100)));
                if (ssaArea.getrY2() >= ssaArea.getrY2()) {
                    ssaArea.setrY2(ssaArea.getrY2() + 0.001d);
                    assaae_sb_y2.setProgress((int)Math.abs((ssaArea.getrY2()-minY)/((maxY-minY)/100)));
                    assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
                }
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });

        assaae_ib_y2_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double increment = +0.001;
                double value = ssaArea.getrY2();
                value += increment;
                if (value < minY) value = minY;
                if (value > maxY) value = maxY;
                ssaArea.setrY2(value);
                assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
                assaae_sb_y2.setProgress((int)Math.abs((ssaArea.getrY2()-minY)/((maxY-minY)/100)));
                if (ssaArea.getrY2() >= ssaArea.getrY2()) {
                    ssaArea.setrY2(ssaArea.getrY2() + 0.001d);
                    assaae_sb_y2.setProgress((int)Math.abs((ssaArea.getrY2()-minY)/((maxY-minY)/100)));
                    assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
                }
                assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
                assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
                if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
                if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
                assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));
            }
        });




        lssacch_bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cropConditionIndex > 1) {
                    cropConditionIndex--;
                    showCropConditions();
                }
            }
        });

        lssacch_bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cropConditionIndex < ssaArea.getListCropConditions().size()) {
                    cropConditionIndex++;
                    showCropConditions();
                }

            }
        });

        lssacch_bt_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SSA_Crop_Condition> list = ssaArea.getListCropConditions();
                list.add(new SSA_Crop_Condition(ssaArea.getKey()));
                ssaArea.setListCropConditions(list);
                cropConditionIndex = list.size();
                showCropConditions();
            }
        });

        lssacch_bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                deleteCropCondition(item.getKey());
                cropConditionIndex--;
                if (cropConditionIndex == 0 && ssaArea.getListCropConditions().size() > 0) cropConditionIndex = 1;
                applyCropConditions(null);
            }
        });

        lssarch_bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbtConditionIndex > 1) {
                    rbtConditionIndex--;
                    showRbtConditions();
                }

            }
        });

        lssarch_bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbtConditionIndex < ssaArea.getListRBTConditions().size()) {
                    rbtConditionIndex++;
                    showRbtConditions();
                }

            }
        });

        lssarch_bt_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SSA_RBT_Condition> list = ssaArea.getListRBTConditions();
                list.add(new SSA_RBT_Condition(ssaArea.getKey()));
                ssaArea.setListRBTConditions(list);
                rbtConditionIndex = list.size();
                showRbtConditions();
            }
        });

        lssarch_bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                deleteRbtCondition(item.getKey());
                rbtConditionIndex--;
                if (rbtConditionIndex == 0 && ssaArea.getListRBTConditions().size() > 0) rbtConditionIndex = 1;
                applyRbtConditions(null);
            }
        });

        lssacrc_sw_use.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (manualChange) {
                    SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                    item.setSkip(!isChecked);
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_rg_direction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (manualChange) {
                    switch (checkedId) {
                        case R.id.lssacrc_rb_LR:
                            item.setDirection(PictureProcessorDirection.FROM_LEFT_TO_RIGHT);
                            break;
                        case R.id.lssacrc_rb_RL:
                            item.setDirection(PictureProcessorDirection.FROM_RIGHT_TO_LEFT);
                            break;
                        case R.id.lssacrc_rb_TB:
                            item.setDirection(PictureProcessorDirection.FROM_TOP_TO_BOTTOM);
                            break;
                        case R.id.lssacrc_rb_BT:
                            item.setDirection(PictureProcessorDirection.FROM_BOTTOM_TO_TOP);
                            break;
                        default:
                    }
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_cb_return_first_fragment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (manualChange) {
                    item.setReturnFirstFragment(isChecked);
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_cb_only_first.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (manualChange) {
                    item.setOnlyFirst(isChecked);
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_tv_color_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cropConditionIndex != 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Area_Activity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Colors");

                    final SSA_ColorsListAdapter arrayAdapter = new SSA_ColorsListAdapter(SSA_Area_Activity.this);
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex - 1);
                            SSA_Color ssaColor = arrayAdapter.getItem(which);
                            item.setSsaColorStart(ssaColor);
                            lssacrc_tv_color_start.setTextColor(item.getSsaColorStart().getColor());
                            lssacrc_tv_color_start_key_value.setText(item.getSsaColorStart().getKey());
                            lssacrc_tv_color_start_name_value.setText(item.getSsaColorStart().getName());
                            applyCropConditions(item);
                        }
                    });
                    builder.show();
                }
            }
        });

        lssacrc_tv_color_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cropConditionIndex != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Area_Activity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Colors");

                    final SSA_ColorsListAdapter arrayAdapter = new SSA_ColorsListAdapter(SSA_Area_Activity.this);
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex - 1);
                            SSA_Color ssaColor = arrayAdapter.getItem(which);
                            item.setSsaColorEnd(ssaColor);
                            lssacrc_tv_color_end.setTextColor(item.getSsaColorEnd().getColor());
                            lssacrc_tv_color_end_key_value.setText(item.getSsaColorEnd().getKey());
                            lssacrc_tv_color_end_name_value.setText(item.getSsaColorEnd().getName());
                            applyCropConditions(item);
                        }
                    });
                    builder.show();
                }
            }
        });

        lssacrc_sb_threshold_start.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                    item.setThresholdStart(progress);
                    lssacrc_tv_threshold_start_value.setText(String.valueOf(item.getThresholdStart()));
                    applyCropConditions(item);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lssacrc_sb_min_freq_start.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                    item.setMinFrequencyStart(((float)progress)/100);
                    lssacrc_tv_min_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyStart()));
                    applyCropConditions(item);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lssacrc_sb_max_freq_start.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                    item.setMaxFrequencyStart(((float)progress)/100);
                    lssacrc_tv_max_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyStart()));
                    applyCropConditions(item);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        lssacrc_sb_threshold_end.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                    item.setThresholdEnd(progress);
                    lssacrc_tv_threshold_end_value.setText(String.valueOf(item.getThresholdEnd()));
                    applyCropConditions(item);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lssacrc_sb_min_freq_end.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                    item.setMinFrequencyEnd(((float)progress)/100);
                    lssacrc_tv_min_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyEnd()));
                    applyCropConditions(item);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lssacrc_sb_max_freq_end.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                    item.setMaxFrequencyEnd(((float)progress)/100);
                    lssacrc_tv_max_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyEnd()));
                    applyCropConditions(item);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lssacrc_bt_threshold_start_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getThresholdStart() > 0) {
                    item.setThresholdStart(item.getThresholdStart()-1);
                    lssacrc_tv_threshold_start_value.setText(String.valueOf(item.getThresholdStart()));
                    lssacrc_sb_threshold_start.setProgress(item.getThresholdStart());
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_threshold_start_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getThresholdStart() < 255) {
                    item.setThresholdStart(item.getThresholdStart()+1);
                    lssacrc_tv_threshold_start_value.setText(String.valueOf(item.getThresholdStart()));
                    lssacrc_sb_threshold_start.setProgress(item.getThresholdStart());
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_min_freq_start_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMinFrequencyStart() >= 0.01) {
                    item.setMinFrequencyStart(item.getMinFrequencyStart()-0.01f);
                    lssacrc_tv_min_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyStart()));
                    lssacrc_sb_min_freq_start.setProgress((int)(item.getMinFrequencyStart()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_min_freq_start_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMinFrequencyStart() >= 0.001) {
                    item.setMinFrequencyStart(item.getMinFrequencyStart()-0.001f);
                    lssacrc_tv_min_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyStart()));
                    lssacrc_sb_min_freq_start.setProgress((int)(item.getMinFrequencyStart()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_min_freq_start_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMinFrequencyStart() <= 0.999) {
                    item.setMinFrequencyStart(item.getMinFrequencyStart()+0.001f);
                    lssacrc_tv_min_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyStart()));
                    lssacrc_sb_min_freq_start.setProgress((int)(item.getMinFrequencyStart()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_min_freq_start_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMinFrequencyStart() <= 0.99) {
                    item.setMinFrequencyStart(item.getMinFrequencyStart()+0.01f);
                    lssacrc_tv_min_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyStart()));
                    lssacrc_sb_min_freq_start.setProgress((int)(item.getMinFrequencyStart()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_max_freq_start_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMaxFrequencyStart() >= 0.01) {
                    item.setMaxFrequencyStart(item.getMaxFrequencyStart()-0.01f);
                    lssacrc_tv_max_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyStart()));
                    lssacrc_sb_max_freq_start.setProgress((int)(item.getMaxFrequencyStart()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_max_freq_start_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMaxFrequencyStart() >= 0.001) {
                    item.setMaxFrequencyStart(item.getMaxFrequencyStart()-0.001f);
                    lssacrc_tv_max_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyStart()));
                    lssacrc_sb_max_freq_start.setProgress((int)(item.getMaxFrequencyStart()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_max_freq_start_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMaxFrequencyStart() <= 0.999) {
                    item.setMaxFrequencyStart(item.getMaxFrequencyStart()+0.001f);
                    lssacrc_tv_max_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyStart()));
                    lssacrc_sb_max_freq_start.setProgress((int)(item.getMaxFrequencyStart()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_max_freq_start_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMaxFrequencyStart() <= 0.99) {
                    item.setMaxFrequencyStart(item.getMaxFrequencyStart()+0.01f);
                    lssacrc_tv_max_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyStart()));
                    lssacrc_sb_max_freq_start.setProgress((int)(item.getMaxFrequencyStart()*100));
                    applyCropConditions(item);
                }
            }
        });


        lssacrc_bt_threshold_end_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getThresholdEnd() > 0) {
                    item.setThresholdEnd(item.getThresholdEnd()-1);
                    lssacrc_tv_threshold_end_value.setText(String.valueOf(item.getThresholdEnd()));
                    lssacrc_sb_threshold_end.setProgress(item.getThresholdEnd());
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_threshold_end_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getThresholdEnd() < 255) {
                    item.setThresholdEnd(item.getThresholdEnd()+1);
                    lssacrc_tv_threshold_end_value.setText(String.valueOf(item.getThresholdEnd()));
                    lssacrc_sb_threshold_end.setProgress(item.getThresholdEnd());
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_min_freq_end_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMinFrequencyEnd() >= 0.01) {
                    item.setMinFrequencyEnd(item.getMinFrequencyEnd()-0.01f);
                    lssacrc_tv_min_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyEnd()));
                    lssacrc_sb_min_freq_end.setProgress((int)(item.getMinFrequencyEnd()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_min_freq_end_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMinFrequencyEnd() >= 0.001) {
                    item.setMinFrequencyEnd(item.getMinFrequencyEnd()-0.001f);
                    lssacrc_tv_min_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyEnd()));
                    lssacrc_sb_min_freq_end.setProgress((int)(item.getMinFrequencyEnd()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_min_freq_end_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMinFrequencyEnd() <= 0.999) {
                    item.setMinFrequencyEnd(item.getMinFrequencyEnd()+0.001f);
                    lssacrc_tv_min_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyEnd()));
                    lssacrc_sb_min_freq_end.setProgress((int)(item.getMinFrequencyEnd()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_min_freq_end_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMinFrequencyEnd() <= 0.99) {
                    item.setMinFrequencyEnd(item.getMinFrequencyEnd()+0.01f);
                    lssacrc_tv_min_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyEnd()));
                    lssacrc_sb_min_freq_end.setProgress((int)(item.getMinFrequencyEnd()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_max_freq_end_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMaxFrequencyEnd() >= 0.01) {
                    item.setMaxFrequencyEnd(item.getMaxFrequencyEnd()-0.01f);
                    lssacrc_tv_max_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyEnd()));
                    lssacrc_sb_max_freq_end.setProgress((int)(item.getMaxFrequencyEnd()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_max_freq_end_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMaxFrequencyEnd() >= 0.001) {
                    item.setMaxFrequencyEnd(item.getMaxFrequencyEnd()-0.001f);
                    lssacrc_tv_max_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyEnd()));
                    lssacrc_sb_max_freq_end.setProgress((int)(item.getMaxFrequencyEnd()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_max_freq_end_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMaxFrequencyEnd() <= 0.999) {
                    item.setMaxFrequencyEnd(item.getMaxFrequencyEnd()+0.001f);
                    lssacrc_tv_max_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyEnd()));
                    lssacrc_sb_max_freq_end.setProgress((int)(item.getMaxFrequencyEnd()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssacrc_bt_max_freq_end_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
                if (item.getMaxFrequencyEnd() <= 0.99) {
                    item.setMaxFrequencyEnd(item.getMaxFrequencyEnd()+0.01f);
                    lssacrc_tv_max_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyEnd()));
                    lssacrc_sb_max_freq_end.setProgress((int)(item.getMaxFrequencyEnd()*100));
                    applyCropConditions(item);
                }
            }
        });

        lssarbt_sw_use.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (manualChange) {
                    SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                    item.setSkip(!isChecked);
                    applyRbtConditions(item);
                }
            }
        });

        lssarbt_rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (manualChange) {
                    SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                    switch (checkedId) {
                        case R.id.lssarbt_rb_resize:
                            item.setType(0);
                            break;
                        case R.id.lssarbt_rb_BW:
                            item.setType(1);
                            break;
                        case R.id.lssarbt_rb_WB:
                            item.setType(2);
                            break;
                        case R.id.lssarbt_rb_transparent:
                            item.setType(3);
                            break;
                        default:
                    }
                    applyRbtConditions(item);
                }
            }
        });

        lssarbt_tv_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rbtConditionIndex != 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Area_Activity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Colors");

                    final SSA_ColorsListAdapter arrayAdapter = new SSA_ColorsListAdapter(SSA_Area_Activity.this);
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex - 1);
                            SSA_Color ssaColor = arrayAdapter.getItem(which);
                            item.setSsaColor(ssaColor);
                            lssarbt_tv_color.setTextColor(item.getSsaColor().getColor());
                            lssarbt_tv_color_key_value.setText(item.getSsaColor().getKey());
                            lssarbt_tv_color_name_value.setText(item.getSsaColor().getName());
                            applyRbtConditions(item);
                        }
                    });
                    builder.show();
                }

            }
        });

        lssarbt_sb_threshold.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                    item.setThreshold(progress);
                    lssarbt_tv_threshold_value.setText(String.valueOf(item.getThreshold()));
                    applyRbtConditions(item);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lssarbt_sb_scale_x.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                    item.setScaleX(((float)progress)/10);
                    lssarbt_tv_scale_x_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleX()));
                    applyRbtConditions(item);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lssarbt_sb_scale_y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                    item.setScaleY(((float)progress)/10);
                    lssarbt_tv_scale_y_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleY()));
                    applyRbtConditions(item);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        lssarbt_bt_threshold_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                if (item.getThreshold() > 0) {
                    item.setThreshold(item.getThreshold()-1);
                    lssarbt_tv_threshold_value.setText(String.valueOf(item.getThreshold()));
                    lssarbt_sb_threshold.setProgress(item.getThreshold());
                    applyRbtConditions(item);
                }
            }
        });

        lssarbt_bt_threshold_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                if (item.getThreshold() < 255) {
                    item.setThreshold(item.getThreshold()+1);
                    lssarbt_tv_threshold_value.setText(String.valueOf(item.getThreshold()));
                    lssarbt_sb_threshold.setProgress(item.getThreshold());
                    applyRbtConditions(item);
                }
            }
        });

        lssarbt_bt_scale_x_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                if (item.getScaleX() > 1.0) {
                    item.setScaleX(item.getScaleX()-1.0f);
                    lssarbt_tv_scale_x_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleX()));
                    lssarbt_sb_scale_x.setProgress((int)(item.getScaleX()*10));
                    applyRbtConditions(item);
                }
            }
        });

        lssarbt_bt_scale_x_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                if (item.getScaleX() > 0.1) {
                    item.setScaleX(item.getScaleX()-0.1f);
                    lssarbt_tv_scale_x_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleX()));
                    lssarbt_sb_scale_x.setProgress((int)(item.getScaleX()*10));
                    applyRbtConditions(item);
                }
            }
        });

        lssarbt_bt_scale_x_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                if (item.getScaleX() <= 9.9) {
                    item.setScaleX(item.getScaleX()+0.1f);
                    lssarbt_tv_scale_x_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleX()));
                    lssarbt_sb_scale_x.setProgress((int)(item.getScaleX()*10));
                    applyRbtConditions(item);
                }
            }
        });

        lssarbt_bt_scale_x_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                if (item.getScaleX() <= 9.0) {
                    item.setScaleX(item.getScaleX()+1.0f);
                    lssarbt_tv_scale_x_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleX()));
                    lssarbt_sb_scale_x.setProgress((int)(item.getScaleX()*10));
                    applyRbtConditions(item);
                }
            }
        });

        lssarbt_bt_scale_y_minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                if (item.getScaleY() > 1.0) {
                    item.setScaleY(item.getScaleY()-1.0f);
                    lssarbt_tv_scale_y_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleY()));
                    lssarbt_sb_scale_y.setProgress((int)(item.getScaleY()*10));
                    applyRbtConditions(item);
                }
            }
        });

        lssarbt_bt_scale_y_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                if (item.getScaleY() > 0.1) {
                    item.setScaleY(item.getScaleY()-0.1f);
                    lssarbt_tv_scale_y_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleY()));
                    lssarbt_sb_scale_y.setProgress((int)(item.getScaleY()*10));
                    applyRbtConditions(item);
                }
            }
        });

        lssarbt_bt_scale_y_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                if (item.getScaleY() <= 9.9) {
                    item.setScaleY(item.getScaleY()+0.1f);
                    lssarbt_tv_scale_y_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleY()));
                    lssarbt_sb_scale_y.setProgress((int)(item.getScaleY()*10));
                    applyRbtConditions(item);
                }
            }
        });

        lssarbt_bt_scale_y_plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
                if (item.getScaleY() <= 9.0) {
                    item.setScaleY(item.getScaleY()+1.0f);
                    lssarbt_tv_scale_y_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleY()));
                    lssarbt_sb_scale_y.setProgress((int)(item.getScaleY()*10));
                    applyRbtConditions(item);
                }
            }
        });


    }


    private void setMinMax() {
        int width, height;
        if (ssaArea.getParentArea() == null) {
            width = ssaScreenshot.getBitmap().getWidth() - Math.abs(ssaScreenshot.getCenterX()*2);
            height = ssaScreenshot.getBitmap().getHeight() - Math.abs(ssaScreenshot.getCenterY()*2);
        } else {
            width = ssaArea.getParentArea().getAreaBitmap(ssaScreenshot).getWidth();
            height = ssaArea.getParentArea().getAreaBitmap(ssaScreenshot).getHeight();
        }

        double aspect = (double)width / (double)height;
        double delta = Math.ceil((aspect / 2) * 1000)/1000;
        minY = -1.0;
        maxY = +1.0;
        switch (ssaArea.getSnap()) {
            case -1:
                minX = 0;
                maxX = delta * 2;
                break;
            case 0:
                minX = -delta;
                maxX = +delta;
                break;
            case 1:
                minX = -delta * 2;
                maxX = 0;
                break;
            default:
        }

    }

    private void loadDataToViews() {
        String areaKey = ssaArea.getKey() == null ? "N/A" : ssaArea.getKey();
        String areaName = ssaArea.getName() == null ? "N/A" : ssaArea.getName();
        String areaParentKey = ssaArea.getParentArea() == null ? "" : ssaArea.getParentArea().getKey();
        assaae_tv_key_value.setText(areaKey);
        assaae_tv_name_value.setText(areaName);
        assaae_tv_parent_value.setText(areaParentKey);

        switch (ssaArea.getSnap()) {
            case -1:
                assaae_rb_left.setChecked(true);
                break;
            case 0:
                assaae_rb_center.setChecked(true);
                break;
            case 1:
                assaae_rb_right.setChecked(true);
                break;
            default:
        }
        assaae_tv_x1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX1()));
        assaae_tv_x2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrX2()));
        assaae_tv_y1_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY1()));
        assaae_tv_y2_value.setText(Utils.convertDoubleToStringFormatter3digit(ssaArea.getrY2()));
        assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
        assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));

        assaae_sb_x1.setProgress((int)Math.abs((ssaArea.getrX1()-minX)/((maxX-minX)/100)));
        assaae_sb_x2.setProgress((int)Math.abs((ssaArea.getrX2()-minX)/((maxX-minX)/100)));
        assaae_sb_y1.setProgress((int)Math.abs((ssaArea.getrY1()-minY)/((maxY-minY)/100)));
        assaae_sb_y2.setProgress((int)Math.abs((ssaArea.getrY2()-minY)/((maxY-minY)/100)));

        if (ssaArea.getListCropConditions() == null) ssaArea.setListCropConditions(new ArrayList<>());
        if (ssaArea.getListRBTConditions() == null) ssaArea.setListRBTConditions(new ArrayList<>());

        cropConditionIndex = ssaArea.getListCropConditions().size() == 0 ? 0 : 1;
        rbtConditionIndex = ssaArea.getListRBTConditions().size() == 0 ? 0 : 1;

        showCropConditions();
        showRbtConditions();

        assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));


    }

    private void initializeViews() {

        lssacrc_ll_layout = findViewById(R.id.lssacrc_ll_layout);
        lssarbt_ll_layout = findViewById(R.id.lssarbt_ll_layout);
        assaae_tv_key_value = findViewById(R.id.assaae_tv_key_value);
        assaae_tv_name_value = findViewById(R.id.assaae_tv_name_value);
        assaae_tv_parent_value = findViewById(R.id.assaae_tv_parent_value);
        assaae_rg_snap = findViewById(R.id.assaae_rg_snap);
        assaae_rb_left = findViewById(R.id.assaae_rb_left);
        assaae_rb_center = findViewById(R.id.assaae_rb_center);
        assaae_rb_right = findViewById(R.id.assaae_rb_right);
        assaae_tv_x1_value = findViewById(R.id.assaae_tv_x1_value);
        assaae_tv_x2_value = findViewById(R.id.assaae_tv_x2_value);
        assaae_tv_y1_value = findViewById(R.id.assaae_tv_y1_value);
        assaae_tv_y2_value = findViewById(R.id.assaae_tv_y2_value);
        assaae_iv_image = findViewById(R.id.assaae_iv_image);
        assaae_sb_x1 = findViewById(R.id.assaae_sb_x1);
        assaae_sb_x2 = findViewById(R.id.assaae_sb_x2);
        assaae_sb_y1 = findViewById(R.id.assaae_sb_y1);
        assaae_sb_y2 = findViewById(R.id.assaae_sb_y2);
        assaae_iv_image_rbt = findViewById(R.id.assaae_iv_image_rbt);
        assaae_tv_ocr = findViewById(R.id.assaae_tv_ocr);
        lssacrc_iv_image = findViewById(R.id.lssacrc_iv_image);
        lssarbt_iv_image = findViewById(R.id.lssarbt_iv_image);

        assaae_ib_x1_minus10 = findViewById(R.id.assaae_ib_x1_minus10);
        assaae_ib_x1_minus1 = findViewById(R.id.assaae_ib_x1_minus1);
        assaae_ib_reset_x1 = findViewById(R.id.assaae_ib_reset_x1);
        assaae_ib_x1_plus1 = findViewById(R.id.assaae_ib_x1_plus1);
        assaae_ib_x1_plus10 = findViewById(R.id.assaae_ib_x1_plus10);
        assaae_ib_x2_minus10 = findViewById(R.id.assaae_ib_x2_minus10);
        assaae_ib_x2_minus1 = findViewById(R.id.assaae_ib_x2_minus1);
        assaae_ib_reset_x2 = findViewById(R.id.assaae_ib_reset_x2);
        assaae_ib_x2_plus1 = findViewById(R.id.assaae_ib_x2_plus1);
        assaae_ib_x2_plus10 = findViewById(R.id.assaae_ib_x2_plus10);
        assaae_ib_y1_minus10 = findViewById(R.id.assaae_ib_y1_minus10);
        assaae_ib_y1_minus1 = findViewById(R.id.assaae_ib_y1_minus1);
        assaae_ib_reset_y1 = findViewById(R.id.assaae_ib_reset_y1);
        assaae_ib_y1_plus1 = findViewById(R.id.assaae_ib_y1_plus1);
        assaae_ib_y1_plus10 = findViewById(R.id.assaae_ib_y1_plus10);
        assaae_ib_y2_minus10 = findViewById(R.id.assaae_ib_y2_minus10);
        assaae_ib_y2_minus1 = findViewById(R.id.assaae_ib_y2_minus1);
        assaae_ib_reset_y2 = findViewById(R.id.assaae_ib_reset_y2);
        assaae_ib_y2_plus1 = findViewById(R.id.assaae_ib_y2_plus1);
        assaae_ib_y2_plus10 = findViewById(R.id.assaae_ib_y2_plus10);

        
        lssacch_bt_previous = findViewById(R.id.lssacch_bt_previous);
        lssacch_bt_next = findViewById(R.id.lssacch_bt_next);
        lssacch_bt_add_new = findViewById(R.id.lssacch_bt_add_new);
        lssacch_bt_delete = findViewById(R.id.lssacch_bt_delete);
        lssacch_tv_index = findViewById(R.id.lssacch_tv_index);

        lssacrc_sw_use = findViewById(R.id.lssacrc_sw_use);
        lssacrc_rg_direction = findViewById(R.id.lssacrc_rg_direction);
        lssacrc_rb_LR = findViewById(R.id.lssacrc_rb_LR);
        lssacrc_rb_RL = findViewById(R.id.lssacrc_rb_RL);
        lssacrc_rb_TB = findViewById(R.id.lssacrc_rb_TB);
        lssacrc_rb_BT = findViewById(R.id.lssacrc_rb_BT);
        lssacrc_cb_return_first_fragment = findViewById(R.id.lssacrc_cb_return_first_fragment);
        lssacrc_cb_only_first = findViewById(R.id.lssacrc_cb_only_first);
        lssacrc_tv_color_start = findViewById(R.id.lssacrc_tv_color_start);
        lssacrc_tv_color_start_key_value = findViewById(R.id.lssacrc_tv_color_start_key_value);
        lssacrc_tv_color_start_name_value = findViewById(R.id.lssacrc_tv_color_start_name_value);
        lssacrc_tv_threshold_start_value = findViewById(R.id.lssacrc_tv_threshold_start_value);
        lssacrc_bt_threshold_start_minus = findViewById(R.id.lssacrc_bt_threshold_start_minus);
        lssacrc_bt_threshold_start_plus = findViewById(R.id.lssacrc_bt_threshold_start_plus);
        lssacrc_sb_threshold_start = findViewById(R.id.lssacrc_sb_threshold_start);
        lssacrc_tv_min_freq_start_value = findViewById(R.id.lssacrc_tv_min_freq_start_value);
        lssacrc_bt_min_freq_start_minus10 = findViewById(R.id.lssacrc_bt_min_freq_start_minus10);
        lssacrc_bt_min_freq_start_minus1 = findViewById(R.id.lssacrc_bt_min_freq_start_minus1);
        lssacrc_bt_min_freq_start_plus1 = findViewById(R.id.lssacrc_bt_min_freq_start_plus1);
        lssacrc_bt_min_freq_start_plus10 = findViewById(R.id.lssacrc_bt_min_freq_start_plus10);
        lssacrc_sb_min_freq_start = findViewById(R.id.lssacrc_sb_min_freq_start);
        lssacrc_tv_max_freq_start_value = findViewById(R.id.lssacrc_tv_max_freq_start_value);
        lssacrc_bt_max_freq_start_minus10 = findViewById(R.id.lssacrc_bt_max_freq_start_minus10);
        lssacrc_bt_max_freq_start_minus1 = findViewById(R.id.lssacrc_bt_max_freq_start_minus1);
        lssacrc_bt_max_freq_start_plus1 = findViewById(R.id.lssacrc_bt_max_freq_start_plus1);
        lssacrc_bt_max_freq_start_plus10 = findViewById(R.id.lssacrc_bt_max_freq_start_plus10);
        lssacrc_sb_max_freq_start = findViewById(R.id.lssacrc_sb_max_freq_start);
        lssacrc_tv_color_end = findViewById(R.id.lssacrc_tv_color_end);
        lssacrc_tv_color_end_key_value = findViewById(R.id.lssacrc_tv_color_end_key_value);
        lssacrc_tv_color_end_name_value = findViewById(R.id.lssacrc_tv_color_end_name_value);
        lssacrc_tv_threshold_end_value = findViewById(R.id.lssacrc_tv_threshold_end_value);
        lssacrc_bt_threshold_end_minus = findViewById(R.id.lssacrc_bt_threshold_end_minus);
        lssacrc_bt_threshold_end_plus = findViewById(R.id.lssacrc_bt_threshold_end_plus);
        lssacrc_sb_threshold_end = findViewById(R.id.lssacrc_sb_threshold_end);
        lssacrc_tv_min_freq_end_value = findViewById(R.id.lssacrc_tv_min_freq_end_value);
        lssacrc_bt_min_freq_end_minus10 = findViewById(R.id.lssacrc_bt_min_freq_end_minus10);
        lssacrc_bt_min_freq_end_minus1 = findViewById(R.id.lssacrc_bt_min_freq_end_minus1);
        lssacrc_bt_min_freq_end_plus1 = findViewById(R.id.lssacrc_bt_min_freq_end_plus1);
        lssacrc_bt_min_freq_end_plus10 = findViewById(R.id.lssacrc_bt_min_freq_end_plus10);
        lssacrc_sb_min_freq_end = findViewById(R.id.lssacrc_sb_min_freq_end);
        lssacrc_tv_max_freq_end_value = findViewById(R.id.lssacrc_tv_max_freq_end_value);
        lssacrc_bt_max_freq_end_minus10 = findViewById(R.id.lssacrc_bt_max_freq_end_minus10);
        lssacrc_bt_max_freq_end_minus1 = findViewById(R.id.lssacrc_bt_max_freq_end_minus1);
        lssacrc_bt_max_freq_end_plus1 = findViewById(R.id.lssacrc_bt_max_freq_end_plus1);
        lssacrc_bt_max_freq_end_plus10 = findViewById(R.id.lssacrc_bt_max_freq_end_plus10);
        lssacrc_sb_max_freq_end = findViewById(R.id.lssacrc_sb_max_freq_end);

        lssarch_bt_previous = findViewById(R.id.lssarch_bt_previous);
        lssarch_bt_next = findViewById(R.id.lssarch_bt_next);
        lssarch_bt_add_new = findViewById(R.id.lssarch_bt_add_new);
        lssarch_bt_delete = findViewById(R.id.lssarch_bt_delete);
        lssarch_tv_index = findViewById(R.id.lssarch_tv_index);

        lssarbt_sw_use = findViewById(R.id.lssarbt_sw_use);
        lssarbt_rg_type = findViewById(R.id.lssarbt_rg_type);
        lssarbt_rb_resize = findViewById(R.id.lssarbt_rb_resize);
        lssarbt_rb_BW = findViewById(R.id.lssarbt_rb_BW);
        lssarbt_rb_WB = findViewById(R.id.lssarbt_rb_WB);
        lssarbt_rb_transparent = findViewById(R.id.lssarbt_rb_transparent);
        lssarbt_tv_color = findViewById(R.id.lssarbt_tv_color);
        lssarbt_tv_color_key_value = findViewById(R.id.lssarbt_tv_color_key_value);
        lssarbt_tv_color_name_value = findViewById(R.id.lssarbt_tv_color_name_value);
        lssarbt_tv_threshold_value = findViewById(R.id.lssarbt_tv_threshold_value);
        lssarbt_bt_threshold_minus = findViewById(R.id.lssarbt_bt_threshold_minus);
        lssarbt_bt_threshold_plus = findViewById(R.id.lssarbt_bt_threshold_plus);
        lssarbt_sb_threshold = findViewById(R.id.lssarbt_sb_threshold);
        lssarbt_tv_scale_x_value = findViewById(R.id.lssarbt_tv_scale_x_value);
        lssarbt_bt_scale_x_minus10 = findViewById(R.id.lssarbt_bt_scale_x_minus10);
        lssarbt_bt_scale_x_minus1 = findViewById(R.id.lssarbt_bt_scale_x_minus1);
        lssarbt_bt_scale_x_plus1 = findViewById(R.id.lssarbt_bt_scale_x_plus1);
        lssarbt_bt_scale_x_plus10 = findViewById(R.id.lssarbt_bt_scale_x_plus10);
        lssarbt_sb_scale_x = findViewById(R.id.lssarbt_sb_scale_x);
        lssarbt_tv_scale_y_value = findViewById(R.id.lssarbt_tv_scale_y_value);
        lssarbt_bt_scale_y_minus10 = findViewById(R.id.lssarbt_bt_scale_y_minus10);
        lssarbt_bt_scale_y_minus1 = findViewById(R.id.lssarbt_bt_scale_y_minus1);
        lssarbt_bt_scale_y_plus1 = findViewById(R.id.lssarbt_bt_scale_y_plus1);
        lssarbt_bt_scale_y_plus10 = findViewById(R.id.lssarbt_bt_scale_y_plus10);
        lssarbt_sb_scale_y = findViewById(R.id.lssarbt_sb_scale_y);
        assaae_bt_show_areas = findViewById(R.id.assaae_bt_show_areas);
        assaae_pb_progress = findViewById(R.id.assaae_pb_progress);
        assaae_tv_conditions = findViewById(R.id.assaae_tv_conditions);

    }

    private void showCropConditions() {
        int countConditions = ssaArea.getListCropConditions().size();
        lssacch_bt_previous.setEnabled(cropConditionIndex > 1);
        lssacch_bt_next.setEnabled(cropConditionIndex < countConditions);
        lssacch_bt_delete.setEnabled(cropConditionIndex != 0);
        lssacch_tv_index.setText("" + cropConditionIndex + "/" + countConditions);

        lssacrc_sw_use.setEnabled(cropConditionIndex != 0);
        lssacrc_cb_return_first_fragment.setEnabled(cropConditionIndex != 0);
        lssacrc_cb_only_first.setEnabled(cropConditionIndex != 0);
        lssacrc_sb_threshold_start.setEnabled(cropConditionIndex != 0);
        lssacrc_sb_min_freq_start.setEnabled(cropConditionIndex != 0);
        lssacrc_sb_max_freq_start.setEnabled(cropConditionIndex != 0);
        lssacrc_sb_threshold_end.setEnabled(cropConditionIndex != 0);
        lssacrc_sb_min_freq_end.setEnabled(cropConditionIndex != 0);
        lssacrc_sb_max_freq_end.setEnabled(cropConditionIndex != 0);
        lssacrc_ll_layout.setVisibility(cropConditionIndex != 0 ? View.VISIBLE : View.INVISIBLE);

        if (cropConditionIndex > 0) {
            SSA_Crop_Condition item = ssaArea.getListCropConditions().get(cropConditionIndex-1);
            manualChange = false;
            lssacrc_sw_use.setChecked(!item.isSkip());
            if (item.getDirection().equals(PictureProcessorDirection.FROM_LEFT_TO_RIGHT)) lssacrc_rb_LR.setChecked(true);
            if (item.getDirection().equals(PictureProcessorDirection.FROM_RIGHT_TO_LEFT)) lssacrc_rb_RL.setChecked(true);
            if (item.getDirection().equals(PictureProcessorDirection.FROM_TOP_TO_BOTTOM)) lssacrc_rb_TB.setChecked(true);
            if (item.getDirection().equals(PictureProcessorDirection.FROM_BOTTOM_TO_TOP)) lssacrc_rb_BT.setChecked(true);
            lssacrc_cb_return_first_fragment.setChecked(item.isReturnFirstFragment());
            lssacrc_cb_only_first.setChecked(item.isOnlyFirst());
            lssacrc_tv_color_start.setTextColor(item.getSsaColorStart().getColor());
            lssacrc_tv_color_start_key_value.setText(item.getSsaColorStart().getKey());
            lssacrc_tv_color_start_name_value.setText(item.getSsaColorStart().getName());
            lssacrc_tv_color_end.setTextColor(item.getSsaColorEnd().getColor());
            lssacrc_tv_color_end_key_value.setText(item.getSsaColorEnd().getKey());
            lssacrc_tv_color_end_name_value.setText(item.getSsaColorEnd().getName());
            lssacrc_tv_threshold_start_value.setText(String.valueOf(item.getThresholdStart()));
            lssacrc_sb_threshold_start.setProgress(item.getThresholdStart());
            lssacrc_tv_min_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyStart()));
            lssacrc_sb_min_freq_start.setProgress((int)(item.getMinFrequencyStart()*100));
            lssacrc_tv_max_freq_start_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyStart()));
            lssacrc_sb_max_freq_start.setProgress((int)(item.getMaxFrequencyStart()*100));
            lssacrc_tv_threshold_end_value.setText(String.valueOf(item.getThresholdEnd()));
            lssacrc_sb_threshold_end.setProgress(item.getThresholdEnd());
            lssacrc_tv_min_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMinFrequencyEnd()));
            lssacrc_sb_min_freq_end.setProgress((int)(item.getMinFrequencyEnd()*100));
            lssacrc_tv_max_freq_end_value.setText(Utils.convertFloatToStringFormatter3digit(item.getMaxFrequencyEnd()));
            lssacrc_sb_max_freq_end.setProgress((int)(item.getMaxFrequencyEnd()*100));
            manualChange = true;
            lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,item));
        }


        
    }

    private void showRbtConditions() {

        int countConditions = ssaArea.getListRBTConditions().size();
        lssarch_bt_previous.setEnabled(rbtConditionIndex > 1);
        lssarch_bt_next.setEnabled(rbtConditionIndex < countConditions);
        lssarch_bt_delete.setEnabled(rbtConditionIndex != 0);
        lssarch_tv_index.setText("" + rbtConditionIndex + "/" + countConditions);



        lssarbt_sw_use.setEnabled(rbtConditionIndex != 0);
        lssarbt_sb_threshold.setEnabled(rbtConditionIndex != 0);
        lssarbt_sb_scale_x.setEnabled(rbtConditionIndex != 0);
        lssarbt_sb_scale_y.setEnabled(rbtConditionIndex != 0);
        lssarbt_ll_layout.setVisibility(rbtConditionIndex != 0 ? View.VISIBLE : View.INVISIBLE);

        if (rbtConditionIndex > 0) {
            SSA_RBT_Condition item = ssaArea.getListRBTConditions().get(rbtConditionIndex-1);
            manualChange = false;
            lssarbt_sw_use.setChecked(!item.isSkip());
            if (item.getType() == 0) lssarbt_rb_resize.setChecked(true);
            if (item.getType() == 1) lssarbt_rb_BW.setChecked(true);
            if (item.getType() == 2) lssarbt_rb_WB.setChecked(true);
            if (item.getType() == 3) lssarbt_rb_transparent.setChecked(true);
            lssarbt_tv_color.setTextColor(item.getSsaColor().getColor());
            lssarbt_tv_color_key_value.setText(item.getSsaColor().getKey());
            lssarbt_tv_color_name_value.setText(item.getSsaColor().getName());
            lssarbt_tv_threshold_value.setText(String.valueOf(item.getThreshold()));
            lssarbt_sb_threshold.setProgress(item.getThreshold());
            lssarbt_tv_scale_x_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleX()));
            lssarbt_sb_scale_x.setProgress((int)(item.getScaleX()*10));
            lssarbt_tv_scale_y_value.setText(Utils.convertFloatToStringFormatter1digit(item.getScaleY()));
            lssarbt_sb_scale_y.setProgress((int)(item.getScaleY()*10));
            manualChange = true;
            lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,item));
        }


    }

    private void applyCropConditions(SSA_Crop_Condition ssaCropCondition) {
        if  (ssaCropCondition != null) {
            List<SSA_Crop_Condition> list = ssaArea.getListCropConditions();
            int index = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getKey().equals(ssaCropCondition.getKey())) {
                    index = i;
                    break;
                }
            }
            if (index >=0 ) {
                list.remove(index);
                list.add(index,ssaCropCondition);
                ssaArea.setListCropConditions(list);
            }
        }
        
        assaae_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot));
        if (cropConditionIndex != 0 && !ssaArea.getListCropConditions().get(cropConditionIndex-1).isSkip()) lssacrc_iv_image.setImageBitmap(ssaArea.getAreaBitmap(ssaScreenshot,ssaArea.getListCropConditions().get(cropConditionIndex-1)));
        applyRbtConditions(null);

        showCropConditions();
    }

    private void applyRbtConditions(SSA_RBT_Condition ssaRbtCondition) {

        if  (ssaRbtCondition != null) {
            List<SSA_RBT_Condition> list = ssaArea.getListRBTConditions();
            int index = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getKey().equals(ssaRbtCondition.getKey())) {
                    index = i;
                    break;
                }
            }
            if (index >=0 ) {
                list.remove(index);
                list.add(index,ssaRbtCondition);
                ssaArea.setListRBTConditions(list);
            }
        }
        
        assaae_iv_image_rbt.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot));
        if (rbtConditionIndex != 0 && !ssaArea.getListRBTConditions().get(rbtConditionIndex-1).isSkip()) lssarbt_iv_image.setImageBitmap(ssaArea.getAreaBitmapRBT(ssaScreenshot,ssaArea.getListRBTConditions().get(rbtConditionIndex-1)));
        assaae_tv_ocr.setText(ssaArea.getOCR(ssaScreenshot));

        showRbtConditions();
    }

    public void setName(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Area_Activity.this);
        builder.setTitle("Area name");
        String defaultValue = ssaArea.getName();
        final EditText input = new EditText(SSA_Area_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (!newValue.equals(defaultValue)) {
                    SSA_Area tmp = SSA_Areas.getArea(newValue);
                    if (tmp == null) {
                        ssaArea.setName(newValue);
                        loadDataToViews();
                    } else {
                        Toast.makeText(SSA_Area_Activity.this,"Область с таким именем уже существует.", Toast.LENGTH_LONG).show();
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


    public void setKey(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SSA_Area_Activity.this);
        builder.setTitle("Area KEY");
        String defaultValue = ssaArea.getKey();
        final EditText input = new EditText(SSA_Area_Activity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(defaultValue);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();
                if (!newValue.equals(defaultValue)) {
                    SSA_Area tmp = SSA_Areas.getArea(newValue);
                    if (tmp == null) {
                        SSA_Areas.delArea(ssaArea);
                        ssaArea.setKey(newValue);
                        SSA_Areas.putArea(ssaArea);
                        loadDataToViews();
                    } else {
                        Toast.makeText(SSA_Area_Activity.this,"Область с таким ключом уже существует.", Toast.LENGTH_LONG).show();
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

    public void setParent(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Areas");

        final SSA_AreasListAdapter arrayAdapter = new SSA_AreasListAdapter(this, SSA_Areas.getAreasList());
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SSA_Area ssaParentArea = arrayAdapter.getItem(which);
                if (!ssaParentArea.getKey().equals(ssaArea.getKey())) {
                    ssaArea.setParentArea(ssaParentArea);
                    setMinMax();
                    loadDataToViews();
                }
            }
        });
        builder.show();

    }

    public void resetParent(View view) {
        ssaArea.setParentArea(null);
        setMinMax();
        loadDataToViews();
    }

    private void deleteCropCondition(String key) {

        List<SSA_Crop_Condition> list = ssaArea.getListCropConditions();
        SSA_Crop_Condition toDel = null;
        for (SSA_Crop_Condition ssaCropCondition: list) {
            if (ssaCropCondition.getKey().equals(key)) {
                toDel = ssaCropCondition;
                break;
            }
        }
        if (toDel != null) {
            list.remove(toDel);
            ssaArea.setListCropConditions(list);
        }

    }

    private void deleteRbtCondition(String key) {

        List<SSA_RBT_Condition> list = ssaArea.getListRBTConditions();
        SSA_RBT_Condition toDel = null;
        for (SSA_RBT_Condition ssaRbtCondition: list) {
            if (ssaRbtCondition.getKey().equals(key)) {
                toDel = ssaRbtCondition;
                break;
            }
        }
        if (toDel != null) {
            list.remove(toDel);
            ssaArea.setListRBTConditions(list);
        }

    }


    public void showSatisfiedConditions(View view) {

        GetListSatisfiedConditions thread = new GetListSatisfiedConditions();
        thread.execute();

    }

    class GetListSatisfiedConditions extends AsyncTask<Void, Integer, Void> {

        List<SSA_Condition> sourceList = new ArrayList<>();
        List<SSA_Condition> resultList = new ArrayList<>();

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            assaae_bt_show_areas.setEnabled(false);
            sourceList = SSA_Conditions.getConditionsList();
            assaae_pb_progress.setMin(0);
            assaae_pb_progress.setMax(sourceList.size());
            assaae_pb_progress.setProgress(0);
            assaae_pb_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            assaae_bt_show_areas.setEnabled(true);
            assaae_pb_progress.setVisibility(View.INVISIBLE);
            String text = "";
            for (SSA_Condition ssaCondition: resultList) {
                text = text + ssaCondition.getKey() + " (" + ssaCondition.getName() + ")\n";
            }
            assaae_tv_conditions.setText(text);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            assaae_pb_progress.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < sourceList.size(); i++) {
                publishProgress(i);
                SSA_Condition ssaCondition = sourceList.get(i);
                if (ssaArea.isSatisfiesCondition(ssaScreenshot, ssaCondition)) resultList.add(ssaCondition);
            }
            return null;
        }
    }

}