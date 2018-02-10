package com.example.milja.languageapp.model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.milja.languageapp.R;

import org.w3c.dom.Text;

/**
 * Created by Milja on 2018-02-07.
 */

public class ListCursorAdapter extends CursorAdapter {

    private final LayoutInflater layoutInflater;

    //Constructor
    public ListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("MyLog", "Listcursoradapter");
    }

    //Inflater
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d("MyLog", "newView");
        return layoutInflater.inflate(R.layout.activity_list_view_cell, parent, false);
    }


    //Bind data from the cursor to individual row view
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView word = view.findViewById(R.id.textViewWord);
        word.setText(cursor.getString(1));
        TextView translation = view.findViewById(R.id.textViewTranslation);
        translation.setText(cursor.getString(2));
        TextView gender = view.findViewById(R.id.textViewGender);
        int g = cursor.getInt(6);
        if (g==1) {
            gender.setText("M");
        } else if (g==2){
            gender.setText("F");
        } else {
            gender.setText("");
        }
    }
}
