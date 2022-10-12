package com.multimedia.vrh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GraphActivity extends AppCompatActivity {
    TextView timeText;
    TextView xText;
    TextView yText;
    private LineChart chart;
    public int counter;
    private int velocity;
    private int heigth;
    private boolean serverComputation;
    ArrayList<Double> xCoordinates = new ArrayList<>();
    ArrayList<Double> yCoordinates = new ArrayList<>();
    ArrayList<Double>timeList = new ArrayList<>();
    OkHttpClient client;
    Button button;
    CanvasView canvasView;
    Button animationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_graph);
        chart  = findViewById(R.id.chart);
        xText = findViewById(R.id.textView12);
        yText = findViewById(R.id.textView13);
        timeText = findViewById(R.id.textView11);
        client = new OkHttpClient();
        Bundle b = getIntent().getExtras();
        button = findViewById(R.id.button);
        canvasView = findViewById(R.id.canvasView);
        chart.getDescription().setEnabled(false);

        animationButton = findViewById(R.id.button2);
        if(b!= null)
            retrieveMessage();

        animationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                draw();
            }
        });
        if(serverComputation) {
            try {
                String url = "http://147.175.182.155:4567/computation?angle="+String.valueOf(heigth)+"&velocity="+String.valueOf(velocity);
                Gson gson = new Gson();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            GraphActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CalculationResponse calculationResponse =  gson.fromJson(myResponse, CalculationResponse.class);
                                    System.out.println(myResponse);
                                    xCoordinates = calculationResponse.getX();
                                    xText.setText(myResponse);
                                    yCoordinates = calculationResponse.getY();
                                    timeList = calculationResponse.getTime();

                                    draw();
                                }
                            });
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            Calculaction calculaction = new Calculaction();
            CalculationResponse calculationResponse = calculaction.getCalculation(velocity,heigth);

            xCoordinates= calculationResponse.getX();
            yCoordinates= calculationResponse.getY();
            timeList = calculationResponse.getTime();
            draw();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivitiesWithData();

            }
        });

    }

    private void switchActivitiesWithData() {
        Intent switchActivityIntent = new Intent(this, TableActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("x",xCoordinates);
        extras.putSerializable("y",yCoordinates);
        extras.putSerializable("time",timeList);
        switchActivityIntent.putExtras(extras);
        startActivity(switchActivityIntent);
    }

    public void refreshGraph(LineDataSet dataSet){
        LineData data = new LineData(dataSet);
        chart.setData(data);
        chart.notifyDataSetChanged();
        chart.invalidate();

    }
    private void retrieveMessage() {
        Bundle data = getIntent().getExtras();

        this.velocity = data.getInt("velocity",0);
        this.heigth = data.getInt("heigth",0);
        this.serverComputation = data.getBoolean("serverComputation");
    }
    public Entry createEntry(Double x, Double y){
        return new Entry(x.floatValue(),y.floatValue());

    }
    @SuppressLint("SetTextI18n")
    public void setCoordinateText(Double x, Double y){
        xText.setText("X: " + String.valueOf(x.floatValue())+"m" );
        yText.setText("Y: " +String.valueOf(y.floatValue())+"m" );
    }
    public void draw() {
        ArrayList<Entry> entries = new ArrayList<>();
        int interval = 10;
        Double finalTime = timeList.get(timeList.size()-1)*3000 + interval;
        counter = 0;
        ArrayList<Double> finalXCoordinates = xCoordinates;
        ArrayList<Double> finalYCoordinates = yCoordinates;
        ArrayList<Double> finalTimeList = timeList;
        new CountDownTimer(finalTime.longValue(), interval){
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished){

                timeText.setText(String.valueOf("t: "+((float)counter*interval)/1000)+"s");
                if(counter == finalXCoordinates.size() -1) {
                    cancel();
                }
                canvasView.x = (int) (finalXCoordinates.get(counter)*8d);
                canvasView.y = (int) (finalYCoordinates.get(counter)*8d);
                canvasView.invalidate();
                setCoordinateText(finalXCoordinates.get(counter), finalYCoordinates.get(counter));
                entries.add(createEntry(finalTimeList.get(counter), finalYCoordinates.get(counter)));

                LineDataSet dataSet= new LineDataSet(entries,"y based on time");


                refreshGraph(dataSet);



                counter++;
            }
            public  void onFinish(){
                timeText.setText(String.valueOf(finalTimeList.get(finalTimeList.size()-1)));
                if(counter < finalXCoordinates.size()) {
                    setCoordinateText(finalXCoordinates.get(counter), finalYCoordinates.get(counter));
                    entries.add(createEntry(finalTimeList.get(finalTimeList.size()-1), finalYCoordinates.get(finalYCoordinates.size()-1)));
                    LineDataSet dataSet= new LineDataSet(entries,"y based on time");
                    refreshGraph(dataSet);
                }
                timeText.setText(String.valueOf(counter));

                counter = 0;
            }
        }.start();
    }



}
