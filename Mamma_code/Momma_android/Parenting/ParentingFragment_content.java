package momma_beta.momma_bv.Parenting;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import momma_beta.momma_bv.R;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;


public class ParentingFragment_content extends MasterFragment {

	private NavigationActivity mContext;
	private LinearLayoutManager linearLayoutManager;
	private ArrayList<Itemdata_parenting> dataset;
	private Button filtering_btn;
	private RecyclerviewAdapterParenting recyclerviewAdapterParenting;
	private TabLayout tabLayout_parenting;
	private ViewPager viewPager_parenting;
	private RecyclerView recyclerView;
	private ParentingPagerAdapter parentingPagerAdapter;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mContext = (NavigationActivity) getMasterActivity();
		View view = inflater.inflate(R.layout.activity_parenting_list, container, false);
		recyclerView = (RecyclerView)view.findViewById(R.id.recycleview_parenting);
		recyclerView.setHasFixedSize(true);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(linearLayoutManager);

		dataset = new ArrayList<Itemdata_parenting>();
		dataset.add(new Itemdata_parenting(R.drawable.ready_icon, "1"));
		dataset.add(new Itemdata_parenting(R.drawable.ready_icon, "2"));
		dataset.add(new Itemdata_parenting(R.drawable.ready_icon, "3"));
		dataset.add(new Itemdata_parenting(R.drawable.ready_icon, "4"));
		dataset.add(new Itemdata_parenting(R.drawable.ready_icon, "5"));
		dataset.add(new Itemdata_parenting(R.drawable.ready_icon, "6"));

		recyclerviewAdapterParenting = new RecyclerviewAdapterParenting(dataset, mContext);
		recyclerView.setAdapter(recyclerviewAdapterParenting);

		return view;
	}
}
