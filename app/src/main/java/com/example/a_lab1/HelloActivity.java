package com.example.a_lab1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.function.ToIntBiFunction;

public class HelloActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        TextView textView = findViewById(R.id.textView);

        final int[] TblK = {0};
        final int[] red = {0};
        final int[] green = {255};

        ArrayList<String> myStringArray = new ArrayList<String>();
        ArrayAdapter<String> TextAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray);

        //textList.setAdapter(TextAdapter);


        button1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                button1.setText("Тыкнулась");
                if (TblK[0] != 50) {
                    ++TblK[0];
                    textView.setTextColor(Color.rgb(red[0] += 4, green[0] -= 4, 0));
                }
                textView.setText(Integer.toString(TblK[0]));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button2.setText("Тоже тыкнулась");
                if (TblK[0] != 0) {
                    TblK[0]--;
                    textView.setTextColor(Color.rgb(red[0] -= 4, green[0] += 4, 0));
                }

                textView.setText(Integer.toString(TblK[0]));
            }
        });
    }
}
