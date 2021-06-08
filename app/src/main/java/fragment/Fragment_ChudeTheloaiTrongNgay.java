package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.app_zingmp3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import activity.Danhsachbaihat_Activity;
import activity.Danhsachtatcachude_Activity;
import activity.Danhsachtheloaitheochude_Activity;
import model.ChuDe;
import model.TheLoai;
import model.Theloaitrongngay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.APIService;
import service.Dataservice;

public class Fragment_ChudeTheloaiTrongNgay extends Fragment {
    View view;
    HorizontalScrollView horizontalScrollView;
    TextView xemthemchudetheloai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chudetheloaitrongngay, container, false);

        horizontalScrollView = view.findViewById(R.id.horizontalScrollview);
        xemthemchudetheloai = view.findViewById(R.id.textviewxemthem);
        xemthemchudetheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Danhsachtatcachude_Activity.class);
                startActivity(intent);
            }
        });

        GetData();
        return view;
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<Theloaitrongngay> callback = dataservice.GetCategoryMusic();
        callback.enqueue(new Callback<Theloaitrongngay>() {
            @Override
            public void onResponse(Call<Theloaitrongngay> call, Response<Theloaitrongngay> response) {
                Theloaitrongngay theloai = response.body();

                final ArrayList<ChuDe> chuDeArr = new ArrayList<>();
                chuDeArr.addAll(theloai.getChuDe());

                final ArrayList<TheLoai> theLoaiArr = new ArrayList<>();
                theLoaiArr.addAll(theloai.getTheLoai());

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(580,250);
                layout.setMargins(10,20,10,30); //set kich thuoc cho cardview khi dua vao linearlayout
                for (int i = 0; i < (chuDeArr.size()) ; i++) {// dua hinh chu de vao cardview
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(chuDeArr.get(i).getHinhChuDe() != null){
                        Picasso.with(getActivity()).load(chuDeArr.get(i).getHinhChuDe()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), Danhsachtheloaitheochude_Activity.class);
                            intent.putExtra("chude", chuDeArr.get(finalI));
                            startActivity(intent);
                        }
                    });
                }

                for (int j = 0; j < (chuDeArr.size()) ; j++) {//dua hinh the loai vao cardview
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(theLoaiArr.get(j).getHinhTheLoai() != null){
                        Picasso.with(getActivity()).load(theLoaiArr.get(j).getHinhTheLoai()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);

                    final int finalJ = j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), Danhsachbaihat_Activity.class);
                            intent.putExtra("idtheloai", theLoaiArr.get(finalJ));
                            startActivity(intent);
                        }
                    });
                }
                horizontalScrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(Call<Theloaitrongngay> call, Throwable t) {

            }
        });
    }
}
