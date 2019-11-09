package com.example.sketcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    DrawingView dv = null;
    // Color available, default = red
    int[] color_arr = {R.color.colorRed, R.color.colorGreen,
        R.color.colorBlue,R.color.colorPurple};
    float[] size_arr = {15,30,60};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dv = new DrawingView(this,null);
        LinearLayout ll = (LinearLayout) findViewById(R.id.draw_view);
        ll.addView(dv);
    }
    public void clearDrawing(View v){
        dv.startNew();
    }

    public void changeBrushSize(View v){
        int viewID = v.getId();
        if(viewID == findViewById(R.id.button_small).getId()){
            dv.changePaintSize(size_arr[0]);
        }else if(viewID == findViewById(R.id.button_medium).getId()){
            dv.changePaintSize(size_arr[1]);
        }else if(viewID == findViewById(R.id.button_large).getId()){
            dv.changePaintSize(size_arr[2]);
        }
    }

    public void changePaintColor(View v){
        int viewID = v.getId();
        if(viewID == findViewById(R.id.button_red).getId()){
            dv.changePaintColor(color_arr[0]);
        }else if(viewID == findViewById(R.id.button_green).getId()){
            dv.changePaintColor(color_arr[1]);
        }else if(viewID == findViewById(R.id.button_blue).getId()){
            dv.changePaintColor(color_arr[2]);
        }else if(viewID == findViewById(R.id.button_purple).getId()){
            dv.changePaintColor(color_arr[3]);
        }
    }


}
