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
import model.Album;

public class TatCaAlbumAdapter extends RecyclerView.Adapter<TatCaAlbumAdapter.ViewHolder> {

    Context context;
    ArrayList<Album> arralbum;

    public TatCaAlbumAdapter(Context context, ArrayList<Album> arralbum) {
        this.context = context;
        this.arralbum = arralbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_all_album,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = arralbum.get(position);
        Picasso.with(context).load(album.getHinhanhAlbum()).into(holder.allalbum);
        holder.tenallalbum.setText(album.getTenAlbum());

    }

    @Override
    public int getItemCount() {
        return arralbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView allalbum;
        TextView tenallalbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allalbum = itemView.findViewById(R.id.imageallalbum);
            tenallalbum = itemView.findViewById(R.id.textviewten);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Danhsachbaihat_Activity.class);
                    intent.putExtra("album", arralbum.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
