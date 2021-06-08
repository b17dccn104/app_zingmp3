package activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_zingmp3.R;

import java.util.ArrayList;
import java.util.List;

import adapter.TatCachudeAdapter;
import model.ChuDe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.APIService;
import service.Dataservice;

public class Danhsachtatcachude_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    TatCachudeAdapter Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtatcachude);
        init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<ChuDe>> callback = dataservice.GetAllChuDe();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                ArrayList<ChuDe> chude = (ArrayList<ChuDe>) response.body();
                //Log.d("ZZZ", mangchude.get(0).getTenChuDe());
                Adapter = new TatCachudeAdapter(Danhsachtatcachude_Activity.this,chude);
                recyclerView.setLayoutManager(new GridLayoutManager(Danhsachtatcachude_Activity.this,1));
                recyclerView.setAdapter(Adapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Chủ Đề");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
