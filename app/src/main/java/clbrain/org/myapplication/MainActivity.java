package clbrain.org.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    RoundProgressLine progressLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressLine = findViewById(R.id.qwe);
        progressLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressLine.setProgress(progressLine.getProgress() + 100);
            }
        });
    }
}
