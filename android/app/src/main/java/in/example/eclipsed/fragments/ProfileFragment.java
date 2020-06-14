package in.example.eclipsed.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import in.example.eclipsed.LoginActivity;
import in.example.eclipsed.R;
import in.example.eclipsed.models.GlobalRequest;
import in.example.eclipsed.models.GlobalResponse;
import in.example.eclipsed.models.Message;
import in.example.eclipsed.models.User;
import in.example.eclipsed.remote.APIClient;
import in.example.eclipsed.remote.APIService;
import in.example.eclipsed.session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private final String TAG = "ProfileFragment";
    TextView welcomeTxtView;
    TextView welcomeUsername;
    TextView loggedInUserMessagesCount;
    SessionManager sessionManager;
    APIService apiService;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    private static final String ARG_PROFILE = "isOwnProfile";
    private static final String USER_DATA = "userData";
    private boolean isOwnProfile;
    public static ProfileFragment newInstance(boolean isOwnProfile, User user) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PROFILE,isOwnProfile);
        args.putSerializable(USER_DATA, user);
        profileFragment.setArguments(args);
        return profileFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        loggedInUserMessagesCount = view.findViewById(R.id.receivedMessagesCount);
        apiService = APIClient.getClient().create(APIService.class);
        progressDialog  = new ProgressDialog(getContext());
        progressDialog.setTitle("Sending message");
        progressDialog.setCancelable(false);
        sessionManager = new SessionManager(getContext());
        Call<GlobalResponse> call = apiService.getMessages("Bearer " + sessionManager.getKeyAccessToken());

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout infoLayout = view.findViewById(R.id.infoLayout);
        LinearLayout userInfoLayout = view.findViewById(R.id.userInfoLayout);
        TextView searchUserName;
        TextView searchUsername;
        final EditText message;
        Button sendButton;
        if(getArguments() != null){
            isOwnProfile = getArguments().getBoolean(ARG_PROFILE);
            Log.d(TAG, ((User) getArguments().getSerializable(USER_DATA)).getName());
            if (isOwnProfile) {
                infoLayout.setVisibility(View.VISIBLE);
                userInfoLayout.setVisibility(View.INVISIBLE);
            }
            else {
                infoLayout.setVisibility(View.INVISIBLE);
                userInfoLayout.setVisibility(View.VISIBLE);
                searchUserName = view.findViewById(R.id.searchUserName);
                searchUsername = view.findViewById(R.id.searchUsername);
                message = view.findViewById(R.id.messageToBeSent);
                sendButton = view.findViewById(R.id.sendMsgButton);
                searchUserName.setText(((User) getArguments().getSerializable(USER_DATA)).getName());
                searchUsername.setText(((User) getArguments().getSerializable(USER_DATA)).getUsername());
                sendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        //call send message api from here
                        GlobalRequest request = new GlobalRequest();
                        request.setUsername(((User) getArguments().getSerializable(USER_DATA)).getUsername());
                        request.setMessage(message.getText().toString());
                        Call<GlobalResponse> call = apiService.sendMessage(request);
                        call.enqueue(new Callback<GlobalResponse>() {
                            @Override
                            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
                                progressDialog.dismiss();
                                builder.setMessage(new Gson().toJson(response.body().getMessage()));
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, ProfileFragment.newInstance(true,sessionManager.getUserData())).commit();
                                    }
                                });
                                alertDialog = builder.create();
                                alertDialog.show();
                                Log.d(TAG,"Response Successful");
                                Log.d(TAG, String.valueOf(response.code()));
                                Log.d(TAG, new Gson().toJson(response.body()));
                            }

                            @Override
                            public void onFailure(Call<GlobalResponse> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                Log.d(TAG,t.getMessage());
                            }
                        });
                    }
                });
            }
        }
        welcomeTxtView = view.findViewById(R.id.welcomeTxtView);
        welcomeUsername = view.findViewById(R.id.welcomeUsername);
        sessionManager = new SessionManager(getActivity());
        if (sessionManager.getKeyAccessToken() != null && !sessionManager.getKeyAccessToken().isEmpty())
        {
            welcomeTxtView.setText("Welcome " + sessionManager.getUserData().getName());
            welcomeUsername.setText("Username: " + sessionManager.getUserData().getUsername());
            loggedInUserMessagesCount.setText("Received Messages: " + sessionManager.getUserData().getMessages_count());
        }
        else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            Toast.makeText(getActivity(), "Please login to continue!", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        return view;
    }

}
