package com.example.a_lab1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class ListActivity extends Activity {
    ArrayAdapter<String> adapter;
    ArrayList<String> catNames = new ArrayList<>();
    ArrayList<String> selectedCats = new ArrayList<>();

    @SuppressLint("SetTextI18n") // ?
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listact);

        Button addButton = findViewById(R.id.addButton);
        Button delButton = findViewById(R.id.delButton);
        TextView textView = findViewById(R.id.listName); // оставь

        ListView listView = findViewById(R.id.listView);

        Collections.addAll(catNames, "Гера", "Капичка", "Еще кот", "И еще кот");

        Bundle arguments = getIntent().getExtras();
//        String user = arguments.get("user").toString();

        if (arguments != null) {
            String user = arguments.getString("user");
            Toast.makeText(ListActivity.this,
                    "Вы зашли под именем " + user, Toast.LENGTH_LONG).show();
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, catNames);
        listView.setAdapter(adapter);
        delButton.setEnabled(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String user = adapter.getItem(position);
                if (listView.isItemChecked(position))
                    selectedCats.add(user);
                else
                    selectedCats.remove(user);
                delButton.setEnabled(true); //TODO
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                add(view);
            }
        });
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(view, listView);
                delButton.setEnabled(false);
            }
        });
    }

    public void add(View view) {

        EditText editText = findViewById(R.id.editText);
        String user = editText.getText().toString();
        if (!user.isEmpty()) {
            adapter.add(user);
            editText.setText("");
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

//delButton.setEnabled(!selectedCats.isEmpty());
