package gjw.bibi.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.bean.RecommendBean;
import gjw.bibi.utils.TimeUtils;

/**
 * Created by 皇上 on 2017/3/23.
 */

class GridviewAdapter extends BaseAdapter {

    private final List<RecommendBean.DataBean> recommendBeanData;
    private final Context context;

    public GridviewAdapter(Context context, List<RecommendBean.DataBean> recommendBeanData) {
        this.context = context;
        this.recommendBeanData = recommendBeanData;
    }

    @Override
    public int getCount() {
        return recommendBeanData == null ? 0 : recommendBeanData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_recommend_gridview_content, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final RecommendBean.DataBean dataBean = recommendBeanData.get(position);

        Glide.with(context).load(dataBean.getCover()).into(viewHolder.ivAreasContentIcon1);

        int play = dataBean.getPlay();
        if (play > 9999) {
            float playf = ((float) play - (float) play % 1000) / 10000;
            viewHolder.paly.setText(playf + "万");
        } else {
            viewHolder.paly.setText(play + "");
        }

        viewHolder.danmakus.setText(dataBean.getDanmaku() + "");

        //毫秒变时分秒
        TimeUtils timeUtils = new TimeUtils();
        int duration = dataBean.getDuration();
        String time = timeUtils.stringForTime(duration * 1000);

        viewHolder.time.setText(time);

        viewHolder.tvAreasContentTitle1.setText(dataBean.getTitle());
        viewHolder.tvAreasContentName1.setText(dataBean.getTname());


        viewHolder.tvAreasContentWatch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                new AlertDialog.Builder(context)
//                        .setView()
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//
//
//
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .show();


                Toast.makeText(context, "dddddd", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.itemLiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "KKKKKKKK", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_areas_content_icon1)
        ImageView ivAreasContentIcon1;
        @InjectView(R.id.paly)
        TextView paly;
        @InjectView(R.id.danmakus)
        TextView danmakus;
        @InjectView(R.id.time)
        TextView time;
        @InjectView(R.id.tv_areas_content_title1)
        TextView tvAreasContentTitle1;
        @InjectView(R.id.tv_areas_content_name1)
        TextView tvAreasContentName1;
        @InjectView(R.id.tv_areas_content_watch1)
        ImageView tvAreasContentWatch1;
        @InjectView(R.id.item_live_layout)
        CardView itemLiveLayout;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
