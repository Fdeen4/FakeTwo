package com.example.demo.model.apis.apixu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stat {

    private Double maxtemp_c;
    private Double maxtemp_f;
    private Double mintemp_c;
    private Double mintemp_f;
    private Double avgtemp_c;
    private Double avgtemp_f;
    private Double maxwind_mph;
    private Double maxwind_kph;
    private Double totalprecip_mm;
    private Double totalprecip_in;
    private Double avgvis_km;
    private Double avgvis_miles;
    private Integer avghumidity;
    private Map<String, String> condition;
    private Double uv;

    public Stat() {
    }

    public Double getMaxtemp_c() {
        return maxtemp_c;
    }

    public void setMaxtemp_c(Double maxtemp_c) {
        this.maxtemp_c = maxtemp_c;
    }

    public Double getMaxtemp_f() {
        return maxtemp_f;
    }

    public void setMaxtemp_f(Double maxtemp_f) {
        this.maxtemp_f = maxtemp_f;
    }

    public Double getMintemp_c() {
        return mintemp_c;
    }

    public void setMintemp_c(Double mintemp_c) {
        this.mintemp_c = mintemp_c;
    }

    public Double getMintemp_f() {
        return mintemp_f;
    }

    public void setMintemp_f(Double mintemp_f) {
        this.mintemp_f = mintemp_f;
    }

    public Double getAvgtemp_c() {
        return avgtemp_c;
    }

    public void setAvgtemp_c(Double avgtemp_c) {
        this.avgtemp_c = avgtemp_c;
    }

    public Double getAvgtemp_f() {
        return avgtemp_f;
    }

    public void setAvgtemp_f(Double avgtemp_f) {
        this.avgtemp_f = avgtemp_f;
    }

    public Double getMaxwind_mph() {
        return maxwind_mph;
    }

    public void setMaxwind_mph(Double maxwind_mph) {
        this.maxwind_mph = maxwind_mph;
    }

    public Double getMaxwind_kph() {
        return maxwind_kph;
    }

    public void setMaxwind_kph(Double maxwind_kph) {
        this.maxwind_kph = maxwind_kph;
    }

    public Double getTotalprecip_mm() {
        return totalprecip_mm;
    }

    public void setTotalprecip_mm(Double totalprecip_mm) {
        this.totalprecip_mm = totalprecip_mm;
    }

    public Double getTotalprecip_in() {
        return totalprecip_in;
    }

    public void setTotalprecip_in(Double totalprecip_in) {
        this.totalprecip_in = totalprecip_in;
    }

    public Double getAvgvis_km() {
        return avgvis_km;
    }

    public void setAvgvis_km(Double avgvis_km) {
        this.avgvis_km = avgvis_km;
    }

    public Double getAvgvis_miles() {
        return avgvis_miles;
    }

    public void setAvgvis_miles(Double avgvis_miles) {
        this.avgvis_miles = avgvis_miles;
    }

    public Integer getAvghumidity() {
        return avghumidity;
    }

    public void setAvghumidity(Integer avghumidity) {
        this.avghumidity = avghumidity;
    }

    public Map<String, String> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, String> condition) {
        this.condition = condition;
    }

    public Double getUv() {
        return uv;
    }

    public void setUv(Double uv) {
        this.uv = uv;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "maxtemp_c=" + maxtemp_c +
                ", maxtemp_f=" + maxtemp_f +
                ", mintemp_c=" + mintemp_c +
                ", mintemp_f=" + mintemp_f +
                ", avgtemp_c=" + avgtemp_c +
                ", avgtemp_f=" + avgtemp_f +
                ", maxwind_mph=" + maxwind_mph +
                ", maxwind_kph=" + maxwind_kph +
                ", totalprecip_mm=" + totalprecip_mm +
                ", totalprecip_in=" + totalprecip_in +
                ", avgvis_km=" + avgvis_km +
                ", avgvis_miles=" + avgvis_miles +
                ", avghumidity=" + avghumidity +
                ", condition=" + condition +
                ", uv=" + uv +
                '}';
    }
}