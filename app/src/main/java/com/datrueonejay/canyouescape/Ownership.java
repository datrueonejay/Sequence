package com.datrueonejay.canyouescape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Ownership extends AppCompatActivity {

    private TextView ownership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownership);

        ownership = (TextView) findViewById(R.id.ownership);
        ownership.getLayoutParams().height = MainMenu.screenHeight/3;
        ownership.setText(R.string.ownership);

    }
}
