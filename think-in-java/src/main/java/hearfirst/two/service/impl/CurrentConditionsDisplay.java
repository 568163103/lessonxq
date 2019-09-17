package hearfirst.two.service.impl;

import hearfirst.two.service.DisplayElement;
import hearfirst.two.service.Observer;
import hearfirst.two.service.Subject;

public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private  float temperature;
    private  float humidity;
    private Subject weatherData;

    public CurrentConditionsDisplay(Subject weatherDate){
        this.weatherData = weatherDate;
        weatherDate.registerObserver(this);
    }
    public void update(float temp, float humidity, float pressure) {
    this.temperature = temp;
    this.humidity = humidity;
    display();
    }

    public void display() {
        System.out.println("Current conditions:"+ temperature + "F degrees" + humidity +"% humidity");
    }
}
