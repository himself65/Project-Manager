package com.example.frontendexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;


public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);

    }

    public void login(View v) {
        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) { //correct username/password
            Toast.makeText(getApplicationContext(), "Correct Credentials", Toast.LENGTH_SHORT).show();
            Intent page2 = new Intent(this, MainActivity.class);
            page2.putExtra("username", (username.getText().toString()));
            startActivity(page2);
        }
        else { //wrong username/password
            Toast.makeText(getApplicationContext(), "Wrong Credentials!", Toast.LENGTH_SHORT).show();
        }
    }

}