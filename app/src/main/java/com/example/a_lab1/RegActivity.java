package com.example.a_lab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class RegActivity extends Activity {
    Button loginButton, loginSW;
    EditText loginInput, passInput;
//    Switch loginSW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_act);
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

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.loginSW) {
            Intent intent = new Intent(this, LogRegActivity.class);
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
//                    Toast.makeText(RegActivity.this, "Отслеживание переключения: " + (isChecked ? "on" : "off"),
//                            Toast.LENGTH_SHORT).show();
//
//                    startActivity(!loginSW.isChecked() ? intent1 : intent); // off on
//                }
//            });
        }
    }
}
