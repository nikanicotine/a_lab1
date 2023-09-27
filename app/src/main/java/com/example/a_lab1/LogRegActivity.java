package com.example.a_lab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class LogRegActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_reg_act);

        Button loginButton = findViewById(R.id.loginButton);
        EditText passInput = findViewById(R.id.passInput);

    }

    public void sendLogin(View v) { // loginButton onClick

        Button loginButton = findViewById(R.id.loginButton);
        EditText loginInput = findViewById(R.id.loginInput);
        EditText passInput = findViewById(R.id.passInput);

        String user = loginInput.getText().toString();
        String password = passInput.getText().toString();

        if (user.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "You did not enter a username or password", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
