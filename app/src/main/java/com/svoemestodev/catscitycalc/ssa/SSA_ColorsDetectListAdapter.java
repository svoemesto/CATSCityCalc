package com.svoemestodev.catscitycalc.ssa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.utils.ColorFrequency;
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.List;

public class SSA_ColorsDetectListAdapter extends ArrayAdapter<ColorFrequency> {

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
                    SSA_Color_Activity.ssaColor = ssaColor;;
                }
            });
        } else {
            lssaclsd_tv_key_value.setText(item.getSsaColor().getKey());
            lssaclsd_tv_name_value.setText(item.getSsaColor().getName());
        }

        return convertView;

    }

}
