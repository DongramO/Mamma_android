package momma_beta.momma_bv.Review;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import momma_beta.momma_bv.Model.ReviewDetail;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.Review.Review_model.Reviewbad;
import momma_beta.momma_bv.Review.Review_model.Reviewgood;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewDetailFragment extends MasterFragment {

	private NavigationActivity mContext;
	NetworkService networkService;
	String milkname,writer;
	int id;
	ImageView milk_image,goodbtn,badbtn;
	TextView nickname,goodnum, badnum, goodcoment, badcoment, tip, title, company, milkname1, goodandbad, nickworry, worry;
	RatingBar grade;
	ReviewDetail.ResultData data;
	Reviewgood.ResultData data1;
	Reviewbad.ResultData data2;
	String username="";
	SharedPreferences auto;
	SharedPreferences.Editor autoLogin;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		mContext = (NavigationActivity) getMasterActivity();
		View view = inflater.inflate(R.layout.activity_review_detail, container, false);
		networkService = ApplicationController.getInstance().getNetworkService();

		auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
		username = auto.getString("nickname",null);
		milkname = getArguments().getString("milkname");
		writer = getArguments().getString("writer");
		id = getArguments().getInt("reviewid");

		Log.i("myTag","리뷰상세보기 정보 : "+ milkname+ writer+id);

		milk_image = (ImageView)view.findViewById(R.id.detail_image);
		company = (TextView)view.findViewById(R.id.detail_company_txt);
		milkname1 = (TextView)view.findViewById(R.id.detail_milkname_txt);
//		nickname = (TextView)view.findViewById(R.id.detail_writer_txt);
		title = (TextView)view.findViewById(R.id.oneline_comment);
		grade = (RatingBar)view.findViewById(R.id.detail_review_rating);
		goodnum = (TextView)view.findViewById(R.id.detail_goodnum);
		badnum = (TextView)view.findViewById(R.id.detail_badnum);
		goodcoment = (TextView)view.findViewById(R.id.goodpoint_txt);
		badcoment = (TextView)view.findViewById(R.id.badpoint_txt);
		tip = (TextView)view.findViewById(R.id.honeytip_txt);
		goodandbad = (TextView)view.findViewById(R.id.goodandbad);
		nickworry = (TextView)view.findViewById(R.id.textView2);
		worry = (TextView)view.findViewById(R.id.textView3);
		goodbtn = (ImageView)view.findViewById(R.id.imageView5);
		badbtn = (ImageView)view.findViewById(R.id.imageView6);

		MyListener myListener = new MyListener();
		goodbtn.setOnClickListener(myListener);
		badbtn.setOnClickListener(myListener);

		getReviewDetailInfoResult();
		return view;
	}

	public void getReviewDetailInfoResult()
	{
		Call<ReviewDetail> reviewdetailinfo = networkService.getReviewDetailInfoResult(writer,milkname,id);
		reviewdetailinfo.enqueue(new Callback<ReviewDetail>()
		{
			@Override
			public void onResponse(Call<ReviewDetail> call, Response<ReviewDetail> response)
			{
				if (response.isSuccessful())
				{
					Log.i("MyTag", "review detail");
					data = response.body().result;
					Glide.with(getActivity())
							.load(data.review.milk_image)
							.into(milk_image);
					milkname1.setText(data.review.milk_name);
//					nickname.setText(writer);
					title.setText(data.review.title);
					grade.setRating(data.review.review_grade);
					goodnum.setText(data.review.goodnum);
					badnum.setText(data.review.badnum);
					goodcoment.setText(data.review.good);
					badcoment.setText(data.review.bad);
					tip.setText(data.review.tip);
					goodandbad.setText(writer+"의 리뷰 입니다.");
					nickworry.setText(writer+"의 아이는");
					worry.setText(data.review.worry);
				}
			}
			@Override
			public void onFailure(Call<ReviewDetail> call, Throwable t)
			{
				Log.i("MyTag", "실패~");
			}
		});
	}

	public void getReviewGoodResult()
	{
		Call<Reviewgood> reviewgoodCall = networkService.getReviewGoodResult(username,id);
		reviewgoodCall.enqueue(new Callback<Reviewgood>()
		{
			@Override
			public void onResponse(Call<Reviewgood> call, Response<Reviewgood> response)
			{
				if (response.isSuccessful())
				{
					Log.i("MyTag", "review detail");
					data1 = response.body().result;
					Log.i("Mytag",data1.goodnum+"");
					goodnum.setText(data1.goodnum);
					badnum.setText(data1.badnum);
				}
			}
			@Override
			public void onFailure(Call<Reviewgood> call, Throwable t)
			{
				Log.i("MyTag", "실패~");
			}
		});
	}
	public void getReviewBadResult()
	{
		Call<Reviewbad> reviewdetailinfo = networkService.getReviewBadResult(username,id);
		reviewdetailinfo.enqueue(new Callback<Reviewbad>()
		{
			@Override
			public void onResponse(Call<Reviewbad> call, Response<Reviewbad> response)
			{
				if (response.isSuccessful())
				{
					Log.i("MyTag", "review detail");
					data2 = response.body().result;
					goodnum.setText(data2.goodnum);
					badnum.setText(data2.badnum);
				}
			}
			@Override
			public void onFailure(Call<Reviewbad> call, Throwable t)
			{
				Log.i("MyTag", "실패~");
			}
		});
	}
	public class MyListener implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.imageView5:
				{
					getReviewGoodResult();
					Toast.makeText(mContext, "선호도를 수정 합니다.", Toast.LENGTH_SHORT).show();
					break;
				}
				case R.id.imageView6:
				{
					getReviewBadResult();
					Toast.makeText(mContext, "선호도를 수정 합니다.", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}
	}


}
