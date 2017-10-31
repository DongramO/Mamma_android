package momma_beta.momma_bv.MyPage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import momma_beta.momma_bv.Model.MyBookMark;
import momma_beta.momma_bv.MyPage.Mypage_Adapter.Adapter_myBrand;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class F_BrandFragment extends MasterFragment {

    private NavigationActivity mContext;
    private RecyclerView brandgridview;
    private GridLayoutManager gridLayoutManager;
    Adapter_myBrand adapter1;
    NetworkService networkService;
    SharedPreferences auto;

    String nickname;
    String kind ="company";
    MyBookMark.ResultData data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        networkService = ApplicationController.getInstance().getNetworkService();

        mContext = (NavigationActivity) getMasterActivity();
        View view = inflater.inflate(R.layout.favorite_brand_header, container, false);

        auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        nickname = auto.getString("nickname",null);

        brandgridview = (RecyclerView)view.findViewById(R.id.mypage_brand_rv);
        brandgridview.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                if(position == 0) return 1;
                return 1;
            }
        });


        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        brandgridview.setLayoutManager(gridLayoutManager);

        getMyBookmarkResult();
        return view;
    }

    public void getMyBookmarkResult()
    {
        Call<MyBookMark> mybookmarkresult = networkService.getMyBookmarkResult(nickname, kind);
        mybookmarkresult.enqueue(new Callback<MyBookMark>()
        {
            @Override
            public void onResponse(Call<MyBookMark> call, Response<MyBookMark> response)
            {
                if (response.isSuccessful())
                {
                    Log.i("MyTag", "my bookmark list");
                    data = response.body().result;
                    adapter1 = new Adapter_myBrand(data.bookmark,mContext);
                    brandgridview.setAdapter(adapter1);

                }
            }
            @Override
            public void onFailure(Call<MyBookMark> call, Throwable t)
            {
                Log.i("MyTag", "실패~");
            }
        });
    }
}
