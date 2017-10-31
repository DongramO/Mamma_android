package momma_beta.momma_bv.Review;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.FilterReview;
import momma_beta.momma_bv.Model.FilterReviewInfo;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFilteringFragment extends MasterFragment {

    private NavigationActivity mContext;
    CheckBox hit, rec;
    CheckBox worry1,worry2,worry3,worry4,worry5,worry6,worry7,worry8;
    Button compelete;
    String worry="";
    String kind ="";
    int count= 0;
    int Flaghit = 0;
    int Flagrec = 0;
    int Flagcompelte=0;
    ArrayList<CheckBox> boxlist;
    ArrayList<CheckBox> boxkind;
    ArrayList<FilterReviewInfo> reviewlist;
    NetworkService networkService;
    FilterReview.ResultData data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContext = (NavigationActivity) getMasterActivity();
        View view = inflater.inflate(R.layout.activity_review_filtering_fragment, container, false);

        networkService = ApplicationController.getInstance().getNetworkService();
        boxlist = new ArrayList<>();
        boxkind = new ArrayList<>();

        hit = (CheckBox)view.findViewById(R.id.filtering_click_img); boxkind.add(hit);
        rec = (CheckBox)view.findViewById(R.id.filtering_recommand_img); boxkind.add(rec);

        worry1 = (CheckBox)view.findViewById(R.id.f_indigestion_cb); boxlist.add(worry1);
        worry2 = (CheckBox)view.findViewById(R.id.f_atopy_cb);boxlist.add(worry2);
        worry3 = (CheckBox)view.findViewById(R.id.f_constipation_cb);boxlist.add(worry3);
        worry4 = (CheckBox)view.findViewById(R.id.f_allergy_cb);boxlist.add(worry4);
        worry5 = (CheckBox)view.findViewById(R.id.f_diarrhea_cb);boxlist.add(worry5);
        worry6 = (CheckBox)view.findViewById(R.id.f_growth_failure_cb);boxlist.add(worry6);
        worry7 = (CheckBox)view.findViewById(R.id.f_skin_eruption_cb);boxlist.add(worry7);
        worry8 = (CheckBox)view.findViewById(R.id.f_overweight_cb);boxlist.add(worry8);

        compelete = (Button)view.findViewById(R.id.filtering_end_btn);

        FilterListener filterListener = new FilterListener();
        compelete.setOnClickListener(filterListener);
        hit.setOnClickListener(filterListener);
        rec.setOnClickListener(filterListener);

        return view;
    }

    public class FilterListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            for(int i=0; i<boxkind.size(); i++)
            {
                boxkind.get(i).setChecked(false);
            }
            switch(v.getId()) {
                case R.id.filtering_click_img:
                    hit.setChecked(true);
                    kind = "goodnum";
                break;
                case R.id.filtering_recommand_img:
                    rec.setChecked(true);
                    kind = "review_hit";
                break;
                case R.id.filtering_end_btn:
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
                    if(kind.equals(""))
                    {
                        Toast.makeText(mContext, "종류를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        getFilterReviewResult();
                    }
                    break;
            }
        }
    }
    public void getFilterReviewResult()
    {
        Call<FilterReview> productlist = networkService.getFilterReviewResult(worry,count,kind);
        productlist.enqueue(new Callback<FilterReview>()
        {
            @Override
            public void onResponse(Call<FilterReview> call, Response<FilterReview> response)
            {
                if (response.isSuccessful())
                {
                    Log.i("MyTag", "dry milklist");
                    data = response.body().result;
                    reviewlist = data.review_list;
                    mContext.changeFragment(4,reviewlist);
                }
            }
            @Override
            public void onFailure(Call<FilterReview> call, Throwable t)
            {
                Log.i("MyTag", "실패~");
            }
        });
    }

}
