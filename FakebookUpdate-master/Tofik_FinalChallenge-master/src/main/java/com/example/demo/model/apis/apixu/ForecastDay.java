package com.example.demo.model.apis.apixu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDay {
    private LocalDate date;
    private Instant date_epoch;
    @JsonProperty("day")
    private Stat stat;
    private Map<String, String> astro;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getDate_epoch() {
        return date_epoch;
    }

    public void setDate_epoch(Instant date_epoch) {
        this.date_epoch = date_epoch;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public Map<String, String> getAstro() {
        return astro;
    }

    public void setAstro(Map<String, String> astro) {
        this.astro = astro;
    }

    @Override
    public String toString() {
        return "ForecastDay{" +
                "date=" + date +
                ", date_epoch=" + date_epoch +
                ", stat=" + stat +
                ", astro=" + astro +
                '}';
    }
}