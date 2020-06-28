package com.svoemestodev.catscitycalc.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.svoemestodev.catscitycalc.R;
import com.svoemestodev.catscitycalc.classes.Car;

import java.util.List;


public class ListCarsAdapter extends ArrayAdapter<Car> {

    private boolean checkVisible;

    public ListCarsAdapter(@NonNull Context context, List<Car> list, boolean isCheckVisible) {
        super(context, R.layout.select_car, list);
        checkVisible = isCheckVisible;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Car item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.select_car, null);
        }

        ImageView sc_iv_car_building_icon = convertView.findViewById(R.id.sc_iv_car_building_icon);
        ImageView sc_iv_car_task_icon = convertView.findViewById(R.id.sc_iv_car_task_icon);
        ImageView sc_iv_car = convertView.findViewById(R.id.sc_iv_car);
        TextView sc_tv_car_repair = convertView.findViewById(R.id.sc_tv_car_repair);
        TextView sc_tv_car_health_shield = convertView.findViewById(R.id.sc_tv_car_health_shield);
        TextView sc_tv_car_user_nic = convertView.findViewById(R.id.sc_tv_car_user_nic);
        TextView sc_tv_car_name = convertView.findViewById(R.id.sc_tv_car_name);
        CheckBox sc_cb_checked = convertView.findViewById(R.id.sc_cb_checked);

        sc_cb_checked.setVisibility(checkVisible ? View.VISIBLE : View.INVISIBLE);

        sc_tv_car_name.setText(item.getName());
        sc_tv_car_user_nic.setText(item.getUserNIC());
        sc_tv_car_health_shield.setText(item.getHealth() + "/" + item.getShield());
        sc_cb_checked.setChecked(item.isChecked());

        Bitmap carBitmap = item.isFree() ? item.getCarPicture() : item.isRepairing() ? item.getCarPictureRepairing() : item.isDefencing() ? item.getCarPictureDefencing() : item.getCarPicture() ;
        if (carBitmap != null) sc_iv_car.setImageBitmap(carBitmap);
        String carTextRepair = item.isRepairing() ? "" + item.getTimeStringToEndRepairing() : "";
        sc_tv_car_repair.setVisibility(carTextRepair.equals("") ? View.INVISIBLE : View.VISIBLE);
        sc_tv_car_repair.setText(carTextRepair);
        sc_iv_car_building_icon.setVisibility(item.getBuilding() >= 0 ? View.VISIBLE : View.INVISIBLE);
        sc_iv_car_task_icon.setVisibility(item.getBuildingTask() > 0 ? View.VISIBLE : View.INVISIBLE);
        switch (item.getBuilding()) {
            case 0:
                sc_iv_car_building_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_bxx_black));
                break;
            case 1:
                sc_iv_car_building_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_blt_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_blt_blue : R.drawable.ic_blt_red));
                break;
            case 2:
                sc_iv_car_building_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_blc_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_blc_blue : R.drawable.ic_blc_red));
                break;
            case 3:
                sc_iv_car_building_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_blb_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_blb_blue : R.drawable.ic_blb_red));
                break;
            case 4:
                sc_iv_car_building_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_brt_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_brt_blue : R.drawable.ic_brt_red));
                break;
            case 5:
                sc_iv_car_building_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_brc_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_brc_blue : R.drawable.ic_brc_red));
                break;
            case 6:
                sc_iv_car_building_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_brb_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_brb_blue : R.drawable.ic_brb_red));
                break;
            default:
        }
        switch (item.getBuildingTask()) {
            case 0:
                sc_iv_car_task_icon.setImageDrawable(getContext().getDrawable(R.drawable.ic_bxx_black));
                break;
            case 1:
                sc_iv_car_task_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_blt_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_blt_blue : R.drawable.ic_blt_red));
                break;
            case 2:
                sc_iv_car_task_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_blc_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_blc_blue : R.drawable.ic_blc_red));
                break;
            case 3:
                sc_iv_car_task_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_blb_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_blb_blue : R.drawable.ic_blb_red));
                break;
            case 4:
                sc_iv_car_task_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_brt_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_brt_blue : R.drawable.ic_brt_red));
                break;
            case 5:
                sc_iv_car_task_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_brc_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_brc_blue : R.drawable.ic_brc_red));
                break;
            case 6:
                sc_iv_car_task_icon.setImageDrawable(getContext().getDrawable(item.getBuildingTask() == -1 ? R.drawable.ic_brb_gray : item.getBuilding() == item.getBuildingTask() ? R.drawable.ic_brb_blue : R.drawable.ic_brb_red));
                break;
            default:
        }

        if (checkVisible) {
            sc_cb_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setChecked(isChecked);
                }
            });
        }


        return convertView;

    }
    
}
