package momma_beta.momma_bv.Search;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import momma_beta.momma_bv.R;


public class SearchFragment extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.search_fragment, container, false);


		return view;
	}
	public void ReplaceFragement(Fragment fragment)
	{
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.search_fragment_container, fragment);
		transaction.commit();
	}
}
