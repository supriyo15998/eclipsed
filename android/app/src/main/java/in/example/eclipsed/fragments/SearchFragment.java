package in.example.eclipsed.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import in.example.eclipsed.R;
import in.example.eclipsed.adapters.UserAdapter;
import in.example.eclipsed.models.GlobalRequest;
import in.example.eclipsed.models.GlobalResponse;
import in.example.eclipsed.models.User;
import in.example.eclipsed.remote.APIClient;
import in.example.eclipsed.remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private String TAG = "SearchFragment";
    SearchView searchView;
    APIService apiService;
    private RecyclerView searchRecyclerView;
    private UserAdapter userAdapter;
    private List<User> users;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.search);
        apiService = APIClient.getClient().create(APIService.class);
        searchRecyclerView = view.findViewById(R.id.searchRecyclerview);
        users = new ArrayList<>();
        userAdapter = new UserAdapter(getActivity(),users);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.setAdapter(userAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                GlobalRequest gr = new GlobalRequest();
                gr.setUsername(newText);
                Call<GlobalResponse> call = apiService.getUsers(gr);
                call.enqueue(new Callback<GlobalResponse>() {
                    @Override
                    public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
                        Log.d(TAG,"response successful");
                        Log.d(TAG,String.valueOf(response.code()));
                        Log.d(TAG,new Gson().toJson(response.body()));
                        users.clear();
                        users.addAll(response.body().getUsers());
                        userAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<GlobalResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }
        });
        return view;
    }
}
