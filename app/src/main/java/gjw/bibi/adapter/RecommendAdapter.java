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
    //

    //当前类型
    private static int currentType = 1;


    private final Context context;
    private final List<RecommendBean.DataBean> recommendBeanData;
    private LayoutInflater inflater;

    public RecommendAdapter(Context context, List<RecommendBean.DataBean> recommendBeanData) {
        this.context = context;
        this.recommendBeanData = recommendBeanData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return 1;
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
//        @InjectView(R.id.iv_areas_content_icon1)
//        ImageView ivAreasContentIcon1;
//        @InjectView(R.id.tv_areas_content_title1)
//        TextView tvAreasContentTitle1;
//        @InjectView(R.id.tv_areas_content_name1)
//        TextView tvAreasContentName1;
//        @InjectView(R.id.tv_areas_content_watch1)
//        TextView tvAreasContentWatch1;
//        @InjectView(R.id.item_live_layout)
//        CardView itemLiveLayout;

        @InjectView(R.id.gv_recommend_gridview)
        MyGridView gvRecommendGridview;

        public GridviewViewHolder(Context context, View view) {
            super(view);
            ButterKnife.inject(this, view);
            this.context = context;
        }

        public void setData(List<RecommendBean.DataBean> recommendBeanData) {

//            Glide.with(context).load(recommendBeanData.get(getLayoutPosition()).getCover()).into(ivAreasContentIcon1);
//            tvAreasContentTitle1.setText(recommendBeanData.get(getLayoutPosition()).getTitle());
//            tvAreasContentName1.setText(recommendBeanData.get(getLayoutPosition()).getName());
//
//            tvAreasContentWatch1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "dddddddd", Toast.LENGTH_SHORT).show();
//                }
//            });


            GridviewAdapter gridviewAdapter = new GridviewAdapter(context, recommendBeanData);
            gvRecommendGridview.setAdapter(gridviewAdapter);

        }
    }
}
