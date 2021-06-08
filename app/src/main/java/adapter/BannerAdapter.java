package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.app_zingmp3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import activity.Danhsachbaihat_Activity;
import model.Quangcao;

public class BannerAdapter  extends PagerAdapter {
    private Context context;
    private ArrayList<Quangcao> arrbanner;

    public BannerAdapter(Context context, ArrayList<Quangcao> arrayListbanner) {
        this.context = context;
        this.arrbanner = arrayListbanner;
    }

    @Override
    public int getCount() {
        return arrbanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_banner, null);

        ImageView imgbackground = view.findViewById(R.id.imageviewbackground);
        ImageView imgsong = view.findViewById(R.id.imagebanner);
        TextView txttitlesong = view.findViewById(R.id.textviewtitle);
        TextView txtnoidung = view.findViewById(R.id.noidung);

        Picasso.with(context).load(arrbanner.get(position).getHinhanh()).into(imgbackground);
        Picasso.with(context).load(arrbanner.get(position).getHinhBaiHat()).into(imgsong);

        txttitlesong.setText(arrbanner.get(position).getTenBaiHat());
        txtnoidung.setText(arrbanner.get(position).getNoidung());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Danhsachbaihat_Activity.class);
                intent.putExtra("banner", arrbanner.get(position));
                context.startActivity(intent);
            }
        });

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
