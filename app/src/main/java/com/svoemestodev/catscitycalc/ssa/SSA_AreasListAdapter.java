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

import java.util.List;

public class SSA_AreasListAdapter extends ArrayAdapter<SSA_Area> {

    public SSA_AreasListAdapter(@NonNull Context context, List<SSA_Area> listAreas) {
        super(context, R.layout.layout_ssa_areas_list_adapter, listAreas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final SSA_Area item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_areas_list_adapter, null);
        }

        TextView lssaala_tv_key_value = convertView.findViewById(R.id.lssaala_tv_key_value);
        TextView lssaala_tv_name_value = convertView.findViewById(R.id.lssaala_tv_name_value);

        lssaala_tv_key_value.setText(item.getKey());
        lssaala_tv_name_value.setText(item.getName());

        return convertView;

    }

}
