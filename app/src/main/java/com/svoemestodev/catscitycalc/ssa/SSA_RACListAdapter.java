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
import com.svoemestodev.catscitycalc.utils.Utils;

import java.util.List;

public class SSA_RACListAdapter extends ArrayAdapter<SSA_Rule_Area_Condition> {

    public SSA_RACListAdapter(@NonNull Context context, List<SSA_Rule_Area_Condition> listRACs) {
        super(context, R.layout.layout_ssa_racs_list_adapter, listRACs);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final SSA_Rule_Area_Condition item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_racs_list_adapter, null);
        }

        TextView lssaracla_tv_key_value = convertView.findViewById(R.id.lssaracla_tv_key_value);
        TextView lssaracla_tv_name_value = convertView.findViewById(R.id.lssaracla_tv_name_value);

        String racKey = item.getKey() == null ? "N/A" : item.getKey();
        String racName = item.getName() == null ? "N/A" : item.getName();

        lssaracla_tv_key_value.setText(racKey);
        lssaracla_tv_name_value.setText(racName);

        return convertView;

    }

}
