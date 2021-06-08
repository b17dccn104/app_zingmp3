package activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.app_zingmp3.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import adapter.ViewPagerPlaylist;
import fragment.Fragment_dianhac;
import fragment.Fragment_dsbaihat;
import model.Baihat;

public class PlayNhac_Activity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtTime, txtTotalTime;
    SeekBar sbtime;
    ImageButton play, repeat, next, pre, random;
    ViewPager viewPager;

    public static ArrayList<Baihat> mangbaihat = new ArrayList<>();
    public static ViewPagerPlaylist adapternhac;
    Fragment_dianhac fragment_dia_nhac;
    Fragment_dsbaihat fragment_play_danh_sach_cac_bai_hat;

    MediaPlayer mediaPlayer;

    int position = 0;
    boolean btrepeat = false;
    boolean checkrandom = false;
    boolean btnext = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playnhac);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();//kiem tra tin hieu mang
        StrictMode.setThreadPolicy(policy);

        GetDataFromIntent();
        init();
        eventClick();
    }

    private void eventClick() {//gan hinh bai hat len dia nhac va thay doi trang thai nut play
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapternhac.getItem(1) != null) {
                    if (mangbaihat.size() > 0) {
                        fragment_dia_nhac.Playnhac(mangbaihat.get(0).getHinhbaihat());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 300);//neu load bai hat lau qua se doi 3s roi lặp lại
                    }
                }
            }
        }, 500);
       play.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {//click khi dang nghe
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.iconplay);
                    if (fragment_dia_nhac.objectAnimator != null) {
                        fragment_dia_nhac.objectAnimator.pause();
                    }
                } else {
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.iconpause);
                    if (fragment_dia_nhac.objectAnimator != null) {
                        fragment_dia_nhac.objectAnimator.resume();
                    }
                }
            }
        });

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btrepeat == false) {
                    if (checkrandom == true) {
                        checkrandom = false;
                        repeat.setImageResource(R.drawable.iconsyned);
                        random.setImageResource(R.drawable.iconsuffle);
                    }
                    repeat.setImageResource(R.drawable.iconsyned);
                    btrepeat = true;
                } else {
                    repeat.setImageResource(R.drawable.iconrepeat);
                    btrepeat = false;
                }
            }
        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkrandom == false) {
                    if (btrepeat == true) {
                        btrepeat = false;
                        random.setImageResource(R.drawable.iconshuffled);
                        repeat.setImageResource(R.drawable.iconrepeat);
                    }
                    random.setImageResource(R.drawable.iconshuffled);
                    checkrandom = true;
                } else {
                    random.setImageResource(R.drawable.iconsuffle);
                    checkrandom = false;
                }
            }
        });

        sbtime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());//keo seekbar den dau thi phat nhac o doan day
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {//neu co bai dang hat thi cho stop
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < mangbaihat.size()) {//tranh truoc hop next vuot qua mang
                        play.setImageResource(R.drawable.iconpause);
                        position++;
                        if (btrepeat == true) {
                            if (position == 0) {
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > mangbaihat.size() - 1) {
                            position = 0;
                        }
                        new PlayMp3().execute(mangbaihat.get(position).getLinkbaihat());
                        fragment_dia_nhac.Playnhac(mangbaihat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
                pre.setClickable(false);
                next.setClickable(false);
                Handler handler1 = new Handler();//click next hoat dong sau 5s
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pre.setClickable(true);
                        next.setClickable(true);
                    }
                }, 5000);
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < mangbaihat.size()) {
                        play.setImageResource(R.drawable.iconpause);
                        position--;
                        if (position < 0) {
                            position = mangbaihat.size() - 1;
                        }
                        if (btrepeat == true) {
                            position += 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        new PlayMp3().execute(mangbaihat.get(position).getLinkbaihat());
                        fragment_dia_nhac.Playnhac(mangbaihat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
                pre.setClickable(false);
                next.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pre.setClickable(true);
                        next.setClickable(true);
                    }
                }, 500);
            }
        });
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangbaihat.clear(); //tranh truong hop bai hat de len nhau
        if (intent != null) {
            if (intent.hasExtra("cakhuc")) {
                Baihat baihat = intent.getParcelableExtra("cakhuc");//nhan du lieu dang obj
                mangbaihat.add(baihat);
            }
        }
        if (intent.hasExtra("cacbaihat")) {
            ArrayList<Baihat> arbaihat = intent.getParcelableArrayListExtra("cacbaihat");//nhan du lieu dang mang obj
            mangbaihat = arbaihat;
        }
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        txtTime = findViewById(R.id.textviewtime);
        txtTotalTime = findViewById(R.id.textviewtotaltime);
        sbtime = findViewById(R.id.seekbar);
        play = findViewById(R.id.buttonplay);
        pre = findViewById(R.id.buttonpreview);
        next = findViewById(R.id.buttonnext);
        repeat = findViewById(R.id.buttonrepeat);
        random = findViewById(R.id.buttonsuffle);
        viewPager = findViewById(R.id.viewpager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                mangbaihat.clear();  //k de bai hat add chong len nhau
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        adapternhac = new ViewPagerPlaylist(getSupportFragmentManager());
        fragment_dia_nhac = new Fragment_dianhac();
        fragment_play_danh_sach_cac_bai_hat = new Fragment_dsbaihat();
        adapternhac.AddFragment(fragment_play_danh_sach_cac_bai_hat);
        adapternhac.AddFragment(fragment_dia_nhac); //phai de sau addFragment play danh sach bai hat
        viewPager.setAdapter(adapternhac);

        fragment_dia_nhac = (Fragment_dianhac) adapternhac.getItem(1);

        if (mangbaihat.size() > 0) {  //neu mang > 0 thi play ca khuc dau tien
            getSupportActionBar().setTitle(mangbaihat.get(0).getTenbaihat());
            new PlayMp3().execute(mangbaihat.get(0).getLinkbaihat());
            play.setImageResource(R.drawable.iconpause);
        }
    }

    class PlayMp3 extends AsyncTask<String, Void, String> { // thuc hien cac buoc de play duoc bai hat
        @Override
        protected String doInBackground(String... strings) {//tra ve duong link bai hat cho onPostExecute thuc thi
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//play nhac dang online
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();//tranh loi khi 1 bai hat cap nhat qua lau
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(baihat);//khoi tao du lieu tu duong link bai hat
                mediaPlayer.prepare();//phat nhac
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            UpdateTime();
        }
    }

    private void TimeSong() {//cap nhat thoi gian bai hat
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");//format thoi gian
        txtTotalTime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));//lay tong time cua bai hat
        sbtime.setMax(mediaPlayer.getDuration());//keo thoi gian bai hat, cap nhat len seekbar
    }

    private void UpdateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    sbtime.setProgress(mediaPlayer.getCurrentPosition());//cap nhat thoi gian cho seekbar
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");//format chuoi thoi gian
                    txtTime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 300); //cap nhat chay lien tuc
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {//khi chay het bai hat thi chuyen bai
                            btnext = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }, 500);
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (btnext == true) {
                    if (position < mangbaihat.size()) {
                        play.setImageResource(R.drawable.iconpause);
                        position++;
                        if (btrepeat == true) {
                            if (position == 0) {
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > mangbaihat.size() - 1) {
                            position = 0;
                        }
                        new PlayMp3().execute(mangbaihat.get(position).getLinkbaihat());
                        fragment_dia_nhac.Playnhac(mangbaihat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                    }

                    pre.setClickable(false);
                    next.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pre.setClickable(true);
                            next.setClickable(true);
                        }
                    }, 5000);
                    btnext = false;
                    handler1.removeCallbacks(this);
                } else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }
}
