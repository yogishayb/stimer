package com.insajet.snehatimer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    boolean running = false;
    TextView textView;
    int seconds = 0;
    long milliseconds = 0;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(0xff333300));
      getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\">Timer</font>"));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.timer);
        listView  = findViewById(R.id.lv);
        arrayList = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.e("timee", String.valueOf(milliseconds));
//                int h = seconds/3600;
//                int m = (seconds%3600)/60;
//                int s = (seconds%3600)%60;
                long h = milliseconds/3600000;
                long m = (milliseconds%36000000)/60000;
                long s =( (milliseconds%36000000)%60000)/1000;
                long ms =(( (milliseconds%36000000)%60000)%1000)/getRandom();
                String ftm =String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d",h,m,s,ms);
                textView.setText(ftm);

                if (running){
                    milliseconds=100+milliseconds;
                }
                handler.postDelayed(this,100);
            }
        });
    }


    public void click(View view){

        if(view.getId()==R.id.b1){
            if(!running){
                running = true;
                ((Button)view).setText("Pause");
            }else if(running){
                running = false;
                ((Button)view).setText("Resume");
            }
        }

        else if(view.getId()==R.id.b2){
            running = false;
            seconds  =0;
            milliseconds = 0;
            arrayList.clear();
            setListView();
            ((Button)findViewById(R.id.b1)).setText("Start");
        }

        else if(view.getId() == R.id.lapbtn){
            if(running)
                arrayList.add(textView.getText().toString());
            setListView();
        }
    }


    public void setListView(){
        arrayAdapter.notifyDataSetChanged();
        listView.smoothScrollToPosition(arrayAdapter.getCount()-1);
    }

     public static long getRandom(){

         return new Random().nextInt(10)+1;
    }

}