package com.alex.waveview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import github.alex.waveview.WaveView;

public class MainActivity extends AppCompatActivity {
    private WaveView waveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        waveView = (WaveView) findViewById(R.id.wv);
    }
}
