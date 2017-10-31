package momma_beta.momma_bv.Intro;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import momma_beta.momma_bv.R;

public class intro2_Fragment extends Fragment {

    public Activity activity;

    public intro2_Fragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro2_fragment, null);


        return view;
    }
    }




