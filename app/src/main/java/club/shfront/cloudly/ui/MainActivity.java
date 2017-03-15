package club.shfront.cloudly.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.shfront.cloudly.R;
import club.shfront.cloudly.weather.Current;
import club.shfront.cloudly.weather.Daily;
import club.shfront.cloudly.weather.Forecast;
import club.shfront.cloudly.weather.Hourly;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Forecast xForecast;


    @BindView(R.id.timeLabel) TextView xTimeLabel;
    @BindView(R.id.tempLabel) TextView xTempLabel;
    @BindView(R.id.humidityValue) TextView xHumidityValue;
    @BindView(R.id.precentageValue) TextView xPercentageValue;
    @BindView(R.id.summaryValue) TextView xSummaryValue;
    @BindView(R.id.iconImageView) ImageView xIconImageView;
    @BindView(R.id.refreshimageView) ImageView xRefreshImageView;
    @BindView(R.id.progressBar) ProgressBar xProgressBar;
    @BindView(R.id.locationLabel) TextView xLocationLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        xProgressBar.setVisibility(View.INVISIBLE);
        final double latitude = 31.229316;
        final double longitude = 121.470591;
        xRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getForecast(latitude,longitude);
            }
        });
        getForecast(latitude,longitude);
        Log.d(TAG,"Main UI");

    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "7d9ad9898f1ac7024b40e8b42e3102ae";
        String forecastUrl = "https://api.darksky.net/forecast/" + apiKey +
                "/" + latitude + "," + longitude;
        if (isNetworkAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            xForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateData();
                                }
                            });

                        } else {
                            alertUserError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught:", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException caught:", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, R.string.network_condition, Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleRefresh() {
        if (xProgressBar.getVisibility() == View.INVISIBLE) {
            xProgressBar.setVisibility(View.VISIBLE);
            xRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            xProgressBar.setVisibility(View.INVISIBLE);
            xRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateData() {
        Current current = xForecast.getCurrent();
        xTempLabel.setText(String.format("%s", current.getTemp()));
        xTimeLabel.setText("At " + current.getFormatTime() + " it will be");
        xHumidityValue.setText(String.format("%s", current.getHumidity()));
        xPercentageValue.setText(String.format("%s%%", current.getPrecentageChance()));
        xSummaryValue.setText(current.getSummary());
        Drawable iconDrawable = getResources().getDrawable(current.getIconId());
        xIconImageView.setImageDrawable(iconDrawable);
        xLocationLabel.setText(current.getTimeZone());

    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();
        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));
        return forecast;
    }

    private Daily[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");
        Daily[] dailys = new Daily[data.length()];
        for (int i = 0;i < data.length();i++){
            JSONObject jsonDaily = data.getJSONObject(i);
            Daily day = new Daily();
            day.setTimezone(timeZone);
            day.setSummary(jsonDaily.getString("summary"));
            day.setTempMax(jsonDaily.getDouble("temperatureMax"));
            day.setTime(jsonDaily.getLong("time"));
            dailys[i] = day;
        }
        return dailys;
    }

    private Hourly[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");
        Hourly[] hours = new Hourly[data.length()];
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hourly hour = new Hourly();
            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemp(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timeZone);
            hours[i] = hour;
        }
        return hours;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");
        Log.i(TAG,"From JSON: " + timeZone);
        JSONObject currently = forecast.getJSONObject("currently");
        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecentageChance(currently.getDouble("precipProbability"));
        current.setTemp(currently.getDouble("temperature"));
        current.setSummary(currently.getString("summary"));
        current.setTimeZone(timeZone);
        Log.d(TAG, current.getFormatTime());
        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error_dialog");
    }
}
