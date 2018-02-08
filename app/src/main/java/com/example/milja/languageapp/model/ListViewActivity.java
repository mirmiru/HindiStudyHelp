package com.example.milja.languageapp.model;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.milja.languageapp.R;
import com.example.milja.languageapp.database.DBHelper;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    private DBHelper dbHelper = new DBHelper(this);
    private ListCursorAdapter listCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        findViews();
        createCursorAdapter();

        //Move to info view for word
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MyLog", "onItemClick: Click on item works");
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("WordId", id);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        this.listView = (ListView)findViewById(R.id.listView);
        Log.d("MyLog", "findViews");
    }

    private void createCursorAdapter() {
        Cursor c = dbHelper.getAllWordsCursor();
        this.listCursorAdapter = new ListCursorAdapter(this, c);
        listView.setAdapter(listCursorAdapter);
        Log.d("MyLog", "createCursorAdapter");

    }
}