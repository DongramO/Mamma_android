package momma_beta.momma_bv.Brand;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import momma_beta.momma_bv.R;
import momma_beta.momma_bv.navigation_main.MasterFragment;

public class BrandListFragment extends MasterFragment
{
    private FrameLayout brand_fragment_container;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.brand_fragment, container, false);
        brand_fragment_container = (FrameLayout)view.findViewById(R.id.brand_fragment_container);

        if (savedInstanceState == null)
        {
            Fragment landingFragment = new BrandFragment();
            ReplaceFragement(landingFragment);
        }

        return view;
    }
    public void ReplaceFragement(Fragment fragment)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.brand_fragment_container, fragment);
        transaction.commit();
    }
}
