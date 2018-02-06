package com.example.milja.languageapp.database;

/**
 * Created by Milja on 2018-02-04.
 */

public class Word {

    private long wordId;
    private String wordWord;
    private String wordTranslation;
    private String wordSentenceHindi;
    private String wordSentenceEng;
    private int wordDifficult;
    private long wordGenderId;
    private long wordTypeId;

    //Empty constructor
    public Word() {}

    //Constructor with all arguments
    public Word(long wordId, String wordWord, String wordTranslation, String wordSentenceHindi, String wordSentenceEng, int wordDifficult, long wordGenderId, long wordTypeId) {
        this.wordId = wordId;
        this.wordWord = wordWord;
        this.wordTranslation = wordTranslation;
        this.wordSentenceHindi = wordSentenceHindi;
        this.wordSentenceEng = wordSentenceEng;
        this.wordDifficult = wordDifficult;
        this.wordGenderId = wordGenderId;
        this.wordTypeId = wordTypeId;
    }

    //Getters and setters

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public String getWordWord() {
        return wordWord;
    }

    public void setWordWord(String wordWord) {
        this.wordWord = wordWord;
    }

    public String getWordTranslation() {
        return wordTranslation;
    }

    public void setWordTranslation(String wordTranslation) {
        this.wordTranslation = wordTranslation;
    }

    public String getWordSentenceHindi() {
        return wordSentenceHindi;
    }

    public void setWordSentenceHindi(String wordSentenceHindi) {
        this.wordSentenceHindi = wordSentenceHindi;
    }

    public String getWordSentenceEng() {
        return wordSentenceEng;
    }

    public void setWordSentenceEng(String wordSentenceEng) {
        this.wordSentenceEng = wordSentenceEng;
    }

    public int getWordDifficult() {
        return wordDifficult;
    }

    public void setWordDifficult(int wordDifficult) {
        this.wordDifficult = wordDifficult;
    }

    public long getWordGenderId() {
        return wordGenderId;
    }

    public void setWordGenderId(long wordGenderId) {
        this.wordGenderId = wordGenderId;
    }

    public long getWordTypeId() {
        return wordTypeId;
    }

    public void setWordTypeId(long wordTypeId) {
        this.wordTypeId = wordTypeId;
    }
}
