package gjw.bibi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.bean.ZoneHeadBean;

/**
 * Created by 皇上 on 2017/3/23.
 */

public class ZoneHeadAdapter extends BaseAdapter {

    private final List<ZoneHeadBean.DataBean> zoneHeadBeanData;
    private final Context context;

    public ZoneHeadAdapter(Context context, List<ZoneHeadBean.DataBean> zoneHeadBeanData) {
        this.context = context;
        this.zoneHeadBeanData = zoneHeadBeanData;
    }

    @Override
    public int getCount() {
        return zoneHeadBeanData.size();
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
            convertView = View.inflate(context, R.layout.item_zone_head_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ZoneHeadBean.DataBean dataBean = zoneHeadBeanData.get(position);

        Glide.with(context).load(dataBean.getLogo()).into(viewHolder.ivRecommend);
        viewHolder.tvRecommend.setText(dataBean.getName());

        viewHolder.zoneTitleClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "3563513154365", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_recommend)
        ImageView ivRecommend;
        @InjectView(R.id.tv_recommend)
        TextView tvRecommend;
        @InjectView(R.id.zone_title_classify)
        LinearLayout zoneTitleClassify;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
