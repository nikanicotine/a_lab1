package com.example.a_lab1;

import static java.lang.Boolean.TRUE;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Objects;

public class LogActivity extends Activity {
    Button loginButton, regNewUserButton, delUserButton;
    static CheckBox ponCheckBox;
    static EditText loginInput;
    EditText passInput;
    EditText newLoginInput;
    EditText regFirstPassInput;
    EditText regSecondPassInput;
    EditText delLoginInput;
    EditText delPassInput;
    EditText loginInput2;
    EditText oldPassInput;
    static EditText newPassInput;
    CardView logCard, regCard, passCard, delCard;

    public static final String APP_PREFERENCES = "mysettingsLog";
    public static final String APP_PREFERENCES_LOG = "Login";

    static SharedPreferences mSettings;
    DatabaseHandlerUser db = new DatabaseHandlerUser(this);

    final Looper looper = Looper.getMainLooper();

    final Handler handler = new Handler(looper) {
        @Override
        public void handleMessage(Message msg) {

            if (msg.sendingUid == 1) {
                if (msg.obj == TRUE) {
                    Intent intent = new Intent(LogActivity.this, ListActivity.class);
                    intent.putExtra("user", loginInput.getText().toString());
                    startActivity(intent);
                } else
                    Toast.makeText(LogActivity.this, "Wrong a username or password", Toast.LENGTH_SHORT).show();
                    // возможно нужно еще раз вызвать создание потока
            }

            if (msg.sendingUid == 2) {
                if (msg.obj == TRUE) {
                    newLoginInput.setText("");
                    regFirstPassInput.setText("");
                    regSecondPassInput.setText("");
                    Intent intent = new Intent(LogActivity.this, ListActivity.class);
                    intent.putExtra("user", newLoginInput.getText().toString());
                    startActivity(intent);
                } else{
                    Toast.makeText(LogActivity.this, "Always exist!", Toast.LENGTH_SHORT).show();
                    regFirstPassInput.setText("");
                    regSecondPassInput.setText("");
                }
            }
            if (msg.sendingUid == 3) {
                if (msg.obj == TRUE) {
                    delCard.setVisibility(View.GONE);
                    logCard.setVisibility(View.VISIBLE);
                    Toast.makeText(LogActivity.this, "User " + delLoginInput.getText().toString() + " has been deleted :(", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(LogActivity.this, "Not found!", Toast.LENGTH_SHORT).show();
                    delPassInput.setText("");
                }
            }
            if (msg.sendingUid == 4) {
                if (msg.obj == TRUE) {
                    passCard.setVisibility(View.GONE);
                    logCard.setVisibility(View.VISIBLE);
                    Toast.makeText(LogActivity.this, "Password changed (:", Toast.LENGTH_SHORT).show();
                    loginInput2.setText("");
                } else{
                    Toast.makeText(LogActivity.this, "Not found!", Toast.LENGTH_SHORT).show();
                }
                oldPassInput.setText("");
                newPassInput.setText("");
            }
        }
    };

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public static class DB {
        public static final class DBContract {

            private DBContract() {
            }

            public static class UserEntry implements BaseColumns {
                public static final String TABLE_NAME = "USERS";
                public static final String COLUMN_NAME_KEY_ID = "ID"; // _id
                public static final String COLUMN_NAME_LOGIN = "LOGIN";
                public static final String COLUMN_NAME_PASS = "PASS";
            }

            public static class CatEntry implements BaseColumns {
                public static final String TABLE_NAME = "CATS";
                public static final String COLUMN_NAME_CAT_KEY_ID = "ID_CAT";
                public static final String COLUMN_NAME_CAT = "NAME";
            }
        }

        public static class DatabaseHandlerUser extends SQLiteOpenHelper {

            static final int DATABASE_VERSION = 1;
            static final String DATABASE_NAME = "Users.db";

            public DatabaseHandlerUser(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                String CREATE_USERS_TABLE = "CREATE TABLE " + LogActivity.DBContract.UserEntry.TABLE_NAME + "("
                        + LogActivity.DBContract.UserEntry.COLUMN_NAME_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        LogActivity.DBContract.UserEntry.COLUMN_NAME_LOGIN + " TEXT, " + LogActivity.DBContract.UserEntry.COLUMN_NAME_PASS + " TEXT" + ")";
                Log.v("log", CREATE_USERS_TABLE);
                db.execSQL(CREATE_USERS_TABLE);

                String CREATE_CATS_TABLE = "CREATE TABLE "
                        + LogActivity.DBContract.CatEntry.TABLE_NAME + "("
                        + LogActivity.DBContract.CatEntry.COLUMN_NAME_CAT_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + LogActivity.DBContract.CatEntry.COLUMN_NAME_CAT + " TEXT" + ")";
                db.execSQL(CREATE_CATS_TABLE);
            }


            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + LogActivity.DBContract.UserEntry.TABLE_NAME);
                db.execSQL("DROP TABLE IF EXISTS " + LogActivity.DBContract.CatEntry.TABLE_NAME);
                onCreate(db);
            }

//        public boolean sendUser(User user) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            String check = user.getLogin();
//            String check1 = user.getPass();
//            String query = "select * from USERS where LOGIN like " + '"' + check + '"' + "and PASS like " + '"' + check1 + '"';
//            Cursor cur = db.rawQuery(query, null);
//            String count = String.valueOf(cur.getCount());
//            if (count.equals("0") || count.equals("-1")) return false;
//            cur.close();
//
//            //
//            if (ponCheckBox.isChecked()) {
//                SavePreferences();
//            } else {
//                SharedPreferences.Editor editor = mSettings.edit();
//                editor.putString(APP_PREFERENCES_LOG, "");
//                editor.apply();
//            }
//
//            db.close();
//            return true;
//        }

            public boolean checkUser(String userLogin) {
                SQLiteDatabase db = this.getWritableDatabase();
                String selectQuery = "SELECT  * FROM " + LogActivity.DBContract.UserEntry.TABLE_NAME + " WHERE login = '" + userLogin + "'";
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst())
                    return true;
                else
                    return false;
            }

