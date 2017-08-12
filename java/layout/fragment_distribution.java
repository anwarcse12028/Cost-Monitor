package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdul.wadud.costmonitor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_distribution extends Fragment {


    public fragment_distribution() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_distribution, container, false);
    }

}
