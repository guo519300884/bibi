package gjw.bibi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import gjw.bibi.base.BaseFragment;
import gjw.bibi.bean.TagSearchBean;

/**
 * Created by 皇上 on 2017/3/28.
 */

public class SearchAdapter extends FragmentPagerAdapter {
    private final List<BaseFragment> fms;
    private List<TagSearchBean.DataBean.NavBean> navBeen;


    public SearchAdapter(FragmentManager sfm, List<BaseFragment> fms, TagSearchBean.DataBean tagSearchBeanData) {
        super(sfm);
        this.fms = fms;
        navBeen = tagSearchBeanData.getNav();
    }

    private String[] titles = {"综合",
            navBeen.get(0).getName() + "(" + navBeen.get(0).getTotal() + ")",
            navBeen.get(1).getName() + "(" + navBeen.get(1).getTotal() + ")",
            navBeen.get(2).getName() + "(" + navBeen.get(2).getTotal() + ")"};

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
