package com.multimedia.vrh;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;



import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    SeekBar heigth;
    SeekBar velocity;
    TextView heigthText;
    TextView velocityText;
    Switch serverSwitch;
    Button start;
    Timer timer;
    public int counter;
    int i = 0;
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heigth = findViewById(R.id.seekBar5);
        velocity = findViewById(R.id.seekBar2);
        heigthText = findViewById(R.id.textView7);
        velocityText = findViewById(R.id.textView8);
        serverSwitch = findViewById(R.id.switch1);

        velocityText.setText(new StringBuilder().append(String.valueOf(heigth.getProgress())).append("°").toString());
        heigthText.setText(new StringBuilder().append(String.valueOf(velocity.getProgress())).append("m/s").toString());
        start = findViewById(R.id.button3);


        heigth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                velocityText.setText(String.valueOf(i)+"°");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        velocity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            @SuppressLint("SetTextI18n")
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                heigthText.setText(String.valueOf(velocity.getProgress())+"m/s");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivitiesWithData();

            }
        });

    }
    private void switchActivitiesWithData() {

        if(serverSwitch.isChecked()) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork == null) {
                Toast.makeText(getApplicationContext(),
                        "No internet connection",
                        Toast.LENGTH_LONG).show();
            } else{
                switchToGraph();
            }
        } else{
            switchToGraph();
        }


    }
    private void switchToGraph(){
        Intent switchActivityIntent = new Intent(this, GraphActivity.class);

        Bundle extras = new Bundle();
        extras.putInt("velocity",velocity.getProgress());
        extras.putInt("heigth",heigth.getProgress());
        extras.putBoolean("serverComputation", serverSwitch.isChecked());
        switchActivityIntent.putExtras(extras);

        startActivity(switchActivityIntent);
    }

}