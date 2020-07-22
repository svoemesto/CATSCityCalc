package com.svoemestodev.catscitycalc.ssa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.svoemestodev.catscitycalc.R;

public class SSA_ColorsListAdapter extends ArrayAdapter<SSA_Color> {

    public SSA_ColorsListAdapter(@NonNull Context context) {
        super(context, R.layout.layout_ssa_colors_list_adapter, SSA_Colors.getColorsList());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final SSA_Color item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_colors_list_adapter, null);
        }

        TextView lssaclsla_tv_color = convertView.findViewById(R.id.lssaclsla_tv_color);
        TextView lssaclsla_tv_key_value = convertView.findViewById(R.id.lssaclsla_tv_key_value);
        TextView lssaclsla_tv_name_value = convertView.findViewById(R.id.lssaclsla_tv_name_value);

        lssaclsla_tv_key_value.setText(item.getKey());
        lssaclsla_tv_name_value.setText(item.getName());
        lssaclsla_tv_color.setTextColor(item.getColor());

        return convertView;

    }

}
