package gjw.bibi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.bean.RecommendBean;
import gjw.bibi.view.MyGridView;

/**
 * Created by 皇上 on 2017/3/23.
 */

public class RecommendAdapter extends RecyclerView.Adapter {

    //GridView
    private static final int GRIDVIEW = 1;
    //当前类型
    private static int currentType = 1;


    private final Context context;
    private LayoutInflater inflater;
    private List<RecommendBean.DataBean> recommendBeanData;

    public void setDate(List<RecommendBean.DataBean> data) {
        this.recommendBeanData = data;
    }

    public RecommendAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return recommendBeanData == null ? 0 : recommendBeanData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == GRIDVIEW) {
            currentType = GRIDVIEW;
        }
        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == GRIDVIEW) {
            return new GridviewViewHolder(context, inflater.inflate(R.layout.item_recommend_gridview, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == GRIDVIEW) {
            GridviewViewHolder gridviewViewHolder = (GridviewViewHolder) holder;
            gridviewViewHolder.setData(recommendBeanData);
        }
    }

    static class GridviewViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        @InjectView(R.id.gv_recommend_gridview)
        MyGridView gvRecommendGridview;

        public GridviewViewHolder(Context context, View view) {
            super(view);
            ButterKnife.inject(this, view);
            this.context = context;
        }

        public void setData(List<RecommendBean.DataBean> recommendBeanData) {

            GridviewAdapter gridviewAdapter = new GridviewAdapter(context, recommendBeanData);
            gvRecommendGridview.setAdapter(gridviewAdapter);

        }
    }
}
