package activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.app_zingmp3.R;
import com.google.android.material.tabs.TabLayout;

import adapter.MainViewPagerAdapter;
import fragment.Fragment_TimKiem;
import fragment.Fragment_TrangChu;

public class MainActivity extends AppCompatActivity {
    
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        AnhXa();
        init();
    }

    private void init() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new Fragment_TrangChu(),"Trang chủ");
        mainViewPagerAdapter.addFragment(new Fragment_TimKiem(),"Tìm kiếm");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.icontrangchu);
        tabLayout.getTabAt(1).setIcon(R.drawable.iconsearch);
    }

    private void AnhXa() {
        tabLayout = findViewById(R.id.TabLayout);
        viewPager = findViewById(R.id.ViewPager);
    }
}
