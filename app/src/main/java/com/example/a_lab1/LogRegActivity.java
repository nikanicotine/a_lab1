package com.example.a_lab1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Objects;

public class LogRegActivity extends Activity {
    Button loginButton;
    CheckBox ponCheckBox;
    EditText loginInput, passInput;

    public static final String APP_PREFERENCES = "mysettingsLog";
    public static final String APP_PREFERENCES_LOG = "Login"; // логин

    SharedPreferences mSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_reg_act);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        loginButton = findViewById(R.id.loginButton);
        loginInput = findViewById(R.id.loginInput);
        passInput = findViewById(R.id.passInput);
        LoadPreferences();
    }

    public void sendLogin(View v) { // loginButton onClick

        TextView textView = findViewById(R.id.textView);


        ponCheckBox = findViewById(R.id.ponCheckBox);
        if (ponCheckBox.isChecked()) {
            textView.setText("Флажок выбран");
            SavePreferences();
        } else {
            textView.setText("Флажок не выбран");
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_LOG, "");
            editor.apply();
        }

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

    protected void SavePreferences() {
        String log = loginInput.getText().toString();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_LOG, log);
        editor.apply();
    }

    protected void LoadPreferences() {
        SharedPreferences data = this.getSharedPreferences("mysettingsLog", MODE_PRIVATE);
        String dataSet = data.getString("Login", null);
        if (Objects.equals(dataSet, null)) return;
        if (Objects.equals(dataSet, "[]")) return;
        dataSet = dataSet.replaceAll("^\\[|\\]$", "");
        loginInput.setText(dataSet);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.regSW) {
            Intent intent = new Intent(this, RegActivity.class);
            startActivity(intent);
        } else if (id == R.id.loginSW) {
            return;
        }
    }
}