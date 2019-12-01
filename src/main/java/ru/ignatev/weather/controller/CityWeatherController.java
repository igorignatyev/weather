package ru.ignatev.weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ignatev.weather.model.CityWeather;
import ru.ignatev.weather.service.CityWeatherService;

/**
 * @author ignatyev.i.s
 * @ created 2019-11-30
 */
@Controller
public class CityWeatherController {
    private final CityWeatherService service;

    public CityWeatherController(CityWeatherService service) {
        this.service = service;
    }

    @GetMapping("all")
    @ResponseBody
    public Iterable<CityWeather> all() {
        return service.findAll();
    }

    @PostMapping("{city}")
    @ResponseBody
    public CityWeather add(@PathVariable String city) {
        return service.getCityWeatherAndSave(city);
    }

    @DeleteMapping("{city}")
    @ResponseBody
    public void delete(@PathVariable String city) {
        CityWeather cityWeather = service.findByCity(city);
        service.delete(cityWeather);
    }

    @PostMapping("set/{city}")
    @ResponseBody
    public CityWeather setStatus(@PathVariable String city, @RequestParam CityWeather.Status status) {
        CityWeather cityWeather = service.findByCity(city);
        cityWeather.setStatus(status);
        service.save(cityWeather);

        return cityWeather;
    }
}
