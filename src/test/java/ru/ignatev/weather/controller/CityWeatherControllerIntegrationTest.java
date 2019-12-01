package ru.ignatev.weather.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.ignatev.weather.Application;
import ru.ignatev.weather.model.CityWeather;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ignatyev.i.s
 * @ created 2019-12-01
 */
@SpringBootTest(classes = Application.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql({"schema.sql", "data.sql"})
class CityWeatherControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void allCityWeathersTest() {
        List cityWeathersList = restTemplate.getForObject("http://localhost:" + port + "/all", List.class);
        assertEquals(3, cityWeathersList.size());
    }

    @Test
    void addCityWeatherTest() {
        CityWeather cityWeather = restTemplate.postForObject(
                "http://localhost:" + port + "/Saint Petersburg",
                null,
                CityWeather.class);
        List cityWeathersList = restTemplate.getForObject("http://localhost:" + port + "/all", List.class);

        assertEquals(4, cityWeathersList.size());
        assertEquals("Saint Petersburg", cityWeather.getCity());
    }

    @Test
    void deleteCityWeatherTest() {
        restTemplate.delete("http://localhost:" + port + "/Moscow");
        List cityWeathersList = restTemplate.getForObject("http://localhost:" + port + "/all", List.class);
        assertEquals(2, cityWeathersList.size());
    }

    @Test
    void setStatusTest() {
        CityWeather cityWeather = restTemplate.postForObject(
                "http://localhost:" + port + "/set/Ukhta?status=PAUSED",
                null,
                CityWeather.class);

        assertEquals(CityWeather.Status.PAUSED, cityWeather.getStatus());
    }

}
