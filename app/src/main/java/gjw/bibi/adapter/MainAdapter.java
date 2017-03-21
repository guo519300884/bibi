package gjw.bibi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import gjw.bibi.base.BaseFragment;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class MainAdapter extends FragmentPagerAdapter {


    private List<BaseFragment> fragments;
    private String[] titles = {"直播", "推荐", "追番", "分区", "动态", "发现"};

    public MainAdapter(FragmentManager fm, List<BaseFragment> fragments) {
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
