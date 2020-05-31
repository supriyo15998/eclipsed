package in.example.eclipsed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.example.eclipsed.R;
import in.example.eclipsed.models.Message;
import in.example.eclipsed.viewholder.MessageHolder;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {
    Context context;
    List<Message> messageModel;

    public MessageAdapter(Context context, List<Message> messageModel) {
        this.context = context;
        this.messageModel = messageModel;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg,null);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Message message = messageModel.get(position);
        holder.message.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageModel.size();
    }
}
