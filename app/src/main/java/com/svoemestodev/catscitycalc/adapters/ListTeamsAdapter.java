package com.svoemestodev.catscitycalc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.database.DbTeam;

public class ListTeamsAdapter extends ArrayAdapter<DbTeam> {

    public ListTeamsAdapter(@NonNull Context context) {
        super(context, R.layout.select_item, DbTeam.listTeams);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final DbTeam item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.select_item, null);
        }

        TextView select_item_name = convertView.findViewById(R.id.select_item_name);
        select_item_name.setText(item.getTeamName());

        return convertView;

    }
}
