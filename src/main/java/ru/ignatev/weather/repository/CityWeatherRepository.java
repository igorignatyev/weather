package ru.ignatev.weather.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ignatev.weather.model.CityWeather;

/**
 * @author ignatyev.i.s
 * @ created 2019-11-30
 */
public interface CityWeatherRepository extends CrudRepository<CityWeather, String> {
}
