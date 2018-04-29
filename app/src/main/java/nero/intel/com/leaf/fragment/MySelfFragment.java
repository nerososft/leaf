package nero.intel.com.leaf.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nero.intel.com.leaf.R;

/**
 * Created by ny on 2018/3/6.
 */

public class MySelfFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_fragment, container, false);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
