package com.example.a_lab1;

import static com.example.a_lab1.LogActivity.APP_PREFERENCES;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    ArrayList<String> catNames = new ArrayList<>();
    ArrayList<String> selectedCats = new ArrayList<>();
    Button addButton, delButton;
    TextView textView;
    ListView listCats;
    EditText editCat;
    public static final String APP_PREFERENCES_CATS = "Cat";
    SharedPreferences mSettings;

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
            Toast.makeText(ListActivity.this,
                    "Вы зашли под именем " + user, Toast.LENGTH_LONG).show();
            toolbar.setTitle("Привет, " + user);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, catNames);

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
