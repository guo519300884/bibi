package gjw.bibi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gjw.bibi.view. base.BaseFragment;
import gjw.bibi.bean.TagSearchBean;

/**
 * Created by 皇上 on 2017/3/28.
 */

public class SearchAdapter extends FragmentStatePagerAdapter {

    private final List<BaseFragment> fms;
    private String title = "综合";
    private List titles = new ArrayList();

    private List<TagSearchBean.DataBean.NavBean> navBeen;
    private String nt;

    public SearchAdapter(FragmentManager sfm, List<BaseFragment> fms, TagSearchBean.DataBean tagSearchBeanData) {
        super(sfm);
        this.fms = fms;
        navBeen = tagSearchBeanData.getNav();

        initData();
    }

    private void initData() {
        titles.add(title);
        for (int i = 0; i < navBeen.size(); i++) {
            String name = navBeen.get(i).getName();
            int total = navBeen.get(i).getTotal();
            String tt = "(" + total + ")";

            if (total == 0) {
                nt = name;
            } else {
                nt = name + tt;
            }
            titles.add(nt);
        }
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
        return (CharSequence) titles.get(position);
    }
}