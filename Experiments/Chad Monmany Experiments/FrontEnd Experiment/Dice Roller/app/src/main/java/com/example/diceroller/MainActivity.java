package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;


import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Button rollButton = findViewById(R.id.button);
       rollButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Dice dice = new Dice(6);
               int numRolled = dice.roll();
               TextView result = findViewById(R.id.textView);
               result.setText(String.valueOf(numRolled));

           }
       });





    }

}

class Dice {

    private final int numSides;
    Dice(int numSides)
    {
        this.numSides = numSides;
    }
    int roll()
    {
        Random rand =new Random();
        return rand.nextInt(numSides) + 1;
    }
}