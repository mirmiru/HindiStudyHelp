package com.example.milja.languageapp.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milja.languageapp.R;
import com.example.milja.languageapp.database.DBHelper;
import com.example.milja.languageapp.database.Word;

import org.w3c.dom.Text;

public class InfoActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);
    public static final int REQUEST_CODE_EDIT = 2;

    private Long wordId;
    private Word word;

    private TextView textViewHindiWord;
    private TextView textViewEngWord;
    private TextView textViewType;
    private TextView textViewHindiSent;
    private TextView textViewEngSent;

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
        this.textViewHindiSent = findViewById(R.id.textViewSentHindi);
        this.textViewEngSent = findViewById(R.id.textViewSentEng);
    }

    //Display word information in view
    private void showWordInfo() {
        this.textViewHindiWord.setText(word.getWordWord());
        this.textViewEngWord.setText(word.getWordTranslation());
        String typeString = "";
        switch ((int)word.getWordTypeId()) {
            case 1:
                typeString = "Adj.";
                break;
            case 2:
                typeString = "Adv.";
                break;
            case 3:
                typeString = "Noun";
                break;
            case 4:
                typeString = "Verb";
                break;
            case 5:
                typeString = "Other";
                break;
        }
        this.textViewType.setText(typeString);
        this.textViewHindiSent.setText(word.getWordSentenceHindi());
        this.textViewEngSent.setText(word.getWordSentenceEng());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_delete:
                if (dbHelper.deleteWord(wordId)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Deleted entry " + word.getWordWord(), Toast.LENGTH_SHORT);
                    toast.show();
                }
                Intent listIntent = new Intent(this, ListViewActivity.class);
                this.startActivity(listIntent);
                break;
            case R.id.action_edit:
                Intent editIntent = new Intent(this, EditActivity.class);
                editIntent.putExtra("WordId", wordId);
                this.startActivity(editIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}

