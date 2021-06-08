package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.app_zingmp3.R;

import java.util.ArrayList;
import java.util.List;

import adapter.BannerAdapter;
import me.relex.circleindicator.CircleIndicator;
import model.Quangcao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.APIService;
import service.Dataservice;

public class Fragment_Banner extends Fragment {

    View view;

    ViewPager viewPager;
    CircleIndicator circleIndicator;

    BannerAdapter Adapter;

    //2 thu vien nay dung de tu dong next quang cao sau 1 khoang thoi gian
    Runnable runnable;
    Handler handler;
    int Item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        AnhXa();
        GetData();
        return view;
    }

    private void AnhXa() {
        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.indicatordefault);
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Quangcao>> callback = dataservice.GetDataBanner();
        callback.enqueue(new Callback<List<Quangcao>>() {
            @Override
            public void onResponse(Call<List<Quangcao>> call, Response<List<Quangcao>> response) {//lang nghe cho co ket qua tra ve
                ArrayList<Quangcao> banners = (ArrayList<Quangcao>) response.body();//nhan du lieu tu response
                if(banners != null) {

                    Adapter = new BannerAdapter(getActivity(), banners);
                    viewPager.setAdapter(Adapter);
                    circleIndicator.setViewPager(viewPager);

                    handler = new Handler();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            Item = viewPager.getCurrentItem();
                            Item++;
                            if (Item >= viewPager.getAdapter().getCount()) {
                                Item = 0;
                            }
                            viewPager.setCurrentItem(Item, true);
                            handler.postDelayed(runnable, 4000);
                        }
                    };
                    handler.postDelayed(runnable, 4000);
                }
            }

            @Override
            public void onFailure(Call<List<Quangcao>> call, Throwable t) {

            }
        });
    }
}
