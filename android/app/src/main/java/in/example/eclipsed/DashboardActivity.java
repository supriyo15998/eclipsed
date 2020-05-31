package in.example.eclipsed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        //starts here
//        welcomeTxtView = findViewById(R.id.welcomeTxtView);
//        welcomeUsername = findViewById(R.id.welcomeUsername);
        //ends here
        bottomNavigationMenu = findViewById(R.id.bottomNavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment selected = null;

                switch (menuItem.getItemId()) {
                    case R.id.nav_profile:
                        selected = new ProfileFragment();
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
                Toast.makeText(this, "Logout pressed!", Toast.LENGTH_SHORT).show();
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
