package com.example.milja.languageapp.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.milja.languageapp.R;
import com.example.milja.languageapp.database.DBHelper;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);

    private EditText searchWord;
    private String userEntry;
    private long wordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void setUp() {
        searchWord = findViewById(R.id.editTextSearchWord);
        userEntry = searchWord.getText().toString();
    }

    protected void onButtonSearchClick(View view) {
        setUp();
        wordId = dbHelper.searchHindiWord(userEntry);
        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
        intent.putExtra("WordId", wordId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_search:
                Intent mainIntent = new Intent(this, MainActivity.class);
                this.startActivity(mainIntent);
                break;
            case R.id.action_add:
                Intent addIntent = new Intent(this, AddActivity.class);
                this.startActivity(addIntent);
                break;
            case R.id.action_list:
                Intent listIntent = new Intent(this, ListViewActivity.class);
                this.startActivity(listIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
