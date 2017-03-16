package club.shfront.cloudly.weather;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Daily implements Parcelable {
    private long xTime;
    private String xSummary;
    private double xTempMax;
    private String xIcon;
    private String xTimezone;
    public long getTime() {
        return xTime;
    }

    public void setTime(long time) {
        xTime = time;
    }

    public String getSummary() {
        return xSummary;
    }

    public void setSummary(String summary) {
        xSummary = summary;
    }

    public int getTempMax() {
        int FTemp = (int)Math.round(xTempMax);
        return (int)Math.round((FTemp - 32)/1.8);
    }

    public void setTempMax(double tempMax) {
        xTempMax = tempMax;
    }

    public String getIcon() {
        return xIcon;
    }

    public void setIcon(String icon) {
        xIcon = icon;
    }

    public String getTimezone() {
        return xTimezone;
    }

    public void setTimezone(String timezone) {
        xTimezone = timezone;
    }

    public int getIconId() {
        return Forecast.getIconId(xIcon);
    }

    public String getDayOfTheWeek() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone(xTimezone));
        Date dataTime = new Date(xTime * 1000);
        return  format.format(dataTime);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(xTime);
        parcel.writeString(xSummary);
        parcel.writeDouble(xTempMax);
        parcel.writeString(xIcon);
        parcel.writeString(xTimezone);
    }
    private Daily(Parcel in) {
        xTime = in.readLong();
        xSummary = in.readString();
        xTempMax = in.readDouble();
        xIcon = in.readString();
        xTimezone = in.readString();
    }

    public Daily (){}

    public static final Creator<Daily> CREATOR = new Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel parcel) {
            return new Daily(parcel);
        }

        @Override
        public Daily[] newArray(int i) {
            return new Daily[i];
        }
    };
}
