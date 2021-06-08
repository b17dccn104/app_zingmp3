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

import adapter.TatCaAlbumAdapter;
import model.Album;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.APIService;
import service.Dataservice;

public class DanhsachtatcaAlbum_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    TatCaAlbumAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtatca_album);
        init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Album>> callback = dataservice.GetAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> album = (ArrayList<Album>) response.body();
                Adapter = new TatCaAlbumAdapter(DanhsachtatcaAlbum_Activity.this,album);
                recyclerView.setLayoutManager(new GridLayoutManager(DanhsachtatcaAlbum_Activity.this,2));
                recyclerView.setAdapter(Adapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerviewtatcaalbum);
        toolbar = findViewById(R.id.toolbartatcaalbum);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Các Album");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
