package momma_beta.momma_bv.home;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


/**
 * Created by pc on 2017-02-26.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    // Count number of tabs
    private int tabCount;
    public Activity activity;


    public HomePagerAdapter(FragmentManager fm, int tabCount, Activity activity) {
        super(fm);
        this.tabCount = tabCount;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position)
    {
        // Returning the current tabs
        switch (position)
        {
            case 0:
                Rank1 rank1 = new Rank1(activity);
                return rank1;
            case 1:
                Rank2 rank2 = new Rank2(activity);
                return rank2;
            case 2:
                Rank3 rank3 = new Rank3(activity);
                return rank3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public int getItemPosition(Object object)
    {
        return super.getItemPosition(object);
    }
}
