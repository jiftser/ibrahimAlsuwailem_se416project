package com.example.ibrahimalsuwailem_se416project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {
    String weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q=ariana,tn&appid=2156e2dd5b92590ab69c0ae1b2d24586&units=metric";
    TextView temperature, description, city, humidity, windSpeed;
    JSONObject jsonObj;
    ImageView weatherBackground;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        temperature = (TextView) findViewById(R.id.temperature);
        description = (TextView) findViewById(R.id.description);
        city = (TextView) findViewById(R.id.city);
        humidity = (TextView) findViewById(R.id.humidity);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        weatherBackground = (ImageView) findViewById(R.id.weatherbackground);
        spinner = (Spinner)findViewById(R.id.spinner);
        Button bttn = (Button)findViewById(R.id.button);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner.getSelectedItem().toString().equals("Riyadh")){
                    String url = "http://api.openweathermap.org/data/2.5/weather?q=Riyadh&appid=60d15afa0289eccd91ed24cb07bf0b28&units=metric";
                    weather(url);
                } else if(spinner.getSelectedItem().toString().equals("London")){
                    String url = "http://api.openweathermap.org/data/2.5/weather?q=London&appid=60d15afa0289eccd91ed24cb07bf0b28&units=metric";
                    weather(url);
                }
            }
        });

    }

    public void weather(String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Ibrahim", "Response received");
                Log.d("Ibrahim", response.toString());
                try{
                    String town = response.getString("name");
                    Log.d("Ibrahim-town", town);
                    JSONObject jsonMain = response.getJSONObject("main");
                    double temp = jsonMain.getDouble("temp");
                    int humid = jsonMain.getInt("humidity");
                    Log.d("Ibrahim-temp", String.valueOf(temp));
                    temperature.setText(String.valueOf(temp));
                    city.setText(town);
                    humidity.setText("Humidity: "+String.valueOf(humid));
                    JSONObject jsonWind = response.getJSONObject("wind");
                    double wind = jsonWind.getDouble("speed");
                    windSpeed.setText("Wind speed: "+String.valueOf(wind));
                    JSONArray jsonWeatherArray = response.getJSONArray("weather");
                    Log.d("Ibrahim-weather", jsonWeatherArray.toString());
                    chooseBackground(jsonWeatherArray);
                }catch (JSONException e){
                    e.printStackTrace();
                    Log.e("Receive error", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Ibrahim", "Error retrieving url");
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }

    public void chooseBackground(JSONArray jsonWeatherArray){
        try{
            for (int i=0; i < jsonWeatherArray.length(); i++)
            {
                JSONObject oneObject = jsonWeatherArray.getJSONObject(i);
                // Pulling items from the array
                String weather = oneObject.getString("main");
                Log.d("Ibrahim-clouds", weather);
                description.setText(weather);
                if(weather.equals("Clear")){
                    Glide.with(this).load("https://lh3.googleusercontent.com/tFLhMKVH90elgpEiYflqzUv8W6sBlefoxkFH7TpmT-8bRWpIJQaEl6GZjAZ9RIPUziZhtRU9=w640-h400-e365-rj-sc0x00ffffff").into(weatherBackground);
                }else if(weather.equals("Clouds")) {
                    Glide.with(this).load("https://img.freepik.com/free-photo/black-rain-abstract-dark-power_1127-2380.jpg?size=626&ext=jpg").into(weatherBackground);
                }else if(weather.equals("Rain")) {
                    Glide.with(this).load("https://quevedoportwine.com/wp-content/uploads/2013/09/rain.jpeg").into(weatherBackground);
                }else{
                    Glide.with(this).load("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOAAAADgCAMAAAAt85rTAAAAA1BMVEX///+nxBvIAAAASElEQVR4nO3BMQEAAADCoPVPbQo/oAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAICXAcTgAAG6EJuyAAAAAElFTkSuQmCC").into(weatherBackground);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("JSONArray error", e.toString());
        }
    }
}