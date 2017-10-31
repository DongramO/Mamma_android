package momma_beta.momma_bv.MyPage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import momma_beta.momma_bv.Model.MyBookMark;
import momma_beta.momma_bv.MyPage.Mypage_Adapter.Adapter_myProduct;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class F_ProductFragment extends MasterFragment {

	private NavigationActivity mContext;
	private RecyclerView productrecyclerview;
//	private AdapterF_Product adapterF_product;
//	private ArrayList<Productitem> productitems;
	private LinearLayoutManager linearLayoutManager;
	NetworkService networkService;
	String nickname;
	SharedPreferences auto;
	String kind = "product";
	MyBookMark.ResultData data;
	Adapter_myProduct adapter1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = (NavigationActivity) getMasterActivity();

		auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
		nickname = auto.getString("nickname",null);
		networkService = ApplicationController.getInstance().getNetworkService();
		View view = inflater.inflate(R.layout.favorite_product_header, container, false);

		productrecyclerview = (RecyclerView)view.findViewById(R.id.mypage_product_rv);
		productrecyclerview.setHasFixedSize(true);

		linearLayoutManager = new LinearLayoutManager(mContext);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		productrecyclerview.setLayoutManager(linearLayoutManager);

//		productitems = new ArrayList<Productitem>();
//
//
//		adapterF_product = new AdapterF_Product(productitems);
//		productrecyclerview.setAdapter(adapterF_product);

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
					Log.i("MyTag",data.bookmark.size()+"");
					adapter1 = new Adapter_myProduct(data.bookmark,mContext);
					productrecyclerview.setAdapter(adapter1);

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
