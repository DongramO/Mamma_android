package momma_beta.momma_bv.Brand;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import momma_beta.momma_bv.Brand.BrandAdapter.BrandListAdapter;
import momma_beta.momma_bv.Model.BrandName;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import momma_beta.momma_bv.product.Product_model.addbookmark;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BrandFragment extends MasterFragment {

    RecyclerView recyclerView1;
    LinearLayoutManager linearLayoutManager1;
    NetworkService networkService;
    BrandListAdapter adapter1;
    String  manuname;
    BrandName.ResultData data;
    ImageView img;
    NavigationActivity mContext;
    RelativeLayout bookmark;
    SharedPreferences auto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContext = (NavigationActivity) getMasterActivity();
        View view = inflater.inflate(R.layout.brand_fragment, container, false);
        auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        bookmark = (RelativeLayout)view.findViewById(R.id.relativeLayout);

        recyclerView1 = (RecyclerView)view.findViewById(R.id.brand_fragment_list);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        networkService = ApplicationController.getInstance().getNetworkService();
        img = (ImageView)view.findViewById(R.id.brand_main_pic);

        manuname = getArguments().getString("manuname");

        getBrandResult();

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addBookmark();
            }
        });
        return view;
    }

    public void getBrandResult()
    {
        Call<BrandName> brandResult = networkService.getBrandResult(manuname);
        brandResult.enqueue(new Callback<BrandName>()
        {
            @Override
            public void onResponse(Call<BrandName> call, Response<BrandName> response)
            {
                if (response.isSuccessful())
                {
                    Log.i("MyTag", "Brand list");
                    data = response.body().result;
                    adapter1 = new BrandListAdapter(data.drymilk, clicklistener1);
                    recyclerView1.setAdapter(adapter1);
                    Glide.with(getActivity())
                            .load(data.manufactor.manu_image)
                            .into(img);
                }
            }

            @Override
            public void onFailure(Call<BrandName> call, Throwable t)
            {
                Log.i("MyTag", "실패~");
            }
        });
    }
    public View.OnClickListener clicklistener1 = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            final int position = recyclerView1.getChildPosition(v);
        }
    };

    public void addBookmark()
    {
        addbookmark mark = new addbookmark();
        mark.kind ="company";
        mark.id = getArguments().getInt("manuid");
        Log.i("mytag", mark.id+"");
        mark.nickname = auto.getString("nickname",null);

        Call<addbookmark> bookmark = networkService.addBookmark(mark);
        bookmark.enqueue(new Callback<addbookmark>()
        {
            @Override
            public void onResponse(Call<addbookmark> call, Response<addbookmark> response)
            {
                if (response.isSuccessful())
                {
                    Log.i("MyTag", "product detail success");
                    Toast.makeText(mContext, "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<addbookmark> call, Throwable t)
            {
                Log.i("MyTag", "product detail fail");
                Toast.makeText(mContext, "이미 추가된 제품 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
