package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_zingmp3.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Playlist;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }
    class ViewHolder{
        TextView tenplaylist;
        ImageView background, playlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_playlist,null);

            viewHolder = new ViewHolder();
            viewHolder.tenplaylist = convertView.findViewById(R.id.tenplaylist);
            viewHolder.playlist = convertView.findViewById(R.id.playlist);
            viewHolder.background = convertView.findViewById(R.id.backgroundplaylist);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Playlist playlist = getItem(position);
        Picasso.with(getContext()).load(playlist.getHinhPlaylist()).into(viewHolder.background);
        Picasso.with(getContext()).load(playlist.getIcon()).into(viewHolder.playlist);
        viewHolder.tenplaylist.setText(playlist.getTen());

        return convertView;
    }
}
