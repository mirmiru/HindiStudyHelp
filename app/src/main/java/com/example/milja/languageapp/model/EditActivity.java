package com.example.milja.languageapp.model;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milja.languageapp.R;
import com.example.milja.languageapp.database.DBHelper;
import com.example.milja.languageapp.database.Type;
import com.example.milja.languageapp.database.Word;

import java.util.List;

public class EditActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);

    private Long oldWordId;
    private Word word;

    private String wordHindi;
    private String wordEng;
    private int checkBoxValue;
    private Type type = new Type();
    private int typeValue;
    private int genderValue;
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

    private int isDifficult = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWord();
        getViews();
        loadValues();
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

    //Load all values
    private void loadValues() {
        setAllTypes();
        setRadioButton();
        this.editTextHindiWord.setText(word.getWordWord());
        this.editTextEngWord.setText(word.getWordTranslation());
        this.editTextHindiSentence.setText(word.getWordSentenceHindi());
        this.editTextEngSentence.setText(word.getWordSentenceEng());

        if (word.getWordDifficult()==isDifficult) {
            this.checkBoxDifficult.setChecked(true);
            this.checkBoxValue = 2;
        }
    }

    //Populate spinner and set default value to the one previously selected by user
    public void setAllTypes() {
        List typeList = dbHelper.getAllTypes();
        String previousValue = word.getWordTypeGroup();
        this.typeValue = (int)word.getWordTypeId();
        ArrayAdapter<Type> adapter = new ArrayAdapter<Type>(this, android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setSelection(getSpinnerIndex(spinnerType, previousValue));
    }

    //Set radiobutton value
    public void setRadioButton() {
        int defaultValue = (int)word.getWordGenderId();
        switch(defaultValue) {
            case 1:
                this.radioMale.setChecked(true);
                this.genderValue = 1;
                break;
            case 2:
                this.radioFemale.setChecked(true);
                this.genderValue = 2;
                break;
            case 3:
                this.genderValue = 3;
        }
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

    //Save new values to database
    public void onEditButtonClick(View view) {
        this.wordHindi = editTextHindiWord.getText().toString();
        this.wordEng = editTextEngWord.getText().toString();
        this.sentenceHindi = editTextHindiSentence.getText().toString();
        this.sentenceEng = editTextEngSentence.getText().toString();

        String wordHindiNEW = wordHindi;
        String wordEngNEW = wordEng;
        String sentenceHindiNEW = sentenceHindi;
        String sentenceEngNEW = sentenceEng;
        long genderValueNEW = genderValue;
        long typeValueNEW = typeValue;
        int checkBoxValueNEW = checkBoxValue;

        Word wordWithNewValues = new Word(wordHindiNEW, wordEngNEW, sentenceHindiNEW, sentenceEngNEW, genderValueNEW, typeValueNEW, checkBoxValueNEW);
        boolean result = dbHelper.editWord(wordWithNewValues, oldWordId);

        if (result) {
            Toast toast = Toast.makeText(getApplicationContext(), "Saved new values for " + word.getWordWord(), Toast.LENGTH_SHORT);
            toast.show();
        }
        Intent listIntent = new Intent(this, ListViewActivity.class);
        startActivity(listIntent);
    }

    //Get default value for spinner
    private int getSpinnerIndex(Spinner spinner, String string) {
        int index = 0;
        for (int i=0; i < spinner.getCount(); i++) {
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(string)) {
                index = i;
                break;
            }
        }
        return index;
    }

    //Get the word from the database
    private void getWord() {
        Intent intent = getIntent();
        this.oldWordId = intent.getLongExtra("WordId", 0);
        this.word = dbHelper.getWordById(oldWordId);
    }
}