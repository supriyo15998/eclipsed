package in.example.eclipsed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.example.eclipsed.models.ErrorsResponse;
import in.example.eclipsed.models.GlobalRequest;
import in.example.eclipsed.models.GlobalResponse;
import in.example.eclipsed.remote.APIClient;
import in.example.eclipsed.remote.APIService;
import in.example.eclipsed.session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    TextView registerTextView;
    EditText username;
    EditText password;
    Button loginButton;
    APIService apiService;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    public void loginButtonSubmit(View view)
    {
        apiService = APIClient.getClient().create(APIService.class);
        progressDialog.show();
        GlobalRequest globalRequest = new GlobalRequest();
        globalRequest.setUsername(username.getText().toString());
        globalRequest.setPassword(password.getText().toString());
        Call<GlobalResponse> call = apiService.loginUser(globalRequest);
        call.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    Log.d(TAG,String.valueOf(response.code()));
                    if (response.code() == 200)
                    {
                        Log.d(TAG,new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            sessionManager.setKeyAccessToken(jsonObject.getString("access_token"));
                            sessionManager.setUserData(response.body().getUser());
                            sessionManager.setIsLoggedIn(true);
                            Toast.makeText(LoginActivity.this, "Welcome " + new Gson().toJson(response.body().getUser().getName().toString()), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (response.errorBody() != null)
                {
                    try {
                        ErrorsResponse errorsResponse = new Gson().fromJson(response.errorBody().string(),ErrorsResponse.class);
                        if (errorsResponse.getMessage() != null)
                        {
                            username.setError(errorsResponse.getMessage());
                            password.setError(errorsResponse.getMessage());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GlobalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Response failed");
                Log.e(TAG, t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpViews();
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    private void setUpViews()
    {
        registerTextView = findViewById(R.id.registerTextView);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Requesting...");
        progressDialog.setCancelable(false);
        sessionManager = new SessionManager(this);
    }
}
