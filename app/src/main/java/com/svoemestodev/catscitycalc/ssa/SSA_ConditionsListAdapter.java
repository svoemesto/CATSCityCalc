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

public class SSA_ConditionsListAdapter extends ArrayAdapter<SSA_Condition> {

    public SSA_ConditionsListAdapter(@NonNull Context context, List<SSA_Condition> listConditions) {
        super(context, R.layout.layout_ssa_conditions_list_adapter, listConditions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final SSA_Condition item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ssa_conditions_list_adapter, null);
        }

        TextView lssacla_tv_key_value = convertView.findViewById(R.id.lssacla_tv_key_value);
        TextView lssacla_tv_name_value = convertView.findViewById(R.id.lssacla_tv_name_value);
        TextView lssacla_tv_color = convertView.findViewById(R.id.lssacla_tv_color);
        TextView lssacla_tv_threshold = convertView.findViewById(R.id.lssacla_tv_threshold);
        TextView lssacla_tv_frequency = convertView.findViewById(R.id.lssacla_tv_frequency);

        String conditionKey = item.getKey() == null ? "N/A" : item.getKey();
        String conditionName = item.getName() == null ? "N/A" : item.getName();

        lssacla_tv_key_value.setText(conditionKey);
        lssacla_tv_name_value.setText(conditionName);
        lssacla_tv_color.setTextColor(item.getSsaColor().getColor());
        lssacla_tv_threshold.setText(item.getSsaColor().getKey());
        lssacla_tv_frequency.setText("th" + item.getThreshold() + ": " + Utils.convertFloatToStringFormatter3digit(item.getMinFrequency()) + "-" + Utils.convertFloatToStringFormatter3digit(item.getMaxFrequency()));


        return convertView;

    }

}
