package com.example.s.lists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> tasks;
    private ArrayAdapter<String> tasksAdapter;
    private ListView tasklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readTasks();
        tasklist = (ListView) findViewById(R.id.tasklist);
        tasks = new ArrayList<String>();
        tasksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);

        tasklist.setAdapter(tasksAdapter);
        tasks.add("Finish this app");
        tasks.add("Work on Hangman");

        setupListViewListener();
    }

    private void readTasks() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            tasks = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            tasks = new ArrayList<String>();
        }
    }

    private void writeTasks() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onAddTask(View v){
        EditText newTask = (EditText) findViewById(R.id.newTask);
        String taskText = newTask.getText().toString();
        tasksAdapter.add(taskText);
        newTask.setText(" ");
        writeTasks();
    }

    protected void setupListViewListener() {
        tasklist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                tasks.remove(position);
                tasksAdapter.notifyDataSetChanged();
                writeTasks();
                return true;
            }
        });
    }


}
