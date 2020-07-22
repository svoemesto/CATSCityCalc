package com.svoemestodev.catscitycalc.ssa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.activities.BordersActivity;
import com.svoemestodev.catscitycalc.adapters.ListBuildingAdapter;
import com.svoemestodev.catscitycalc.classes.Building;
import com.svoemestodev.catscitycalc.utils.ColorFrequency;
import com.svoemestodev.catscitycalc.utils.PictureProcessor;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.List;

public class SSA_ColorDetect_Activity extends AppCompatActivity {

    private static final int REQUEST_CODE_SSA_COLORS_ACTIVITY = 200;

    Button assacd_btn_get_area;
    TextView assacd_tv_area_key;
    TextView assacd_tv_area_name;
    ImageView assacd_iv_area;
    ListView assacd_lv_colors;
    public static SSA_Area ssaArea;
    public static Bitmap areaBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssa_color_detect);

        ActionBar actionBar = getSupportActionBar();    // экшен-бар
        if (actionBar != null) {                        // если экшен-бар есть
            actionBar.setDisplayHomeAsUpEnabled(true);  // показываем кнопку "<-"
        }

        initializeViews();

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
            List<ColorFrequency> colorFrequencyList = PictureProcessor.getFrequencyMap(areaBitmap,0,0);
            if (colorFrequencyList.size() > 0) {
                SSA_ColorsDetectListAdapter adapter = new SSA_ColorsDetectListAdapter(SSA_ColorDetect_Activity.this, colorFrequencyList);
                assacd_lv_colors.setAdapter(adapter);
            }
        }
    }

    private void initializeViews() {
        assacd_btn_get_area = findViewById(R.id.assacd_btn_get_area);
        assacd_iv_area = findViewById(R.id.assacd_iv_area);
        assacd_lv_colors = findViewById(R.id.assacd_lv_colors);
        assacd_tv_area_key = findViewById(R.id.assacd_tv_area_key);
        assacd_tv_area_name = findViewById(R.id.assacd_tv_area_name);
    }

    public void doGetArea(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Areas");

        final SSA_AreasListAdapter arrayAdapter = new SSA_AreasListAdapter(this);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ssaArea = arrayAdapter.getItem(which);
                assacd_tv_area_key.setText(ssaArea.getKey());
                assacd_tv_area_name.setText(ssaArea.getName());
                areaBitmap = ssaArea.getAreaBitmap(SSA_Buttons_Activity.ssaScreenshot);
                assacd_iv_area.setImageBitmap(areaBitmap);

                List<ColorFrequency> colorFrequencyList = PictureProcessor.getFrequencyMap(areaBitmap,0,0);
                if (colorFrequencyList.size() > 0) {
                    SSA_ColorsDetectListAdapter adapter = new SSA_ColorsDetectListAdapter(SSA_ColorDetect_Activity.this, colorFrequencyList);
                    assacd_lv_colors.setAdapter(adapter);
                }
            }
        });
        builder.show();

    }

    private class SSA_ColorsDetectListAdapter extends ArrayAdapter<ColorFrequency> {

        public SSA_ColorsDetectListAdapter(@NonNull Context context, List<ColorFrequency> colorFrequencyList) {
            super(context, R.layout.layout_ssa_colors_detect, colorFrequencyList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final ColorFrequency item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_colors_detect, null);
            }

            ImageButton lssaclsd_bt_add_color = convertView.findViewById(R.id.lssaclsd_bt_add_color);
            TextView lssaclsd_tv_color = convertView.findViewById(R.id.lssaclsd_tv_color);
            TextView lssaclsd_tv_frequency = convertView.findViewById(R.id.lssaclsd_tv_frequency);
            TextView lssaclsd_tv_color_value = convertView.findViewById(R.id.lssaclsd_tv_color_value);
            TextView lssaclsd_tv_key_value = convertView.findViewById(R.id.lssaclsd_tv_key_value);
            TextView lssaclsd_tv_name_value = convertView.findViewById(R.id.lssaclsd_tv_name_value);

            lssaclsd_tv_color.setTextColor(item.getColor());
            lssaclsd_tv_frequency.setText(Utils.convertFloatToStringFormatter3digit(item.getFrequency()));
            final String colorHex = Integer.toHexString(item.getColor()).toUpperCase();
            lssaclsd_tv_color_value.setText(colorHex);
            if (item.getSsaColor() == null) {
                lssaclsd_tv_key_value.setText("");
                lssaclsd_tv_name_value.setText("");
                lssaclsd_bt_add_color.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SSA_Color ssaColor = new SSA_Color(colorHex);
                        ssaColor.setName(ssaColor.getKey());
                        ssaColor.setColor(item.getColor());
                        SSA_Colors.putColor(ssaColor);
                        SSA_Color_Activity.ssaColor = ssaColor;
                        Intent intent = new Intent(SSA_ColorDetect_Activity.this, SSA_Color_Activity.class);
                        startActivityForResult(intent, REQUEST_CODE_SSA_COLORS_ACTIVITY);
                    }
                });
            } else {
                lssaclsd_tv_key_value.setText(item.getSsaColor().getKey());
                lssaclsd_tv_name_value.setText(item.getSsaColor().getName());
            }

            return convertView;

        }

    }

}