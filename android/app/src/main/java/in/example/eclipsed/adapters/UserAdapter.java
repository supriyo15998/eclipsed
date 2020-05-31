package in.example.eclipsed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.example.eclipsed.R;
import in.example.eclipsed.models.User;
import in.example.eclipsed.viewholder.UserHolder;

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    Context context;
    List<User> userModel;

    public UserAdapter(Context context, List<User> userModel) {
        this.context = context;
        this.userModel = userModel;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user,null);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = userModel.get(position);
        holder.users.setText(user.getName());
        holder.usersName.setText(user.getUsername());
    }

    @Override
    public int getItemCount() {
        return userModel.size();
    }
}
