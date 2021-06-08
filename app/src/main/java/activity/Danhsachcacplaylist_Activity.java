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

import adapter.DanhsachplaylistAdapter;
import model.Playlist;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.APIService;
import service.Dataservice;

public class Danhsachcacplaylist_Activity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    DanhsachplaylistAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachcacplaylist);
        AnhXa();
        init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Playlist>> callback = dataservice.GetDanhsachcacPlaylist();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> mangplaylist = (ArrayList<Playlist>) response.body();
                //Log.d("KKK",mangplaylist.get(0).getTen());
                Adapter = new DanhsachplaylistAdapter(Danhsachcacplaylist_Activity.this,mangplaylist);
                recyclerView.setLayoutManager(new GridLayoutManager(Danhsachcacplaylist_Activity.this,2));
                recyclerView.setAdapter(Adapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Playlists");
        toolbar.setTitleTextColor(getResources().getColor(R.color.mautim));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarplaylist);
        recyclerView = findViewById(R.id.recyclerview);
    }
}
