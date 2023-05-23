package com.aman.weatherapp.weatherapp.WeekActivityClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aman.weatherapp.weatherapp.DataBeanClass.HourlyDataBean;
import com.aman.weatherapp.weatherapp.MainActivity;
import com.aman.weatherapp.R;

import java.util.ArrayList;

public class MainActivityAdapterhandler extends RecyclerView.Adapter<MainActivityAdapterhandler.ViewHolder> {
    MainActivity mainActivity;
    ArrayList<HourlyDataBean> hourlyRecycler;

    View viewer;
    public MainActivityAdapterhandler(MainActivity mainActivity, ArrayList<HourlyDataBean> hourlyRecycler) {
        this.mainActivity = mainActivity;
        this.hourlyRecycler = hourlyRecycler;
    }

    @NonNull
    @Override
    public MainActivityAdapterhandler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewer = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view1_hr, parent, false);
        return new ViewHolder(viewer);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityAdapterhandler.ViewHolder holder, int position) {
        String hrlyicon = hourlyRecycler.get(position).getIcons();
        hrlyicon = hrlyicon.replace("-", "_");
        int resId = mainActivity.getResources().getIdentifier(hrlyicon,
                "drawable", mainActivity.getPackageName());
        holder.times.setText(hourlyRecycler.get(position).getTimes());
        holder.days.setText(hourlyRecycler.get(position).getHoursDtimeEp());
        holder.description.setText(hourlyRecycler.get(position).getConditions());
        holder.temperature.setText(hourlyRecycler.get(position).getTemperature());
        holder.icons.setImageResource(resId);
    }

    @Override
    public int getItemCount() {
        return hourlyRecycler.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView days, times, temperature, description;
        ImageView icons;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            days = itemView.findViewById(R.id.v1_hourly_view);
            times = itemView.findViewById(R.id.v2_hourly_view);
            temperature = itemView.findViewById(R.id.v3_hourly_view);
            description = itemView.findViewById(R.id.v4_hourly_view);
            icons = itemView.findViewById(R.id.hourly_view_logo);
        }
    }
}
