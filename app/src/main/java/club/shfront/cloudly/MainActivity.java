package club.shfront.cloudly;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private CurrentWeather xCurrentWeather;


    @BindView(R.id.timeLabel) TextView xTimeLabel;
    @BindView(R.id.tempLabel) TextView xTempLabel;
    @BindView(R.id.humidityValue) TextView xHumidityValue;
    @BindView(R.id.precentageValue) TextView xPercentageValue;
    @BindView(R.id.summaryValue) TextView xSummaryValue;
    @BindView(R.id.iconImageView) ImageView xIconImageView;
    @BindView(R.id.refreshimageView) ImageView xRefreshImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        xRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        String apiKey = "7d9ad9898f1ac7024b40e8b42e3102ae";
        double latitude = 31.229316;
        double longitude = 121.470591;
        String forecast = "https://api.darksky.net/forecast/" + apiKey +
                "/" + latitude + "," + longitude;
        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecast).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            xCurrentWeather = getCurrentDetails(jsonData);
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
        Log.d(TAG,"Main UI");

    }

    private void updateData() {
        xTempLabel.setText(String.format("%s", xCurrentWeather.getTemp()));
        xTimeLabel.setText("At " + xCurrentWeather.getFormatTime() + " it will be");
        xHumidityValue.setText(String.format("%s", xCurrentWeather.getHumidity()));
        xPercentageValue.setText(String.format("%s%%", xCurrentWeather.getPrecentageChance()));
        xSummaryValue.setText(xCurrentWeather.getSummary());
        Drawable iconDrawable = getResources().getDrawable(xCurrentWeather.getIconId());
        xIconImageView.setImageDrawable(iconDrawable);
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");
        Log.i(TAG,"From JSON: " + timeZone);
        JSONObject currently = forecast.getJSONObject("currently");
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setPrecentageChance(currently.getDouble("precipProbability"));
        currentWeather.setTemp(currently.getDouble("temperature"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTimeZone(timeZone);
        Log.d(TAG,currentWeather.getFormatTime());
        return currentWeather;
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
