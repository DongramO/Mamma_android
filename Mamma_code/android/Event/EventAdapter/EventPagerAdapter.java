package momma_beta.momma_bv.Event.EventAdapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import momma_beta.momma_bv.Parenting.ParentingFragment_content;


/**
 * Created by pc on 2017-02-26.
 */

public class EventPagerAdapter extends FragmentPagerAdapter {
    private int tabCount;
    private Context context;
    private Activity activity;
    private int position;


    public EventPagerAdapter(FragmentManager fm, int tabCount, Activity activity) {
        super(fm);
        this.tabCount = tabCount;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        this.position = position;
        return new ParentingFragment_content();
    }
}
