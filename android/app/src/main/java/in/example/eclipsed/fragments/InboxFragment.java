package in.example.eclipsed.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import in.example.eclipsed.R;
import in.example.eclipsed.adapters.MessageAdapter;
import in.example.eclipsed.models.GlobalRequest;
import in.example.eclipsed.models.GlobalResponse;
import in.example.eclipsed.models.Message;
import in.example.eclipsed.remote.APIClient;
import in.example.eclipsed.remote.APIService;
import in.example.eclipsed.session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxFragment extends Fragment {
    private String TAG = "InboxFragment";
    APIService apiService;
    SessionManager sessionManager;
    ProgressDialog progressDialog;

    private RecyclerView messageView;
    private MessageAdapter messageAdapter;
    private List<Message> messages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Requesting");
        progressDialog.setCancelable(false);
        sessionManager = new SessionManager(getContext());
        apiService = APIClient.getClient().create(APIService.class);

        progressDialog.show();
        messageView = view.findViewById(R.id.msgRecyclerView);
        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(getActivity(), messages);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        messageView.setLayoutManager(layoutManager);
        messageView.setAdapter(messageAdapter);

        Call<GlobalResponse> call = apiService.getMessages("Bearer " + sessionManager.getKeyAccessToken());
        call.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
                progressDialog.dismiss();
                Log.d(TAG,"Response Successful");
                Log.d(TAG, new Gson().toJson(response.body().getMessages().get(0)));
                Log.d(TAG,String.valueOf(response.code()));

                messages.clear();
                messages.addAll(response.body().getMessages());

                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GlobalResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG,"Response failed");
                Log.e(TAG, t.getMessage());
                t.printStackTrace();
            }
        });
        return view;
    }
}
