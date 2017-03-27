package gjw.bibi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.bean.ZoneTypeBean;
import gjw.bibi.utils.TimeUtils;
import gjw.bibi.videoplayer.DanmkuVideoActivity;

import static gjw.bibi.adapter.AllareasAdapter.VIDEO;
import static gjw.bibi.adapter.AllareasAdapter.VT;

/**
 * Created by 皇上 on 2017/3/24.
 */

class ZoneRegionAdapter extends BaseAdapter {

    private final Context context;
    private final ZoneTypeBean.DataBean dataBean;
    private Intent intent;

    public ZoneRegionAdapter(Context context, ZoneTypeBean.DataBean dataBean) {
        this.context = context;
        this.dataBean = dataBean;
    }

    @Override
    public int getCount() {
        return 4;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gv_gv, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(dataBean.getBody().get(position).getCover()).into(viewHolder.ivAreasContentIcon1);
        viewHolder.tvAreasContentTitle1.setText(dataBean.getBody().get(position).getTitle());

        int play = dataBean.getBody().get(position).getPlay();
        TimeUtils timeUtils = new TimeUtils();
        String number = timeUtils.play(play);
        viewHolder.tvAreasContentName1.setText(number);

        viewHolder.tvAreasContentWatch1.setText(dataBean.getBody().get(position).getDanmaku() + "");

        viewHolder.itemLiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = dataBean.getBody().get(position).getTitle();
                String uri = dataBean.getBody().get(position).getUri();

                intent = new Intent(context, DanmkuVideoActivity.class);
                intent.putExtra(VIDEO, uri);
                intent.putExtra(VT, title);

                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_areas_content_icon1)
        ImageView ivAreasContentIcon1;
        @InjectView(R.id.tv_areas_content_title1)
        TextView tvAreasContentTitle1;
        @InjectView(R.id.tv_areas_content_name1)
        TextView tvAreasContentName1;
        @InjectView(R.id.tv_areas_content_watch1)
        TextView tvAreasContentWatch1;
        @InjectView(R.id.item_live_layout)
        CardView itemLiveLayout;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
