package gjw.bibi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import gjw.bibi.view.base.BaseFragment;

/**
 * Created by 皇上 on 2017/3/30.
 * 作者：郭建伟
 * QQ:519300884
 */

public class DLAdapter extends FragmentPagerAdapter {

    private final List<BaseFragment> fms;
    private String[] titles = {"已下载","下载中"};

    public DLAdapter(android.support.v4.app.FragmentManager supportFragmentManager, List<BaseFragment> fms) {
        super(supportFragmentManager);
        this.fms = fms;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return fms.get(position);
    }

    @Override
    public int getCount() {
        return fms.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
