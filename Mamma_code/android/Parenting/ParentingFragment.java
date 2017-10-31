package momma_beta.momma_bv.Parenting;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import momma_beta.momma_bv.R;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;

import static momma_beta.momma_bv.R.drawable.camera;
import static momma_beta.momma_bv.R.drawable.event;


public class ParentingFragment extends MasterFragment {

	private NavigationActivity mContext;
	private LinearLayoutManager linearLayoutManager;
	private ArrayList<Itemdata_parenting> dataset;
	private Button filtering_btn;
	private RecyclerviewAdapterParenting recyclerviewAdapterParenting;
	private TabLayout tabLayout_parenting;
	private ViewPager viewPager_parenting;
	private ParentingFragment_content parentingFragment_content;
	private ParentingPagerAdapter parentingPagerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mContext = (NavigationActivity) getMasterActivity();
		View view = inflater.inflate(R.layout.activity_parenting, container, false);


		tabLayout_parenting = (TabLayout)view.findViewById(R.id.parenting_tablayout);
		viewPager_parenting = (ViewPager)view.findViewById(R.id.parenting_viewpager);
//		viewPager_parenting.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View view, MotionEvent motionEvent) {
//				switch (motionEvent.getAction()){
//				}
//				return false;
//			}
//		});

		for(int i=0; i<3; i++){
			tabLayout_parenting.addTab(tabLayout_parenting.newTab());
		}

		tabLayout_parenting.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				viewPager_parenting.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		parentingPagerAdapter = new ParentingPagerAdapter(getFragmentManager(), tabLayout_parenting.getTabCount(),getActivity());
		viewPager_parenting.setAdapter(parentingPagerAdapter);
		viewPager_parenting.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_parenting));

		return view;
	}
}
