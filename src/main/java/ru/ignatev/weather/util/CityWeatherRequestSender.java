package ru.ignatev.weather.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

/**
 * @author ignatyev.i.s
 * @ created 2019-11-30
 */
@Component
public class CityWeatherRequestSender {
    private static final String PLACEHOLDER = "_";
    private static final String URI = "http://api.openweathermap.org/data/2.5/weather?q=" +
                                      PLACEHOLDER +
                                      "&APPID=84c29af20b2f0c27696b535084eec8a9";

    public JSONObject getResponse(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(URI.replace(PLACEHOLDER, city), String.class);
        return new JSONObject(response);
    }
}
