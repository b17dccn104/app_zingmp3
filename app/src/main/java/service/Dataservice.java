package service;

import java.util.List;

import model.Album;
import model.Baihat;
import model.ChuDe;
import model.Playlist;
import model.Quangcao;
import model.TheLoai;
import model.Theloaitrongngay;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Dataservice {
    @GET("Server/songbanner.php")

    Call<List<Quangcao>> GetDataBanner();

    @GET("Server/playlistforcurrentday.php")
    Call<List<Playlist>> GetPlaylistCurrentDay();

    @GET("Server/chudevatheloaitrongngay.php")
    Call<Theloaitrongngay> GetCategoryMusic();

    @GET("Server/albumhot.php")
    Call<List<Album>> GetAlbumHot();

    @GET("Server/baihatduocthich.php")
    Call<List<Baihat>> GetBaiHatHot();

    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihattheoquangcao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihattheoplaylist(@Field("idplaylist") String idplaylist);

    @GET("Server/danhsachplaylist.php")
    Call<List<Playlist>> GetDanhsachcacPlaylist();

    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<Baihat>> GetDanhSachBaiHatTheoTheLoai(@Field("idtheloai") String idtheloai);

    @GET("Server/tatcachude.php")
    Call<List<ChuDe>> GetAllChuDe();

    @FormUrlEncoded
    @POST("Server/theloaitheochude.php")
    Call<List<TheLoai>> GetTheloaitheochude(@Field("idchude") String idchude);
    @GET("Server/tatcaalbum.php")
    Call<List<Album>> GetAlbum();

    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihattheoalbum(@Field("idalbum") String idalbum);
    @FormUrlEncoded
    @POST("Server/updateluotthich.php")
    Call<String> UpdateLuotThich(@Field("luotthich") String luotthich,
                                 @Field("idbaihat") String idbaihat);
    @FormUrlEncoded
    @POST("Server/searchbaihat.php")
    Call<List<Baihat>> GetSearchBaihat(@Field("tukhoa") String tukhoa);
}
