package club.shfront.cloudly.weather;


public class Daily {
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

    public double getTempMax() {
        return xTempMax;
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
}
