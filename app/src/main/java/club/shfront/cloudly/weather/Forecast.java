package club.shfront.cloudly.weather;


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
}
