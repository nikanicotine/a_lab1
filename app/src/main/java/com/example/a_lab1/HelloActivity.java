package com.example.a_lab1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.function.ToIntBiFunction;

public class HelloActivity extends Activity {
    ArrayAdapter<String> adapter;
    ArrayList<String> catNames = new ArrayList<>();
    ArrayList<String> selectedCats = new ArrayList<>();
    ListView listView = findViewById(R.id.listView);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact);

        Button addButton = findViewById(R.id.addButton);
        Button delButton = findViewById(R.id.delButton);
        TextView textView = findViewById(R.id.textView);

        final EditText editText = (EditText) findViewById(R.id.editText);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String user = adapter.getItem(position);
                if (listView.isItemChecked(position))
                    selectedCats.add(user);
                else
                    selectedCats.remove(user);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
//                button1.setText("Тыкнулась");
//                if (TblK[0] != 50) {
//                    ++TblK[0];
//                    textView.setTextColor(Color.rgb(red[0] += 4, green[0] -= 4, 0));
//                }
//                textView.setText(Integer.toString(TblK[0]));
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                delButton.setText("Тоже тыкнулась");
//                if (TblK[0] != 0) {
//                    TblK[0]--;
//                    textView.setTextColor(Color.rgb(red[0] -= 4, green[0] += 4, 0));
//                }

//                textView.setText(Integer.toString(TblK[0]));
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

    public void remove(View view) {
        // получаем и удаляем выделенные элементы
        for (int i = 0; i < selectedCats.size(); i++) {
            adapter.remove(selectedCats.get(i));
        }
        // снимаем все ранее установленные отметки
        listView.clearChoices();
        // очищаем массив выбраных объектов
        selectedCats.clear();

        adapter.notifyDataSetChanged();
    }
}
