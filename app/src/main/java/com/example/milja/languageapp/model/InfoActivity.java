package com.example.milja.languageapp.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.milja.languageapp.R;
import com.example.milja.languageapp.database.DBHelper;
import com.example.milja.languageapp.database.Word;

public class InfoActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);

    private Long wordId;
    private Word word;

    private TextView textViewHindiWord;
    private TextView textViewEngWord;
    private TextView textViewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        findViews();
        getWord();
        showWordInfo();
    }

    //Get the word from the database
    private void getWord() {
        Intent intent = getIntent();
        this.wordId = intent.getLongExtra("WordId", 0);
        this.word = dbHelper.getWordById(wordId);
    }

    //Find all views and save to variables
    private void findViews() {
        this.textViewHindiWord = findViewById(R.id.textViewHindiWord);
        this.textViewEngWord = findViewById(R.id.textViewEngWord);
        this.textViewType = findViewById(R.id.textViewType);
    }

    //Display word information in view
    private void showWordInfo() {
        this.textViewHindiWord.setText(word.getWordWord());
        this.textViewEngWord.setText(word.getWordTranslation());
        this.textViewType.setText(word.getWordTypeGroup());
    }
}

