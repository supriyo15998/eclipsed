package in.example.eclipsed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

import in.example.eclipsed.models.Errors;
import in.example.eclipsed.models.ErrorsResponse;
import in.example.eclipsed.models.GlobalRequest;
import in.example.eclipsed.models.GlobalResponse;
import in.example.eclipsed.models.User;
import in.example.eclipsed.remote.APIClient;
import in.example.eclipsed.remote.APIService;
import in.example.eclipsed.session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText name, username, email, password, cnf_password;
    RadioGroup gender;
    Button registerButton;
    TextView loginTextView;
    CheckBox checkBox;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    APIService apiService;
    SessionManager sessionManager;
    public void registerBtnSubmit(View view)
    {
        if(!checkBox.isChecked()) {
            Log.d("RegisterActivity", "Please check to tnc");
            builder.setTitle("Agree to terms and conditions");
            builder.setMessage("Please agree to the terms and conditions");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.show();
        }
        else {
            Log.d("RegisterActivity", "All good to go");
            apiService = APIClient.getClient().create(APIService.class);
            registerUserFromActivity();
        }
    }

    private String findRadioButton(int checkedId) {
        String returnval;
        switch (checkedId) {
            case R.id.male:
                returnval = "male";
                break;
            case R.id.female:
                returnval = "female";
                break;
            case R.id.other:
                returnval = "other";
                break;
            default:
                returnval = "";
        }
        return returnval;
    }

    public void registerUserFromActivity()
    {
        String genderFinal = "";
        progressDialog.show();
        int checkedId = gender.getCheckedRadioButtonId();
        if (checkedId == -1){
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
        }
        else {
            genderFinal = findRadioButton(checkedId);
        }
        GlobalRequest request = new GlobalRequest();
        request.setName(name.getText().toString());
        request.setEmail(email.getText().toString());
        request.setGender(genderFinal);
        request.setUsername(username.getText().toString());
        request.setPassword(password.getText().toString());
        request.setPasswordConfirmation((cnf_password.getText().toString()));

        Call<GlobalResponse> call = apiService.registerUser(request);
        call.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
               progressDialog.dismiss();
               //String s = null;
                if(response.isSuccessful()) {
                    // sessionManager.setUser(response.body().getUser()); this is wahi sharedpreferences na? Haa
                    // redirect from here
                    Log.d(TAG,String.valueOf(response.code()));
                    if (response.code() == 200 || response.code() == 201)
                    {
                        try {
                            Log.d(TAG,"Inside try block");
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            //JSONObject jsonObject = new JSONObject(response.body().toString());
                            //Log.d(TAG,jsonObject.getString("access_token"));
                            sessionManager.setKeyAccessToken(jsonObject.getString("access_token"));
                            sessionManager.setUserData(response.body().getUser());
                            sessionManager.setIsLoggedIn(true);
                            Toast.makeText(RegisterActivity.this, "Welcome" + new Gson().toJson(response.body().getUser().getName().toString()), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,DashboardActivity.class));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        s = new Gson().toJson(response.body()).toString();
//                        Log.d(TAG,s);
                    }
                }


                if(response.errorBody() != null) {
                    try {
                        ErrorsResponse errorsResponse = new Gson().fromJson(response.errorBody().string(), ErrorsResponse.class);

                        Errors errors = errorsResponse.getErrors();

                        if (errors.getName() != null)
                            name.setError(errors.getName().get(0));

                        if (errors.getEmail() != null)
                            email.setError(errors.getEmail().get(0));

                        if (errors.getPassword() != null)
                            password.setError(errors.getPassword().get(0));

                        if (errors.getUsername() != null)
                            username.setError(errors.getUsername().get(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GlobalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Response failed");
                Log.e(TAG, t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupView();
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setupView() {
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cnf_password = findViewById(R.id.cnf_password);
        gender = findViewById(R.id.radioGroup);
        registerButton = findViewById(R.id.registerButton);
        loginTextView = findViewById(R.id.loginTextView);
        checkBox = findViewById(R.id.checkBox);
        builder = new AlertDialog.Builder(RegisterActivity.this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Requesting...");
        progressDialog.setCancelable(false);
        sessionManager = new SessionManager(this);
    }
}
