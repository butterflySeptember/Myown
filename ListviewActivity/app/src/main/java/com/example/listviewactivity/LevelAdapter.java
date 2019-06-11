package com.example.listviewactivity;

import android.widget.ArrayAdapter;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LevelAdapter extends ArrayAdapter {
    private final int resourceid;
    public LevelAdapter(Context context, int textviewResourceId, List<levelclass> objects) {
        super(context, textviewResourceId, objects);
        resourceid = textviewResourceId;
    }

    public  View getView(int position, View convertView, ViewGroup parent){
        levelclass level = (levelclass)getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceid,null);
        ImageView levelimage = (ImageView)view.findViewById(R.id.level_image);
        TextView levelname = (TextView)view.findViewById(R.id.level_name);
        levelimage.setImageResource(levelclass.getImage());
        levelname.setText(levelclass.getnamelevel());
        return view;
    }
}
