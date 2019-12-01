package ru.ignatev.weather.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ignatev.weather.model.CityWeather;
import ru.ignatev.weather.service.CityWeatherService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author ignatyev.i.s
 * @ created 2019-12-01
 */
@SpringBootTest
class CityWeatherControllerTest {
    @InjectMocks
    CityWeatherController cityWeatherController;

    @Mock
    CityWeatherService cityWeatherService;

    @Test
    void all() {
        List<CityWeather> expected = Collections.singletonList(new CityWeather());
        when(cityWeatherService.findAll()).thenReturn(expected);

        assertIterableEquals(expected, cityWeatherController.all());
        verify(cityWeatherService, times(1)).findAll();
    }

    @Test
    void add() {
        CityWeather cityWeather = new CityWeather();
        when(cityWeatherService.getCityWeatherAndSave("Moscow")).thenReturn(cityWeather);

        assertEquals(cityWeather, cityWeatherController.add("Moscow"));
        verify(cityWeatherService, times(1)).getCityWeatherAndSave("Moscow");
    }

    @Test
    void delete() {
        CityWeather cityWeather = new CityWeather();
        when(cityWeatherService.findByCity("London")).thenReturn(cityWeather);
        cityWeatherController.delete("London");

        verify(cityWeatherService, times(1)).delete(cityWeather);
    }

    @Test
    void setStatus() {
        CityWeather cityWeather = new CityWeather();
        when(cityWeatherService.findByCity("London")).thenReturn(cityWeather);
        cityWeatherController.setStatus("London", CityWeather.Status.ACTIVE);

        assertEquals(CityWeather.Status.ACTIVE, cityWeather.getStatus());
        verify(cityWeatherService, times(1)).save(cityWeather);
    }
}