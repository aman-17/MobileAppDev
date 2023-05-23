package com.aman.weatherapp.weatherapp.WeekActivityClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aman.weatherapp.weatherapp.DataBeanClass.DaysDataBean;
import com.aman.weatherapp.R;

import java.util.List;

public class WeeklyActAdapterhandler extends RecyclerView.Adapter<WeeklyActAdapterhandler.ViewHolder>{
    WeekActivity weekActivity;
    List<DaysDataBean> daysBeanList;
    View viewr;
    public WeeklyActAdapterhandler(WeekActivity weekActivity, List<DaysDataBean> daysBeanList) {
        this.weekActivity = weekActivity;

        this.daysBeanList = daysBeanList;
    }

    @NonNull
    @Override
    public WeeklyActAdapterhandler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         viewr = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view2_week, parent, false);
        return new ViewHolder(viewr);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyActAdapterhandler.ViewHolder holder, int position) {
        holder.tvweeklyDescription.setText(daysBeanList.get(position).getDesc());
        holder.tvweeklyPrecip.setText(daysBeanList.get(position).getPrecipprobility());
        holder.tvweeklyDay.setText(daysBeanList.get(position).getDtTimeEp());
        holder.tvminmaxTemp.setText(daysBeanList.get(position).getTempratureMax());

        holder.tvnoonTemp.setText(daysBeanList.get(position).getAfternoontime());
        holder.tvevenTemp.setText(daysBeanList.get(position).getEveningtime());
        holder.tvuvIndexWeekly.setText(daysBeanList.get(position).getuVIndex());
        holder.tvmornTemp.setText(daysBeanList.get(position).getMorningtime());

        holder.tvnightTemp.setText(daysBeanList.get(position).getNighttime());

        String icon = daysBeanList.get(position).getIcons().replace("-", "_");
        int iconResId = weekActivity.getResources().getIdentifier(icon,
                "drawable", weekActivity.getPackageName());
        holder.tvimageView.setImageResource(iconResId);
    }

    @Override
    public int getItemCount() {
        return daysBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvweeklyDay, tvminmaxTemp, tvweeklyDescription;
        TextView tvweeklyPrecip, tvuvIndexWeekly, tvmornTemp, tvnoonTemp, tvevenTemp, tvnightTemp;
        ImageView tvimageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvweeklyDescription = itemView.findViewById(R.id.v3_week_view);
            tvweeklyPrecip = itemView.findViewById(R.id.v4_week_view);
            tvweeklyDay = itemView.findViewById(R.id.v1_week_view);
            tvminmaxTemp = itemView.findViewById(R.id.v2_week_view);

            tvuvIndexWeekly = itemView.findViewById(R.id.v5_week_view);
            tvmornTemp = itemView.findViewById(R.id.v8_week_view);
            tvnightTemp = itemView.findViewById(R.id.v14_week_view);
            tvimageView = itemView.findViewById(R.id.v6_week_view);
            tvnoonTemp = itemView.findViewById(R.id.v10_week_view);
            tvevenTemp = itemView.findViewById(R.id.v12_week_view);

        }
    }
}
