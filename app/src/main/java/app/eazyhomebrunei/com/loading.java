package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;


public class loading extends AppCompatActivity {
    private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        this.progress=findViewById(R.id.progress);
    progress.setVisibility(View.VISIBLE);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            progress.setVisibility(View.GONE);
        }
    },5000);
    }

    private void init() {
    }
}
