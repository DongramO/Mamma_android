package momma_beta.momma_bv.MyPage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.MypageModifyResult;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPageEditFragment extends MasterFragment
{

	private NavigationActivity mContext;
	private RelativeLayout mypage_brand_layout, mypage_product_layout, mypage_review_layout;
	private Button myreview_btn;
	private EditText p_name, b_name, nickname, birth;
	private String worry="";
	private ArrayList<CheckBox> boxlist;
    private CheckBox worry1,worry2,worry3,worry4,worry5,worry6,worry7,worry8;
    private Button sign_end_btn;
    private int count;
    private String CurrentNickname;
    NetworkService networkService;
    public SharedPreferences auto;
    private SharedPreferences.Editor autoLogin;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = (NavigationActivity) getMasterActivity();
        networkService = ApplicationController.getInstance().getNetworkService();

        boxlist = new ArrayList<CheckBox>();
        auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        CurrentNickname = auto.getString("nickname", null);

//        CurrentNickname = "kkkk";
        Log.i("Mytag", CurrentNickname+"current");

        View view = inflater.inflate(R.layout.activity_mypage_edit, container, false);

        sign_end_btn = (Button)view.findViewById(R.id.sign_end_btn);
		worry1 = (CheckBox)view.findViewById(R.id.indigestion_cb); boxlist.add(worry1);
		worry2 = (CheckBox)view.findViewById(R.id.atopy_cb);boxlist.add(worry2);
		worry3 = (CheckBox)view.findViewById(R.id.constipation_cb);boxlist.add(worry3);
		worry4 = (CheckBox)view.findViewById(R.id.allergy_cb);boxlist.add(worry4);
		worry5 = (CheckBox)view.findViewById(R.id.diarrhea_cb);boxlist.add(worry5);
		worry6 = (CheckBox)view.findViewById(R.id.growth_failure_cb);boxlist.add(worry6);
		worry7 = (CheckBox)view.findViewById(R.id.skin_eruption_cb);boxlist.add(worry7);
		worry8 = (CheckBox)view.findViewById(R.id.overweight_cb);boxlist.add(worry8);

        p_name = (EditText)view.findViewById(R.id.mypage_edit_pname);
        b_name = (EditText)view.findViewById(R.id.mypage_edit_bname);
        birth = (EditText)view.findViewById(R.id.mypage_edit_bbirth);

        ClickListener clickListener = new ClickListener();
        sign_end_btn.setOnClickListener(clickListener);

		return view;
	}

    public class ClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.sign_end_btn:
                {
                    for(int i =0; i<8;i++)
                    {
                        if(boxlist.get(i).isChecked())
                        {
                            count++;
                            switch(i)
                            {
                                case 0: worry +="소화불량,";
                                    break;
                                case 1: worry +="아토피,";
                                    break;
                                case 2: worry +="변비,";
                                    break;
                                case 3: worry +="알레르기,";
                                    break;
                                case 4: worry +="설사,";
                                    break;
                                case 5: worry +="성장부진,";
                                    break;
                                case 6: worry +="피부발진,";
                                    break;
                                case 7: worry +="과다체중,";
                                    break;
                            }
                        }
                    }
                    int worrylength = worry.length();
                    worry = worry.substring(0,worrylength-1);
                    ModifyInfo();
                    mContext.changeFragment(2,null);
                }
            }
        }
    }


    public void ModifyInfo()
    {
        final MypageModifyResult modify = new MypageModifyResult();
        modify.p_name = p_name.getText().toString();
        modify.b_name = b_name.getText().toString();
        modify.worry = worry;
        modify.birth = birth.getText().toString();


        Call<MypageModifyResult> modilist = networkService.getModifyResult(CurrentNickname,modify);
        modilist.enqueue(new Callback<MypageModifyResult>()
        {
            @Override
            public void onResponse(Call<MypageModifyResult> call, Response<MypageModifyResult> response)
            {
                if (response.isSuccessful())
                {
                    Log.i("MyTag", "Modify info success");
                }
            }
            @Override
            public void onFailure(Call<MypageModifyResult> call, Throwable t)
            {
                Log.i("MyTag", "Modify info Fail~");
            }
        });
    }
}
