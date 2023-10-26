package com.example.a_lab1;

import static com.example.a_lab1.LogActivity.APP_PREFERENCES;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
//import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class ListActivity extends Activity {
    ArrayAdapter<String> adapter;
    static ArrayList<String> catNames = new ArrayList<>();
    ArrayList<String> selectedCats = new ArrayList<>();
    Button addButton, delButton;
    TextView textView;
    ListView listCats;
    EditText editCat;
    public static final String APP_PREFERENCES_CATS = "Cat";
    public static final String DATABASE_NAME = "Users.db";
    SharedPreferences mSettings;
    DatabaseHandler db1 = new DatabaseHandler(this);

    @SuppressLint("SetTextI18n") // ?
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listact);

        Toolbar toolbar = findViewById(R.id.materialToolbar2);

        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        addButton = findViewById(R.id.addButton);
        delButton = findViewById(R.id.delUserButton);
        textView = findViewById(R.id.listName);
        listCats = findViewById(R.id.listCats);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editCat = findViewById(R.id.editText);

        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            String user = arguments.getString("user");
            Toast.makeText(ListActivity.this, "Вы зашли под именем " + user,
                    Toast.LENGTH_LONG).show();
        }

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, catNames);

        listCats.setAdapter(adapter);
        LoadPreferences();
        delButton.setEnabled(false);

        listCats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String user = adapter.getItem(position);
                if (listCats.isItemChecked(position))
                    selectedCats.add(user);
                else
                    selectedCats.remove(user);
                delButton.setEnabled(true); // TODO
            }
        });
    }

    public static class Cats {
        int _id;
        String _name;

        public Cats() {
        }

        public Cats(int id, String name) {
            this._id = id;
            this._name = name;
        }

        public Cats(String name) {
            this._name = name;
        }

        public int getID() {
            return this._id;
        }

        public void setID(int id) {
            this._id = id;
        }

        public String getName() {
            return this._name;
        }

        public void setName(String name) {
            this._name = name;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        db1.close();
        Log.i("мой тег", "Произошел дестрой");
    }

    public static final class DBContract {

        private DBContract() {
        }

        public static class CatEntry implements BaseColumns {
            public static final String TABLE_NAME = "CATS";
            public static final String COLUMN_NAME_CAT_KEY_ID = "ID_CAT";
            public static final String COLUMN_USER_NAME_ID = "ID_USER";
            public static final String COLUMN_NAME_CAT = "NAME";
        }
    }

    public static class DatabaseHandler extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;


        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db3) {
            String CREATE_CATS_TABLE = "CREATE TABLE "
                    + ListActivity.DBContract.CatEntry.TABLE_NAME + "("
                    + ListActivity.DBContract.CatEntry.COLUMN_NAME_CAT_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                    + ListActivity.DBContract.CatEntry.COLUMN_USER_NAME_ID + " INTEGER, "
                    + ListActivity.DBContract.CatEntry.COLUMN_NAME_CAT + " TEXT" + ")";
            Log.v("log", CREATE_CATS_TABLE);
            Log.v("log", "aaaaaaaaaaaaaAAAaAAAaaaaaAAaaa");
            db3.execSQL(CREATE_CATS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {
            db1.execSQL("DROP TABLE IF EXISTS " + ListActivity.DBContract.CatEntry.TABLE_NAME);
            onCreate(db1);
        }

        public void Open() {
            SQLiteDatabase db1 = this.getWritableDatabase();
            db1.execSQL("DROP TABLE IF EXISTS " + ListActivity.DBContract.CatEntry.TABLE_NAME);
            onCreate(db1);

//            String cat = catNames.toString();
            String dataSet = catNames.toString();
            if (Objects.equals(dataSet, "[]")) return;
            dataSet = dataSet.replaceAll("^\\[|\\]$", "");
            String[] cats = dataSet.split(", ");
            Log.i("мой тег", "Пауза до сейва");
            for (String s : cats) {
                Save(new Cats(s));
            }

            Log.i("мой тег", "Пауза после сейва");
//            ContentValues values = new ContentValues();
//            values.put(DBContract.CatEntry.COLUMN_NAME_CAT, cat.getName());
//            Log.v("log", values.toString());
//            db1.insert(ListActivity.DBContract.CatEntry.TABLE_NAME, null, values);
            db1.close();
        }

        public void Save(Cats cat) {
            SQLiteDatabase db1 = this.getWritableDatabase();
//            db1.execSQL("DROP TABLE IF EXISTS " + ListActivity.DBContract.CatEntry.TABLE_NAME);
//            onCreate(db1);
            ContentValues values = new ContentValues();
            values.put(DBContract.CatEntry.COLUMN_NAME_CAT, cat.getName());
            Log.v("log", values.toString());
            db1.insert(ListActivity.DBContract.CatEntry.TABLE_NAME, null, values);
            db1.close();
        }

    }

    protected void SavePreferences() {
        String cat = catNames.toString();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_CATS, cat);
        editor.apply();
    }

    protected void LoadPreferences() {
        SharedPreferences data = this.getSharedPreferences("mysettingsLog", MODE_PRIVATE);
        String dataSet = data.getString("Cat", null);
        if (Objects.equals(dataSet, null)) return;
        if (Objects.equals(dataSet, "[]")) return;
        dataSet = dataSet.replaceAll("^\\[|\\]$", "");
        String[] cats = dataSet.split(", ");

        adapter.addAll(cats);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.addButton) {
            add(v);
        } else if (id == R.id.delUserButton) {
            remove(v, listCats);
            delButton.setEnabled(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        db1.Open();

        Log.i("мой тег", "Пауза после сейва");
        SavePreferences();
    }

    public void add(View view) {
        editCat = findViewById(R.id.editText);
        String cat = editCat.getText().toString();
        if (!cat.isEmpty()) {
            adapter.add(cat);
            editCat.setText("");
            adapter.notifyDataSetChanged();
        }
    }

    public void remove(View view, ListView listView) {
        for (int i = 0; i < selectedCats.size(); i++) {
            adapter.remove(selectedCats.get(i));
        }
        listView.clearChoices();
        selectedCats.clear();
        adapter.notifyDataSetChanged();
        Toast.makeText(ListActivity.this,
                "Выбранные котики удалены", Toast.LENGTH_SHORT).show();
    }
}
