package com.example.a_lab1;

import static com.example.a_lab1.LogActivity.APP_PREFERENCES;
import static com.example.a_lab1.LogActivity.DatabaseHandlerUser.DATABASE_VERSION;
import static com.example.a_lab1.LogActivity.DatabaseHandlerUser.DATABASE_NAME;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
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

public class ListActivity extends Activity {
    static ArrayAdapter<String> adapter;
    static ArrayList<String> catNames = new ArrayList<>();
    ArrayList<String> selectedCats = new ArrayList<>();
    Button addButton, delButton;
    TextView textView;
    ListView listCats;
    EditText editCat;
    public static final String APP_PREFERENCES_CATS = "Cat";
//    public static final String DATABASE_NAME = "Users.db";

    //    private static final int DATABASE_VERSION = 1;
    SharedPreferences mSettings;

    DatabaseHandlerCat db = new DatabaseHandlerCat(this);


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
        load();
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

    public static class CallBack<T> {

        public void onSuccess() {
        }

        public void onSuccess(T result) {
        }

        public void onFail(String message) {

        }

        public void onFailure(T result) {

        }

    }

    public static class DatabaseHandlerCat extends SQLiteOpenHelper {

        public DatabaseHandlerCat(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void saveCat(String cat) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(LogActivity.DBContract.CatEntry.COLUMN_NAME_CAT, cat);
            db.insert(LogActivity.DBContract.CatEntry.TABLE_NAME, null, values);
            db.close();

        }

        public void delCat(String cat) {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] delString = new String[]{cat};
            db.delete(LogActivity.DBContract.CatEntry.TABLE_NAME,
                    LogActivity.DBContract.CatEntry.COLUMN_NAME_CAT + "=?", delString);
            db.close();
        }

        protected boolean LoadSQL() {

            SQLiteDatabase db = this.getWritableDatabase();
            String query = "select * from CATS";
            Cursor cur = db.rawQuery(query, null);
            String count = String.valueOf(cur.getCount());
            if (count.equals("0")) return false;
            if (cur.moveToFirst()) {
                do {
                    @SuppressLint("Range") String cats = cur.getString(cur.getColumnIndex("NAME"));
                    adapter.addAll(cats);
                    adapter.notifyDataSetChanged();
                } while (cur.moveToNext());
            }

            cur.close();
            db.close();
            return true;
        }
    }


    protected void onDestroy() {
        super.onDestroy();
        Log.i("мой тег", "Произошел дестрой");
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
        Log.i("не мой тег", "Произошел пауз");
//        db.saveList();
        adapter.clear();
    }

    public void add(View view) {
        editCat = findViewById(R.id.editText);
        String cat = editCat.getText().toString();
        int pos = -1;
        boolean sov = false;
        if (!cat.isEmpty()) {
            for (int i = 0; i < adapter.getCount(); i++) {
                pos++;
                if (!adapter.isEmpty()) {
                    if (cat.equals(adapter.getItem(pos))) {
                        editCat.setText("");
                        Toast.makeText(ListActivity.this, "Кот должен быть уникален",
                                Toast.LENGTH_LONG).show();
                        sov = true;
                        break;
                    }
                }
            }
        }
        if (pos == -1) {
            adapter.add(cat);
            db.saveCat(cat);
            editCat.setText("");
            adapter.notifyDataSetChanged();
        }
        if (!sov && pos + 1 == adapter.getCount()) {
            adapter.add(cat);
            db.saveCat(cat);
            editCat.setText("");
            adapter.notifyDataSetChanged();
        }
    }

    public void remove(View view, ListView listView) {
        for (int i = 0; i < selectedCats.size(); i++) {
            adapter.remove(selectedCats.get(i));
            db.delCat(selectedCats.get(i));
        }
        listView.clearChoices();
        selectedCats.clear();
        adapter.notifyDataSetChanged();
        Toast.makeText(ListActivity.this,
                "Выбранные котики удалены", Toast.LENGTH_SHORT).show();
    }

    public void load() {
        db.LoadSQL();
    }
}
