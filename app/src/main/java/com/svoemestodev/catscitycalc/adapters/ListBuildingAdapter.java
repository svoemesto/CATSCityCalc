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
        if (item.getBitmap() != null) select_item_bitmap.setImageBitmap(item.getBitmap());
        select_item_bitmap.setVisibility(item.getSlot() != -1 ? View.VISIBLE : View.INVISIBLE);
        switch (item.getSlot()) {
            case -1:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_bfx_black));
                break;
            case 1:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_bld1_black));
                break;
            case 2:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_bld2_black));
                break;
            case 3:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_bld3_black));
                break;
            case 4:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_bld4_black));
                break;
            case 5:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_bld5_black));
                break;
            case 6:
                select_item_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_bld6_black));
                break;
            default:
        }

        return convertView;

    }

}
