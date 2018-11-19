package com.example.cabla.drinkmachinephoneapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {


    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view_frag = inflater.inflate(R.layout.fragment_status2, container, false);
        TextView mode_var = view_frag.findViewById(R.id.mode_variable);
        TextView status_var = view_frag.findViewById(R.id.status_variable);
        TextView cur_user_var = view_frag.findViewById(R.id.cur_user_variable);

        mode_var.setText("Employee");
        status_var.setText("Idle");
        cur_user_var.setText("Chris");

        return view_frag;
    }

}
