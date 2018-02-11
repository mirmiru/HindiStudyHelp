package com.example.milja.languageapp.model;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milja.languageapp.R;

/**
 * Created by Milja on 2018-02-11.
 */

public class DifficultListCursorAdapter extends CursorAdapter {

    private final LayoutInflater layoutInflater;

    //Constructor
    public DifficultListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Inflater
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.activity_difficult_list_cell, parent, false);
    }

    //Bind data from the cursor to individual row view
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView word = view.findViewById(R.id.textViewWord);
        word.setText(cursor.getString(1));
        TextView translation = view.findViewById(R.id.textViewTranslation);
        translation.setText(cursor.getString(2));
    }
}
