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

import java.util.ArrayList;

import momma_beta.momma_bv.Model.MyReview;
import momma_beta.momma_bv.MyPage.Mypage_Adapter.AdapterMy_Review;
import momma_beta.momma_bv.MyPage.Mypage_Adapter.Adapter_WritedReview;
import momma_beta.momma_bv.MyPage.Mypage_Adapter.Reviewitem;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class My_ReviewFragment extends MasterFragment
{

	private NavigationActivity mContext;
	private ArrayList<Reviewitem> reviewitems;
	private AdapterMy_Review adapterMy_review;
	private RecyclerView myreviewrecyclerview;
	private LinearLayoutManager linearLayoutManager;
	NetworkService networkService;
	String nickname;
	String kind = "review";
	SharedPreferences auto;
	Adapter_WritedReview adapter1;
	MyReview.ResultData data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mContext = (NavigationActivity) getMasterActivity();
		networkService = ApplicationController.getInstance().getNetworkService();
		View view = inflater.inflate(R.layout.activity_my_review, container, false);

		auto = this.getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
		nickname = auto.getString("nickname",null);
		myreviewrecyclerview = (RecyclerView) view.findViewById(R.id.myreviewrecyclerview);
		myreviewrecyclerview.setHasFixedSize(true);

		linearLayoutManager = new LinearLayoutManager(mContext);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		myreviewrecyclerview.setLayoutManager(linearLayoutManager);


		getMyReviewResult();
		return view;
	}

	public void getMyReviewResult()
	{
		Call<MyReview> mybookmarkresult = networkService.getMyReviewResult(nickname);
		mybookmarkresult.enqueue(new Callback<MyReview>()
		{
			@Override
			public void onResponse(Call<MyReview> call, Response<MyReview> response)
			{
				if (response.isSuccessful())
				{
					Log.i("MyTag", "my review list");
					data = response.body().result;
					Log.i("mytag",data.review.get(0).review_image);
					adapter1 = new Adapter_WritedReview(data.review,mContext);
					myreviewrecyclerview.setAdapter(adapter1);
				}
			}
			@Override
			public void onFailure(Call<MyReview> call, Throwable t)
			{
				Log.i("MyTag", "실패~");
			}
		});
	}
}
