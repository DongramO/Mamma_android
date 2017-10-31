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
import momma_beta.momma_bv.MyPage.Mypage_Adapter.Adapter_myReview;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class F_ReviewFragment extends MasterFragment  {

	private NavigationActivity mContext;
	private RecyclerView reviewrecyclerview;
	private LinearLayoutManager linearLayoutManager;
	NetworkService networkService;
	String nickname;
	SharedPreferences auto;
	String kind = "review";
	Adapter_myReview adapter1;
	MyBookMark.ResultData data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		networkService = ApplicationController.getInstance().getNetworkService();
		mContext = (NavigationActivity) getMasterActivity();
		View view = inflater.inflate(R.layout.favorite_review_header, container, false);

		auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
		nickname = auto.getString("nickname",null);
		reviewrecyclerview = (RecyclerView) view.findViewById(R.id.mypage_review_rv);
		reviewrecyclerview.setHasFixedSize(true);

		linearLayoutManager = new LinearLayoutManager(mContext);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		reviewrecyclerview.setLayoutManager(linearLayoutManager);



//		adapterF_review = new AdapterF_Review(reviewitems);
//		reviewrecyclerview.setAdapter(adapterF_review);

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
					adapter1 = new Adapter_myReview(data.bookmark,mContext);
					reviewrecyclerview.setAdapter(adapter1);
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
