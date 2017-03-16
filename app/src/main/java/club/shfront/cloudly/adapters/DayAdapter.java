package club.shfront.cloudly.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import club.shfront.cloudly.R;
import club.shfront.cloudly.weather.Daily;
public class DayAdapter extends BaseAdapter {

    private Context xContext;
    private Daily[] xDailies;
    public DayAdapter(Context context, Daily[] dailies){
        xContext = context;
        xDailies = dailies;
    }
    @Override
    public int getCount() {
        return xDailies.length;
    }

    @Override
    public Object getItem(int i) {
        return xDailies[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(xContext).inflate(R.layout.daily_list_item,null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) view.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) view.findViewById(R.id.tempLabel);
            holder.dayLabel = (TextView) view.findViewById(R.id.dayNameLabel);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Daily daily = xDailies[i];
        holder.iconImageView.setImageResource(daily.getIconId());
        holder.temperatureLabel.setText(String.format(Locale.US,"%d", daily.getTempMax()));
        holder.dayLabel.setText(daily.getDayOfTheWeek());

        return view;
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView dayLabel;
    }
}
