package com.movtech.smartgarden.data;

public class Tanaman1 {
    private Float moist1;
    private Float moist2;
    private Float moist3;
    
    public Tanaman1(){
        
    }

    public Tanaman1(Float moist1, Float moist2, Float moist3) {
        this.moist1 = moist1;
        this.moist2 = moist2;
        this.moist3 = moist3;
    }

    public Float getMoist1() {
        return moist1;
    }

    public Float getmoist2() {
        return moist2;
    }

    public Float getmoist3() {
        return moist3;
    }
}
