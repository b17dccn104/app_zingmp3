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
import model.TheLoai;

public class TheLoaiTheoChudeAdapter extends RecyclerView.Adapter<TheLoaiTheoChudeAdapter.ViewHolder> {
    Context context;
    ArrayList<TheLoai> arrtheLoai;

    public TheLoaiTheoChudeAdapter(Context context, ArrayList<TheLoai> arrtheLoai) {
        this.context = context;
        this.arrtheLoai = arrtheLoai;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_the_loai_theo_chu_de,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheLoai theLoai = arrtheLoai.get(position);
        Picasso.with(context).load(theLoai.getHinhTheLoai()).into(holder.hinhnen);
        holder.tentheloai.setText(theLoai.getTenTheLoai());
    }

    @Override
    public int getItemCount() {
        return arrtheLoai.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView hinhnen;
        TextView tentheloai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhnen = itemView.findViewById(R.id.theloaitheochude);
            tentheloai = itemView.findViewById(R.id.tvtheloaitheochude);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Danhsachbaihat_Activity.class);
                    intent.putExtra("idtheloai",arrtheLoai.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
