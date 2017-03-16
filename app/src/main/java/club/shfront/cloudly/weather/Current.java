package club.shfront.cloudly.weather;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import club.shfront.cloudly.R;

public class Current {
    private String xIcon;
    private long xTime;
    private double xTemp;
    private double xHumidity;
    private double xPrecentageChance;
    private String xSummary;
    private String xTimeZone;
    private String xLocation;

    public String getIcon() {
        return xIcon;
    }

    public void setIcon(String icon) {
        xIcon = icon;
    }

    public int getIconId(){
        return Forecast.getIconId(xIcon);
    }
    public long getTime() {
        return xTime;
    }

    public String getFormatTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("kk:mm", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formatter.format(dateTime);
        return timeString;
    }
    public void setTime(long time) {
        xTime = time;
    }

    public int getTemp() {
        int FTemp = (int)Math.round(xTemp);
        return (int)Math.round((FTemp - 32)/1.8);
    }

    public void setTemp(double temp) {
        xTemp = temp;
    }

    public int getPrecentageChance() {
        return (int) Math.round(xPrecentageChance * 100);
    }

    public void setPrecentageChance(double precentageChance) {
        xPrecentageChance = precentageChance;
    }

    public String getSummary() {
        return xSummary;
    }

    public void setSummary(String summary) {
        xSummary = summary;
    }

    public double getHumidity() {
        return xHumidity;
    }

    public void setHumidity(double humidity) {
        xHumidity = humidity;
    }

    public String getTimeZone() {
        return xTimeZone;
    }

    public void setTimeZone(String timeZone) {
        xTimeZone = timeZone;
    }

    public String getLocation() {
        String[] parts =  xTimeZone.split("/");
        return parts[1];
    }

    public void setLocation(String location) {
        xLocation = location;
    }
}
