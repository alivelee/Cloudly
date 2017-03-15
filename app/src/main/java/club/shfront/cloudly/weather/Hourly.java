package club.shfront.cloudly.weather;


public class Hourly {
    private long xTime;
    private String xSummary;
    private double xTemp;
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

    public double getTemp() {
        return xTemp;
    }

    public void setTemp(double temp) {
        xTemp = temp;
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
}
