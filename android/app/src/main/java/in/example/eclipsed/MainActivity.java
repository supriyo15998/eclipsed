package in.example.eclipsed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import in.example.eclipsed.session.SessionManager;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    SessionManager sessionManager;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sessionManager = new SessionManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (sessionManager.isIsLoggedIn())
        {
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            finish();
        }
        else
        {
            progressBar = findViewById(R.id.progress);
            Handler handler = new Handler();
            final Timer t = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    counter+=2;
                    progressBar.setProgress(counter);
                    if (counter == 100)
                        t.cancel();
                }
            };
            t.schedule(tt,0,50);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            },2500);
        }
    }
}
