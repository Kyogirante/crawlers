package example.com.demo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by wang on 2015/7/27.
 */
public class TabFragmentAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles){
        this(fm);
        mFragments = fragments;
        mTitles = titles;
    }
    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mTitles.get(position);
    }
}
