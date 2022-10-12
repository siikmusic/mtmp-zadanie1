package com.multimedia.vrh;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
    TableLayout tableLayout;

    ArrayList<Double> xCoordinates;
    ArrayList<Double> yCoordinates;
    ArrayList<Double> timeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        tableLayout = findViewById(R.id.table);
        retrieveMessage();
        for(int i = 0; i<xCoordinates.size();i++){
            TableRow tableRow = new TableRow(this);
            TextView time = new TextView(this);
            time.setGravity(Gravity.CENTER);

            TextView x = new TextView(this);
            x.setGravity(Gravity.CENTER);

            TextView y = new TextView(this);
            y.setGravity(Gravity.CENTER);

            time.setText(String.valueOf(timeList.get(i)));
            x.setText(String.valueOf(xCoordinates.get(i)));
            y.setText(String.valueOf(yCoordinates.get(i)));

            tableRow.addView(x);
            tableRow.addView(y);
            tableRow.addView(time);

            tableLayout.addView(tableRow);
        }
        tableLayout.invalidate();
    }

    private void retrieveMessage() {
        Bundle data = getIntent().getExtras();
        xCoordinates = (ArrayList<Double>) data.getSerializable("x");
        yCoordinates = (ArrayList<Double>) data.getSerializable("y");
        timeList = (ArrayList<Double>) data.getSerializable("time");

    }
    }
