package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_zingmp3.R;

import activity.PlayNhac_Activity;
import adapter.PlayNhacAdapter;

public class Fragment_dsbaihat extends Fragment {
    View view;
    RecyclerView recyclerView;
    PlayNhacAdapter Adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dsbaihat,container,false);
        recyclerView = view.findViewById(R.id.recyclerviewPlaybaihat);
        if(PlayNhac_Activity.mangbaihat.size()>0){
            Adapter = new PlayNhacAdapter(getActivity(), PlayNhac_Activity.mangbaihat);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(Adapter);
        }
        return view;
    }
}
