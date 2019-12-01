package ru.ignatev.weather.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;

/**
 * @author ignatyev.i.s
 * @ created 2019-12-01
 */
class TemperatureHandlerTest {
    @InjectMocks
    private TemperatureHandler temperatureHandler;

    @Mock
    private CityWeatherRequestSender cityWeatherRequestSender;

    @BeforeEach
    void setUp() throws IOException, JSONException {
        MockitoAnnotations.initMocks(this);

        String jsonLondon = new String(Files.readAllBytes(Paths.get("src/test/resources/london.json")));
        String jsonSpb = new String(Files.readAllBytes(Paths.get("src/test/resources/spb.json")));
        JSONObject jsonObjectLondon = new JSONObject(jsonLondon);
        JSONObject jsonObjectSpb = new JSONObject(jsonSpb);
        when(cityWeatherRequestSender.getResponse("London")).thenReturn(jsonObjectLondon);
        when(cityWeatherRequestSender.getResponse("Saint Petersburg")).thenReturn(jsonObjectSpb);
    }

    @Test
    void getTemperatureFromRemoteServer() {
        BigDecimal londonActual = temperatureHandler.getTemperatureFromRemoteServer("London");
        BigDecimal spbActual = temperatureHandler.getTemperatureFromRemoteServer("Saint Petersburg");

        Assertions.assertEquals(BigDecimal.valueOf(6.7), londonActual);
        Assertions.assertEquals(BigDecimal.valueOf(-1.3), spbActual);
    }
}