package com.multimedia.vrh;

import java.util.ArrayList;

public class CalculationResponse {
    private ArrayList<Double> x;
    private  ArrayList<Double> y;
    private ArrayList<Double> time;
    public CalculationResponse(ArrayList<Double> time,ArrayList<Double>x,ArrayList<Double>y){
        this.x =x;
        this.time = time;
        this.y = y;
    }

    public CalculationResponse() {
    }

    public ArrayList<Double> getX() {
        return x;
    }


    public ArrayList<Double> getY() {
        return y;
    }


    public ArrayList<Double> getTime() {
        return time;
    }

}
