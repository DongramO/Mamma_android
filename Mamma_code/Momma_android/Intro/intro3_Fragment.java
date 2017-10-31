package momma_beta.momma_bv.Intro;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import momma_beta.momma_bv.R;
import momma_beta.momma_bv.navigation_main.NavigationActivity;

public class intro3_Fragment extends Fragment {

    private Button intro_end_btn;
    private TextView intro_text;
    private String strColor = "#FFFFFF";

    public Activity activity;

    public intro3_Fragment(Activity activity) {
        this.activity = activity;
    }
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.intro3_fragment, container, false);
            intro_end_btn = (Button)layout.findViewById(R.id.intro_end_btn);


            intro_end_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intro_end_btn.setBackgroundResource(R.drawable.intro_mint_box);
                    intro_end_btn.setTextColor(Color.parseColor(strColor));
                    Intent main = new Intent(getActivity(), NavigationActivity.class);
                    main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(main);
                }
            });
            return layout;
        }
    }




