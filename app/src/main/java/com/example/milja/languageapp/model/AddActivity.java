package com.example.milja.languageapp.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.milja.languageapp.R;
import com.example.milja.languageapp.database.DBHelper;
import com.example.milja.languageapp.database.Gender;
import com.example.milja.languageapp.database.Type;
import com.example.milja.languageapp.database.Word;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private String wordHindi;
    private String wordEng;
    private int checkBoxValue = 1;
    private Type type = new Type();
    private int typeValue = 5;
    private int genderValue = 3;
    private String sentenceHindi;
    private String sentenceEng;

    private EditText editTextHindiWord;
    private CheckBox checkBoxDifficult;
    private EditText editTextEngWord;
    private Spinner spinnerType;
    private RadioGroup radioGroup;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private EditText editTextHindiSentence;
    private EditText editTextEngSentence;

    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getViews();
        getAllTypes();
    }

    //Method to find views.
    private void getViews() {
        this.editTextHindiWord = findViewById(R.id.editTextHindiWord);
        this.checkBoxDifficult = findViewById(R.id.checkBoxDifficult);
        this.editTextEngWord = findViewById(R.id.editTextEngWord);
        this.spinnerType = findViewById(R.id.spinnerValue);
        this.radioGroup = findViewById(R.id.radioGroup);
        this.radioMale = findViewById(R.id.radioButtonMale);
        this.radioFemale = findViewById(R.id.radioButtonFemale);
        this.editTextHindiSentence = findViewById(R.id.editTextHindiSentence);
        this.editTextEngSentence = findViewById(R.id.editTextEngSentence);
    }

    //Populate spinner from database
    public void getAllTypes() {
        List typeList = dbHelper.getAllTypes();
        ArrayAdapter<Type> adapter = new ArrayAdapter<Type>(AddActivity.this, android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
    }

    //Save all inserted form data
    public void onAddButtonClick(View view) {
        this.wordHindi = editTextHindiWord.getText().toString();
        this.wordEng = editTextEngWord.getText().toString();

        this.type = (Type)spinnerType.getSelectedItem();
        this.typeValue = (int)type.getTypeId();
        this.sentenceHindi = editTextHindiSentence.getText().toString();
        this.sentenceEng = editTextEngSentence.getText().toString();

        addWord();
    }

    //Get radiobutton value
    public void onRadioButtonClick(View view) {
        boolean checked = ((RadioButton)view).isChecked();

        switch(view.getId()) {
            case R.id.radioButtonMale:
                if(checked)
                    genderValue = 1;
                Log.d("MyLog", "Chose male");
                break;
            case R.id.radioButtonFemale:
                if(checked)
                    genderValue = 2;
                Log.d("MyLog", "Chose female");
                break;
        }
    }

    //Get checkbox value
    public void onCheckBoxClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if(checked) {
            Log.d("MyLog", "Checked as difficult.");
            checkBoxValue = 2;
        } else {
            Log.d("MyLog", "Not difficult.");
            checkBoxValue = 1;
        }
    }

    public void addWord() {
        Word word = new Word(wordHindi, wordEng, sentenceHindi, sentenceEng, genderValue, typeValue, checkBoxValue);
        dbHelper.addWord(word);
        Log.d("MyLog", "ADD ACT: " + word.getWordId() + ", " + word.getWordWord() + ", " + word.getWordTranslation() + ", " + word.getWordSentenceHindi() + ", " + word.getWordSentenceEng() + ", gender: " + word.getWordGenderId() + ", " + word.getWordGenderGroup() + ", type: "+ word.getWordTypeId() + ", "+ word.getWordTypeGroup() + ", diff: " + word.getWordDifficult() );
        //TODO: Display toast and move to new activity
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("WordId", word.getWordId());
        startActivity(intent);
        finish();
    }


}
