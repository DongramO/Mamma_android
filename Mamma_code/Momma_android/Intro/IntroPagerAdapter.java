package momma_beta.momma_bv.Intro;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import momma_beta.momma_bv.home.Rank1;
import momma_beta.momma_bv.home.Rank2;
import momma_beta.momma_bv.home.Rank3;
import momma_beta.momma_bv.home.intro1_Fragment;


/**
 * Created by pc on 2017-02-26.
 */

public class IntroPagerAdapter extends FragmentPagerAdapter {
    // Count number of tabs
    private int tabCount;
    private Activity activity;


    public IntroPagerAdapter(FragmentManager fm, int tabCount, Activity activity) {
        super(fm);
        this.tabCount = tabCount;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                intro1_Fragment intro1_fragment = new intro1_Fragment(activity);
                return intro1_fragment;
            case 1:
                intro2_Fragment intro2_fragment = new intro2_Fragment(activity);
                return intro2_fragment;
            case 2:
                intro3_Fragment intro3_fragment = new intro3_Fragment(activity);
                return intro3_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
