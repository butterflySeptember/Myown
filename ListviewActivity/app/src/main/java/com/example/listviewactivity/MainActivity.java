package com.example.listviewactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<levelclass> levellist = new ArrayList<levelclass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initlevelclass();
        initname();
    }

    private void initname() {
        levelclass peopleone = new levelclass("peopleone",R.drawable.ic_launcher_background);
        levellist.add(peopleone);

}

    private void initlevelclass() {
        LevelAdapter adapter = new LevelAdapter(MainActivity.this,R.layout.level_layout,levellist);
        ListView listview = (ListView)findViewById(R.id.list_view);
        listview.setAdapter(adapter);
    }
}
