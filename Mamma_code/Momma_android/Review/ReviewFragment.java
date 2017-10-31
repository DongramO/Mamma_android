package momma_beta.momma_bv.Review;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.FilterReviewInfo;
import momma_beta.momma_bv.Model.ReviewList;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewFragment extends MasterFragment {

	private NavigationActivity mContext;
	private ArrayList<Itemdata_review> dataset;
	private ImageView filtering_btn, review_write;
	private AdapterReview adapterProduct;
	private RecyclerView recyclerView;
	NetworkService networkService;
	ReviewList.ResultData data;
	ArrayList<FilterReviewInfo> filterReview;
	int initFlag =0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		networkService = ApplicationController.getInstance().getNetworkService();

		mContext = (NavigationActivity) getMasterActivity();
		View view = inflater.inflate(R.layout.activity_review, container, false);
		filtering_btn = (ImageView)view.findViewById(R.id.filtering_btn_review);
		review_write = (ImageView)view.findViewById(R.id.review_create_btn);
		recyclerView = (RecyclerView)view.findViewById(R.id.recycleview_review_main);
		recyclerView.setHasFixedSize(true);

		filtering_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view) {
				mContext.changeFragment(12,null);
			}
		});

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(linearLayoutManager);
		dataset = new ArrayList<Itemdata_review>();

		if(initFlag == 0)
		{
			initFlag++;
		}

//		review_write.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				mContext.changeFragment(14,null);
//			}
//		});

		return view;
	}


	@Override
	public void onStart()
	{
		super.onStart();
		if(getArguments().getSerializable("reviewinfo") != null)
		{
			Toast.makeText(mContext, "필터링 결과", Toast.LENGTH_SHORT).show();
			filterReview = (ArrayList<FilterReviewInfo>) getArguments().getSerializable("reviewinfo");
			for(int i=0; i< filterReview.size(); i++)
			{
				dataset.add(new Itemdata_review(filterReview.get(i).nickname, filterReview.get(i).title, filterReview.get(i).image, filterReview.get(i).grade, filterReview.get(i).id, filterReview.get(i).milkname));
			}

			adapterProduct = new AdapterReview(dataset, mContext, clicklistener1);
			adapterProduct.notifyDataSetChanged();
			recyclerView.setAdapter(adapterProduct);

		}
		else
		{
			getReviewListResult();
		}
	}

	public void getReviewListResult()
	{
		Call<ReviewList> productlist = networkService.getReviewlistResult();
		productlist.enqueue(new Callback<ReviewList>()
		{
			@Override
			public void onResponse(Call<ReviewList> call, Response<ReviewList> response)
			{
				if (response.isSuccessful())
				{
					Log.i("MyTag", "dry milklist");
					data = response.body().result;
					for(int i=0; i<data.review.size();i++)
					{
						dataset.add(new Itemdata_review(data.review.get(i).nickname,data.review.get(i).title, data.review.get(i).image, data.review.get(i).grade, data.review.get(i).id, data.review.get(i).milkname));
					}

					adapterProduct = new AdapterReview(dataset, mContext, clicklistener1);
					recyclerView.setAdapter(adapterProduct);

				}
			}
			@Override
			public void onFailure(Call<ReviewList> call, Throwable t)
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
			final int position = recyclerView.getChildPosition(v);
			Fragment reviewDetail = new ReviewDetailFragment();
			Bundle bundle = new Bundle(3); // 파라미터는 전달할 데이터 개수

			bundle.putString("milkname",dataset.get(position).milkname); // key , value
			bundle.putInt("reviewid",dataset.get(position).id); // key , value
			bundle.putString("writer",dataset.get(position).nickname); // key , value

			reviewDetail.setArguments(bundle);
			mContext.flag = 4;
			mContext.ReplaceFragement(reviewDetail);
		}
	};
}
