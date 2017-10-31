package momma_beta.momma_bv.Search;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import momma_beta.momma_bv.Brand.BrandFragment;
import momma_beta.momma_bv.Model.IngreList;
import momma_beta.momma_bv.Model.SearchResult;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import momma_beta.momma_bv.product.IngreDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Search_Result_Fragment extends MasterFragment
{
	NetworkService networkService, networkService2;
	NavigationActivity mContext;
	SearchResult.ResultData data;
	String milkname, ingrename;
	BrandAdapter adapter1; //리사이클러뷰 어뎁터
	IngreAdapter adapter2;
	ProductAdapter adapter3;
	LinearLayoutManager linearLayoutManager1,linearLayoutManager2,linearLayoutManager3; //사용할 레이아웃 매니저
	RecyclerView recyclerView1, recyclerView2, recyclerView3;
    IngreList.ResultData data1;
    TextView name_kr, name_en, effect1, effect2, effect3;
    LayoutInflater dialog;
    View dialogLayout;
    IngreDialog customDialog;
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mContext = (NavigationActivity) getMasterActivity();
		networkService = ApplicationController.getInstance().getNetworkService();
        networkService2 = ApplicationController.getInstance().getNetworkService();
		View view = inflater.inflate(R.layout.search_result_fragment, container, false);
		milkname = getArguments().getString("milkname");


		recyclerView1 = (RecyclerView)view.findViewById(R.id.search_result_brand);
		recyclerView1.setHasFixedSize(true);
		recyclerView1.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

		linearLayoutManager1 = new LinearLayoutManager(getActivity());
		linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView1.setLayoutManager(linearLayoutManager1);

		recyclerView2 = (RecyclerView)view.findViewById(R.id.search_result_ingredient);
		recyclerView2.setHasFixedSize(true);
		recyclerView2.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

		linearLayoutManager2 = new LinearLayoutManager(getActivity());
		linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView2.setLayoutManager(linearLayoutManager2);

		recyclerView3 = (RecyclerView)view.findViewById(R.id.search_result_product);
		recyclerView3.setHasFixedSize(true);
		recyclerView3.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

		linearLayoutManager3 = new LinearLayoutManager(getActivity());
		linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView3.setLayoutManager(linearLayoutManager3);


        getSearchResult();

		// 데이터셋과 리스너를 어뎁터 생성자에 셋팅, 리사이클러뷰 어뎁터에 셋어뎁터

		return view;
	}

	public View.OnClickListener clicklistener1 = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			final int position = recyclerView1.getChildPosition(v);

//			mContext.changeFragment(17,null);
			Fragment search = new BrandFragment();
			Bundle bundle = new Bundle(2); // 파라미터는 전달할 데이터 개수
			bundle.putString("manuname",data.manufactor.get(position).manu_name); // key , value
			bundle.putInt("manuid",data.manufactor.get(position).manu_id);
			Log.i("mytag","manu id : "+data.manufactor.get(position).manu_id);
			search.setArguments(bundle);
			ReplaceFragement(search);
			mContext.title_image.setVisibility(View.INVISIBLE);
			mContext.searchbar.setVisibility(View.GONE);
			mContext.titlerl.setVisibility(View.VISIBLE);
			mContext.title_text.setText("회사 정보");
			mContext.flag =100;

		}
	};
	public View.OnClickListener clicklistener2 = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			final int position = recyclerView2.getChildPosition(v);
            TextView tv = (TextView) v.findViewById(R.id.ingredient_txt1);
            ingrename = tv.getText().toString();
            Log.i("Mytag",  ingrename+"");

            customDialog = new IngreDialog(getActivity(), " ", mClickCloseListener, ingrename);
            customDialog.setCanceledOnTouchOutside(true);
            customDialog.show();

		}
	};
	public View.OnClickListener clicklistener3 = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			final int position = recyclerView3.getChildPosition(v);
		}
	};


    public void getSearchResult()
    {
        Call<SearchResult> searchResult = networkService.getSearchResult(milkname);
        searchResult.enqueue(new Callback<SearchResult>()
        {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response)
            {
                if (response.isSuccessful())
                {
                    Log.i("MyTag", "성공~2");

                    data = response.body().result;
					if(data.manufactor != null){
						adapter1 = new BrandAdapter(data.manufactor, clicklistener1);
						recyclerView1.setAdapter(adapter1);
					}
					if(data.ingredient != null) {
						adapter2 = new IngreAdapter(data.ingredient, clicklistener2);
						recyclerView2.setAdapter(adapter2);
					}
					if(data.drymilk != null) {
						Log.i("mytag","ddd");
						adapter3 = new ProductAdapter(data.drymilk, clicklistener3, getActivity());
						recyclerView3.setAdapter(adapter3);
					}
					if(data.manufactor == null && data.ingredient == null && data.drymilk == null)
					{
						Toast.makeText(mContext, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
					}
                }
            }
            @Override
            public void onFailure(Call<SearchResult> call, Throwable t)
            {
                Log.i("MyTag", "실패~");
            }
        });
    }
    View.OnClickListener mClickCloseListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            customDialog.dismiss();
        }
    };
	public void ReplaceFragement(Fragment fragment)
	{

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.addToBackStack(null);
		transaction.replace(R.id.fragment_container, fragment);
		transaction.commit();
	}


}