            public void addUser(User user) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(LogActivity.DBContract.UserEntry.COLUMN_NAME_LOGIN, user.getLogin());
                values.put(LogActivity.DBContract.UserEntry.COLUMN_NAME_PASS, user.getPass());

                db.insert(LogActivity.DBContract.UserEntry.TABLE_NAME, null, values);
                db.close();
            }

            public void delUser(String userLogin) {
                SQLiteDatabase db = this.getWritableDatabase();

                String[] delString = new String[]{userLogin};
                db.delete(LogActivity.DBContract.UserEntry.TABLE_NAME, LogActivity.DBContract.UserEntry.COLUMN_NAME_LOGIN + "=?", delString);
                db.close();
            }

            public void changePass(String user) {
                SQLiteDatabase db = this.getWritableDatabase();

                String password2 = newPassInput.getText().toString();
                String query1 = "UPDATE USERS SET PASS = " + '"' + password2 + '"' + " WHERE LOGIN = " + '"' + user + '"';
                db.execSQL(query1);
                db.close();
            }

            public void sendUser(String user, String password) {
                SQLiteDatabase db = this.getWritableDatabase();

                if (ponCheckBox.isChecked()) {
                    SavePreferences();
                } else {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_LOG, "");
                    editor.apply();
                }
                db.close();
            }
        }
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

        public static class CatEntry implements BaseColumns {
            public static final String TABLE_NAME = "CATS";
            public static final String COLUMN_NAME_CAT_KEY_ID = "ID_CAT";
            public static final String COLUMN_NAME_CAT = "NAME";
        }
    }

    public static class DatabaseHandlerUser extends SQLiteOpenHelper {

        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "Users.db";

        public DatabaseHandlerUser(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_USERS_TABLE = "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + "("
                    + DBContract.UserEntry.COLUMN_NAME_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DBContract.UserEntry.COLUMN_NAME_LOGIN + " TEXT, " + DBContract.UserEntry.COLUMN_NAME_PASS + " TEXT" + ")";
            Log.v("log", CREATE_USERS_TABLE);
            db.execSQL(CREATE_USERS_TABLE);

            String CREATE_CATS_TABLE = "CREATE TABLE "
                    + DBContract.CatEntry.TABLE_NAME + "("
                    + DBContract.CatEntry.COLUMN_NAME_CAT_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DBContract.CatEntry.COLUMN_NAME_CAT + " TEXT" + ")";
            db.execSQL(CREATE_CATS_TABLE);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DBContract.CatEntry.TABLE_NAME);
            onCreate(db);
        }

//        public boolean sendUser(User user) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            String check = user.getLogin();
//            String check1 = user.getPass();
//            String query = "select * from USERS where LOGIN like " + '"' + check + '"' + "and PASS like " + '"' + check1 + '"';
//            Cursor cur = db.rawQuery(query, null);
//            String count = String.valueOf(cur.getCount());
//            if (count.equals("0") || count.equals("-1")) return false;
//            cur.close();
//
//            //
//            if (ponCheckBox.isChecked()) {
//                SavePreferences();
//            } else {
//                SharedPreferences.Editor editor = mSettings.edit();
//                editor.putString(APP_PREFERENCES_LOG, "");
//                editor.apply();
//            }
//
//            db.close();
//            return true;
//        }

        public boolean checkUser(String userLogin) {
            SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT  * FROM " + DBContract.UserEntry.TABLE_NAME + " WHERE login = '" + userLogin + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst())
                return true;
            else
                return false;
        }

        public void addUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, user.getLogin());
            values.put(DBContract.UserEntry.COLUMN_NAME_PASS, user.getPass());

            db.insert(DBContract.UserEntry.TABLE_NAME, null, values);
            db.close();
        }

        public void delUser(String userLogin) {
            SQLiteDatabase db = this.getWritableDatabase();

            String[] delString = new String[]{userLogin};
            db.delete(DBContract.UserEntry.TABLE_NAME, DBContract.UserEntry.COLUMN_NAME_LOGIN + "=?", delString);
            db.close();
        }

        public void changePass(String user) {
            SQLiteDatabase db = this.getWritableDatabase();

            String password2 = newPassInput.getText().toString();
            String query1 = "UPDATE USERS SET PASS = " + '"' + password2 + '"' + " WHERE LOGIN = " + '"' + user + '"';
            db.execSQL(query1);
            db.close();
        }

        public void sendUser(String user, String password) {
            SQLiteDatabase db = this.getWritableDatabase();

            if (ponCheckBox.isChecked()) {
                SavePreferences();
            } else {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_LOG, "");
                editor.apply();
            }
            db.close();
        }
    }

    public static class User {
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

        new ThreadTask(handler).sendLoginTask(user, password);


//        ArrayList<String> list = new ArrayList<>();
//        list.add(user);
//        list.add(password);
//
//        Message message = Message.obtain();
//        message.obj = list;
//        message.sendingUid = 1;
//        ThreadTask.handler.sendMessage(message);

//        if (!db.sendUser(new User(user, password))) {
//            Toast.makeText(this, "Wrong a username or password", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        Intent intent = new Intent(this, ListActivity.class);
//        intent.putExtra("user", user);
//        startActivity(intent);
    }

    public void regUser(View v) {

        regNewUserButton = findViewById(R.id.regNewUserButton);
        newLoginInput = findViewById(R.id.newLoginInput);
        regFirstPassInput = findViewById(R.id.regFirstPassInput);
        regSecondPassInput = findViewById(R.id.regSecondPassInput);

        String user = newLoginInput.getText().toString();
        String password1 = regFirstPassInput.getText().toString();
        String password2 = regSecondPassInput.getText().toString();

        if (user.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "You did not enter a username or password", Toast.LENGTH_SHORT).show();
        } else if (password1.equals(password2)) {
            new ThreadTask(handler).regUserTask(user, password1);

//            ArrayList<String> list = new ArrayList<>();
//            list.add(user);
//            list.add(password1);
//
//            Message message = Message.obtain();
//            message.obj = list;
//            message.sendingUid = 2;
//            ThreadTask.handler.sendMessage(message);

//            if (!db.addUser(new User(user, password1))) {
//                Toast.makeText(this, "Always exist!", Toast.LENGTH_SHORT).show();
//                regFirstPassInput.setText("");
//                regSecondPassInput.setText("");
//            } else {
//                newLoginInput.setText("");
//                regFirstPassInput.setText("");
//                regSecondPassInput.setText("");
//                Intent intent = new Intent(this, ListActivity.class);
//                intent.putExtra("user", user);
//                startActivity(intent);
//            }
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
                new ThreadTask(handler).removeUserTask(user);

//                ArrayList<String> list = new ArrayList<>();
//                list.add(user);
//                list.add(password);
//
//                Message message = Message.obtain();
//                message.obj = list;
//                message.sendingUid = 3;
//                ThreadTask.handler.sendMessage(message);

//                if (!db.delUser(new User(user, password))) {
//                    Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show();
//                    delPassInput.setText("");
//                } else {
//                    delCard.setVisibility(View.GONE);
//                    logCard.setVisibility(View.VISIBLE);
//                    Toast.makeText(this, "User " + user + " has been deleted :(", Toast.LENGTH_SHORT).show();
//                }
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
            new ThreadTask(handler).savePassTask(user);

//            ArrayList<String> list = new ArrayList<>();
//            list.add(user);
//
//            Message message = Message.obtain();
//            message.obj = list;
//            message.sendingUid = 4;
//            ThreadTask.handler.sendMessage(message);

//            if (!db.changePass(new User(user, password1))) {
//                Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show();
//                oldPassInput.setText("");
//                newPassInput.setText("");
//            } else {
//                passCard.setVisibility(View.GONE);
//                logCard.setVisibility(View.VISIBLE);
//                Toast.makeText(this, "Password changed (:", Toast.LENGTH_SHORT).show();
//                loginInput2.setText("");
//                oldPassInput.setText("");
//                newPassInput.setText("");
//            }
        }
    }

    protected static void SavePreferences() {
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