package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_zingmp3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import activity.Danhsachbaihat_Activity;
import model.Playlist;

public class DanhsachplaylistAdapter extends RecyclerView.Adapter<DanhsachplaylistAdapter.ViewHolder> {

    Context context;
    ArrayList<Playlist> arrplaylist;

    public DanhsachplaylistAdapter(Context context, ArrayList<Playlist> arrplaylist) {
        this.context = context;
        this.arrplaylist = arrplaylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_danh_sach_cac_playlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = arrplaylist.get(position);
        Picasso.with(context).load(playlist.getHinhPlaylist()).into(holder.hinhnen);
        holder.tenplaylist.setText(playlist.getTen());
    }

    @Override
    public int getItemCount() {
        return arrplaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView hinhnen;
        TextView tenplaylist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhnen = itemView.findViewById(R.id.danhsachcacplaylist);
            tenplaylist = itemView.findViewById(R.id.tendanhsachcacplaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Danhsachbaihat_Activity.class);
                    intent.putExtra("itemplaylist",arrplaylist.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
