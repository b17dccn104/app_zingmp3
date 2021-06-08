package adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_zingmp3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import activity.PlayNhac_Activity;
import model.Baihat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.APIService;
import service.Dataservice;

public class SearchBaiHatAdapter extends RecyclerView.Adapter<SearchBaiHatAdapter.ViewHolder> {
    Context context;
    ArrayList<Baihat> arrbaihat;

    MediaPlayer mediaPlayer;

    public SearchBaiHatAdapter(Context context, ArrayList<Baihat>  arrbaihat) {
        this.context = context;
        this. arrbaihat =  arrbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_search_bai_hat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat =  arrbaihat.get(position);
        holder.Tenbaihat.setText(baihat.getTenbaihat());
        holder.Casi.setText(baihat.getCasi());
        Picasso.with(context).load(baihat.getHinhbaihat()).into(holder.baihat);
    }

    @Override
    public int getItemCount() {
        return  arrbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Tenbaihat, Casi;
        ImageView baihat, luotthich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tenbaihat = itemView.findViewById(R.id.textviewsearchten);
            Casi = itemView.findViewById(R.id.textviewsearchcasi);
            baihat = itemView.findViewById(R.id.imagesearch);
            luotthich = itemView.findViewById(R.id.luotthich);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent intent = new Intent(context, PlayNhac_Activity.class);
                        intent.putExtra("cakhuc",  arrbaihat.get(getPosition()));
                        context.startActivity(intent);
                    }
            });
            luotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    luotthich.setImageResource(R.drawable.iconloved);
                    Dataservice dataservice = APIService.getService();
                    Call<String> callback = dataservice.UpdateLuotThich("1",  arrbaihat.get(getPosition()).getIdbaihat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if (ketqua.equals("ok")) {
                                Toast.makeText(context, "Da thich", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Loi", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    luotthich.setEnabled(false);
                }
            });
        }
    }
}
