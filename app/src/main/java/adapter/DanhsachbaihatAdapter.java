package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_zingmp3.R;

import java.util.ArrayList;

import activity.PlayNhac_Activity;
import model.Baihat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.APIService;
import service.Dataservice;

public class DanhsachbaihatAdapter extends RecyclerView.Adapter<DanhsachbaihatAdapter.ViewHolder> {
    Context context;
    ArrayList<Baihat> arrbaihat;

    public DanhsachbaihatAdapter(Context context, ArrayList<Baihat> arrbaihat) {
        this.context = context;
        this.arrbaihat = arrbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_danh_sach_bai_hat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat = arrbaihat.get(position);
        holder.tenbaihat.setText(baihat.getTenbaihat());
        holder.casi.setText(baihat.getCasi());
        holder.index.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        return arrbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView index, tenbaihat, casi;
        ImageView luotthich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            casi = itemView.findViewById(R.id.tencasi);
            index = itemView.findViewById(R.id.danhsachindex);
            tenbaihat = itemView.findViewById(R.id.tenbaihat);
            luotthich = itemView.findViewById(R.id.luotthichdanhsachbaihat);
            luotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    luotthich.setImageResource(R.drawable.iconloved);
                    Dataservice dataservice = APIService.getService();
                    Call<String> callback = dataservice.UpdateLuotThich("1",arrbaihat.get(getPosition()).getIdbaihat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if(ketqua.equals("ok")){
                                Toast.makeText(context, "Đã thích", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Lỗi!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    luotthich.setEnabled(false);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhac_Activity.class);
                    intent.putExtra("cakhuc", arrbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }

    }
}
