package gjw.bibi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import gjw.bibi.base.BaseFragment;

/**
 * Created by 皇上 on 2017/3/22.
 */

public class OriginalAdapter extends FragmentPagerAdapter {


    private final List<BaseFragment> fragments;
    private String[] titles = {"原创", "全站", "番剧"};

    public OriginalAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
