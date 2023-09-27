package com.example.a_lab1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

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
        EditText loginInput = findViewById(R.id.loginInput);
        EditText passInput = findViewById(R.id.passInput);

    }
    public void sendLogin(View v) {

        Button loginButton = findViewById(R.id.loginButton);
        EditText loginInput = findViewById(R.id.loginInput);

        String user = loginInput.getText().toString();

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
//////////




////////////
//    String user = loginInput.getText().toString();
//    String password = passInput.getText().toString();
//                if (!user.isEmpty() || !password.isEmpty()) {
//                        loginButton.setEnabled(true);
//                        }
