package momma_beta.momma_bv.product;

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

import momma_beta.momma_bv.Model.ProductList;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import momma_beta.momma_bv.product.Adapter.AdapterProduct;
import momma_beta.momma_bv.product.Product_model.FilterProductInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductFragment extends MasterFragment {

	private NavigationActivity mContext;
	private LinearLayoutManager linearLayoutManager;
	private ArrayList<Itemdata_product> dataset;
	private AdapterProduct adapterProduct;
	private RecyclerView recyclerView;
	private ImageView filtering_btn;
	NetworkService networkService;
	ProductList.ResultData data;
	ArrayList<FilterProductInfo> productinfo;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState)
	{
		mContext = (NavigationActivity) getMasterActivity();
		View view = inflater.inflate(R.layout.activity_product, container, false);

		networkService = ApplicationController.getInstance().getNetworkService();
		filtering_btn = (ImageView)view.findViewById(R.id.filtering_btn_product);
		recyclerView = (RecyclerView)view.findViewById(R.id.recycleview_product_main);
		recyclerView.setHasFixedSize(true);

		linearLayoutManager = new LinearLayoutManager(mContext);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(linearLayoutManager);

		dataset = new ArrayList<Itemdata_product>();


		filtering_btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				mContext.changeFragment(13,null);
			}
		});
		if(getArguments().getSerializable("filterinfo") != null)
		{
			Toast.makeText(mContext, "필터링 결과", Toast.LENGTH_SHORT).show();
			productinfo = (ArrayList<FilterProductInfo>) getArguments().getSerializable("filterinfo");
			dataset.clear();
			for(int i=0; i< productinfo.size(); i++)
			{
				dataset.add(new Itemdata_product(productinfo.get(i).milkname, productinfo.get(i).image, productinfo.get(i).grade, productinfo.get(i).id));
			}

			adapterProduct = new AdapterProduct(dataset, mContext, clicklistener1);
			adapterProduct.notifyDataSetChanged();
			recyclerView.setAdapter(adapterProduct);
		}
		else
		{
			getProductListResult();
		}

		return view;
	}

	@Override
	public void onStart()
	{
		super.onStart();

	}

	public void getProductListResult()
	{
		Call<ProductList> productlist = networkService.getProductlistResult();
		productlist.enqueue(new Callback<ProductList>()
		{
			@Override
			public void onResponse(Call<ProductList> call, Response<ProductList> response)
			{
				if (response.isSuccessful())
				{
					Log.i("MyTag", "dry milklist");
					data = response.body().result;
					for(int i=0; i<data.drymilk.size();i++)
					{
						dataset.add(new Itemdata_product(data.drymilk.get(i).name,data.drymilk.get(i).image,data.drymilk.get(i).grade, data.drymilk.get(i).id));
					}

					adapterProduct = new AdapterProduct(dataset, mContext,clicklistener1);
					adapterProduct.notifyDataSetChanged();
					recyclerView.setAdapter(adapterProduct);

				}
			}
			@Override
			public void onFailure(Call<ProductList> call, Throwable t)
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
			Fragment reviewDetail = new ProductDetailFragment();
			Bundle bundle = new Bundle(2); // 파라미터는 전달할 데이터 개수
			bundle.putString("milkname",dataset.get(position).milkname); // key , value
			bundle.putInt("id",dataset.get(position).id);
			reviewDetail.setArguments(bundle);
			mContext.flag = 3;
			mContext.ReplaceFragement(reviewDetail);
		}
	};
}
