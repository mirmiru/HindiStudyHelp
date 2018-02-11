package com.example.milja.languageapp.model;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.milja.languageapp.R;
import com.example.milja.languageapp.database.DBHelper;

public class DifficultListActivity extends AppCompatActivity {

    private ListView listView;
    private DBHelper dbHelper = new DBHelper(this);
    private DifficultListCursorAdapter adapter;
    private ImageView star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficult_list);

        findViews();
        createCursorAdapter();
    }

    private void findViews() {
        listView = (ListView) findViewById(R.id.difficultListView);
        star = findViewById(R.id.imageViewStar);
    }

    private void createCursorAdapter() {
        Cursor c = dbHelper.getDifficultWordsCursor();
        adapter = new DifficultListCursorAdapter(this, c);
        listView.setAdapter(adapter);
    }

    //TODO: ADD MENU HERE
}
