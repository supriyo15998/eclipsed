package in.example.eclipsed.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.example.eclipsed.R;

public class MessageHolder extends RecyclerView.ViewHolder {

    public TextView message;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        this.message = itemView.findViewById(R.id.singleMsg);
    }
}
