package in.example.eclipsed.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.example.eclipsed.R;

public class UserHolder extends RecyclerView.ViewHolder {

    public TextView users;
    public TextView usersName;

    public UserHolder(@NonNull View itemView) {
        super(itemView);
        this.users = itemView.findViewById(R.id.singleUser);
        this.usersName = itemView.findViewById(R.id.singleUserUsername);
    }
}
