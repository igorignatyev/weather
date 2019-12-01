package ru.ignatev.weather.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ignatyev.i.s
 * @ created 2019-11-30
 */
@Entity
public class CityWeather {
    @Id
    @Column(name = "name")
    private String city;

    @Column(name = "temp")
    private BigDecimal temperature;

    @JsonFormat(pattern = "HH:mm dd.MM.YYYY", timezone = "GMT+3:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSync;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        ACTIVE,
        PAUSED
    }

    public CityWeather() {
    }

    public CityWeather(String city, BigDecimal temperature, Date lastSync, Status status) {
        this.city = city;
        this.temperature = temperature;
        this.lastSync = lastSync;
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public Date getLastSync() {
        return lastSync;
    }

    public void setLastSync(Date lastSync) {
        this.lastSync = lastSync;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
