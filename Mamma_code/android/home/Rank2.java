package momma_beta.momma_bv.home;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import momma_beta.momma_bv.Model.HomeList;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Rank2 extends Fragment {
	public Activity activity;
	NetworkService networkService;
	HomeList.ResultData data;
	ImageView home_rank_image;
	TextView home_rank_txt;
	RatingBar home_rank_rating;
	TextView home_rank_txt1;

	public Rank2(Activity activity) {
		this.activity = activity;
	}
	@Override
	public void onResume() {
		super.onStart();
		getHomeResult();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_home_rank2, null);
		networkService = ApplicationController.getInstance().getNetworkService();
		home_rank_image = (ImageView)view.findViewById(R.id.home_rank2_img);
		home_rank_txt = (TextView)view.findViewById(R.id.home_rank2_txt);
        home_rank_txt1 = (TextView)view.findViewById(R.id.home_rank2_txt1);
        home_rank_rating=(RatingBar)view.findViewById(R.id.home_rank2_rating);

		getHomeResult();
		return view;
	}
	public void getHomeResult()
	{

		Call<HomeList> HomeResult = networkService.getHomeResult();
		HomeResult.enqueue(new Callback<HomeList>()
		{
			@Override
			public void onResponse(Call<HomeList> call, Response<HomeList> response)
			{
				if (response.isSuccessful())
				{
					data = response.body().result;
					update();
				}
			}

			@Override
			public void onFailure(Call<HomeList> call, Throwable t)
			{
				Log.i("MyTag", "실패~");
			}
		});
	}
	public void update()
	{
        Glide.with(getActivity())
                .load(data.searchrank.get(1).image)
                .into(home_rank_image);
		home_rank_txt.setText(data.searchrank.get(1).name);
		home_rank_txt1.setText(data.searchrank.get(1).company);

	}
}
