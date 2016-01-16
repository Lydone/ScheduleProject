package com.lydone.schedule;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);


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
                    textView.append(bus + ";" + ' ' + date + '\n');
                    editText.setText(null);
                    clipboard.setText(textView.getText());
                    Log.d("TAG", textView.getText().toString());
                    writeFileSD(textView.getText().toString());
                }
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Текст сохраняется автоматически в буфер и в: " + path, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void writeFile(String string) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("Schedule.txt", MODE_WORLD_WRITEABLE)));
            bw.write(string);
            bw.close();
            Log.d("TAG", "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeFileSD(String string) {
        if (!Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)); {
            //Log.d("TAG", "SD Карта не найдена" + Environment.getExternalStorageState());
        }
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "Schedule");
        sdPath.mkdirs();
        File sdFile = new File(sdPath, "Schedule.txt");

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile, true));
            bw.write(string);
            bw.close();
            path = sdFile.getAbsolutePath();
            Log.d("TAG", "Файл записан" + sdFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
