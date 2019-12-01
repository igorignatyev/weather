package ru.ignatev.weather.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ignatev.weather.model.CityWeather;
import ru.ignatev.weather.repository.CityWeatherRepository;
import ru.ignatev.weather.util.TemperatureHandler;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author ignatyev.i.s
 * @ created 2019-12-01
 */
@SpringBootTest
class CityWeatherServiceTest {
    @InjectMocks
    CityWeatherService cityWeatherService;

    @Mock
    CityWeatherRepository cityWeatherRepository;

    @Mock
    TemperatureHandler temperatureHandler;

    @Test
    void findByCity() {
        CityWeather cityWeather = new CityWeather();
        cityWeather.setCity("Moscow");
        when(cityWeatherRepository.findById("Moscow")).thenReturn(Optional.of(cityWeather));

        Assertions.assertEquals(cityWeather, cityWeatherService.findByCity("Moscow"));
    }

    @Test
    void findAll() {
        List<CityWeather> expectedCityWeatherList = Arrays.asList(
                new CityWeather("Moscow", new BigDecimal(2.5), new Date(), CityWeather.Status.PAUSED),
                new CityWeather("Saint Petersburg", new BigDecimal(3), new Date(), CityWeather.Status.ACTIVE),
                new CityWeather("London", new BigDecimal(-2.5), new Date(), CityWeather.Status.PAUSED)
        );
        when(cityWeatherRepository.findAll()).thenReturn(expectedCityWeatherList);

        Assertions.assertIterableEquals(expectedCityWeatherList, cityWeatherService.findAll());
        verify(cityWeatherRepository, times(1)).findAll();
    }

    @Test
    void save() {
        CityWeather cityWeather = new CityWeather();
        cityWeatherService.save(cityWeather);
        List<CityWeather> expected = Collections.singletonList(cityWeather);
        when(cityWeatherRepository.findAll()).thenReturn(expected);

        verify(cityWeatherRepository, times(1)).save(cityWeather);
        Assertions.assertIterableEquals(expected, cityWeatherService.findAll());
    }

    @Test
    void getCityWeatherAndSave() {
        Date beforeTest = new Date();
        when(temperatureHandler.getTemperatureFromRemoteServer(anyString())).thenReturn(BigDecimal.valueOf(2.5));
        CityWeather cityWeather = cityWeatherService.getCityWeatherAndSave("Moscow");

        assertEquals("Moscow", cityWeather.getCity());
        assertEquals(BigDecimal.valueOf(2.5), cityWeather.getTemperature());
        assertTrue(beforeTest.before(cityWeather.getLastSync()));
        assertEquals(CityWeather.Status.ACTIVE, cityWeather.getStatus());
    }

    @Test
    void delete() {
        CityWeather cityWeather = new CityWeather();
        cityWeatherService.delete(cityWeather);

        verify(cityWeatherRepository, times(1)).delete(cityWeather);
    }

    @Test
    void updateTemperature() {
        CityWeather moscowCityWeather = new CityWeather("Moscow", new BigDecimal(2.5), new Date(), CityWeather.Status.PAUSED);
        CityWeather spbCityWeather = new CityWeather("Saint Petersburg", new BigDecimal(3), new Date(), CityWeather.Status.ACTIVE);
        CityWeather londonCityWeather = new CityWeather("London", new BigDecimal(-2.5), new Date(), CityWeather.Status.PAUSED);
        CityWeatherService spyCityWeatherService = spy(cityWeatherService);
        when(cityWeatherRepository.findAll()).thenReturn(Arrays.asList(
                moscowCityWeather,
                spbCityWeather,
                londonCityWeather
        ));
        spyCityWeatherService.updateTemperature();

        verify(spyCityWeatherService, times(0)).getCityWeatherAndSave("Moscow");
        verify(spyCityWeatherService, times(1)).getCityWeatherAndSave("Saint Petersburg");
        verify(spyCityWeatherService, times(0)).getCityWeatherAndSave("London");
    }
}