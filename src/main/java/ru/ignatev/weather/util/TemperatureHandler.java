package ru.ignatev.weather.util;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author ignatyev.i.s
 * @ created 2019-11-30
 */
@Component
public class TemperatureHandler {
    private final CityWeatherRequestSender cityWeatherRequestSender;

    public TemperatureHandler(CityWeatherRequestSender cityWeatherRequestSender) {
        this.cityWeatherRequestSender = cityWeatherRequestSender;
    }

    private double fromKelvinToCelsius(double temperature) {
        return temperature - 273.15;
    }

    private BigDecimal getFormattedTemperature(JSONObject response) {
        double temperature = response.getJSONObject("main").getDouble("temp");
        BigDecimal bigDecimalTemperature = BigDecimal.valueOf(fromKelvinToCelsius(temperature));
        return bigDecimalTemperature.setScale(1, RoundingMode.HALF_UP);
    }

    public BigDecimal getTemperatureFromRemoteServer(String city) {
        JSONObject response = cityWeatherRequestSender.getResponse(city);
        return getFormattedTemperature(response);
    }
}
