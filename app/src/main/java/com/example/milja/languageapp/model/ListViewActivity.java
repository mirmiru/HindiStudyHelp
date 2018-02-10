package com.example.milja.languageapp.model;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

    @Override
    protected void onRestart() {
        super.onRestart();
        Cursor c = dbHelper.getAllWordsCursor();
        listCursorAdapter.changeCursor(c);
        listCursorAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_main:
                Intent mainIntent = new Intent(this, MainActivity.class);
                this.startActivity(mainIntent);
                break;
            case R.id.action_add:
                Intent addIntent = new Intent(this, AddActivity.class);
                this.startActivity(addIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}