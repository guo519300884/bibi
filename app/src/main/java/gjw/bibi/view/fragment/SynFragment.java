package gjw.bibi.view.fragment;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.adapter.SearchResultAdapter;
import gjw.bibi.view. base.BaseFragment;
import gjw.bibi.bean.TagSearchBean;
import gjw.bibi.view.myview.MyGridView;

/**
 * Created by 皇上 on 2017/3/28.
 */

public class SynFragment extends BaseFragment {

    @InjectView(R.id.gv_sr)
    MyGridView gvSr;
    private View view;
    public SearchResultAdapter searchResultAdapter;
    private final List<TagSearchBean.DataBean.ItemsBean.ArchiveBean> archiveBeanList;

    public SynFragment(TagSearchBean.DataBean tagSearchBeanData) {
        super();
        Log.e("TAG", "SynFragment SynFragment()" + tagSearchBeanData);
        archiveBeanList = tagSearchBeanData.getItems().getArchive();
    }

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_sr, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {

        if (archiveBeanList != null && archiveBeanList.size() > 0) {

            searchResultAdapter = new SearchResultAdapter(context, archiveBeanList);
            gvSr.setAdapter(searchResultAdapter);

            gvSr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Toast.makeText(context, "5555", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
