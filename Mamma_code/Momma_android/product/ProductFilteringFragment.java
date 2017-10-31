package momma_beta.momma_bv.product;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import momma_beta.momma_bv.product.Product_model.FilterProduct;
import momma_beta.momma_bv.product.Product_model.FilterProductInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductFilteringFragment extends MasterFragment {

	private NavigationActivity mContext;
	CheckBox hit,review,grade,brix,salt;
	CheckBox img1,img2,img3,img4,img5,img6,img7,img8,img9;
	Button complete;
	ArrayList<CheckBox> check;
	ArrayList<CheckBox> kind;
	NetworkService networkService;
	String selectKind="";
	String selectBrand="";
	Fragment FilterProduct;
	Bundle bundle;
	FilterProduct.ResultData data;
	ArrayList<FilterProductInfo> filter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mContext = (NavigationActivity) getMasterActivity();
		View view = inflater.inflate(R.layout.activity_product_filtering_fragment, container, false);
		networkService = ApplicationController.getInstance().getNetworkService();

		check = new ArrayList<>();
		kind = new ArrayList<>();
		bundle = new Bundle(1);
		hit = (CheckBox)view.findViewById(R.id.filtering_hit);kind.add(hit);
		review = (CheckBox)view.findViewById(R.id.filtering_review);kind.add(review);
		grade = (CheckBox)view.findViewById(R.id.filtering_grade);kind.add(grade);
		brix = (CheckBox)view.findViewById(R.id.filtering_brix);kind.add(brix);
		salt = (CheckBox)view.findViewById(R.id.filtering_salt);kind.add(salt);

		img1 = (CheckBox)view.findViewById(R.id.favorite_brand_img1);check.add(img1);
		img2 = (CheckBox)view.findViewById(R.id.favorite_brand_img2);check.add(img2);
		img3 = (CheckBox)view.findViewById(R.id.favorite_brand_img3);check.add(img3);
		img4 = (CheckBox)view.findViewById(R.id.favorite_brand_img4);check.add(img4);
		img5 = (CheckBox)view.findViewById(R.id.favorite_brand_img5);check.add(img5);
		img6 = (CheckBox)view.findViewById(R.id.favorite_brand_img6);check.add(img6);

		BrandListener filterListener = new BrandListener();
		img1.setOnClickListener(filterListener);
		img2.setOnClickListener(filterListener);
		img3.setOnClickListener(filterListener);
		img4.setOnClickListener(filterListener);
		img5.setOnClickListener(filterListener);
		img6.setOnClickListener(filterListener);


		KindListener kindListener = new KindListener();
		hit.setOnClickListener(kindListener);
		review.setOnClickListener(kindListener);
		grade.setOnClickListener(kindListener);
		brix.setOnClickListener(kindListener);
		salt.setOnClickListener(kindListener);




		complete = (Button)view.findViewById(R.id.f_product_end_btn);
		complete.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				getFilterProductResult();
			}

		});

		return view;
	}


	public void getFilterProductResult()
	{
		Call<FilterProduct> productlist = networkService.getFilterProductResult(selectKind,selectBrand);
		productlist.enqueue(new Callback<FilterProduct>()
		{
			@Override
			public void onResponse(Call<FilterProduct> call, Response<FilterProduct> response)
			{
				if (response.isSuccessful())
				{
					data = response.body().result;
					filter = data.productinfo;
					FilterProduct = new ProductFragment();
					bundle.putSerializable("filterinfo", filter); // key , value
					FilterProduct.setArguments(bundle);
					mContext.flag = 4;
					this.ReplaceFragement(FilterProduct);
				}

			}
			public void ReplaceFragement(Fragment fragment)
			{
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.fragment_container, fragment);
				transaction.commit();
			}
			@Override
			public void onFailure(Call<FilterProduct> call, Throwable t)
			{
				Log.i("MyTag", "실패~");
			}
		});
	}
	public class BrandListener implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			for(int i=0; i<check.size(); i++)
			{
				check.get(i).setChecked(false);
			}
			switch(v.getId())
			{
				case R.id.favorite_brand_img1:
					img1.setChecked(true);
					selectBrand ="매일유업";
					break;
				case R.id.favorite_brand_img2:
					img2.setChecked(true);
					selectBrand ="남양유업";
					break;
				case R.id.favorite_brand_img3:
					img3.setChecked(true);
					selectBrand ="파스퇴르";
					break;
				case R.id.favorite_brand_img4:
					img4.setChecked(true);
					selectBrand ="일동후디스";
					break;
				case R.id.favorite_brand_img5:
					img5.setChecked(true);
					selectBrand ="녹십자";
					break;
				case R.id.favorite_brand_img6:
					img6.setChecked(true);
					selectBrand ="아이배넷";
					break;
			}
		}
	}
	public class KindListener implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			for(int i=0; i<kind.size(); i++)
			{
				kind.get(i).setChecked(false);
			}
			switch(v.getId())
			{
				case R.id.filtering_hit:
					hit.setChecked(true);
					selectKind ="hit";
					break;
				case R.id.filtering_review:
					review.setChecked(true);
					selectKind ="review";
					break;
				case R.id.filtering_grade:
					grade.setChecked(true);
					selectKind ="grade";
					break;
				case R.id.filtering_brix:
					brix.setChecked(true);
					selectKind ="brix";
					break;
				case R.id.filtering_salt:
					salt.setChecked(true);
					selectKind ="salt";
					break;
			}
		}
	}

}
