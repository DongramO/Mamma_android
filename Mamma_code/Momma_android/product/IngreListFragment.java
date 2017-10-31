package momma_beta.momma_bv.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import momma_beta.momma_bv.Model.IngreDetailList;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import momma_beta.momma_bv.product.Adapter.Adapter_ingreList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IngreListFragment extends MasterFragment
{
    public IngreDialog customDialog;
    private NavigationActivity mContext;
    private LinearLayoutManager linearLayoutManager;
    private Adapter_ingreList adapter1;
    private RecyclerView recyclerView;
    private ImageView filtering_btn;
    NetworkService networkService;
    IngreDetailList.ResultData data;
    String milkname;
    String ingrename;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.search_result_ingredient_fragment, container, false);

        milkname = getArguments().getString("milkname");
        mContext = (NavigationActivity) getMasterActivity();

        networkService = ApplicationController.getInstance().getNetworkService();

        recyclerView = (RecyclerView)view.findViewById(R.id.RCV_ingredient_detail);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        getProductIngreDetailResult();
        return view;
    }

    public void getProductIngreDetailResult()
    {
        Call<IngreDetailList> productDetailInfoResult = networkService.getProductIngreDetailResult(milkname);
        productDetailInfoResult.enqueue(new Callback<IngreDetailList>()
        {
            @Override
            public void onResponse(Call<IngreDetailList> call, Response<IngreDetailList> response)
            {
                if (response.isSuccessful())
                {
                    Log.i("MyTag", "product detail success");
                    data = response.body().result;
                    adapter1 = new Adapter_ingreList(data.ingredient,clickListener);
                    recyclerView.setAdapter(adapter1);
                }
            }
            @Override
            public void onFailure(Call<IngreDetailList> call, Throwable t)
            {
                Log.i("MyTag", "product detail fail");
            }
        });
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            final int position = recyclerView.getChildPosition(v);
            TextView tv = (TextView) v.findViewById(R.id.ingre_name_kr);
            ingrename = tv.getText().toString();
            customDialog = new IngreDialog(getActivity(), " ", mClickCloseListener, ingrename);
            customDialog.setCanceledOnTouchOutside(true);
//            customDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            customDialog.show();

        }

    };

    View.OnClickListener mClickCloseListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            customDialog.dismiss();
        }
    };

}
