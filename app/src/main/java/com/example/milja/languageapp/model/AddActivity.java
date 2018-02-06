package com.example.milja.languageapp.model;

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
    private long checkBoxValue;
    private Type type = new Type();
    private long radioButtonValue;
    private long typeValue;
    private long genderValue;
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

        //this.checkBoxValue = checkBoxDifficult.getText().toString();

        this.type = (Type)spinnerType.getSelectedItem();
        this.typeValue = type.getTypeId();

        this.sentenceHindi = editTextHindiSentence.getText().toString();
        this.sentenceEng = editTextEngSentence.getText().toString();

    }

    //Get radiobutton value
    public void onRadioButtonClick(View view) {
        boolean checked = ((RadioButton)view).isChecked();

        switch(view.getId()) {
            case R.id.radioButtonMale:
                if(checked)
                    radioButtonValue = 1;
                Log.d("MyLog", "Chose male");
                break;
            case R.id.radioButtonFemale:
                if(checked)
                    radioButtonValue = 2;
                Log.d("MyLog", "Chose female");
                break;
        }
    }

    public void addWord() {
        Word word = new Word();
        dbHelper.addWord(word);
        //TODO: Display toast and move to new activity
    }


}
