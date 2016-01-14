package com.lydone.schedule;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(this.text);
        Log.e("TAG", this.text);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        setSupportActionBar(toolbar);

         editText.setOnKeyListener(new View.OnKeyListener() {
            // getting text from keyboard
            @Override

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String bus = editText.getText().toString();
                    Calendar calendar = Calendar.getInstance();
                    String date = calendar.getTime().toString();
                   //textView.append(bus + ";" + ' ' + date + '\n');
                    text += (bus + ";" + ' ' + date + '\n');
                    editText.setText(null);
                    textView.setText(text);
                    //some changes

                }
                return false;
            }
         });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Текст скопирован в буфер обмена", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                clipboard.setText(textView.getText());
            }
        });
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        text = savedInstanceState.getString("text");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", text);
    }


}