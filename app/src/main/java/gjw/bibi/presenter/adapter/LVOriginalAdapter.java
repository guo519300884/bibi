package gjw.bibi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.activity.CircumActivity;
import gjw.bibi.bean.OriginalBean;
import gjw.bibi.utils.DensityUtil;

/**
 * Created by 皇上 on 2017/3/22.
 */

public class LVOriginalAdapter extends BaseAdapter {

    private final Context context;
    private final List<OriginalBean.DataBean> originalBeanData;
    private String s;

    public LVOriginalAdapter(Context context, List<OriginalBean.DataBean> originalBeanData) {
        this.context = context;
        this.originalBeanData = originalBeanData;
    }

    @Override
    public int getCount() {
        return originalBeanData.size();
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
            convertView = View.inflate(context, R.layout.item_allstation, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        OriginalBean.DataBean dataBean = originalBeanData.get(position);

        Glide.with(context).load(dataBean.getCover()).into(viewHolder.ivAllstation);

        viewHolder.tvAllstation.setText(dataBean.getTitle());
        viewHolder.tvUpName.setText("UP主：" + dataBean.getName());
        viewHolder.tvGrade.setText("综合评分：" + String.valueOf(dataBean.getPts()));


        viewHolder.tvRanking.setText((position + 1) + "");
        if (position == 0) {
            viewHolder.tvRanking.setTextColor(Color.parseColor("#fb7299"));
            viewHolder.tvRanking.setTextSize(DensityUtil.px2dip(context, 60));
        }
        if (position == 1) {
            viewHolder.tvRanking.setTextColor(Color.parseColor("#fb7299"));
            viewHolder.tvRanking.setTextSize(DensityUtil.px2dip(context, 50));
        }
        if (position == 2) {
            viewHolder.tvRanking.setTextColor(Color.parseColor("#fb7299"));
            viewHolder.tvRanking.setTextSize(DensityUtil.px2dip(context, 40));
        }


        viewHolder.llAllstation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CircumActivity.class);
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.tv_ranking)
        TextView tvRanking;
        @InjectView(R.id.iv_allstation)
        ImageView ivAllstation;
        @InjectView(R.id.tv_allstation)
        TextView tvAllstation;
        @InjectView(R.id.tv_up_name)
        TextView tvUpName;
        @InjectView(R.id.tv_grade)
        TextView tvGrade;
        @InjectView(R.id.ll_allstation)
        LinearLayout llAllstation;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
