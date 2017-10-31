package momma_beta.momma_bv.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;


public class HomeFragment extends MasterFragment
{
	private NavigationActivity mContext;
	private TabLayout tabLayout;
	private ViewPager viewPager;
	private HomePagerAdapter homePagerAdapter;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.i("mytag","start home fragment");
		mContext = (NavigationActivity)getMasterActivity();
		View view = inflater.inflate(R.layout.activity_home, container, false);

		tabLayout = (TabLayout)view.findViewById(R.id.hometablayout);
		viewPager = (ViewPager)view.findViewById(R.id.homeviewpager);


		tabLayout.addTab(tabLayout.newTab());
		tabLayout.addTab(tabLayout.newTab());
		tabLayout.addTab(tabLayout.newTab());


		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
		{
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				viewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		homePagerAdapter = new HomePagerAdapter(getFragmentManager(), tabLayout.getTabCount(), getActivity());
		homePagerAdapter.notifyDataSetChanged();
		viewPager.setCurrentItem(0);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setAdapter(homePagerAdapter);
		viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		return view;
	}


	@Override
	public void onResume() {
		super.onResume();
		homePagerAdapter.notifyDataSetChanged();
	}
}
