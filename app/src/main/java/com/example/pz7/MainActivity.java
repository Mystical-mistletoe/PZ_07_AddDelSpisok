package com.example.pz7;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

import android.text.Editable;
import android.text.TextWatcher;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> users = new ArrayList();
    ArrayList<String> selectedUsers = new ArrayList();
    ArrayAdapter<String> adapter;
    ListView usersList;

    ArrayList<String> filteredUsers = new ArrayList<>();
    EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Collections.addAll(users, "Омельницкая", "Иванов", "Петров", "Сидоров");
        usersList = findViewById(R.id.usersList);

        filteredUsers.addAll(users);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, filteredUsers);

        usersList.setAdapter(adapter);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String user = adapter.getItem(position);
                if(usersList.isItemChecked(position))
                    selectedUsers.add(user);
                else
                    selectedUsers.remove(user);
            }
        });

        searchField = findViewById(R.id.searchField);

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    //фильтрация списка без учeта регистра
    private void filterList(String query) {
        filteredUsers.clear();
        if (query.isEmpty()) {
            filteredUsers.addAll(users);
        } else {
            String lowerQuery = query.toLowerCase();
            for (String user : users) {
                if (user.toLowerCase().contains(lowerQuery)) {
                    filteredUsers.add(user);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    public void add(View view) {
        EditText userName = findViewById(R.id.userName);
        String user = userName.getText().toString().trim();
        if (!user.isEmpty())
        {
            users.add(user);
            userName.setText("");
            filterList(searchField.getText().toString());
        }
    }
    public void remove(View view) {
        for (int i = 0; i < selectedUsers.size(); i++)
        {
            users.remove(selectedUsers.get(i));
        }
        usersList.clearChoices();
        selectedUsers.clear();
        filterList(searchField.getText().toString());
    }
}