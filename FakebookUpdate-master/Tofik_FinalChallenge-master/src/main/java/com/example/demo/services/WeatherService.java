package com.example.demo.services;

import com.example.demo.model.apis.apixu.APIXUResponse;
import com.example.demo.model.apis.apixu.ForecastDay;
import com.example.demo.model.auth.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Autowired
    AppUserRepository users;

    public Iterable<ForecastDay> forecasts(){
        RestTemplate fromApi = new RestTemplate();
        APIXUResponse response = fromApi.getForObject(apiUrlByZipCode("20910"), APIXUResponse.class);

        return response.getForecast().getForecastDays();
    }
    public Iterable<ForecastDay> forecasts(String zipcode){
        RestTemplate fromApi = new RestTemplate();
        APIXUResponse response = fromApi.getForObject(apiUrlByZipCode(zipcode), APIXUResponse.class);

        return response.getForecast().getForecastDays();
    }

    public String apiUrlByZipCode(String zipcode){
        return "https://api.apixu.com/v1/forecast.json?key=201413ff23c1475ab1f153627181807&q="+zipcode+"&days=10";
    }

    @Override
    public String toString(){//for testing
        String weatherText = "";
        for (ForecastDay forecast : forecasts()){
            weatherText += forecast.toString() + "\n\n";
        }
        return weatherText;
    }
}