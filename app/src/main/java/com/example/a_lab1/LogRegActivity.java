package com.example.a_lab1;

import static com.example.a_lab1.ListActivity.APP_PREFERENCES;

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
    SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettingsLog";
    public static final String APP_PREFERENCES_NAME = "Nickname"; // имя кота

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_reg_act);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        LoadPreferences();
    }

    public void sendLogin(View v) { // loginButton onClick

        loginButton = findViewById(R.id.loginButton);
        loginInput = findViewById(R.id.loginInput);
        passInput = findViewById(R.id.passInput);

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
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_high_score_key), newHighScore);
        editor.apply();
    }

    protected void LoadPreferences() {
//        SharedPreferences data = getPreferences(MODE_PRIVATE);
//        String dataSet = data.getString("Nickname", "fff");
//        adapter.add(dataSet);
//        adapter.notifyDataSetChanged();

//        SharedPreferences data = this.getSharedPreferences("mysettings", MODE_PRIVATE);
//        String dataSet = data.getString("Nickname", null);
//        if (Objects.equals(dataSet, "[]")) return;
//        dataSet = dataSet.replaceAll("^\\[|\\]$", "");
//        String[] cats = dataSet.split(", ");

//        loginInput.setText(sharedPref.getString("text",""));
    }

    public void onClick(View v) {
        TextView textView = findViewById(R.id.textView);
        int id = v.getId();
        if (id == R.id.regSW) {
            Intent intent = new Intent(this, RegActivity.class);
            startActivity(intent);
        } else if (id == R.id.loginSW) {
            return;
        } else if (id == R.id.loginButton) {
            ponCheckBox = findViewById(R.id.ponCheckBox);
            if (ponCheckBox.isChecked()) {
                textView.setText("Флажок выбран");
                SavePreferences();
            } else {
                textView.setText("Флажок не выбран");
            }
        }
//        else if (id == R.id.ponCheckBox) {
//            if(ponCheckBox.isChecked())
//                textView.setText("Флажок выбран");
//            else {
//                textView.setText("Флажок не выбран");
//            }
//        }
    }

//    public void onClick1(View v) {
//        TextView textView = findViewById(R.id.textView);
//        ponCheckBox = findViewById(R.id.ponCheckBox);
//        if (ponCheckBox.isChecked()) {
//            textView.setText("Флажок выбран");
//            SavePreferences();
//        } else {
//            textView.setText("Флажок не выбран");
//        }
//    }
}