package com.multimedia.vrh;

import java.util.ArrayList;

public class Calculaction {
    private int angle;
    private int speed;
    private double time;
    public Calculaction() {
        angle = 0;
        speed = 0;
        time = 0;
    }

    private void calculateTime() {
        this.time = (2*speed*(Math.sin(Math.toRadians(angle)))/9.8);
    }


    public CalculationResponse getCalculation(int speed, int angle) {
        ArrayList<Double> time = new ArrayList<>();
        ArrayList<Double> y = new ArrayList<>();
        ArrayList<Double> x = new ArrayList<>();
        this.speed =speed;
        this.angle = angle;
        calculateTime();
        for(double i = 0; i<this.time; i = i+ 0.01){
            Double timeT = i;
            time.add(timeT);

            Double yT = this.speed*i * Math.sin(Math.toRadians(this.angle)) - (0.5)*9.8*i*i;
            y.add(yT);

            Double xT = this.speed*i * Math.cos(Math.toRadians(this.angle));
            x.add(xT);

        }

        return new CalculationResponse(time,x,y);

    }
}
