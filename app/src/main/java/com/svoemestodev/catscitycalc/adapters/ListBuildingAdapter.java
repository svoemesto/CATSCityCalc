package com.svoemestodev.catscitycalc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.svoemestodev.catscitycalc.classes.Building;
import com.svoemestodev.catscitycalc.R;

public class ListBuildingAdapter extends ArrayAdapter<Building> {

    public ListBuildingAdapter(@NonNull Context context) {
        super(context, R.layout.select_bitmap, Building.loadList());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Building item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.select_bitmap, null);
        }

        ImageView select_item_bitmap = convertView.findViewById(R.id.select_item_bitmap);
        ImageView select_item_icon = convertView.findViewById(R.id.select_item_icon);
        select_item_bitmap.setImageBitmap(item.getBitmap());
        switch (item.getSlot()) {
            case 1:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_blt_black));
                break;
            case 2:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_blc_black));
                break;
            case 3:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_blb_black));
                break;
            case 4:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_brt_black));
                break;
            case 5:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_brc_black));
                break;
            case 6:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_brb_black));
                break;
            default:
        }

        return convertView;

    }

}
