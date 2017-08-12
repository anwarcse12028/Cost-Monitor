package layout;


import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.abdul.wadud.costmonitor.R;
import com.abdul.wadud.costmonitor.Tab1;
import com.abdul.wadud.costmonitor.Tab2;

/**
 * A simple {@link Fragment} subclass.
 */
public class add_expense extends Fragment {


    public add_expense() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        TabHost TabHostWindow = (TabHost)view.findViewById(R.id.tabhost_add);
        LocalActivityManager localActivityManager = new LocalActivityManager(getActivity(), false);
        localActivityManager.dispatchCreate(savedInstanceState);
        TabHostWindow.setup(localActivityManager);

        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("First Tab");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Second Tab");

        //Setting up tab 1 name.
        TabMenu1.setIndicator("Category");
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(getActivity(),Tab1.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("Expense");
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(getActivity(),Tab2.class));


        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        return view;
    }

}
