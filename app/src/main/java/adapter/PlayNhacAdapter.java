package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_zingmp3.R;

import java.util.ArrayList;

import model.Baihat;

public class PlayNhacAdapter extends RecyclerView.Adapter<PlayNhacAdapter.ViewHolder> {
    Context context;
    ArrayList<Baihat> arrbaihat;

    public PlayNhacAdapter(Context context, ArrayList<Baihat> arrbaihat) {
        this.context = context;
        this.arrbaihat = arrbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_play_bai_hat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat = arrbaihat.get(position);
        holder.tencasi.setText(baihat.getCasi());
        holder.index.setText(position + 1 + "");
        holder.tenbaihat.setText(baihat.getTenbaihat());
    }

    @Override
    public int getItemCount() {
        return arrbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView index, tenbaihat, tencasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tencasi = itemView.findViewById(R.id.playnhactencasi);
            tenbaihat = itemView.findViewById(R.id.playnhactenbaihat);
            index = itemView.findViewById(R.id.playnhacindex);
        }
    }
}
