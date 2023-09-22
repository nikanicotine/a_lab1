package com.example.a_lab1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LogRegActivity extends Activity {
    ArrayAdapter<String> adapter;
    ArrayList<String> catNames = new ArrayList<>();
    ArrayList<String> selectedCats = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_reg_act);

        Button loginButton = findViewById(R.id.loginButton);
        Intent intent = new Intent(this, ListActivity.class);
//        Button delButton = findViewById(R.id.);
//        TextView textView = findViewById(R.id.textView); // оставь
//
//        ListView listView = findViewById(R.id.listView);
//
//        Collections.addAll(catNames, "Я", "Я", "Я", "И еще я");
//
//        final EditText editText = (EditText) findViewById(R.id.editText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}
