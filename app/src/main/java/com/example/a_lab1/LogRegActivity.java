package com.example.a_lab1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import android.widget.CompoundButton.OnCheckedChangeListener;

import java.util.Objects;

public class LogRegActivity extends Activity {
    Button loginButton, loginSW;
    CheckBox ponCheckBox;
    EditText loginInput, passInput;

//    Switch loginSW;

    public static final String APP_PREFERENCES = "mysettingsLog";
    public static final String APP_PREFERENCES_LOG = "Login";

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

//        loginSW = findViewById(R.id.loginSW);
////        if (loginSW != null) {
////            loginSW.setOnCheckedChangeListener();
////        }
//
//        loginSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Intent intent = new Intent(this, RegActivity.class);
//                Intent intent1 = new Intent(this, LogRegActivity.class);
//                Toast.makeText(LogRegActivity.this, "Отслеживание переключения: " + (isChecked ? "on" : "off"),
//                Toast.LENGTH_SHORT).show();
//
//                if (loginSW.isChecked()) startActivity(intent) : startActivity(intent1);
//            }
//        });

    }
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Toast.makeText(this, "Отслеживание переключения: " + (isChecked ? "on" : "off"),
//                Toast.LENGTH_SHORT).show();
//    }

    public void sendLogin(View v) { // loginButton onClick
        ponCheckBox = findViewById(R.id.ponCheckBox);
        if (ponCheckBox.isChecked()) {
            SavePreferences();
        } else {
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
//            Intent intent = new Intent(this, LogRegActivity.class);
//            Intent intent1 = new Intent(this, RegActivity.class);
//
//            loginSW = findViewById(R.id.loginSW);
////        if (loginSW != null) {
////            loginSW.setOnCheckedChangeListener();
////        }
//
//            loginSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    Toast.makeText(LogRegActivity.this, "Отслеживание переключения: " + (isChecked ? "on" : "off"),
//                            Toast.LENGTH_SHORT).show();
//
//                    startActivity(!loginSW.isChecked() ? intent : intent1); //off on
//                }
//            });
        }
    }
}