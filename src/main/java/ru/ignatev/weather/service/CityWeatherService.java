package ru.ignatev.weather.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ignatev.weather.model.CityWeather;
import ru.ignatev.weather.exception.NotFoundException;
import ru.ignatev.weather.repository.CityWeatherRepository;
import ru.ignatev.weather.util.TemperatureHandler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.StreamSupport;

/**
 * @author ignatyev.i.s
 * @ created 2019-12-01
 */
@Service
public class CityWeatherService {
    private final CityWeatherRepository repository;
    private final TemperatureHandler temperatureHandler;

    public CityWeatherService(CityWeatherRepository repository, TemperatureHandler temperatureHandler) {
        this.repository = repository;
        this.temperatureHandler = temperatureHandler;
    }

    public CityWeather findByCity(String city) {
        return repository.findById(city).orElseThrow(NotFoundException::new);
    }

    public Iterable<CityWeather> findAll() {
        return repository.findAll();
    }

    public CityWeather save(CityWeather cityWeather) {
        repository.save(cityWeather);
        return cityWeather;
    }

    public CityWeather getCityWeatherAndSave(String city) {
        BigDecimal temperature = temperatureHandler.getTemperatureFromRemoteServer(city);
        Date lastSync = new Date();
        CityWeather.Status status = CityWeather.Status.ACTIVE;
        CityWeather cityWeather = new CityWeather(city, temperature, lastSync, status);

        return save(cityWeather);
    }

    public void delete(CityWeather cityWeather) {
        repository.delete(cityWeather);
    }

    @Scheduled(fixedRate = 60000)
    public void updateTemperature() {
        Iterable<CityWeather> cityWeathers = repository.findAll();
        StreamSupport.stream(cityWeathers.spliterator(), true)
                .filter(c -> c.getStatus() == CityWeather.Status.ACTIVE)
                .forEach(c -> getCityWeatherAndSave(c.getCity()));
    }
}
