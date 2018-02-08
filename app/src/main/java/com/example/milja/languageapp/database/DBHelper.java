package com.example.milja.languageapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milja on 2018-02-04.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "WORD";
    private static final String DATABASE_NAME = "hindiHelper.db";
    private static final int DATABASE_VERSION = 1;

    //Constructor
    public DBHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    //Word table
    public static final String TABLE_WORD = "word";
    public static final String COLUMN_WORD_ID = "_id";
    public static final String COLUMN_WORD_WORD = "wordWord";
    public static final String COLUMN_WORD_TRANSLATION = "wordTranslation";
    public static final String COLUMN_WORD_SENTENCEHINDI = "wordSentenceHindi";
    public static final String COLUMN_WORD_SENTENCEENG = "wordSentenceEng";
    public static final String COLUMN_WORD_GENDERID = "wordGenderId";
    public static final String COLUMN_WORD_TYPEID = "wordTypeId";
    public static final String COLUMN_WORD_DIFFICULT = "wordDifficult";
    private static final String TABLE_WORD_CREATE =
            "CREATE TABLE " + TABLE_WORD + " (" +
                    COLUMN_WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_WORD_WORD + " TEXT, " +
                    COLUMN_WORD_TRANSLATION + " TEXT, " +
                    COLUMN_WORD_SENTENCEHINDI + " TEXT, " +
                    COLUMN_WORD_SENTENCEENG + " TEXT, " +
                    COLUMN_WORD_TYPEID + " INTEGER, " +
                    COLUMN_WORD_GENDERID + " INTEGER, " +
                    COLUMN_WORD_DIFFICULT + " INTEGER" +
                    ")";

    //Gender table
    public static final String TABLE_GENDER = "gender";
    public static final String COLUMN_GENDER_ID = "_id";
    public static final String COLUMN_GENDER_GROUP = "genderGroup";
    private static final String TABLE_GENDER_CREATE =
            "CREATE TABLE " + TABLE_GENDER + " (" +
                    COLUMN_GENDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_GENDER_GROUP + " TEXT" +
                    ")";

    //Type table
    public static final String TABLE_TYPE = "type";
    public static final String COLUMN_TYPE_ID = "_id";
    public static final String COLUMN_TYPE_GROUP = "typeGroup";
    private static final String TABLE_TYPE_CREATE =
            "CREATE TABLE " + TABLE_TYPE + " (" +
                    COLUMN_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TYPE_GROUP + " TEXT " +
                    ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create tables
        db.execSQL(TABLE_WORD_CREATE);
        db.execSQL(TABLE_GENDER_CREATE);
        db.execSQL(TABLE_TYPE_CREATE);

        Log.i(LOGTAG, "Tables created.");

        //Gender table - Default values
        ContentValues valuesGender = new ContentValues();
        valuesGender.put(COLUMN_GENDER_GROUP, "Male");
        db.insert(TABLE_GENDER, null, valuesGender);
        valuesGender.put(COLUMN_GENDER_GROUP, "Female");
        db.insert(TABLE_GENDER, null, valuesGender);
        valuesGender.put(COLUMN_GENDER_GROUP, "No gender");
        db.insert(TABLE_GENDER, null, valuesGender);

        Log.i(LOGTAG, "Gender values inserted.");

        //Type table - Default values
        ContentValues valuesType = new ContentValues();
        valuesType.put(COLUMN_TYPE_GROUP, "Adjective");
        db.insert(TABLE_TYPE, null, valuesType);
        valuesType.put(COLUMN_TYPE_GROUP, "Adverb");
        db.insert(TABLE_TYPE, null, valuesType);
        valuesType.put(COLUMN_TYPE_GROUP, "Noun");
        db.insert(TABLE_TYPE, null, valuesType);
        valuesType.put(COLUMN_TYPE_GROUP, "Verb");
        db.insert(TABLE_TYPE, null, valuesType);
        valuesType.put(COLUMN_TYPE_GROUP, "Other");
        db.insert(TABLE_TYPE, null, valuesType);

        Log.i(LOGTAG, "Type values inserted.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);

        Log.i(LOGTAG, "Database has been upgraded from " + oldVersion + " to " + newVersion);
    }


    //Add word
    public void addWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_WORD_WORD, word.getWordWord());
        values.put(COLUMN_WORD_TRANSLATION, word.getWordTranslation());
        values.put(COLUMN_WORD_SENTENCEHINDI, word.getWordSentenceHindi());
        values.put(COLUMN_WORD_SENTENCEENG, word.getWordSentenceEng());
        values.put(COLUMN_WORD_GENDERID, word.getWordGenderId());
        values.put(COLUMN_WORD_TYPEID, word.getWordTypeId());
        values.put(COLUMN_WORD_DIFFICULT, word.getWordDifficult());

        //Set word id
        long id = db.insert(TABLE_WORD, null, values);
        word.setWordId(id);

        //Set gender name -> getGenderGroupName
        setWordGenderGroup(word);
        //Set typename -> getTypeName
        setWordTypeGroup(word);
    }

    public long searchHindiWord(String word) {
        Log.d("MyLog", "Passed: "+word);
        String selection = COLUMN_WORD_WORD + "=?";
        String[] selectionArgs = new String[] {word};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_WORD, null, selection, selectionArgs, null, null, null);

        long id = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            id = c.getLong(c.getColumnIndex(DBHelper.COLUMN_WORD_ID));
        }
        return id;
    }

    public boolean deleteWord(long id) {
        SQLiteDatabase db = getWritableDatabase();

        String[] selectionArgs = new String[] {Long.toString(id)};
        int result = db.delete(TABLE_WORD, "_id=?", selectionArgs);

        if (result==1) {
            return true;
        } else {
            return false;
        }
    }


    public Word getWordById(long id) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " +
                "word INNER JOIN type ON " +
                "word.wordTypeId =  type._id " +
                "INNER JOIN gender ON " +
                "word.wordGenderId = gender._id " +
                "WHERE word._id=" + id;

        Cursor c = db.rawQuery(query, null);

        Word word = new Word();

        if (c.moveToFirst()) {
            do {
                word.setWordId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_WORD_ID)));
                word.setWordWord(c.getString(c.getColumnIndex(DBHelper.COLUMN_WORD_WORD)));
                word.setWordTranslation(c.getString(c.getColumnIndex(DBHelper.COLUMN_WORD_TRANSLATION)));
                word.setWordSentenceHindi(c.getString(c.getColumnIndex(DBHelper.COLUMN_WORD_SENTENCEHINDI)));
                word.setWordSentenceEng(c.getString(c.getColumnIndex(DBHelper.COLUMN_WORD_SENTENCEENG)));
                word.setWordGenderId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_WORD_GENDERID)));
                word.setWordGenderGroup(c.getString(c.getColumnIndex(DBHelper.COLUMN_GENDER_GROUP)));
                word.setWordTypeId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_WORD_TYPEID)));
                word.setWordTypeGroup(c.getString(c.getColumnIndex(DBHelper.COLUMN_TYPE_GROUP)));
                word.setWordDifficult(c.getInt(c.getColumnIndex(DBHelper.COLUMN_WORD_DIFFICULT)));
                Log.d("MyLog", "INSERTED: " + word.getWordId() + ", " + word.getWordWord() + ", " + word.getWordTranslation() + ", " + word.getWordSentenceHindi() + ", " + word.getWordSentenceEng() + ", gender: " + word.getWordGenderId() + ", " + word.getWordGenderGroup() + ", type: "+ word.getWordTypeId() + ", "+ word.getWordTypeGroup() + ", diff: " + word.getWordDifficult() );
            } while (c.moveToNext());
        }
        c.close();
        return word;
    }

    //Get all type values
    public List<Type> getAllTypes() {
        List<Type> typeList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_TYPE, null, null, null, null, null, null);
        boolean success = c.moveToFirst();

        if(success) {
            do {
                Type type = new Type();
                type.setTypeId(c.getLong(0));
                type.setTypeGroup(c.getString(1));
                typeList.add(type);
                Log.d("MyLog", "Type: " + type.getTypeId() + ", " + type.getTypeGroup());
            } while (c.moveToNext());
        }
        c.close();
        return typeList;
    }

    //Get all words
    public Cursor getAllWordsCursor() {
        SQLiteDatabase db = getReadableDatabase();

        long rows = DatabaseUtils.queryNumEntries(db, TABLE_WORD);
        Log.d("MyLog", "Rows: " + rows);

        return db.query(TABLE_WORD, null, null, null, null, null, null);
    }


    //Get all gender values
    public List<Gender> getAllGenders() {
        List<Gender> genderList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_GENDER, null, null, null, null, null, null);

        if(c.moveToFirst()) {
            do {
                Gender gender = new Gender();
                gender.setGenderId(c.getLong(0));
                gender.setGenderGroup(c.getString(1));
                genderList.add(gender);

                Log.d("MyLog", "Gender: " + gender.getGenderId() + ", " + gender.getGenderGroup());
            } while (c.moveToNext());
        }
        c.close();
        return genderList;
    }

    public void setWordGenderGroup(Word word) {
        long id = word.getWordId();
        String selection = COLUMN_GENDER_ID +"=?";
        String[] selectionArgs = new String[] {Long.toString(id)};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_GENDER, null, selection, selectionArgs, null, null, null, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            String genderGroup = c.getString(1);
            word.setWordGenderGroup(genderGroup);
            Log.d(LOGTAG, "Word " + word.getWordWord() + " Gender group set as " + genderGroup);
        }
        c.close();
    }

    public void setWordTypeGroup(Word word) {
        long id = word.getWordTypeId();
        String selection = COLUMN_TYPE_ID + "=?";
        String[] selectionArgs = new String[] {Long.toString(id)};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_TYPE, null, selection, selectionArgs, null, null, null, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            String typeGroup = c.getString(1);
            word.setWordTypeGroup(typeGroup);
            Log.d(LOGTAG, "Word " + word.getWordWord() + " type group set as " + typeGroup);
        }
        c.close();
    }
}