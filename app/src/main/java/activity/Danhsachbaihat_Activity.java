package activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.app_zingmp3.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapter.DanhsachbaihatAdapter;
import model.Album;
import model.Baihat;
import model.Playlist;
import model.Quangcao;
import model.TheLoai;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.APIService;
import service.Dataservice;

public class Danhsachbaihat_Activity extends AppCompatActivity {
    CoordinatorLayout coordinator;
    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;  //de support appcompact
    RecyclerView recyclerView;
    FloatingActionButton ActionButton;

    Quangcao quangcao;
    ImageView imgcakhuc;

    ArrayList<Baihat> baihat;
    DanhsachbaihatAdapter Adapter;

    Playlist playlist;
    TheLoai theLoai;
    Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachbaihat);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        DataIntent();
        anhxa();
        init();
        //Kiem tra quang cao co ton tai khong thi moi gui du lieu len
        if(quangcao != null && !quangcao.getTenBaiHat().equals("")){
            setValueInView(quangcao.getTenBaiHat(), quangcao.getHinhBaiHat());  //gan ten bai hat len toolbar
            GetDataQuangcao(quangcao.getIdQuangCao());
        }
        if(playlist != null && !playlist.getTen().equals("")){
            setValueInView(playlist.getTen(), playlist.getHinhPlaylist());
            GetDataPlaylist(playlist.getIdPlaylist());
        }
        if (theLoai != null && !theLoai.getTenTheLoai().equals("")){
            setValueInView(theLoai.getTenTheLoai(),theLoai.getHinhTheLoai());
            GetDataTheLoai(theLoai.getIdTheLoai());
        }
        if(album != null && !album.getTenAlbum().equals("")){
            setValueInView(album.getTenAlbum(),album.getHinhanhAlbum());
            GetDataAlbum(album.getIdAlbum());
        }
    }

    private void GetDataAlbum(String idalbum) {
        Dataservice dataservice = APIService.getService();
        Call<List<Baihat>> callback = dataservice.GetDanhsachbaihattheoalbum(idalbum);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
               baihat = (ArrayList<Baihat>) response.body();
               Adapter = new DanhsachbaihatAdapter(Danhsachbaihat_Activity.this, baihat);
                recyclerView.setLayoutManager(new LinearLayoutManager(Danhsachbaihat_Activity.this));
                recyclerView.setAdapter(Adapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }

    private void GetDataTheLoai(String idtheloai){
        Dataservice dataservice = APIService.getService();
        Call<List<Baihat>> callback = dataservice.GetDanhSachBaiHatTheoTheLoai(idtheloai);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
              baihat = (ArrayList<Baihat>) response.body();
               Adapter = new DanhsachbaihatAdapter(Danhsachbaihat_Activity.this, baihat);
                recyclerView.setLayoutManager(new LinearLayoutManager(Danhsachbaihat_Activity.this));
                recyclerView.setAdapter(Adapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }

    private void GetDataPlaylist(String idplaylist) {
        Dataservice dataservice = APIService.getService();
        Call<List<Baihat>> callback = dataservice.GetDanhsachbaihattheoplaylist(idplaylist);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                baihat = (ArrayList<Baihat>) response.body();
               Adapter = new DanhsachbaihatAdapter(Danhsachbaihat_Activity.this,baihat);
                recyclerView.setLayoutManager(new LinearLayoutManager(Danhsachbaihat_Activity.this));
                recyclerView.setAdapter(Adapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }

    private void setValueInView(String ten, String hinh) {
        collapsingToolbar.setTitle(ten);
        try {
            URL url = new URL(hinh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            //convert tu url ve bitmap de gan duoc du lieu len collapsingtoolbar
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
            collapsingToolbar.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(hinh).into(imgcakhuc);
    }

    private void GetDataQuangcao(String idquangcao){
        final Dataservice dataservice = APIService.getService();
        Call<List<Baihat>> callback = dataservice.GetDanhsachbaihattheoquangcao(idquangcao);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
              baihat = (ArrayList<Baihat>) response.body();
              //Log.d("HHH", mangbaihat.get(0).getTenbaihat());
                Adapter = new DanhsachbaihatAdapter(Danhsachbaihat_Activity.this,baihat);
                recyclerView.setLayoutManager(new LinearLayoutManager(Danhsachbaihat_Activity.this));
                recyclerView.setAdapter(Adapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);  //vi bo actionbar di de thay the toolbar nen phai set lai chuc nang
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//tao mui ten quay ve trang truoc
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);//khi keo len xuong thi settext cho du lieu cua toolbar
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        ActionButton.setEnabled(false);
    }

    private void anhxa() {
        coordinator = findViewById(R.id.coordinator);
        collapsingToolbar = findViewById(R.id.toolbar);
        toolbar = findViewById(R.id.toolbardanhsachbh);
        recyclerView = findViewById(R.id.recyclerview);
        ActionButton = findViewById(R.id.actionbutton);
        imgcakhuc = findViewById(R.id.imagecakhuc);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("banner")){
                quangcao = (Quangcao) intent.getSerializableExtra("banner");
                //Toast.makeText(this, quangcao.getTenBaiHat(), Toast.LENGTH_SHORT).show();
            }
            if(intent.hasExtra("itemplaylist")){
                playlist = (Playlist) intent.getSerializableExtra("itemplaylist");
            }
            if(intent.hasExtra("idtheloai")){
                theLoai = (TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if(intent.hasExtra("album")){
                album = (Album) intent.getSerializableExtra("album");
            }
        }
    }

    private void eventClick(){
        ActionButton.setEnabled(true);
        ActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Danhsachbaihat_Activity.this, PlayNhac_Activity.class);
                intent.putExtra("cacbaihat", baihat);
                startActivity(intent);
            }
        });
    }
}
