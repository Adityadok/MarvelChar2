package com.example.backup.marvelchar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SecondActivity extends AppCompatActivity {
    public TextView textView,textView1,textView2,textView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView=(TextView) findViewById(R.id.text);
        textView1=(TextView) findViewById(R.id.text1);
        textView2=(TextView) findViewById(R.id.text3);
        textView3=(TextView )findViewById(R.id.text6);

        textView.setText(getIntent().getExtras().getString("name"));
        textView1.setText(getIntent().getExtras().getString("modified"));
        textView2.setText(getIntent().getExtras().getString("id"));
        textView3.setText(getIntent().getExtras().getString("resourceURI"));

    }
}
