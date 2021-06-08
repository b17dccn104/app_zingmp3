package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_zingmp3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import activity.Danhsachtheloaitheochude_Activity;
import model.ChuDe;

public class TatCachudeAdapter extends RecyclerView.Adapter<TatCachudeAdapter.ViewHolder> {

    Context context;
    ArrayList<ChuDe> arrchude;

    public TatCachudeAdapter(Context context, ArrayList<ChuDe> arrchude) {
        this.context = context;
        this.arrchude = arrchude;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_cac_chu_de,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChuDe chuDe = arrchude.get(position);
        Picasso.with(context).load(chuDe.getHinhChuDe()).into(holder.chude);
    }

    @Override
    public int getItemCount() {
        return arrchude.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView chude;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chude = itemView.findViewById(R.id.dongcacchude);
            chude.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Danhsachtheloaitheochude_Activity.class);
                    intent.putExtra("chude", arrchude.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
