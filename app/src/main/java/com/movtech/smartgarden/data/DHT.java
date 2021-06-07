package com.movtech.smartgarden.data;

public class DHT {
    private Float hum;
    private Float temp;

    public DHT(){

    }
    public DHT(Float hum, Float temp) {
        this.hum = hum;
        this.temp = temp;
    }

    public Float getHum() {
        return hum;
    }

    public Float getTemp() {
        return temp;
    }
}
