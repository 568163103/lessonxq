package headfirst.two.demo;

import headfirst.two.domain.WeatherData;
import headfirst.two.service.impl.CurrentConditionsDisplay;

public class WeatherStation {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentConditionsDisplay  = new CurrentConditionsDisplay(weatherData);
        weatherData.setMeasurements(80,60,30.4f);
        weatherData.setMeasurements(82,70,20.4f);
        weatherData.setMeasurements(90,70,20.4f);
    }
}
