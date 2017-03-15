package club.shfront.cloudly;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CurrentWeather {
    private String xIcon;
    private long xTime;
    private double xTemp;
    private double xHumidity;
    private double xPrecentageChance;
    private String xSummary;
    private String xTimeZone;

    public String getIcon() {
        return xIcon;
    }

    public void setIcon(String icon) {
        xIcon = icon;
    }

    public int getIconId(){
        int iconId = R.mipmap.clear_day;
        if (xIcon.equals("clear-day")) {
            iconId = R.mipmap.clear_day;
        }
        else if (xIcon.equals("clear-night")) {
            iconId = R.mipmap.clear_night;
        }
        else if (xIcon.equals("rain")) {
            iconId = R.mipmap.rain;
        }
        else if (xIcon.equals("snow")) {
            iconId = R.mipmap.snow;
        }
        else if (xIcon.equals("sleet")) {
            iconId = R.mipmap.sleet;
        }
        else if (xIcon.equals("wind")) {
            iconId = R.mipmap.wind;
        }
        else if (xIcon.equals("fog")) {
            iconId = R.mipmap.fog;
        }
        else if (xIcon.equals("cloudy")) {
            iconId = R.mipmap.cloudy;
        }
        else if (xIcon.equals("partly-cloudy-day")) {
            iconId = R.mipmap.partly_cloudy;
        }
        else if (xIcon.equals("partly-cloudy-night")) {
            iconId = R.mipmap.cloudy_night;
        }
        return iconId;
    }
    public long getTime() {
        return xTime;
    }

    public String getFormatTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.US);
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
        String[] parts = xTimeZone.split("/");
        return parts[1];
    }

    public void setTimeZone(String timeZone) {
        xTimeZone = timeZone;
    }
}
