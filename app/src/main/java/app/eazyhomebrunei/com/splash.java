package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;




public class splash extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final Boolean islogged = pref.getBoolean("islogged", false);
        Thread timerthread = new Thread() {
            public void run() {
                try {
                    sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            finally

            {
                if (islogged == true) {
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                }
            }

        }
        };
        timerthread.start();

    }

    }



