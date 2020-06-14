package in.example.eclipsed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import in.example.eclipsed.fragments.InboxFragment;
import in.example.eclipsed.fragments.ProfileFragment;
import in.example.eclipsed.fragments.SearchFragment;
import in.example.eclipsed.session.SessionManager;

public class DashboardActivity extends AppCompatActivity {
    TextView welcomeTxtView;
    TextView welcomeUsername;
    SessionManager sessionManager;
    BottomNavigationView bottomNavigationMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(this);
        bottomNavigationMenu = findViewById(R.id.bottomNavigation);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(task.isSuccessful()) {
                    Log.d("DashboardActivity", task.getResult().getToken());
                } else {
                    Log.e("DashboardActivity", task.getException().getMessage());
                    task.getException().printStackTrace();
                }
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment selected = null;

                switch (menuItem.getItemId()) {
                    case R.id.nav_profile:
                        selected = ProfileFragment.newInstance(true, sessionManager.getUserData());
                        break;
                    case R.id.inbox:
                        selected = new InboxFragment();
                        break;
                    case R.id.search:
                        selected = new SearchFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selected).commit();

                return true;
            }
        });

    }

    private BottomNavigationView.OnNavigationItemReselectedListener navListener = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_navigation,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Toast.makeText(this, "You're successfully logged out!", Toast.LENGTH_SHORT).show();
                sessionManager.setIsLoggedIn(false);
                sessionManager.setKeyAccessToken(null);
                sessionManager.setUserData(null);
                startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
