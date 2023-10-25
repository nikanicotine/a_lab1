package com.example.a_lab1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogActivity extends Activity {
    Button loginButton, regNewUserButton, delUserButton;
    CheckBox ponCheckBox;
    EditText loginInput, passInput, newLoginInput, regFirstPassInput, regSecondPassInput,
            delLoginInput, delPassInput, loginInput2, oldPassInput, newPassInput;
    CardView logCard, regCard, passCard, delCard;

    public static final String APP_PREFERENCES = "mysettingsLog";
    public static final String APP_PREFERENCES_LOG = "Login";

    SharedPreferences mSettings;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_act);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        loginButton = findViewById(R.id.loginButton);
        loginInput = findViewById(R.id.loginInput);
        passInput = findViewById(R.id.passInput);
        LoadPreferences();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        db.deleteAll();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public static final class DBContract {

        private DBContract() {
        }

        public static class UserEntry implements BaseColumns {
            public static final String TABLE_NAME = "USERS";
            public static final String COLUMN_NAME_KEY_ID = "ID"; // _id
            public static final String COLUMN_NAME_LOGIN = "LOGIN";
            public static final String COLUMN_NAME_PASS = "PASS";
        }
    }

    public class DatabaseHandler extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "Users.db";

        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_USERS_TABLE = "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + "("
                    + DBContract.UserEntry.COLUMN_NAME_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBContract.UserEntry.COLUMN_NAME_LOGIN + " TEXT, " + DBContract.UserEntry.COLUMN_NAME_PASS + " TEXT" + ")";
            Log.v("log", CREATE_USERS_TABLE);
            db.execSQL(CREATE_USERS_TABLE);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME);
            onCreate(db);
        }

        public boolean sendUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            String check = user.getLogin();
            String check1 = user.getPass();
            String query = "select * from USERS where LOGIN like " + '"' + check + '"' + "and PASS like " + '"' + check1 + '"';
            Cursor cur = db.rawQuery(query, null);
            String count = String.valueOf(cur.getCount());
            if (count.equals("0") || count.equals("-1")) return false;
            //
            if (ponCheckBox.isChecked()) {
                SavePreferences();
            } else {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_LOG, "");
                editor.apply();
            }
            cur.close();
            db.close();
            return true;
        }

        public boolean addUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, user.getLogin());
            values.put(DBContract.UserEntry.COLUMN_NAME_PASS, user.getPass());
            String check = user.getLogin();
            String query = "select * from USERS where LOGIN like " + '"' + check + '"';
            Cursor cur = db.rawQuery(query, null);
            String count = String.valueOf(cur.getCount());
            if (!count.equals("0")) return false;
            db.insert(DBContract.UserEntry.TABLE_NAME, null, values);
            cur.close();
            db.close();
            return true;
        }

        public boolean delUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            String[] delString = new String[]{user.getLogin()};
            String check = user.getLogin();
            String check1 = user.getPass();
            String query = "select * from USERS where LOGIN like " + '"' + check + '"' + "and PASS like " + '"' + check1 + '"';
            Cursor cur = db.rawQuery(query, null);
            String count = String.valueOf(cur.getCount());
            if (count.equals("0") || count.equals("-1")) return false;
            db.delete(DBContract.UserEntry.TABLE_NAME, DBContract.UserEntry.COLUMN_NAME_LOGIN + "=?", delString);
            db.close();
            return true;
        }

        public boolean changePass(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            String password2 = newPassInput.getText().toString();
            String check = user.getLogin();
            String check1 = user.getPass();
            String query = "select ID from USERS where LOGIN like " + '"' + check + '"' + "and PASS like " + '"' + check1 + '"';
            Cursor cur = db.rawQuery(query, null);
            String count = String.valueOf(cur.getCount());
            if (count.equals("0") || count.equals("-1")) return false;
            String query1 = "UPDATE USERS SET PASS = " + '"' + password2 + '"' + " WHERE LOGIN = " + '"' + check + '"';
            db.execSQL(query1);
            cur.close();
            db.close();
            return true;
        }

        public List<User> getAllUsers() {
            List<User> usersList = new ArrayList<User>();
            String selectQuery = "SELECT  * FROM " + DBContract.UserEntry.TABLE_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setID(Integer.parseInt(cursor.getString(0)));
                    user.setLogin(cursor.getString(1));
                    user.setPass(cursor.getString(2));
                    usersList.add(user);
                } while (cursor.moveToNext());
            }
            return usersList;
        }
    }

    public class User {
        int _id;
        String _login;
        String _pass;

        public User() {
        }

        public User(int id, String login, String pass) {
            this._id = id;
            this._login = login;
            this._pass = pass;
        }

        public User(String login, String pass) {
            this._login = login;
            this._pass = pass;
        }

        public int getID() {
            return this._id;
        }

        public void setID(int id) {
            this._id = id;
        }

        public String getLogin() {
            return this._login;
        }

        public void setLogin(String login) {
            this._login = login;
        }

        public String getPass() {
            return this._pass;
        }

        public void setPass(String pass) {
            this._pass = pass;
        }
    }

    public void sendLogin(View v) { // loginButton onClick
        ponCheckBox = findViewById(R.id.ponCheckBox);
        String user = loginInput.getText().toString();
        String password = passInput.getText().toString();

        if (user.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "You did not enter a username or password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!db.sendUser(new User(user, password))){
            Toast.makeText(this, "Wrong a username or password", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void regUser(View v) {

        regNewUserButton = findViewById(R.id.regNewUserButton);
        newLoginInput = findViewById(R.id.newLoginInput);
        regFirstPassInput = findViewById(R.id.regFirstPassInput);
        regSecondPassInput = findViewById(R.id.regSecondPassInput);

        String user = newLoginInput.getText().toString();
        String password1 = regFirstPassInput.getText().toString();
        String password2 = regSecondPassInput.getText().toString();

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("user", user);


        if (user.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "You did not enter a username or password", Toast.LENGTH_SHORT).show();
        } else if (password1.equals(password2)) {
            if (!db.addUser(new User(user, password1))) {
                Toast.makeText(this, "Always exist!", Toast.LENGTH_SHORT).show();
                regFirstPassInput.setText("");
                regSecondPassInput.setText("");
            } else {
                newLoginInput.setText("");
                regFirstPassInput.setText("");
                regSecondPassInput.setText("");
                startActivity(intent);
            }
        } else Toast.makeText(this, "Passwords mismatch!", Toast.LENGTH_SHORT).show();
    }

    public void del(View v) {
        int id = v.getId();

        delUserButton = findViewById(R.id.delUserButton);
        delLoginInput = findViewById(R.id.delLoginInput);
        delPassInput = findViewById(R.id.delPassInput);

        String user = delLoginInput.getText().toString();
        String password = delPassInput.getText().toString();

        if (id == R.id.delUserButton) {
            if (user.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "You did not enter a username or password", Toast.LENGTH_SHORT).show();
            } else {
                if (!db.delUser(new User(user, password))) {
                    Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show();
                    delPassInput.setText("");
                } else {
                    delCard.setVisibility(View.GONE);
                    logCard.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "User " + user + " has been deleted :(", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void savePass(View v) {
        loginInput2 = findViewById(R.id.loginInput2);
        oldPassInput = findViewById(R.id.oldPassInput);
        newPassInput = findViewById(R.id.newPassInput);

        String user = loginInput2.getText().toString();
        String password1 = oldPassInput.getText().toString();
        String password2 = newPassInput.getText().toString();

        if (user.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "You did not enter a username or password", Toast.LENGTH_SHORT).show();
        } else {
            if (!db.changePass(new User(user, password1))) {
                Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show();
                oldPassInput.setText("");
                newPassInput.setText("");
            } else {
                passCard.setVisibility(View.GONE);
                logCard.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Password changed (:", Toast.LENGTH_SHORT).show();
                loginInput2.setText("");
                oldPassInput.setText("");
                newPassInput.setText("");
            }
        }
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

//        List<User> users = db.getAllUsers();
//        for (User usr : users) {
//            String log = "Id: "+usr.getID()+" ,Login: " + usr.getLogin() + " ,Password: " + usr.getPass();
//            Log.v("Loading...", log);
//        }
    }

    public void onClick(View v) {
        int id = v.getId();

//        topLayout = findViewById(R.id.topLayout);
        logCard = findViewById(R.id.logCard);
        regCard = findViewById(R.id.regCard);
        passCard = findViewById(R.id.passCard);
        delCard = findViewById(R.id.delCard);

        if (id == R.id.regSW) {
            logCard.setVisibility(View.GONE);
            passCard.setVisibility(View.GONE);
            delCard.setVisibility(View.GONE);
            regCard.setVisibility(View.VISIBLE);
        } else if (id == R.id.loginSW) {
            passCard.setVisibility(View.GONE);
            regCard.setVisibility(View.GONE);
            delCard.setVisibility(View.GONE);
            logCard.setVisibility(View.VISIBLE);
        } else if (id == R.id.del) {
            logCard.setVisibility(View.GONE);
            delCard.setVisibility(View.VISIBLE);
        } else if (id == R.id.delCancelButton) {
            delLoginInput.setText("");
            delPassInput.setText("");
            delCard.setVisibility(View.GONE);
            logCard.setVisibility(View.VISIBLE);
//        } else if (id == R.id.delUserButton) {
//            delCard.setVisibility(View.GONE);
//            logCard.setVisibility(View.VISIBLE);
//            Toast.makeText(this, "User has been deleted :(", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.changePass) {
            logCard.setVisibility(View.GONE);
            passCard.setVisibility(View.VISIBLE);
        } else if (id == R.id.cancelButton) {
            loginInput2.setText("");
            oldPassInput.setText("");
            newPassInput.setText("");
            passCard.setVisibility(View.GONE);
            logCard.setVisibility(View.VISIBLE);
        }
//        else if (id == R.id.saveButton) {
//            passCard.setVisibility(View.GONE);
//            logCard.setVisibility(View.VISIBLE);
//        savePass();
//        }
    }
}