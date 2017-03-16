package club.shfront.cloudly.weather;


import club.shfront.cloudly.R;

public class Forecast {
    private Current xCurrent;
    private Hourly[] xHourlyForecast;
    private Daily[] xDailyForecast;

    public Current getCurrent() {
        return xCurrent;
    }

    public void setCurrent(Current current) {
        xCurrent = current;
    }

    public Hourly[] getHourlyForecast() {
        return xHourlyForecast;
    }

    public void setHourlyForecast(Hourly[] hourlyForecast) {
        xHourlyForecast = hourlyForecast;
    }

    public Daily[] getDailyForecast() {
        return xDailyForecast;
    }

    public void setDailyForecast(Daily[] dailyForecast) {
        xDailyForecast = dailyForecast;
    }

    public static int getIconId(String xIcon) {
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
}
