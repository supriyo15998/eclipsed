package in.example.eclipsed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.example.eclipsed.R;
import in.example.eclipsed.models.User;
import in.example.eclipsed.viewholder.UserHolder;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    Context context;
    List<User> userModel;
    UserListener userListener;
    public UserAdapter(Context context, List<User> userModel, UserListener userListener) {
        this.context = context;
        this.userModel = userModel;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user,null);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        final User user = userModel.get(position);
        holder.users.setText(user.getName());
        holder.usersName.setText(user.getUsername());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userListener.onSearchedUserClicked(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModel.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView users;
        public TextView usersName;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            this.users = itemView.findViewById(R.id.singleUser);
            this.usersName = itemView.findViewById(R.id.singleUserUsername);
            this.view = itemView;
        }
    }
}
