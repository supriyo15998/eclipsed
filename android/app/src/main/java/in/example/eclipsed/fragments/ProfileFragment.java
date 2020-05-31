package in.example.eclipsed.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import in.example.eclipsed.LoginActivity;
import in.example.eclipsed.R;
import in.example.eclipsed.session.SessionManager;

public class ProfileFragment extends Fragment {
    TextView welcomeTxtView;
    TextView welcomeUsername;
    SessionManager sessionManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        welcomeTxtView = view.findViewById(R.id.welcomeTxtView);
        welcomeUsername = view.findViewById(R.id.welcomeUsername);
        sessionManager = new SessionManager(getActivity());
        if (sessionManager.getKeyAccessToken() != null && !sessionManager.getKeyAccessToken().isEmpty())
        {
            welcomeTxtView.setText("Welcome " + sessionManager.getUserData().getName());
            welcomeUsername.setText("Username: " + sessionManager.getUserData().getUsername());
        }
        else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            Toast.makeText(getActivity(), "Please login to continue!", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        return view;
    }

}
